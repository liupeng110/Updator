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
        Updator.init("1.1.1.0", "https://raw.githubusercontent.com/ukiy2010/testJson/master/README.md");
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
