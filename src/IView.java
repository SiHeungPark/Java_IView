import java.awt.*;
import java.io.File;
import java.util.*;

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

	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	L_Panel lpl = new L_Panel();
	R_Panel rpl = new R_Panel();
	
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
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		jsp.setLeftComponent(lpl);
		jsp.setRightComponent(rpl);
		jsp.setOneTouchExpandable(true);
		jsp.setResizeWeight(0.278);
		con.add("Center", jsp);
	}
	
	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getEvent();
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent e)
			throws ExpandVetoException {
		rpl.dataRemove();
		lpl.getEvent(e);
		File[] tmp = lpl.getFile();
		rpl.setTable(tmp);
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent e)
			throws ExpandVetoException {
	}
	
	public void getEvent() {
		lpl.addTree(this);
	}
}


public class IView {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IView_Frame frm = new IView_Frame("IView");
	}
}

class Error_Frame extends JDialog {
	private JLabel lbl = new JLabel("       접근할 수 없는 파일입니다");
	public Error_Frame() {
		this.setTitle("오류");
		this.setModal(true);
		this.setMinimumSize(new Dimension(200,100));
		this.setLayout(new BorderLayout(10,10));
		this.add("Center", lbl);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
}