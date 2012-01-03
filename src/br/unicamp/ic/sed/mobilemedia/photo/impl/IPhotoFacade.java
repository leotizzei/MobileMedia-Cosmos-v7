//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto;

class IPhotoFacade implements IPhoto{

	private PhotoController mainPhotocontroller;
	
	
	
	public IPhotoFacade() {
		this.mainPhotocontroller = new PhotoController();
		
	}
	
	
	//#ifdef includeSmsFeature 
	public void initPhotoViewScreen(Image image , byte[] img ) {
		
		this.mainPhotocontroller.initPhotoViewScreen(image, img);
		
		
	}
	
	public String getImageName() {
		return this.mainPhotocontroller.getImageName();
	}
	
	public void showLastImage() {
		this.mainPhotocontroller.showLastImage();
	}
	//#endif

	public void showImage(String albumName, String imageName) {
		this.mainPhotocontroller.showImage(albumName, imageName);
	}

	// #ifdef capturePhoto
	/**
	 * Added in MobileMedia-Cosmos-v7
	 * this method displays a video simulating user manipulation and allowing the 
	 * user to capture a photo (screenshot) of the video
	 * 
	 */
	public boolean capturePhoto() {
		return this.mainPhotocontroller.capturePhoto();
	}
	//#endif
	
}
//#endif