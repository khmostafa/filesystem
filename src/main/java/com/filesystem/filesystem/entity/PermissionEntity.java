package com.filesystem.filesystem.entity;

import com.filesystem.filesystem.enums.PermissionLevel;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_email")
    @NonNull
    private String userEmail;

    @Column(name="permission_level")
    @NonNull
    private PermissionLevel permissionLevel;

    @ManyToOne
    @JoinColumn(name="group_id")
    @NonNull
    private GroupEntity group;
}
