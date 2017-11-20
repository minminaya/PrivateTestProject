package cn.minminaya.privatetestproject.img.big;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Created by Niwa on 2017/11/20.
 */

public class MoveGestureDetector extends BaseGestureDetector {

    private PointF mCurrentPoint;
    private PointF mPrePoint;

    /**
     * 仅仅为了减少创建内存
     **/
    private PointF mDeltaPoint = new PointF();

    /**
     * 用于记录最终结果
     */
    private PointF mExternalPoint = new PointF();

    public float getExternalPointX() {
        return mExternalPoint.x;
    }

    public float getExternalPointY() {
        return mExternalPoint.y;
    }


    private OnMoveGestureListener mListener;

    public MoveGestureDetector(Context context, OnMoveGestureListener mListener) {
        super(context);
        this.mListener = mListener;
    }

    @Override
    protected void handleInProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mListener.onMoveEnd(this);
                resetState();
                break;
            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);
                boolean update = mListener.onMove(this);
                if (update) {
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
        }
    }

    @Override
    protected void handleStartProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                resetState();//防止没有接收到CANCEL or UP ,保险起见
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateByEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mGestureInProgress = mListener.onMoveBegin(this);
                break;
        }
    }

    @Override
    protected void updateStateByEvent(MotionEvent event) {
        MotionEvent prev = mPreMotionEvent;
        mPrePoint = caculateFocalPoint(prev);
        mCurrentPoint = caculateFocalPoint(event);

        //如果move时后up之后的event，多指数量不一样，就为0
        boolean mSkipThisMoveEvent = prev.getPointerCount() != event.getPointerCount();

        mExternalPoint.x = mSkipThisMoveEvent ? 0 : mCurrentPoint.x - mPrePoint.x;
        mExternalPoint.y = mSkipThisMoveEvent ? 0 : mCurrentPoint.y - mPrePoint.y;
    }

    /**
     * 根据Event计算多指中心点
     */
    private PointF caculateFocalPoint(MotionEvent event) {
        int cout = event.getPointerCount();
        float x = 0, y = 0;
        for (int i = 0; i < cout; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= cout;
        y /= cout;
        return new PointF(x, y);
    }


    public interface OnMoveGestureListener {
        boolean onMoveBegin(MoveGestureDetector detector);

        boolean onMove(MoveGestureDetector detector);

        void onMoveEnd(MoveGestureDetector detector);
    }

    public static class SimpleMoveGestureDetector implements OnMoveGestureListener {

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            return false;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {
        }
    }
}
