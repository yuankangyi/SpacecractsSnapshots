package casc.cast.c504.I2P;

import java.io.File;


public class DataPrepare {

	protected static final String modeleFilePath = "C://work//I2P//workspace//testdata2//";
	protected static final String snapshotBaseFolderPath = "C://work//I2P//workspace//testdata4//";
	protected static final String exeFilePath ="C://Program Files//Autodesk//FBX Review//fbxreview.exe";//C://Users//Administrator//Downloads//mview-win-qt4-0.3.3//mview-qt4.exe  D://STK 9//bin//AgMDE.exe
	protected static final String snapshotExtName = ".png";
	protected static final String changeWindowPath = "D://window.exe";
	
	static final int[] x = {0, 45, 90, 135, 180, 225, 270, 315};
	static final int[] y = {0, 45, 90, 135, 180, 225, 270, 315};
	static final int[] z = {0, 45, 90, 135, 180, 225, 270, 315};
	
	private String targetBaseFolder;
	private String sourceBaseFolder;
	
	public String getTargetBaseFolder() {
		return targetBaseFolder;
	}
	public void setTargetBaseFolder(String targetBaseFolder) {
		this.targetBaseFolder = targetBaseFolder;
	}
	
	public String getSourceBaseFolder() {
		return sourceBaseFolder;
	}
	public void setSourceBaseFolder(String sourceBaseFolder) {

	}
	
	public static void main(String[] args) throws Exception {
		
		//prepare model
		
//		String sourceModelPath = "C://work//I2P//workspace//testdata//xm-radio" + ".mdl";
	//	String targetBaseFolderPath = "C://work//I2P//workspace//testdata1//";
	//	
//		String articulationName = "XMRadio";
//		
//		int m = RotationMaker.rotate(x, y, z, sourceModelPath, targetBaseFolderPath, articulationName);
//		
//		if (m == 0) {
//			System.out.println(sourceModelPath);
//		} else {
//			System.out.println(m);
//		}
//		
//		System.exit(0);
		
		//take snapshots
		File sourceFileBaseFolder = new File(modeleFilePath);
		if (sourceFileBaseFolder.exists() && sourceFileBaseFolder.isDirectory()) {
			
			File[] fs = sourceFileBaseFolder.listFiles();
			if (fs != null) {
				int i = 0;
				while (i < fs.length) {
					
					File f = fs[i];
					
//					if (f.getName().compareTo("cobe") < 0) {
//						continue;
//					}
					
					if (f.isDirectory()) {
						
						String fnPrefix = f.getName();
						
						File[] fs1 = f.listFiles();
						if (fs1 != null) {
							
							File df = new File(snapshotBaseFolderPath + "//" + fnPrefix + "//");
							if (!df.exists()) {
								df.mkdir();
							}
							
							int j = 0;
							while (j < fs1.length) {
								
								File ff = fs1[j];
								String mfp = ff.getAbsolutePath();
								
								String fn = ff.getName();
								if (mfp.toLowerCase().endsWith(".obj")) {
									fn = fn.substring(0, fn.lastIndexOf('.'));
									try {
										Process p = ProcessControl.openModule(exeFilePath, mfp);
										Process p1 = ProcessControl.openExe(changeWindowPath);
										
										long fsize = ff.length();
										if (fsize < 524288L) {
											Thread.sleep(600L);
										} else if (fsize < 2097152L) {
											Thread.sleep(1000L);
										} else {
											Thread.sleep(4000L);
										}
										
									
										Snapshot.takeSnapshotOnRegion(200, 200, 1200, 1000, df.getAbsolutePath() + "//" + fn);
										
										ProcessControl.closeModule(p);
										ProcessControl.closeWindow(p1);
										
										Thread.sleep(500L);
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								j++;
							}
							
							System.out.println(df.getAbsolutePath() + ": " + j + " finished!");
							
						}
					} else {
						
						String fn = f.getName();
						
						String mfp = f.getAbsolutePath();
						if (mfp.toLowerCase().endsWith(".obj")) {
							fn = fn.substring(0, fn.lastIndexOf('.'));
							try {
								Process p = ProcessControl.openModule(exeFilePath, mfp);
								Process p1 = ProcessControl.openExe(changeWindowPath);								
								long fsize = f.length();
								if (fsize < 524288L) {
									Thread.sleep(600L);
								} else if (fsize < 2097152L) {
									Thread.sleep(1000L);
								} else {
									Thread.sleep(4000L);
								}

								Snapshot.takeSnapshotOnRegion(200, 200, 1200, 1000, snapshotBaseFolderPath + fn);  //¸Ä±ä½ØÆÁ´°¿Ú200, 200, 1200, 1000
								
								ProcessControl.closeModule(p);
								ProcessControl.closeWindow(p1);
								
								Thread.sleep(500L);
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					
					i++;
				}
				System.out.println(sourceFileBaseFolder.getAbsolutePath() + ": " + i + " finished!");
			}
		}
		
	}
	
}
