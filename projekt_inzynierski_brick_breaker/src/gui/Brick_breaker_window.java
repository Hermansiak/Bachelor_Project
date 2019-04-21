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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

import games_elements.Ball;
import games_elements.Brick;
import games_elements.Pad;
import projekt_inzynierski_brick_breaker.BrickBreaker;
import utilities.EndGame_ops;
import utilities.GazeListener;
import utilities.MyTimer;
import utilities.Sort;

@SuppressWarnings("serial")
public class Brick_breaker_window extends JPanel implements ActionListener, KeyListener {

	private File brick_breaker_high_score;
	public static double iks, igrek;
	Timer t = new Timer(3,this);
	MyTimer tim;

	int brick_posX=0, brick_posY=0, brick_width=BrickBreaker.WIDTH/10, brick_height=BrickBreaker.HEIGHT/10;
	int gratzX=BrickBreaker.WIDTH/2 - 250, gratzY=40;

	Ball ball = new Ball();
	Pad pad = new Pad();
	Brick[] brick_arr= brick_init();
	private double safety_region = 1; 
	private int font_size=30,score =0, final_score=0, lives=3;
	EndGame_ops endgame_case= EndGame_ops.UNKNOWN,end_case;
	boolean init=false, timer_initialized=false, reversed_order=true, started=false;
	private TextField nickname_field= new TextField();
	private String nickname;
	private int max_length=20,countdown=3,counter=0;
	Font Nickname_font= new Font("ALGERIAN",Font.PLAIN,font_size);
	Sort sorter= new Sort();




