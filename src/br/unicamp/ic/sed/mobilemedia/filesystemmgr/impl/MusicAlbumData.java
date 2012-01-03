// #ifdef includeMusic
// [NC] Added in the scenario 07
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MultiMediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;

class MusicAlbumData extends AlbumData {
	
	public MusicAlbumData() {
		mediaAccessor = new MusicMediaAccessor();
	}
	
	protected InputStream getMusicFromRecordStore(String recordStore, String musicName) throws MediaNotFoundException, PersistenceMechanismException {

		IMediaData mediaInfo = null;
		mediaInfo = mediaAccessor.getMediaInfoFromTable(musicName, recordStore);
		//Find the record ID and store name of the image to retrieve
		int mediaId = mediaInfo.getForeignRecordId();
		String album = mediaInfo.getParentAlbumName();
		//Now, load the image (on demand) from RMS and cache it in the hashtable
		byte[] musicData = (mediaAccessor).loadMediaBytesFromRMS(album, mediaId);
		return new ByteArrayInputStream(musicData);

	}
	
	protected String getMusicType(String musicName, String albumName) throws MediaNotFoundException {
				
		IMediaData music = this.getMediaInfo( musicName, albumName);
		return music.getFileExtension();
	}
	
	protected void setMusicType( String musicName , String albumName, String type ) throws MediaNotFoundException{
		MultiMediaData music = (MultiMediaData) this.getMediaInfo(musicName, albumName);
		music.setFileExtension(type);
		
	}
}
//#endif
