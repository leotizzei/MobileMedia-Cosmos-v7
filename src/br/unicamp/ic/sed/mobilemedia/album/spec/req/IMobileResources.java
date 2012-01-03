package br.unicamp.ic.sed.mobilemedia.album.spec.req;

import javax.microedition.midlet.MIDlet;

public interface IMobileResources {

	/**
	 * Get the Midlet 
	 * @return
	 */
	public MIDlet getMainMIDlet();
		
	/**
	 * Destroy the Midlet
	 * @param unconditional
	 */
	public void destroyApp(boolean unconditional);
}
