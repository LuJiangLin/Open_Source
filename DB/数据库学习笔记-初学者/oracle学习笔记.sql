--Oracle ѧϰ�ʼ����ܽ�

--��������
lsnrctl start

--�رռ���
lsnrctl stop

--�������ݿ�
net start OracleServiceORCL--ORCLΪ���ݿ����SID

--�ر����ݿ�
net stop OracleServiceORCL--ORCLΪ���ݿ����SID

--�������������
oemctl start oms

--�رչ��������
oemctl stop oms--�ر�ʱҪ�������Ա�û���and����

--dos��½SQL*Plus
sqlplus
sqlplus scott/tiger

--window��ʽ��¼SQL*Plus
sqlplusw
sqlplusw scott/tiger
sqlplusw scott/tiger@ORCL--ORCLΪ���ݿ����SID

--�鿴sqlplus������÷�
sqlplus -?
--�˳�SQL*Plus
quit/exit

--SQL*Plus�޸�����
passw(ord)--����ʾ��������

--spool off  ����Ļ�ϵ��������뵽�Ƕ��ļ���

--�л��û�
 conn system/sa;  --sa Ϊ����

 --�鿴��ǰ�û�
 show user;

--������ӻ�ɾ�����ݿ�
dbca;

--�����µĶ���
 create user xiaoming identified by system;

--������Ȩ

grant select on emp to xiaoming with grant option;



eg:

conn system/sa;
create user mm identified by sa;
 GRANT CREATE USER,DROP USER,ALTER USER ,CREATE ANY VIEW ,    DROP ANY VIEW,EXP_FULL_DATABASE,IMP_FULL_DATABASE,       DBA,CONNECT,RESOURCE,CREATE SESSION TO mm;
����


--�����ļ����� 3�ε�½������ס  2��
create profile lock_account limit failed_login_attempts 3 password_lock_time 2;

--�û��Ѹ���
alter user xiaoming profile lock_account;

--��xiaoming����
alter user xiaoming account unlock;


--��ѧ����
CREATE TABLE student(
xh number(4),
xm VARCHAR2(20),
sex CHAR(2),
birthday DATE,
sal number(7,2)
);
--��ѯ
SELECT * from student;


--�����༶��
create table classes(
classId number(2),
cname varchar2(20)
);
--��ѯ
select * from classes;

--��ѧ�������һ��
alter table student add(classId number(2))

--�鿴ѧ����ṹ
desc student;

--�޸��еĳ���
alter table student modify (xm varchar2(21))

--�޸ı���
rename student to stu

--ɾ���ֶ�
alter table student drop column sal

--ɾ����
drop table student

--��������    ע��ʱ���ʽ�Ĳ���

insert into stu values(001,'Ѧ����','��','12-11��-2016',1234.65,12);
insert into stu values(002,'����','��','12-11��-2016',1234.65,12);
insert into stu values(003,'����','��','12-11��-2016',1234.65,12);
insert into stu values(004,'����','��','12-11��-2016',1234.65,11);
insert into stu values(005,'������','Ů','12-11��-2016',1234.65,10);
insert into stu values(006,'����','Ů','12-11��-2016',1234.65,12);
insert into stu values(007,'��˼÷','Ů','12-11��-2016',1234.65,11);
insert into stu values(008,'������','��','12-11��-2016',1234.65,11);

--�޸������ʱ������Ϊ   yyyy-mm-dd
alter session set nls_date_format='yyyy-mm-dd';

--�����µ�����
insert into stu values(008,'�Ž�','��','2016-12-12',1234.65,11);
--�������
insert into stu(xh,xm,sex,sal,classId) values(010,'�żҽ�','��',1234.65,11);
--��ֵ����
insert into stu(xh,xm,sex,sal,classId) values(010,null,'��',1234.65,11);
--����Ϊ�յĲ�ѯ
select * from stu where xm is null;

--�޸���Ϣ
update stu set xh=009 where xm='�Ž�';

--���� ����ӱ�
update stu set sal = sal*2 where sex='��';

--deleteֻɾ�����ݣ���ɾ����Ľṹ
delete from stu

--����Ļع�����
--SQL*Plusʹ��
--������ʼ��
savepoint aa;
--ɾ����Ϣ
delete from stu;
--����ع�����ʼ��
rollback to aa;
--��ѯ��Ϣ
select * from stu;



��ѯ��ʼ

--�鿴��Ľṹ
desc dept;

--��ʱ����ʾ��
set timing on;

--��ѯ
 select deptno,job from emp;
 --ȥ�ز�ѯ
 select distinct deptno,job from emp;


--������ѯ --ע��  ���ִ�Сд
select deptno,job,sal from emp where ename='SMITH';
--�������ʽ
select ename as ����,sal*12 as �깤�� from emp;
select ename ����,sal*12 �깤�� from emp;
--nvl(����,0)   �������Ϊ�� ��0 ���㣬����ԭ���ּ���
select ename ����,(sal+nvl(comm,0))*12 �깤�� from emp;

select ename,sal from emp where sal>3000;
--1982��1��1��֮����ְ��Ա��
select ename ,hiredate from emp where hiredate > '1-1��-1982';

select ename,sal from emp where sal between 2000 and 2500;

select * from emp where empno in(7369,7521,7698);

select * from emp where mgr is null;

select ename,job from emp where (sal >500 or job ='MANAGER') and ename like 'J%';

--ģ����ѯ
select ename,sal from emp where ename like 'S%'; 
select ename,sal from emp where ename like '%S%'; 
--�������ַ�ΪO����Ϣ                              ����_
select ename,sal from emp where ename like '__O%'; 

--��������
select * from emp order by sal ;
select * from emp order by sal asc;
select * from emp order by sal desc;

--��������  ���ʽ���
select * from emp order by deptno asc , sal desc;

select * from emp order by deptno asc , sal desc,hiredate desc;

--�������ԣ������滻��  
select ename,(sal+nvl(comm,0))*12 as "��н" from emp order by "��н";

select ename,(sal+nvl(comm,0))*12 as ��н from emp order by ��н;

--��ѯ������ߵ�
select * from emp where sal = (select max(sal) from emp);
--�������    ���
select ename,sal  from emp where sal in ((select max(sal) from emp),(select min(sal) from emp)) ;
select count (*) from emp;
select sum(sal) from emp;
select avg(sal) ƽ������,sum(sal+nvl(comm,0)) �����ܺ� from emp;

--����ƽ������
select * from emp where sal <(select avg(sal) from emp);
--����
select avg(sal),max(sal),deptno from emp group by deptno;
--ƽ�ֹ��ʴ���2000 ���ݲ���
select avg(sal),max(sal),deptno from emp group by deptno having avg(sal) >2000;
select avg(sal),max(sal),deptno from emp group by deptno having avg(sal) >2000 order by avg(sal);


