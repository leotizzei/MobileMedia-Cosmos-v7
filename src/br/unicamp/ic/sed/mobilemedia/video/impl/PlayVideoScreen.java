// #ifdef includeVideo
// [NC] Added in the scenario 08
package br.unicamp.ic.sed.mobilemedia.video.impl;


import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;
import javax.microedition.midlet.MIDlet;


public class PlayVideoScreen extends GameCanvas {

	private Player player = null;
	private VideoControl videoControl = null;

	private Command start = new Command("Start", Command.EXIT, 1);
	private Command back = new Command("Back", Command.ITEM, 1);
	private Command stop = new Command("Stop", Command.ITEM, 1);


	// #ifdef includeCopyPhoto
	// [NC] Added in the scenario 08
	public static final Command copy = new Command("Copy", Command.ITEM, 1);
	//#endif

	private boolean error = false;

	private int dy = 2;  

	public void startVideo(){
		if(error) return;   

		try {
			player.start();
		} catch (MediaException e) {
			Handler handler = new Handler();
			handler.handle(e);
		}
		System.out.println("start!!!!");

	}

	public void stopVideo(){
		if(player != null){
			try {
				player.stop();
			} catch (MediaException e) {
				Handler handler = new Handler();
				handler.handle(e);
			}
		}
	}

	public void close(){
		player.close();
	}

	public void setController( AbstractController controller ){
		this.setCommandListener(controller);
	}


	public PlayVideoScreen(MIDlet midlet,InputStream storedVideo, String type){
		super(false); // do not supress key events

		// create the alerts, canvas and displays

		System.out.println("Stream: "+ storedVideo + "type: " + type);

		this.addCommand(start);
		this.addCommand(stop);
		this.addCommand(back);

		//#ifdef includeCopyPhoto
		this.addCommand(copy);
		//#endif

		try {
			System.out.println("Supported Content Types:");
			String types[] = Manager.getSupportedContentTypes("http");
			for(int i = 0 ; i< types.length ; i++ ){
				System.out.println(types[i]);
			}

			// change content type for different devices, mp4 for C975, mpeg4 for M75
			// #if simulatePlayVideo
			player = Manager.createPlayer( getClass().getResourceAsStream("/images/fish.mpg"), "video/mpeg");	
			//#else
			player = Manager.createPlayer(storedVideo, "video/mpeg");
			//#endif

			player.realize();

			videoControl = (VideoControl)player.getControl("VideoControl");      
		} catch (Exception e) {
			Handler handler = new Handler();
			handler.handle(e);
		}
		try{ 
			// this.setCommandListener(controller);
			System.out.println("Criou os comandos e vai iniciar o display");
			// initialize the VideoControl display
			videoControl.initDisplayMode(VideoControl.USE_DIRECT_VIDEO, this);
		}catch(Exception e){
			Handler handler = new Handler();
			handler.handle(e);
		} 
		int halfCanvasWidth = this.getWidth();
		int halfCanvasHeight = this.getHeight();
		try {      
			videoControl.setDisplayFullScreen(false);
			videoControl.setDisplaySize(halfCanvasWidth-10, halfCanvasHeight-10);
			videoControl.setDisplayLocation(5, 5);      
		} catch(Exception e) {
			Handler handler = new Handler();
			handler.handle(e);
		}      
	}

	public void paint(Graphics g) { 
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());  
		flushGraphics();
	}

	public void keyPressed(int keyCode) {
		int gameAction = getGameAction(keyCode);
		int y = videoControl.getDisplayY();
		if(gameAction == UP) {
			y -= dy;
		} else if(gameAction == DOWN) {
			y += dy;
		}
		videoControl.setDisplayLocation(videoControl.getDisplayX(), y);   
		repaint();     
	} 

	public void setVisibleVideo(){
		//display.setCurrent(this);
		videoControl.setVisible(true);
	}
}


//#endif