	public Brick_breaker_window()
	{
		this.addKeyListener(this);
		if(!Start_window.eyetracker_controls)
			t = new Timer(0,this);
		
		t.start();

		this.tim=new MyTimer();
		setFocusable(true);
		this.brick_breaker_high_score= new File("highscores\\BrickBreaker.txt");
		try {
			if (brick_breaker_high_score.createNewFile())
			{
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("File could not be created, provide necessary priviledges to the application");
			e.printStackTrace();
		}
		ball.set_ball_velY(0);
		tim.start();
	}




	public void paintComponent(Graphics g) {



		super.paintComponent(g);
		Graphics2D g2= (Graphics2D) g;

		end_case=endgame_case.get_option();
		switch(end_case) {
		case UNKNOWN:
			pad.drawPad(g2);
			ball.drawBall(g2);
			brick_draw(g2, brick_arr);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Algerian", Font.PLAIN, font_size));
			g.drawString("time elapsed: "+Integer.toString(tim.get_elapsed_time()), 0,BrickBreaker.HEIGHT-font_size);
			g.drawString("SCORE: "+Integer.toString(score), (BrickBreaker.WIDTH/2)-50,BrickBreaker.HEIGHT-font_size);
			g.drawString("LIVES: "+Integer.toString(lives), BrickBreaker.WIDTH-150,BrickBreaker.HEIGHT-font_size);


			if(countdown>0 && Start_window.eyetracker_controls) {
				g.setFont(new Font("Algerian", Font.PLAIN, font_size+300));
				g.drawString(Integer.toString(countdown), BrickBreaker.WIDTH/2-50,BrickBreaker.HEIGHT/2);

			}

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

			final_score= score+ (lives*100) + (10000/tim.get_elapsed_time() );
			g.setFont(new Font("Algerian", Font.BOLD, font_size+20));
			g.setColor(Color.BLUE);
			g.drawString("CONGRATULATIONS", gratzX-5, gratzY-5);
			g.setColor(Color.BLACK);
			g.drawString("CONGRATULATIONS", gratzX-4, gratzY-4);
			g.setColor(Color.YELLOW);
			g.drawString("CONGRATULATIONS", gratzX-3, gratzY-3);
			g.setColor(Color.CYAN);
			g.drawString("CONGRATULATIONS", gratzX-2, gratzY-2);
			g.setColor(Color.RED);
			g.drawString("CONGRATULATIONS", gratzX-1, gratzY-1);
			g.setColor(Color.RED);
			g.drawString("CONGRATULATIONS", gratzX, gratzY);

			g.drawString("SCORE: "+Integer.toString(score),BrickBreaker.WIDTH/2 -("SCORE: "+Integer.toString(score)).length()/2*font_size, 4*font_size);
			g.drawString("TIME ELAPSED: " + Integer.toString(tim.get_elapsed_time()), BrickBreaker.WIDTH/2 - ("TIME ELAPSED: ".length() + Integer.toString(tim.get_elapsed_time()).length())/2*font_size , 6*font_size);
			g.drawString("LIVES LEFT: " + Integer.toString(lives), BrickBreaker.WIDTH/2 - ("LIVES LEFT: ".length() + Integer.toString(lives).length())/2*font_size, 8*font_size);
			g.drawString("FINAL SCORE: " + Integer.toString(final_score),BrickBreaker.WIDTH/2 -("FINAL SCORE: "+Integer.toString(score)).length()/2*font_size, 10*font_size);
			g.drawString("TYPE YOUR NICKNAME",BrickBreaker.WIDTH/2 -("TYPE YOUR NICKNAME").length()/2*font_size,BrickBreaker.HEIGHT/2 + 4*font_size);
			g.setFont(new Font("Times new roman", Font.BOLD, font_size/2));
			g.drawString("(max 20 characters, rest won't be taken into account)",BrickBreaker.WIDTH/2 -3*"(max 20 characters, rest won't be taken into account)".length() ,BrickBreaker.HEIGHT/2 + 5*font_size);
			g.drawString("PRESS ENTER",BrickBreaker.WIDTH/2 - 50, BrickBreaker.HEIGHT-font_size);

			break;
		case LOST:
			if(!init) {
				nickname_field.setBounds(BrickBreaker.WIDTH/2 -("TYPE YOUR NICKNAME").length()/2*font_size, BrickBreaker.HEIGHT/2 + 6*font_size, ("TYPE YOUR NICKNAME").length()*font_size, 2*font_size);
				//nickname_field.setText(nickname);

				this.add(nickname_field);
				nickname_field.requestFocus();
				nickname_field.addKeyListener(this);
				nickname_field.setFont(Nickname_font);

				init=true;
			}

			g.setFont(new Font("Algerian", Font.BOLD, font_size+20));
			g.setColor(Color.RED);
			g.drawString("GAME OVER",BrickBreaker.WIDTH/2 - (font_size*"GAME OVER".length())/2,BrickBreaker.HEIGHT/2- 50 );
			g.drawString("SCORE: "+Integer.toString(score),BrickBreaker.WIDTH/2 -("SCORE: "+Integer.toString(score)).length()/2*font_size,BrickBreaker.HEIGHT/2 + 2*font_size);
			g.drawString("TYPE YOUR NICKNAME",BrickBreaker.WIDTH/2 -("TYPE YOUR NICKNAME").length()/2*font_size,BrickBreaker.HEIGHT/2 + 4*font_size);
			g.setFont(new Font("Times new roman", Font.BOLD, font_size/2));
			g.drawString("(max 20 characters, rest won't be taken into account)",BrickBreaker.WIDTH/2 -3*"(max 20 characters, rest won't be taken into account)".length() ,BrickBreaker.HEIGHT/2 + 5*font_size);
			g.drawString("PRESS ENTER",BrickBreaker.WIDTH/2 - 50, BrickBreaker.HEIGHT-font_size);
			break;
		}


	}


	public void actionPerformed(ActionEvent e) {

		if(Start_window.eyetracker_controls) {
			if(countdown>0) {

				counter ++;

				if(counter>=200) {
					countdown--;
					counter=0;
				}


			}else if(ball.get_ball_velY()==0) {
				ball.set_ball_velY(1);

			}
		}




		end_case=endgame_case.get_option();
		switch(end_case) {
		case UNKNOWN:
			if(Start_window.eyetracker_controls) {
				if(pad.get_pad_posX()<Pad.get_desired_pad_posX()) {
					pad.move_pad(2);
				}else pad.move_pad(-2);
			}
			if(ball.ball_hit_bottom_screen_border()) {

				ball.reset_position();
				pad.reset_position();
				ball.set_ball_velX(0);
				ball.set_ball_velY(0);
				countdown=3;
				lives--;
				if(lives<=0) {
					endgame_case.set_option("lost");
				}
			}

			if(ball.ball_hit_top_screen_border()) {
				ball.negate_velY();
				//System.exit(0);
			}

			if(ball.ball_hit_side_screen_border()) {
				ball.negate_velX();
			}

			if(pad.ball_hit_center_part_of_pad(ball.get_ball_posX(),ball.get_ball_posY())) {
				ball.negate_velY();
			}

			if(pad.ball_hit_left_part_of_pad(ball.get_ball_posX(),ball.get_ball_posY())) {

				if(ball.get_prev_posX()<=ball.get_ball_posX()) {
					ball.negate_velY();
					if(ball.get_ball_velX()==0) {
						ball.set_ball_velX(-1);
					}else ball.negate_velX();

				}else ball.negate_velY();

			}
			if(pad.ball_hit_right_part_of_pad(ball.get_ball_posX(),ball.get_ball_posY())) {
				if(ball.get_prev_posX()>=ball.get_ball_posX()) {
					ball.negate_velY();
					if(ball.get_ball_velX()==0) {
						ball.set_ball_velX(1);
					}else ball.negate_velX();
				}else ball.negate_velY();
			}


			collision_detection((ball.get_ball_posX() +ball.get_circle_radius()/2), (ball.get_ball_posY()+ball.get_circle_radius()/2), ball.get_ball_velX(), ball.get_ball_velY(), brick_arr);
			if(Brick.disabled_bricks>=brick_arr.length) {

				endgame_case.set_option("win");
				tim.stop();

			}
			ball.move_ball();
			repaint();
			break;
		case LOST:
			t.stop();
			break;
		case WIN:
			break;
		default:
			break;
		}

	}

	public void collision_detection(double x,double y, double velX, double velY, Brick[] b) {


		for(int i=0; i< b.length; i++){

			if(
					(((x==b[i].posX && y==b[i].posY) || ((x+ball.get_circle_radius()/2== b[i].posX) && (y+ball.get_circle_radius()/2)==b[i].posY)) 
							&& ball.get_prev_posX() < ball.get_ball_posX() && ball.get_prev_posY() < ball.get_ball_posY()) 
					||
					(((x==(b[i].posX + b[i].width) && y==b[i].posY) || ((x-ball.get_circle_radius()/2==(b[i].posX + b[i].width)) && (y+ball.get_circle_radius()/2)==b[i].posY) )
							&& ball.get_prev_posX() > ball.get_ball_posX() && ball.get_prev_posY() < ball.get_ball_posY())
					||
					(((x==b[i].posX && y==(b[i].posY+b[i].height)) || ((x+ball.get_circle_radius()/2== b[i].posX) && (y-ball.get_circle_radius()/2)==(b[i].posY+b[i].height)) )
							&& ball.get_prev_posX() < ball.get_ball_posX() && ball.get_prev_posY() > ball.get_ball_posY())
					||
					(((x==(b[i].posX + b[i].width) && y==(b[i].posY+b[i].height)) || ((x-ball.get_circle_radius()/2== (b[i].posX + b[i].width)) && (y-ball.get_circle_radius()/2)==(b[i].posY+b[i].height)) )
							&& ball.get_prev_posX() > ball.get_ball_posX() && ball.get_prev_posY() > ball.get_ball_posY())
					){
				if(b[i].isEnabled()) {
					b[i].disable_brick();
					score+=100;
					ball.negate_velX();
					ball.negate_velY();
				}
			}else {


				if(((x + ball.get_circle_radius()/2)>=b[i].posX && (x + ball.get_circle_radius()/2)<(b[i].posX+safety_region)) 
						&& ((y + ball.get_circle_radius()/2)>=b[i].posY && (y + ball.get_circle_radius()/2)<=(b[i].posY +b[i].height)) 
						|| (((x - ball.get_circle_radius()/2)>=(b[i].posX + b[i].width-safety_region) && ((x - ball.get_circle_radius()/2)<(b[i].posX+b[i].width))) 
								&& ((y - ball.get_circle_radius()/2)>=b[i].posY-safety_region && (y + ball.get_circle_radius()/2)<(b[i].posY +b[i].height + safety_region)))){

					if(b[i].isEnabled()) {
						b[i].disable_brick();
						score+=100;
						ball.negate_velX();
					}
				}

				if((x + ball.get_circle_radius()/2)>=b[i].posX && (x - ball.get_circle_radius()/2)<=(b[i].posX+b[i].width)){
					if(((y - ball.get_circle_radius()/2)<=(b[i].posY+b[i].height) && (y - ball.get_circle_radius()/2)>=(b[i].posY +b[i].height- safety_region)) || ((y + ball.get_circle_radius()/2) >= b[i].posY && (y + ball.get_circle_radius()/2) < b[i].posY+safety_region )) {
						if(b[i].isEnabled()) {
							b[i].disable_brick();
							score+=100;
							ball.negate_velY();
						}
					}
				}

			}
		}

	}



	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(!Start_window.eyetracker_controls) {


			

			switch(end_case) {

			case UNKNOWN:

				if(code== KeyEvent.VK_LEFT && pad.get_pad_posX()+pad.get_pad_width()/2>0) {
					if(ball.get_ball_velX()==0 && ball.get_ball_velY()==0) {
						ball.set_ball_velY(1);
					}
					for(int i=0; i<15;i++) {
						pad.move_pad(-1);
					}
				}

				if(code== KeyEvent.VK_RIGHT && pad.get_pad_posX()+pad.get_pad_width()/2<BrickBreaker.WIDTH) {
					if(ball.get_ball_velX()==0 && ball.get_ball_velY()==0) {
						ball.set_ball_velY(1);
					}
					for(int i=0; i<15;i++) {
						pad.move_pad(1);
					}
				}
				break;
			case LOST:
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
						writer = new BufferedWriter(new FileWriter(brick_breaker_high_score, true));
						if(score==0)
							writer.write("0000 " + nickname +"\n");
						else if(score<1000)
							writer.write("0"+score + " " + nickname +"\n");
						else writer.write(score + " " + nickname +"\n");
						writer.newLine();
						writer.close();
						sorter.sorter(brick_breaker_high_score,reversed_order);
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					System.exit(0);
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
						writer = new BufferedWriter(new FileWriter(brick_breaker_high_score, true));
						writer.write(final_score + " " + nickname +"\n");
						writer.newLine();
						writer.close();
						sorter.sorter(brick_breaker_high_score, reversed_order);
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					System.exit(0);
				}
				break;
			default:
				break;
			}
		}else {
			switch(end_case) {
			case LOST:
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
						writer = new BufferedWriter(new FileWriter(brick_breaker_high_score, true));
						writer.write(score + " " + nickname +"\n");
						writer.newLine();
						writer.close();
						sorter.sorter(brick_breaker_high_score,reversed_order);
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					System.exit(0);
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
						writer = new BufferedWriter(new FileWriter(brick_breaker_high_score, true));
						writer.write(final_score + " " + nickname +"\n");
						writer.newLine();
						writer.close();
						sorter.sorter(brick_breaker_high_score, reversed_order);
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					System.exit(0);
				}
				break;
			}
		}
	}


	private void brick_draw(Graphics2D g2, Brick[] brick_arr) {

		for(int i=0; i< brick_arr.length; i++){
			brick_arr[i].createRectangle(g2);		
		}

	}

	private Brick[] brick_init() {
		Brick b11=new Brick(0,0,brick_width,brick_height);
		Brick b12=new Brick(0+brick_width+40,0,brick_width,brick_height);
		Brick b13=new Brick(0+2*(brick_width+40),0,brick_width,brick_height);
		Brick b14=new Brick(0+3*(brick_width+40),0,brick_width,brick_height);
		Brick b15=new Brick(0+4*(brick_width+40),0,brick_width,brick_height);
		Brick b16=new Brick(0+5*(brick_width+40),0,brick_width,brick_height);
		Brick b17=new Brick(0+6*(brick_width+40),0,brick_width,brick_height);
		Brick b18=new Brick(0+7*(brick_width+40),0,brick_width,brick_height);

		Brick b21=new Brick(0+brick_width/2,brick_height+10,brick_width,brick_height);
		Brick b22=new Brick((0+brick_width/2+brick_width+40),brick_height+10,brick_width,brick_height);
		Brick b23=new Brick((0+brick_width/2+2*(brick_width+40)),brick_height+10,brick_width,brick_height);
		Brick b24=new Brick((0+brick_width/2+3*(brick_width+40)),brick_height+10,brick_width,brick_height);
		Brick b25=new Brick((0+brick_width/2+4*(brick_width+40)),brick_height+10,brick_width,brick_height);
		Brick b26=new Brick((0+brick_width/2+5*(brick_width+40)),brick_height+10,brick_width,brick_height);
		Brick b27=new Brick((0+brick_width/2+6*(brick_width+40)),brick_height+10,brick_width,brick_height);

		Brick b31=new Brick(0,2*(brick_height+10),brick_width,brick_height);
		Brick b32=new Brick(0+brick_width+40,2*(brick_height+10),brick_width,brick_height);
		Brick b33=new Brick(0+2*(brick_width+40),2*(brick_height+10),brick_width,brick_height);
		Brick b34=new Brick(0+3*(brick_width+40),2*(brick_height+10),brick_width,brick_height);
		Brick b35=new Brick(0+4*(brick_width+40),2*(brick_height+10),brick_width,brick_height);
		Brick b36=new Brick(0+5*(brick_width+40),2*(brick_height+10),brick_width,brick_height);
		Brick b37=new Brick(0+6*(brick_width+40),2*(brick_height+10),brick_width,brick_height);
		Brick b38=new Brick(0+7*(brick_width+40),2*(brick_height+10),brick_width,brick_height);

		Brick[] brick_arr= {b11,b12,b13, b14,b15,b16,b17,b18,b21,b22,b23,b24,b25,b26,b27,b31,b32,b33,b34,b35,b36,b37,b38};

		return brick_arr;
	}

	public int get_lives() {
		return lives;
	}




	@Override
	public void keyReleased(KeyEvent arg0) {
		// 

	}




	@Override
	public void keyTyped(KeyEvent arg0) {


	}

}

