--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.0

-- Started on 2025-02-12 18:58:42

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3502 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 231 (class 1259 OID 16478)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16477)
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO postgres;

--
-- TOC entry 3503 (class 0 OID 0)
-- Dependencies: 230
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- TOC entry 223 (class 1259 OID 16423)
-- Name: coupons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.coupons (
    id integer NOT NULL,
    code character varying(50) NOT NULL,
    discount_percentage numeric(5,2),
    valid_until timestamp without time zone,
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT coupons_discount_percentage_check CHECK (((discount_percentage >= (0)::numeric) AND (discount_percentage <= (100)::numeric)))
);


ALTER TABLE public.coupons OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16422)
-- Name: coupons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.coupons_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.coupons_id_seq OWNER TO postgres;

--
-- TOC entry 3504 (class 0 OID 0)
-- Dependencies: 222
-- Name: coupons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.coupons_id_seq OWNED BY public.coupons.id;


--
-- TOC entry 229 (class 1259 OID 16461)
-- Name: order_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_items (
    id bigint NOT NULL,
    order_id bigint,
    product_id bigint,
    quantity integer NOT NULL,
    review_id bigint
);


ALTER TABLE public.order_items OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16460)
-- Name: order_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_items_id_seq OWNER TO postgres;

--
-- TOC entry 3505 (class 0 OID 0)
-- Dependencies: 228
-- Name: order_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_items_id_seq OWNED BY public.order_items.id;


--
-- TOC entry 225 (class 1259 OID 16435)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    user_id bigint,
    total_amount numeric(38,2) NOT NULL,
    status character varying(50) DEFAULT 'Pending'::character varying,
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    type character varying(20) NOT NULL,
    coupon_id integer,
    shipping_address text,
    full_name character varying(255),
    phone_number character varying(15)
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16434)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNER TO postgres;

--
-- TOC entry 3506 (class 0 OID 0)
-- Dependencies: 224
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 232 (class 1259 OID 16490)
-- Name: product_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_categories (
    product_id bigint NOT NULL,
    category_id integer NOT NULL
);


ALTER TABLE public.product_categories OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16450)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    sale_price numeric(10,2) NOT NULL,
    image_url character varying[],
    stock_quantity integer NOT NULL,
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    purchase_price numeric(10,2) NOT NULL
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16449)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 3507 (class 0 OID 0)
-- Dependencies: 226
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 236 (class 1259 OID 40961)
-- Name: report; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.report (
    id bigint NOT NULL,
    date date NOT NULL,
    type character varying(50),
    amount numeric(38,2) DEFAULT 0,
    status character varying(50),
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.report OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 40960)
-- Name: report_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.report_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.report_id_seq OWNER TO postgres;

--
-- TOC entry 3508 (class 0 OID 0)
-- Dependencies: 235
-- Name: report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.report_id_seq OWNED BY public.report.id;


--
-- TOC entry 234 (class 1259 OID 16506)
-- Name: reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews (
    id bigint NOT NULL,
    rating integer,
    comment text,
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT reviews_rating_check CHECK (((rating >= 1) AND (rating <= 5)))
);


ALTER TABLE public.reviews OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16505)
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reviews_id_seq OWNER TO postgres;

--
-- TOC entry 3509 (class 0 OID 0)
-- Dependencies: 233
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reviews_id_seq OWNED BY public.reviews.id;


--
-- TOC entry 218 (class 1259 OID 16386)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16385)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO postgres;

--
-- TOC entry 3510 (class 0 OID 0)
-- Dependencies: 217
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 221 (class 1259 OID 16407)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16393)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(120) NOT NULL,
    user_name character varying(20) NOT NULL,
    full_name character varying(255),
    phone_number character varying(15),
    address text,
    create_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    status character varying(20) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16392)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3511 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3273 (class 2604 OID 16481)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 3262 (class 2604 OID 16426)
