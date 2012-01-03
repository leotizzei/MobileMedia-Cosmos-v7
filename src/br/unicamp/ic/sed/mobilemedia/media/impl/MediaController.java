/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.media.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.media.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.media.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IVideo;

//#ifdef includeMusic
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IMusic;
//#endif

//#ifdef Album
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IPhoto;
//#endif

class MediaController extends MediaListController {

	private IMediaData media;

	private MIDlet midlet;

	private NewLabelScreen screen;

	private String currentSelectedMediaName;

	private String albumName;

	public MediaController (MIDlet midlet) {
		super( midlet );
		this.midlet = midlet;
	}

	private void editLabel() throws ImageNotFoundException, NullAlbumDataReference, MediaNotFoundException{
		//TODO print error message on the screen

		String selectedMediaName = getSelectedMediaName();

		String albumName = this.getAlbumName();

		// get metainformation about media
		IManager manager = (IManager) ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		media = filesystem.getMediaInfo(selectedMediaName, albumName, this.typeOfmedia);

		String mediaType = media.getMediaType();

		NewLabelScreen newLabelScreen = new NewLabelScreen("Edit Label "+mediaType, NewLabelScreen.LABEL_PHOTO);
		newLabelScreen.setCommandListener(this);
		this.setScreen( newLabelScreen );
		setCurrentScreen( newLabelScreen );
		newLabelScreen = null;

		ScreenSingleton.getInstance().setCurrentScreenName( Constants.NEWLABEL_SCREEN );

	}



	/**
	 * @return the media
	 */
	private IMediaData getImage() {
		return media;
	}

	private NewLabelScreen getScreen() {
		return screen;
	}

	/**
	 * 
	 * This really only gets the selected List item. 
	 * So it is only an media name if you are on the Photo/Music/Video List screen...
	 * Need to fix thisnextController
	 */
	private String getSelectedMediaName() {
		
		List selected = (List) Display.getDisplay(midlet).getCurrent();
		this.currentSelectedMediaName = selected.getString(selected.getSelectedIndex());
		if( this.currentSelectedMediaName == null ){
			System.out.println("===> [MediaController:getSelectedMediaName] Error! No item is selected!");
		}
		return this.currentSelectedMediaName;
	}

	/**
	 * TODO [EF] update this method or merge with method of super class.
	 * Go to the previous screen
	 * @throws UnavailablePhotoAlbumException 
	 */
	private boolean goToPreviousScreen() throws UnavailablePhotoAlbumException {
		System.out.println("<* MediaController.goToPreviousScreen() *>");
		String currentScreenName = ScreenSingleton.getInstance().getCurrentScreenName();

		if (currentScreenName.equals(Constants.ALBUMLIST_SCREEN)) {
			System.out.println("Can't go back here...Should never reach this spot");
		} else if (currentScreenName.equals(Constants.IMAGE_SCREEN)) {		    
			//Go to the media list here, not the main screen...

			this.showMediaList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}
		else if (currentScreenName.equals(Constants.ADDPHOTOTOALBUM_SCREEN)) {

			this.showMediaList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}else if(currentScreenName.equals(Constants.IMAGELIST_SCREEN)){
			/*IManager manager = ComponentFactory.createInstance();
			IMobilePhone phone = (IMobilePhone) manager.getRequiredInterface("IMobilePhone");
			phone.goToPreviousScreen();
			return true;
			 */
		}else if(currentScreenName.equals(Constants.NEWLABEL_SCREEN )){
			this.showMediaList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}
		System.out.println("[MediaController could not handle either BACK or CANCEL: goToPreviousScreen() returns false]");
		return false;
	}