--����ѯ  ��Ա����  ����  ����
select EMP.ename ��Ա����,emp.sal ����,dept.dname ���� from emp ,dept where EMP.DEPTNO=DEPT.DEPTNO;
select EMP.ename ��Ա����,emp.sal ����,dept.dname ����,DEPT.deptno ���ű�� from emp ,dept where EMP.DEPTNO=DEPT.DEPTNO order by DEPT.DEPTNO asc;

select DEPT.DNAME ��������,EMP.ename Ա������,EMP.sal ���� from emp,dept where EMP.DEPTNO=DEPT.DEPTNO and DEPT.DEPTNO=10;
--Ա������  ����  ����
select EMP.ename Ա������,EMP.sal ����,SALGRADE.grade ���� from emp,salgrade where EMP.sal between salgrade.losal and SALGRADE.hisal;


--������
select WORKER.ename ��Ա����,BOSS.ename �ϰ����� from emp worker,emp boss where WORKER.empno = BOSS.empno and WORKER.ename ='FORD';


--�Ӳ�ѯ
--��SMITH��ͬһ���ŵ�Ա�������ʣ����ű��
select ename,job,sal,deptno from emp where deptno = (select deptno from emp where ename = 'SMITH');

--��10�Ų�����ͬ�Ĺ�����λ��Ա����Ϣ
select * from emp where job in (select distinct job from emp where deptno =10);

--���ʴ��ڲ���30 �е�����Ա������Ϣ
select ename,sal,deptno from emp where sal>all(select sal from emp where deptno =30);
select ename,sal,deptno from emp where sal>(select max(sal) from emp where deptno =30);

--���ʴ��ڲ���30 �е�����Ա������Ϣ
select ename,sal,deptno from emp where sal>any(select sal from emp where deptno =30);
select ename,sal,deptno from emp where sal>(select min(sal) from emp where deptno =30);

--��SMITH ���ź͸�λ��ͬ��������Ϣ
select * from emp where (deptno,job)=(select deptno,job from emp where ename='SMITH');

--�����Լ����ŵ�ƽ�����ʵ�Ա����Ϣ
select * from emp ,(select deptno,avg(sal) moy from emp group by deptno) a1 where EMP.deptno = A1.deptno and EMP.sal >A1.moy;

----��ҳ    ������
--1.  rownum��ҳ (rownum    ��oracle�����)
select * from emp;
select a1.* from emp a1;
select a1.*,rownum rn from (select * from emp) a1;
select a1.*,rownum rn from (select * from emp) a1 where rownum between 1 and 5;
--ע������������ѯֻ��Ҫ�޸ģ����ڲ�����Ϣ����
select emp.*,rownum from emp  where rownum between 1 and 5;
select * from (select a1.*,rownum rn from (select * from emp) a1 where rownum <=10) where rownum <6;

--������ҳ
select a1.*,rownum rn from (select * from emp) a1;
select a1.*,rownum from (select * from emp order by sal) a1 where rownum between 1 and 5;
select a1.*,rownum from (select * from emp order by sal desc) a1 where rownum between 1 and 5;


--�ò�ѯ�������һ�ű�
create table myemp(id,ename,sal) as select empno,ename,sal from emp;
select * from myemp;
create table aa as select * from emp;
select * from aa;

--�ϲ���ѯ   union ȥ���������Ϻϲ�֮����ظ��ļ�¼����ͬ����ֻ��ʾһ�ݣ�
--union ����
select ename,sal,job from emp where sal>2500;
select ename,sal,job from emp where job = 'MANAGER';
select ename,sal,job from emp where sal>2500 union select ename,sal,job from emp where job = 'MANAGER';

--union  all   ��ȡ���ظ���
select ename,sal,job from emp where sal>2500 union all select ename,sal,job from emp where job = 'MANAGER';

--intersect ����
select ename,sal,job from emp where sal>2500 intersect select ename,sal,job from emp where job = 'MANAGER';

--minus ȡ  �-b��������a����   b-a ����minus�Ľ��
select ename,sal,job from emp where sal>2500 minus select ename,sal,job from emp where job = 'MANAGER';

--�÷�
insert into emp values(123,'����','����',1213,'12-11��-2016',123,4590,10);
insert into emp values(12,'����','����',1213,to_date('2016-12-11','yyyy-mm-dd'),123,4590,10);
insert into emp values(12,'����','����',1213,to_date('2016/12/11','yyyy/mm/dd'),123,4590,10);

--   �����ݵ���������
create table bb(
myId number(4),
myName VARCHAR2(50),
myDept number(5)
);

insert into bb(myId,myName,myDept) select empno,ename,deptno from emp where deptno =10;
select * from bb;
--ϣ��Ա��scott�Ĺ��ʡ���λ��������Smithһ��
update emp set (job,sal,comm) =  (select job,sal,comm from emp where ename = 'SMITH') where ename = 'SCOTT';


--sqlplus   ����oracle�п��Ե���native for Oracleϵ�в�Ʒ������
--����Ļع�   --ע��   ֻҪcommit֮��   ���еĻع��㶼������
select * from emp;
commit;
savepoint a1;
delete from emp where empno=12;
savepoint a2;
delete from emp where empno=7369;
rollback to a1;

--����ֻ������
set transaction read only;
 

--�ַ�����
--lower(char) ���ַ���ת��ΪСд
select lower(ename) from emp; 

--upper(char) ���ַ���ת��Ϊ��д
select upper(ename) from emp; 

--length(char)�����ַ����ĳ���   ����Ϊ5������Ա������
select ename from emp where length(ename)=5;

--substr(char,m,n)ȡ���ַ������Ӵ�
--��ʾԱ��������ǰ3���ַ�
select substr(ename,1,3) from emp;
select a1.*,rownum from (select substr(ename,1,3) from emp) a1 where rownum between 1 and 5;

--����ĸ��д  ��ʾ����
--����ĸ��д
select  upper(substr(ename,1,1)) as ���� from emp 
--����Сд
select  lower(substr(ename,2,length(ename)-1)) as ���� from emp 
--�ϲ���Сд�ַ���
select  upper(substr(ename,1,1)) || lower(substr(ename,2,length(ename)-1))  as ���� from emp 
----����ĸСд  ��ʾ����
select  lower(substr(ename,1,1)) || upper(substr(ename,2,length(ename)-1))  as ���� from emp 

--�ַ����滻   ������A-���滻 A
select replace(ename,'A','����A-') from emp;

--��ѧ����  round   ��������
select ename,(round(nvl(comm,0)+sal))*13 ���� from emp;
--����  ��λ  
select ename,(round(sal,2))*13 ���� from emp;
select ename,(round(nvl(comm,0)+sal,2)*13) ���� from emp;
--��ѧ����  trunc   ֱ�ӽ�ȡ
select ename,(trunc(nvl(comm,0)+sal))*13 ���� from emp;

