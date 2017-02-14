-- Tabla Alumnos
create table alumnos (
  _id integer primary key autoincrement,
  nombre text not null,
  curso text not null,
  telefono text not null,
  direccion text
);
