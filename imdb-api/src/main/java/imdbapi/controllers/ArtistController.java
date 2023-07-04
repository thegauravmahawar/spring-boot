package imdbapi.controllers;

import imdbapi.dao.Artist;
import imdbapi.dao.Title;
import imdbapi.dao.User;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.models.ApiResponseSuccess;
import imdbapi.resources.ArtistResource;
import imdbapi.resources.TitleResource;
import imdbapi.services.ArtistService;
import imdbapi.services.TitleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping(value = "/artist", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseSuccess createArtist(@RequestBody ArtistResource artistResource, HttpServletRequest request) throws InvalidParameterException {
        User principal = (User) request.getAttribute("principal");
        Artist artist = artistService.create(artistResource, principal);
        return new ApiResponseSuccess(new ArtistResource(artist));
    }
}
