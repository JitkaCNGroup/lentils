-- user
INSERT INTO user_account(user_id, username, password)
  SELECT 1, 'admin', '$2a$10$qs.xj/pIYebwtf2FrchEteOvdQ38qLTuFeOZXm8twXL0uOJFY1kMG'
  WHERE NOT EXISTS(
    SELECT * FROM user_account WHERE user_id = 1);
INSERT INTO user_account(user_id, username, password)
  SELECT 2, 'organizer', '$2a$10$7s.PnBW19pd8y7dgYrxDCeSflx4P/QI4Mk3TOuoBHLIEt2qQna9wG'
  WHERE NOT EXISTS(
    SELECT * FROM user_account WHERE user_id = 2);

-- roles
INSERT INTO role(role_id, role)
  SELECT 1, 'ADMIN'
  WHERE NOT EXISTS(
    SELECT * FROM role WHERE role_id = 1);
INSERT INTO role(role_id, role)
  SELECT 2, 'ORGANIZER'
  WHERE NOT EXISTS(
    SELECT * FROM role WHERE role_id = 2);
INSERT INTO role(role_id, role)
  SELECT 3, 'USER'
  WHERE NOT EXISTS(
    SELECT * FROM role WHERE role_id = 3);

-- user-role mapping
INSERT INTO user_role(user_id, role_id)
  SELECT 1, 1
  WHERE NOT EXISTS(
    SELECT * FROM user_role WHERE user_id = 1 AND role_id = 1);
INSERT INTO user_role(user_id, role_id)
  SELECT 2, 2
  WHERE NOT EXISTS(
    SELECT * FROM user_role WHERE user_id = 2 AND role_id = 2);
