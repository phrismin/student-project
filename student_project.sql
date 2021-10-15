DROP TABLE IF EXISTS student_child;
DROP TABLE IF EXISTS student_order;
DROP TABLE IF EXISTS passport_office;
DROP TABLE IF EXISTS register_office;
DROP TABLE IF EXISTS country_structure;
DROP TABLE IF EXISTS street;

CREATE TABLE street
(
    street_code INTEGER,
    street_name VARCHAR(100),
    PRIMARY KEY (street_code)
);

CREATE TABLE country_structure
(
    area_id   CHAR(7) NOT NULL,
    area_name VARCHAR(100),
    PRIMARY KEY (area_id)
);

CREATE TABLE passport_office
(
    passp_office_id      INTEGER NOT NULL,
    passp_office_area_id CHAR(7) NOT NULL,
    passp_office_name    VARCHAR(100),
    PRIMARY KEY (passp_office_id),
    FOREIGN KEY (passp_office_area_id) REFERENCES country_structure (area_id) ON DELETE RESTRICT
);

CREATE TABLE register_office
(
    reg_office_id      INTEGER NOT NULL,
    reg_office_area_id CHAR(7) NOT NULL,
    reg_office_name    VARCHAR(100),
    PRIMARY KEY (reg_office_id),
    FOREIGN KEY (reg_office_area_id) REFERENCES country_structure (area_id) ON DELETE RESTRICT
);

CREATE TABLE student_order
(
    student_order_id        SERIAL,
    husb_sur_name           VARCHAR(50) NOT NULL,
    husb_given_name         VARCHAR(50) NOT NULL,
    husb_patronymic         VARCHAR(50) NOT NULL,
    husb_date_of_birth      DATE        NOT NULL,
    husb_passport_series    VARCHAR(10) NOT NULL,
    husb_passport_number    VARCHAR(10) NOT NULL,
    husb_passport_date      DATE        NOT NULL,
    husb_passport_office_id INTEGER     NOT NULL,
    husb_post_index         CHAR(6),
    husb_street_code        INTEGER     NOT NULL,
    husb_building           VARCHAR(5)  NOT NULL,
    husb_extension          VARCHAR(5),
    husb_apartment          VARCHAR(5),
    wife_sur_name           VARCHAR(50) NOT NULL,
    wife_given_name         VARCHAR(50) NOT NULL,
    wife_patronymic         VARCHAR(50) NOT NULL,
    wife_date_of_birth      DATE        NOT NULL,
    wife_passport_series    VARCHAR(10) NOT NULL,
    wife_passport_number    VARCHAR(10) NOT NULL,
    wife_passport_date      DATE        NOT NULL,
    wife_passport_office_id INTEGER     NOT NULL,
    wife_post_index         CHAR(6),
    wife_street_code        INTEGER     NOT NULL,
    wife_building           VARCHAR(5)  NOT NULL,
    wife_extension          VARCHAR(5),
    wife_apartment          VARCHAR(5),
    certificate_id          VARCHAR(20) NOT NULL,
    register_office_id      INTEGER     NOT NULL,
    marriage_date           DATE        NOT NULL,
    PRIMARY KEY (student_order_id),
    FOREIGN KEY (husb_street_code) REFERENCES street (street_code) ON DELETE RESTRICT,
    FOREIGN KEY (wife_street_code) REFERENCES street (street_code) ON DELETE RESTRICT,
    FOREIGN KEY (register_office_id) REFERENCES register_office (reg_office_id) ON DELETE RESTRICT
);

CREATE TABLE student_child
(
    student_child_id         SERIAL,
    student_order_id         INTEGER     NOT NULL,
    child_sur_name           VARCHAR(50) NOT NULL,
    child_given_name         VARCHAR(50) NOT NULL,
    child_patronymic         VARCHAR(50) NOT NULL,
    child_date_of_birth      DATE        NOT NULL,
    child_certificate_number VARCHAR(10) NOT NULL,
    child_certificate_date   DATE        NOT NULL,
    child_register_office_id INTEGER     NOT NULL,
    child_post_index         CHAR(6),
    child_street_code        INTEGER     NOT NULL,
    child_building           VARCHAR(5)  NOT NULL,
    child_extension          VARCHAR(5),
    child_apartment          VARCHAR(5),
    PRIMARY KEY (student_child_id),
    FOREIGN KEY (child_street_code) REFERENCES street (street_code) ON DELETE RESTRICT,
    FOREIGN KEY (child_register_office_id) REFERENCES register_office (reg_office_id) ON DELETE RESTRICT
);