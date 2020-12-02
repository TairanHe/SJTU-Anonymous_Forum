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

package com.xuexiang.wukefenggao.fragment;

import android.content.Intent;

import com.xuexiang.wukefenggao.R;
import com.xuexiang.wukefenggao.activity.LoginActivity;
import com.xuexiang.wukefenggao.core.BaseFragment;
import com.xuexiang.wukefenggao.utils.MMKVUtils;
import com.xuexiang.wukefenggao.utils.Utils;
import com.xuexiang.wukefenggao.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xutil.XUtil;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2019-10-15 22:38
 */
@Page(name = "设置")
public class SettingsFragment extends BaseFragment implements SuperTextView.OnSuperTextViewClickListener {

//    @BindView(R.id.menu_common)
//    SuperTextView menuCommon;
    @BindView(R.id.menu_privacy)
    SuperTextView menuPrivacy;
//    @BindView(R.id.menu_push)
//    SuperTextView menuPush;
    @BindView(R.id.menu_helper)
    SuperTextView menuHelper;
    @BindView(R.id.menu_change_account)
    SuperTextView menuChangeAccount;
    @BindView(R.id.menu_logout)
    SuperTextView menuLogout;
    @BindView(R.id.menu_about)
    SuperTextView menuAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initViews() {
//        menuCommon.setOnSuperTextViewClickListener(this);
        menuPrivacy.setOnSuperTextViewClickListener(this);
//        menuPush.setOnSuperTextViewClickListener(this);
        menuHelper.setOnSuperTextViewClickListener(this);
        menuChangeAccount.setOnSuperTextViewClickListener(this);
        menuLogout.setOnSuperTextViewClickListener(this);
        menuAbout.setOnSuperTextViewClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(SuperTextView superTextView) {
        switch(superTextView.getId()) {
//            case R.id.menu_common:
//                 break;
            case R.id.menu_privacy:
                Utils.showOnlyPrivacyDialog(getContext(), (dialog, which) -> {
                    dialog.dismiss();
                });
                break;
//            case R.id.menu_push:
//                break;
            case R.id.menu_helper:
                Utils.showHelperDialog(getContext(), (dialog, which) -> {
                    dialog.dismiss();
                });
                break;
            case R.id.menu_about:
                openNewPage(AboutFragment.class);
                break;
            case R.id.menu_change_account:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.menu_logout:
                XToastUtils.toast(superTextView.getCenterString());
                MMKVUtils.put("Token", "NULL");
                XUtil.get().exitApp();
                break;
            default:
                break;
        }
    }
}
