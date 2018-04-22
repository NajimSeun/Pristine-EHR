package com.mpit.pristine.location;

/**
 *
 * @author Hp
 */
public class LocalGovernmentArea implements Location {
    
    private String Name ;

    @Override
    public String getName() {
        return Name ;
    }

    public LocalGovernmentArea(String Name) {
        this.Name = Name;
    }
    
    public State getState(){
       return null ;
       
    }
    
}
