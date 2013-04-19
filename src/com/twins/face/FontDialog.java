package com.twins.face;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
@SuppressWarnings("serial")
public class FontDialog extends JDialog{
	private JLabel fontFamily = new JLabel("字体:");
	private JLabel fontShape = new JLabel("字形:");
	private JLabel fontSize = new JLabel("大小:");
    private JTextField fontFamilyField = new JTextField(10);
    private JTextField fontShapeField = new JTextField(8);
    private JTextField fontSizeField = new JTextField(6);
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private JList fontFamilyList = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private JList fontShapeList = new JList(new Object[]{"常规","粗体","倾斜"});
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private JList fontSizeList = new JList(new Object[]{12,13,14,24,26,36});

    private JButton ok =new JButton("确认");
    private JButton cancel = new JButton("取消");

    
    String selectedFontFamily;
    int shapeStyle ;
    int sizeStyle ;
    
    private TextNotepad frame;
    public FontDialog(TextNotepad frame){
    	this.frame = frame;
    	
    	setSize(400,300);
    	setLocationRelativeTo(null);
    	init();
    	addListener();
    	setVisible(true);
    }

    public void addListener(){
    	JListListener listener = new JListListener();
    	fontFamilyList.addListSelectionListener(listener);
    	fontSizeList.addListSelectionListener(listener);
    	fontShapeList.addListSelectionListener(listener);

    	JButtonListener buttonListener = new JButtonListener();
    	ok.addActionListener(buttonListener);
    	cancel.addActionListener(buttonListener);

    }

  public void init(){
		Container container = this.getContentPane();
		GridBagLayout gbl = new GridBagLayout();
		JPanel gridPane = new JPanel(gbl);
		GridBagConstraints  gbc1=new  GridBagConstraints();
		gbc1.gridx=0;
		gbc1.gridy=0;
		gbl.setConstraints(fontFamily, gbc1);
		gridPane.add(fontFamily);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx=1;
		gbc2.gridy=0;
		gbl.setConstraints(fontShape, gbc2);
		gridPane.add(fontShape);
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridx=2;
		gbc3.gridy =0;
		gbl.setConstraints(fontSize, gbc3);
		gridPane.add(fontSize);
		GridBagConstraints gbc4 =new GridBagConstraints();
		gbc4.gridx=0;
		gbc4.gridy=1;
		gbl.setConstraints(fontFamilyField, gbc4);
		fontFamilyField.setText(selectedFontFamily);
		gridPane.add(fontFamilyField);
		GridBagConstraints gbc5 =new GridBagConstraints();
		gbc5.gridx=1;
		gbc5.gridy=1;
		gbl.setConstraints(fontShapeField, gbc5);
		fontShapeField.setText(""+shapeStyle+"");
		gridPane.add(fontShapeField);
		GridBagConstraints gbc6 =new GridBagConstraints();
		gbc6.gridx=2;
		gbc6.gridy=1;
		gbl.setConstraints(fontSizeField, gbc6);
		fontSizeField.setText(""+sizeStyle+"");
		gridPane.add(fontSizeField);
		JScrollPane jsp = new JScrollPane(fontFamilyList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(110, 130));
		GridBagConstraints gbc7 = new GridBagConstraints();
		gbc7.gridx=0;
		gbc7.gridy=2;
		gbl.setConstraints(jsp, gbc7);
		gridPane.add(jsp);
		JScrollPane jsp2 = new JScrollPane(fontShapeList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp2.setPreferredSize(new Dimension(90, 80));
		GridBagConstraints gbc8 =new GridBagConstraints();
		gbc8.gridx=1;
		gbc8.gridy=2;
		gbc8.anchor=GridBagConstraints.NORTHWEST;
		gbl.setConstraints(jsp2, gbc8);
		gridPane.add(jsp2);
		JScrollPane jsp3 = new JScrollPane(fontSizeList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp3.setPreferredSize(new Dimension(60,90));
		GridBagConstraints gbc9  = new GridBagConstraints();
		gbc9.gridx=2;
		gbc9.gridy=2;
		gbc9.anchor = GridBagConstraints.NORTHWEST;
		gbl.setConstraints(jsp3, gbc9);
		gridPane.add(jsp3);
		container.add(gridPane,BorderLayout.NORTH);
		GridBagConstraints gbc10 = new GridBagConstraints();
		gbc10.gridx=3;
		gbc10.gridy=1;
		gbl.setConstraints(ok, gbc10);
		gridPane.add(ok);
		GridBagConstraints gbc11 = new GridBagConstraints();
		gbc11.gridx=3;
		gbc11.gridy =2;
		gbc11.anchor = GridBagConstraints.NORTHWEST;
		gbl.setConstraints(cancel,gbc11);
		gridPane.add(cancel);
	}
 public class JButtonListener implements ActionListener{
    
	@Override
	public void actionPerformed(ActionEvent event) {
		//判断用户点击的是确定还是取消
		if(event.getSource()==ok){
			//获得
		   Font font = new Font(selectedFontFamily, shapeStyle , sizeStyle);
		   FontDialog.this.frame.getJtaContent().setFont(font);
			//jtaContent.setFont(font);
			FontDialog.this.setVisible(false);
			FontDialog.this.dispose();

		}else if (event.getSource()==cancel){//点击取消关闭对话框FontDialog
			FontDialog.this.setVisible(false);
			FontDialog.this.dispose();

		}
	}
 }

 public class JListListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			System.out.println(e.getSource());

			if (e.getSource()==fontFamilyList){
				Object value =  fontFamilyList.getSelectedValue();
				fontFamilyField.setText(value.toString());
				selectedFontFamily = fontFamilyField.getText();
			}else if (e.getSource()==fontSizeList){
				Object value = fontSizeList.getSelectedValue();
				fontSizeField.setText(value.toString());
				sizeStyle =  Integer.valueOf(fontSizeField.getText())-Integer.valueOf("0");

			}else if (e.getSource()== fontShapeList){
				Object value = fontShapeList.getSelectedValue();
				shapeStyle = fontShapeList.getSelectedIndex();
				fontShapeField.setText(value.toString());
			}
		}
	}
}