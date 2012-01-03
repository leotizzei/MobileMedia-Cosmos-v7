//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.spec.req;

import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{
 
	/**
	 * Modified in MobileMedia-Cosmos-OO-v7
	 * Get metainformation about a media specified by the parameter mediaName and by the mediaType
	 * @throws ImageNotFoundException 
	 */
	public IMediaData getMediaInfo( String mediaName, String albumName, String mediaType ) throws MediaNotFoundException, NullAlbumDataReference;

		
	public void updateImageInfo( IMediaData velhoID , IMediaData novoID ) throws InvalidImageDataException, PersistenceMechanismException;

	/**add v6 OO cosmos**/
	//#ifdef includeMusic
	public InputStream getMusicFromRecordStore(String recordName , String musicName ) throws MediaNotFoundException, PersistenceMechanismException;
	public String getMusicType( String musicName , String albumName)throws MediaNotFoundException;
	//#endif
}
//#endif