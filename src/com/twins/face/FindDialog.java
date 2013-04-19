package com.twins.face;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FindDialog extends JDialog{
	private JLabel jlbFind = new JLabel("查找内容");
	private JTextField jtfFind = new JTextField(10);
	private JButton jbFind = new JButton("查找下一个");

	private TextNotepad frame;


	public FindDialog(TextNotepad frame){
		super(frame);
		setSize(300,80);		
		this.frame = frame;

		setLocationRelativeTo(null);
		init();
		addListener();
		setVisible(true);

	}
	public void init(){
		Container container = getContentPane();

		//改变container的布局管理器为流式布局
		container.setLayout(new FlowLayout());

		container.add(jlbFind);
		container.add(jtfFind);
		container.add(jbFind);

	}


	public void addListener(){
		QueryListener ql = new QueryListener();
		jbFind.addActionListener(ql);
	}


	int fromIndex=0;

	public class QueryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent evnet) {
			//得到查找对话框文本框中的内容
			String queryStr = jtfFind.getText();
			if (queryStr!=null && !queryStr.equals("")){
				//执行查找
				//获得记事本主窗体文本域中的文字
				String content = frame.getJtaContent().getText();

				int startIndex = content.indexOf(queryStr, fromIndex);
				if (startIndex == -1){
					JOptionPane.showMessageDialog(frame, "未找到:"+queryStr);
				}else{
					//选中被找到的文字
					frame.getJtaContent().setSelectionStart(startIndex);
					frame.getJtaContent().setSelectionEnd(startIndex+queryStr.length());
					fromIndex = startIndex+queryStr.length();

					//replaceSelection(String content)
				}

			}

		}

	}

}