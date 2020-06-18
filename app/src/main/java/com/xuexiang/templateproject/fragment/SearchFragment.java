package com.xuexiang.templateproject.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.LookThroughActivity;
import com.xuexiang.templateproject.activity.SearchActivity;
import com.xuexiang.templateproject.activity.SplashActivity;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SingleDelegateAdapter;
import com.xuexiang.templateproject.adapter.entity.NewInfo;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.ExchangeInfosWithAli;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;

/**
 * 首页动态
 *
 * @author xuexiang
 * @since 2019-10-30 00:15
 */
@Page(anim = CoreAnim.none, name = "搜索结果")
public class SearchFragment extends BaseFragment {
    //    @BindView(R.id.thread)
//    SuperTextView thread;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private SimpleDelegateAdapter<NewInfo> mNewsAdapter;

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
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
                holder.text(R.id.tv_title, "搜索列表");
                holder.text(R.id.tv_action, "更多");
                holder.click(R.id.tv_action, v -> XToastUtils.toast("更多"));
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
                    holder.text(R.id.tv_tag, model.getTag());
                    holder.text(R.id.tv_title, model.getTitle());
                    holder.text(R.id.tv_summary, model.getSummary());
                    holder.text(R.id.tv_praise, model.getPraise() == 0 ? "点赞" : String.valueOf(model.getPraise()));
                    holder.text(R.id.tv_comment, model.getComment() == 0 ? "评论" : String.valueOf(model.getComment()));
                    holder.text(R.id.tv_read, "阅读量 " + model.getRead());
//                    holder.image(R.id.iv_image, model.getImageUrl());

                    //holder.click(R.id.card_view, v -> Utils.goWeb(getContext(), model.getDetailUrl()));
                    holder.click(R.id.card_view, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LookThroughActivity.threadid = model.getThreadID();
                            LookThroughActivity.threadtitle = model.getTitle();
                            LookThroughActivity.threadsummary = model.getSummary();
                            LookThroughActivity.threadposttime = model.getLastUpdateTime();
                            LookThroughActivity.praisenum = model.getPraise();
                            LookThroughActivity.dislikenum = model.getDislike();
                            Intent intent = new Intent(getActivity(), LookThroughActivity.class);
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
                Log.d("dyy",SearchActivity.queryString);
                mNewsAdapter.refresh(ExchangeInfosWithAli.query(SearchActivity.queryString));
                refreshLayout.finishRefresh();
            }, 1000);
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().postDelayed(() -> {
                Log.d("dyy",SearchActivity.queryString);
                mNewsAdapter.refresh(ExchangeInfosWithAli.query(SearchActivity.queryString));
                refreshLayout.finishLoadMore();
            }, 1000);
        });
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

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
