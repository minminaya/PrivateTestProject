package cn.minminaya.privatetestproject.view.custom;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Niwa on 2018/1/4.
 */
public class HuaweiWeather extends View {

    private Paint mOtherPaint;
    private Paint mColorSegmentPaint;
    private Path circleBarPath = new Path();
    /**
     * View中心点
     */
    private int centerX;
    private int centerY;

    /**
     * 外圆坐标点
     */

    private PointF mEndPointF;

    /**
     * 内圆坐标点
     */
    private PointF mStartPointF;


    /**
     * View中心点坐标点
     */
    private PointF mCenterPointF;

    /**
     * 圆环宽度
     */
    private int barLength = SizeUtils.dp2px(20);

    /**
     * 内圆半径dp
     */
    private int innerRadius = SizeUtils.dp2px(150);
    /**
     * 外圆半径dp
     */
    private int outRadius = innerRadius + barLength;
    /**
     * bar条的偏移角度，初始值为5
     */
    private int barAngle = 5;

    private int barAmount = 360 / barAngle;

    /**
     * 圆环下夹角的一半
     */
    private int includeAngle = 25;

    private ObjectAnimator mSegmentAnimator;

    private int segmentBarAmount = 10;

    private List<Float> linesList = new ArrayList<>();
    private float[] linesPoint;

    public void setSegmentBarAmount(int segmentBarAmount) {
        this.segmentBarAmount = segmentBarAmount;
        Log.e("data", "估值器数值：" + segmentBarAmount);
        invalidate();
    }

    public void setBarAngle(int barAngle) {
        this.barAngle = barAngle;
        barAmount = 360 / barAngle;
    }

    public void setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;

        circleBarPath = new Path();
    }

    public HuaweiWeather(Context context) {
        super(context);
    }

    public HuaweiWeather(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HuaweiWeather(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mOtherPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorSegmentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mOtherPaint.setColor(Color.WHITE);
        mOtherPaint.setStrokeWidth(5);
        mOtherPaint.setStrokeCap(Paint.Cap.ROUND);

        mColorSegmentPaint.setColor(Color.GREEN);
        mColorSegmentPaint.setStrokeWidth(5);
        mColorSegmentPaint.setStrokeCap(Paint.Cap.ROUND);
        Shader shader = new RadialGradient(SizeUtils.dp2px(200), -SizeUtils.dp2px(200),1000, Color.GREEN, Color.MAGENTA, Shader.TileMode.CLAMP);
        mColorSegmentPaint.setShader(shader);


        mStartPointF = new PointF(0, 0);
        mEndPointF = new PointF(0, 0);

        mCenterPointF = new PointF(0, 0);

        mSegmentAnimator = ObjectAnimator.ofInt(this, "segmentBarAmount", 10, 0);
        mSegmentAnimator.setInterpolator(new LinearInterpolator());
        mSegmentAnimator.setDuration(1000);
        mSegmentAnimator.setStartDelay(500);
        mSegmentAnimator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**将坐标原点移动到View中心点*/
        canvas.translate(centerX, centerY);

        canvas.drawCircle(0, 0, 20, mOtherPaint);

        for (int i = 0; i < barAmount; i++) {

            if (barAngle * i < 270 - includeAngle || barAngle * i > 270 + includeAngle) {

                barLength = SizeUtils.dp2px(20);
                outRadius = innerRadius + barLength;

                mOtherPaint.setStrokeWidth(5);

                mStartPointF = calculatPoint(mCenterPointF, innerRadius, barAngle * i);
                mEndPointF = calculatPoint(mCenterPointF, outRadius, barAngle * i);

                canvas.drawLine(mStartPointF.x, mStartPointF.y, mEndPointF.x, mEndPointF.y, mOtherPaint);
            } else if (barAngle * i == 270 - includeAngle || barAngle * i == 270 + includeAngle) {

                barLength = SizeUtils.dp2px(30);
                outRadius = innerRadius + barLength;

                mOtherPaint.setStrokeWidth(2);
                mStartPointF = calculatPoint(mCenterPointF, innerRadius, barAngle * i);
                mEndPointF = calculatPoint(mCenterPointF, outRadius, barAngle * i);

                canvas.drawLine(mStartPointF.x, mStartPointF.y, mEndPointF.x, mEndPointF.y, mOtherPaint);

            }

            Log.e("HuaweiWeather", "mStartPointF的x和y分别为;" + "(" + mStartPointF.x + "," + mStartPointF.y + ")");
            Log.e("HuaweiWeather", "mEndPointF的x和y分别为;" + "(" + mEndPointF.x + "," + mEndPointF.y + ")");
        }


        //画滚动条
        barLength = SizeUtils.dp2px(20);
        outRadius = innerRadius + barLength;
        mStartPointF = calculatPoint(mCenterPointF, innerRadius, barAngle * segmentBarAmount);
        mEndPointF = calculatPoint(mCenterPointF, outRadius, barAngle * segmentBarAmount);
        caculatePoints();
        canvas.drawLines(linesPoint, mColorSegmentPaint);


        mStartPointF = calculatPoint(mCenterPointF, innerRadius - SizeUtils.dp2px(20), barAngle * segmentBarAmount);
        canvas.drawCircle(mStartPointF.x, mStartPointF.y, SizeUtils.dp2px(5), mColorSegmentPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
    }

    /**
     * 输入起点、长度、旋转角度计算终点
     * <p>
     * 知道一个线段，一个定点，线段旋转角度求终点坐标
     * 根据极坐标系原理 x = pcog(a), y = psin(a)
     *
     * @param startPoint 起点
     * @param length     长度
     * @param angle      旋转角度
     * @return 计算结果点
     */
    private static PointF calculatPoint(PointF startPoint, float length, float angle) {
        float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
        //符合Android坐标的y轴朝下的标准，和y轴有关的统一减180度
        float deltaY = (float) Math.sin(Math.toRadians(angle - 180)) * length;
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    /**
     * 计算用于画动的segment线的float数组
     */
    private synchronized void caculatePoints() {
        linesList.add(mStartPointF.x);
        linesList.add(mStartPointF.y);
        linesList.add(mEndPointF.x);
        linesList.add(mEndPointF.y);

        linesPoint = new float[linesList.size()];

        for (int j = 0; j < linesList.size(); j++) {
            linesPoint[j] = linesList.get(j);
//            Log.e("HuaweiWeather", "linesPoint:" + linesPoint[j]);
        }
    }
}
