package com.example.studysystem.service.file;

import com.example.studysystem.db.InsertDB;
import com.example.studysystem.db.Insert_field;
import com.example.studysystem.db.spider;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceImplTest {
    private FileServiceImpl fileService=new FileServiceImpl();
    private FileServiceImpl mockFileService=mock(FileServiceImpl.class);
    private InsertDB mockInserDB=mock(InsertDB.class);
    private Insert_field mockInsert_field=mock(Insert_field.class);

    public void prepare(){fileService.set(mockInserDB,mockInsert_field);}
    @Test
    void getFiles() {
        prepare();
        mockFileService.getFiles();
        verify(mockFileService,times(1)).getFiles();
        assertNotNull(fileService.getFiles());
    }

    @Test
    void addFile() {
        prepare();
        mockFileService.addFile(null);
        verify(mockFileService,times(1)).addFile(null);
        assertEquals("失败",fileService.addFile(null).getMessage());
    }

    @Test
    void updateDB() {
        prepare();
        mockFileService.updateDB(new ArrayList<String>());
        verify(mockFileService,times(1)).updateDB(new ArrayList<String>());
        when(mockInserDB.tranfData(new ArrayList<String>())).thenThrow(new RuntimeException());
        assertEquals("失败",fileService.updateDB(new ArrayList<String>()).getMessage());
        verify(mockInserDB,times(1)).tranfData(new ArrayList<String>());
    }

    @Test
    void onlinePython() throws IOException, InterruptedException {
        spider mockSpider=mock(spider.class);
        mockFileService.onlinePython("str","1");
        verify(mockFileService,times(1)).onlinePython("str","1");
    }
}