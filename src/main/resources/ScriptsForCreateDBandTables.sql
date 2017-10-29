-- Database: DominoDB

-- DROP DATABASE DominoDB;

CREATE DATABASE DominoDB
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Ukrainian_Ukraine.1251'
    LC_CTYPE = 'Ukrainian_Ukraine.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
-- Table: public.chains

-- DROP TABLE public.chains;

CREATE TABLE public.chains
(
    chain_id integer NOT NULL DEFAULT nextval('chains_chain_id_seq'::regclass),
    date date NOT NULL,
    chain text COLLATE pg_catalog."default",
    CONSTRAINT chains_pkey PRIMARY KEY (chain_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.chains
    OWNER to postgres;
	
-- Table: public.sets

-- DROP TABLE public.sets;

CREATE TABLE public.sets
(
    set_id integer NOT NULL DEFAULT nextval('sets_set_id_seq'::regclass),
    chain_id integer NOT NULL,
    set text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT sets_pkey PRIMARY KEY (set_id),
    CONSTRAINT chain_id FOREIGN KEY (chain_id)
        REFERENCES public.chains (chain_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.sets
    OWNER to postgres;