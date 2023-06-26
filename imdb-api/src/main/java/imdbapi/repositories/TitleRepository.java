package imdbapi.repositories;

import imdbapi.dao.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TitleRepository extends CrudRepository<Title, UUID> {
}
