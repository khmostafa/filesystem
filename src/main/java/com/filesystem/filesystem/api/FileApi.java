package com.filesystem.filesystem.api;

import com.filesystem.filesystem.request.FileRequest;
import com.filesystem.filesystem.sevice.in.interfaces.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileApi {

    private final IFileService fileService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addFile(FileRequest fileRequest){
        return fileService.uploadFile(fileRequest);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateFile(FileRequest fileRequest){
        return fileService.updateFile(fileRequest);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteFile(FileRequest fileRequest){
        return fileService.deleteFile(fileRequest);
    }

    @GetMapping(value = "/view")
    public ResponseEntity<?> getFile(FileRequest fileRequest){
        return fileService.getFile(fileRequest);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<?> downloadFile(FileRequest fileRequest){
        return fileService.downloadFile(fileRequest);
    }
}
