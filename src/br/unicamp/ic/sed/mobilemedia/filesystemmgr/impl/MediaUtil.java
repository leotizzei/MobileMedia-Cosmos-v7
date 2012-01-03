package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.IOException;
import java.io.InputStream;


import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidArrayFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageFormatException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;


/**
 * @author trevor This is a utility class. It performs conversions between Image
 *         objects and byte arrays, and Image metadata objects and byte arrays.
 *         Byte arrays are the main format for storing data in RMS, and for
 *         sending data over the wire.
 */
public class MediaUtil {

	// Delimiter used in record store data to separate fields in a string.
	protected static final String DELIMITER = "*";

	// [NC] Changed in the scenario 07: to support extension of the method getMediaInfoFromBytes
	protected int endIndex = 0;
	
	/**
	 * Constructor
	 */
	public MediaUtil() {
		super();
	}

	/**
	 * This method reads an Image from an Input Stream and converts it from a
	 * standard image file format into a byte array, so that it can be
	 * transported over wireless protocols such as SMS
	 * 
	 * @throws ImagePathNotValidException
	 * @throws InvalidImageFormatException
	 */
	public byte[] readMediaAsByteArray(String imageFile)
			throws ImagePathNotValidException, InvalidImageFormatException {

		byte bArray[] = new byte[1000];

		// Read an Image into a byte array
		// Required to transfer images over SMS
		InputStream is = null;
		try {
			is = (InputStream) this.getClass().getResourceAsStream(imageFile);
		} catch (Exception e) {
			throw new ImagePathNotValidException(
					"Path not valid for this image:"+imageFile);
		}

		int i, len = 0;
		byte bArray2[];
		byte b[] = new byte[1];
		try {
			while (is.read(b) != -1) {

				if (len + 1 >= bArray.length) {

					bArray2 = new byte[bArray.length];

					// Transfer all data from old array to temp array
					for (i = 0; i < len; i++)
						bArray2[i] = bArray[i];

					bArray = new byte[bArray2.length + 500];

					// Re-Copy contents back into new bigger array
					for (i = 0; i < len; i++)
						bArray[i] = bArray2[i];
				}

				// Set the size to be exact
				bArray[len] = b[0];
				len++;
			}

			is.close();
		} catch (IOException e1) {
			throw new InvalidImageFormatException(
					"The file "+imageFile+" does not have PNG format");
		} catch(NullPointerException e2){
			throw new ImagePathNotValidException(
					"Path not valid for this image:"+imageFile);
		}

		return bArray;
	}

	/**
	 * 
	 * Convert the byte array from a retrieved RecordStore record into the
	 * ImageInfo ((renamed MediaData) object Order of the string will look like
	 * this: <recordId>*<foreignRecordId>*<labelName>*<imageLabel> Depending
	 * on the optional features, additional fields may be: <phoneNum>
	 * 
	 * @throws InvalidArrayFormatException
	 */
	protected MediaData getMediaInfoFromBytes(byte[] bytes)
			throws InvalidArrayFormatException {

		try {
			String iiString = new String(bytes);
			
			System.out.println( iiString );
			
			// Track our position in the String using delimiters
			// Ie. Get chars from beginning of String to first Delim
			int startIndex = 0;
			endIndex = iiString.indexOf(DELIMITER);
			
			// Get recordID int value as String - everything before first
			// delimeter
			String intString = iiString.substring(startIndex, endIndex);
			
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] intString="+intString);
			
			// Get 'foreign' record ID corresponding to the image table
			startIndex = endIndex + 1;
			endIndex = iiString.indexOf(DELIMITER, startIndex);
			String fidString = iiString.substring(startIndex, endIndex);
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] fidString="+fidString);
			
			// Get Album name (recordstore) - next delimeter
			startIndex = endIndex + 1;
			
