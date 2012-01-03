package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov;

import java.io.InputStream;

import javax.microedition.lcdui.Image;



import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{

	// #ifdef includeCopyPhoto
	public void addMediaData(String mediaName, IMediaData mediaData, String albumName) throws InvalidImageDataException, PersistenceMechanismException;
	// #endif 
	
	
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname, String fileExtension) throws InvalidImageDataException, PersistenceMechanismException ;
	//#endif 
	
	public void addNewMediaToAlbum ( String mediaType, String mediaName,String fileExtension, String mediaPath, String albumName ) throws InvalidImageDataException, PersistenceMechanismException, MediaNotFoundException; 
	
	public void createNewMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException, InvalidPhotoAlbumNameException; 
	
	public void deleteMedia ( String mediaType, String mediaName, String albumName ) throws PersistenceMechanismException, MediaNotFoundException; 
	
	public void deleteMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException; 
	
	/**
	 * Return the list of albums of a specified media type
	 * @param mediaType
	 * @return
	 * @throws MediaNotFoundException 
	 */
	public String[] getAlbumNames (String mediaType  ) throws MediaNotFoundException; 
	
	//#ifdef Album
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws PersistenceMechanismException, MediaNotFoundException; 
	
	/**
	 * Added in MobileMedia-Cosmos-OO-v5
	 * @return an array of bytes related to the image specified by the parameters
	 */
	public byte[] loadImageBytesFromRMS(String recordName, String imageName, int recordId) throws PersistenceMechanismException;
	//#endif
	
	/**
	 * Modified in MobileMedia-Cosmos-OO-v7
	 * Get metainformation about a media specified by the parameter mediaName and by the mediaType.
	 * This metainformation is retrieved from a hashtable
	 * @throws ImageNotFoundException 
	 */
	public IMediaData getMediaInfo( String mediaName, String albumName, String mediaType ) throws MediaNotFoundException, NullAlbumDataReference;
	
	/**
	 * Return metadata about all the medias in a specified album. 
	 * @param albumName the name of the album where are the medias whose metadata are returned 
	 * @param mediaType the type of the media (e.g. video, music, photo)
	 * @return
	 * @throws UnavailablePhotoAlbumException
	 */
	public IMediaData[] getMedias( String albumName, String mediaType )throws UnavailablePhotoAlbumException; 
	
	/**
	 * Reset all modifications that were performed since the last reset. For instance, number of views,
	 * favourites medias, edited labels, etc.
	 * 
	 * @param mediaType the type of the media whose metainformation is reseted
	 * @throws PersistenceMechanismException 
	 * @throws MediaNotFoundException
	 */
	public void resetMediaData ( String mediaType ) throws PersistenceMechanismException, MediaNotFoundException;
	
	
	public void updateMediaInfo(IMediaData velhoID, IMediaData novoID) throws InvalidImageDataException, PersistenceMechanismException ;
	
	
	//#ifdef includeMusic
	public InputStream getMusicFromRecordStore(String recordName , String musicName ) throws MediaNotFoundException, PersistenceMechanismException;
	
	/**
	 * Return the type of music specified by the its name (e.g. wav, mp3)
	 * @param musicName the name of the audio whose type is required
	 * @return
	 * @throws MediaNotFoundException
	 */
	public String getMusicType( String musicName , String albumName )throws MediaNotFoundException;
	
	public void setMusicType( String musicName , String albumName, String type ) throws MediaNotFoundException, InvalidImageDataException, PersistenceMechanismException, NullAlbumDataReference;
	//#endif
	
	//#ifdef includeVideo
	/** 
	 * Added in MobileMedia-Cosmos-OO-v7
	 * 
	 * @param albumName the name of the album where the video is.
	 * @param videoName the name of the media whose stream will be returned.
	 * @throws PersistenceMechanismException 
	 * @throws MediaNotFoundException 
	 */
	public InputStream getVideoStreamFromRecordStore(String albumName , String videoName ) throws MediaNotFoundException, PersistenceMechanismException;
	//#endif
}