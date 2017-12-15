package com.example.zn.loginshareandpaydemo.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zn.loginshareandpaydemo.R;
import com.example.zn.loginshareandpaydemo.util.Constant;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created by xinwei on 2017/11/12.
 */

public class QQLoginActivity extends AppCompatActivity {

    private TextView mLoginQQBtn;

    private TextView mGetUserInfoBtn;

    private TextView mUserInfoTextView;

    private Tencent mTencent;

    private UserInfo mUserInfo;

    private static final String TAG = Constant.LOG + "QQLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_qq);

        initView();
        initData();
    }

    private void initView() {
        mLoginQQBtn = (TextView) findViewById(R.id.login_qq_btn);
        mGetUserInfoBtn = (TextView) findViewById(R.id.login_qq_get_userinfo_btn);
        mUserInfoTextView = (TextView) findViewById(R.id.login_qq_user_info);
        mLoginQQBtn.setOnClickListener(mClickListener);
        mGetUserInfoBtn.setOnClickListener(mClickListener);
    }

    private void initData() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        mTencent = Tencent.createInstance(Constant.APP_ID, getApplicationContext());
    }

    private void getUserInfo() {
        mUserInfo = new UserInfo(QQLoginActivity.this, mTencent.getQQToken());
        mUserInfo.getUserInfo(mUserInfoListener);
    }

    private void login() {
        if (!mTencent.isSessionValid()) {
            String scope = "all";
            mTencent.login(this, scope, new BaseUiListener());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            mTencent.handleResultData(data, new BaseUiListener());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_qq_btn:
                    login();
                    break;
                case R.id.login_qq_get_userinfo_btn:
                    getUserInfo();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mTencent != null) {
            //注销登录
            mTencent.logout(QQLoginActivity.this);
        }
        super.onDestroy();
    }

    /**
     * 实现回调 IUiListener
     * 调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
     */
    private class BaseUiListener implements IUiListener {

        /**
         * 返回json数据样例 {
         * "ret":0,
         * "pay_token":"xxxxxxxxxxxxxxxx",
         * "pf":"openmobile_android",
         * "expires_in":"7776000",
         * "openid":"xxxxxxxxxxxxxxxxxxx",
         * "pfkey":"xxxxxxxxxxxxxxxxxxx",
         * "msg":"sucess",
         * "access_token":"xxxxxxxxxxxxxxxxxxxxx"
         * }
         */

        @Override
        public void onComplete(Object o) {
            Log.d(TAG, "onComplete()");

            try {
                JSONObject jo = (JSONObject) o;

                int ret = jo.getInt("ret");
                Log.d(TAG, "json=" + String.valueOf(jo));

                if (ret == 0) {
                    String openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        @Override
        public void onError(UiError e) {
            Log.d(TAG, "onError() " + e);

        }

        @Override
        public void onCancel() {
            Log.d(TAG, "onCancel() ");
        }
    }

    private IUiListener mUserInfoListener = new IUiListener() {

        @Override
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(QQLoginActivity.this, "onError",
                    Toast.LENGTH_LONG).show();
        }

        /**
         * 返回用户信息样例
         *
         * {"is_yellow_year_vip":"0","ret":0,
         * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
         * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
         * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
         * "city":"黄冈","
         * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
         * "vip":"0","level":"0",
         * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
         * "province":"湖北",
         * "is_yellow_vip":"0","gender":"男",
         * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
         */
        @Override
        public void onComplete(Object arg0) {
            // TODO Auto-generated method stub
            if (arg0 == null) {
                return;
            }
            try {
                JSONObject jo = (JSONObject) arg0;
                int ret = jo.getInt("ret");
                Log.d(TAG, "onComplete() | json = " + String.valueOf(jo));
                String nickName = jo.getString("nickname");
                String gender = jo.getString("gender");
                String province = jo.getString("province");
                String city = jo.getString("city");
                String figureurl = jo.getString("figureurl");

                Toast.makeText(QQLoginActivity.this, "你好，" + nickName,
                        Toast.LENGTH_LONG).show();

                mUserInfoTextView.setText(String.valueOf(jo));

            } catch (Exception e) {
                // TODO: handle exception
            }

        }

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
        }
    };


    /**
     * 实现回调 IRequestListener
     * 使用requestAsync、request等通用方法调用sdk未封装的接口时，例如上传图片、查看相册等，需传入该回调的实例。
     */
    private class BaseApiListener implements IRequestListener {
        @Override
        public void onComplete(JSONObject jsonObject) {

        }

        @Override
        public void onIOException(IOException e) {

        }

        @Override
        public void onMalformedURLException(MalformedURLException e) {

        }

        @Override
        public void onJSONException(JSONException e) {

        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException e) {

        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException e) {

        }

        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {

        }

        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e) {

        }

        @Override
        public void onUnknowException(Exception e) {

        }
    }
}
