package com.filesystem.filesystem.sevice.in.Impl;

import com.filesystem.filesystem.entity.GroupEntity;
import com.filesystem.filesystem.enums.Exceptions;
import com.filesystem.filesystem.repository.GroupRepository;
import com.filesystem.filesystem.sevice.in.interfaces.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService implements IGroupService {

    private final GroupRepository groupRepository;

    @Override
    public ResponseEntity<?> addGroup(GroupEntity group) {
        if (groupRepository.existsByName(group.getName())){
            return new ResponseEntity<>(Exceptions.Item_Already_Existed, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(groupRepository.save(group), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getGroup(Long id) {
        Optional<GroupEntity> group = groupRepository.findById(id);
        if (group.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(group.get(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity deleteGroup(GroupEntity group) {
        if (!groupRepository.existsByName(group.getName())){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }else{
            groupRepository.delete(group);
            return new ResponseEntity<>(Exceptions.Item_Deleted_Successfully, HttpStatus.OK);
        }
    }
}
