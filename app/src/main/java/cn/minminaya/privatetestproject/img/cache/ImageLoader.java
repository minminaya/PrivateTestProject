package cn.minminaya.privatetestproject.img.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import cn.minminaya.privatetestproject.R;

/**
 * 图片处理类
 * Created by Niwa on 2017/11/16.
 */

public class ImageLoader {

    private LruCache<String, Bitmap> mBitmapLruCache;
    private RecyclerView recyclerView;//传过来的RecyclerView对象
    private Set<ImageLoaderTask> mTask = null;//存异步任务的set集合

    public ImageLoader(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mTask = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mBitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
            //重写
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存即调用
                return value.getByteCount();
            }
        };
    }

    /**
     * 根据图片url下载图片
     *
     * @param imgUrl 图片路径
     * @return bitmap
     */
    public Bitmap getBitmapByImgUrl(String imgUrl) {
        Bitmap bitmap = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL mUrl = new URL(imgUrl);
            try {
                httpURLConnection = (HttpURLConnection) mUrl.openConnection();
                httpURLConnection.setConnectTimeout(10 * 1000);
                httpURLConnection.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                //断开连接
                httpURLConnection.disconnect();
            }
        }
        return bitmap;
    }

    /**
     * 将bitmap加入cache
     */
    public void addBitmapToCache(String urlKey, Bitmap bitmap) {
        mBitmapLruCache.put(urlKey, bitmap);
    }

    /**
     * 从cache获取bitmap
     */
    public Bitmap getBitmapfromCache(String urlKey) {
        return mBitmapLruCache.get(urlKey);
    }

    /**
     * 取消所有下载异步任务
     */
    public void cancelAllTask() {
        if (mTask != null) {
            for (ImageLoaderTask task :
                    mTask) {
                task.cancel(false);
            }
        }
    }

    /**
     * 按当前item的序号区间显示图片
     */
    public void showImages(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            String imageUrl = ImagCacheRAdapter.URLS[i];
            Bitmap bitmap = getBitmapfromCache(imageUrl);//从缓存中获取

            if (bitmap == null) {
                //如果缓存为空，则开启异步线程
                ImageLoaderTask imageLoaderTask = new ImageLoaderTask(imageUrl);
                imageLoaderTask.execute();
                //加入HashSet中
                mTask.add(imageLoaderTask);
            } else {
                ImageView imageView = recyclerView.findViewWithTag(imageUrl);
                imageView.setImageBitmap(bitmap);
            }
        }
    }


    /**
     * 显示图片
     *
     * @param imageView
     * @param imageUrl
     */
    public void showImage(ImageView imageView, String imageUrl) {
        //从缓存中取图片
        Bitmap bitmap = getBitmapfromCache(imageUrl);
        //如果缓存中没有，则去下载
        if (bitmap == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }
    private class ImageLoaderTask extends AsyncTask<Void, Void, Bitmap> {
        private String mImagerUrl;

        public ImageLoaderTask(String mImagerUrl) {
            this.mImagerUrl = mImagerUrl;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            //获取图片并且加入缓存
            Bitmap bitmap = getBitmapByImgUrl(mImagerUrl);
            if (bitmap != null) {
                addBitmapToCache(mImagerUrl, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = recyclerView.findViewWithTag(mImagerUrl);
            if (null != imageView && null != bitmap) {
                imageView.setImageBitmap(bitmap);
            }
            //显示成功后就把当前的AsyncTask从mTask中移除
            mTask.remove(this);
        }
    }
}
