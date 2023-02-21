CREATE TABLE IF NOT EXISTS MESSAGE (
    message_id bigint NOT NULL AUTO_INCREMENT,
    message varchar(100) NOT NULL,
    PRIMARY KEY (message_id)
);

CREATE TABLE IF NOT EXISTS MEMBER (
    member_id bigint NOT NULL AUTO_INCREMENT,
    email varchar(100) NOT NULL UNIQUE,
    name varchar(100) NOT NULL,
    phone varchar(20) NOT NULL,
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS COFFEE (
    coffee_id bigint NOT NULL AUTO_INCREMENT,
    kor_name varchar(100) NOT NULL,
    eng_name varchar(100) NOT NULL,
    price int NOT NULL,
    coffee_code char(3) NOT NULL,
    PRIMARY KEY (coffee_id)
);

CREATE TABLE IF NOT EXISTS ORDERS (
    order_id bigint NOT NULL AUTO_INCREMENT,
    member_id bigint NOT NULL,
    order_status varchar(20) NOT NULL,
    created_at datetime NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (member_id) REFERENCES MEMBER(member_id)
);

CREATE TABLE IF NOT EXISTS ORDER_COFFEE (
    order_coffee_id bigint NOT NULL AUTO_INCREMENT,
    order_id bigint NOT NULL,
    coffee_id bigint NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY (order_coffee_id),
    FOREIGN KEY (order_id) REFERENCES ORDERS(order_id),
    FOREIGN KEY (coffee_id) REFERENCES COFFEE(coffee_id)
);
