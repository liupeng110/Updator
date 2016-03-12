package com.ukiy.module.updator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ukiy.module.updator.core.Updator;

public class UpdatorTestActivity extends AppCompatActivity {

    private void findView() {
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updator.start(UpdatorTestActivity.this);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Updator.init("1.1.2.0", "https://raw.githubusercontent.com/ukiy2010/testJson/master/README.md");
        findView();
    }

}
