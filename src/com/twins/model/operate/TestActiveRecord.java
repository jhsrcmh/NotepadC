package com.twins.model.operate;

import org.javalite.activejdbc.DB;
import org.javalite.instrumentation.Instrumentation;
import com.twins.model.data.*;
class TestActiveRecord {
    static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost/ActiveDB";
	public static String username = "root";
	public static String password = "123456";
	
	public static void instrumentation() {
		try {
			Instrumentation ins = new Instrumentation();
			ins.setOutputDirectory(ClassLoader.getSystemResource("").getPath());
			ins.instrument();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    public static void main(String[] args) {
    	instrumentation();
    	new DB("activedb").open(driver, url, username, password);
        Employee e = new Employee();
        e.set("first_name", "John");
        e.set("last_name", "Doe");
        e.saveIt();
        selectEmployee();
        e.findAll().dump();
    }

    private static void createEmployee() {
        Employee e = new Employee();
        e.set("first_name", "John");
        e.set("last_name", "Doe");
        e.saveIt();
    }

    private static void selectEmployee() {
        Employee e = Employee.findFirst("first_name = ?", "John");
    }

    private static void updateEmployee() {
        Employee e = Employee.findFirst("first_name = ?", "John");
        e.set("last_name", "Steinbeck").saveIt();
    }

    private static void deleteEmployee() {
        Employee e = Employee.findFirst("first_name = ?", "John");
        e.delete();
    }

    private static void deleteAllEmployees() {
            Employee.deleteAll();
    }

    private static void selectAllEmployees() {
    }
}
