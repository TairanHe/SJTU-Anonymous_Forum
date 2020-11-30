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

package com.xuexiang.templateproject.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.LookThroughActivity;
import com.xuexiang.templateproject.activity.PostThreadActivity;
import com.xuexiang.templateproject.activity.SearchActivity;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.entity.NewInfo;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.webview.AgentWebActivity;
import com.xuexiang.templateproject.fragment.FavorFragment;
import com.xuexiang.templateproject.fragment.MyThreadsFragment;
import com.xuexiang.templateproject.fragment.SearchFragment;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.XUtil;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import me.jessyan.autosize.external.ExternalAdaptInfo;

import static com.xuexiang.templateproject.core.webview.AgentWebFragment.KEY_URL;
import static com.xuexiang.templateproject.utils.ExchangeInfosWithAli.CancelFavourThread_json;
import static com.xuexiang.xutil.app.ActivityUtils.startActivity;

/**
 * 工具类
 *
 * @author xuexiang
 * @since 2020-02-23 15:12
 */
public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 显示删除的提示
     *
     * @param context
     * @param submitListener 同意的监听
     * @return
     */
    public static Dialog showDeleteFavorDialog(Context context, MaterialDialog.SingleButtonCallback submitListener, BaseFragment baseFragment, String thread_id) {
        MaterialDialog dialog = new MaterialDialog.Builder(context).title(R.string.title_reminder).autoDismiss(false).cancelable(false)
                .positiveText("不删除").onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                })
                .negativeText("删除").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        try {
                            CancelFavourThread_json(thread_id);
                        } catch (JSONException | IOException e) {
//                            Snackbar snackbar = Snackbar.make(view,"请检查网络后重试",Snackbar.LENGTH_SHORT);
//                            snackbar.show();
                            XToastUtils.toast("请检查网络后重试");
                            e.printStackTrace();
                        }
                        Log.d("LookThroughActivity.U", thread_id);
                        dialog.dismiss();
                        // 这里是取消收藏的函数接口
                        baseFragment.openNewPage(FavorFragment.class);
                        baseFragment.getActivity().onBackPressed();
                    }
                }).build();
//        dialog.setContent(getPrivacyContent(context));
        dialog.setContent("是否确认删除此收藏？\n");
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }

    public static Dialog showReportDialog(Context context, MaterialDialog.SingleButtonCallback submitListener, String thread_id) {
        MaterialDialog dialog = new MaterialDialog.Builder(context).title(R.string.title_reminder).autoDismiss(false).cancelable(false)
                .positiveText("不举报").onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                })
                .negativeText("举报").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 这里是举报的函数接口
                        if (ExchangeInfosWithAli.WhetherReport == 0){
                            try {
                                ExchangeInfosWithAli.ReportThread_json(thread_id);
                                XToastUtils.toast("举报成功");
                                Log.d("LookThroughActivity.U", thread_id);
                                ExchangeInfosWithAli.WhetherReport = 1;
                            } catch (JSONException | IOException e) {
//                                Snackbar snackbar = Snackbar.make(view,"请检查网络后重试",Snackbar.LENGTH_SHORT);
//                                snackbar.show();
                                XToastUtils.toast("请检查网络后重试");
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                        else {
                            XToastUtils.toast("请勿重复举报");
                            Log.d("LookThroughActivity.U", thread_id);
                            dialog.dismiss();
                        }

                    }
                }).build();
//        dialog.setContent(getPrivacyContent(context));
        dialog.setContent("是否确认举报该帖？\n请慎重举报\n我们一起努力建设良好的社区环境");
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }

    public static Dialog showUpdateDialog(Context context, MaterialDialog.SingleButtonCallback submitListener, String update_url) {
        MaterialDialog dialog = new MaterialDialog.Builder(context).title(R.string.title_reminder).autoDismiss(false).cancelable(false)
//                .positiveText("不更新").onPositive((dialog1, which) -> {
//                    if (submitListener != null) {
//                        submitListener.onClick(dialog1, which);
//                    } else {
//                        dialog1.dismiss();
//                    }
//                })
                .negativeText("更新").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 这里是更新的函数接口
                        Uri uri = Uri.parse(update_url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);



                    }
                }).build();
