/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPU;

import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Ben
 */
public interface ISMS {

    public void sendErrorReport(Date timeStamp, String incidentName, String location, String type, double longitude, double latitude, String description, int severity, String callno, int errorCode, String errorDescription);
    public ArrayList<AgencyNumbers> sendAgencyNumbers();
}
