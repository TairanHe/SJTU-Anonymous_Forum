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

import com.xuexiang.templateproject.adapter.entity.FloorInfo;
import com.xuexiang.templateproject.adapter.entity.NewInfo;

import java.util.ArrayList;
import java.util.List;

public class ExchangeInfosWithAli {
    public static int NumOfQuery = 0;

    public static List<NewInfo> GetAliRecommandedNewsInfos() {
        String QueryString = EncapsulateString(1, NumOfQuery + "", 0, "get");
        NumOfQuery += 1;
        String receive_message = RunTCP(QueryString);
        Log.d("dyy:", receive_message);
        return DecapsulateStringToList_recommmend(receive_message);
    }

    public static List<FloorInfo> GetAliThread(String ThreadID) {
        String QueryString = EncapsulateString(2, ThreadID, 0, "get");
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList_thread(receive_message);
    }

    public static void SendMyThread(String title, String content){
        String QueryString = EncapsulateString(3, title, 0, content);
        RunTCP(QueryString);
    }

    public static void SendMyReply(int threadID, int replytoID, String content){
        String QueryString = EncapsulateString(4, threadID + "", replytoID , content);
        RunTCP(QueryString);
    }

    private static String EncapsulateString(int OperatingNumber, String Op1, int Op2, String Context) {
        return OperatingNumber + "\021" + Op1 + "\021" + Op2 + "\021" + Context;
    }

    private static List<NewInfo> DecapsulateStringToList_recommmend(String InputString) {
        Log.d("dyy:", InputString);
        List<NewInfo> list = new ArrayList<>();
        String[] main_split = InputString.split("\023");
        Log.d("dyy:", (main_split[0].replace("\021", " Z ")).replace("\022", " Y "));
        for (String retval : main_split[0].split("\022")) {
            if (retval.equals("")) continue;
            Log.d("dyy:", retval.replace("\021", " Z "));
            String[] temp = retval.split("\021");
            list.add(new NewInfo("这里以后再说", temp[1])
                    .setSummary(temp[2]).setPraise(Integer.parseInt(temp[3])).setComment(Integer.parseInt(temp[5])).setThreadID(temp[0]));
        }
        if (main_split.length > 1) {
            NumOfQuery = 1;
            XToastUtils.toast("帖子只有这么多了,要不再来一遍?");
        }
        return list;
    }

    private static List<FloorInfo> DecapsulateStringToList_thread(String InputString) {
        List<FloorInfo> list = new ArrayList<>();
        for (String retval : InputString.split("\022")) {
            //Log.d("dyy:", retval.replace("\021", "Z"));
            if (retval.equals("")) continue;
            String[] temp = retval.split("\021");
            //Log.d("dyy:", temp.length + "");
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
}