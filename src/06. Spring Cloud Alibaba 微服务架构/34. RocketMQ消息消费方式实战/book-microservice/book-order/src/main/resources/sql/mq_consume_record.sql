CREATE TABLE IF NOT EXISTS mq_consume_record (
    id BIGSERIAL PRIMARY KEY,
    consumer_group VARCHAR(100) NOT NULL,
    biz_key VARCHAR(100) NOT NULL,
    topic VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PROCESSING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_consumer_group_biz_key UNIQUE (consumer_group, biz_key)
    );

CREATE INDEX idx_mq_consume_record_status ON mq_consume_record(status);