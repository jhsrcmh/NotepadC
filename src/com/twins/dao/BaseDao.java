package com.twins.dao;

import java.sql.*;
import java.lang.String;
public class BaseDao {

	private static final String user_name = "root";
	private static final String password = "123456";
	private static final String database = "activedb";
	private static java.sql.Connection con = null; 
	private PreparedStatement ps;

	public BaseDao() {
	}


	public java.sql.Connection getConnction() {
		if ((con == null)) {
			try {
				Class.forName("org.gjt.mm.mysql.Driver").newInstance();
				String URL = "jdbc:mysql://localhost:3306/"+database+"?useUnicode=true&characterEncoding=utf-8";
				con = DriverManager.getConnection(URL, user_name, password);
				System.out.println("successful->connection()->BaseDao()");
			} catch (Exception ex) {
				System.out.println("BaseDao->getConnection->" + ex.toString());
			}
		}
		try {
			if (con.isClosed()) {
				try {
					Class.forName("org.gjt.mm.mysql.Driver").newInstance();
					String URL = "jdbc:mysql://localhost:3306/"+database.toString()+"?useUnicode=true&characterEncoding=utf-8";
					con = DriverManager.getConnection(URL, user_name, password);
				} catch (Exception ex) {
					System.out.println("BaseDao->getConnection->"
							+ ex.toString());
				}
			}

		} catch (Exception ex) {
			System.out.println("BaseDao->GetConnction");
		}

		return con;
	}

	/**
	 * close the db, to make the basic applaction
	 * 
	 * @param con
	 */
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}
	/**
	 * To get resultset of the sql statement
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getResult() throws SQLException {
		return ps.getResultSet();
	}

	public static void main(final String[] args) {
		BaseDao basedao = new BaseDao();
		basedao.getConnction();
		basedao.close();
		System.out.println("successfully!");
	}
}
