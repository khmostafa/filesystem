package com.filesystem.filesystem.sevice.in.interfaces;

import com.filesystem.filesystem.entity.GroupEntity;
import org.springframework.http.ResponseEntity;

public interface IGroupService {
    ResponseEntity addGroup(GroupEntity group);
    ResponseEntity getGroup(Long id);
    ResponseEntity deleteGroup(GroupEntity group);
}
