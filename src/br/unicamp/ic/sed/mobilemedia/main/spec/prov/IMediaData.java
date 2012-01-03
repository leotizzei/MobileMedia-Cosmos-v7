package br.unicamp.ic.sed.mobilemedia.main.spec.prov;

public interface IMediaData {

	/**
	 * @return Returns the recordId.
	 */
	public int getRecordId() ;
	
	/**
	 * @param recordId The recordId to set.
	 */
	public void setRecordId(int recordId) ;
	
	/**
	 * @return Returns the foreignRecordId.
	 */
	public int getForeignRecordId() ;
	
	/**
	 * @param foreignRecordId The foreignRecordId to set.
	 */
	public void setForeignRecordId(int foreignRecordId) ;
	
	/**
	 * @return Returns the imageLabel.
	 */
	public String getMediaLabel() ;
	
	/**
	 * @param imageLabel The imageLabel to set.
	 */
	public void setMediaLabel(String imageLabel) ;
	
	/**
	 * @return Returns the parentAlbumName.
	 */
	public String getParentAlbumName() ;
	
	/**
	 * @param parentAlbumName The parentAlbumName to set.
	 */
	public void setParentAlbumName(String parentAlbumName) ;

	// #ifdef includeFavourites
	/**
	 * [EF] Added in the scenario 03
	 */
	public void toggleFavorite() ;
	
	/**
	 * [EF] Added in the scenario 03
	 * @param favorite
	 */
	public void setFavorite(boolean favorite) ;

	/**
	 * [EF] Added in the scenario 03
	 * @return the favorite
	 */
	public boolean isFavorite() ;
	// #endif	

	// #ifdef includeSorting
	/**
	 * [EF] Added in the scenario 02 
	 */
	public void increaseNumberOfViews() ;

	/**
	 * [EF] Added in the scenario 02 
	 * @return the numberOfViews
	 */
	public int getNumberOfViews() ;
	
	/**
	 * [EF] Added in the scenario 02 
	 * @param views
	 */
	public void setNumberOfViews(int views) ;
	// #endif	
	
	/**
	 * Added in MobileMedia-Cosmos-v7
	 */
	public String getMediaType();
	
	
	/**
	 * Added in MobileMedia-Cosmos-v7
	 * @param mediaType
	 */
	public void setMediaType(String mediaType);
	
	
	/**
	 * Added in MobileMedia-Cosmos-v7
	 * Return the specific type of a media (e.g. png, x-wav, mpeg)
	 * @return
	 */
	public String getFileExtension();
	
	
	/**
	 * Added in MobileMedia-Cosmos-v7
	 * Determine the specific type of a media (e.g. png, x-wav, mpeg)
	 * @param fileExtension
	 * @return
	 */
	public void setFileExtension(String fileExtension);
}
