-- Đặt database về single user và rollback connections
IF EXISTS(SELECT name FROM sys.databases WHERE name = 'SHOESTORE')
BEGIN
    ALTER DATABASE SHOESTORE SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE SHOESTORE;
    PRINT 'Da xoa database SHOESTORE thanh cong!';
END
ELSE
BEGIN
    PRINT 'Database SHOESTORE khong ton tai!';
END
GO

USE master
GO

IF EXISTS(SELECT * FROM sys.databases WHERE name='SHOESTORE')
DROP DATABASE SHOESTORE
GO

CREATE DATABASE SHOESTORE
GO

USE SHOESTORE
GO

CREATE TABLE membership_ranks (
    id INT IDENTITY(1,1) PRIMARY KEY,
    rank_name NVARCHAR(50),
    min_point INT,
    discount_percent DECIMAL(5,2),
    description NVARCHAR(255),
    status INT
)


CREATE TABLE accounts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_code VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    role VARCHAR(20),
    status INT,
    membership_rank_id INT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (membership_rank_id) REFERENCES membership_ranks(id)
)


CREATE TABLE addresses (
    id INT IDENTITY(1,1) PRIMARY KEY,
    receiving_name NVARCHAR(100),
    phone_number VARCHAR(15),
    province NVARCHAR(100),
    district NVARCHAR(50),
    ward NVARCHAR(50),
    street_detail NVARCHAR(150),
    is_default BIT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES accounts(id)
)


CREATE TABLE shops (
    id INT IDENTITY(1,1) PRIMARY KEY,
    shop_code VARCHAR(20) UNIQUE,
    shop_name NVARCHAR(100),
    description NVARCHAR(255),
    status INT,
    user_id INT,
    address_id INT,
    FOREIGN KEY (user_id) REFERENCES accounts(id),
    FOREIGN KEY (address_id) REFERENCES addresses(id)
)


CREATE TABLE categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    category_code VARCHAR(20) UNIQUE,
    category_name NVARCHAR(100),
    status INT
)


CREATE TABLE sizes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    size_name NVARCHAR(20)
)


CREATE TABLE colors (
    id INT IDENTITY(1,1) PRIMARY KEY,
    color_name NVARCHAR(50)
)


CREATE TABLE products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    product_code VARCHAR(20) UNIQUE,
    product_name NVARCHAR(255),
    description NVARCHAR(MAX),
    status INT,
    created_at DATETIME DEFAULT GETDATE(),
    shop_id INT,
    category_id INT,
    FOREIGN KEY (shop_id) REFERENCES shops(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
)


CREATE TABLE product_variants (
    id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT,
    size_id INT,
    color_id INT,
    price DECIMAL(12,2),
    quantity INT,
    status INT,

    UNIQUE(product_id,size_id,color_id),

    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (size_id) REFERENCES sizes(id),
    FOREIGN KEY (color_id) REFERENCES colors(id)
)


CREATE TABLE product_images (
    id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT,
    image_url NVARCHAR(255),
    is_primary BIT,
    FOREIGN KEY (product_id) REFERENCES products(id)
)


CREATE TABLE carts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES accounts(id)
)


CREATE TABLE cart_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    cart_id INT,
    product_variant_id INT,
    quantity INT,
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_variant_id) REFERENCES product_variants(id)
)


CREATE TABLE platform_vouchers (
    id INT IDENTITY(1,1) PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    discount_value DECIMAL(12,2),
    min_order_value DECIMAL(12,2),
    start_date DATETIME,
    end_date DATETIME,
    status INT
)


CREATE TABLE shop_vouchers (
    id INT IDENTITY(1,1) PRIMARY KEY,
    shop_id INT,
    code VARCHAR(20) UNIQUE,
    discount_value DECIMAL(12,2),
    min_order_value DECIMAL(12,2),
    start_date DATETIME,
    end_date DATETIME,
    status INT,
    FOREIGN KEY (shop_id) REFERENCES shops(id)
)


CREATE TABLE user_vouchers (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    voucher_type VARCHAR(20),
    voucher_id INT,
    is_used BIT,
    FOREIGN KEY (user_id) REFERENCES accounts(id)
)


CREATE TABLE payment_methods (
    id INT IDENTITY(1,1) PRIMARY KEY,
    method_name NVARCHAR(50)
)


CREATE TABLE shipping_methods (
    id INT IDENTITY(1,1) PRIMARY KEY,
    method_name NVARCHAR(100),
    fee DECIMAL(12,2),
    status INT
)


CREATE TABLE orders (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_code VARCHAR(20) UNIQUE,
    status INT,
    total_amount DECIMAL(12,2),
    shipping_fee DECIMAL(12,2),
    final_amount DECIMAL(12,2),
    receiver_address_id INT,
    shop_id INT,
    user_id INT,
    payment_method_id INT,
    shipping_method_id INT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (receiver_address_id) REFERENCES addresses(id),
    FOREIGN KEY (shop_id) REFERENCES shops(id),
    FOREIGN KEY (user_id) REFERENCES accounts(id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
    FOREIGN KEY (shipping_method_id) REFERENCES shipping_methods(id)
)


CREATE TABLE order_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    product_variant_id INT,
    quantity INT,
    price DECIMAL(12,2),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_variant_id) REFERENCES product_variants(id)
)


