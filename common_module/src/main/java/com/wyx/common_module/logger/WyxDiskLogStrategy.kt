package com.wyx.common_module.logger

import android.os.Environment
import com.orhanobut.logger.LogStrategy
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors

const val MAX_BYTES = 1000 * 1024 // 500K averages to a 4000 lines per file

class MCDiskLogStrategy : LogStrategy {

    private val dateFormatDate = SimpleDateFormat("yyyy.MM.dd_HH", Locale.CHINA)
    private val format = SimpleDateFormat("yyyyMMdd", Locale.CHINA)

    private val mOtherService =
        Executors.newSingleThreadExecutor()

    private val folder = Environment.getExternalStorageDirectory()
        .absolutePath + File.separatorChar + "DC/logger"
    private val pool: ArrayListPool by lazy {
        ArrayListPool()
    }
    private val queueMap: ConcurrentHashMap<String, ConcurrentLinkedQueue<LogMessage>> by lazy {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<LogMessage>>()
    }

    override fun log(level: Int, tag: String?, message: String) {
        val logMessage = LogMessage(tag!!, message)
        queueMap[tag] ?: queueMap[tag].apply {
            queueMap[tag] = ConcurrentLinkedQueue<LogMessage>()
        }

        queueMap[tag]!!.offer(logMessage)
        checkLength(tag)
    }

    @Synchronized
    private fun checkLength(tag: String) {

        if (!queueMap[tag]!!.isEmpty()) {
            mOtherService.execute(Runnable {
                val queue: Queue<LogMessage>? = queueMap[tag]
                val m = queue!!.poll() ?: return@Runnable
                findFileAndWrite(m.tag, m.message)
            })
        }
        mOtherService.execute { pollAndWrite(tag) }
    }

    @Synchronized
    private fun pollAndWrite(tag: String) {
        val logMessageList: MutableList<LogMessage> = pool.obtain()
        queueMap[tag]?.apply {
            repeat(size) {
                poll()?.let {
                    logMessageList.add(it)
                    findFileAndWriteList(tag, logMessageList)
                }
            }
        }
    }

    class ArrayListPool {
        private val concurrentLinkedQueue =
            ConcurrentLinkedQueue<MyRecycleList>()

        @Synchronized
        fun obtain(): MyRecycleList {
            return if (concurrentLinkedQueue.isEmpty()) {
                MyRecycleList()
            } else {
                concurrentLinkedQueue.poll()!!
            }
        }

        @Synchronized
        fun recycleList(list: MyRecycleList) {
            concurrentLinkedQueue.offer(list)
        }

        inner class MyRecycleList : ArrayList<LogMessage>() {
            fun recycle() {
                clear()
                recycleList(this)
            }
        }
    }

    data class LogMessage(val tag: String, val message: String?)

    private fun findFileAndWriteList(
        tag: String,
        list: List<LogMessage>
    ) {
        val date = dateFormatDate.format(Date())
        val day = format.format(Date())
        val hour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val path =
            folder + File.separatorChar + day + File.separatorChar + hour
        var fileWriter: FileWriter? = null
        val logFile = getLogFile(path, tag + "_" + date, MAX_BYTES)
        try {
            fileWriter = FileWriter(logFile, true)
            for (message in list) {
                writeLog(fileWriter, message.message)
            }
            fileWriter.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            (list as ArrayListPool.MyRecycleList).recycle()
            if (null != fileWriter) {
                try {
                    fileWriter.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun findFileAndWrite(tag: String, message: String?) {
        val date = dateFormatDate.format(Date())
        val day = format.format(Date())
        val hour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val path =
            folder + File.separatorChar + day + File.separatorChar + hour
        var fileWriter: FileWriter? = null
        val logFile = getLogFile(path, tag + "_" + date, MAX_BYTES)
        try {
            fileWriter = FileWriter(logFile, true)
            writeLog(fileWriter, message)
            fileWriter.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (null != fileWriter) {
                try {
                    fileWriter.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * This is always called on a single background thread.
     * Implementing classes must ONLY write to the fileWriter and nothing more.
     * The abstract class takes care of everything else including close the stream and catching IOException
     *
     * @param fileWriter an instance of FileWriter already initialised to the correct file
     */
    @Throws(IOException::class)
    private fun writeLog(
        fileWriter: FileWriter,
        content: String?
    ) {
        fileWriter.append(content)
    }

    private fun getLogFile(
        folderName: String,
        fileName: String,
        maxFileSize: Int
    ): File {
        val folder = File(folderName)
        if (!folder.exists()) {
            //TODO: What if folder is not created, what happens then?
            folder.mkdirs()
        }
        var newFileCount = 0
        var newFile: File
        var existingFile: File? = null
        newFile = File(folder, String.format("%s_%s.csv", fileName, newFileCount))
        while (newFile.exists()) {
            existingFile = newFile
            newFileCount++
            newFile =
                File(folder, String.format("%s_%s.csv", fileName, newFileCount))
        }
        return if (existingFile != null) {
            if (existingFile.length() >= maxFileSize) {
                newFile
            } else existingFile
        } else newFile
    }
}