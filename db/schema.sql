create database ecommercedb;

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(120) NOT NULL,
    user_name VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(255),
    phone_number VARCHAR(15),
    address TEXT,
    status VARCHAR(20) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE coupons (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    discount_percentage NUMERIC(5, 2) CHECK (discount_percentage >= 0 AND discount_percentage <= 100),
    valid_until TIMESTAMP,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (	
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    total_amount NUMERIC NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    coupon_id int REFERENCES coupons(id),
    full_name VARCHAR(255),
    phone_number VARCHAR(15),
    shipping_address TEXT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    image_url VARCHAR[],
    stock_quantity INT NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_categories (
    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    category_id INT REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, category_id)
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


insert into ecommercedb.public.roles("name") values 
('ROLE_USER'),
('ROLE_ADMIN'),
('ROLE_MODERATOR')


-- Thiết bị điện tử (Category ID: 1)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Smartphone XYZ', 'Điện thoại thông minh XYZ với nhiều tính năng hiện đại', 10000000, '{1,2,3}', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tablet ABC', 'Máy tính bảng ABC với màn hình lớn và pin lâu', 8000000, '{4,5}', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Smartwatch DEF', 'Đồng hồ thông minh DEF với khả năng theo dõi sức khỏe', 3000000, '{6}', 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Thời trang nam (Category ID: 2)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Áo sơ mi nam', 'Áo sơ mi nam chất liệu cotton cao cấp', 500000, '{7}', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Quần jean nam', 'Quần jean nam phong cách trẻ trung', 700000, '{8}', 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Áo khoác nam', 'Áo khoác nam giữ ấm mùa đông', 1200000, '{9,10}', 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Điện thoại & phụ kiện (Category ID: 3)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Ốp lưng điện thoại', 'Ốp lưng bảo vệ điện thoại chống va đập', 150000, '{11}', 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tai nghe không dây', 'Tai nghe Bluetooth chất lượng âm thanh cao', 1200000, '{12}', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cáp sạc nhanh', 'Cáp sạc nhanh tương thích với nhiều thiết bị', 300000, '{13}', 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Máy tính & laptop (Category ID: 4)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Laptop Gaming', 'Laptop chơi game hiệu năng cao', 25000000, '{14,15}', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bàn phím cơ', 'Bàn phím cơ RGB dành cho game thủ', 2000000, '{16}', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Chuột không dây', 'Chuột không dây tiện lợi', 800000, '{17}', 70, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Máy ảnh & máy quay phim (Category ID: 5)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Máy ảnh DSLR', 'Máy ảnh DSLR chuyên nghiệp', 30000000, '{18}', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ống kính máy ảnh', 'Ống kính chất lượng cao', 10000000, '{19}', 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gimbal', 'Gimbal chống rung cho máy quay', 5000000, '{20}', 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Đồng hồ (Category ID: 6)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Đồng hồ cơ', 'Đồng hồ cơ tự động', 15000000, '{21}', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Đồng hồ thể thao', 'Đồng hồ thể thao chống nước', 5000000, '{22}', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Đồng hồ thông minh', 'Đồng hồ thông minh với nhiều tính năng', 7000000, '{23}', 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Giày dép nam (Category ID: 7)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Giày thể thao', 'Giày thể thao nam năng động', 1500000, '{24}', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Giày tây', 'Giày tây nam lịch lãm', 2000000, '{25}', 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dép quai hậu', 'Dép quai hậu nam thoải mái', 500000, '{26}', 120, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Thiết bị điện gia dụng (Category ID: 8)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Máy hút bụi', 'Máy hút bụi công suất lớn', 3000000, '{27}', 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nồi chiên không dầu', 'Nồi chiên không dầu đa năng', 2500000, '{28}', 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Quạt điện', 'Quạt điện điều chỉnh 3 tốc độ', 800000, '{29}', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Thể thao & du lịch (Category ID: 9)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Ba lô du lịch', 'Ba lô du lịch chống thấm nước', 1200000, '{30}', 70, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bóng đá', 'Bóng đá tiêu chuẩn FIFA', 500000, '{31}', 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Vợt cầu lông', 'Vợt cầu lông siêu nhẹ', 800000, '{32}', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Thời trang nữ (Category ID: 10)
INSERT INTO products (name, description, price, image_url, stock_quantity, create_date, update_date) 
VALUES 
('Áo dài nữ', 'Áo dài truyền thống Việt Nam', 2000000, '{33}', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Đầm dạ hội', 'Đầm dạ hội sang trọng', 3000000, '{34}', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Túi xách nữ', 'Túi xách thời trang', 1500000, '{35}', 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
