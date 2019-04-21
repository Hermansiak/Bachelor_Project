package games_elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import projekt_inzynierski_brick_breaker.BrickBreaker;

public class Pad {
	
	private static double pad_width=BrickBreaker.WIDTH/4;
	private double pad_height=10;
	private static double border_length=BrickBreaker.WIDTH/20;
	private double prev_padX=0;
	private double padX=BrickBreaker.WIDTH/2-pad_width/2, padY= BrickBreaker.HEIGHT-100;
	private static double desired_padX=0;
	public void drawPad(Graphics2D g2) {
		Rectangle2D pad_left= new Rectangle2D.Double(padX,padY,border_length,pad_height);
		Rectangle2D pad_right= new Rectangle2D.Double(padX+(pad_width-border_length),padY,border_length,pad_height);
		Rectangle2D pad_center= new Rectangle2D.Double(padX+border_length,padY,pad_width-(2*border_length),pad_height);
		g2.setColor(Color.RED);
		g2.fill(pad_left);
		g2.fill(pad_right);
		g2.setColor(Color.ORANGE);
		g2.fill(pad_center);
	}
	public double get_pad_posX() {
		return padX;
	}
	public double get_pad_posY() {
		return padY;
	}
	public static double set_desired_pad_posX(double pos) {
		desired_padX=pos- pad_width/2;
		return desired_padX;
	}
	public static double get_desired_pad_posX() {
		return desired_padX;
	}
	
	public double get_pad_height() {
		return pad_height;
	}
	public double get_pad_width() {
		return pad_width;
	}
	
	public void reset_position() {
		padX=BrickBreaker.WIDTH/2-pad_width/2;
		padY= BrickBreaker.HEIGHT-100;
	}
	
	public double move_pad(double a) {
		prev_padX=padX;
		padX+=a;
		return padX;
	}
	public boolean ball_hit_center_part_of_pad(double x, double y)
	{
		if(x>(padX+ border_length) && x < (padX+(pad_width-border_length-5)) && (y >=padY-10 && y<=padY+ pad_height)) {
			return true;
		}else return false;
	}
	
	public boolean ball_hit_left_part_of_pad(double x, double y)
	{
		if(x>(padX-10) && x< (padX+border_length) && (y >=padY-10 && y<=padY+ pad_height)) {
			return true;
		}else return false;
	}
	public boolean ball_hit_right_part_of_pad(double x, double y)
	{
		if( (x > (padX+(pad_width-border_length-5)) &&( x < (padX+pad_width+10))) && (y >=(padY-10) && y<=padY+ pad_height) ) {
			return true;
		}else return false;
	}
	
}
