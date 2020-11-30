package com.xuexiang.templateproject.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.utils.ExchangeInfosWithAli;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import org.json.JSONException;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostThreadActivity extends AppCompatActivity{

    private CompoundButton RG;
    private int sectionID = 1;
    private RadioGroup RG1, RG2;
    private MultiLineEditText multiLineEditText, titlemultiLineEditText;
    private ButtonView buttonView;
    private RadioButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    private RadioGroup RG_anonymous, RG_random;
    private RadioButton btn_abc, btn_us_president, btn_tarot;
    private RadioButton btn_random_no, btn_random_yes;
    private String anonymous_type = "abc";
    private int random_seed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_thread);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        RG1 = findViewById(R.id.radio_group1);
        RG2 = findViewById(R.id.radio_group2);
        RG_anonymous = findViewById(R.id.anonymous_group1);
        RG_random = findViewById(R.id.random_group1);

        buttonView = findViewById(R.id.button_submit);
//        clearEditText = findViewById(R.id.clearEditText);
        titlemultiLineEditText = findViewById(R.id.titlemultiLineEditText);
        multiLineEditText = findViewById(R.id.multiLineEditText);
        btn1 = findViewById(R.id.section1);btn2 = findViewById(R.id.section2);btn3 = findViewById(R.id.section3);btn4 = findViewById(R.id.section4);
        btn5 = findViewById(R.id.section5);btn6 = findViewById(R.id.section6);btn7 = findViewById(R.id.section7);btn8 = findViewById(R.id.section8);
        btn_abc = findViewById(R.id.abc); btn_us_president = findViewById(R.id.us_president); btn_tarot = findViewById(R.id.tarot);
        btn_random_no = findViewById(R.id.random_no); btn_random_yes = findViewById(R.id.random_yes);


        RG1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.section1:
                        if (btn1.isChecked()){
                            sectionID = 1;
                            RG2.clearCheck();
                        }
                        break;
                    case R.id.section2:
                        if (btn2.isChecked()){
                            sectionID = 2;
                            RG2.clearCheck();
                        }
                        break;
                    case R.id.section3:
                        if (btn3.isChecked()){
                            sectionID = 3;
                            RG2.clearCheck();
                        }
                        break;
                    case R.id.section4:
                        if (btn4.isChecked()){
                            sectionID = 4;
                            RG2.clearCheck();
                        }
                        break;
                    default:
                        break;
                }

            }
        });
        RG2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.section5:
                        if (btn5.isChecked()){
                            sectionID = 5;
                            RG1.clearCheck();
                        }
                        break;
                    case R.id.section6:
                        if (btn6.isChecked()){
                            sectionID = 6;
                            RG1.clearCheck();
                        }
                        break;
                    case R.id.section7:
                        if (btn7.isChecked()){
                            sectionID = 7;
                            RG1.clearCheck();
                        }
                        break;
                    case R.id.section8:
                        if (btn8.isChecked()){
                            sectionID = 8;
                            RG1.clearCheck();
                        }
                }

            }
        });

        RG_anonymous.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.abc:
                        if (btn_abc.isChecked()){
                            anonymous_type = "abc";
                            break;
                        }
                        break;
                    case R.id.us_president:
                        if (btn_us_president.isChecked()){
                            anonymous_type = "us_president";
                            break;
                        }
                    case R.id.tarot:
                        if (btn_tarot.isChecked()){
                            anonymous_type = "tarot";
                            break;
                        }
                }

            }
        });

        RG_random.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.random_no:
                        if (btn_random_no.isChecked()){
                            random_seed = 0;
                        }
                        break;
                    case R.id.random_yes:
                        if (btn_random_yes.isChecked()){
                            random_seed = new Random().nextInt(Integer.MAX_VALUE - 1) + 1;
                        }
                }

            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.button_submit:
                        String thread_Title = titlemultiLineEditText.getContentText().toString();
                        String thread_Content = multiLineEditText.getContentText().toString();

                        Matcher title_matcher = Pattern.compile("\r\n|\r|\n").matcher(thread_Title);
                        int title_lines = 1;
                        while (title_matcher.find())
                        {
                            title_lines ++;
                        }

                        Matcher content_matcher = Pattern.compile("\r\n|\r|\n").matcher(thread_Content);
                        int content_lines = 1;
                        while (content_matcher.find())
                        {
                            content_lines ++;
                        }

                        if(thread_Title.isEmpty() || thread_Content.isEmpty()){
                            XToastUtils.toast("输入不能为空");
//                            Toast.makeText(PostThreadActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                        }
                        else if(title_lines > 1){
                            XToastUtils.toast("标题不能提行哦～");
                        }
                        else if(content_lines > 20){
                            XToastUtils.toast("帖子内容不能提行超过20次哦～");
                        }
                        else if (thread_Title.length() > 40){
                            XToastUtils.toast("标题长度不能长于40个字符哟");
                        }
                        else if (thread_Content.length() > 817){
                            XToastUtils.toast("发帖内容太长啦～不能长于817个字符哟");
                        }
                        else {
//                            ExchangeInfosWithAli.SendMyThread(thread_Title, thread_Content, sectionID);
                            try {
                                ExchangeInfosWithAli.SendMyThread_json(thread_Title, thread_Content, sectionID, anonymous_type, random_seed);
                                XToastUtils.toast("发帖成功");
//                                Snackbar snackbar = Snackbar.make(view,"发帖成功",Snackbar.LENGTH_SHORT);
//                                snackbar.show();
                                finish();

                            } catch (JSONException | IOException e) {
                                Snackbar snackbar = Snackbar.make(view,"请检查网络后重试",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                e.printStackTrace();
                            }
                            //                            后端函数 这里thread_Title是帖子标题，thread_Content是帖子内容，在最外面int型的sectionID是帖子所属的板块，只有这三个参数传递给后端
//                                    另外，帖子标题和帖子内容不能为空的逻辑已经判断过了，不必再判断。

                        }
                        break;
                    default:
                            break;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.button_submit:
//                String thread_Title = clearEditText.getText().toString();
//                String thread_content = multiLineEditText.getContentText().toString();
//                if(thread_Title.isEmpty() || thread_content.isEmpty()){
//                    Toast.makeText(PostThreadActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
//                }
//                else {
////                            Toast.makeText(PostThreadActivity.this, thread_Title + '\n' + thread_content, Toast.LENGTH_LONG).show();
////                            后端函数
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    OnCheckedChangeListener mChangeListener = new OnCheckedChangeListener() {
//        @SuppressLint("ResourceType")
//        @Override
//        public void onCheckedChanged(RadioGroup groupView, int checkedId) {
//            if(groupView != null && checkedId > 0){
//                if(groupView == firstRG){
//                    secondRG.clearCheck();
//                }else if(groupView == secondRG){
//                    firstRG.clearCheck();
//                }
//                groupView.check(checkedId);
//            }
//
//        }
//    };


    //        ClearEditText clearEditText = findViewById(R.id.clearEditText);
//        firstRG = findViewById(R.id.radio_group1);
//        secondRG = findViewById(R.id.radio_group2);
//        RG = findViewById(R.id.radio_group);
//
//        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                firstRG.setOnCheckedChangeListener(this);
//                secondRG.setOnCheckedChangeListener(this);
//
//
//            }
//
//
//        });
//
//        MultiLineEditText multiLineEditText = findViewById(R.id.multiLineEditText);
//        ButtonView buttonView = findViewById(R.id.button_submit);
//        buttonView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()){
//                    case R.id.button_submit:
//                        String thread_Title = clearEditText.getText().toString();
//                        String thread_content = multiLineEditText.getContentText().toString();
//                        if(thread_Title.isEmpty() || thread_content.isEmpty()){
//                            Toast.makeText(PostThreadActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
//                        }
//                        else {
////                            Toast.makeText(PostThreadActivity.this, thread_Title + '\n' + thread_content, Toast.LENGTH_LONG).show();
////                            后端函数
//                        }
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//        });


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

}
