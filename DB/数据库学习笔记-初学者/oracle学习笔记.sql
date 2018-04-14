--Oracle 学习笔记与总结

--启动监听
lsnrctl start

--关闭监听
lsnrctl stop

--启动数据库
net start OracleServiceORCL--ORCL为数据库口令SID

--关闭数据库
net stop OracleServiceORCL--ORCL为数据库口令SID

--启动管理服务器
oemctl start oms

--关闭管理服务器
oemctl stop oms--关闭时要输入管理员用户名and密码

--dos登陆SQL*Plus
sqlplus
sqlplus scott/tiger

--window方式登录SQL*Plus
sqlplusw
sqlplusw scott/tiger
sqlplusw scott/tiger@ORCL--ORCL为数据库口令SID

--查看sqlplus命令的用法
sqlplus -?
--退出SQL*Plus
quit/exit

--SQL*Plus修改密码
passw(ord)--按提示操作即可

--spool off  将屏幕上的内容输入到是定文件、

--切换用户
 conn system/sa;  --sa 为密码

 --查看当前用户
 show user;

--命令添加或删除数据库
dbca;

--创建新的对象
 create user xiaoming identified by system;

--对象授权

grant select on emp to xiaoming with grant option;



eg:

conn system/sa;
create user mm identified by sa;
 GRANT CREATE USER,DROP USER,ALTER USER ,CREATE ANY VIEW ,    DROP ANY VIEW,EXP_FULL_DATABASE,IMP_FULL_DATABASE,       DBA,CONNECT,RESOURCE,CREATE SESSION TO mm;
即可


--创建文件控制 3次登陆错误锁住  2天
create profile lock_account limit failed_login_attempts 3 password_lock_time 2;

--用户已更改
alter user xiaoming profile lock_account;

--给xiaoming解锁
alter user xiaoming account unlock;


--建学生表
CREATE TABLE student(
xh number(4),
xm VARCHAR2(20),
sex CHAR(2),
birthday DATE,
sal number(7,2)
);
--查询
SELECT * from student;


--创建班级表
create table classes(
classId number(2),
cname varchar2(20)
);
--查询
select * from classes;

--给学生表添加一列
alter table student add(classId number(2))

--查看学生表结构
desc student;

--修改列的长度
alter table student modify (xm varchar2(21))

--修改表名
rename student to stu

--删除字段
alter table student drop column sal

--删除表
drop table student

--插入数据    注意时间格式的插入

insert into stu values(001,'薛俊鹏','男','12-11月-2016',1234.65,12);
insert into stu values(002,'张三','男','12-11月-2016',1234.65,12);
insert into stu values(003,'李四','男','12-11月-2016',1234.65,12);
insert into stu values(004,'王五','男','12-11月-2016',1234.65,11);
insert into stu values(005,'张娜拉','女','12-11月-2016',1234.65,10);
insert into stu values(006,'李娜','女','12-11月-2016',1234.65,12);
insert into stu values(007,'蒋思梅','女','12-11月-2016',1234.65,11);
insert into stu values(008,'冬不拉','男','12-11月-2016',1234.65,11);

--修改输入的时间类型为   yyyy-mm-dd
alter session set nls_date_format='yyyy-mm-dd';

--插入新的数据
insert into stu values(008,'张杰','男','2016-12-12',1234.65,11);
--特殊插入
insert into stu(xh,xm,sex,sal,classId) values(010,'张家界','男',1234.65,11);
--空值插入
insert into stu(xh,xm,sex,sal,classId) values(010,null,'男',1234.65,11);
--姓名为空的查询
select * from stu where xm is null;

--修改信息
update stu set xh=009 where xm='张杰';

--男生 奖金加倍
update stu set sal = sal*2 where sex='男';

--delete只删除数据，不删除表的结构
delete from stu

--事务的回滚处理
--SQL*Plus使用
--创建初始点
savepoint aa;
--删除信息
delete from stu;
--事务回滚到初始点
rollback to aa;
--查询信息
select * from stu;



查询开始

--查看表的结构
desc dept;

--打开时间显示器
set timing on;

--查询
 select deptno,job from emp;
 --去重查询
 select distinct deptno,job from emp;


--条件查询 --注意  区分大小写
select deptno,job,sal from emp where ename='SMITH';
--算术表达式
select ename as 姓名,sal*12 as 年工资 from emp;
select ename 姓名,sal*12 年工资 from emp;
--nvl(奖金,0)   如果奖金为空 则按0 计算，否则按原数字计算
select ename 姓名,(sal+nvl(comm,0))*12 年工资 from emp;

select ename,sal from emp where sal>3000;
--1982年1月1日之后入职的员工
select ename ,hiredate from emp where hiredate > '1-1月-1982';

select ename,sal from emp where sal between 2000 and 2500;

select * from emp where empno in(7369,7521,7698);

select * from emp where mgr is null;

select ename,job from emp where (sal >500 or job ='MANAGER') and ename like 'J%';

--模糊查询
select ename,sal from emp where ename like 'S%'; 
select ename,sal from emp where ename like '%S%'; 
--第三个字符为O的信息                              两个_
select ename,sal from emp where ename like '__O%'; 

--分组排序
select * from emp order by sal ;
select * from emp order by sal asc;
select * from emp order by sal desc;

--部门升序  工资降序
select * from emp order by deptno asc , sal desc;

select * from emp order by deptno asc , sal desc,hiredate desc;

--新增属性（更改替换）  
select ename,(sal+nvl(comm,0))*12 as "年薪" from emp order by "年薪";

select ename,(sal+nvl(comm,0))*12 as 年薪 from emp order by 年薪;

