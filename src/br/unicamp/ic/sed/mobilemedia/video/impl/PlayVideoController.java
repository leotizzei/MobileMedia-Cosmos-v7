/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: March 2009

 * Changed in MM-Cosmos-v7
 */
//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.video.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.midlet.MIDlet;


import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.video.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.video.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.video.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.video.spec.req.IMedia;

class PlayVideoController extends AbstractController{
	// #ifdef includeCopyPhoto
	// [NC] Added in the scenario 07
	private String mediaName;
	//#endif

	private PlayVideoScreen pmscreen;

	private String currentAlbumName = null;

	private IMedia media;
	
	public PlayVideoController(MIDlet midlet,PlayVideoScreen pmscreen) {
		super( midlet );
		this.pmscreen = pmscreen;
		IManager mgr = ComponentFactory.createInstance();
		this.media = (IMedia) mgr.getRequiredInterface("IMedia");

	}

	protected void setCurrentStoreName(String albumName){
		this.currentAlbumName = albumName;
	}

	protected String getCurrentAlbumName() {
		return currentAlbumName;
	}

	protected void setCurrentAlbumName(String currentAlbumName) {
		this.currentAlbumName = currentAlbumName;
	}

	public boolean handleCommand(Command command) throws MediaNotFoundException, NullAlbumDataReference {
		String label = command.getLabel();
		System.out.println( "<* PlayVideoController.handleCommand() *> " + label);
		
		/** Case: Copy photo to a different album */
		if (label.equals("Start")) {
			pmscreen.startVideo();
			return true;
		}else if (label.equals("Stop")) {
			pmscreen.stopVideo();
			return true;
		}else if ((label.equals("Back"))||(label.equals("Cancel"))){
			pmscreen.stopVideo();
			// [NC] Changed in the scenario 07: just the first line below to support generic AbstractController
			/* [LT]
			 * ((AlbumListScreen) getAlbumListScreen()).repaintListAlbum(getAlbumData().getAlbumNames());
			setCurrentScreen( getAlbumListScreen() );
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.ALBUMLIST_SCREEN);*/
			String albumName = this.getCurrentAlbumName();
			System.out.println("VideoAlbum: " + albumName );
			this.media.showMediaList( albumName , Constants.VIDEO_MEDIA);
			return true;
		}
		// #ifdef includeCopyPhoto
		// [NC] Added in the scenario 07
		/** Case: Copy photo to a different album */
		else if (label.equals("Copy")) {
			//get video name
			String mediaName = this.getMediaName();
			
			//get file extension
			IManager manager = ComponentFactory.createInstance();
			IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
			IMediaData mediaData = filesystem.getMediaInfo(mediaName, this.currentAlbumName, Constants.VIDEO_MEDIA);
			String fileExtension = mediaData.getFileExtension();
			
			String albumName = this.getCurrentAlbumName();
			
			this.media.initCopyMediaToAlbum( mediaName, albumName, null, null, Constants.VIDEO_MEDIA, fileExtension);
			//Display.getDisplay(this.midlet).setCurrent(copyPhotoToAlbum);
			return true;
		}/* else if (label.equals("Save Item")) {
			try {
				MediaData imageData = null;	
				try {
						imageData = getAlbumData().getMediaInfo(mediaName);
				} catch (ImageNotFoundException e) {
						Alert alert = new Alert("Error", "The selected photo was not found in the mobile device", null, AlertType.ERROR);
						Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
				}
				String albumname = ((AddMediaToAlbum) getCurrentScreen()).getPath();
				getAlbumData().addMediaData(imageData, albumname); 
			} catch (InvalidImageDataException e) {
				Alert alert = null;
				if (e instanceof ImagePathNotValidException)
					alert = new Alert("Error", "The path is not valid", null, AlertType.ERROR);
				else
					alert = new Alert("Error", "The music file format is not valid", null, AlertType.ERROR);
				Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
				return true;
				// alert.setTimeout(5000);
			} catch (PersistenceMechanismException e) {
				Alert alert = null;
				if (e.getCause() instanceof RecordStoreFullException)
					alert = new Alert("Error", "The mobile database is full", null, AlertType.ERROR);
				else
					alert = new Alert("Error", "The mobile database can not add a new music", null, AlertType.ERROR);
				Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
			}
			// [NC] Changed in the scenario 07: just the first line below to support generic AbstractController
			((AlbumListScreen) getAlbumListScreen()).repaintListAlbum(getAlbumData().getAlbumNames());
			setCurrentScreen( getAlbumListScreen() );
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.ALBUMLIST_SCREEN);
			return true;
		}*/
		//#endif

		return false;
	}


	// #ifdef includeCopyPhoto
	// [NC] Added in the scenario 07
	protected String getMediaName() {
		return mediaName;
	}

	protected void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	//#endif
}
//#endif
