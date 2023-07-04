package imdbapi.services;

import imdbapi.dao.Artist;
import imdbapi.dao.Title;
import imdbapi.dao.User;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.repositories.ArtistRepository;
import imdbapi.resources.ArtistResource;
import imdbapi.utils.ResourceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public Artist create(ArtistResource artistResource, User principal) throws InvalidParameterException {
        ResourceValidator.validate(artistResource);
        Artist artist = artistResource.getModel();
        artist.setCreatedBy(principal);
        return artistRepository.save(artist);
    }
}