-- Name: coupons id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coupons ALTER COLUMN id SET DEFAULT nextval('public.coupons_id_seq'::regclass);


--
-- TOC entry 3272 (class 2604 OID 16464)
-- Name: order_items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq'::regclass);


--
-- TOC entry 3265 (class 2604 OID 16438)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- TOC entry 3269 (class 2604 OID 16453)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 3279 (class 2604 OID 40964)
-- Name: report id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report ALTER COLUMN id SET DEFAULT nextval('public.report_id_seq'::regclass);


--
-- TOC entry 3276 (class 2604 OID 16509)
-- Name: reviews id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews ALTER COLUMN id SET DEFAULT nextval('public.reviews_id_seq'::regclass);


--
-- TOC entry 3258 (class 2604 OID 16389)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 3259 (class 2604 OID 16396)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3491 (class 0 OID 16478)
-- Dependencies: 231
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categories VALUES (1, 'Thiết bị điện tử', NULL, '2025-01-25 15:38:22.750329', '2025-01-25 15:38:22.750329');
INSERT INTO public.categories VALUES (2, 'Thời trang nam', NULL, '2025-01-25 15:38:22.75703', '2025-01-25 15:38:22.75703');
INSERT INTO public.categories VALUES (3, 'Điện thoại & phụ kiện', NULL, '2025-01-25 15:38:22.760784', '2025-01-25 15:38:22.760784');
INSERT INTO public.categories VALUES (4, 'Máy tính & laptop', NULL, '2025-01-25 15:38:22.765233', '2025-01-25 15:38:22.765233');
INSERT INTO public.categories VALUES (5, 'Máy ảnh & máy quay phim', NULL, '2025-01-25 15:38:22.768299', '2025-01-25 15:38:22.768299');
INSERT INTO public.categories VALUES (6, 'Đồng hồ', NULL, '2025-01-25 15:38:22.771791', '2025-01-25 15:38:22.771791');
INSERT INTO public.categories VALUES (7, 'Giày dép nam', NULL, '2025-01-25 15:38:22.774831', '2025-01-25 15:38:22.774831');
INSERT INTO public.categories VALUES (8, 'Thiết bị điện gia dụng', NULL, '2025-01-25 15:38:22.778332', '2025-01-25 15:38:22.778332');
INSERT INTO public.categories VALUES (9, 'Thể thao & du lịch', NULL, '2025-01-25 15:38:22.78158', '2025-01-25 15:38:22.78158');
INSERT INTO public.categories VALUES (10, 'Thời trang nữ', NULL, '2025-01-25 15:38:22.784742', '2025-01-25 15:38:22.784742');
INSERT INTO public.categories VALUES (11, 'Mẹ & bé', NULL, '2025-01-25 15:38:22.78757', '2025-01-25 15:38:22.78757');
INSERT INTO public.categories VALUES (12, 'Sức khoẻ', NULL, '2025-01-25 15:38:22.791174', '2025-01-25 15:38:22.791174');
INSERT INTO public.categories VALUES (15, 'Không xác định', NULL, '2025-01-25 15:38:22.750329', '2025-01-25 15:38:22.750329');