--�Ƚ�
select ename,(round(nvl(comm,0)+sal,1)) ����,(nvl(comm,0)+sal) ����1 from emp;
select ename,(trunc(nvl(comm,0)+sal,1)) ����,(nvl(comm,0)+sal) ����1 from emp;

select ename,(round(nvl(comm,0)+sal,1)) ����,(nvl(comm,0)+sal) ����1,(trunc(nvl(comm,0)+sal,1)) ����2,(nvl(comm,0)+sal) ����3 from emp;


--floor(n)  <=n���������
select floor(sal),sal from emp ;

--ceil��n�� >=n����������
select ceil(sal),sal from emp ;

--ȡģ  dual  ��  �����  ����ר��
select mod(10,3) from dual;
--�����չ���   �ض�  
select ename,trunc((nvl(comm,0)+sal)/30) ���� from emp;


--���ں���
--OracleĬ�ϵ����ں�����dd-mm-yy ��12-12��-2016
--����ϵͳʱ��
select sysdate from dual;

--��ְ8�������ϵ�Ա��
select ename,hiredate,sysdate from emp where sysdate > add_months(hiredate,8);
--��ְ10������
select ename ����,hiredate �ܹ����� from emp where sysdate > add_months(hiredate,12*10);
--last_day(d)   ����ָ�����������·ݵ����һ��
--��ʾԱ�����빫˾������
select ename,sysdate - hiredate ��ְ���� from emp;
select ename,trunc(sysdate - hiredate) ��ְ���� from emp;
select ename,floor(sysdate - hiredate) ��ְ���� from emp;

--�ҳ����·�  ������3���ܹ͵�����Ա��
select ename,hiredate,last_day(hiredate) from emp where (last_day(hiredate)-hiredate)=2; 

--��������ת��   ���������ʹ�һ��װ��Ϊ��һ��
--Oracle   ������ת��   ���ֿ����Զ�ʶ��


--to_char

select hiredate ת��ǰ,to_char(hiredate) ת���� from emp;
select ename,hiredate ת��ǰ,to_char(hiredate,'yyyy') ת���� from emp;
select ename,hiredate ת��ǰ,to_char(hiredate,'yyyy-mm-dd') ת���� from emp;
select ename,hiredate ת��ǰ,to_char(hiredate,'yyyy/mm/dd') ת���� from emp;
select ename,hiredate ת��ǰ,to_char(hiredate,'yyyy-mm-dd hh24:mi:ss') ת���� from emp;
select ename,hiredate ת��ǰ,to_char(hiredate,'yyyy-mm-dd hh24:mi:ss') ת����1,to_char(hiredate,'yyyy-mm-dd') ת����2,to_char(hiredate,'yyyy/mm/dd') ת����3 from emp;

select ename,sal,to_char(sal,'L99999.99') from emp;
select ename,sal,to_char(sal,'L99,999.99') from emp;
select ename,sal,to_char(sal,'$99,999.99') from emp;
select ename,sal ����,to_char(sal,'L99999.99') ����1,to_char(sal,'L99,999.99') ����2,to_char(sal,'$99999.99') ����3,to_char(sal,'$99,999.99') ����4 from emp;

--��ʾ1980����ְ��Ա��
select * from emp where to_char(hiredate,'yyyy') =1980;
--��ʾ����12�·���ְ��Ա��
select * from emp where to_char(hiredate,'mm') =12;

--ϵͳ����   sys_context
--��ѯ��ǰ���ݿ������
select sys_context('userenv','db_name') from dual;--���ݿ�����
select sys_context('userenv','terminal') from dual;--��ǰ�û���Ӧ���ն˵ı�ʶ��
select sys_context('userenv','language') from dual;--����
select sys_context('userenv','nls_date_format') from dual;--���ڸ�ʽ
select sys_context('userenv','session_user') from dual;--��ǰ�û�
select sys_context('userenv','host') from dual;--���ݿ���������
select sys_context('userenv','current_schema') from dual;--Ĭ�Ϸ�����


--Oracle  ����Ա�Ļ���ְ��
ÿһ��Oracle���ݿⶼ������һ�����ݿ����Ա������һ��С�����ݿ⣬һ��dba�͹��ˣ�
���Ƕ���һ��������ݿ������Ҫ���dba��ͬ�ֵ���ͬ�Ĺ���Աְ��
Oracle  ����Ա�Ļ���ְ��
1.��װ������Oracle���ݿ�
2.���⡢��ռ䣬����ͼ�������ȡ�
3.�ƶ���ʵʩ������ָ��ƻ�
4.���ݿ�Ȩ�޹������ţ������ų�
5.���ڸ߼�dba��Ҫ���ܲ�����Ŀ���������дsql��䣬�洢���̣�������������Լ������...


--���ݿ���߼�������ָ�
��������Ϊ  �����������������������ݿ�  ����

--��ѯȨ��
sql*plus ���������ע�������system��½����ʾ����Ϣ�Ƚ�ȫ�棩
select * from dba_roles;
--��ѯoracle�����ж���Ȩ��
select distinct privilege from dba_tab_privs;
--��ѯ���ݿ�ı�ռ�
select tablespace_name from dba_tablespaces;
--��ѯĳ���û�ӵ�������Ľ�ɫ
select * from dba_role_privs where grantee='�û���';

--a.һ����ɫ������ϵͳȨ��
select * from dba_sys_privs where grantee='DBA';
���ߣ�
select * from role_sys_privs where role='DBA';
--b.һ����ɫ�����Ķ���Ȩ��
select * from dba_tab_privs where grantee ='��ɫ��';
select * from dba_tab_privs where grantee ='DBA';
select * from dba_tab_privs where grantee ='CONNECT';



--������ռ�  ��Ĭ�ϴ�С 20m ������ 128k
create tablespace data01 datafile 'd:\data001.dbf' size 20m uniform size 128k;

--ʹ�ñ�ռ�
create table bb(
myId number(4),
myName VARCHAR2(50),
myDept number(5)
) tablespace data01;




--���ݵ�������----Լ��
not null,unique,primary key,foreign key,check ����


--�̵��ۻ�ϵͳ������
--Ҫ��
--1.�����
--2.�ͻ���������Ϊ��
--3.���۱������0����������������1-30֮��
--4.email�����ظ�
--5.�ͻ��Ա�������л�Ů   Ĭ��Ϊ��


--��Ʒ��  ��Ʒ�ţ�goodsId������Ʒ���ƣ�goodsName�������ۣ�unitprice������Ʒ���category��,��Ӧ�̣�provider��
create table goods(
goodsId char(8) primary key,
goodsName varchar2(30),
unitprice number(10,2) check(unitprice>0),
category varchar2(8),
provider varchar2(30)
);

select * from goods;

--�ͻ���  �ͻ��ţ�customerId��,������name����סַ��address�����ʼ���email�����Ա�sex�������֤��cardId��
create table customer(
customerId char(8) primary key,
name varchar2(30) not null,
address varchar2(50),
email varchar2(50) unique,
sex char(2) default '��' check (sex in('��','Ů')),
cardId char(18)
);

