package com.eventLogMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EventMonitorUtils {
	
	private static final Logger log = Logger.getLogger(EventMonitor.class.getName());
	
	public EventMonitorUtils(){
	}
	public static String executeWevtutilQuery(String logFile, String searchTerm, String server) {
		String command = "wevtutil qe " + logFile + " /q:\"" + searchTerm + "\" /r:" + server;
		log.fine(command);
		String inputStream = "<Events>";
		String line = "";

		try {
			Runtime rt = Runtime.getRuntime();
			Process execute = rt.exec(command);
			BufferedReader BR = new BufferedReader(new InputStreamReader(execute.getInputStream()));

			while ((line = BR.readLine()) != null){
				inputStream += line;
			}
			inputStream += "</Events>";
			execute.destroy();
		}
		catch (Throwable t) {
			t.printStackTrace();
			log.log(Level.SEVERE, "Error occured while obtaining raw event data: ", t);
			return null;
		}
		return (inputStream);
	}
	public static String calculateNewEvents(String inputStream, String searchTerm,String connectionURL,String server,String logFile,String originalSearchTerm) {
		Transformer transformer;
		try {
			boolean newRecord = false;
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new StringWriter());
			Source source = new StreamSource(new StringReader(inputStream));
			transformer.transform(source, result);
			String xmlString = result.getWriter().toString();
			//System.out.println(xmlString);
			log.fine(xmlString);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse( new InputSource( new StringReader(xmlString) ) );
			document.getDocumentElement().normalize();
			NodeList eventList = document.getElementsByTagName("Event");
			int numEvents = eventList.getLength();
			String EventData = "";
			String EventDataTemp = "";
			String EventID = "";
			String EventRecordID = "";
			String EventTime = "";
			String EventSource = "";
			for(int x=0; x<numEvents; x++)
			{
				newRecord = true;
				Node event = eventList.item(x);
				NodeList eventAttributes = event.getChildNodes();
				int totalAttributes = eventAttributes.getLength();
				log.fine("**************************************************************************");
				for(int z=0; z<totalAttributes; z++)
				{
					Node attribute = eventAttributes.item(z);
					String type = attribute.getNodeName();
					if(type.equals("EventData"))
					{
						EventDataTemp = attribute.getTextContent();
					}
					else if(type.equals("System"))
					{
						NodeList systemAttributes = attribute.getChildNodes();
						int sysLength = systemAttributes.getLength();
						log.fine(Integer.toString(sysLength));
						for(int w=0; w<sysLength; w++)
						{
							Node sysAttribute = systemAttributes.item(w);
							String sysType = sysAttribute.getNodeName();
							if(sysType.equals("EventRecordID"))
							{
								EventRecordID = sysAttribute.getTextContent();
							}
							else if(sysType.equals("EventID"))
							{
								EventID = EventRecordID = sysAttribute.getTextContent();
							}
							else if(sysType.equals("TimeCreated"))
							{
								 NamedNodeMap timeAtts = sysAttribute.getAttributes();
								 Node time = timeAtts.getNamedItem("SystemTime");
								 EventTime = time.getNodeValue();
							}
							else if(sysType.equals("Provider"))
							{
								 NamedNodeMap providerAtts = sysAttribute.getAttributes();
								 Node provider = providerAtts.getNamedItem("Name");
								 EventSource = provider.getNodeValue();
							}
						}
					}
						
				}
				log.fine(EventID);
				log.fine(EventTime);
				log.fine(EventData);
				EventData += "<hr>[EventID: " + EventID + " | EventSource: " + EventSource + " | Event #" + EventRecordID + " | Source Time:" + EventTime + " GMT] - " + EventDataTemp;
				log.fine("**************************************************************************");
			}
			if(newRecord)
			{
				log.fine(EventData);
				Pattern p = Pattern.compile("(\\Q@SystemTime>\\E.*Z)");
				Matcher matches = p.matcher(searchTerm);
				searchTerm = matches.replaceAll(("@SystemTime>\'" + EventTime));
				boolean updateCheck = EventMonitorUtils.updateDatabase(connectionURL,server,logFile,originalSearchTerm,searchTerm,EventData);
				if(updateCheck)
				{
					return (searchTerm);
				}
				else
				{
					log.severe("Database update Attempt 1 failed");
					updateCheck = EventMonitorUtils.updateDatabase(connectionURL,server,logFile,originalSearchTerm,searchTerm,EventData);
					if(updateCheck)
					{
						log.severe("Database update Attempt 2 Succeed");
						return (searchTerm);
					}
					else
					{
						log.severe("Database update Attempt 2 failed");
						updateCheck = EventMonitorUtils.updateDatabase(connectionURL,server,logFile,originalSearchTerm,searchTerm,EventData);
						if(updateCheck)
						{
							log.severe("Database update Attempt 3 Succeed");
							return (searchTerm);
						}
						else
						{
							log.severe("Database update Attempt 3 failed.  The monitor will throw an error.  Please rerun this monitor.");
							return null;
						}
					}
				}
			}
		} catch (TransformerConfigurationException e) {
			log.log(Level.SEVERE, "Error occured while Processing raw event data: ", e);
			return null;
		} catch (TransformerFactoryConfigurationError e) {
			log.log(Level.SEVERE, "Error occured while Processing raw event data: ", e);
			return null;
		} catch (TransformerException e) {
			log.log(Level.SEVERE, "Error occured while Processing raw event data: ", e);
			return null;
		} catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, "Error occured while Processing raw event data: ", e);
			return null;
		} catch (SAXException e) {
			log.log(Level.SEVERE, "Error occured while Processing raw event data: ", e);
			return null;
		} catch (IOException e) {
			log.log(Level.SEVERE, "Error occured while Processing raw event data: ", e);
			return null;
		}
		return (searchTerm);
	}
	public static String getCurrentSearchTerm(String connectionURL,	String server, String logFile, String originalSearchTerm) {
		try {
			String currentSearchTerm = originalSearchTerm;
			boolean hasRows = false;
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(connectionURL);
			String SQL = "select * from EventLog where Host = '"
					+ server + "' and EventSource = '" + logFile
					+ "' and EventID = '" + StringEscapeUtils.escapeSql(originalSearchTerm) + "';";
			log.fine(SQL);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
		    	 hasRows = true;
		    	 currentSearchTerm = rs.getString("RecordNumber");
		     }
			if(!hasRows)
			{
				SQL = "Insert into EventLog (RecordNumber,EventID,Host,EventSource,EventDescription) VALUES ('"
						+ StringEscapeUtils.escapeSql(currentSearchTerm)
						+ "','"
						+ StringEscapeUtils.escapeSql(originalSearchTerm)
						+ "','"
						+ server
						+ "','"
						+ logFile
						+ "','"
						+ " "
						+ "');";
				log.fine(SQL);
				stmt.executeUpdate(SQL);
			}
			return currentSearchTerm;
			
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, "Error occured while getting initial database connection: ", e);
			return null;
		} catch (SQLException e) {
			log.log(Level.SEVERE, "Error occured while getting initial database connection: ", e);
			return null;
		}
	}
	
	public static boolean updateDatabase(String connectionURL, String server, String logFile, String originalSearchTerm, String searchTerm, String EventData) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(connectionURL);
			Statement stmt = con.createStatement();
			String SQL = "update EventLog set RecordNumber = \'"
					+ StringEscapeUtils.escapeSql(searchTerm) + "\',EventDescription = \'"
					+ StringEscapeUtils.escapeSql(EventData)
					+ "\' where Host = '" + server + "' and EventSource = '"
					+ logFile + "' and EventID = '" + StringEscapeUtils.escapeSql(originalSearchTerm) + "';";
			log.fine(SQL);
			stmt.executeUpdate(SQL);
			con.close();
			return true;
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, "Error occured while updating database: ", e);
			return false;
		} catch (SQLException e) {
			log.log(Level.SEVERE, "Error occured while updating database: ", e);
			return false;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error occured while updating database: ", e);
			return false;
		}
	}
}