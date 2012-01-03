package br.unicamp.ic.sed.mobilemedia.media.spec.prov;

public interface IMedia {
	
	/**
	 * Show a list of medias in a specified album
	 * @param albumName the name of the album whose medias will be listed
	 * @param mediaType the type of media inside the album
	 */
	public void showMediaList( String albumName , String mediaType );	
	
	/**
	 * Copy the selected media to another album. 
	 * modified in MobileMedia-Cosmos-OO-v7
	 * 
	 * @param mediaName the name of the selected media
	 * @param mediaBytes the media content in bytes
	 * @param media
	 * @param mediaType the type of the media
	 */
	public void initCopyMediaToAlbum( String oldMediaName, String oldAlbumName, byte[] mediaBytes , Object media , String mediaType, String fileExtension );
	
	//TODO create the javadoc
	public void showLastMediaList();
	
	/**
	 * added in MM-Cosmos-v7
	 * @param mediaName
	 * @param mediaBytes
	 * @param media
	 * @param mediaType
	 */
	//public void initCopyMediaToAlbum( String mediaName , byte[] mediaBytes , Object media , String mediaType );
}
