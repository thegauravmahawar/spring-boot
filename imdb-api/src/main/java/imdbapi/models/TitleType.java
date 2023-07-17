package imdbapi.models;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum TitleType implements Serializable {
    MOVIE, TV_SERIES, DOCUMENTARY
}
