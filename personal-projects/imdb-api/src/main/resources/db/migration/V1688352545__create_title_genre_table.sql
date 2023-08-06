CREATE TABLE title_genre (
    title_id UUID NOT NULL REFERENCES title(id),
    genre VARCHAR(50) NOT NULL CHECK (genre IN ('ACTION', 'THRILLER', 'SUSPENSE', 'HORROR', 'COMEDY', 'ROMANCE', 'DRAMA', 'MUSICAL', 'FANTASY', 'WESTERN', 'ANIMATION'))
);

CREATE INDEX title_genre_title_index ON title_genre(title_id);
CREATE INDEX title_genre_genre_index ON title_genre(genre);