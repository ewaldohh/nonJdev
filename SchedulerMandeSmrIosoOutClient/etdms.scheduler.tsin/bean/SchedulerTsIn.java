package bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import scheduler.ws.SchedulerMain;
import scheduler.ws.SchedulerMainService;
import utils.AES;

public class SchedulerTsIn {
	private static String soapEndpointUrl = "";
	private static String operationMethodUrl = "";
	private static String namespace = "";
	private static String namespaceURI = "";
	private static String operationName = "";
	private static String parameterName = "";
	private static String parameterValue = "";
	private static String getSourceFolder = "";
	private static String getDestinationFolder = "";
	static ArrayList<String> foldersToProcess = new ArrayList<String>();
	static ArrayList<String> foldersInProgress = new ArrayList<String>();
	static ArrayList<String> transmittalIdHistory = new ArrayList<String>();

	// SAAJ - SOAP Client Testing
	public static void main(String args[])
			throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		try {
			initialize();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			initializeFolder();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		foldersToProcess = folderList(getSourceFolder);
		String transmittalHistoryId = null;
		for (int i = 0; i < foldersToProcess.size(); i++) {
			transmittalHistoryId = insertToEtdmsTransmittalHistory(
					foldersToProcess.get(i).substring(foldersToProcess.get(i).lastIndexOf("\\") + 1));
			transmittalIdHistory.add(transmittalHistoryId);
		}
		for (int i = 0; i < foldersToProcess.size(); i++) {
			try {
				System.out.println(foldersToProcess.get(i));
				System.out.println(transmittalIdHistory.get(i));
				SchedulerMainService schedulerMainService = new SchedulerMainService();
				SchedulerMain schedulerMain = schedulerMainService.getSchedulerMainPort();
				schedulerMain.schedulerTsInWS(foldersToProcess.get(i), transmittalIdHistory.get(i));
			} catch (Exception e) {
				// TODO: Add catch code
				e.printStackTrace();
			}
		}
//		try {
//			initialize();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		callSoapWebService(soapEndpointUrl, operationMethodUrl);
//		
//		try {
//			initializeTsOut();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		callSoapWebService(soapEndpointUrl, operationMethodUrl);
	}

	public static String insertToEtdmsTransmittalHistory(Object reference) {

		// String user = secCntx.getUserPrincipal().getName();
		String user = "weblogic";
		System.out.println("=============================================================");
		System.out.println("insertToEtdmsTransmittalHistory");
		Connection con = null;
		PreparedStatement pr = null;
		// int tsoutId = getCurrentTsOutIdSequence();
		String STMT = "INSERT INTO ETDMS_TRANSMITTAL_HISTORY (EXECUTION_DATE,REFERENCE,EXECUTOR,TRANSMITTAL_ID,STATUS) "
				+ "VALUES(to_date(?,'MM/dd/yyyy hh:mi:ss am'),?,?,?,?)";
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Date today = Calendar.getInstance().getTime();
		String currentDate = df.format(today);
		System.out.println("Date inserted: " + currentDate);
		Long transmittalHistoryId = null;
		try {

			con = getConnection();
			// pr = con.prepareStatement(STMT);
			pr = con.prepareStatement(STMT, new String[] { "transmittal_history_id" });

			pr.setObject(1, currentDate.toString());
			pr.setObject(2, reference);
			pr.setObject(3, user);
			pr.setObject(4, null);
			pr.setObject(5, "In Progress");
			System.out.println("Query :" + STMT);
			// pr.execute();
			// pr.executeUpdate();
			if (pr.executeUpdate() > 0) {

				// getGeneratedKeys() returns result set of keys that were auto
				// generated
				// in our case student_id column
				ResultSet generatedKeys = pr.getGeneratedKeys();

				// if resultset has data, get the primary key value
				// of last inserted record
				if (null != generatedKeys && generatedKeys.next()) {

					// voila! we got student id which was generated from sequence
					transmittalHistoryId = generatedKeys.getLong(1);
				}
				System.out.println("transmittalHistoryId :" + transmittalHistoryId);
				generatedKeys.close();
			}
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close connection
			try {
				if (pr != null)
					pr.close();
			} catch (Exception e) {
			}
			;
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
			;
		}
		System.out.println("=============================================================");
		return String.valueOf(transmittalHistoryId);
	}

