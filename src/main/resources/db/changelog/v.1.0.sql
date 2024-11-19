CREATE TABLE seller
(
    id               UUID PRIMARY KEY,
    telegram_id      BIGINT UNIQUE            NOT NULL,
    account_id       BIGINT                   NOT NULL,
    fullname         CHARACTER VARYING,
    phone            VARCHAR(15)              NOT NULL,
    email            CHARACTER VARYING        NOT NULL,
    login            CHARACTER VARYING UNIQUE NOT NULL,
    password         CHARACTER VARYING        NOT NULL,
    is_has_demo      BOOLEAN DEFAULT FALSE    NOT NULL,
    notify_enable    BOOLEAN DEFAULT FALSE    NOT NULL,
    last_query_stats TIMESTAMP
);

CREATE INDEX idx_seller_account_id ON seller (account_id);
CREATE INDEX idx_seller_is_has_demo ON seller (is_has_demo);

CREATE TABLE seller_shop
(
    id        UUID PRIMARY KEY,
    seller_id UUID              NOT NULL,
    shop_id   BIGINT            NOT NULL,
    name      CHARACTER VARYING NOT NULL,

    CONSTRAINT fk_seller_shop_seller_id
        FOREIGN KEY (seller_id) REFERENCES seller (id) ON DELETE CASCADE
);

CREATE INDEX idx_seller_shop_seller_shop ON seller_shop (seller_id, shop_id);

CREATE TABLE shop_item
(
    id               UUID PRIMARY KEY,
    marketplace      VARCHAR(5),
    seller_id        UUID              NOT NULL,
    seller_shop_id   UUID              NOT NULL,
    category_id      BIGINT            NOT NULL,
    product_id       BIGINT            NOT NULL,
    sku_id           BIGINT            NOT NULL,
    name             CHARACTER VARYING NOT NULL,
    price            DECIMAL(10, 2)    NOT NULL,
    purchase_price   DECIMAL(10, 2),
    product_sku      CHARACTER VARYING NOT NULL,
    available_amount BIGINT            NOT NULL,
    date_collection  TIMESTAMP         NOT NULL,

    CONSTRAINT fk_seller_shop_item_seller
        FOREIGN KEY (seller_id) REFERENCES seller (id) ON DELETE CASCADE,
    CONSTRAINT fk_seller_shop_item_seller_shop
        FOREIGN KEY (seller_shop_id) REFERENCES seller_shop (id) ON DELETE CASCADE
);

CREATE INDEX idx_shop_item_seller_shop_category ON shop_item (seller_id, seller_shop_id, category_id);
