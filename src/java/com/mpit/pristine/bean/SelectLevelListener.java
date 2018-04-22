package com.mpit.pristine.bean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.extensions.component.masterdetail.SelectLevelEvent;

/**
 *
 * @author najim
 */
@Named(value = "selectLevelListener")
@RequestScoped
public class SelectLevelListener {

    /**
     * Creates a new instance of SelectLevelListener
     */
    public SelectLevelListener() {
    }
    @PostConstruct
    public void postInit(){
      LOG.log(java.util.logging.Level.SEVERE, "Constructed");
    }
    private static final Logger LOG = Logger.getLogger( SelectLevelListener.class.getName()); 
    private boolean errorOccured = false;
    private int Level = 2 ;
    public int handleNavigation(SelectLevelEvent selectLevelEvent) {
        LOG.log(java.util.logging.Level.SEVERE, "Navigator  called");
		if (errorOccured) {
			return Level;
		} else {
                    String l = ""+selectLevelEvent.getNewLevel() ;
                        LOG.log(java.util.logging.Level.OFF,l );
			return selectLevelEvent.getNewLevel();
		}
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }
        
        
}
