//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.video.impl;

import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.video.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.video.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.video.spec.prov.IVideo;
import br.unicamp.ic.sed.mobilemedia.video.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.video.spec.req.IMobileResources;

class IVideoFacade implements IVideo {

	private PlayVideoController playVideoController;
	
	private MIDlet midlet;
	
	private IManager manager;
	
	IVideoFacade(IManager mgr) {
		this.manager = mgr; 
		
		
	}
	
	private PlayVideoController getPlayVideoController(String albumName, String videoName) throws MediaNotFoundException, PersistenceMechanismException, NullAlbumDataReference{
		System.out.println("[IVideoFacade:getPlayVideoController]AlbumName: " + albumName + " : VideoName:  "+ videoName );
		//if( this.playVideoController == null){
			System.out.println("[IVideoFacade:getPlayVideoController] playVideoController is null");
			this.midlet = this.getMidlet();
			//get video inputstream
			IFilesystem filesystem = (IFilesystem) this.manager.getRequiredInterface("IFilesystem");
			System.out.println("[IVideoFacade:getPlayVideoController] filesystem="+filesystem);
			InputStream videoStream = filesystem.getVideoStreamFromRecordStore(albumName, videoName);
			
			//get video metadata
			System.out.println("AlbumNameFACADE: " +  albumName );
			IMediaData mediaData = filesystem.getMediaInfo(videoName, albumName, Constants.VIDEO_MEDIA);
			String fileExtension = mediaData.getFileExtension();
					
			//create video controller
			PlayVideoScreen playVideoScreen = new PlayVideoScreen(midlet,videoStream,Constants.VIDEO_MEDIA+"/"+fileExtension);
			this.playVideoController = new PlayVideoController(midlet,playVideoScreen);		
			this.playVideoController.setMediaName( videoName );
			playVideoScreen.setController( this.playVideoController );
			
			
			this.playVideoController.setCurrentStoreName( albumName );
			Display.getDisplay( this.getMidlet() ).setCurrent( playVideoScreen );
			playVideoScreen.setVisibleVideo();
		//}
		//debug
		System.out.println("[IVideoFacade:getPlayVideoController] playVideoController="+playVideoController);
		
		return playVideoController;
	}

	private MIDlet getMidlet() {
		
		if( this.midlet == null){
			IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
			this.midlet = mobileResources.getMainMIDlet();
		}
		return midlet;
	}

	public boolean playVideo(String albumName, String videoName) throws MediaNotFoundException, PersistenceMechanismException, NullAlbumDataReference{
		//define current album name
		this.playVideoController = this.getPlayVideoController( albumName , videoName );
		
		
		//define the name of the selected media
		//this.playVideoController.setMediaName(videoName);
		
		//start video
		//Command startCommand = new Command("Start", Command.ITEM, 1);
		//return this.playVideoController.postCommand(startCommand);
		return true;
	}

	
	public boolean captureVideo(String videoName, String albumName) {
		MIDlet midlet = this.getMidlet();
		System.out.println("Ivideofacade:capturevideo "+videoName+" "+albumName);
		
		CaptureVideoScreen captureVideoScr = new CaptureVideoScreen( midlet, CaptureVideoScreen.CAPTUREVIDEO);
		System.out.println("Ivideofacade:capturevideo  depois de criar o capturevideoscreen");
		
		captureVideoScr.setVisibleVideo();
		System.out.println("Ivideofacade:capturevideo  depois setar o video como visivel");
		
		VideoCaptureController videoCaptureController = new VideoCaptureController( midlet, captureVideoScr );
		System.out.println("Ivideofacade:capturevideo  depois de criar videocapturecontroller");
		
		videoCaptureController.setCurrentVideoName(videoName);
		System.out.println("Ivideofacade:capturevideo  depois de setar o video corrente como "+videoName);
		
		videoCaptureController.setCurrentAlbumName(albumName);
		System.out.println("Ivideofacade:capturevideo  depois de setar o album corrente como "+albumName);
		
		videoCaptureController.setCaptureVideoScreen(captureVideoScr);
		Command start = new Command("Start", Command.EXIT, 1);
		return videoCaptureController.handleCommand( start );
		
	}

}
//#endif