//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.media_music.impl;

import br.unicamp.ic.sed.mobilemedia.music.spec.req.IMedia;

public class IAdapterMediaMusic implements IMedia {

	IManager manager = ComponentFactory.createInstance();

	public void showLastMediaList() {
	
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia media = 
			(br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia)
				manager.getRequiredInterface("IMedia");
		
		media.showLastMediaList();
	}

	
	public void initCopyMediaToAlbum(String oldMediaName, String oldAlbumName,
			byte[] mediaBytes, Object media, String mediaType,
			String fileExtension) {
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia imedia = 
			(br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia)
				manager.getRequiredInterface("IMedia");
		imedia.initCopyMediaToAlbum(oldMediaName, oldAlbumName, mediaBytes, media, mediaType, fileExtension);
		
	}

	
}
//#endif