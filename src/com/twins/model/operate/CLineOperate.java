package com.twins.model.operate;

/**
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.LazyList;
import org.javalite.instrumentation.Instrumentation;
import com.twins.model.data.*;
public class CLineOperate {
	
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
	public void init() {
		instrumentation();
    	new DB("activedb").open(driver, url, username, password);
	}
	public static void main(String[] args) {
		CLineOperate os = new CLineOperate();
		os.init();
		System.out.println(os.getModel("11"));
    }
	
	/**
	 * add record of a model according a filename
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public boolean saveModel(File file) throws IOException {
		BufferedReader br;
		String filename = file.getName();
		// read lines
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF-8"));
			String str = br.readLine();
			while (str != null) {
				// write to database
				CLine.create("modelname", filename, "modelcontent", str);
			}
			return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * get all models from filename, and returns strings
	 * @param file
	 * @return
	 */
	public String getModel(String filename) {
		StringBuffer sb = new StringBuffer();
		LazyList<CLine> models = CLine.where("modelname = ?", filename);
		if(!models.isEmpty()) {
			for(CLine cline: models) {
				sb.append(cline.get("modelcontent"));
			}
			return sb.toString();
		}
		return null;
	}
}
