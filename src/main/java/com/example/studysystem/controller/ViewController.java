package com.example.studysystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/view")
public class ViewController {
    @RequestMapping(value = "/first")
    public String jumpToFirst(){
        return "index.html";
    }
    @RequestMapping(value="/adminSignIn")
    public String jumpToSignIn(){return "adminSignIn.html";}
    @RequestMapping(value = "/home")
    public String jumpToHome(){
        return "home.html";
    }
    @RequestMapping(value = "/manage")
    public String jumpToManage(){
        return "manage.html";
    }
    @RequestMapping(value = "/onlinePython")
    public String jumpToOnlinePython(){
        return "onlinePython.html";
    }

    @RequestMapping(value = "/admin")
    public String jumpToAdmin(){
        return "adminIndex.html";
    }

    @RequestMapping(value = "/author")
    public String jumpToAuthor(){
        return "author.html";
    }

    @RequestMapping(value = "/org")
    public String jumpToOrg(){
        return "org.html";
    }

    @RequestMapping(value = "/field")
    public String jumpToField(){
        return "field.html";
    }

    @RequestMapping(value = "/field-detail")
    public String jumpToFieldDetail(){
        return "fieldDetail.html";
    }

    @RequestMapping(value = "/meeting")
    public String jumpToMeeting(){
        return "meeting.html";
    }

    @RequestMapping(value = "/paper-detail")
    public String jumpToPaperDetail(){
        return "paperDetail.html";
    }

    @RequestMapping(value = "/org-detail")
    public String jumpToOrgDetail(){
        return "orgDetail.html";
    }

    @RequestMapping(value = "/author-detail")
    public String jumpToAuthorDetail(){
        return "authorDetail.html";
    }

    @RequestMapping(value = "/author-detail-normal")
    public String jumpToAuthorDetailStatisticsContent(){ return "authorDetail-normal.html"; }

    @RequestMapping(value = "/author-detail-statistics")
    public String jumpToAuthorDetailStatistics(){ return "authorDetail-statistics.html"; }

    @RequestMapping(value = "/author-detail-interest")
    public String jumpToAuthorDetailInterest(){ return "authorDetail-interest.html"; }

    @RequestMapping(value = "/author-detail-relation")
    public String jumpToAuthorDetailRelation(){ return "authorDetail-relation.html"; }

    @RequestMapping(value = "/author-detail-rank")
    public String jumpToAuthorDetailRank(){ return "authorDetail-rank.html"; }

    @RequestMapping(value = "/meeting-detail")
    public String jumpToMeetingDetail(){
        return "meetingDetail.html";
    }
}
