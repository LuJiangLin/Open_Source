-- ˫�л��ߣ�ע�ͣ�����ע�ͣ���# Ҳ�ǵ���ע��
--�������ݿ�
create database mydatabase charset utf8;

--�����ؼ������ݿ�    ʧ��
create database database charset utf8;
--�����ؼ������ݿ�    �ɹ�
create database `database` charset utf8;

--�����������ݿ� ʧ��
create database �й� charset utf8;
--�����������ݿ� ʧ��
create database `�й�` charset utf8;
--�������������������  ��ǰ�������ַ�����ʲô   �ɹ�
set names gbk;
create database `�й�` charset utf8;


--�鿴�������ݿ�
show databases;
--�鿴ָ�����ֵ����ݿ�   ģ����ѯ
show databases like 'pattern';--pattern��ƥ��ģʽ
--%:��ʾƥ�����ַ�
--  _:��ʾƥ�䵥���ַ�

--�������ݿ�
create database infromationtest charset utf8;
--�鿴��infromation_��ʼ�����ݿ�: _��Ҫ��ת��
show databases like 'infromation\_%';
show databases like 'infromation_%';

--�鿴���ݿ�Ĵ������
show create database mydatabase;

--�޸ģ����£����ݿ�
--���ݿ����ֲ������޸�
--���ݵ��޸�  ���޿�ѡ��ַ�����У�Լ���У�Լ������ַ�����
alter database ���ݿ�����[��ѡ��];
charset/character set[=] �ַ���
collate У�Լ�

--�޸����ݿ�infromationtest���ַ���
alter database infromationtest charset GBK;


--ɾ�����ݿ�
drop database infromationtest;

--�������ݱ�
create table [if not exists] ����()
{
�ֶ��� ��������,
�ֶ�����   --���һ�в���Ҫ����
}[��ѡ��];

if not exists:��������ھʹ���������ִ�д��룺���
��ѡ����Ʊ�ı���
�ַ�����charset /character set �����ַ���
У�Լ���collate ����У�Լ�


--������
create table if not exists student(
name varchar(10),
gender varchar(10),
num varchar(10),
age int
)charset utf8;
--����   δ֪�����ݿ�
--����һ����ʾ��ָ��������
create table ���ݿ���.����();--����ǰ���������ݿ���
--������
create table if not exists mydatabase.student(
name varchar(10),
gender varchar(10),
num varchar(10),
age int
)charset utf8;

--������;��ʽ��ָ���������ݿ⣺�Ƚ��뵽ĳ�����ݿ⻷����
--Ȼ�������������ݿ��Զ�������ָ�������ݿ�
--�������ݿ⣺use ���ݿ�����;
use mydatabase;
create table if not exists class(
name varchar(10),
room varchar(10)
)charset utf8;


--�鿴���ݱ�  ���б�
show tables;
--�鿴���ֱ� ��s��β��
show tables like '%s';

--�鿴�������
show create table student;
show create table student\g  --\g�൱�ڷֺ�
show create table student\G   --  ����ת90��
desc/describe/show columns from ����
desc class;
describe class;
show columns from class;



--�޸����ݱ�

--�޸ı���,,,�޸��ַ���

--�޸ı���
--�޸ı���   
--������   student��-> my_student
--rename �ϱ��� to �±���
rename table student to my_student;

--��student����һ��id�ŵ���һ��λ��
alter table student
add column id int
first;


--�޸��ֶ�  alter table modify �ֶ���
--��ѧ�����е�ѧ�ű�ɹ̶����ȣ��ŵ�����Ϊ
alter table student
modify num char(10) after id;

alter table student 
change num number varchar(10);

alter table infromationtest drop number;


MySQL�﷨��ȫ_�Լ������ѧϰ�ʼ�


select * from emp;  #ע��
----------------------------
------����������MySql---------

--����mysql������
net start mysql

--�ر�   
net stop mysql  

---����
mysql -h ������ַ -u �û��� ��p �û����� 

--�˳�
exit

-----------------------------
------MySql�û�����---------

--�޸�����:������DOS �½���mysql��װ·����binĿ¼�£�Ȼ�������������:
mysqladmin -uroot -p123 password 456;

--�����û�
--��ʽ:grant Ȩ�� on ���ݿ�.* to �û���@��¼���� identified by '����'
/*
�磬����һ���û�user1����Ϊpassword1����������ڱ����ϵ�¼�� �����������ݿ��в�ѯ�����롢�޸ġ�ɾ����Ȩ�ޡ���������root�û�����mysql��Ȼ������������ 
grant select,insert,update,delete on *.* to user1@localhost Identified by "password1"; 
���ϣ�����û��ܹ����κλ����ϵ�½mysql����localhost��Ϊ"%"�� 
����㲻��user1�����룬�����ٴ�һ���������ȥ���� 
grant select,insert,update,delete on mydb.* to user1@localhost identified by ""; 
*/

grant all privileges on wpj1105.* to sunxiao@localhost identified by '123';   #all privileges ����Ȩ��

----------------------------
-------MySql���ݿ��������-----

--��ʾ���ݿ�
show databases;

--�ж��Ƿ�������ݿ�wpj1105,�еĻ���ɾ��
drop database if exists wpj1105;

--�������ݿ�
create database wpj1105;

--ɾ�����ݿ�
drop database wpj1105;

--ʹ�ø����ݿ�
use wpj1105;

--��ʾ���ݿ��еı�
show tables;

