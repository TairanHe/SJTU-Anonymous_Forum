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

package com.xuexiang.templateproject.activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.commonsdk.debug.E;
import com.xuexiang.templateproject.utils.ExchangeInfosWithAli;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.LinearLayout;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.utils.XToastUtils;

import org.json.JSONException;

public class LookThroughActivity extends AppCompatActivity implements View.OnClickListener{
    public static String threadid;
    public static String threadtitle;
    public static String threadsummary;
    public static String threadposttime;
    public static int favournum;
    public static int praisenum;
    public static int dislikenum;

    private TextView bt_comment;
    private ImageView bt_favor;
    private ImageView bt_praise;
    private ImageView bt_tread;

    private TextView praise_num;
    private TextView tread_num;
    private TextView favor_num;
    private BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_through);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        initView();

    }

    private void initView() {
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);

        bt_favor = (ImageView) findViewById(R.id.iv_favor_thread);
        bt_favor.setOnClickListener(this);

        bt_praise = (ImageView) findViewById(R.id.iv_praise_thread);
        bt_praise.setOnClickListener(this);

        bt_tread = (ImageView) findViewById(R.id.iv_tread_thread);
        bt_tread.setOnClickListener(this);

        favor_num = (TextView) findViewById(R.id.tv_favor_thread);
        praise_num = (TextView) findViewById(R.id.tv_praise_thread);
        tread_num = (TextView) findViewById(R.id.tv_tread_thread);
        favor_num.setText("");
        praise_num.setText(praisenum + "");
        tread_num.setText(dislikenum + "");
//        if (ExchangeInfosWithAli.WhetherFavour == 1)
//        {
//            XToastUtils.toast("此贴已收藏！");
//            bt_favor.setImageDrawable(getResources().getDrawable(R.drawable.ic_favor_already));
//        }
//
//        if (ExchangeInfosWithAli.WhetherPraise == 1)
//        {
//            XToastUtils.toast("此贴已点赞！");
//            bt_praise.setImageDrawable(getResources().getDrawable(R.drawable.ic_praise_already));
//        }
//        else if (ExchangeInfosWithAli.WhetherPraise == -1)
//        {
//            XToastUtils.toast("此贴已点踩！");
//            bt_tread.setImageDrawable(getResources().getDrawable(R.drawable.ic_tread_already));
//        }

