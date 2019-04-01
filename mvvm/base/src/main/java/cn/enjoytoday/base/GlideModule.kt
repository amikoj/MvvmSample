package cn.enjoytoday.base

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory


@GlideModule
class GlideModule:AppGlideModule(){
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //自定义缓存目录，磁盘缓存给150M 另外一种设置缓存方式
        builder.setDiskCache(InternalCacheDiskCacheFactory(context,
                "GlideImgCache", (150 * 1024 * 1024).toLong()))
        //配置图片缓存格式 默认格式为8888
        builder.setDefaultRequestOptions(RequestOptions
                .formatOf(DecodeFormat.PREFER_ARGB_8888))
//        ViewTarget.setTagId(R.id.glide_tag_id)
    }


    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }
}