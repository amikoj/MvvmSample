package cn.enjoytoday.base.pattern

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import cn.enjoytoday.base.BaseActivity
import cn.enjoytoday.base.obtainViewModel
import cn.enjoytoday.base.utils.LogUtils
import java.lang.reflect.ParameterizedType

/**
 * @ClassName BaseMvvmActivity
 * @Description 与layout绑定的activity继承基类
 * @Author hfcai
 * @Date 2019/4/11 9:41
 * @Version 1.0
 *
 */
abstract class BaseMvvmActivity<in T: ViewDataBinding, V:ViewModel>(): BaseActivity() {
    /**
     * 泛型占位符
     */
    private  val tClass:Class<T>
    private  val vClass:Class<V>

    constructor( layoutId:Int):this()

    init {
        val type = javaClass.genericSuperclass
        val types = (type as ParameterizedType).actualTypeArguments
        tClass = types[0] as Class<T>
        vClass = types[1] as Class<V>
    }

    /**
     * 数据绑定生成类
     */
    private lateinit var viewBinding:T

    /**
     * 数据操作ViewModel
     */
    protected lateinit  var viewModel: V

    override fun inflateLayout() {
        viewModel = obtainViewModel(vClass).also {
            //observe
            observe(it)
        }
        val method = tClass.getMethod("inflate", LayoutInflater::class.java)
        method.isAccessible =true
        viewBinding=  (method.invoke(null,layoutInflater) as T).apply {
            val objClass = javaClass
            try{
                val mt =   objClass.methods.last {
                    it.name.startsWith("set") && it.parameterTypes.size==1 && it.parameterTypes[0] == vClass
                }
                mt?.isAccessible = true
                mt?.invoke(this,viewModel)
            }catch (e:Exception){
                e.printStackTrace()
            }
            lifecycleOwner = this@BaseMvvmActivity
        }
        setContentView(viewBinding.root)
    }


    /**
     * 数据监听回调
     * @param v 绑定的viewModel
     */
    abstract fun observe(v:V)

    /**
     * setContent之后
     */
    open fun init()=Unit


}