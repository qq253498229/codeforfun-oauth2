insert ignore into t_client
(
  id,
  client_id,
  client_secret,
  access_token_validity_seconds,
  refresh_token_validity_seconds,
  authorized_grant_types,
  scope
)
values
  (
    1,
    'client',
    'secret',
    7200,
    604800,
    'authorization_code,refresh_token,password',
    'app'
  );

insert ignore into t_user (id, enabled, password, username)
values (1, true, '$2a$10$fgZ5WShCP0C6j7KX6k4MpuYTGUqtRgJfORZXCCw9JnFjkVYYqczky', 'user');

insert ignore into t_role (id, name) values (1, 'USER');

insert ignore into t_user_role (user_id, role_id) values (1, 1);