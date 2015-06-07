import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Image_Frame extends JFrame{
	private BufferedImage buffer;
	
	public Image_Frame(String loot) {
		try { buffer = ImageIO.read(new File(loot)); } catch (IOException e) { }
		this.setTitle("크게 보기");
		this.setSize(buffer.getWidth(), buffer.getHeight());
		
		ImageIcon ic  = new ImageIcon(loot);
	    JLabel lbl  = new JLabel(ic);
	    
	    this.add(lbl);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
