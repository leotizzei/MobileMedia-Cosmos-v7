//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.impl;

import java.io.InputStream;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.music.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.music.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.music.spec.req.IExceptionHandler;
import br.unicamp.ic.sed.mobilemedia.music.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.music.spec.req.IMobileResources;

public class MusicController {
	private MIDlet midlet;
	private MusicPlayController musicPlayController = null;
	private PlayMediaScreen playMediaScreen = null;
	
	private String musicName;
	private IManager manager = ComponentFactory.createInstance();
	
	public MusicController(){
		IManager manager = ComponentFactory.createInstance();
		IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
		this.midlet = mobileResources.getMainMIDlet();
		
		manager = ComponentFactory.createInstance();
	}

	public String getImageName() {
		return this.musicName;
	}

	public void playMusic(String albumName, String musicName) {
		this.musicName = musicName;

		System.out.println("[MusicController:playMusic] albumName="+albumName+" musicName="+musicName);
		
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		try {
			
			InputStream music = filesystem.getMusicFromRecordStore(albumName, musicName);
			
			String fileExtension = filesystem.getMusicType(musicName, albumName);
			String type = Constants.MUSIC_MEDIA+"/"+fileExtension;
			
			System.out.println("[MusicController:playMusic] type="+type);
			
			this.playMediaScreen = new PlayMediaScreen( midlet , music , type );
			this.musicPlayController = new MusicPlayController( midlet , playMediaScreen , musicName , albumName);
			
			this.playMediaScreen.setFormController( this.musicPlayController );
			
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGE_SCREEN );
		
		} catch (MediaNotFoundException e) {
			IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
			handler.handle( e );
		} catch (PersistenceMechanismException e) {
			IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
			handler.handle( e );
		}
	}
	
	public void showLastImage(){
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGE_SCREEN );	
		Display.getDisplay( midlet ).setCurrent( this.playMediaScreen );
	}
}
//#endif