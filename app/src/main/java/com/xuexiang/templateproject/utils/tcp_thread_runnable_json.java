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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.json.*;



public class tcp_thread_runnable_json implements Runnable {
    private String ip_addr;
    private int port;
    private JSONObject text;
    public JSONObject receive_text;
    public void set_addr(String in_ip_addr, int in_port){
        ip_addr = in_ip_addr;
        port = in_port;
    }

    public void set_text(JSONObject in_text){
        text = in_text;
    }

    public JSONObject get_receive_text() {
        return receive_text;
    }

    JSONException je;
    IOException ie;

    @Override
    public void run(){
        je = null;
        ie = null;
        try {
//            int len = -1;
//            byte[] buf = new byte[20480];
//            int exp = 1;
            String strInputstream = "";
            Socket socket = new Socket(ip_addr, port);
            OutputStream out = socket.getOutputStream();
            out.write(text.toString().getBytes());
            System.out.println("发送给服务器的信息为："+ text);
            out.flush();
            // 读取服务端返回的数据，使用 Socket 读取流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            strInputstream = br.readLine();
            socket.close();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                receive_text  = new JSONObject(strInputstream);
            } catch (JSONException err){
                je = err;
                Log.d("Error", err.toString());
            }
        }catch (IOException err){
            ie = err;
            Log.d("Error", err.toString());
        }
    }
}

