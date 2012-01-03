/*
 * Created on Sep 13, 2004
 *
 */
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidArrayFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;

/**
 * @author trevor
 * 
 * This is the main data access class. It handles all the connectivity with the
 * RMS record stores to fetch and save data associated with MobileMedia TODO:
 * Refactor into stable interface for future updates. We may want to access data
 * from RMS, or eventually direct from the 'file system' on devices that support
 * the FileConnection optional API.
 * 
 */
public abstract class MediaAccessor {

	// Note: Our midlet only ever has access to Record Stores it created
	// For now, use naming convention to create record stores used by
	// MobileMedia
	protected String album_label; // "mpa- all album names
	// are prefixed with
	// this label
	protected String info_label; // "mpi- all album info
	// stores are prefixed with
	// this label
	protected String default_album_name; // default
	// album
	// name

	private final String SEPARATOR = ":";  

	//imageInfo holds image metadata like label, album name and 'foreign key' index to
	// corresponding RMS entry that stores the actual Image object
	private Hashtable mediaInfoTable = new Hashtable();

	protected String[] albumNames; // User defined names of photo albums

	// Record Stores
	private RecordStore mediaRS = null;
	private RecordStore mediaInfoRS = null;

	/**
	 * @param album_label
	 * @param info_label
	 * @param default_album_name
	 */
	public MediaAccessor(String album_label, String info_label, String default_album_name) {
		this.album_label = album_label; 
		this.info_label = info_label; 
		this.default_album_name = default_album_name; 
	}

	/**
	 * Load all existing photo albums that are defined in the record store.
	 * 
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 * @throws MediaNotFoundException 
	 */
	public void loadAlbums() throws InvalidImageDataException,
	PersistenceMechanismException, MediaNotFoundException {
		// Try to find any existing Albums (record stores)
		String[] currentStores = RecordStore.listRecordStores();
		if (currentStores != null) {

			String[] temp = new String[currentStores.length];
			int count = 0;
			// Only use record stores that follow the naming convention defined
			for (int i = 0; i < currentStores.length; i++) {
				String curr = currentStores[i];

				// If this record store is a photo album...
				if (curr.startsWith(album_label)) {
					// Strip out the mpa- identifier
					curr = curr.substring(4);
					// Add the album name to the array
					temp[i] = curr;
					count++;
				}
			}
			// Re-copy the contents into a smaller array now that we know the
			// size
			albumNames = new String[count];
			int count2 = 0;
			for (int i = 0; i < temp.length; i++) {
				if (temp[i] != null) {
					albumNames[count2] = temp[i];
					count2++;
				}
			}
		} else {
			resetRecordStore();
			loadAlbums();
		}
	}

	protected abstract void resetRecordStore()
	throws InvalidImageDataException, PersistenceMechanismException, MediaNotFoundException;

	protected abstract byte[] getByteFromMediaInfo(IMediaData ii)
	throws InvalidImageDataException;

	protected abstract byte[] getMediaArrayOfByte(String path)
	throws ImagePathNotValidException, InvalidImageFormatException;

	protected abstract IMediaData getMediaFromBytes(byte[] data)
	throws InvalidArrayFormatException;

	public void addMediaData(String mediaName, String path, String mediaType, String fileExtension, String albumName)
	throws InvalidImageDataException, PersistenceMechanismException {
		try {
			byte[] data1 = getMediaArrayOfByte(path);
			addMediaArrayOfBytes(mediaName, albumName, mediaType, fileExtension, data1);
		} catch (RecordStoreException e) {
			throw new PersistenceMechanismException(e.getMessage());
		}
	}

