package com.filesystem.filesystem.repository;

import com.filesystem.filesystem.entity.FileDataEntity;
import com.filesystem.filesystem.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileDataEntity, Long> {
    Optional<FileDataEntity> findByFile(FileEntity file);

}
