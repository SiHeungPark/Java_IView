import java.awt.*;
import java.io.File;
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
	private JPanel pic_prev = new JPanel();
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private Vector header = new Vector();
	private Vector data = new Vector();
	private JTable table;
	private JScrollPane table_jsp;
	public JScrollPane pic_jsp;
	
	private int identifier = 0;
	private ArrayList<String> list = new ArrayList <String> ();
	
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

		table_jsp.setPreferredSize(new Dimension(650,425));
		jsp.setLeftComponent(table_jsp);
		
		pic_jsp = new JScrollPane(pic_prev);
		jsp.setRightComponent(pic_jsp);
		jsp.setOneTouchExpandable(true);
		//jsp.setResizeWeight(0.3);
		this.add("Center",jsp);
	}
	
	// set informations of files to Table
	public void setTable(File[] ff) {	
		list.clear();
		pic_prev.removeAll();
		
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
				
				// draw image in pic_prev
				if(tmp.equals("jpg") || tmp.equals("JPG") || 
					tmp.equals("png") || tmp.equals("PNG") ||
					tmp.equals("gif") || tmp.equals("GIF") 	) {
					
					list.add(ff[i].getAbsolutePath());
					pic_prev.add(new PicCtrl(list.get(list.size()-1), true));					
				}
			}
		}
		table.removeAll();
		table = new JTable(data, header);
		jsp.setLeftComponent(table_jsp);
		jsp.setRightComponent(pic_jsp);
	}
	
	// delete data
	public void dataRemove() {
		data.removeAllElements();
	}
}
