package oss.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

public class FetchFrame {
	public static void fetchFrame(String videofile, String framefile)
	        throws Exception {
	    long start = System.currentTimeMillis();
	    File targetFile = new File(framefile);
	    FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile); 
	    ff.start();
	    int lenght = ff.getLengthInFrames();
	    int i = 0;
	    Frame f = null;
	    while (i < lenght) {
	        f = ff.grabFrame();
	        if ((i > 300) && (f.image != null)) {
	            break;
	        }
	        i++;
	    }
	    IplImage img = f.image;
	    int owidth = img.width();
	    int oheight = img.height();
	    int width = 800;
	    int height = (int) (((double) width / owidth) * oheight);
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
	            0, 0, null);
	    ImageIO.write(bi, "jpg", targetFile);
	    //ff.flush();
	    ff.stop();
	    System.out.println(System.currentTimeMillis() - start);
	}

	/*public static void main(String[] args) {
	    try {
	    	FetchFrame.fetchFrame("h:/new/进击的巨人.第三季.12.中日双语.HDTV.720P.甜饼字幕组.mp4", "h:/new/test4.jpg");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
}*/
}
