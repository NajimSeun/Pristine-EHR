package com.mpit.pristine.location;

import com.mpit.pristine.utility.LocationUtility;
import java.util.List;

/**
 *
 * @author Hp
 */
public class State implements Location {

    private String Name;
    private Country _Country;

    public State(Country country, String Name) {
        this.Name = Name;
        this._Country = country;
    }

    @Override
    public String getName() {
        return Name;
    }

    public Country getCountry() {
        return _Country;
    }

    public List<String> getLocalGovernment() {


        return LocationUtility.getLocalGovernmentListAsStr(this);
    }
}
