CREATE TABLE user_system_privilege (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) UNIQUE,
    privilege VARCHAR(50) NOT NULL CHECK (privilege IN ('ADMIN', 'STAFF'))
);

INSERT INTO user_system_privilege (id, user_id, privilege)
SELECT gen_random_uuid(), u.id, 'ADMIN'
FROM users u WHERE u.email = 'system_admin@example.com';