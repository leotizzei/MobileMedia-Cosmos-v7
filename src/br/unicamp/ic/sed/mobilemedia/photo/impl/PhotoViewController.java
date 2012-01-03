/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */

//#if includeCopyPhoto && Album
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;


import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMedia;



/**
 * Added in MobileMedia-Cosmos-OO-v4
 */

class PhotoViewController extends AbstractController {

	private String imageName = "";
	
	private Displayable lastDisplay;
	
	// #ifdef capturePhoto
	// [NC] Added in the scenario 08
	private CaptureVideoScreen cpVideoScreen = null;
	//#endif
	
	

	public PhotoViewController(MIDlet midlet,  String imageName) {
		super( midlet );
		this.setImageName(imageName);
		

	}
	
	

	/* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#handleCommand(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public boolean handleCommand(Command c) throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException, MediaNotFoundException{
		String label = c.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);
		
		IManager manager = ComponentFactory.createInstance();
		IMedia imedia = (IMedia) manager.getRequiredInterface("IMedia");
		
		/** Case: Copy photo to a different album */
		if (label.equals("Copy")) {
			PhotoViewScreen photoViewScreen = (PhotoViewScreen) this.getCurrentScreen();
			photoViewScreen.setFromSMS(false);
			this.initCopyPhotoToAlbum( );
			this.lastDisplay = photoViewScreen;
			return true;
		}
		
		else if( label.equals("Back")){
			imedia.showLastMediaList();
			return true;
		}
		else if( label.equals("Cancel")){
			this.setCurrentScreen( this.lastDisplay );
			return true;
		}
		// #ifdef capturePhoto
		// [NC] Added in the scenario 08	
		else if (label.equals("Take photo")){
			CaptureVideoScreen captureVideoScr = this.getCaptureVideoScreen();
			captureVideoScr.setCommandListener(this);
			byte[] newPhoto = captureVideoScr.takePicture();
			String albumName = this.getCurrentStoreName();
			String imageName = this.getImageName();
			

			imedia.initCopyMediaToAlbum( imageName, albumName, newPhoto, null, Constants.IMAGE_MEDIA, null);
		
			
			return true;

		}
		//TODO there is a bug in the original mobilemedia in this part
		else if(label.equalsIgnoreCase("Yes")){
			System.out.println("YES!");
		}
		//#endif
		

		return false;
	}



	private String getImageName() {
		return imageName;
	}



	private void setImageName(String imageName) {
		this.imageName = imageName;
	}



	// #ifdef includeCopyPhoto
	private void initCopyPhotoToAlbum() throws MediaNotFoundException, NullAlbumDataReference {
		IManager manager = ComponentFactory.createInstance();
		
		IMedia media = (IMedia)manager.getRequiredInterface("IMedia");
		
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		
		String albumName = this.getCurrentStoreName();
		
		String imageName = this.getImageName();
		IMediaData mediaData = filesystem.getMediaInfo(imageName, albumName, Constants.IMAGE_MEDIA);
		
		String fileExtension = mediaData.getFileExtension();
		
		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		PhotoViewScreen photoViewScreen = (PhotoViewScreen) this.getCurrentScreen();
		boolean isFromSMS = photoViewScreen.isFromSMS();
		System.out.println("[PhotoViewController:initCopyPhotoToAlbum] isFromSMS="+isFromSMS);
		if ( isFromSMS ){
			
			Object mediaObj = photoViewScreen.getImage();
			byte[] mediaBytes = photoViewScreen.getImageBytes();
			
			
			media.initCopyMediaToAlbum(imageName, albumName, mediaBytes, mediaObj, Constants.IMAGE_MEDIA, fileExtension);
			return;
		}
		//#endif
		
		System.out.println("[PhotoViewController:initCopyPhotoToAlbum] "+imageName+","+albumName);
		media.initCopyMediaToAlbum(imageName, albumName, null, null, Constants.IMAGE_MEDIA, fileExtension);
		
	}
	// #endif
	
	// #ifdef capturePhoto
	// [NC] Added in the scenario 08
	public CaptureVideoScreen getCaptureVideoScreen() {
		return cpVideoScreen;
	}

	public void setCaptureVideoScreen(CaptureVideoScreen cpVideoScreen) {
		this.cpVideoScreen = cpVideoScreen;
	}
	//#endif
}
//#endif