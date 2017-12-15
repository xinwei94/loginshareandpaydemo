package com.example.zn.loginshareandpaydemo.share;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.example.zn.loginshareandpaydemo.R;
import com.example.zn.loginshareandpaydemo.util.Constant;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Zn on 2017/12/10.
 */

public class QQShareActivity extends AppCompatActivity {

    private static final String TAG = Constant.LOG + "QQLoginActivity";

    private TextView mShareTextBtn;

    private TextView mSharePicBtn;

    private TextView mShareQzoneBtn;

    private TextView mPublishQzoneBtn;

    private Tencent mTencent;

    private Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_qq);

        initView();
        initData();
    }

    private void initView() {
        mShareTextBtn = (TextView) findViewById(R.id.share_qq_text_btn);
        mSharePicBtn = (TextView) findViewById(R.id.share_qq_picture_btn);
        mShareQzoneBtn = (TextView) findViewById(R.id.share_qzone_btn);
        mPublishQzoneBtn = (TextView) findViewById(R.id.publish_qzone_btn);

        mShareTextBtn.setOnClickListener(mClickListener);
        mSharePicBtn.setOnClickListener(mClickListener);
        mShareQzoneBtn.setOnClickListener(mClickListener);
        mPublishQzoneBtn.setOnClickListener(mClickListener);
    }

    private void initData() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(Constant.APP_ID, getApplicationContext());
    }


    //分享消息到QQ
    private void shareToQQ() {
        params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "标题");// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");// 内容地址

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");

        mTencent.shareToQQ(QQShareActivity.this, params, new MyIUiListener());
    }

    //分享纯图片到QQ
    private void shareImgToQQ(String imgUrl) {
        params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);// 设置分享类型为纯图片分享
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrl);// 需要分享的本地图片URL

        mTencent.shareToQQ(QQShareActivity.this, params, new MyIUiListener());

    }

    //分享到QQ空间
    private void shareToQZone() {
        params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");// 标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");// 摘要
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");// 内容地址
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址

        mTencent.shareToQzone(QQShareActivity.this, params, new MyIUiListener());
    }

    //上传图片到QQ空间
    private void publishToQzone(ArrayList<String> imgUrlList) {
        // 分享类型
        params = new Bundle();
        params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_SUMMARY, "说说正文");
        params.putStringArrayList(QzonePublish.PUBLISH_TO_QZONE_IMAGE_URL,
                imgUrlList);// 图片地址ArrayList

        mTencent.publishToQzone(QQShareActivity.this, params, new MyIUiListener());
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.share_qq_text_btn:
                    shareToQQ();
                    break;
                case R.id.share_qq_picture_btn:
                    shareImgToQQ(Environment.getExternalStorageDirectory()+"/test_share.png");
                    break;

                case R.id.share_qzone_btn:
                    shareToQZone();
                    break;
                case R.id.publish_qzone_btn:
                    ArrayList<String> imgUrlList = new ArrayList<>();
                    imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
                    publishToQzone(imgUrlList);
                    break;
            }
        }
    };

    class MyIUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            // 操作成功
        }

        @Override
        public void onError(UiError uiError) {
            // 分享异常

        }

        @Override
        public void onCancel() {
            // 取消分享
        }
    }
}
