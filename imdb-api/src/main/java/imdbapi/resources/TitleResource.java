package imdbapi.resources;

import imdbapi.dao.Title;
import imdbapi.models.Genre;
import imdbapi.models.Resource;
import imdbapi.models.TitleType;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class TitleResource extends Resource<Title> {

    public TitleResource(Title title) {
        super(title);
    }

    public TitleResource() {
        super(new Title());
    }

    public void setName(String name) {
        getModel().setName(name);
    }

    public String getName() {
        return getModel().getName();
    }

    public void setType(TitleType type) {
        getModel().setType(type);
    }

    public TitleType getType() {
        return getModel().getType();
    }

    public void setStartYear(int startYear) {
        getModel().setStartYear(startYear);
    }

    public void setEndYear(int endYear) {
        getModel().setStartYear(endYear);
    }

    public String getYear() {
        if (Objects.nonNull(getModel().getStartYear()) && Objects.nonNull(getModel().getEndYear())) {
            return String.format("%d-%d", getModel().getStartYear(), getModel().getEndYear());
        }
        if (Objects.nonNull(getModel().getStartYear())) {
            return String.valueOf(getModel().getStartYear());
        }
        return StringUtils.EMPTY;
    }

    public void setGenres(List<Genre> genres) {
        getModel().setGenres(genres);
    }

    public List<Genre> getGenres() {
        return getModel().getGenres();
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
