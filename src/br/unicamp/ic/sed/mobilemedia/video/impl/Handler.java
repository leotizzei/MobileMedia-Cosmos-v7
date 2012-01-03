//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.video.impl;

import br.unicamp.ic.sed.mobilemedia.video.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.video.spec.req.IExceptionHandler;

class Handler {
	
	protected void handle(Exception e){
		IManager manager = ComponentFactory.createInstance();
		IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
		handler.handle(e);
	}

}
//#endif