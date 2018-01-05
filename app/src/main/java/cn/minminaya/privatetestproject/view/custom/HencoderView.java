package cn.minminaya.privatetestproject.view.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import cn.minminaya.privatetestproject.R;

/**
 * Created by Niwa on 2017/12/25.
 */

public class HencoderView extends View {


    private Bitmap bitmap;
    private Paint mPaint;
    private int bitmapWidth;
    private int bitmapHeight;
    private int centerX;
    private int centerY;
    private int x;
    private int y;

    private int screenWidth;
    private int screenHeight;

    private ObjectAnimator objectAnimator1;
    private ObjectAnimator objectAnimator2;
    private ObjectAnimator objectAnimator3;

    private Camera camera;

    private int rotateY;
    private int rotateZ;

    public void setRotateZ(int rotateZ) {
        this.rotateZ = rotateZ;
        invalidate();
    }

    public void setRotateY(int rotateY) {
        this.rotateY = rotateY;
        invalidate();
    }

    public HencoderView(Context context) {
        this(context, null);
    }

    public HencoderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HencoderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);

        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        Log.d("HencoderView", "当前的bitmapWidth为：" + bitmapWidth);
        Log.d("HencoderView", "当前的bitmapHeight为：" + bitmapHeight);

        centerX = bitmapWidth / 2;
        centerY = bitmapHeight / 2;
        Log.d("HencoderView", "当前的centerX为：" + centerX);
        Log.d("HencoderView", "当前的centerY为：" + centerY);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();


        //camera镜头后移
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float camaraZ = -displayMetrics.density * 8;
        camera.setLocation(0, 0, camaraZ);

        objectAnimator2 = ObjectAnimator.ofInt(this, "rotateZ", 0, 270);
        objectAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator2.setDuration(2000);

        objectAnimator1 = ObjectAnimator.ofInt(this, "rotateY", 0, 45);
        objectAnimator1.setInterpolator(new LinearInterpolator());
        objectAnimator1.setDuration(600);
        objectAnimator1.setStartDelay(500);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator1,objectAnimator2);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateY = 0;
                rotateZ = 0;
                animatorSet.setStartDelay(1000);
                animatorSet.start();

            }

        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //先画动的部分
        canvas.save();
        camera.save();
        canvas.translate(screenWidth / 2, screenHeight / 2);

        canvas.rotate(-rotateZ);//关键在这里，1 先旋转

        camera.rotateY(-rotateY);//2. 旋转Y轴camera
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -centerY*2, centerX*2, centerY*2);//这里裁剪的区域主要是bitmap的一半就行

        canvas.rotate(rotateZ);//3.最后旋转回来

        canvas.translate(-screenWidth / 2, -screenHeight / 2);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, mPaint);
        canvas.restore();

        //画不动的部分
        canvas.save();

        canvas.translate(screenWidth / 2, screenHeight / 2);
        canvas.rotate(-rotateZ);
        canvas.clipRect(-centerX*2, -centerY*2, 0, centerY*2);
        canvas.rotate(rotateZ);
        canvas.translate(-screenWidth / 2, -screenHeight / 2);

        canvas.drawBitmap(bitmap, x, y, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
        Log.d("HencoderView", "当前的screenWidth为：" + screenWidth);

        x = screenWidth / 2 - centerX;
        y = screenHeight / 2 - centerY;

        Log.d("HencoderView", "当前的x为：" + x);
        Log.d("HencoderView", "当前的y为：" + y);

    }
}
