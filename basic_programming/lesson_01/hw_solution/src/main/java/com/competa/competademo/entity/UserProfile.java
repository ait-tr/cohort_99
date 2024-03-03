package com.competa.competademo.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String profession;

    @Column
    private String level;

    @Column
    boolean isPublic;

    @Column
    private String residence;

    @Column
    boolean isReadyToMove;

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    @NotAudited
    private ImageInfo profileAvatar;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Competa> competas = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", profession='" + profession + '\'' +
                ", level='" + level + '\'' +
                ", isPublic=" + isPublic +
                ", residence='" + residence + '\'' +
                ", isReadyToMove=" + isReadyToMove +
                ", profileAvatar=" + profileAvatar +
                ", competas=" + competas +
                ", userId=" + user.getId() +
                '}';
    }
}
