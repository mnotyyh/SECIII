package com.example.studysystem.service.field;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.FieldDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.SimpleAuthor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {
    private FieldServiceImpl mockFieldServiceImpl=mock(FieldServiceImpl.class);
    private FieldServiceImpl fieldService=new FieldServiceImpl();
    private FieldDao mockFieldDao=mock(FieldDao.class);
    private PaperDao mockPaperDao=mock(PaperDao.class);
    private OrgDao mockOrgDao=mock(OrgDao.class);
    private AuthorDao mockAuthorDao=mock(AuthorDao.class);
    public void prepare(){fieldService.set(mockFieldDao,mockPaperDao,mockOrgDao,mockAuthorDao);}
    @Test
    void getFields() {
        prepare();
        mockFieldServiceImpl.getFields();
        verify(mockFieldServiceImpl,times(1)).getFields();
        when(mockFieldDao.getFields()).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getFields().getMessage());
        verify(mockFieldDao,times(1)).getFields();
    }

    @Test
    void getTop20Field() {
        prepare();;
        mockFieldServiceImpl.getTop20Field(anyInt());
        verify(mockFieldServiceImpl,times(1)).getTop20Field(anyInt());
        when(mockFieldDao.getTopField_paperNum()).thenThrow(new RuntimeException());
        when(mockFieldDao.getTopField_citationSum()).thenReturn(null);
        when(mockFieldDao.getTopField_point()).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTop20Field(1).getMessage());
        verify(mockFieldDao,times(1)).getTopField_paperNum();
        assertEquals(null,fieldService.getTop20Field(2).getContent());
        verify(mockFieldDao,times(1)).getTopField_citationSum();
        assertEquals("失败",fieldService.getTop20Field(3).getMessage());
        verify(mockFieldDao,times(1)).getTopField_point();
    }

    @Test
    void getIdByName() {
        prepare();
        mockFieldServiceImpl.getIdByName("str");
        verify(mockFieldServiceImpl,times(1)).getIdByName("str");
        when(mockFieldDao.getIdByName("str")).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getIdByName("str").getMessage());
        verify(mockFieldDao,times(1)).getIdByName("str");
    }

    @Test
    void getFieldById() {
        prepare();
        mockFieldServiceImpl.getFieldById(1);
        verify(mockFieldServiceImpl,times(1)).getFieldById(1);
        when(mockFieldDao.getFieldById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getFieldById(1).getMessage());
        verify(mockFieldDao,times(1)).getFieldById(1);
    }

    @Test
    void getCloud() {
        prepare();
        mockFieldServiceImpl.getCloud();
        verify(mockFieldServiceImpl,times(1)).getCloud();
        when(mockFieldDao.getFields()).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getCloud().getMessage());
        verify(mockFieldDao,times(1)).getFields();
    }

    @Test
    void getField_year_change() {
        prepare();
        mockFieldServiceImpl.getField_year_change(1,2);
        verify(mockFieldServiceImpl,times(1)).getField_year_change(1,2);
        when(mockFieldDao.getTopField_paperNum()).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getField_year_change(1,2).getMessage());
        verify(mockFieldDao,times(1)).getTopField_paperNum();
    }

    @Test
    void getHistory() {
        prepare();
        mockFieldServiceImpl.getHistory(1);
        verify(mockFieldServiceImpl,times(1)).getHistory(1);
        when(mockFieldDao.getPaperId(1)).thenReturn("1;2;3");
        ArrayList<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getHistory(1).getMessage());
        verify(mockFieldDao,times(1)).getPaperId(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void getRank() {
        prepare();
        mockFieldServiceImpl.getRank(1);
        verify(mockFieldServiceImpl,times(1)).getRank(1);
        when(mockFieldDao.getFields()).thenReturn(null);
        when(mockFieldDao.getFieldById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getRank(1).getMessage());
        verify(mockFieldDao,times(1)).getFields();
        verify(mockFieldDao,times(1)).getFieldById(1);
    }

    @Test
    void getRankData() {
        prepare();
        mockFieldServiceImpl.getRankData(1);
        verify(mockFieldServiceImpl,times(1)).getRankData(1);
        when(mockFieldDao.getFields()).thenReturn(null);
        when(mockFieldDao.getFieldById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getRankData(1).getMessage());
        verify(mockFieldDao,times(1)).getFields();
        verify(mockFieldDao,times(1)).getFieldById(1);
    }

    @Test
    void getTopAuthors() {
        prepare();
        mockFieldServiceImpl.getTopAuthors(1);
        verify(mockFieldServiceImpl,times(1)).getTopAuthors(1);
        when(mockFieldDao.getPaperId(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        List<SimpleAuthor> ll=new ArrayList<>();
        when(mockAuthorDao.getSimpleAuthorByPaperId(l)).thenReturn(ll);
        when(mockAuthorDao.getAuthors_concat_name_org(anyList())).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTopAuthors(1).getMessage());
        verify(mockFieldDao,times(1)).getPaperId(1);
        verify(mockAuthorDao,times(1)).getSimpleAuthorByPaperId(l);
        verify(mockAuthorDao,times(1)).getAuthors_concat_name_org(anyList());
    }

    @Test
    void getTopOrgs() {
        prepare();
        mockFieldServiceImpl.getTopOrgs(1);
        verify(mockFieldServiceImpl,times(1)).getTopOrgs(1);
        when(mockFieldDao.getPaperId(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        List<String> ll=new ArrayList<>();
        when(mockOrgDao.getOrg_nameByPaperId(l)).thenReturn(ll);
        when(mockOrgDao.getOrgsByNameList(anyList())).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTopOrgs(1).getMessage());
        verify(mockFieldDao,times(1)).getPaperId(1);
        verify(mockOrgDao,times(1)).getOrg_nameByPaperId(l);
        verify(mockOrgDao,times(1)).getOrgsByNameList(anyList());
    }

    @Test
    void getTopPapers() {
        prepare();
        mockFieldServiceImpl.getTopPapers(1);
        verify(mockFieldServiceImpl,times(1)).getTopPapers(1);
        when(mockFieldDao.getPaperId(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();
        l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getTopPaper(l)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTopPapers(1).getMessage());
        verify(mockFieldDao,times(1)).getPaperId(1);
        verify(mockPaperDao,times(1)).getTopPaper(l);
    }
}