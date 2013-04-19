package com.twins.face;

/**
 * Notepad with gcc modified by twins
 */
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.awt.TextArea;
import java.awt.CardLayout;
import com.twins.cmd.CMD;
import com.twins.dao.CLineDao;

@SuppressWarnings("serial")
public class TextNotepad extends JFrame {

	private JFrame jframe;
	JPanel jpanel;
	JMenuBar jmenuBar = new JMenuBar();
	JMenu jmenuFile, jmenuEdit;
	JMenuItem jmenuItemOpen, jmenuItemNew, jmenuItemSave, jmenuItemSaveAs,
			jmenuItemExit;
	JMenuItem fileExit; 
	JTextArea jtextArea;
	JTextArea jtextArea_2;
	JFileChooser jfileChooser;
	JOptionPane xzy;
	JMenu jmFormat;
	JCheckBoxMenuItem jcbmiAuto;
	JMenuItem textFont;
	JMenu jmEdit;// 记事本的“编辑"
	JMenuItem jmiRevoke;// revoke
	JMenuItem jmiCopy;
	JMenuItem jmiPaste;
	JMenuItem jmiDelete;
	JMenuItem jmiCut;
	JMenuItem jmiFind;

	JMenu aboutMe;
	JMenuItem aboutVersion;

	File currentFile;
	String oldText;
	
	boolean ifRunTrue = false;
	private JScrollPane jscrollPane;
	private TextArea textArea_1;
	private JMenu mnNewMenu;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;

	public JTextArea getJtaContent() {
		return this.jtextArea;
	}

