package com.example.studysystem.service.author;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceImplTest {
    private AuthorDao mockAuthorDao=mock(AuthorDao.class);
    private OrgDao mockOrgDao=mock(OrgDao.class);
    private PaperDao mockPaperDao=mock(PaperDao.class);
    private SimplePaperDao mockSimplePaperDao=mock(SimplePaperDao.class);
    private AuthorServiceImpl authorService=new AuthorServiceImpl();
    private AuthorServiceImpl mockAuthorService=mock(AuthorServiceImpl.class);

    public void prepare(){authorService.set(mockAuthorDao,mockOrgDao,mockPaperDao,mockSimplePaperDao);}
    @Test
    void getAuthors() {
        prepare();
        mockAuthorService.getAuthors();
        verify(mockAuthorService,times(1)).getAuthors();
        when(mockAuthorDao.getAuthors()).thenThrow(new RuntimeException());
        assertEquals("失败", authorService.getAuthors().getMessage());
        verify(mockAuthorDao,times(1)).getAuthors();
    }

    @Test
    void getAuthorById() {
        prepare();
        mockAuthorService.getAuthorById(1);
        verify(mockAuthorService,times(1)).getAuthorById(1);
        when(mockAuthorDao.getAuthorById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getAuthorById(1).getMessage());
        verify(mockAuthorDao,times(1)).getAuthorById(1);
    }

    @Test
    void getIdByAuthorAndOrg() {
        prepare();
        mockAuthorService.getIdByAuthorAndOrg("str","str");
        verify(mockAuthorService,times(1)).getIdByAuthorAndOrg("str","str");
        when(mockAuthorDao.getIdByAuthorAndOrg("str","str")).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getIdByAuthorAndOrg("str","str").getMessage());
        verify(mockAuthorDao,times(1)).getIdByAuthorAndOrg("str","str");
    }

    @Test
    void getSimplePaperByAuthor() {
        prepare();
        mockAuthorService.getSimplePaperByAuthor(1);
        verify(mockAuthorService,times(1)).getSimplePaperByAuthor(1);
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getSimplePaperByAuthor(1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void searchAuthors() {
        prepare();
        mockAuthorService.searchAuthors(1,1,"str");
        verify(mockAuthorService,times(1)).searchAuthors(1,1,"str");
        ArrayList<Author> authors=new ArrayList<Author>();authors.add(new Author());
        when(mockAuthorDao.searchAuthors_org("str")).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.searchAuthors(1,1,"str").getMessage());
        verify(mockAuthorDao,times(1)).searchAuthors_org("str");
    }

    @Test
    void getTopPaper() {
        prepare();
        mockAuthorService.getTopPaper(1);
        verify(mockAuthorService,times(1)).getTopPaper(1);
        Author a=new Author();a.setOrg("str");a.setAuthor_name("str");
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        when(mockAuthorDao.getAuthorById(1)).thenReturn(a);
        when(mockSimplePaperDao.getSimplePaperByAuthor_Org("str","str")).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getTopPaper(1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockAuthorDao,times(1)).getAuthorById(1);
        verify(mockSimplePaperDao,times(1)).getSimplePaperByAuthor_Org("str","str");
    }

    @Test
    void getTopKeyword() {
        prepare();
        mockAuthorService.getTopKeyword(1);
        verify(mockAuthorService,times(1)).getTopKeyword(1);
        when(mockAuthorDao.getKeywords(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getTopKeyword(1).getMessage());
        verify(mockAuthorDao,times(1)).getKeywords(1);
    }

    @Test
    void getTop10Author() {
        prepare();
        mockAuthorService.getTop10Author(1);
        verify(mockAuthorService,times(1)).getTop10Author(1);
        when(mockAuthorDao.getTopAuthor_paperNum()).thenThrow(new RuntimeException());
        when(mockAuthorDao.getTopAuthor_citationSum()).thenReturn(null);
        when(mockAuthorDao.getTopAuthor_point()).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getTop10Author(1).getMessage());
        verify(mockAuthorDao,times(1)).getTopAuthor_paperNum();
        assertEquals(null,authorService.getTop10Author(2).getContent());
        verify(mockAuthorDao,times(1)).getTopAuthor_citationSum();
        assertEquals("失败",authorService.getTop10Author(3).getMessage());
        verify(mockAuthorDao,times(1)).getTopAuthor_point();
    }

    @Test
    void getRelatedAuthors() {
        prepare();
        mockAuthorService.getRelatedAuthors(1,1);
        verify(mockAuthorService,times(1)).getRelatedAuthors(1,1);
        ArrayList<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockAuthorDao.getAuthorById(1)).thenReturn(new Author());
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        when(mockAuthorDao.getRelatedAuthor_byPaperId(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getRelatedAuthors(1,1).getMessage());
        verify(mockAuthorDao,times(1)).getAuthorById(1);
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockAuthorDao,times(1)).getRelatedAuthor_byPaperId(l);
    }

    @Test
    void getRelatedOrgs() {
        prepare();
        mockAuthorService.getRelatedOrgs(1);
        verify(mockAuthorService,times(1)).getRelatedOrgs(1);
        when(mockOrgDao.getRelatedOrg_byAuthorId(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getRelatedOrgs(1).getMessage());
        verify(mockOrgDao,times(1)).getRelatedOrg_byAuthorId(1);
    }

    @Test
    void getHistory() {
        prepare();
        mockAuthorService.getHistory(1);
        verify(mockAuthorService,times(1)).getHistory(1);
        when(mockAuthorDao.getHistory(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getHistory(1).getMessage());
        verify(mockAuthorDao,times(1)).getHistory(1);
    }

    @Test
    void getInterest() {
        prepare();
        mockAuthorService.getInterest(1);
        verify(mockAuthorService,times(1)).getInterest(1);
        when(mockAuthorDao.getTitles(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getInterest(1).getMessage());
        verify(mockAuthorDao,times(1)).getTitles(1);
    }

    @Test
    void getInterest2() {
        prepare();
        mockAuthorService.getInterest2(1);
        verify(mockAuthorService,times(1)).getInterest2(1);
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        ArrayList<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getInterest2(1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void getRencent() {
        prepare();
        mockAuthorService.getRencent(1,1);
        verify(mockAuthorService,times(1)).getRencent(1,1);
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        ArrayList<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getRencent(1,1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void getInterest3() {
        prepare();
        mockAuthorService.getInterest3(1);
        verify(mockAuthorService,times(1)).getInterest3(1);
        ArrayList<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getInterest3(1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void getMeetings() {
        prepare();
        mockAuthorService.getMeetings(1);
        verify(mockAuthorService,times(1)).getMeetings(1);
        ArrayList<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        when(mockPaperDao.getSimpleMeetingByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getMeetings(1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockPaperDao,times(1)).getSimpleMeetingByIds(l);
    }

    @Test
    void getRank() {
        prepare();
        mockAuthorService.getRank(1,1);
        verify(mockAuthorService,times(1)).getRank(1,1);
        when(mockAuthorDao.getAuthorById(1)).thenReturn(new Author());
        when(mockAuthorDao.getAuthors()).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getRank(1,1).getMessage());
        verify(mockAuthorDao,times(1)).getAuthorById(1);
        verify(mockAuthorDao,times(1)).getAuthors();
    }

    @Test
    void getRankData() {
        prepare();
        mockAuthorService.getRankData(1,1);
        verify(mockAuthorService,times(1)).getRankData(1,1);
        when(mockAuthorDao.getAuthorById(1)).thenReturn(new Author());
        when(mockAuthorDao.getAuthors()).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getRankData(1,1).getMessage());
        verify(mockAuthorDao,times(1)).getAuthorById(1);
        verify(mockAuthorDao,times(1)).getAuthors();
    }

    @Test
    void sortByValueDescending() {
        Map<String,Integer> m1=new TreeMap<>();
        m1.put("a",1);m1.put("b",8);m1.put("c",6);m1.put("d",2);m1.put("e",5);m1.put("f",7);m1.put("g",10);
        Map<String,Integer> m2=new TreeMap<>();
        m2.put("g",10);m2.put("b",8);m2.put("f",7);m2.put("c",6);m2.put("e",5);m2.put("d",2);m2.put("a",1);
        mockAuthorService.sortByValueDescending(m1);
        verify(mockAuthorService,times(1)).sortByValueDescending(m1);
        assertEquals(m2,authorService.sortByValueDescending(m1));
    }
}