--查询工资最高的
select * from emp where sal = (select max(sal) from emp);
--工资最高    最低
select ename,sal  from emp where sal in ((select max(sal) from emp),(select min(sal) from emp)) ;
select count (*) from emp;
select sum(sal) from emp;
select avg(sal) 平均工资,sum(sal+nvl(comm,0)) 工资总和 from emp;

--低与平均工资
select * from emp where sal <(select avg(sal) from emp);
--分组
select avg(sal),max(sal),deptno from emp group by deptno;
--平局工资大于2000 根据部门
select avg(sal),max(sal),deptno from emp group by deptno having avg(sal) >2000;
select avg(sal),max(sal),deptno from emp group by deptno having avg(sal) >2000 order by avg(sal);


--多表查询  雇员名称  工资  部门
select EMP.ename 雇员名称,emp.sal 工资,dept.dname 部门 from emp ,dept where EMP.DEPTNO=DEPT.DEPTNO;
select EMP.ename 雇员名称,emp.sal 工资,dept.dname 部门,DEPT.deptno 部门编号 from emp ,dept where EMP.DEPTNO=DEPT.DEPTNO order by DEPT.DEPTNO asc;

select DEPT.DNAME 部门名称,EMP.ename 员工姓名,EMP.sal 工资 from emp,dept where EMP.DEPTNO=DEPT.DEPTNO and DEPT.DEPTNO=10;
--员工姓名  工资  级别
select EMP.ename 员工姓名,EMP.sal 工资,SALGRADE.grade 级别 from emp,salgrade where EMP.sal between salgrade.losal and SALGRADE.hisal;


--自连接
select WORKER.ename 雇员名称,BOSS.ename 老板名称 from emp worker,emp boss where WORKER.empno = BOSS.empno and WORKER.ename ='FORD';


--子查询
--和SMITH在同一部门的员工，工资，部门编号
select ename,job,sal,deptno from emp where deptno = (select deptno from emp where ename = 'SMITH');

--和10号部门相同的工作岗位的员工信息
select * from emp where job in (select distinct job from emp where deptno =10);

--工资大于部门30 中的所有员工的信息
select ename,sal,deptno from emp where sal>all(select sal from emp where deptno =30);
select ename,sal,deptno from emp where sal>(select max(sal) from emp where deptno =30);

--工资大于部门30 中的任意员工的信息
select ename,sal,deptno from emp where sal>any(select sal from emp where deptno =30);
select ename,sal,deptno from emp where sal>(select min(sal) from emp where deptno =30);

--和SMITH 部门和岗位相同的所有信息
select * from emp where (deptno,job)=(select deptno,job from emp where ename='SMITH');

--高于自己部门的平均工资的员工信息
select * from emp ,(select deptno,avg(sal) moy from emp group by deptno) a1 where EMP.deptno = A1.deptno and EMP.sal >A1.moy;

----分页    有三种
--1.  rownum分页 (rownum    是oracle分配的)
select * from emp;
select a1.* from emp a1;
select a1.*,rownum rn from (select * from emp) a1;
select a1.*,rownum rn from (select * from emp) a1 where rownum between 1 and 5;
--注：所有条件查询只需要修改（）内部的信息即可
select emp.*,rownum from emp  where rownum between 1 and 5;
select * from (select a1.*,rownum rn from (select * from emp) a1 where rownum <=10) where rownum <6;

--条件分页
select a1.*,rownum rn from (select * from emp) a1;
select a1.*,rownum from (select * from emp order by sal) a1 where rownum between 1 and 5;
select a1.*,rownum from (select * from emp order by sal desc) a1 where rownum between 1 and 5;


--用查询结果创建一张表
create table myemp(id,ename,sal) as select empno,ename,sal from emp;
select * from myemp;
create table aa as select * from emp;
select * from aa;

--合并查询   union 去掉两个集合合并之后多重复的记录（相同数据只显示一份）
--union 并集
select ename,sal,job from emp where sal>2500;
select ename,sal,job from emp where job = 'MANAGER';
select ename,sal,job from emp where sal>2500 union select ename,sal,job from emp where job = 'MANAGER';

--union  all   不取消重复行
select ename,sal,job from emp where sal>2500 union all select ename,sal,job from emp where job = 'MANAGER';

--intersect 交集
select ename,sal,job from emp where sal>2500 intersect select ename,sal,job from emp where job = 'MANAGER';

--minus 取  差集-b集合属于a集合   b-a 就是minus的结果
select ename,sal,job from emp where sal>2500 minus select ename,sal,job from emp where job = 'MANAGER';

--用法
insert into emp values(123,'张三','服务',1213,'12-11月-2016',123,4590,10);
insert into emp values(12,'张三','服务',1213,to_date('2016-12-11','yyyy-mm-dd'),123,4590,10);
insert into emp values(12,'张三','服务',1213,to_date('2016/12/11','yyyy/mm/dd'),123,4590,10);

--   表数据的条件复制
create table bb(
myId number(4),
myName VARCHAR2(50),
myDept number(5)
);

insert into bb(myId,myName,myDept) select empno,ename,deptno from emp where deptno =10;
select * from bb;
--希望员工scott的工资、岗位、补助与Smith一样
update emp set (job,sal,comm) =  (select job,sal,comm from emp where ename = 'SMITH') where ename = 'SCOTT';


--sqlplus   或者oracle中可以但是native for Oracle系列产品不可以
--事务的回滚   --注意   只要commit之后   所有的回滚点都作废了
select * from emp;
commit;
savepoint a1;
delete from emp where empno=12;
savepoint a2;
delete from emp where empno=7369;
rollback to a1;

--设置只读事务
set transaction read only;
 

--字符函数
--lower(char) 将字符串转化为小写
select lower(ename) from emp; 

--upper(char) 将字符串转化为大写
select upper(ename) from emp; 

--length(char)返回字符串的长度   长度为5的所有员工姓名
select ename from emp where length(ename)=5;

