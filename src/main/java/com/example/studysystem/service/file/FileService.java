package com.example.studysystem.service.file;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {
    Response getFiles();
    Response addFile(MultipartFile file);
    Response updateDB(List<String> fileTobeUpdate);
    Response onlinePython(String s,String mode);
}
