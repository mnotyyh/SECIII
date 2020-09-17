package com.example.studysystem.service.org;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class OrgServiceTest {
    private OrgService mockOrgService=mock(OrgService.class);
    @Test
    void getOrgs() {
        mockOrgService.getOrgs();
        verify(mockOrgService,times(1)).getOrgs();
    }

    @Test
    void getOrgById() {
        mockOrgService.getOrgById(1);
        verify(mockOrgService,times(1)).getOrgById(1);
    }

    @Test
    void getIdByOrgName() {
        mockOrgService.getIdByOrgName("str");
        verify(mockOrgService,times(1)).getIdByOrgName("str");
    }

    @Test
    void getOrgsByname() {
        mockOrgService.getOrgsByname("str",1);
        verify(mockOrgService,times(1)).getOrgsByname("str",1);
    }

    @Test
    void getSimplePaperByOrg() {
        mockOrgService.getSimplePaperByOrg(1);
        verify(mockOrgService,times(1)).getSimplePaperByOrg(1);
    }

    @Test
    void searchOrgs() {
        mockOrgService.searchOrgs("Hello world!","1");
        verify(mockOrgService,times(1)).searchOrgs("Hello world!","1");
    }


    @Test
    void getTopPaper() {
        mockOrgService.getTopPaper(1);
        verify(mockOrgService,times(1)).getTopPaper(1);
    }

    @Test
    void getTopKeyword() {
        mockOrgService.getTopKeyword(1);
        verify(mockOrgService,times(1)).getTopKeyword(1);
    }

    @Test
    void getTopAuthor() {
        mockOrgService.getTopAuthor(1);
        verify(mockOrgService,times(1)).getTopAuthor(1);
    }

    @Test
    void getTop10Org() {
        mockOrgService.getTop10Org(anyInt());
        verify(mockOrgService,times(1)).getTop10Org(anyInt());
    }

    @Test
    void getRelatedAuthors() {
        mockOrgService.getRelatedAuthors(1);
        verify(mockOrgService,times(1)).getRelatedAuthors(1);
    }

    @Test
    void getRelatedOrgs() {
        mockOrgService.getRelatedOrgs(1);
        verify(mockOrgService,times(1)).getRelatedOrgs(1);
    }

    @Test
    void getHistory() {
        mockOrgService.getHistory(1);
        verify(mockOrgService,times(1)).getHistory(1);
    }

    @Test
    void getInterest() {
        mockOrgService.getInterest(1);
        verify(mockOrgService,times(1)).getInterest(1);
    }
}