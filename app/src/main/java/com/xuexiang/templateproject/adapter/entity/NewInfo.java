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

package com.xuexiang.templateproject.adapter.entity;

/**
 * 新闻信息
 *
 * @author xuexiang
 * @since 2019/4/7 下午12:07
 */
public class NewInfo {
    /**
     * 帖子ID
     */
    private String ThreadID;

    /**
     * 所属板块
     */
    private String Block;

    /**
     * 标签
     */
    private String Tag;

    /**
     * 标题
     */
    private String Title;

    /**
     * 摘要
     */
    private String Summary;

    /**
     * 图片
     */
    private String ImageUrl;

    /**
     * 点赞数
     */
    private int Praise;

    /**
     * 点踩数
     */
    private int Dislike;

    /**
     * 评论数
     */
    private int Comment;

    /**
     * 阅读量
     */
    private int Read;

    /**
     * 新闻的详情地址
     */
    private String DetailUrl;

    /**
     * 发布时间
     */
    private String PostTime;

    /**
     * 最后更新时间
     */
    private String LastUpdateTime;

    /**
     * 是否收藏
     */
    private int WhetherFavour;

    /**
     * 是否点赞(或点踩)
     */
    private int WhetherLike;

    /**
     * 是否被删了
     */
    private int WhetherVisible;
    /**
     * 是否阅读了最新的更新 0是未读，1是已读
     */
    private int WhetherReadUpdate;

    private String AnonymousType;

    private int RandomSeed;

    private int WhetherTop;

    public NewInfo() {

    }

//    public NewInfo(String threadID, String title, String summary, String block, int praise, int dislike, int comment, int read,
//                   int whetherlike, String lastupdatetime) {
//        ThreadID = threadID;
//        Title = title;
//        Summary = summary;
//        Block = block;
//        Praise = praise;
//        Dislike = dislike;
//        Comment = comment;
//        Read = read;
//        WhetherLike = whetherlike;
//        LastUpdateTime = lastupdatetime;
//    }
//
//    public NewInfo(String threadID, String title, String summary, String block, int praise, int dislike, int comment, int read,
//                   int whetherlike, String lastupdatetime, String postTime) {
//        ThreadID = threadID;
//        Title = title;
//        Summary = summary;
//        Block = block;
//        Praise = praise;
//        Dislike = dislike;
//        Comment = comment;
//        Read = read;
//        WhetherLike = whetherlike;
//        LastUpdateTime = lastupdatetime;
//        PostTime = postTime;
//    }
    public NewInfo(String threadID, String title, String summary, String block, int praise, int dislike, int comment, int read,
                   int whetherlike, String lastupdatetime, String postTime, String anonymousType, int randomSeed) {
        ThreadID = threadID;
        Title = title;
        Summary = summary;
        Block = block;
        Praise = praise;
        Dislike = dislike;
        Comment = comment;
        Read = read;
        WhetherLike = whetherlike;
        LastUpdateTime = lastupdatetime;
        PostTime = postTime;
        AnonymousType = anonymousType;
        RandomSeed = randomSeed;
    }

    public NewInfo(String threadID, String title, String summary, String block, int praise, int dislike, int comment, int read,
                   int whetherlike, String lastupdatetime, String postTime, String anonymousType, int randomSeed, int whetherTop) {
        ThreadID = threadID;
        Title = title;
        Summary = summary;
        Block = block;
        Praise = praise;
        Dislike = dislike;
        Comment = comment;
        Read = read;
        WhetherLike = whetherlike;
        LastUpdateTime = lastupdatetime;
        PostTime = postTime;
        AnonymousType = anonymousType;
        RandomSeed = randomSeed;
        WhetherTop = whetherTop;
    }



    public NewInfo(String tag, String title, String summary, String imageUrl, String detailUrl) {
        Tag = tag;
        Title = title;
        Summary = summary;
        ImageUrl = imageUrl;
        DetailUrl = detailUrl;
    }


