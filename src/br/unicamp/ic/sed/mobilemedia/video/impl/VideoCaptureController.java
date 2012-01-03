//#ifdef captureVideo
package br.unicamp.ic.sed.mobilemedia.video.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.video.spec.req.IMedia;
import br.unicamp.ic.sed.mobilemedia.video.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.video.spec.prov.IManager;


public class VideoCaptureController extends AbstractController{
	
	private CaptureVideoScreen pmscreen;
	
	private String fileExtension;
	//private AddMediaToAlbum saveVideoToAlbum;
	
	private String currentVideoName;
	
	private String currentAlbumName;
	

	

	public VideoCaptureController(MIDlet midlet, CaptureVideoScreen pmscreen) {
		super(midlet);
		pmscreen.setCommandListener(this);
		
	}

	protected void setCaptureVideoScreen(CaptureVideoScreen cp){
		this.pmscreen = cp;
	}
	
	protected CaptureVideoScreen getCaptureVideoScreen(){
		return this.pmscreen;
	}
	
	public boolean handleCommand(Command command) {
		if( command == null){
			System.out.println("====> [VideoCaptureControl] command is null");
		}
		String label = command.getLabel();
		System.out.println( "<* VideoCaptureController.handleCommand() *> " + label);

		CaptureVideoScreen cp = this.getCaptureVideoScreen();
		System.out.println("cp="+cp);
		if (label.equals("Start")) {
			cp.startCapture();
			return true;
		}else if (label.equals("Stop")) {
			cp.pauseCapture();
			
			IManager manager = ComponentFactory.createInstance();
			IMedia imedia = (IMedia) manager.getProvidedInterface("IMedia");
			byte[] captureVideo = cp.getByteArrays();
			String fileExtension = this.getFileExtension();
			
			String albumName = this.getCurrentAlbumName();
			String videoName = this.getCurrentVideoName();
			
			imedia.initCopyMediaToAlbum(videoName, albumName, captureVideo, null, Constants.VIDEO_MEDIA, fileExtension);
			
			
			return true;
		}
		// implemented in the media component
		/*else if (label.equals("Save Item")) {
			String videoname = ((AddMediaToAlbum) getCurrentScreen()).getItemName();
			String albumname = ((AddMediaToAlbum) getCurrentScreen()).getPath();
			try {
				getAlbumData().addVideoData(videoname, albumname, saveVideoToAlbum.getCapturedMedia());
			} catch (InvalidImageDataException e) {
				e.printStackTrace();
			} catch (PersistenceMechanismException e) {
				e.printStackTrace();
			}
		}*/else if ((label.equals("Back"))||(label.equals("Cancel"))){
			cp.pauseCapture();
			// [NC] Changed in the scenario 07: just the first line below to support generic AbstractController
			IManager manager = ComponentFactory.createInstance();
			IMedia imedia = (IMedia) manager.getRequiredInterface("IMedia");
			imedia.showLastMediaList();
			
			/*((AlbumListScreen) getAlbumListScreen()).repaintListAlbum(getAlbumData().getAlbumNames());
			setCurrentScreen( getAlbumListScreen() );
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.ALBUMLIST_SCREEN);*/
			return true;
		}
		return false;
	}
	
	protected String getFileExtension() {
		return fileExtension;
	}

	protected void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	protected String getCurrentVideoName() {
		return currentVideoName;
	}

	protected void setCurrentVideoName(String currentVideoName) {
		this.currentVideoName = currentVideoName;
	}
	
		
	protected String getCurrentAlbumName() {
		return currentAlbumName;
	}

	protected void setCurrentAlbumName(String currentAlbumName) {
		this.currentAlbumName = currentAlbumName;
	}
}
//#endif
