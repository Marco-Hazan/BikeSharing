--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4 (Ubuntu 12.4-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.4 (Ubuntu 12.4-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: bikesharing; Type: SCHEMA; Schema: -; Owner: marco_hazan
--

CREATE SCHEMA bikesharing;


ALTER SCHEMA bikesharing OWNER TO marco_hazan;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bicicletta; Type: TABLE; Schema: bikesharing; Owner: marco_hazan
--

CREATE TABLE bikesharing.bicicletta (
    codice character(8) NOT NULL,
    rastrelliera character varying(30),
    posizione smallint,
    tipo character varying(30) DEFAULT 1 NOT NULL,
    stato text DEFAULT 'OK'::text NOT NULL
);


ALTER TABLE bikesharing.bicicletta OWNER TO marco_hazan;

--
-- Name: Biciclette_codice_seq; Type: SEQUENCE; Schema: bikesharing; Owner: marco_hazan
--

CREATE SEQUENCE bikesharing."Biciclette_codice_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bikesharing."Biciclette_codice_seq" OWNER TO marco_hazan;

--
-- Name: Biciclette_codice_seq; Type: SEQUENCE OWNED BY; Schema: bikesharing; Owner: marco_hazan
--

ALTER SEQUENCE bikesharing."Biciclette_codice_seq" OWNED BY bikesharing.bicicletta.codice;


--
-- Name: abbonato; Type: TABLE; Schema: bikesharing; Owner: marco_hazan
--

CREATE TABLE bikesharing.abbonato (
    codice character varying(50) NOT NULL,
    scadenza timestamp without time zone,
    tipologia character varying(30) NOT NULL,
    penalita integer DEFAULT 0 NOT NULL,
    debito numeric(6,2) DEFAULT 0 NOT NULL,
    carta character(16) NOT NULL,
    studente boolean DEFAULT true NOT NULL,
    password character varying(50) NOT NULL,
    username character varying(30) DEFAULT 'x'::character varying NOT NULL
);


ALTER TABLE bikesharing.abbonato OWNER TO marco_hazan;

--
-- Name: abbonamenti_debito_seq; Type: SEQUENCE; Schema: bikesharing; Owner: marco_hazan
--

CREATE SEQUENCE bikesharing.abbonamenti_debito_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bikesharing.abbonamenti_debito_seq OWNER TO marco_hazan;

--
-- Name: abbonamenti_debito_seq; Type: SEQUENCE OWNED BY; Schema: bikesharing; Owner: marco_hazan
--

ALTER SEQUENCE bikesharing.abbonamenti_debito_seq OWNED BY bikesharing.abbonato.debito;


--
-- Name: abbonamenti_penalita _seq; Type: SEQUENCE; Schema: bikesharing; Owner: marco_hazan
--

CREATE SEQUENCE bikesharing."abbonamenti_penalita _seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bikesharing."abbonamenti_penalita _seq" OWNER TO marco_hazan;

--
-- Name: abbonamenti_penalita _seq; Type: SEQUENCE OWNED BY; Schema: bikesharing; Owner: marco_hazan
--

ALTER SEQUENCE bikesharing."abbonamenti_penalita _seq" OWNED BY bikesharing.abbonato.penalita;


--
-- Name: carta; Type: TABLE; Schema: bikesharing; Owner: marco_hazan
--

CREATE TABLE bikesharing.carta (
    numero character(16) NOT NULL,
    cvv character(3) NOT NULL,
    mese smallint NOT NULL,
    anno smallint NOT NULL,
    soldi_spesi numeric(6,2) DEFAULT 0 NOT NULL,
    tetto numeric(6,2) DEFAULT 200 NOT NULL
);


ALTER TABLE bikesharing.carta OWNER TO marco_hazan;

--
-- Name: corsa; Type: TABLE; Schema: bikesharing; Owner: marco_hazan
--

CREATE TABLE bikesharing.corsa (
    id integer NOT NULL,
    abbonato character(7) NOT NULL,
    bicicletta character(8) NOT NULL,
    rastrelliera_partenza character varying(30) NOT NULL,
    rastrelliera_arrivo character varying(30),
    attiva boolean NOT NULL,
    start timestamp without time zone NOT NULL,
    end_corsa timestamp without time zone,
    multata boolean DEFAULT false NOT NULL
);


ALTER TABLE bikesharing.corsa OWNER TO marco_hazan;

--
-- Name: corsa_id_seq; Type: SEQUENCE; Schema: bikesharing; Owner: marco_hazan
--

CREATE SEQUENCE bikesharing.corsa_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bikesharing.corsa_id_seq OWNER TO marco_hazan;

--
-- Name: corsa_id_seq; Type: SEQUENCE OWNED BY; Schema: bikesharing; Owner: marco_hazan
--

ALTER SEQUENCE bikesharing.corsa_id_seq OWNED BY bikesharing.corsa.id;


--
-- Name: personale; Type: TABLE; Schema: bikesharing; Owner: marco_hazan
--

CREATE TABLE bikesharing.personale (
    id integer NOT NULL,
    codice character varying(50) NOT NULL,
    password integer NOT NULL
);


ALTER TABLE bikesharing.personale OWNER TO marco_hazan;

--
-- Name: personale_id_seq; Type: SEQUENCE; Schema: bikesharing; Owner: marco_hazan
--

CREATE SEQUENCE bikesharing.personale_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bikesharing.personale_id_seq OWNER TO marco_hazan;

--
-- Name: personale_id_seq; Type: SEQUENCE OWNED BY; Schema: bikesharing; Owner: marco_hazan
--

ALTER SEQUENCE bikesharing.personale_id_seq OWNED BY bikesharing.personale.id;


--
-- Name: personale_password_seq; Type: SEQUENCE; Schema: bikesharing; Owner: marco_hazan
--

CREATE SEQUENCE bikesharing.personale_password_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bikesharing.personale_password_seq OWNER TO marco_hazan;

--
-- Name: personale_password_seq; Type: SEQUENCE OWNED BY; Schema: bikesharing; Owner: marco_hazan
--

ALTER SEQUENCE bikesharing.personale_password_seq OWNED BY bikesharing.personale.password;


--
-- Name: rastrelliera; Type: TABLE; Schema: bikesharing; Owner: marco_hazan
--

CREATE TABLE bikesharing.rastrelliera (
    nome character varying(30) NOT NULL,
    size smallint NOT NULL,
    tot_morse_elettriche smallint
);


ALTER TABLE bikesharing.rastrelliera OWNER TO marco_hazan;

--
-- Name: rastrelliere_codice_seq; Type: SEQUENCE; Schema: bikesharing; Owner: marco_hazan
--

CREATE SEQUENCE bikesharing.rastrelliere_codice_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bikesharing.rastrelliere_codice_seq OWNER TO marco_hazan;

--
-- Name: rastrelliere_codice_seq; Type: SEQUENCE OWNED BY; Schema: bikesharing; Owner: marco_hazan
--

ALTER SEQUENCE bikesharing.rastrelliere_codice_seq OWNED BY bikesharing.rastrelliera.nome;


--
-- Name: corsa id; Type: DEFAULT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.corsa ALTER COLUMN id SET DEFAULT nextval('bikesharing.corsa_id_seq'::regclass);


--
-- Name: personale id; Type: DEFAULT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.personale ALTER COLUMN id SET DEFAULT nextval('bikesharing.personale_id_seq'::regclass);


--
-- Name: personale password; Type: DEFAULT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.personale ALTER COLUMN password SET DEFAULT nextval('bikesharing.personale_password_seq'::regclass);


--
-- Data for Name: abbonato; Type: TABLE DATA; Schema: bikesharing; Owner: marco_hazan
--

INSERT INTO bikesharing.abbonato VALUES ('ZOOE7QG', NULL, 'settimanale', 0, 0.00, '7680240786577792', false, 'luca98', 'luca');
INSERT INTO bikesharing.abbonato VALUES ('7HP18R7', NULL, 'settimanale', 0, 0.00, '8444121204674459', true, 'emanuele', 'emanuele');
INSERT INTO bikesharing.abbonato VALUES ('XL7D2OD', '2022-10-04 21:42:29.037', 'annuale', 0, 0.00, '5922058420183886', false, 'password', 'paolo');
INSERT INTO bikesharing.abbonato VALUES ('FS3UPMJ', NULL, 'mariano', 0, 0.00, '0523619489999890', false, 'mariano', 'mariano');
INSERT INTO bikesharing.abbonato VALUES ('O6GYOEV', NULL, 'giornaliero', 0, 0.00, '0799152364299721', true, 'laura11', 'laura');
INSERT INTO bikesharing.abbonato VALUES ('JGHFE6J', '2022-09-29 14:49:20.451', 'annuale', 0, 0.00, '1100110011001100', true, 'testerpassword', 'tester');


--
-- Data for Name: bicicletta; Type: TABLE DATA; Schema: bikesharing; Owner: marco_hazan
--

INSERT INTO bikesharing.bicicletta VALUES ('C1251984', 'Cenisio', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4442104', 'Cenisio', 15, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6877274', 'Bignami', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E0044368', 'Bignami', 14, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6641894', 'Viale Tunisia', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C5385549', 'Viale Tunisia', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E4347412', 'Viale Tunisia', 23, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C5829009', 'Ple Lagosta', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3753339', 'Ple Lagosta', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E6085337', 'Sondrio', 15, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E9461782', 'Sondrio', 20, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0902835', 'Maciachini', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S9652617', 'Maciachini', 12, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7937738', 'Piazza Piola', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S7704924', 'Piazza Piola', 19, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S0511984', 'Piazza Piola', 22, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E9471278', 'Piazza Piola', 26, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3137913', 'Duomo', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S0629690', 'Duomo', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8861741', 'Duomo', 21, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S3508355', 'Duomo', 29, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8584936', 'Zara', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4853307', 'Zara', 17, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S6115640', 'Zara', 21, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2965351', 'Istria', 12, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3109307', 'Alserio', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E4951854', 'Alserio', 14, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4340578', 'Carbonari', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S2851087', 'Bicocca', 18, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E4150232', 'Bicocca', 26, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1838273', 'Cenisio', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7941261', 'Cenisio', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S0479489', 'Bignami', 8, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6536496', 'Viale Tunisia', 7, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6818946', 'Bicocca', 11, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E9033854', 'Bicocca', 25, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E9044307', 'Viale Tunisia', 18, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2908506', 'Bignami', 15, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4222782', 'Piazza Piola', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S3266985', 'Ple Lagosta', 11, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S1135419', 'Ple Lagosta', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2110279', 'Ple Lagosta', 19, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9337711', 'Sondrio', 7, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S0225885', 'Sondrio', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E3758267', 'Maciachini', 13, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S7209017', 'Maciachini', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E8459511', 'Piazza Piola', 23, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S6027342', 'Piazza Piola', 25, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7953433', 'Duomo', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4965312', 'Duomo', 7, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6465303', 'Duomo', 9, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S2029013', 'Duomo', 17, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9558006', 'Zara', 8, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8303515', 'Zara', 14, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4947203', 'Zara', 22, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S5258176', 'Istria', 13, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7787545', 'Alserio', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E7982498', 'Alserio', 15, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E1251911', 'Carbonari', 11, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E3986892', 'Carbonari', 17, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E6418964', 'Cenisio', 11, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E0349768', 'Cenisio', 16, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S6332888', 'Bignami', 9, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3750081', 'Viale Tunisia', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4032256', 'Viale Tunisia', 8, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S1928179', 'Viale Tunisia', 14, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8360515', 'Piazza Piola', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4033670', 'Ple Lagosta', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E8274559', 'Ple Lagosta', 12, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4155301', 'Sondrio', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4826092', 'Sondrio', 14, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E7376613', 'Sondrio', 21, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1602127', 'Piazza Piola', 7, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9874072', 'Maciachini', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E6793098', 'Maciachini', 17, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0599858', 'Piazza Piola', 11, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C2735486', 'Duomo', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3711609', 'Duomo', 8, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4217389', 'Duomo', 10, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S3034379', 'Duomo', 18, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7611979', 'Zara', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S5268367', 'Zara', 18, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S0595633', 'Zara', 23, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3972751', 'Alserio', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S2336737', 'Alserio', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S6911736', 'Alserio', 20, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E9168369', 'Piazza Piola', 24, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E1414131', 'Bicocca', 19, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3518250', 'Carbonari', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S7282938', 'Carbonari', 12, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4285670', 'Bicocca', 27, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C2146521', 'Cenisio', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0605405', 'Cenisio', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6556621', 'Piazza Piola', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S7849430', 'Bignami', 10, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6553686', 'Viale Tunisia', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E3145683', 'Viale Tunisia', 19, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S5760693', 'Viale Tunisia', 21, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C2511732', 'Piazza Piola', 8, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2948231', 'Ple Lagosta', 13, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S6180164', 'Ple Lagosta', 17, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7934623', 'Sondrio', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3710178', 'Sondrio', 8, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E8203379', 'Sondrio', 17, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C5014205', 'Maciachini', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S7927950', 'Maciachini', 14, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9849507', 'Duomo', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E9645486', 'Duomo', 22, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E8511439', 'Duomo', 25, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S0884295', 'Duomo', 30, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4055892', 'Zara', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8769881', 'Zara', 9, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S2615098', 'Zara', 24, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9034434', 'Bicocca', 8, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8023695', 'Bicocca', 12, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S9545505', 'Alserio', 11, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S2605190', 'Alserio', 17, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2305543', 'Carbonari', 13, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E8592252', 'Carbonari', 18, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4327941', 'Cenisio', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E0390894', 'Cenisio', 12, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0040757', 'Piazza Piola', 10, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E4162756', 'Bignami', 11, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S9671712', 'Piazza Piola', 17, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3938722', 'Viale Tunisia', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8507738', 'Viale Tunisia', 15, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E7787281', 'Viale Tunisia', 22, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9208518', 'Ple Lagosta', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0102810', 'Sondrio', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1970445', 'Sondrio', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C5182349', 'Sondrio', 9, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0956717', 'Maciachini', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0074725', 'Maciachini', 6, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7295767', 'Duomo', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E7379652', 'Duomo', 23, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8048946', 'Duomo', 26, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6400755', 'Zara', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S1822694', 'Zara', 15, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S3078297', 'Zara', 19, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8861109', 'Istria', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3919384', 'Alserio', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E5842310', 'Alserio', 18, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7897175', 'Bicocca', 9, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9933977', 'Ple Lagosta', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8208747', 'Bicocca', 28, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3587831', 'Carbonari', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S1002831', 'Carbonari', 14, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3733227', 'Cenisio', 7, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S6296624', 'Cenisio', 13, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8046238', 'Bignami', 1, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E8833145', 'Bignami', 12, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1990342', 'Viale Tunisia', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0258297', 'Viale Tunisia', 9, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4170540', 'Viale Tunisia', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E4453715', 'Ple Lagosta', 14, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E9973185', 'Ple Lagosta', 18, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9293410', 'Sondrio', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C0063272', 'Sondrio', 10, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E4872843', 'Sondrio', 18, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4016764', 'Piazza Piola', 9, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6479144', 'Maciachini', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C5408921', 'Maciachini', 7, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S7486965', 'Piazza Piola', 18, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4446063', 'Piazza Piola', 27, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E0220747', 'Duomo', 19, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8336927', 'Duomo', 24, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S5347651', 'Duomo', 27, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1311901', 'Zara', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8914035', 'Zara', 7, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C2657242', 'Zara', 10, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8615728', 'Istria', 11, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E8869727', 'Alserio', 12, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E5803728', 'Alserio', 19, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C6573583', 'Bicocca', 10, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7945702', 'Bicocca', 13, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2275494', 'Carbonari', 15, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S3682494', 'Cenisio', 14, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3874165', 'Bignami', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E1872819', 'Bignami', 13, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4233222', 'Viale Tunisia', 10, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E6976765', 'Viale Tunisia', 17, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S1881783', 'Viale Tunisia', 20, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C3758547', 'Sondrio', 4, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C7587855', 'Ple Lagosta', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S9679223', 'Ple Lagosta', 15, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S9605392', 'Sondrio', 19, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C4110694', 'Piazza Piola', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E3004059', 'Piazza Piola', 21, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1292007', 'Carbonari', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2010759', 'Maciachini', 11, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E2965600', 'Maciachini', 15, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E1016167', 'Maciachini', 18, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1957775', 'Duomo', 5, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E5136985', 'Duomo', 20, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S2457899', 'Duomo', 28, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C1150589', 'Zara', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S8646817', 'Zara', 16, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E6118844', 'Zara', 20, 'E', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C8421587', 'Istria', 2, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('C9425881', 'Alserio', 3, 'C', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S5587566', 'Alserio', 13, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('S4414660', 'Bicocca', 23, 'S', 'OK');
INSERT INTO bikesharing.bicicletta VALUES ('E0196718', 'Bicocca', 29, 'E', 'OK');


--
-- Data for Name: carta; Type: TABLE DATA; Schema: bikesharing; Owner: marco_hazan
--

INSERT INTO bikesharing.carta VALUES ('7680240786577792', '122', 1, 2022, 9.00, 300.00);
INSERT INTO bikesharing.carta VALUES ('8444121204674459', '122', 11, 2022, 9.00, 300.00);
INSERT INTO bikesharing.carta VALUES ('0523619489999890', '111', 10, 2022, 4.50, 300.00);
INSERT INTO bikesharing.carta VALUES ('0799152364299721', '222', 11, 2022, 4.50, 300.00);
INSERT INTO bikesharing.carta VALUES ('4747130779955940', '122', 11, 2022, 36.00, 200.00);
INSERT INTO bikesharing.carta VALUES ('5922058420183886', '122', 11, 2022, 36.00, 300.00);
INSERT INTO bikesharing.carta VALUES ('5506786314353168', '122', 11, 2022, 36.00, 200.00);
INSERT INTO bikesharing.carta VALUES ('1100110011001100', '110', 11, 2022, 36.00, 300.00);


--
-- Data for Name: corsa; Type: TABLE DATA; Schema: bikesharing; Owner: marco_hazan
--



--
-- Data for Name: personale; Type: TABLE DATA; Schema: bikesharing; Owner: marco_hazan
--

INSERT INTO bikesharing.personale VALUES (1, 'QWERTY98', 123);


--
-- Data for Name: rastrelliera; Type: TABLE DATA; Schema: bikesharing; Owner: marco_hazan
--

INSERT INTO bikesharing.rastrelliera VALUES ('Duomo', 30, 15);
INSERT INTO bikesharing.rastrelliera VALUES ('Carbonari', 20, 10);
INSERT INTO bikesharing.rastrelliera VALUES ('Zara', 25, 12);
INSERT INTO bikesharing.rastrelliera VALUES ('Bicocca', 30, 15);
INSERT INTO bikesharing.rastrelliera VALUES ('Maciachini', 20, 10);
INSERT INTO bikesharing.rastrelliera VALUES ('Cenisio', 20, 10);
INSERT INTO bikesharing.rastrelliera VALUES ('Piazza Piola', 30, 15);
INSERT INTO bikesharing.rastrelliera VALUES ('Via Ponale', 25, 12);
INSERT INTO bikesharing.rastrelliera VALUES ('Sondrio', 25, 12);
INSERT INTO bikesharing.rastrelliera VALUES ('Alserio', 20, 10);
INSERT INTO bikesharing.rastrelliera VALUES ('Ple Lagosta', 20, 10);
INSERT INTO bikesharing.rastrelliera VALUES ('Istria', 20, 10);
INSERT INTO bikesharing.rastrelliera VALUES ('Bignami', 22, 15);
INSERT INTO bikesharing.rastrelliera VALUES ('Viale Tunisia', 25, 12);


--
-- Name: Biciclette_codice_seq; Type: SEQUENCE SET; Schema: bikesharing; Owner: marco_hazan
--

SELECT pg_catalog.setval('bikesharing."Biciclette_codice_seq"', 23, true);


--
-- Name: abbonamenti_debito_seq; Type: SEQUENCE SET; Schema: bikesharing; Owner: marco_hazan
--

SELECT pg_catalog.setval('bikesharing.abbonamenti_debito_seq', 1, false);


--
-- Name: abbonamenti_penalita _seq; Type: SEQUENCE SET; Schema: bikesharing; Owner: marco_hazan
--

SELECT pg_catalog.setval('bikesharing."abbonamenti_penalita _seq"', 1, false);


--
-- Name: corsa_id_seq; Type: SEQUENCE SET; Schema: bikesharing; Owner: marco_hazan
--

SELECT pg_catalog.setval('bikesharing.corsa_id_seq', 152, true);


--
-- Name: personale_id_seq; Type: SEQUENCE SET; Schema: bikesharing; Owner: marco_hazan
--

SELECT pg_catalog.setval('bikesharing.personale_id_seq', 1, true);


--
-- Name: personale_password_seq; Type: SEQUENCE SET; Schema: bikesharing; Owner: marco_hazan
--

SELECT pg_catalog.setval('bikesharing.personale_password_seq', 1, true);


--
-- Name: rastrelliere_codice_seq; Type: SEQUENCE SET; Schema: bikesharing; Owner: marco_hazan
--

SELECT pg_catalog.setval('bikesharing.rastrelliere_codice_seq', 1, false);


--
-- Name: bicicletta Biciclette_pkey; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.bicicletta
    ADD CONSTRAINT "Biciclette_pkey" PRIMARY KEY (codice);


--
-- Name: abbonato abbonamenti_pkey; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.abbonato
    ADD CONSTRAINT abbonamenti_pkey PRIMARY KEY (codice);


--
-- Name: abbonato abbonato_username_key; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.abbonato
    ADD CONSTRAINT abbonato_username_key UNIQUE (username);


--
-- Name: bicicletta bicicletta_rastrelliera_posizione_key; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.bicicletta
    ADD CONSTRAINT bicicletta_rastrelliera_posizione_key UNIQUE (rastrelliera, posizione);


--
-- Name: carta carta_pkey; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.carta
    ADD CONSTRAINT carta_pkey PRIMARY KEY (numero);


--
-- Name: carta carte_numero_key; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.carta
    ADD CONSTRAINT carte_numero_key UNIQUE (numero);


--
-- Name: corsa corsa_pkey; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.corsa
    ADD CONSTRAINT corsa_pkey PRIMARY KEY (id);


--
-- Name: personale personale_pkey; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.personale
    ADD CONSTRAINT personale_pkey PRIMARY KEY (id);


--
-- Name: rastrelliera rastrelliere_pkey; Type: CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.rastrelliera
    ADD CONSTRAINT rastrelliere_pkey PRIMARY KEY (nome);


--
-- Name: abbonato abbonato_carta_fkey; Type: FK CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.abbonato
    ADD CONSTRAINT abbonato_carta_fkey FOREIGN KEY (carta) REFERENCES bikesharing.carta(numero) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: bicicletta biciclette_rastrelliera_fkey; Type: FK CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.bicicletta
    ADD CONSTRAINT biciclette_rastrelliera_fkey FOREIGN KEY (rastrelliera) REFERENCES bikesharing.rastrelliera(nome) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: corsa corsa_abbonato_fkey; Type: FK CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.corsa
    ADD CONSTRAINT corsa_abbonato_fkey FOREIGN KEY (abbonato) REFERENCES bikesharing.abbonato(codice) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: corsa corsa_bicicletta_fkey; Type: FK CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.corsa
    ADD CONSTRAINT corsa_bicicletta_fkey FOREIGN KEY (bicicletta) REFERENCES bikesharing.bicicletta(codice) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: corsa corsa_rastrelliera_arrivo_fkey; Type: FK CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.corsa
    ADD CONSTRAINT corsa_rastrelliera_arrivo_fkey FOREIGN KEY (rastrelliera_arrivo) REFERENCES bikesharing.rastrelliera(nome) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: corsa corsa_rastrelliera_partenza_fkey; Type: FK CONSTRAINT; Schema: bikesharing; Owner: marco_hazan
--

ALTER TABLE ONLY bikesharing.corsa
    ADD CONSTRAINT corsa_rastrelliera_partenza_fkey FOREIGN KEY (rastrelliera_partenza) REFERENCES bikesharing.rastrelliera(nome) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

