package todoapi.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Name cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z]+\\s[a-zA-Z]+$", message = "The name is invalid. Format: 'John Doe'")
    @Size(min = 1, max = 100)
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

    @Transient
    private String authKey;

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
