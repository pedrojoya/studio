-- Students table
create table students (
  _id integer primary key autoincrement,
  name text not null unique,
  grade text not null,
  phone text not null,
  address text
);

insert into students values (
  0, "Baldomero", "2º CFGS DAM", "666666666","La casa de Baldo"
);