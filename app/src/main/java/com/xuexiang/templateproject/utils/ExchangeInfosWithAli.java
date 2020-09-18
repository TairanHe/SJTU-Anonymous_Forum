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

import android.app.DownloadManager;
import android.text.LoginFilter;
import android.util.Log;

import com.bumptech.glide.load.model.stream.QMediaStoreUriLoader;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.FloorInfo;
import com.xuexiang.templateproject.adapter.entity.MessageInfo;
import com.xuexiang.templateproject.adapter.entity.NewInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import java.util.ArrayList;
import java.util.List;


import static com.xuexiang.xutil.resource.ResUtils.getResources;

public class ExchangeInfosWithAli {
    public static int NumOfQuery = 0;
    public static int WhetherFavour = 0;
    public static int WhetherPraise = 0;
    public static String UserName = "dyy";

    public static List<NewInfo> GetAliRecommandedNewsInfos(int block) {
        String QueryString = EncapsulateString("1", NumOfQuery + "", block + "", UserName, "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_Recommmand(receive_message);
    }
    public static List<NewInfo> GetAliRecommandedNewsInfos_json(int block) throws JSONException {
        JSONObject QueryJson = EncapsulateString_json("1", NumOfQuery + "", block + "", UserName, "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return DecapsulateJsonToList_Recommmand(receive_message);
    }

    public static List<FloorInfo> GetAliThread(String ThreadID) {
        String QueryString = EncapsulateString("2", ThreadID, UserName, "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        WhetherFavour = 0;
        WhetherPraise = 0;
        return DecapsulateStringToList_floor(receive_message);
    }
    public static List<FloorInfo> GetAliThread_json(String ThreadID) throws JSONException {
        JSONObject QueryJson = EncapsulateString_json("2", ThreadID, UserName, "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        WhetherFavour = 0;
        WhetherPraise = 0;
        return DecapsulateJsonToList_floor(receive_message);
    }

    public static void SendMyThread(String title, String content, int block) {
        String QueryString = EncapsulateString("3", title, block + "", content, UserName, "0");
        QueryString = QueryString.replace(";", ",");
        RunTCP(QueryString);
    }

    public static void Alicomment(String UserID, String threadID, String content) {
        String QueryString = EncapsulateString("4", threadID, UserName, content, "0", "0");
        QueryString = protectString(QueryString);
        RunTCP(QueryString);
    }

    public static void AliReply(String UserID, String threadID, String content, int ReplytoFloorID) {
        String QueryString = EncapsulateString("4", threadID, UserName, content, ReplytoFloorID + "", "0");
        QueryString = protectString(QueryString);
        RunTCP(QueryString);
    }

    public static void FavourThread(String ThreadID) {
        String QueryString = EncapsulateString("5", ThreadID, UserName, "1", "0", "0");
        RunTCP(QueryString);
    }

    public static void DisFavourThread(String ThreadID) {
        String QueryString = EncapsulateString("5", ThreadID, UserName, "2", "0", "0");
        RunTCP(QueryString);
    }

    public static void CancelFavourThread(String ThreadID) {
        String QueryString = EncapsulateString("5", ThreadID, UserName, "2", "0", "0");
        RunTCP(QueryString);
    }

    public static List<NewInfo> GetFavourThread() {
        String QueryString = EncapsulateString("6", UserName, "0", "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_Favour(receive_message);
    }

    public static List<NewInfo> GetMyThread() {
        String QueryString = EncapsulateString("7", UserName, "0", "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_Basic(receive_message);
    }

    public static void PraiseFloor(String ThreadID, int floor) {
        String QueryString = EncapsulateString("8", ThreadID, UserName, "1", floor + "", "0");
        RunTCP(QueryString);
    }

    public static void CancelPraiseFloor(String ThreadID, int floor) {
        String QueryString = EncapsulateString("8", ThreadID, UserName, "2", floor + "", "0");
        RunTCP(QueryString);
    }

    public static void PraiseThread(String ThreadID) {
        String QueryString = EncapsulateString("8", ThreadID, UserName, "1", "1", "0");
        RunTCP(QueryString);
    }

    public static void CancelPraiseThread(String ThreadID) {
        String QueryString = EncapsulateString("8", ThreadID, UserName, "2", "1", "0");
        RunTCP(QueryString);
    }

    public static void DislikeThread(String ThreadID) {
        String QueryString = EncapsulateString("9", ThreadID, UserName, "1", "1", "0");
        RunTCP(QueryString);
    }

    public static void CancelDislikeThread(String ThreadID) {
        String QueryString = EncapsulateString("9", ThreadID, UserName, "2", "1", "0");
        RunTCP(QueryString);
    }

    public static List<MessageInfo> GetMessageList() {
        String QueryString = EncapsulateString("a", UserName, "0", "1", "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringtoList_Message(receive_message);
    }

    public static List<NewInfo> query(String queryString) {
        String QueryString = EncapsulateString("b", queryString, UserName, "2", "0", "0");
        QueryString = protectString(QueryString);
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_Basic(receive_message);
    }

    public static void deletethread(String ThreadID) {
        String QueryString = EncapsulateString("c", ThreadID, UserName, "0", "0", "0");
        RunTCP(QueryString);
    }

    public static List<NewInfo> hottest_thread() {
        String QueryString = EncapsulateString("d", UserName, "0", "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_Basic(receive_message);
    }

    public static int Register(String Username, String Userpw) {
        Username = protectString(Username);
        Userpw = protectString(Userpw);
        String QueryString = EncapsulateString("e", Username, Userpw, "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        if (receive_message.equals("")) {
            XToastUtils.toast("网络连接不稳定,请再试一次");
            return -1;
        }
        return Integer.parseInt(receive_message);
    }

    public static int Login(String Username, String Userpw) {
        Username = protectString(Username);
        Userpw = protectString(Userpw);
        String QueryString = EncapsulateString("f", Username, Userpw, "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        if (receive_message.equals("")) {
            XToastUtils.toast("网络连接不稳定,无法安全登录");
            return -1;
        }
        if (receive_message.equals("0")) {
            UserName = Username;
        }
        return Integer.parseInt(receive_message);
    }

    public static int Login_json(String Username, String Userpw) throws JSONException, InterruptedException {
        Username = protectString(Username);
        Userpw = protectString(Userpw);
        JSONObject QueryJson = EncapsulateString_json("f", Username, Userpw, "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        String login_flag = receive_message.getString("login_flag");
        Log.d("Login_flag是：", login_flag);
        if (login_flag.equals("")) {
            XToastUtils.toast("网络连接不稳定,无法安全登录");
            return -1;
        }
        if (login_flag.equals("0")) {
            UserName = Username;
        }
        return Integer.parseInt(login_flag);
    }

    private static String EncapsulateString(String OperatingNumber, String Op1, String Op2, String Op3, String Op4, String Op5) {
        return OperatingNumber + "\021" + Op1 + "\021" + Op2 + "\021" + Op3 + "\021" + Op4 + "\021" + Op5 + "\021";
    }

    private static JSONObject EncapsulateString_json(String op_code, String pa_1, String pa_2, String pa_3, String pa_4, String pa_5) throws JSONException {
        JSONObject js = new JSONObject();
        js.put("op_code", op_code);
        js.put("pa_1", pa_1);
        js.put("pa_2", pa_2);
        js.put("pa_3", pa_3);
        js.put("pa_4", pa_4);
        js.put("pa_5", pa_5);
        return js;
    }

    private static List<NewInfo> DecapsulateStringToList_Recommmand(String InputString) {
        ShowLog(InputString);
        List<NewInfo> list = new ArrayList<>();
        String[] main_split = InputString.split("\023");
        if (main_split.length < 1) {
            XToastUtils.toast("帖子只有这么多了,呜呜呜");
            return null;
        }
        NumOfQuery += 1;
        for (String retval : main_split[0].split("\022")) {
            String[] temp = retval.split("\021");
            if (temp.length < 5) continue;
            list.add(new NewInfo(temp[0], temp[2], temp[3], get_block_name(Integer.parseInt(temp[1])),
                    Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]),
                    Integer.parseInt(temp[7]), Integer.parseInt(temp[8]), temp[9]));
        }
        return list;
    }

    private static List<NewInfo> DecapsulateJsonToList_Recommmand(JSONObject InputJson) throws JSONException {

        List<NewInfo> list = new ArrayList<>();
        JSONArray thread_list= InputJson.getJSONArray("thread_list");
        NumOfQuery += 1;
        for (int i = 0; i < thread_list.length(); i++) {
            JSONObject thread = thread_list.getJSONObject(i);
            Log.d("Thread:", thread.toString());
            list.add(new NewInfo(thread.getString("ThreadID"),
                    thread.getString("Title"),
                    thread.getString("Summary"),
                    get_block_name(Integer.parseInt(thread.getString("Block"))),
                    Integer.parseInt(thread.getString("Praise")),
                    Integer.parseInt(thread.getString("Dislike")),
                    Integer.parseInt(thread.getString("Comment")),
                    Integer.parseInt(thread.getString("Read")),
                    Integer.parseInt(thread.getString("WhetherLike")),
                    thread.getString("LastUpdateTime")));
        }
        return list;
    }

    private static List<NewInfo> DecapsulateStringToList_Basic(String InputString) {
        ShowLog(InputString);
        List<NewInfo> list = new ArrayList<>();
        String[] main_split = InputString.split("\023");
        if (main_split.length < 1) {
            XToastUtils.toast("空空如也~");
            return null;
        }
        for (String retval : main_split[0].split("\022")) {
            String[] temp = retval.split("\021");
            if (temp.length < 10) continue;
            list.add(new NewInfo(temp[0], temp[2], temp[3], get_block_name(Integer.parseInt(temp[1])),
                    Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]),
                    Integer.parseInt(temp[7]), Integer.parseInt(temp[8]), temp[9]));
        }
        return list;
    }

    private static List<NewInfo> DecapsulateStringToList_Favour(String InputString) {
        ShowLog(InputString);
        List<NewInfo> list = new ArrayList<>();
        String[] main_split = InputString.split("\023");
        if (main_split.length < 1) {
            XToastUtils.toast("您好像还没有收藏过帖子~");
            return null;
        }
        for (String retval : main_split[0].split("\022")) {
            String[] temp = retval.split("\021");
            if (temp.length < 10) continue;
            list.add(new NewInfo(temp[0], temp[2], temp[3], get_block_name(Integer.parseInt(temp[1])),
                    Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]),
                    Integer.parseInt(temp[7]), Integer.parseInt(temp[8]), temp[9])
                    .setTag(Integer.parseInt(temp[10]) + ""));

        }
        return list;
    }

    private static List<FloorInfo> DecapsulateStringToList_floor(String InputString) {
        ShowLog(InputString);
        if (InputString.length() < 1) {
            XToastUtils.toast("似乎出了一点问题...");
            return null;
        }
        List<FloorInfo> list = new ArrayList<>();
        int i = 0;
        for (String retval : InputString.split("\022")) {
            String[] temp = retval.split("\021");
            if (temp.length < 6) continue;
            if (i == 0) {
                WhetherPraise = Integer.parseInt(temp[6]);
                WhetherFavour = Integer.parseInt(temp[7]);
                i += 1;
                continue;
            }
            list.add(new FloorInfo(temp[0], temp[1], temp[2], temp[3], temp[4],
                    Integer.parseInt(temp[5]), Integer.parseInt(temp[6])));
            i += 1;
        }
        return list;
    }

    private static List<FloorInfo> DecapsulateJsonToList_floor(JSONObject InputJson) throws JSONException {
        List<FloorInfo> list = new ArrayList<>();
        JSONArray floor_list= InputJson.getJSONArray("floor_list");
        for (int i = 0; i < floor_list.length(); i++) {
            JSONObject floor = floor_list.getJSONObject(i);
            Log.d("Floor:", floor.toString());
            list.add(new FloorInfo(floor.getString("FloorID"),
                    floor.getString("Speakername"),
                    floor.getString("Replytoname"),
                    floor.getString("Replytofloor"),
                    floor.getString("Context"),
                    Integer.parseInt(floor.getString("Praise")),
                    Integer.parseInt(floor.getString("WhetherPraise"))));
        }
        return list;

    }

    private static List<MessageInfo> DecapsulateStringtoList_Message(String InputString) {
        ShowLog(InputString);
        if (InputString.length() <= 1) {
            XToastUtils.toast("好像您还没有收到信息");
            return null;
        }
        List<MessageInfo> list = new ArrayList<>();
        for (String retval : InputString.split("\022")) {
            String[] temp = retval.split("\021");
            if (temp.length < 4) continue;
            list.add(new MessageInfo(temp[0], temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), temp[4], temp[5]));
        }
        return list;
    }

    private static String RunTCP(String QueryString) {
        tcp_thread_runnable tcp_one = new tcp_thread_runnable();
        tcp_one.set_text(QueryString);
        tcp_one.set_addr("172.81.215.104", 8080);
        Thread tcp_thread = new Thread(tcp_one);
        tcp_thread.start();
        try {
            tcp_thread.join();  //阻塞主进程,确保网络请求完成了再进行下一步
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tcp_one.get_receive_text();
    }

    private static JSONObject RunTCP_json(JSONObject QueryJson) {
        tcp_thread_runnable_json tcp_one = new tcp_thread_runnable_json();
        tcp_one.set_text(QueryJson);
        tcp_one.set_addr("172.81.215.104", 8080);
        Thread tcp_thread = new Thread(tcp_one);
        tcp_thread.start();
        try {
            tcp_thread.join();  //阻塞主进程,确保网络请求完成了再进行下一步
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tcp_one.get_receive_text();
    }

    public static int get_block_id(String name) {
        switch (name) {
            case "主干道":
                return -1;
            case "体育":
                return 0;
            case "音乐":
                return 1;
            case "科学":
                return 2;
            case "数码":
                return 3;
            case "娱乐":
                return 4;
            case "情感":
                return 5;
            case "社会":
                return 6;
            default:
                return -1;
        }
    }

    public static String get_block_name(int id) {
        switch (id) {
            case -1:
                return "主干道";
            case 0:
                return "体育";
            case 1:
                return "音乐";
            case 2:
                return "科学";
            case 3:
                return "数码";
            case 4:
                return "娱乐";
            case 5:
                return "情感";
            case 6:
                return "社会";
            default:
                return "主干道";
        }
    }

    private static void ShowLog(String inputstring) {
        Log.d("dyy", "  \n" + inputstring.replace("\n", "*换行*")
                .replace("\021", " \\021 ").replace("\022", " \\022\n")
                .replace("\023", "\n\\023!!!"));
    }

    private static String protectString(String str) {
        str = str.replaceAll(";", ",");
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("'", "");
        str = str.replaceAll("--", "");
        str = str.replaceAll("/", "");
        str = str.replaceAll("%", "");
        return str;
    }
}