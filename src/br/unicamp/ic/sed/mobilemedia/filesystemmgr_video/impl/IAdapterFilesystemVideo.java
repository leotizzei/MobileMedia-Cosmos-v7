//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.filesystemmgr_video.impl;

import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;
import br.unicamp.ic.sed.mobilemedia.video.spec.req.IFilesystem;



class IAdapterFilesystemVideo implements IFilesystem {




	public InputStream getVideoStreamFromRecordStore(String albumName,
			String videoName)
	throws br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException,
	br.unicamp.ic.sed.mobilemedia.video.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getVideoStreamFromRecordStore(albumName, videoName);
		} catch (MediaNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException(e.getMessage());
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.video.spec.excep.PersistenceMechanismException(e);
		}

	}


	public IMediaData getMediaInfo(String mediaName, String albumName, String mediaType) throws br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException, br.unicamp.ic.sed.mobilemedia.video.spec.excep.NullAlbumDataReference{
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getMediaInfo(mediaName, albumName, mediaType);
		} catch (MediaNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException(e.getMessage());
		} catch (NullAlbumDataReference e) {
			throw new br.unicamp.ic.sed.mobilemedia.video.spec.excep.NullAlbumDataReference(e);
		} 

	}

}
//#endif