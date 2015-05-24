import java.awt.*;
import java.io.File;

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
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent e)
			throws ExpandVetoException {
		long start = System.currentTimeMillis();
		rpl.dataRemove();
		lpl.getEvent(e);
		File[] tmp = lpl.getFile();
		rpl.setTable(tmp);
		long end = System.currentTimeMillis();
		System.out.println("실행 시간 : " + (end - start) / 1000.0);
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent e)
			throws ExpandVetoException {
	}
	
	public void getEvent() {
		lpl.addTree(this);
	}
	
	public static void getClickSignal(String str){
		lpl.showPic(str);
	}
}


public class IView {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new IView_Frame("IView");
	}
}

// Error!
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