package imdbapi.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static imdbapi.models.Privilege.ADMIN;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "Name cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z]+\\s[a-zA-Z]+$", message = "The name is invalid. Format: 'John Doe'")
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;

    @Email(message = "The email is invalid.")
    @NotBlank(message = "Email cannot be blank.")
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
//    @Size(min = 8, max = 20, message = "Password should have 8-20 characters.")
    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "key_generated_at")
    private LocalDateTime keyGeneratedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user")
    private UserSystemPrivilege userSystemPrivilege;

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @Transient
    private String authKey;

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public boolean isAdmin() {
        return Objects.nonNull(userSystemPrivilege) && ADMIN == userSystemPrivilege.getPrivilege();
    }
}
