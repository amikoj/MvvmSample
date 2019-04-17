package com.worfu.peagang.base.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.permissions.RxPermissions
import com.luck.picture.lib.tools.PictureFileUtils
import com.worfu.peagang.base.R
import com.worfu.peagang.base.utils.LogUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * create by http://me.xuxiao.wang on 2019/4/9 0009 15:50
 * 带获取照片的fragment
 */
abstract class BaseTakePhotoFragment : BaseFragment() {

    abstract fun takeSuccess(images: List<String>)

    // 只取一张并裁剪
    fun takeOneAndCrop() {
        takeOneAndCrop(1, 1)
    }

    // 自定义裁剪比例
    fun takeOneAndCrop(aspect_ratio_x: Int = 1, aspect_ratio_y: Int = 1) {
        take(1, null, PictureConfig.SINGLE, true, aspect_ratio_x, aspect_ratio_y)
    }

    // 取一张不裁剪
    fun takeOne() {
        take(1, null, PictureConfig.SINGLE, false)
    }

    // 取多张不裁剪
    fun takeList(maxSelectNum: Int, selectList: List<LocalMedia>?) {
        take(maxSelectNum, selectList, PictureConfig.MULTIPLE, false)
    }

    fun take(
        maxSelectNum: Int = 1,
        selectList: List<LocalMedia>?,
        selectionMode: Int = PictureConfig.MULTIPLE,
        enableCrop: Boolean = false,
        aspect_ratio_x: Int = 1,
        aspect_ratio_y: Int = 1
    ) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .theme(R.style.picture_white_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
            .maxSelectNum(maxSelectNum)// 最大图片选择数量
            .minSelectNum(1)// 最小选择数量
            .imageSpanCount(4)// 每行显示个数
            .selectionMode(selectionMode)// 多选 or 单选 PictureConfig.SINGLE
            .previewImage(true)// 是否可预览图片
            .isCamera(true)// 是否显示拍照按钮
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .enableCrop(enableCrop)// 是否裁剪
            .compress(true)// 是否压缩
            .synOrAsy(false)//同步true或异步false 压缩 默认同步
            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
            .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
            .selectionMedia(selectList)// 是否传入已选图片
            .minimumCompressSize(100)// 小于100kb的图片不压缩
            .cropWH(aspect_ratio_x, aspect_ratio_y)// 裁剪宽高比，设置如果大于图片本身宽高则无效

            //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
            //.compressSavePath(getPath())//压缩图片保存地址
            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//            .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//            .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//            .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//            .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//            .openClickSound(cb_voice.isChecked())// 是否开启点击声音
            //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
            //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
            //.cropCompressQuality(90)// 裁剪压缩质量 默认100
            //            .previewVideo(false)// 是否可预览视频
//            .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
            //.rotateEnabled(true) // 裁剪是否可旋转图片
            //.scaleEnabled(true)// 裁剪是否可放大缩小图片
            //.videoQuality()// 视频录制质量 0 or 1
            //.videoSecond()//显示多少秒以内的视频or音频也可适用
            //.recordVideoSecond()//录制视频秒数 默认60s
            .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    val obtainMultipleResult = PictureSelector.obtainMultipleResult(data)

                    val list = mutableListOf<String>()

                    for (media in obtainMultipleResult) {
                        var url = ""
                        when {
                            media.isCompressed -> {
                                url = media.compressPath
                                LogUtils.i("压缩路径：$url")
                            }
                            media.isCut -> {
                                url = media.cutPath
                                LogUtils.i("裁剪路径：$url")
                            }
                            else -> {
                                url = media.path
                                LogUtils.i("原图路径：$url")
                            }
                        }

                        list.add(url)
                    }

                    takeSuccess(list)

                }
            }
        }
    }

    // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
    fun cleanCache() {
        val permissions = RxPermissions(activity!!)
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(object : Observer<Boolean> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(aBoolean: Boolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(context)
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        })

    }

}