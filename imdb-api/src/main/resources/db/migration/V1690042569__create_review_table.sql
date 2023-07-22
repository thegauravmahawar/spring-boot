CREATE TABLE review (
    id UUID PRIMARY KEY,
    message TEXT NOT NULL,
    stars FLOAT NOT NULL,
    title_id UUID NOT NULL REFERENCES title(id),
    reviewer_id UUID NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX review_reviewer_idx ON review(reviewer_id);
CREATE INDEX review_title_updated_at_idx ON review(title_id);
CREATE INDEX review_updated_at_idx ON review(updated_at DESC);
