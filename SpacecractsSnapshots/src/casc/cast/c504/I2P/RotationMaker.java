package casc.cast.c504.I2P;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class RotationMaker {

	public static int rotate(int[] x, int[] y, int[] z, String sourceModelPath, String targetBaseFolderPath, String articulationName) throws Exception {
		
		String[] window = new String[3];
		window[0] = null;
		window[1] = null;
		window[2] = null;
		
//		int[] idxs = new int[3];
//		idxs[0] = -1;
//		idxs[1] = -1;
//		idxs[2] = -1;
		
		int cCounter = 0;
		
		int generatedModelNum = 0;
		
		List<String[]> rotations = null;
		
		if (x != null && y != null && z != null && x.length >0 && y.length > 0 && z.length > 0) {
			
			rotations = new ArrayList<String[]>();
			
			File sf = new File(sourceModelPath);
			if (sf.exists() && sf.isFile() && sf.getName().toLowerCase().endsWith(".obj")) {
				
				StringBuffer sb1 = new StringBuffer("");
				StringBuffer sb2 = new StringBuffer("");
				
				BufferedReader br = new BufferedReader(new FileReader(sf));
				String line = null;
	
				boolean down = false;
				boolean correctArticulation = false;
				
				while ((line = br.readLine()) != null) {
					
					if (!down) {
						sb1.append(line + "\n");
//						System.out.println(line + "\n");
					} else {
						sb2.append(line + "\n");
//						System.out.println(line + "\n");
						continue;
					}
					
					if (line.trim().startsWith("Articulation")) {
						String[] parts = line.trim().split("\\s+");
						if (parts != null && parts.length == 2 && parts[0].equals("Articulation") && parts[1].equals(articulationName)) {
							correctArticulation = true;
						} 
					}
					
					if (window[0] == null) {
						window[0] = line;
//						idxs[0] = cCounter;
					} else if (window[1] == null) {
						window[1] = line;
//						idxs[1] = cCounter;
					} else if (window[2] == null) {
						window[2] = line;
//						idxs[2] = cCounter;
					} else {
						window[0] = window[1];
						window[1] = window[2];
						window[2] = line;
//						idxs[0] = idxs[1];
//						idxs[1] = idxs[2];
//						idxs[2] = cCounter;
					}
					
					if (correctArticulation && 
							window[2].contains("zRotate") && window[1].contains("yRotate") && window[0].contains("xRotate")) {
						
						String[] xes = window[0].trim().split("\\s+");
						String[] yes = window[1].trim().split("\\s+");
						String[] zes = window[2].trim().split("\\s+");
						
						int xlb = Integer.MIN_VALUE;
						int xub = Integer.MIN_VALUE;
						
						int ylb = Integer.MIN_VALUE;
						int yub = Integer.MIN_VALUE;
						
						int zlb = Integer.MIN_VALUE;
						int zub = Integer.MIN_VALUE;
						
						if (xes != null && xes.length == 5) {
							xlb = Integer.valueOf(xes[2]);
							xub = Integer.valueOf(xes[4]);
						}
						
						if (yes != null && yes.length == 5) {
							ylb = Integer.valueOf(yes[2]);
							yub = Integer.valueOf(yes[4]);
						}
						
						if (zes != null && zes.length == 5) {
							zlb = Integer.valueOf(zes[2]);
							zub = Integer.valueOf(zes[4]);
						}
						
						if (xlb > Integer.MIN_VALUE && xub > Integer.MIN_VALUE && 
								ylb > Integer.MIN_VALUE && yub > Integer.MIN_VALUE && 
								zlb > Integer.MIN_VALUE && zub > Integer.MIN_VALUE) {
							
//							System.out.println(sb1.length() + idxs[0]);
							
							int m = sb1.lastIndexOf("xRotate");
							sb1.delete(m, sb1.length());
							
//							sb1.delete(idxs[0], sb1.length());
//							System.out.println(sb1.length() + idxs[0]);
							down = true;
							
							//generate rotation lines
							int i = 0;
							while (i < x.length) {
								
								int j = 0;
								while (j < y.length) {
									
									int k = 0;
									while (k < z.length) {
										
										int xr = xlb + x[i];
										int yr = ylb + y[j];
										int zr = zlb + z[k];
										
										String rotationX = xes[0] + " " + xes[1] + " " + xes[2] + " " + xr + " " + xes[4];
										String rotationY = "\t\t\t" + yes[0] + " " + yes[1] + " " + yes[2] + " " + yr + " " + yes[4];
										String rotationZ = "\t\t\t" + zes[0] + " " + zes[1] + " " + zes[2] + " " + zr + " " + zes[4];
										
										rotations.add(new String[]{rotationX, rotationY, rotationZ});
										
										k++;
									}
									j++;
								}
								i++;
							}
							
							correctArticulation = false;
						}
					}
					cCounter = cCounter + line.length();
				}
				
				generatedModelNum = rotations.size();
				
				String newFolderName = sf.getName().substring(0, sf.getName().length() - 4);
				String folderPath = targetBaseFolderPath + "//" + newFolderName + "//";
				
				File ff = new File(folderPath);
				if (!ff.exists() || ff.exists() && !ff.isDirectory()) {
					ff.mkdir();
				}
				
				int i = 1;
				while (i <= generatedModelNum) {
					
					String fn = newFolderName + "_" + String.format("%04d", i) + ".obj";
					
					File df = new File(folderPath + fn);
					if (!df.exists()) {
						df.createNewFile();
					}
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(df));
					
					bw.write(sb1.toString());
					bw.write(rotations.get(i-1)[0] + "\n");
					bw.write(rotations.get(i-1)[1] + "\n");
					bw.write(rotations.get(i-1)[2] + "\n");
					
					bw.write(sb2.toString());
					
					bw.flush();
					bw.close();
					i++;
				}
			}
		}
		return generatedModelNum;
	}
	
	
	public static void main(String[] args) throws Exception {
		int[] x = {0, 90, 180, 270};
		int[] y = {0, 90, 180, 270};
		int[] z = {0, 90, 180, 270};
		
	//	String sourceModelPath = "D://STK 9//STKData//VO//Models//Space//mars_odyssey.mdl";
		String sourceModelPath = "C://work//I2P//workspace//testdata3//Result.obj";
		String targetBaseFolderPath = "C://work//I2P//workspace//testdata2//";
		
		String articulationName = "Body";
		
		int i = rotate(x, y, z, sourceModelPath, targetBaseFolderPath, articulationName);
		
		System.out.println(i);
	}
}
