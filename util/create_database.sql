drop database if exists `Acme-L3-23.1`;
create database `Acme-L3-23.4.1`
	character set = 'utf8mb4'
  	collate = 'utf8mb4_unicode_ci';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-L3-23.4.1`.* to 'acme-user'@'%';

drop database if exists `Acme-L3-23.1-Test`;
create database `Acme-L3-23.4.1-Test`
	character set = 'utf8mb4'
  	collate = 'utf8mb4_unicode_ci';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-L3-23.4.1-Test`.* to 'acme-user'@'%';