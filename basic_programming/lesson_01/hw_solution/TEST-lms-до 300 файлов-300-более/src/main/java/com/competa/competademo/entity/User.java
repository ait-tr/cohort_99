package com.competa.competademo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
    @Length(max = 100)
    private String nickName;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "varchar(20) default 'NOT_CONFIRMED'")
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @NotAudited
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id")
    @NotAudited
    private UserProfile userProfile;

    @NotAudited
    @OneToMany(mappedBy = "user")
    private Set<ConfirmationCode> codes = new HashSet<>();

    public User(String nickName,
                String password,
                String email,
                UserStatus userStatus,
                Set<Role> roles,
                UserProfile userProfile) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.userStatus = userStatus;
        this.roles = roles;
        this.userProfile = userProfile;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", status=" + userStatus +
                ", userProfile=" + userProfile +
                '}';
    }
}
