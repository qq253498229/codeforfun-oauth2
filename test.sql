INSERT INTO oauth.oauth_client
( id, client_id, client_secret, access_token_validity_seconds, refresh_token_validity_seconds
, authorized_grant_types, registered_redirect_uri, scope)
VALUES (1, 'client', '$2a$10$xBJqaO4VrIXEXmjPdeDBzOECu8JuoK/8XddYhY5PbHTJH.YDJ14xi', 7200, 604800,
        'password,authorization_code,refresh_token', 'https://www.baidu.com', 'app');

INSERT INTO oauth.oauth_user (id, username, password, enabled, create_at, update_at)
VALUES (1, 'admin', '$2a$10$cI9hPRHj1qUyzZkeTT6eEOdMV0iVpRFQqPo3uaZwlFXu4RZwRRCB6', true, now(), now());
INSERT INTO oauth.oauth_user (id, username, password, enabled, create_at, update_at)
VALUES (2, 'user', '$2a$10$COc55C/4boBCAzpQkDitPOjPifJjm56fAJLh1eC35O5LHScdfqaDK', true, now(), now());

INSERT INTO oauth.oauth_role (id, name)
VALUES (1, 'ADMIN');
INSERT INTO oauth.oauth_role (id, name)
VALUES (2, 'USER');

INSERT INTO oauth.mapping_user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO oauth.mapping_user_role (user_id, role_id)
VALUES (1, 2);
INSERT INTO oauth.mapping_user_role (user_id, role_id)
VALUES (2, 2);