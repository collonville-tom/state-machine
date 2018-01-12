package org.tc.osgi.bundle.ts.utils;

import java.io.FileNotFoundException;

import org.tc.osgi.bundle.ts.m3.ITs;
import org.tc.osgi.bundle.ts.utils.exception.TSFileNotFoundException;
import org.tc.osgi.bundle.utils.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.serial.SerialTool;

/**
 * TSSerialTool.java.
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
    private final String defaultSerialisationDir = null;

    /**
     * FsmSerialization constructor.
     */
    protected TSSerialTool() {
        super();
    }

    /**
     * getDefaultSerialisationDir.
     * @return String
     * @throws FieldTrackingAssignementException
     */
    public String getDefaultSerialisationDir() throws FieldTrackingAssignementException {
        // TODO
        // if (this.defaultSerialisationDir == null) {
        // UtilsPropertyFile.getInstance("ts").fieldTraking(this,
        // "defaultSerialisationDir");
        // }
        return defaultSerialisationDir;
    }

    /**
     * readFSM.
     * @param name String
     * @return FiniteStateMachine
     * @throws FieldTrackingAssignementException
     * @throws TSFileNotFoundException
     * @throws FileNotFoundException
     */
    public ITs readTS(final String name) throws FieldTrackingAssignementException, TSFileNotFoundException {
        LoggerGestionnary.getInstance(TSSerialTool.class).debug("deserialisation de " + name);
        ITs o;
        try {
            o = new SerialTool<ITs>().read(name + TSSerialTool.xmlExtension, getDefaultSerialisationDir());
        } catch (final FileNotFoundException e) {
            throw new TSFileNotFoundException("Le fichier ne contient pas de TS", e);
        }
        return o;

    }

    /**
     * saveFSM.
     * @param fsm FiniteStateMachine
     * @throws FieldTrackingAssignementException
     * @throws FileNotFoundException
     */
    public void saveTS(final ITs ts) throws FieldTrackingAssignementException, FileNotFoundException {
        new SerialTool<ITs>().save(ts, ts.getName() + TSSerialTool.xmlExtension, getDefaultSerialisationDir());
        LoggerGestionnary.getInstance(TSSerialTool.class).debug(ts.getName() + "serialisé");

    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.utils.serial.AbstractSerialTool#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("Serialisation xml vers/depuis ");
        try {
            buff.append(getDefaultSerialisationDir());
        } catch (final FieldTrackingAssignementException e) {
            LoggerGestionnary.getInstance(TSSerialTool.class).error("Probleme d'acces au fichier de parametre de serialisation", e);
            buff.append("unknow dir cause exception");
        }
        return buff.toString();
    }

}
