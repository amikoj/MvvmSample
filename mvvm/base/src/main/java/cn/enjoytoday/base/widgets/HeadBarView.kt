package cn.enjoytoday.base.widgets

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import cn.enjoytoday.base.R
import cn.enjoytoday.base.onClick
import cn.enjoytoday.base.visibility
import kotlinx.android.synthetic.main.head_bar_view.view.*
import java.lang.reflect.InvocationTargetException


class HeadBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val defaultColor = Color.parseColor("#333333")

    private var bgColor: Int = Color.TRANSPARENT
    private var leftIcon: Int = R.drawable.icon_back
    private var isHideLeftIcon: Boolean = false
    private var leftText: String? = ""
    private var leftTextColor: Int = defaultColor
    private var title: String? = ""
    private var titleColor: Int = defaultColor
    private var rightText: String? = ""
    private var rightTextColor: Int = defaultColor
    private var rightIcon: Int = 0
    private var isShowBottomLine = true
    private var bottomLineColor: Int = Color.parseColor("#DFDFDF")
    private var bottomLineHeight: Int = 0
    private var onLeftClickString: String? = null
    private var onRightClickString: String? = null

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadBarView)
        bgColor = typedArray.getColor(R.styleable.HeadBarView_bgColor, bgColor)
        leftIcon = typedArray.getResourceId(R.styleable.HeadBarView_leftIcon, leftIcon)
        isHideLeftIcon = typedArray.getBoolean(R.styleable.HeadBarView_hideLeftIcon, isHideLeftIcon)
        leftText = typedArray.getString(R.styleable.HeadBarView_leftText)
        leftTextColor = typedArray.getColor(R.styleable.HeadBarView_leftTextColor, leftTextColor)
        title = typedArray.getString(R.styleable.HeadBarView_title)
        titleColor = typedArray.getColor(R.styleable.HeadBarView_titleColor, titleColor)
        rightText = typedArray.getString(R.styleable.HeadBarView_rightText)
        rightTextColor = typedArray.getColor(R.styleable.HeadBarView_rightTextColor, rightTextColor)
        rightIcon = typedArray.getResourceId(R.styleable.HeadBarView_rightIcon, rightIcon)
        isShowBottomLine = typedArray.getBoolean(R.styleable.HeadBarView_isShowBottomLine, isShowBottomLine)
        bottomLineColor = typedArray.getColor(R.styleable.HeadBarView_bottomLineColor, bottomLineColor)
        onLeftClickString = typedArray.getString(R.styleable.HeadBarView_onLeftClick)
        onRightClickString = typedArray.getString(R.styleable.HeadBarView_onRightClick)
        bottomLineHeight =
            typedArray.getDimensionPixelOffset(R.styleable.HeadBarView_bottomLineHeight, bottomLineHeight)

        typedArray.recycle()

        View.inflate(context, R.layout.head_bar_view, this)

        initView()
    }

    private fun initView() {

        setBgColor(bgColor)
        setLeftIcon(leftIcon)
        isHideLeftIcon(isHideLeftIcon)
        setLeftText(leftText)
        setLeftTextColor(leftTextColor)

        setRightIcon(rightIcon)
        setRightText(rightText)
        setRightTextColor(rightTextColor)

        setTitle(title)
        setTitleColor(titleColor)

        isShowBottomLine(isShowBottomLine)
        setBottomLineColor(bottomLineColor)

        setBottomLineHeight(bottomLineHeight)


        // 左边按钮点击事件
        setLeftViewOnClick {
            if (onLeftClickString.isNullOrEmpty()) {
                if (context is Activity) {
                    (context as Activity).finish()
                }
            } else {
                getMethod(onLeftClickString!!)
            }
        }

        // 右边按钮点击事件
        if (onRightClickString.isNullOrEmpty()) {
            setRightViewOnClick {
                getMethod(onRightClickString!!)
            }
        }
    }

    private fun getMethod(name: String) {
        try {
            if (!context.isRestricted) {
                val method = context.javaClass.getMethod(name, View::class.java)
                try {
                    method.invoke(context, this)
                } catch (e: IllegalAccessException) {
                    throw IllegalStateException(
                        "Could not execute non-public method for android:onClick", e
                    )
                } catch (e: InvocationTargetException) {
                    throw IllegalStateException(
                        "Could not execute method for android:onClick", e
                    )
                }
            }
        } catch (e: NoSuchMethodException) {
            // Failed to find method, keep searching up the hierarchy.
        }
    }


    // 设置背景色
    fun setBgColor(color: Int) {
        bgColor = color
        mHeadBarViewBackground.setBackgroundColor(bgColor)
    }

    // 设置左边图标
    fun setLeftIcon(resource: Int) {
        leftIcon = resource
        mHeadBarViewLeftIcon.visibility(leftIcon != 0)
        mHeadBarViewLeftIcon.setImageResource(leftIcon)
        // 当左边既没有文字描述，又没有图标时，不显示左边按钮
        mHeadBarViewLeftClickView.visibility(TextUtils.isEmpty(leftText).not() || leftIcon != 0)
    }

    fun isHideLeftIcon(isHide: Boolean) {
        isHideLeftIcon = isHide
        mHeadBarViewLeftIcon.visibility(isHideLeftIcon.not())
    }

    // 设置左边文字
    fun setLeftText(text: String?) {
        leftText = text
        mHeadBarViewLeftText.visibility(TextUtils.isEmpty(leftText).not())
        mHeadBarViewLeftText.text = text ?: ""
        // 当左边既没有文字描述，又没有图标时，不显示左边按钮
        mHeadBarViewLeftClickView.visibility(TextUtils.isEmpty(leftText).not() || leftIcon != 0)
    }

    // 设置左边文字颜色
    fun setLeftTextColor(color: Int) {
        leftTextColor = color
        mHeadBarViewLeftText.setTextColor(color)
    }

    // 左边按钮点击事件
    fun setLeftViewOnClick(method: () -> Unit) {
        mHeadBarViewLeftClickView.isClickable = true
        mHeadBarViewLeftClickView.onClick(method)
    }

    // 左边按钮点击事件
    fun setOnLeftClick(listener: OnClickListener) {
        mHeadBarViewLeftClickView.setOnClickListener(listener)
    }

    // 设置右边图标
    fun setRightIcon(resource: Int) {
        rightIcon = resource
        mHeadBarViewRightIcon.visibility(rightIcon != 0)
        mHeadBarViewRightIcon.setImageResource(rightIcon)
        // 当右边既没有文字描述，又没有图标时，不显示右边按钮
        mHeadBarViewRightClickView.visibility(TextUtils.isEmpty(rightText).not() || rightIcon != 0)
    }

    // 设置右边文字
    fun setRightText(text: String?) {
        rightText = text
        mHeadBarViewRightText.visibility(TextUtils.isEmpty(rightText).not())
        mHeadBarViewRightText.text = text ?: ""
        // 当左边既没有文字描述，又没有图标时，不显示左边按钮
        mHeadBarViewRightClickView.visibility(TextUtils.isEmpty(rightText).not() || rightIcon != 0)
    }

    // 设置右边文字颜色
    fun setRightTextColor(color: Int) {
        rightTextColor = color
        mHeadBarViewRightText.setTextColor(color)
    }

    // 右边按钮点击事件
    fun setRightViewOnClick(method: () -> Unit) {
        mHeadBarViewRightClickView.isClickable = true
        mHeadBarViewRightClickView.onClick(method)
    }

    // 右边按钮点击事件
    fun setOnRightClick(listener: OnClickListener) {
        mHeadBarViewRightClickView.setOnClickListener(listener)
    }

    // 设置标题文字
    fun setTitle(text: String?) {
        title = text
        mHeadBarViewTitle.text = text ?: ""
    }

    // 设置标题文字颜色
    fun setTitleColor(color: Int) {
        titleColor = color
        mHeadBarViewTitle.setTextColor(titleColor)
    }

    // 底部分割线是否显示
    fun isShowBottomLine(isShow: Boolean) {
        isShowBottomLine = isShow
        mHeadBarViewBottomLine.visibility(isShowBottomLine)
    }

    // 底部分割线颜色
    fun setBottomLineColor(color: Int) {
        bottomLineColor = color
        mHeadBarViewBottomLine.setBackgroundColor(bottomLineColor)
    }

    // 底部分割线高度
    fun setBottomLineHeight(height: Int) {
        if (height > 0) {
            bottomLineHeight = height
            val layoutParams = mHeadBarViewBottomLine.layoutParams
            layoutParams.height = height
            mHeadBarViewBottomLine.layoutParams = layoutParams
        }
    }
}
