package com.example.studysystem.controller;

import com.example.studysystem.service.author.AuthorService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AuthorControllerTest {
    private AuthorService mockAuthorService=mock(AuthorService.class);
    private AuthorController authorController =new AuthorController();
    private AuthorController mockAuthorController=mock(AuthorController.class);
    public void prepare(){ authorController.set(mockAuthorService);}

    @Test
    void getAuthors() {
        prepare();
        mockAuthorController.getAuthors();
        verify(mockAuthorController,times(1)).getAuthors();
        authorController.getAuthors();
        verify(mockAuthorService,times(1)).getAuthors();
    }

    @Test
    void getAuthorById() {
        prepare();
        mockAuthorController.getAuthorById(1);
        verify(mockAuthorController,times(1)).getAuthorById(1);
        authorController.getAuthorById(1);
        verify(mockAuthorService,times(1)).getAuthorById(1);
    }

    @Test
    void getIdByAuthorAndOrg() {
        prepare();
        mockAuthorController.getIdByAuthorAndOrg("str","str");
        verify(mockAuthorController,times(1)).getIdByAuthorAndOrg("str","str");
        authorController.getIdByAuthorAndOrg("str","str");
        verify(mockAuthorService,times(1)).getIdByAuthorAndOrg("str","str");
    }

    @Test
    void getTopPaper() {
        prepare();
        mockAuthorController.getTopPaper(1);
        verify(mockAuthorController,times(1)).getTopPaper(1);
        authorController.getTopPaper(1);
        verify(mockAuthorService,times(1)).getTopPaper(1);
    }

    @Test
    void getTopKeyword() {
        prepare();
        mockAuthorController.getTopKeyword(1);
        verify(mockAuthorController,times(1)).getTopKeyword(1);
        authorController.getTopKeyword(1);
        verify(mockAuthorService,times(1)).getTopKeyword(1);
    }

    @Test
    void getSimplePaperByOrg() {
        prepare();
        mockAuthorController.getSimplePaperByOrg(1);
        verify(mockAuthorController,times(1)).getSimplePaperByOrg(1);
        authorController.getSimplePaperByOrg(1);
        verify(mockAuthorService,times(1)).getSimplePaperByAuthor(1);
    }

    @Test
    void searchAuthors() {
        prepare();
        mockAuthorController.searchAuthors(1,1,"str");
        verify(mockAuthorController,times(1)).searchAuthors(1,1,"str");
        authorController.searchAuthors(1,1,"str");
        verify(mockAuthorService,times(1)).searchAuthors(1,1,"str");
    }

    @Test
    void getRelatedAuthors() {
        prepare();
        mockAuthorController.getRelatedAuthors(1,0);
        verify(mockAuthorController,times(1)).getRelatedAuthors(1,0);
        authorController.getRelatedAuthors(1,0);
        verify(mockAuthorService,times(1)).getRelatedAuthors(1,0);
    }

    @Test
    void getRelatedOrgs() {
        prepare();
        mockAuthorController.getRelatedOrgs(1);
        verify(mockAuthorController,times(1)).getRelatedOrgs(1);
        authorController.getRelatedOrgs(1);
        verify(mockAuthorService,times(1)).getRelatedOrgs(1);
    }

    @Test
    void getTop10Author() {
        prepare();
        mockAuthorController.getTop10Author(1);
        verify(mockAuthorController,times(1)).getTop10Author(1);
        authorController.getTop10Author(1);
        verify(mockAuthorService,times(1)).getTop10Author(1);
    }

    @Test
    void getHistory() {
        prepare();
        mockAuthorController.getHistory(1);
        verify(mockAuthorController,times(1)).getHistory(1);
        authorController.getHistory(1);
        verify(mockAuthorService,times(1)).getHistory(1);
    }

    @Test
    void getInterest() {
        prepare();
        mockAuthorController.getInterest(1);
        verify(mockAuthorController,times(1)).getInterest(1);
        authorController.getInterest(1);
        verify(mockAuthorService,times(1)).getInterest2(1);
    }

    @Test
    void getRencent() {
        prepare();
        mockAuthorController.getRencent(1,1);
        verify(mockAuthorController,times(1)).getRencent(1,1);
        authorController.getRencent(1,1);
        verify(mockAuthorService,times(1)).getRencent(1,1);
    }

    @Test
    void getInterest_change() {
        prepare();
        mockAuthorController.getInterest_change(1);
        verify(mockAuthorController,times(1)).getInterest_change(1);
        authorController.getInterest_change(1);
        verify(mockAuthorService,times(1)).getInterest3(1);
    }

    @Test
    void getMeeting() {
        prepare();
        mockAuthorController.getMeeting(1);
        verify(mockAuthorController,times(1)).getMeeting(1);
        authorController.getMeeting(1);
        verify(mockAuthorService,times(1)).getMeetings(1);
    }

    @Test
    void getRank() {
        prepare();
        mockAuthorController.getRank(1,1);
        verify(mockAuthorController,times(1)).getRank(1,1);
        authorController.getRank(1,1);
        verify(mockAuthorService,times(1)).getRank(1,1);
    }

    @Test
    void getRankData() {
        prepare();
        mockAuthorController.getRankData(1,1);
        verify(mockAuthorController,times(1)).getRankData(1,1);
        authorController.getRankData(1,1);
        verify(mockAuthorService,times(1)).getRankData(1,1);
    }
}