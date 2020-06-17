package com.xuexiang.templateproject.adapter.entity;

public class MessageInfo {
    private String ThreadID;
    private String Title;
    private String Summary;
    private int Type;   //0为有人回复,非0为由多少人点赞
    private int Judge;  //1为已读
    private int JudgeExtend;
    private String PostTime;

    public MessageInfo(String threadid, String title, int type, int judge, String posttime, String summary) {
        ThreadID = threadid;
        Title = title;
        Type = type;
        Judge = judge;
        PostTime = posttime;
        Summary = summary;
    }

    public MessageInfo(String threadid, String title, int type, int judge, int judgeextend) {
        ThreadID = threadid;
        Title = title;
        Type = type;
        Judge = judge;
        JudgeExtend = judgeextend;
    }


    public String getThreadID() {
        return ThreadID;
    }

    public String getTitle() {
        return Title;
    }

    public int getType() {
        return Type;
    }

    public int getJudge() {
        return Judge;
    }

    public int getJudgeExtend() {
        return JudgeExtend;
    }

    public String getPostTime() {
        return PostTime;
    }

    public String getSummary(){
        return Summary;
    }
}
