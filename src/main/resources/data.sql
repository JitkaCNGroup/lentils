-- users
INSERT INTO user(user_id, username, password)
  SELECT 1, 'admin', '$2a$10$qs.xj/pIYebwtf2FrchEteOvdQ38qLTuFeOZXm8twXL0uOJFY1kMG'
  WHERE NOT EXISTS(
    SELECT * FROM user WHERE user_id = 1);

-- roles
INSERT INTO role(role_id, role)
  SELECT 1, 'ADMIN'
  WHERE NOT EXISTS(
    SELECT * FROM role WHERE role_id = 1);
INSERT INTO role(role_id, role)
  SELECT 2, 'USER'
  WHERE NOT EXISTS(
    SELECT * FROM role WHERE role_id = 2);

-- user-role mapping
INSERT INTO user_role(user_id, role_id)
  SELECT 1, 1
  WHERE NOT EXISTS(
    SELECT * FROM user_role WHERE user_id = 1 AND role_id = 1);
