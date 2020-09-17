package com.example.studysystem.service.author;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AuthorServiceTest {
    private AuthorService mockAuthorService=mock(AuthorService.class);

    @Test
    void getAuthors() {
        mockAuthorService.getAuthors();
        verify(mockAuthorService,times(1)).getAuthors();
    }

    @Test
    void getAuthorById() {
        mockAuthorService.getAuthorById(1);
        verify(mockAuthorService,times(1)).getAuthorById(1);
    }

    @Test
    void getIdByAuthorAndOrg() {
        mockAuthorService.getIdByAuthorAndOrg("str","str");
        verify(mockAuthorService,times(1)).getIdByAuthorAndOrg("str","str");
    }

    @Test
    void getSimplePaperByAuthor() {
        mockAuthorService.getSimplePaperByAuthor(1);
        verify(mockAuthorService,times(1)).getSimplePaperByAuthor(1);
    }

    @Test
    void searchAuthors() {
        mockAuthorService.searchAuthors(1,1,"str");
        verify(mockAuthorService,times(1)).searchAuthors(1,1,"str");
    }

    @Test
    void getTopPaper() {
        mockAuthorService.getTopPaper(1);
        verify(mockAuthorService,times(1)).getTopPaper(1);
    }

    @Test
    void getTopKeyword() {
        mockAuthorService.getTopKeyword(1);
        verify(mockAuthorService,times(1)).getTopKeyword(1);
    }

    @Test
    void getTop10Author() {
        mockAuthorService.getTop10Author(1);
        verify(mockAuthorService,times(1)).getTop10Author(1);
    }

    @Test
    void getRelatedAuthors() {
        mockAuthorService.getRelatedAuthors(1,0);
        verify(mockAuthorService,times(1)).getRelatedAuthors(1,0);
    }

    @Test
    void getRelatedOrgs() {
        mockAuthorService.getRelatedOrgs(1);
        verify(mockAuthorService,times(1)).getRelatedOrgs(1);
    }

    @Test
    void getHistory() {
        mockAuthorService.getHistory(1);
        verify(mockAuthorService,times(1)).getHistory(1);
    }

    @Test
    void getInterest() {
        mockAuthorService.getInterest(1);
        verify(mockAuthorService,times(1)).getInterest(1);
    }

    @Test
    void getInterest2() {
        mockAuthorService.getInterest2(1);
        verify(mockAuthorService,times(1)).getInterest2(1);
    }

    @Test
    void getRencent() {
        mockAuthorService.getRencent(1,1);
        verify(mockAuthorService,times(1)).getRencent(1,1);
    }

    @Test
    void getInterest3() {
        mockAuthorService.getInterest3(1);
        verify(mockAuthorService,times(1)).getInterest3(1);
    }

    @Test
    void getMeetings() {
        mockAuthorService.getMeetings(1);
        verify(mockAuthorService,times(1)).getMeetings(1);
    }

    @Test
    void getRank() {
        mockAuthorService.getRank(1,1);
        verify(mockAuthorService,times(1)).getRank(1,1);
    }

    @Test
    void getRankData() {
        mockAuthorService.getRankData(1,1);
        verify(mockAuthorService,times(1)).getRankData(1,1);
    }
}