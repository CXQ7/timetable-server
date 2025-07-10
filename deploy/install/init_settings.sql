CREATE TABLE calendar_settings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64),
    first_week_day DATE,
    classes_per_day INT,
    semester_count INT,
    show_saturday BOOLEAN,
    show_sunday BOOLEAN,
    show_non_current_week BOOLEAN,
    show_teacher BOOLEAN,
    show_classroom BOOLEAN,
    class_times JSON,
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);