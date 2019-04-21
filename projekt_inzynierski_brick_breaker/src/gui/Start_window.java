package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.theeyetribe.client.GazeManager;
import com.theeyetribe.client.GazeManager.ApiVersion;
import com.theeyetribe.client.GazeManager.ClientMode;

import projekt_inzynierski_brick_breaker.BrickBreaker;
import utilities.GazeListener;

@SuppressWarnings("serial")
public class Start_window extends JPanel implements ActionListener, KeyListener {

	private BufferedImage img, img2;
	private int menu_option=0;
	private boolean controls_chosen = false, eyetracker_ready=false;
	public static boolean eyetracker_controls=false;
	Timer t = new Timer(4,this);
	Brick_breaker_window brick_breaker_game = new Brick_breaker_window();
	Labirynth_window labirynth_game= new Labirynth_window();
	public static HighScores_window highscore= new HighScores_window();
	public Start_window() {

		this.addKeyListener(this);
		t.start();


		try {
			this.img= ImageIO.read(new File("graphics/Start_window.png"));
			this.img2= ImageIO.read(new File("graphics/controls.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setFocusable(true);
	}

	public void paintComponent(Graphics g1) {
		setFocusable(true);
		super.paintComponent(g1);
		g1.setColor(Color.BLACK);
		g1.setFont(new Font("Algerian", Font.PLAIN, 32));
		g1.fillRect(0, 0, BrickBreaker.WIDTH,  BrickBreaker.HEIGHT);
		if(!controls_chosen && !eyetracker_ready) {
			g1.drawImage(img2,((BrickBreaker.WIDTH/2)-(img2.getWidth()/2)),((BrickBreaker.HEIGHT/2)-(img2.getHeight()/2)),this);
			switch(menu_option) {
			case 1:
				g1.setColor(Color.ORANGE);
				g1.drawRect(((BrickBreaker.WIDTH/2)-(img2.getWidth()/2))+110, ((BrickBreaker.HEIGHT/2)-(img2.getHeight()/2))+60, 160, 40);
				break;
			case 2:
				g1.setColor(Color.ORANGE);
				g1.drawRect(((BrickBreaker.WIDTH/2)-(img2.getWidth()/2))+25, ((BrickBreaker.HEIGHT/2)-(img2.getHeight()/2))+130, 360, 35);
				break;
			case 10:
				g1.setColor(Color.BLACK);
				g1.fillRect(0, 0, BrickBreaker.WIDTH,  BrickBreaker.HEIGHT);
				g1.setColor(Color.YELLOW);
				g1.drawString("RUN EYE TRACKER SERVER", BrickBreaker.WIDTH/2 -150, BrickBreaker.HEIGHT/2);
				g1.drawString("PRESS ENTER TO TRY AGAIN", BrickBreaker.WIDTH/2 -155, BrickBreaker.HEIGHT/2 + 40);
				g1.setColor(Color.BLACK);
				break;
			}
		}else {

			g1.drawImage(img,((BrickBreaker.WIDTH/2)-(img.getWidth()/2)),0,this);
			g1.setColor(Color.YELLOW);
			g1.drawString("BRICK BREAKER", BrickBreaker.WIDTH/4, (BrickBreaker.HEIGHT/4)*3);
			g1.drawString("LABYRINTH", 3*(BrickBreaker.WIDTH/4), (BrickBreaker.HEIGHT/4)*3);
			g1.drawString("HIGH SCORES", BrickBreaker.WIDTH/10, (BrickBreaker.HEIGHT/10)*9);
			g1.drawString("EXIT", (BrickBreaker.WIDTH/10)*9, (BrickBreaker.HEIGHT/10)*9);
			switch(menu_option) {
			case 0: 
				break;
			case 1: 
				g1.drawRect(BrickBreaker.WIDTH/4,(BrickBreaker.HEIGHT/4)*3 -32,250,35);
				break;
			case 2: 
				g1.drawRect( 3*(BrickBreaker.WIDTH/4), (BrickBreaker.HEIGHT/4)*3 -32,190,35);
				break;
			case 3: 
				g1.drawRect((BrickBreaker.WIDTH/10)*9, (BrickBreaker.HEIGHT/10)*9 -32,80,35);
				break;
			case 4: 
				g1.drawRect(BrickBreaker.WIDTH/10-5, (BrickBreaker.HEIGHT/10)*9 -32,220,35);
				break;
			}
		}


	}











	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(controls_chosen) {
			if(code== KeyEvent.VK_LEFT) {

				switch(menu_option) {
				case 0: 
					menu_option=3;
					break;
				case 1: 
					menu_option=4;
					break;
				case 2: 
					menu_option=1;
					break;
				case 3: 
					menu_option=2;
					break;
				case 4: 
					menu_option=3;
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
					menu_option=4;
					break;
				case 4: 
					menu_option=1;
					break;
				}
			}



			if(code== KeyEvent.VK_ENTER){

				switch(menu_option) {
				case 0: 
					break;
				case 1: 

					JFrame brick_breaker = new JFrame();

					brick_breaker.add(brick_breaker_game);
					brick_breaker.setVisible(true);
					brick_breaker.setSize(BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
					brick_breaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					brick_breaker.setTitle("BrickBreaker");
					brick_breaker.setResizable(false);
					BrickBreaker.option=2;
					BrickBreaker.start_window.dispose();
					break;
				case 2: 
					JFrame labirynth = new JFrame();
					labirynth.add(labirynth_game);
					labirynth.setVisible(true);
					labirynth.setSize(BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
					labirynth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					labirynth.setTitle("BrickBreaker");
					labirynth.setResizable(false);
					BrickBreaker.start_window.dispose();
					break;
				case 3: 
					System.exit(0);
					break;
				case 4: 
					
					JFrame HighScore = new JFrame();
					HighScore.add(highscore);
					HighScore.setVisible(true);
					HighScore.setSize(BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
					HighScore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					HighScore.setTitle("highscore");
					HighScore.setResizable(false);
					
					BrickBreaker.start_window.setVisible(false);
					
					
					break;
				}
			}
		}else {


			if(code== KeyEvent.VK_UP) {
				switch(menu_option) {
				case 0: 
					menu_option=2;
					break;
				case 1: 
					menu_option=2;
					break;
				case 2:
					menu_option=1;
					break;
				}
			}


				if(code== KeyEvent.VK_DOWN) {
					switch(menu_option) {
					case 0: 
						menu_option=1;
						break;
					case 1: 
						menu_option=2;
						break;
					case 2:
						menu_option=1;
						break;
					}
				}

				if(code== KeyEvent.VK_ENTER){

					switch(menu_option) {
					case 0: 
						break;
					case 1: 
						menu_option=0;
						controls_chosen=true;
						break;
					case 2:
						GazeManager gm = GazeManager.getInstance();
						
				        if(gm.activate(ApiVersion.VERSION_1_0, ClientMode.PUSH)) {
				        	eyetracker_ready=true;
				        	controls_chosen=true;
				        	eyetracker_controls=true;
				        	menu_option=0;
				        }else menu_option=10;
				   				        					        
						final GazeListener gazeListener = new GazeListener();
						gm.addGazeListener(gazeListener);
						break;
					case 10:
						menu_option=0;
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

}
