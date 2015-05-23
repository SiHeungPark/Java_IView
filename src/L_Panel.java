import java.awt.*;
import java.io.File;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/* Make Main Frame's Left Panel
 * Form (Width : 250, Dick.Height : 350, PictureBox.Height : 250)
 *  ---------
 * |    D    |
 * |    I    |
 * |    C    |
 * |    K    |
 * |---------|
 * | PICTURE |
 * |   BOX   |
 *  ---------
 */
class L_Panel extends JPanel {
	private DefaultMutableTreeNode root = 
			new DefaultMutableTreeNode("내 컴퓨터");
	private JTree tree = new JTree(root);
	private JScrollPane tree_jsp = new JScrollPane(tree);
	private File[] dir = File.listRoots();
	private File[] dir_toR = null;
	
	// pl will be PictureBox
	private JPanel pl= new JPanel();
	private JLabel lbl = new JLabel("PICTURE BOX");
	
	private String getPos(TreePath tp) {
		StringBuffer tmp = new StringBuffer(tp.toString());
		tmp.delete(0, 1);
		tmp.delete((tp.toString().length()-2),(tp.toString().length()-1));

		StringTokenizer tk = new StringTokenizer(tmp.toString(),",");
		String str = "";
		tk.nextToken();
		while(tk.hasMoreElements()) {
			str += tk.nextToken().trim() + File.separator;
		}
		return str;
	}
	
	public L_Panel() {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		for(int i = 0; i<dir.length; ++i) {
			DefaultMutableTreeNode tmp = 
					new DefaultMutableTreeNode(dir[i].toString());
			DefaultMutableTreeNode tmp1 = 
					new DefaultMutableTreeNode("");
			tmp.add(tmp1);
			root.add(tmp);
		}
		
		tree_jsp.setPreferredSize(new Dimension(250,350));
		lbl.setPreferredSize(new Dimension(250,250));
						
		this.add(tree_jsp);
		this.add(lbl);
	}
	
	public void getEvent(TreeExpansionEvent e) {
		if(e.getSource() == tree) {
			tree.setSelectionPath(e.getPath());
			String pos = this.getPos(e.getPath());
			if(pos == null || pos.trim().length() == 0) return;
			File f = new File(pos);
			File[] ff = f.listFiles();
			DefaultMutableTreeNode tmp = 
					(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
			tmp.removeAllChildren();
			int cnt = 0;
			if(ff != null) {
				for(int i = 0;i<ff.length;++i) {
					if(ff[i].isDirectory()) {
						DefaultMutableTreeNode tmp1 = 
								new DefaultMutableTreeNode(ff[i].getName());
						tmp1.add(new DefaultMutableTreeNode(""));
						tmp.add(tmp1);
						cnt++;
					}
				}
				if(cnt == 0)
					tmp.add(new DefaultMutableTreeNode(""));
				
				dir_toR = ff;
			}
			else {
				Error_Frame ex = new Error_Frame();
			}
		}
	}
	
	public void addTree(IView_Frame iView_Frame) {
		tree.addTreeWillExpandListener(iView_Frame);
	}
	
	
	public File[] getFile() {
		return dir_toR;
	}
	
}
