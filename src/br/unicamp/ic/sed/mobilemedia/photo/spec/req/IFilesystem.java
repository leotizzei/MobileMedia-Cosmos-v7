//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.spec.req;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{
	
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws MediaNotFoundException, PersistenceMechanismException; 
 
	/**
	 * Modified in MobileMedia-Cosmos-OO-v7
	 * Get metainformation about a media specified by the parameter mediaName and by the mediaType
	 * @throws ImageNotFoundException 
	 */
	public IMediaData getMediaInfo( String mediaName, String albumName, String mediaType ) throws MediaNotFoundException, NullAlbumDataReference;

		
	public void updateImageInfo( String mediaType, IMediaData velhoID , IMediaData novoID ) throws InvalidImageDataException, PersistenceMechanismException;
}
//#endif