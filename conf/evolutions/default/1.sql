# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table a (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_a primary key (id))
;

create table b (
  id                        bigint not null,
  name                      varchar(255),
  parent_id                 bigint not null,
  constraint pk_b primary key (id))
;

create table c (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_c primary key (id))
;

create table d (
  id                        bigint not null,
  name                      varchar(255),
  c_id                      bigint not null,
  constraint pk_d primary key (id))
;

create sequence a_seq;

create sequence b_seq;

create sequence c_seq;

create sequence d_seq;

alter table b add constraint fk_b_parent_1 foreign key (parent_id) references a (id) on delete restrict on update restrict;
create index ix_b_parent_1 on b (parent_id);
alter table d add constraint fk_d_c_2 foreign key (c_id) references c (id) on delete restrict on update restrict;
create index ix_d_c_2 on d (c_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists a;

drop table if exists b;

drop table if exists c;

drop table if exists d;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists a_seq;

drop sequence if exists b_seq;

drop sequence if exists c_seq;

drop sequence if exists d_seq;

