package com.example.circleimagedemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        ImageView imageView = (ImageView) findViewById(R.id.imageView4);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView5);
        imageView.setImageDrawable(new CircleImageDrawable(bitmap));

        Picasso.with(this)
                .load(R.drawable.image)
                .transform(new CircleImageTransformer())
                .into(imageView1);
    }
}