--���жϱ��Ƿ����,������ɾ��
drop table if exists student;

--������
create table student(
id int auto_increment primary key,
name varchar(50),
sex varchar(20),
date varchar(50),
content varchar(100)
)default charset=utf8;

--ɾ����
drop table student;

--�鿴��Ľṹ
describe student;  --���Լ�дΪdesc student;

--��������
insert into student values(null,'aa','��','1988-10-2','......');
insert into student values(null,'bb','Ů','1889-03-6','......');
insert into student values(null,'cc','��','1889-08-8','......');
insert into student values(null,'dd','Ů','1889-12-8','......');
insert into student values(null,'ee','Ů','1889-09-6','......');
insert into student values(null,'ff','null','1889-09-6','......');
insert into student values(null,'����','��','1988-10-2','......');
insert into student values(null,'����','��','1889-03-6','......');
insert into student values(null,'����','��','1889-08-8','......');
insert into student values(null,'����','��','1889-12-8','......');
insert into student values(null,'�','��','1889-09-6','......');
--��ѯ���е�����
select * from student;
select id,name from student;

--���ݿ���в�������������������
--�鿴������s���⴦����ַ���
show  variables like 'character_set%';
--   ����취    �ı��������Ĭ�Ͻ����ַ���ΪGBK
set character_set_client = gbk;

--���ݿ�������������룬����취
set character_set_results= gbk;


--web������������
--���������


--�޸�ĳһ������
update student set sex='��' where id=4;

--ɾ������
delete from student where id=5;

-- and ��
select * from student where date>'1988-1-2' and date<'1988-12-1';

-- or ��
select * from student where date<'1988-11-2' or date>'1988-12-1';
   
--between
select * from student where date between '1988-1-2' and '1988-12-1';

--in ��ѯ�ƶ������ڵ�����
select * from student where id in (1,3,5);

--���� asc ����  desc ����
select * from student order by id asc;

--�����ѯ #�ۺϺ��� 
select max(id),name,sex from student group by sex;

select min(date) from student;

select avg(id) as '��ƽ��' from student;

select count(*) from student;   --ͳ�Ʊ�������

select count(sex) from student;   --ͳ�Ʊ����Ա�����  ����һ��������sexΪ�յĻ�,�Ͳ�����ͳ��~

select sum(id) from student;

--��ѯ��i���Ժ󵽵�j��������(��������i��)
select * from student limit 2,5;  --��ʾ3-5������  ����������ݣ��ӵڶ�����ʼ�� 
select * from student limit 5; 

--������ϰ
create table c(
 id int primary key auto_increment,
 name varchar(10) not null,
 sex varchar(50) , --DEFAULT '��' ,
 age int unsigned, --����Ϊ��ֵ(��Ϊ��ֵ ��Ĭ��Ϊ0)
 sno int unique    --�����ظ�
);

drop table c;
desc c;

insert into c (id,name,sex,age,sno) values (null,'�θ�','��',68,1);
insert into c (id,name,sex,age,sno) values (null,'aa','��',68,2);
insert into c (id,name,sex,age,sno) values (null,'ƽƽ','��',35,3);
...

select * from c;

#�޸����� 
update c set age=66 where id=2;
update c set name='����',age=21,sex='Ů' where id=2
delete from c where age=21;

--���ò�ѯ���
select name,age ,id from c
select * from c where age>40 and age<60;  ---and
select * from c where age<40 or age<60;  --or
select * from c where age between 40 and 60 ---between
select * from c where age in (30,48,68,99);    --in ��ѯָ�������ڵ�����
select * from c order by age desc;      --order by ��asc���� des����

--�����ѯ
select name,max(age) from c group by sex; --���Ա������������ֵ
--�ۺϺ���
select min(age) from c;
select avg(age) as 'ƽ������ ' from c;
select count(*) from c;  --ͳ�Ʊ�����������
select sum(age) from c;

--�޸ı������
--��ʽ:alter table tbl_name rename to new_name
alter table c rename to a;
 
--��ṹ�޸�
create table test
(
id int not null auto_increment primary key, --�趨����
name varchar(20) not null default 'NoName', --�趨Ĭ��ֵ
department_id int not null,
position_id int not null,
unique (department_id,position_id) --�趨Ψһֵ
);

--�޸ı������
---��ʽ:alter table tbl_name rename to new_name
alter table test rename to test_rename;

--���������һ���ֶ�(��)
--��ʽ:alter table tablename add columnname type;/alter table tablename add(columnname type);
alter table test add  columnname varchar(20);

--�޸ı���ĳ���ֶε�����
alter table tablename change columnname newcolumnname type;  #�޸�һ������ֶ���
alter table test change name uname varchar(50);

select * from test;

--��position ������test
alter table position add(test char(10));
--��position �޸���test
alter table position modify test char(20) not null;
--��position �޸���test Ĭ��ֵ
alter table position alter test set default 'system';
--��position ȥ��test Ĭ��ֵ
alter table position alter test drop default;
--��position ȥ����test
alter table position drop column test;
--��depart_pos ɾ������
alter table depart_pos drop primary key;
--��depart_pos ��������
alter table depart_pos add primary key PK_depart_pos
(department_id,position_id);

--���ı���ʽ������װ�����ݿ���У�����D:/mysql.txt��
load data local infile "D:/mysql.txt" into table MYTABLE;

--����.sql�ļ��������D:/mysql.sql��
source d:/mysql.sql;  --����  /. d:/mysql.sql;