			endIndex = iiString.indexOf(DELIMITER, startIndex);
			//System.out.println("[MediaUtil] startIndex:"+startIndex+" endIndex:"+endIndex);
			String albumLabel = iiString.substring(startIndex, endIndex);
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] album="+albumLabel);
			
			startIndex = endIndex + 1;
			
			endIndex = iiString.indexOf(DELIMITER, startIndex);
			
			if (endIndex == -1)
				endIndex = iiString.length();
			
			String imageLabel = "";
			//System.out.println("[MediaUtil] startIndex:"+startIndex+" endIndex:"+endIndex);
			imageLabel = iiString.substring(startIndex, endIndex);
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] imageLabel="+imageLabel); 
			
			
			// #ifdef includeFavourites
			// [EF] Favorite (Scenario 03)
			boolean favorite = false;
			startIndex = endIndex + 1;
			endIndex = iiString.indexOf(DELIMITER, startIndex);
			
			if (endIndex == -1)
				endIndex = iiString.length(); 
			favorite = (iiString.substring(startIndex, endIndex)).equalsIgnoreCase("true");
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] favorite="+favorite);
			// #endif

			// #ifdef includeSorting
			// [EF] Number of Views (Scenario 02)
			startIndex = endIndex + 1;
			endIndex = iiString.indexOf(DELIMITER, startIndex);

			if (endIndex == -1)
				endIndex = iiString.length();

			int numberOfViews = 0;
			try {
				//System.out.println("[MediaUtil] startIndex:"+startIndex+" endIndex:"+endIndex);
				if( endIndex < startIndex){
					System.err.println("Error: it was not possible to get the number of views");
					numberOfViews = 0;
				}
				else{
					numberOfViews = Integer.parseInt(iiString.substring(startIndex, endIndex));
				}
			} catch (RuntimeException e) {
				numberOfViews = 0;
				//System.out.println(e.getLocalizedMessage());
				System.out.println("[MediaUtil]Error: index out of bounds!" );
				e.printStackTrace();
			}
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] numberOfViews="+numberOfViews);
			// #endif
				
			Integer x = Integer.valueOf(fidString);
			MediaData ii = new MediaData(x.intValue(), albumLabel, imageLabel);
			
			// #ifdef includeFavourites
			ii.setFavorite(favorite);
			// #endif
			
			// #ifdef includeSorting
			ii.setNumberOfViews(numberOfViews);
			// #endif
			
			x = Integer.valueOf(intString);
			ii.setRecordId(x.intValue());
			
			//System.out.println("start="+startIndex+" end="+endIndex);
			startIndex = endIndex + 1;
			endIndex = iiString.indexOf(DELIMITER, startIndex);

			
			//get media type
			if (endIndex == -1)
				setEndIndex(iiString.length());
			//System.out.println("iiString.charAt("+startIndex+")="+iiString.charAt(startIndex)+" ["+endIndex+"]="+iiString.charAt(endIndex));
			String mediaType = iiString.substring(startIndex, endIndex);
			ii.setMediaType(mediaType);
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] mediaType="+mediaType);
			
			//System.out.println("start="+startIndex+" end="+endIndex);
			//get file extension
			startIndex = endIndex + 1;
			setEndIndex(iiString.indexOf(DELIMITER, startIndex));
			if (endIndex == -1)
				setEndIndex(iiString.length());
			String fileExtension = iiString.substring(startIndex, endIndex);
			ii.setFileExtension(fileExtension);
			//System.out.println("[MediaUtil:getMediaInfoFromBytes] fileExtension="+fileExtension);
			
			return ii;
		} catch (Exception e) {
			System.out.println("[MediaUtil:getMediaInfoFromBytes] Error:"+e.getMessage());
			throw new InvalidArrayFormatException( e.getMessage() );
		}
	}

	/**
	 * 
	 * Convert the ImageInfo (renamed MediaData) object into bytes so we can
	 * store it in RMS Order of the string will look like this: <recordId>*<foreignRecordId>*<labelName>*<imageLabel>
	 * Depending on the optional features, additional fields may be: <phoneNum>
	 * @throws InvalidImageDataException 
	 */
	protected byte[] getBytesFromMediaInfo(IMediaData ii) throws InvalidImageDataException {
		//this.teste();
		// Take each String and get the bytes from it, separating fields with a
		// delimiter
		try {
			String byteString = new String();

			// Convert the record ID for this record
			int i = ii.getRecordId();
			Integer j = new Integer(i);
			byteString = byteString.concat(j.toString());
			byteString = byteString.concat(DELIMITER);

			// Convert the 'Foreign' Record ID field for the corresponding Image
			// record store
			int i2 = ii.getForeignRecordId();
			Integer j2 = new Integer(i2);
			byteString = byteString.concat(j2.toString());
			byteString = byteString.concat(DELIMITER);

			// Convert the album name field
			byteString = byteString.concat(ii.getParentAlbumName());
			byteString = byteString.concat(DELIMITER);

			// Convert the label (name) field
			byteString = byteString.concat(ii.getMediaLabel());

			// #ifdef includeFavourites
			// [EF] Added in scenario 03
			byteString = byteString.concat(DELIMITER);
			if (ii.isFavorite()) byteString = byteString.concat("true");
			else byteString = byteString.concat("false");
			// #endif

			// #ifdef includeSorting
			// [EF] Added in scenario 02
			byteString = byteString.concat(DELIMITER);
			byteString = byteString.concat(""+ii.getNumberOfViews());
			// #endif
			
			//add media type
			byteString = byteString.concat(DELIMITER);
			byteString = byteString.concat(""+ii.getMediaType());
			
			//add file extension
			byteString = byteString.concat(DELIMITER);
			byteString = byteString.concat(""+ii.getFileExtension());
			
			
			
			// Convert the phone number field
			return byteString.getBytes();
		} catch (Exception e) {
			throw new InvalidImageDataException("The provided data are not valid");
		}

	}

	protected void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	protected int getEndIndex() {
		return endIndex;
	}

	
	
}