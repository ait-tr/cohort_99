package com.competa.competademo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Competa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Audited
    private Long id;

    @ManyToOne
    private Ctype ctype; // тип = edCompeta, jobCompeta, hsCompeta, ssCompeta
    
    @Audited
    private String title;
    
    @Audited
    private String description;
    
    @Audited
    private boolean status; // is private
    
    @Audited
    private boolean isConfirmed; // статус подтверждения
    
    @Audited
    private int trustIndex; // индекс доверия

    @ManyToOne
    @NotAudited
    private Industry industry; // индустрия
    /**
     * Description of Education (e.g. Primary, Secondary, PhD, est)
     */
    @Enumerated(value = EnumType.STRING)
    @NotAudited
    private EduType eduType;

    @Audited
    private int views;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotAudited
    private Date dateOut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Audited
    private String timeOut;

    @ManyToOne
    @JoinColumn(name = "competaImage_id")
    private ImageInfo competaImage;

    @ManyToOne
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "jobTitel_id")
    private JobTitle jobTitle;
    
    @OneToMany(mappedBy = "competa", cascade = CascadeType.PERSIST)
    private List<DriverLicence> driverLicences;

    @OneToMany(mappedBy = "competa", cascade = CascadeType.PERSIST)
    private List<EduLevel> eduLevels;

    @OneToMany(mappedBy = "competa", cascade = CascadeType.PERSIST)
    private List<HardSkill> hardSkills;
   
    @OneToMany(mappedBy = "competa", cascade = CascadeType.PERSIST)
    private List<Industry> industries;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competa competa = (Competa) o;
        return Objects.equals(id, competa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Competa{");
        sb.append("id=").append(id);
        sb.append(", competaType='").append(ctype).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", industry='").append(industry).append('\'');
        sb.append(", status=").append(status);
        // TODO
        // добавить статус подтверждения
        // добавить индекс доверия
        sb.append(", views=").append(views);
        sb.append(", dateOut=").append(dateOut);
        sb.append(", timeOut='").append(timeOut).append('\'');
        sb.append(", userId=").append(userProfile.getId());
        sb.append('}');
        return sb.toString();
    }
}
