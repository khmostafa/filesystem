package com.filesystem.filesystem.entity;


import com.filesystem.filesystem.enums.FileType;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="File_Info")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private FileType type;

    @NonNull
    private String name;

    @OneToOne
    @JoinColumn(name="parent_file_id")
    private FileEntity parentFile;

    @ManyToOne
    @JoinColumn(name="permission_group_id")
    @NonNull
    private GroupEntity group;
}
