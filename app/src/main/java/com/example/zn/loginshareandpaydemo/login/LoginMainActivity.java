package com.example.zn.loginshareandpaydemo.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zn.loginshareandpaydemo.R;
import com.example.zn.loginshareandpaydemo.util.ActivityUtil;

/**
 * Created by xinwei on 2017/11/12.
 */

public class LoginMainActivity extends AppCompatActivity {

    private TextView mLoginQQBtn;
    private TextView mLoginWXBtn;
    private TextView mLoginWBBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        initView();
    }

    private void initView() {
        mLoginQQBtn = (TextView) findViewById(R.id.login_main_btn_qq);
        mLoginWXBtn = (TextView) findViewById(R.id.login_main_btn_wx);
        mLoginWBBtn = (TextView) findViewById(R.id.login_main_btn_wb);

        mLoginQQBtn.setOnClickListener(mClickListener);
        mLoginWXBtn.setOnClickListener(mClickListener);
        mLoginWBBtn.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_main_btn_qq:
                    ActivityUtil.gotoActivity(LoginMainActivity.this, QQLoginActivity.class, null);
                    break;
                case R.id.login_main_btn_wx:

                    break;
                case R.id.login_main_btn_wb:

                    break;
            }
        }
    };
}
