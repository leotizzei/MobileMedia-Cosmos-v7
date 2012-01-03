package br.unicamp.ic.sed.mobilemedia.media.spec.req;

public interface IPhoto {
	
	public void showPhoto( String albumName , String imageName );
	
	// #ifdef capturePhoto
	/**
	 * Added in MobileMedia-Cosmos-v7
	 * this method displays a video simulating user manipulation and allowing the 
	 * user to capture a photo (screenshot) of the video
	 * 
	 */
	public boolean capturePhoto();
	// #endif
}
	