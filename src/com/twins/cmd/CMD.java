package com.twins.cmd;

/**
 * Make a interface with cmd in windows, and static String gcc is 
 * the path of the gcc. help for mingw
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class CMD implements Runnable {

	private InputStream ins = null;
	private static String url;
	private static StringBuffer strings = new StringBuffer(); 
	public static String gcc = "D:\\CodeBlocks\\MinGW\\bin\\gcc.exe ";
	public CMD(InputStream instream) {
		this.ins = instream;
	}

	public CMD() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// 每一次run之前，都清空一下缓冲区
		strings.delete(0, strings.length());
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				//get all line's powerline ok, get all result
				strings.append(line+"\n");
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Build a c file and report as task.exe
	 * @author twins
	 * @param task
	 * @return
	 */
	public static String cmd(String task) {
		
		//gcc专用配置
		String [] cmd = new String[] { "cmd.exe", "/C", gcc + " -o "+ task+".exe "+task };

		 try {
	            Process process = Runtime.getRuntime().exec(cmd);
	            //开启两个线程进行监听
	            new Thread(new CMD(process.getInputStream())).start();
	            new Thread(new CMD(process.getErrorStream())).start();
	            process.getOutputStream().close();
	            int exitValue = process.waitFor();
	            process.destroy();
	            System.out.println("return: " + exitValue);
	            //成功
	            if(exitValue == 0) {
	            	strings.append(exitValue);
	            }
	            return strings.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return "error";
	}
	
	/**
	 * Run the task and capture of the cmd's outputstream
	 * @param task
	 * @return
	 */
	public static String run(String task) {
		//gcc's path
		String [] cmd = new String[] { "cmd.exe", "/C", task+".exe " };

		 try {
	            Process process = Runtime.getRuntime().exec(cmd);
	            //开启两个线程进行监听
	            new Thread(new CMD(process.getInputStream())).start();
	            new Thread(new CMD(process.getErrorStream())).start();
	            process.getOutputStream().close();
	            int exitValue = process.waitFor();
	            process.destroy();
	            System.out.println("return: " + exitValue);
	            //if success
	            return strings.append("\n\nreturn :"+exitValue).toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return "error";
	}
	
	/**
	 * Read a file to a string
	 * @param file
	 * @return String
	 */
	public static String openFile(File file) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			// br = new BufferedReader(new FileReader(file));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF-8"));
			String str = br.readLine();
			while (str != null) {
				sb.append(str+"\n");
			}
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * if a task is filled cpu, kill it forcely!
	 * @param taskName
	 * @return
	 */
	public static boolean taskKill(String taskName) {
		String [] cmd = new String[] { "cmd.exe", "/C", "taskkill /im " + taskName +" /f" };
		 try {
	            Process process = Runtime.getRuntime().exec(cmd);
	            //开启两个线程进行监听
	            new Thread(new CMD(process.getInputStream())).start();
	            new Thread(new CMD(process.getErrorStream())).start();
	            process.getOutputStream().close();
	            process.destroy();
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return false;
	}
	@SuppressWarnings("static-access")
	
	public static void main(String [] args) {
		CMD cmd = new CMD();
		cmd.run("1.c");
		System.out.println(strings);
	}
}
