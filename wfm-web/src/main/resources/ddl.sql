CREATE TABLE IF NOT EXISTS FILE_META_TABLE(
    id VARCHAR(32) PRIMARY KEY,
    file_name VARCHAR_IGNORECASE(255),
    file_size BIGINT,
    file_type TINYINT,
    index_id SMALLINT,
    directory_id INT,
    file_modify_time TIMESTAMP
);
CREATE TABLE IF NOT EXISTS DIRECTORY_TABLE(
    id INT AUTO_INCREMENT PRIMARY KEY,
    parent_id INT,
    index_id SMALLINT,
    path VARCHAR(1023),
    directory_modify_time TIMESTAMP
);
CREATE TABLE IF NOT EXISTS BASE_CONFIG_TABLE(
    id INT AUTO_INCREMENT PRIMARY KEY,
    other_files VARCHAR(25600),
    domain VARCHAR(1024),
    init BOOLEAN
);
CREATE TABLE IF NOT EXISTS INDEX_DIRECTORY_TABLE(
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    path VARCHAR(1023),
    listen BOOLEAN,
    is_remote BOOLEAN,
    protocol TINYINT,
    authentication VARCHAR,
    index_time TIMESTAMP
);
CREATE TABLE IF NOT EXISTS SHARE_FILE_TABLE(
    code VARCHAR(32) PRIMARY KEY,
    file_name VARCHAR,
    fetch_code VARCHAR(6),
    download_count INT,
    enable BOOLEAN,
    share_time TIMESTAMP,
    invalid_time TIMESTAMP
);
CREATE TABLE IF NOT EXISTS USERS_TABLE(
    user_name VARCHAR PRIMARY KEY,
    password VARCHAR,
    enable BOOLEAN,
    role_id SMALLINT
);
CREATE TABLE IF NOT EXISTS ROLES_TABLE(
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR,
    authorities VARCHAR
);
CREATE INDEX IF NOT EXISTS INDEX_FILE_NAME ON FILE_META_TABLE(file_name);
CREATE INDEX IF NOT EXISTS INDEX_FILE_DIRECTORY_ID ON FILE_META_TABLE(directory_id);
CREATE INDEX IF NOT EXISTS INDEX_DIRECTORY_PATH ON DIRECTORY_TABLE(path);