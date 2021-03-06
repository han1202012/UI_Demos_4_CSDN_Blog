package com.hanshuliang.shader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hanshuliang.shader.R;


public class PaintBitmapShaderRepeat extends View {

    public PaintBitmapShaderRepeat(Context context) {
        super(context);
    }

    public PaintBitmapShaderRepeat(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintBitmapShaderRepeat(Context context, @Nullable AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        Paint mPaint = new Paint();
        Bitmap mBitmap = ((BitmapDrawable)getResources().
                getDrawable(R.mipmap.aodesai)).getBitmap();

        //1. 创建位图渲染对象, 并设置拉伸方式, 此处设置Shader.TileMode.CLAMP,
        //   如果绘制的位置超出了图像的边界, 使用平铺方式填充
        BitmapShader bitmapShader = new BitmapShader(mBitmap,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        //2. 设置渲染到 Paint 对象
        mPaint.setShader(bitmapShader);

        //3. 打开抗锯齿
        mPaint.setAntiAlias(true);

        //4. 绘制指定的矩形区域
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
