// #ifdef Album
// [NC] Added in the scenario 07
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;

public class ImageAlbumData extends AlbumData {
	
	public ImageAlbumData() {
		mediaAccessor = new ImageMediaAccessor();
	}
	
	/**
	 *  Get a particular image (by name) from a photo album. The album name corresponds
	 *  to a record store.
	 * @throws ImageNotFoundException 
	 * @throws PersistenceMechanismException 
	 */
	public Image getImageFromRecordStore(String recordStore, String imageName) throws MediaNotFoundException, PersistenceMechanismException {
		
		System.out.println("ImageAlbumData:getImageFromRecordStore: recordStore="+recordStore+" imageName="+imageName);
		
		IMediaData imageInfo = null;
		imageInfo = mediaAccessor.getMediaInfoFromTable(imageName, recordStore);
	
		//Find the record ID and store name of the image to retrieve
		
		
		int imageId = imageInfo.getForeignRecordId();
				
		String album = imageInfo.getParentAlbumName();
		//Now, load the image (on demand) from RMS and cache it in the hashtable
		Image imageRec = ((ImageMediaAccessor) mediaAccessor).loadSingleImageFromRMS(album, imageId); //rs.getRecord(recordId);
		return imageRec;
	}
}
//#endif