//        dialog.setContent(getPrivacyContent(context));
        dialog.setContent("麻烦请更新至最新版APP～\n否则无法正常使用～");
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }


    public static Dialog showDeleteMyThreadDialog(Context context, MaterialDialog.SingleButtonCallback submitListener, BaseFragment baseFragment, String thread_id) {
        MaterialDialog dialog = new MaterialDialog.Builder(context).title(R.string.title_reminder).autoDismiss(false).cancelable(false)
                .positiveText("不删除").onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                })
                .negativeText("删除").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        try {
                            ExchangeInfosWithAli.DeleteThread_json(thread_id);
                        } catch (JSONException | IOException e) {
//                            Snackbar snackbar = Snackbar.make(view,"请检查网络后重试",Snackbar.LENGTH_SHORT);
//                            snackbar.show();
                            XToastUtils.toast("请检查网络后重试");
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        //这里是删除自己帖子的函数接口
                        baseFragment.openNewPage(MyThreadsFragment.class);
                        baseFragment.getActivity().onBackPressed();
                    }
                }).build();
//        dialog.setContent(getPrivacyContent(context));
        dialog.setContent("是否确认删除此帖？\n一旦删除，不可恢复！\n");
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }



    /**
     * 这里填写你的应用隐私政策网页地址
     */
    private static final String PRIVACY_URL = "https://gitee.com/xuexiangjys/TemplateAppProject/raw/master/LICENSE";

    /**
     * 显示隐私政策的提示
     *
     * @param context
     * @param submitListener 同意的监听
     * @return
     */
    public static Dialog showPrivacyDialog(Context context, MaterialDialog.SingleButtonCallback submitListener) {
        final EditText et = new EditText(context);
        MaterialDialog dialog = new MaterialDialog.Builder(context).title("隐私条款").autoDismiss(false).cancelable(false)
                .positiveText(R.string.lab_agree).onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                })
                .negativeText(R.string.lab_disagree).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        DialogLoader.getInstance().showConfirmDialog(context, ResUtils.getString(R.string.title_reminder), String.format(ResUtils.getString(R.string.content_privacy_explain_again), ResUtils.getString(R.string.app_name)), ResUtils.getString(R.string.lab_look_again), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                showPrivacyDialog(context, submitListener);
                            }
                        }, ResUtils.getString(R.string.lab_still_disagree), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                DialogLoader.getInstance().showConfirmDialog(context, ResUtils.getString(R.string.content_think_about_it_again), ResUtils.getString(R.string.lab_look_again), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        showPrivacyDialog(context, submitListener);
                                    }
                                }, ResUtils.getString(R.string.lab_exit_app), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        XUtil.get().exitApp();
                                    }
                                });
                            }
                        });
                    }
                }).build();
        dialog.setContent(getPrivacyContent(context));
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }
    public static Dialog showOnlyPrivacyDialog(Context context, MaterialDialog.SingleButtonCallback submitListener) {
        final EditText et = new EditText(context);
        MaterialDialog dialog = new MaterialDialog.Builder(context).title("隐私条款").autoDismiss(false).cancelable(false)
                .positiveText("返回").onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                }).build();
        dialog.setContent(getPrivacyContent(context));
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }
    public static Dialog showHelperDialog(Context context, MaterialDialog.SingleButtonCallback submitListener) {
        final EditText et = new EditText(context);
        MaterialDialog dialog = new MaterialDialog.Builder(context).title("联系我们").autoDismiss(false).cancelable(false)
                .positiveText("返回").onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                }).build();
        dialog.setContent(getHelperContent(context));
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }
    public static Dialog showZenDialog(Context context, MaterialDialog.SingleButtonCallback submitListener) {
        final EditText et = new EditText(context);
        MaterialDialog dialog = new MaterialDialog.Builder(context).title("The Zen of Wukefenggao,\nby Tairan He").autoDismiss(false).cancelable(false)
                .positiveText("返回").onPositive((dialog1, which) -> {
                    if (submitListener != null) {
                        submitListener.onClick(dialog1, which);
                    } else {
                        dialog1.dismiss();
                    }
                }).build();
        dialog.setContent(getZenContent(context));
        //开始响应点击事件
        dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
        return dialog;
    }
    /**
     * @return 隐私政策说明
     */
    private static SpannableStringBuilder getPrivacyContent(Context context) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder()
                .append("欢迎您来到无可奉告。\n" +
                        "\n" +
                        "请您仔细阅读以下条款，如果您对本协议的任何条款表示异议，您可以选择不进入无可奉告。当您注册成功，无论是进入无可奉告，还是在无可奉告上发布任何内容（即「内容」），均意味着您（即「用户」）完全接受本协议项下的全部条款。\n" +
                        "\n" +
                        "1. 您可以使用上海交通大学邮箱登录无可奉告，并将其作为您的用户账号，用户应当对以其用户帐号进行的所有活动和事件负法律责任。\n" +
                        "\n" +
                        "2. 用户须对在无可奉告的注册信息的真实性、合法性、有效性承担全部责任，用户不得冒充他人；不得利用他人的名义发布任何信息；不得恶意使用注册帐号导致其他用户误认；否则无可奉告有权立即停止提供服务，收回其帐号并由用户独自承担由此而产生的一切法律责任。\n" +
                        "\n" +
                        "3. 用户直接或通过各类方式间接使用无可奉告服务和数据的行为，都将被视作已无条件接受本协议全部内容；若用户对本协议的任何条款存在异议，请停止使用无可奉告所提供的全部服务。\n" +
                        "\n" +
                        "4. 用户承诺不得以任何方式利用无可奉告直接或间接从事违反中国法律的行为，无可奉告有权对违反上述承诺的内容予以删除。\n" +
                        "\n" +
                        "5. 用户不得利用无可奉告服务制作、上载、复制、发布、传播或者转载如下内容：\n" +
                        "\n" +
                        "反对宪法所确定的基本原则的；\n" +
                        "危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；\n" +
                        "损害国家荣誉和利益的；\n" +
                        "煽动民族仇恨、民族歧视，破坏民族团结的；\n" +
                        "侮辱、滥用英烈形象，否定英烈事迹，美化粉饰侵略战争行为的；\n" +
                        "破坏国家宗教政策，宣扬邪教和封建迷信的；\n" +
                        "散布谣言，扰乱社会秩序，破坏社会稳定的；\n" +
                        "散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；\n" +
                        "侮辱或者诽谤他人，侵害他人合法权益的；\n" +
                        "含有法律、行政法规禁止的其他内容的信息。\n" +
                        "\n" +
                        "6. 无可奉告有权对用户使用无可奉告的情况进行审查和监督，如用户在使用无可奉告时违反任何上述规定，无可奉告或其授权的人有权要求用户改正或直接采取一切必要的措施（包括但不限于更改或删除用户张贴的内容、暂停或终止用户使用无可奉告的权利）以减轻用户不当行为造成的影响。");