//        bt_reply_image = (ImageView) findViewById(R.id.iv_focus_thread);
//        bt_reply_image.setOnClickListener(this);
//        bt_reply_text = (TextView) findViewById(R.id.tv_reply);
//        bt_reply_text.setOnClickListener(this);
    }

    public void checkthreebuttons(){
        Log.d("dyy", ExchangeInfosWithAli.WhetherFavour+ "-Favour");
        Log.d("dyy", ExchangeInfosWithAli.WhetherPraise+ "-Praise");
        if (ExchangeInfosWithAli.WhetherFavour == 1)
        {
            //XToastUtils.toast("此贴已收藏！");
            bt_favor.setImageDrawable(getResources().getDrawable(R.drawable.ic_favor_already));
        }else{
            bt_favor.setImageDrawable(getResources().getDrawable(R.drawable.icon_collect_3));
        }

        if (ExchangeInfosWithAli.WhetherPraise == 1)
        {
            //XToastUtils.toast("此贴已点赞！");
            bt_praise.setImageDrawable(getResources().getDrawable(R.drawable.ic_praise_already));
        }
        else if (ExchangeInfosWithAli.WhetherPraise == -1)
        {
            //XToastUtils.toast("此贴已点踩！");
            bt_tread.setImageDrawable(getResources().getDrawable(R.drawable.ic_tread_already));
        }else{
            bt_tread.setImageDrawable(getResources().getDrawable(R.drawable.ic_tread_black));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.detail_page_do_comment){

            showCommentDialog();
        }
        if(view.getId() == R.id.iv_favor_thread) {
            if (ExchangeInfosWithAli.WhetherFavour == 0)
            {
                XToastUtils.toast("点击收藏！");
                bt_favor.setImageDrawable(getResources().getDrawable(R.drawable.ic_favor_already));
                try {
                    ExchangeInfosWithAli.FavourThread_json(LookThroughActivity.threadid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ExchangeInfosWithAli.WhetherFavour = 1;
            }
            else
            {

                XToastUtils.toast("取消收藏！");
                bt_favor.setImageDrawable(getResources().getDrawable(R.drawable.icon_collect_3));
                try {
                    ExchangeInfosWithAli.CancelFavourThread_json(LookThroughActivity.threadid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ExchangeInfosWithAli.WhetherFavour = 0;
            }

        }

        if(view.getId() == R.id.iv_praise_thread) {
            if (ExchangeInfosWithAli.WhetherPraise == 0)
            {
                XToastUtils.toast("点赞帖子！");
                bt_praise.setImageDrawable(getResources().getDrawable(R.drawable.ic_praise_already));
                ExchangeInfosWithAli.WhetherPraise = 1;
                try {
                    ExchangeInfosWithAli.PraiseThread_json(LookThroughActivity.threadid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                praise_num.setText(String.valueOf(Integer.parseInt(praise_num.getText().toString())+1));
            }
            else if (ExchangeInfosWithAli.WhetherPraise == 1)
            {
                XToastUtils.toast("取消点赞帖子！");
                bt_praise.setImageDrawable(getResources().getDrawable(R.drawable.ic_praise_black));
                ExchangeInfosWithAli.WhetherPraise = 0;
                try {
                    ExchangeInfosWithAli.CancelPraiseThread_json(LookThroughActivity.threadid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                praise_num.setText(String.valueOf(Integer.parseInt(praise_num.getText().toString())-1));
            }
            else if (ExchangeInfosWithAli.WhetherPraise == -1)
            {
                XToastUtils.toast("不能同时点赞点踩哦！");
            }
        }
        if(view.getId() == R.id.iv_tread_thread) {
            if (ExchangeInfosWithAli.WhetherPraise == 0)
            {
                XToastUtils.toast("点踩帖子！");
                bt_tread.setImageDrawable(getResources().getDrawable(R.drawable.ic_tread_already));
                ExchangeInfosWithAli.WhetherPraise = -1;
                try {
                    ExchangeInfosWithAli.DislikeThread_json(LookThroughActivity.threadid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tread_num.setText(String.valueOf(Integer.parseInt(tread_num.getText().toString())+1));
            }
            else if (ExchangeInfosWithAli.WhetherPraise == -1)
            {
                XToastUtils.toast("取消点踩帖子！");
                bt_tread.setImageDrawable(getResources().getDrawable(R.drawable.ic_tread_black));
                ExchangeInfosWithAli.WhetherPraise = 0;
                try {
                    ExchangeInfosWithAli.CancelDislikeThread_json(LookThroughActivity.threadid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tread_num.setText(String.valueOf(Integer.parseInt(tread_num.getText().toString())-1));
            }
            else if (ExchangeInfosWithAli.WhetherPraise == 1)
            {
                XToastUtils.toast("不能同时点赞点踩哦！");
            }
        }

//        if(view.getId() == R.id.iv_reply || view.getId() == R.id.tv_reply) {
//
//            showReplyDialog(2);
//        }
    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    try {
                        ExchangeInfosWithAli.AlicommentThread_json(LookThroughActivity.threadid, commentContent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    CommentDetailBean detailBean = new CommentDetailBean("小明", commentContent,"刚刚");
//                    adapter.addTheCommentData(detailBean);
                    XToastUtils.toast("评论成功");
//                    Toast.makeText(LookThroughActivity.this,"评论成功",Toast.LENGTH_SHORT).show();

                }else {
                    XToastUtils.toast("评论内容不能为空");
//                    Toast.makeText(LookThroughActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
     public void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);

        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + position + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){
                    dialog.dismiss();
                    try {
                        ExchangeInfosWithAli.AliReplyFloor_json(LookThroughActivity.threadid, replyContent , position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    XToastUtils.toast("回复成功");
//                    Toast.makeText(LookThroughActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
                }else {
                    XToastUtils.toast("回复内容不能为空");
//                    Toast.makeText(LookThroughActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }



}
