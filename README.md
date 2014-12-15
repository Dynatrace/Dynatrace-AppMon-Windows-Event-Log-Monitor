# Windows Event Log Monitor

## Overview

![images_community/download/attachments/174469104/icon.png](images_community/download/attachments/174469104/icon.png) The Windows Event Log Plugin will monitor the Windows Event Log for the existance
of a given event. It stores the event details in a SQL database so that it knows where it ended the last time it ran so it's not seeing the same events over and over.

## Plugin Details


| Name | Windows Event Log Monitor
| :--- | :---
| Author |Derek Abing
| Supported dynaTrace Versions | >= 5.5 
| License | [dynaTrace BSD](dynaTraceBSD.txt)
| Support | [Community Supported](https://community.compuwareapm.com/community/display/DL/Support+Levels)
| Download | [com.eventLogMonitor_1.5.0.jar](com.eventLogMonitor_1.5.0.jar)

## Screenshots

![images_community/download/attachments/174469104/EventLog_table_design.jpg](images_community/download/attachments/174469104/EventLog_table_design.jpg)

![images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Properties.jpg](images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Properties.jpg)

![images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Measure.jpg](images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Measure.jpg)

![images_community/download/attachments/174469104/Search_Term.jpg](images_community/download/attachments/174469104/Search_Term.jpg)

![images_community/download/attachments/174469104/Search_Term_XML.jpg](images_community/download/attachments/174469104/Search_Term_XML.jpg)

![images_community/download/attachments/174469104/Search_Term_XML_Change.jpg](images_community/download/attachments/174469104/Search_Term_XML_Change.jpg)

## Installation

Import the plugin on the dynaTrace Server. For more details see [Plugins](https://community.compuwareapm.com/community/display/DOCDT61/Plugins)

The table in the database can be created by running [this SQL script ](Create_EventLog.sql)or you can use the screenshot from the Design view in the table to manually enter the
values. The script will create the table on a database called dynaTracePluginDB. Change this name if you desire

## Monitor Properties

When setting up the monitor you need to define the following configuration properties

| Property | Description
| :------- | :--------
| EventLog | Enter which Event Log the event is in (i.e. Application, System, Security, etc).
| Search Term | The search term obtained from the XML in windows event log. You can either tweak the values from the below example. Otherwise, in order to obtain the Search Term in regex format, you have to log onto
the server, apply the filter (as seen in the image) and then click on the XML tab which will show you the XML of the filter. Remove everything from the filter (as seen in the image) and you'll want to make sure your Date/Time is set to greater than (">"). That way the first time the Monitor runs it will start with the records after that date and will keep track of where it left off going forward.
| | EXAMPLE: *[System[Provider[@Name='eventlog' or @Name='Microsoft-Windows-Eventlog'] and (EventID=6008) and TimeCreated[@SystemTime>'2014-01-13T06:00:00.000Z']]]
| SQLServer | Enter the name of you SQL Database Server where you created the dynaTracePluginDB database.
| Username | Enter the username to connect to the database. (SQL Server Authentication)
| Password | Enter password for username used. (SQL Server Authentication)

## Monitoring Measures

| Measure | Description
| :------ | :--------
| New Message | If a new event log message is found this number will be 1, otherwise it will be 0

