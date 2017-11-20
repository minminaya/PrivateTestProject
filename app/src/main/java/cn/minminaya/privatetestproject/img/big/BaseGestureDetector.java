package cn.minminaya.privatetestproject.img.big;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by Niwa on 2017/11/20.
 */

public abstract class BaseGestureDetector {

    /**是否在*/
    protected boolean mGestureInProgress;

    /**move事件（包含）之前的Event*/
    protected MotionEvent mPreMotionEvent;
    protected MotionEvent mCurrentMotionEvent;

    protected Context context;

    public BaseGestureDetector(Context context) {
        this.context = context;
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (!mGestureInProgress) {
            handleStartProgressEvent(event);
        } else {
            handleInProgressEvent(event);
        }

        return true;
    }

    protected abstract void handleInProgressEvent(MotionEvent event);

    protected abstract void handleStartProgressEvent(MotionEvent event);

    protected abstract void updateStateByEvent(MotionEvent event);


    protected void resetState() {
        if (mPreMotionEvent != null) {
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }
        if (mCurrentMotionEvent != null) {
            mCurrentMotionEvent.recycle();
            mCurrentMotionEvent = null;
        }

        mGestureInProgress = false;

    }
}
