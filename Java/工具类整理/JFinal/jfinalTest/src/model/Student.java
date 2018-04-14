package model;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Student extends Model<Student>{
	
   public static final Student dao=new Student();
//   Íâ¼ü²éÑ¯
   public Classes getClasses() {
       return Classes.dao.findById(get("classesid"));
   }
}
