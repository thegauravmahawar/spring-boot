package imdbapi.services;

import imdbapi.dao.Review;
import imdbapi.dao.Title;
import imdbapi.dao.User;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.exceptions.NotFoundException;
import imdbapi.repositories.ReviewRepository;
import imdbapi.repositories.TitleRepository;
import imdbapi.resources.ReviewResource;
import imdbapi.utils.ResourceValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class ReviewService {

    private final TitleRepository titleRepository;
    private final ReviewRepository reviewRepository;

    public ReviewService(TitleRepository titleRepository, ReviewRepository reviewRepository) {
        this.titleRepository = titleRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public Review create(ReviewResource reviewResource, String titleId, User principal)
            throws InvalidParameterException, NotFoundException {
        ResourceValidator.validate(reviewResource);
        validate(reviewResource);
        Review review = reviewResource.getModel();
        Title title = titleRepository.findById(UUID.fromString(titleId))
                .orElseThrow(() -> new NotFoundException(format("Title not found with id %s.", titleId), "NOT_FOUND"));
        review.setTitle(title);
        review.setReviewer(principal);
        return reviewRepository.save(review);
    }

    private void validate(ReviewResource reviewResource) throws InvalidParameterException {
        if (StringUtils.isBlank(reviewResource.getMessage())) {
            throw new InvalidParameterException("Review message is required.", "INVALID_PARAMETER");
        }
        if (Objects.isNull(reviewResource.getStars())) {
            throw new InvalidParameterException("Review stars are required.", "INVALID_PARAMETER");
        }
        if (Float.compare(reviewResource.getStars(), 0.5f) < 0
                || Float.compare(reviewResource.getStars(), 5.0f) > 0
                || reviewResource.getStars() % (0.5f) != 0) {
            throw new InvalidParameterException("Review stars are invalid.", "INVALID_PARAMETER");
        }
    }

    @Transactional(rollbackFor = {Throwable.class})
    public Review update(ReviewResource reviewResource, String reviewId, User principal)
            throws InvalidParameterException, NotFoundException {
        ResourceValidator.validate(reviewResource);
        validate(reviewResource);
        Review updatedReview = reviewResource.getModel();
        Review review = reviewRepository.findByIdAndReviewer(UUID.fromString(reviewId), principal)
                .orElseThrow(() -> new NotFoundException(format("Review not found with id %s.", reviewId), "NOT_FOUND"));
        review.setMessage(updatedReview.getMessage());
        review.setStars(updatedReview.getStars());
        review.setUpdatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Transactional(rollbackFor = {Throwable.class})
    public void delete(String reviewId, User principal) {
        reviewRepository.deleteByIdAndReviewer(UUID.fromString(reviewId), principal);
    }
}
