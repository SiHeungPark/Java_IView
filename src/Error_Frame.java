import java.awt.*;
import javax.swing.*;

// Error!
class Error_Frame extends JDialog {
	private JLabel lbl = new JLabel("       ������ �� ���� �����Դϴ�");
	public Error_Frame() {
		this.setTitle("����");
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