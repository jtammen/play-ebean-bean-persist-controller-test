# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table another_entity (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_another_entity primary key (id))
;

create table child (
  id                        bigint not null,
  name                      varchar(255),
  parent_id                 bigint not null,
  constraint pk_child primary key (id))
;

create table dependent_entitiy (
  id                        bigint not null,
  name                      varchar(255),
  parent_id                 bigint not null,
  constraint pk_dependent_entitiy primary key (id))
;

create table parent (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_parent primary key (id))
;

create sequence another_entity_seq;

create sequence child_seq;

create sequence dependent_entitiy_seq;

create sequence parent_seq;

alter table child add constraint fk_child_parent_1 foreign key (parent_id) references parent (id) on delete restrict on update restrict;
create index ix_child_parent_1 on child (parent_id);
alter table dependent_entitiy add constraint fk_dependent_entitiy_parent_2 foreign key (parent_id) references another_entity (id) on delete restrict on update restrict;
create index ix_dependent_entitiy_parent_2 on dependent_entitiy (parent_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists another_entity;

drop table if exists child;

drop table if exists dependent_entitiy;

drop table if exists parent;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists another_entity_seq;

drop sequence if exists child_seq;

drop sequence if exists dependent_entitiy_seq;

drop sequence if exists parent_seq;

