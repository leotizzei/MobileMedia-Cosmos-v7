//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.filesystemmgr_music.impl;

import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.music.spec.req.IFilesystem;
 

public class IAdapterFilesystemMusic implements IFilesystem {

	
	public IMediaData getMediaInfo(String imageName, String albumName, String mediaType ) throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException, br.unicamp.ic.sed.mobilemedia.music.spec.excep.NullAlbumDataReference {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getMediaInfo(imageName, albumName, mediaType );
		} catch (MediaNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException( e.getMessage() );
		} catch (NullAlbumDataReference e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.NullAlbumDataReference(e.getMessage() );
		}
	}

	public void updateImageInfo(IMediaData velhoID, IMediaData novoID) throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.InvalidImageDataException, br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.updateMediaInfo( velhoID, novoID);
		} catch (InvalidImageDataException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.InvalidImageDataException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}


	public InputStream getMusicFromRecordStore(String recordName,String musicName)
			throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException, br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException{
		
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getMusicFromRecordStore(recordName, musicName);
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException( e.getMessage() );
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException(e.getMessage());
			
		}
	}


	public String getMusicType(String musicName, String albumName)
			throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
	
		try {
			return filesystem.getMusicType( musicName, albumName );
		} catch (MediaNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.MediaNotFoundException( e.getMessage() );
		}
	}

	

}
//#endif