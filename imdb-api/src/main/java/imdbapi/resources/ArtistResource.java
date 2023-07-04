package imdbapi.resources;

import imdbapi.dao.Artist;
import imdbapi.models.Resource;

import java.time.format.DateTimeFormatter;

public class ArtistResource extends Resource<Artist> {

    public ArtistResource(Artist artist) {
        super(artist);
    }

    public ArtistResource() {
        super(new Artist());
    }

    public void setName(String name) {
        getModel().setName(name);
    }

    public String getName() {
        return getModel().getName();
    }

    public UserResource getCreatedBy() {
        return new UserResource(getModel().getCreatedBy());
    }

    public String getCreatedAt() {
        return getModel().getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public UserResource getUpdatedBy() {
        return new UserResource(getModel().getUpdatedBy());
    }

    public String getUpdatedAt() {
        return getModel().getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
