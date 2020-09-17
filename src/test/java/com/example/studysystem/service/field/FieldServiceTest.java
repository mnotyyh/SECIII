package com.example.studysystem.service.field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceTest {
    private FieldService mockFieldService=mock(FieldService.class);
    @Test
    void getFields() {
        mockFieldService.getFields();
        verify(mockFieldService,times(1)).getFields();
    }

    @Test
    void getTop20Field() {
        mockFieldService.getTop20Field(anyInt());
        verify(mockFieldService,times(1)).getTop20Field(anyInt());
    }

    @Test
    void getIdByName() {
        mockFieldService.getIdByName("str");
        verify(mockFieldService,times(1)).getIdByName("str");
    }

    @Test
    void getFieldById() {
        mockFieldService.getFieldById(1);
        verify(mockFieldService,times(1)).getFieldById(1);
    }

    @Test
    void getCloud() {
        mockFieldService.getCloud();
        verify(mockFieldService,times(1)).getCloud();
    }

    @Test
    void getField_year_change() {
        mockFieldService.getField_year_change(1,2);
        verify(mockFieldService,times(1)).getField_year_change(1,2);
    }

    @Test
    void getHistory() {
        mockFieldService.getHistory(1);
        verify(mockFieldService,times(1)).getHistory(1);
    }

    @Test
    void getRank() {
        mockFieldService.getRank(1);
        verify(mockFieldService,times(1)).getRank(1);
    }

    @Test
    void getRankData() {
        mockFieldService.getRankData(1);
        verify(mockFieldService,times(1)).getRankData(1);
    }

    @Test
    void getTopAuthors() {
        mockFieldService.getTopAuthors(1);
        verify(mockFieldService,times(1)).getTopAuthors(1);
    }

    @Test
    void getTopOrgs() {
        mockFieldService.getTopOrgs(1);
        verify(mockFieldService,times(1)).getTopOrgs(1);
    }

    @Test
    void getTopPapers() {
        mockFieldService.getTopPapers(1);
        verify(mockFieldService,times(1)).getTopPapers(1);
    }
}