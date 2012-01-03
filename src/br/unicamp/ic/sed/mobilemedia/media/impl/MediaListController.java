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
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IAlbum;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IFilesystem;

class MediaListController extends AbstractController {

	private MIDlet midlet;

	public MediaListController(MIDlet midlet) {
		super( midlet );
		this.midlet = midlet;

	}

	// #ifdef includeSorting
	/**
	 * Sorts an int array using basic bubble sort
	 * 
	 * @param numbers the int array to sort
	 */
	private void bubbleSort(IMediaData[] images) {
		System.out.print("Sorting by BubbleSort...");		
		for (int end = images.length; end > 1; end --) {
			for (int current = 0; current < end - 1; current ++) {
				if (images[current].getNumberOfViews() > images[current+1].getNumberOfViews()) {
					exchange(images, current, current+1);
				}
			}
		}
		System.out.println("done.");
	}
	// #endif

	// #ifdef includeSorting
	/**
	 * @param images
	 * @param pos1
	 * @param pos2
	 */
	private void exchange(IMediaData[] images, int pos1, int pos2) {
		IMediaData tmp = images[pos1];
		images[pos1] = images[pos2];
		images[pos2] = tmp;
	}
	// #endif





	/* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#handleCommand(java.lang.String)
	 */
	public boolean handleCommand(Command command) throws UnavailablePhotoAlbumException, ImageNotFoundException, NullAlbumDataReference, PersistenceMechanismException, InvalidImageDataException, MediaNotFoundException {
		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);
		IManager manager = ComponentFactory.createInstance();
		/** Case: Select PhotoAlbum to view **/
		if (label.equals("Select")) {
			// Get the name of the PhotoAlbum selected, and load image list from
			// RecordStore
			List down = (List) Display.getDisplay(midlet).getCurrent();
			ScreenSingleton.getInstance().setCurrentStoreName(down.getString(down.getSelectedIndex()));
			this.showMediaList(getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN);
			return true;
		}
		else if( label.equals("Back")){
			
			IAlbum album = (IAlbum)manager.getRequiredInterface("IAlbum");
			album.reinitAlbumListScreen();
			return true;
		}
		

		return false;
	}



	/**
	 * Show the list of images in the record store
	 * TODO: Refactor - Move this to ImageList class
	 * @throws UnavailablePhotoAlbumException 
	 */
	protected void showMediaList(String recordName, boolean sort, boolean favorite) throws UnavailablePhotoAlbumException {

		//#if (Album && includeMusic) || (includeMusic && includeVideo) || (Album && includeVideo)
		this.setTypeOfMedia( ScreenSingleton.getInstance().getCurrentMediaType() );
		//#endif

		ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN );
		if (recordName == null)
			recordName = getCurrentStoreName();


		IManager manager = ComponentFactory.createInstance();

		MediaController mediaController = new MediaController( midlet );

		//#if (Album && includeMusic) || (includeMusic && includeVideo) || (Album && includeVideo)
		mediaController.setTypeOfMedia( this.getTypeOfMedia() );
		//#endif

		mediaController.setAlbumName( recordName );
		mediaController.setNextController(this);

		MediaListScreen mediaListScreen = null;

		//#ifdef Album 
		if( this.getTypeOfMedia().equals(Constants.IMAGE_MEDIA))
			mediaListScreen = new MediaListScreen( MediaListScreen.SHOWPHOTO );
		//#endif

		//#ifdef includeMusic
		if( this.getTypeOfMedia().equals(Constants.MUSIC_MEDIA))
			mediaListScreen = new MediaListScreen( MediaListScreen.PLAYMUSIC );
		//#endif

		//#ifdef includeVideo
		if( this.getTypeOfMedia().equals(Constants.VIDEO_MEDIA))
			mediaListScreen = new MediaListScreen( MediaListScreen.PLAYVIDEO );
		//#endif

		mediaListScreen.setCommandListener(mediaController);

		//debug
		System.out.println("[MediaListController:showMedialist] current command listener="+mediaController.getClass().getName());


		//Command selectCommand = new Command("Open", Command.ITEM, 1);
		mediaListScreen.initMenu();


		IMediaData[] images = null;
	
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		images = filesystem.getMedias(recordName, this.typeOfmedia );


		if (images==null) return;

		// #ifdef includeSorting
		// [EF] Check if sort is true (Add in the Scenario 02)
		if (sort) {
			this.bubbleSort(images);
		}
		// #endif

		//loop through array and add labels to list
		for (int i = 0; i < images.length; i++) {
			if (images[i] != null) {
				//Add album name to menu list

				String imageLabel = images[i].getMediaLabel();
				// #ifdef includeFavourites
				// [EF] Check if favorite is true (Add in the Scenario 03)
				if (favorite) {
					if (images[i].isFavorite())
						mediaListScreen.append(imageLabel, null);
				}
				else 
					// #endif
					mediaListScreen.append(imageLabel, null);

			}
		}
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN );
		this.setCurrentScreen(mediaListScreen);
	}

	protected String getCurrentStoreName() {
		return ScreenSingleton.getInstance().getCurrentStoreName();
	}

	
	


}