select * from customer;

--����� �ͻ��ţ�customerId������Ʒ�ţ�goodsId��������������nums��
create table purchase(
customerId char(8) references customer(customerId),
goodsId char(8) references goods(goodsId),
nums number(8) check(nums between 1 and 30)
);

select * from purchase;

--����ڽ���ʱ���Ǳ�ҪԼ����������ٽ��������ʹ��alter table ����Լ����
--�������������Ϊnot nullʱ  ��Ҫʹ�� modify ѡ��,������ѡ��ʹ��add����

--�޸ģ�
--�̵��ۻ�ϵͳ������
--Ҫ��
--1.�����
--2.�ͻ���������Ϊ��  --��Ʒ���Ʋ���Ϊ��
--3.���۱������0����������������1-30֮��
--4.email�����ظ�   ���֤�����ظ�
--5.�ͻ��Ա�������л�Ů   Ĭ��Ϊ��
--6.���ӿͻ���סַֻ��Ϊ���������������ǣ����ǣ�ͨ�ݣ�����

alter table goods modify goodsName not null;
alter table customer add constraint c unique(cardId);
alter table customer add constraint ad check(address in('����','����','����','����','ͨ��','����')); 

--ɾ��Լ��
alter table ���� drop constraint Լ������;
--ɾ������Լ��
--����������ϵ
alter table ���� drop primary key;
--��������ϵ
alter table ���� drop primary key cascade;

--�м����壺�����е�ͬʱ����Լ����   �����壺������֮���ٶ���Լ��



--���������ڼ������ݴ�ȡ�Ķ��󣬺����ʹ���������Դ��Ľ���i/o������
--�Ӷ�������ݵķ������ܣ�����������������Խ��Խ�á� 
--��������
create index nameindex on customer(name);
create index emp_index on emp(ename,job);


--����һ���򵥵ı�
create table mytest(
name varchar2(30),
password varchar2(30)
);



--SQL*Plus��д
--��д�洢����
--replace   :����������滻
create or replace procedure sp_por1 is 
begin 
--ִ�в���
insert into mytest values('Ѧ����','123');
end;

--��β鿴������Ϣ
show error;

--��ε���  �ù���
1.exec ������������1������2...��
 exec sp_por1;
2.call ������������1������2...��   
 call sp_por1;

create or replace procedure sp_por1 is
  begin
 delete from mytest where name='Ѧ����';
 end;
  /
  --��ʾ��
Procedure created




--PL/SQL   ��̻���
PL/SQL ������������ɣ����岿�֡�ִ�в��֡����⴦����
daclear   --���岿��--���峣�����������αꡢ���⡢���Ӿ�����
begin      --Ҫִ�е�PL/SQL ����sql���
exception --���⴦����   �������еĸ��ִ���


--���岿���Ǵ�declear��ʼ��   ��ѡ��ִ���Ǵ�  begin��ʼ��   �����
--���ⲿ��  ��exception��ʼ��  ��ѡ��

--��򵥵Ŀ�
begin
	dbms_output.put_line('Hello World');
end;

--���������
set serveroutput on;
--�ر��������
set serveroutput off;

--����  + ִ��
declare
	v_ename varchar2(5);--�����ַ�������
begin
	select ename into v_ename from emp where empno=&no;
	dbms_output.put_line('��Ա���ƣ�'||v_ename);
end;
--���룺7369
--��ʾ����Ա���ƣ�SMITH


declare
	v_ename varchar2(5);
	v_eno number(8);
begin
	select ename,empno into v_ename,v_eno from emp where empno=&no;
	dbms_output.put_line('��Ա���ƣ�'||v_ename   ||'��Ա��ţ�'||v_eno);
end;
--���룺7369
--��ʾ����Ա���ƣ�SMITH��Ա��ţ�7369

--����  ����һ�������ڵı��    ��
--��ʾ��
ORA-01403: δ�ҵ�����
ORA-06512: �� line 5
���ʱ����Ҫ�����쳣  --no_data_found


declare
	v_ename varchar2(5);
	v_eno number(8);
begin
	select ename,empno into v_ename,v_eno from emp where empno=&no;
	dbms_output.put_line('��Ա���ƣ�'||v_ename   ||'��Ա��ţ�'||v_eno);
exception
	when no_data_found then
	dbms_output.put_line('���ı������...');
end;
--����  ����һ�������ڵı��    ��
--��ʾ��
���ı������...


--����   �������Ͳ���Ҫָ����С
create or replace procedure gc1(
	newName varchar2,
	newSal number) is
begin 
	update emp set sal = newSal where ename = newName;
end;
--���ù���   ������Ҫ����������
exec gc1('SCOTT',4688);
--����
 call gc1('SCOTT',4688);

--java������ô洢����   ��   java--MyOracle��Ŀ

--����
������ͷ���������  return
--���ӣ������Ա���������ظĹ�Ա����н
create function hs1(name varchar2) 
	return number is year_sal number(7,2);
begin
	select (sal+nvl(comm,0))*12 into year_sal from emp where ename = name;
return year_sal;
end;

--���ú���
var a number;
call hs1('SCOTT')  into:a; 



--��
--������  �淶���������̺ͺ�����˵����û�о���ʵ�ֵĴ��� ��������ʵ�ֹ淶�еĹ��̣�update_sal���ͺ�����anmual_income��
--�������� ����ʹ�� create package body ��ʵ��
create package bao1 is
	procedure update_sal(name varchar2,newsal number);
	function anmual_income(name varchar2) 
	return number;
end;
--��������
create package body bao1 is
	procedure update_sal(name varchar2,newsal number)
	is
begin
	update emp set sal = newsal where ename = name;
end;
	function anmual_income(name varchar2) 
	return number is
	anmual_sallary number;
begin
	select (sal+nvl(comm,0))*12 into anmual_sallary from emp where ename =name;
	return anmual_sallary;
end;
end;

--����bao1��SCOTT�Ĺ���
exec bao1.update_sal('SCOTT',4000);

--������
--��������һ������ִ�еĴ洢����
--�����崥������ʱ����붨�崥�����¼��ʹ����Ĳ�����update��insert��delete��


--����
--�������ͣ�scalar��   ע�������Ķ���һ����  v_    ��ͷ  ����һ����   c_    ��ͷ
--����һ���䳤�ַ���
v_ename varchar2(10);
--����һ��С��  ��Χ  -9999.99~9999.99
v_sal number(6,2);
--����һ��С�� ������һ����ʼֵΪ5.4    := Ϊ��ֵ����
c_sal number(6,2):=5.4;
--����һ����������
v_hiredate date;
--����һ��boolean��������ʼֵΪfalse
v_flag boolean not null default false;

