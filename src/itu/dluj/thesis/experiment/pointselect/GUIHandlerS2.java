package itu.dluj.thesis.experiment.pointselect;

import java.io.PrintStream;
import java.text.DateFormat;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import android.util.Log;

public class GUIHandlerS2 {

	private int screenWidth;
	private int screenHeight;
	private int iBtnToClick;
	private Point[] pCirclesCoords;
	private int iCircleRadius = 40;
	private int iBtnsClicked = 0;
	public boolean allClicked = false;
	public long endS2;
	public long startOfExperiment;
	private String TAG = "itu.dluj.thesis.experiment.pointselect";
	private PrintStream ps;

	public GUIHandlerS2(int width, int height, long milis, PrintStream ps){
		this.ps = ps;
		startOfExperiment = milis;
		screenWidth = width;
		screenHeight = height;
		iBtnToClick = 0;
		//fullScreenImg Coords
		pCirclesCoords = new Point[10];
		pCirclesCoords[0] = new Point(screenWidth*0.25, screenHeight*0.70);
		pCirclesCoords[1] = new Point(screenWidth*0.50, screenHeight*0.40);
		pCirclesCoords[2] = new Point(screenWidth*0.90, screenHeight*0.47);
		pCirclesCoords[3] = new Point(screenWidth*0.30, screenHeight*0.10);
		pCirclesCoords[4] = new Point(screenWidth*0.50, screenHeight*0.80);
		pCirclesCoords[5] = new Point(screenWidth*0.80, screenHeight*0.15);
		pCirclesCoords[6] = new Point(screenWidth*0.39, screenHeight*0.60);
		pCirclesCoords[7] = new Point(screenWidth*0.10, screenHeight*0.15);
		pCirclesCoords[8] = new Point(screenWidth*0.60, screenHeight*0.90);
		pCirclesCoords[9] = new Point(screenWidth*0.70, screenHeight*0.20);
		
	}
	
	/******************************** Drawing methods ****************************************/
	
	public Mat drawCircles(Mat mRgb){
//		for(int i = 0; i<10; i++){
//			Core.circle(mRgb, pCirclesCoords[i], iCircleRadius, Tools.blue, -1);
//			mRgb = writeInfoToImage(mRgb, pCirclesCoords[i], i+"");
//		}
		Core.circle(mRgb, pCirclesCoords[iBtnToClick], iCircleRadius, Tools.blue, -1);

//		Mat output = new Mat();
//		Core.addWeighted(mRgb, 0, rec, 1.0, 0, output);
		return mRgb;
//		return output;
	}
	
	
	/******************************* Action methods *******************************************/
	/*
	 * onClick 
	 * returns true if click is accepted (clicked on something)
	 */
	public boolean onClick(Point click){
		/*
		 * - if iCurrentPatient == -1 --> we are in PatientSelection
		 * 		- check in which patient the click is inside of
		 * Coords [2] == upper left outer rectangle
		 * Coords [3] == lower right outer rectangle
		 */
		
		if(inside(pCirclesCoords[iBtnToClick], click)){
//			bBtnsClicked[iBtnToClick] = true;
			iBtnsClicked = iBtnsClicked + 1;
			iBtnToClick = iBtnToClick + 1;
			if(iBtnsClicked == pCirclesCoords.length){
				allClicked = true;
				endS2 = System.currentTimeMillis();
				Log.i(TAG, "END OF S2 :: startOfCondition :: "+ (endS2 - startOfExperiment)/1000 
						+ " secs :: endOfCondition" + endS2/1000);
				ps.println(TAG+"::"+DateFormat.getTimeInstance().format(System.currentTimeMillis())+ " :: END OF S2 :: startOfCondition :: "+ (endS2 - startOfExperiment)/1000 
						+ " secs :: endOfCondition" + endS2/1000);
				Log.i(TAG, "Drawing screen 3");
				ps.println(TAG+"::"+DateFormat.getTimeInstance().format(System.currentTimeMillis())+ " :: Drawing screen 3");
			}
			return true;
		}
		
		return false;
	}
	
	/******************************* Utility methods ******************************************/
	
	public boolean inside(Point circle, Point point){
		double distance = Math.sqrt(Math.pow(circle.x - point.x, 2) + Math.pow(circle.y - point.y, 2));
		return (distance <= iCircleRadius);
	}

	public void reset(){
		iBtnToClick = 0;
		iBtnsClicked = 0;
		allClicked = false;
//		bBtnsClicked = new boolean[]{false,false};
	}
	
	
	/*
	 * Utility method - writes to color image
	 */
	public Mat writeInfoToImage(Mat mRgb, Point point, final String string) {
		Core.putText(mRgb, string, point, Core.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0,0,0), 5);
		Core.putText(mRgb, string, point, Core.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(255,255,255), 1);
		return mRgb;
	}
	
	/*
	 * Utility method - writes to color image
	 */
	public Mat writeInfoToImage(Mat mRgb, final String string) {
		Point point = new Point(screenWidth*0.05, screenHeight*0.95);
		Core.putText(mRgb, string, point, Core.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0,0,0), 5);
		Core.putText(mRgb, string, point, Core.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(255,255,255), 1);
		return mRgb;
	}	
}
