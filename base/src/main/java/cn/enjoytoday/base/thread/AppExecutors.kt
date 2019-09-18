package cn.enjoytoday.base.thread

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 全局线程池管理类
 * @see runMainThread 运行于主线程
 * @see runIoThread io线程
 * @see runNetWorkThread 网络请求线程池
 */
object AppExecutors {


    /**
     * 切换主线程
     */
   fun runMainThread(method:() -> Unit){
       ExecutorInternal().mainThread().execute {
           method()
       }
   }

    /**
     * 切换io线程(但线程线程池)
     */
    fun runIoThread(method: () -> Unit){
        ExecutorInternal().diskIO().execute {
            method()
        }
    }


    /**
     * 网络线程池(maxThread 10)
     */
    fun runNetWorkThread(method: () -> Unit){
        ExecutorInternal().networkIO().execute {
            method()
        }
    }


    /**
     * 线程内部类
     */
    class ExecutorInternal{
        private  val THREAD_COUNT = 10
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


        fun mainThread():Executor {
            return  mainThread
        }
    }

}