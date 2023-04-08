package com.filesystem.filesystem.repository;

import com.filesystem.filesystem.entity.GroupEntity;
import com.filesystem.filesystem.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity,Long> {
    boolean existsByUserEmailAndGroup(String userEmail, GroupEntity group);
    List<PermissionEntity> findAllByGroup(GroupEntity group);
}
