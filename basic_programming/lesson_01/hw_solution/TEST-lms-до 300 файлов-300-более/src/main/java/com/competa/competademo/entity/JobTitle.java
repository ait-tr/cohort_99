package com.competa.competademo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_title")
public class JobTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "jobTitle", cascade = CascadeType.ALL)
    List<Competa> competas = new ArrayList<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobTitle {");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
