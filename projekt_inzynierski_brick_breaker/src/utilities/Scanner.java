package utilities;

import java.awt.Color;
import java.awt.image.BufferedImage;

import games_elements.Ball;


public class Scanner {


	private int x1=0,x2=0,y1=0,y2=0, average_x=0,average_y=0;

	/**
	 * Method for scanning an image to find a green field (which is start field in labirynth) and placing a ball
	 * in the middle of this field
	 * @param img - labirynth image loaded to the game
	 * @param ball - ball used in game
	 */
	public void scan_image(BufferedImage img, Ball ball) {

		for(int i=1;i<img.getWidth();i++) {			
			for(int j=1;j<img.getHeight();j++) {	//scanning all pixels along X and Y axis
				if(img.getRGB(i, j)==Color.GREEN.getRGB()) { //checks if pixel color is green
					if(x1==0 && y1==0) {
						x1=i;	//assigning values of the beginning of start field to proper variables
						y1=j;
					}else {
						x2=i; //assigning values of the end of start field to proper variables
						y2=j;
					}

				}
			}
		}
		
		average_x= (x1+x2)/2;
		average_y= (y1+y2)/2;
		ball.set_labirytnh_ball_posX(average_x);	//	sets ball position on the middle of found Start field
		ball.set_labirytnh_ball_posY( average_y);

	}
}
