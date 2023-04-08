package com.filesystem.filesystem.api;

import com.filesystem.filesystem.request.PermissionRequest;
import com.filesystem.filesystem.sevice.in.interfaces.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionApi {
    private final IPermissionService permissionService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addPermission(@RequestBody PermissionRequest permissionRequest){
        return permissionService.addPermission( permissionRequest);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updatePermission(@RequestBody PermissionRequest permissionRequest){
        return permissionService.updatePermission( permissionRequest);
    }

    @GetMapping(value = "/view/{id}")
    public ResponseEntity<?> getPermission(@PathVariable Long id){
        return permissionService.getPermission(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id){
        return permissionService.deletePermission(id);
    }
}
