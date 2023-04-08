package com.filesystem.filesystem.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="File_Data")
public class FileDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NonNull
    @JoinColumn(name = "item_id")
    private FileEntity file;

    @Column(name="binary")
    private byte[] data;

}