--substr(char,m,n)取出字符串的子串
--显示员工姓名的前3个字符
select substr(ename,1,3) from emp;
select a1.*,rownum from (select substr(ename,1,3) from emp) a1 where rownum between 1 and 5;

--首字母大写  显示姓名
--首字母大写
select  upper(substr(ename,1,1)) as 姓名 from emp 
--其他小写
select  lower(substr(ename,2,length(ename)-1)) as 姓名 from emp 
--合并大小写字符串
select  upper(substr(ename,1,1)) || lower(substr(ename,2,length(ename)-1))  as 姓名 from emp 
----首字母小写  显示姓名
select  lower(substr(ename,1,1)) || upper(substr(ename,2,length(ename)-1))  as 姓名 from emp 

--字符串替换   ‘我是A-’替换 A
select replace(ename,'A','我是A-') from emp;

--数学函数  round   四舍五入
select ename,(round(nvl(comm,0)+sal))*13 工资 from emp;
--保留  两位  
select ename,(round(sal,2))*13 工资 from emp;
select ename,(round(nvl(comm,0)+sal,2)*13) 工资 from emp;
--数学函数  trunc   直接截取
select ename,(trunc(nvl(comm,0)+sal))*13 工资 from emp;

--比较
select ename,(round(nvl(comm,0)+sal,1)) 工资,(nvl(comm,0)+sal) 工资1 from emp;
select ename,(trunc(nvl(comm,0)+sal,1)) 工资,(nvl(comm,0)+sal) 工资1 from emp;

select ename,(round(nvl(comm,0)+sal,1)) 工资,(nvl(comm,0)+sal) 工资1,(trunc(nvl(comm,0)+sal,1)) 工资2,(nvl(comm,0)+sal) 工资3 from emp;


--floor(n)  <=n的最大整数
select floor(sal),sal from emp ;

--ceil（n） >=n的最大的整数
select ceil(sal),sal from emp ;

--取模  dual  表  虚拟表  测试专用
select mod(10,3) from dual;
--计算日工资   截断  
select ename,trunc((nvl(comm,0)+sal)/30) 工资 from emp;


--日期函数
--Oracle默认的日期函数：dd-mm-yy 即12-12月-2016
--返回系统时间
select sysdate from dual;

--入职8个月以上的员工
select ename,hiredate,sysdate from emp where sysdate > add_months(hiredate,8);
--入职10年以上
select ename 姓名,hiredate 受雇日期 from emp where sysdate > add_months(hiredate,12*10);
--last_day(d)   返回指定日期所在月份的最后一天
--显示员工加入公司的天数
select ename,sysdate - hiredate 入职天数 from emp;
select ename,trunc(sysdate - hiredate) 入职天数 from emp;
select ename,floor(sysdate - hiredate) 入职天数 from emp;

--找出各月份  倒数第3天受雇的所有员工
select ename,hiredate,last_day(hiredate) from emp where (last_day(hiredate)-hiredate)=2; 

--数据类型转换   将数据类型从一种装换为另一种
--Oracle   会隐形转换   部分可以自动识别


--to_char

select hiredate 转换前,to_char(hiredate) 转换后 from emp;
select ename,hiredate 转换前,to_char(hiredate,'yyyy') 转换后 from emp;
select ename,hiredate 转换前,to_char(hiredate,'yyyy-mm-dd') 转换后 from emp;
select ename,hiredate 转换前,to_char(hiredate,'yyyy/mm/dd') 转换后 from emp;
select ename,hiredate 转换前,to_char(hiredate,'yyyy-mm-dd hh24:mi:ss') 转换后 from emp;
select ename,hiredate 转换前,to_char(hiredate,'yyyy-mm-dd hh24:mi:ss') 转换后1,to_char(hiredate,'yyyy-mm-dd') 转换后2,to_char(hiredate,'yyyy/mm/dd') 转换后3 from emp;

select ename,sal,to_char(sal,'L99999.99') from emp;
select ename,sal,to_char(sal,'L99,999.99') from emp;
select ename,sal,to_char(sal,'$99,999.99') from emp;
select ename,sal 工资,to_char(sal,'L99999.99') 工资1,to_char(sal,'L99,999.99') 工资2,to_char(sal,'$99999.99') 工资3,to_char(sal,'$99,999.99') 工资4 from emp;

--显示1980年入职的员工
select * from emp where to_char(hiredate,'yyyy') =1980;
--显示所有12月份入职的员工
select * from emp where to_char(hiredate,'mm') =12;

--系统函数   sys_context
--查询当前数据库的名字
select sys_context('userenv','db_name') from dual;--数据库名称
select sys_context('userenv','terminal') from dual;--当前用户对应的终端的标识符
select sys_context('userenv','language') from dual;--语言
select sys_context('userenv','nls_date_format') from dual;--日期格式
select sys_context('userenv','session_user') from dual;--当前用户
select sys_context('userenv','host') from dual;--数据库主机名称
select sys_context('userenv','current_schema') from dual;--默认方案名


--Oracle  管理员的基本职责
每一个Oracle数据库都至少有一个数据库管理员，对于一个小的数据库，一个dba就够了，
但是对于一个大的数据库可能需要多个dba共同分担不同的管理员职责。
Oracle  管理员的基本职责：
1.安装和升级Oracle数据库
2.建库、表空间，表，视图，索引等。
3.制订并实施备份与恢复计划
4.数据库权限管理，调优，故障排除
5.对于高级dba，要求能参与项目开发，会编写sql语句，存储过程，触发器，规则，约束，包...


--数据库的逻辑备份与恢复
导出：分为  导出表、导出方案、导出数据库  三种

