#. DROP DATABASE tona_mart;
#. CREATE DATABASE tona_mart;
USE tona_mart;

#. CƠ CHẾ BẢO MẬT
CREATE TABLE tbl_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    status ENUM('Đang hoạt động', 'Dừng hoạt động', 'Khóa') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'Đang hoạt động',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tbl_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci UNIQUE NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tbl_group (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci UNIQUE NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tbl_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci UNIQUE NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tbl_user_role (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id),
    FOREIGN KEY (role_id) REFERENCES tbl_role(id)
);

CREATE TABLE tbl_group_role (
    group_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (group_id, role_id),
    FOREIGN KEY (group_id) REFERENCES tbl_group(id),
    FOREIGN KEY (role_id) REFERENCES tbl_role(id)
);

CREATE TABLE tbl_role_permission (
    role_id BIGINT,
    permission_id BIGINT,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES tbl_role(id),
    FOREIGN KEY (permission_id) REFERENCES tbl_permission(id)
);

CREATE TABLE tbl_user_group (
    user_id BIGINT,
    group_id BIGINT,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id),
    FOREIGN KEY (group_id) REFERENCES tbl_group(id)
);

-- Thông tin tài khoản
CREATE TABLE tbl_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    first_name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    last_name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    gender ENUM('Nam', 'Nữ', 'Khác'), 
    date_of_birth DATE, 
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id)
);

CREATE TABLE tbl_address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT, 
    province VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci, -- Tỉnh/Thành phố
    district VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci, -- Huyện/Quận
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) 
);



#. THÔNG TIN SẢN PHẨM

-- Thương hiệu
CREATE TABLE tbl_brand (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci UNIQUE NOT NULL,
    description TEXT
);

-- Danh mục sản phẩm
CREATE TABLE tbl_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    FOREIGN KEY (parent_id) REFERENCES tbl_category(id) ON DELETE CASCADE
);


-- Biến thể sản phẩm
CREATE TABLE tbl_variation (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci UNIQUE NOT NULL
);

CREATE TABLE tbl_variation_category (
	category_id BIGINT,
	variation_id BIGINT,
    PRIMARY KEY(category_id, variation_id),
    FOREIGN KEY(category_id) REFERENCES tbl_category(id),
    FOREIGN KEY(variation_id) REFERENCES tbl_variation(id)
);

-- Loại biến thể
CREATE TABLE tbl_variation_option (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    variation_id BIGINT,
    value VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci UNIQUE NOT NULL,
    FOREIGN KEY(variation_id) REFERENCES tbl_variation(id)
);


-- Sản phẩm chung
CREATE TABLE tbl_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    promotion_rate INT,
    price DECIMAL(18, 0),
    status enum( 'AVAILABLE', 'OUT_OF_STOCK', 'DISCONTINUED', 'COMING_SOON'),
    product_image TEXT,
    brand_id BIGINT,
    category_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (brand_id) REFERENCES tbl_brand(id),
    FOREIGN KEY (category_id) REFERENCES tbl_category(id)
);

-- Chi tiết từng sản phẩm
CREATE TABLE tbl_product_detail (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    sku VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    qty INT NOT NULL,
    product_image TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    price DECIMAL(18, 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES tbl_product(id)
);

-- Chi tiết biến thể của sản phẩm
CREATE TABLE tbl_product_configuration (
	product_id BIGINT,
    variation_option_id BIGINT,
    PRIMARY KEY(product_id, variation_option_id),
    FOREIGN KEY (product_id) REFERENCES tbl_product_detail(id),
    FOREIGN KEY (variation_option_id) REFERENCES tbl_variation_option(id)
);

-- Quảng cáo 
CREATE TABLE tbl_advertise (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    banner_image TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    FOREIGN KEY (product_id) REFERENCES tbl_product(id)
);

-- Yêu thích
CREATE TABLE tbl_favourite (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES tbl_product(id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id)
);

-- Khuyến mãi
CREATE TABLE tbl_promotion (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    discount_rate INT NOT NULL,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP NOT NULL
);

CREATE TABLE tbl_product_promotion (
	product_id BIGINT,
    promotion_id BIGINT,
    PRIMARY KEY (product_id, promotion_id),
    FOREIGN KEY (product_id) REFERENCES tbl_product(id),
    FOREIGN KEY (promotion_id) REFERENCES tbl_promotion(id)
);
#. THÔNG SỐ KỸ THUẬT

-- Bảng thông số kỹ thuật
CREATE TABLE tbl_specification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT
);

