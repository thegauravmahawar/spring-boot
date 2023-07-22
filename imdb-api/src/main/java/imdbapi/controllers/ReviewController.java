package imdbapi.controllers;

import imdbapi.dao.Review;
import imdbapi.dao.User;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.exceptions.NotFoundException;
import imdbapi.models.ApiResponseSuccess;
import imdbapi.resources.ReviewResource;
import imdbapi.services.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "/title/{titleId}/review", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseSuccess createReview(@PathVariable("titleId") String titleId,
                                           @RequestBody ReviewResource reviewResource,
                                           HttpServletRequest request) throws InvalidParameterException, NotFoundException {
        User principal = (User) request.getAttribute("principal");
        Review review = reviewService.create(reviewResource, titleId, principal);
        return new ApiResponseSuccess(new ReviewResource(review));
    }

    @PutMapping(value = "/review/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseSuccess updateReview(@PathVariable("reviewId") String reviewId,
                                           @RequestBody ReviewResource reviewResource,
                                           HttpServletRequest request) throws InvalidParameterException, NotFoundException {
        User principal = (User) request.getAttribute("principal");
        Review review = reviewService.update(reviewResource, reviewId, principal);
        return new ApiResponseSuccess(new ReviewResource(review));
    }

    @DeleteMapping(value = "/review/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseSuccess deleteReview(@PathVariable("reviewId") String reviewId,
                                           HttpServletRequest request) {
        User principal = (User) request.getAttribute("principal");
        reviewService.delete(reviewId, principal);
        return new ApiResponseSuccess("Review deleted.");
    }
}
