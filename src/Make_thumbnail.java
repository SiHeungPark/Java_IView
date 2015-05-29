import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


class Make_thumbnail implements Runnable {
	private R_Panel rpl;
	private IView_Frame frm;
	private ArrayList <String> list;
	private ArrayList <String> thumbroot;
	private ArrayList <String> thumbworklist;

	
	@SuppressWarnings("unchecked")
	public Make_thumbnail(R_Panel rpl, IView_Frame frm) {
		this.rpl = rpl;
		this.frm = frm;
		this.list = (ArrayList<String>) rpl.list.clone();
		this.thumbroot = (ArrayList<String>) rpl.thumbroot.clone();
		this.thumbworklist = (ArrayList<String>) rpl.thumbworklist.clone();
	}
	
	
	@Override
	public void run() {
		for (int i  = 0; i < list.size() ; i++) {
			makethumbnail(rpl.list.get(i), rpl.thumbworklist.get(i));
			thumbworklist.set(i, "None");
			rpl.pic_prev.add(new Pic_Ctrl(rpl.list.get(i), rpl.thumbroot.get(i)));
		}
		rpl.setJSP();
		frm.repaint();
	}
	
	private void makethumbnail(String ori_path, String thumb_path) {	
		if (thumb_path.equals("None")) return;
		
		File ori_name = new File(ori_path);
		File thumb_name = new File(thumb_path);
		
		try {
			BufferedImage buf_ori_img = ImageIO.read(ori_name);
			
			int x = 230 * buf_ori_img.getWidth()/buf_ori_img.getHeight();
			
			BufferedImage buf_thumb_img = 
					new BufferedImage(x, 230, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphic = buf_thumb_img.createGraphics();
			graphic.drawImage(buf_ori_img, 0, 0, x,230,null);
			ImageIO.write(buf_thumb_img,  "jpg",  thumb_name);
		} catch (IndexOutOfBoundsException | IOException e){ };
	}
}
