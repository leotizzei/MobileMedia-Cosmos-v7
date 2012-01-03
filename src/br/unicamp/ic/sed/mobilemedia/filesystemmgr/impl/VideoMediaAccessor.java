// #ifdef includeVideo
// [NC] Added in the scenario 08
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidArrayFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;



class VideoMediaAccessor  extends MediaAccessor{
	
	private MediaUtil mediaUtil;
	
	public VideoMediaAccessor() {
		super("vvp-","vvpi-","My Video Album");
		mediaUtil = new MediaUtil(); 
	}
	
	protected void resetRecordStore() throws InvalidImageDataException, PersistenceMechanismException {
		removeRecords();
		
		// Now, create a new default album for testing
		//	addVideoData("Fish", default_album_name, this.getClass().getResourceAsStream(name))
		
		IMediaData media = null;
		IMediaData mmedi = null;
		InputStream is = (InputStream) this.getClass().getResourceAsStream("/images/fish.mpg");
		byte[] video = null;
		try {
			video = inputStreamToBytes(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//TODO permitir outros tipos de extensoes de arquivos
		addVideoData("Fish", default_album_name, Constants.MPEG, video);
		loadMediaDataFromRMS(default_album_name);

		try {
			System.out.println("[VideoMediaAccessor:resetRecordStore] default_album_name="+default_album_name);
			media = this.getMediaInfoFromTable("Fish",default_album_name);
			mmedi = new MediaData(media.getForeignRecordId(), media.getParentAlbumName(), media.getMediaLabel());
			mmedi.setRecordId(media.getRecordId());
			mmedi.setMediaType(Constants.VIDEO_MEDIA);
			mmedi.setFileExtension(Constants.MPEG);
			
			//debug
			printMediaData(media);
			printMediaData(mmedi);
			
			this.updateMediaInfo(media, mmedi);
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected void addVideoData(String videoname, String albumname, String fileExtension, byte[] video)
		throws InvalidImageDataException, PersistenceMechanismException {
		try {
			addMediaArrayOfBytes(videoname, albumname, Constants.VIDEO_MEDIA, fileExtension, video);
		} catch (RecordStoreException e) {
			throw new PersistenceMechanismException();
		}
	}
	
	protected byte[] inputStreamToBytes(InputStream inputStream) throws IOException {
		return this.readBytes(inputStream);
	}
	
	private byte[] readBytes( InputStream in ){
		try {	
			String b = in.toString();
			byte[] bytes = new byte[ in.available() ];
		
			in.read( bytes , 0 , in.available() );
			System.out.println("Size: " + b.length() );
			
		return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	protected byte[] getByteFromMediaInfo(IMediaData ii)
			throws InvalidImageDataException {
		
		return this.mediaUtil.getBytesFromMediaInfo(ii);
	}

	
	protected byte[] getMediaArrayOfByte(String path)
			throws ImagePathNotValidException, InvalidImageFormatException {
		return this.mediaUtil.readMediaAsByteArray(path);
	}

	
	protected IMediaData getMediaFromBytes(byte[] data)
			throws InvalidArrayFormatException {
		return  this.mediaUtil.getMediaInfoFromBytes(data);
		
	}
	
	/**
	 * 
	 */
	private void removeRecords() {
		String storeName = null;
		String infoStoreName = null;

		// remove any existing album stores...
		if (albumNames != null) {
			for (int i = 0; i < albumNames.length; i++) {
				try {
					// Delete all existing stores containing Image objects as
					// well as the associated ImageInfo objects
					// Add the prefixes labels to the info store

					storeName = album_label + albumNames[i];
					infoStoreName = info_label + albumNames[i];

					System.out.println("<* ImageAccessor.resetVideoRecordStore() *> delete "+storeName);
					
					RecordStore.deleteRecordStore(storeName);
					RecordStore.deleteRecordStore(infoStoreName);

				} catch (RecordStoreException e) {
					System.out.println("No record store named " + storeName
							+ " to delete.");
					System.out.println("...or...No record store named "
							+ infoStoreName + " to delete.");
					System.out.println("Ignoring Exception: " + e);
					// ignore any errors...
				}
			}
		} else {
			// Do nothing for now
			System.out
					.println("ImageAccessor::resetVideoRecordStore: albumNames array was null. Nothing to delete.");
		}
	}
	
	//debug
	private void printMediaData(IMediaData media){
		if(media == null)
			System.out.println("media is null");
		else{
			System.out.println("recordID="+media.getRecordId());
			System.out.println("foreignID="+media.getForeignRecordId());
			System.out.println("mediaName="+media.getMediaLabel());
			System.out.println("mediaType"+media.getMediaType());
			System.out.println("fileExtension"+media.getFileExtension());
			
		}
	}
	
}
//#endif