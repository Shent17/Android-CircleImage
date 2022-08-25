package com.example.circleimagedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.squareup.picasso.Transformation;


@RequiresApi(api = Build.VERSION_CODES.O_MR1)
public class CircleImageTransformer implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        int mSize = Math.min(source.getHeight(), source.getWidth());

        Bitmap bitmap = Bitmap.createBitmap(mSize, mSize, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float radius = mSize / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "Circle-Image";
    }
}
