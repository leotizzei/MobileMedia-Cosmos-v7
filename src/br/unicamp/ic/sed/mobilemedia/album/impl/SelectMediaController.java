// #if (includeMusic && Album) || (Album && includeVideo) || (includeMusic && includeVideo)
// [NC] Added in the scenario 07

package br.unicamp.ic.sed.mobilemedia.album.impl;


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.album.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.album.spec.excep.MediaNotFoundException;



public class SelectMediaController extends AbstractController {
	//#ifdef Album
	private BaseController basePhotoController;
	//#endif	
	
	//#ifdef includeMusic
	private BaseController baseMusicController;
	//#endif
	
	//#ifdef includeVideo
	private BaseController baseVideoController;
	//#endif
	
	public SelectMediaController(MIDlet midlet, AlbumListScreen albumListScreen , BaseController photo , BaseController music , BaseController video) {
		super(midlet , albumListScreen );
		//#ifdef Album
		this.basePhotoController = photo;
		//#endif
		
		//#ifdef includeMusic
		this.baseMusicController = music;
		//#endif
		
		//#ifdef includeVideo
		this.baseVideoController = video;
		//#endif
	}
	
	
	public boolean handleCommand(Command command) throws MediaNotFoundException {
		String label = command.getLabel();
      	System.out.println( "<* SelectMediaController.handleCommand() *>: " + label);
		
      	if (label.equals("Select")) {
 			List down = (List) Display.getDisplay(this.getMidlet()).getCurrent();
 			
 			//#ifdef Album
 			if (down.getString(down.getSelectedIndex()).equals("Photos")){
 				ScreenSingleton.getInstance().setCurrentMediaType( Constants.IMAGE_MEDIA );
 				basePhotoController.init();
 			}
 			//#endif
 			
 			//#ifdef includeMusic
 			if (down.getString(down.getSelectedIndex()).equals("Musics")){
 				ScreenSingleton.getInstance().setCurrentMediaType( Constants.MUSIC_MEDIA );
				baseMusicController.init();
 			}
 			//#endif
 			
 			//#ifdef includeVideo
 			if (down.getString(down.getSelectedIndex()).equals("Videos")){
 				System.out.println("[SelectMediaController:handleCommand]o video foi escolhido!!" );
 				ScreenSingleton.getInstance().setCurrentMediaType( Constants.VIDEO_MEDIA);
 				baseVideoController.init();
 			}
 			//#endif
 			
			return true;	
      	}
      	return false;
	}

}
//#endif