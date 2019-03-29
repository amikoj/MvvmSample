package cn.enjoytoday.base.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * 主线程线程池
 */
class MainThreadExecutor :Executor{
    private val mainHandler = Handler(Looper.getMainLooper())


    override fun execute(command: Runnable?) {
        mainHandler.post(command)
    }
}