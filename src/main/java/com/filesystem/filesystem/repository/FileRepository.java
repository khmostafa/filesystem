package com.filesystem.filesystem.repository;

import com.filesystem.filesystem.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query(value = "Select * from File_Info where id=:id", nativeQuery = true)
    List<FileEntity> getFileInfo(Long id);

}
