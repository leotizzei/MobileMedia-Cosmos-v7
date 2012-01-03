//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.video.spec.prov;

import br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.PersistenceMechanismException;

/**
 * Added in MobileMedia-Cosmos-v7
 * 
 *
 */
public interface IVideo {

	/**
	 * 
	 * @param albumName
	 * @param videoName
	 * @return
	 * @throws MediaNotFoundException
	 * @throws PersistenceMechanismException
	 * @throws NullAlbumDataReference
	 */
	public boolean playVideo(String albumName , String videoName) throws MediaNotFoundException, PersistenceMechanismException, NullAlbumDataReference;
	
	//#ifdef captureVideo
	/**
	 * records part of a video after determining a name and an album
	 * @return true if it was successfully completed and false otherwise
	 */
	public boolean captureVideo(String videoName, String albumName);
	//#endif
}
//#endif