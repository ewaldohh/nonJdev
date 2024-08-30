package bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SchedulerCleansing {
	private static String soapEndpointUrl = "";
	private static String operationMethodUrl = "";
	private static String namespace = "";
	private static String namespaceURI = "";
	private static String operationName = "";
	private static String parameterName = "";
	private static String parameterValue = "";

	// SAAJ - SOAP Client Testing
	public static void main(String args[])
			throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {

		try {
			initialize();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		callSoapWebService(soapEndpointUrl, operationMethodUrl);

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

	private static void initialize()
			throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder.parse(new InputSource(
				new FileReader(new File("D:/MANDe/Scheduler/SchedulerMandeCleansingClientConfig.xml"))));
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