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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.templateproject.utils.ExchangeInfosWithAli;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LookThroughActivity extends AppCompatActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener {
    public static String threadid;
    public static String threadtitle;
    public static String threadsummary;
    public static String threadposttime;
    public static int favournum;
    public static int praisenum;
    public static int dislikenum;
    public static String lastupdatetime;
    public static String anonymousType;
    public static int randomSeed;

    private TextView bt_comment;
    private ImageView bt_favor;
    private ImageView bt_praise;
    private ImageView bt_tread;

    private TextView praise_num;
    private TextView tread_num;
    private TextView favor_num;
    private TextView mTitle;
    private BottomSheetDialog dialog;

    private Toolbar toolbar;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        进入新帖子，重设LastSeenFloorID
        ExchangeInfosWithAli.LastSeenFloorID = "NULL";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_through);

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_look_through);
        toolbar.setOnMenuItemClickListener(this);

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
        praise_num.setText("");
        tread_num.setText("");


        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(threadtitle);
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
        praise_num.setText("" + ExchangeInfosWithAli.Num_Praise);
        tread_num.setText("" + ExchangeInfosWithAli.Num_Dislike);
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.d("dyy:", String.valueOf(item.getItemId()));
        //Log.d("dyy:", String.valueOf(R.id.action_privacy));
        switch (item.getItemId()) {
            case R.id.action_report:
                XToastUtils.toast("举报");
                Utils.showreportDialog(this, null, threadid);
                //Utils.showPrivacyDialog(this, null);
//                Intent intent = new Intent(MainActivity.this, PostThreadActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
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
    public void showCommentDialog(){
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
                Matcher comment_matcher = Pattern.compile("\r\n|\r|\n").matcher(commentContent);
                int comment_lines = 1;
                while (comment_matcher.find())
                {
                    comment_lines ++;
                }
                if(TextUtils.isEmpty(commentContent)){
                    XToastUtils.toast("评论内容不能为空");
                    Toast.makeText(LookThroughActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
                else if (comment_lines > 20){
                    XToastUtils.toast("评论内容不能提行超过20次哦～");
                }
                else if (commentContent.length() > 817){
                    XToastUtils.toast("评论内容太长啦～不能长于817个字符哟");
                }
                else {
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
//
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2)
                if(!TextUtils.isEmpty(charSequence)){
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
                Matcher reply_matcher = Pattern.compile("\r\n|\r|\n").matcher(replyContent);
                int reply_lines = 1;
                while (reply_matcher.find())
                {
                    reply_lines ++;
                }
                if(TextUtils.isEmpty(replyContent)){
                    XToastUtils.toast("评论内容不能为空");
//                    Toast.makeText(LookThroughActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();

                }
                else if (reply_lines > 20){
                    XToastUtils.toast("评论内容不能提行超过20次哦～");
                }
                else if (replyContent.length() > 817){
                    XToastUtils.toast("评论内容太长啦～不能长于817个字符哟");
                }
                else {
                    dialog.dismiss();
                    try {
                        ExchangeInfosWithAli.AliReplyFloor_json(LookThroughActivity.threadid, replyContent , position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    XToastUtils.toast("回复成功");
//                    Toast.makeText(LookThroughActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>0)
                if(!TextUtils.isEmpty(charSequence)){
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
