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
public class PaintLinearGradientShader extends View {

    public PaintLinearGradientShader(Context context) {
        super(context);
    }

    public PaintLinearGradientShader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintLinearGradientShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        Paint mPaint = new Paint();

        //1. 设置线性渲染 的 颜色值 数组
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        // 创建 线性渲染 , 设置其 渲染方向 , 渲染颜色数组 , 渲染模式等
        LinearGradient linearGradient = new LinearGradient( 0, 0, getWidth(), getHeight(), colors, null, Shader.TileMode.CLAMP);

        //2. 设置渲染到 Paint 对象
        mPaint.setShader(linearGradient);

        //3. 打开抗锯齿
        mPaint.setAntiAlias(true);

        //4. 绘制指定的矩形区域
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