	public TextNotepad() {
		oldText = "";
		jframe = new JFrame("我的记事本");
		jpanel = new JPanel();
		jmenuBar = new JMenuBar();
		jtextArea = new JTextArea();
		jtextArea.setLineWrap(true);// 设置自动换行

		jscrollPane = new JScrollPane(jtextArea);
		JScrollPane jscrollPane_2 = new JScrollPane(jtextArea_2);
		jfileChooser = new JFileChooser();
		xzy = new JOptionPane();
		jfileChooser.setFileFilter(new FileNameExtensionFilter("文本文件(*.txt)",
				"txt"));

		jmenuFile = new JMenu("文件(F)");
		jmenuFile.setMnemonic('F');
		jmenuEdit = new JMenu("编辑(E)");
		jmenuEdit.setMnemonic('E');
		JTAContentLostFocus jtaListener = new JTAContentLostFocus();
		jtextArea.addFocusListener((FocusListener) jtaListener);
		final UndoManager undoManager = new UndoManager();
		jtextArea.getDocument().addUndoableEditListener(undoManager);
		jmiRevoke = new JMenuItem("撤销");
		jmiRevoke.setMnemonic('z');
		jmiRevoke.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (undoManager.canUndo()) {
					try {
						undoManager.undo();
					} catch (Exception er) {
					}
				}
			}

		});
		jmiFind = new JMenuItem("查找");
		jmiFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_DOWN_MASK));
		jmiFind.addActionListener(new ActionListener() {

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FindDialog query = new FindDialog(TextNotepad.this);
			}

		});
		jmiCut = new JMenuItem("剪切");
		jmiCut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jtextArea.cut();

			}
		});
		jmiDelete = new JMenuItem("删除");
		jmiDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jtextArea.replaceSelection("");

			}
		});
		jmiPaste = new JMenuItem("粘贴");
		jmiPaste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jtextArea.paste();

			}
		});
		jmiCopy = new JMenuItem("复制");
		jmiCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jtextArea.copy();

			}
		});
		jmenuItemNew = new JMenuItem("新建(N)", 'N');
		jmenuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_DOWN_MASK));
		jmenuItemNew.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				menuItem_1.setEnabled(false);
				jframe.setTitle("未命名");
				if (currentFile == null) {
					if (!jtextArea.getText().equals(oldText)) {
						int res = xzy.showConfirmDialog(jframe,
								"文件已修改，没有保存，是否要保存？");
						if (res == xzy.YES_OPTION) {
							saveFile();
						}
					}

				} else {
					if (!jtextArea.getText().equals(oldText)) {
						int res = xzy.showConfirmDialog(jframe,
								"文件已修改，没有保存，是否要保存？");
						if (res == xzy.YES_OPTION) {
							saveFile();
						}
					}
				}
				currentFile = null;
				jtextArea.setText("");

			}
		});
		jmenuItemOpen = new JMenuItem("打开(O)", 'O');
		jmenuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_DOWN_MASK));
		jmenuItemOpen.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				menuItem_1.setEnabled(false);
				if (!jtextArea.getText().equals(oldText)) {
					int res2 = xzy.showConfirmDialog(jframe,
							"文件已修改，没有保存，是否要保存？");
					if (res2 == xzy.YES_OPTION) {
						saveFile();
					}
				}
				int res = jfileChooser.showOpenDialog(jframe);

				if (res == jfileChooser.APPROVE_OPTION) {
					currentFile = jfileChooser.getSelectedFile();
					jtextArea.setText("");
					//openFile(currentFile);
					CLineDao cd = new CLineDao();
					String content = cd.getModel(currentFile.getName());
					jframe.setTitle(currentFile + "-记事本");
					System.out.println("s");
					jtextArea.setText(content);
				}
			}
		});
		jmenuItemSave = new JMenuItem("保存(S)", 'S');
		jmenuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_DOWN_MASK));
		jmenuItemSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveFile();
			}
		});
		jmenuItemSaveAs = new JMenuItem("另存为");
		jmenuItemSaveAs.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int res = jfileChooser.showSaveDialog(jframe);
				if (res == jfileChooser.APPROVE_OPTION) {
					currentFile = jfileChooser.getSelectedFile();
					jframe.setTitle(currentFile + "-记事本");
					saveFile();
					//saveFileAs(currentFile,jtextArea.getText().replaceAll("\n", "\r\n"));
				}
			}
		});
		fileExit = new JMenuItem("退出");
		fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				InputEvent.CTRL_DOWN_MASK));
		fileExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jframe.setVisible(false);
				jframe.dispose();
			}
		});
		jmenuEdit.add(jmiRevoke);
		jmenuEdit.addSeparator();
		jmenuEdit.add(jmiCut);
		jmenuEdit.add(jmiCopy);
		jmenuEdit.add(jmiPaste);
		jmenuEdit.add(jmiDelete);
		jmenuEdit.addSeparator();
		jmenuEdit.add(jmiFind);
		// 这里是格式相关的
		jmFormat = new JMenu("格式(O)");
		jmFormat.setMnemonic('O');
		jcbmiAuto = new JCheckBoxMenuItem("自动换行");
		jcbmiAuto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				InputEvent.CTRL_DOWN_MASK));
		jcbmiAuto.setState(true);
		jcbmiAuto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean isChecked = jcbmiAuto.getState();
				jtextArea.setLineWrap(isChecked);// 有一个函数可以实现。。。汗。。
				System.out.println(isChecked);
			}
		});
		textFont = new JMenuItem("字体");//
		textFont.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				InputEvent.CTRL_DOWN_MASK));
		textFont.addActionListener(new ActionListener() {

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FontDialog fontDia = new FontDialog(TextNotepad.this);
			}
		});

		aboutMe = new JMenu("关于(V)");
		aboutMe.setMnemonic('V');
		aboutVersion = new JMenuItem("版本");
		aboutVersion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				InputEvent.CTRL_DOWN_MASK));
		aboutVersion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(TextNotepad.this,
						"DESIGNZ.me\n综艺的第一个Java程序\n记事本", "version info",
						JOptionPane.INFORMATION_MESSAGE);
			}

		});
		jframe.getContentPane().setLayout(new CardLayout(0, 2));
		jmenuBar.add(jmenuFile);
		jmenuBar.add(jmenuEdit);
		jmenuBar.add(jmFormat);
		jmenuFile.add(jmenuItemNew);
		jmenuFile.add(jmenuItemOpen);
		jmenuFile.add(jmenuItemSave);
		jmenuFile.add(jmenuItemSaveAs);
		jmenuFile.addSeparator();
		jmenuFile.add(fileExit);
		jmFormat.add(jcbmiAuto);
		jmFormat.add(textFont);

		mnNewMenu = new JMenu("运行");
		jmenuBar.add(mnNewMenu);

		menuItem = new JMenuItem("编译");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				textArea_1.setText("");
				jframe.setTitle("编译");
				saveFile();
				textArea_1.setText(buildFile());
				if(ifRunTrue) {
					menuItem_1.setEnabled(true);
				}
			}
		});
		mnNewMenu.add(menuItem);
		
		menuItem_1 = new JMenuItem("运行");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
				jframe.setTitle("运行");
				textArea_1.setText(runFile());
			}
		});
		menuItem_1.setEnabled(false);
		mnNewMenu.add(menuItem_1);
		jmenuBar.add(aboutMe);
		aboutMe.add(aboutVersion);
		jpanel.setLayout(new BorderLayout(0, 0));
		jpanel.add(jmenuBar, "North");
		jpanel.add(jscrollPane, "Center");
		jpanel.add(jscrollPane_2, "South");

		textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		jscrollPane_2.setViewportView(textArea_1);
		jframe.getContentPane().add(jpanel, "name_175094892744550");
		jframe.setSize(611, 516);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
	@SuppressWarnings("static-access")
	public void saveFile() {
		menuItem_1.setEnabled(false);
		if (currentFile == null) {
			
			//文件没有的话
			int res = jfileChooser.showSaveDialog(jframe);
			if (res == jfileChooser.APPROVE_OPTION) {
				//文件保存成功，进行文件数据库连接。
				currentFile = jfileChooser.getSelectedFile();
				jframe.setTitle(currentFile + "-C语言编辑器");
			}
		}
		if (currentFile != null) {
			String[] strings = jtextArea.getText().split("\n");
			saveFileAs(currentFile, jtextArea.getText()
					.replaceAll("\n", "\r\n"));
			//哈哈，就不管什么效率问题啦。。考研啊，fuck You！初始化，保存一个模型，得先删除育有的模型
			CLineDao cd = new CLineDao();
			try {
				if(cd.ifGetModelByName(currentFile)) {
					cd.deleteModelByName(currentFile);
					cd.addResultWithBatch(currentFile, strings);
				}else {
					cd.addResultWithBatch(currentFile, strings);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void saveFileAs(File file, String text) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
			pw.write(text);
			pw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public void resetFile(File file) {
		String text = "您看到的是由twins开发的模型库，淡然，您的模型有属于自己的归宿";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
			pw.write(text);
			pw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	@SuppressWarnings("static-access")
	public String buildFile() {
		//saveFile();
		CMD cmd = new CMD();
		System.out.println(currentFile.getAbsolutePath());
		if(cmd.cmd(currentFile.getAbsolutePath()).equals("0")) {
			ifRunTrue = true;
			return "build successfully";
		}
		return cmd.cmd(currentFile.getAbsolutePath());
	}
	@SuppressWarnings("static-access")
	public String runFile() {
		CMD cmd = new CMD();
		System.out.println(currentFile.getAbsolutePath());
		return cmd.run(currentFile.getAbsolutePath());
	}
	public void openFile(File file) {
		BufferedReader br = null;
		try {
			// br = new BufferedReader(new FileReader(file));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF-8"));
			String str = br.readLine();
			while (str != null) {
				jtextArea.append(str + "\n");
				oldText = jtextArea.getText();
				System.out.println(str);
				str = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 失去焦点监听器
	 * 
	 * @author Teach
	 * 
	 */
	public class JTAContentLostFocus implements FocusListener {

		public void focusGained(FocusEvent e) {
			System.out.println("获得焦点");
		}
		public void focusLost(FocusEvent e) {
			System.out.println("失去焦点");
			String selectedText = jtextArea.getSelectedText();// 获得文本域组件中被选中的文字
			System.out.println(selectedText);
			if (selectedText != null && !selectedText.equals("")) {// 已选中文字
				setEditStatus(true);
			} else {// 未选中文字
				setEditStatus(false);
			}
		}

	}
	/**
	 * 该方法用户改变编辑菜单下剪切,复制,删除按钮的可用状态
	 * 
	 * @param falg
	 */
	public void setEditStatus(boolean falg) {
		jmiCut.setEnabled(falg);// Enabled设置组件不可用
		jmiCopy.setEnabled(falg);
		jmiDelete.setEnabled(falg);
	}

	public static void main(String[] args) {
		new TextNotepad();
	}
	public JMenu getMnNewMenu() {
		return mnNewMenu;
	}
}