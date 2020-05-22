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

import com.xuexiang.templateproject.adapter.entity.NewInfo;

import java.util.ArrayList;
import java.util.List;

public class ExchangeInfosWithAli {
    public static int NumOfQuery = 0;
    public static List<NewInfo> GetAliRecommandedNewsInfos() {
        List<NewInfo> list = new ArrayList<>();
        String QueryString = EncapsulateString(1, NumOfQuery, 0, "发个中文看看");
        NumOfQuery += 1;
        String receive_message = RunTCP(QueryString);
        return DecapsulateStringToList(receive_message);
    }

    private static String EncapsulateString(int OperatingNumber, int Op1, int Op2, String Context) {
        return OperatingNumber + "\010" + Op1 + "\010" + Op2 + "\010" + Context;
    }

    private static List<NewInfo> DecapsulateStringToList(String InputString){
        List<NewInfo> list = new ArrayList<>();
        for (String retval: InputString.split("\011")){
            if (retval.equals("")) continue;
            String[] temp = retval.split("\010");
            list.add(new NewInfo("这里以后再说", temp[1])
                    .setSummary(temp[2]).setPraise(Integer.parseInt(temp[3])).setComment(Integer.parseInt(temp[5])).setThreadID(temp[0]));
        }
        return list;
    }

    private static String RunTCP(String QueryString) {
        tcp_thread_runnable tcp_one = new tcp_thread_runnable();
        tcp_one.set_text(QueryString);
        tcp_one.set_addr("47.103.6.74", 8000);
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