--查询权限
sql*plus 输入命令：（注：最好是system登陆，显示的信息比较全面）
select * from dba_roles;
--查询oracle中所有对象权限
select distinct privilege from dba_tab_privs;
--查询数据库的表空间
select tablespace_name from dba_tablespaces;
--查询某个用户拥有怎样的角色
select * from dba_role_privs where grantee='用户名';

--a.一个角色包含的系统权限
select * from dba_sys_privs where grantee='DBA';
或者：
select * from role_sys_privs where role='DBA';
--b.一个角色包含的对象权限
select * from dba_tab_privs where grantee ='角色名';
select * from dba_tab_privs where grantee ='DBA';
select * from dba_tab_privs where grantee ='CONNECT';



--建立表空间  ，默认大小 20m 自增长 128k
create tablespace data01 datafile 'd:\data001.dbf' size 20m uniform size 128k;

--使用表空间
create table bb(
myId number(4),
myName VARCHAR2(50),
myDept number(5)
) tablespace data01;




--数据的完整性----约束
not null,unique,primary key,foreign key,check 五种


--商店售货系统表的设计
--要求
--1.主外键
--2.客户姓名不能为空
--3.单价必须大于0，购买数量必须在1-30之间
--4.email不能重复
--5.客户性别必须是男或女   默认为男


--商品表  商品号（goodsId），商品名称（goodsName），单价（unitprice），商品类别（category）,供应商（provider）
create table goods(
goodsId char(8) primary key,
goodsName varchar2(30),
unitprice number(10,2) check(unitprice>0),
category varchar2(8),
provider varchar2(30)
);

select * from goods;

--客户表  客户号（customerId）,姓名（name），住址（address），邮件（email），性别（sex），身份证（cardId）
create table customer(
customerId char(8) primary key,
name varchar2(30) not null,
address varchar2(50),
email varchar2(50) unique,
sex char(2) default '男' check (sex in('男','女')),
cardId char(18)
);

select * from customer;

--购物表 客户号（customerId），商品号（goodsId），购买数量（nums）
create table purchase(
customerId char(8) references customer(customerId),
goodsId char(8) references goods(goodsId),
nums number(8) check(nums between 1 and 30)
);

select * from purchase;

--如果在建表时忘记必要约束，则可以再建立建表后使用alter table 增加约束，
--但是如果列属性为not null时  需要使用 modify 选项,其他的选项使用add即可

--修改：
--商店售货系统表的设计
--要求
--1.主外键
--2.客户姓名不能为空  --商品名称不能为空
--3.单价必须大于0，购买数量必须在1-30之间
--4.email不能重复   身份证不能重复
--5.客户性别必须是男或女   默认为男
--6.增加客户的住址只能为：海淀，朝阳，东城，西城，通州，崇文

alter table goods modify goodsName not null;
alter table customer add constraint c unique(cardId);
alter table customer add constraint ad check(address in('海淀','朝阳','东城','西城','通州','崇文')); 

--删除约束
alter table 表名 drop constraint 约束名称;
--删除主键约束
--不存在外间关系
alter table 表名 drop primary key;
--存在外间关系
alter table 表名 drop primary key cascade;

--列级定义：定义列的同时定义约束，   表级定义：定义列之后再定义约束



--索引是用于加速数据存取的对象，合理的使用索引可以大大的降低i/o次数，
--从而提高数据的访问性能，并不是索引建立的越多越好。 
--创建索引
create index nameindex on customer(name);
create index emp_index on emp(ename,job);


--创建一个简单的表
create table mytest(
name varchar2(30),
password varchar2(30)
);



--SQL*Plus编写
--编写存储过程
--replace   :如果存在则替换
create or replace procedure sp_por1 is 
begin 
--执行部分
insert into mytest values('薛俊鹏','123');
end;

--如何查看错误信息
show error;

--如何调用  该过程
1.exec 过程名（参数1，参数2...）
 exec sp_por1;
2.call 过程名（参数1，参数2...）   
 call sp_por1;

create or replace procedure sp_por1 is
  begin
 delete from mytest where name='薛俊鹏';
 end;
  /
  --显示：
Procedure created




--PL/SQL   编程基础
PL/SQL 由三个部分组成：定义部分、执行部分、例外处理部分
daclear   --定义部分--定义常量、变量、游标、例外、复杂据类型
begin      --要执行的PL/SQL 语句和sql语句
exception --例外处理部分   处理运行的各种错误


--定义部分是从declear开始的   可选，执行是从  begin开始的   必须的
--例外部分  从exception开始的  可选的

--最简单的块
begin
	dbms_output.put_line('Hello World');
end;

--打开输出语句块
set serveroutput on;
--关闭输出语句块
set serveroutput off;

--定义  + 执行
declare
	v_ename varchar2(5);--定义字符串变量
begin
	select ename into v_ename from emp where empno=&no;
	dbms_output.put_line('雇员名称：'||v_ename);
end;
--输入：7369
--显示：雇员名称：SMITH


declare
	v_ename varchar2(5);
	v_eno number(8);
begin
	select ename,empno into v_ename,v_eno from emp where empno=&no;
	dbms_output.put_line('雇员名称：'||v_ename   ||'雇员编号：'||v_eno);
end;
--输入：7369
--显示：雇员名称：SMITH雇员编号：7369

--上面  输入一个不存在的编号    则
--显示：
ORA-01403: 未找到数据
ORA-06512: 在 line 5
这个时候需要捕获异常  --no_data_found


declare
	v_ename varchar2(5);
	v_eno number(8);
begin
	select ename,empno into v_ename,v_eno from emp where empno=&no;
	dbms_output.put_line('雇员名称：'||v_ename   ||'雇员编号：'||v_eno);
exception
	when no_data_found then
	dbms_output.put_line('您的编号有误...');
end;
--上面  输入一个不存在的编号    则
--显示：
您的编号有误...


--过程   数据类型不需要指定大小
create or replace procedure gc1(
	newName varchar2,
	newSal number) is
