CREATE TABLE couriers (
    id UUID PRIMARY KEY,
    user_id UUID UNIQUE NOT NULL,
    vehicle_type VARCHAR(30) NOT NULL CHECK (vehicle_type IN ('BICYCLE', 'CAR', 'MOTORCYCLE')),
    plate_number VARCHAR(30),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_courier_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Inserção de usuários fictícios para os entregadores padrão
INSERT INTO users (id, name, email, created_at) VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'Entregador Bike', 'bike@entrega.com', NOW()),
    ('550e8400-e29b-41d4-a716-446655440002', 'Entregador Carro', 'car@entrega.com', NOW()),
    ('550e8400-e29b-41d4-a716-446655440003', 'Entregador Moto', 'moto@entrega.com', NOW());

-- Inserção de entregadores padrão
INSERT INTO couriers (id, user_id, vehicle_type, plate_number, created_at) VALUES
    ('6e4e225e-7f64-4b96-9cd5-446655440001', '550e8400-e29b-41d4-a716-446655440001', 'BICYCLE', NULL, NOW()),
    ('6e4e225e-7f64-4b96-9cd5-446655440002', '550e8400-e29b-41d4-a716-446655440002', 'CAR', 'ABC1234', NOW()),
    ('6e4e225e-7f64-4b96-9cd5-446655440003', '550e8400-e29b-41d4-a716-446655440003', 'MOTORCYCLE', 'XYZ5678', NOW());
