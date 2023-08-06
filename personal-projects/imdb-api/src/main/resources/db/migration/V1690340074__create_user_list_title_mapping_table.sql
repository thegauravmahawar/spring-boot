CREATE TABLE user_list_title_mapping (
    id UUID PRIMARY KEY,
    user_list_id UUID NOT NULL REFERENCES user_list(id),
    title_id UUID NOT NULL REFERENCES title(id)
);

CREATE INDEX user_list_title_mapping_user_list_id_idx ON user_list_title_mapping(user_list_id);