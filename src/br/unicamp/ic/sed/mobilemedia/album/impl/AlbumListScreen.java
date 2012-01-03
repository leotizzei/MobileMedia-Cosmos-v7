/*
 * Created on Sep 13, 2004
 *
 * Modified on Nov 24, 2008
 */

package br.unicamp.ic.sed.mobilemedia.album.impl;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

import br.unicamp.ic.sed.mobilemedia.music.spec.dt.Constants;


/**
 * 
 *
 * This screen displays a list of photo albums available to select.
 * A user can also create a new album on this screen.
 */
class AlbumListScreen extends List {

	//#if includeMusic && Album
	//[NC] Added in the scenario 07
	public static final Command exitCommand = new Command("Back", Command.STOP, 2);
	//#elif Album || includeMusic
	public static final Command exitCommand = new Command("Exit", Command.STOP, 2);
	//#endif
	private static final Command selectCommand = new Command("Select", Command.ITEM, 1);
	private Command createAlbumCommand ;
	private static final Command deleteAlbumCommand = new Command("Delete Album", Command.ITEM, 1);
	private static final Command resetCommand = new Command("Reset", Command.ITEM, 1);
	
	/**
	 * Constructor
	 */
	public AlbumListScreen(String mediaType) {
		super("Select Album", Choice.IMPLICIT);
		//#ifdef Album
		if(mediaType.equals(Constants.IMAGE_MEDIA))
			createAlbumCommand = new Command("New photo album", Command.ITEM, 1);
		//#endif
		
		//#ifdef includeMusic
		if(mediaType.equals(Constants.MUSIC_MEDIA))
			createAlbumCommand = new Command("New music album", Command.ITEM, 1);
		//#endif
		
		//#ifdef includeVideo
		if(mediaType.equals(Constants.VIDEO_MEDIA))
			createAlbumCommand = new Command("New video album", Command.ITEM, 1);
		//#endif
	}


	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 */
	public AlbumListScreen(String arg0, int arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public AlbumListScreen(String arg0, int arg1, String[] arg2, Image[] arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	
	/**
	 * Initialize the menu items for this screen
	 * 
	 */
	public void initMenu() {
		//#if includeMusic || Album || includeVideo
		this.addCommand(exitCommand);
		//#endif
		this.addCommand(selectCommand);
		this.addCommand(createAlbumCommand);
		this.addCommand(deleteAlbumCommand);
		this.addCommand(resetCommand);
	}
	public void deleteAll(){
		for (int i = 0; i < this.size(); i++) {
			this.delete(i);
		} 
	}
	public void repaintListAlbum(String[] names){
		String[] albumNames = names;
	    this.deleteAll();
		for (int i = 0; i < albumNames.length; i++) {
			if (albumNames[i] != null) {
				//Add album name to menu list
				this.append(albumNames[i], null);
			}
		}
		this.initMenu();
	}

}
