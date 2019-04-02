package cn.enjoytoday.base

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.enjoytoday.base.data.base.BaseRepository
import cn.enjoytoday.base.viewmodel.*


/**
 * viewModel生成器，通过代理模式创建ViewModel代理
 */
class ViewModelFactory private constructor(private val mApplication: Application, val tasksRepository: BaseRepository)
    : ViewModelProvider.NewInstanceFactory() {


    /**
     * 代理模式创建viewModel
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(mApplication,tasksRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(mApplication, tasksRepository) as T
            modelClass.isAssignableFrom(ContactViewModel::class.java) -> ContactViewModel(mApplication, tasksRepository) as T
            modelClass.isAssignableFrom(CallViewModel::class.java) -> CallViewModel(mApplication, tasksRepository) as T
            modelClass.isAssignableFrom(SMSViewModel::class.java) -> SMSViewModel(mApplication, tasksRepository) as T
            modelClass.isAssignableFrom(PhotosViewModel::class.java) -> PhotosViewModel(mApplication, tasksRepository) as T
            modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> ScheduleViewModel(mApplication, tasksRepository) as T
            modelClass.isAssignableFrom(TestViewModel::class.java) -> TestViewModel(mApplication, tasksRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {

            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(application, BaseRepository())
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
