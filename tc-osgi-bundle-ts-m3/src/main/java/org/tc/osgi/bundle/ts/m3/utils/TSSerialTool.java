package org.tc.osgi.bundle.ts.m3.utils;

import java.io.FileNotFoundException;

import org.tc.osgi.bundle.ts.m3.core.ITs;
import org.tc.osgi.bundle.ts.m3.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.ts.m3.module.service.UtilsServiceProxy;
import org.tc.osgi.bundle.ts.m3.utils.exception.TSFileNotFoundException;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.serial.ISerialTool;

/**
 * TSSerialTool.java.
 * 
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class TSSerialTool {

	/**
	 * FsmSerialization fsmSer.
	 */
	protected static TSSerialTool serialTool = null;

	/**
	 * String xmlExtension.
	 */
	public final static String xmlExtension = ".xml";

	/**
	 * getInstance.
	 * 
	 * @return FsmSerialization
	 */
	public static TSSerialTool getInstance() {
		if (TSSerialTool.serialTool == null) {
			TSSerialTool.serialTool = new TSSerialTool();
		}
		return TSSerialTool.serialTool;
	}

	/**
	 * String defaultSerialisationDir.
	 */
	private final String defaultSerialisationDir = "target/";

	/**
	 * FsmSerialization constructor.
	 */
	protected TSSerialTool() {
		super();
	}

	/**
	 * getDefaultSerialisationDir.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getDefaultSerialisationDir() {
		// TODO
		// if (this.defaultSerialisationDir == null) {
		// UtilsPropertyFile.getInstance("ts").fieldTraking(this,
		// "defaultSerialisationDir");
		// }
		return defaultSerialisationDir;
	}

	/**
	 * readFSM.
	 * 
	 * @param name
	 *            String
	 * @return FiniteStateMachine
	 * @throws FieldTrackingAssignementException
	 * @throws TSFileNotFoundException
	 * @throws FileNotFoundException
	 */
	public ITs readTS(final String name) throws FieldTrackingAssignementException, TSFileNotFoundException {
		LoggerServiceProxy.getInstance().getLogger(TSSerialTool.class).debug("deserialisation de " + name);
		ITs o;
		try {
			ISerialTool<ITs> t = UtilsServiceProxy.getInstance().getSerialTool();
			o = t.read(name + TSSerialTool.xmlExtension, getDefaultSerialisationDir());
		} catch (final FileNotFoundException e) {
			throw new TSFileNotFoundException("Le fichier ne contient pas de TS", e);
		}
		return o;

	}

	/**
	 * saveFSM.
	 * 
	 * @param fsm
	 *            FiniteStateMachine
	 * @throws FieldTrackingAssignementException
	 * @throws FileNotFoundException
	 */
	public void saveTS(final ITs ts) throws FieldTrackingAssignementException, FileNotFoundException {
		ISerialTool<ITs> t = UtilsServiceProxy.getInstance().getSerialTool();
		t.save(ts, ts.getName() + TSSerialTool.xmlExtension, getDefaultSerialisationDir());
		LoggerServiceProxy.getInstance().getLogger(TSSerialTool.class).debug(ts.getName() + "serialisé");

	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.serial.AbstractSerialTool#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer buff = new StringBuffer("Serialisation xml vers/depuis ");

		buff.append(getDefaultSerialisationDir());

		return buff.toString();
	}

}
