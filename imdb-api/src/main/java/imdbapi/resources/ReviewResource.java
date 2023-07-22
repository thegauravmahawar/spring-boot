package imdbapi.resources;

import imdbapi.dao.Review;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ReviewResource extends Resource<Review> {

    public ReviewResource(Review review) {
        super(review);
    }

    public ReviewResource() {
        super(new Review());
    }

    public void setMessage(String message) {
        getModel().setMessage(message);
    }

    public void setStars(Float stars) {
        getModel().setStars(stars);
    }

    public UUID getId() {
        return getModel().getId();
    }

    public String getMessage() {
        return getModel().getMessage();
    }

    public Float getStars() {
        return getModel().getStars();
    }

    public String getCreatedAt() {
        return getModel().getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getUpdatedAt() {
        return getModel().getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
