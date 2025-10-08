CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    courier_id UUID,
    status VARCHAR(30) NOT NULL CHECK (status IN ('PENDING', 'IN_PROGRESS', 'DELIVERED', 'CANCELED')),
    total_price DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(30) NOT NULL CHECK (payment_status IN ('PENDING', 'PAID', 'FAILED')),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_order_courier FOREIGN KEY (user_id) REFERENCES users(id)
);
