package com.filesystem.filesystem.sevice.in.Impl;

import com.filesystem.filesystem.entity.FileDataEntity;
import com.filesystem.filesystem.entity.FileEntity;
import com.filesystem.filesystem.entity.GroupEntity;
import com.filesystem.filesystem.entity.PermissionEntity;
import com.filesystem.filesystem.enums.Exceptions;
import com.filesystem.filesystem.enums.FileType;
import com.filesystem.filesystem.enums.Operation;
import com.filesystem.filesystem.enums.PermissionLevel;
import com.filesystem.filesystem.repository.FileDataRepository;
import com.filesystem.filesystem.repository.FileRepository;
import com.filesystem.filesystem.repository.GroupRepository;
import com.filesystem.filesystem.repository.PermissionRepository;
import com.filesystem.filesystem.request.FileRequest;
import com.filesystem.filesystem.sevice.in.interfaces.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {
    private final FileRepository fileRepository;
    private final GroupRepository groupRepository;
    private final FileDataRepository fileDataRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public ResponseEntity uploadFile(FileRequest fileRequest) {

        if(Objects.isNull(fileRequest.getGroupId())){
            return new ResponseEntity<>(Exceptions.File_GroupId_Is_Null, HttpStatus.OK);
        }

        if(Objects.isNull(fileRequest.getUserEmail())){
            return new ResponseEntity<>(Exceptions.Requester_Email_Is_Null, HttpStatus.OK);
        }

        Optional<GroupEntity> response = groupRepository.findById(fileRequest.getGroupId());
        if(response.isEmpty()){return new ResponseEntity<>(Exceptions.File_Group_Is_Not_Exist, HttpStatus.OK);}
        GroupEntity groupEntity = response.get();

        if(!validateUserPermission(fileRequest.getUserEmail(), groupEntity, Operation.Add)){
            return new ResponseEntity<>(Exceptions.Operation_Is_Denied, HttpStatus.OK);
        }

        if(Objects.isNull(fileRequest.getName())){
            return new ResponseEntity<>(Exceptions.File_Name_Is_Null, HttpStatus.OK);
        }
        if(Objects.isNull(fileRequest.getType())){
            return new ResponseEntity<>(Exceptions.File_Type_Is_Null, HttpStatus.OK);
        }

        FileType type;
        try{
            type = FileType.valueOf(fileRequest.getType());
        }catch(Exception e){
            return new ResponseEntity<>(Exceptions.File_Type_Value_Is_Not_Valid, HttpStatus.OK);
        }

        if(!type.equals(FileType.Space) && Objects.isNull(fileRequest.getParentFileId())){
            return new ResponseEntity<>(Exceptions.File_ParentFileId_Is_Null, HttpStatus.OK);
        }


        if(type.equals(FileType.File) && Objects.isNull(fileRequest.getData())){
            return new ResponseEntity<>(Exceptions.File_Data_Is_Null, HttpStatus.OK);
        }

        if(!type.equals(FileType.File) && Objects.nonNull(fileRequest.getData())){
            return new ResponseEntity<>(Exceptions.Only_File_Type_That_Can_Have_BinaryData, HttpStatus.OK);
        }

        FileEntity parentFile;
        if(type.equals(FileType.Space)){
            parentFile = null;
        }else{
            Optional<FileEntity> parentResponse = fileRepository.findById(fileRequest.getParentFileId());
            if(response.isEmpty()){return new ResponseEntity<>(Exceptions.File_ParentFile_Is_Not_Exist, HttpStatus.OK);}
            parentFile = parentResponse.get();
        }

        FileEntity fileEntity = FileEntity.builder()
                .name(fileRequest.getName())
                .type(type)
                .parentFile(parentFile)
                .group(response.get()).build();

        FileDataEntity fileDataEntity = FileDataEntity.builder()
                .file(fileEntity)
                .data(fileRequest.getData()).build();

        FileEntity responseEntity = fileRepository.save(fileEntity);
        fileDataRepository.save(fileDataEntity);

        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateFile(FileRequest fileRequest) {
        if(Objects.isNull(fileRequest.getId())){
            return new ResponseEntity<>(Exceptions.File_Id_Is_Null, HttpStatus.OK);
        }
        if(Objects.isNull(fileRequest.getUserEmail())){
            return new ResponseEntity<>(Exceptions.Requester_Email_Is_Null, HttpStatus.OK);
        }
        Optional<FileEntity> fileResponse = fileRepository.findById(fileRequest.getId());
        if(fileResponse.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }
        FileEntity fileEntity = fileResponse.get();
        FileDataEntity fileDataEntity = fileDataRepository.findByFile(fileEntity).get();

        if(!validateUserPermission(fileRequest.getUserEmail(), fileEntity.getGroup(), Operation.Update)){
            return new ResponseEntity<>(Exceptions.Operation_Is_Denied, HttpStatus.OK);
        }

        if(Objects.nonNull(fileRequest.getName())){
            fileEntity.setName(fileRequest.getName());
        }

        if(Objects.nonNull(fileRequest.getType())){
            FileType type;
            try{
                type = FileType.valueOf(fileRequest.getType());
            }catch(Exception e){
                return new ResponseEntity<>(Exceptions.File_Type_Value_Is_Not_Valid, HttpStatus.OK);
            }
            if(!type.equals(FileType.Space) && Objects.isNull(fileRequest.getParentFileId())){
                return new ResponseEntity<>(Exceptions.File_ParentFileId_Is_Null, HttpStatus.OK);
            }
            fileEntity.setType(type);
        }

        if(Objects.nonNull(fileRequest.getGroupId())){
            Optional<GroupEntity> response = groupRepository.findById(fileRequest.getGroupId());
            if(response.isEmpty()){return new ResponseEntity<>(Exceptions.File_Group_Is_Not_Exist, HttpStatus.OK);}
            fileEntity.setGroup(response.get());
        }

        if(Objects.nonNull(fileRequest.getParentFileId())){
            FileEntity parentFile;
            Optional<FileEntity> response = fileRepository.findById(fileRequest.getParentFileId());
            if(response.isEmpty()){return new ResponseEntity<>(Exceptions.File_ParentFile_Is_Not_Exist, HttpStatus.OK);}
            parentFile = response.get();
            if(parentFile.getType().equals(FileType.File)){
                return new ResponseEntity<>(Exceptions.File_ParentFile_Type_Is_Not_Valid, HttpStatus.OK);
            }
            fileEntity.setParentFile(parentFile);
        }

        if(Objects.nonNull(fileRequest.getData())){
            fileDataEntity.setData(fileRequest.getData());
        }

        fileRepository.save(fileEntity);
        fileDataRepository.save(fileDataEntity);

        return new ResponseEntity<>(Exceptions.Item_Updated_Successfully, HttpStatus.OK);

    }

    @Override
    public ResponseEntity downloadFile(FileRequest fileRequest) {
        if(Objects.isNull(fileRequest.getUserEmail())){
            return new ResponseEntity<>(Exceptions.Requester_Email_Is_Null, HttpStatus.OK);
        }

        Optional<FileEntity> fileResponse = fileRepository.findById(fileRequest.getId());
        if(fileResponse.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }
        FileEntity fileEntity = fileResponse.get();

        if(!validateUserPermission(fileRequest.getUserEmail(), fileEntity.getGroup(), Operation.Download)){
            return new ResponseEntity<>(Exceptions.Operation_Is_Denied, HttpStatus.OK);
        }

        if(!fileEntity.getType().equals(FileType.File)){
            return new ResponseEntity<>(Exceptions.File_Cannot_Be_Downloaded_FileType_Value_Is_Not_File, HttpStatus.OK);
        }

        FileDataEntity fileDataEntity = fileDataRepository.findByFile(fileResponse.get()).get();
        return new ResponseEntity<>(fileDataEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteFile(FileRequest fileRequest) {
        if(Objects.isNull(fileRequest.getUserEmail())){
            return new ResponseEntity<>(Exceptions.Requester_Email_Is_Null, HttpStatus.OK);
        }
        Optional<FileEntity> fileResponse = fileRepository.findById(fileRequest.getId());
        if(fileResponse.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }
        FileEntity fileEntity = fileResponse.get();

        if(!validateUserPermission(fileRequest.getUserEmail(), fileEntity.getGroup(), Operation.delete)){
            return new ResponseEntity<>(Exceptions.Operation_Is_Denied, HttpStatus.OK);
        }

        if(!fileEntity.getType().equals(FileType.File)){
            return new ResponseEntity<>(Exceptions.File_Cannot_Be_Downloaded_FileType_Value_Is_Not_File, HttpStatus.OK);
        }

        FileDataEntity fileDataEntity = fileDataRepository.findByFile(fileResponse.get()).get();
        fileDataRepository.delete(fileDataEntity);
        fileRepository.delete(fileEntity);
        return new ResponseEntity<>(Exceptions.Item_Deleted_Successfully, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getFile(FileRequest fileRequest) {
        if(Objects.isNull(fileRequest.getUserEmail())){
            return new ResponseEntity<>(Exceptions.Requester_Email_Is_Null, HttpStatus.OK);
        }

        List<FileEntity> fileResponse = fileRepository.getFileInfo(fileRequest.getId());
        if(fileResponse.isEmpty()){
            return new ResponseEntity<>(Exceptions.Item_Does_Not_Exist, HttpStatus.OK);
        }
        FileEntity fileEntity = fileResponse.get(0);

        if(!validateUserPermission(fileRequest.getUserEmail(), fileEntity.getGroup(), Operation.View)){
            return new ResponseEntity<>(Exceptions.Operation_Is_Denied, HttpStatus.OK);
        }
        return new ResponseEntity<>(fileEntity, HttpStatus.OK);
    }

    private boolean validateUserPermission(String email, GroupEntity groupEntity, Operation operation){
        List<PermissionEntity> permissionEntityList = permissionRepository.findAllByGroup(groupEntity);
        for(PermissionEntity permissionEntity:  permissionEntityList){
            if(permissionEntity.getUserEmail().equals(email)){
                if(operation.equals(Operation.Add) || operation.equals(Operation.Update) || operation.equals(Operation.delete)){
                    if(permissionEntity.getPermissionLevel().equals(PermissionLevel.Edite)){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return true;
                }
            }
        }
        return false;
    }
}
