//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.spec.prov;

import javax.microedition.lcdui.Image;

public interface IPhoto{
	
	
	
	/**[MD][Cosmos][SMS]*/
	//#ifdef includeSmsFeature 
	public void initPhotoViewScreen(Image image,  byte[] img);
	//#endif
	
	public void showImage( String albumName , String imageName );

	
	public String getImageName();
	
	public void showLastImage();
	
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
//#endif