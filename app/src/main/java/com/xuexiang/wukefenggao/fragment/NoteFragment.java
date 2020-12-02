package com.xuexiang.wukefenggao.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.wukefenggao.R;
import com.xuexiang.wukefenggao.activity.LookThroughActivity;
import com.xuexiang.wukefenggao.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.wukefenggao.adapter.base.delegate.SingleDelegateAdapter;
import com.xuexiang.wukefenggao.adapter.entity.MessageInfo;
import com.xuexiang.wukefenggao.core.BaseFragment;
import com.xuexiang.wukefenggao.utils.AnonymousColor;
import com.xuexiang.wukefenggao.utils.ExchangeInfosWithAli;
import com.xuexiang.wukefenggao.utils.HTR_RGBA;
import com.xuexiang.wukefenggao.utils.MyHandler;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

/**
 * 首页动态
 *
 * @author xuexiang
 * @since 2019-10-30 00:15
 */
@Page(anim = CoreAnim.none, name = "消息通知")
public class NoteFragment extends BaseFragment {
    //    @BindView(R.id.thread)
//    SuperTextView thread;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private SimpleDelegateAdapter<MessageInfo> mNewsAdapter;


//    /**
//     * @return 返回为 null意为不需要导航栏
//     */
//    @Override
//    protected TitleBar initTitle() {
//        return null;
//    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_note;
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

//        //九宫格菜单
//        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
//        gridLayoutHelper.setPadding(0, 16, 0, 0);
//        gridLayoutHelper.setVGap(10);
//        gridLayoutHelper.setHGap(0);
//        SimpleDelegateAdapter<AdapterItem> commonAdapter = new SimpleDelegateAdapter<AdapterItem>(R.layout.adapter_common_grid_item, gridLayoutHelper, DemoDataProvider.getGridItems(getContext())) {
//            @Override
//            protected void bindData(@NonNull RecyclerViewHolder holder, int position, AdapterItem item) {
//                if (item != null) {
//                    RadiusImageView imageView = holder.findViewById(R.id.riv_item);
//                    imageView.setCircle(true);
//                    ImageLoader.get().loadImage(imageView, item.getIcon());
//                    holder.text(R.id.tv_title, item.getTitle().toString().substring(0, 1));
//                    holder.text(R.id.tv_sub_title, item.getTitle());
//
//                    holder.click(R.id.ll_container, v -> XToastUtils.toast("点击了：" + item.getTitle()));
//                }
//            }
//        };

        //资讯的标题
        SingleDelegateAdapter titleAdapter = new SingleDelegateAdapter(R.layout.adapter_title_item) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                holder.text(R.id.tv_title, "消息列表");
//                holder.text(R.id.tv_action, "更多");
//                holder.click(R.id.tv_action, v -> XToastUtils.toast("更多"));
            }
        };

        //资讯
        mNewsAdapter = new SimpleDelegateAdapter<MessageInfo>(R.layout.adapter_note_card_view_list_item, new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, MessageInfo model) {
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
                    holder.text(R.id.tv_tag, model.getJudge() == 0 ? "未读" : "已读"); // 这里设置已读未读
                    if(model.getJudge() == 0)
                    {
                        holder.image(R.id.iv_judge, R.drawable.dian_blue);
                    }
                    else {
                        holder.image(R.id.iv_judge, R.drawable.dian_gray);
                    }
                    holder.text(R.id.tv_title, model.getTitle());
                    int type = model.getType();
                    holder.text(R.id.tv_note, model.getType() == 0 ? "有人悄悄回复了你！" : "有" + type + "人悄悄点赞了你！");
//                    holder.text(R.id.tv_praise, model.getPraise() == 0 ? "点赞" : String.valueOf(model.getPraise()));
//                    holder.text(R.id.tv_comment, model.getComment() == 0 ? "评论" : String.valueOf(model.getComment()));
//                    holder.text(R.id.tv_read, "阅读量 " + model.getRead());
//                    holder.image(R.id.iv_image, model.getImageUrl());

                    ImageView hat_view = holder.findViewById(R.id.iv_hat_back);
                    GradientDrawable background_avatar = (GradientDrawable) hat_view.getBackground();
                    AnonymousColor AC = new AnonymousColor();
                    List<HTR_RGBA> colorlist = AC.getcolorlist("xui_v1_dark", Integer.parseInt(model.getThreadID()));
                    HTR_RGBA avatar_color_title = colorlist.get(0 % colorlist.size()); //和楼主一致
                    background_avatar.setColor(Color.argb(avatar_color_title.A, avatar_color_title.R, avatar_color_title.G, avatar_color_title.B));

                    //holder.click(R.id.card_view, v -> Utils.goWeb(getContext(), model.getDetailUrl()));
                    holder.click(R.id.card_view, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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
        //delegateAdapter.addAdapter(commonAdapter);
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
                //ExchangeInfosWithAli.NumOfQuery = 0;
//                try {
//                    ExchangeInfosWithAli.LastSeenMessageThreadID = "NULL";
//                    mNewsAdapter.refresh(ExchangeInfosWithAli.GetMessageList_json());
//                } catch (JSONException | IOException e) {
//                    e.printStackTrace();
//                }
//                refreshLayout.finishRefresh();
                ExchangeInfosWithAli.LastSeenMessageThreadID = "NULL";
                Activity htr = getActivity();
                Handler handler = new MyHandler.MessageRefreshHandler(mNewsAdapter, refreshLayout, htr);
                new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            msg.arg1 = 0;
                            msg.obj  =ExchangeInfosWithAli.GetMessageList_json();
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
//                try {
//                    mNewsAdapter.loadMore(ExchangeInfosWithAli.GetMessageList_json());
//                } catch (JSONException | IOException e) {
//                    e.printStackTrace();
//                }
//                refreshLayout.finishLoadMore();
                Activity htr = getActivity();
                Handler handler = new MyHandler.MessageLoadMoreHandler(mNewsAdapter, refreshLayout, htr);
                new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            msg.arg1 = 0;
                            msg.obj  =ExchangeInfosWithAli.GetMessageList_json();
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
