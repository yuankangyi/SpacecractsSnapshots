package casc.cast.c504.I2P;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class ProcessControl {

	public static Process openModule(String exeFilePath, String modeleFilePath) throws Exception {
		
		Process p = Runtime.getRuntime().exec(new String[]{exeFilePath, modeleFilePath, });
		
		return p;
	}
	
	
	public static void closeModule(Process p) {
		p.destroy();
	}
	
	public static void closeWindow(Process p) {
		p.destroy();
	}
	
	public static void enlargeModel(){
		
		
	}
	

	public static Process openExe(String changeWindowPath) throws Exception {
		Process p = Runtime.getRuntime().exec(new String[]{changeWindowPath,});
		
		return p;

	}
	public static void main(String[] args) throws Exception {
		
		Process p1 = openModule(DataPrepare.exeFilePath, DataPrepare.modeleFilePath + "Result_2.obj");
//		Process p2 = openExe(DataPrepare.openExePath);		
        rotation.rotate(1077,0);   //1077תһȦ
		Snapshot.takeSnapshotOnRegion(200, 200, 1200, 1000, "C://work//I2P//workspace//testdata2//test.bmp");
		
		//closeModule(p1);
		
	}
}
