package com.example.studysystem.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class OrgDaoTest {
    @Autowired
    private OrgDao orgDao;

    @Test
    @Transactional
    @Rollback
    void getOrgById() {
        assert orgDao!=null;
        assertTrue(orgDao.getOrgById(1)==null||orgDao.getOrgById(1).getId()>0);
    }

    @Test
    @Transactional
    @Rollback
    void getOrgsByname() {
        assert orgDao!=null;
        assertNotNull(orgDao.getOrgsByname("str"));
    }

    @Test
    @Transactional
    @Rollback
    void getIdByOrgName() {
        assert orgDao!=null;
        assertTrue(orgDao.getIdByOrgName("str")==null||orgDao.getIdByOrgName("str")>=0);
    }

    @Test
    @Transactional
    @Rollback
    void getOrg_nameByPaperId() {
        assert orgDao!=null;
        List<Integer>l=new ArrayList<>();l.add(1);
        assertNotNull(orgDao.getOrg_nameByPaperId(l));
    }

    @Test
    @Transactional
    @Rollback
    void getOrgsByNameList() {
        assert orgDao!=null;
        List<String>l=new ArrayList<>();l.add("str");
        assertNotNull(orgDao.getOrgsByNameList(l));
    }

    @Test
    @Transactional
    @Rollback
    void getOrgByname() {
        assert orgDao!=null;
        assertTrue(orgDao.getOrgByname("str")==null||orgDao.getOrgByname("str").getId()>0);
    }

    @Test
    @Transactional
    @Rollback
    void getOrgs() {
        assert orgDao!=null;
        assertNotNull(orgDao.getOrgs());
    }

    @Test
    @Transactional
    @Rollback
    void searchOrgs() {
        assert orgDao!=null;
        assertNotNull(orgDao.searchOrgs("Hello world!",1));
    }

    @Test
    @Transactional
    @Rollback
    void getPaperIdByOrg() {
        assert orgDao!=null;
        assertTrue(orgDao.getPaperIdByOrg(1)==null||orgDao.getPaperIdByOrg(1).length()>0);
    }

    @Test
    @Transactional
    @Rollback
    void getTopPaperIds() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTopPaperIds(1));
    }

    @Test
    @Transactional
    @Rollback
    void getKeywords() {
        assert orgDao!=null;
        assertNotNull(orgDao.getKeywords(1));
    }

    @Test
    @Transactional
    @Rollback
    void getTitles() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTitles(1));
    }

    @Test
    @Transactional
    @Rollback
    void getAuthors() {
        assert orgDao!=null;
        assertTrue(orgDao.getAuthors(1)==null||orgDao.getAuthors(1).length()>0);
    }

    @Test
    @Transactional
    @Rollback
    void getTopOrg_paperNum() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTopOrg_paperNum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopOrg_citationSum() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTopOrg_citationSum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopOrg_point() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTopOrg_point());
    }

    @Test
    @Transactional
    @Rollback
    void getTopOrg() {
        assert orgDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(orgDao.getTopOrg(l));
    }

    @Test
    @Transactional
    @Rollback
    void getRelatedOrg_byAuthorId() {
        assert orgDao!=null;
        assertNotNull(orgDao.getRelatedOrg_byAuthorId(1));
    }

    @Test
    @Transactional
    @Rollback
    void getRelatedOrg_byOrgId() {
        assert orgDao!=null;
        assertNotNull(orgDao.getRelatedOrg_byOrgId(1));
    }

    @Test
    @Transactional
    @Rollback
    void getHistory() {
        assert orgDao!=null;
        assertNotNull(orgDao.getHistory(1));
    }
}