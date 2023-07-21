package imdbapi.services;

import imdbapi.dao.Review;
import imdbapi.dao.User;
import imdbapi.repositories.ReviewRepository;
import imdbapi.repositories.TitleRepository;
import imdbapi.resources.ReviewResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final TitleRepository titleRepository;
    private final ReviewRepository reviewRepository;

    public ReviewService(TitleRepository titleRepository, ReviewRepository reviewRepository) {
        this.titleRepository = titleRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public Review create(ReviewResource reviewResource, String titleId, User principal) {
        return null;
    }
}
