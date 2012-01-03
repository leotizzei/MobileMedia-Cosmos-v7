//#ifdef includeVideo
package br.unicamp.ic.sed.mobilemedia.video_exceptionhandler.impl;


import br.unicamp.ic.sed.mobilemedia.video.spec.req.IExceptionHandler;

class IAdapterVideoEH implements IExceptionHandler {

	Manager manager;
	
	IAdapterVideoEH(Manager mgr) {
		manager = mgr;
	}
	
	public void handle(Exception exception) {
	
		br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler handler = (br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
		handler.handle(exception);

	}

}
//#endif