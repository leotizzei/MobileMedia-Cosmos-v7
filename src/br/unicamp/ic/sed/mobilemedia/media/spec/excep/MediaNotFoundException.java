package br.unicamp.ic.sed.mobilemedia.media.spec.excep;

public class MediaNotFoundException extends Exception {

	public MediaNotFoundException(String message) {
		super(message);
	}
	
	
	
	public String getMessage(){
		return "MediaNotFoundException: Error, check the name and the album of the media";
		
	}
	
}