	protected void addMediaArrayOfBytes(String mediaName, String albumName, String mediaType, String fileExtension, byte[] data1) throws RecordStoreException,
	RecordStoreFullException, RecordStoreNotFoundException,
	RecordStoreNotOpenException, InvalidImageDataException {


		mediaRS = RecordStore.openRecordStore(album_label + albumName, true);

		//System.out.println("[MediaAccessor:addMediaArrayOfBytes]"+info_label + albumName);
		mediaInfoRS = RecordStore.openRecordStore(info_label + albumName, true);

		int rid = 0; // new record ID for Image (bytes)
		int rid2; // new record ID for ImageData (metadata)
		try{
			rid = mediaRS.addRecord(data1, 0, data1.length);
		}catch( Exception e ){ e.printStackTrace(); }
		IMediaData ii = new MediaData(rid,
				album_label + albumName, mediaName);
		rid2 = mediaInfoRS.getNextRecordID();

		ii.setRecordId(rid2);

		//added in MM-Cosmos-v7
		ii.setMediaType(mediaType);
		ii.setFileExtension(fileExtension);

		data1 = getByteFromMediaInfo(ii);

		//debug
		//str = new String( data1 );
		//System.out.println("2====>"+str);

		mediaInfoRS.addRecord(data1, 0, data1.length);
		mediaRS.closeRecordStore();
		mediaInfoRS.closeRecordStore();
	}