	/**
	 * modified in MobileMedia-Cosmos-OO-v4
	 * @throws NullAlbumDataReference 
	 * @throws ImageNotFoundException 
	 * @throws PersistenceMechanismException 
	 * @throws br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException 
	 * @throws InvalidImageDataException 
	 * @throws br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException 
	 * @throws UnavailablePhotoAlbumException 
	 * @throws ImageNotFoundException 
	 * @throws MediaNotFoundException 
	 */
	public boolean handleCommand(Command command) throws NullAlbumDataReference, PersistenceMechanismException, InvalidImageDataException, UnavailablePhotoAlbumException, ImageNotFoundException, MediaNotFoundException  {
		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		IManager manager = (IManager) ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		//#if Album && includeMusic
		this.setTypeOfMedia( ScreenSingleton.getInstance().getCurrentMediaType() );
		//#endif

		//#ifdef Album
		if (label.equals("View")) {
			String selectedImageName = getSelectedMediaName();
			showImage(selectedImageName);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGE_SCREEN);
			
			//#ifdef includeSorting
			IMediaData media = filesystem.getMediaInfo( selectedImageName , albumName,  Constants.IMAGE_MEDIA );
			media.increaseNumberOfViews();
			filesystem.updateMediaInfo( media , media );
			//#endif
			
			return true;
		}else
			//#endif


			if( label.equals("Play") ){
				String mediaType = this.getTypeOfMedia();
				String albumName = this.getAlbumName();
				String selectedMediaName = this.getSelectedMediaName();
				
				System.out.println("[Mediacontroller:handleCommand] mediaType="+mediaType+" albumName="+albumName+ " "+ selectedMediaName);
				//#ifdef includeMusic
				if( mediaType.equals(Constants.MUSIC_MEDIA) ){
					IMusic music = (IMusic) manager.getRequiredInterface("IMusic");
					music.playMusic( albumName , selectedMediaName );
				
					//#ifdef includeSorting
					IMediaData media = filesystem.getMediaInfo( selectedMediaName , albumName,  Constants.MUSIC_MEDIA );
					media.increaseNumberOfViews();
					filesystem.updateMediaInfo( media , media );
					//#endif
				}
				
				//#endif
				//#ifdef includeVideo
				if( mediaType.equals(Constants.VIDEO_MEDIA) ){
					IVideo video = (IVideo) manager.getRequiredInterface("IVideo");
					video.playVideo( albumName , selectedMediaName );
				
					//#ifdef includeSorting
					IMediaData media = filesystem.getMediaInfo( selectedMediaName , albumName,  Constants.VIDEO_MEDIA );
					media.increaseNumberOfViews();
					filesystem.updateMediaInfo( media , media );
					//#endif
				
				}
				
				//#endif
				
				
				return true;
			} else	

				/** Case: Add photo * */
				if (label.equals("Add")) {
					ScreenSingleton.getInstance().setCurrentScreenName(Constants.ADDPHOTOTOALBUM_SCREEN);
					String storeName = this.getCurrentStoreName();
					this.initAddMediaToAlbum( storeName , Constants.IMAGE_MEDIA, Constants.PNG);
					return true;

					/** Case: Save Add photo * */
				} else if (label.equals("Save Item")) {
					//TODO print message error on the screen

					AddMediaToAlbum addMediaToAlbum = (AddMediaToAlbum) this.getCurrentScreen();
					String addedMediaName = addMediaToAlbum.getLabeltxt();
					String addedMediaPath = addMediaToAlbum.getMediapathtxt();
					String fileExtension = addMediaToAlbum.getFileExtension();
					String albumName = this.getAlbumName();
					System.out.println("MediaController:handleCommand - Save Item "+addedMediaName+" , "+addedMediaPath+" , "+albumName);
					filesystem.addNewMediaToAlbum( this.typeOfmedia , addedMediaName , fileExtension, addedMediaPath , albumName );

					//#ifdef includeMusic
					String mediaType = addMediaToAlbum.getMediaType();
					if( mediaType.equals(Constants.MUSIC_MEDIA) ){
						filesystem.setMusicType( addedMediaName , albumName, mediaType );
					}
					//#endif

					return goToPreviousScreen();
					/** Case: Delete selected Photo from recordstore * */
				} else if (label.equals("Delete")) {
					String selectedImageName = getSelectedMediaName();

					filesystem.deleteMedia( this.typeOfmedia ,selectedImageName, getCurrentStoreName());

					showMediaList(getCurrentStoreName(), false, false);
					ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
					return true;

					/** Case: Edit photo label
					 *  [EF] Added in the scenario 02 */
				} else if (label.equals("Edit Label")) {
					this.editLabel();
					return true;

					// #ifdef includeSorting
					/**
					 * Case: Sort photos by number of views 
					 * [EF] Added in the scenario 02 */
				} else if (label.equals("Sort by Views")) {
					showMediaList(getCurrentStoreName(), true, false);
					ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);

