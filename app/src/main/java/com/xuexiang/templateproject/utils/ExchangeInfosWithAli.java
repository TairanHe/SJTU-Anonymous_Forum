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
import android.provider.Settings;
import android.text.LoginFilter;
import android.util.Log;

import com.bumptech.glide.load.model.stream.QMediaStoreUriLoader;
import com.google.gson.JsonParser;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.FloorInfo;
import com.xuexiang.templateproject.adapter.entity.MessageInfo;
import com.xuexiang.templateproject.adapter.entity.NewInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static com.xuexiang.xutil.resource.ResUtils.getResources;

public class ExchangeInfosWithAli {
    public static String LastSeenThreadID = "NULL";
    public static String LastSeenFloorID = "NULL";
    public static String LastSeenFavorThreadID = "NULL";
    public static String LastSeenMessageThreadID = "NULL";
    public static String LastSeenMyThreadID = "NULL";
    public static String LastSeenQueryThreadID = "NULL";
    public static String LastSeenHotThreadID = "NULL";
    public static int WhetherFavour = 0;
    public static int WhetherPraise = 0;
    public static int Num_Praise = 0;
    public static int Num_Dislike = 0;
    public static int WhetherReport = 0;
    public static String UserName = "无可奉告";

    public static int Request_verifycode(String emailaddress) throws JSONException ,IOException{
        JSONObject QueryJson = EncapsulateString_json("0", emailaddress,  "0", "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return receive_message.getInt("VarifiedEmailAddress");
    }

    public static List<NewInfo> GetAliRecommandedNewsInfos_json(int block) throws JSONException ,IOException{
        JSONObject QueryJson = EncapsulateString_json("1", LastSeenThreadID + "", block + "", "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return DecapsulateJsonToList_Recommmand(receive_message);
    }

    public static List<FloorInfo> GetAliFloor_json(String ThreadID, String order) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("2", ThreadID, LastSeenFloorID, order, "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
//        if (receive_message == null){
//            XToastUtils.toast("网络异常");
//            return null;
//        }
        WhetherFavour = 0;
        WhetherPraise = 0;
        return DecapsulateJsonToList_floor(receive_message);
    }

    public static void SendMyThread_json(String title, String content, int block, String anonymous_type, int random_seed) throws JSONException,IOException {
        JSONObject QueryJson = EncapsulateString_json("3", title, block + "", content, anonymous_type, random_seed+"");
        RunTCP_json(QueryJson);
    }

    public static void AlicommentThread_json(String threadID, String content) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("4", threadID, "0", content, "0", "0");
        RunTCP_json(QueryJson);
    }



    public static void AliReplyFloor_json( String threadID, String content, int ReplytoFloorID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("4_2", threadID, "0", content, ReplytoFloorID + "", "0");
        RunTCP_json(QueryJson);
    }



    public static void FavourThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("5", ThreadID, "0", "1", "0", "0");
        RunTCP_json(QueryJson);
    }




