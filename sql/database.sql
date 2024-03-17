-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: mysql8-container
-- Thời gian đã tạo: Th3 17, 2024 lúc 09:19 AM
-- Phiên bản máy phục vụ: 8.2.0
-- Phiên bản PHP: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ShopApp`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(3, 'Bánh kẹo'),
(5, 'Chăn ga gối đệm'),
(7, 'Phụ kiện'),
(6, 'Quần áo'),
(4, 'Đồ gia dụng'),
(2, 'Đồ điện tử');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comments`
--

CREATE TABLE `comments` (
  `id` bigint NOT NULL,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coupons`
--

CREATE TABLE `coupons` (
  `id` int NOT NULL,
  `code` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `coupons`
--

INSERT INTO `coupons` (`id`, `code`, `active`) VALUES
(1, 'HAPPY', 1),
(2, '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coupon_conditions`
--

CREATE TABLE `coupon_conditions` (
  `id` bigint NOT NULL,
  `coupon_id` int NOT NULL,
  `attribute` varchar(255) NOT NULL,
  `operator` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `discount_amount` decimal(38,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `coupon_conditions`
--

INSERT INTO `coupon_conditions` (`id`, `coupon_id`, `attribute`, `operator`, `value`, `discount_amount`) VALUES
(1, 1, 'minimum_amount', '>', '100', 10.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `flyway_schema_history`
--

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `flyway_schema_history`
--

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(2, '1', 'alter some tables', 'SQL', 'V1__alter_some_tables.sql', 670188877, 'root', '2023-12-01 10:05:58', 605, 1),
(3, '2', 'change tokens', 'SQL', 'V2__change_tokens.sql', 1468721430, 'root', '2023-12-01 10:05:58', 27, 1),
(4, '3', 'refresh tokens', 'SQL', 'V3__refresh_tokens.sql', 1847335528, 'root', '2023-12-03 04:50:25', 36, 1),
(5, '4', 'create comments table', 'SQL', 'V4__create_comments_table.sql', -16394321, 'root', '2024-03-10 07:51:13', 187, 1),
(6, '5', 'create coupon table', 'SQL', 'V5__create_coupon_table.sql', 1588630482, 'root', '2024-03-10 07:51:13', 757, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `phone_number` varchar(100) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `note` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `order_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_money` float DEFAULT NULL,
  `shipping_method` varchar(255) DEFAULT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `shipping_date` date DEFAULT NULL,
  `tracking_number` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `coupon_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `fullname`, `email`, `phone_number`, `address`, `note`, `order_date`, `status`, `total_money`, `shipping_method`, `shipping_address`, `shipping_date`, `tracking_number`, `payment_method`, `active`, `coupon_id`) VALUES
(90, 5, 'Nguyễn Văn Y', '12312@yahoo.com', '1232131', 'Nhà a ngõ B, ngách D', 'Hàng dễ vỡ xin nhẹ tay', '2024-03-14', 'pending', 123.45, 'express', NULL, '2024-03-14', NULL, 'cod', 1, 1),
(91, 11, 'ád', 'chinh1726@gmail.com', '0838832312', '0838832312', '0838832312', '2024-03-17', 'pending', 5.4, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(92, 11, '0838832312', 'chinh1726@gmail.com', '0838832312', '0838832312', '0838832312', '2024-03-17', 'pending', 10.8, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(93, 11, '0838832312', 'chinh1726@gmail.com', '0838832312', '0838832312', '0838832312', '2024-03-17', 'pending', 16.2, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 1),
(94, 11, '0838832312', 'chinh1726@gmail.com', '0838832312', 'chinh1726@gmail.com', 'chinh1726@gmail.com', '2024-03-17', 'pending', 43.2, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(95, 11, 'ád', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', '2024-03-17', 'pending', 35.1, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(96, 11, 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', NULL, 'shipped', 32.4, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(97, 11, 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', NULL, 'shipped', 32.4, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(98, 11, 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', NULL, 'shipped', 32.4, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(99, 11, '0838832312', 'chinh1726@gmail.com', '0838832312', '0838832312', '0838832312', NULL, 'processing', 21.6, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(100, 11, '0838832312', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', 'chinh1726@gmail.com', NULL, 'shipped', 27, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(101, 11, '0838832312', 'chinh1726@gmail.com', '0838832312', 'chinh1726@gmail.com', 'chinh1726@gmail.com', NULL, 'shipped', 5.4, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(102, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', NULL, 'processing', 8.87, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(103, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', NULL, 'processing', 5.4, 'express', NULL, NULL, NULL, 'cod', 1, 2),
(104, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', '2024-03-17', 'pending', 5.4, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(105, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', '2024-03-17', 'pending', 124.18, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(106, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', '2024-03-17', 'pending', 108, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(107, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', '2024-03-17', 'pending', 386.1, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(108, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', '2024-03-17', 'pending', 140.4, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2),
(109, 5, 'Nguyen Thuy Vy', 'nthuyvyy910@gmail.com', '0838832313', 'ho chi minh city', '', '2024-03-17', 'pending', 10.8, 'express', NULL, '2024-03-17', NULL, 'cod', 1, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_details`
--

CREATE TABLE `order_details` (
  `id` bigint NOT NULL,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `price` float NOT NULL,
  `number_of_products` int DEFAULT '1',
  `total_money` decimal(10,2) DEFAULT '0.00',
  `color` varchar(255) DEFAULT NULL,
  `coupon_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `order_details`
--

INSERT INTO `order_details` (`id`, `order_id`, `product_id`, `price`, `number_of_products`, `total_money`, `color`, `coupon_id`) VALUES
(147, 90, 5, 32.85, 7, NULL, NULL, NULL),
(148, 90, 7, 32.94, 2, NULL, NULL, NULL),
(149, 90, 9, 45.88, 3, NULL, NULL, NULL),
(150, 91, 29, 5.4, 1, NULL, NULL, NULL),
(151, 92, 29, 5.4, 2, NULL, NULL, NULL),
(152, 93, 29, 5.4, 3, NULL, NULL, NULL),
(153, 94, 29, 5.4, 8, NULL, NULL, NULL),
(154, 95, 30, 35.1, 1, NULL, NULL, NULL),
(155, 96, 29, 5.4, 6, NULL, NULL, NULL),
(156, 97, 29, 5.4, 6, NULL, NULL, NULL),
(157, 98, 29, 5.4, 6, NULL, NULL, NULL),
(158, 99, 29, 5.4, 4, NULL, NULL, NULL),
(159, 100, 29, 5.4, 5, NULL, NULL, NULL),
(160, 101, 29, 5.4, 1, NULL, NULL, NULL),
(161, 102, 28, 8.87, 1, NULL, NULL, NULL),
(162, 103, 29, 5.4, 1, NULL, NULL, NULL),
(163, 104, 29, 5.4, 1, NULL, NULL, NULL),
(164, 105, 28, 8.87, 14, NULL, NULL, NULL),
(165, 106, 29, 5.4, 20, NULL, NULL, NULL),
(166, 107, 30, 35.1, 11, NULL, NULL, NULL),
(167, 108, 30, 35.1, 4, NULL, NULL, NULL),
(168, 109, 29, 5.4, 2, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` int NOT NULL,
  `name` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Tên sản phẩm',
  `price` float DEFAULT NULL,
  `thumbnail` varchar(300) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `thumbnail`, `description`, `created_at`, `updated_at`, `category_id`) VALUES
(1, 'Kệ sách', 70.07, 'f2fcda26-8304-48bc-9472-ead1c297770f_001.jpg', 'Thiết kế nhỏ gọn.', '2023-07-31 08:28:28.000000', '2024-03-14 11:40:46.292620', 4),
(2, 'Bàn chải điện', 18.09, '34823684-2d69-4e22-8ad6-aa2bc1ccbaf6_006.jpg', 'Nhiều màu sắc', '2023-07-31 08:28:28.000000', '2024-03-16 06:34:08.696840', 2),
(3, 'Hộp đựng thứuc ăn', 12.87, 'c4bd25e4-620c-44fc-9f69-22edde299a8f_011.jpg', 'Nhiều tầng, tiện lợi.', '2023-07-31 08:28:28.000000', '2024-03-15 08:16:48.198473', 4),
(5, 'Ổ cắm', 32.85, '6ba6318c-ed8b-4f6d-9a9b-d8ca1c6e1afc_016.jpg', 'Tiện dụng', '2023-07-31 08:28:28.000000', '2024-03-16 06:38:44.195201', 2),
(6, 'Quạt đeo cổ', 91.24, '3ade127a-dd5f-43d9-88a7-9b8d2c529d08_Screenshot 2024-01-06 093733.png', 'Làm mát tiện dụng', '2023-07-31 08:28:28.000000', '2024-03-16 06:43:50.476327', 2),
(7, 'Đèn trang trí', 32.94, '3036dff0-72cd-48d4-9efd-7221f41fc61d_022.jpg', 'Xinh xắn', '2023-07-31 08:28:28.000000', '2024-03-16 06:46:38.835959', 2),
(8, 'Cây lăn bụi', 25.76, '36bf6016-4eff-4864-bf27-1de57e7d6589_027.jpg', 'Dễ sử dụng', '2023-07-31 08:28:28.000000', '2024-03-16 06:49:11.046197', 4),
(9, 'Mắt kính', 45.88, '326235eb-015b-4176-8977-fb96df932557_032.jpg', 'Cho nữ', '2023-07-31 08:28:28.000000', '2024-03-16 06:49:39.976949', 7),
(10, 'Viết nhiều màu', 42.91, '3d3097a5-38dc-45b7-81be-7300816ab4b9_037.jpg', 'Dễ sử dụng', '2023-07-31 08:28:28.000000', '2024-03-16 06:51:06.518689', 7),
(11, 'Gấu bông', 76.33, '67dedbd7-d14a-4db0-801f-3ab18fdca6d3_038.jpg', 'Khủng lông xanh', '2023-07-31 08:28:28.000000', '2024-03-16 06:53:07.932228', 5),
(12, 'Kệ để bút', 16.09, 'cf00f484-f8a4-4b9a-8a9f-ef74bfd7ebcf_045.jpg', 'Nhiều ngăn', '2023-07-31 08:28:28.000000', '2024-03-16 06:54:12.982797', 7),
(13, 'Rổ đựng thức ăn', 30.82, '862e0948-8961-4c2a-b76a-f2322c846a4b_050.jpg', 'Tiện dụng', '2023-07-31 08:28:28.000000', '2024-03-16 06:57:34.207544', 4),
(14, 'Hộp bảo quản thức ăn', 32.83, 'd152fdfd-6c49-440c-a249-8fdc08646333_055.jpg', 'Nhiều ngăn', '2023-07-31 08:28:28.000000', '2024-03-16 07:00:15.735539', 4),
(15, 'Hệ thống trợ lực', 21.25, '904a9f1c-207c-4df6-bb49-c954656a8c06_059.jpg', 'THông minh', '2023-07-31 08:28:28.000000', '2024-03-16 07:00:54.416280', 7),
(16, 'Đồng hồ', 56.38, 'c86bb69d-e5a2-4c93-8f0a-87504a8966ea_064.jpg', 'Cá tính', '2023-07-31 08:28:28.000000', '2024-03-16 07:01:11.819751', 2),
(17, 'Thước dây đo', 9.99, 'dafed832-a59c-41ad-bc60-011fadaf8020_069.jpg', 'Kéo dài', '2023-07-31 08:28:28.000000', '2024-03-16 07:01:42.829947', 7),
(18, 'Balo', 75.35, 'a98c6339-a19f-4dfe-a230-9a0ea1236192_083.jpg', 'Đựng nhiều đồ', '2023-07-31 08:28:28.000000', '2024-03-16 07:03:37.655825', 7),
(19, 'Dép cho nữ', 70.74, '699080d6-5b3c-4153-b3cb-140ee06f23f9_088.jpg', 'Dễ thương', '2023-07-31 08:28:28.000000', '2024-03-16 07:05:52.372448', 7),
(22, 'Ốp lưng điện thoại', 4.58, '79e8b5ff-8635-4416-8b4a-461f06ea0c03_096.jpg', 'Dễ thương', '2023-07-31 08:28:28.000000', '2024-03-16 07:08:10.585048', 7),
(23, 'Hộp nhựa', 11.74, '742f6cfb-8fdf-4fdf-9bee-68b31a70a470_099.jpg', 'Màu xanh', '2023-07-31 08:28:28.000000', '2024-03-16 07:10:15.845683', 4),
(24, 'Bình nước', 37.95, '52f4e6a0-d342-4bf4-b7d9-bc7bcf564014_102.jpg', 'Nhỏ xinh', '2023-07-31 08:28:28.000000', '2024-03-16 07:06:18.710931', 4),
(25, 'Bình nước', 31.3, '6b4e1d1f-1bc2-4f4a-a1a8-567ed652b9bc_104.jpg', 'Nhỏ gọn', '2023-07-31 08:28:28.000000', '2024-03-16 07:10:37.657221', 4),
(26, 'Bàn chải', 23.14, '8ecbe322-1e99-400c-a11f-faafdf136092_109.jpg', '3 màu', '2023-07-31 08:28:28.000000', '2024-03-16 07:11:31.202926', 2),
(27, 'Bàn chải điện', 18.06, '68b52bd4-c6b5-41bf-88f2-8471e90b1f14_113.jpg', 'Thiết kế đẹp', '2023-07-31 08:28:29.000000', '2024-03-16 07:11:04.469708', 2),
(28, 'Giá đỡ laptop', 8.87, '1575726c-6bec-4e9c-a063-fc3ed7589a32_118.jpg', 'Chắc chắn', '2023-07-31 08:28:29.000000', '2024-03-16 07:12:04.558261', 7),
(29, 'Ốp lưng điện thoại', 5.4, '9dd25e0b-b962-4f5c-b798-378998f2c8a8_122.jpg', 'Tiện dụng', '2023-07-31 08:28:29.000000', '2024-03-16 07:14:18.497152', 2),
(30, 'Kệ trang trí', 35.1, '9360eaa0-d99f-4b05-96b2-819bdb434555_0d83d40d-78e9-4c7d-80ee-3b5b68003104_002.jpg', 'Tiện dụng', '2023-07-31 08:28:29.000000', '2024-03-16 07:16:35.463455', 7),
(31, 'Mắt kính thời trang', 19.98, 'd40a5b86-a736-4bec-8b80-78dd33928ddd_126.jpg', 'Tiện dụng', '2023-07-31 08:28:29.000000', '2024-03-16 07:18:02.926076', 6),
(32, 'Heavy Duty Iron Clock', 6.67, '0172df63-6c6c-4565-bf5f-c9ac847e77e9_128.jpg', 'Et ducimus illum.', '2023-07-31 08:28:29.000000', '2023-07-31 08:28:29.000000', 2),
(33, 'Quạt', 7.89, '4bde7c98-7e06-4e20-9081-8aa2c345da02_131.jpg', 'Tiện dụng', '2023-07-31 08:28:29.000000', '2024-03-16 07:13:55.568410', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_images`
--

CREATE TABLE `product_images` (
  `id` bigint NOT NULL,
  `product_id` int DEFAULT NULL,
  `image_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_images`
--

INSERT INTO `product_images` (`id`, `product_id`, `image_url`) VALUES
(8, 1, 'f2fcda26-8304-48bc-9472-ead1c297770f_001.jpg'),
(9, 1, '0d83d40d-78e9-4c7d-80ee-3b5b68003104_002.jpg'),
(10, 1, 'cbef6f33-b6b7-45e0-ac44-f1a0b5fe1d4c_003.jpg'),
(13, 1, 'aabdceb6-c8de-43aa-9d3f-c2fe6d113ef2_006.jpg'),
(28, 7, '3036dff0-72cd-48d4-9efd-7221f41fc61d_022.jpg'),
(29, 7, 'cb6259db-e46d-48a5-8b7a-6c46a3b55446_023.jpg'),
(30, 7, 'bf379bbd-9e69-4cd6-bc03-d6db54b780b7_024.jpg'),
(33, 8, '36bf6016-4eff-4864-bf27-1de57e7d6589_027.jpg'),
(34, 8, '07175991-c161-48ce-8e66-a0ec093eea50_028.jpg'),
(38, 9, '326235eb-015b-4176-8977-fb96df932557_032.jpg'),
(39, 9, 'e950378b-a6ff-40c6-a6b5-b469d841b282_033.jpg'),
(40, 9, '4e8a2b84-96b2-49c4-bbcf-2805934a8076_034.jpg'),
(41, 9, '95251a5e-7eaa-45e9-9571-04b3247ccfab_035.jpg'),
(44, 10, '390bbdba-c579-4f5a-a455-59d7dbda9c72_038.jpg'),
(45, 10, '29925120-5403-49cc-b85c-922673a0d5ed_039.jpg'),
(46, 10, 'd1fef884-a4c1-471b-8fc9-400195436564_040.jpg'),
(51, 11, 'cad35cbc-fd56-4f4c-b2db-77d04f63a149_042.jpg'),
(53, 12, '09443def-0c28-40e1-b18d-f1cc8be05464_046.jpg'),
(54, 12, '9fc08b21-eb95-474e-b9c8-9de7bc6360e0_047.jpg'),
(55, 12, '813ad44a-a7bd-432a-bddc-b8f9a32b0039_048.jpg'),
(57, 13, '862e0948-8961-4c2a-b76a-f2322c846a4b_050.jpg'),
(58, 13, '8db6fb15-d24b-4c7f-b61d-c9d3452dd2fe_051.jpg'),
(59, 13, 'fbf2c23a-a2c3-48fb-ac69-c8ac4913bfb2_052.jpg'),
(60, 13, '25cefd9a-e306-422e-9fbf-8c98141dc81a_053.jpg'),
(62, 14, 'd152fdfd-6c49-440c-a249-8fdc08646333_055.jpg'),
(63, 14, 'e29ac0a0-8c1f-493f-a66b-6f170de48f94_056.jpg'),
(67, 15, '904a9f1c-207c-4df6-bb49-c954656a8c06_059.jpg'),
(68, 15, '5d8fd37f-06a5-4906-9252-61ba564723c1_060.jpg'),
(69, 15, '95a13b88-919f-49af-b868-e2bfed0bb98e_061.jpg'),
(70, 15, '0ca19870-50b7-4719-82ae-b6b8272d45cd_062.jpg'),
(71, 15, '26365791-3978-4df8-b6fd-f2f516b1e7e3_063.jpg'),
(72, 16, 'c86bb69d-e5a2-4c93-8f0a-87504a8966ea_064.jpg'),
(73, 16, '93b29ad3-b5ab-40c1-ace8-ec069cba2db1_065.jpg'),
(74, 16, 'd832472c-856a-4243-bc98-531af0b1a1a5_066.jpg'),
(75, 16, '05b3bc62-c8f1-41c3-abd0-3515a1f19ed3_067.jpg'),
(76, 16, 'ee6f30dc-68de-4c8a-bfd7-62df2ed20d22_068.jpg'),
(77, 17, 'dafed832-a59c-41ad-bc60-011fadaf8020_069.jpg'),
(78, 17, '65cf789d-ed86-4fd7-8030-2a79af81a88b_070.jpg'),
(79, 17, 'a2ac276a-5384-4646-ad52-ad00b731cc9b_080.jpg'),
(82, 18, 'a98c6339-a19f-4dfe-a230-9a0ea1236192_083.jpg'),
(83, 18, 'a2dc214e-e02d-4b63-bcfd-03926d983833_084.jpg'),
(87, 19, '699080d6-5b3c-4153-b3cb-140ee06f23f9_088.jpg'),
(88, 19, 'e21a7434-b10d-412a-996b-be2a0703f8b0_089.jpg'),
(89, 19, '121db8eb-34dd-4f28-879e-4206ca84410a_090.jpg'),
(104, 23, '742f6cfb-8fdf-4fdf-9bee-68b31a70a470_099.jpg'),
(105, 23, '912a1687-201e-4e80-98ab-f7ebddf157f9_100.jpg'),
(106, 23, 'c1bb4b13-d3e5-4543-9517-debacd1ec1c9_101.jpg'),
(107, 24, '52f4e6a0-d342-4bf4-b7d9-bc7bcf564014_102.jpg'),
(108, 24, '25de850a-1d1d-44b0-bf47-d755b4cc70e9_103.jpg'),
(109, 24, 'af47fc82-8b9f-487c-99cf-bbc453f8189e_104.jpg'),
(110, 25, '6b4e1d1f-1bc2-4f4a-a1a8-567ed652b9bc_104.jpg'),
(111, 25, '71bb68f6-6c70-416c-b506-208a96e0a0ef_105.jpg'),
(112, 25, '7ab15e39-6589-4fe2-9dbe-5ef5ef1775b6_106.jpg'),
(113, 25, '0954d8c6-0bde-406d-8448-8e7570ddb092_108.jpg'),
(114, 26, '8ecbe322-1e99-400c-a11f-faafdf136092_109.jpg'),
(115, 26, '0db7e33b-0133-48b1-aa3d-210235f78016_110.jpg'),
(116, 26, 'fa29af18-1765-4370-955f-4ea0c14ede78_111.jpg'),
(117, 26, '7569bde4-4484-44cb-9338-a14ea927011d_112.jpg'),
(118, 27, '68b52bd4-c6b5-41bf-88f2-8471e90b1f14_113.jpg'),
(119, 27, 'aaa89b16-33c0-4e60-bd07-e89970be7a0a_114.jpg'),
(120, 27, '3e810ada-210a-4449-a25c-df0ffcee8bfd_115.jpg'),
(121, 27, '5d84cbea-90da-4753-b273-10db236b2fad_116.jpg'),
(122, 27, '7435fb50-5b74-4d77-b397-b211ab62c26c_117.jpg'),
(123, 28, '1575726c-6bec-4e9c-a063-fc3ed7589a32_118.jpg'),
(124, 28, 'b0c66298-0fa2-4431-99b9-77583cefe70d_119.jpg'),
(127, 29, '9dd25e0b-b962-4f5c-b798-378998f2c8a8_122.jpg'),
(128, 29, '066c0a93-9553-4aa6-9467-867a14dc368d_123.jpg'),
(129, 29, '1d5b57ce-3f73-48f2-ac92-f96d35dba744_124.jpg'),
(133, 32, '0172df63-6c6c-4565-bf5f-c9ac847e77e9_128.jpg'),
(134, 32, '28bb7329-fb63-4e5f-b21c-a66d4a61366d_129.jpg'),
(135, 32, '12babe42-6fd2-45e6-81e9-14ca68a5774e_130.jpg'),
(136, 33, '4bde7c98-7e06-4e20-9081-8aa2c345da02_131.jpg'),
(137, 33, 'e81584ec-0f6c-4f6c-babd-d5275ca64191_132.jpg'),
(138, 33, 'e7c5de4c-b197-408c-a8ee-6209e1392299_133.jpg'),
(152, 3, '90445e17-bc13-4bab-9afb-f5f8a32a5674_0f6cc84b-a25d-471a-8b33-7281987968a8_099.jpg'),
(153, 3, '698efb30-11e6-4b0d-a843-867e320db68b_01bac487-2758-4a55-be45-ae020dbbf65f_098.jpg'),
(154, 3, 'fdbde163-9950-4c4b-a303-e696096c3be8_4253fc5d-899f-4e6d-b45c-c239acbdc5e8_097.jpg'),
(156, 3, '4fc2e6a4-d957-47dc-95d0-ca346983cbca_c1bb4b13-d3e5-4543-9517-debacd1ec1c9_101.jpg'),
(157, 3, 'a13e6fe4-3c4e-4b20-90a5-956063dd75b6_01bac487-2758-4a55-be45-ae020dbbf65f_098.jpg'),
(158, 2, '099becb1-88c5-44fc-bb46-f1e2c36cc7e8_5d84cbea-90da-4753-b273-10db236b2fad_116.jpg'),
(159, 2, 'c6a5afbe-de40-408f-a115-0f2031f19588_8ecbe322-1e99-400c-a11f-faafdf136092_109.jpg'),
(160, 2, 'b948d29e-6cd7-4993-9317-19e319917261_fa29af18-1765-4370-955f-4ea0c14ede78_111.jpg'),
(161, 2, 'd1da552b-03cc-4707-a304-3702e5aac975_0db7e33b-0133-48b1-aa3d-210235f78016_110.jpg'),
(164, 2, 'e69fb4ad-1b32-4e20-950f-ecac55463f2c_7569bde4-4484-44cb-9338-a14ea927011d_112.jpg'),
(165, 5, '20e05380-e562-4885-b9a1-a24afd1f8f89_2ad86d1c-2b0a-4817-8fbc-0785f7d74870_091.jpg'),
(166, 5, '0facca62-dd2e-4c18-902e-510104237fea_9e591a61-f1de-4ccc-a37e-d60b08ad9f7b_094.jpg'),
(167, 5, 'e530d394-9373-4b11-8689-a248cbac7d4b_79e8b5ff-8635-4416-8b4a-461f06ea0c03_096.jpg'),
(168, 5, '5f85a687-4f85-45c4-8d4f-7983c72fa5a0_a8dbe441-3db0-44d5-9b87-baf5b932683e_093.jpg'),
(169, 5, '92d82350-17f2-4720-8ef7-0e5eaf66bc9e_a544ae1e-8cac-4940-9b7e-c7ddd8b40efc_092.jpg'),
(170, 6, '54d7fb45-5b04-47e1-a112-70af6b098db7_4bde7c98-7e06-4e20-9081-8aa2c345da02_131.jpg'),
(172, 6, 'cd37613d-32a2-47e7-98e9-a32348efdc8c_28bb7329-fb63-4e5f-b21c-a66d4a61366d_129.jpg'),
(173, 6, '4b97ab51-d074-4eed-825d-dad96dba4bb4_395efb19-034f-47d6-aa9f-8a773b20971e_127.jpg'),
(175, 6, '016a96a0-f145-495a-95dc-97ab3a7f6985_12babe42-6fd2-45e6-81e9-14ca68a5774e_130.jpg'),
(176, 6, '5574cd09-23bc-4524-a798-061bb2de21ca_d40a5b86-a736-4bec-8b80-78dd33928ddd_126.jpg'),
(177, 8, 'add37ba9-2d8b-4159-905b-5867dae1a69b_36bf6016-4eff-4864-bf27-1de57e7d6589_027.jpg'),
(178, 8, '7362cd3c-2b92-4759-bd9c-eb0d8064d02e_07175991-c161-48ce-8e66-a0ec093eea50_028.jpg'),
(179, 10, '2e577a83-d785-4d80-8da3-d11b98c4d246_0e32e381-4cb5-4771-a008-1354a612e763_041.jpg'),
(181, 11, 'e7919b75-9c97-4e47-9354-c443aaea6b3c_cf00f484-f8a4-4b9a-8a9f-ef74bfd7ebcf_045.jpg'),
(183, 13, '4f4f3afd-c223-472d-8bb6-c63f44b18373_fbf2c23a-a2c3-48fb-ac69-c8ac4913bfb2_052.jpg'),
(184, 14, '958a6115-60f8-409e-be81-13a678c7e195_e29ac0a0-8c1f-493f-a66b-6f170de48f94_056.jpg'),
(185, 14, 'cfbac4c4-2057-4198-ac76-fd0f6def162b_d152fdfd-6c49-440c-a249-8fdc08646333_055.jpg'),
(186, 18, 'f95b0c1e-6541-4f22-a3ef-701173a6f8ea_a98c6339-a19f-4dfe-a230-9a0ea1236192_083.jpg'),
(187, 19, 'f3e074e5-3eaa-401d-a110-c82392a3e6f4_699080d6-5b3c-4153-b3cb-140ee06f23f9_088.jpg'),
(188, 22, '57a09410-9f5d-47bf-b7bb-b3d248c91cf3_1d5b57ce-3f73-48f2-ac92-f96d35dba744_124.jpg'),
(189, 22, '0cbdc8a1-a9e5-4b9e-8d3a-6f9a0567b949_9dd25e0b-b962-4f5c-b798-378998f2c8a8_122.jpg'),
(190, 22, '588b5755-fd61-400a-9492-cad0eb046d9e_066c0a93-9553-4aa6-9467-867a14dc368d_123.jpg'),
(191, 22, '613521b5-7454-49b8-9c3e-9ca2822e62ad_c52e5599-e351-46ea-b6a4-0729a3118972_120.jpg'),
(192, 30, '9360eaa0-d99f-4b05-96b2-819bdb434555_0d83d40d-78e9-4c7d-80ee-3b5b68003104_002.jpg'),
(195, 30, 'bb4512d9-69ff-44f5-a281-746d60391d2d_0d83d40d-78e9-4c7d-80ee-3b5b68003104_002.jpg'),
(196, 30, 'd02ab836-05be-4650-a220-ca1b08d8ae15_9360eaa0-d99f-4b05-96b2-819bdb434555_0d83d40d-78e9-4c7d-80ee-3b5b68003104_002.jpg'),
(197, 30, '75cdd039-d8fb-41c7-b83d-c668c73e88e2_cbef6f33-b6b7-45e0-ac44-f1a0b5fe1d4c_003.jpg'),
(198, 30, '8ee31e74-5d97-45e8-944c-67682ada7f22_d7c317b7-4e8d-47b6-ad89-53f7a9c713c6_004.jpg'),
(201, 31, 'bc7d4927-ef93-4e0e-a35b-2dd5ad7a4dda_4e8a2b84-96b2-49c4-bbcf-2805934a8076_034.jpg'),
(202, 31, 'c16b63f3-9507-4f2f-99f4-39e498dfc7ff_95251a5e-7eaa-45e9-9571-04b3247ccfab_035.jpg'),
(203, 31, '9797fe21-ad6b-4852-bfea-043c00c98c4c_326235eb-015b-4176-8977-fb96df932557_032.jpg'),
(204, 31, 'fb918a5a-1c10-4672-a866-aa3dcd61472a_e950378b-a6ff-40c6-a6b5-b469d841b282_033.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'user'),
(2, 'admin');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `social_accounts`
--

CREATE TABLE `social_accounts` (
  `id` bigint NOT NULL,
  `provider` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Tên nhà social network',
  `provider_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Email tài khoản',
  `name` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tokens`
--

CREATE TABLE `tokens` (
  `id` bigint NOT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `expiration_date` datetime(6) DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expired` tinyint(1) NOT NULL,
  `user_id` int DEFAULT NULL,
  `is_mobile` tinyint(1) DEFAULT '0',
  `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `refresh_expiration_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tokens`
--

INSERT INTO `tokens` (`id`, `token`, `token_type`, `expiration_date`, `revoked`, `expired`, `user_id`, `is_mobile`, `refresh_token`, `refresh_expiration_date`) VALUES
(39, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4Mzg4MzIzMTMiLCJ1c2VySWQiOjksInN1YiI6IjA4Mzg4MzIzMTMiLCJleHAiOjE3MTI2NTA0MzJ9.OLysJsdOYjmRkPgFdpvk6FNrOro0x4sHRDGEdwYtonc', 'Bearer', '2024-04-09 08:13:53.000000', 0, 0, 9, 0, '032b8947-3154-4d32-b3a7-923fccb9d36c', '2024-05-09 08:13:53.000000'),
(47, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjEyMzQ1Njc4OSIsInVzZXJJZCI6Nywic3ViIjoiMTIzNDU2Nzg5IiwiZXhwIjoxNzEyOTIwODU4fQ.duaPeSoJY23r5kBu1KJnrW6h-JOl3avN5OJzX1yecTI', 'Bearer', '2024-04-12 11:20:58.000000', 0, 0, 7, 0, 'dde07ae5-656a-46a5-b8d7-c0297802cb6f', '2024-05-12 11:20:58.000000'),
(81, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjMzNDQ1NTY2IiwidXNlcklkIjo1LCJzdWIiOiIzMzQ0NTU2NiIsImV4cCI6MTcxMzA4NjQ5Mn0.EZEi03qqx71ZTZUv3X1as8HgCqQevxWCt0USh5tSBbY', 'Bearer', '2024-04-14 09:21:32.689381', 0, 0, 5, 0, 'a46afcbb-6f36-452e-99f4-d379be169bc0', '2024-05-14 09:21:32.689381'),
(85, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4Mzg4MzIzMTIiLCJ1c2VySWQiOjExLCJzdWIiOiIwODM4ODMyMzEyIiwiZXhwIjoxNzEzMjU0MTYyfQ.PnvK5cEs3shdoqHgjMvusrhq1XdPmlfjIYciDQEgo8w', 'Bearer', '2024-04-16 07:56:02.300512', 0, 0, 11, 0, '0a08d061-54aa-4e54-8439-6f0df2531f50', '2024-05-16 07:56:02.300512'),
(92, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjMzNDQ1NTY2IiwidXNlcklkIjo1LCJzdWIiOiIzMzQ0NTU2NiIsImV4cCI6MTcxMzI1NDkwN30.l5ZAwTnm7kAx3kjOVBHRWOfoj74SKayZqCwRRqoV6HE', 'Bearer', '2024-04-16 08:08:27.569910', 0, 0, 5, 0, '635bb6ac-aa97-4595-a530-224821a939bc', '2024-05-16 08:08:27.569910'),
(94, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjExMjIzMzQ0IiwidXNlcklkIjozLCJzdWIiOiIxMTIyMzM0NCIsImV4cCI6MTcxMzI1NjMyN30.mYl9aUkTQlUwTygk-PmjRqR7CMJiShp6sJjEVwrLMWk', 'Bearer', '2024-04-16 08:32:07.081932', 0, 0, 3, 0, '11011978-8d0e-4254-a359-40e2bb2a8fa2', '2024-05-16 08:32:07.081932'),
(95, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjMzNDQ1NTY2IiwidXNlcklkIjo1LCJzdWIiOiIzMzQ0NTU2NiIsImV4cCI6MTcxMzI1NjMzNX0.yiTm2c4VH1WhTKG3b7jn6evTtYKgfcfjsaLrjesiLeM', 'Bearer', '2024-04-16 08:32:15.332504', 0, 0, 5, 0, '8090fa5c-76f6-4333-aae7-0054c431531e', '2024-05-16 08:32:15.332504'),
(96, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjExMjIzMzQ0IiwidXNlcklkIjozLCJzdWIiOiIxMTIyMzM0NCIsImV4cCI6MTcxMzI1NzE4Mn0.mlXQmRqzv_Fz9U0D-dE9PWhbdEZEPiLbQ5nNiG6qvjc', 'Bearer', '2024-04-16 08:46:22.271164', 0, 0, 3, 0, 'f73e10e3-ee44-4fba-bbd2-9b4ddc197387', '2024-05-16 08:46:22.271164'),
(97, 'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjExMjIzMzQ0IiwidXNlcklkIjozLCJzdWIiOiIxMTIyMzM0NCIsImV4cCI6MTcxMzI1ODA0Mn0.d2eSMSrpzIl4U0uyU39Y0HAmz5Zya3641NnVoWYORAQ', 'Bearer', '2024-04-16 09:00:42.213479', 0, 0, 3, 0, '556dc928-a094-4f82-a844-4d05a3f63b09', '2024-05-16 09:00:42.213479');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `fullname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `phone_number` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `password` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `date_of_birth` datetime(6) DEFAULT NULL,
  `facebook_account_id` int DEFAULT '0',
  `google_account_id` int DEFAULT '0',
  `role_id` int DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `fullname`, `phone_number`, `address`, `password`, `created_at`, `updated_at`, `is_active`, `date_of_birth`, `facebook_account_id`, `google_account_id`, `role_id`) VALUES
(2, 'Nguyễn Văn A', '012456878', 'Nhà a ngõ b', '$2a$10$WdSf5UuyxQMAHcO502qXredzcc8OZQo4XQZNp3UxeT6/bKbuJx/6y', '2023-08-03 05:36:11.000000', '2023-08-03 05:36:11.000000', 1, '1999-10-25 00:00:00.000000', 0, 0, 2),
(3, 'Tài khoản admin 1', '11223344', 'Đây là admin nhé', '$2a$10$JFQT3HeFUKDl7c/l.iNFAeybFr7Wi3krwgVVR7ieif.2De5p9LGAG', '2023-08-06 00:34:35.000000', '2023-08-06 00:34:35.000000', 1, '1993-10-25 00:00:00.000000', 0, 0, 2),
(5, 'Nguyễn Văn test 11', '33445566', 'Nhà a ngõ b 11', '$2a$10$e95NOMrOFFpfhWibOpAw/uJrqfsBuJ9O3xNgZAP6vnHTh5QHvgliu', '2023-08-08 03:02:48.000000', '2023-11-16 01:05:34.000000', 1, '2000-10-25 00:00:00.000000', 2, 3, 1),
(7, 'Nguyen Van Y', '123456789', 'Đây là user', '$2a$10$oZwu2RA2iiNVIaQZgdi7bueKc5YNWr39yu.gXdsavBzo5AOb1kP5e', '2023-11-16 00:52:29.000000', '2023-11-16 00:52:29.000000', 1, '2000-10-25 00:00:00.000000', 0, 0, 1),
(9, 'Do DO', '0838832313', 'Go Vap HCM', '$2a$10$lXAGK/hq9Vj6qrSxhfLZteTD0NNIv9EuCza2GCByqqwyXqxvzPeia', '2024-03-10 08:01:48.000000', '2024-03-10 08:12:48.000000', 1, '2000-03-12 00:00:00.000000', 0, 0, 1),
(11, 'Nguyen Thuy Vy', '0838832312', 'Go Vap HCM', '$2a$10$nqCxJWOf6AbaLcJj8eVQs.Y9kFPHeNtjcbfCg9zCWatnhqCArr73q', '2024-03-13 11:26:45.000000', '2024-03-17 07:55:36.968760', 1, '2003-03-12 00:00:00.000000', 0, 0, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Chỉ mục cho bảng `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `coupon_conditions`
--
ALTER TABLE `coupon_conditions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `coupon_id` (`coupon_id`);

--
-- Chỉ mục cho bảng `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `fk_orders_coupon` (`coupon_id`);

--
-- Chỉ mục cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `fk_order_details_coupon` (`coupon_id`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Chỉ mục cho bảng `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_product_images_product_id` (`product_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `social_accounts`
--
ALTER TABLE `social_accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `token` (`token`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `comments`
--
ALTER TABLE `comments`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `coupon_conditions`
--
ALTER TABLE `coupon_conditions`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;

--
-- AUTO_INCREMENT cho bảng `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5781;

--
-- AUTO_INCREMENT cho bảng `product_images`
--
ALTER TABLE `product_images`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=205;

--
-- AUTO_INCREMENT cho bảng `social_accounts`
--
ALTER TABLE `social_accounts`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tokens`
--
ALTER TABLE `tokens`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `coupon_conditions`
--
ALTER TABLE `coupon_conditions`
  ADD CONSTRAINT `coupon_conditions_ibfk_1` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_orders_coupon` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`),
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `fk_order_details_coupon` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`),
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Các ràng buộc cho bảng `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `fk_product_images_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `social_accounts`
--
ALTER TABLE `social_accounts`
  ADD CONSTRAINT `social_accounts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `tokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
