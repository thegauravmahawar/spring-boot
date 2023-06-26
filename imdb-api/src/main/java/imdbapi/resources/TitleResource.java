package imdbapi.resources;

import imdbapi.dao.Title;
import imdbapi.models.Resource;

public class TitleResource extends Resource<Title> {

    public TitleResource(Title title) {
        super(title);
    }

    public TitleResource() {
        super(new Title());
    }
}
