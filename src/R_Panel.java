import java.awt.*;
import java.io.File;
import java.nio.*;
import java.nio.file.Files;
import java.util.*;

import javax.swing.*;

/* Make Right Panel
 * Form (V : 650, ICONS's Height : 450, PIC_PREV's Height : 150)
 *  --------------------------
 * |                          |
 * |                          |
 * |          ICONS           |
 * |       (in folder)        |
 * |                          |
 * |--------------------------|
 * |      PICTURE PREVIEW     |
 *  --------------------------
 */

public class R_Panel extends JPanel{
	private JPanel ICONS = new JPanel(new CardLayout());
	private JLabel PIC_PREV = new JLabel("PICTURE PRIEVIEW");
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	
	
	private Vector header = new Vector();
	private Vector data = new Vector();
	private JTable table;
	private JScrollPane table_jsp;
		
	public R_Panel() {
		this.setLayout(new BorderLayout());
		ICONS.setBackground(Color.white);
	//	this.setSize(new Dimension(600,600));
		
		header.addElement("이름");
		header.addElement("수정된 날짜");
		header.addElement("유형");
		header.addElement("크기");
		
		table = new JTable(data, header);
		table_jsp = new JScrollPane(table);
		table_jsp.setPreferredSize(new Dimension(650,450));
		jsp.setLeftComponent(table_jsp);
		PIC_PREV.setSize(new Dimension(650,150));
		jsp.setRightComponent(PIC_PREV);
		jsp.setOneTouchExpandable(true);
		this.add("Center",jsp);
	}
	
	
	public void setTable(File[] ff) {		
		if(ff == null) return;
		for (int i = 0;i < ff.length;++i) {
			if(ff[i].isDirectory()) {
				Vector vc = new Vector();
				vc.addElement(ff[i].getName());
				vc.addElement(new Date(ff[i].lastModified()).toString());
				vc.add("파일 폴더");
				vc.add(null);
				data.addElement(vc);
			}
		}
		for (int i = 0;i < ff.length;++i) {
			if(ff[i].isFile()) {
				Vector vc = new Vector();
				vc.addElement(ff[i].getName());
				vc.addElement(new Date(ff[i].lastModified()).toString());
				vc.addElement(null);
				vc.addElement(ff[i].length() + "Kb");
				data.addElement(vc);
			}
		}
		table = new JTable(data, header);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_jsp = new JScrollPane(table);
		jsp.setLeftComponent(table_jsp);
		this.add(jsp);
	}
	
	public void dataRemove() {
		data.removeAllElements();
	}
}
