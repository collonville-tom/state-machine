package org.tc.osgi.bundle.ts.destkit.utils;

import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.tc.osgi.bundle.utils.exception.FieldTrackingAssignementException;

/**
 * NameBuilder.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class NameBuilder {

    /**
     * TreeSet<String> deComposeName.
     */
    private TreeSet<String> deComposeName;
    /**
     * String delimiter.
     */
    private String delimiter = null;

    /**
     * NameBuilder constructor.
     * @param name String
     * @param delimiter String
     */
    public NameBuilder(final String name, final String delimiter) {
        this.delimiter = delimiter;
        deComposeName = new TreeSet<String>(new Comparator<String>() {

            @Override
            public int compare(final String arg0, final String arg1) {
                if (arg0.compareTo(arg1) < 0) {
                    return 1;
                }
                if (arg0.compareTo(arg1) > 0) {
                    return -1;
                }
                return 0;
            }

        });

        for (final StringTokenizer str = new StringTokenizer(name, delimiter); str.hasMoreTokens();) {
            deComposeName.add(str.nextToken());
        }

    }

    /**
     * contains.
     * @param name String
     * @return boolean
     */
    public boolean contains(final String name) {
        final TreeSet<String> deComposeName2 = new TreeSet<String>(new Comparator<String>() {

            @Override
            public int compare(final String arg0, final String arg1) {
                if (arg0.compareTo(arg1) < 0) {
                    return 1;
                }
                if (arg0.compareTo(arg1) > 0) {
                    return -1;
                }
                return 0;
            }

        });
        for (final StringTokenizer str = new StringTokenizer(name, delimiter); str.hasMoreTokens();) {
            deComposeName2.add(str.nextToken());
        }
        if (deComposeName.containsAll(deComposeName2)) {
            return true;
        }
        return false;
    }

    /**
     * @return TreeSet<String>
     */
    public TreeSet<String> getDeComposeName() {
        return deComposeName;
    }

    /**
     * getDelimiter.
     * @return String
     * @throws FieldTrackingAssignementException
     */
    public String getDelimiter() throws FieldTrackingAssignementException {
        // if (delimiter == null) {
        // PropertyFile.getInstance("destkit").fieldTraking(this, "delimiter");
        // }
        return delimiter;
    }

    /**
     * getName.
     * @return String
     */
    public String getName() {
        final StringBuffer buff = new StringBuffer();
        boolean first = true;
        for (final String c : deComposeName) {
            if (!first) {
                buff.append(delimiter);
            }
            buff.append(c);
            first = false;
        }
        return buff.toString();
    }

    /**
     * @param deComposeName  TreeSet<String>
     */
    public void setDeComposeName(final TreeSet<String> deComposeName) {
        this.deComposeName = deComposeName;
    }
}
