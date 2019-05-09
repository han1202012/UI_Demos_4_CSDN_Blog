package com.hanshuliang.shader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hanshuliang.shader.R;


public class PaintShaderActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_1);

        findViewById(R.id.bitmap_shader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaintShaderActivity.this, BitmapShaderActivity.class));
            }
        });

    }

}
