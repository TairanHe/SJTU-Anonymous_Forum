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

package org.wkfg.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
//import com.xuexiang.templateproject.activity.RegisterActivity;
import org.wkfg.utils.CountDownTimerUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import org.wkfg.R;
import org.wkfg.activity.MainActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import org.wkfg.utils.XToastUtils;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xutil.app.ActivityUtils;
import org.wkfg.utils.ExchangeInfosWithAli;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页面
 *
 * @author xuexiang
 * @since 2019-11-17 22:15
 */
@Page(anim = CoreAnim.none)
public class LoginFragment extends BaseFragment {

    @BindView(R.id.et_phone_number)
    MaterialEditText etPhoneNumber;
    @BindView(R.id.et_verify_code)
    MaterialEditText etVerifyCode;
//    @BindView(R.id.btn_get_verify_code)
//    RoundButton btnGetVerifyCode;

    private CountDownButtonHelper mCountDownHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
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
/*        SettingSPUtils spUtils = SettingSPUtils.getInstance();
        if (!spUtils.isAgreePrivacy()) {
            PrivacyUtils.showPrivacyDialog(getContext(), (dialog, which) -> {
                dialog.dismiss();
                spUtils.setIsAgreePrivacy(true);
            });
        }*/
    }

    @SingleClick
    @OnClick({R.id.btn_login, R.id.btn_register, R.id.tv_user_protocol, R.id.tv_privacy_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.btn_get_verify_code:
//                if (etPhoneNumber.validate()) {
//                    getVerifyCode(etPhoneNumber.getEditValue());
//                }
//                break;
            case R.id.btn_login:
                    loginByVerifyCode(etPhoneNumber.getEditValue(), etVerifyCode.getEditValue());
                    Log.d("LoginFragment.java", etPhoneNumber.getEditValue() + "   " + etVerifyCode.getEditValue());
                break;
            case R.id.btn_register:
                XToastUtils.info("验证码已送至您的邮箱\n请注意查收～勿重复发送验证码");
                try {
                    int VarifiedEmailAddress = ExchangeInfosWithAli.Request_verifycode(etPhoneNumber.getEditValue());
                    if (VarifiedEmailAddress == 0){
                        XToastUtils.toast("请输入交大邮箱哟～");
                    }
                    else {
                        SuperButton sb = findViewById(R.id.btn_register);
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(sb, 60000, 1000); //倒计时1分钟
                        mCountDownTimerUtils.start();
                    }

                } catch (JSONException | IOException e) {
                    Snackbar snackbar = Snackbar.make(view,"请检查网络后重试",Snackbar.LENGTH_SHORT);
                    snackbar.show();e.printStackTrace();
                }
                break;
//            case R.id.tv_other_login:
//                XToastUtils.info("修改密码");
//                break;
//            case R.id.tv_forget_password:
//                XToastUtils.info("忘记密码");
//                break;
            case R.id.tv_user_protocol:
                XToastUtils.info("用户协议");
                break;
            case R.id.tv_privacy_protocol:
                XToastUtils.info("隐私政策");
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */

/*
    private void getVerifyCode(String phoneNumber) {
        // TODO: 2019-11-18 这里只是界面演示而已
        XToastUtils.warning("只是演示，验证码请随便输");
        mCountDownHelper.start();
    }
*/

    private void loginByVerifyCode(String user_name, String verifyCode){
        if(!"".equals(user_name)){
            Log.d("Login","邮箱号不为空");
            if(!"".equals(verifyCode)){
//                int result = ExchangeInfosWithAli.Login(user_name,verifyCode);
                int result = 0;
                try {
                    result = ExchangeInfosWithAli.Login_json(user_name,verifyCode);
                } catch (JSONException | IOException e) {
//                Snackbar snackbar = Snackbar.make(view,"请检查网络后重试",Snackbar.LENGTH_SHORT);
//                snackbar.show();layout.finishLoadMore();
                    XToastUtils.toast("请检查网络后重试");
                    e.printStackTrace();
                }
                switch (result){
                    case -1:
                        XToastUtils.info("请先申请验证码");
                        break;
                    case 0:
                        XToastUtils.info("登录成功");
                        ActivityUtils.startActivity(MainActivity.class);
                        break;
                    case 1:
                        XToastUtils.info("验证码过期啦，请重新请求验证码");
                        break;
                    case 2:
                        XToastUtils.info("验证码错误，请重新请求验证码");
                        break;
                    case 3:
                        XToastUtils.info("json传回来3");
                    default:
                        break;
                }
            }
            else {
                XToastUtils.info("密码不能为空");
            }
        }
        else {
            XToastUtils.info("邮箱号不能为空");
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
