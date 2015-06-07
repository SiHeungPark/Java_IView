import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Image_Frame extends JFrame{
	
	public Image_Frame(String loot) {
		this.setTitle("크게 보기");
		ImageIcon ic  = new ImageIcon(loot);
	    JLabel lbl  = new JLabel(ic);
	    JScrollPane jsp = new JScrollPane(lbl);
	    
	    this.add(jsp);
	    this.setVisible(true);
	    this.pack();
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
