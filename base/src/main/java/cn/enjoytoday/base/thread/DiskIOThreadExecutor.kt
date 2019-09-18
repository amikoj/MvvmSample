package cn.enjoytoday.base.thread

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * IO线程线程池
 */
class DiskIOThreadExecutor(val mDiskIo:Executor = Executors.newSingleThreadExecutor()):Executor{

    override fun execute(command: Runnable?) {
        mDiskIo.execute(command)
    }
}