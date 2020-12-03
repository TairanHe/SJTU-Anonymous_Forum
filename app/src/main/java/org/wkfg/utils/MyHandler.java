package org.wkfg.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import org.wkfg.activity.LookThroughActivity;
import org.wkfg.activity.MainActivity;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import org.wkfg.adapter.entity.FloorInfo;
import org.wkfg.adapter.entity.MessageInfo;
import org.wkfg.adapter.entity.NewInfo;

import java.util.List;

public abstract class MyHandler {


    public static class ThreadRefreshHandler extends Handler {
        SimpleDelegateAdapter<NewInfo> adapter;
        RefreshLayout layout;
        MainActivity mainActivity;

        public ThreadRefreshHandler(SimpleDelegateAdapter<NewInfo> adapter, RefreshLayout layout, MainActivity mainActivity) {
            this.adapter = adapter;
            this.layout = layout;
            this.mainActivity = mainActivity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.refresh(((List<NewInfo>) msg.obj));
                layout.finishRefresh();
            }
            else if (msg.arg1 == -1){
                Utils.showCoorSnackBar("没有更多啦", mainActivity);
                layout.finishRefresh();
            }
            else {
                Utils.showCoorSnackBar("请检查网络后重试", mainActivity);
                layout.finishRefresh();
            }

        }
    }


    public static class ThreadLoadMoreHandler extends Handler {
        SimpleDelegateAdapter<NewInfo> adapter;
        RefreshLayout layout;
        MainActivity mainActivity;

        public ThreadLoadMoreHandler(SimpleDelegateAdapter<NewInfo> adapter, RefreshLayout layout, MainActivity mainActivity) {
            this.adapter = adapter;
            this.layout = layout;
            this.mainActivity = mainActivity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.loadMore((List<NewInfo>) msg.obj);
                layout.finishLoadMore();
            }
            else if (msg.arg1 == -1){
                Utils.showCoorSnackBar("没有更多啦", mainActivity);
                layout.finishLoadMore();
            }
            else {
                Utils.showCoorSnackBar("请检查网络后重试", mainActivity);
                layout.finishLoadMore();
            }

        }
    }

    public static class ThreadNoMainRefreshHandler extends Handler {
        SimpleDelegateAdapter<NewInfo> adapter;
        RefreshLayout layout;
        Activity activity;

        public ThreadNoMainRefreshHandler(SimpleDelegateAdapter<NewInfo> adapter, RefreshLayout layout, Activity activity) {
            this.adapter = adapter;
            this.layout = layout;
            this.activity = activity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.refresh(((List<NewInfo>) msg.obj));
                layout.finishRefresh();
            }
            else if (msg.arg1 == -1){
                Utils.showSnackBar("没有更多啦", activity);
                layout.finishRefresh();
            }
            else {
                Utils.showSnackBar("请检查网络后重试", activity);
                layout.finishRefresh();
            }

        }
    }


    public static class ThreadNoMainLoadMoreHandler extends Handler {
        SimpleDelegateAdapter<NewInfo> adapter;
        RefreshLayout layout;
        Activity activity;

        public ThreadNoMainLoadMoreHandler(SimpleDelegateAdapter<NewInfo> adapter, RefreshLayout layout, Activity activity) {
            this.adapter = adapter;
            this.layout = layout;
            this.activity = activity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.loadMore((List<NewInfo>) msg.obj);
                layout.finishLoadMore();
            }
            else if (msg.arg1 == -1){
                Utils.showSnackBar("没有更多啦", activity);
                layout.finishLoadMore();
            }
            else {
                Utils.showSnackBar("请检查网络后重试", activity);
                layout.finishLoadMore();
            }

        }
    }

    public static class MessageRefreshHandler extends Handler {
        SimpleDelegateAdapter<MessageInfo> adapter;
        RefreshLayout layout;
        Activity activity;

        public MessageRefreshHandler(SimpleDelegateAdapter<MessageInfo> adapter, RefreshLayout layout, Activity activity) {
            this.adapter = adapter;
            this.layout = layout;
            this.activity = activity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.refresh(((List<MessageInfo>) msg.obj));
                layout.finishRefresh();
            }
            else if (msg.arg1 == -1){
                Utils.showSnackBar("没有更多啦", activity);
                layout.finishRefresh();
            }
            else {
                Utils.showSnackBar("请检查网络后重试", activity);
                layout.finishRefresh();
            }

        }
    }


    public static class MessageLoadMoreHandler extends Handler {
        SimpleDelegateAdapter<MessageInfo> adapter;
        RefreshLayout layout;
        Activity activity;

        public MessageLoadMoreHandler(SimpleDelegateAdapter<MessageInfo> adapter, RefreshLayout layout, Activity activity) {
            this.adapter = adapter;
            this.layout = layout;
            this.activity = activity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.loadMore((List<MessageInfo>) msg.obj);
                layout.finishLoadMore();

            }
            else if (msg.arg1 == -1){
                Utils.showSnackBar("没有更多啦", activity);
                layout.finishLoadMore();
            }
            else {
                Utils.showSnackBar("请检查网络后重试", activity);
                layout.finishLoadMore();
            }

        }
    }

    public static class FloorRefreshHandler extends Handler {
        SimpleDelegateAdapter<FloorInfo> adapter;
        RefreshLayout layout;
        LookThroughActivity lookThroughActivity;

        public FloorRefreshHandler(SimpleDelegateAdapter<FloorInfo> adapter, RefreshLayout layout, LookThroughActivity lookThroughActivity) {
            this.adapter = adapter;
            this.layout = layout;
            this.lookThroughActivity = lookThroughActivity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.refresh(((List<FloorInfo>) msg.obj));
                layout.finishRefresh();
                lookThroughActivity.checkthreebuttons();
            }
            else if (msg.arg1 == -1){
                Utils.showFloorSnackBar("没有更多啦", lookThroughActivity);
                layout.finishRefresh();
                lookThroughActivity.checkthreebuttons();
            }
            else {
                Utils.showFloorSnackBar("请检查网络后重试", lookThroughActivity);
                layout.finishRefresh();
            }

        }
    }

    public static class FloorLoadMoreHandler extends Handler {
        SimpleDelegateAdapter<FloorInfo> adapter;
        RefreshLayout layout;
        LookThroughActivity lookThroughActivity;

        public FloorLoadMoreHandler(SimpleDelegateAdapter<FloorInfo> adapter, RefreshLayout layout, LookThroughActivity lookThroughActivity) {
            this.adapter = adapter;
            this.layout = layout;
            this.lookThroughActivity = lookThroughActivity;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                adapter.loadMore((List<FloorInfo>) msg.obj);
                layout.finishLoadMore();
                lookThroughActivity.checkthreebuttons();
            }
            else if (msg.arg1 == -1){
                Utils.showFloorSnackBar("没有更多啦", lookThroughActivity);
                layout.finishLoadMore();
                lookThroughActivity.checkthreebuttons();
            }
            else
            {
                Utils.showFloorSnackBar("请检查网络后重试", lookThroughActivity);
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
//                Snackbar snackbar = Snackbar.make(view,"请检查网络后重试",Snackbar.LENGTH_SHORT);
//                    snackbar.show()
//            }
//
//        }
//    }
}
