import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/* Make Main Frame
 * Form (V : 900, H : 600)
 *  ------------------------------------
 * |    D    |                          |
 * |    I    |                          |
 * |    C    |         ICONS            |
 * |    K    |      (in folder)         |
 * |---------|                          |
 * | PICTURE |--------------------------|
 * |   BOX   |     PICTURE PREVIEW      |
 *  ------------------------------------
 */


class IView_Frame extends JFrame implements TreeWillExpandListener {
	private JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	static L_Panel lpl = new L_Panel();
	static R_Panel rpl = new R_Panel();
	//static Right_Pnl rpl = new Right_Pnl();
	
	private String thumbRoot;
	
	public IView_Frame(String title) {
		super(title);

		this.init();
		this.start();
		
		this.setSize(900,600);
		int xpos = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int ypos = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		this.setLocation(xpos,ypos);
		
		this.setResizable(false);
		this.setVisible(true);
	}	
	private void init() {
		this.setLayout(new BorderLayout());
		jsp.setLeftComponent(lpl);
		jsp.setRightComponent(rpl);
		jsp.setOneTouchExpandable(true);
		jsp.setResizeWeight(0.274);
		this.add("Center", jsp);
	}
	private void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getEvent();
		this.makeFolder();
	}
	private void makeFolder() {
		File thumbFolder = new File("_thumbnail_IView");
		if(!thumbFolder.exists()) {
			thumbFolder.mkdirs();
		}
		thumbRoot = new String(thumbFolder.getAbsolutePath());
	}
	
	
	@Override
	public void treeWillExpand(TreeExpansionEvent e)
			throws ExpandVetoException {
		rpl.dataRemove();
		lpl.getEvent(e);
		File[] tmp = lpl.getFile();
		rpl.setTable(tmp, thumbRoot);
		
		(new Thread(new Make_thumbnail(rpl, this))).start();
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent e)
			throws ExpandVetoException {
		rpl.treeColloapse();
		lpl.treeColloapse();
		this.repaint();
	}
	
	public void getEvent() {
		lpl.addTree(this);
	}
	
	public static void getShowSignal(String str){
		lpl.showPic(str);
	}
}

