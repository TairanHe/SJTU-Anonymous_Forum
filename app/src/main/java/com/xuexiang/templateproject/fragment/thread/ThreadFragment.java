/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.templateproject.fragment.thread;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.webview.AgentWebActivity;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.grouplist.XUIGroupListView;
import com.xuexiang.xutil.app.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Page(name = "帖子")
public class ThreadFragment extends BaseFragment {

    @BindView(R.id.version)
    TextView mVersionTextView;
    @BindView(R.id.about_list)
    XUIGroupListView mAboutGroupListView;
    @BindView(R.id.copyright)
    TextView mCopyrightTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initViews() {
        mVersionTextView.setText(String.format("版本号：%s", AppUtils.getAppVersionName()));

        XUIGroupListView.newSection(getContext())
                .addItemView(mAboutGroupListView.createItemView(getResources().getString(R.string.about_item_homepage)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_project_github)))
                .addItemView(mAboutGroupListView.createItemView(getResources().getString(R.string.about_item_author_github)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_author_github)))
                .addItemView(mAboutGroupListView.createItemView(getResources().getString(R.string.about_item_donation_link)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_donation_link)))
                .addItemView(mAboutGroupListView.createItemView(getResources().getString(R.string.about_item_add_qq_group)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_add_qq_group)))
                .addTo(mAboutGroupListView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String currentYear = dateFormat.format(new Date());
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), currentYear));
    }
}