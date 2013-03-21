/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz3003.recipient;

/**
 *
 * @author Ben
 */
import java.io.Serializable;

public class AgencyNumbers implements Serializable {

    private String agencyName;
    private String number;
    private String type;

    public AgencyNumbers(String agencyName, String number, String type) {
        this.agencyName = agencyName;
        this.number = number;
        this.type = type;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }
}
