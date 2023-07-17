package imdbapi.repositories;

import imdbapi.dao.Title;
import imdbapi.models.TitleSearch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TitleRepository extends CrudRepository<Title, UUID> {

    @Query(nativeQuery = true)
    List<TitleSearch> getAllDataForSearch();
}
