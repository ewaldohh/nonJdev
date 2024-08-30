
package scheduler.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for schedulerTsInWS complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="schedulerTsInWS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="folder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="transmittalHistoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schedulerTsInWS", propOrder = { "folder", "transmittalHistoryId" })
public class SchedulerTsInWS {

    protected String folder;
    protected String transmittalHistoryId;

    /**
     * Gets the value of the folder property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFolder() {
        return folder;
    }

    /**
     * Sets the value of the folder property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFolder(String value) {
        this.folder = value;
    }

    /**
     * Gets the value of the transmittalHistoryId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransmittalHistoryId() {
        return transmittalHistoryId;
    }

    /**
     * Sets the value of the transmittalHistoryId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransmittalHistoryId(String value) {
        this.transmittalHistoryId = value;
    }

}
