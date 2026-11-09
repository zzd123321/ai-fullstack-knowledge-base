DROP TABLE IF EXISTS book;

CREATE TABLE book (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(100) NOT NULL,
                      author VARCHAR(50) NOT NULL,
                      price NUMERIC(10, 2) NOT NULL,
                      stock INT NOT NULL,
                      sales INT DEFAULT 0,
                      category VARCHAR(20),
                      isbn VARCHAR(20),
                      description VARCHAR(100),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      deleted INT NOT NULL DEFAULT 0
);