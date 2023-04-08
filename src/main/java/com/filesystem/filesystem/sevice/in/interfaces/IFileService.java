package com.filesystem.filesystem.sevice.in.interfaces;

import com.filesystem.filesystem.request.FileRequest;
import org.springframework.http.ResponseEntity;

public interface IFileService {

    ResponseEntity uploadFile(FileRequest fileRequest);
    ResponseEntity updateFile(FileRequest fileRequest);
    ResponseEntity downloadFile(FileRequest fileRequest);
    ResponseEntity deleteFile(FileRequest fileRequest);
    ResponseEntity getFile(FileRequest fileRequest);
}
