// #ifdef includeVideo
// [NC] Added in the scenario 08
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;


class VideoAlbumData extends AlbumData{
	
	public VideoAlbumData() {
		mediaAccessor = new VideoMediaAccessor();
	}
	
	public InputStream getVideoFromRecordStore(String recordStore, String musicName) throws PersistenceMechanismException, MediaNotFoundException {
		IMediaData mediaInfo = null;
		mediaInfo = mediaAccessor.getMediaInfoFromTable(musicName, recordStore);
		//Find the record ID and store name of the image to retrieve
		int mediaId = mediaInfo.getForeignRecordId();
		String album = mediaInfo.getParentAlbumName();
		//Now, load the image (on demand) from RMS and cache it in the hashtable
		System.out.println("VIDEO-FILE-SYSTEM:---- AlbumName: " + album +" : " + musicName);
		byte[] musicData = (mediaAccessor).loadMediaBytesFromRMS(album, mediaId);
		return new ByteArrayInputStream(musicData);

	}
}
//#endif