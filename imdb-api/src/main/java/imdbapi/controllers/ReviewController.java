package imdbapi.controllers;

import imdbapi.dao.Review;
import imdbapi.dao.Title;
import imdbapi.dao.User;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.models.ApiResponseSuccess;
import imdbapi.resources.ReviewResource;
import imdbapi.services.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
                                           HttpServletRequest request) throws InvalidParameterException {
        User principal = (User) request.getAttribute("principal");
        Review review = reviewService.create(reviewResource, titleId, principal);
        return new ApiResponseSuccess(new ReviewResource(review));
    }
}
