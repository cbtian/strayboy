package com.staryboy.staryboy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.xianghui.strayboy.BitmapCallBack;
import com.xianghui.strayboy.R;
import com.xianghui.strayboy.views.GlideRoundTransform;

/**
 * Created by tiancb on 2018/4/10.
 */

public class GlideUtils {
    public static final int default_icon = R.mipmap.ic_launcher;


    public void loadMapImage(Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate()
                .skipMemoryCache(false).listener(new ImageLoadListener(imageView)).into(imageView);
    }

    /**
     * 带监听的loadview
     *
     * @param context
     * @param imgUrl
     */
    public void loadImageAsBitmap(final Context context, String imgUrl, final BitmapCallBack bitmapCallBack) {
        try {
            Glide.with(context).load(imgUrl).asBitmap().dontAnimate().placeholder(default_icon).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>(100, 100) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (bitmapCallBack != null) {
                        bitmapCallBack.success(resource);
                    }
                }
            });
        } catch (Exception re) {
            re.printStackTrace();
        }

//        Glide.with(context).load(imgUrl).asBitmap().into(new Target<Bitmap>() {
//            @Override
//            public void onLoadStarted(Drawable placeholder) {
//                Log.i("Glide","onLoadStarted");
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                Log.i("Glide","onLoadFailed");
//            }
//
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                //TODO set bitmap
//                if (bitmapCallBack != null) {
//                    bitmapCallBack.success(resource);
//                }
//            }
//
//            @Override
//            public void onLoadCleared(Drawable placeholder) {
//                Log.i("Glide","onLoadCleared");
//            }
//
//            @Override
//            public void getSize(SizeReadyCallback cb) {
//                Log.i("Glide","getSize");
//            }
//
//            @Override
//            public void setRequest(com.bumptech.glide.request.Request request) {
//                Log.i("Glide","setRequest");
//            }
//
//
//
//            @Override
//            public com.bumptech.glide.request.Request getRequest() {
//                Log.i("Glide","getRequest");
//                return null;
//            }
//
//            @Override
//            public void onStart() {
//                Log.i("Glide","onStart");
//            }
//
//            @Override
//            public void onStop() {
//                Log.i("Glide","onStop");
//            }
//
//            @Override
//            public void onDestroy() {
//                Log.i("Glide","onDestroy");
//            }
//        });
    }

    public void loadImage(Context context, String url, final ImageView imageView) {
        try {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().placeholder(default_icon).error(default_icon)
                    .skipMemoryCache(false).listener(new ImageLoadListener(imageView)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadImage(Context context, String url, final ImageView imageView, int placeholder, int error) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                .skipMemoryCache(false).placeholder(placeholder).error(error).into(imageView);
    }

    public void downLoadImage(Context context, String url, ImageView intoView) {
        Glide.with(context)
                .load(url)
                .crossFade()//支持图片的淡入淡出动画效果（调用crossFade()方法）和查看动画的属性的功能
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(intoView);
    }

    public void loadRoundImage(Context context, String url, final ImageView imageView, int placeholder, int error) {
        Glide.with(context).load(url).transform(new GlideRoundTransform(context, 5)).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate()
                .skipMemoryCache(false).placeholder(placeholder).error(error).into(imageView);
    }

    public void loadImage(Fragment fragment, String url, ImageView imageView) {
        if (fragment.getActivity() == null) {
            return;
        }
        Glide.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().priority(Priority.HIGH)
                .skipMemoryCache(false).listener(new ImageLoadListener(imageView)).into(imageView);
    }

    public void loadRoundImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).centerCrop().listener(new ImageLoadListener(imageView)).into(imageView);
    }

    public class ImageLoadListener implements RequestListener<String, GlideDrawable> {

        private ImageView iv;

        public ImageLoadListener(ImageView iv) {
            this.iv = iv;
        }

        @Override
        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
            iv.setImageResource(default_icon);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            iv.setImageDrawable(glideDrawable);
            return false;
        }
    }

}
