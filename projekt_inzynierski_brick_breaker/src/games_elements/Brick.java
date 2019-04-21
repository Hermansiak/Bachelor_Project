package games_elements;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.geom.Rectangle2D;

public class Brick{
	public double posX=0, posY=0, width=0, height=0;
	int score=0;
	boolean enabled=true;
	public static int disabled_bricks=0;
	Graphics2D g2;
	public Brick(int X, int Y, int width, int height)
	{
		this.posX=X;
		this.posY=Y;
		this.width=width;
		this.height=height;
		
		
	}
	public void createRectangle( Graphics2D g2){
		if(enabled) {
		Rectangle2D brick= new Rectangle2D.Double(posX,posY,width,height);
		
		g2.setColor(Color.CYAN);
		g2.fill(brick);
		g2.setColor(Color.BLACK);
		g2.drawRect((int)posX, (int)posY, (int)width, (int)height);
		}
	}
	public boolean disable_brick() {
		enabled=false;
		disabled_bricks++;
		return enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	
	

}
