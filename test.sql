INSERT INTO oauth.t_client
  (id, access_token_validity_seconds, additional_information, authorities, authorized_grant_types, auto_approve_scope, client_id, client_secret, refresh_token_validity_seconds, registered_redirect_uri, resource_ids, scope)
VALUES
  ('ff808081649507f9016495080da70004', 7200, null, null, 'password,authorization_code,refresh_token', null, 'client', '$2a$10$xBJqaO4VrIXEXmjPdeDBzOECu8JuoK/8XddYhY5PbHTJH.YDJ14xi', 604800, 'http://www.baidu.com', null, 'app');

INSERT INTO oauth.t_role (id, name) VALUES ('ff808081649507f9016495080bfb0000', 'USER');
INSERT INTO oauth.t_role (id, name) VALUES ('ff808081649507f9016495080c4c0001', 'ADMIN');

INSERT INTO oauth.t_user (id, create_at, enabled, password, phone_id, sex, update_at, username, user_id) VALUES ('ff808081649507f9016495080cc30002', null, true, '$2a$10$COc55C/4boBCAzpQkDitPOjPifJjm56fAJLh1eC35O5LHScdfqaDK', null, null, null, 'user', null);
INSERT INTO oauth.t_user (id, create_at, enabled, password, phone_id, sex, update_at, username, user_id) VALUES ('ff808081649507f9016495080d370003', null, true, '$2a$10$cI9hPRHj1qUyzZkeTT6eEOdMV0iVpRFQqPo3uaZwlFXu4RZwRRCB6', null, null, null, 'admin', null);

INSERT INTO oauth.t_user_role (user_id, role_id) VALUES ('ff808081649507f9016495080cc30002', 'ff808081649507f9016495080bfb0000');
INSERT INTO oauth.t_user_role (user_id, role_id) VALUES ('ff808081649507f9016495080d370003', 'ff808081649507f9016495080bfb0000');
INSERT INTO oauth.t_user_role (user_id, role_id) VALUES ('ff808081649507f9016495080d370003', 'ff808081649507f9016495080c4c0001');