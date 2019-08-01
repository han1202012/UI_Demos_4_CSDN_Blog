package net.csdn.blog.hanshuliang.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 滤镜示例 : 颜色通道值翻倍 , 反向效果 ( 底片效果 ) 就是 设置 颜色通道 倍数为 -1 倍 , 然后每个颜色值加上 255
 *           将 RGBA 某个通道的值翻倍 , 即将对应的颜色矩阵值设置成对应的倍数
 *           第 1 行 第 1 列 : R ( Red ) , 红色通道倍数 ;
 *           第 2 行 第 2 列 : G ( Green ) , 绿色通道倍数 ;
 *           第 3 行 第 3 列 : B ( Blue ) , 蓝色通道倍数 ;
 *           第 4 行 第 4 列 : A ( Alpha ) , 透明度通道倍数 ;
 *
 */
public class PaintFilterC extends View {

    /**
     * 设置滤镜时的画笔
     */
    private Paint paint;

    /**
     * 使用滤镜处理的图像
     */
    private Bitmap bitmap;

    public PaintFilterC(Context context) {
        super(context);
    }

    public PaintFilterC(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 创建画笔 , 并打开抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 加载图像资源
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.trump);
    }

    public PaintFilterC(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // ① 设置颜色矩阵 , 底片效果
        ColorMatrix matrix = new ColorMatrix(new float[]{
                -1,  0,  0,  0,  255,
                0,  -1,  0,  0,  255,
                0,  0,  -1,  0,  255,
                0,  0,  0,   1,  0,
        });

        // ② 根据颜色矩阵创建滤镜
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        // ③ 绘制区域
        RectF rectF = new RectF(0,0,getWidth(), getHeight());
        // ④ 设置滤镜
        paint.setColorFilter(filter);

        // ⑤ 绘制经过滤镜处理的图片
        canvas.drawBitmap(bitmap, null , rectF, paint);

    }
}
