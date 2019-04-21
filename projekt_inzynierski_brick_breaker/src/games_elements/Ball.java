package games_elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import projekt_inzynierski_brick_breaker.BrickBreaker;
import utilities.GazeListener;

public class Ball {

	private int circleradius=8;
	private int prev_posX=0, prev_posY=0 , x=BrickBreaker.WIDTH/2, y=BrickBreaker.HEIGHT/2, velX=0, velY=0;
	private int labirynth_x= 0, labirynth_y=0;
	private boolean white_line_detected_right=true,white_line_detected_left=true,white_line_detected_up=true,white_line_detected_down=true;
	/**
	 * method drawing a ball for Brick Breaker game
	 * @param g2 -Graphics2D parameter
	 */
	public void drawBall(Graphics2D g2) {
		Ellipse2D circle = new Ellipse2D.Double(x,y,circleradius,circleradius);
		g2.setColor(Color.GREEN);
		g2.fill(circle);
	}

	/**
	 * method drawing a ball for Labirynth game 
	 * @param g2 -Graphics2D parameter
	 */
	public void draw_labirynth_ball(Graphics2D g2) {
		
		Ellipse2D circle = new Ellipse2D.Double(labirynth_x,labirynth_y,2*circleradius,2*circleradius);
		g2.setColor(Color.MAGENTA);
		g2.fill(circle);
	}

	/**
	 * Method moving ball in Brick Breaker game
	 */
	public void move_ball() {

		prev_posX=x;
		prev_posY=y;
		x +=velX;
		y += velY;
	}

	/**
	 * Labirynth game move up of the ball
	 */
	public void move_ball_up() {

		labirynth_y -= velY;

	}

	/**
	 * Labirynth game move down of the ball
	 */
	public void move_ball_down() {

		labirynth_y += velY;

	}


	/**
	 * Labirynth game move left of the ball
	 */
	public void move_ball_left() {

		labirynth_x -= velX;

	}

	/**
	 * 
	 * Labirynth game move right of the ball
	 */
	public void move_ball_right() {

		labirynth_x += velX;

	}

	/**
	 * Brick Breaker Game
	 * Negation of ball movement along X axis after collision 
	 */
	public void negate_velX() {

		velX = -velX;
	}

	/**
	 * Brick Breaker Game
	 * Negation of ball movement along X axis after collision 
	 */
	public void negate_velY() {
		velY = -velY;
	}

	/**
	 * Brick Breaker Game
	 * Method setting a value of Velocity of the ball along X axix
	 */
	public void set_ball_velX(int o)	{
		velX = o;
	}

	/**
	 * Brick Breaker Game
	 * Method setting a value of Velocity of the ball along Y axix
	 */
	public void set_ball_velY(int o)	{
		velY = o;
	}
	public void set_labirytnh_ball_posX(int o)	{
		labirynth_x = o;
	}
	public void set_labirytnh_ball_posY(int o)	{
		labirynth_y = o;
	}
	public void reset_position() {
		x=BrickBreaker.WIDTH/2;
		y=BrickBreaker.HEIGHT/2;
		velX=0;
	}
	public int get_labirynth_ball_posY() {
		return labirynth_y;
	}
	public int get_labirynth_ball_posX() {
		return labirynth_x;
	}
	public int get_labirynth_ball_middle_posX() {
		return labirynth_x + circleradius;
	}
	public int get_labirynth_ball_middle_posY() {
		return labirynth_y + circleradius;
	}
	public int get_ball_posX() {
		int x1=x;
		return x1;
	}
	public int get_ball_posY() {
		int y1=y;
		return y1;
	}
	public double get_ball_velX() {
		return velX;
	}
	public double get_ball_velY() {
		return velY;
	}
	public int get_circle_radius() {
		return circleradius/2;
	}
	public double get_prev_posX() {
		return prev_posX;
	}
	public double get_prev_posY() {
		return prev_posY;
	}
	//	
	//	public boolean ball_hit_left_part_of_pad(Pad pad) {
	//		if((get_ball_posY()+ get_circle_radius()) >  pad.get_pad_posY() && (get_ball_posY()+ get_circle_radius())< (pad.get_pad_posY()+pad.get_pad_height()) && pad.ball_is_on_left_border_part(get_ball_posX())){
	//			return true;	
	//		}else return false;
	//	}
	//	
	//	public boolean ball_hit_right_part_of_pad(Pad pad) {
	//		if((get_ball_posY()+ get_circle_radius()) >  pad.get_pad_posY() && (get_ball_posY()+ get_circle_radius())< (pad.get_pad_posY()+pad.get_pad_height()) && pad.ball_is_on_right_border_part(get_ball_posX())){
	//			return true;	
	//		}else return false;
	//	}
	//	public boolean ball_hit_center_part_of_pad(Pad pad) {
	//		if((get_ball_posY()+ get_circle_radius()) >  pad.get_pad_posY() && (get_ball_posY()+ get_circle_radius())< (pad.get_pad_posY()+pad.get_pad_height()) && pad.ball_is_on_center_part(get_ball_posX())){
	//			return true;	
	//		}else return false;
	//	}

	public boolean ball_hit_side_screen_border()
	{
		if(get_ball_posX()<0 ||(get_ball_posX()+ 2*get_circle_radius())>BrickBreaker.WIDTH ) {
			return true;
		}else return false;


	}
	public boolean ball_hit_bottom_screen_border()
	{
		if((get_ball_posY()+ get_circle_radius()/2)>BrickBreaker.HEIGHT - get_circle_radius()) {
			return true;
		}else return false;


	}
	public boolean ball_hit_top_screen_border()
	{
		if((get_ball_posY()+ get_circle_radius()/2)<0) {
			return true;
		}else return false;


	}

	public void move_labirynth_ball(BufferedImage labirynth_image_1) {
	
		for(int i=1;i<= 2;i++) {
			
			if(GazeListener.get_x()>get_labirynth_ball_middle_posX()) {
				if(!(labirynth_image_1.getRGB(get_labirynth_ball_middle_posX()+i, get_labirynth_ball_middle_posY())==Color.WHITE.getRGB()))
					white_line_detected_right=false;

				
			}
			if(GazeListener.get_x()<get_labirynth_ball_middle_posX()) {
				if(!(labirynth_image_1.getRGB(get_labirynth_ball_middle_posX()-i, get_labirynth_ball_middle_posY())==Color.WHITE.getRGB()))
					white_line_detected_left=false;
				
			}	

			if(GazeListener.get_y()>get_labirynth_ball_middle_posY()) {
				if(!(labirynth_image_1.getRGB(get_labirynth_ball_middle_posX(), get_labirynth_ball_middle_posY()+i)==Color.WHITE.getRGB()))
					white_line_detected_down=false;
			}
			if(GazeListener.get_y()<get_labirynth_ball_middle_posY()) {
				if(!(labirynth_image_1.getRGB(get_labirynth_ball_middle_posX(), get_labirynth_ball_middle_posY()-i)==Color.WHITE.getRGB()))
					white_line_detected_up=false;

				
				
			}		
		}
		
		if(!white_line_detected_right)
			move_ball_right();
		if(!white_line_detected_left)
			move_ball_left();
		if(!white_line_detected_up)
			move_ball_up();
		if(!white_line_detected_down)
			move_ball_down();
		
		white_line_detected_right=true;
		white_line_detected_left=true;
		white_line_detected_up=true;
		white_line_detected_down=true;
	}
}



