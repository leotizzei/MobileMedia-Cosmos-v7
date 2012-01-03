package br.unicamp.ic.sed.mobilemedia.media.spec.req;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;


/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{

	//#ifdef includeCopyPhoto
	public void addMediaData(String photoName, IMediaData mediaData, String albumName) throws InvalidImageDataException, PersistenceMechanismException;
	//#endif
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname, String fileExtension) throws InvalidImageDataException, PersistenceMechanismException;
	//#endif
	
	/**
	 * Modified in MobileMedia-Cosmos-OO-v7
	 * Get metainformation about a media specified by the parameter mediaName and by the mediaType
	 * @throws ImageNotFoundException 
	 * @throws ImageNotFoundException 
	 */
	public IMediaData getMediaInfo( String mediaName, String albumName, String mediaType ) throws MediaNotFoundException, NullAlbumDataReference, ImageNotFoundException;

	public void addNewMediaToAlbum ( String mediaType , String mediaName, String fileExtension, String imagePath, String albumName ) throws InvalidImageDataException, PersistenceMechanismException, MediaNotFoundException;
	public void updateMediaInfo( IMediaData oldID , IMediaData newID ) throws InvalidImageDataException, PersistenceMechanismException;
	public void deleteMedia ( String mediaType , String imageName, String albumName ) throws PersistenceMechanismException, MediaNotFoundException;
	public IMediaData[] getMedias( String albumName , String mediaType )throws UnavailablePhotoAlbumException;

	//#ifdef includeMusic
	public void setMusicType( String musicName , String albumName, String type ) throws MediaNotFoundException;
	//#endif
}