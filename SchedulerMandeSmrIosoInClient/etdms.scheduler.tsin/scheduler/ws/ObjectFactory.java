
package scheduler.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the scheduler.ws package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IOException_QNAME = new QName("http://ws.scheduler/", "IOException");
    private final static QName _InvalidFormatException_QNAME =
        new QName("http://ws.scheduler/", "InvalidFormatException");
    private final static QName _ParseException_QNAME = new QName("http://ws.scheduler/", "ParseException");
    private final static QName _ParserConfigurationException_QNAME =
        new QName("http://ws.scheduler/", "ParserConfigurationException");
    private final static QName _SchedulerExportRequestWS_QNAME =
        new QName("http://ws.scheduler/", "schedulerExportRequestWS");
    private final static QName _SchedulerExportRequestWSResponse_QNAME =
        new QName("http://ws.scheduler/", "schedulerExportRequestWSResponse");
    private final static QName _SchedulerTsInWS_QNAME = new QName("http://ws.scheduler/", "schedulerTsInWS");
    private final static QName _SchedulerTsInWSResponse_QNAME =
        new QName("http://ws.scheduler/", "schedulerTsInWSResponse");
    private final static QName _SchedulerTsOutWS_QNAME = new QName("http://ws.scheduler/", "schedulerTsOutWS");
    private final static QName _SchedulerTsOutWSResponse_QNAME =
        new QName("http://ws.scheduler/", "schedulerTsOutWSResponse");
    private final static QName _SchedulerRemoveTempFileWS_QNAME =
        new QName("http://ws.scheduler/", "schedulerRemoveTempFileWS");
    private final static QName _SchedulerRemoveTempFileWSResponse_QNAME =
        new QName("http://ws.scheduler/", "schedulerRemoveTempFileWSResponse");
    private final static QName _SAXException_QNAME = new QName("http://ws.scheduler/", "SAXException");
    private final static QName _SQLException_QNAME = new QName("http://ws.scheduler/", "SQLException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: scheduler.ws
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IOException }
     *
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link InvalidFormatException }
     *
     */
    public InvalidFormatException createInvalidFormatException() {
        return new InvalidFormatException();
    }

    /**
     * Create an instance of {@link ParseException }
     *
     */
    public ParseException createParseException() {
        return new ParseException();
    }

    /**
     * Create an instance of {@link ParserConfigurationException }
     *
     */
    public ParserConfigurationException createParserConfigurationException() {
        return new ParserConfigurationException();
    }

    /**
     * Create an instance of {@link SchedulerExportRequestWS }
     *
     */
    public SchedulerExportRequestWS createSchedulerExportRequestWS() {
        return new SchedulerExportRequestWS();
    }

    /**
     * Create an instance of {@link SchedulerExportRequestWSResponse }
     *
     */
    public SchedulerExportRequestWSResponse createSchedulerExportRequestWSResponse() {
        return new SchedulerExportRequestWSResponse();
    }

    /**
     * Create an instance of {@link SchedulerTsInWS }
     *
     */
    public SchedulerTsInWS createSchedulerTsInWS() {
        return new SchedulerTsInWS();
    }

    /**
     * Create an instance of {@link SchedulerTsInWSResponse }
     *
     */
    public SchedulerTsInWSResponse createSchedulerTsInWSResponse() {
        return new SchedulerTsInWSResponse();
    }

    /**
     * Create an instance of {@link SchedulerTsOutWS }
     *
     */
    public SchedulerTsOutWS createSchedulerTsOutWS() {
        return new SchedulerTsOutWS();
    }

    /**
     * Create an instance of {@link SchedulerTsOutWSResponse }
     *
     */
    public SchedulerTsOutWSResponse createSchedulerTsOutWSResponse() {
        return new SchedulerTsOutWSResponse();
    }

    /**
     * Create an instance of {@link SchedulerRemoveTempFileWS }
     *
     */
    public SchedulerRemoveTempFileWS createSchedulerRemoveTempFileWS() {
        return new SchedulerRemoveTempFileWS();
    }

    /**
     * Create an instance of {@link SchedulerRemoveTempFileWSResponse }
     *
     */
    public SchedulerRemoveTempFileWSResponse createSchedulerRemoveTempFileWSResponse() {
        return new SchedulerRemoveTempFileWSResponse();
    }

    /**
     * Create an instance of {@link SAXException }
     *
     */
    public SAXException createSAXException() {
        return new SAXException();
    }

    /**
     * Create an instance of {@link SQLException }
     *
     */
    public SQLException createSQLException() {
        return new SQLException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidFormatException }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "InvalidFormatException")
    public JAXBElement<InvalidFormatException> createInvalidFormatException(InvalidFormatException value) {
        return new JAXBElement<InvalidFormatException>(_InvalidFormatException_QNAME, InvalidFormatException.class,
                                                       null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseException }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "ParseException")
    public JAXBElement<ParseException> createParseException(ParseException value) {
        return new JAXBElement<ParseException>(_ParseException_QNAME, ParseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParserConfigurationException }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "ParserConfigurationException")
    public JAXBElement<ParserConfigurationException> createParserConfigurationException(ParserConfigurationException value) {
        return new JAXBElement<ParserConfigurationException>(_ParserConfigurationException_QNAME,
                                                             ParserConfigurationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerExportRequestWS }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerExportRequestWS")
    public JAXBElement<SchedulerExportRequestWS> createSchedulerExportRequestWS(SchedulerExportRequestWS value) {
        return new JAXBElement<SchedulerExportRequestWS>(_SchedulerExportRequestWS_QNAME,
                                                         SchedulerExportRequestWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerExportRequestWSResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerExportRequestWSResponse")
    public JAXBElement<SchedulerExportRequestWSResponse> createSchedulerExportRequestWSResponse(SchedulerExportRequestWSResponse value) {
        return new JAXBElement<SchedulerExportRequestWSResponse>(_SchedulerExportRequestWSResponse_QNAME,
                                                                 SchedulerExportRequestWSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerTsInWS }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerTsInWS")
    public JAXBElement<SchedulerTsInWS> createSchedulerTsInWS(SchedulerTsInWS value) {
        return new JAXBElement<SchedulerTsInWS>(_SchedulerTsInWS_QNAME, SchedulerTsInWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerTsInWSResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerTsInWSResponse")
    public JAXBElement<SchedulerTsInWSResponse> createSchedulerTsInWSResponse(SchedulerTsInWSResponse value) {
        return new JAXBElement<SchedulerTsInWSResponse>(_SchedulerTsInWSResponse_QNAME, SchedulerTsInWSResponse.class,
                                                        null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerTsOutWS }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerTsOutWS")
    public JAXBElement<SchedulerTsOutWS> createSchedulerTsOutWS(SchedulerTsOutWS value) {
        return new JAXBElement<SchedulerTsOutWS>(_SchedulerTsOutWS_QNAME, SchedulerTsOutWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerTsOutWSResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerTsOutWSResponse")
    public JAXBElement<SchedulerTsOutWSResponse> createSchedulerTsOutWSResponse(SchedulerTsOutWSResponse value) {
        return new JAXBElement<SchedulerTsOutWSResponse>(_SchedulerTsOutWSResponse_QNAME,
                                                         SchedulerTsOutWSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerRemoveTempFileWS }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerRemoveTempFileWS")
    public JAXBElement<SchedulerRemoveTempFileWS> createSchedulerRemoveTempFileWS(SchedulerRemoveTempFileWS value) {
        return new JAXBElement<SchedulerRemoveTempFileWS>(_SchedulerRemoveTempFileWS_QNAME,
                                                          SchedulerRemoveTempFileWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SchedulerRemoveTempFileWSResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "schedulerRemoveTempFileWSResponse")
    public JAXBElement<SchedulerRemoveTempFileWSResponse> createSchedulerRemoveTempFileWSResponse(SchedulerRemoveTempFileWSResponse value) {
        return new JAXBElement<SchedulerRemoveTempFileWSResponse>(_SchedulerRemoveTempFileWSResponse_QNAME,
                                                                  SchedulerRemoveTempFileWSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SAXException }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "SAXException")
    public JAXBElement<SAXException> createSAXException(SAXException value) {
        return new JAXBElement<SAXException>(_SAXException_QNAME, SAXException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SQLException }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.scheduler/", name = "SQLException")
    public JAXBElement<SQLException> createSQLException(SQLException value) {
        return new JAXBElement<SQLException>(_SQLException_QNAME, SQLException.class, null, value);
    }

}