//                .append("    欢迎来到").append(ResUtils.getString(R.string.app_name)).append("!\n")
//                .append("    我可以回答一句无可奉告，但是你们又不高兴，我怎么办？\n")
//                .append("    为了更好地保护你的权益，同时遵守相关监管的要求，我们会用西方那套理论");
//        stringBuilder/*.append(getPrivacyLink(context, PRIVACY_URL))*/
//                .append("向你说明我们会如何收集、存储、保护、使用及对外提供你的信息，并说明你享有的权利。\n")
//                .append("    你问我支持不支持、我当然是支持的")
//                /*.append(getPrivacyLink(context, PRIVACY_URL))*/
//                .append("");
        return stringBuilder;
    }
    /**
     * @return 反馈说明
     */
    private static SpannableStringBuilder getHelperContent(Context context) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder()
                .append("联系无可奉告：\n" +
                        "\n" +
                        "请发邮件至 「 sjtuwkfg@163.com 」\n" +
                        "\n\n" +
                        "或关注微信公众号：\n" +
                        "\n" +
                        "「 SJTU无可奉告 」\n" +
                        "\n" +
                        "并留言\n"+
                        "\n"+
                        "感谢每一位SJTUer的反馈与支持～");
        return stringBuilder;
    }

    /**
     * @return 禅说明
     */
    private static SpannableStringBuilder getZenContent(Context context) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder()
                .append("Love is better than hate.\n" +
                                "Real is better than fake.\n" +
                                "Beautiful is better than ugly.\n" +
                                "Friendly is better than aggressive.\n" +
                                "Sound is better than reticence.  \n" +
                                "Equality is better than disparity. \n" +
                                "Unease is better than comfort.\n" +
                                "Pursue is better than abide. \n" +
                                "Respect is better than understand. \n" +
                                "Understand is better than ignorance. \n" +
                                "Ignorance is better than prejudice. \n" +
                                "Every voice counts. \n" +
                                "Special cases aren't special enough to break the rules.\n" +
                                "Injustices should never pass silently.\n" +
                                "Unless, no unless."
//                        "If the implementation is hard to explain, it's a bad idea.\n" +
//                        "If the implementation is easy to explain, it may be a good idea.\n" +
//                        "Namespaces are one honking great idea -- let's do more of those!"
                );
        return stringBuilder;
    }

    /**
     * @param context 隐私政策的链接
     * @return
     */
    private static SpannableString getPrivacyLink(Context context, String privacyUrl) {
        String privacyName = String.format(ResUtils.getString(R.string.lab_privacy_name), ResUtils.getString(R.string.app_name));
        SpannableString spannableString = new SpannableString(privacyName);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                goWeb(context, privacyUrl);
            }
        }, 0, privacyName.length(), Spanned.SPAN_MARK_MARK);
        return spannableString;
    }


    /**
     * 请求浏览器
     *
     * @param url
     */
    public static void goWeb(Context context, final String url) {
        Intent intent = new Intent(context, AgentWebActivity.class);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }


    /**
     * 是否是深色的颜色
     *
     * @param color
     * @return
     */
    public static boolean isColorDark(@ColorInt int color) {
        double darkness =
                1
                        - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))
                        / 255;
        return darkness >= 0.382;
    }

    public static void initTheme(Activity activity) {
        if (SettingSPUtils.getInstance().isUseCustomTheme()) {
            activity.setTheme(R.style.CustomAppTheme);
        } else {
            XUI.initTheme(activity);
        }
    }
    public static void showSnackBar(String message, Activity activity) {
        Snackbar.make(
                activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT
        ).setDuration(300).show();


    }

