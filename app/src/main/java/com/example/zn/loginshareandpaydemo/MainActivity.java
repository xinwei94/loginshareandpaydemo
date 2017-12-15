package com.example.zn.loginshareandpaydemo;

/**
 * Created by xinwei on 2017/11/12.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zn.loginshareandpaydemo.login.LoginMainActivity;
import com.example.zn.loginshareandpaydemo.share.ShareMainActivity;
import com.example.zn.loginshareandpaydemo.util.ActivityUtil;

public class MainActivity extends AppCompatActivity {

    private TextView mLoginBtn;
    private TextView mShareBtn;
    private TextView mPayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mLoginBtn = (TextView) findViewById(R.id.main_btn_login);
        mShareBtn = (TextView) findViewById(R.id.main_btn_share);
        mPayBtn = (TextView) findViewById(R.id.main_btn_pay);

        mLoginBtn.setOnClickListener(mClickListener);
        mShareBtn.setOnClickListener(mClickListener);
        mPayBtn.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.main_btn_login:
                    ActivityUtil.gotoActivity(MainActivity.this, LoginMainActivity.class, null);
                    break;
                case R.id.main_btn_share:
                    ActivityUtil.gotoActivity(MainActivity.this, ShareMainActivity.class, null);
                    break;
                case R.id.main_btn_pay:

                    break;
            }
        }
    };
}
