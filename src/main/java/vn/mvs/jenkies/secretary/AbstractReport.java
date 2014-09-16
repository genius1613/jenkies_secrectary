package vn.mvs.jenkies.secretary;

import org.apache.commons.configuration.XMLConfiguration;

/**
 * Created by tienbm on 16/09/2014.
 */
public abstract class AbstractReport {

    public AbstractReport(){
        loadConfiguation();
    }

    public AbstractReport(XMLConfiguration xmlConfiguration) {

    }

    public abstract String getNotifcation();

    public abstract void loadConfiguation();

}
