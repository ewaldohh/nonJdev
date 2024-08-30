
package scheduler.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for schedulerExportRequestWS complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="schedulerExportRequestWS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="jobExecutor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schedulerExportRequestWS", propOrder = { "jobExecutor" })
public class SchedulerExportRequestWS {

    protected String jobExecutor;

    /**
     * Gets the value of the jobExecutor property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getJobExecutor() {
        return jobExecutor;
    }

    /**
     * Sets the value of the jobExecutor property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJobExecutor(String value) {
        this.jobExecutor = value;
    }

}
