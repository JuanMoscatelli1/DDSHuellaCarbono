INSERT INTO `tp-dds`.usuario(id, contrasenia, nombre_de_usuario) VALUES(4, '123456', 'ADMINISTRADOR');
INSERT INTO `tp-dds`.pais(id, nombre) VALUES(1, 'ARGENTINA');
INSERT INTO `tp-dds`.provincia(id, nombre, pais_id, sector_provincial_id)
VALUES(169, 'CATAMARCA', 1, NULL), 
      (170, 'CHACO', 1, NULL),
      (171, 'CHUBUT' , 1, NULL),
      (172, 'CORDOBA', 1, NULL),
      (173, 'CORRIENTES', 1, NULL),
      (174, 'CIUDAD DE BUENOS AIRES', 1, NULL),
      (175, 'ENTRE RIOS', 1, NULL),
      (176, 'FORMOSA', 1, NULL),
      (177, 'JUJUY', 1, NULL),
      (178, 'LA PAMPA', 1, NULL),
      (179, 'LA RIOJA', 1, NULL),
      (180, 'MENDOZA', 1, NULL);
      
INSERT INTO `tp-dds`.sector_provincial(id, provincia_id, usuario_id) 
VALUES (2, 170, NULL),
	   (3, 171, NULL),
       (4, 172, NULL),
       (5, 173, NULL),
       (6, 174, NULL),
       (7, 175, NULL),
       (8, 176, NULL),
	   (9, 177, NULL),
       (10, 178, NULL),
       (11, 179, NULL),
       (12, 180, NULL);

INSERT INTO `tp-dds`.municipio(id, nombre, provincia_id, sector_municipal_id)
VALUES (332, 'ALBERTI', 168, NULL),
       (333, 'ALMIRANTE BROWN', 168, NULL),
       (335, 'AVELLANEDA', 168, NULL),
       (338, 'BAHIA BLANCA', 168, NULL),
       (339, 'BALCARCE', 168, NULL),
       (357, 'COLON', 168, NULL), 
       (369, 'FLORENCIO VARELA', 168, NULL);

INSERT INTO `tp-dds`.sector_municipal(id, municipio_id, sector_provincial_id, usuario_id)
VALUES (3, 332, 1, NULL),
       (4, 333, 1, NULL),
	   (5, 335, 1, NULL),
       (6, 338, 1, NULL),
       (7, 339, 1, NULL),
       (8, 357, 1, NULL),
       (9, 369, 1, NULL);
       
INSERT INTO `tp-dds`.localidad(id, codigo_postal, nombre, municipio_id)
VALUES (3297, 1667, 'VILLA ORTIZ', 332),
       (3300, 1667, 'VILLA GRISOLIA (EST. ACHUPALLAS)', 332),
       (3301, 1667, 'VILLA MARIA', 332),
       (3302, 1846, 'ADROGUE', 333),
       (3303, 1852, 'BURZACO', 333),
       (3304, 1856, 'GLEW', 333),
       (3320, 1869, 'GERLI', 335),
       (3321, 1868, 'PIÑEIRO', 335),
       (3324, 1875, 'WILDE', 335),
       (3353, 8000, 'VILLA MITRE', 338),
       (3354, 8000, 'VILLA NUEVA', 338),
       (3358, 7623, 'LOS PINOS', 339),
       (3357, 7623, 'SAN AGUSTIN', 339), 
	   (3527, 2720, 'PEARSON', 357),
       (3626, 1888, 'VILLA SAN LUIS', 369);
       

       