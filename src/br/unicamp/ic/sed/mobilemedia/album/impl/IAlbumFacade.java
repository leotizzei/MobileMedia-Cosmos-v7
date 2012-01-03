/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */ 
package br.unicamp.ic.sed.mobilemedia.album.impl;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.album.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum;
import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IMobileResources;


class IAlbumFacade implements IAlbum{

	

	//#ifdef Album
	private AlbumListScreen albumPhoto;
	private AlbumController albumController;
	private BaseController imageRootController;
	//#endif

	//#ifdef includeMusic
	private AlbumListScreen albumMusic;
	private AlbumController albumMusicController;
	private BaseController musicRootController;
	//#endif


	//#ifdef includeVideo
	private AlbumListScreen albumVideo;
	private AlbumController albumVideoController;
	private BaseController videoRootController;
	//#endif

	public void reinitAlbumListScreen(){
		//#if (Album && includeMusic) || (includeMusic && includeVideo) || (Album && includeVideo)
		if( ScreenSingleton.getInstance().getCurrentMediaType() != null ){
			if( ScreenSingleton.getInstance().getCurrentMediaType().equals(Constants.IMAGE_MEDIA) ){
				ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
				albumController.setCurrentScreen( imageRootController.getAlbumListScreen() );
			}
			else{
				if( ScreenSingleton.getInstance().getCurrentMediaType().equals(Constants.MUSIC_MEDIA) ){
					ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
					albumController.setCurrentScreen( musicRootController.getAlbumListScreen() );
				}
				else{
					ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
					albumController.setCurrentScreen( videoRootController.getAlbumListScreen() );
				}
			}
		}else{
			albumController.setCurrentScreen( ScreenSingleton.getInstance().getMainMenu() );
		}
		//#elif Album
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
		albumController.setCurrentScreen( imageRootController.getAlbumListScreen() );	
		//#elif includeMusic
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
		albumMusicController.setCurrentScreen( musicRootController.getAlbumListScreen() );
		//#elif includeVideo
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
		albumMusicController.setCurrentScreen( videoRootController.getAlbumListScreen() );
		//#endif

	}

	public void startUp() {

		IManager manager = ComponentFactory.createInstance();
		IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");

		MIDlet midlet = mobileResources.getMainMIDlet();
		int i = 0;

		// #ifdef Album
		albumPhoto = new AlbumListScreen(Constants.IMAGE_MEDIA);

		imageRootController = new BaseController( midlet , albumPhoto );

		albumController = new AlbumController( midlet , albumPhoto );

		albumController.setNextController( imageRootController );

		albumPhoto.setCommandListener(albumController);
		//#endif

		//#if (Album && includeMusic) || (Album && includeVideo)
		imageRootController.setTypeOfMedia( Constants.IMAGE_MEDIA );
		//#endif
		
		
		// #ifdef includeMusic
		albumMusic = new AlbumListScreen(Constants.MUSIC_MEDIA);

		musicRootController = new BaseController( midlet , albumMusic );

		albumMusicController = new AlbumController(midlet , albumMusic);

		albumMusicController.setNextController(musicRootController );

		albumMusic.setCommandListener(albumMusicController);
		//#endif
		
		//#if (Album && includeMusic) || (includeMusic && includeVideo)
		musicRootController.setTypeOfMedia( Constants.MUSIC_MEDIA );
		//#endif

		
		// #ifdef includeVideo
		albumVideo = new AlbumListScreen(Constants.VIDEO_MEDIA);

		videoRootController = new BaseController( midlet , albumVideo );

		albumVideoController = new AlbumController(midlet , albumVideo);

		albumVideoController.setNextController(videoRootController );

		albumVideo.setCommandListener(albumVideoController);
		//#endif

		//#if (Album && includeVideo) || (includeMusic && includeVideo)
		videoRootController.setTypeOfMedia( Constants.VIDEO_MEDIA );
		//#endif
		
		// #if (includeMusic && Album && includeVideo) 
		SelectMediaController selectcontroller = new SelectMediaController( midlet , albumPhoto , imageRootController , musicRootController, videoRootController);
		
		// #elif (includeMusic && Album)
		SelectMediaController selectcontroller = new SelectMediaController( midlet , albumPhoto , imageRootController , musicRootController, null);
		
		// #elif (includeVideo && Album)
		SelectMediaController selectcontroller = new SelectMediaController( midlet , albumPhoto , imageRootController , null, videoRootController);
		
		// #elif (includeMusic && includeVideo)
		SelectMediaController selectcontroller = new SelectMediaController( midlet , albumPhoto , null , musicRootController, videoRootController);
		// #endif
		
		//#ifdef Album
		selectcontroller.setNextController(imageRootController);
		//#endif 
		
		//#ifdef includeMusic
		selectcontroller.setNextController(musicRootController);
		//#endif
		
		//#ifdef includeVideo
		selectcontroller.setNextController(videoRootController);
		//#endif
		
		SelectTypeOfMedia mainscreen = new SelectTypeOfMedia();
		
		mainscreen.initMenu();
		
		mainscreen.setCommandListener(selectcontroller);
		
		Display.getDisplay( midlet ).setCurrent(mainscreen);
		
		ScreenSingleton.getInstance().setMainMenu(mainscreen);
		
		
		//#if Album && !includeVideo && !includeMusic
		imageRootController.init( );
		//#elif includeMusic && !includeVideo && !Album
		musicRootController.init( );
		//#elif includeVideo && !includeMusic && !Album
		videoRootController.init( );
		//#endif
		

	}

	public void initAlbumListScreen(String[] albumNames) {}

}