					return true;
					// #endif

					// #ifdef includeFavourites
					/**
					 * Case: Set photo as favorite
					 * [EF] Added in the scenario 03 */
				} else if (label.equals("Set Favorite")) {
					String selectedImageName = getSelectedMediaName();

					String albumName = this.getAlbumName();
					IMediaData media = filesystem.getMediaInfo(selectedImageName , albumName,  this.typeOfmedia );



					media.toggleFavorite();
					updateMedia(media);
					System.out.println("<* BaseController.handleCommand() *> Image = "+ selectedImageName + "; Favorite = " + media.isFavorite());

					return true;

					/**
					 * Case: View favorite photos 
					 * [EF] Added in the scenario 03 */
				} else if (label.equals("View Favorites")) {
					showMediaList(getCurrentStoreName(), false, true);
					ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);

					return true;
					// #endif

					/** Case: Save new Photo Label */
				} else if (label.equals("Save new label")) {
					NewLabelScreen newLabelScreen = this.getScreen();
					String labelName = newLabelScreen.getLabelName();
					System.out.println("<* MediaController.handleCommand() *> Save Photo Label = "+ labelName);
					this.getImage().setMediaLabel( labelName );
					this.updateMedia(media);
					return this.goToPreviousScreen();

					/** Case: Go to the Previous or Fallback screen * */
				} else if (label.equals("Back")) {
					System.out.println("[MediaController.handleCommand()] Back");
					return this.goToPreviousScreen();

					/** Case: Cancel the current screen and go back one* */
				} else if (label.equals("Cancel")) {
					return this.goToPreviousScreen();

				}
		//#ifdef capturePhoto
				else if( label.equals("Capture Photo")){
					String photoName = this.getSelectedMediaName();
					String albumName = this.getAlbumName();
					IPhoto photo = (IPhoto) manager.getRequiredInterface("IPhoto");
					return photo.capturePhoto(/*photoName, albumName*/);
				}
		//#endif

		// #ifdef captureVideo
				else if (label.equals("Capture Video")) {
					String videoName = this.getSelectedMediaName();
					String albumName = this.getCurrentStoreName();

					IVideo video = (IVideo) manager.getRequiredInterface("IVideo");
					return video.captureVideo(videoName, albumName);			
				}
		//#endif

		return false;
	} 

	private void initAddMediaToAlbum(String albumName, String mediaType, String fileExtension) {

		//Get all required interfaces for this method

		MIDlet midlet = this.getMidlet();
		String title ="Copy Media to Album";
		if( albumName == null){
			System.err.println("Image name is null");
			return;
		}
		else{
			AddMediaToAlbum addMediaToAlbum = null;
			//#ifdef includeMusic

			addMediaToAlbum  = new AddMediaToAlbum( title , mediaType, fileExtension );
			//#else
			addMediaToAlbum  = new AddMediaToAlbum( title , mediaType, fileExtension  );
			//#endif

			addMediaToAlbum.setCommandListener(this);
			Display.getDisplay( midlet ).setCurrent( addMediaToAlbum );

		}
	}

	/**
	 * @param image the image to set
	 */
	/*public void setImage(IMediaData image) {
		this.image = image;
	}*/



	private void setScreen(NewLabelScreen screen) {
		this.screen = screen;
	} 


	/**
	 * Show the current image that is selected
	 * Modified in MobileMedia-Cosmos-OO-v4
	 * @throws PersistenceMechanismException 
	 * @throws ImageNotFoundException 
	 */
	//#if Album
	private void showImage(String name) throws ImageNotFoundException, PersistenceMechanismException {
		// [EF] Instead of replicating this code, I change to use the method "getSelectedMediaName()". 		

		IManager manager = ComponentFactory.createInstance();
		IPhoto photo = (IPhoto) manager.getRequiredInterface( "IPhoto" );
		photo.showPhoto( this.getAlbumName() , name );

	}
	//#endif

	private void updateMedia(IMediaData media) throws InvalidImageDataException, PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		filesystem.updateMediaInfo( media, media);

	}

	

	protected void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	protected String getAlbumName() {
		return albumName;
	}

}
