// #ifdef includeMusic
// [NC] Added in the scenario 07
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMediaData;

public class MultiMediaData extends MediaData {
	
	//private String typemedia;
	
	public MultiMediaData(int foreignRecordId, String parentAlbumName,
			String mediaLabel, String type) {
		super(foreignRecordId, parentAlbumName, mediaLabel);
		this.setMediaType(type);
		
	}
	
	public MultiMediaData(IMediaData mdata, String mediaType){
		super(mdata.getForeignRecordId(), mdata.getParentAlbumName(), mdata.getMediaLabel());
		this.setMediaType(mediaType);
	
		super.setRecordId(mdata.getRecordId());
		// #ifdef includeFavourites
		super.setFavorite(mdata.isFavorite());
		// #endif
		
		// #ifdef includeSorting
		super.setNumberOfViews(mdata.getNumberOfViews());
		// #endif
		this.setMediaType(mediaType);
	}
	
	
	public MultiMediaData(IMediaData mdata, String mediaType, String fileExtension){
		super(mdata.getForeignRecordId(), mdata.getParentAlbumName(), mdata.getMediaLabel());
		this.setMediaType(mediaType);
		this.setFileExtension(fileExtension);
		super.setRecordId(mdata.getRecordId());
		// #ifdef includeFavourites
		super.setFavorite(mdata.isFavorite());
		// #endif
		
		// #ifdef includeSorting
		super.setNumberOfViews(mdata.getNumberOfViews());
		// #endif
		this.setMediaType(mediaType);
	}
	/*public String getTypeMedia() {
		return typemedia;
	}
	public void setTypeMedia(String type) {
		this.typemedia = type;
	}*/
}
//#endif