package com.competa.competademo.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "image_info")
public class ImageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "image_name",columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(name = "url",columnDefinition = "TEXT", nullable = false)
    private String url;

}
