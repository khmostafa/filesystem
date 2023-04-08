package com.filesystem.filesystem.api;

import com.filesystem.filesystem.entity.GroupEntity;
import com.filesystem.filesystem.sevice.in.interfaces.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupApi {
    private final IGroupService groupService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addGroup(@RequestBody GroupEntity groupEntity){
        return groupService.addGroup(groupEntity);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteGroup(@RequestBody GroupEntity groupEntity){
        return groupService.deleteGroup(groupEntity);
    }

    @GetMapping(value = "/view/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id){
        return groupService.getGroup(id);
    }
}
