package imdbapi.dao;

import imdbapi.models.Genre;
import imdbapi.models.TitleSearch;
import imdbapi.models.TitleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Title.getAllDataForSearch",
                query = "SELECT t.id, t.name, t.type, t.start_year, t.end_year FROM title t",
                resultSetMapping = "titleSearchMapping"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "titleSearchMapping", classes = {
                @ConstructorResult(
                        targetClass = TitleSearch.class,
                        columns = {
                                @ColumnResult(name = "id", type = UUID.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "type", type = String.class),
                                @ColumnResult(name = "start_year", type = Integer.class),
                                @ColumnResult(name = "end_year", type = Integer.class)
                        }
                )
        })
})
@Data
@Entity
@Table(name = "title")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TitleType type;

    @NotNull(message = "Start year cannot be null")
    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "updated_by")
    @ManyToOne
    private User updatedBy;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "title_genre", joinColumns = @JoinColumn(name = "title_id"))
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private List<Genre> genres = new ArrayList<>(0);

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
        updatedBy = createdBy;
    }
}
