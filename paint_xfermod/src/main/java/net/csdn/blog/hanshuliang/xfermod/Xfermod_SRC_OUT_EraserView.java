package net.csdn.blog.hanshuliang.xfermod;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 橡皮擦
 */
public class Xfermod_SRC_OUT_EraserView extends View {

    /**
     * 绘制 目标图像 和 源图像 的画笔
     */
    private Paint mPaint;

    /**
     * 目标图像
     */
    private Bitmap Destination;

    /**
     * 源图像
     */
    private Bitmap Source;

    /**
     * 记录橡皮擦的轨迹
     */
    private Path eraserPath;


    private float mPreX,mPreY;

    public Xfermod_SRC_OUT_EraserView(Context context) {
        super(context);
    }
    public Xfermod_SRC_OUT_EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public Xfermod_SRC_OUT_EraserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Xfermod_SRC_OUT_EraserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        return true;
    }
}
