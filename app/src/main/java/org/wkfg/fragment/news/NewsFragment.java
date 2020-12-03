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

package org.wkfg.fragment.news;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import org.wkfg.R;
import org.wkfg.activity.LookThroughActivity;
import org.wkfg.activity.MainActivity;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SingleDelegateAdapter;
import org.wkfg.adapter.entity.NewInfo;
import com.xuexiang.templateproject.core.BaseFragment;
import org.wkfg.utils.AnonymousColor;
import org.wkfg.utils.DateHelper;
import org.wkfg.utils.DemoDataProvider;
import org.wkfg.utils.ExchangeInfosWithAli;
import org.wkfg.utils.HTR_RGBA;
import org.wkfg.utils.Utils;
import org.wkfg.utils.MyHandler;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;

/**
 * 首页动态
 *
 * @author xuexiang
 * @since 2019-10-30 00:15
 */
@Page(anim = CoreAnim.none)
public class NewsFragment extends BaseFragment {
//    @BindView(R.id.thread)
//    SuperTextView thread;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private SimpleDelegateAdapter<NewInfo> mNewsAdapter;

    private int block = 0;



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
        return R.layout.fragment_news;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);





//        //轮播条
//        SingleDelegateAdapter bannerAdapter = new SingleDelegateAdapter(R.layout.include_head_view_banner) {
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
//                SimpleImageBanner banner = holder.findViewById(R.id.sib_simple_usage);
//                banner.setSource(DemoDataProvider.getBannerList())
//                        .setOnItemClickListener((view, item, position1) -> XToastUtils.toast("headBanner position--->" + position1)).startScroll();
//            }
//        };

        //九宫格菜单
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setPadding(0, 16, 0, 0);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(0);
        SimpleDelegateAdapter<AdapterItem> commonAdapter = new SimpleDelegateAdapter<AdapterItem>(R.layout.adapter_common_grid_item, gridLayoutHelper, DemoDataProvider.getGridItems(getContext())) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, AdapterItem item) {
                if (item != null) {
                    RadiusImageView imageView = holder.findViewById(R.id.riv_item);
                    imageView.setCircle(true);
                    ImageLoader.get().loadImage(imageView, item.getIcon());
                    holder.text(R.id.tv_title, item.getTitle().toString().substring(0, 1));
                    holder.text(R.id.tv_sub_title, item.getTitle());
                    holder.click(R.id.ll_container, v -> {
//                        点击板块，重设LastSeenThreadID
                        ExchangeInfosWithAli.LastSeenThreadID = "NULL";
                        block = ExchangeInfosWithAli.get_block_id(item.getTitle().toString());
//                        Log.d("dyy", block+"");
//                        XToastUtils.toast("切换到板块hhh：" + item.getTitle());
//                        Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(),"切换到板块：" + item.getTitle(),Snackbar.LENGTH_SHORT);
////                        snackbar.show();


                        Utils.showCoorSnackBar("切换到板块：" + item.getTitle(), getActivity());

                        refreshLayout.autoRefresh(0, 0, 0,false);
                    });
                }
            }
        };

        //资讯的标题
        SingleDelegateAdapter titleAdapter = new SingleDelegateAdapter(R.layout.adapter_title_item) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                holder.text(R.id.tv_title, "帖子广场");
