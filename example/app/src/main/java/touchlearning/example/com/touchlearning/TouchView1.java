package touchlearning.example.com.touchlearning;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Administrator on 2017/7/11.
 */

public class TouchView1 extends View {
    private boolean canTouch;
    private float lastX,lastY;
    private int mWidth,mHeight;
    private int parentWidth,parentHeight;
    public TouchView1(Context context) {
        super(context);
    }

    public TouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TouchView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.TouchView1);
        canTouch=typedArray.getBoolean(R.styleable.TouchView1_canTocuh,false);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!canTouch){
            return super.onTouchEvent(event);
        }
        if(parentWidth<=0 || parentHeight<=0) {
            ViewParent parentView = getParent();
            if (parentView instanceof ViewGroup) {
                parentWidth = ((ViewGroup) parentView).getWidth();
                parentHeight = ((ViewGroup) parentView).getHeight();
            }
        }
        float rawX=event.getRawX();
        float rawY=event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=rawX;
                lastY=rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX=rawX-lastX;
                float offsetY=rawY-lastY;
                int setLeft= (int) (getLeft() + offsetX);
                int setTop= (int) (getTop() + offsetY);
                int setRight= (int) (getRight() + offsetX);
                int setBottom= (int) (getBottom() + offsetY);
                lastX = rawX;
                lastY = rawY;
                if(setLeft<0){
                    setLeft=0;
                    setRight=mWidth;
                }
                if(parentWidth!=0 && setRight>parentWidth){
                   setRight=parentWidth;
                   setLeft=parentWidth-mWidth;
                }
                if(setTop<0){
                   setTop=0;
                    setBottom=mHeight;
                }
                if(parentHeight!=0 && setBottom>parentHeight){
                   setBottom=parentHeight;
                    setTop=parentHeight-mHeight;
                }
                Log.d("测试",setLeft+"/"+setTop+"/"+setRight+"/"+setBottom+"/"+parentWidth+"/"+parentHeight+"/"+mWidth+"/"+mHeight);

                layout(setLeft
                        , setTop
                        , setRight
                        , setBottom);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


}
