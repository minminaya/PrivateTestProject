package cn.minminaya.privatetestproject.img.load;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Niwa on 2017/11/19.
 */

public class CompressUtil {

    /**
     *  这里的请求宽高意思不是目标分辨率，而是用这个分辨率的与原图分辨率的比值来计算缩小的最小比例，目标bitmap应该是原图分辨率的x倍（int）
     * */
    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int requestWidth, int requestHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果inJustDecoedBounds设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间(就是不返回bitmap)
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        //调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        //使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 可以根据传入的宽和高，计算出合适的inSampleSize值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        //源图片的高度和宽度
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;//初始化值为1
        if (height > requestHeight || width > requestWidth) {
            //计算出实际宽高和目标宽高的比率
            int heightRatio = Math.round((float) height / (float) requestHeight);
            int widthRatio = Math.round((float) width / (float) requestWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
