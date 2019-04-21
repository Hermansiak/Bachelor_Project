package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import games_elements.Ball;
import projekt_inzynierski_brick_breaker.BrickBreaker;
import utilities.EndGame_ops;
import utilities.GazeListener;
import utilities.MyTimer;
import utilities.Scanner;
import utilities.Sort;

@SuppressWarnings("serial")
public class Labirynth_window extends JPanel implements ActionListener, KeyListener{



	private File labirynth_high_score;

	private BufferedImage labirynth_image_1;
	private int font_size=30, gratzX=BrickBreaker.WIDTH/2 - 250, gratzY=40;;
	Timer t = new Timer(1,this);
	Ball ball = new Ball();
	boolean countdown_bool=true, white_line_detected=false, timer_initialized=false, init= false,  reversed_order=false, scanned = false;
	MyTimer labirynth_timer= new MyTimer();
	EndGame_ops end_case=EndGame_ops.UNKNOWN, test;
	private TextField nickname_field= new TextField();
	private String nickname;
	private int max_length=20, countdown=3, counter=0;
	Sort sorter_for_labirynth= new Sort();
	Font Nickname_font= new Font("ALGERIAN",Font.PLAIN,font_size);
	int match, match2;

	Scanner start_scan= new Scanner();
	public Labirynth_window() {
		this.labirynth_high_score= new File("highscores\\Labirynth.txt");
		if(!Start_window.eyetracker_controls) {
			ball.set_ball_velX(3);
			ball.set_ball_velY(3);
		}else {
			ball.set_ball_velX(1);
			ball.set_ball_velY(1);
		}

		try {
			this.labirynth_image_1=ImageIO.read(new File("graphics/labirynth1.png"));
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		t.start();
		this.addKeyListener(this);
		setFocusable(true);
	}


	public void paintComponent(Graphics painter) {
		super.paintComponent(painter);
		setFocusable(true);
		Graphics2D g2= (Graphics2D) painter;
		this.setVisible(true);
		end_case=end_case.get_option();
		switch(end_case) {
		case UNKNOWN:
			painter.setColor(Color.BLACK);
			painter.fillRect(0, 0, BrickBreaker.WIDTH,BrickBreaker.HEIGHT );
			painter.drawImage(labirynth_image_1,0,0,this);

			ball.draw_labirynth_ball(g2);


			if(labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX(), ball.get_labirynth_ball_middle_posY())==Color.BLUE.getRGB()) {
				end_case.set_option("win");
				labirynth_timer.stop();
			}

			if(countdown>0 && Start_window.eyetracker_controls) {
				painter.setColor(Color.YELLOW);
				painter.setFont(new Font("Algerian", Font.PLAIN, font_size+300));
				painter.drawString(Integer.toString(countdown), BrickBreaker.WIDTH/2-50,BrickBreaker.HEIGHT/2);

			}
			painter.setFont(new Font("Algerian", Font.PLAIN, font_size));
			painter.drawString("time elapsed: "+Integer.toString(labirynth_timer.get_elapsed_time()), (BrickBreaker.WIDTH/2) -100,BrickBreaker.HEIGHT-font_size);

			g2.setColor(Color.RED);
			Ellipse2D punkt = new Ellipse2D.Double(GazeListener.get_x(),GazeListener.get_y(),3,3);
			g2.fill(punkt);
			break;
		case WIN:
			if(!init) {

				nickname_field.setBounds(BrickBreaker.WIDTH/2 -("TYPE YOUR NICKNAME").length()/2*font_size, BrickBreaker.HEIGHT/2 + 6*font_size, ("TYPE YOUR NICKNAME").length()*font_size, 2*font_size);
				this.add(nickname_field);
				nickname_field.requestFocus();
				nickname_field.addKeyListener(this);
				nickname_field.setFont(Nickname_font);
				init=true;

			}


			painter.setFont(new Font("Algerian", Font.BOLD, font_size+20));
			painter.setColor(Color.BLUE);
			painter.drawString("CONGRATULATIONS", gratzX-5, gratzY-5);
			painter.setColor(Color.BLACK);
			painter.drawString("CONGRATULATIONS", gratzX-4, gratzY-4);
			painter.setColor(Color.YELLOW);
			painter.drawString("CONGRATULATIONS", gratzX-3, gratzY-3);
			painter.setColor(Color.CYAN);
			painter.drawString("CONGRATULATIONS", gratzX-2, gratzY-2);
			painter.setColor(Color.RED);
			painter.drawString("CONGRATULATIONS", gratzX-1, gratzY-1);
			painter.setColor(Color.RED);
			painter.drawString("CONGRATULATIONS", gratzX, gratzY);

			painter.drawString("TIME ELAPSED: " + Integer.toString(labirynth_timer.get_elapsed_time()), BrickBreaker.WIDTH/2 - ("TIME ELAPSED: ".length() + Integer.toString(labirynth_timer.get_elapsed_time()).length())/2*font_size , 6*font_size);
			painter.drawString("TYPE YOUR NICKNAME",BrickBreaker.WIDTH/2 -("TYPE YOUR NICKNAME").length()/2*font_size,BrickBreaker.HEIGHT/2 + 4*font_size);
			painter.setFont(new Font("Times new roman", Font.BOLD, font_size/2));
			painter.drawString("(max 20 characters, rest won't be taken into account)",BrickBreaker.WIDTH/2 -3*"(max 20 characters, rest won't be taken into account)".length() ,BrickBreaker.HEIGHT/2 + 5*font_size);
			painter.drawString("PRESS ENTER",BrickBreaker.WIDTH/2 - 50, BrickBreaker.HEIGHT-font_size);

			break;
		default:
			painter.setColor(Color.BLACK);
			painter.fillRect(0, 0, BrickBreaker.WIDTH,BrickBreaker.HEIGHT );
			painter.drawImage(labirynth_image_1,0,0,this);

			ball.draw_labirynth_ball(g2);


			if(labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX(), ball.get_labirynth_ball_middle_posY())==Color.BLUE.getRGB()) {
				end_case.set_option("win");
				labirynth_timer.stop();
			}

			if(countdown>0 && Start_window.eyetracker_controls) {
				painter.setColor(Color.YELLOW);
				painter.setFont(new Font("Algerian", Font.PLAIN, font_size+300));
				painter.drawString(Integer.toString(countdown), BrickBreaker.WIDTH/2-50,BrickBreaker.HEIGHT/2);

			}
			painter.setFont(new Font("Algerian", Font.PLAIN, font_size));
			painter.drawString("time elapsed: "+Integer.toString(labirynth_timer.get_elapsed_time()), (BrickBreaker.WIDTH/2) -100,BrickBreaker.HEIGHT-font_size);
			test=end_case.get_option();
			g2.setColor(Color.RED);
			Ellipse2D punkt2 = new Ellipse2D.Double(GazeListener.get_x(),GazeListener.get_y(),3,3);
			g2.fill(punkt2);
			break;
		}


	}




	@Override
	public void keyPressed(KeyEvent e) {
		


		int code = e.getKeyCode();
		end_case=end_case.get_option();
		switch(end_case) {
		default:
			break;

		case UNKNOWN:
			if(!Start_window.eyetracker_controls) {
				if(!timer_initialized){
					labirynth_timer.start();
					timer_initialized=true;
				}
					
				if(code== KeyEvent.VK_LEFT) {

					for(int i=0;i<=ball.get_ball_velX()+1;i++) {
						match=labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX()-i, ball.get_labirynth_ball_middle_posY());

						if(match==Color.WHITE.getRGB())
							white_line_detected=true;
					}

					if(!white_line_detected) {
						ball.move_ball_left();
					}
				}
				if(code== KeyEvent.VK_RIGHT) {
					for(int i=0;i<=ball.get_ball_velX()+1;i++) {
						match=labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX()+i, ball.get_labirynth_ball_middle_posY());

						if(labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX()+i, ball.get_labirynth_ball_middle_posY())==Color.WHITE.getRGB())
							white_line_detected=true;
					}

					if(!white_line_detected) {
						ball.move_ball_right();
					}

				}
				if(code== KeyEvent.VK_UP) {
					for(int i=0;i<=ball.get_ball_velY()+1;i++) {
						match=labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX()-i, ball.get_labirynth_ball_middle_posY());

						if(labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX(), ball.get_labirynth_ball_middle_posY()-i)==Color.WHITE.getRGB())
							white_line_detected=true;
					}

					if(!white_line_detected) {
						ball.move_ball_up();
					}

				}
				if(code== KeyEvent.VK_DOWN) {
					for(int i=0;i<=ball.get_ball_velY()+1;i++) {
						match=labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX()-i, ball.get_labirynth_ball_middle_posY());
						match2=Color.WHITE.getRGB();
						if(labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX(), (ball.get_labirynth_ball_middle_posY())+i)==Color.WHITE.getRGB())
							white_line_detected=true;
					}

					if(!white_line_detected) {
						ball.move_ball_down();
					}

				}

				match=labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX(),ball.get_labirynth_ball_middle_posY());
				match2=Color.BLUE.getRGB();
				if(labirynth_image_1.getRGB(ball.get_labirynth_ball_middle_posX(),ball.get_labirynth_ball_middle_posY())==Color.BLUE.getRGB()) {// && match >= -16776970) {
					end_case.set_option("win");
					labirynth_timer.stop();
				}


				white_line_detected=false;
			
		}
			break;




		case WIN:
			if(code==KeyEvent.VK_ENTER) {
				nickname=nickname_field.getText();
				if(nickname.length()>max_length) {
					nickname=nickname.substring(0, max_length);
				}
				if(nickname.isEmpty()) {
					nickname="anonymous";
				}
				System.out.println(nickname);
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter(labirynth_high_score, true));
					writer.write(labirynth_timer.get_elapsed_time() + " " + nickname +"\n");
					writer.newLine();
					writer.close();
					sorter_for_labirynth.sorter(labirynth_high_score,reversed_order);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				System.exit(0);
			}
			break;

		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	
		repaint();
		
		if(!scanned) {
		
			start_scan.scan_image(labirynth_image_1, ball);
			
			scanned=true;
		}
		if(Start_window.eyetracker_controls) {

			if(countdown_bool) {
				if(countdown>0) {


					counter ++;

					if(counter>=80) {
						countdown--;
						counter=0;
					}


				}else {
					counter ++;

					if(counter>=3) {
						ball.move_labirynth_ball(labirynth_image_1);
						countdown_bool=false;
						labirynth_timer.reset();
						labirynth_timer.start();
					}

				}
			}else ball.move_labirynth_ball(labirynth_image_1);
			repaint();
			t.restart();
		}




		
	}

}
