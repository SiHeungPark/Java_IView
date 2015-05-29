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
	class Pic_Ctrl extends JPanel implements MouseListener{
		BufferedImage image;
		private String ori;
		private String thumb;
		private int xlen, ylen;
		
		public Pic_Ctrl(String str, String loot) {
			File sourceimage = new File(loot);
			thumb = new String(loot);
			ori = new String(str);
			try { image = ImageIO.read(sourceimage); } catch (IndexOutOfBoundsException | IOException e) { }
			
			
			xlen = 100;
			ylen = 100;
			
			/*
			else {
				if(image.getWidth() >= image.getHeight()) {
					xlen = 250;
					ylen = 230;// * image.getHeight() / image.getWidth();
				}
				
				else {
					xlen = 250 * image.getWidth() / image.getHeight();
					ylen = 230;
				}
			}
			*/
			this.addMouseListener(this);
		}
		
		public Pic_Ctrl(String loot, int x, int y) {
			File sourceimage = new File(loot);
			try { image = ImageIO.read(sourceimage); } catch (IOException e) { }
			
			
			if(image.getWidth() >= image.getHeight()) {
				xlen = 250;
				ylen = 230 * image.getHeight() / image.getWidth();
			}
			
			else {
				xlen = 250 * image.getWidth() / image.getHeight();
				ylen = 230;
			}
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
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getClickCount() == 2) new Image_Frame(ori);
			else if (e.getClickCount() == 1) IView_Frame.getShowSignal(thumb);
			else;
		}
		
		
		
		
		
		
		
		
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}


	}