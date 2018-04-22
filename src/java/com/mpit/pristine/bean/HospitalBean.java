package com.mpit.pristine.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpit.pristine.json.JsonResourceReader;
import com.mpit.pristine.persistence.entity.Hospital;
import java.io.BufferedReader;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author neemarh
 */
@Named(value = "hospitalBean")
@ApplicationScoped
public class HospitalBean {

    /**
     * Creates a new instance of HospitalBean
     */
    private boolean jsonUsed = false;
    private Hospital hospital;

    public HospitalBean() {
        JsonResourceReader jrr = new JsonResourceReader();
        if (jrr.checkHospitalDefinationJSONResx()) {
            BufferedReader reader = jrr.getHospitalDefinitionReader();
            GsonBuilder gb = new GsonBuilder();
            Gson gs = gb.setPrettyPrinting().create();
            this.hospital = gs.fromJson(reader, Hospital.class);
            this.jsonUsed = true ;
        }

        
        
    }

    public boolean isJsonUsed() {
        return jsonUsed;
    }

    public void setJsonUsed(boolean jsonUsed) {
        this.jsonUsed = jsonUsed;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

}
