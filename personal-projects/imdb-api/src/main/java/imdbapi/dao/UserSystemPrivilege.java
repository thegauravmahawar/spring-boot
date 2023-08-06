package imdbapi.dao;

import imdbapi.models.PrivilegeType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_system_privilege")
public class UserSystemPrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    @Column(name = "privilege")
    @Enumerated(EnumType.STRING)
    private PrivilegeType privilege;
}
