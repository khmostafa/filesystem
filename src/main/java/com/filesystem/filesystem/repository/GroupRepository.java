package com.filesystem.filesystem.repository;

import com.filesystem.filesystem.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  GroupRepository extends JpaRepository<GroupEntity,Long> {

    Optional<GroupEntity> findById(Long id);

    boolean existsByName(String name);
}
