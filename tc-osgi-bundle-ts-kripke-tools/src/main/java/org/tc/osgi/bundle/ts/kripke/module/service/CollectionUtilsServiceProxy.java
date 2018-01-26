package org.tc.osgi.bundle.ts.kripke.module.service;

import java.util.Collection;

import org.tc.osgi.bundle.ts.m3.module.service.UtilsServiceProxy;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.collection.ITransformer;
import org.tc.osgi.bundle.utils.interf.module.service.ICollectionUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IUtilsService;

public class CollectionUtilsServiceProxy implements ICollectionUtilsService {

	/**
	 * UtilsServiceProxy instance.
	 */
	private static CollectionUtilsServiceProxy instance = null;

	/**
	 * getInstance.
	 * @return UtilsServiceProxy
	 */
	public static CollectionUtilsServiceProxy getInstance() {
		if (CollectionUtilsServiceProxy.instance == null) {
			CollectionUtilsServiceProxy.instance = new CollectionUtilsServiceProxy();
		}
		return CollectionUtilsServiceProxy.instance;
	}

	/**
	 * IUtilsService service.
	 */
	private ICollectionUtilsService service = null;

	/**
	 * UtilsServiceProxy constructor.
	 */
	private CollectionUtilsServiceProxy() {

	}
	
	@Override
	public <T> Collection<T> array2List(T[] t) {
		return this.service.array2List(t);
	}

	@Override
	public <T> Collection<T> collect(Collection<T> c, ITransformer<T> t) {
		return this.service.collect(c,t);
	}

	@Override
	public <T> boolean detect(Collection<T> c, IPredicate<T> p) {
		return this.service.detect(c,p);
	}

	@Override
	public <T> T extract(Collection<T> c, IPredicate<T> p) {
		return this.service.extract(c,p);
	}

	@Override
	public <T> Collection<T> reject(Collection<T> c, IPredicate<T> p) {
		return this.service.reject(c,p);
	}

	@Override
	public <T> Collection<T> select(Collection<T> c, IPredicate<T> p) {
		return this.service.select(c,p);
	}

}
