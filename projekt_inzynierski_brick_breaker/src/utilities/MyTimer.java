package utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class MyTimer implements ActionListener{
	private int elapsed_time=0;
	
	public void start() {
		myTimer.start();
	}
	Timer myTimer = new Timer(1000,this);
	@Override
	public void actionPerformed(ActionEvent e) {
		elapsed_time++;
		
	}
	
	public int get_elapsed_time() {
		return elapsed_time;
	}

	public void stop() {
		// TODO Auto-generated method stub
		myTimer.stop();
	}
	public void reset() {
		// TODO Auto-generated method stub
		elapsed_time=0;
	}
}
