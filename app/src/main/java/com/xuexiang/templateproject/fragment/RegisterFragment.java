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

package com.xuexiang.templateproject.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.LoginActivity;
import com.xuexiang.templateproject.activity.MainActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.PrivacyUtils;
import com.xuexiang.templateproject.utils.RandomUtils;
import com.xuexiang.templateproject.utils.SettingSPUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.templateproject.utils.ExchangeInfosWithAli;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xutil.app.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页面
 *
 * @author xuexiang
 * @since 2019-11-17 22:15
 */
@Page(anim = CoreAnim.none)
public class RegisterFragment extends BaseFragment {

    @BindView(R.id.et_phone_number1)
    MaterialEditText user_name;
    @BindView(R.id.et_verify_code1)
    MaterialEditText etVerifyCode;
    @BindView(R.id.et_verify_code2)
    MaterialEditText etVerifyCode2;
//    @BindView(R.id.btn_get_verify_code)
//    RoundButton btnGetVerifyCode;

    private CountDownButtonHelper mCountDownHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        titleBar.setLeftImageDrawable(ResUtils.getVectorDrawable(getContext(), R.drawable.ic_login_close));
        return titleBar;
    }

    @Override
    protected void initViews() {
//        mCountDownHelper = new CountDownButtonHelper(btnGetVerifyCode, 60);

        //隐私政策弹窗
//        SettingSPUtils spUtils = SettingSPUtils.getInstance();
//        if (!spUtils.isAgreePrivacy()) {
//            PrivacyUtils.showPrivacyDialog(getContext(), (dialog, which) -> {
//                dialog.dismiss();
//                spUtils.setIsAgreePrivacy(true);
//            });
//        }
    }

    @SingleClick
    @OnClick({R.id.btn_register2, R.id.tv_user_protocol1, R.id.tv_privacy_protocol1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.btn_get_verify_code:
//                if (etPhoneNumber.validate()) {
//                    getVerifyCode(etPhoneNumber.getEditValue());
//                }
//                break;
//            case R.id.btn_register:
//                // 这里写上判断
//                if (etPhoneNumber.validate()) {
//                    if (etVerifyCode.validate()) {
//                        loginByVerifyCode(etPhoneNumber.getEditValue(), etVerifyCode.getEditValue());
//                        Log.d("LoginFragment.java", etPhoneNumber.getEditValue() + "   " + etVerifyCode.getEditValue());
//                    }
//                }
//                break;
            case R.id.btn_register2:
                // 写个注册的接口
                // 判断用户名有没有用过
                if(user_name.validate())
                {
                    if(etVerifyCode.validate())
                    {
                        if(etVerifyCode.getEditValue().equals(etVerifyCode2.getEditValue())){
                            Log.d("RegisterFragment.java", user_name.getEditValue() + etVerifyCode.getEditValue() + etVerifyCode2.getEditValue());
                            Register_verify(user_name.getEditValue(),etVerifyCode.getEditValue());
                        }
                        else{
                            XToastUtils.info("密码与验证密码不一致！");
                        }
                    }
                    else {
                        XToastUtils.info("密码不能为空");
                    }
                }
                else {
                    XToastUtils.info("用户名不能为空");
                }
                break;
            case R.id.tv_user_protocol1:
                XToastUtils.info("用户协议");
                break;
            case R.id.tv_privacy_protocol1:
                XToastUtils.info("隐私政策");
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode(String phoneNumber) {
        // TODO: 2019-11-18 这里只是界面演示而已
        XToastUtils.warning("只是演示，验证码请随便输");
        mCountDownHelper.start();
    }

    private void Register_verify(String user_name, String password) {
        //向后端传递
        int result = ExchangeInfosWithAli.Register(user_name,password);
        String s = String.valueOf(result);
        Log.d("MAS",s);
        if (result == 0) {
            XToastUtils.info("注册成功！");
            popToBack();
            ActivityUtils.startActivity(LoginActivity.class);
        }
        else {
            XToastUtils.info("注册失败");
        }
    }


    @Override
    public void onDestroyView() {
        if (mCountDownHelper != null) {
            mCountDownHelper.recycle();
        }
        super.onDestroyView();
    }
}
