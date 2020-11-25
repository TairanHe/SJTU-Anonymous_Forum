/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.templateproject.activity;

import android.view.KeyEvent;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.utils.ExchangeInfosWithAli;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.SettingSPUtils;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.templateproject.utils.TokenUtils;

import org.json.JSONException;

import java.io.IOException;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * 启动页【无需适配屏幕大小】
 *
 * @author xuexiang
 * @since 2019-06-30 17:32
 */
public class SplashActivity extends BaseSplashActivity implements CancelAdapt {

    public final static String KEY_IS_DISPLAY = "key_is_display";
    public final static String KEY_ENABLE_ALPHA_ANIM = "key_enable_alpha_anim";

    private boolean isDisplay = false;

    @Override
    protected long getSplashDurationMillis() {
        return 500;
    }

    /**
     * activity启动后的初始化
     */
    @Override
    protected void onCreateActivity() {
        isDisplay = getIntent().getBooleanExtra(KEY_IS_DISPLAY, isDisplay);
        boolean enableAlphaAnim = getIntent().getBooleanExtra(KEY_ENABLE_ALPHA_ANIM, false);
        SettingSPUtils spUtil = SettingSPUtils.getInstance();
        if (enableAlphaAnim) {
            initSplashView(R.drawable.bg_splash);
        } else {
            initSplashView(R.drawable.xui_config_bg_splash);
        }
        startSplash(false);

    }


    /**
     * 启动页结束后的动作
     */
    @Override
    protected void onSplashFinished() {
        boolean isAgree = MMKVUtils.getBoolean("key_agree_privacy", false);
        if (isAgree) {
            try {
                if (ExchangeInfosWithAli.VerifyToken_json() == 1){
                    ActivityUtils.startActivity(MainActivity.class);
                    finish();
                } else if (ExchangeInfosWithAli.VerifyToken_json() == 0){
                    ActivityUtils.startActivity(LoginActivity.class);
                    finish();
                }
                else {
                    XToastUtils.toast("您的账号已被封禁");
//                    android.os.Process.killProcess(android.os.Process.myPid());
                    finish();
                }

            } catch (JSONException | IOException e) {
                XToastUtils.toast("无法连接到网络，请检查网络后重试");
                finish();
                e.printStackTrace();
            }
        } else {
            Utils.showPrivacyDialog(this, (dialog, which) -> {
                dialog.dismiss();
                MMKVUtils.put("key_agree_privacy", true);
                ActivityUtils.startActivity(LoginActivity.class);
                finish();
            });
        }
//        if (!isDisplay) {
//            if (TokenUtils.hasToken()) {
//                ActivityUtils.startActivity(MainActivity.class);
//            } else {
//                ActivityUtils.startActivity(LoginActivity.class);
//            }
//        }
//        finish();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }

}