begin 
	update emp set sal = newSal where ename = newName;
end;
--调用过程   过程需要的两个参数
exec gc1('SCOTT',4688);
--或者
 call gc1('SCOTT',4688);

--java程序调用存储过程   见   java--MyOracle项目

--函数
函数的头部必须包含  return
--例子：输入雇员姓名，返回改雇员的年薪
create function hs1(name varchar2) 
	return number is year_sal number(7,2);
begin
	select (sal+nvl(comm,0))*12 into year_sal from emp where ename = name;
return year_sal;
end;

--调用函数
var a number;
call hs1('SCOTT')  into:a; 



--包
--创建包  规范：包括过程和函数的说明，没有具体实现的代码 包体用于实现规范中的过程（update_sal）和函数（anmual_income）
--创建包体 可以使用 create package body 来实现
create package bao1 is
	procedure update_sal(name varchar2,newsal number);
	function anmual_income(name varchar2) 
	return number;
end;
--创建包体
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

--调用bao1给SCOTT改工资
exec bao1.update_sal('SCOTT',4000);

--触发器
--触发器是一个隐含执行的存储过程
--当定义触发器的时候必须定义触发的事件和触发的操作（update、insert、delete）


--变量
--标量类型（scalar）   注：变量的定义一般以  v_    开头  常量一般以   c_    开头
--定义一个变长字符串
v_ename varchar2(10);
--定义一个小数  范围  -9999.99~9999.99
v_sal number(6,2);
--定义一个小数 并给定一个初始值为5.4    := 为赋值符号
c_sal number(6,2):=5.4;
--定义一个日期类型
v_hiredate date;
--定义一个boolean变量，初始值为false
v_flag boolean not null default false;

--使用标量
--输入员工号，显示员工姓名，工资，个人所得税（税率为0.03）
declare
	c_tax_rate number(3,2):=0.03;
	v_name varchar2(10);
	v_sal number(7,2);
	v_tax_sal number(7,2);
begin
	select ename,sal into v_name,v_sal from emp where empno = &no;
	v_tax_sal:=v_sal*c_tax_rate;
	dbms_output.put_line('姓名为：'  ||v_name   ||'工资为：' ||   v_sal   ||   '交税：'  ||v_tax_sal);
end;

输入：7369
显示:
姓名为：SMITH工资为：800.32交税：24.01

如果定义时的长度太小   会报一个  字符串缓冲区太小的错误
解决办法：
定义变量改   v_name varchar2(10);  为  v_name emp.ename%type;
表示  v_name 的类型和emp表中的ename的类型一致

declare
	c_tax_rate number(3,2):=0.03;
	v_name emp.ename%type;
	v_sal emp.sal%type;
	v_tax_sal number(7,2);
begin
	select ename,sal into v_name,v_sal from emp where empno = &no;
	v_tax_sal:=v_sal*c_tax_rate;
	dbms_output.put_line('姓名为：'  ||v_name   ||'工资为：' ||   v_sal   ||   '交税：'  ||v_tax_sal);
end;


--符合类型（composite）
--定义一个记录类型emp_record_type  包含三个数据name，salary，title
--PL/SQL 记录   相当于高级语言中的结构体
declare 
	type emp_record_type is record(
	name emp.ename%type,
	salary emp.sal%type,
	title emp.job%type
	);
	--定义一个变量a_record   变量类型为emp_record_type
	a_record emp_record_type;
begin
	select ename,sal,job into a_record from emp where empno = 7369;
	dbms_output.put_line('员工名：'   ||a_record.name  ||  '薪水：'|| a_record.salary   ||  '工作为：'  ||  a_record.title);
end;

显示：
员工名：SMITH薪水：800.32工作为：CLERK

--PL/SQL 表   相当于高级语言中的数组   但是其下标可以为负数
declare
	type table_type is table of emp.ename%type
	index by binary_Integer;
	b_table table_type;
begin
	select ename into b_table(0) from emp where empno=7369;
	dbms_output.put_line('员工姓名：'    ||b_table(0));
end;
显示：
员工姓名：SMITH
--table_type：PL/SQL 表类型
--emp.ename%type：指定表元素的类型和长度
--b_table：PL/SQL 表变量
--b_table(0)  下标为0的元素


--去掉  where条件
declare
	type table_type is table of emp.ename%type
	index by binary_Integer;
	b_table table_type;
begin
	select ename into b_table(0) from emp;
	dbms_output.put_line('员工姓名：'    ||b_table(0));
end;
显示：
ORA-01422: 实际返回的行数超出请求的行数
ORA-06512: 在 line 6
解决办法：
使用参照变量


--参照类型（reference）   参照变量分为：游标变量（ref cursor）和对象类型变量（ref obj-type）
--lob（large object）
--定义游标的时候不需要指定相应的select语句，但是在使用的时候就需要
--例子1.输入部门  显示该部门的所有员工姓名和工资
declare
	type x_emp_cursor is ref cursor;--定义游标类型
	test_cursor x_emp_cursor;--定义一个游标变量
	--定义变量
	v_ename emp.ename%type;
	v_sal emp.sal%type;
begin
	--把test_cursor和select结合
	open test_cursor for select ename,sal from emp where deptno = &no;
	--循环取出
	loop
		fetch test_cursor into v_ename,v_sal;
		--判断是否test_cursor为空   为空退出
		exit when test_cursor%notfound;
		dbms_output.put_line('员工姓名：'||v_ename     ||'工资：' || v_sal);
	end loop;
	close test_cursor;
end;
显示：
员工姓名：CLARK工资：2450
员工姓名：KING工资：5000
员工姓名：MILLER工资：1300.3
员工姓名：张三工资：43222
PL/SQL procedure successfully completed

