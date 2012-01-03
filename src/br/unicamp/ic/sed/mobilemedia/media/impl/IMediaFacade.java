/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: March 2009
  
 * Changed in MM-Cosmos-v7
 */
package br.unicamp.ic.sed.mobilemedia.media.impl;

import br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia;

public class IMediaFacade implements IMedia{

	private MediaMainController mediaMainController = new MediaMainController();
	
	public IMediaFacade() {
		System.out.println("[IMediaFacade:constructor]");
	}
	
	public void showMediaList(String albumName , String mediaType ) {
		System.out.println("[IMediaFacade:showMediaList]");
		this.mediaMainController.showMediaList(albumName , mediaType );
	}
	
	public void showLastMediaList(){
		System.out.println("[IMediaFacade:showLastMediaList]");
		this.mediaMainController.showLastMediaList();
	}

	public void initCopyMediaToAlbum(String oldMediaName, String oldAlbumName,  byte[] mediaBytes , Object media , String mediaType, String fileExtension ) {
		System.out.println("[IMediaFacade:initCopyMediaToAlbum("+mediaBytes+","+media+","+mediaType+","+fileExtension+")" );
		this.mediaMainController.initCopyMediaToAlbum( oldMediaName, oldAlbumName, mediaBytes , media , mediaType, fileExtension );
	}

	
	

	

}
