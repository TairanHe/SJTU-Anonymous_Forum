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

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
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
import com.xuexiang.templateproject.utils.AnonymousColor;
import com.xuexiang.templateproject.utils.DateHelper;

import java.io.IOException;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.xuexiang.templateproject.utils.HTR_RGBA;
import com.xuexiang.templateproject.utils.MyHandler;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.actionbar.TitleBar;

//import android.support.design.widget.BottomSheetDialog;
import org.json.JSONException;
import org.w3c.dom.Text;

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


    List<String> namelist;
    List<HTR_RGBA> colorlist;

    TextView order;
    String now_order = "0";

    public Random random = new Random();


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
        return R.layout.fragment_floor;
    }



    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        AnonymousName AN = new AnonymousName();
        namelist = AN.getnamelist(LookThroughActivity.anonymousType, LookThroughActivity.randomSeed);
        AnonymousColor AC = new AnonymousColor();
        colorlist = AC.getcolorlist("xui_v1_dark", Integer.parseInt(LookThroughActivity.threadid));

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
                Log.d("Thread post time", LookThroughActivity.threadposttime);
                try {
                    holder.text(R.id.tv_time, DateHelper.getPastTimebyString(LookThroughActivity.threadposttime));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                XToastUtils.toast(LookThroughActivity.anonymousType);
                String name = AnonymousName.getname(namelist,0);
                holder.text(R.id.tv_speaker, name);
//                holder.image(R.id.iv_touxiang, R.drawable.xiaoren_0);
//                Drawable drawable = getResources().getDrawable(R.drawable.avatar_circle);
//                drawable.mutate().setColorFilter(Color.argb(255, 255, 128, 103), PorterDuff.Mode.SRC_IN);
//                R.id.tv_avatar_title.setBackground(drawable);
//                holder.
                TextView title_avatar = holder.findViewById(R.id.tv_avatar_title);
                GradientDrawable background = (GradientDrawable) title_avatar.getBackground();

                HTR_RGBA avatar_color_title = colorlist.get(0 % colorlist.size());
                background.setColor(Color.argb(avatar_color_title.A, avatar_color_title.R, avatar_color_title.G, avatar_color_title.B));

                String [] spString = name.split("\\s+");
                holder.text(R.id.tv_avatar_title,"" + spString[spString.length-1].charAt(0));
//                holder.text(R.id.tv_avatar_title, ""+name.charAt(0));
            }
        };



        //楼层的顺序
        SingleDelegateAdapter orderAdapter = new SingleDelegateAdapter(R.layout.adapter_floor_order_item) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
//                holder.text(R.id.tv_title, "帖子广场");

                order = (TextView) holder.findViewById(R.id.tv_action);
                Random r = new Random();
                if (now_order == "0"){
                    order.setText("最早回复");
                }
                else{
                    order.setText("最晚回复");
                }

