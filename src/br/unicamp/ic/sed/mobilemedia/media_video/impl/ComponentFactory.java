//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.media_video.impl;


public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}

//#endif


