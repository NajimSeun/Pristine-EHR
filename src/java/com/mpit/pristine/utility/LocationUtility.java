
package com.mpit.pristine.utility;

import com.mpit.pristine.location.Country;
import com.mpit.pristine.location.LocalGovernmentArea;
import com.mpit.pristine.location.State;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class LocationUtility {

    private static final String LocationContextPath = "location/";
    private static final String StateContextPath = "/state/";
    private static final String StateExtens = "_states";
    private static final String LocGovExtens = "_lgs";
    private static final String LocGovContextPath = "localgovernment/";
    private static Logger LOG = Logger.getLogger(LocationUtility.class.getName());

    public static List<String> getCountryListAsStr(String path) {

        String expandedPath = LocationContextPath + path;
        return Utility.read(expandedPath);
    }

    public static List<Country> getCountryList(String path) {
        List<String> listAsString = getCountryListAsStr(path);
        List<Country> countryList = new ArrayList();
        if (listAsString == null) {
            return null;
        }

        for (int i = 0; i < listAsString.size(); i++) {
            countryList.add(new Country(listAsString.get(i)));
        }

        return countryList;
    }

    public static List<String> getStatesListAsStr(Country country) {
        String countryLowerCaseName = country.getName().toLowerCase();
        String fileNamePlusExtension = countryLowerCaseName + StateExtens + Utility.XMLExtension;
        String extendedPath = LocationContextPath + countryLowerCaseName + StateContextPath + fileNamePlusExtension;
        return Utility.read(extendedPath);
    }

    public static List<String> getLocalGovernmentListAsStr(State state) {
        String stateLowerCaseName = state.getName().toLowerCase();
        if (stateLowerCaseName.contains(" ")) {
            stateLowerCaseName = stateLowerCaseName.replace(' ', '_');
            LOG.log(Level.SEVERE, "I am called really");
        }


        String fileNamePlusExtension = stateLowerCaseName + LocGovExtens + Utility.XMLExtension;
        LOG.log(Level.SEVERE, fileNamePlusExtension);
        String extendedPath = LocationContextPath + (state.getCountry().getName().toLowerCase()) + StateContextPath + LocGovContextPath + fileNamePlusExtension;
        return Utility.read(extendedPath);
    }

    public static List<State> getStatesList(String path) {
        return null;
    }

    public static List<LocalGovernmentArea> getLocalGovernmentList(String path) {
        return null;
    }
}
