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
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	
	public L_Panel() {
		this.setLayout(new BorderLayout());
		
		for(int i = 0; i<dir.length; ++i) {
			DefaultMutableTreeNode tmp = 
					new DefaultMutableTreeNode(dir[i].toString());
			DefaultMutableTreeNode tmp1 = 
					new DefaultMutableTreeNode("");
			tmp.add(tmp1);
			root.add(tmp);
		}
		
		tree_jsp.setPreferredSize(new Dimension(250,350));
		pl.setPreferredSize(new Dimension(250,250));
		
		jsp.setLeftComponent(tree_jsp);
		jsp.setRightComponent(pl);
		jsp.setResizeWeight(0.5833);
		this.add("Center", jsp);
	}
	
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
	
	
	public void getEvent(TreeExpansionEvent e) {
		pl.removeAll();
		jsp.setRightComponent(pl);
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
	
	public void showPic(String str) {
		pl.removeAll();
		pl.setLayout(new GridLayout(1,1));
		pl.add(new PicCtrl(str, false));
		jsp.setRightComponent(pl);
	}
}
