
package br.unicamp.ic.sed.mobilemedia.media.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;

import br.unicamp.ic.sed.mobilemedia.video.spec.dt.Constants;

public class AddMediaToAlbum extends Form {
	
	// the name of the new media
	private TextField labeltxt = new TextField("Media label", "", 15, TextField.ANY);
	
	// the album of the new media
	private TextField mediapathtxt = new TextField("Path", "", 20, TextField.ANY);
	
	private String mediaType;
	private String fileExtension;
	private byte[] captureMedia = null;
	
	private String mediaName;
	private String albumName;
	
	// #ifdef includeMusic
	// [NC] Added in the scenario 07
	TextField itemtype = new TextField("Type of media", "", 20, TextField.ANY);
	//#endif
	
	
	

	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	Image image = null;
	//#endif	
	
	Command ok;
	Command cancel;
	private byte[] imageBytes;

	public AddMediaToAlbum(String title, String mediaType, String fileExtension) {
		super(title);
		this.mediaType = mediaType;
		this.fileExtension = fileExtension;
		this.append(labeltxt);
		this.append(mediapathtxt);
	
		// #ifdef includeMusic
		// [NC] Added in the scenario 07
		if( Constants.MUSIC_MEDIA.equals(mediaType) )
			this.append(itemtype);
		//#endif
		
		
		ok = new Command("Save Item", Command.SCREEN, 0);
		cancel = new Command("Cancel", Command.EXIT, 1);
		this.addCommand(ok);
		this.addCommand(cancel);
		System.out.println("AddMediaToAlbum created");
	}
	
	protected String getMediaName(){
		return this.mediaName;
	}
	
	/**
	 * [EF] Added in scenario 05 in order to reuse this screen in the Copy Photo functionality
	 * @param photoName
	 */
	protected void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	
	protected String getPath() {
		System.out.println("[AddMediaToAlbum.getPath] "+mediapathtxt.getString());
		return mediapathtxt.getString();
	}

	/**
	 * [EF] Added in scenario 05 in order to reuse this screen in the Copy Photo functionality
	 * @param photoName
	 */
	protected void setLabelMediaPath(String label) {
		mediapathtxt.setLabel(label);
	}
	
	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	protected Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	//#endif

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	
	public byte[] getImageBytes(){
		return this.imageBytes;
	}

	//#ifdef includeMusic
	public String getMediaType(){
		return mediaType;	
	}
	
	//#endif
	
	protected String getFileExtension() {
		return fileExtension;
	}
	
	// #if captureVideo || includeSmsFeature || capturePhoto
	// [NC] Added in the scenario 08
	// Add in scenario 6 as getImage and setImage. Due to some problems to convert 
	// Image to byte[], we decided to provide a byte[] rather than Image.
	public byte[] getCapturedMedia() {
		return this.captureMedia;
	}

	public void setCapturedMedia(byte[] capturedMedia) {
		this.captureMedia = capturedMedia;
	}
	//#endif
	
	protected String getAlbumName() {
		return albumName;
	}

	protected void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	protected String getLabeltxt() {
		return labeltxt.getString();
	}

	protected void setLabeltxt(TextField labeltxt) {
		this.labeltxt = labeltxt;
	}

	protected String getMediapathtxt() {
		return mediapathtxt.getString();
	}

	protected void setMediapathtxt(TextField mediapathtxt) {
		this.mediapathtxt = mediapathtxt;
	}
}