//                holder.text(R.id.tv_action, "更多");
//                holder.click(R.id.tv_action, v -> XToastUtils.toast("更多"));
            }
        };

        //资讯
        mNewsAdapter = new SimpleDelegateAdapter<NewInfo>(R.layout.adapter_news_card_view_list_item, new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, NewInfo model) {
                if (model != null) {
                    Integer strlen = model.getThreadID().length();
                    Integer zerolen = 6 - strlen;
                    String jingThreadID = " #";
                    for (int i = 0; i < zerolen; i++) {
                        jingThreadID = jingThreadID + "0";
                    }
                    jingThreadID = jingThreadID + model.getThreadID();
                    holder.text(R.id.tv_thread_id, jingThreadID);
//                    holder.text(R.id.tv_tag, model.getTag());
                    try {
                        holder.text(R.id.tv_tag,  DateHelper.getPastTimebyString(model.getLastUpdateTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    holder.text(R.id.tv_title, model.getTitle());
                    holder.text(R.id.tv_summary, model.getSummary());
//                    holder.text(R.id.tv_praise, model.getPraise() == 0 ? "点赞" : String.valueOf(model.getPraise()));
//                    holder.text(R.id.tv_comment, model.getComment() == 0 ? "评论" : String.valueOf(model.getComment()));
                    holder.text(R.id.tv_praise,   String.valueOf(model.getPraise()));
                    holder.text(R.id.tv_comment,  String.valueOf(model.getComment()));
                    holder.text(R.id.tv_read, "阅读量 " + model.getRead());
//                    holder.image(R.id.iv_image, model.getImageUrl());

                    Log.d("whethertop", "" + model.getWhetherTop());
                    if (model.getWhetherTop() == 1){
//                        Log.d("flag",  "Enter 1 1 1 11111111");
                        int resID = getResources().getIdentifier("official_blue", "drawable", "com.xuexiang.templateproject");
                        Drawable official = getResources().getDrawable(resID);
//                        holder.image(R.id.iv_top, official);
                        ImageView top = (ImageView) holder.findViewById(R.id.iv_top);
                        top.setImageDrawable(official);
                        top.setAlpha((float) 1);
//                        ImageView hat_view = holder.findViewById(R.id.iv_hat_back);
//                        GradientDrawable background = (GradientDrawable) hat_view.getBackground();
//                        HTR_RGBA avatar_color_title = colorlist.get(position % colorlist.size());
//                        background.setColor(Color.argb(250,41, 158, 227));
                    }
                    else{
//                        Log.d("flag",  "Enter 00 0 0 0 0 0 00 ");
                        ImageView top = (ImageView) holder.findViewById(R.id.iv_top);
                        top.setAlpha((float) 0);
//                        ImageView hat_view = holder.findViewById(R.id.iv_hat_back);
//                        GradientDrawable background = (GradientDrawable) hat_view.getBackground();
//                        HTR_RGBA avatar_color_title = colorlist.get(position % colorlist.size());
//                        background.setColor(Color.argb(avatar_color_title.A, avatar_color_title.R, avatar_color_title.G, avatar_color_title.B));

                    }
                    ImageView hat_view = holder.findViewById(R.id.iv_hat_back);
                    GradientDrawable background = (GradientDrawable) hat_view.getBackground();
                    AnonymousColor AC = new AnonymousColor();
                    List<HTR_RGBA> colorlist = AC.getcolorlist("xui_v1_dark", Integer.parseInt(model.getThreadID()));
                    HTR_RGBA avatar_color_title = colorlist.get(0 % colorlist.size()); //和楼主一致
                    background.setColor(Color.argb(avatar_color_title.A, avatar_color_title.R, avatar_color_title.G, avatar_color_title.B));




//                    hat_view.setBackgroundColor(Color.argb(200,50,30,200));
                    //holder.click(R.id.card_view, v -> Utils.goWeb(getContext(), model.getDetailUrl()));

                    holder.click(R.id.card_view, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            XToastUtils.toast("点击帖子！");
//                            LookThroughActivity.threadid = model.getThreadID();
//                            LookThroughActivity.threadtitle = model.getTitle();
//                            LookThroughActivity.threadsummary = model.getSummary();
//                            LookThroughActivity.threadposttime = model.getPostTime();
////                            LookThroughActivity.praisenum = model.getPraise();
////                            LookThroughActivity.dislikenum = model.getDislike();
//                            LookThroughActivity.anonymousType = model.getAnonymousType();
//                            LookThroughActivity.randomSeed = model.getRandomSeed();

                            Intent intent = new Intent(getActivity(), LookThroughActivity.class);
                            intent.putExtra("ThreadID", model.getThreadID());
                            startActivity(intent);
                        }
                    });
                }
            }
        };

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(commonAdapter);
        delegateAdapter.addAdapter(titleAdapter);
        delegateAdapter.addAdapter(mNewsAdapter);

        recyclerView.setAdapter(delegateAdapter);

    }




    @Override
    protected void initListeners() {

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {

//                mNewsAdapter.refresh(ExchangeInfosWithAli.GetAliRecommandedNewsInfos(block));
                ExchangeInfosWithAli.LastSeenThreadID = "NULL";
                MainActivity htr = (MainActivity) getActivity();
                Handler handler = new MyHandler.ThreadRefreshHandler(mNewsAdapter, refreshLayout, htr);
                new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            msg.arg1 = 0;
                            msg.obj  = ExchangeInfosWithAli.GetAliRecommandedNewsInfos_json(block);
                            if (msg.obj == null){
                                msg.arg1 = -1;
                            }
                        } catch (JSONException | IOException e) {
                            msg.arg1 = 1;
                        }
                        handler.sendMessage(msg);
                    }
                }.start();
            }, 0);
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
//                mNewsAdapter.loadMore(ExchangeInfosWithAli.GetAliRecommandedNewsInfos(block));
//                try {
//                    mNewsAdapter.loadMore(ExchangeInfosWithAli.GetAliRecommandedNewsInfos_json(block));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                MainActivity htr = (MainActivity) getActivity();
                Handler handler = new MyHandler.ThreadLoadMoreHandler(mNewsAdapter, refreshLayout, htr);
                new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            msg.arg1 = 0;
                            msg.obj  = ExchangeInfosWithAli.GetAliRecommandedNewsInfos_json(block);
                            if (msg.obj == null){
                                msg.arg1 = -1;
                            }
                        } catch (JSONException | IOException e) {
                            msg.arg1 = 1;
                        }
                        handler.sendMessage(msg);
                    }
                }.start();
            }, 0);
        });


//        Handler handler = new MyHandler.AutoRefreshHandler(refreshLayout);
//        new Thread() {
//            @Override
//            public void run() {
//                Message msg = new Message();
//                try {
//                    msg.arg1 = 0;
//                    msg.obj  = ExchangeInfosWithAli.GetAliRecommandedNewsInfos_json(block);
//                } catch (JSONException | IOException e) {
//                    msg.arg1 = 1;
//                }
//                handler.sendMessage(msg);
//            }
//        }.start();


        refreshLayout.autoRefresh(0, 0, 0,false);//第一次进入触发自动刷新，演示效果

        //thread.setOnSuperTextViewClickListener(this);
    }

//    @SingleClick
//    public void onClick(SuperTextView view) {
//        switch(view.getId()) {
//            case R.id.thread:
//                openNewPage(ThreadFragment.class);
//                break;
//            default:
//                break;
//        }
//    }
}
