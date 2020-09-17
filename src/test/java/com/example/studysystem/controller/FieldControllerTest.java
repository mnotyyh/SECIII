package com.example.studysystem.controller;

import com.example.studysystem.service.field.FieldService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldControllerTest {
    FieldController f=new FieldController();
    FieldController mockF=mock(FieldController.class);
    FieldService fs=mock(FieldService.class);
    public void prepare(){f.set(fs);}

    @Test
    void getById() {
        prepare();
        mockF.getById(1);
        verify(mockF,times(1)).getById(1);
        f.getById(1);
        verify(fs,times(1)).getFieldById(1);
    }

    @Test
    void getIdByName() {
        prepare();
        mockF.getIdByName("str");
        verify(mockF,times(1)).getIdByName("str");
        f.getIdByName("str");
        verify(fs,times(1)).getIdByName("str");
    }

    @Test
    void getTop10Field() {
        prepare();
        mockF.getTop10Field(1);
        verify(mockF,times(1)).getTop10Field(1);
        f.getTop10Field(1);
        verify(fs,times(1)).getTop20Field(1);
    }

    @Test
    void getTopAuthors() {
        prepare();
        mockF.getTopAuthors(1);
        verify(mockF,times(1)).getTopAuthors(1);
        f.getTopAuthors(1);
        verify(fs,times(1)).getTopAuthors(1);
    }

    @Test
    void getTopOrgs() {
        prepare();
        mockF.getTopOrgs(1);
        verify(mockF,times(1)).getTopOrgs(1);
        f.getTopOrgs(1);
        verify(fs,times(1)).getTopOrgs(1);
    }

    @Test
    void getTopPapers() {
        prepare();
        mockF.getTopPapers(1);
        verify(mockF,times(1)).getTopPapers(1);
        f.getTopPapers(1);
        verify(fs,times(1)).getTopPapers(1);
    }

    @Test
    void getHistory() {
        prepare();
        mockF.getHistory(1);
        verify(mockF,times(1)).getHistory(1);
        f.getHistory(1);
        verify(fs,times(1)).getHistory(1);
    }

    @Test
    void getRank() {
        prepare();
        mockF.getRank(1);
        verify(mockF,times(1)).getRank(1);
        f.getRank(1);
        verify(fs,times(1)).getRank(1);
    }

    @Test
    void getRankData() {
        prepare();
        mockF.getRankData(1);
        verify(mockF,times(1)).getRankData(1);
        f.getRankData(1);
        verify(fs,times(1)).getRankData(1);
    }

    @Test
    void getCloud() {
        prepare();
        mockF.getCloud();
        verify(mockF,times(1)).getCloud();
        f.getCloud();
        verify(fs,times(1)).getCloud();
    }

    @Test
    void getField_year_change() {
        prepare();
        mockF.getField_year_change(1,2);
        verify(mockF,times(1)).getField_year_change(1,2);
        f.getField_year_change(1,2);
        verify(fs,times(1)).getField_year_change(1,2);
    }
}