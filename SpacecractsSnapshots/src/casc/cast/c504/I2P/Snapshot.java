package casc.cast.c504.I2P;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Snapshot {

	public static void takeSnapshotOnRegion(int aX, int aY, int bX, int bY, String snapshotFilePath) throws Exception {
		
		Rectangle rect = new Rectangle(new Point(aX, aY));
		
		rect.width = bX - aX;
		rect.height = bY - aY;
		
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
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.mouseWheel(-2);
			Thread.sleep(3000L);
			for(int ax=0;ax<3;ax++){
				for(int ay=0;ay<4;ay++){
					
					BufferedImage bi = r.createScreenCapture(rect);
					String new_snapshotFilePath = snapshotFilePath + '_' + String.valueOf(ax*4+ay) + DataPrepare.snapshotExtName;
					File ssf = new File(new_snapshotFilePath);
					
					try {	
						ImageIO.write(bi, "png", ssf);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Thread.sleep(3000L);
					rotation.rotate(ax*360, ay*270);//135,135
				}
			}
//			BufferedImage bi = r.createScreenCapture(rect);
//			File ssf = new File(snapshotFilePath);
			
//			try {
//				ImageIO.write(bi, "bmp", ssf);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		takeSnapshotOnRegion(10, 25, 110, 500, "C://work//I2P//workspace//testdata2//Result");
	}
	
}