    public NewInfo(String tag, String title) {
        Tag = tag;
        Title = title;

//        Praise = (int) (Math.random() * 3 + 2);
//        Comment = (int) (Math.random() * 7 + 3);
//        Read = (int) (Math.random() * 8);
    }


    public String getThreadID() {
        return ThreadID;
    }

    public NewInfo setThreadID(String threadID) {
        ThreadID = threadID;
        return this;
    }

    public String getBlock() {
        return Block;
    }

    public NewInfo setBlock(String block) {
        Block = block;
        return this;
    }

    public String getTag() {
        return Tag;
    }

    public NewInfo setTag(String tag) {
        Tag = tag;
        return this;
    }

    public String getTitle() {
        return Title;
    }

    public NewInfo setTitle(String title) {
        Title = title;
        return this;
    }

    public String getSummary() {
        return Summary;
    }

    public NewInfo setSummary(String summary) {
        Summary = summary;
        return this;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public NewInfo setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
        return this;
    }

    public int getPraise() {
        return Praise;
    }

    public NewInfo setPraise(int praise) {
        Praise = praise;
        return this;
    }

    public int getDislike() {
        return Dislike;
    }

    public NewInfo setDislike(int dislike) {
        Dislike = dislike;
        return this;
    }

    public int getComment() {
        return Comment;
    }

    public NewInfo setComment(int comment) {
        Comment = comment;
        return this;
    }

    public int getRead() {
        return Read;
    }

    public NewInfo setRead(int read) {
        Read = read;
        return this;
    }

    public String getDetailUrl() {
        return DetailUrl;
    }

    public NewInfo setDetailUrl(String detailUrl) {
        DetailUrl = detailUrl;
        return this;
    }

    public int getWhetherFavour(){
        return WhetherFavour;
    }

    public NewInfo setWhetherFavour(int whetherfavour){
        WhetherFavour = whetherfavour;
        return this;
    }


    public int getWhetherLike(){
        return WhetherLike;
    }

    public int getWhetherDisLike(){
        return -WhetherLike;
    }

    public NewInfo setWhetherLike(int whetherlike){
        WhetherLike = whetherlike;
        return this;
    }


    public NewInfo setWhetherDisLike(int whetherdislike){
        WhetherLike = -whetherdislike;
        return this;
    }

    public int getWhetherVisible(){
        return WhetherVisible;
    }

    public NewInfo setWhetherVisible(int whethervisible){
        WhetherVisible = whethervisible;
        return this;
    }

    public String getPostTime(){
        return PostTime;
    }

    public NewInfo setPostTime(String posttime){
        PostTime = posttime;
        return this;
    }

    public String getAnonymousType(){
        return AnonymousType;
    }

    public NewInfo setAnonymousType(String anonymousType){
        AnonymousType = anonymousType;
        return this;
    }

    public int getRandomSeed(){
        return RandomSeed;
    }

    public NewInfo setRandomSeed(int randomSeed){
        RandomSeed = randomSeed;
        return this;
    }


    public String getLastUpdateTime(){
        return LastUpdateTime;
    }

    public NewInfo setLastUpdateTime(String lastUpdateTime){
        LastUpdateTime = lastUpdateTime;
        return this;
    }

    public NewInfo setWhetherReadUpdate(int whetherReadUpdate){
        WhetherReadUpdate = whetherReadUpdate;
        return this;
    }

    public int getWhetherReadUpdate(){
        return WhetherReadUpdate;
    }

    public NewInfo setWhetherTop(int whetherTop){
        WhetherTop = whetherTop;
        return this;
    }

    public int getWhetherTop(){
        return WhetherTop;
    }




    @Override
    public String toString() {
        return "NewInfo{" +
                "ThreadID='" + ThreadID + '\'' +
                ", Tag='" + Tag + '\'' +
                ", Title='" + Title + '\'' +
                ", Summary='" + Summary + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", Praise=" + Praise +
                ", Comment=" + Comment +
                ", Read=" + Read +
                ", DetailUrl='" + DetailUrl + '\'' +
                '}';
    }
}