	// #ifdef includeCopyPhoto
	/**
	 * [EF] Add in scenario 05
	 * @param photoname
	 * @param imageData
	 * @param albumName
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	public void addMediaData(IMediaData mediaData, String albumName) throws InvalidImageDataException, PersistenceMechanismException {
		try {
			//mediaData.setParentAlbumName( album_label + albumName );

			mediaRS = RecordStore.openRecordStore(album_label + albumName, true);
			mediaInfoRS = RecordStore.openRecordStore(info_label + albumName, true);
			int rid2; // new record ID for ImageData (metadata)
			rid2 = mediaInfoRS.getNextRecordID();
			mediaData.setRecordId(rid2);
			byte[] data1 = getByteFromMediaInfo(mediaData);

			mediaInfoRS.addRecord(data1, 0, data1.length);
		} catch (RecordStoreException e) {
			throw new PersistenceMechanismException( e.getMessage() );
		}finally{
			try {
				mediaRS.closeRecordStore();
				mediaInfoRS.closeRecordStore();
			} catch (RecordStoreNotOpenException e) {
				e.printStackTrace();
			} catch (RecordStoreException e) {
				e.printStackTrace();
			}
		}
	}
	// #endif

	/**
	 * This will populate the imageInfo hashtable with the ImageInfo object,
	 * referenced by label name and populate the imageTable hashtable with Image
	 * objects referenced by the RMS record Id
	 * 
	 * @throws PersistenceMechanismException
	 */
	public IMediaData[] loadMediaDataFromRMS(String recordName)
	throws PersistenceMechanismException, InvalidImageDataException {
		Vector mediaVector = new Vector();
		try {
			String infoStoreName = info_label + recordName;
			RecordStore infoStore = RecordStore.openRecordStore(infoStoreName,
					false);
			RecordEnumeration isEnum = infoStore.enumerateRecords(null, null,
					false);
			while (isEnum.hasNextElement()) {
				// Get next record
				int currentId = isEnum.nextRecordId();
				byte[] data = infoStore.getRecord(currentId);
				// Convert the data from a byte array into our ImageData
				// (metadata) object

				//debug
				String str = new String( data );
				System.out.println("[MediaAccessor:loadMediaDataFromRMS] str="+str);


				IMediaData iiObject = getMediaFromBytes(data);
				// Add the info to the metadata hashtable
				String label = iiObject.getMediaLabel();
				mediaVector.addElement(iiObject);


				this.setMediaInfo(label,recordName, iiObject);
			}
			infoStore.closeRecordStore();
		}catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}
		// Re-copy the contents into a smaller array
		MediaData[] labelArray = new MediaData[mediaVector.size()];
		mediaVector.copyInto(labelArray);
		return labelArray;
	}

	public IMediaData[] reLoadMediaDataFromRMS(String recordName)
	throws PersistenceMechanismException, InvalidImageDataException, MediaNotFoundException {
		Vector mediaVector = new Vector();
		try {
			String infoStoreName = info_label + recordName;
			RecordStore infoStore = RecordStore.openRecordStore(infoStoreName,
					false);
			RecordEnumeration isEnum = infoStore.enumerateRecords(null, null,
					false);
			while (isEnum.hasNextElement()) {
				// Get next record
				int currentId = isEnum.nextRecordId();
				byte[] data = infoStore.getRecord(currentId);
				// Convert the data from a byte array into our ImageData
				// (metadata) object
				IMediaData iiObject = getMediaFromBytes(data);
				// Add the info to the metadata hashtable
				String label = iiObject.getMediaLabel();
				mediaVector.addElement(iiObject);


				Object media = this.getMediaInfoFromTable(label, recordName);
				if( media == null ){
					this.setMediaInfo(label, recordName, iiObject);
				}
			}
			infoStore.closeRecordStore();
		}catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}
		// Re-copy the contents into a smaller array
		MediaData[] labelArray = new MediaData[mediaVector.size()];
		mediaVector.copyInto(labelArray);
		return labelArray;
	}

	/**
	 * Update the Image metadata associated with this named photo
	 * @throws InvalidImageDataException 
	 * @throws PersistenceMechanismException 
	 */
	public boolean updateMediaInfo(IMediaData oldData, IMediaData newData) throws InvalidImageDataException, PersistenceMechanismException {
		boolean success = false;
		RecordStore infoStore = null;
		try {

			// Parse the Data store name to get the Info store name
			String infoStoreName = oldData.getParentAlbumName();

			infoStoreName = info_label + infoStoreName.substring(album_label.length());
			infoStore = RecordStore.openRecordStore(infoStoreName, false);
			byte[] mediaDataBytes = getByteFromMediaInfo(newData);

			infoStore.setRecord(newData.getRecordId(), mediaDataBytes, 0, mediaDataBytes.length);
			//infoStore.setRecord(oldData.getRecordId(), mediaDataBytes, 0, mediaDataBytes.length);

		} catch (RecordStoreException rse) {
			System.out.println("[IFilesystem:updateMediaInfo] Exception:"+rse.getMessage());
			rse.printStackTrace();
			throw new PersistenceMechanismException(rse.getMessage());
		}
		// Update the Hashtable 'cache'
		setMediaInfo(oldData.getMediaLabel(), newData.getParentAlbumName(), newData);
		try {
			infoStore.closeRecordStore();
		} catch (RecordStoreNotOpenException e) {
			//No problem if the RecordStore is not Open
		} catch (RecordStoreException e) {
			throw new PersistenceMechanismException( e.getMessage() );
		}
		return success;
	}



	/**
	 * Get the data for an Image as a byte array. This is useful for sending
	 * images via SMS or HTTP
	 * @throws PersistenceMechanismException 
	 */
	public byte[] loadMediaBytesFromRMS(String recordName, int recordId) throws PersistenceMechanismException {
		byte[] mediaData = null;
		try {

			RecordStore albumStore = RecordStore.openRecordStore(recordName,false);

			mediaData = albumStore.getRecord(recordId);

			albumStore.closeRecordStore();

		} catch (RecordStoreException rse) {
			System.out.println("[MediaAccessor:loadMediaBytesFromRMS] Error:"+rse.getMessage());
			throw new PersistenceMechanismException(rse);
		}
		return mediaData;
	}

	/**
	 * Delete a single (specified) image from the (specified) record store. This
	 * will permanently delete the image data and metadata from the device.
	 * @throws PersistenceMechanismException 
	 * @throws NullAlbumDataReference 
	 * @throws ImageNotFoundException 
	 */
	public boolean deleteSingleMediaFromRMS(String storeName, String mediaName) throws PersistenceMechanismException, MediaNotFoundException {
		boolean success = false;
		// Open the record stores containing the byte data and the meta data
		// (info)
		try {
			// Verify storeName is name without pre-fix
			mediaRS = RecordStore.openRecordStore(album_label + storeName, true);
			mediaInfoRS = RecordStore.openRecordStore(info_label + storeName,true);
			IMediaData mediaData = getMediaInfoFromTable(mediaName,storeName);
			int rid = mediaData.getForeignRecordId();


			mediaRS.deleteRecord(rid);
			mediaInfoRS.deleteRecord(rid);
			mediaRS.closeRecordStore();
			mediaInfoRS.closeRecordStore();
		} catch (RecordStoreException rse) {
			rse.printStackTrace();

			throw new PersistenceMechanismException(rse);
		}
		return success;
	}

	/**
	 * Define a new photo album for mobile photo users. This creates a new
	 * record store to store photos for the album.
	 * @throws PersistenceMechanismException 
	 * @throws InvalidPhotoAlbumNameException 
	 */
	public void createNewAlbum(String albumName) throws PersistenceMechanismException, InvalidPhotoAlbumNameException {
		RecordStore newAlbumRS = null;
		RecordStore newAlbumInfoRS = null;
		if (albumName.equals("")){
			throw new InvalidPhotoAlbumNameException("Invalid album name!");
		}
		String[] names  = getAlbumNames();
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(albumName))
				throw new InvalidPhotoAlbumNameException("There is already a album with this name: " +  albumName );
		}
		try {
			newAlbumRS = RecordStore.openRecordStore(album_label + albumName,
					true);
			newAlbumInfoRS = RecordStore.openRecordStore(
					info_label + albumName, true);
			newAlbumRS.closeRecordStore();
			newAlbumInfoRS.closeRecordStore();
		} catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}
	}

	/**
	 * @param albumName
	 * @throws PersistenceMechanismException
	 */
	public void deleteAlbum(String albumName) throws PersistenceMechanismException {
		try {
			RecordStore.deleteRecordStore(album_label + albumName);
			RecordStore.deleteRecordStore(info_label + albumName);
		} catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}
	}

	/**
	 * Get the list of photo album names currently loaded.
	 * 
	 * @return Returns the albumNames.
	 */
	public String[] getAlbumNames() {
		return albumNames;
	}




	/**
	 * Get information about the media specified by both media name and its album's name
	 * @param mediaName the name of the media whose information will be retrieved
	 * @param albumName the name of the album that the media belongs to
	 * @return
	 * @throws MediaNotFoundException 
	 */
	protected IMediaData getMediaInfoFromTable(String mediaName, String albumName) throws MediaNotFoundException{
		System.out.println("Print the media info table:");
		this.printHashtable(mediaInfoTable);

		if( ( mediaName != null) && ( albumName != null )){
			String key = this.createKey(mediaName, albumName);
			System.out.println("[MediaAccessor:getMediaInfoFromTable] key="+key);
			IMediaData mediaData = (IMediaData) mediaInfoTable.get(key);
			if(mediaData == null)
				System.err.println("[MediaAccessor:getMediaInfoFromTable] MediaData is null");
			else
				System.out.println("[MediaAccessor:getMediaInfo] It was found "+mediaData.getRecordId()+"*"+mediaData.getForeignRecordId()+"*"+mediaData.getParentAlbumName()+"*"+mediaData.getMediaLabel());
			return mediaData;
		}
		else{
			System.err.println("====> Media "+mediaName+" from the album "+albumName+" was not found");
			throw new MediaNotFoundException("Media "+mediaName+" from the album "+albumName+" was not found");
		}
	}

	protected boolean setMediaInfo(String mediaName, String albumName, Object obj){
		if( (mediaName != null) && (albumName != null) && (obj != null) ){
			String key = this.createKey(mediaName, albumName); 


			IMediaData mediaData = (IMediaData) obj;
			//System.out.println("[MediaAccessor:setMediaInfo] adding "+key+" id="+mediaData.getRecordId()+ " foreignID="+mediaData.getRecordId() );


			this.mediaInfoTable.put(key, obj);
			return true;
		}
		else
			return false;
	}

	private String createKey(String mediaName, String albumName){
		if( ( mediaName != null) && ( albumName != null )){
			String key = albumName + SEPARATOR + mediaName;
			return key;
		}
		return null;


	}

	//TODO delete - debug
	private void printHashtable(Hashtable ht){
		Enumeration en = ht.keys();
		while( en.hasMoreElements() ){
			String md = (String) en.nextElement();
			System.out.println(md);
		}
	}
}