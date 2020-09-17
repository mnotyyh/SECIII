package com.example.studysystem.dao;

import com.example.studysystem.entity.Author;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    @Transactional
    @Rollback
    void getAuthors() {
        assert authorDao!=null;
        assertNotNull(authorDao.getAuthors());
    }

    @Test
    @Transactional
    @Rollback
    void getAuthorsByOrg() {
        assert authorDao!=null;
        assertNotNull(authorDao.getAuthorsByOrg("str"));
    }

    @Test
    @Transactional
    @Rollback
    void getAuthorById() {
        assert authorDao!=null;
        assertTrue(authorDao.getAuthorById(1)==null||authorDao.getAuthorById(1).getId()>0);
    }

    @Test
    @Transactional
    @Rollback
    void getIdByAuthorAndOrg() {
        assert authorDao!=null;
        assertTrue(authorDao.getIdByAuthorAndOrg("str","str")==null||authorDao.getIdByAuthorAndOrg("str","str")>0);
    }

    @Test
    @Transactional
    @Rollback
    void searchAuthors_name() {
        assert authorDao!=null;
        assertNotNull(authorDao.searchAuthors_name("str"));
    }

    @Test
    @Transactional
    @Rollback
    void searchAuthors_org() {
        assert authorDao!=null;
        assertNotNull(authorDao.searchAuthors_org("str"));
    }

    @Test
    @Transactional
    @Rollback
    void searchAuthors_paperNum() {
        assert authorDao!=null;
        assertNotNull(authorDao.searchAuthors_paperNum(1));
    }

    @Test
    @Transactional
    @Rollback
    void searchAuthors_citationSum() {
        assert authorDao!=null;
        assertNotNull(authorDao.searchAuthors_citationSum(1));
    }

    @Test
    @Transactional
    @Rollback
    void getPaperIdByAuthor() {
        assert authorDao!=null;
        assertTrue(authorDao.getPaperIdByAuthor(1)==null||authorDao.getPaperIdByAuthor(1).length()>=1);
    }

    @Test
    @Transactional
    @Rollback
    void getKeywords() {
        assert authorDao!=null;
        assertNotNull(authorDao.getKeywords(1));
    }

    @Test
    @Transactional
    @Rollback
    void getTitles() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTitles(1));
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_paperNum() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTopAuthor_paperNum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_citationSum() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTopAuthor_citationSum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_point() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTopAuthor_point());
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_byName() {
        assert authorDao!=null;
        List<String> l=new ArrayList<>();l.add("Hello world!");
        assertNotNull(authorDao.getTopAuthor_byName(l));
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_byId() {
        assert authorDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(authorDao.getTopAuthor_byId(l));
    }

    @Test
    @Transactional
    @Rollback
    void getRelatedAuthor_byPaperId() {
        assert authorDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(authorDao.getRelatedAuthor_byPaperId(l));
    }

//    @Test
//    @Transactional
//    @Rollback
//    void getRelatedAuthor_byAuthorId_orgDif() {
//        assert authorDao!=null;
//        List<Integer> l=new ArrayList<>();l.add(1);
//        assertNotNull(authorDao.getRelatedAuthor_byAuthorId_orgDif(l,"str","str"));
//    }

    @Test
    @Transactional
    @Rollback
    void getRelatedAuthor_byOrgId() {
        assert authorDao!=null;
        assertNotNull(authorDao.getRelatedAuthor_byOrgId(1));
    }

    @Test
    @Transactional
    @Rollback
    void getHistory() {
        assert authorDao!=null;
        assertNotNull(authorDao.getHistory(1));
    }

//    @Test
//    @Transactional
//    @Rollback
//    void getTop20AuthorByPaperId() {
//        assert authorDao!=null;
//        List<Integer> l=new ArrayList<>();l.add(1);
//        assertNotNull(authorDao.getTop20AuthorByPaperId(l));
//    }

    @Test
    @Transactional
    @Rollback
    void getSimpleAuthorByPaperId() {
        assert authorDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(authorDao.getSimpleAuthorByPaperId(l));
    }

    @Test
    @Transactional
    @Rollback
    void getAuthors_concat_name_org() {
        assert authorDao!=null;
        List<String> l=new ArrayList<>();l.add("str");
        assertNotNull(authorDao.getAuthors_concat_name_org(l));
    }
}