package cn.enjoytoday.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.enjoytoday.base.data.base.BaseRepository
import cn.enjoytoday.base.viewmodel.*


/**
 * viewModel生成器，通过代理模式创建ViewModel代理
 */
class ViewModelFactory private constructor(val tasksRepository: BaseRepository)
    : ViewModelProvider.NewInstanceFactory() {


    /**
     * 代理模式创建viewModel
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T  =

        with(modelClass){
            when {

                isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(tasksRepository)
                modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel( tasksRepository)
                modelClass.isAssignableFrom(ContactViewModel::class.java) -> ContactViewModel(tasksRepository)
                modelClass.isAssignableFrom(CallViewModel::class.java) -> CallViewModel(tasksRepository)
                modelClass.isAssignableFrom(SMSViewModel::class.java) -> SMSViewModel(tasksRepository)
                modelClass.isAssignableFrom(PhotosViewModel::class.java) -> PhotosViewModel( tasksRepository)
                modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> ScheduleViewModel(tasksRepository)
                modelClass.isAssignableFrom(TestViewModel::class.java) -> TestViewModel( tasksRepository)
                else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
            }
        } as T




    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(
                            BaseRepository())
                            .also { INSTANCE = it }
                }


        @VisibleForTesting fun destroyInstance() {
            INSTANCE = null
        }
    }
}
