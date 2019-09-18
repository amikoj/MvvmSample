package cn.enjoytoday.base.pattern

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import cn.enjoytoday.base.BaseFragment
import cn.enjoytoday.base.obtainViewModel
import cn.enjoytoday.base.utils.LogUtils
import java.lang.reflect.ParameterizedType

/**
 * @ClassName BaseMvvmFragment
 * @Description TODO
 * @Author 绑定数据类型的fragment
 * @Date 2019/4/11 16:19
 * @Version 1.0
 */
abstract class BaseMvvmFragment<in T: ViewDataBinding, V: ViewModel>() : BaseFragment(){

    /**
     * 泛型占位符
     */
    private  val tClass:Class<T>
    private  val vClass:Class<V>
    constructor(layoutId:Int):this()


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            if (it is AppCompatActivity){
                viewModel =  it.obtainViewModel(vClass).also { viewModel ->
                    observe(viewModel)
                }
            }
        }
        val method = tClass.getMethod("inflate", LayoutInflater::class.java,ViewGroup::class.java,
            Boolean::class.java)
        method.isAccessible =true
        viewBinding=  (method.invoke(null,layoutInflater,container,false) as T).apply {
            val objClass = javaClass
            try{
                val mt =   objClass.methods.last {
                    it.name.startsWith("set") && it.parameterTypes.size==1 && it.parameterTypes[0] == vClass
                }

                mt?.isAccessible = true
                mt.invoke(this,viewModel)
            }catch (e: Exception){
                e.printStackTrace()
                LogUtils.e("获取viewModel异常:${e.message}")
            }
            lifecycleOwner = viewLifecycleOwner
        }
        val view = viewBinding.root
        init(view)
        return view
    }

    /**
     *
     * 数据监听回调
     * @param v 绑定的viewModel
     */
    abstract fun observe(v:V)

    /**
     * 重写初始化UI或者data,非必要
     */
   open fun init(view:View)=Unit

}