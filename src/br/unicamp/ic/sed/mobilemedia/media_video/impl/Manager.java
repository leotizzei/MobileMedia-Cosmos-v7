//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.media_video.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class Manager implements IManager{

	Hashtable requiredInterfaces = new Hashtable();
	Hashtable providedInterfaces = new Hashtable();
	
	public Manager() {
		System.out.println("[media_video:manager] constructor");
		this.providedInterfaces.put("IVideo", new AdapterVideoMedia(this));
		this.providedInterfaces.put("IMedia", new AdapterMediaVideo(this));
	}
	
	
	public String[] getProvidedInterfaces(){
		      
	   return convertListToArray(providedInterfaces.keys());
	}
	
	public String[] getRequiredInterfaces(){
	
		return convertListToArray(requiredInterfaces.keys());
	}
	
	public Object getProvidedInterface(String name){
  
	   return this.providedInterfaces.get(name);
	}
	
	public void setRequiredInterface(String name, Object adapter){
		requiredInterfaces.put(name,adapter);
	}
	
	public Object getRequiredInterface(String name){
	   return requiredInterfaces.get(name);
	}

	private String[] convertListToArray(Enumeration stringEnum){
		Vector stringVector = new Vector();
		for (Enumeration iter = stringEnum; iter.hasMoreElements();) {
			String element = (String) iter.nextElement();
			stringVector.addElement(element);
		}
		
		String[] stringArray = new String[stringVector.size()];
		for (int i=0; i < stringVector.size(); i++){
			stringArray[i] = (String) stringVector.elementAt(i);
		}
		return stringArray;
	}
}



//#endif