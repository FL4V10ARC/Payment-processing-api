CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE payments (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    description VARCHAR(255),
    correlation_id VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_payment_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE payment_audits (
    id UUID PRIMARY KEY,
    payment_id UUID NOT NULL,
    old_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    changed_at TIMESTAMP NOT NULL,
    correlation_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_audit_payment
        FOREIGN KEY (payment_id)
        REFERENCES payments(id)
);