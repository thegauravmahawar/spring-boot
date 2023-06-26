package imdbapi.dao;

import imdbapi.models.TitleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Column(name = "start_year")
    private LocalDate startYear;

    @Column(name = "end_year")
    private LocalDate endYear;

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

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }
}