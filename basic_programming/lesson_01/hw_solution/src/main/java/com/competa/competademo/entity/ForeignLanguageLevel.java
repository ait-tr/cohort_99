package com.competa.competademo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="foreign_language_level")
public class ForeignLanguageLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForeignLanguageLevel foreignLanguageLevel = (ForeignLanguageLevel) o;
        return id == foreignLanguageLevel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
