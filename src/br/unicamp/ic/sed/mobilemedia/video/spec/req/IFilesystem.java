package br.unicamp.ic.sed.mobilemedia.video.spec.req;

import java.io.InputStream;


import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.*;



/* In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{


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
	
	/**
	 * Modified in MobileMedia-Cosmos-OO-v7
	 * Get metainformation about a media specified by the parameter mediaName and by the mediaType
	 * @throws ImageNotFoundException 
	 */
	public IMediaData getMediaInfo( String mediaName, String albumName, String mediaType ) throws MediaNotFoundException, NullAlbumDataReference;

}