package imdbapi.repositories;

import imdbapi.dao.Artist;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtistRepository extends ListCrudRepository<Artist, UUID> {
}