--ʹ�ñ���
--����Ա���ţ���ʾԱ�����������ʣ���������˰��˰��Ϊ0.03��
declare
	c_tax_rate number(3,2):=0.03;
	v_name varchar2(10);
	v_sal number(7,2);
	v_tax_sal number(7,2);
begin
	select ename,sal into v_name,v_sal from emp where empno = &no;
	v_tax_sal:=v_sal*c_tax_rate;
	dbms_output.put_line('����Ϊ��'  ||v_name   ||'����Ϊ��' ||   v_sal   ||   '��˰��'  ||v_tax_sal);
end;

���룺7369
��ʾ:
����Ϊ��SMITH����Ϊ��800.32��˰��24.01

�������ʱ�ĳ���̫С   �ᱨһ��  �ַ���������̫С�Ĵ���
����취��
���������   v_name varchar2(10);  Ϊ  v_name emp.ename%type;
��ʾ  v_name �����ͺ�emp���е�ename������һ��

declare
	c_tax_rate number(3,2):=0.03;
	v_name emp.ename%type;
	v_sal emp.sal%type;
	v_tax_sal number(7,2);
begin
	select ename,sal into v_name,v_sal from emp where empno = &no;
	v_tax_sal:=v_sal*c_tax_rate;
	dbms_output.put_line('����Ϊ��'  ||v_name   ||'����Ϊ��' ||   v_sal   ||   '��˰��'  ||v_tax_sal);
end;


--�������ͣ�composite��
--����һ����¼����emp_record_type  ������������name��salary��title
--PL/SQL ��¼   �൱�ڸ߼������еĽṹ��
declare 
	type emp_record_type is record(
	name emp.ename%type,
	salary emp.sal%type,
	title emp.job%type
	);
	--����һ������a_record   ��������Ϊemp_record_type
	a_record emp_record_type;
begin
	select ename,sal,job into a_record from emp where empno = 7369;
	dbms_output.put_line('Ա������'   ||a_record.name  ||  'нˮ��'|| a_record.salary   ||  '����Ϊ��'  ||  a_record.title);
end;

��ʾ��
Ա������SMITHнˮ��800.32����Ϊ��CLERK

--PL/SQL ��   �൱�ڸ߼������е�����   �������±����Ϊ����
declare
	type table_type is table of emp.ename%type
	index by binary_Integer;
	b_table table_type;
begin
	select ename into b_table(0) from emp where empno=7369;
	dbms_output.put_line('Ա��������'    ||b_table(0));
end;
��ʾ��
Ա��������SMITH
--table_type��PL/SQL ������
--emp.ename%type��ָ����Ԫ�ص����ͺͳ���
--b_table��PL/SQL �����
--b_table(0)  �±�Ϊ0��Ԫ��


--ȥ��  where����
declare
	type table_type is table of emp.ename%type
	index by binary_Integer;
	b_table table_type;
begin
	select ename into b_table(0) from emp;
	dbms_output.put_line('Ա��������'    ||b_table(0));
end;
��ʾ��
ORA-01422: ʵ�ʷ��ص������������������
ORA-06512: �� line 6
����취��
ʹ�ò��ձ���


--�������ͣ�reference��   ���ձ�����Ϊ���α������ref cursor���Ͷ������ͱ�����ref obj-type��
--lob��large object��
--�����α��ʱ����Ҫָ����Ӧ��select��䣬������ʹ�õ�ʱ�����Ҫ
--����1.���벿��  ��ʾ�ò��ŵ�����Ա�������͹���
declare
	type x_emp_cursor is ref cursor;--�����α�����
	test_cursor x_emp_cursor;--����һ���α����
	--�������
	v_ename emp.ename%type;
	v_sal emp.sal%type;
begin
	--��test_cursor��select���
	open test_cursor for select ename,sal from emp where deptno = &no;
	--ѭ��ȡ��
	loop
		fetch test_cursor into v_ename,v_sal;
		--�ж��Ƿ�test_cursorΪ��   Ϊ���˳�
		exit when test_cursor%notfound;
		dbms_output.put_line('Ա��������'||v_ename     ||'���ʣ�' || v_sal);
	end loop;
	close test_cursor;
end;
��ʾ��
Ա��������CLARK���ʣ�2450
Ա��������KING���ʣ�5000
Ա��������MILLER���ʣ�1300.3
Ա���������������ʣ�43222
PL/SQL procedure successfully completed

--����2.��1�Ļ�����   Ա������<2000  ��+100Ԫ����
declare
	type x_emp_cursor is ref cursor;--�����α�����
	test_cursor x_emp_cursor;--����һ���α����
	--�������
	v_ename emp.ename%type;
	v_sal emp.sal%type;
begin
	--��test_cursor��select���
	open test_cursor for select ename,sal from emp where deptno = &no;
	--ѭ��ȡ��
	loop
		fetch test_cursor into v_ename,v_sal;
		if v_sal <2000 
		then update emp set sal= (sal+100) where ename= v_ename;
		--�ж��Ƿ�test_cursorΪ��   Ϊ���˳�
		exit when test_cursor%notfound;
		dbms_output.put_line('Ա��������'||v_ename     ||'���ʣ�' || v_sal);
		end if;
	end loop;
	close test_cursor;
end;





create or replace procedure gc2(
	newName varchar2
	) is
	v_sal emp.sal%type;
begin 
	select sal into v_sal from emp where ename = newName;
	if v_sal <2000 then 
	update emp set sal= sal*1.1 where ename= newName;
	end if;
end;
--��ѯ 
select ename,sal from emp where deptno =10;
��ʾ��
ENAME       SAL
---------- ---------
CLARK   2450.00
KING   5000.00
MILLER   1300.30
����    43222.00
--���ù���
call gc2('MILLER');
--��ѯ
select ename,sal from emp where deptno =10;
��ʾ��
ENAME       SAL
---------- ---------
CLARK   2450.00
KING   5000.00
MILLER   1430.33
����    43222.00

--����������֧
�����Ա���ƣ����������comm������0 ��+100  �������Ϊ0��+200
--v_comm<>0��ʾ   v_comm��=0
create or replace procedure gc3(
	newName varchar2
	) is
	v_comm emp.comm%type;
begin 
	select nvl(comm,0) into v_comm from emp where ename = newName;
	if v_comm<>0 then 
	update emp set comm= (comm+100) where ename= newName;
	else 
	update emp set comm= (comm+200) where ename= newName;
	end if;
end;

call gc2('MILLER');

--�����Ա���
--PRESIDENT  sal+1000    MANGER+500   ����ְλ+200
create or replace procedure gc4(eno number)is
	v_job emp.job%type;
begin
	select job into v_job from emp where empno = eno;
	if v_job='PRESIDENT' then 
	update emp set sal = sal+1000 where empno=eno;
	elsif v_job='MANAGER' then 
	update emp set sal = sal+500 where empno=eno;
	else
	update emp set sal = sal+200 where empno=eno;
	end if;
end;

exec gc4(7839);


--ѭ����users���10����Ϣ
create table users(
	userNo number,
	userName varchar2(40));
