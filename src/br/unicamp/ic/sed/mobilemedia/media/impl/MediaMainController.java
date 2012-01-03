package br.unicamp.ic.sed.mobilemedia.media.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
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
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IExceptionHandler;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IMobileResources;

//#ifdef includeCopyPhoto
class MediaMainController implements CommandListener{
	//#else
	//class MediaMainController{
	//#endif
	
	//private MediaListScreen mediaListScreen;
	private MediaListController mediaListController;
	
	private MIDlet midlet;
	
	private String lastAlbumName;
	
	private IManager manager ;

	private IFilesystem filesystem = null;

	
	
	MediaMainController() {
		this.manager = ComponentFactory.createInstance();
		System.out.println("[MediaMainController:constructor]");
	}
	


	protected String getLastAlbumName() {
		return lastAlbumName;
	}



	protected void setLastAlbumName(String lastAlbumName) {
		this.lastAlbumName = lastAlbumName;
	}



	private IFilesystem getFilesystem() {
		if( this.filesystem == null){
			filesystem = (IFilesystem) this.manager.getRequiredInterface("IFilesystem");
		}
		return filesystem;
	}

	protected void showMediaList( String albumName , String mediaType ){
		try {
			this.lastAlbumName = albumName;

			IMobileResources resources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
			this.midlet = resources.getMainMIDlet();

			mediaListController = new MediaListController( midlet );
			//#if (Album && includeMusic) || (includeMusic && includeVideo) || (Album && includeVideo)
			if( mediaType != null )
				ScreenSingleton.getInstance().setCurrentMediaType( mediaType );
			//#endif

			ScreenSingleton.getInstance().setCurrentStoreName( albumName );
			mediaListController.showMediaList(albumName, false, false);

		} catch (UnavailablePhotoAlbumException e) {
			IExceptionHandler handler = (IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
			handler.handle(e);
		}
	}

	protected void showLastMediaList(){
		if( this.lastAlbumName != null )
			this.showMediaList( this.lastAlbumName , null );
		else{
			IAlbum album = (IAlbum) manager.getRequiredInterface("IAlbum");
			album.reinitAlbumListScreen();
		}
	}

	//#ifdef includeCopyPhoto
	private String mediaName;
	private AddMediaToAlbum addMediaToAlbum;

	protected void initCopyMediaToAlbum(String oldMediaName, String oldAlbumName, byte[] mediaBytes , Object media , String mediaType, String fileExtension ) {
		System.out.println("[MediaMainController:initCopyMediaToAlbum] oldMediaName="+oldMediaName+" oldAlbumName="+oldAlbumName+" media="+media+" mediaType="+mediaType);
		
		String title = new String("Copy "+mediaType+" to album");
		String labelMediaPath = new String("Copy to Album:");

		AddMediaToAlbum addMediaToAlbum =  new AddMediaToAlbum( title , mediaType , fileExtension );
	

		addMediaToAlbum.setMediaName( oldMediaName );
		addMediaToAlbum.setAlbumName( oldAlbumName );
		//addMediaToAlbum.setLabelMediaPath( labelMediaPath );

		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		if ( (media != null ) && ( mediaType.equals(Constants.IMAGE_MEDIA))){
			System.out.println("[MediaMainController:initCopyMediaToAlbum] media="+media);
			addMediaToAlbum.setImage( (Image)media );
			addMediaToAlbum.setImageBytes( mediaBytes );
		}
		//#endif	

		
		//Get all required interfaces for this method
		IMobileResources resources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
		this.midlet = resources.getMainMIDlet();

		Display.getDisplay( midlet ).setCurrent( addMediaToAlbum );

		addMediaToAlbum.setCommandListener(this);
		
		this.addMediaToAlbum = addMediaToAlbum ;

	}

	/*private void savePhoto() throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException, UnavailablePhotoAlbumException, MediaNotFoundException{
		IMediaData mediaData = null;
		IFilesystem filesystem = this.getFilesystem();


		// #ifdef includeSmsFeature
		
		Image img = this.addMediaToAlbum.getImage();
		
		if (img == null)
		//#endif
		{
			String mediaType = this.addMediaToAlbum.getMediaType();
			String albumName = this.getLastAlbumName();
			mediaData = filesystem.getMediaInfo(mediaName , albumName, mediaType );
			
		}


		AddMediaToAlbum addMediaToAlbum = this.addMediaToAlbum;


		String oldPhotoName = addMediaToAlbum.getMediaName(); 


		String oldAlbumName = addMediaToAlbum.getAlbumName();


		// TODO change the last parameter to allow other image types
		// #ifdef includeSmsFeature
		
		if (img != null){ 
			filesystem.addImageData(oldPhotoName, addMediaToAlbum.getImageBytes(), oldAlbumName, Constants.PNG);
		}
		// #endif 

		// #if includeCopyPhoto && includeSmsFeature
		
		if (img == null)
			//#endif

			// #if includeCopyPhoto 
		
			filesystem.addMediaData( oldPhotoName, mediaData, oldAlbumName);
		// #endif

		//((MusicController)this.getNextController()).showImageList(ScreenSingleton.getInstance().getCurrentStoreName(), false, false);

		ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
		this.showLastMediaList();
	}*/


	private void saveMedia() throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException, UnavailablePhotoAlbumException, MediaNotFoundException{
		IMediaData mediaData = null;
		IFilesystem filesystem = this.getFilesystem();

		AddMediaToAlbum addMediaToAlbum = this.addMediaToAlbum;
		System.out.println("[MediaMAinController:saveMedia] addMediaToAlbum="+addMediaToAlbum);
		
		//get new media name 
		String newMediaName = addMediaToAlbum.getLabeltxt(); 
		System.out.println("[MediaMAinController:saveMedia] mediaName="+newMediaName);
		
		//get new album name
		String newAlbumName = addMediaToAlbum.getMediapathtxt();
		System.out.println("[MediaMAinController:saveMedia] albumName="+newAlbumName);
		
		//get media type
		String mediaType = addMediaToAlbum.getMediaType();
		System.out.println("[MediaMAinController:saveMedia] mediaType="+mediaType);
		
		//get media data using old key (= old media name + old album name)
		String oldMediaName = addMediaToAlbum.getMediaName();
		String oldAlbumName = addMediaToAlbum.getAlbumName();
		mediaData = filesystem.getMediaInfo(oldMediaName , oldAlbumName, mediaType);
		System.out.println("[MediaMAinController:saveMedia] mediaData="+mediaData);
		
		filesystem.addMediaData( newMediaName, mediaData, newAlbumName);

		//ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
		this.showLastMediaList();
	}

	public void commandAction(Command command, Displayable arg1) {
		IExceptionHandler handler = (IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");


		if( command.getLabel().equals("Save Item")){
			try {
				String mediaType = null;
				if(this.addMediaToAlbum != null)
					mediaType = this.addMediaToAlbum.getMediaType();
				
				System.out.println("MediaMainController -> Save Item");
				
				//#ifdef Album
				if(mediaType.equals(Constants.IMAGE_MEDIA))
					//this.savePhoto();
					this.saveMedia();
				//#endif
				
				//#ifdef includeMusic
				if(mediaType.equals(Constants.MUSIC_MEDIA))
					this.saveMedia();
				//#endif
				
				//#ifdef includeVideo
				if(mediaType.equals(Constants.VIDEO_MEDIA))
					this.saveMedia();
				//#endif

				
				System.out.println("[MediaMainController:commandAction()]Error: media type ["+mediaType+"] is not supported");

			} catch (ImageNotFoundException e) {
				handler.handle(e);
			} catch (NullAlbumDataReference e) {
				handler.handle(e);
			} catch (InvalidImageDataException e) {
				handler.handle(e);
			} catch (PersistenceMechanismException e) {
				handler.handle(e);
			} catch (UnavailablePhotoAlbumException e) {
				handler.handle(e);
			} catch (MediaNotFoundException e) {
				handler.handle(e);
			}
		}
	}
	//#endif


	/*protected void initCopyMediaToAlbum(String mediaName, byte[] mediaBytes,
			Object media, String mediaType){

		String title = new String("Copy Media to Album");
		String labelMediaPath = new String("Copy to Album:");

		AddMediaToAlbum addMediaToAlbum = null;

		addMediaToAlbum = new AddMediaToAlbum( title , mediaType );

		addMediaToAlbum = new AddMediaToAlbum( title , mediaType );


		addMediaToAlbum.setMediaName( mediaName );
		addMediaToAlbum.setLabelMediaPath( labelMediaPath );


		if ( media != null ){
			addMediaToAlbum.setImage( (Image)media );
			addMediaToAlbum.setImageBytes( mediaBytes );
		}


		this.mediaName = mediaName;


		//Get all required interfaces for this method
		IMobileResources resources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
		this.midlet = resources.getMainMIDlet();

		//addMediaToAlbum.setCommandListener( this );
		Display.getDisplay( midlet ).setCurrent( addMediaToAlbum );

		addMediaToAlbum.setCommandListener(this);

	}

	 *//**
	 * Added in MM-Cosmos-v7
	 * Based on savePhoto(..)
	 * 
	 */
	/*protected void saveMedia(String albumName, String title, String mediaType) throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException, UnavailablePhotoAlbumException{

		AddMediaToAlbum addMediaToAlbum = new AddMediaToAlbum(title,mediaType);

		String mediaName = addMediaToAlbum.getMediaName(); 
		System.out.println("[PhotoViewController.saveMedia] mediaName = "+mediaName);

		String path = addMediaToAlbum.getPath();
		System.out.println("[PhotoViewController.saveMedia] path = "+path);

		filesystem.addNewMediaToAlbum(mediaType, mediaName, path, albumName);

		ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
		this.showLastMediaList();
	}*/
}
