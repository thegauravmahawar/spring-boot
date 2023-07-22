package imdbapi.repositories;

import imdbapi.dao.Review;
import imdbapi.dao.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends CrudRepository<Review, UUID> {

    Optional<Review> findByIdAndReviewer(UUID id, User reviewer);

    @Modifying
    void deleteByIdAndReviewer(UUID id, User reviewer);
}
