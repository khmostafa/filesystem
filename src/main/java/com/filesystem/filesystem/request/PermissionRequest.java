package com.filesystem.filesystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class  PermissionRequest {
    private Long id;
    private String email;
    private String permissionLevel;
    private Long GroupId;
}