//                R.id.tv_action.setOnMenuItemClickListener(this);
                holder.click(R.id.tv_action, v -> showPopup(v));
            }
        };


        //帖子的楼层
        mFloorsAdapter = new SimpleDelegateAdapter<FloorInfo>(R.layout.adapter_thread_floor_view_list_item, new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, FloorInfo model) {
                if (model != null) {
                    holder.text(R.id.tv_floor_id, "#" + model.getFloorID() + "楼");
                    try {
                        holder.text(R.id.tv_time,  DateHelper.getPastTimebyString(model.getRTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String name = AnonymousName.getname(namelist,Integer.parseInt(model.getSpeakername()));
                    if (model.getReplytoname().equals("NULL") || Integer.parseInt(model.getReplytofloor()) < 1)
                    {
                        holder.text(R.id.tv_speaker, name);
                    }
                    else
                    {
                        String replytoname = AnonymousName.getname(namelist,Integer.parseInt(model.getReplytoname()));
                        holder.text(R.id.tv_speaker,  name + " 回复 " + replytoname + " #" + model.getReplytofloor() + "楼" );

                    }

//                    int resID = getResources().getIdentifier("xiaoren_"+ model.getSpeakername(), "drawable", "com.xuexiang.templateproject");
//                    Drawable touxiang = getResources().getDrawable(resID);
//                    holder.image(R.id.iv_touxiang, touxiang);

                    TextView floor_avatar = holder.findViewById(R.id.tv_avatar_floor);
                    GradientDrawable background = (GradientDrawable) floor_avatar.getBackground();
                    HTR_RGBA avatar_color_floor;
                    avatar_color_floor = colorlist.get((Integer.parseInt(model.getSpeakername()) % colorlist.size()));
                    background.setColor(Color.argb(avatar_color_floor.A, avatar_color_floor.R, avatar_color_floor.G, avatar_color_floor.B));


                    String [] spString = name.split("\\s+");
                    holder.text(R.id.tv_avatar_floor,"" + spString[spString.length-1].charAt(0));


                    holder.text(R.id.tv_context, model.getContext());
                    holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                    holder.text(R.id.tv_reply,  "评论");
                    Log.d("Floor", "floorid" + model.getFloorID());
                    Log.d("Floor", "whetherpraise" + model.getWhetherPraise());
                    if(model.getWhetherPraise() == 1)
                    {
                        holder.image(R.id.iv_praise, R.drawable.ic_praise_already_blue);
                    }
//                    为了修复+8楼的莫名其妙的bug
                    if(model.getWhetherPraise() == 0)
                    {
                        holder.image(R.id.iv_praise, R.drawable.ic_praise);
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
                                    holder.image(R.id.iv_praise, R.drawable.ic_praise_already_blue);
                                    model.setPraise(model.getPraise()+1);
                                    holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                                } catch (JSONException | IOException e) {
                                    XToastUtils.toast("请检查网络后重试");
                                    e.printStackTrace();
                                }

                            }
                            else if (model.getWhetherPraise() == 1)
                            {
                                XToastUtils.toast("取消点赞楼层！");
                                FloorFragment.floorid = model.getFloorID();
                                model.setWhetherPraise(0);
                                try {
                                    ExchangeInfosWithAli.CancelPraiseFloor_json(LookThroughActivity.threadid, Integer.parseInt(FloorFragment.floorid));
                                    holder.image(R.id.iv_praise, R.drawable.ic_praise);
                                    model.setPraise(model.getPraise()-1);
                                    holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                                } catch (JSONException | IOException e) {
                                    XToastUtils.toast("请检查网络后重试");
                                    e.printStackTrace();
                                }

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
                                    holder.image(R.id.iv_praise, R.drawable.ic_praise_already_blue);
                                    model.setPraise(model.getPraise()+1);
                                    holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                                } catch (JSONException | IOException e) {
                                    XToastUtils.toast("请检查网络后重试");
                                    e.printStackTrace();
                                }

                            }
                            else if (model.getWhetherPraise() == 1)
                            {
                                XToastUtils.toast("取消点赞楼层！");
                                FloorFragment.floorid = model.getFloorID();
                                model.setWhetherPraise(0);
                                try {
                                    ExchangeInfosWithAli.CancelPraiseFloor_json(LookThroughActivity.threadid, Integer.parseInt(FloorFragment.floorid));
                                    holder.image(R.id.iv_praise, R.drawable.ic_praise);
                                    model.setPraise(model.getPraise()-1);
                                    holder.text(R.id.tv_praise,  "点赞（" + String.valueOf(model.getPraise()) + "）");
                                } catch (JSONException | IOException e) {
                                    XToastUtils.toast("请检查网络后重试");
                                    e.printStackTrace();
                                }

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
        delegateAdapter.addAdapter(orderAdapter);
        delegateAdapter.addAdapter(mFloorsAdapter);

        recyclerView.setAdapter(delegateAdapter);

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu((LookThroughActivity) getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_order, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        popup.show();
    }


    public boolean onMenuItemClick(MenuItem item) {
        Log.d("dyy:", String.valueOf(item.getItemId()));
        //Log.d("dyy:", String.valueOf(R.id.action_privacy));
        switch (item.getItemId()) {
            case R.id.action_early:
                order.setText("最早回复");
//                XToastUtils.toast("最早回复");
                try {
                    ExchangeInfosWithAli.LastSeenFloorID = "NULL";
                    now_order = "0";
                    mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid, now_order));
                } catch (JSONException | IOException e) {
                    XToastUtils.toast("请检查网络后重试");
                    e.printStackTrace();
                }
                refreshLayout.finishRefresh();
                break;
            case R.id.action_late:
                order.setText("最晚回复");
//                XToastUtils.toast("最晚回复");
                try {
                    ExchangeInfosWithAli.LastSeenFloorID = "NULL";
                    now_order = "1";
                    mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid, now_order));
                } catch (JSONException | IOException e) {
                    XToastUtils.toast("请检查网络后重试");
                    e.printStackTrace();
                }
                refreshLayout.finishRefresh();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void initListeners() {

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
//                mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliThread(LookThroughActivity.threadid));
//                try {
//                    ExchangeInfosWithAli.LastSeenFloorID = "NULL";
//                    mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid, now_order));
//                } catch (JSONException | IOException e) {
//                    e.printStackTrace();
//                }
                ExchangeInfosWithAli.LastSeenFloorID = "NULL";
                LookThroughActivity htr = (LookThroughActivity) getActivity();
                Handler handler = new MyHandler.FloorRefreshHandler(mFloorsAdapter, refreshLayout, htr);
                new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            msg.arg1 = 0;
                            msg.obj  = ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid, now_order);
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
//                mFloorsAdapter.refresh(ExchangeInfosWithAli.GetAliThread(LookThroughActivity.threadid));
//                try {
//                    mFloorsAdapter.loadMore(ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid, now_order));
//                } catch (JSONException | IOException e) {
//                    e.printStackTrace();
//                }
                LookThroughActivity htr = (LookThroughActivity) getActivity();
                Handler handler = new MyHandler.FloorLoadMoreHandler(mFloorsAdapter, refreshLayout, htr);
                new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            msg.arg1 = 0;
                            msg.obj  = ExchangeInfosWithAli.GetAliFloor_json(LookThroughActivity.threadid, now_order);
                            if (msg.obj == null){
                                msg.arg1 = -1;
                            }
                        } catch (JSONException | IOException e) {
                            msg.arg1 = 1;
                        }
                        handler.sendMessage(msg);
                    }
                }.start();
//                refreshLayout.finishLoadMore();

            }, 0);
        });

        refreshLayout.autoRefresh(0, 0, 0,false);//第一次进入触发自动刷新，演示效果

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


