package com.hanshuliang.shader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


public class B_1_Paint_Shader_Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_1);

        findViewById(R.id.bitmap_shader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(B_1_Paint_Shader_Activity.this, B_2_Paint_BitmapShader_Activity.class));
            }
        });

    }

}
