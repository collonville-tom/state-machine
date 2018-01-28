package org.tc.osgi.bundle.ts.m3.module.service;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IUtilsService;
import org.tc.osgi.bundle.utils.interf.serial.ISerialTool;

public class UtilsServiceProxy implements IUtilsService {

	/**
	 * UtilsServiceProxy instance.
	 */
	private static UtilsServiceProxy instance = null;

	/**
	 * getInstance.
	 * @return UtilsServiceProxy
	 */
	public static UtilsServiceProxy getInstance() {
		if (UtilsServiceProxy.instance == null) {
			UtilsServiceProxy.instance = new UtilsServiceProxy();
		}
		return UtilsServiceProxy.instance;
	}

	/**
	 * IUtilsService service.
	 */
	private IUtilsService service = null;

	public void setService(IUtilsService service) {
		this.service = service;
	}


	/**
	 * UtilsServiceProxy constructor.
	 */
	private UtilsServiceProxy() {

	}

	
	@Override
	public <T extends Serializable> ISerialTool<T> getSerialTool() {
		return this.service.getSerialTool();
	}

	@Override
	public boolean contains(Annotation[] annots, Class<?> ann) {
		return this.service.contains(annots, ann);
	}

	@Override
	public String list2String(List chaines, String delimiter) {
		return this.service.list2String(chaines, delimiter);
	}

	@Override
	public <T> String tab2String(T[] tab, String delimiter) {
		return this.service.tab2String(tab, delimiter);
	}

}
