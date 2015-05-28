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
		pnl_pic.setPreferredSize(new Dimension(650,150));
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
		for (int i = 0;i<ff.length;++i) 
			if(ff[i].isDirectory()) 
				if(ff[i].getName() == ".thumbnailIView") 
					thumbexist = true;
		
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
					
					
					
					if(true) {
						// Synchronization between this.Folder and thumbnail_Folder
						File f = new File(root + File.separator + "_thumbnail_IView");
						File[] thumbf = f.listFiles();
						for (int x = 0 ; x < thumbf.length ; ++x) {
							if(ff[i].getName().equals(thumbf[x].getName())) 
								IView.thumbroot.add(thumbf[x].getAbsolutePath());
							else {
								int tmp = IView.list.get(IView.list.size() - 1).lastIndexOf(File.separator);
								IView.thumbroot.add(IView.list.get(IView.list.size() - 1).substring(0, tmp) + File.separator + "_thumbnail_IView" + File.separator + ff[i].getName());
								IView.thumbworked = true;
							}
						}
					}
					else {
						// make thumbnail_Folder
						
					}
				}
				
				// GIF will be separate 
				// because GIFImageIO has trouble in raw quality GIF Files
				if(extension.equals("gif") || extension.equals("GIF")) {
					
					IView.list.add(ff[i].getAbsolutePath());
					
					if(thumbexist) {
						// Synchronization between this.Folder and thumbnail_Folder
					}
					else {
						// make thumbnail_Folder
						
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
		IView.list.clear();
		pic_prev.removeAll();
		table.removeAll();
		this.removeAll();
	}	
	
	
	public void treeColloapse() {
		data.removeAllElements();
		IView.list.clear();
		pic_prev.removeAll();
		table.removeAll();
		
		this.removeAll();
		this.add(jsp_table);
		this.add(pnl_pic);
	}
}
