package itu.dluj.thesis.experiment.pointselect;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class Tools {

	public static Scalar red = new Scalar(255,0,0);
	public static Scalar green = new Scalar(0,255,0);
	public static Scalar blue = new Scalar(0,0,255);
	public static Scalar magenta = new Scalar(255,0,255);
	public static Scalar gray = new Scalar(10, 10, 10);
	public static Scalar white = new Scalar(255, 255, 255);
	
	/*************************** Utility methods ************************************/

	/*
	 * Draws convexity Defects in color image
	 * params: convexity defects, center of hand contour, hand contour
	 * 
	 */
	public static Mat drawDefects(Mat mRgb, List<Point[]> convexityDefects, Point handContourCentroid) {
		/*
		 * convexityDefects -> Point[] containing [0] = start of contour, [1] = farthest point from convex hull.
		 */
		Point start;
//		Point end;
		//		float depth;

		for(int i=0; i< convexityDefects.size(); i++){
			start = convexityDefects.get(i)[0]; 
//			farthest = convexityDefects.get(i)[1];
			//		        line from farthest point to start
//			Core.line(mRgb, farthest, start, red, 1);
			//		        line from center of contour to start
//			Core.line(mRgb, start, handContourCentroid, blue, 1);
			Core.circle(mRgb, start, 3, red, -1);
		}
		return mRgb;
	}

	/*
	 * Calculates the center of the contour
	 * Params: hand contour
	 * returns Point centroid -> centroid{x,y}
	 */
	public static Point getCentroid(MatOfPoint contour) {
		Moments moments = Imgproc.moments(contour);
		Point centroid = new Point();
		centroid.x = moments.get_m10() / moments.get_m00();
		centroid.y = moments.get_m01() / moments.get_m00();
		return centroid;
	}


	/*
	 * Utility method - gets distance between two points
	 */
	public static double getDistanceBetweenPoints(Point one, Point two) {
		return Math.sqrt( Math.pow((two.x-one.x), 2) + Math.pow((two.y-one.y), 2));
	}

	/*
	 * Utility method - gets distance between two points
	 */
	public static Point getPointBetweenPoints(Point one, Point two) {
		Point result = new Point();
		result.x = (one.x + two.x)/2;
		result.y = (one.y + two.y)/2;
		return result;
	}
}
