import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
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
	public JPanel pic_prev = new JPanel();
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private Vector header = new Vector();
	private Vector data = new Vector();
	private JTable table;
	private JScrollPane table_jsp;
	private JScrollPane pic_jsp;
	
	private int identifier = 0;
	
	public ArrayList <String> list = new ArrayList <String> ();
	public ArrayList <String> thumbroot = new ArrayList <String> ();
	public ArrayList <String> thumbworklist = new ArrayList <String> ();
	
	// Constructor of R_Panel
	public R_Panel() {
		this.setLayout(new BorderLayout());
		
		header.addElement("이름");
		header.addElement("수정된 날짜");
		//header.addElement("유형");
		header.addElement("크기");
		
		table = new JTable(data, header);
		table_jsp = new JScrollPane(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		//table.setGridColor(Color.WHITE);

		table_jsp.setPreferredSize(new Dimension(650,425));
		//table_jsp.setBackground(Color.WHITE);
		jsp.setLeftComponent(table_jsp);
		
		pic_jsp = new JScrollPane(pic_prev);
		jsp.setRightComponent(pic_jsp);
		jsp.setOneTouchExpandable(true);
		//jsp.setResizeWeight(0.3);
		this.add("Center",jsp);
	}
	
	// set informations of files to Table
	public void setTable(File[] ff, String thumbRoot) {	
		if(ff == null) return;
		
		for (int i = 0;i < ff.length;++i) {
			if(ff[i].isFile()) {
				Vector vc = new Vector();
				vc.addElement(ff[i].getName());
				vc.addElement(new Date(ff[i].lastModified()).toString());
				vc.addElement(ff[i].length() + "Kb");
				data.addElement(vc);	
				
				int n = ff[i].getName().lastIndexOf(".");
				String extension = ff[i].getName().substring(n+1);
				
				// make thumbnail image and show it
				if(extension.equals("jpg") || extension.equals("JPG") || 
				   extension.equals("png") || extension.equals("PNG") ||
				   extension.equals("gif") || extension.equals("GIF")) {				
					
					
					
					list.add(ff[i].getAbsolutePath());
					
					String thumb_name = 
							new String(ff[i].lastModified() + "_" + ff[i].length() + "_" + ff[i].getName());
					String temp = thumbRoot + File.separator + thumb_name;
										
					// Synchronization between this.Folder and thumbnail_Folder
					File f = new File(thumbRoot);
					File[] thumbf = f.listFiles();
					boolean exist = false;
					
					for ( int x = 0; x < thumbf.length ; ++x) {
						if(thumbf[x].isFile()) {
							if(thumb_name.equals(thumbf[x].getName())) {
								thumbroot.add(thumbf[x].getAbsolutePath());
								thumbworklist.add("None");
								exist = true;
								break;
							}
						}
					}
					if(!exist) {
						thumbworklist.add(temp);
						thumbroot.add(temp);
					}
				}
			}
		}
		table = new JTable(data, header);
		this.setJSP();
	}
	
	// delete data
	public void dataRemove() {
		data.removeAllElements();
		list.clear();
		thumbroot.clear();
		thumbworklist.clear();
		pic_prev.removeAll();
		table.removeAll();
		this.setJSP();
	}
	
	public void treeColloapse() {
		this.dataRemove();
	}
	
	public void setJSP() {
		jsp.setLeftComponent(table_jsp);
		jsp.setRightComponent(pic_jsp);
	}
}
