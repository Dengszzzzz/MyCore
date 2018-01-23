package com.dengzh.sample.utils.glideUtils;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by dengzh on 2017/11/13.
 * 加载图片工具类
 * Glide
 * 1.
 * load(String string)	string可以为一个文件路径、uri或者url
 * load(Uri uri)	uri类型
 * load(File file)	文件
 * load(Integer resourceId)	资源Id,R.drawable.xxx或者R.mipmap.xxx
 * load(byte[] model)
 */

public class GlideUtils {

    //测试用图
    public static String url = "http://www.2cto.com/uploadfile/Collfiles/20140615/20140615094106112.jpg";

    public static int ROUND_IMAGE = 1000000;
    public static int CIRCLE_IMAGE = 1000001;

    /**
     * 是否是ui线程,Glide加载图片只能在UI线程
     * @return
     */
    public static boolean isUiThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 加载网络图片
     * @param context  如果是fragment/activity,则与生命周期绑定,如果是Applicaition 则不绑定
     * @param v
     * @param url  网络url
     */
    public static void loadImg(Context context, ImageView v,String url){
        try {
            if (!isUiThread()) return;
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(v);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 加载本地图片
     * @param resourceId  本地资源id
     */
    public static void loadImg(Context context, ImageView v,Integer resourceId){
        try {
            if (!isUiThread()) return;
            Glide.with(context)
                    .load(resourceId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(v);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载圆角图片 网络
     */
    public static void loadRoundImg(Context context, ImageView v, String url,int round) {
        try{
            if(!isUiThread()) return;
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideRoundTransform(v.getContext(),round))
                    .into(v);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载圆角图片 本地
     */
    public static void loadRoundImg(Context context, ImageView v, Integer resourceId,int round) {
        try{
            if(!isUiThread()) return;
            Glide.with(context)
                    .load(resourceId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideRoundTransform(v.getContext(),round))
                    .into(v);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载圆形图片  网络
     * @param context
     * @param v
     * @param url
     */
    public static void loadCircleImg(Context context, ImageView v, String url) {
        try{
            if(!isUiThread()) return;
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(v.getContext()))
                    .into(v);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载圆形图片  本地
     * @param context
     * @param v
     * @param resourceId
     */
    public static void loadCircleImg(Context context, ImageView v, Integer resourceId) {
        try{
            if(!isUiThread()) return;
            Glide.with(context)
                    .load(resourceId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(v.getContext()))
                    .into(v);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
