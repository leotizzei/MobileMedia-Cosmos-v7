package br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl;

import br.unicamp.ic.sed.mobilemedia.album.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;


public class IAdapterFilesystemAlbum implements IFilesystem {

	
	public void createNewMediaAlbum(String mediaType , String albumName) throws br.unicamp.ic.sed.mobilemedia.album.spec.excep.InvalidPhotoAlbumNameException, br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try{
		filesystem.createNewMediaAlbum(mediaType, albumName);
		}catch(InvalidPhotoAlbumNameException e){
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.InvalidPhotoAlbumNameException( e.getMessage() );
		}catch(PersistenceMechanismException e){
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}
	
	public void deleteMediaAlbum(String mediaType , String albumName) throws br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException
	 {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.deleteMediaAlbum( mediaType , albumName);
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

	public String[] getAlbumNames( String mediaType ) throws br.unicamp.ic.sed.mobilemedia.album.spec.excep.MediaNotFoundException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getAlbumNames( mediaType );
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.MediaNotFoundException(e.getMessage());
		}
	}

	public void resetMediaData( String mediaType ) throws br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException, br.unicamp.ic.sed.mobilemedia.album.spec.excep.MediaNotFoundException  {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.resetMediaData( mediaType );
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException( e.getMessage() );
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.MediaNotFoundException(e.getMessage());
		}
	}
}
