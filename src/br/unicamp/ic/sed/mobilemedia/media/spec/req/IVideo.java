package br.unicamp.ic.sed.mobilemedia.media.spec.req;

import br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;

public interface IVideo {

	public boolean playVideo(String albumName , String videoName) throws MediaNotFoundException, PersistenceMechanismException, NullAlbumDataReference;
	
	//#ifdef captureVideo
	/**
	 * added in MobileMedia-Cosmos-v7
	 * records part of a video after determining a name and an album
	 * @return true if it was successfully completed and false otherwise
	 */
	public boolean captureVideo(String videoName, String albumName);
	//#endif
	
}
