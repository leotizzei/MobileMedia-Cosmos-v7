//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.media_video.impl;

import br.unicamp.ic.sed.mobilemedia.media.spec.req.IVideo;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.video.spec.excep.PersistenceMechanismException;

class AdapterVideoMedia implements IVideo {

	private IManager manager;
	
	public AdapterVideoMedia(IManager mgr) {
		manager = mgr;
	}
	
	public boolean playVideo(String albumName, String videoName) throws br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException, br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException, br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference {
		br.unicamp.ic.sed.mobilemedia.video.spec.prov.IVideo video = (br.unicamp.ic.sed.mobilemedia.video.spec.prov.IVideo) manager.getRequiredInterface("IVideo");
		try {
			return video.playVideo(albumName, videoName);
		} catch (MediaNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.MediaNotFoundException(e.getMessage());
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException();
		} catch (NullAlbumDataReference e) {
			throw new br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference(e);
		}
	}


	public boolean captureVideo(String videoName, String albumName) {
		br.unicamp.ic.sed.mobilemedia.video.spec.prov.IVideo video = (br.unicamp.ic.sed.mobilemedia.video.spec.prov.IVideo) manager.getRequiredInterface("IVideo");
		return video.captureVideo(videoName, albumName);
	}

}
//#endif