	public static ArrayList<String> folderList(String parentFolder) throws IOException {

		File f = new File(parentFolder); // current directory

		ArrayList<String> list = new ArrayList<String>();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			// System.out.println("documentId: " + documentId);
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT REFERENCE FROM ETDMS_TRANSMITTAL_HISTORY WHERE STATUS='FLAG'");
			while (rs.next()) {
				foldersInProgress.add(rs.getString("REFERENCE"));

			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException sqle) {
			// TODO: Add catch code
			sqle.printStackTrace();
		} catch (NullPointerException npe) {
			// TODO: Add catch code
			npe.printStackTrace();
		} catch (Exception e) {
			// TODO: Add catch code
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
			;
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			;

		}
		FileFilter directoryFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};

		File[] files = f.listFiles(directoryFilter);
		for (File file : files) {
			if (file.isDirectory()) {
				if (!foldersInProgress.contains(file.getName())) {
					list.add(file.getCanonicalPath());
				}

				// String m1 = "directory:";
				// System.out.print(m1);
				// listMessage.add("["+sdf.format(timestamp)+"] "+m1);

			} else {
				// String m2 = " file:";
				// System.out.print(m2);
				// listMessage.add("["+sdf.format(timestamp)+"] "+m2);
			}
			// String m3 = file.getCanonicalPath();
			// System.out.println(m3);
			// listMessage.add("["+sdf.format(timestamp)+"] "+m3);
		}

