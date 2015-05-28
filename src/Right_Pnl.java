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
 * |       Information        |
 * |       (in folder)        |
 * |                          |
 * |--------------------------|
 * |      PICTURE PREVIEW     |
 *  --------------------------
 */

public class Right_Pnl extends JPanel{
	
	// Picture mini preview DS
	private JPanel pnl_pic = new JPanel(new BorderLayout());	
	private JButton btn_prev = new JButton("◀");
	private JButton btn_next = new JButton("▶");
	private JPanel pic_prev = new JPanel();
	private int prev_index = 0;
	
	// JTable DS
	private Vector header = new Vector();
	private Vector data = new Vector();
	private JTable table;
	private JScrollPane jsp_table;

	//Right Panel Layout
	public Right_Pnl() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		header.addElement("이름");
		header.addElement("수정된 날짜");
		header.addElement("크기");
		
		// set Information Table
		table = new JTable(data, header);
		jsp_table = new JScrollPane(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		// set Picture mini preview
		//pnl_pic.setPreferredSize(new Dimension(650,150));
		pnl_pic.add("West", btn_prev);
		pnl_pic.add("Center", pic_prev);
		pnl_pic.add("East", btn_next);
		
		this.removeAll();
		this.add(jsp_table);
		this.add(pnl_pic);
	}
	
	// set informations of files to Table 
	public void setTable(File[] ff) {
		if(ff == null) return;
		String root = ff[1].getAbsolutePath().substring(0, ff[1].getAbsolutePath().lastIndexOf(File.separator));
				
		// thumbnail Folder exist?
		boolean thumbexist = false;
		for (int i = 0;i<ff.length;++i) {
			if(ff[i].isDirectory()) {
				if(ff[i].getName().equals("_thumbnail_IView")) {
					thumbexist = true;
				}
			}
		}
		// set data 
		for (int i = 0;i<ff.length;++i) {
			if(ff[i].isFile()) {
				Vector vc = new Vector();
				vc.addElement(ff[i].getName());
				vc.addElement(new Date(ff[i].lastModified()).toString());
				vc.addElement(ff[i].length() + "Kb");
				data.addElement(vc);
				
				// File is Image Files?
				int n = ff[i].getName().lastIndexOf(".");
				String extension = ff[i].getName().substring(n+1);
				
				if(extension.equals("jpg") || extension.equals("JPG") ||
				   extension.equals("png") || extension.equals("PNG")) {
					
					IView.list.add(ff[i].getAbsolutePath());
					String tmp = new String(root + File.separator + "_thumbnail_IView");
					File f = new File(tmp);
					
					//int tmp = IView.list.get(IView.list.size() - 1).lastIndexOf(File.separator);
					String temp = tmp + File.separator + ff[i].getName();
										
					if(thumbexist) {
						// Synchronization between this.Folder and thumbnail_Folder
						File[] thumbf = f.listFiles();
						boolean exist = true;
						int x = 0;
						for ( ; x < thumbf.length ; ++x) {
							if(thumbf[x].isFile()) {
								if(ff[i].getName().equals(thumbf[x].getName())) {
									IView.thumbroot.add(thumbf[x].getAbsolutePath());
									IView.thumbworklist.add("None");
									exist = false;
									break;
								}
							}
						}
						if(exist) {
							IView.thumbworklist.add(temp);
							IView.thumbroot.add(temp);
							IView.thumbworked = true;
						}
					}
					else {
						// make thumbnail_Folder		
						f.mkdirs();						
						IView.thumbroot.add(temp);
						IView.thumbworklist.add(temp);
						IView.thumbworked = true;
					}
				}
				
				// GIF will be separate 
				// because GIFImageIO has trouble in raw quality GIF Files
				if(extension.equals("gif") || extension.equals("GIF")) {
					
					//IView.list.add(ff[i].getAbsolutePath());
					
					if(thumbexist) {
						// Synchronization between this.Folder and thumbnail_Folder
					}
					else {
						// make thumbnail_Folder
						IView.thumbworked = true;
						
					}
				}
				
			}
		}
		
		// update Component
		table = new JTable(data, header);
		
		this.add(jsp_table);
		this.add(pnl_pic);
	}
	
	// delete data
	public void dataRemove() {
		data.removeAllElements();
		pic_prev.removeAll();
		table.removeAll();
		IView.list.clear();
		IView.thumbroot.clear();
		IView.thumbworklist.clear();
		this.removeAll();
	}	
	
	
	public void treeColloapse() {
		this.dataRemove();
		
		this.add(jsp_table);
		this.add(pnl_pic);
	}
	
	public void showPic(int i) {
		pic_prev.removeAll();
		pic_prev.add(new PicCtrl(IView.list.get(i), IView.thumbroot.get(i)));
		this.add(pnl_pic);
	}
}
