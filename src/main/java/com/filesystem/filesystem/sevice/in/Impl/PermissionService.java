package com.filesystem.filesystem.sevice.in.Impl;

import com.filesystem.filesystem.entity.GroupEntity;
import com.filesystem.filesystem.entity.PermissionEntity;
import com.filesystem.filesystem.enums.Exceptions;
import com.filesystem.filesystem.enums.PermissionLevel;
import com.filesystem.filesystem.repository.GroupRepository;
import com.filesystem.filesystem.repository.PermissionRepository;
import com.filesystem.filesystem.request.PermissionRequest;
import com.filesystem.filesystem.sevice.in.interfaces.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public ResponseEntity<?> addPermission(PermissionRequest permissionRequest) {

        if (Objects.isNull(permissionRequest.getEmail())) {
            return new ResponseEntity<>(Exceptions.Permission_UserEmail_Is_Null, HttpStatus.OK);
        }
        if (Objects.isNull(permissionRequest.getPermissionLevel())) {
            return new ResponseEntity<>(Exceptions.Permission_level_Is_Null, HttpStatus.OK);
        }
        if (Objects.isNull(permissionRequest.getGroupId())) {
            return new ResponseEntity<>(Exceptions.Permission_GroupId_Is_Null, HttpStatus.OK);
        }
        Optional<GroupEntity> group = groupRepository.findById(permissionRequest.getGroupId());
        if(group.isEmpty()){
            return new ResponseEntity<>(Exceptions.Permission_Group_Is_Not_Existed, HttpStatus.OK);
        }

        if(permissionRepository.existsByUserEmailAndGroup(permissionRequest.getEmail(), group.get())){
            return new ResponseEntity<>(Exceptions.Item_Already_Existed, HttpStatus.OK);
        }

        PermissionEntity permissionEntity = new PermissionEntity();

        try{
            permissionEntity.setPermissionLevel(PermissionLevel.valueOf(permissionRequest.getPermissionLevel()));
        }catch(Exception e){
            return new ResponseEntity<>(Exceptions.Permission_level_Value_Is_Not_Valid, HttpStatus.OK);
        }

        permissionEntity.setUserEmail(permissionRequest.getEmail());
        permissionEntity.setGroup(group.get());
        return new ResponseEntity<>(permissionRepository.save(permissionEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity updatePermission(PermissionRequest permissionRequest) {
        if (Objects.isNull(permissionRequest.getId())) {
            return new ResponseEntity<>(Exceptions.Permission_Id_Is_Null, HttpStatus.OK);
        }
        Optional<PermissionEntity> response = permissionRepository.findById(permissionRequest.getId());
        if(response.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }
        PermissionEntity permissionEntity = response.get();

        if(Objects.nonNull(permissionRequest.getEmail())){
            permissionEntity.setUserEmail(permissionRequest.getEmail());
        }

        if(Objects.nonNull(permissionRequest.getPermissionLevel())){
            try{
                permissionEntity.setPermissionLevel(PermissionLevel.valueOf(permissionRequest.getPermissionLevel()));
            }catch(Exception e){
                return new ResponseEntity<>(Exceptions.Permission_level_Value_Is_Not_Valid, HttpStatus.OK);
            }
        }

        if(Objects.nonNull(permissionRequest.getGroupId())){
            Optional<GroupEntity> group = groupRepository.findById(permissionRequest.getGroupId());
            if(group.isEmpty()){
                return new ResponseEntity<>(Exceptions.Permission_Group_Is_Not_Existed, HttpStatus.OK);
            }
            permissionEntity.setGroup(group.get());
        }
        return new ResponseEntity<>(permissionRepository.save(permissionEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPermission(Long id) {
        Optional<PermissionEntity> response = permissionRepository.findById(id);
        if(response.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }
        PermissionEntity permissionEntity = response.get();
        return new ResponseEntity<>(permissionEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePermission(Long id) {
        Optional<PermissionEntity> response = permissionRepository.findById(id);
        if(response.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }
        PermissionEntity permissionEntity = response.get();
        permissionRepository.delete(permissionEntity);
        return new ResponseEntity<>(Exceptions.Item_Deleted_Successfully, HttpStatus.OK);
    }
}
