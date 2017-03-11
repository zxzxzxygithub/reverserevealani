package com.example.smoothimagedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.roamer.ui.view.SquareCenterImageView;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by zhengyongxiang on 2017/3/10.
 */

public class BigScaleActivity extends Activity {

    public static DisplayImageOptions mNormalImageOptions;
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator + "demo" + File.separator + "images" + File.separator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigscale);

        initImageLoader(this);

        final SquareCenterImageView imageView = (SquareCenterImageView) findViewById(R.id.squareiv);
        final String bmpUrl = BitmapSampleUtil.getBmpUrl();
        Uri uri = Uri.parse("res://" + getPackageName()+
                "/" + R.drawable.ic_launcher);
        imageView.setImageURI(uri);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BigScaleActivity.this, SpaceImageDetailActivity.class);
                ArrayList<String> datas = new ArrayList<String>();
                datas.add(bmpUrl);
                intent.putExtra("images", datas);
                intent.putExtra("position", 0);
                int[] location = new int[2];
                imageView.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);

                intent.putExtra("width", imageView.getWidth());
                intent.putExtra("height", imageView.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


    }


    private void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
        MemoryCacheAware<String, Bitmap> memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }

        mNormalImageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisc(true)
                .resetViewBeforeLoading(true).build();

        // This
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(mNormalImageOptions)
                .denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
                // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(memoryCache)
                // .memoryCacheSize(memoryCacheSize)
                .tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3).build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
