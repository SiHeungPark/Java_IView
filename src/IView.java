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
	//static R_Panel rpl = new R_Panel();
	static Right_Pnl rpl = new Right_Pnl();
	
	private File thumbFolder;
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
		//this.makeFolder();
	}
	
	/*
	private void makeFolder() {
		File thumbFolder = new File("IView");
		if(!thumbFolder.exists()) {
			thumbFolder.mkdirs();
		}
		else {
			File[] destroy = thumbFolder.listFiles();
			for(File list : destroy) list.delete();
		}
		thumbRoot = new String(thumbFolder.getAbsolutePath());
	}
	*/

	@Override
	public void treeWillExpand(TreeExpansionEvent e)
			throws ExpandVetoException {
		long start = System.currentTimeMillis();
		rpl.dataRemove();
		lpl.getEvent(e);
		File[] tmp = lpl.getFile();
		//rpl.setTable(tmp, thumbRoot);
		rpl.setTable(tmp);
		long end = System.currentTimeMillis();
		System.out.println("실행 시간 : " + (end - start) / 1000.0);
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent e)
			throws ExpandVetoException {
		rpl.treeColloapse();
		this.repaint();
	}
	
	public void getEvent() {
		lpl.addTree(this);
	}
	
	public static void getShowSignal(String str){
		lpl.showPic(str);
	}
	public static void getShowSignal(int num) {
		for(int i = 0 ; i < num ; i++) rpl.showPic(i);
	}
}


public class IView extends Thread{
	public static ArrayList <String> thumbroot = 
			new ArrayList <String> ();
	
	public static ArrayList <String> thumbworklist = 
			new ArrayList <String> ();
	
	public static ArrayList <String> list = 
			new ArrayList <String> ();
	
	public static boolean thumbworked = false;
	public static boolean recursioncall = false;
	
	private int outputready = 0;
	
	// Thread 1
	private void main_program() {
		IView_Frame frm = new IView_Frame("IView");
	}
	
	// Thread 2
	// check thumbnail signal(0.2sec)
	private void thumbprocess() {
		for(;;) {
			try {
				Thread.sleep(200);
				if(thumbworked) {
					for (int i  = 0; i < list.size() ; i++) {
						makethumbnail(list.get(i), thumbworklist.get(i));
						thumbworklist.set(i, "None");
						
						// Decide to show Preview image
						switch (outputready) {
							case 0:
								for(int j = 0 ; j < 5 - thumbworklist.size()%5 ; j++) {
									if (thumbworklist.get(i).equals("None")) outputready = 1;
									else {
										outputready = 0;
										break;
									}
								}
								
							case 1:
								IView_Frame.getShowSignal(5 - thumbworklist.size()%5);
								outputready = 2;
								break;
						}
					}
					thumbworked = false;
				} 
			}catch (InterruptedException e) { }
		}
	}
	
	/* 
	 * [[ Make Thumbnail Images ]]
	 * store thumbnail image in .thumbnail_IView folder
	 * and save it's AbsolutePath in thumbroot(for pic_prev)
	 */
	private void makethumbnail(String ori_path, String thumb_path) {
		
		if (thumb_path.equals("None")) return;
		
		File ori_name = new File(ori_path);
		File thumb_name = new File(thumb_path);
		
		try {
			BufferedImage buf_ori_img = ImageIO.read(ori_name);
			BufferedImage buf_thumb_img = 
					new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphic = buf_thumb_img.createGraphics();
			graphic.drawImage(buf_ori_img, 0, 0, 100,100,null);
			ImageIO.write(buf_thumb_img,  "jpg",  thumb_name);
		} catch (IndexOutOfBoundsException | IOException e){ };
		
		// Recursion (if try to index another directory during make thumbnails)
		if(recursioncall) {
			//this.makethumbnail();
			recursioncall = false;
		}
		
		
	}
	
	@Override
	public void run() {
		this.main_program();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IView view = new IView();
		view.start();
		view.thumbprocess();
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