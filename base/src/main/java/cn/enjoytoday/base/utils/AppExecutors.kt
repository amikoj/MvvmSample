package cn.enjoytoday.base.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 全局线程池管理类
 */
class AppExecutors() {
    private  val THREAD_COUNT = 5
    private  val diskIO:Executor
    private  val  networkIO:Executor
    private  val  mainThread:Executor

    init {
        diskIO = DiskIOThreadExecutor()
        networkIO = Executors.newFixedThreadPool(THREAD_COUNT)
        mainThread = MainThreadExecutor()

    }


    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }



}