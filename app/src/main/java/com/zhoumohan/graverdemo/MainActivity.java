package com.zhoumohan.graverdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhoumohan.graver.BindView;
import com.zhoumohan.graver.ContentView;
import com.zhoumohan.graver.OnClick;
import com.zhoumohan.graverdemo.base.BaseActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @BindView(R.id.btn)
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, button.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.tv_1, R.id.tv_2, R.id.btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Toast.makeText(this, button.getText().toString(), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
