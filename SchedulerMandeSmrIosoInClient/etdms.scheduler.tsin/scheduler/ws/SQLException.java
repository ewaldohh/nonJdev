
package scheduler.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SQLException complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SQLException"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SQLState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nextException" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SQLException", propOrder = { "sqlState", "errorCode", "message", "nextException" })
public class SQLException {

    @XmlElement(name = "SQLState")
    protected String sqlState;
    protected int errorCode;
    protected String message;
    @XmlSchemaType(name = "anySimpleType")
    protected Object nextException;

    /**
     * Gets the value of the sqlState property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSQLState() {
        return sqlState;
    }

    /**
     * Sets the value of the sqlState property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSQLState(String value) {
        this.sqlState = value;
    }

    /**
     * Gets the value of the errorCode property.
     *
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     *
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the message property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the nextException property.
     *
     * @return
     *     possible object is
     *     {@link Object }
     *
     */
    public Object getNextException() {
        return nextException;
    }

    /**
     * Sets the value of the nextException property.
     *
     * @param value
     *     allowed object is
     *     {@link Object }
     *
     */
    public void setNextException(Object value) {
        this.nextException = value;
    }

}