		return list;
	}

	public static void initialize() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			// System.out.println("documentId: " + documentId);
			con = getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT WS_ENDPOINT_URL,WS_OPERATION_URL,NAMESPACE,NAMESPACEURI,OPERATION_NAME FROM ETDMS_SCHEDULER WHERE SCHEDULER_ID='mande_smr_ioso'");
			while (rs.next()) {
				soapEndpointUrl = rs.getString("WS_ENDPOINT_URL");
				operationMethodUrl = rs.getString("WS_OPERATION_URL");
				namespace = rs.getString("NAMESPACE");
				namespaceURI = rs.getString("NAMESPACEURI");
				operationName = rs.getString("OPERATION_NAME");

			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			stmt.close();
			con.close();
			e.printStackTrace();
			System.out.println("Error Select Data :" + e);
		}

		try {
			System.out.println("soapEndpointUrl :" + soapEndpointUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("operationMethodUrl :" + operationMethodUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("namespace :" + namespace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("namespaceURI :" + namespaceURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("operationName :" + operationName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws Exception, NullPointerException {
		String host = null;
		String port = null;
		String service = null;
		String username = null;
		String password = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = builder.parse("D:/MANDe/Scheduler/MANDeServices/Config/db_conn.config");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = null;
		try {
			expr = xpath.compile("//configuration/appSettings/add[@value]");
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		for (int i = 0; i < nl.getLength(); i++) {
			Node currentItem = nl.item(i);
			String key = currentItem.getAttributes().getNamedItem("key").getNodeValue();
			String value = currentItem.getAttributes().getNamedItem("value").getNodeValue();
			if (key.equals("db_username")) {
				username = value;
				System.out.println("username :" + username);
			}
			if (key.equals("db_password")) {
				password = value;
				AES aes = new AES();
				password = aes.decrypt(password, "ETDMS_DEV");
				System.out.println("password :" + password);
			}
			if (key.equals("db_port")) {
				port = value;
				System.out.println("port :" + port);
			}
			if (key.equals("db_host")) {
				host = value;
				System.out.println("host :" + host);
			}
			if (key.equals("db_service")) {
				service = value;
				System.out.println("service :" + service);
			}
		}
		Class.forName(driver);
		String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;
		System.out.println("url database :" + url);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("==============Connection===============");
		return conn;
	}

	public static void initializeFolder() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			// System.out.println("documentId: " + documentId);
			con = getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT FOLDER_READ, FOLDER_WRITE FROM ETDMS_SCHEDULER WHERE SCHEDULER_ID='mande_smr_ioso'");
			while (rs.next()) {

				getSourceFolder = rs.getString("FOLDER_READ");
				getDestinationFolder = rs.getString("FOLDER_WRITE");
				System.out.println("Source Folder:" + getSourceFolder);
				System.out.println("Destination Folder:" + getDestinationFolder);

			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			stmt.close();
			con.close();
			e.printStackTrace();
			System.out.println("Error Select Data :" + e);
		}

	}

	protected static String getString(String tagName, Element element) {
		NodeList list = element.getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			NodeList subList = list.item(0).getChildNodes();

			if (subList != null && subList.getLength() > 0) {
				return subList.item(0).getNodeValue();
			}
		}

		return null;
	}

//	private static void initialize()
//			throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
//		org.w3c.dom.Document document = builder.parse(
//				new InputSource(new FileReader(new File("D:/MANDe/Scheduler/SchedulerMandeSmrIosoClientConfig.xml"))));
//		NodeList configList = document.getElementsByTagName("configuration");
//
//		for (int i = 0; i < configList.getLength(); i++) {
//			NodeList childList = configList.item(i).getChildNodes();
//
//			for (int j = 0; j < childList.getLength(); j++) { // lvl 2
//				Node childNode = childList.item(j);
//
//				switch (childNode.getNodeName()) {
//				case "soapEndpointUrl":
//					soapEndpointUrl = childList.item(j).getTextContent().trim();
//					break;
//				case "operationMethodUrl":
//					operationMethodUrl = childList.item(j).getTextContent().trim();
//					break;
//				case "namespace":
//					namespace = childList.item(j).getTextContent().trim();
//					break;
//				case "namespaceURI":
//					namespaceURI = childList.item(j).getTextContent().trim();
//					break;
//				case "operationName":
//					operationName = childList.item(j).getTextContent().trim();
//					break;
//				case "parameterName":
//					parameterName = childList.item(j).getTextContent().trim();
//					break;
//				case "parameterValue":
//					parameterValue = childList.item(j).getTextContent().trim();
//					break;
//
//				}
//			}
//		}
//		try {
//			System.out.println("soapEndpointUrl :" + soapEndpointUrl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("operationMethodUrl :" + operationMethodUrl);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("namespace :" + namespace);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("namespaceURI :" + namespaceURI);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("operationName :" + operationName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("parameterName :" + parameterName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("parameterValue :" + parameterValue);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Connection con = null;
//	}

	private static void initializeTsOut()
			throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder.parse(new InputSource(
				new FileReader(new File("D:/MANDe/Scheduler/SchedulerMandeSmrIosoTsoutClientConfig.xml"))));
		NodeList configList = document.getElementsByTagName("configuration");

		for (int i = 0; i < configList.getLength(); i++) {
			NodeList childList = configList.item(i).getChildNodes();

			for (int j = 0; j < childList.getLength(); j++) { // lvl 2
				Node childNode = childList.item(j);

				switch (childNode.getNodeName()) {
				case "soapEndpointUrl":
					soapEndpointUrl = childList.item(j).getTextContent().trim();
					break;
				case "operationMethodUrl":
					operationMethodUrl = childList.item(j).getTextContent().trim();
					break;
				case "namespace":
					namespace = childList.item(j).getTextContent().trim();
					break;
				case "namespaceURI":
					namespaceURI = childList.item(j).getTextContent().trim();
					break;
				case "operationName":
					operationName = childList.item(j).getTextContent().trim();
					break;
				case "parameterName":
					parameterName = childList.item(j).getTextContent().trim();
					break;
				case "parameterValue":
					parameterValue = childList.item(j).getTextContent().trim();
					break;

				}
			}
		}
		try {
			System.out.println("soapEndpointUrl :" + soapEndpointUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("operationMethodUrl :" + operationMethodUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("namespace :" + namespace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("namespaceURI :" + namespaceURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("operationName :" + operationName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("parameterName :" + parameterName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("parameterValue :" + parameterValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Connection con = null;
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(namespace, namespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement(operationName, namespace);
//		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(parameterName);
//		soapBodyElem1.addTextNode(parameterValue);
	}

	private static void callSoapWebService(String soapEndpointUrl, String soapAction)
			throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

			// Print the SOAP Response
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);
			String strXml = new String(out.toByteArray());

			soapConnection.close();
			org.w3c.dom.Document document = loadXMLFromString(strXml);
			Element rootElement = document.getDocumentElement();
			String resultxml = getString("return", rootElement);
			System.out.println("\nresultxml :" + resultxml);

		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}

	}

	public static Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		createSoapEnvelope(soapMessage);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);

		soapMessage.saveChanges();

		/* Print the request message, just for debugging purposes */
		System.out.println("\nRequest SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");

		return soapMessage;
	}
}