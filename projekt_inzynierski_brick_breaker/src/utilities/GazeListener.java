package utilities;

import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;

import games_elements.Pad;

import  java.lang.Math;
import gui.Start_window;

public class GazeListener implements IGazeListener
{


	private static double x=0,y=0;
	private double prevX=0;
	private double prevY=0;
	private double anti_shake_lengh=70;

	@Override
	public void onGazeUpdate(GazeData gazeData) {
	
		if(Start_window.eyetracker_controls) {	//checks if eyetracker is chosen as a controlling device
			
			if(Math.abs(gazeData.rawCoordinates.y-prevY )>=anti_shake_lengh) { // anti shaking of the gaze point
				Pad.set_desired_pad_posX(gazeData.rawCoordinates.x);
				x=gazeData.rawCoordinates.x;
			}
			
			if(Math.abs(gazeData.rawCoordinates.x-prevX )>=anti_shake_lengh) {
				y=gazeData.rawCoordinates.y;
			}
		}
	}

	public static double get_x() {
		return x;
	}
	public static double get_y() {
		return y;
	}

}