-- Bảng giá trị thông số kỹ thuật
CREATE TABLE tbl_specification_value (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    specification_id BIGINT,
    product_id BIGINT,
    value TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    FOREIGN KEY (specification_id) REFERENCES tbl_specification(id),
    FOREIGN KEY (product_id) REFERENCES tbl_product(id)
);


#. GIỎ HÀNG
CREATE TABLE tbl_cart (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id)
);

CREATE TABLE tbl_cart_detail (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_id BIGINT,
    product_detail_id BIGINT,
    qty INT,
    FOREIGN KEY (cart_id) REFERENCES tbl_cart(id),
    FOREIGN KEY (product_detail_id) REFERENCES tbl_product_detail(id)
);

#. THÔNG BÁO

CREATE TABLE tbl_notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    title VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    message TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id)
);

#. THANH TOÁN

CREATE TABLE tbl_payment_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description TEXT
);

CREATE TABLE tbl_user_payment_method (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    payment_type_id BIGINT,
    provider VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    account_number VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    account_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id),
    FOREIGN KEY (payment_type_id) REFERENCES tbl_payment_type(id)
);


#. GIAO HÀNG

CREATE TABLE tbl_shipping_address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT, 
    province VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci, -- Tỉnh/Thành phố
    district VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci, -- Huyện/Quận
    street VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci, -- Tên đường
    apartment VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci, -- Số nhà
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) 
);
 
CREATE TABLE tbl_shipping_method (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    price decimal(18, 0) NOT NULL
);

CREATE TABLE tbl_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    shipping_address_id BIGINT,
    shipping_method_id BIGINT,
    payment_method_id BIGINT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(18, 0),
    status ENUM('Chờ xác nhận', 'Đang trên đường giao', 'Đã giao thành công', 'Đã hủy') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'Chờ xác nhận',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id),
    FOREIGN KEY (shipping_method_id) REFERENCES tbl_shipping_method(id),
    FOREIGN KEY (shipping_address_id) REFERENCES tbl_shipping_address(id),
	FOREIGN KEY (payment_method_id) REFERENCES tbl_user_payment_method(id)
);

CREATE TABLE tbl_transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    order_id BIGINT,
    payment_method_id BIGINT,
    amount DECIMAL(18, 2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Đang xử lý', 'Hoàn thành', 'Thất bại', 'Đã hoàn tiền') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'Đang xử lý',
    FOREIGN KEY (user_id) REFERENCES tbl_user(id),
    FOREIGN KEY (order_id) REFERENCES tbl_order(id),
    FOREIGN KEY (payment_method_id) REFERENCES tbl_user_payment_method(id)
);


CREATE TABLE tbl_order_detail (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_item_id BIGINT,
    order_id BIGINT,
    qty INT,
    price DECIMAL(18, 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_item_id) REFERENCES tbl_product_detail(id),
    FOREIGN KEY (order_id) REFERENCES tbl_order(id)
);

CREATE TABLE tbl_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_detail_id BIGINT,
    user_id BIGINT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_detail_id) REFERENCES tbl_order_detail(id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id)
);









#. CHUẨN HÓA DỮ LIỆU


-- Thêm dữ liệu cho các danh mục lớn
INSERT INTO tbl_category (name, parent_id) VALUES ('Công nghệ', NULL);
INSERT INTO tbl_category (name, parent_id) VALUES ('Gia dụng', NULL);
INSERT INTO tbl_category (name, parent_id) VALUES ('Thời trang', NULL);


-- Thêm dữ liệu cho các danh mục con của "Công nghệ"
SET @parent_id = (SELECT id FROM tbl_category WHERE name = 'Công nghệ');
INSERT INTO tbl_category (name, parent_id) VALUES
('Điện thoại', @parent_id),
('Máy tính bảng', @parent_id),
('Laptop', @parent_id),
('PC', @parent_id),
('TV', @parent_id),
('Đồng hồ', @parent_id),
('Tai nghe', @parent_id);


-- Thêm dữ liệu cho các danh mục con của "Gia dụng"
SET @parent_id = (SELECT id FROM tbl_category WHERE name = 'Gia dụng');
INSERT INTO tbl_category (name, parent_id) VALUES
('Nồi chiên không dầu', @parent_id),
('Máy rửa bát', @parent_id),
('Lò vi sóng', @parent_id),
('Nồi cơm điện', @parent_id),
('Máy xay sinh tố', @parent_id),
('Máy ép trái cây', @parent_id),
('Máy làm sữa hạt', @parent_id),
('Bếp điện', @parent_id),
('Ấm siêu tốc', @parent_id),
('Nồi áp suất', @parent_id);


