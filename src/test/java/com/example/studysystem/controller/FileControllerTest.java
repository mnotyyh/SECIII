package com.example.studysystem.controller;

import com.example.studysystem.service.file.FileService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class FileControllerTest {
    private FileService mockFileService=mock(FileService.class);
    private FileController fileController=new FileController();
    private FileController mockFileController=mock(FileController.class);
    public void prepare(){fileController.set(mockFileService);}

    @Test
    void getFiles() {
        prepare();
        mockFileController.getFiles();
        verify(mockFileController,times(1)).getFiles();
        fileController.getFiles();
        verify(mockFileService,times(1)).getFiles();
    }

    @Test
    void uploadFile() {
        prepare();
        mockFileController.uploadFile(null);
        verify(mockFileController,times(1)).uploadFile(null);
        fileController.uploadFile(null);
        verify(mockFileService,times(1)).addFile(null);

    }

    @Test
    void updateDB() {
        prepare();
        mockFileController.updateDB(new ArrayList<String>());
        verify(mockFileController,times(1)).updateDB(new ArrayList<String>());
        fileController.updateDB(new ArrayList<String>());
        verify(mockFileService,times(1)).updateDB(new ArrayList<String>());
    }

    @Test
    void onlinePython() {
        prepare();
        mockFileController.onlinePython("str","1");
        verify(mockFileController,times(1)).onlinePython("str","1");
        fileController.onlinePython("str","1");
        verify(mockFileService,times(1)).onlinePython("str","1");
    }
}