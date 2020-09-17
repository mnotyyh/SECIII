package com.example.studysystem.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class ViewControllerTest {
    private ViewController mockViewController=mock(ViewController.class);

    @Test
    void jumpToFirst() {
        mockViewController.jumpToFirst();
        verify(mockViewController,times(1)).jumpToFirst();
    }

    @Test
    void jumpToSignIn() {
        mockViewController.jumpToSignIn();
        verify(mockViewController,times(1)).jumpToSignIn();
    }

    @Test
    void jumpToHome() {
        mockViewController.jumpToHome();
        verify(mockViewController,times(1)).jumpToHome();
    }

    @Test
    void jumpToManage() {
        mockViewController.jumpToManage();
        verify(mockViewController,times(1)).jumpToManage();
    }

    @Test
    void jumpToOnlinePython() {
        mockViewController.jumpToOnlinePython();
        verify(mockViewController,times(1)).jumpToOnlinePython();
    }

    @Test
    void jumpToAdmin() {
        mockViewController.jumpToAdmin();
        verify(mockViewController,times(1)).jumpToAdmin();
    }

    @Test
    void jumpToAuthor() {
        mockViewController.jumpToAuthor();
        verify(mockViewController,times(1)).jumpToAuthor();
    }

    @Test
    void jumpToOrg() {
        mockViewController.jumpToOrg();
        verify(mockViewController,times(1)).jumpToOrg();
    }

    @Test
    void jumpToField() {
        mockViewController.jumpToField();
        verify(mockViewController,times(1)).jumpToField();
    }

    @Test
    void jumpToFieldDetail() {
        mockViewController.jumpToFieldDetail();
        verify(mockViewController,times(1)).jumpToFieldDetail();
    }

    @Test
    void jumpToMeeting() {
        mockViewController.jumpToMeeting();
        verify(mockViewController,times(1)).jumpToMeeting();
    }

    @Test
    void jumpToPaperDetail() {
        mockViewController.jumpToPaperDetail();
        verify(mockViewController,times(1)).jumpToPaperDetail();
    }

    @Test
    void jumpToOrgDetail() {
        mockViewController.jumpToOrgDetail();
        verify(mockViewController,times(1)).jumpToOrgDetail();
    }

    @Test
    void jumpToAuthorDetail() {
        mockViewController.jumpToAuthorDetail();
        verify(mockViewController,times(1)).jumpToAuthorDetail();
    }

    @Test
    void jumpToAuthorDetailStatisticsContent() {
        mockViewController.jumpToAuthorDetailStatisticsContent();
        verify(mockViewController,times(1)).jumpToAuthorDetailStatisticsContent();
    }

    @Test
    void jumpToAuthorDetailStatistics() {
        mockViewController.jumpToAuthorDetailStatistics();
        verify(mockViewController,times(1)).jumpToAuthorDetailStatistics();
    }

    @Test
    void jumpToAuthorDetailInterest() {
        mockViewController.jumpToAuthorDetailInterest();
        verify(mockViewController,times(1)).jumpToAuthorDetailInterest();
    }

    @Test
    void jumpToAuthorDetailRelation() {
        mockViewController.jumpToAuthorDetailRelation();
        verify(mockViewController,times(1)).jumpToAuthorDetailRelation();
    }

    @Test
    void jumpToAuthorDetailRank() {
        mockViewController.jumpToAuthorDetailRank();
        verify(mockViewController,times(1)).jumpToAuthorDetailRank();
    }

    @Test
    void jumpToMeetingDetail() {
        mockViewController.jumpToMeetingDetail();
        verify(mockViewController,times(1)).jumpToMeetingDetail();
    }
}