--if-else/when 
create or replace procedure gc5(name varchar2) is
	v_num number:=1;
	begin
	loop
		insert into users values(v_num,name);
		exit when v_num=10;
		v_num:=v_num+1;
	end loop;
end;

exec gc5('����');

select * from users;
��ʾ��
    USERNO USERNAME
---------- ----------------------------------------
         1 ����
         2 ����
         3 ����
         4 ����
         5 ����
         6 ����
         7 ����
         8 ����
         9 ����
        10 ����
10 rows selected
--while
create or replace procedure gc6(name varchar2) is
	v_num number:=11;
	begin
	while v_num<21
	loop
		insert into users values(v_num,name);
		v_num:=v_num+1;
	end loop;
end;

exec gc6('����');

select * from users;
��ʾ��
    USERNO USERNAME
---------- ----------------------------------------
         1 ����
         2 ����
         3 ����
         4 ����
         5 ����
         6 ����
         7 ����
         8 ����
         9 ����
        10 ����
        11 ����
        12 ����
        13 ����
        14 ����
        15 ����
        16 ����
        17 ����
        18 ����
        19 ����
        20 ����
20 rows selected

--for
create or replace procedure gc7(name varchar2) is
	v_num number:=21;
	begin
	for i in reverse 21..25
	loop
		insert into users values(v_num,name);
		v_num:=v_num+1;
	end loop;
end;


exec gc7('����');

select * from users;
��ʾ��
    USERNO USERNAME
---------- ----------------------------------------
        21 ����
        22 ����
        23 ����
        24 ����
        25 ����

--goto���   ������ʹ��
--goto�����ӳ���ĸ��Ӷȣ��ɶ��Ա��
--һ�㲻����ʹ��

declare
	i int:=1;
begin
	loop
	dbms_output.put_line('���i= '  || i);
	if i=10 then
	goto end_loop;
	end if;
	i:=i+1;
	end loop;
	<<end_loop>>
dbms_output.put_line('ѭ������ ');
end;

��ʾ��
���i= 1
���i= 2
���i= 3
���i= 4
���i= 5
���i= 6
���i= 7
���i= 8
���i= 9
���i= 10
ѭ������ 



declare
	i int:=1;
begin
	loop
	dbms_output.put_line('���i= '  || i);
	if i=10 then
	goto end_loop;
	end if;
	i:=i+1;
	end loop;
	dbms_output.put_line('ѭ������ ');
	<<end_loop>>
	dbms_output.put_line('ѭ������222 ');
end;

��ʾ��
���i= 1
���i= 2
���i= 3
���i= 4
���i= 5
���i= 6
���i= 7
���i= 8
���i= 9
���i= 10
ѭ������222 





DECLARE 
	x VARCHAR2(20);
BEGIN
	x:='hello World!';
	dbms_output.put_line('x��ֵ�ǣ�'||x);
end;

DECLARE 
	score number :=70;
BEGIN
	IF score>85 THEN
	dbms_output.put_line('�ɼ�����');
	ELSIF score>60 THEN
	dbms_output.put_line('�ɼ�һ��');
	ELSE 
	dbms_output.put_line('Ŭ��');
end if;
END;

DECLARE 
	g CHAR(1) :='B';
	a VARCHAR2(20);
BEGIN
	a:=
	case g
	WHEN 'A' THEN '��'
	WHEN 'B' THEN '��'
	WHEN 'C' THEN '��'
	WHEN 'D' THEN 'һ��'
	WHEN 'E' THEN '��'
	ELSE
	'û�������ĵȼ�'
END;
dbms_output.put_line('grade:'||g || '    is   ' || a);
end;


DECLARE
	t NUMBER :=1;
	n NUMBER :=1;
BEGIN
	loop
		t:=t*n;
		n:=n+1;
		exit when n=10;
	end loop;
dbms_output.put_line('1��10 �Ľ׳ˣ�'||t);
end;

--forѭ��
DECLARE
	t NUMBER :=1;
	n NUMBER :=1;
BEGIN
	for n in 1..10 loop
		t:=t*n;
		--n:=n+1;
dbms_output.put_line('n�ǣ�'||n);
		exit when n=10;
	end loop;
dbms_output.put_line('1��10 �Ľ׳ˣ�'||t);
end;

--forѭ��
DECLARE
	t NUMBER :=1;
	n NUMBER :=1;
BEGIN
	for n in reverse 1..10 loop
		t:=t*n;
		--n:=n+1;
dbms_output.put_line('n�ǣ�'||n);
		exit when n=10;
	end loop;
dbms_output.put_line('1-10 �ģ�'||t);
end;



DECLARE
	eno EMP.EMPNO%type;
BEGIN
	SELECT empno INTO eno FROM EMP WHERE EMPNO=7369;
	dbms_output.put_line('����ǣ�'||eno);
END;



DECLARE
	TYPE EMPRECOED id RECORD(
	no EMP.EMPNO%type,
	name EMP.ENAME%TYPE,
	sal EMP.SAL%TYPE
);

	oneemp EMPRECOED;
BEGIN
	SELECT empno,ename,salinto oneemp from EMP where EMPNO=7369;
	dbms_output.put_line('����ǣ�'||oneemp.no);
	dbms_output.put_line('�����ǣ�'||oneemp.name);
	dbms_output.put_line('����ǣ�'||oneemp.sal);
END;

DECLARE
	oneemp emp%rowtype;
BEGIN
	SELECT * into oneemp from EMP where EMPNO=7369;
	dbms_output.put_line('����ǣ�'||oneemp.empno);
	dbms_output.put_line('�����ǣ�'||oneemp.ename);
	dbms_output.put_line('����ǣ�'||oneemp.sal);
END;

DECLARE
	TYPE emps_type is ref cursor return emp%rowtype;
	emps emps_type;
	oneemp emp%rowtype;
BEGIN
	if NOT emps%isopen THEN
	open emps FOR SELECT * from EMP;
	END if;
	loop
	FETCH emps into oneemp;
	exit WHEN emps%notfound;
	dbms_output.put_line('����ǣ�'|| oneemp.empno );
	end loop;
	dbms_output.put_line('��������' || emps%rowcount);
	close emps;
END;


CREATE OR replace PROCEDURE VIEW_emp AS 
	TYPE emps_type is ref cursor return emp%rowtype;
	emps emps_type;
	oneemp emp%rowtype;
BEGIN
	if NOT emps%isopen THEN
	open emps FOR SELECT * from EMP;
	END if;
	loop
	FETCH emps into oneemp;
	exit WHEN emps%notfound;
	dbms_output.put_line('����ǣ�'|| oneemp.empno);
	end loop;
	dbms_output.put_line('��������' || emps%rowcount);
	close emps;
END;
-----ִ�к���exec
SET serveroutput on;
exec VIEW_emp;
---show errors  ��ʾ����



-----------
CREATE OR replace PROCEDURE check_emp(
	num in EMP.EMPNO%type,
	name out EMP.ENAME%type,
	sal out EMP.sal%TYPE
	) AS 
	TYPE emps_type is ref cursor return emp%rowtype;
	emps emps_type;
	oneemp emp%rowtype;
BEGIN
	if NOT emps%isopen THEN
	open emps FOR SELECT * from EMP WHERE EMPNO=num;
	END if;
	loop
	FETCH emps into oneemp;
	exit WHEN emps%notfound;
	--dbms_output.put_line('����ǣ�'|| oneemp.empno);
	name:=oneemp.ename;
	sal:=oneemp.sal;
	end loop;
	--dbms_output.put_line('��������' || emps%rowcount);
	close emps;
END;

DECLARE
		num  EMP.EMPNO%type :=7369;
		name EMP.ename%type;
		sal  EMP.sal%TYPE;
BEGIN
	check_emp(num,name,sal);
	dbms_output.put_line('����ǣ�'|| num || '�����ǣ�'||name||'����Ϊ��'||sal);
END;





CREATE OR REPLACE 
FUNCTION "get_name" (eno IN NUMBER)
RETURN VARCHAR2
AS
name EMP.ENAME%type;
BEGIN

	SELECT ename INTO name FROM EMP WHERE EMPNO = eno;
	RETURN name;
END;


--������

CREATE OR replace TRIGGER emp_count
after DELETE on NEWEMP
DECLARE
	coun NUMBER;
BEGIN
	SELECT count(*) into coun FROM NEWEMP;
	dbms_output.put_line('Ա�������ڻ���:'||
												coun ||'������!');
END;


DELETE from NEWEMP WHERE empno=1000;
--�½�������
CREATE table NEWDEPT AS SELECT * FROM DEPT;

CREATE OR replace trigger update_newdept
after UPDATE on newdept for each row 
BEGIN 
	UPDATE newemp SET deptno = :new.deptno
	where deptno=:old.deptno;
	dbms_output.put_line('Ա���Ĳ��ű���Ѹ��£�:');
end;
SELECT * FROM newdept;

UPDATE newdept SET deptno =10 where deptno = 60;



CREATE or replace view v_emp
AS
select * from emp;

GRANT CREATE view TO SCOTT;

SELECT * FROM v_emp;

CREATE or replace view v_emp_dept
AS
select d.dname,e.ename,e.sal from emp e,dept d where e.deptno = d.deptno;

SELECT * FROM v_emp_dept;




--��ҳ
--��д����  ͨ��java�����������
--����
create table book(
bookId number,
bookName varchar2(50),
publishHouse varchar2(50));

--��д����
--in   ��ʾ����һ���������
--out  ��ʾһ���������
create or replace procedure gc_book(
gcBookId in number,
gcBookName in varchar2,
gcpublishHouse in varchar2) is
begin
	insert into book values(gcBookId,gcBookName,gcpublishHouse);
end;
--��java�е���
�����MyOracle��Ŀcom.sc.test���µ�Gc_Insert����

���룺
package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.Ѧ
 * java�����޷���ֵ�Ĵ洢Oracle����  ��gc_book  Ϊbook�����ͼ��
 */
public class Gc_Insert {

