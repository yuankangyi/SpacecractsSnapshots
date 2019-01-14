package casc.cast.c504.I2P;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class rotation {
	public static void rotate(int x, int y ) throws Exception {
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		if (r != null) {

	//		r.mouseWheel(5);
		//	Robot robot = new Robot();
			r.mouseMove(700, 600);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseMove(700+x, 600+y);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
	//		r.mouseWheel(-4);
			r.mouseMove(700, 600);
			Thread.sleep(3000L);

			
		}
	}
	
	public static void main(String[] args) throws Exception {
		rotate(10,10);
	}

}
