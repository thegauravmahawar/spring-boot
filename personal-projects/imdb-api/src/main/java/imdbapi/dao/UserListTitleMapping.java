package imdbapi.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_list_title_mapping")
public class UserListTitleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_list_id")
    private UserList userList;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    public UserListTitleMapping(UserList userList, Title title) {
        this.userList = userList;
        this.title = title;
    }
}