--例子2.在1的基础上   员工工资<2000  则+100元补贴
declare
	type x_emp_cursor is ref cursor;--定义游标类型
	test_cursor x_emp_cursor;--定义一个游标变量
	--定义变量
	v_ename emp.ename%type;
	v_sal emp.sal%type;
begin
	--把test_cursor和select结合
	open test_cursor for select ename,sal from emp where deptno = &no;
	--循环取出
	loop
		fetch test_cursor into v_ename,v_sal;
		if v_sal <2000 
		then update emp set sal= (sal+100) where ename= v_ename;
		--判断是否test_cursor为空   为空退出
		exit when test_cursor%notfound;
		dbms_output.put_line('员工姓名：'||v_ename     ||'工资：' || v_sal);
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
--查询 
select ename,sal from emp where deptno =10;
显示：
ENAME       SAL
---------- ---------
CLARK   2450.00
KING   5000.00
MILLER   1300.30
张三    43222.00
--调用过程
call gc2('MILLER');
--查询
select ename,sal from emp where deptno =10;
显示：
ENAME       SAL
---------- ---------
CLARK   2450.00
KING   5000.00
MILLER   1430.33
张三    43222.00

--二重条件分支
输入雇员名称，如果补助（comm）不是0 就+100  如果补助为0就+200
--v_comm<>0表示   v_comm！=0
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

--输入雇员编号
--PRESIDENT  sal+1000    MANGER+500   其他职位+200
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


--循环给users添加10条信息
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

exec gc5('张三');

select * from users;
显示：
    USERNO USERNAME
---------- ----------------------------------------
         1 张三
         2 张三
         3 张三
         4 张三
         5 张三
         6 张三
         7 张三
         8 张三
         9 张三
        10 张三
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

exec gc6('李四');

select * from users;
显示：
    USERNO USERNAME
---------- ----------------------------------------
         1 张三
         2 张三
         3 张三
         4 张三
         5 张三
         6 张三
         7 张三
         8 张三
         9 张三
        10 张三
        11 李四
        12 李四
        13 李四
        14 李四
        15 李四
        16 李四
        17 李四
        18 李四
        19 李四
        20 李四
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


exec gc7('王五');

select * from users;
显示：
    USERNO USERNAME
---------- ----------------------------------------
        21 王五
        22 王五
        23 王五
        24 王五
        25 王五

--goto语句   不建议使用
--goto会增加程序的复杂度，可读性变差
--一般不建议使用

declare
	i int:=1;
begin
	loop
	dbms_output.put_line('输出i= '  || i);
	if i=10 then
	goto end_loop;
	end if;
	i:=i+1;
	end loop;
	<<end_loop>>
dbms_output.put_line('循环结束 ');
end;

显示：
输出i= 1
输出i= 2
输出i= 3
输出i= 4
输出i= 5
输出i= 6
输出i= 7
输出i= 8
输出i= 9
输出i= 10
循环结束 



declare
	i int:=1;
begin
	loop
	dbms_output.put_line('输出i= '  || i);
	if i=10 then
	goto end_loop;
	end if;
	i:=i+1;
	end loop;
	dbms_output.put_line('循环结束 ');
	<<end_loop>>
	dbms_output.put_line('循环结束222 ');
end;

显示：
输出i= 1
输出i= 2
输出i= 3
输出i= 4
输出i= 5
输出i= 6
输出i= 7
输出i= 8
输出i= 9
输出i= 10
循环结束222 





DECLARE 
	x VARCHAR2(20);
BEGIN
	x:='hello World!';
	dbms_output.put_line('x的值是：'||x);
end;

DECLARE 
	score number :=70;
BEGIN
	IF score>85 THEN
	dbms_output.put_line('成级优秀');
	ELSIF score>60 THEN
	dbms_output.put_line('成级一般');
	ELSE 
	dbms_output.put_line('努力');
end if;
END;

DECLARE 
	g CHAR(1) :='B';
	a VARCHAR2(20);
BEGIN
	a:=
	case g
	WHEN 'A' THEN '优'
	WHEN 'B' THEN '良'
	WHEN 'C' THEN '中'
	WHEN 'D' THEN '一般'
	WHEN 'E' THEN '差'
	ELSE
	'没有这样的等级'
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
dbms_output.put_line('1到10 的阶乘：'||t);
end;

--for循环
DECLARE
	t NUMBER :=1;
	n NUMBER :=1;
BEGIN
	for n in 1..10 loop
		t:=t*n;
		--n:=n+1;
dbms_output.put_line('n是：'||n);
		exit when n=10;
	end loop;
dbms_output.put_line('1到10 的阶乘：'||t);
end;

--for循环
DECLARE
	t NUMBER :=1;
	n NUMBER :=1;
BEGIN
	for n in reverse 1..10 loop
		t:=t*n;
		--n:=n+1;
dbms_output.put_line('n是：'||n);
		exit when n=10;
	end loop;
dbms_output.put_line('1-10 的：'||t);
end;



DECLARE
	eno EMP.EMPNO%type;
BEGIN
	SELECT empno INTO eno FROM EMP WHERE EMPNO=7369;
	dbms_output.put_line('编号是：'||eno);
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
	dbms_output.put_line('编号是：'||oneemp.no);
	dbms_output.put_line('名称是：'||oneemp.name);
	dbms_output.put_line('编号是：'||oneemp.sal);
END;

DECLARE
	oneemp emp%rowtype;
BEGIN
	SELECT * into oneemp from EMP where EMPNO=7369;
	dbms_output.put_line('编号是：'||oneemp.empno);
	dbms_output.put_line('名称是：'||oneemp.ename);
	dbms_output.put_line('编号是：'||oneemp.sal);
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
	dbms_output.put_line('编号是：'|| oneemp.empno );
	end loop;
	dbms_output.put_line('总条数：' || emps%rowcount);
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
	dbms_output.put_line('编号是：'|| oneemp.empno);
	end loop;
	dbms_output.put_line('总条数：' || emps%rowcount);
	close emps;
