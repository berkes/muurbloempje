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
	double pad; // How far to keep away from the walls
	
	/**
	 * run: Move around the walls
	 */
	public void run() {
		// @TODO Set colors

		// Initialize peek to false
		peek = false;
		int currentWall = 0;
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
		double distance = 0;

		// A robot is 36x36, so we're staying within 35 from the wall
		// @TODO randomize
        pad = 36;

		//Rectangle2D fieldRect = new Rectangle2D.Double(pad, pad, getBattleFieldWidth()-(2*pad), getBattleFieldHeight()-(2*pad));
		
        switch(wallIndicator) {
			case 0: distance = getBattleFieldHeight() - (getY() + pad); break; //north
			case 1: distance = getBattleFieldWidth()  - (getX() + pad); break; //east
			case 2: distance = getY() - pad; break; //south
			case 3: distance = getX() - pad; break; //west
		}

		// Initialize moveAmount to the maximum possible for this battlefield.
		return distance;
	}
	
    private double angleToWall(int wallIndicator) {
		double angle = 0;
		switch(wallIndicator) {
			case 0: angle = 0;   break; //north
			case 1: angle = 90;  break; //east
			case 2: angle = 180; break; //south
			case 3: angle = 270; break; //west
		}
		return angle - getHeading();
	}
}
