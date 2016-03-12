package com.ukiy.test.testupdator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ukiy.module.updator.core.Updator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Updator.init("1.1.1.0", "http://dd.myapp.com/16891/445EDA31D2839EA4DA3334BF214BB084.apk?fsname=com.tencent.tmgp.mhxy.sqsy_1.66.0_10660.apk");
        Button btn = new Button(MainActivity.this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updator.start(MainActivity.this);
            }
        });
        setContentView(btn);
    }
}
