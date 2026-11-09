CREATE TABLE book (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(100) NOT NULL,
                      author VARCHAR(50) NOT NULL,
                      price DECIMAL(10, 2) NOT NULL,
                      stock INT NOT NULL
);