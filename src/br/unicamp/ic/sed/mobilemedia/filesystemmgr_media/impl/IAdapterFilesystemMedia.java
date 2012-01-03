package br.unicamp.ic.sed.mobilemedia.filesystemmgr_media.impl;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl.ComponentFactory;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl.IManager;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IFilesystem;


class IAdapterFilesystemMedia implements IFilesystem {

	public void addNewMediaToAlbum(String mediaType , String mediaName, String fileExtension, String imagePath,
			String albumName) throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException, br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException, br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		
		try{
			filesystem.addNewMediaToAlbum(mediaType, mediaName, fileExtension, imagePath, albumName);
		}catch(InvalidImageDataException e){
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException(e.getMessage());
		}catch(PersistenceMechanismException e){
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException(e.getMessage());
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException(e.getMessage());
		}
	}

	public void deleteMedia(String mediaType , String imageName, String albumName) throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException, br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		
		try {
			filesystem.deleteMedia( mediaType , imageName, albumName);
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException(e.getMessage());
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException(e.getMessage());
		}
		
	}

	public IMediaData getMediaInfo(String imageName , String albumName, String mediaType ) throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException, br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference, br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getMediaInfo(imageName, albumName, mediaType );
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException( e.getMessage() );	
		} catch (NullAlbumDataReference e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference(e);
		} 
	}

	public IMediaData[] getMedias(String albumName , String mediaType ) throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException{
		System.out.println("IAdapterFilesystemMedia:getMedias("+albumName+" , "+mediaType+" )");
		
		IManager manager = ComponentFactory.createInstance();
		
		
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		
		try {
			return filesystem.getMedias(albumName, mediaType );
		} catch (UnavailablePhotoAlbumException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException(e.getMessage());
		}
		
	}

	public void updateMediaInfo(IMediaData velhoID, IMediaData novoID) throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException, br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.updateMediaInfo(velhoID, novoID);
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException(e.getMessage());
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException(e.getMessage());
		}
	}

	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname, String fileExtension)
			throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException,
			br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.addImageData(photoname, img, albumname, fileExtension);
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException( e.getMessage() );
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException( e.getMessage() );
		}
			
	}
	//#endif

	//#ifdef includeCopyPhoto
	public void addMediaData(String photoName, IMediaData mediaData,
			String albumName)
			throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException,
			br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.addMediaData(photoName, mediaData , albumName);
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException( e.getMessage() );
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException( e.getMessage() );
		}
	}
	//#endif
	
	//#ifdef includeMusic
	public void setMusicType( String musicName , String albumName, String type ) 
		throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException{
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try{
		filesystem.setMusicType(musicName, albumName, type);
		}catch (MediaNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException( e.getMessage() );
		} catch (InvalidImageDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistenceMechanismException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullAlbumDataReference e) {
			
			e.printStackTrace();
		}
	}
	//#endif

	
	
}
