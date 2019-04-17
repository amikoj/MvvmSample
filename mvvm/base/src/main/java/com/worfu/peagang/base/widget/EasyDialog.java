package com.worfu.peagang.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.worfu.peagang.base.R;

/**
 * @author 王许晓
 * web: http://me.xuxiao.wang
 * @date 2019/3/18 0018 上午 11:51
 * desc:
 */
public class EasyDialog {

    private Dialog dialog;

    private Context context;
    private String title = "提示"; // 标题
    private int titleGravity = Gravity.CENTER; // 标题显示位置
    private String content; // 内容
    private String leftText = "取消"; // 左边按钮文本
    private String rightText = "确定"; // 右边按钮文本
    private View contentView; // 自定义view


    private OnClickListener leftTextClick;
    private OnClickListener rightTextClick;


    private EasyDialog(Context context) {
        this.context = context;
    }

    private void show() {

        dialog = new Dialog(context, R.style.EasyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.easy_dialog_layout, null);
        dialog.setContentView(view);
        initView(dialog, view);
        dialog.show();
    }

    private void initView(final Dialog dialog, View view) {
        TextView mTvTitle = view.findViewById(R.id.mTvTitle);
        TextView mTvContent = view.findViewById(R.id.mTvContent);
        FrameLayout mFlView = view.findViewById(R.id.mFrameLayout);
        TextView mTvLeftText = view.findViewById(R.id.mTvLeftButton);
        TextView mTvRightText = view.findViewById(R.id.mTvRightButton);
        View mVCenterLine = view.findViewById(R.id.mVCenterLine);


        // 标题
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
            mTvTitle.setGravity(titleGravity);
        } else {
            mTvTitle.setVisibility(View.GONE);
        }

        // 提示
        if (!TextUtils.isEmpty(content)) {
            mTvContent.setText(content);
            mTvContent.setVisibility(View.VISIBLE);
        } else {
            mTvContent.setVisibility(View.GONE);
        }


        // 左边按钮
        if (TextUtils.isEmpty(leftText)) {
            mTvLeftText.setVisibility(View.GONE);
        } else {
            mTvLeftText.setVisibility(View.VISIBLE);

            if (leftTextClick == null) {
                mTvLeftText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            } else {
                mTvLeftText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftTextClick.onClick(dialog, v);
                    }
                });
            }
        }

        // 右边按钮
        if (TextUtils.isEmpty(rightText)) {
            mTvRightText.setVisibility(View.GONE);
        } else {
            mTvRightText.setText(rightText);

            if (rightTextClick == null) {

                mTvRightText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            } else {
                mTvRightText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightTextClick.onClick(dialog, v);
                    }
                });
            }


        }

        if (!TextUtils.isEmpty(leftText) && !TextUtils.isEmpty(rightText)) {
            mVCenterLine.setVisibility(View.VISIBLE);
        } else {
            mVCenterLine.setVisibility(View.GONE);
        }

        // 自定义view
        if (contentView != null) {
            mFlView.addView(contentView);
        }


    }


    public static class Builder {
        private EasyDialog d;

        public Builder(Context context) {
            d = new EasyDialog(context);
        }

        public Builder setTitle(String title) {
            d.title = title;
            return this;
        }

        public Builder setTitleGravity(int gravity) {
            d.titleGravity = gravity;
            return this;
        }

        public Builder setMessage(String message) {
            d.content = message;
            return this;
        }

        public Builder setLeftText(String leftText) {
            d.leftText = leftText;
            return this;
        }

        public Builder setLeftText(String leftText, OnClickListener listener) {
            d.leftText = leftText;
            d.leftTextClick = listener;
            return this;
        }

        public Builder setRightText(String rightText) {
            d.rightText = rightText;
            return this;
        }

        public Builder setRightText(String text, OnClickListener listener) {
            d.rightText = text;
            d.rightTextClick = listener;
            return this;
        }

        public Builder setContentView(View view) {
            d.contentView = view;
            return this;
        }

        public void show() {
            d.show();
        }
    }

    public interface OnClickListener {
        void onClick(Dialog dialog, View view);
    }

}
