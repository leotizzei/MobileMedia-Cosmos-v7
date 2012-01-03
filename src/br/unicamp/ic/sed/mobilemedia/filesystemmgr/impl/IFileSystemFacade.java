/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group (http://www.sed.ic.unicamp.br)
 *
 * date: February 2009
 * 
 */   
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.InputStream;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MultiMediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;

class IFilesystemFacade implements IFilesystem{



	//#ifdef Album
	private ImageAlbumData imageAlbumData = new ImageAlbumData();
	//#endif

	//#ifdef includeMusic
	private MusicAlbumData musicAlbumData = new MusicAlbumData();
	//#endif

	//#ifdef includeVideo
	private VideoAlbumData videoAlbumData = new VideoAlbumData();
	//#endif

	// #ifdef includeCopyPhoto
	public void addMediaData(String mediaName, IMediaData mediaData,
			String albumName) throws InvalidImageDataException, PersistenceMechanismException {


		String mediaType = mediaData.getMediaType();
		mediaData.setMediaLabel( mediaName );

		if(mediaType == null){
			System.out.println("[IFilesystemFacade] Error: It was not possible to determine the media type");
			return;
		}

		//#ifdef Album 
		if(mediaType.equals(Constants.IMAGE_MEDIA))
			this.imageAlbumData.addMediaData( mediaData, albumName);
		//#endif

		//#ifdef includeMusic 
		if(mediaType.equals(Constants.MUSIC_MEDIA))
			this.musicAlbumData.addMediaData( mediaData, albumName);
		//#endif


		//#ifdef includeVideo 
		if(mediaType.equals(Constants.VIDEO_MEDIA))
			this.videoAlbumData.addMediaData( mediaData, albumName);
		//#endif
	} 
	// #endif 

	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname, String fileExtension) throws InvalidImageDataException, PersistenceMechanismException {
		this.imageAlbumData.addImageData(photoname, img, albumname, fileExtension);
	}
	//#endif


	public void addNewMediaToAlbum ( String mediaType, String imageName, String imagePath, String albumName, String fileExtension) throws InvalidImageDataException, PersistenceMechanismException, MediaNotFoundException{	

		if( mediaType == null ){
			System.out.println("[IFilesystemFacade] ERROR: mediaType is null");
			return;
		}

		System.out.println("[IFilesystemFacade] -> mediaType: " + mediaType + " mediaName: "+ imageName +" imagePath: " + imagePath+ " albumName: " +albumName );

		//#ifdef Album 
		if( mediaType.equals( Constants.IMAGE_MEDIA ) )
			this.imageAlbumData.addNewMediaToAlbum(imageName, imagePath, albumName, mediaType, fileExtension);
		//#endif

		//#ifdef includeMusic
		if( mediaType.equals( Constants.MUSIC_MEDIA))
			this.musicAlbumData.addNewMediaToAlbum(imageName, imagePath, albumName, mediaType, fileExtension);
		//#endif

		//#ifdef includeVideo
		if( mediaType.equals( Constants.VIDEO_MEDIA) )
			this.videoAlbumData.addNewMediaToAlbum(imageName, imagePath, albumName, mediaType, fileExtension);
		//#endif


	} 

	public void createNewMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException, InvalidPhotoAlbumNameException{

		if( mediaType == null ){
			System.out.println("[IFilesystemFacade] ERROR: mediaType is null");
			return;
		}

		//#ifdef Album 
		if( mediaType.equals( Constants.IMAGE_MEDIA ) )
			this.imageAlbumData.createNewAlbum(albumName);
		//#endif

		//#ifdef includeMusic 
		if( mediaType.equals( Constants.MUSIC_MEDIA ) )
			this.musicAlbumData.createNewAlbum(albumName);
		//#endif


		//#ifdef includeVideo 
		if( mediaType.equals( Constants.VIDEO_MEDIA ) )
			this.videoAlbumData.createNewAlbum(albumName);
		//#endif
	} 

	public void deleteMedia ( String mediaType, String imageName, String albumName ) throws PersistenceMechanismException, MediaNotFoundException{

		if( mediaType == null ){
			System.out.println("[IFilesystemFacade] ERROR: mediaType is null");
			return;
		}

		//#ifdef Album 
		if( mediaType.equals( Constants.IMAGE_MEDIA ) )
			this.imageAlbumData.deleteMedia(imageName, albumName);
		//#endif

		//#ifdef includeMusic 
		if( mediaType.equals( Constants.MUSIC_MEDIA) )
			this.musicAlbumData.deleteMedia(imageName, albumName);
		//#endif

		//#ifdef includeVideo 
		if( mediaType.equals( Constants.VIDEO_MEDIA) )
			this.videoAlbumData.deleteMedia(imageName, albumName);
		//#endif

	} 

	public void deleteMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException{

		if( mediaType == null ){
			System.out.println("[IFilesystemFacade] ERROR: mediaType is null");
			return ;
		}

		//#ifdef Album 
		if( mediaType.equals( Constants.IMAGE_MEDIA ) )
			this.imageAlbumData.deleteAlbum(albumName);
		//#endif

		//#ifdef includeMusic 
		if( mediaType.equals( Constants.MUSIC_MEDIA) )
			this.musicAlbumData.deleteAlbum(albumName);
		//#endif

		//#ifdef includeVideo 
		if( mediaType.equals( Constants.VIDEO_MEDIA) )
			this.videoAlbumData.deleteAlbum(albumName);
		//#endif

	} 

	public String[] getAlbumNames ( String mediaType  ) throws MediaNotFoundException{
		if( mediaType == null ){
			System.out.println("[IFilesystemFacade] ERROR: mediaType is null");
			return null;
		} else{

			//#ifdef Album 
			if( mediaType.equals( Constants.IMAGE_MEDIA ) )
				return this.imageAlbumData.getAlbumNames(); 
			//#endif

			//#ifdef includeMusic 
			if( mediaType.equals( Constants.MUSIC_MEDIA) )
				return this.musicAlbumData.getAlbumNames();
			//#endif

			//#ifdef includeVideo 
			if( mediaType.equals( Constants.VIDEO_MEDIA) ){
				System.out.println("[IFilesystemFacade:getAlbumNames] tipo video");
				String[] albumNames = this.videoAlbumData.getAlbumNames();
				if( (albumNames == null) || (albumNames.length <=0 ) ){
					System.out.println("[IFilesystemFacade:getAlbumNames] no video album!!!!");
				}
				for(int i=0;i<albumNames.length;i++){
					System.out.println("albumNames["+i+"]="+albumNames[i]);
				}
				
				return  albumNames;
			}
			//#endif

			System.out.println("[IFilesystemFacade] ERROR: no album was returned");
			return null;
		}

	} 

	//#ifdef Album
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws MediaNotFoundException, PersistenceMechanismException{
		return imageAlbumData.getImageFromRecordStore(albumName, imageName);
	} 
	//#endif

	public IMediaData getMediaInfo(String mediaName, String albumName, String mediaType) throws NullAlbumDataReference, MediaNotFoundException {
		if( mediaType == null ){
			System.out.println("[IFilesystemFacade] ERROR: mediaType is null");
			return null;
		}

		//#ifdef Album 
		if( mediaType.equals( Constants.IMAGE_MEDIA ) )
			return this.imageAlbumData.getMediaInfo(mediaName, albumName);
		//#endif

		//#ifdef includeMusic 
		if( mediaType.equals( Constants.MUSIC_MEDIA) )
			return this.musicAlbumData.getMediaInfo(mediaName, albumName);
		//#endif

		//#ifdef includeVideo 
		if( mediaType.equals( Constants.VIDEO_MEDIA) )
			return this.videoAlbumData.getMediaInfo(mediaName, albumName);
		//#endif

		System.out.println("Either the media type "+mediaType+" is not supported or the media "+mediaName+" was not found");
		return null;

	}


	public IMediaData[] getMedias ( String albumName, String mediaType ) throws UnavailablePhotoAlbumException{

		//#ifdef Album 
		if( mediaType.equals( Constants.IMAGE_MEDIA ) )
			return this.imageAlbumData.getMedias(albumName);
		//#endif

		//#ifdef includeMusic 
		if( mediaType.equals( Constants.MUSIC_MEDIA) )
			return this.musicAlbumData.getMedias(albumName);
		//#endif

		//#ifdef includeVideo 
		if( mediaType.equals( Constants.VIDEO_MEDIA) )
			return this.videoAlbumData.getMedias(albumName);
		//#endif

		System.out.println("Either the media type "+mediaType+" is not supported or the album "+albumName+" was not found");
		return null;

	}


	public void resetMediaData ( String mediaType ) throws PersistenceMechanismException, MediaNotFoundException{
		System.out.println("[IFilesystemFacade:resetMediaData] mediaType="+mediaType);
		
		//#ifdef Album 
		if( mediaType.equals( Constants.IMAGE_MEDIA ) )
			this.imageAlbumData.resetMediaData();
		//#endif

		//#ifdef includeMusic 
		if( mediaType.equals( Constants.MUSIC_MEDIA) )
			this.musicAlbumData.resetMediaData();
		//#endif

		//#ifdef includeVideo 
		if( mediaType.equals( Constants.VIDEO_MEDIA) )
			this.videoAlbumData.resetMediaData();
		//#endif

	}

	public void updateMediaInfo(IMediaData velhoID, IMediaData novoID) throws InvalidImageDataException, PersistenceMechanismException {

		if( novoID != null){
			
			
			String mediaType = novoID.getMediaType();
			System.out.println("[IFilesystemFacade:updateMediaInfo] mediaType="+mediaType);
			if( mediaType != null){

				//#ifdef Album 
				if( mediaType.equals( Constants.IMAGE_MEDIA ) )
					this.imageAlbumData.updateMediaInfo(velhoID, novoID);
				//#endif

				//#ifdef includeMusic 
				if( mediaType.equals( Constants.MUSIC_MEDIA) )
					this.musicAlbumData.updateMediaInfo(velhoID, novoID);
				//#endif

				//#ifdef includeVideo 
				if( mediaType.equals( Constants.VIDEO_MEDIA) ){
					this.videoAlbumData.updateMediaInfo(velhoID, novoID);
				}
				//#endif
			}
		}
		else
			System.out.println("[IFilesystemFacade] ERROR: It was not possible to update media info!");
		return;
	}


	/**
	 * Added in MobileMedia-Cosmos-OO-v5
	 * @return an array of bytes related to the image specified by the parameters
	 */
	//#ifdef Album
	public byte[] loadImageBytesFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {

		return this.imageAlbumData.loadMediaBytesFromRMS(recordName, recordId);
	}
	//#endif

	//#ifdef includeMusic
	public InputStream getMusicFromRecordStore(String recordName , String musicName ) throws MediaNotFoundException, PersistenceMechanismException{
		return this.musicAlbumData.getMusicFromRecordStore(recordName, musicName);
	}

	public String getMusicType(String musicName, String albumName) throws MediaNotFoundException {
		return this.musicAlbumData.getMusicType( musicName , albumName);
	}

	public void setMusicType( String musicName , String albumName, String type ) throws MediaNotFoundException, InvalidImageDataException, PersistenceMechanismException, NullAlbumDataReference{

		MediaData media = (MediaData) musicAlbumData.getMediaInfo(musicName, albumName);
		MultiMediaData musicMedia = new MultiMediaData( media , type );
		musicAlbumData.updateMediaInfo(media, musicMedia);

		this.musicAlbumData.setMusicType(musicName, albumName, type);
	}
	//#endif

	//#ifdef includeVideo
	public InputStream getVideoStreamFromRecordStore(String albumName,
			String videoName) throws MediaNotFoundException,
			PersistenceMechanismException {
		return videoAlbumData.getVideoFromRecordStore(albumName, videoName);

	}
	//#endif



}