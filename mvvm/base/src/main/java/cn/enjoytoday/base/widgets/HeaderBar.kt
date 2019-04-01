package cn.enjoytoday.base.widgets

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import cn.enjoytoday.base.R
import cn.enjoytoday.base.onClick
import kotlinx.android.synthetic.main.layout_header_bar.view.*

/**
 * 标题头布局
 */
class HeaderBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    //是否显示"返回"图标
    private var isShowBack = true
    // 是否显示底部分割线
    private var isShowLine = true
    // 返回按钮的颜色
    private var leftIconColor: Int = -1
    private var titleColor: Int

    // 是否显示左边文字
    private var isShowBackText = false
    //Title文字
    private var titleText: String? = null
    //右侧文字
    private var rightText: String? = null
    //左侧文字
    private var leftText: String? = null
    private var rightIcon: Int? = 0
    // 背景色
    private var backgroundColor: Int? = 0


    /**
     * 左边返回按钮点击是否有响应，默认finish
     */
    private var leftCanClick = true


    init {
        //获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true)
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)
        rightIcon = typedArray.getResourceId(R.styleable.HeaderBar_rightIcon, 0)
        leftText = typedArray.getString(R.styleable.HeaderBar_leftText)
        backgroundColor = typedArray.getColor(R.styleable.HeaderBar_backgroundColor, Color.WHITE)
        leftIconColor = typedArray.getColor(R.styleable.HeaderBar_leftIconColor, -0)
        isShowLine = typedArray.getBoolean(R.styleable.HeaderBar_isShowLine, true)
        titleColor = typedArray.getColor(R.styleable.HeaderBar_titleColor, Color.BLACK)
        leftCanClick = typedArray.getBoolean(R.styleable.HeaderBar_leftCanClick, true)
        typedArray.recycle()
        initView()
    }

    /*
        初始化视图
     */
    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)

        mLeftIv.let {
            it.visibility = if (isShowBack) View.VISIBLE else View.GONE
            if (leftIconColor != 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    it.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }
        }


        //标题不为空，设置值
        titleText?.let {
            mTitleTv.text = it
        }

        mTitleTv.setTextColor(titleColor)

        //右侧文字不为空，设置值
        rightText?.let {
            mRightTv.text = it
            mRightTv.visibility = View.VISIBLE
        }


        leftText?.let {
            mLeftIv.visibility = View.GONE
            mLeftTv.visibility = View.VISIBLE
            mLeftTv.text = it

        }

        //返回图标默认实现（关闭Activity）
        if (leftCanClick) {
            mLeftTv.onClick {
                if (context is Activity) {
                    (context as Activity).finish()
                }
            }
        }

        if (this.rightIcon!! > 0) {
            mRightIv.setImageResource(rightIcon!!)
            mRightIv.visibility = View.VISIBLE
        }

        backgroundColor?.let {
            mBackground.setBackgroundColor(it)
        }

        mLine.let {
            it.visibility = if (isShowLine) View.VISIBLE else View.GONE
        }


    }

    /*
        获取左侧视图
     */
    fun getLeftView(): ImageView {
        return mLeftIv
    }

    fun setTitle(title: String) {
        mTitleTv.text = title
    }

    fun setTitleShow(isShow: Boolean) {
        mTitleTv.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

    fun getTitleView(): TextView {
        return mTitleTv
    }

    fun setHeadBackgroundColor(color: Int) {
        mLine.visibility = View.GONE
        mBackground.setBackgroundColor(color)
    }

    fun getContentView(): TextView {
        return mTitleTv
    }

    /**
     *  获取右侧视图
     */
    fun getRightView(): TextView {
        return mRightTv
    }

    /**
     *获取右侧文字
     */
    fun getRightText(): String {
        return mRightTv.text.toString()
    }

    /**
     * 获取右边的图片
     */
    fun getRightIcon(): ImageView {
        return mRightIv
    }
}
