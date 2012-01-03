// #if capturePhoto
// [NC] Added in the scenario 08
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import java.io.ByteArrayOutputStream;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;
import javax.microedition.media.control.VideoControl;
import javax.microedition.midlet.MIDlet;



public class CaptureVideoScreen extends GameCanvas {
	
	Player capturePlayer = null;

	Display display = null;

	private AbstractController currentController;
	
	
	private VideoControl videoControl = null;
	private RecordControl rControl = null;
	ByteArrayOutputStream byteOfArray = new ByteArrayOutputStream();
	
	// #ifdef captureVideo
	// [NC] Added in the scenario 08
	private Command start = new Command("Start", Command.EXIT, 1);
	private Command stop = new Command("Stop", Command.ITEM, 1);
	//#endif
	
	// #ifdef capturePhoto
	// [NC] Added in the scenario 08
	private Command takephoto = new Command("Take photo", Command.EXIT, 1);
	//#endif

	private Command back = new Command("Back", Command.ITEM, 1);

	boolean recording = false;
	
	// #ifdef capturePhoto
	// [NC] Added in the scenario 08
	public final static int CAPTUREPHOTO = 1;
	//#endif

	// #ifdef captureVideo
	// [NC] Added in the scenario 08
	public final static int CAPTUREVIDEO = 2;
	//#endif
	
	private int typescreen = 0;

	public CaptureVideoScreen(MIDlet midlet, int type) {
		super(false);
		typescreen = type;

		display = Display.getDisplay(midlet);

		try {
			capturePlayer = Manager.createPlayer("capture://video");
			capturePlayer.realize();
		} catch (Exception e) {
			new Handler().handle( e );
		}

		videoControl = (VideoControl) capturePlayer
				.getControl("javax.microedition.media.control.VideoControl");
		try {
			if (videoControl == null)
				throw new Exception("No Video Control for capturing!");

			videoControl.initDisplayMode(VideoControl.USE_DIRECT_VIDEO, this);
			videoControl.setDisplayFullScreen(true);
		} catch (MediaException me) {
			videoControl.setDisplayLocation(5, 5);
			try {
				videoControl.setDisplaySize(getWidth() - 10, getHeight() - 10);
			} catch (Exception e) {
				new Handler().handle( e );
			}
			repaint();
		} catch (Exception e) {
			new Handler().handle( e );
		}
		// #ifdef captureVideo
		// [NC] Added in the scenario 08
		if (typescreen == CAPTUREVIDEO){
			this.addCommand(start);
			this.addCommand(stop);
		}
		//#endif
		
		// #ifdef capturePhoto
		// [NC] Added in the scenario 08
		if (typescreen == CAPTUREPHOTO){
			this.addCommand(takephoto);
		}
		//#endif

		this.addCommand(back);
	}
	
	// #ifdef captureVideo
	// [NC] Added in the scenario 08
	public void startCapture() {
		try {
			if (!recording) {
				rControl = (RecordControl) capturePlayer
						.getControl("RecordControl");
				if (rControl == null)
					throw new Exception("No RecordControl found!");
				byteOfArray = new ByteArrayOutputStream();
				rControl.setRecordStream(byteOfArray);
				rControl.startRecord();
				recording = true;
			}
		} catch (Exception e) {
			new Handler().handle( e );
		}
	}
	
	public void pauseCapture() {
		try {
			if (recording) {
				rControl.stopRecord();
				rControl.commit();
				recording = false;
			}
		} catch (Exception e) {
			new Handler().handle( e );
		}
	}
	public byte[] getByteArrays() {
		return byteOfArray.toByteArray();
	}
	//#endif
	
	// #ifdef capturePhoto
	// [NC] Added in the scenario 08
	protected byte[] takePicture() {
		try {
			Alert alert = new Alert("Error", "The mobile database is full", null, AlertType.INFO);
			alert.setTimeout(5000);
			display.setCurrent(alert);
			AbstractController controller = this.getCurrentController();
			this.setCommandListener( controller );
			System.out.println( "<* CaptureVideoScreen.takePicture() *> take a picture now...");
			
			// the parameter null defines a default type, which is png
			byte[] imageArray = videoControl.getSnapshot(null);
			System.out.println( "<* CaptureVideoScreen.takePicture() *> picture: "+imageArray);
			return imageArray;
		} catch (Exception e) {
			System.out.println("[CaptureVideoScreen:takePicture()] Exception "+e.getMessage());
			new Handler().handle( e );
		}
		return null;
	}
	//#endif

	public void keyPressed(int keyCode) {

	}

	public void paint(Graphics g) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(0x44ff66);
		g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
	}

	public void setVisibleVideo() {
		display.setCurrent(this);
		videoControl.setVisible(true);
		try {
			capturePlayer.start();
		} catch (Exception e) {
			new Handler().handle( e );
		}
	}
	
	protected AbstractController getCurrentController() {
		return currentController;
	}

	protected void setCurrentController(AbstractController currentController) {
		this.currentController = currentController;
	}

}
//#endif