	public static void main(String[] args) {
		Connection con = null;
//		����һ��
		try {
			con = new DBUtilora().getConnection();
			CallableStatement cs = con.prepareCall("{call GC_BOOK(?,?,?)}");
//			���� ��ֵ
			cs.setInt(1, 10);
			cs.setString(2, "Ѧ����֮Oracle���");
			cs.setString(3, "���������");
			cs.execute();
			con.commit();
			cs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}



select * from book;
��ʾ��
    BOOKID BOOKNAME                             PUBLISHHOUSE
---------- -------------------------------------------------- --------------------------------------------------
        10 Ѧ����֮Oracle���                  ���������


--��д����   �з���ֵ
--����Ա�����   ����Ա����
create or replace procedure gc_xx(
spno in number,
spName out varchar2) is
begin
	select ename into spName from emp where empno=spno;
end;

--�����MyOracle��Ŀcom.sc.test���µ�Gc_Xx����

���룺


package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.Ѧ
 * java�����з���ֵ�Ĵ洢Oracle����  ��gc_xx 
*  ����Ա�����   ����Ա����
 */
public class Gc_Xx {

	public static void main(String[] args) {
		Connection con = null;
//		����һ��
		try {
			con = new DBUtilora().getConnection();
//			��ε����з���ֵ�Ĺ���
			CallableStatement cs = con.prepareCall("{call GC_XX(?,?)}");
//			���� ��ֵ
			cs.setInt(1, 7369);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);//ȡ���ڵڶ�����������
			cs.execute();
			con.commit();
//			ȡ������ֵ
			String name = cs.getString(2);
			System.out.println("���Ϊ7369��Ա������Ϊ��"+name);
			cs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

����̨��ʾ��
���Ϊ7369��Ա������Ϊ��SMITH


--��д����   �з���ֵ
--����Ա�����   ����Ա����������,����
create or replace procedure gc_xx_all(
spno in number,
spName out varchar2,
spSal out number,
spJob out varchar2) is
begin
	select ename,sal,job into spName,spSal, spJob from emp where empno=spno;
end;

--�����MyOracle��Ŀcom.sc.test���µ�Gc_Xx_All����

���룺
package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.Ѧ
 * java�����з���ֵ�Ĵ洢Oracle����  ��gc_xx 
*  ����Ա�����   ����Ա����������,����
 */
public class Gc_Xx_All {

	public static void main(String[] args) {
		Connection con = null;
//		����һ��
		try {
			con = new DBUtilora().getConnection();
//			��ε����з���ֵ�Ĺ���
			CallableStatement cs = con.prepareCall("{call GC_XX_ALL(?,?,?,?)}");
//			���� ��ֵ
			cs.setInt(1, 7369);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);//ȡ���ڵڶ������ķ��ؽ�������
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.DOUBLE);//ȡ���ڵ��������ķ��ؽ�������
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.VARCHAR);//ȡ���ڵ��ĸ����ķ��ؽ�������
			cs.execute();
//			con.commit();
//			ȡ������ֵ
			String name = cs.getString(2);
			Double sal = cs.getDouble(3);
			String job = cs.getString(4);
			System.out.println("���Ϊ7369��Ա������Ϊ��"+name+"   ����Ϊ��"+sal+"����Ϊ��"+job);
			cs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}


����̨��ʾ��
���Ϊ7369��Ա������Ϊ��SMITH   ����Ϊ��880.35����Ϊ��CLERK


--���벿�ź�  ��������Ա����Ϣ   �����
--����һ����   �ð��ж�������test_cursor   ������Ϊһ���α�
create or replace package seeall as
type test_cursor is ref cursor;
end seeall;
--����һ���洢����
create or replace procedure see_all(
spNo in number,
sp_cursor out seeall.test_cursor) is 
begin
	open sp_cursor for select * from emp where deptno = spNo;
end;

--�����java�е���
--�����MyOracle��Ŀcom.sc.test���µ�Gc_SeeAll����
package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.Ѧ
 * java�����з���ֵ�Ĵ洢Oracle����  ��see_all    
 * ����seeall
*  ���벿�ű��   ����Ա��Ϣ
 */
public class Gc_SeeAll {

	public static void main(String[] args) {
		Connection con = null;
//		����һ��
		try {
			con = new DBUtilora().getConnection();
//			��ε����з���ֵ�Ĺ���
			CallableStatement cs = con.prepareCall("{call SEE_ALL(?,?)}");
//			���� ��ֵ
			cs.setInt(1, 10);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);//ȡ���ڵڶ������ķ��ؽ�������CURSOR   �α�����
			cs.execute();
			con.commit();
//			ȡ������ֵ����
			ResultSet rs =(ResultSet) cs.getObject(2);
			while(rs.next())
			{
				System.out.println("Ա����ţ�"+rs.getString(1)+
										"        Ա��������"+rs.getString(2)+
										"        Ա��ְ�ƣ�"+rs.getString(3)+
										"        ��˾��ţ�"+rs.getString(4)+
										"        ��ְʱ�䣺"+rs.getString(5)+
										"        Ա�����ʣ�"+rs.getString(6)+
										"        Ա������"+rs.getInt(7)+
										"        ���ڲ��ţ�"+rs.getString(8));
			}
			cs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

����̨��ʾ��
Ա����ţ�7782        Ա��������CLARK        Ա��ְ�ƣ�MANAGER        ��˾��ţ�7839        ��ְʱ�䣺1981-06-09 00:00:00.0        Ա�����ʣ�2450        Ա������0        ���ڲ��ţ�10
Ա����ţ�7839        Ա��������KING        Ա��ְ�ƣ�PRESIDENT        ��˾��ţ�null        ��ְʱ�䣺1981-11-17 00:00:00.0        Ա�����ʣ�5000        Ա������0        ���ڲ��ţ�10
Ա����ţ�7934        Ա��������MILLER        Ա��ְ�ƣ�CLERK        ��˾��ţ�7782        ��ְʱ�䣺1982-01-23 00:00:00.0        Ա�����ʣ�1430.33        Ա������0        ���ڲ��ţ�10
Ա����ţ�12        Ա������������        Ա��ְ�ƣ�����        ��˾��ţ�7782        ��ְʱ�䣺2016-09-23 00:00:00.0        Ա�����ʣ�43222        Ա������342        ���ڲ��ţ�10


--��д��ҳ�Ĺ���
--����һ����
create or replace package seeall as
type test_cursor is ref cursor;
end seeall;


--��ҳ  tableName  ����
create or replace procedure fenye(
tableName in varchar2,
Pagesize in number,--ÿҳ�ļ�¼��
Pagenow in number,
myrows out number,--�ܼ�¼��
myPageCount out number,--��ҳ��
p_cursor out seeall.test_cursor  --���صļ�¼��
) is
--����sql���  �ַ���
v_sql varchar2(1000);
--������������
v_begin number:=(Pagenow-1)*Pagesize+1;
v_end number:=Pagenow*Pagesize;
begin
	v_sql:='select a1.*,rownum rn from (select * from '||tableName||') a1 where rownum between '||v_begin||' and '||v_end;
	--dbms_output.put_line(v_sql);
	--���α�  ����sql���
	open p_cursor for v_sql;
	--����myrows ��myPageCount
	--��֯sql���
	v_sql:='select count(*) from '||tableName;
	--ִ��sql    ���ѷ��ص�ֵ  ����myrows
	execute immediate v_sql into myrows;
	--�����myPageCount
	if mod(myrows,Pagesize)=0 then
	myPageCount:=myrows/Pagesize;
	else
	myPageCount:=myrows/Pagesize+1;
	end if;
	--�ر��α�
	--close p_cursor;
end;

--sql��ҳ���
select a1.*,rownum rn from (select * from emp) a1 where rownum between 1 and 5;


��������(���ȽϷ���)
--��ҳ  tableName  ����
create or replace procedure fenye(
tableName in varchar2,
Pagesize in number,--ÿҳ�ļ�¼��
Pagenow in number,
myrows out number,--�ܼ�¼��
myPageCount out number,--��ҳ��
p_cursor out seeall.test_cursor  --���صļ�¼��
) is
--����sql���  �ַ���
v_sql varchar2(1000);
--������������
v_begin number:=(Pagenow-1)*Pagesize+1;
v_end number:=Pagenow*Pagesize;
begin
	v_sql:='select * from (select a1.*,rownum rn from (select * from '||tableName 
	||') a1 where rownum <= '||v_end||') where rn>='||v_begin;
	--���α�  ����sql���
	open p_cursor for v_sql;
	--����myrows ��myPageCount
	--��֯sql���
	v_sql:="select count(*) from '"||tableName;
	--ִ��sql    ���ѷ��ص�ֵ  ����myrows
	execute immediate v_sql into myrows;
	--�����myPageCount
	if mod(myrows,Pagesize)=0 then
	myPageCount:=myrows/Pagesize;
	else
	myPageCount:=myrows/Pagesize+1;
	end if;
	--�ر��α�
	close p_cursor;
end;	


--ʹ��java����   ����ο�    Java����

package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.Ѧ
 *  ���Է�ҳ
 */
public class Fenye {
	public static void main(String[] args) {
		Connection con = null;
//		����һ��
		try {
			con = new DBUtilora().getConnection();
			CallableStatement cs = con.prepareCall("{call FENYE(?,?,?,?,?,?)}");
//			����
			cs.setString(1, "emp");//����
			cs.setInt(2, 5);//ÿҳ��ʾ��¼��
			cs.setInt(3, 1);//�ڼ�ҳ
//			�������
			cs.setInt(4, oracle.jdbc.OracleTypes.INTEGER);//�ܼ�¼��
			cs.setInt(5, oracle.jdbc.OracleTypes.INTEGER);//��ҳ��
			cs.setInt(6, oracle.jdbc.OracleTypes.CURSOR);//�����
			cs.execute();
//			ȡ��
			int rowNum = cs.getInt(4);
			int pageCount = cs.getInt(5);
			ResultSet rs = (ResultSet)cs.getObject(6);
			while(rs.next())
			{
				System.out.println("��ţ�"+rs.getInt(1));
			}
//			��ʾ���
			System.out.println("�����ݣ�  "+rowNum);
			System.out.println("��ҳ����  "+pageCount);
		} catch (Exception e) {
		e.printStackTrace();
	}
}


}




declare 
  a number;
  b number;
  c seeall.test_cursor;
begin
  fenye('emp',5,1,a,b,c);
  dbms_output.put_line(a||'----'||b);
end;