-- Inserts:
--     - Roles
--     - 2 users: admin, organizer
--     - contact
--     - final place
--     - 10 cyphers with 3 hints

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

-- contact
INSERT INTO contact(contact_id, fcb_event, name, phone_number, web_pages, email)
    SELECT 5, 'https://www.facebook.com/events/504711523393003/', STRINGDECODE('Veru Malan\u00edkov\u00e1'), '776 747 421', 'https://www.cnjobs.dk/', 'malanikova@cngroup.dk'
    WHERE NOT EXISTS(
            SELECT * FROM contact WHERE contact_id > 0);

-- final place
INSERT INTO final_place(final_place_id, description, location, opening_time)
    SELECT 6, 'Budova OD 13. (5. patro)', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a8c8cc0ebee040489cbd67530bbf', TIMESTAMP '2019-05-21 18:00:00'
    WHERE NOT EXISTS(
            SELECT * FROM final_place WHERE final_place_id > 0);

-- CYPHER
INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 1, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword A', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher A', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword A'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 1);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 22, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword B', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher B', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword B'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 22);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 23, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword C', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher C', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword C'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 23);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 24, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword D', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher D', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword D'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 24);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 25, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword E', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher E', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword E'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 25);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 26, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword F', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher F', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword F'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 26);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 27, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword G', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher G', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword G'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 27);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 28, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword H', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher H', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword H'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 28);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 29, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword I', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher I', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword I'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 29);

INSERT INTO cypher(cypher_id, bonus_content, codeword, location, map_address, name, place_description, stage, trap_codeword)
    SELECT 30, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 'Codeword J', X'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704031a9ce5c0ebee040489ce97cad6c4a', 'https://www.google.com/maps/place/Park+Komensk%C3%A9ho/@49.2243061,17.6624255,17z/data=!3m1!4b1!4m5!3m4!1s0x47137351924a9803:0x538af3b59f58a3e6!8m2!3d49.2243026!4d17.6646195', 'Cypher J', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Cras elementum. Aliquam id dolor. Fusce nibh. Fusce tellus. Vivamus ac leo pretium faucibus. Morbi scelerisque luctus velit. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. Proin in tellus sit amet nibh dignissim sagittis.', 1, 'XCodeword J'
    WHERE NOT EXISTS(
        SELECT * FROM cypher WHERE cypher_id = 30);

-- hint
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 2, 'Hint 1 for Cypher A', 1, 1
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 2);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 3, 'Hint 2 for Cypher A', 3, 1
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 3);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 4, 'Hint 3 for Cypher A', 5, 1
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 4);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 52, 'Hint 1 for Cypher B', 1, 22
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 52);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 53, 'Hint 2 for Cypher B', 3, 22
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 53);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 54, 'Hint 3 for Cypher B', 5, 22
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 54);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 62, 'Hint 1 for Cypher C', 1, 23
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 62);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 63, 'Hint 2 for Cypher C', 3, 23
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 63);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 64, 'Hint 3 for Cypher C', 5, 23
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 64);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 72, 'Hint 1 for Cypher D', 1, 24
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 72);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 73, 'Hint 2 for Cypher D', 3, 24
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 73);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 74, 'Hint 3 for Cypher D', 5, 24
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 74);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 82, 'Hint 1 for Cypher E', 1, 25
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 82);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 83, 'Hint 2 for Cypher E', 3, 25
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 83);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 84, 'Hint 3 for Cypher E', 5, 25
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 84);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 92, 'Hint 1 for Cypher F', 1, 26
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 92);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 93, 'Hint 2 for Cypher F', 3, 26
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 93);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 94, 'Hint 3 for Cypher F', 5, 26
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 94);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 102, 'Hint 1 for Cypher G', 1, 27
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 102);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 103, 'Hint 2 for Cypher G', 3, 27
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 103);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 104, 'Hint 3 for Cypher G', 5, 27
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 104);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 112, 'Hint 1 for Cypher H', 1, 28
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 112);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 113, 'Hint 2 for Cypher H', 3, 28
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 113);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 114, 'Hint 3 for Cypher H', 5, 28
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 114);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 122, 'Hint 1 for Cypher I', 1, 29
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 122);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 123, 'Hint 2 for Cypher I', 3, 29
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 123);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 124, 'Hint 3 for Cypher I', 5, 29
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 124);

INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 132, 'Hint 1 for Cypher J', 1, 30
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 132);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 133, 'Hint 2 for Cypher J', 3, 30
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 132);
INSERT INTO hint(hint_id, text, value, cypher_id)
    SELECT 134, 'Hint 3 for Cypher J', 5, 30
    WHERE NOT EXISTS(
        SELECT * FROM hint WHERE hint_id = 132);

