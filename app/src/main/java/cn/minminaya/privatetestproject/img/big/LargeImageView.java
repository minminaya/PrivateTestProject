package cn.minminaya.privatetestproject.img.big;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义的ImageView 巨图
 * Created by Niwa on 2017/11/20.
 */

public class LargeImageView extends View {

    private BitmapRegionDecoder mDecoder;

    private int mImgWidth;
    private int mImgHeight;

    private volatile Rect mRect = new Rect();

    private MoveGestureDetector mDetector;
    private static final BitmapFactory.Options options = new BitmapFactory.Options();

    static {
        //切换图片编码
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public void setInputStream(InputStream is) {
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);

            BitmapFactory.Options tmOptions = new BitmapFactory.Options();
            //如果inJustDecoedBounds设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间(就是不返回bitmap)
            tmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, tmOptions);
            mImgWidth = tmOptions.outWidth;
            mImgHeight = tmOptions.outHeight;

            //另外一种获取图片真实尺寸的方法
//            mImgWidth = mDecoder.getWidth();
//            mImgHeight = mDecoder.getHeight();


            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LargeImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mDetector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleMoveGestureDetector() {

            @Override
            public boolean onMove(MoveGestureDetector detector) {

                int moveX = (int) detector.getExternalPointX();
                int moveY = (int) detector.getExternalPointY();

                if (mImgWidth > getWidth()) {
                    mRect.offset(-moveX, 0);
                    checkWidth();
                    invalidate();
                }
                if (mImgHeight > getHeight()) {
                    mRect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }
                return true;
            }
        });

    }

    private void checkWidth() {
        Rect rect = mRect;
        int imageWidth = mImgWidth;
        int imageHeight = mImgHeight;
        if (rect.right > imageWidth) {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }
        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    private void checkHeight() {
        Rect rect = mRect;
        int imageWidth = mImgWidth;
        int imageHeight = mImgHeight;
        if (rect.bottom > imageHeight) {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }
        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionEvent = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionEvent) {
            case MotionEvent.ACTION_MOVE:
                Log.e("LargeImageView", "坐标：" + event.getX());
                break;
        }
        mDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mImgWidth;
        int imageHeight = mImgHeight;


        Log.e("LargeImageView","width:"+width);
        Log.e("LargeImageView","height:"+height);
        Log.e("LargeImageView","imageWidth:"+imageWidth);
        Log.e("LargeImageView","imageHeight:"+imageHeight);

        //这里大小不能超过4096*4096，不然显示不出来
        mRect.set(0,0,2000,imageHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mDecoder.decodeRegion(mRect, options);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}
