package berkes;
import robocode.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * MyFirstRobot - a robot by (your name here)
 */
public class MyFirstRobot extends Robot {
  public void run() {
    while(true) {
		ahead(100);
		turnGunRight(360);
		back(100);
		turnGunRight(360);
	}
  }
  
  public void onScannedRobot(ScannedRobotEvent e) {
	fire(1);
  }
}
								