/* Populate de tablas */
INSERT INTO regiones(id, nombre) VALUES (1, 'Sudamérica');
INSERT INTO regiones(id, nombre) VALUES (2, 'Centroamérica');
INSERT INTO regiones(id, nombre) VALUES (3, 'Norteamérica');
INSERT INTO regiones(id, nombre) VALUES (4, 'Europa');
INSERT INTO regiones(id, nombre) VALUES (5, 'Asia');
INSERT INTO regiones(id, nombre) VALUES (6, 'África');
INSERT INTO regiones(id, nombre) VALUES (7, 'Oceanía');
INSERT INTO regiones(id, nombre) VALUES (8, 'Antártida');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (1, 'Titulo1', 'Esta es la historia 1', '01/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (2, 'Titulo2', 'Esta es la historia 2', '02/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (4, 'Titulo3', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (4, 'Titulo4', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (4, 'Titulo5', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (3, 'Titulo6', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (3, 'Titulo7', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (3, 'Titulo8', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (3, 'Titulo9', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (5, 'Titulo10', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (6, 'Titulo11', 'Esta es la historia 3', '03/12/2001');
INSERT INTO anuncios (region_id, titulo, historia, create_at) VALUES (7, 'Titulo12', 'Esta es la historia 3', '03/12/2001');
/* Creamos algunos usuarios */
INSERT INTO usuarios (username, password, enabled) VALUES ('carlos', '$2a$10$dnMX4L8HCKNK2WhojrRVJurE/XfHDakqLdPfH2A0Z6eDLJ95iaI8G', true);
INSERT INTO usuarios (username, password, enabled) VALUES ('admin', '$2a$10$PfjC2J8mrOKHQraydu.k..LKDpOkk27FyZ4KuHxF9jPmH2hDhn4Pu', true);
INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);