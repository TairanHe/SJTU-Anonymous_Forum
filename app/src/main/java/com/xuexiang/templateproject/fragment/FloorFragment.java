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

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.LookThroughActivity;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SingleDelegateAdapter;
import com.xuexiang.templateproject.adapter.entity.FloorInfo;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.ExchangeInfosWithAli;
import com.xuexiang.templateproject.utils.AnonymousName;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.actionbar.TitleBar;

//import android.support.design.widget.BottomSheetDialog;
import org.json.JSONException;

import butterknife.BindView;


//import com.moos.example.adapter.CommentExpandAdapter;
//import com.moos.example.bean.CommentBean;
//import com.moos.example.bean.CommentDetailBean;
//import com.moos.example.bean.ReplyDetailBean;
//import com.moos.example.view.CommentExpandableListView;

/**
 * 首页动态
 *
 * @author xuexiang
 * @since 2019-10-30 00:15
 */
@Page(anim = CoreAnim.none)
public class FloorFragment extends BaseFragment{
    public static String floorid;

    //    @BindView(R.id.thread)
//    SuperTextView thread;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private SimpleDelegateAdapter<FloorInfo> mFloorsAdapter;

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
        //findViewById(R.layout.reply_item);


        //帖子的标题
        SingleDelegateAdapter titleAdapter = new SingleDelegateAdapter(R.layout.dyytitle) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                holder.text(R.id.tv_dyytitle, LookThroughActivity.threadtitle);
                holder.text(R.id.tv_context, LookThroughActivity.threadsummary);
                holder.text(R.id.tv_time, LookThroughActivity.threadposttime);
                holder.text(R.id.tv_speaker, "Adam");
                holder.image(R.id.iv_touxiang, R.drawable.xiaoren_1);

//                holder.text(R.id.tv_action, "更多");
//                holder.click(R.id.tv_action, v -> XToastUtils.toast("更多"));
            }
        };

        //帖子的楼层
        mFloorsAdapter = new SimpleDelegateAdapter<FloorInfo>(R.layout.adapter_thread_floor_view_list_item, new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, FloorInfo model) {
                if (model != null) {
                    holder.text(R.id.tv_floor_id, "#" + model.getFloorID() + "楼");
                    if (model.getReplytoname().equals("NULL") || Integer.parseInt(model.getReplytofloor()) < 1)
                    {
                        holder.text(R.id.tv_speaker, AnonymousName.getname(model.getSpeakername()));
                    }
                    else
                    {
                        holder.text(R.id.tv_speaker, AnonymousName.getname(model.getSpeakername())+" 回复 " + AnonymousName.getname(model.getReplytoname()));

                    }

                    int resID = getResources().getIdentifier("xiaoren_"+model.getSpeakername(), "drawable", "com.xuexiang.templateproject");
                    Drawable touxiang = getResources().getDrawable(resID);
                    holder.image(R.id.iv_touxiang, touxiang);

                    holder.text(R.id.tv_context, model.getContext());
                    holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                    holder.text(R.id.tv_reply,  "评论");
                    if(model.getWhetherPraise() == 1)
                    {
                        holder.image(R.id.iv_praise, R.drawable.ic_praise_already_blue);
                    }

//                    holder.text(R.id.tv_read, "阅读量 " + model.getRead());
//                    holder.image(R.id.iv_image, model.getImageUrl());

                    //holder.click(R.id.card_view, v -> Utils.goWeb(getContext(), model.getDetailUrl()));
                    holder.click(R.id.tv_reply, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(getActivity(), LookThroughActivity.class);
//                            startActivity(intent);
                            XToastUtils.toast("回复楼层！");
                            FloorFragment.floorid = model.getFloorID();
//                            LookThroughActivity.threadtilte = model.getTitle();
//                            getContext().showReplyDialog(3);
                            LookThroughActivity htr = (LookThroughActivity) getActivity();
                            htr.showReplyDialog(Integer.parseInt(FloorFragment.floorid));
                        }
                    });
                    holder.click(R.id.iv_reply, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(getActivity(), LookThroughActivity.class);
//                            startActivity(intent);
                            XToastUtils.toast("回复楼层！");
                            FloorFragment.floorid = model.getFloorID();
//                            LookThroughActivity.threadtilte = model.getTitle();
//                            getContext().showReplyDialog(3);
                            LookThroughActivity htr = (LookThroughActivity) getActivity();
                            htr.showReplyDialog(Integer.parseInt(FloorFragment.floorid));
                        }
                    });
                    holder.click(R.id.tv_praise, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(getActivity(), LookThroughActivity.class);
//                            startActivity(intent);

                            if (model.getWhetherPraise() == 0)
                            {
                                XToastUtils.toast("点赞楼层！");
                                FloorFragment.floorid = model.getFloorID();
                                model.setWhetherPraise(1);
                                try {
                                    ExchangeInfosWithAli.PraiseFloor_json(LookThroughActivity.threadid, Integer.parseInt(FloorFragment.floorid));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                holder.image(R.id.iv_praise, R.drawable.ic_praise_already_blue);
                                model.setPraise(model.getPraise()+1);
                                holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                            }
                            else if (model.getWhetherPraise() == 1)
                            {
                                XToastUtils.toast("取消点赞楼层！");
                                FloorFragment.floorid = model.getFloorID();
                                model.setWhetherPraise(0);
                                try {
                                    ExchangeInfosWithAli.CancelPraiseFloor_json(LookThroughActivity.threadid, Integer.parseInt(FloorFragment.floorid));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                holder.image(R.id.iv_praise, R.drawable.ic_praise);
                                model.setPraise(model.getPraise()-1);
                                holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                            }
                        }
                    });
                    holder.click(R.id.iv_praise, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(getActivity(), LookThroughActivity.class);
//                            startActivity(intent);

                            if (model.getWhetherPraise() == 0)
                            {
                                XToastUtils.toast("点赞楼层！");
                                FloorFragment.floorid = model.getFloorID();
                                model.setWhetherPraise(1);
                                try {
                                    ExchangeInfosWithAli.PraiseFloor_json(LookThroughActivity.threadid, Integer.parseInt(FloorFragment.floorid));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                holder.image(R.id.iv_praise, R.drawable.ic_praise_already_blue);
                                model.setPraise(model.getPraise()+1);
                                holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                            }
                            else if (model.getWhetherPraise() == 1)
                            {
                                XToastUtils.toast("取消点赞楼层！");
                                FloorFragment.floorid = model.getFloorID();
                                model.setWhetherPraise(0);
                                try {
                                    ExchangeInfosWithAli.CancelPraiseFloor_json(LookThroughActivity.threadid, Integer.parseInt(FloorFragment.floorid));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                holder.image(R.id.iv_praise, R.drawable.ic_praise);
                                model.setPraise(model.getPraise()-1);
                                holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                            }
                        }
                    });
                }
            }
        };

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //delegateAdapter.addAdapter(bannerAdapter);
//        delegateAdapter.addAdapter(commonAdapter);
        delegateAdapter.addAdapter(titleAdapter);
        delegateAdapter.addAdapter(mFloorsAdapter);

        recyclerView.setAdapter(delegateAdapter);

    }

    @Override
    protected void initListeners() {

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
//                mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliThread(LookThroughActivity.threadid));
                try {
                    ExchangeInfosWithAli.LastSeenFloorID = "NULL";
                    mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshLayout.finishRefresh();
                LookThroughActivity htr = (LookThroughActivity) getActivity();
                htr.checkthreebuttons();
            }, 400);
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
//                mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliThread(LookThroughActivity.threadid));
                try {
                    mFloorsAdapter.loadMore(ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshLayout.finishLoadMore();
                LookThroughActivity htr = (LookThroughActivity) getActivity();
                htr.checkthreebuttons();
            }, 400);
        });

        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        //thread.setOnSuperTextViewClickListener(this);
    }






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


