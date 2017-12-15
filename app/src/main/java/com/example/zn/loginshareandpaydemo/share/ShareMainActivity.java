package com.example.zn.loginshareandpaydemo.share;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zn.loginshareandpaydemo.R;
import com.example.zn.loginshareandpaydemo.util.ActivityUtil;

/**
 * Created by Zn on 2017/12/10.
 */

public class ShareMainActivity extends AppCompatActivity {

    private TextView mShareQQBtn;
    private TextView mShareWXBtn;
    private TextView mShareWBBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_main);

        initView();
    }

    private void initView() {
        mShareQQBtn = (TextView) findViewById(R.id.share_main_btn_qq);
        mShareWXBtn = (TextView) findViewById(R.id.share_main_btn_wx);
        mShareWBBtn = (TextView) findViewById(R.id.share_main_btn_wb);

        mShareQQBtn.setOnClickListener(mClickListener);
        mShareWXBtn.setOnClickListener(mClickListener);
        mShareWBBtn.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.share_main_btn_qq:
                    ActivityUtil.gotoActivity(ShareMainActivity.this, QQShareActivity.class, null);
                    break;
                case R.id.share_main_btn_wx:

                    break;
                case R.id.share_main_btn_wb:

                    break;
            }
        }
    };
}
