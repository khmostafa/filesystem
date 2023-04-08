package com.filesystem.filesystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {
    private String userEmail;
    private Long id;
    private String type;
    private String name;
    private Long parentFileId;
    private Long GroupId;
    private byte[] data;
}
