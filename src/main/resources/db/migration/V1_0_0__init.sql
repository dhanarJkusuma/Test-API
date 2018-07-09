CREATE TABLE location (
    id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(10, 8) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE category (
    id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

ALTER TABLE category ADD CONSTRAINT category_unique_slug UNIQUE (slug);

CREATE TABLE event (
    id BIGSERIAL NOT NULL,
    name VARCHAR(150) NOT NULL,
    slug VARCHAR(200) NOT NULL,
    thumbnail_photo VARCHAR(255),
    organizer_name VARCHAR(50) NOT NULL,
    organizer_photo VARCHAR(255),
    description TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time VARCHAR(5) NOT NULL,
    end_time VARCHAR(5) NOT NULL,
    location_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT fk_location_idx_location_id
                    FOREIGN KEY (location_id)
                    REFERENCES location(id),
    CONSTRAINT fk_category_idx_category_id
                        FOREIGN KEY (category_id)
                        REFERENCES category(id)

);

ALTER TABLE event ADD CONSTRAINT event_unique_slug UNIQUE (slug);

CREATE TABLE ticket (
    id BIGSERIAL NOT NULL,
    event_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    ticket_count BIGINT NOT NULL,
    actual_ticket_count BIGINT NOT NULL,
    description TEXT NOT NULL,
    flag VARCHAR(10) NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT fk_event_idx_event_id
                    FOREIGN KEY (event_id)
                    REFERENCES event(id)

);

CREATE TABLE order_transaction (
    id BIGSERIAL NOT NULL,
    order_date TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_first_name VARCHAR(30) NOT NULL,
    customer_last_name VARCHAR(30) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(20) NOT NULL,
    customer_date_of_birth DATE NOT NULL,
    customer_gender VARCHAR(10) NOT NULL,
    event_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_event_idx_event_id
                        FOREIGN KEY (event_id)
                        REFERENCES event(id)
);

CREATE TABLE order_transaction_item (
    id BIGSERIAL NOT NULL,
    order_transaction_id BIGINT NOT NULL,
    ticket_id BIGINT NOT NULL,
    qty BIGINT NOT NULL,
    price NUMERIC(16, 2) NOT NULL,
    total_price NUMERIC(16, 2) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_transaction_idx_order_transaction_item
                        FOREIGN KEY (order_transaction_id)
                        REFERENCES order_transaction(id),
    CONSTRAINT fk_ticket_idx_ticket
                        FOREIGN KEY (ticket_id)
                        REFERENCES ticket(id),
);
