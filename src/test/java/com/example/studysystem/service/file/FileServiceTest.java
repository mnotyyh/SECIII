package com.example.studysystem.service.file;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {
    private FileService mockFileService=mock(FileService.class);
    @Test
    void getFiles() {
        mockFileService.getFiles();
        verify(mockFileService,times(1)).getFiles();
    }

    @Test
    void addFile() {
        mockFileService.addFile(null);
        verify(mockFileService,times(1)).addFile(null);
    }

    @Test
    void updateDB() {
        mockFileService.updateDB(new ArrayList<String>());
        verify(mockFileService,times(1)).updateDB(new ArrayList<String>());
    }

    @Test
    void onlinePython() {
        mockFileService.onlinePython("str","1");
        verify(mockFileService,times(1)).onlinePython("str","1");
    }
}