drop database if exists montana;
create database montana;
use montana;

create table sucursal_estado(
	codigo tinyint primary key not null auto_increment,
    nombre varchar(30) not null,
    descripcion varchar(200)
);
insert into sucursal_estado (nombre, descripcion) values 
('Activo','Esta sucursal esta en funcionamiento'),
('Desactivado','Esta sucursal ya no está en funcionamiento'),
('','Esta sucursal está pronta a empezar su funcionamiento')
;

create table sucursal_categoria(
	codigo tinyint primary key not null auto_increment,
    nombre varchar(30) not null,
    descripcion varchar(200)
);
insert into sucursal_categoria (nombre, descripcion) values 
('Tienda',''),
('Almacen','')
;

create table sucursal(
	codigo int primary key not null auto_increment,
    nombre varchar(200) not null,
    distrito varchar(100) not null,
    estado tinyint,
    foreign key (estado) references sucursal_estado(codigo)
);
insert into sucursal (nombre, distrito,estado) values 
('Montana Puruchuco','Ate',1),
('Montana Barranco','Barranco',1),
('Montana Ovalo Balta','Barranco',1),
('Montana Barranca','Barranca',1),
('Montana Breña','Breña',1),
('Montana Martinto','Breña',1),
('Montana Bellavista','Bellavista',1),
('Montana Angelica Gamarra','Callao',1),
('Montana Minka','Callao',1),
('Montana Emancipacion','Callao',1),
('Montana Colonial','Cercado de Lima',1),
('Montana Plaza Castilla','Cercado de Lima',1),
('Montana Chorillos','Chorillos',1),
('Montana Huaylas','Chorillos',1),
('Montana Alameda Sur','Chorillos',1),
('Montana Paseo Chorillos','Chorillos',1),
('Montana Pascana','Comas',1),
('Montana Comas Retablo','Comas',1),
('Montana Chaclacayo','Chaclacayo',1),
('Montana Chosica','Chosica',1),
('Montana El agustino','El agustino',1),
('Montana Garzón','Jesus María',1),
('Montana Pershing','Jesus María',1),
('Montana San Felipe','Jesus María',1),
('Montana Independencia','Independencia',1),
('Montana Plaza Norte','Independencia',1),
('Montana La Molina','La Molina',1),
('Montana Canada','La Victoria',1),
('Montana Arenales','Lince',1),
('Montana Shell','Miraflores',1),
('Montana Grimaldo del Solar','Miraflores',1),
('Montana Sucre','Pueblo Libre',1),
('Montana Ingenieria','Rimac',1),
('Montana Perricholi','Rimac',1),
('Montana Limatambo','San Borja',1),
('Montana Faucett','San Miguel',1),
('Montana La Marina','San Miguel',1),
('Montana Atocongo','San Juan de Miraflores',1),
('Montana San Juan de Miraflores','San Juan de Miraflores',1),
('Montana Miotta','San Juan de Miraflores',1),
('Montana San Juan de Lurigancho','San Juan de Lurigancho',1),
('Montana Proceres','San Juan de Lurigancho',1),
('Montana Aramburu','Surquillo',1),
('Montana Ventanilla','Ventanilla',1)
;

CREATE TABLE empleado_categoria(
	id tinyint not null primary key auto_increment,
    nombre varchar(20) not null,
    descripcion varchar(2000)
);
insert into empleado_categoria (nombre, descripcion) values 
('Root','Este usuario tendrá acceso a todas las tablas y podrá modificarlas.'),
('Gerente','Este usuario podrá visualizar las tablas de empleados y modificar los procesos de productos.'),
('Encargo del Almacen','Este usuario tendrá a cargo toda el area del almacen y sus procesos.'),
('Cliente','Este usuario solo podrá visualizar el catalogo y sus procesos.')
;
create procedure empleado_categoria_listar()
    select * from empleado_categoria
;
create procedure empleado_categoria_agregar(nombreV varchar(20), descripcionV varchar(2000))
    insert into empleado_categoria (nombre, descripcion) values(nombreV, descripcionV)
;
create procedure empleado_categoria_busquedaID(idV tinyint)
    select * from empleado_categoria where id=idV;
;
create procedure empleado_categoria_busquedaGeneral(obj varchar(100))
    select * from empleado_categoria where id=obj;
;
create procedure empleado_categoria_actualizar(idV tinyint, nombreV varchar(20), descripcionV varchar(2000))
    update empleado_categoria set nombre=nombreV, descripcion=descripcionV where id=idV
;
create procedure empleado_categoria_eliminar(idV tinyint)
    delete from empleado_categoria where id=idV;
;

CREATE TABLE empleado(
	id tinyint not null primary key auto_increment,
    nombre varchar(120) not null,
    paterno varchar(80) not null,
    materno varchar(80) not null,
    usuario varchar(15) not null,
    contrasena varchar(15) not null,
    empleado_categoria tinyint not null,
    foreign key (empleado_categoria) references empleado_categoria(id)
);empleado
insert into empleado (nombre, paterno, materno, usuario, contrasena, empleado_categoria) values
('Muebleria Montana','','','root','123',1),
('Miguel','Cruz','Guzman','guzman','123',2),
('Erick','Mozombite','Quispe','erick','123',3),
('Jose','Loyola','Loyola','loyola','123',4);
create procedure empleado_validarSesion(usuarioV varchar(15), contrasenaV varchar(15))
    select * from empleado where usuario=usuarioV and contrasena=contrasenaV
;
create procedure empleado_listar()
    select * from empleado
;
create procedure empleado_agregar(nombreV varchar(120), paternoV varchar(80), maternoV varchar(80), usuarioV varchar(15), contrasenaV varchar(15), empleado_categoriaV tinyint)
    insert into empleado (nombre, paterno, materno, usuario, contrasena, empleado_categoria) values(nombreV, paternoV, maternoV, usuarioV, contrasenaV, empleado_categoriaV)
;
create procedure empleado_busquedaID(idV tinyint)
    select * from empleado where id=idV;
;
create procedure empleado_busquedaGeneral(obj varchar(100))
    select * from empleado where id=idV;
;
create procedure empleado_actualizar(idV tinyint, nombreV varchar(120), paternoV varchar(80), maternoV varchar(80), usuarioV varchar(15), contrasenaV varchar(15), empleado_categoriaV tinyint)
    update empleado set nombre=nombreV, paterno=paternoV, materno=maternoV, usuario=usuarioV, contrasena=contrasenaV, empleado_categoria=empleado_categoriaV where id=idV
;
create procedure empleado_actualizarDatos(idV tinyint, nombreV varchar(120), paternoV varchar(80), maternoV varchar(80), empleado_categoriaV tinyint)
    update empleado set nombre=nombreV, paterno=paternoV, materno=maternoV, empleado_categoria=empleado_categoriaV where id=idV
;
create procedure empleado_actualizarCredencial(idV tinyint, usuarioV varchar(15), contrasenaV varchar(15))
    update empleado set usuarioV=usuario, contrasenaV=contrasena, empleado_categoria=empleado_categoriaV where id=idV
;
create procedure empleado_actualizarRol(idV tinyint, empleado_categoriaV tinyint)
    update empleado set empleado_categoria=empleado_categoriaV where id=idV
;
create procedure empleado_eliminar(idV tinyint)
    delete from empleado where id=idV;
;