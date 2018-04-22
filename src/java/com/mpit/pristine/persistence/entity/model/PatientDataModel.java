package com.mpit.pristine.persistence.entity.model;

import com.mpit.pristine.persistence.entity.Patient;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author najim
 */
public class PatientDataModel extends ListDataModel<Patient> implements SelectableDataModel<Patient>, Serializable {

    private static final long serialVersionUID = 1L;

    public PatientDataModel(List<Patient> patients) {
        super(patients);
    }

    public PatientDataModel() {
    }

    @Override
    public Patient getRowData(String patientID) {
        List<Patient> openedPatients = (List<Patient>) this.getWrappedData();

        for (Patient p : openedPatients) {
            if (p.getId().getID().equals(patientID)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Patient p) {
        return p.getId().getID();
    }
}
