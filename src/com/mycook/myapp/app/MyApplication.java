package com.mycook.myapp.app;

import android.app.Application;
import com.mycook.myapp.R;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.pkgg.k.MyKAM;

/**
 * Created by happy_000 on 2014/11/17.
 */
public class MyApplication extends Application {
    private DisplayImageOptions options;
    public static String COOL_ID="a12f8f612d5a4b109408528a434ee427";
    /*多盟*/
    public  static String PUBLISHER_ID="56OJxiOIuN4Dz4YjKI";
    public  static String SplashPPID="16TLeEloApzgSNUOcPvFBEhk";
    public  static String InlinePPID="16TLeEloApzgSNUOcgOdbGns";

    /*多盟---end*/

    @Override
    public void onCreate() {
        super.onCreate();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.home)            //加载图片时的图片
                .showImageForEmptyUri(R.drawable.home)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.home)              //加载失败时的图片
                .delayBeforeLoading(R.drawable.home)
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .displayer(new RoundedBitmapDisplayer(10))         //设置显示风格这里是圆角矩形
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(5 * 1024 * 1024)
                        // .discCacheFileNameGenerator(newMd5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                        // .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);

      /*  加载酷仔*/
        MyKAM.getInstance().setId(this, COOL_ID);
//        //调用酷仔接口
        MyKAM.getInstance().showKuguoSprite(this, MyKAM.STYLE_KUZAI);

    }
}
