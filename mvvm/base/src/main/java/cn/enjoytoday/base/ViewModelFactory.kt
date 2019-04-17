package cn.enjoytoday.base

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.enjoytoday.base.data.BaseRepository


/**
 * A creator is used to inject the product ID into the ViewModel
 *
 *
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
class ViewModelFactory private constructor(
     val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    /**
     * 工厂模式创建
     */
    override fun <T : ViewModel> create(modelClass: Class<T>) = modelClass.newInstance()


    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance() =
            INSTANCE
                    ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE
                        ?: ViewModelFactory(
                                BaseRepository())
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
