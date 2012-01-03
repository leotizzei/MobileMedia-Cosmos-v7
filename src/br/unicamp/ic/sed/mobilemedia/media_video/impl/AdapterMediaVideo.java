//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.media_video.impl;

import br.unicamp.ic.sed.mobilemedia.video.spec.req.IMedia;

class AdapterMediaVideo implements IMedia {

	private IManager manager; 
	
	AdapterMediaVideo(IManager manager) {
		this.manager = manager;
	}



	public void showLastMediaList() {
		
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia imedia = (br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia) manager.getRequiredInterface("IMedia");
		imedia.showLastMediaList();
	}


	public void showMediaList(String albumName, String mediaType) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia imedia = (br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia) manager.getRequiredInterface("IMedia");
		imedia.showMediaList(albumName, mediaType);
	}


	
	
	public void initCopyMediaToAlbum(String oldMediaName, String oldAlbumName,
			byte[] mediaBytes, Object media, String mediaType,
			String fileExtension) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia imedia = (br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia) manager.getRequiredInterface("IMedia");
		imedia.initCopyMediaToAlbum(oldMediaName, oldAlbumName, mediaBytes, imedia, mediaType, fileExtension);
		
		
	}

}
//#endif