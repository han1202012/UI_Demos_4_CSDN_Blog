package net.csdn.blog.hanshuliang.xfermod;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * S ( Source ):                源图
 * D ( Destination ) :          目标图
 * Sa ( Source Alhpa ) :        源图透明度
 * Da ( Destination Alhpa ) :   目标图透明度
 * Sc ( Source Color ) :        源图色值
 * Dc ( Destination Color ) :   目标图色值
 */
public class XfermodesView extends View {
    private static int W;
    private static int H;
    private static final int ROW_MAX = 4;   // 每行绘制的个数

    private Bitmap mSrcB;
    private Bitmap mDstB;
    private Shader mBG;     // 背景棋盘格渲染器


    //方便使用循环设置 Xfermod 图形混合模式
    private static final Xfermode[] sModes = {
            //该模式可以清除绘制区域内的所有像素元素
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };

    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
    };

    public XfermodesView(Context context) {
        super(context);
    }

    public XfermodesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XfermodesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if(W == 0){
            W = (int) ((getWidth() - 60) / 4f);
            H = W;

            mSrcB = makeSrc(W, H);
            mDstB = makeDst(W, H);

            // 绘制背景图片用的 , 这里创建一个位图渲染

            // 使用颜色值创建 指定大小的位图
            Bitmap bm = Bitmap.createBitmap(new int[] { 0xFFFFFFFF, 0xFFCCCCCC,
                            0xFFCCCCCC, 0xFFFFFFFF }, 2, 2,
                    Bitmap.Config.RGB_565);
            // 创建位图渲染
            mBG = new BitmapShader(bm,
                    Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT);
            // 设置矩阵信息
            Matrix m = new Matrix();
            m.setScale(6, 6);
            mBG.setLocalMatrix(m);
        }

        // 创建绘制 时 的画笔
        Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelP.setTextAlign(Paint.Align.CENTER);
        labelP.setTextSize(30);

        Paint paint = new Paint();
        paint.setFilterBitmap(false);

        // 画布偏移
        canvas.translate(15, 60);

        int x = 0;
        int y = 0;

        // 逐个绘制 16 个不同的模式
        for (int i = 0; i < sModes.length; i++) {
            // 绘制矩形边框
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            paint.setShader(null);
            canvas.drawRect(x - 0.5f, y - 0.5f,
                    x + W + 0.5f, y + H + 0.5f, paint);

            // 绘制矩形背景
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(mBG);
            canvas.drawRect(x, y, x + W, y + H, paint);

            // 绘制 目标图 和 源图 , 注意 先画 目标图 , 在绘制 源图

            // 新建图层 , 保存canvas 状态 , 该方法与 restore 配对使用 , save 之后进行变换位移等操作 , restore 恢复到之前的状态
            int sc = canvas.saveLayer(x, y, x + W, y + H, null, Canvas.ALL_SAVE_FLAG);
            canvas.translate(x, y);

            //先绘制 目标图
            canvas.drawBitmap(mDstB, 0, 0, paint);

            //设置 Xfermod 图形混合模式
            paint.setXfermode(sModes[i]);

            //在绘制 源图 , 使用源图 与 目标图交汇 , 然后两张图按照 设置的 Xfermod 模式来显示
            canvas.drawBitmap(mSrcB, 0, 0, paint);

            //绘制完 两个 图形后 , 要取消 Xfermod , 以免影响下个循环绘制下一组 Xfermod 图形
            //下一次循环还需要使用该 paint 画笔进行绘制
            paint.setXfermode(null);

            //回退到第 sc 次入栈时的状态 , 该方法与 save 方法配对使用
            canvas.restoreToCount(sc);

            // 绘制文字
            canvas.drawText(sLabels[i],
                    x + W/2, y - labelP.getTextSize()/2, labelP);

            x += W + 10;

            // 一行画满4个后换行
            if ((i % ROW_MAX) == ROW_MAX - 1) {
                x = 0;
                y += H + 60;
            }
        }
    }

    // create a bitmap with a circle, used for the "dst" image
    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w*3/4, h*3/4), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
        c.drawRect(w/3, h/3, w*19/20, h*19/20, p);
        return bm;
    }
}