    public static void CancelFavourThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("5_2", ThreadID, "0","2", "0", "0");
        RunTCP_json(QueryJson);
    }



    public static List<NewInfo> GetFavourThread_json() throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("6", LastSeenFavorThreadID, "0", "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return DecapsulateJsonToList_Favour(receive_message);
    }

    public static void ReportThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("e", ThreadID, "0", "0", "0", "0");
        RunTCP_json(QueryJson);
    }




    public static List<NewInfo> GetMyThread_json() throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("7", LastSeenMyThreadID, "0", "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return DecapsulateJsonToList_My(receive_message);
    }


    public static void PraiseFloor_json(String ThreadID, int floor) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("8", ThreadID, "0", "0", floor + "", "0");
        RunTCP_json(QueryJson);
    }



    public static void CancelPraiseFloor_json(String ThreadID, int floor) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("8_2", ThreadID, "0", "0", floor + "", "0");
        RunTCP_json(QueryJson);
    }


    public static void PraiseThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("8_3", ThreadID, "0", "0", "0", "0");
        RunTCP_json(QueryJson);
    }


    public static void CancelPraiseThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("8_4", ThreadID, "0", "0", "0", "0");
        RunTCP_json(QueryJson);
    }


    public static void DislikeThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("9", ThreadID, "0", "0", "0", "0");
        RunTCP_json(QueryJson);
    }



    public static void CancelDislikeThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("9_2", ThreadID, "0", "2", "1", "0");
        RunTCP_json(QueryJson);
    }



    public static List<MessageInfo> GetMessageList_json() throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("a", LastSeenMessageThreadID, "0", "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return DecapsulateJsontoList_Message(receive_message);
    }


    public static List<NewInfo> Query_json(String queryString) throws JSONException ,IOException {
        JSONObject QueryJson= EncapsulateString_json("b", queryString, LastSeenQueryThreadID, "2", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return DecapsulateJsonToList_Query(receive_message);
    }


    public static void DeleteThread_json(String ThreadID) throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("c", ThreadID, "0", "0", "0", "0");
        RunTCP_json(QueryJson);
    }


    public static List<NewInfo> HottestThread_json() throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("d", LastSeenHotThreadID, "0", "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        return DecapsulateJsonToList_Hot(receive_message);
    }


    public static int Login_json(String Username, String Userpw) throws JSONException ,IOException {
        String deviceID = UUID.randomUUID().toString();
        JSONObject QueryJson = EncapsulateString_json("f", Username, Userpw, deviceID, "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
        String login_flag = receive_message.getString("login_flag");
        String Token = receive_message.getString("Token");
        Log.d("Token!!!!!", Token);
        MMKVUtils.put("Token", Token);
        Log.d("Login_flag是：", login_flag);
        if (login_flag.equals("")) {
            XToastUtils.toast("网络连接不稳定,无法安全登录");
            return -1;
        }
//        if (login_flag.equals("0")) {
//            UserName = Username;
//        }
        return Integer.parseInt(login_flag);
    }

    public static int VerifyToken_json() throws JSONException ,IOException {
        JSONObject QueryJson = EncapsulateString_json("-1", "0", "0", "0", "0", "0");
        JSONObject receive_message = RunTCP_json(QueryJson);
//        if (receive_message == null){
//            XToastUtils.toast("网络链接失败");
//            return 100;
//        }
        String login_flag = receive_message.getString("login_flag");
        return Integer.parseInt(login_flag);

    }


    private static JSONObject EncapsulateString_json(String op_code, String pa_1, String pa_2, String pa_3, String pa_4, String pa_5) throws JSONException {
        JSONObject js = new JSONObject();
        js.put("op_code", op_code);
        js.put("pa_1", pa_1);
        js.put("pa_2", pa_2);
        js.put("pa_3", pa_3);
        js.put("pa_4", pa_4);
        js.put("pa_5", pa_5);
        js.put("Token", MMKVUtils.getString("Token", "NULL"));
        return js;
    }


    private static List<NewInfo> DecapsulateJsonToList_Recommmand(JSONObject InputJson) throws JSONException {

        LastSeenThreadID = InputJson.getString("LastSeenThreadID");
        List<NewInfo> list = new ArrayList<>();
        JSONArray thread_list= InputJson.getJSONArray("thread_list");
        if (thread_list.length() < 1) {
//            XToastUtils.toast("没有更多啦～");
            return null;
        }
        for (int i = 0; i < thread_list.length(); i++) {
            JSONObject thread = thread_list.getJSONObject(i);
            Log.d("Thread:", thread.toString());
            list.add(new NewInfo(thread.getString("ThreadID"),
                    thread.getString("Title"),
                    thread.getString("Summary"),
                    get_block_name(Integer.parseInt(thread.getString("Block"))),
                    Integer.parseInt(thread.getString("Like")),
                    Integer.parseInt(thread.getString("Dislike")),
                    Integer.parseInt(thread.getString("Comment")),
                    Integer.parseInt(thread.getString("Read")),
                    Integer.parseInt(thread.getString("WhetherLike")),
                    thread.getString("LastUpdateTime"),
                    thread.getString("PostTime"),
                    thread.getString("AnonymousType"),
                    Integer.parseInt(thread.getString("RandomSeed")),
                    Integer.parseInt(thread.getString("WhetherTop"))));
        }
        return list;
    }


    private static List<NewInfo> DecapsulateJsonToList_Basic(JSONObject InputJson) throws JSONException {
        List<NewInfo> list = new ArrayList<>();
        JSONArray thread_list= InputJson.getJSONArray("thread_list");
        if (thread_list.length() < 1) {
//            XToastUtils.toast("没有更多啦～");
            return null;
        }
        for (int i = 0; i < thread_list.length(); i++) {
            JSONObject thread = thread_list.getJSONObject(i);
            Log.d("Thread:", thread.toString());
            list.add(new NewInfo(thread.getString("ThreadID"),
                    thread.getString("Title"),
                    thread.getString("Summary"),
                    get_block_name(Integer.parseInt(thread.getString("Block"))),
                    Integer.parseInt(thread.getString("Like")),
                    Integer.parseInt(thread.getString("Dislike")),
                    Integer.parseInt(thread.getString("Comment")),
                    Integer.parseInt(thread.getString("Read")),
                    Integer.parseInt(thread.getString("WhetherLike")),
                    thread.getString("LastUpdateTime"),
                    thread.getString("PostTime"),
                    thread.getString("AnonymousType"),
                    Integer.parseInt(thread.getString("RandomSeed"))));
        }
        return list;
    }

    private static List<NewInfo> DecapsulateJsonToList_Hot(JSONObject InputJson) throws JSONException {
        LastSeenHotThreadID = InputJson.getString("LastSeenHotThreadID");
        List<NewInfo> list = new ArrayList<>();
        JSONArray thread_list= InputJson.getJSONArray("thread_list");
        Log.d("hot thread list length", "" + thread_list.length());
        if (thread_list.length() < 1) {
//            XToastUtils.toast("没有更多啦～");
            return null;
        }

        for (int i = 0; i < thread_list.length(); i++) {
            JSONObject thread = thread_list.getJSONObject(i);
            Log.d("Thread:", thread.toString());
            list.add(new NewInfo(thread.getString("ThreadID"),
                    thread.getString("Title"),
                    thread.getString("Summary"),
                    get_block_name(Integer.parseInt(thread.getString("Block"))),
                    Integer.parseInt(thread.getString("Like")),
                    Integer.parseInt(thread.getString("Dislike")),
                    Integer.parseInt(thread.getString("Comment")),
                    Integer.parseInt(thread.getString("Read")),
                    Integer.parseInt(thread.getString("WhetherLike")),
                    thread.getString("LastUpdateTime"),
                    thread.getString("PostTime"),
                    thread.getString("AnonymousType"),
                    Integer.parseInt(thread.getString("RandomSeed"))));
        }
        return list;
    }

    private static List<NewInfo> DecapsulateJsonToList_My(JSONObject InputJson) throws JSONException {
        LastSeenMyThreadID = InputJson.getString("LastSeenMyThreadID");
        List<NewInfo> list = new ArrayList<>();
        JSONArray thread_list= InputJson.getJSONArray("thread_list");
        if (thread_list.length() < 1) {
//            if (LastSeenMyThreadID.equals("NULL")){
//                XToastUtils.toast("您好像还没有发布过帖子~");
//                return null;
//            }
//            else{
//                XToastUtils.toast("没有更多啦～");
//                return null;
//            }
            return null;

        }
        for (int i = 0; i < thread_list.length(); i++) {
            JSONObject thread = thread_list.getJSONObject(i);
            Log.d("Thread:", thread.toString());
            list.add(new NewInfo(thread.getString("ThreadID"),
                    thread.getString("Title"),
                    thread.getString("Summary"),
                    get_block_name(Integer.parseInt(thread.getString("Block"))),
                    Integer.parseInt(thread.getString("Like")),
                    Integer.parseInt(thread.getString("Dislike")),
                    Integer.parseInt(thread.getString("Comment")),
                    Integer.parseInt(thread.getString("Read")),
                    Integer.parseInt(thread.getString("WhetherLike")),
                    thread.getString("LastUpdateTime"),
                    thread.getString("PostTime"),
                    thread.getString("AnonymousType"),
                    Integer.parseInt(thread.getString("RandomSeed"))));
        }
        return list;
    }

    private static List<NewInfo> DecapsulateJsonToList_Query(JSONObject InputJson) throws JSONException {
        LastSeenQueryThreadID = InputJson.getString("LastSeenQueryThreadID");
        List<NewInfo> list = new ArrayList<>();
        JSONArray thread_list= InputJson.getJSONArray("thread_list");
        if (thread_list.length() < 1) {
//            XToastUtils.toast("没有更多啦～");
            return null;
        }
        for (int i = 0; i < thread_list.length(); i++) {
            JSONObject thread = thread_list.getJSONObject(i);
            Log.d("Thread:", thread.toString());
            list.add(new NewInfo(thread.getString("ThreadID"),
                    thread.getString("Title"),
                    thread.getString("Summary"),
                    get_block_name(Integer.parseInt(thread.getString("Block"))),
                    Integer.parseInt(thread.getString("Like")),
                    Integer.parseInt(thread.getString("Dislike")),
                    Integer.parseInt(thread.getString("Comment")),
                    Integer.parseInt(thread.getString("Read")),
                    Integer.parseInt(thread.getString("WhetherLike")),
                    thread.getString("LastUpdateTime"),
                    thread.getString("PostTime"),
                    thread.getString("AnonymousType"),
                    Integer.parseInt(thread.getString("RandomSeed"))));
        }
        return list;
    }

//    private static List<NewInfo> DecapsulateStringToList_Favour(String InputString) {
//        ShowLog(InputString);
//        List<NewInfo> list = new ArrayList<>();
//        String[] main_split = InputString.split("\023");
//        if (main_split.length < 1) {
//            XToastUtils.toast("您好像还没有收藏过帖子~");
//            return null;
//        }
//        for (String retval : main_split[0].split("\022")) {
//            String[] temp = retval.split("\021");
//            if (temp.length < 10) continue;
//            list.add(new NewInfo(temp[0], temp[2], temp[3], get_block_name(Integer.parseInt(temp[1])),
//                    Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]),
//                    Integer.parseInt(temp[7]), Integer.parseInt(temp[8]), temp[9])
//                    .setTag(Integer.parseInt(temp[10]) + ""));
//
//        }
//        return list;
//    }

    private static List<NewInfo> DecapsulateJsonToList_Favour(JSONObject InputJson) throws JSONException {
        LastSeenFavorThreadID = InputJson.getString("LastSeenFavorThreadID");
        List<NewInfo> list = new ArrayList<>();
        JSONArray thread_list= InputJson.getJSONArray("thread_list");
        if (thread_list.length() < 1) {
//            if (LastSeenFavorThreadID.equals("NULL")){
//                XToastUtils.toast("您好像还没有收藏过帖子~");
//                return null;
//            }
//            else{
//                XToastUtils.toast("没有更多啦～");
//                return null;
//            }
            return null;
        }
        for (int i = 0; i < thread_list.length(); i++) {
            JSONObject thread = thread_list.getJSONObject(i);
            Log.d("Thread:", thread.toString());
            list.add(new NewInfo(thread.getString("ThreadID"),
                    thread.getString("Title"),
                    thread.getString("Summary"),
                    get_block_name(Integer.parseInt(thread.getString("Block"))),
                    Integer.parseInt(thread.getString("Like")),
                    Integer.parseInt(thread.getString("Dislike")),
                    Integer.parseInt(thread.getString("Comment")),
                    Integer.parseInt(thread.getString("Read")),
                    Integer.parseInt(thread.getString("WhetherLike")),
                    thread.getString("LastUpdateTime"),
                    thread.getString("PostTime"),
                    thread.getString("AnonymousType"),
                    Integer.parseInt(thread.getString("RandomSeed"))).setWhetherReadUpdate(Integer.parseInt(thread.getString("WhetherReadUpdate"))));
        }
        return list;
    }


    private static List<FloorInfo> DecapsulateJsonToList_floor(JSONObject InputJson) throws JSONException {
        List<FloorInfo> list = new ArrayList<>();
        JSONArray floor_list= InputJson.getJSONArray("floor_list");
//        WhetherPraise = Integer.parseInt(InputJson.getString("WhetherLike"));


        JSONObject this_thread = InputJson.getJSONObject("this_thread");
        WhetherFavour = Integer.parseInt(this_thread.getString("WhetherFavour"));
        WhetherPraise = Integer.parseInt(this_thread.getString("WhetherLike"));
        Num_Praise = Integer.parseInt(this_thread.getString("Like"));
        Num_Dislike = Integer.parseInt(this_thread.getString("Dislike"));
        WhetherReport = Integer.parseInt(this_thread.getString("WhetherReport"));
        LastSeenFloorID = InputJson.getString("LastSeenFloorID");
        if (floor_list.length() < 1) {
//            XToastUtils.toast("没有更多啦～");
            return null;
        }
        for (int i = 0; i < floor_list.length(); i++) {
            JSONObject floor = floor_list.getJSONObject(i);
            Log.d("Floor:", floor.toString());
            list.add(new FloorInfo(floor.getString("FloorID"),
                    floor.getString("Speakername"),
                    floor.getString("Replytoname"),
                    floor.getString("Replytofloor"),
                    floor.getString("Context"),
                    Integer.parseInt(floor.getString("Like")),
                    Integer.parseInt(floor.getString("WhetherLike")),
                    floor.getString("RTime")));
        }
        return list;

    }


    private static List<MessageInfo> DecapsulateJsontoList_Message(JSONObject InputJson) throws JSONException {
        LastSeenMessageThreadID = InputJson.getString("LastSeenMessageThreadID");
        List<MessageInfo> list = new ArrayList<>();
        Log.d("Message:", InputJson.toString());
        JSONArray message_list= InputJson.getJSONArray("message_list");
        if (message_list.length() < 1 ) {
//            if (LastSeenMessageThreadID.equals("NULL")){
//                XToastUtils.toast("好像您还没有收到信息");
//                return null;
//            }
//            else{
//                XToastUtils.toast("没有更多啦～");
//            }
            return null;
        }

        for (int i = 0; i < message_list.length(); i++) {
            JSONObject message = message_list.getJSONObject(i);
            Log.d("Message:", message.toString());
            list.add(new MessageInfo(message.getString("ThreadID"),
                    message.getString("Title"),
                    message.getString("Summary"),
                    get_block_name(Integer.parseInt(message.getString("Block"))),
                    Integer.parseInt(message.getString("Like")),
                    Integer.parseInt(message.getString("Dislike")),
                    Integer.parseInt(message.getString("Comment")),
                    Integer.parseInt(message.getString("Read")),
                    Integer.parseInt(message.getString("WhetherLike")),
                    message.getString("LastUpdateTime"),
                    message.getString("PostTime"),
                    message.getString("AnonymousType"),
                    Integer.parseInt(message.getString("RandomSeed")),
                    Integer.parseInt(message.getString("Type")),
                    Integer.parseInt(message.getString("Judge"))));
        }
        return list;
//        ThreadID = threadid;
//        Title = title;
//        Type = type;
//        Judge = judge;
//        PostTime = posttime;
//        Summary = summary;
    }


    private static JSONObject RunTCP_json(JSONObject QueryJson) throws JSONException, IOException {
        tcp_thread_runnable_json tcp_one = new tcp_thread_runnable_json();
        tcp_one.set_text(QueryJson);
        tcp_one.set_addr("172.81.215.104", 8080);
        Thread tcp_thread = new Thread(tcp_one);
        tcp_thread.start();
        try {
            tcp_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (tcp_one.je != null) throw tcp_one.je;
        if (tcp_one.ie != null) throw tcp_one.ie;
        return tcp_one.get_receive_text();
    }

    public static int get_block_id(String name) {
        switch (name) {
            case "主干道":
                return 0;
            case "体育":
                return 1;
            case "音乐":
                return 2;
            case "科学":
                return 3;
            case "数码":
                return 4;
            case "娱乐":
                return 5;
            case "情感":
                return 6;
            case "社会":
                return 7;
            case "其他":
                return 8;
            default:
                return -1;
        }
    }

    public static String get_block_name(int id) {
        switch (id) {
            case 0:
                return "主干道";
            case 1:
                return "体育";
            case 2:
                return "音乐";
            case 3:
                return "科学";
            case 4:
                return "数码";
            case 5:
                return "娱乐";
            case 6:
                return "情感";
            case 7:
                return "社会";
            case 8:
                return "其他";
            default:
                return "主干道";
        }
    }

    private static void ShowLog(String inputstring) {
        Log.d("dyy", "  \n" + inputstring.replace("\n", "*换行*")
                .replace("\021", " \\021 ").replace("\022", " \\022\n")
                .replace("\023", "\n\\023!!!"));
    }

//    private static String protectString(String str) {
//        str = str.replaceAll(";", ",");
//        str = str.replaceAll("&", "&amp;");
//        str = str.replaceAll("<", "&lt;");
//        str = str.replaceAll(">", "&gt;");
//        str = str.replaceAll("'", "");
//        str = str.replaceAll("--", "");
//        str = str.replaceAll("/", "");
//        str = str.replaceAll("%", "");
//        return str;
//    }
}