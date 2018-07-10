--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: category; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    slug character varying(100) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    is_deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.category OWNER TO test;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.category_id_seq OWNER TO test;

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- Name: event; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.event (
    id bigint NOT NULL,
    name character varying(150) NOT NULL,
    slug character varying(200) NOT NULL,
    thumbnail_photo character varying(255),
    organizer_name character varying(50) NOT NULL,
    organizer_photo character varying(255),
    description text NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    start_time character varying(5) NOT NULL,
    end_time character varying(5) NOT NULL,
    location_id bigint NOT NULL,
    category_id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    is_deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.event OWNER TO test;

--
-- Name: event_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.event_id_seq OWNER TO test;

--
-- Name: event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.event_id_seq OWNED BY public.event.id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO test;

--
-- Name: location; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.location (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    city character varying(100) NOT NULL,
    address text NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    is_deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.location OWNER TO test;

--
-- Name: location_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.location_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.location_id_seq OWNER TO test;

--
-- Name: location_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.location_id_seq OWNED BY public.location.id;


--
-- Name: order_transaction; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.order_transaction (
    id bigint NOT NULL,
    public_id character varying(255) NOT NULL,
    order_date timestamp with time zone NOT NULL,
    customer_first_name character varying(30) NOT NULL,
    customer_last_name character varying(30) NOT NULL,
    customer_email character varying(100) NOT NULL,
    customer_phone character varying(20) NOT NULL,
    customer_date_of_birth date NOT NULL,
    customer_gender character varying(10) NOT NULL,
    event_id bigint NOT NULL
);


ALTER TABLE public.order_transaction OWNER TO test;

--
-- Name: order_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.order_transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_transaction_id_seq OWNER TO test;

--
-- Name: order_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.order_transaction_id_seq OWNED BY public.order_transaction.id;


--
-- Name: order_transaction_item; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.order_transaction_item (
    id bigint NOT NULL,
    order_transaction_id bigint NOT NULL,
    ticket_id bigint NOT NULL,
    qty bigint NOT NULL,
    price numeric(16,2) NOT NULL,
    total_price numeric(16,2) NOT NULL
);


ALTER TABLE public.order_transaction_item OWNER TO test;

--
-- Name: order_transaction_item_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.order_transaction_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_transaction_item_id_seq OWNER TO test;

--
-- Name: order_transaction_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.order_transaction_item_id_seq OWNED BY public.order_transaction_item.id;


--
-- Name: service_order_transaction_public_id; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.service_order_transaction_public_id
    START WITH 83562
    INCREMENT BY 1
    MINVALUE 83562
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.service_order_transaction_public_id OWNER TO test;

--
-- Name: ticket; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.ticket (
    id bigint NOT NULL,
    event_id bigint NOT NULL,
    name character varying(150) NOT NULL,
    ticket_count bigint NOT NULL,
    actual_ticket_count bigint NOT NULL,
    description text NOT NULL,
    flag character varying(10) NOT NULL,
    price numeric(16,2) NOT NULL,
    start_date timestamp with time zone NOT NULL,
    end_date timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    is_deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.ticket OWNER TO test;

--
-- Name: ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.ticket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ticket_id_seq OWNER TO test;

--
-- Name: ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.ticket_id_seq OWNED BY public.ticket.id;


--
-- Name: category id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- Name: event id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.event ALTER COLUMN id SET DEFAULT nextval('public.event_id_seq'::regclass);


--
-- Name: location id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.location ALTER COLUMN id SET DEFAULT nextval('public.location_id_seq'::regclass);


--
-- Name: order_transaction id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.order_transaction ALTER COLUMN id SET DEFAULT nextval('public.order_transaction_id_seq'::regclass);


--
-- Name: order_transaction_item id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.order_transaction_item ALTER COLUMN id SET DEFAULT nextval('public.order_transaction_item_id_seq'::regclass);


--
-- Name: ticket id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.ticket ALTER COLUMN id SET DEFAULT nextval('public.ticket_id_seq'::regclass);


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.category (id, name, slug, created_at, updated_at, is_deleted) FROM stdin;
1	FESTIVAL ELECTRONIC DANCE MUSIC	festival-electronic-dance-music	2018-07-11 04:28:20.144+07	2018-07-11 04:28:20.144+07	f
2	FESTIVAL GENERAL	festival-general	2018-07-11 04:28:32.516+07	2018-07-11 04:28:32.516+07	f
3	CONCERT	concert	2018-07-11 04:28:41.501+07	2018-07-11 04:28:41.501+07	f
4	SPORTS	sports	2018-07-11 04:28:47.629+07	2018-07-11 04:28:47.629+07	f
5	EXHIBITION	exhibition	2018-07-11 04:28:55.789+07	2018-07-11 04:28:55.789+07	f
6	CONFERENCE	conference	2018-07-11 04:29:03.269+07	2018-07-11 04:29:03.269+07	f
7	WORKSHOP	workshop	2018-07-11 04:29:11.336+07	2018-07-11 04:29:11.336+07	f
8	THEATER & DRAMA MUSICAL	theater--drama-musical	2018-07-11 04:29:31.692+07	2018-07-11 04:29:31.692+07	f
9	ATTRACTION	attraction	2018-07-11 04:29:44.509+07	2018-07-11 04:29:44.509+07	f
10	ACCOMODATION	accomodation	2018-07-11 04:29:50.942+07	2018-07-11 04:29:50.942+07	f
11	TRANSPORTATION	transportation	2018-07-11 04:29:59.692+07	2018-07-11 04:29:59.692+07	f
12	OTHER	other	2018-07-11 04:30:06.115+07	2018-07-11 04:30:06.115+07	f
\.


--
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.event (id, name, slug, thumbnail_photo, organizer_name, organizer_photo, description, start_date, end_date, start_time, end_time, location_id, category_id, created_at, updated_at, is_deleted) FROM stdin;
1	Glenn Fredly Live On Concert	glenn-fredly-live-on-concert	\N	PT. Pandawa 5	\N	Glenn Fredly Live On Concert Untuk pertama kalinya tahun ini, Glenn Fredly akan menyapa serta menghibur kota Jogjakarta! Pastinya gak sabar dong buat nyanyi lagu Terserah, Akhir cerita cinta, Sekali ini saja dan lagu-lagu hits lainnya? Dapatkan tiketmu segera! 	2018-07-13	2018-07-13	20:00	23:00	2	3	2018-07-11 04:35:36.783+07	2018-07-11 04:35:36.783+07	f
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1.0.0	init	SQL	V1_0_0__init.sql	1130151143	test	2018-07-11 04:27:32.884882	206	t
\.


--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.location (id, name, city, address, latitude, longitude, created_at, updated_at, is_deleted) FROM stdin;
1	Lapangan Soekarno Hatta	DKI Jakarta	Jl Merdeka 45	-6.1255810000000004	106.652483	2018-07-11 04:30:27.281+07	2018-07-11 04:30:27.281+07	f
2	Grand Pacific Hall Yogyakarta	Kab. Sleman	Jalan Magelang Km 4.5, Depan TVRI Yogyakarta, Sinduadi, Mlati, Kabupaten Sleman, Daerah Istimewa Yogyakarta	-7.7651779999999997	110.36188199999999	2018-07-11 04:34:17.987+07	2018-07-11 04:34:17.987+07	f
\.


--
-- Data for Name: order_transaction; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.order_transaction (id, public_id, order_date, customer_first_name, customer_last_name, customer_email, customer_phone, customer_date_of_birth, customer_gender, event_id) FROM stdin;
1	INV020180711083562	2018-07-11 04:39:36.973+07	Dhanar	J Kusuma	dhanar.j.kusuma@gmail.com	08282828282	1995-08-06	MALE	1
\.


--
-- Data for Name: order_transaction_item; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.order_transaction_item (id, order_transaction_id, ticket_id, qty, price, total_price) FROM stdin;
1	1	1	2	200000.00	400000.00
\.


--
-- Data for Name: ticket; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.ticket (id, event_id, name, ticket_count, actual_ticket_count, description, flag, price, start_date, end_date, created_at, updated_at, is_deleted) FROM stdin;
2	1	VIP	1000	1000	First Come First Served.	PAID	500000.00	2018-07-11 07:00:00+07	2018-07-13 07:00:00+07	2018-07-11 04:37:59.804+07	2018-07-11 04:37:59.804+07	f
3	1	Buy 1 Get 1 Free	1000	1000	FESTIVAL.	PAID	200000.00	2018-07-11 07:00:00+07	2018-07-13 07:00:00+07	2018-07-11 04:38:30.851+07	2018-07-11 04:38:30.851+07	f
1	1	Presale - Festival	1000	998	beli aja	PAID	200000.00	2018-07-11 07:00:00+07	2018-07-13 07:00:00+07	2018-07-11 04:37:27.321+07	2018-07-11 04:37:27.321+07	f
\.


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.category_id_seq', 12, true);


--
-- Name: event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.event_id_seq', 1, true);


--
-- Name: location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.location_id_seq', 2, true);


--
-- Name: order_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.order_transaction_id_seq', 1, true);


--
-- Name: order_transaction_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.order_transaction_item_id_seq', 1, true);


--
-- Name: service_order_transaction_public_id; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.service_order_transaction_public_id', 83562, true);


--
-- Name: ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.ticket_id_seq', 3, true);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: category category_unique_slug; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_unique_slug UNIQUE (slug);


--
-- Name: event event_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT event_pkey PRIMARY KEY (id);


--
-- Name: event event_unique_slug; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT event_unique_slug UNIQUE (slug);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- Name: order_transaction_item order_transaction_item_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.order_transaction_item
    ADD CONSTRAINT order_transaction_item_pkey PRIMARY KEY (id);


--
-- Name: order_transaction order_transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.order_transaction
    ADD CONSTRAINT order_transaction_pkey PRIMARY KEY (id);


--
-- Name: ticket ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: public_id_idx_order_transaction; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX public_id_idx_order_transaction ON public.order_transaction USING btree (public_id);


--
-- Name: event fk_category_idx_category_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT fk_category_idx_category_id FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: ticket fk_event_idx_event_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT fk_event_idx_event_id FOREIGN KEY (event_id) REFERENCES public.event(id);


--
-- Name: order_transaction fk_event_idx_event_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.order_transaction
    ADD CONSTRAINT fk_event_idx_event_id FOREIGN KEY (event_id) REFERENCES public.event(id);


--
-- Name: event fk_location_idx_location_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT fk_location_idx_location_id FOREIGN KEY (location_id) REFERENCES public.location(id);


--
-- Name: order_transaction_item fk_ticket_idx_ticket; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.order_transaction_item
    ADD CONSTRAINT fk_ticket_idx_ticket FOREIGN KEY (ticket_id) REFERENCES public.ticket(id);


--
-- Name: order_transaction_item fk_transaction_idx_order_transaction_item; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.order_transaction_item
    ADD CONSTRAINT fk_transaction_idx_order_transaction_item FOREIGN KEY (order_transaction_id) REFERENCES public.order_transaction(id);


--
-- PostgreSQL database dump complete
--

