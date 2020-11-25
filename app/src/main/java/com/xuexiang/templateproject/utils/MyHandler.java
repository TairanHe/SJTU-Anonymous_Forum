package com.xuexiang.templateproject.utils;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.entity.FloorInfo;
import com.xuexiang.templateproject.adapter.entity.MessageInfo;
import com.xuexiang.templateproject.adapter.entity.NewInfo;

import java.util.List;

public abstract class MyHandler {


    public static class ThreadRefreshHandler extends Handler {
        SimpleDelegateAdapter<NewInfo> adapter;
        RefreshLayout layout;

        public ThreadRefreshHandler(SimpleDelegateAdapter<NewInfo> adapter, RefreshLayout layout) {
            this.adapter = adapter;
            this.layout = layout;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.refresh(((List<NewInfo>) msg.obj));
                layout.finishRefresh();
            }
            else if (msg.arg1 == -1){
                XToastUtils.toast("没有更多啦");
                layout.finishRefresh();
            }
            else {
                XToastUtils.toast("请检查网络后重试");
                layout.finishRefresh();
            }

        }
    }


    public static class ThreadLoadMoreHandler extends Handler {
        SimpleDelegateAdapter<NewInfo> adapter;
        RefreshLayout layout;

        public ThreadLoadMoreHandler(SimpleDelegateAdapter<NewInfo> adapter, RefreshLayout layout) {
            this.adapter = adapter;
            this.layout = layout;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.loadMore((List<NewInfo>) msg.obj);
                layout.finishLoadMore();
            }
            else if (msg.arg1 == -1){
                XToastUtils.toast("没有更多啦");
                layout.finishLoadMore();
            }
            else {
                XToastUtils.toast("请检查网络后重试");
                layout.finishLoadMore();
            }

        }
    }

    public static class MessageRefreshHandler extends Handler {
        SimpleDelegateAdapter<MessageInfo> adapter;
        RefreshLayout layout;

        public MessageRefreshHandler(SimpleDelegateAdapter<MessageInfo> adapter, RefreshLayout layout) {
            this.adapter = adapter;
            this.layout = layout;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.refresh(((List<MessageInfo>) msg.obj));
                layout.finishRefresh();
            }
            else if (msg.arg1 == -1){
                XToastUtils.toast("没有更多啦");
                layout.finishRefresh();
            }
            else {
                XToastUtils.toast("请检查网络后重试");
                layout.finishRefresh();
            }

        }
    }


    public static class MessageLoadMoreHandler extends Handler {
        SimpleDelegateAdapter<MessageInfo> adapter;
        RefreshLayout layout;

        public MessageLoadMoreHandler(SimpleDelegateAdapter<MessageInfo> adapter, RefreshLayout layout) {
            this.adapter = adapter;
            this.layout = layout;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.loadMore((List<MessageInfo>) msg.obj);
                layout.finishLoadMore();
            }
            else if (msg.arg1 == -1){
                XToastUtils.toast("没有更多啦");
                layout.finishLoadMore();
            }
            else {
                XToastUtils.toast("请检查网络后重试");
                layout.finishLoadMore();
            }

        }
    }

    public static class FloorRefreshHandler extends Handler {
        SimpleDelegateAdapter<FloorInfo> adapter;
        RefreshLayout layout;

        public FloorRefreshHandler(SimpleDelegateAdapter<FloorInfo> adapter, RefreshLayout layout) {
            this.adapter = adapter;
            this.layout = layout;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.refresh(((List<FloorInfo>) msg.obj));
                layout.finishRefresh();
            }
            else if (msg.arg1 == -1){
                XToastUtils.toast("没有更多啦");
                layout.finishRefresh();
            }
            else {
//                XToastUtils.toast("请检查网络后重试");
                layout.finishRefresh();
            }

        }
    }

    public static class FloorLoadMoreHandler extends Handler {
        SimpleDelegateAdapter<FloorInfo> adapter;
        RefreshLayout layout;

        public FloorLoadMoreHandler(SimpleDelegateAdapter<FloorInfo> adapter, RefreshLayout layout) {
            this.adapter = adapter;
            this.layout = layout;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.loadMore((List<FloorInfo>) msg.obj);
                layout.finishLoadMore();
            }
            else if (msg.arg1 == -1){
                XToastUtils.toast("没有更多啦");
                layout.finishLoadMore();
            }
            else
            {
                XToastUtils.toast("请检查网络后重试");
                layout.finishLoadMore();
            }

        }
    }

//    public static class AutoRefreshHandler extends Handler {
//        SimpleDelegateAdapter<NewInfo> adapter;
//        RefreshLayout layout;
//        View v;
//
//        public AutoRefreshHandler(RefreshLayout layout) {
//            this.layout = layout;
//        }
//        @Override
//        public void handleMessage(Message msg) {
//            if(msg.arg1 == 0){
//                layout.autoRefresh(0, 0, 0,false);
//            }
//            else {
//                XToastUtils.toast("请检查网络后重试");
//            }
//
//        }
//    }
}
