// #ifdef includeMusic
// [NC] Added in the scenario 07
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MultiMediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidArrayFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;

public class MusicMediaAccessor extends MediaAccessor {
 
	private MediaUtil converter = new MediaUtil();
	
	public MusicMediaAccessor() {
		super("mmp-","mmpi-","My Music Album");
	}
	
	protected  byte[] getMediaArrayOfByte(String path)	throws ImagePathNotValidException, InvalidImageFormatException {
		byte[] data1 = converter.readMediaAsByteArray(path);
		return data1;
	}
	
	protected byte[] getByteFromMediaInfo(IMediaData ii) throws InvalidImageDataException {
			return converter.getBytesFromMediaInfo(ii);
	}
	
	protected IMediaData getMediaFromBytes(byte[] data) throws InvalidArrayFormatException {
		IMediaData iiObject = converter.getMediaInfoFromBytes(data);
		return iiObject;
	}

	public void resetRecordStore() throws InvalidImageDataException, PersistenceMechanismException, MediaNotFoundException {
		
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

					System.out.println("<* MusicMediaAccessor.resetImageRecordStore() *> delete "+storeName);
					
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
					.println("ImageAccessor::resetImageRecordStore: albumNames array was null. Nothing to delete.");
		}

		// Now, create a new default album for testing
		IMediaData media = null;
		MultiMediaData mmedi = null;

		
		addMediaData("Applause", "/images/applause.wav",Constants.MUSIC_MEDIA, Constants.X_WAV, default_album_name);
		addMediaData("Baby", "/images/baby.wav",Constants.MUSIC_MEDIA, Constants.X_WAV, default_album_name);
		addMediaData("Bong", "/images/bong.wav",Constants.MUSIC_MEDIA, Constants.X_WAV, default_album_name);
		addMediaData("Frogs", "/images/frogs.mp3",Constants.MUSIC_MEDIA, Constants.MP3, default_album_name);
		addMediaData("Jump", "/images/jump.wav",Constants.MUSIC_MEDIA, Constants.X_WAV, default_album_name);
		addMediaData("Printer", "/images/printer.wav",Constants.MUSIC_MEDIA, Constants.X_WAV, default_album_name);
		addMediaData("Tango", "/images/cabeza.mid",Constants.MUSIC_MEDIA, Constants.MID, default_album_name);
		
		loadMediaDataFromRMS(default_album_name);

		try {
			media = this.getMediaInfoFromTable("Applause",default_album_name);
			mmedi = new MultiMediaData(media, Constants.MUSIC_MEDIA, Constants.X_WAV);
			this.updateMediaInfo(media, mmedi);

			media = this.getMediaInfoFromTable("Baby",default_album_name);
			mmedi = new MultiMediaData(media, Constants.MUSIC_MEDIA, Constants.X_WAV);
			this.updateMediaInfo(media, mmedi);

			media = this.getMediaInfoFromTable("Bong",default_album_name);
			mmedi = new MultiMediaData(media, Constants.MUSIC_MEDIA, Constants.X_WAV);
			this.updateMediaInfo(media, mmedi);

			media = this.getMediaInfoFromTable("Frogs",default_album_name);
			mmedi = new MultiMediaData(media, Constants.MUSIC_MEDIA, Constants.MP3	);
			this.updateMediaInfo(media, mmedi);

			media = this.getMediaInfoFromTable("Jump",default_album_name);
			mmedi = new MultiMediaData(media, Constants.MUSIC_MEDIA, Constants.X_WAV);
			this.updateMediaInfo(media, mmedi);

			media = this.getMediaInfoFromTable("Printer",default_album_name);
			mmedi = new MultiMediaData(media, Constants.MUSIC_MEDIA, Constants.X_WAV);
			this.updateMediaInfo(media, mmedi);
			
			media = this.getMediaInfoFromTable("Tango",default_album_name);
			mmedi = new MultiMediaData(media, Constants.MUSIC_MEDIA, Constants.MID);
			this.updateMediaInfo(media, mmedi);
		} catch (MediaNotFoundException e) {
			e.printStackTrace();
		}

	}

}
//#endif