CREATE TABLE favourites (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    product_id INT,
    FOREIGN KEY (user_id) REFERENCES accounts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
)


CREATE TABLE product_reviews (
    id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT,
    user_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment NVARCHAR(MAX),
    status INT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES accounts(id)
)
GO

INSERT INTO membership_ranks(rank_name,min_point,discount_percent,description,status)
VALUES
(N'Đồng',0,0,N'Thành viên mới',1),
(N'Bạc',500,5,N'Thành viên bạc',1),
(N'Vàng',2000,10,N'Thành viên vàng',1);
GO

INSERT INTO accounts(user_code,email,password,full_name,phone,role,status,membership_rank_id)
VALUES
('U001','admin@gmail.com','123456',N'Admin','0900000001','ADMIN',1,3),
('U002','user1@gmail.com','123456',N'Nguyễn Văn A','0900000002','USER',1,1);
GO

INSERT INTO addresses(receiving_name,phone_number,province,district,ward,street_detail,is_default,user_id)
VALUES
(N'Admin','0900000001',N'Cần Thơ',N'Ninh Kiều',N'Xuân Khánh',N'45 Lê Lợi',1,1),
(N'Nguyễn Văn A','0900000002',N'Cần Thơ',N'Ninh Kiều',N'An Bình',N'12 Nguyễn Trãi',1,2);
GO

INSERT INTO shops(shop_code,shop_name,description,status,user_id,address_id)
VALUES
('S001',N'Shop Giày Sport',N'Chuyên giày thể thao',1,1,1);
GO

INSERT INTO categories(category_code,category_name,status)
VALUES
('C001',N'Giày thể thao',1),
('C002',N'Giày chạy bộ',1),
('C003',N'Giày bóng đá',1);
GO

-- NOTE: Set size_name as NVARCHAR explicitly above
INSERT INTO sizes(size_name)
VALUES
(N'38'),
(N'39'),
(N'40'),
(N'41'),
(N'42');
GO

INSERT INTO colors(color_name)
VALUES
(N'Đen'),
(N'Trắng'),
(N'Xanh');
GO

INSERT INTO products(product_code,product_name,description,status,shop_id,category_id)
VALUES
('P001',N'Nike Air',N'Giày Nike chính hãng',1,1,1),
('P002',N'Adidas Run',N'Giày chạy bộ',1,1,2);
GO

INSERT INTO product_variants(product_id,size_id,color_id,price,quantity,status)
VALUES
(1,3,1,1500000,10,1),
(1,4,2,1500000,8,1),
(2,3,2,1200000,5,1);
GO

INSERT INTO product_images(product_id,image_url,is_primary)
VALUES
(1,'nike1.jpg',1),
(1,'nike2.jpg',0),
(2,'adidas1.jpg',1);
GO

INSERT INTO carts(user_id)
VALUES
(2);
GO

INSERT INTO cart_items(cart_id,product_variant_id,quantity)
VALUES
(1,1,2);
GO

INSERT INTO platform_vouchers(code,discount_value,min_order_value,start_date,end_date,status)
VALUES
('SALE10',100000,500000,GETDATE(),'2026-01-01',1);
GO

INSERT INTO shop_vouchers(shop_id,code,discount_value,min_order_value,start_date,end_date,status)
VALUES
(1,'SHOP50',50000,300000,GETDATE(),'2026-01-01',1);
GO

INSERT INTO user_vouchers(user_id,voucher_type,voucher_id,is_used)
VALUES
(2,'PLATFORM',1,0),
(2,'SHOP',1,0);
GO

INSERT INTO payment_methods(method_name)
VALUES
(N'Thanh toán khi nhận hàng'),
(N'Chuyển khoản');
GO

INSERT INTO shipping_methods(method_name,fee,status)
VALUES
(N'Giao hàng nhanh',30000,1),
(N'Giao hàng tiết kiệm',15000,1);
GO

INSERT INTO orders(order_code,status,total_amount,shipping_fee,final_amount,
receiver_address_id,shop_id,user_id,payment_method_id,shipping_method_id)
VALUES
('O001',1,1500000,30000,1530000,2,1,2,1,1);
GO

INSERT INTO order_items(order_id,product_variant_id,quantity,price)
VALUES
(1,1,1,1500000);
GO

INSERT INTO favourites(user_id,product_id)
VALUES
(2,1),
(2,2);
GO

INSERT INTO product_reviews(product_id,user_id,rating,comment,status)
VALUES
(1,2,5,N'Giày rất đẹp',1),
(2,2,4,N'Mang êm',1);
GO