--
-- TOC entry 3483 (class 0 OID 16423)
-- Dependencies: 223
-- Data for Name: coupons; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.coupons VALUES (1, 'COUPON1', 10.00, '2025-12-31 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (2, 'COUPON2', 15.50, '2025-11-30 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (3, 'COUPON3', 5.00, '2025-10-15 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (4, 'COUPON4', 20.00, '2025-09-01 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (5, 'COUPON5', 50.00, '2025-08-20 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (6, 'COUPON6', 30.00, '2025-07-10 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (7, 'COUPON7', 25.00, '2025-06-30 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (8, 'COUPON8', 40.00, '2025-05-15 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (9, 'COUPON9', 35.00, '2025-04-01 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');
INSERT INTO public.coupons VALUES (10, 'COUPON10', 60.00, '2025-03-20 23:59:59', '2025-01-27 13:02:51.748748', '2025-01-27 13:02:51.748748');


--
-- TOC entry 3489 (class 0 OID 16461)
-- Dependencies: 229
-- Data for Name: order_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.order_items VALUES (9, 5, 1, 12, 2);
INSERT INTO public.order_items VALUES (10, 5, 2, 10, 3);
INSERT INTO public.order_items VALUES (11, 6, 2, 10, 4);
INSERT INTO public.order_items VALUES (13, 7, 1, 12, NULL);
INSERT INTO public.order_items VALUES (14, 7, 2, 10, NULL);


--
-- TOC entry 3485 (class 0 OID 16435)
-- Dependencies: 225
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.orders VALUES (5, 2, 198000000.00, 'PROCESSING', '2025-01-25 23:37:30.447', '2025-01-25 23:37:30.447', 'SALES', 1, 'Đồng Bụt, Quốc Oai', 'Trần Phú Huy', '0965199203');
INSERT INTO public.orders VALUES (6, 2, 0.00, 'PENDING', NULL, NULL, 'SALES', NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES (7, 3, 100000000.00, 'PROCESSING', NULL, NULL, 'PURCHASE', NULL, 'Đồng Bụt, Quốc Oai', 'Trần Phú Huy', '0965199203');


--
-- TOC entry 3492 (class 0 OID 16490)
-- Dependencies: 232
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product_categories VALUES (1, 1);
INSERT INTO public.product_categories VALUES (1, 3);
INSERT INTO public.product_categories VALUES (32, 1);
INSERT INTO public.product_categories VALUES (32, 3);
INSERT INTO public.product_categories VALUES (32, 2);


--
-- TOC entry 3487 (class 0 OID 16450)
-- Dependencies: 227
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.products VALUES (3, 'Smartwatch DEF', 'Đồng hồ thông minh DEF với khả năng theo dõi sức khỏe', 3000000.00, '{6}', 20, '2025-01-25 15:44:44.261178', '2025-01-25 15:44:44.261178', 1500000.00);
INSERT INTO public.products VALUES (1, 'Smartphone XYZ', 'Điện thoại thông minh XYZ với nhiều tính năng hiện đại', 10000000.00, '{http://localhost:8080/v1/image/view/sg-11134301-7rdvs-m01bhb1twkvn33@resize_w450_nl.webp,http://localhost:8080/v1/image/view/sg-11134301-7rdxf-m01bu9wypdi590.webp,http://localhost:8080/v1/image/view/sg-11134301-7rdxw-m01bhrwnbdhc39.webp}', 50, '2025-01-25 15:44:44.261178', '2025-01-25 15:44:44.261178', 5000000.00);
INSERT INTO public.products VALUES (4, 'Áo sơ mi nam', 'Áo sơ mi nam chất liệu cotton cao cấp', 500000.00, '{7}', 100, '2025-01-25 15:44:49.544045', '2025-01-25 15:44:49.544045', 250000.00);
INSERT INTO public.products VALUES (5, 'Quần jean nam', 'Quần jean nam phong cách trẻ trung', 700000.00, '{8}', 80, '2025-01-25 15:44:49.544045', '2025-01-25 15:44:49.544045', 350000.00);
INSERT INTO public.products VALUES (6, 'Áo khoác nam', 'Áo khoác nam giữ ấm mùa đông', 1200000.00, '{9,10}', 60, '2025-01-25 15:44:49.544045', '2025-01-25 15:44:49.544045', 600000.00);
INSERT INTO public.products VALUES (7, 'Ốp lưng điện thoại', 'Ốp lưng bảo vệ điện thoại chống va đập', 150000.00, '{11}', 200, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 75000.00);
INSERT INTO public.products VALUES (8, 'Tai nghe không dây', 'Tai nghe Bluetooth chất lượng âm thanh cao', 1200000.00, '{12}', 100, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 600000.00);
INSERT INTO public.products VALUES (9, 'Cáp sạc nhanh', 'Cáp sạc nhanh tương thích với nhiều thiết bị', 300000.00, '{13}', 150, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 150000.00);
INSERT INTO public.products VALUES (10, 'Laptop Gaming', 'Laptop chơi game hiệu năng cao', 25000000.00, '{14,15}', 10, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 12500000.00);
INSERT INTO public.products VALUES (11, 'Bàn phím cơ', 'Bàn phím cơ RGB dành cho game thủ', 2000000.00, '{16}', 50, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 1000000.00);
INSERT INTO public.products VALUES (12, 'Chuột không dây', 'Chuột không dây tiện lợi', 800000.00, '{17}', 70, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 400000.00);
INSERT INTO public.products VALUES (13, 'Máy ảnh DSLR', 'Máy ảnh DSLR chuyên nghiệp', 30000000.00, '{18}', 15, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 15000000.00);
INSERT INTO public.products VALUES (14, 'Ống kính máy ảnh', 'Ống kính chất lượng cao', 10000000.00, '{19}', 20, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 5000000.00);
INSERT INTO public.products VALUES (15, 'Gimbal', 'Gimbal chống rung cho máy quay', 5000000.00, '{20}', 25, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 2500000.00);
INSERT INTO public.products VALUES (16, 'Đồng hồ cơ', 'Đồng hồ cơ tự động', 15000000.00, '{21}', 30, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 7500000.00);
INSERT INTO public.products VALUES (17, 'Đồng hồ thể thao', 'Đồng hồ thể thao chống nước', 5000000.00, '{22}', 50, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 2500000.00);
INSERT INTO public.products VALUES (18, 'Đồng hồ thông minh', 'Đồng hồ thông minh với nhiều tính năng', 7000000.00, '{23}', 40, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 3500000.00);
INSERT INTO public.products VALUES (19, 'Giày thể thao', 'Giày thể thao nam năng động', 1500000.00, '{24}', 100, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 750000.00);
INSERT INTO public.products VALUES (20, 'Giày tây', 'Giày tây nam lịch lãm', 2000000.00, '{25}', 80, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 1000000.00);
INSERT INTO public.products VALUES (21, 'Dép quai hậu', 'Dép quai hậu nam thoải mái', 500000.00, '{26}', 120, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 250000.00);
INSERT INTO public.products VALUES (22, 'Máy hút bụi', 'Máy hút bụi công suất lớn', 3000000.00, '{27}', 40, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 1500000.00);
INSERT INTO public.products VALUES (23, 'Nồi chiên không dầu', 'Nồi chiên không dầu đa năng', 2500000.00, '{28}', 60, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 1250000.00);
INSERT INTO public.products VALUES (24, 'Quạt điện', 'Quạt điện điều chỉnh 3 tốc độ', 800000.00, '{29}', 100, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 400000.00);
INSERT INTO public.products VALUES (25, 'Ba lô du lịch', 'Ba lô du lịch chống thấm nước', 1200000.00, '{30}', 70, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 600000.00);
INSERT INTO public.products VALUES (26, 'Bóng đá', 'Bóng đá tiêu chuẩn FIFA', 500000.00, '{31}', 150, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 250000.00);
INSERT INTO public.products VALUES (27, 'Vợt cầu lông', 'Vợt cầu lông siêu nhẹ', 800000.00, '{32}', 100, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 400000.00);
INSERT INTO public.products VALUES (28, 'Áo dài nữ', 'Áo dài truyền thống Việt Nam', 2000000.00, '{33}', 50, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 1000000.00);
INSERT INTO public.products VALUES (29, 'Đầm dạ hội', 'Đầm dạ hội sang trọng', 3000000.00, '{34}', 30, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 1500000.00);
INSERT INTO public.products VALUES (30, 'Túi xách nữ', 'Túi xách thời trang', 1500000.00, '{35}', 80, '2025-01-25 15:44:55.966111', '2025-01-25 15:44:55.966111', 750000.00);
INSERT INTO public.products VALUES (32, 'Iphone 16', 'Điện thoại thông minh của Apple', 33000000.00, '{http://localhost:8080/v1/image/view/cbb784b1-b2f1-41ed-9a34-dee11fd622d3_sg-11134301-7rdvs-m01bhb1twkvn33@resize_w450_nl.webp,http://localhost:8080/v1/image/view/02d8b5fb-903e-4cab-b31c-84382cf6532c_sg-11134301-7rdxf-m01bu9wypdi590.webp,http://localhost:8080/v1/image/view/0e0b2d78-3b6f-47e4-b3a3-55b258662e6d_sg-11134301-7rdxw-m01bhrwnbdhc39.webp}', 55, '2025-01-27 00:44:44', '2025-01-27 00:44:44', 16500000.00);
INSERT INTO public.products VALUES (36, 'Iphone 15', 'Điện thoại thông minh của Apple', 33000000.00, '{http://localhost:8080/v1/image/view/cbb784b1-b2f1-41ed-9a34-dee11fd622d3_sg-11134301-7rdvs-m01bhb1twkvn33@resize_w450_nl.webp,http://localhost:8080/v1/image/view/02d8b5fb-903e-4cab-b31c-84382cf6532c_sg-11134301-7rdxf-m01bu9wypdi590.webp,http://localhost:8080/v1/image/view/0e0b2d78-3b6f-47e4-b3a3-55b258662e6d_sg-11134301-7rdxw-m01bhrwnbdhc39.webp}', 55, '2025-01-27 00:44:44', '2025-01-27 00:44:44', 16500000.00);
INSERT INTO public.products VALUES (37, 'Iphone 14', 'Điện thoại thông minh của Apple', 33000000.00, '{http://localhost:8080/v1/image/view/cbb784b1-b2f1-41ed-9a34-dee11fd622d3_sg-11134301-7rdvs-m01bhb1twkvn33@resize_w450_nl.webp,http://localhost:8080/v1/image/view/02d8b5fb-903e-4cab-b31c-84382cf6532c_sg-11134301-7rdxf-m01bu9wypdi590.webp,http://localhost:8080/v1/image/view/0e0b2d78-3b6f-47e4-b3a3-55b258662e6d_sg-11134301-7rdxw-m01bhrwnbdhc39.webp}', 55, '2025-01-27 00:44:44', '2025-01-27 00:44:44', 16500000.00);
INSERT INTO public.products VALUES (2, 'Tablet ABC', 'Máy tính bảng ABC với màn hình lớn và pin lâu', 8000000.00, '{4,5}', 30, '2025-01-25 15:44:44.261178', '2025-01-25 15:44:44.261178', 4000000.00);


--
-- TOC entry 3496 (class 0 OID 40961)
-- Dependencies: 236
-- Data for Name: report; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.report VALUES (4, '2025-01-29', 'SALES_AMOUNT', 198000000.00, 'ACTIVE', '2025-01-29 17:04:50.33', NULL);
INSERT INTO public.report VALUES (5, '2025-01-29', 'SALES_COUNT', 1.00, 'ACTIVE', '2025-01-29 17:04:50.442', NULL);
INSERT INTO public.report VALUES (6, '2025-02-10', 'SALES_AMOUNT', 298000000.00, 'ACTIVE', '2025-02-10 00:05:27.345', NULL);
INSERT INTO public.report VALUES (7, '2025-02-10', 'SALES_COUNT', 2.00, 'ACTIVE', '2025-02-10 00:05:27.581', NULL);
INSERT INTO public.report VALUES (8, '2025-02-11', 'SALES_AMOUNT', 298000000.00, 'ACTIVE', '2025-02-11 20:19:44.628', NULL);
INSERT INTO public.report VALUES (9, '2025-02-11', 'SALES_COUNT', 2.00, 'ACTIVE', '2025-02-11 20:19:44.782', NULL);
INSERT INTO public.report VALUES (10, '2025-02-12', 'SALES_AMOUNT', 298000000.00, 'ACTIVE', '2025-02-12 17:17:37.969', NULL);
INSERT INTO public.report VALUES (11, '2025-02-12', 'SALES_COUNT', 2.00, 'ACTIVE', '2025-02-12 17:17:38.102', NULL);


--
-- TOC entry 3494 (class 0 OID 16506)
-- Dependencies: 234
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.reviews VALUES (2, 5, 'Hàng lỗi không sử dụng được', '2025-01-27 00:00:18', '2025-01-27 00:00:18');
INSERT INTO public.reviews VALUES (3, 4, 'Hàng tốt', '2025-01-27 00:00:18', '2025-01-27 00:00:18');
INSERT INTO public.reviews VALUES (4, 2, 'Hàng Kém', '2025-01-27 00:00:18', '2025-01-27 00:00:18');


--
-- TOC entry 3478 (class 0 OID 16386)
-- Dependencies: 218
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles VALUES (1, 'ROLE_USER');
INSERT INTO public.roles VALUES (2, 'ROLE_ADMIN');
INSERT INTO public.roles VALUES (3, 'ROLE_MODERATOR');


--
-- TOC entry 3481 (class 0 OID 16407)
-- Dependencies: 221
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_roles VALUES (2, 2);
INSERT INTO public.user_roles VALUES (3, 3);
INSERT INTO public.user_roles VALUES (4, 3);
INSERT INTO public.user_roles VALUES (4, 1);
INSERT INTO public.user_roles VALUES (5, 1);
INSERT INTO public.user_roles VALUES (1, 1);


--
-- TOC entry 3480 (class 0 OID 16393)
-- Dependencies: 220
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (2, 'usertest02@gmail.com', '$2a$10$cARVEG7PO86PE.uS7Udngubs2heyJieSfDZS418mCK6y9axdvYuxi', 'usertest02', NULL, NULL, NULL, NULL, NULL, 'ACTIVE');
INSERT INTO public.users VALUES (3, 'usertest03@gmail.com', '$2a$10$nVkR8TUMJF/ieDjm0ULHN.qGMnLaA6qNEGepCF33tiseWJ2xHJ3Wi', 'usertest03', NULL, NULL, NULL, NULL, NULL, 'ACTIVE');
INSERT INTO public.users VALUES (4, 'usertest04@gmail.com', '$2a$10$K.XpQcztpB5MDp61W2ifJedtxrpOhY9S7xOq9IyPwwAvBIg7RCmly', 'usertest04', NULL, NULL, NULL, NULL, NULL, 'ACTIVE');
INSERT INTO public.users VALUES (5, 'usertest06@gmail.com', '$2a$10$TYhyLGQ1berqUNeR1eP2ue0UimwmyL6Ava9SX6mXQnObQi2oPj7c6', 'usertest06', NULL, NULL, NULL, NULL, NULL, 'ACTIVE');
INSERT INTO public.users VALUES (1, 'tranhuy@gmail.com', '$2a$10$358K/.C5dnMHxQW2M06E3eAqc21nvLkNDG.L15roWqI6GMEE8jlYG', 'usertest01', 'Trần Phú Huy', '0965199203', 'Xóm 5, Đồng Bụt, Quốc Oai, Hà Nội', NULL, NULL, 'BANNED');


--
-- TOC entry 3512 (class 0 OID 0)
-- Dependencies: 230
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categories_id_seq', 15, true);


--
-- TOC entry 3513 (class 0 OID 0)
-- Dependencies: 222
-- Name: coupons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.coupons_id_seq', 13, true);


--
-- TOC entry 3514 (class 0 OID 0)
-- Dependencies: 228
-- Name: order_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_items_id_seq', 14, true);


--
-- TOC entry 3515 (class 0 OID 0)
-- Dependencies: 224
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 7, true);


--
-- TOC entry 3516 (class 0 OID 0)
-- Dependencies: 226
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 37, true);


--
-- TOC entry 3517 (class 0 OID 0)
-- Dependencies: 235
-- Name: report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.report_id_seq', 11, true);


--
-- TOC entry 3518 (class 0 OID 0)
-- Dependencies: 233
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reviews_id_seq', 4, true);


--
-- TOC entry 3519 (class 0 OID 0)
-- Dependencies: 217
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 5, true);


--
-- TOC entry 3314 (class 2606 OID 16489)
-- Name: categories categories_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_name_key UNIQUE (name);


--
-- TOC entry 3316 (class 2606 OID 16487)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 3300 (class 2606 OID 16433)
-- Name: coupons coupons_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT coupons_code_key UNIQUE (code);


--
-- TOC entry 3302 (class 2606 OID 16431)
-- Name: coupons coupons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT coupons_pkey PRIMARY KEY (id);


--
-- TOC entry 3310 (class 2606 OID 16466)
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);


--
-- TOC entry 3306 (class 2606 OID 16443)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3318 (class 2606 OID 16494)
-- Name: product_categories product_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_pkey PRIMARY KEY (product_id, category_id);


--
-- TOC entry 3308 (class 2606 OID 16459)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 3322 (class 2606 OID 40971)
-- Name: report report_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report
    ADD CONSTRAINT report_pkey PRIMARY KEY (id);


--
-- TOC entry 3320 (class 2606 OID 16516)
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- TOC entry 3286 (class 2606 OID 16391)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3288 (class 2606 OID 16561)
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 3312 (class 2606 OID 32786)
-- Name: order_items uka21q86vmkmpadpy1xn0ccxceq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT uka21q86vmkmpadpy1xn0ccxceq UNIQUE (review_id);


--
-- TOC entry 3304 (class 2606 OID 16557)
-- Name: coupons ukeplt0kkm9yf2of2lnx6c1oy9b; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT ukeplt0kkm9yf2of2lnx6c1oy9b UNIQUE (code);


--
-- TOC entry 3290 (class 2606 OID 16559)
-- Name: users ukk8d0f2n7n88w1a16yhua64onx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukk8d0f2n7n88w1a16yhua64onx UNIQUE (user_name);


--
-- TOC entry 3298 (class 2606 OID 16411)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3292 (class 2606 OID 16404)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 3294 (class 2606 OID 16402)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3296 (class 2606 OID 16406)
-- Name: users users_user_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_user_name_key UNIQUE (user_name);


--
-- TOC entry 3327 (class 2606 OID 32787)
-- Name: order_items fkks4llh7842p4oso6or8oaf4vx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT fkks4llh7842p4oso6or8oaf4vx FOREIGN KEY (review_id) REFERENCES public.reviews(id);


--
-- TOC entry 3325 (class 2606 OID 32780)
-- Name: orders fkn1d1gkxckw648m2n2d5gx0yx5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fkn1d1gkxckw648m2n2d5gx0yx5 FOREIGN KEY (coupon_id) REFERENCES public.coupons(id);


--
-- TOC entry 3328 (class 2606 OID 16467)
-- Name: order_items order_items_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- TOC entry 3329 (class 2606 OID 16472)
-- Name: order_items order_items_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 3326 (class 2606 OID 16444)
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 3330 (class 2606 OID 16500)
-- Name: product_categories product_categories_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id) ON DELETE CASCADE;


--
-- TOC entry 3331 (class 2606 OID 16495)
-- Name: product_categories product_categories_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 3323 (class 2606 OID 16417)
-- Name: user_roles user_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- TOC entry 3324 (class 2606 OID 16412)
-- Name: user_roles user_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


-- Completed on 2025-02-12 18:58:42

--
-- PostgreSQL database dump complete
--

