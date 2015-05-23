import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

	// draw image in pic_prev
	class PicCtrl extends JPanel implements MouseListener{
		BufferedImage image;
		private String ref;
		private int xlen, ylen;
		
		public PicCtrl(String str, boolean resizing) {
			File sourceimage = new File(str);
			ref = new String(str);
			try {
				image = ImageIO.read(sourceimage);
			} catch (IOException e) { }
			
			if(resizing) {
				xlen = 105 * image.getWidth()/image.getHeight();
				ylen = 105;
			}
			this.addMouseListener(this);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, xlen, ylen, this);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(xlen, ylen);
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println(ref);
			
		}
		
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}