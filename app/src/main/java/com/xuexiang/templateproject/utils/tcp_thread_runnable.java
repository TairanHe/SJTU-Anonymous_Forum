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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class tcp_thread_runnable implements Runnable {
    private String text, ip_addr;
    private int port;
    private String receive_text;
    public void set_addr(String in_ip_addr, int in_port){
        ip_addr = in_ip_addr;
        port = in_port;
    }

    public void set_text(String in_text){
        text = in_text;
    }

    public String get_receive_text() {
        return receive_text;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ip_addr, port);
            OutputStream out = socket.getOutputStream();
            out.write(text.getBytes());
            // 读取服务端返回的数据，使用 Socket 读取流
            InputStream in = socket.getInputStream();
            byte[] buf = new byte[20480];
            int len = in.read(buf);
            receive_text = new String(buf, 0, len);
            socket.close();
        }catch (IOException e){
            e.printStackTrace();;
        }
    }
}
