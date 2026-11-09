-- ======================================================
-- 1. 图书表 (book)
-- ======================================================
CREATE TABLE IF NOT EXISTS book (
                                    id BIGSERIAL PRIMARY KEY,
                                    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    stock INT NOT NULL,
    sales INT DEFAULT 0,
    category VARCHAR(20),
    isbn VARCHAR(20),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_book_author ON book(author);
CREATE INDEX idx_book_category ON book(category);
CREATE INDEX idx_book_price ON book(price);

-- ======================================================
-- 2. 订单表 (orders)
-- ======================================================
CREATE TABLE IF NOT EXISTS orders (
                                      id BIGSERIAL PRIMARY KEY,
                                      order_number VARCHAR(20) NOT NULL UNIQUE,
    total_amount NUMERIC(12, 2),
    status VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at);

-- ======================================================
-- 3. 订单项表 (order_item)
-- ======================================================
CREATE TABLE IF NOT EXISTS order_item (
                                          id BIGSERIAL PRIMARY KEY,
                                          quantity INT NOT NULL,
                                          price NUMERIC(10, 2) NOT NULL,
    order_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id)
    REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_book FOREIGN KEY (book_id)
    REFERENCES book(id) ON DELETE RESTRICT
    );

CREATE INDEX idx_order_item_order_id ON order_item(order_id);
CREATE INDEX idx_order_item_book_id ON order_item(book_id);




-- ========================
-- 1. 初始化图书数据
-- ========================
INSERT INTO book (title, author, price, stock, sales, category, isbn, description) VALUES
                                                                                       ('Spring实战', 'Craig Walls', 79.00, 100, 20, '技术', '978-7-111-12345-6', 'Spring框架经典教程'),
                                                                                       ('深入理解Java虚拟机', '周志明', 129.00, 50, 30, '技术', '978-7-111-54321-0', 'JVM深度剖析'),
                                                                                       ('三体', '刘慈欣', 93.00, 200, 150, '科幻', '978-7-5366-9293-0', '科幻巨作'),
                                                                                       ('人类简史', '尤瓦尔·赫拉利', 68.00, 80, 60, '历史', '978-7-5086-5847-2', '人类历史通俗读物'),

-- 更新Book表添加一列逻辑删除标志位
ALTER TABLE book ADD COLUMN deleted INT DEFAULT 0;                                                                                       ('算法导论', 'Thomas H. Cormen', 128.00, 30, 25, '技术', '978-7-111-40701-0', '算法经典教材');