package projekt_inzynierski_brick_breaker;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import gui.*;


@SuppressWarnings("serial")
public class BrickBreaker extends JFrame{
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static final int WIDTH =(int) screenSize.getWidth(); ;
	public static final int HEIGHT =(int) screenSize.getHeight();;
	public static JFrame start_window = new JFrame();
	
	public static int option=1;
	public static void main(String[] args) {
		
	
		
	
		
	
		Start_window start = new Start_window();
			
			start_window.add(start);
			start_window.setVisible(true);
			start_window.setSize(WIDTH, HEIGHT);
			start_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			start_window.setName("Start");
			start_window.setVisible(true);

	    }
		
		
	
		
	
	
		
	}
	