//    public static void showMainSnackBar(String message, Activity activity) {
//        Snackbar.make(
//                activity.findViewById(android.R.id.content),
//                message, Snackbar.LENGTH_SHORT
//        ).setAnchorView(activity.findViewById(R.id.bottom_navigation)).show();
//    }

    public static void showCoorSnackBar(String message, Activity activity) {
        Snackbar snack = Snackbar.make(activity.findViewById(R.id.coordinatorLayout),
                message, Snackbar.LENGTH_SHORT).setDuration(500);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snack.getView().getLayoutParams();
        params.setAnchorId(R.id.bottom_navigation); //id of the bottom navigation view
        params.gravity = Gravity.TOP;
        params.anchorGravity = Gravity.TOP;
        snack.getView().setLayoutParams(params);
        snack.show();
    }

    public static void showFloorSnackBar(String message, Activity activity) {
        Snackbar snack = Snackbar.make(activity.findViewById(R.id.coordinatorLayout),
                message, Snackbar.LENGTH_SHORT).setDuration(300);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snack.getView().getLayoutParams();
        params.setAnchorId(R.id.reply_bar); //id of the bottom navigation view
        params.gravity = Gravity.BOTTOM;
        params.anchorGravity = Gravity.TOP;
        snack.getView().setLayoutParams(params);
        snack.show();
    }





}