END;
-----执行函数exec
SET serveroutput on;
exec VIEW_emp;
---show errors  显示错误



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
	--dbms_output.put_line('编号是：'|| oneemp.empno);
	name:=oneemp.ename;
	sal:=oneemp.sal;
	end loop;
	--dbms_output.put_line('总条数：' || emps%rowcount);
	close emps;
END;

DECLARE
		num  EMP.EMPNO%type :=7369;
		name EMP.ename%type;
		sal  EMP.sal%TYPE;
BEGIN
	check_emp(num,name,sal);
	dbms_output.put_line('编号是：'|| num || '姓名是：'||name||'工资为：'||sal);
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


--触发器

CREATE OR replace TRIGGER emp_count
after DELETE on NEWEMP
DECLARE
	coun NUMBER;
BEGIN
	SELECT count(*) into coun FROM NEWEMP;
	dbms_output.put_line('员工表现在还有:'||
												coun ||'条数据!');
END;


DELETE from NEWEMP WHERE empno=1000;
--新建触发器
CREATE table NEWDEPT AS SELECT * FROM DEPT;

CREATE OR replace trigger update_newdept
after UPDATE on newdept for each row 
BEGIN 
	UPDATE newemp SET deptno = :new.deptno
	where deptno=:old.deptno;
	dbms_output.put_line('员工的部门编号已更新！:');
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




--分页
--编写过程  通过java向其中添加书
--建表
create table book(
bookId number,
bookName varchar2(50),
publishHouse varchar2(50));

--编写过程
--in   表示这是一个输入参数
--out  表示一个输出参数
create or replace procedure gc_book(
gcBookId in number,
gcBookName in varchar2,
gcpublishHouse in varchar2) is
begin
	insert into book values(gcBookId,gcBookName,gcpublishHouse);
end;
--在java中调用
详情见MyOracle项目com.sc.test包下的Gc_Insert程序

代码：
package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.薛
 * java调用无返回值的存储Oracle过程  ：gc_book  为book表添加图书
 */
public class Gc_Insert {

