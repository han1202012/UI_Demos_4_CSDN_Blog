package com.hanshuliang.shader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hanshuliang.shader.R;

/**
 * Paint 线性渲染
 */
public class PaintLinearLayoutShader extends View {

    public PaintLinearLayoutShader(Context context) {
        super(context);
    }

    public PaintLinearLayoutShader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintLinearLayoutShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        Paint mPaint = new Paint();

        //1. 创建位线性渲染对象, 并设置拉伸方式, 此处设置Shader.TileMode.CLAMP,  如果绘制的位置超出了图像的边界, 那么超出部分 使用最后一个像素的颜色值绘制
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        LinearGradient linearGradient = new LinearGradient( 0, 0,getWidth(), getHeight(), colors, null, Shader.TileMode.CLAMP);

        //2. 设置渲染到 Paint 对象
        mPaint.setShader(linearGradient);

        //3. 打开抗锯齿
        mPaint.setAntiAlias(true);

        //4. 绘制指定的矩形区域
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
