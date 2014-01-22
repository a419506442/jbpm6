create table if not exists users(
  id bigint not null auto_increment,
  username varchar(50),
  password varchar(50),
  status integer,
  descn varchar(200),
  primary key(id)
);
create table if not exists role(
  id bigint not null auto_increment,
  name varchar(50),
  descn varchar(200),
  primary key(id)
);
create table if not exists resc(
  id bigint not null auto_increment,
  name varchar(50),
  res_type varchar(50),
  res_string varchar(200),
  descn varchar(200),
  primary key(id)
);
create table if not exists user_role(
  user_id bigint,
  role_id bigint,
  foreign key(user_id) references users(id) on delete cascade on update cascade,
  foreign key(role_id) references role(id) on delete cascade on update cascade
);
create table if not exists resc_role(
  resc_id bigint,
  role_id bigint,
  foreign key(resc_id) references resc(id) on delete cascade on update cascade,
  foreign key(role_id) references role(id) on delete cascade on update cascade
);

insert into users(username,password,status,descn) values('admin','admin',1,'管理员');
insert into users(username,password,status,descn) values('user','user',1,'用户');

insert into role(name,descn) values('ROLE_ADMIN','管理员角色');
insert into role(name,descn) values('ROLE_USER','用户角色');

insert into resc(id,name,res_type,res_string,descn) values(1,'','URL','/admin.jsp','');
insert into resc(id,name,res_type,res_string,descn) values(2,'','URL','/**','');


insert into resc_role(resc_id,role_id) values(1,1);
insert into resc_role(resc_id,role_id) values(2,1);
insert into resc_role(resc_id,role_id) values(2,2);
insert into user_role(user_id,role_id) values(1,1);
insert into user_role(user_id,role_id) values(1,2);
insert into user_role(user_id,role_id) values(2,2);
