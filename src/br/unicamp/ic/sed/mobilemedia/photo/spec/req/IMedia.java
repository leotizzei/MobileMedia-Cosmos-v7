/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group (http://www.sed.ic.unicamp.br)
 *
 * date: March 2009
 * 
 */
//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.spec.req;

public interface IMedia {
	
	public void showLastMediaList();
	
	
	
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
	
}
//#endif