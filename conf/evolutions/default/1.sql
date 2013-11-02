# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table submit (
  id                        bigint not null,
  language                  integer,
  source                    clob,
  problem_id                integer,
  problem_time_stamp        bigint,
  create_time               timestamp,
  finish_time               timestamp,
  status                    integer,
  detail                    varchar(255),
  constraint pk_submit primary key (id))
;

create sequence submit_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists submit;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists submit_seq;

