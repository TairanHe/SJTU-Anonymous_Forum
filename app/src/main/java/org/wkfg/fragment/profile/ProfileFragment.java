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

package org.wkfg.fragment.profile;

import org.wkfg.R;
import com.xuexiang.templateproject.core.BaseFragment;
import org.wkfg.fragment.FavorFragment;
import org.wkfg.fragment.MyThreadsFragment;
import org.wkfg.fragment.NoteFragment;
import org.wkfg.fragment.SettingsFragment;
import org.wkfg.utils.ExchangeInfosWithAli;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2019-10-30 00:18
 */
@Page(anim = CoreAnim.none)
public class ProfileFragment extends BaseFragment implements SuperTextView.OnSuperTextViewClickListener {
//    @BindView(R.id.riv_head_pic)
//    RadiusImageView rivHeadPic;
    @BindView(R.id.menu_settings)
    SuperTextView menuSettings;
//    @BindView(R.id.menu_about)
//    SuperTextView menuAbout;
    @BindView(R.id.menu_note)
    SuperTextView menuNote;
    @BindView(R.id.menu_favorite)
    SuperTextView menuFavor;
    @BindView(R.id.menu_my_threads)
    SuperTextView menuMyThreads;
//    @BindView(R.id.menu_search)
//    SuperTextView menuSearch;

    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        menuNote.setOnSuperTextViewClickListener(this);
        menuFavor.setOnSuperTextViewClickListener(this);
        menuSettings.setOnSuperTextViewClickListener(this);
//        menuAbout.setOnSuperTextViewClickListener(this);
        menuMyThreads.setOnSuperTextViewClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        switch(view.getId()) {
            case R.id.menu_note:
                ExchangeInfosWithAli.LastSeenMessageThreadID = "NULL";
                openNewPage(NoteFragment.class);
                break;
            case R.id.menu_favorite:
                ExchangeInfosWithAli.LastSeenFavorThreadID = "NULL";
                openNewPage(FavorFragment.class);
                break;
            case R.id.menu_settings:
                openNewPage(SettingsFragment.class);
                break;
            case R.id.menu_my_threads:
                ExchangeInfosWithAli.LastSeenMyThreadID = "NULL";
                openNewPage(MyThreadsFragment.class);
                break;
            default:
                break;
        }
    }
}
