//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.spec.req;

public interface IMedia {
	
	public void showLastMediaList();
	
	public void initCopyMediaToAlbum( String oldMediaName, String oldAlbumName, byte[] mediaBytes , Object media , String mediaType, String fileExtension );
}
//#endif