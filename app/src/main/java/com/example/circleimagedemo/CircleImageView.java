package com.example.circleimagedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CircleImageView extends AppCompatImageView {
    private int mSize;
    private Paint mPaint;
    private Xfermode mPorterDuffXfermode;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mSize = Math.min(width, height); //取宽高的最小值
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(drawable == null) return;

        Bitmap sourceBitmap = ((BitmapDrawable)getDrawable()).getBitmap();
        if(sourceBitmap != null) {
            //对图片进行缩放以适应控件的大小
            Bitmap bitmap =resizeBitmap(sourceBitmap, getWidth(), getHeight());
            //drawCircleBitmapByXfermode(canvas, bitmap);
            drawCircleBitmapByShader(canvas, bitmap);
        }
    }

    private Bitmap resizeBitmap(Bitmap sourceBitmap, int dstWidth, int dstHeight) {
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();

        float widthScale = ((float) dstWidth) / width;
        float heightscale = ((float) dstHeight) / height;

        //取最大缩放比
        float scale = Math.max(widthScale, heightscale);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        return Bitmap.createBitmap(sourceBitmap, 0, 0, width, height, matrix, true);
    }

    //使用Xfermode
    private void drawCircleBitmapByXfermode(Canvas canvas, Bitmap bitmap) {
        final int sc = canvas.saveLayer(0,0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //绘制dst层
        canvas.drawCircle(mSize / 2, mSize / 2, mSize / 2, mPaint);
        //设置图层混合模式SRC_IN
        mPaint.setXfermode(mPorterDuffXfermode);
        //绘制src层
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restoreToCount(sc);
    }

    //使用BitmapShader
    private void drawCircleBitmapByShader(Canvas canvas, Bitmap bitmap) {
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawCircle(mSize / 2, mSize / 2, mSize / 2, mPaint);
    }
}
