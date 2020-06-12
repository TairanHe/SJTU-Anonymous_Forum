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

import android.util.Log;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.FloorInfo;
import com.xuexiang.templateproject.adapter.entity.NewInfo;

import java.util.ArrayList;
import java.util.List;

import static com.xuexiang.xutil.resource.ResUtils.getResources;

public class ExchangeInfosWithAli {
    public static int NumOfQuery = 0;

    public static List<NewInfo> GetAliRecommandedNewsInfos(int block) {
        String QueryString = EncapsulateString("1", NumOfQuery + "", block + "", "get again", "0", "0");
        String receive_message = RunTCP(QueryString);
        //Log.d("dyy:", receive_message);
        return DecapsulateStringToList_Recommmand(receive_message);
    }

    public static List<FloorInfo> GetAliThread(String ThreadID) {
        String QueryString = EncapsulateString("2", ThreadID, "dyy", "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_thread(receive_message);
    }


    public static void SendMyThread(String title, String content, int block) {
        String QueryString = EncapsulateString("3", title, block + "", content, "dyy", "0");
        RunTCP(QueryString);
    }

    public static void Alicomment(String UserID, String threadID, String content) {
        String QueryString = EncapsulateString("4", threadID, UserID, content, "0", "0");
        RunTCP(QueryString);
    }

    public static void AliReply(String UserID, String threadID, String content, int ReplytoFloorID) {
        String QueryString = EncapsulateString("4", threadID, UserID, content, ReplytoFloorID + "", "0");
        RunTCP(QueryString);
    }

    public static void FavourThread(String ThreadID) {
        String QueryString = EncapsulateString("5", ThreadID, "dyy", "1", "0", "0");
        RunTCP(QueryString);
    }

    public static void DisFavourThread(String ThreadID) {
        String QueryString = EncapsulateString("5", ThreadID, "dyy", "2", "0", "0");
        RunTCP(QueryString);
    }

    public static List<NewInfo> GetFavourThread() {
        String QueryString = EncapsulateString("6", "dyy", "0", "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_Favour(receive_message);
    }

    public static List<NewInfo> GetMyThread() {
        String QueryString = EncapsulateString("7", "dyy", "0", "0", "0", "0");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_Mine(receive_message);
    }

    public static void PraiseFloor(String ThreadID, int floor) {
        String QueryString = EncapsulateString("8", ThreadID, "dyy", "0", floor + "", "0");
        RunTCP(QueryString);
    }

    public static void PraiseThread(String ThreadID) {
        String QueryString = EncapsulateString("8", ThreadID, "dyy", "0", "1", "0");
        RunTCP(QueryString);
    }

    public static void DislikeThread(String ThreadID) {
        String QueryString = EncapsulateString("8", ThreadID, "dyy", "1", "1", "0");
        RunTCP(QueryString);
    }


    private static String EncapsulateString(String OperatingNumber, String Op1, String Op2, String Op3, String Op4, String Op5) {
        return OperatingNumber + "\021" + Op1 + "\021" + Op2 + "\021" + Op3 + "\021" + Op4 + "\021" + Op5 + "\021";
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
            list.add(new NewInfo("这里以后再说", temp[1])
                    .setSummary(temp[2]).setPraise(Integer.parseInt(temp[3])).setComment(Integer.parseInt(temp[5])).setThreadID(temp[0]));
        }
        return list;
    }

    private static List<NewInfo> DecapsulateStringToList_Mine(String InputString) {
        ShowLog(InputString);
        List<NewInfo> list = new ArrayList<>();
        String[] main_split = InputString.split("\023");
        if (main_split.length < 1) {
            XToastUtils.toast("好像您还没有发过帖,要不赶紧水一贴?");
            return null;
        }
        for (String retval : main_split[0].split("\022")) {
            String[] temp = retval.split("\021");
            if (temp.length < 9) continue;
            list.add(new NewInfo(temp[0], "TAG", temp[2], temp[3], get_block_name(Integer.parseInt(temp[1])),
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
            if (temp.length < 5) continue;
            list.add(new NewInfo(temp[0], "TAG", temp[2], temp[3], get_block_name(Integer.parseInt(temp[1])),
                    Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]),
                    Integer.parseInt(temp[7]), Integer.parseInt(temp[8]), temp[9]));
        }
        Log.d("dyy", InputString);
        return list;
    }

    private static List<FloorInfo> DecapsulateStringToList_thread(String InputString) {
        ShowLog(InputString);
        List<FloorInfo> list = new ArrayList<>();
        for (String retval : InputString.split("\022")) {
            String[] temp = retval.split("\021");
            if (temp.length < 5) continue;
            list.add(new FloorInfo(temp[0], temp[1], temp[2], temp[3], temp[4], Integer.parseInt(temp[5])));
        }
        return list;
    }

    private static String RunTCP(String QueryString) {
        tcp_thread_runnable tcp_one = new tcp_thread_runnable();
        tcp_one.set_text(QueryString);
        tcp_one.set_addr("47.103.6.74", 7654);
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
            case "校园":
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
            default:
                return 0;
        }
    }

    public static String get_block_name(int id) {
        switch (id) {
            case 0:
                return "校园";
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
            default:
                return "校园";
        }
    }

    private static void ShowLog(String inputstring){
        Log.d("dyy", "  \n" + inputstring.replace("\n", "*换行*")
                        .replace("\021", " \\021 ").replace("\022"," \\022\n")
                        .replace("\023", "\n\\023!!!"));
    }
}