-- Thêm dữ liệu cho các danh mục con của "Thời trang"
SET @parent_id = (SELECT id FROM tbl_category WHERE name = 'Thời trang');
INSERT INTO tbl_category (name, parent_id) VALUES
-- Thêm các danh mục con cho Thời trang tại đây, ví dụ:
('Áo sơ mi', @parent_id),
('Quần jean', @parent_id),
('Giày dép', @parent_id);


-- Thêm dữ liệu mẫu cho bảng tbl_brand
INSERT INTO tbl_brand (name, description) VALUES 
('Apple', 'Thương hiệu công nghệ nổi tiếng của Mỹ'),
('Samsung', 'Thương hiệu công nghệ lớn của Hàn Quốc'),
('Sony', 'Thương hiệu điện tử nổi tiếng của Nhật Bản'),
('LG', 'Thương hiệu điện tử và gia dụng lớn của Hàn Quốc'),
('Panasonic', 'Thương hiệu điện tử và gia dụng nổi tiếng của Nhật Bản'),
('Nike', 'Thương hiệu thời trang thể thao nổi tiếng của Mỹ'),
('Adidas', 'Thương hiệu thời trang thể thao nổi tiếng của Đức'),
('Philips', 'Thương hiệu điện tử và gia dụng nổi tiếng của Hà Lan'),
('Bose', 'Thương hiệu âm thanh cao cấp của Mỹ'),
('Dell', 'Thương hiệu máy tính nổi tiếng của Mỹ');


-- Thêm dữ liệu mẫu cho bảng tbl_variation
INSERT INTO tbl_variation (name) VALUES 
('Color'),
('Size'),
('Ram'),
('Bộ nhớ trong'),
('Ổ cứng SSD'),
('Công suất');

-- Thêm dữ liệu mẫu cho bảng tbl_variation_category
SET @tech_id = (SELECT id FROM tbl_category WHERE name = 'Công Nghệ');
SET @ram_id = (SELECT id FROM tbl_variation WHERE name = 'Ram');
SET @o_cung_id = (SELECT id FROM tbl_variation WHERE name = 'Ổ cứng SSD');
SET @bo_nho_id = (SELECT id FROM tbl_variation WHERE name = 'Bộ nhớ trong');
INSERT INTO tbl_variation_category (category_id, variation_id) VALUES 
(@tech_id, @ram_id),
(@tech_id, @o_cung_id),
(@tech_id, @bo_nho_id);


SET @fashion_id = (SELECT id FROM tbl_category WHERE name = 'Thời Trang');
SET @color_id = (SELECT id FROM tbl_variation WHERE name = 'Color');
SET @size_id = (SELECT id FROM tbl_variation WHERE name = 'Size');
INSERT INTO tbl_variation_category (category_id, variation_id) VALUES 
(@fashion_id, @color_id),
(@fashion_id, @size_id);

SET @houseware_id  = (SELECT id FROM tbl_category WHERE name = 'Gia dụng');
SET @power_id = (SELECT id FROM tbl_variation WHERE name = 'Công suất');
INSERT INTO tbl_variation_category (category_id, variation_id) VALUES 
(@houseware_id, @power_id);



#. THÊM CÁC GIÁ TRỊ CHO BIẾN THỂ
-- Lấy id của các loại biến thể
SET @color_id = (SELECT id FROM tbl_variation WHERE name = 'Color');
SET @size_id = (SELECT id FROM tbl_variation WHERE name = 'Size');
SET @ram_id = (SELECT id FROM tbl_variation WHERE name = 'Ram');
SET @bo_nho_id = (SELECT id FROM tbl_variation WHERE name = 'Bộ nhớ trong');
SET @o_cung_id = (SELECT id FROM tbl_variation WHERE name = 'Ổ cứng SSD');
SET @power_id = (SELECT id FROM tbl_variation WHERE name = 'Công suất');
-- Thêm các tùy chọn biến thể
INSERT INTO tbl_variation_option (variation_id, value) VALUES 
(@color_id, 'Trắng'),
(@color_id, 'Đen'),
(@color_id, 'Xám'),
(@color_id, 'Vàng'),
(@color_id, 'Đỏ'),
(@color_id, 'Cam'),
(@color_id, 'Tím'),
(@color_id, 'Nâu'),
(@size_id, 'S'),
(@size_id, 'M'),
(@size_id, 'L'),
(@ram_id, '4GB'),
(@ram_id, '8GB'),
(@ram_id, '16GB'),
(@bo_nho_id, '64GB'),
(@bo_nho_id, '128GB'),
(@bo_nho_id, '256GB'),
(@o_cung_id, '512GB'),
(@o_cung_id, '1TB'),
(@power_id, '500W'),
(@power_id, '1000W'),
(@power_id, '1500W'),
(@power_id, '2000W');
