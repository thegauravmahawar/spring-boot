package imdbapi.services;

import imdbapi.dao.Title;
import imdbapi.dao.User;
import imdbapi.repositories.TitleRepository;
import imdbapi.resources.TitleResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TitleService {

    private final TitleRepository titleRepository;

    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public Title create(TitleResource titleResource, User principal) {
        return null;
    }
}
