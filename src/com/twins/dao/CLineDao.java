package com.twins.dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
public class CLineDao {
	private BaseDao bd = new BaseDao();
	public static String tableName = "c_lines";
	public static String database ="hello";
	
	public boolean addResultWithBatch(File file, String[] strings) throws IOException {
		String filename = file.getName();
		try {
			java.sql.PreparedStatement sql = this.bd.getConnction().prepareStatement("insert into "+tableName+"(modelName, modelContent) values(?,?)");
			for(int i = 0; i < strings.length; i++) {
				sql.setString(1, filename);
				sql.setString(2, strings[i]);
				sql.addBatch();
			}
			sql.executeBatch();
			System.out.println("successfully insert the result in CLineDao");
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}	
		return false;
	}
	public boolean deleteModelByName(File file) {
		String filename = file.getName();
		try {
			java.sql.Statement sql = this.bd.getConnction().createStatement();
			String str = "DELETE FROM `activedb`.`c_lines` WHERE `modelName` = '"+filename+"'";
			sql.execute(str);
			System.out.println("delete origin file successfully");
			return true;
		} catch(SQLException ex) {
			System.out.println(ex);
		}
		return false;
	}
	public boolean ifGetModelByName(File file) {
		String filename = file.getName();
		try {
			java.sql.Statement sql = this.bd.getConnction().createStatement();
			String str = "select modelContent from " + tableName +" where modelName = '"+filename+"'";
			java.sql.ResultSet rs = sql.executeQuery(str);
			while( rs.next() ) {
				return true;
			}
		} catch(SQLException ex) {
			System.out.println(ex);
		}
		return false;
	}
	public String getModel(String filename) {
		StringBuffer sb = new StringBuffer() ;
		try {
			java.sql.Statement sql = this.bd.getConnction().createStatement();
			String str = "select modelContent from " + tableName +" where modelName = '"+filename+"'";
			java.sql.ResultSet rs = sql.executeQuery(str);
			while( rs.next() ) {
				sb.append(rs.getString(1)+"\n");
			}
			return sb.toString();
		} catch(SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}
}
