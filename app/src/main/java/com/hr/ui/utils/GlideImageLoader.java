package com.hr.ui.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hr.ui.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by wdr on 2017/12/26.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(Uri.fromFile(new File(path)))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .override(width, height)
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