	public static void main(String[] args) {
		Connection con = null;
//		创建一个
		try {
			con = new DBUtilora().getConnection();
			CallableStatement cs = con.prepareCall("{call GC_BOOK(?,?,?)}");
//			给？ 赋值
			cs.setInt(1, 10);
			cs.setString(2, "薛俊鹏之Oracle详解");
			cs.setString(3, "人民出版社");
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
显示：
    BOOKID BOOKNAME                             PUBLISHHOUSE
---------- -------------------------------------------------- --------------------------------------------------
        10 薛俊鹏之Oracle详解                  人民出版社


--编写过程   有返回值
--输入员工编号   返回员工名
create or replace procedure gc_xx(
spno in number,
spName out varchar2) is
begin
	select ename into spName from emp where empno=spno;
end;

--详情见MyOracle项目com.sc.test包下的Gc_Xx程序

代码：


package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.薛
 * java调用有返回值的存储Oracle过程  ：gc_xx 
*  输入员工编号   返回员工名
 */
public class Gc_Xx {

	public static void main(String[] args) {
		Connection con = null;
//		创建一个
		try {
			con = new DBUtilora().getConnection();
//			如何调用有返回值的过程
			CallableStatement cs = con.prepareCall("{call GC_XX(?,?)}");
//			给？ 赋值
			cs.setInt(1, 7369);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);//取决于第二个？的类型
			cs.execute();
			con.commit();
//			取出返回值
			String name = cs.getString(2);
			System.out.println("编号为7369的员工姓名为："+name);
			cs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

控制台显示：
编号为7369的员工姓名为：SMITH


--编写过程   有返回值
--输入员工编号   返回员工名，工资,工作
create or replace procedure gc_xx_all(
spno in number,
spName out varchar2,
spSal out number,
spJob out varchar2) is
begin
	select ename,sal,job into spName,spSal, spJob from emp where empno=spno;
end;

--详情见MyOracle项目com.sc.test包下的Gc_Xx_All程序

代码：
package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.薛
 * java调用有返回值的存储Oracle过程  ：gc_xx 
*  输入员工编号   返回员工名，工资,工作
 */
public class Gc_Xx_All {

	public static void main(String[] args) {
		Connection con = null;
//		创建一个
		try {
			con = new DBUtilora().getConnection();
//			如何调用有返回值的过程
			CallableStatement cs = con.prepareCall("{call GC_XX_ALL(?,?,?,?)}");
//			给？ 赋值
			cs.setInt(1, 7369);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);//取决于第二个？的返回接收类型
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.DOUBLE);//取决于第三个？的返回接收类型
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.VARCHAR);//取决于第四个？的返回接收类型
			cs.execute();
//			con.commit();
//			取出返回值
			String name = cs.getString(2);
			Double sal = cs.getDouble(3);
			String job = cs.getString(4);
			System.out.println("编号为7369的员工姓名为："+name+"   工资为："+sal+"工作为："+job);
			cs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}


控制台显示：
编号为7369的员工姓名为：SMITH   工资为：880.35工作为：CLERK


--输入部门号  返回所有员工信息   结果集
--创建一个包   该包中定义类型test_cursor   该类型为一个游标
create or replace package seeall as
type test_cursor is ref cursor;
end seeall;
--建立一个存储过程
create or replace procedure see_all(
spNo in number,
sp_cursor out seeall.test_cursor) is 
begin
	open sp_cursor for select * from emp where deptno = spNo;
end;

--如何在java中调用
--详情见MyOracle项目com.sc.test包下的Gc_SeeAll程序
package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.薛
 * java调用有返回值的存储Oracle过程  ：see_all    
 * 包：seeall
*  输入部门编号   返回员信息
 */
public class Gc_SeeAll {

	public static void main(String[] args) {
		Connection con = null;
//		创建一个
		try {
			con = new DBUtilora().getConnection();
//			如何调用有返回值的过程
			CallableStatement cs = con.prepareCall("{call SEE_ALL(?,?)}");
//			给？ 赋值
			cs.setInt(1, 10);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);//取决于第二个？的返回接收类型CURSOR   游标类型
			cs.execute();
			con.commit();
//			取出返回值集合
			ResultSet rs =(ResultSet) cs.getObject(2);
			while(rs.next())
			{
				System.out.println("员工编号："+rs.getString(1)+
										"        员工姓名："+rs.getString(2)+
										"        员工职称："+rs.getString(3)+
										"        上司编号："+rs.getString(4)+
										"        入职时间："+rs.getString(5)+
										"        员工工资："+rs.getString(6)+
										"        员工奖金："+rs.getInt(7)+
										"        所在部门："+rs.getString(8));
			}
			cs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

控制台显示：
员工编号：7782        员工姓名：CLARK        员工职称：MANAGER        上司编号：7839        入职时间：1981-06-09 00:00:00.0        员工工资：2450        员工奖金：0        所在部门：10
员工编号：7839        员工姓名：KING        员工职称：PRESIDENT        上司编号：null        入职时间：1981-11-17 00:00:00.0        员工工资：5000        员工奖金：0        所在部门：10
员工编号：7934        员工姓名：MILLER        员工职称：CLERK        上司编号：7782        入职时间：1982-01-23 00:00:00.0        员工工资：1430.33        员工奖金：0        所在部门：10
员工编号：12        员工姓名：张三        员工职称：经管        上司编号：7782        入职时间：2016-09-23 00:00:00.0        员工工资：43222        员工奖金：342        所在部门：10


--编写分页的过程
--开发一个包
create or replace package seeall as
type test_cursor is ref cursor;
end seeall;


--分页  tableName  表名
create or replace procedure fenye(
tableName in varchar2,
Pagesize in number,--每页的记录数
Pagenow in number,
myrows out number,--总记录数
myPageCount out number,--总页数
p_cursor out seeall.test_cursor  --返回的记录集
) is
--定义sql语句  字符串
v_sql varchar2(1000);
--定义两个整数
v_begin number:=(Pagenow-1)*Pagesize+1;
v_end number:=Pagenow*Pagesize;
begin
	v_sql:='select a1.*,rownum rn from (select * from '||tableName||') a1 where rownum between '||v_begin||' and '||v_end;
	--dbms_output.put_line(v_sql);
	--打开游标  关联sql语句
	open p_cursor for v_sql;
	--计算myrows 和myPageCount
	--组织sql语句
	v_sql:='select count(*) from '||tableName;
	--执行sql    并把返回的值  赋给myrows
	execute immediate v_sql into myrows;
	--计算和myPageCount
	if mod(myrows,Pagesize)=0 then
	myPageCount:=myrows/Pagesize;
	else
	myPageCount:=myrows/Pagesize+1;
	end if;
	--关闭游标
	--close p_cursor;
end;

--sql分页语句
select a1.*,rownum rn from (select * from emp) a1 where rownum between 1 and 5;


方法二：(语句比较繁琐)
--分页  tableName  表名
create or replace procedure fenye(
tableName in varchar2,
Pagesize in number,--每页的记录数
Pagenow in number,
myrows out number,--总记录数
myPageCount out number,--总页数
p_cursor out seeall.test_cursor  --返回的记录集
) is
--定义sql语句  字符串
v_sql varchar2(1000);
--定义两个整数
v_begin number:=(Pagenow-1)*Pagesize+1;
v_end number:=Pagenow*Pagesize;
begin
	v_sql:='select * from (select a1.*,rownum rn from (select * from '||tableName 
	||') a1 where rownum <= '||v_end||') where rn>='||v_begin;
	--打开游标  关联sql语句
	open p_cursor for v_sql;
	--计算myrows 和myPageCount
	--组织sql语句
	v_sql:="select count(*) from '"||tableName;
	--执行sql    并把返回的值  赋给myrows
	execute immediate v_sql into myrows;
	--计算和myPageCount
	if mod(myrows,Pagesize)=0 then
	myPageCount:=myrows/Pagesize;
	else
	myPageCount:=myrows/Pagesize+1;
	end if;
	--关闭游标
	close p_cursor;
end;	


--使用java测试   详情参看    Java代码

package com.sc.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.sc.utils.DBUtilora;

/**
 * @author Mr.薛
 *  测试分页
 */
public class Fenye {
	public static void main(String[] args) {
		Connection con = null;
//		创建一个
		try {
			con = new DBUtilora().getConnection();
			CallableStatement cs = con.prepareCall("{call FENYE(?,?,?,?,?,?)}");
//			输入
			cs.setString(1, "emp");//表名
			cs.setInt(2, 5);//每页显示记录数
			cs.setInt(3, 1);//第几页
//			输出参数
			cs.setInt(4, oracle.jdbc.OracleTypes.INTEGER);//总记录数
			cs.setInt(5, oracle.jdbc.OracleTypes.INTEGER);//总页数
			cs.setInt(6, oracle.jdbc.OracleTypes.CURSOR);//结果集
			cs.execute();
//			取出
			int rowNum = cs.getInt(4);
			int pageCount = cs.getInt(5);
			ResultSet rs = (ResultSet)cs.getObject(6);
			while(rs.next())
			{
				System.out.println("编号："+rs.getInt(1));
			}
//			显示结果
			System.out.println("总数据：  "+rowNum);
			System.out.println("总页数：  "+pageCount);
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