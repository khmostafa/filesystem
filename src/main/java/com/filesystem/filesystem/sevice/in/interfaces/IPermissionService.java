package com.filesystem.filesystem.sevice.in.interfaces;

import com.filesystem.filesystem.request.PermissionRequest;
import org.springframework.http.ResponseEntity;

public interface IPermissionService {
    ResponseEntity addPermission(PermissionRequest permissionRequest);
    ResponseEntity updatePermission(PermissionRequest permissionRequest);
    ResponseEntity getPermission(Long id);
    ResponseEntity deletePermission(Long id);
}
