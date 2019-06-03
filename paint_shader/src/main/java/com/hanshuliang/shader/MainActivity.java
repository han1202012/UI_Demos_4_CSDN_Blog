package com.hanshuliang.shader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hanshuliang.shader.activity.BitmapShaderActivity;
import com.hanshuliang.shader.activity.LinearGradientActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.paint_shader_bitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BitmapShaderActivity.class));
            }
        });

        findViewById(R.id.paint_shader_lineargradient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LinearGradientActivity.class));
            }
        });

    }
}
