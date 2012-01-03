package br.unicamp.ic.sed.mobilemedia.exceptionhandler_media.impl;

import br.unicamp.ic.sed.mobilemedia.media.spec.req.IExceptionHandler;

public class IAdapterMediaEH implements IExceptionHandler {

	public void handle(Exception exception) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler handler = (br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
		handler.handle(exception);

	}

}
