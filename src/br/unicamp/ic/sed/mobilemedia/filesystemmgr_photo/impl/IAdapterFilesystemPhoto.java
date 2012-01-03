//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.filesystemmgr_photo.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IFilesystem;


public class IAdapterFilesystemPhoto implements IFilesystem {

	
	public Image getImageFromRecordStore(String albumName, String imageName) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.MediaNotFoundException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException  {
		IManager manager = ComponentFactory.createInstance();
		System.out.println("IAdapterFilesystemPhoto:manager="+manager);
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		System.out.println("IAdapterFilesystemPhoto:filesystem="+filesystem);
		try {
			return filesystem.getImageFromRecordStore(albumName, imageName);
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.MediaNotFoundException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

	public IMediaData getMediaInfo(String imageName, String albumName, String mediaType) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.MediaNotFoundException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getMediaInfo(imageName, albumName, mediaType);
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.MediaNotFoundException( e.getMessage() );
		} catch (NullAlbumDataReference e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference(e);
		} 
	}

	public void updateImageInfo(String mediaType,IMediaData velhoID, IMediaData novoID) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.updateMediaInfo( velhoID, novoID);
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

}
//#endif