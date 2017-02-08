create table alumnos (
    _id integer primary key autoincrement, 
    avatar text,
    nombre text not null,
    curso text not null,
    telefono text not null,
    direccion text
);
