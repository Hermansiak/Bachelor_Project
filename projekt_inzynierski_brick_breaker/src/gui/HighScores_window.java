package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import projekt_inzynierski_brick_breaker.BrickBreaker;
import utilities.Sort;

@SuppressWarnings("serial")
public class HighScores_window extends JPanel implements ActionListener, KeyListener{

	Timer t = new Timer(4,this);
	private BufferedImage Highscore_image;
	private int menu_option=0,counter=1;
	private boolean brick_breaker_highscore=false, labirynth_highscore=false;
	private File brick_breaker, labirynth;
	private String[] nicknames= {null,null,null,null,null};
	Sort sorter= new Sort();


	public HighScores_window() {

		this.addKeyListener(this);
		t.start();


		try {
			this.Highscore_image= ImageIO.read(new File("graphics/Highscores.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.brick_breaker=new File("highscores/BrickBreaker.txt");
		this.labirynth= new File("highscores/Labirynth.txt");
		setFocusable(true);
	}




	public void paintComponent(Graphics g1) {
		setFocusable(true);
		super.paintComponent(g1);
		g1.setColor(Color.BLACK);
		g1.setFont(new Font("Algerian", Font.PLAIN, 32));
		g1.fillRect(0, 0, BrickBreaker.WIDTH,  BrickBreaker.HEIGHT);

		if(brick_breaker_highscore) {
			g1.setColor(Color.ORANGE);
			g1.setFont(new Font("Algerian", Font.PLAIN,55));
			g1.drawString("BRICK BREAKER GAME HIGHSCORE", (BrickBreaker.WIDTH/2)-400, 70);
			g1.setFont(new Font("Algerian", Font.PLAIN,40));
			for(int i=1;i<=5;i++)
				g1.drawString( Integer.toString(i) + ". " + nicknames[i-1], (BrickBreaker.WIDTH/2)-100, 200 + 80*i);

		}else if(labirynth_highscore) {
			g1.setColor(Color.ORANGE);
			g1.setFont(new Font("Algerian", Font.PLAIN,55));
			g1.drawString("LABIRYNTH GAME HIGHSCORE", (BrickBreaker.WIDTH/2)-400, 70);
			g1.setFont(new Font("Algerian", Font.PLAIN,40));
			for(int i=1;i<=5;i++)
				g1.drawString( Integer.toString(i) + ". " + nicknames[i-1], (BrickBreaker.WIDTH/2)-200, 200 + 80*i);

		}else {
			g1.drawImage(Highscore_image,((BrickBreaker.WIDTH/2)-(Highscore_image.getWidth()/2)),0,this);
			g1.setColor(Color.ORANGE);

			switch(menu_option) {
			case 0: 
				break;
			case 1: 
				g1.drawRect(((BrickBreaker.WIDTH/2)-(Highscore_image.getWidth()/2))+60,300,240,35);
				break;
			case 2: 
				g1.drawRect( ((BrickBreaker.WIDTH/2)-(Highscore_image.getWidth()/2))+540, 300,175,35);
				break;
			case 3: 
				g1.drawRect(((BrickBreaker.WIDTH/2)-(Highscore_image.getWidth()/2))+340, 500,115,35);
				break;

			}

		}



	}
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();


		if(code== KeyEvent.VK_ENTER){

			if(brick_breaker_highscore)
				brick_breaker_highscore=false;

			if(labirynth_highscore)
				labirynth_highscore=false;

			switch(menu_option) {
			case 0: 
				break;
			case 1: 
				brick_breaker_highscore=true;
				menu_option=0;
				try{

					sorter.sorter(brick_breaker,true);
					FileInputStream fstream = new FileInputStream(brick_breaker);

					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;

					while ((strLine = br.readLine()) != null)   {


						nicknames[counter-1]=strLine;
						counter++;
						if(counter>5) {
							counter=1;
							break;
						}
					}

					in.close();
				}catch (Exception e1){
					System.err.println("Error: " + e1.getMessage());
				}
				break;
			case 2: 
				labirynth_highscore=true;
				menu_option=0;
				try{

					sorter.sorter(labirynth,false);

					FileInputStream fstream = new FileInputStream(labirynth);

					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;

					while ((strLine = br.readLine()) != null)   {


						nicknames[counter-1]=strLine;
						counter++;
						if(counter>5) {
							counter=1;
							break;
						}
					}
					//Close the input stream
					in.close();
				}catch (Exception e1){//Catch exception if any
					System.err.println("Error: " + e1.getMessage());
				}
				break;
			case 3: 
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				BrickBreaker.start_window.setVisible(true);
				break;

			}
		}
		if(!brick_breaker_highscore && !labirynth_highscore) {
			if(code== KeyEvent.VK_LEFT){
				switch(menu_option) {
				case 0: 
					menu_option=3;
					break;
				case 1: 
					menu_option=3;
					break;
				case 2: 
					menu_option=1;
					break;
				case 3: 
					menu_option=2;
					break;

				}
			}
			if(code== KeyEvent.VK_RIGHT){
				switch(menu_option) {
				case 0: 
					menu_option=1;
					break;
				case 1: 
					menu_option=2;
					break;
				case 2: 
					menu_option=3;
					break;
				case 3: 
					menu_option=1;
					break;

				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

}
