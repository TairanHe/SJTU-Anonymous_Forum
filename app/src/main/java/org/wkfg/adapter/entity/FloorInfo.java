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

package org.wkfg.adapter.entity;

/**
 * 新闻信息
 *
 * @author Tairan He
 * @since 2019/5/23 上午12:18
 */
public class FloorInfo {
    /**
     * 楼层数
     */
    private String FloorID;
    /**
     * 说话的人的匿名的名字
     */
    private String Speakername;
    /**
     * 说这句话回复的人的名字
     */
    private String Replytoname;
    /**
     * 说这句话回复的楼层
     */
    private String Replytofloor;

    /**
     * 楼层内容
     */
    private String Context;

    /**
     * 点赞数
     */
    private int Praise;

    /**
     * 是否被点赞过
     */
    private int WhetherPraise;
    /**
     * 回复时间
     */
    private String RTime;


    public FloorInfo() {

    }

    public FloorInfo(String floorID, String speakername, String replytoname, String replytofloor,
                     String context, int praise, int whetherpraise) {
        FloorID = floorID;
        Speakername = speakername;
        Replytoname = replytoname;
        Replytofloor = replytofloor;
        Context = context;
        Praise = praise;
        WhetherPraise = whetherpraise;
    }

    public FloorInfo(String floorID, String speakername, String replytoname, String replytofloor,
                     String context, int praise, int whetherpraise, String rtime) {
        FloorID = floorID;
        Speakername = speakername;
        Replytoname = replytoname;
        Replytofloor = replytofloor;
        Context = context;
        Praise = praise;
        WhetherPraise = whetherpraise;
        RTime = rtime;
    }


    public String getFloorID() {
        return FloorID;
    }

    public FloorInfo setFloorID(String floorID) {
        FloorID = floorID;
        return this;
    }


    public String getSpeakername() {
        return Speakername;
    }

    public FloorInfo setSpeakername(String speakername) {
        Speakername = speakername;
        return this;
    }


    public String getReplytoname() {
        return Replytoname;
    }

    public FloorInfo setReplytoname(String replytoname) {
        Replytoname = replytoname;
        return this;
    }

    public String getReplytofloor() {
        return Replytofloor;
    }

    public FloorInfo setReplytofloor(String replytofloor) {
        Replytofloor = replytofloor;
        return this;
    }

    public String getContext() {
        return Context;
    }

    public FloorInfo setContext(String context) {
        Context = context;
        return this;
    }


    public int getPraise() {
        return Praise;
    }

    public FloorInfo setPraise(int praise) {
        Praise = praise;
        return this;
    }


    public int getWhetherPraise() {
        return WhetherPraise;
    }

    public FloorInfo setWhetherPraise(int whetherpraise) {
        WhetherPraise = whetherpraise;
        return this;
    }

    public String getRTime() {
        return RTime;
    }

    public FloorInfo setRTime(String rtime) {
        RTime = rtime;
        return this;
    }



    @Override
    public String toString() {
        return "NewInfo{" +
                "FloorID='" + FloorID + '\'' +
                ", Speakername='" + Speakername + '\'' +
                ", Replytoname='" + Replytoname + '\'' +
                ", Replytofloor='" + Replytofloor + '\'' +
                ", Context='" + Context + '\'' +
                ", Praise='" + Praise + '\'' +
                ", RTime=" + RTime +
                '}';
    }
}
