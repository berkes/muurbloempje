/*******************************************************************************
 * Based on sample::Wall
 *******************************************************************************/
package berkes;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

// import java.awt.*;


/**
 * Moves around the outer edge with the gun facing in.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Bèr Kessels
 */
public class MuurBloempje extends Robot {
	boolean peek; // Don't turn if there's a robot there
	
	/**
	 * run: Move around the walls
	 */
	public void run() {
		// @TODO Set colors

		// Initialize peek to false
		peek = false;
		int currentWall = nearestWall();
		
		moveToWall(currentWall);
		// Turn the gun to turn right 90 degrees.
		peek = true;
		turnGunRight(90);
		turnRight(90);

		while (true) {
		    currentWall++;
			// Look before we turn when ahead() completes.
			peek = true;
			// Move up the wall
			moveToWall(currentWall % 4);
			// Don't look now
			peek = false;
			// Turn to the next wall
		}
	}

	/**
	 * onHitRobot:  Move away a bit.
	 */
	public void onHitRobot(HitRobotEvent e) {
		// If he's in front of us, set back up a bit.
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // else he's in back of us, so set ahead a bit.
		else {
			ahead(100);
		}
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
		// Note that scan is called automatically when the robot is moving.
		// By calling it manually here, we make sure we generate another scan event if there's a robot on the next
		// wall, so that we do not start moving up it until it's gone.
		if (peek) {
			scan();
		}
	}
	
    private void moveToWall(int wallIndicator) {
		turnRight(angleToWall(wallIndicator));
		ahead(distanceToWall(wallIndicator));
	}

    private double distanceToWall(int wallIndicator) {
        //How far to keep from the walls.
        double pad = 36;
        double[] distances = {
			getBattleFieldHeight() - (getY() + pad), //north
			getBattleFieldWidth()  - (getX() + pad), //east
			getY() - pad, //south
			getX() - pad,//west
		};
		return distances[wallIndicator];
	}
	
    private double angleToWall(int wallIndicator) {
		int[] angles = {0,90,180,270};
		return angles[wallIndicator] - getHeading();
	}
	
    private int nearestWall() {
      int nearest = 0;
   	  double min = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
	  for(int wall = 0; wall <= 3; wall++) {
		 if (distanceToWall(wall) <= min) {
			min = distanceToWall(wall);
		 	nearest = wall;
			System.out.println("min: " + min + " wall: " + wall);
         }
	  }
	  return nearest;
	}
}
