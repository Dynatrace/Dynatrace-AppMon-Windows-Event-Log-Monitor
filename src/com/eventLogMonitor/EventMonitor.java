package com.eventLogMonitor;

import java.util.Collection;
import java.util.Properties;

import com.dynatrace.diagnostics.pdk.*;

public class EventMonitor implements Monitor {

	private static final String CONFIG_EVENT_LOG = "EventLog";
	private static final String CONFIG_SEARCH_TERM = "SearchTerm";
	private static final String CONFIG_SQL_SERVER = "SQLServer";

	private static final String CONFIG_SQL_DATABASE = "SQLDatabase";
	private static final String CONFIG_SQL_PORT = "SQLPort";
	private static final String CONFIG_SQL_USER = "SQLUser";
	private static final String CONFIG_SQL_PASS = "SQLPassword";
	private static final String METRIC_GROUP = "Windows Event Log Monitor";
	private static final String MSR_MSG = "New Message";

	private static final String CONFIG_MAIL_FROM = "MailFrom";
	private static final String CONFIG_MAIL_TO = "MailTo";
	private static final String CONFIG_MAIL_HOST = "MailHost";
	private static final String CONFIG_MAIL_SUBJECT = "MailSubject";
	
	
	private String logFile = "";
	private String searchTerm = "";
	private String sqlServer = "";
	private String sqlDatabase = "";
	private String sqlUser = "";
	private String sqlPass = "";
	private String sqlPort = "";
	private String server = "";
	private String connectionURL = "";
	private String originalSearchTerm = "";
	private boolean newRecord = false;
	private Properties mailprops=new Properties();

	@Override
	public Status setup(MonitorEnvironment env) throws Exception {
		Status result = new Status(Status.StatusCode.Success);
		newRecord = false;

		// Setup initial variable configuration
		originalSearchTerm = env.getConfigString(CONFIG_SEARCH_TERM);
		logFile = env.getConfigString(CONFIG_EVENT_LOG);
		sqlServer = env.getConfigString(CONFIG_SQL_SERVER);
		sqlDatabase = env.getConfigString(CONFIG_SQL_DATABASE);
		sqlUser = env.getConfigString(CONFIG_SQL_USER);
		sqlPass = env.getConfigPassword(CONFIG_SQL_PASS);
		sqlPort = env.getConfigString(CONFIG_SQL_PORT);
		server = env.getHost().getAddress();
		connectionURL = "jdbc:sqlserver://" + sqlServer + ":" + sqlPort
				+ ";databaseName=" + sqlDatabase + ";username=" + sqlUser
				+ ";password=" + sqlPass + ";";
		searchTerm = EventMonitorUtils.getCurrentSearchTerm(connectionURL,server,logFile,originalSearchTerm);
		
		mailprops.setProperty("from", env.getConfigString(CONFIG_MAIL_FROM));
		mailprops.setProperty("to", env.getConfigString(CONFIG_MAIL_TO));
		mailprops.setProperty("host", env.getConfigString(CONFIG_MAIL_HOST));
		mailprops.setProperty("subject", env.getConfigString(CONFIG_MAIL_SUBJECT));
		
		return result;
	}

	@Override
	public Status execute(MonitorEnvironment env) throws Exception {
		Status result = new Status(Status.StatusCode.Success);
		
		newRecord = false;
		String wevtutilResult = EventMonitorUtils.executeWevtutilQuery(logFile, searchTerm, server);
		if(wevtutilResult == null)
		{
			searchTerm = EventMonitorUtils.getCurrentSearchTerm(connectionURL,server,logFile,originalSearchTerm);
			result = new Status(Status.StatusCode.ErrorInternal);
			result.setMessage("An error has occured while executing wevtutil.  Please check the logs for more information.");
			return result;
		}
		
		String[] searchEvents = EventMonitorUtils.calculateNewEvents(wevtutilResult,searchTerm, connectionURL,server,logFile,originalSearchTerm,mailprops);
		String searchTerm2=searchEvents[0];
		if(searchTerm2 == null)
		{
			searchTerm = EventMonitorUtils.getCurrentSearchTerm(connectionURL,server,logFile,originalSearchTerm);
			result = new Status(Status.StatusCode.ErrorInternal);
			result.setMessage("An error has occured in the processing of the XML.  Please check the logs for more information.");
			return result;
		}
		else if(!searchTerm2.equals(searchTerm))
		{
			newRecord = true;
			// result = new Status(Status.StatusCode.PartialSuccess);
			result.setMessage("New Message Found:\n" + wevtutilResult);
			searchTerm = searchTerm2;
		}
		Collection<MonitorMeasure> measures;
	 	if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_MSG)) != null) {
	 	for (MonitorMeasure measure : measures)
	 		measure.setValue(newRecord ? new Integer(searchEvents[1]).intValue() : 0);
	 	}
		return result;
	}

	@Override
	public void teardown(MonitorEnvironment env) throws Exception {
	}
	
	
}
