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
	private JPanel pic_prev = new JPanel();
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private Vector header = new Vector();
	private Vector data = new Vector();
	private JTable table;
	private JScrollPane table_jsp;
	public JScrollPane pic_jsp;
	
	private int identifier = 0;
	
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
				String tmp = ff[i].getName().substring(n+1);
				
				// make thumbnail image and show it
				if(tmp.equals("jpg") || tmp.equals("JPG") || 
					tmp.equals("png") || tmp.equals("PNG") ||
					tmp.equals("gif") || tmp.equals("GIF") 	) {

					IView.list.add(ff[i].getAbsolutePath());
					
					File ori_name = new File(ff[i].getAbsolutePath());
					File thumb_name = new File(thumbRoot + IView.list.size() + "." + tmp);
					
		            try {
					BufferedImage buf_ori_img = ImageIO.read(ori_name);
		            BufferedImage buf_thumb_img = 
		            		new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
		            Graphics2D graphic = buf_thumb_img.createGraphics();
		            graphic.drawImage(buf_ori_img, 0, 0, 100, 100, null);
					ImageIO.write(buf_thumb_img, "jpg", thumb_name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
					pic_prev.add(new PicCtrl(thumb_name.toString(),IView.list.get(IView.list.size() - 1)));
					
					//pic_prev.add(new PicCtrl(list.get(list.size()-1), true));	
							
				}
			}
		}
		table = new JTable(data, header);
		jsp.setLeftComponent(table_jsp);
		jsp.setRightComponent(pic_jsp);
	}
	
	// delete data
	public void dataRemove() {
		data.removeAllElements();
		IView.list.clear();
		pic_prev.removeAll();
		table.removeAll();
	}
	
	public void treeColloapse() {
		data.removeAllElements();
		IView.list.clear();
		pic_prev.removeAll();
		table.removeAll();
		jsp.setLeftComponent(table_jsp);
		jsp.setRightComponent(pic_jsp);
	}
}
