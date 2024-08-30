/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webviewer;

/**
 *
 * @author DonnyAM
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import nanoxml.XMLElement;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.annotations.IAnnotationFactory;
import com.qoppa.pdf.annotations.RubberStamp;
import com.qoppa.pdf.source.InputStreamPDFSource;
import com.qoppa.pdfNotes.IPDFSaver;
import com.qoppa.pdfNotes.PDFNotesBean;
import com.qoppa.pdfNotes.settings.AnnotationTools;
import com.qoppa.pdfNotes.settings.ArrowTool;
import com.qoppa.pdfNotes.settings.CalloutTool;
import com.qoppa.pdfNotes.settings.CircleTool;
import com.qoppa.pdfNotes.settings.CloudTool;
import com.qoppa.pdfNotes.settings.LineTool;
import com.qoppa.pdfNotes.settings.PencilTool;
import com.qoppa.pdfNotes.settings.PolygonTool;
import com.qoppa.pdfNotes.settings.PolylineTool;
import com.qoppa.pdfNotes.settings.SquareTool;
import com.qoppa.pdfNotes.settings.StickyNoteTool;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import nanoxml.XMLElement;
import qoppa.webNotes.FileUploadPOST;
import qoppa.webNotes.MessageDialog;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Node;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;

public class PDFWebNotes extends JApplet implements ActionListener, IPDFSaver {

    private boolean Flags;
    private JPanel jpContentPane;
    private JPanel jpTitlePane;
    private JLabel jlTitle;
    private PDFNotesBean m_NotesBean = null;
    //file chooser
    JFileChooser fc;

    // Save URL
    private URL m_SaveURL;

    // Cookies
    private String mCookies;

    // Show success message
    private boolean mShowSucces = false;

    // Show progress
    private boolean m_ShowProgress = true;

    // By default use sockets to save, but we can use HTTP save
    private boolean mUseHTTPSave = false;

    // Flatten annotations on save
    private boolean mFlattenOnSave = false;

    public final static int SAVE_PDF = 0;
    public final static int SAVE_FDF = 1;
    public final static int SAVE_XFDF = 2;
    private int m_SaveFormat = SAVE_PDF;

    // Constants to determine true and false
    public final static String STRING_FALSE = "False";
    public final static String NUMBER_FALSE = "0";
    public final static String STRING_TRUE = "True";
    public final static String NUMBER_TRUE = "1";

    public final static String STAMP_TEXT = "Text";
    public final static String STAMP_TITLE = "Title";
    public final static String STAMP_IMAGE = "Image";
    public final static String STAMP_COLOR = "Color";
    public final static String STAMP_ROTATION = "Rotation";
    public final static String START_STAMP = "StartStamp";
    public final static String STAMP_STICKY = "Sticky";
    public final static String STAMP_SCALE = "Scale";
    public final static String ICON = "Icon";

    private static Hashtable ANNOT_CLASS_NAMES;

    static {
        ANNOT_CLASS_NAMES = new Hashtable();
        ANNOT_CLASS_NAMES.put("Circle", CircleTool.class);
        ANNOT_CLASS_NAMES.put("Arrow", ArrowTool.class);
        ANNOT_CLASS_NAMES.put("Line", LineTool.class);
        ANNOT_CLASS_NAMES.put("Square", SquareTool.class);
        ANNOT_CLASS_NAMES.put("Pencil", PencilTool.class);
        ANNOT_CLASS_NAMES.put("Polygon", PolygonTool.class);
        ANNOT_CLASS_NAMES.put("Polyline", PolylineTool.class);
        ANNOT_CLASS_NAMES.put("Cloud", CloudTool.class);

        ANNOT_CLASS_NAMES.put("StickyNote", StickyNoteTool.class);
        ANNOT_CLASS_NAMES.put("Callout", CalloutTool.class);
    }
    private final static String STARTSYNC = "StartAnnotate";
    private final static String WORKOFFLINE = "workoffline";
    private final static String GOBACK = "gobackonline";
    /**
     * parameter for send soap envelope
     *
     * public static String namespace = "tes"; public static String
     * soapEndpointUrl = "http://127.0.0.1:7101/qoppaweb"; public static String
     * operationMethodUrl = "http://127.0.0.1:7101/qoppaweb"; public static
     * String namespaceURI ="http://testPackage/"; public static String
     * operationName ="testHit";
     *
     *
     */

    public static String namespace;
    public static String soapEndpointUrl;
    public static String operationMethodUrl;
    public static String namespaceURI;
    public static String operationName;

    public static String namespaceGoback;
    public static String soapEndpointUrlGoback;
    public static String operationMethodUrlGoback;
    public static String namespaceURIGoback;
    public static String operationNameGoback;

    public static String namespaceWorkOff;
    public static String soapEndpointUrlWorkOff;
    public static String operationMethodUrlWorkOff;
    public static String namespaceURIWorkOff;
    public static String operationNameWorkOff;

    documentAttribute dAtt = new documentAttribute();

    /**
     * Initialize the applet.
     */
    public void init() {
        PDFNotesBean.setKey("75KT4LF82MC6A9MQ2IAKRS0TO2");
        m_NotesBean.setKey("75KT4LF82MC6A9MQ2IAKRS0TO2");
        dAtt.setIsOffline(getParameter("Password"));
        // Set the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        // Initialize content pane
        this.setContentPane(createContentPane());

        // Config using XML configuration file
        configXML();
        setKey("75KT4LF82MC6A9MQ2IAKRS0TO2");
    }

    public void start() {
        m_NotesBean.setKey("75KT4LF82MC6A9MQ2IAKRS0TO2");
        //calling javaScript for hide all loading page and show applet

        /*
		 don Maulana Start
		 "ie".equals(openMethod)
         */
//                    String cmd = String.format("cmd /c start iexplore \"%s\"", file.getAbsolutePath());
//                    Runtime.getRuntime().exec(cmd);
        namespace = getParameter("NameSpace");
//		soapEndpointUrl = getParameter("SoapEndpointUrl");
//		operationMethodUrl = getParameter("OperationMethodUrl");
        soapEndpointUrl = getParameter("ETDMSServer") + "/SWAApplication-Client-context-root/JWSXfdfPort";
        operationMethodUrl = getParameter("ETDMSServer") + "/SWAApplication-Client-context-root/JWSXfdfPort";
        namespaceURI = getParameter("NameSpaceURI");
        operationName = getParameter("OperationName");
//		isOffline = getParameter("isOffline");

//		if (getParameter("isOffline").equals("true")) {
//			isOffline = true;
//		} else {
//			isOffline = false;
//		}
        namespaceGoback = "off";
        soapEndpointUrlGoback = getParameter("ETDMSServer") + "/SWAApplication-Client-context-root/GobackOnlinePort";
        operationMethodUrlGoback = getParameter("ETDMSServer") + "/SWAApplication-Client-context-root/GobackOnlinePort";
        namespaceURIGoback = "http://offline.client/";
        operationNameGoback = "GobackOnlineService";

        namespaceWorkOff = "off";
        soapEndpointUrlWorkOff = getParameter("ETDMSServer") + "/SWAApplication-Client-context-root/WorkOfflinePort";
        operationMethodUrlWorkOff = getParameter("ETDMSServer") + "/SWAApplication-Client-context-root/WorkOfflinePort";
        namespaceURIWorkOff = "http://offline.client/";
        operationNameWorkOff = "WorkOfflineService";

//		namespace = "";
//		soapEndpointUrl = "http://localhost:7101/SWAApplication-Client-context-root/JWSXfdfPort";
//		operationMethodUrl = "http://localhost:7101/SWAApplication-Client-context-root/JWSXfdfPort";
//		namespaceURI = "http://client/";
//		operationName = "uplodFileWS";
//		dAtt.setFileName(getParameter("FileName"));
//		dAtt.setFileName("yc");
        dAtt.setAclType(getParameter("ACLType"));
        dAtt.setConsolidate(getParameter("Consolidate"));
        dAtt.setDid(getParameter("DID"));
        dAtt.setEtdmsServer(getParameter("ETDMSServer"));
        dAtt.setEtdmsTempDir(getParameter("ETDMSTempDir"));
        dAtt.setFlag("");
        dAtt.setGgi(getParameter("GGI"));
        dAtt.setPassWD(getParameter("Password"));
        dAtt.setReviewId(getParameter("UCMServer"));
        dAtt.setReviewOwner(getParameter("ReviewOwner"));
        dAtt.setUcmServer(getParameter("UCMServer"));
        dAtt.setdDocName(getParameter("Ddocname"));
        dAtt.setUserName(getParameter("UserName"));
        dAtt.setFilePath(getParameter("FileLocation"));
        dAtt.setFileXFDF(getParameter("FileXFDF"));
        dAtt.setFilePDF(getParameter("OpenURL"));
//        File pfFile = new File(dAtt.getFileXFDF().replace(".fdf", ".pdf"));
        dAtt.setSaveFilePDF(new File(dAtt.getFileXFDF().replace(".fdf", ".pdf")));
        dAtt.setFileName(dAtt.getSaveFilePDF().getName());
//		dAtt.setFileXFDF("D:/applet/yc.xfdf");

//		dAtt.setFilePDF(getParameter("FileLocation") + "/" + getParameter("FileName"));
        getPDFNotesBean().getToolbar().getjbOpen().setVisible(false);
        getPDFNotesBean().getToolbar().getjbPrint().setVisible(false);
        // Toolbar 
        getPDFNotesBean().getToolbar().setVisible(toBoolean(getParameter("ToolbarVisible"), true, true));
        // Save button
        // Override the default author
        String author = getParameter("Author");
        if (author != null && author.trim().length() > 0) {
            AnnotationTools.setDefaultAuthor(author);
        }
        // Cookies
        mCookies = getParameter("Cookies");
        if (mCookies != null && mCookies.trim().length() == 0) {
            mCookies = null;
        }

        // Save URL override
        try {
            URL overrideSave = createURL(getParameter("SaveURL"));
            if (overrideSave != null) {
                m_SaveURL = overrideSave;
            }
        } catch (MalformedURLException badURL) {
        }
        // Show success
        mShowSucces = toBoolean(getParameter("ShowSuccess"), false);
        // Set the scale
        int scale = toInteger(getParameter("Scale"));
        if (scale > 0) {
            getPDFNotesBean().setScale2D(scale);
        }
        // Check if there is a custom opener and install it
        installCustomOpener();
        // Load the documents in a separate thread so the
        // applet can finish starting and update the GUI
//		Thread loadThread = new Thread(new Runnable() {
//			public void run() {

        // Load a PDF document from a URL
        String urlString = getParameter("OpenURL");
//				String urlString = "file:///D:/applet/yc.pdf";
        if (urlString != null && urlString.trim().length() > 0) {
            // Load the PDF document
            privLoadPDF(urlString, mCookies);
            // Load FDF or XFDF data
            String mergeURL = getParameter("MergeFDF");
            if (mergeURL != null && mergeURL.trim().length() > 0) {
                mergeFDF(mergeURL);
            }
            mergeURL = getParameter("MergeXFDF");
            if (mergeURL != null && mergeURL.trim().length() > 0) {
                mergeXFDF(mergeURL);
            }
        } else {
            sucessMessage();
        }
//		JSObject win = (JSObject) JSObject.getWindow(this);
//		win.call("showApplet");

//			}
//		});
//		loadThread.start();
        /*
		 don Maulana END
         */
    }

    public void stop() {

        try {
            //        try {
//            int totalAnnots = 0;
//            //                SendComment(dAtt.getFilePath().replace(".pdf", ".xfdf"));
//            for (int pagePDF = 0; pagePDF < m_NotesBean.getDocument().getPageCount(); pagePDF++) {
//                totalAnnots = totalAnnots + m_NotesBean.getDocument().getIPage(pagePDF).getAnnotations().size();
////              System.out.println("size annpotation = " + bean.getDocument().getIPage(pagePDF).getAnnotations().size());  
//            }
//            if (totalAnnots > 0) {
                SendComment(dAtt.getFileXFDF());
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        getPDFNotesBean().save();
//            m_NotesBean.getMutableDocument().exportAnnotsAsFDF(dAtt.getFileXFDF(), null);
            System.out.println("file name PDF = ");
            System.out.println("file path taget document pdf = " + dAtt.getSaveFilePDF().getAbsolutePath());
            System.out.println("saving status = " + m_NotesBean.save(m_NotesBean, dAtt.getFileName(), dAtt.getSaveFilePDF()));
            System.out.println("exit");
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        }
//        getPDFNotesBean().save();
        // Clear any document that is loaded
//        getPDFNotesBean().clearDocument();
    }

    private void loadPDFDoc(String string) {
        //load pdf
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createContentPane() {
        if (jpContentPane == null) {
            jpContentPane = new JPanel();
            jpContentPane.setLayout(new BorderLayout());
            jpContentPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jpContentPane.add(getjpTitlePane(), BorderLayout.NORTH);
            jpContentPane.add(getPDFNotesBean(), BorderLayout.CENTER);
        }
        return jpContentPane;
    }

    private JPanel getjpTitlePane() {
        if (jpTitlePane == null) {
            jpTitlePane = new JPanel();
            jpTitlePane.setLayout(new BorderLayout());
            jpTitlePane.setBorder(new EmptyBorder(6, 0, 8, 0));
            jpTitlePane.add(getjlTitle(), BorderLayout.CENTER);
        }
        return jpTitlePane;
    }

    public JLabel getjlTitle() {
        if (jlTitle == null) {
            jlTitle = new JLabel("");
            jlTitle.setHorizontalAlignment(SwingConstants.CENTER);
            jlTitle.setFont(new Font("sansserif", Font.BOLD, 14));
        }
        return jlTitle;
    }

    public PDFNotesBean getPDFNotesBean() {
        if (m_NotesBean == null) {
            m_NotesBean = new PDFNotesBean();
//            try {
//                m_NotesBean.loadPDF("D:\\cv.pdf");
//            } catch (PDFException ex) {
//                Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
//            }
            System.out.println("isOffline = " + dAtt.getIsOffline());
            if (dAtt.getIsOffline().equalsIgnoreCase("true")) {
                ImageIcon boBack = createImageIcon("/images/goback.png",
                        "a pretty but meaningless splat");
                JButton ftCorrect2 = new JButton("|Go Back Online|");
                ftCorrect2.setForeground(Color.red);
                ftCorrect2.setSize(30, 20);
                ftCorrect2.setActionCommand(GOBACK);
                ftCorrect2.addActionListener(this);
                ftCorrect2.setFont(new Font("sansserif", Font.BOLD, 14));
                m_NotesBean.getToolbar().add(ftCorrect2, 35);
            }

            ImageIcon offlinework = createImageIcon("/images/offline.png",
                    "a pretty but meaningless splat");
            JButton ftCorrect1 = new JButton("|WORK OFFLINE|");
            ftCorrect1.setForeground(Color.red);
            ftCorrect1.setSize(30, 20);
            ftCorrect1.setActionCommand(WORKOFFLINE);
            ftCorrect1.addActionListener(this);
            ftCorrect1.setFont(new Font("sansserif", Font.BOLD, 12));
            if (dAtt.getIsOffline().equalsIgnoreCase("true")) {
                m_NotesBean.getToolbar().add(ftCorrect1, 36);
            } else {
                m_NotesBean.getToolbar().add(ftCorrect1, 35);
            }

            ImageIcon sendReceive = createImageIcon("/images/synch.png",
                    "a pretty but meaningless splat");

            JButton ftCorrect = new JButton(" |SEND AND RECEIVE COMMENT| ");
            ftCorrect.setForeground(Color.red);
            ftCorrect.setFont(new Font("sansserif", Font.BOLD, 12));
            ftCorrect.setSize(30, 20);
            ftCorrect.setActionCommand(STARTSYNC);
            ftCorrect.addActionListener(this);
            if (dAtt.getIsOffline().equalsIgnoreCase("true")) {
                m_NotesBean.getToolbar().add(ftCorrect, 37);
            } else {
                m_NotesBean.getToolbar().add(ftCorrect, 36);
            }

            m_NotesBean.setName("PDFWebNotes");
            m_NotesBean.setPDFSaver(this);
        }
        return m_NotesBean;
    }

    protected ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private boolean toBoolean(String str, boolean emptyValue, boolean defaultValue) {
        // empty value
        if (str == null || str.trim().length() == 0) {
            return emptyValue;
        }

        // Check for true
        if (str.equalsIgnoreCase(STRING_TRUE) || str.equalsIgnoreCase(NUMBER_TRUE)) {
            return true;
        } else if (str.equalsIgnoreCase(STRING_FALSE) || str.equalsIgnoreCase(NUMBER_FALSE)) {
            return false;
        } else {
            return defaultValue;
        }
    }

    private int toInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Loads a PDF document into the PDFNotesBean. This method loads the PDF
     * document inside a privileged action so that it can be called from
     * Javascript. Otherwise, the method would throw a security exception
     * because Javascript does not get much permissions to start with.
     *
     * @param pdfURLString The URL to the PDF document.
     *
     * @return The number of pages in the document
     */
    public int loadPDF(final String pdfURLString) {
        // We need to enclose this in a privileged action so that it
        // can execute properly when called from Javascript
        Object rc = AccessController.doPrivileged(new java.security.PrivilegedAction() {
            public Object run() {
                int pageCount = privLoadPDF(pdfURLString, null);
                return new Integer(pageCount);
            }
        });

        if (rc != null && rc instanceof Integer) {
            return ((Integer) rc).intValue();
        }
        return 0;
    }

    private void mergeFDF(String fdfURLString) {
        InputStream fdfStream = getURLInputStream(fdfURLString);
        if (fdfStream != null) {
            try {
                getPDFNotesBean().getMutableDocument().importAnnotsFromFDF(fdfStream);
            } catch (PDFException pdfE) {
//                JOptionPane.showMessageDialog(this, pdfE.getMessage());
            } finally {
                silentClose(fdfStream);
            }
        }
    }

    private void mergeXFDF(String xfdfURLString) {
        InputStream fdfStream = getURLInputStream(xfdfURLString);
        if (fdfStream != null) {
            try {
                getPDFNotesBean().getMutableDocument().importAnnotsFromXFDF(fdfStream);
            } catch (PDFException pdfE) {
//                JOptionPane.showMessageDialog(this, pdfE.getMessage());
            } finally {
                silentClose(fdfStream);
            }
        }
    }

    private void silentClose(InputStream inStream) {
        if (inStream != null) {
            try {
                inStream.close();
            } catch (Throwable t) {
            }
        }
    }

    private InputStream getURLInputStream(String urlString) {
        try {
            if (urlString != null && urlString.trim().length() > 0) {
                // Form URL object
                URL url = createURL(urlString);

                // Load the XML Tree
                URLConnection urlConnect = url.openConnection();
                urlConnect.setUseCaches(false);
                return urlConnect.getInputStream();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    /**
     * Internal implementation of the load PDF method. This method is safe to
     * call from the applet context, but not from Javascript. If calling from
     * Javascript, then loadPDF() should be used instead.
     *
     * @param pdfURLString The URL to the PDF document.
     *
     * @return The number of pages in the document
     */
    private int privLoadPDF(String pdfURLString, String cookies) {
        try {
            // Form URL object
            URL pdfURL = createURL(pdfURLString);

            // Get the URLConnection
            URLConnection urlConnect = pdfURL.openConnection();
            urlConnect.setUseCaches(false);

            // Set cookies if not null
            if (cookies != null) {
                urlConnect.setRequestProperty("Cookie", cookies);
            }

            // URL Input stream
            InputStream inputStream = urlConnect.getInputStream();

            // Set up the ProgressMonitorInputStream
            if (m_ShowProgress) {
                ProgressMonitorInputStream pmInputStream = new ProgressMonitorInputStream(getPDFNotesBean(), "Loading document...", inputStream);
                pmInputStream.getProgressMonitor().setMaximum(urlConnect.getContentLength());
                inputStream = pmInputStream;
            }

            // Get the document name
            StringBuffer docName = new StringBuffer(pdfURL.getFile());
            int idx = docName.lastIndexOf("/");
            if (idx >= 0 && idx + 1 < docName.length()) {
                docName.delete(0, idx + 1);
            }

            // Load the URL as an input stream
            InputStreamPDFSource source = new InputStreamPDFSource(inputStream, docName.toString());
            getPDFNotesBean().loadPDF(source);

            // Optional call to allow keystroke navigation (up/down arrows, page up/down, ...)
            getPDFNotesBean().getRootPane().getContentPane().requestFocus();

            // Return the page count
            return getPDFNotesBean().getPageCount();
        } catch (MalformedURLException mURL) {
            displayError("Invalid PDF URL: " + mURL.getMessage());
        } catch (PDFException pdfE) {
            displayError(pdfE.getMessage());
        } catch (Throwable t) {
            displayError("Error loading PDF: " + t.getMessage());
        }
        return 0;
    }

    private void displayError(String errorMsg) {
//        JOptionPane.showMessageDialog(this, errorMsg);
        System.out.println(errorMsg);
    }

    private void installCustomOpener() {
        // Get parameters in HTML
        String docList = getParameter("DocumentList");

        // check for XML based opener
        if (docList != null && docList.trim().length() > 0) {
            try {
                // URL to the document list
                URL docListURL = new URL(getCodeBase(), docList);

                // URL to the default save location
                URL defaultSaveURL = null;
                String defaultSave = getParameter("SaveURL");
                if (defaultSave != null && defaultSave.trim().length() > 0) {
                    defaultSaveURL = new URL(getCodeBase(), defaultSave);
                }

                // Create server files object, this object handles opening and saving files
//                ServerFiles sf = new ServerFiles (getCodeBase(), docListURL, defaultSaveURL);
//                getPDFNotesBean().setPDFOpener(sf);
//                getPDFNotesBean().setPDFSaver(sf);
            } catch (MalformedURLException badURL) {
                displayError("Invalid document list URL: " + docList);
            }
        }
    }

    public void setKey(String key) {
        if (key != null && key.length() > 0) {
            key = key.trim();
            boolean valid = PDFNotesBean.setKey(key);
            if (valid == false) {
                valid = PDFNotesBean.setAppletKey(key, this);
            }
            System.out.println("Invalid Key: " + key);
            // Echo invalid key
            if (valid == false) {
                System.out.println("Invalid Key: " + key);
                System.out.println("Host Name: " + getDocumentBase().getHost().toLowerCase());
            } else {
//                getjlTitle().setText("Qoppa Software - " + PDFNotesBean.getVersion());
            }
        }
    }

    private void configXML() {
        // Get the jPDFNotes component
        PDFNotesBean notesBean = getPDFNotesBean();

        // Read stamps from XML file
        String configURLParam = getParameter("Config");
        if (configURLParam != null && configURLParam.trim().length() != 0) {
            try {
                // Form URL object
                URL configURL = createURL(configURLParam);

                // Load the XML Tree
                URLConnection urlConnect = configURL.openConnection();
                urlConnect.setUseCaches(false);
                InputStream xmlStream = urlConnect.getInputStream();

                XMLElement configXML = new XMLElement();
                configXML.parseFromInputStream(xmlStream);
                xmlStream.close();

                for (int count = 0; count < configXML.countChildren(); ++count) {
                    XMLElement child = configXML.getChild(count);
                    if (child.getName().equalsIgnoreCase("Toolbar")) {
                        configToolbar(child, notesBean);
                    } else if (child.getName().equalsIgnoreCase("Save")) {
                        setSaveOptions(child);
                    } else if (child.getName().equalsIgnoreCase("Key")) {
                        setKey(child.getContent());

                    } else if (child.getName().equalsIgnoreCase("Options")) {
                        setGeneralOptions(child);
                    }
                }
            } catch (Throwable t) {
                System.err.println("Error loading stamps configuration: " + configURLParam);
                t.printStackTrace();
            }
        }
    }

    private void setGeneralOptions(XMLElement options) {
        for (int count = 0; count < options.countChildren(); ++count) {
            XMLElement child = options.getChild(count);
            if ("HideTitle".equalsIgnoreCase(child.getName())) {
                getjpTitlePane().setVisible(false);
            } else if ("Title".equalsIgnoreCase(child.getName())) {
                getjlTitle().setText(child.getContent());
            } else if ("HideProgress".equalsIgnoreCase(child.getName())) {
                m_ShowProgress = false;
            } else if ("UseHTTPSave".equalsIgnoreCase(child.getName())) {
                mUseHTTPSave = true;
            }
        }
    }

    private void setSaveOptions(XMLElement saveXML) throws MalformedURLException {
        // Save URL
        m_SaveURL = createURL(saveXML.getStringAttribute("SaveURL"));

        // Save Format
        String saveFormat = saveXML.getStringAttribute("SaveFormat");
        if (saveFormat != null && saveFormat.trim().length() > 0) {
            if ("xfdf".equalsIgnoreCase(saveFormat)) {
                m_SaveFormat = SAVE_XFDF;
            }
            if ("fdf".equalsIgnoreCase(saveFormat)) {
                m_SaveFormat = SAVE_FDF;
            }
        }

        // Flatten Annotations
        mFlattenOnSave = toBoolean(saveXML.getStringAttribute("FlattenAnnotations"), false);
    }

    private void configToolbar(XMLElement toolbarXML, PDFNotesBean notesBean) throws IOException {

        // Parse the XML tree
        for (int count = 0; count < toolbarXML.countChildren(); ++count) {
            XMLElement child = toolbarXML.getChild(count);
            if (child.getName().equalsIgnoreCase("AddTextStamp")) {
                String iconURL = child.getStringAttribute(ICON);
                String text = child.getStringAttribute(STAMP_TEXT);

                if (iconURL != null && text != null) {
                    ImageIcon icon = new ImageIcon(createURL(iconURL));

                    // Create custom buttons
                    JButton newButton = createToolbarButton(icon);
                    newButton.putClientProperty(STAMP_TEXT, text);
                    newButton.putClientProperty(STAMP_COLOR, child.getStringAttribute(STAMP_COLOR));
                    newButton.putClientProperty(STAMP_STICKY, new Boolean(toBoolean(child.getStringAttribute("Sticky"), false)));
                    newButton.putClientProperty(STAMP_SCALE, new Float(child.getDoubleAttribute(STAMP_SCALE, 100)));
                    newButton.putClientProperty(STAMP_ROTATION, new Integer(child.getIntAttribute(STAMP_ROTATION, 0)));
                    newButton.setActionCommand(START_STAMP);
                    newButton.addActionListener(this);
                    m_NotesBean.getAnnotToolbar().add(newButton);
                }
            } else if (child.getName().equalsIgnoreCase("AddImageStamp")) {
                String iconURL = child.getStringAttribute(ICON);
                String imageURL = child.getStringAttribute(STAMP_IMAGE);

                if (iconURL != null && imageURL != null) {
                    try {
                        Image stampImage = ImageIO.read(createURL(imageURL));
                        ImageIcon icon = new ImageIcon(createURL(iconURL));

                        // Create custom buttons
                        JButton newButton = createToolbarButton(icon);
                        newButton.putClientProperty(STAMP_IMAGE, stampImage);
                        newButton.putClientProperty(STAMP_STICKY, new Boolean(toBoolean(child.getStringAttribute("Sticky"), false)));
                        newButton.putClientProperty(STAMP_SCALE, new Float(child.getDoubleAttribute(STAMP_SCALE, 100)));
                        newButton.putClientProperty(STAMP_ROTATION, new Integer(child.getIntAttribute(STAMP_ROTATION, 0)));
                        newButton.setActionCommand(START_STAMP);
                        newButton.addActionListener(this);
                        m_NotesBean.getAnnotToolbar().add(newButton);
                    } catch (Throwable t) {
                        System.err.println("Error adding image stamp: " + child.toString());
                    }
                }
            } else if (child.getName().equalsIgnoreCase("AddSeparator")) {
                notesBean.getAnnotToolbar().addSeparator();
            } else if (child.getName().equalsIgnoreCase("RubberStamps")) {
                configRubberStamps(child, notesBean);
            } else if (child.getName().equalsIgnoreCase("RemoveButton")) {
                String methName = child.getStringAttribute("MethodName");
//                if (methName != null && methName.length() > 0) {
//                    boolean found = removeToolbarButton(methName, m_NotesBean.getEditToolbar());
//                    if (found == false) {
//                        found = removeToolbarButton(methName, m_NotesBean.getSelectToolbar());
//                    }
//                    if (found == false) {
//                        found = removeToolbarButton(methName, m_NotesBean.getAnnotToolbar());
//                    }
//
//                    if (found == false) {
//                        System.err.println("Did not find toolbar button to remove: " + methName);
//                    }
//                }
            } else if (child.getName().equalsIgnoreCase("HideToolbar")) {
                String toolbarName = child.getStringAttribute("Name");
                if (toolbarName != null) {
                    toolbarName = toolbarName.trim();
                    if ("navigation".equalsIgnoreCase(toolbarName)) {
                        m_NotesBean.getToolbar().setVisible(false);
                    } else if ("annotation".equalsIgnoreCase(toolbarName)) {
                        m_NotesBean.getAnnotToolbar().setVisible(false);
                    } else if ("selection".equalsIgnoreCase(toolbarName)) {
                        m_NotesBean.getSelectToolbar().setVisible(false);
                    }
                }
            } else if (child.getName().equalsIgnoreCase("AnnotationDefaults")) {
                configAnnotDefaults(child);
            }
        }
    }

    private void configAnnotDefaults(XMLElement parent) {
        for (int count = 0; count < parent.countChildren(); ++count) {
            XMLElement child = parent.getChild(count);
            if (child.getName().equalsIgnoreCase("BorderColor")) {
                setAnnotColor(child, "setDefaultBorderColor");
            } else if (child.getName().equalsIgnoreCase("FillColor")) {
                setAnnotColor(child, "setDefaultFillColor");
            }
        }
    }

    private void setAnnotColor(XMLElement colorElement, String methodName) {
        String annotName = colorElement.getStringAttribute("AnnotationName");
        String color = colorElement.getStringAttribute("Color");
        if (annotName != null && color != null) {
            Class annotClass = (Class) ANNOT_CLASS_NAMES.get(annotName);
            if (annotClass != null) {
                try {
                    Color c = new Color(Integer.parseInt(color, 16));
                    Method m = annotClass.getMethod(methodName, new Class[]{Color.class});
                    m.invoke(null, new Object[]{c});
                } catch (NumberFormatException nfe) {
                    System.err.println("Error parsing color: " + color);
                } catch (NoSuchMethodException nsme) {
                    System.err.println("Annotation does not support border color.");
                } catch (InvocationTargetException ite) {
                    System.err.println("Error setting color: " + ite.getTargetException().getMessage());
                } catch (IllegalAccessException iae) {
                    System.err.println("Error setting color: " + iae.getMessage());
                }
            } else {
                System.err.println("Invalid annotation type: " + annotName);
            }
        } else {
            System.err.println("Missing annotation name or color in setting border color.");
        }
    }

    private void configRubberStamps(XMLElement stampsXML, PDFNotesBean notesBean) throws IOException {
        // Add separator between standard stamps and custom stamps
        notesBean.getAnnotToolbar().getJmAddStamps().addSeparator();

        // Parse the XML tree
        for (int count = 0; count < stampsXML.countChildren(); ++count) {
            XMLElement child = stampsXML.getChild(count);
            if (child.getName().equalsIgnoreCase("AddTextStamp")) {
                String title = child.getStringAttribute(STAMP_TITLE);
                String text = child.getStringAttribute(STAMP_TEXT);

                if (title != null && text != null) {
                    // add stamp
                    JMenuItem mi = new JMenuItem(title);
                    mi.putClientProperty(STAMP_TEXT, text);
                    mi.putClientProperty(STAMP_COLOR, child.getStringAttribute(STAMP_COLOR));
                    mi.putClientProperty(STAMP_STICKY, new Boolean(toBoolean(child.getStringAttribute("Sticky"), false)));
                    mi.putClientProperty(STAMP_SCALE, new Float(child.getDoubleAttribute(STAMP_SCALE, 100)));
                    mi.putClientProperty(STAMP_ROTATION, new Integer(child.getIntAttribute(STAMP_ROTATION, 0)));
                    mi.setActionCommand(START_STAMP);
                    mi.addActionListener(this);
                    notesBean.getAnnotToolbar().getJmAddStamps().add(mi);
                }
            } else if (child.getName().equalsIgnoreCase("AddImageStamp")) {
                String title = child.getStringAttribute(STAMP_TITLE);
                String imageURL = child.getStringAttribute(STAMP_IMAGE);

                if (title != null && imageURL != null) {
                    // add the stamp
                    JMenuItem mi = new JMenuItem(title);
                    Image image = ImageIO.read(createURL(imageURL));
                    mi.putClientProperty(STAMP_IMAGE, image);
                    mi.putClientProperty(STAMP_STICKY, new Boolean(toBoolean(child.getStringAttribute("Sticky"), false)));
                    mi.putClientProperty(STAMP_SCALE, new Float(child.getDoubleAttribute(STAMP_SCALE, 100)));
                    mi.putClientProperty(STAMP_ROTATION, new Integer(child.getIntAttribute(STAMP_ROTATION, 0)));
                    mi.setActionCommand(START_STAMP);
                    mi.addActionListener(this);
                    notesBean.getAnnotToolbar().getJmAddStamps().add(mi);
                }
            } else if (child.getName().equalsIgnoreCase("RemoveStamp")) {
                String title = child.getStringAttribute(STAMP_TITLE);
                if (title != null) {
                    removeRubberStamp(title);
                }
            } else if (child.getName().equalsIgnoreCase("AddSeparator")) {
                notesBean.getAnnotToolbar().getJmAddStamps().addSeparator();
            }
        }
    }

    private void removeRubberStamp(String title) {
        PDFNotesBean notesBean = getPDFNotesBean();
        int stampCount = notesBean.getAnnotToolbar().getJmAddStamps().getComponentCount();
        for (int count = 0; count < stampCount; ++count) {
            Component c = notesBean.getAnnotToolbar().getJmAddStamps().getComponent(count);
            if (c instanceof JMenuItem && title.equalsIgnoreCase(c.getName())) {
                notesBean.getAnnotToolbar().getJmAddStamps().remove(count);
                return;
            }
        }

        System.err.println("Remove Rubber Stamp: Stamp not found: " + title);
    }

    private URL createURL(String urlString) throws MalformedURLException {
        if (urlString == null || urlString.trim().length() == 0) {
            return null;
        }

        if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
            return new URL(urlString);
        } else {
            return new URL(getDocumentBase(), urlString);
        }
    }

    private boolean toBoolean(String str, boolean defValue) {
        if (str == null) {
            return defValue;
        } else if (str.equalsIgnoreCase(STRING_TRUE) || str.equalsIgnoreCase(NUMBER_TRUE)) {
            return true;
        }

        return false;
    }

    private JButton createToolbarButton(Icon icon) {
        JButton newButton = new JButton(icon);

        Dimension buttonSize = new Dimension(38, 30);
        newButton.setPreferredSize(buttonSize);
        newButton.setMinimumSize(buttonSize);
        newButton.setMaximumSize(buttonSize);

        newButton.setRolloverEnabled(true);
        newButton.setFocusPainted(false);
        newButton.setRequestFocusEnabled(false);
        newButton.setFocusable(false);
        newButton.setBorderPainted(false);

        // we need to paint the border on Mac & Linux when button is rolled over
        newButton.getModel().addChangeListener(new ButtonRollover(newButton));

        return newButton;
    }

//	@Override
//	public boolean save(PDFNotesBean pdfnb, String docName, File pdfFile) {
//		System.out.println("offline loc "+ pdfFile.toString());
//		dAtt.setOfflineLoc(pdfFile.toString());
//		if (m_SaveURL == null) {
//			return pdfnb.save(pdfnb, docName, pdfFile);
//		} else {
//			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//			try {
//				// Flatten annotations on save
//				if (mFlattenOnSave) {
//					getPDFNotesBean().getMutableDocument().flattenAnnotations();
//				}
//
//				// Get any additional variables
//				Hashtable addlVariables = setAddlVariables();
//
//				// Get the contents to save, this can be PDF, FDF or XFDF
//				byte[] msgContent = getMessageContent();
//				docName = getDocName(docName);
//
//				// Upload the file
//				FileUploadPOST upload = new FileUploadPOST();
//				int status;
//				if (mUseHTTPSave) {
//					status = upload.uploadHTTP(msgContent, docName, m_SaveURL, mCookies, addlVariables);
//				} else {
//					status = upload.upload(msgContent, docName, m_SaveURL, mCookies, addlVariables);
//				}
//
//				// Get and handle response
//				String response = upload.getResponse();
//				if (response != null && response.trim().length() > 0) {
//					if (mShowSucces) {
//						MessageDialog.showMessage(this, response);
//					}
//				} else {
//					if (status >= 300) {
//						JOptionPane.showMessageDialog(this, "Error saving the PDF document to the server: " + status);
//					} else {
//						if (mShowSucces) {
//							JOptionPane.showMessageDialog(this, "The PDF document has been saved to the server.");
//						}
//					}
//				}
//
//				return status < 300;
//			} catch (Throwable t) {
//				t.printStackTrace();
//				JOptionPane.showMessageDialog(this, "Error saving document: " + t.getMessage());
//				return false;
//			} finally {
//				// Restore normal cursor
//				setCursor(Cursor.getDefaultCursor());
//			}
//		}
//	}
    @Override
    public boolean save(PDFNotesBean pdfnb, String docName, File pdfFile) {
        try {
            dAtt.setOfflineLoc(pdfFile.toString());
        } catch (Exception n) {
        }
        //            getPDFNotesBean().saveDocument(pdfFile.toString());
        System.out.println("file name PDF = ");
        System.out.println("file path taget document pdf = " + dAtt.getSaveFilePDF().getAbsolutePath());
        System.out.println("saving status = " + m_NotesBean.save(m_NotesBean, dAtt.getFileName(), dAtt.getSaveFilePDF()));
        return true;
    }

    private static class ButtonRollover implements ChangeListener {

        JButton m_Button;

        public ButtonRollover(JButton button) {
            m_Button = button;
        }

        public void stateChanged(ChangeEvent e) {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isSelected() || model.isRollover()) {
                m_Button.setBorderPainted(true);
            } else {
                m_Button.setBorderPainted(false);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == START_STAMP) {
            if (m_NotesBean.getDocument() != null) {
                IAnnotationFactory annotFactory = m_NotesBean.getMutableDocument().getAnnotationFactory();

                // Source menu item
                JComponent source = (JComponent) e.getSource();

                // Get the color, default to blue
                Color c = Color.blue;
                String colorStr = (String) source.getClientProperty(STAMP_COLOR);
                if (colorStr != null) {
                    try {
                        c = new Color(Integer.parseInt(colorStr, 16));
                    } catch (Throwable t) {
                    }
                }

                // Stamp text
                String stampText = (String) source.getClientProperty(STAMP_TEXT);
                boolean isSticky = ((Boolean) source.getClientProperty(STAMP_STICKY)).booleanValue();
                if (stampText != null) {
                    stampText = stampText.replaceAll("\\$user", getUserName());
                    SimpleDateFormat format = new SimpleDateFormat();
                    stampText = stampText.replaceAll("\\$datetime", format.format(new Date()));

                    // Create the rubber stamp
                    double scale = 100;
                    Float scaleProp = (Float) source.getClientProperty(STAMP_SCALE);
                    if (scaleProp != null) {
                        scale = scaleProp.doubleValue();
                    }

                    // Create the rubber stamp
                    RubberStamp stamp = annotFactory.createRubberStamp(stampText, c, scale);

                    // Rotation
                    Integer rotate = (Integer) source.getClientProperty(STAMP_ROTATION);
                    if (rotate != null && rotate.intValue() != 0) {
                        stamp.setRotation(rotate.intValue());
                    }

                    // Start editing
                    getPDFNotesBean().startEdit(stamp, false, isSticky);
                } else {
                    Image stampImage = (Image) source.getClientProperty(STAMP_IMAGE);
                    if (stampImage != null) {
                        // Create the stamp
//                        RubberStamp stamp = annotFactory.createRubberStamp(stampImage);
                        RubberStamp stamp = annotFactory.createRubberStamp(stampImage, null);

                        // Scale
                        Float scale = (Float) source.getClientProperty(STAMP_SCALE);
                        if (scale != null && scale.floatValue() != 100) {
                            Rectangle2D rect = stamp.getRectangle();
                            rect.setRect(rect.getX(), rect.getY(), rect.getWidth() * (scale.floatValue() / 100f), rect.getHeight() * (scale.floatValue() / 100f));
                            stamp.setRectangle(rect);
                        }

                        // Rotation
                        Integer rotate = (Integer) source.getClientProperty(STAMP_ROTATION);
                        if (rotate != null && rotate.intValue() != 0) {
                            stamp.setRotation(rotate.intValue());
                        }
                        // Start editing
                        getPDFNotesBean().startEdit(stamp, false, isSticky);
                    }
                }
            }
        }
        if (e.getActionCommand() == STARTSYNC) {
            try {

                SendComment(dAtt.getFileXFDF());
//				if (isOffline == true) {
//					WorkOfflineSync(dAtt.getUserName(), dAtt.getReviewId());
//				} else {
//					GoBackSync(dAtt.getUserName(), dAtt.getReviewId());
//				}
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //donny start here

        //donny end here
        if (e.getActionCommand() == WORKOFFLINE) {
            System.out.println("start workoffline");
            dAtt.setIsOffline("true");
            workOffline("dest");

            WorkOfflineSync(dAtt.getUserName(), dAtt.getReviewId());
            System.out.println("workoffline done");
        }
        if (e.getActionCommand() == GOBACK) {
            System.out.println("start go back online");
            boolean canGoback = false;
//			AnnotationChooser("D:\\testexp.xfdf");
            try {
                //Create a file chooser
                fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println(file.getAbsolutePath());
//                    canGoback = isCanGoback(file.getAbsolutePath());
//                    if (canGoback == true) {
                    PDFNotesBean bean;
                    try {
                        if (file.getAbsolutePath().contains(".pdf") == true) {
                            bean = new PDFNotesBean();
                            bean.loadPDF(file.getAbsolutePath());
//                                bean.getMutableDocument().exportAnnotsAsXFDF(file.getAbsolutePath().replace(".pdf", "-Mande_ETDMS.xfdf"));
                            bean.getMutableDocument().exportAnnotsAsXFDF(file.getAbsolutePath().replace(".pdf", "-Mande_ETDMS.xfdf"), null);
                            bean.close(true);
                            getNotReadOnlyAnnots(file.getAbsolutePath().replace(".pdf", "-Mande_ETDMS.xfdf"));
                            mergeXFDFOffline(file.getAbsolutePath().replace(".pdf", "-Mande_ETDMS.xfdf"));
                            dAtt.setIsOffline("true");
                            GoBackSync(dAtt.getUserName(), dAtt.getReviewId());
                        } else if (file.getAbsolutePath().contains(".xfdf") == true) {
                            getNotReadOnlyAnnots(file.getAbsolutePath());
                            mergeXFDFOffline(file.getAbsolutePath());
                            dAtt.setIsOffline("true");
                            GoBackSync(dAtt.getUserName(), dAtt.getReviewId());
                        } else if (file.getAbsolutePath().contains(".xfdf") == false || file.getAbsolutePath().contains(".pdf") == false) {
                            JOptionPane.showMessageDialog(rootPane, "Invalid File format");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(rootPane, "Failed to append Comment");
                    }
//                    } else {
//                        JOptionPane.showMessageDialog(rootPane, "You are not Allowed to go back online using this file");
//                    }

                } else {
//					log.append("Open command cancelled by user." + newline);
                    System.out.println("Open command cancelled by user.");
                }
//				log.setCaretPosition(log.getDocument().getLength());
                System.out.println("XFDF LOADED...");

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("go back online got error");
            }

        }
    }

    private void mergeXFDFOffline(String xfdfURLString) {
//		InputStream fdfStream = getURLInputStream(xfdfURLString);
        try {
            getPDFNotesBean().getMutableDocument().importAnnotsFromXFDF(xfdfURLString);
        } catch (PDFException pdfE) {
            JOptionPane.showMessageDialog(this, pdfE.getMessage());
        }
    }

    private void sucessMessage() {
        final ImageIcon icon;
        try {
            icon = new ImageIcon(new URL(getParameter("ETDMSServer") + "/ETDMS/Images/icon/legIcon20.png"));
            JOptionPane.showMessageDialog(rootPane, "Your Comments are Successfully Uploaded.", "Message", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Your Comments are Successfully Uploaded.");
        }
//        JOptionPane.showMessageDialog(rootPane, "Your Comments are Successfully Uploaded.");

    }
//getParameter("ETDMSServer")

    private void errorMessage(Object message) {
//        JOptionPane.showMessageDialog(rootPane, "Send & Receive Comment Failed\n"
//                + "Please do not close the commenting page to avoid losing comments\n"
//                + "Call to MANDe Support (4040).\n"
//                + "\n"
//                + "ERROR MESSAGE : " + message);
        final ImageIcon icon;
        try {
            icon = new ImageIcon(new URL(getParameter("ETDMSServer") + "/ETDMS/Images/icon/legIcon20.png"));
            JOptionPane.showMessageDialog(null, "Send & Receive Comment Failed\n"
                    + "Please do not close the commenting page to avoid losing comments\n"
                    + "Call to MANDe Support (4040).\n"
                    + "\n"
                    + "ERROR MESSAGE : " + message, "Message", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Send & Receive Comment Failed\n"
                    + "Please do not close the commenting page to avoid losing comments\n"
                    + "Call to MANDe Support (4040).\n"
                    + "\n"
                    + "ERROR MESSAGE : " + message);
        }

//		JOptionPane.ERROR_MESSAGE();
    }

    private void errorNullMessage(Object message) {
        final ImageIcon icon;
        try {
            //        JOptionPane.showMessageDialog(rootPane, "Cannot insert comment to this document because of the following reason :\n"
//                + "1. There is other comment from main document.\n"
//                + "2. There is error in pdf file because at least one signature has problems.\n"
//                + "\n"
//                + "Please do create modification request and replace the document with flattened document.\n"
//                + "\n"
//                + "If still have any issues, please contact Call to MANDe Support (4040)."
//                //                + "Call to MANDe Support (4040).\n"
//                + "\n"
//                + "ERROR MESSAGE : " + message);

            icon = new ImageIcon(new URL(getParameter("ETDMSServer") + "/ETDMS/Images/icon/legIcon20.png"));
            JOptionPane.showMessageDialog(rootPane, "Cannot insert comment to this document because of the following reason :\n"
                    + "1. There is other comment from main document.\n"
                    + "2. There is error in pdf file because at least one signature has problems.\n"
                    + "\n"
                    + "Please do create modification request and replace the document with flattened document.\n"
                    + "\n"
                    + "If still have any issues, please contact Call to MANDe Support (4040)."
                    //                + "Call to MANDe Support (4040).\n"
                    + "\n"
                    + "ERROR MESSAGE : " + message, "Message", JOptionPane.INFORMATION_MESSAGE, icon);
//		JOptionPane.ERROR_MESSAGE();
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Cannot insert comment to this document because of the following reason :\n"
                    + "1. There is other comment from main document.\n"
                    + "2. There is error in pdf file because at least one signature has problems.\n"
                    + "\n"
                    + "Please do create modification request and replace the document with flattened document.\n"
                    + "\n"
                    + "If still have any issues, please contact Call to MANDe Support (4040)."
                    //                + "Call to MANDe Support (4040).\n"
                    + "\n"
                    + "ERROR MESSAGE : " + message);
        }
    }

    private void customMessage(String Messages) {
        JOptionPane.showMessageDialog(rootPane, Messages);
    }

    private void workOffline(String fileDest) {
//        System.out.println();
        PDFNotesBean notesBean = null;
        Process commProcess = null;
        try {
            notesBean = getPDFNotesBean();
            m_NotesBean.saveAs();
//			notesBean.saveAs();
            if (dAtt.getOfflineLoc() != null) {
//				commProcess = Runtime.getRuntime().exec("\"C:\\Program Files\\PDFStudio2018\\pdfstudio2018.exe\""
//						+ " \"" + dAtt.getOfflineLoc() + "\"");

//				commProcess = Runtime.getRuntime().exec("\"C:\\Program Files (x86)\\Adobe\\Acrobat 2017\\Acrobat\\Acrobat.exe \"" + "\"" + dAtt.getOfflineLoc() + "\"");
                customMessage("your Document has been saved");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            if (notesBean != null);
            {
                notesBean.close(true);
            }
        }

    }

    private void SendComment(String xfdfDest) throws MalformedURLException {
//		StringWriter errors = null;
        try {

            System.out.println("xfdfDest = " + xfdfDest);
            //start save to ../ETDMS/temp
//            m_NotesBean.save(m_NotesBean, STRING_TRUE, xfdfDest);

            //start save to ../ETDMS/annotation
            //end...
            m_NotesBean.getMutableDocument().exportAnnotsAsFDF(xfdfDest, null);
            System.out.println("saving PDF status = " + m_NotesBean.save(m_NotesBean, dAtt.getFileName(), dAtt.getSaveFilePDF()));
            String returnValidate = callSoapWebServiceValidate(soapEndpointUrl, operationMethodUrl);
            System.out.println("return = " + returnValidate);
            String getReturn[] = returnValidate.split("~");
//            if (returnValidate.contains("berhasil") == true) {
//                dAtt.setdDocName(getReturn[0]);
////                if (returnValidate.contains("null") == true) {
////                    errorNullMessage("DDOCNAME is null");
////                } else {
//                sucessMessage();
////                }
//            } else {
////                errorMessage("error get return to get DDOCNAME");
//                if (returnValidate.contains("flaten") == true) {
//                    errorNullMessage("DDOCNAME is null");
//                } else {
//                    errorMessage("Error to get Return from server to get DDOCNAME, Please try again...");
//                }
//            }

            if (!returnValidate.contains("flaten") == true || !returnValidate.contains("gagal") == true) {
                dAtt.setdDocName(getReturn[0]);
                sucessMessage();
            } else {
                if (returnValidate.contains("flaten") == true) {
                    errorNullMessage("DDOCNAME is null");
                } else {
                    errorMessage("Error to get Return from server to get DDOCNAME, Please try again...");
                }
            }

        } catch (PDFException ex) {
            errorMessage("PDFException error at PDF Document, Please try again...");

        } catch (ParserConfigurationException ex) {
            errorMessage("Parse Configuration Exception, Please try again... ");

        } catch (SAXException ex) {
            errorMessage("Failed to Generate Document for send to server, Please try again...");

        } catch (IOException ex) {
            errorMessage("Failed Input Output - IOException, Please try again...");

        } catch (SOAPException ex) {
            errorMessage("You are not Connected Properly with Internal Network, Please try again... - SOAPException");

        } catch (Exception ex) {
            errorMessage("General ERROR, Please try again..." + ex);

        }
    }

    private String callSoapWebServiceValidate(String soapEndpointUrl, String soapAction)
            throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, SOAPException, Exception {
//        try {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequestValidate(soapAction), soapEndpointUrl);

        // Print the SOAP Response
        System.out.println("Response Message:");
        soapResponse.writeTo(System.out);
        System.out.println();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapResponse.writeTo(out);
        String strXml = new String(out.toByteArray());

        soapConnection.close();
        org.w3c.dom.Document document = loadMyXMLFromString(strXml);
        Element rootElement = document.getDocumentElement();
        String result = getMyString("return", rootElement);
        System.out.println("\nresultxml :" + result);
        try {
            String[] splitString = result.split("~");
            dAtt.setdDocName(splitString[0]);
        } catch (Exception ex) {
        }
        return result;
//        } catch (Exception e) {
//            System.err.println(
//                    "\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
//            e.printStackTrace();
//            return "fail " + e;
//        }

    }

    private SOAPMessage createSOAPRequestValidate(String soapAction) {// throws Exception {
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            createMySoapEnvelope(soapMessage);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            /* Print the request message, just for debugging purposes */
            System.out.println("\nRequest Message:");
            soapMessage.writeTo(System.out);
//            System.out.println("got the soap messages");
            System.out.println("\n");
            System.out.println("requst has been create");

            return soapMessage;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public static Document loadMyXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    protected static String getMyString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }

//    private byte[] getMessageContent() throws IOException, PDFException {
//        PDFNotesBean notesBean = getPDFNotesBean();
//        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//
//        if (m_SaveFormat == SAVE_FDF) {
//            notesBean.getMutableDocument().exportAnnotsAsFDF(byteStream, notesBean.getDocument().getPDFSource().getPath());
//        } else if (m_SaveFormat == SAVE_XFDF) {
//            notesBean.getMutableDocument().exportAnnotsAsXFDF(byteStream, notesBean.getDocument().getPDFSource().getPath());
//        } else {
//            getPDFNotesBean().saveDocument(byteStream);
//        }
//
//        return byteStream.toByteArray();
//    }
    private String getDocName(String docName) {
        // Remove .pdf extension
        if (m_SaveFormat == SAVE_FDF || m_SaveFormat == SAVE_XFDF) {
            if (docName.toLowerCase().endsWith(".pdf")) {
                docName = docName.substring(0, docName.length() - 4);
            }

            if (m_SaveFormat == SAVE_FDF) {
                docName += ".fdf";
            } else if (m_SaveFormat == SAVE_XFDF) {
                docName += ".xfdf";
            }
        }

        return docName;
    }

    private Hashtable setAddlVariables() {
        // Additional variables
        Hashtable addlVariables = new Hashtable();

        String addlString = getParameter("AddlVariables");
        if (addlString != null && addlString.trim().length() > 0) {
            StringTokenizer tokens = new StringTokenizer(addlString, ",");
            while (tokens.hasMoreTokens()) {
                String keyValue = tokens.nextToken();
                if (keyValue.indexOf('=') != -1) {
                    String key = keyValue.substring(0, keyValue.indexOf('='));
                    String value = keyValue.substring(keyValue.indexOf('=') + 1);

                    if (key.trim().length() > 0) {
                        addlVariables.put(key.trim(), value.trim());
                    }
                }
            }
        }

        if (addlVariables.size() == 0) {
            addlVariables = null;
        }

        return addlVariables;
    }

    private String getUserName() {
        try {
            return System.getProperty("user.name");
        } catch (Throwable t) {
            //t.printStackTrace();
            return null;
        }
    }

    private void createMySoapEnvelope(SOAPMessage soapMessage) throws SOAPException, IOException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespace, namespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(operationName, namespace);
        /*   
		 this soap element is one of elemt of parameter you want to add in web service
		 eg. your web service param is String param just add name of the parameter
		 so do as the text node, you just set the text node to set the value of element of the parameter
         */
        SOAPElement fileContent = soapBodyElem.addChildElement("GetFile");
        fileContent.addTextNode(encodeMyFile64Binary(dAtt.getFileXFDF()));
//        fileContent.addTextNode("d");
//        dAtt.setReviewId("0");
        File filess = new File(dAtt.getFileXFDF());
        SOAPElement fileNameIs = soapBodyElem.addChildElement("GetFileName");
        fileNameIs.addTextNode(setURLDecode(filess.getName()));

        SOAPElement file_name = soapBodyElem.addChildElement("file_name");
        file_name.addTextNode(setURLDecode(filess.getName()));

        SOAPElement ddocname = soapBodyElem.addChildElement("dDocName");
        try {
            ddocname.addTextNode(dAtt.getdDocName());
        } catch (NullPointerException n) {
//			ddocname.addTextNode("");
        }

        SOAPElement dDocType = soapBodyElem.addChildElement("dDocType");
        dDocType.addTextNode("review_comment_doc");

        SOAPElement dDocAuthor = soapBodyElem.addChildElement("dDocAuthor");
//        dDocAuthor.addTextNode("weblogic");
        dDocAuthor.addTextNode(dAtt.getUserName());

        SOAPElement ddocauthor = soapBodyElem.addChildElement("ddocauthor");
//        dDocAuthor.addTextNode("weblogic");
        ddocauthor.addTextNode(dAtt.getUserName());

        SOAPElement dSecurityGroup = soapBodyElem.addChildElement("dSecurityGroup");
//        dSecurityGroup.addTextNode(dAtt.getAclType());
        dSecurityGroup.addTextNode("Public");
//		dSecurityGroup.addTextNode("acl");

        SOAPElement dusername = soapBodyElem.addChildElement("dusername");
        dusername.addTextNode(dAtt.getUserName());
//		dusername.addTextNode("username");

//        SOAPElement fileNameIs = soapBodyElem.addChildElement("duploaddate");
//        fileNameIs.addTextNode(dAtt.getFileName());
//        SOAPElement drevisionnumber = soapBodyElem.addChildElement("drevisionnumber");
//        drevisionnumber.addTextNode(dAtt.getFileName());
        SOAPElement ddocnumber = soapBodyElem.addChildElement("ddocnumber");
        ddocnumber.addTextNode("PDF Annotation");

        SOAPElement ddoctitle = soapBodyElem.addChildElement("ddoctitle");
        ddoctitle.addTextNode(setURLDecode(filess.getName()));

        SOAPElement xcountrycode = soapBodyElem.addChildElement("xcountrycode");
        xcountrycode.addTextNode("ID");

        SOAPElement is_active = soapBodyElem.addChildElement("is_active");
        is_active.addTextNode("Y");

        SOAPElement file_size = soapBodyElem.addChildElement("file_size");
        file_size.addTextNode("0");

        SOAPElement file_type = soapBodyElem.addChildElement("file_type");
        file_type.addTextNode("xfdf");

        SOAPElement last_modified_by = soapBodyElem.addChildElement("last_modified_by");
//        last_modified_by.addTextNode(dAtt.getReviewOwner());
        last_modified_by.addTextNode(dAtt.getUserName());

        SOAPElement has_pdf_rendition = soapBodyElem.addChildElement("has_pdf_rendition");
        has_pdf_rendition.addTextNode("Y");

        SOAPElement is_checkout = soapBodyElem.addChildElement("is_checkout");
        is_checkout.addTextNode("N");

        SOAPElement has_reference = soapBodyElem.addChildElement("has_reference");
        has_reference.addTextNode("N");

        SOAPElement has_linkedin = soapBodyElem.addChildElement("has_linkedin");
        has_linkedin.addTextNode("Y");

        SOAPElement xrevision = soapBodyElem.addChildElement("xrevision");
        xrevision.addTextNode("N");

        SOAPElement is_reviewed = soapBodyElem.addChildElement("is_reviewed");
        is_reviewed.addTextNode("N");

        SOAPElement is_modified = soapBodyElem.addChildElement("is_modified");
        is_modified.addTextNode("N");

        SOAPElement has_pos_it = soapBodyElem.addChildElement("has_pos_it");
        has_pos_it.addTextNode("N");

        SOAPElement has_appendices = soapBodyElem.addChildElement("has_appendices");
        has_appendices.addTextNode("N");

        SOAPElement has_translation = soapBodyElem.addChildElement("has_translation");
        has_translation.addTextNode("N");

        SOAPElement has_rendition = soapBodyElem.addChildElement("has_rendition");
        has_rendition.addTextNode("Y");

        SOAPElement is_primary = soapBodyElem.addChildElement("is_primary");
        is_primary.addTextNode("N");

        SOAPElement dsecuritygroup1 = soapBodyElem.addChildElement("dsecuritygroup");
//        dsecuritygroup1.addTextNode(dAtt.getAclType());
        dsecuritygroup1.addTextNode("Public");
//		dsecuritygroup1.addTextNode("acl");

        SOAPElement reviewOwner = soapBodyElem.addChildElement("reviewOwner");
        reviewOwner.addTextNode(dAtt.getReviewOwner());
//		dAtt.setUserName("uname");
//		reviewOwner.addTextNode("usname");

        SOAPElement createUser = soapBodyElem.addChildElement("createUser");
        createUser.addTextNode(dAtt.getUserName());

        SOAPElement reviewType = soapBodyElem.addChildElement("reviewType");
        reviewType.addTextNode("Annotation");

        SOAPElement UCM_Server = soapBodyElem.addChildElement("UCM_Server");
        UCM_Server.addTextNode(dAtt.getUcmServer());
//		UCM_Server.addTextNode("asasas");

        SOAPElement commentStatus = soapBodyElem.addChildElement("commentStatus");
        commentStatus.addTextNode("In Progress");

        SOAPElement consolidate = soapBodyElem.addChildElement("consolidate");
        consolidate.addTextNode(dAtt.getConsolidate());
//		consolidate.addTextNode("asasas");

        SOAPElement GGI = soapBodyElem.addChildElement("GGI");
        GGI.addTextNode(dAtt.getGgi());
//		GGI.addTextNode("asasas");

        SOAPElement reviewid = soapBodyElem.addChildElement("reviewid");
        reviewid.addTextNode(dAtt.getReviewId());
//		reviewid.addTextNode("asas");

//        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(parameterName);
//        soapBodyElem1.addTextNode(parameterValue;
    }

    private String encodeMyFile64Binary(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.US_ASCII);
    }

    public String setURLDecode(String AddressURL) {
        return URLDecoder.decode(AddressURL);
    }

    private void getNotReadOnlyAnnots(String absolutePath) throws SAXException, IOException, ParserConfigurationException, TransformerException {
        ArrayList<String> annotsName = new ArrayList<>();
        File inputFile = new File(absolutePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        //get n stamp
        Node annotlist = doc.getElementsByTagName("annots").item(0);
        NodeList annotNodelist = annotlist.getChildNodes();
        System.out.println(annotNodelist.getLength());
        for (int v = 0; v < annotNodelist.getLength(); v++) {
//				System.out.println("node type + " + annotNodelist.item(v).getNodeType());
            if (annotNodelist.item(v).getNodeType() == 1) {
                if (annotNodelist.item(v)
                        .getAttributes().getNamedItem("flags").getNodeValue().toString().contains("readonly") == true) {
                    annotsName.add(annotNodelist.item(v)
                            .getAttributes().getNamedItem("title").getNodeValue().toString());
                    System.out.println("page: " + (Integer.parseInt(annotNodelist.item(v)
                            .getAttributes().getNamedItem("page").getNodeValue().toString()) + 1) + " Title: " + annotNodelist.item(v)
                            .getAttributes().getNamedItem("title").getNodeValue().toString());
//					System.out.println("flags: " + annotNodelist.item(v)
//							.getAttributes().getNamedItem("flags").getNodeValue().toString());
                    annotlist.removeChild(annotNodelist.item(v));
                    v = v - 1;

                } else if (annotNodelist.item(v)
                        .getAttributes().getNamedItem("flags").getNodeValue().toString().contains("readonly") == false) {
                    annotNodelist.item(v)
                            .getAttributes().getNamedItem("title").setNodeValue(getUserName());
                    System.out.println(annotNodelist.item(v)
                            .getAttributes().getNamedItem("title").getNodeValue().toString());
                    System.out.println("name replaced");
                }
            }

        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        System.out.println("-----------Modified File-----------");
        StreamResult consoleResult = new StreamResult(absolutePath);
        transformer.transform(source, consoleResult);

    }

    private void WorkOfflineSync(String userName, String reviewId) {
        try {
            callSoapWebServiceWorkOffline(soapEndpointUrlWorkOff, operationMethodUrlWorkOff);
        } catch (SAXException ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SOAPException ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void GoBackSync(String userName, String reviewId) {
        try {
            callSoapWebServiceGoBack(soapEndpointUrlGoback, operationMethodUrlGoback);
        } catch (SAXException ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SOAPException ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PDFWebNotes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createSoapEnvelopeWorkOffline(SOAPMessage soapMessage) throws SOAPException, IOException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespaceWorkOff, namespaceURIWorkOff);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(operationNameWorkOff, namespaceWorkOff);
        /*   
		 this soap element is one of elemt of parameter you want to add in web service
		 eg. your web service param is String param just add name of the parameter
		 so do as the text node, you just set the text node to set the value of element of the parameter
         */
        SOAPElement GGI = soapBodyElem.addChildElement("uName");
        GGI.addTextNode(dAtt.getUserName());

        SOAPElement reviewid = soapBodyElem.addChildElement("revID");
        reviewid.addTextNode(dAtt.getReviewId());
    }

    private void createSoapEnvelopeGoBack(SOAPMessage soapMessage) throws SOAPException, IOException {
        SOAPPart soapPart = soapMessage.getSOAPPart();
//		namespace = "";
//		soapEndpointUrl = "http://localhost:7101/SWAApplication-Client-context-root/JWSXfdfPort";
//		operationMethodUrl = "http://localhost:7101/SWAApplication-Client-context-root/JWSXfdfPort";
//		namespaceURI = "http://client/";
//		operationName = "uplodFileWS";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespaceGoback, namespaceURIGoback);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(operationNameGoback, namespaceGoback);
        /*   
		 this soap element is one of elemt of parameter you want to add in web service
		 eg. your web service param is String param just add name of the parameter
		 so do as the text node, you just set the text node to set the value of element of the parameter
         */

        SOAPElement GGI = soapBodyElem.addChildElement("uName");
        GGI.addTextNode(dAtt.getUserName());

        SOAPElement reviewid = soapBodyElem.addChildElement("revID");
        reviewid.addTextNode(dAtt.getReviewId());

    }

    private SOAPMessage createSOAPRequestWorkOffline(String soapAction) {// throws Exception {
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            createSoapEnvelopeWorkOffline(soapMessage);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            /* Print the request message, just for debugging purposes */
            System.out.println("\nRequest Message:");
            soapMessage.writeTo(System.out);
            System.out.println("\n");
            System.out.println("requst has been create");

            return soapMessage;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private SOAPMessage createSOAPRequestGoBack(String soapAction) {// throws Exception {
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            createSoapEnvelopeGoBack(soapMessage);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            /* Print the request message, just for debugging purposes */
            System.out.println("\nRequest Message:");
            soapMessage.writeTo(System.out);
            System.out.println("\n");
            System.out.println("requst has been create");

            return soapMessage;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private String callSoapWebServiceWorkOffline(String soapEndpointUrl, String soapAction)
            throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, SOAPException, Exception {

        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequestWorkOffline(soapAction), soapEndpointUrl);

        // Print the SOAP Response
        System.out.println("Response Message:");
        soapResponse.writeTo(System.out);
        System.out.println();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapResponse.writeTo(out);
        String strXml = new String(out.toByteArray());

        soapConnection.close();
        org.w3c.dom.Document document = loadMyXMLFromString(strXml);
        Element rootElement = document.getDocumentElement();
        String result = getMyString("return", rootElement);
        System.out.println("\nresultxml :" + result);

        return result;

    }

    private String callSoapWebServiceGoBack(String soapEndpointUrl, String soapAction)
            throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, SOAPException, Exception {

        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequestGoBack(soapAction), soapEndpointUrl);

        // Print the SOAP Response
        System.out.println("Response Message:");
        soapResponse.writeTo(System.out);
        System.out.println();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapResponse.writeTo(out);
        String strXml = new String(out.toByteArray());

        soapConnection.close();
        org.w3c.dom.Document document = loadMyXMLFromString(strXml);
        Element rootElement = document.getDocumentElement();
        String result = getMyString("return", rootElement);
        System.out.println("\nresultxml :" + result);

        return result;

    }

    private boolean isCanGoback(String absolutePath) throws IOException {
        PDDocument pdf = null;
        try {
//            pdf = PDDocument.load(new File("XXXX", "YYYYY.pdf"));
            pdf = PDDocument.load(new File(absolutePath));
//			extractJS(pdf);
            return extractJS(pdf);
        } finally {
            if (pdf != null) {
                pdf.close();
            }
        }
    }

    private boolean extractJS(PDDocument pdfDocument) throws IOException {
        System.out.println("start printing field");
        String usernameDoc;
        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        PDActionJavaScript jScript = (PDActionJavaScript) docCatalog.getOpenAction();
        if (jScript instanceof PDActionJavaScript) {
            PDActionJavaScript jsAction = (PDActionJavaScript) jScript;
            String jsString = jsAction.getAction();
            System.out.println(jsString);
            usernameDoc = jsString.substring(jsString.indexOf("usernameIs = '") + 14, jsString.indexOf("';"));
            System.out.println("output string : " + jsString.substring(jsString.indexOf("usernameIs = '") + 14, jsString.indexOf("';")));
            System.out.println();
            if (usernameDoc.equalsIgnoreCase(dAtt.getUserName())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
