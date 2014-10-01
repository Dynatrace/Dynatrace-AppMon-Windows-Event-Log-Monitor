<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Windows Event Log Monitor</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <meta content="Scroll Wiki Publisher" name="generator"/>
    <link type="text/css" rel="stylesheet" href="css/blueprint/liquid.css" media="screen, projection"/>
    <link type="text/css" rel="stylesheet" href="css/blueprint/print.css" media="print"/>
    <link type="text/css" rel="stylesheet" href="css/content-style.css" media="screen, projection, print"/>
    <link type="text/css" rel="stylesheet" href="css/screen.css" media="screen, projection"/>
    <link type="text/css" rel="stylesheet" href="css/print.css" media="print"/>
</head>
<body>
                <h1>Windows Event Log Monitor</h1>
    <div class="section-2"  id="174469104_WindowsEventLogMonitor-Overview"  >
        <h2>Overview</h2>
    <p>
    </p>
    <p>
            <img src="images_community/download/attachments/174469104/icon.png" alt="images_community/download/attachments/174469104/icon.png" class="confluence-embedded-image" />
        The Windows Event Log Plugin will monitor the Windows Event Log for the existance of a given event. It stores the event details in a SQL database so that it knows where it ended the last time it ran so it's not seeing the same events over and over.    </p>
    <div class="tablewrap">
        <table>
<thead class=" "></thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Name and Version    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Windows Event Log Monitor    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Compatible with    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
dynaTrace 5.x, 6.x    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Author    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Derek Abing    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
<a href="https://community/display/DL/Licenses">License</a>    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="attachments_5275722_2_dynaTraceBSD.txt">dynaTrace BSD</a>    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
<a href="https://community/display/DL/Support+Levels">Support Level</a>    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="https://community/display/DL/Support+Levels">Community Supported</a>    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Download    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="attachments_174754202_1_com.eventLogMonitor_1.5.0.jar">com.eventLogMonitor_1.5.0.jar</a>    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Screenshots    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    </p>
    <div class="tablewrap">
        <table>
<thead class=" "></thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/174469104/EventLog_table_design.jpg" alt="images_community/download/attachments/174469104/EventLog_table_design.jpg" class="" />
            </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Properties.jpg" alt="images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Properties.jpg" class="" />
            </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
                </td>
                <td rowspan="1" colspan="1">
                </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Measure.jpg" alt="images_community/download/attachments/174469104/Windows_Event_Log_Monitor_Measure.jpg" class="" />
            </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/174469104/Search_Term.jpg" alt="images_community/download/attachments/174469104/Search_Term.jpg" class="" />
            </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
                </td>
                <td rowspan="1" colspan="1">
                </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/174469104/Search_Term_XML.jpg" alt="images_community/download/attachments/174469104/Search_Term_XML.jpg" class="" />
            </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/174469104/Search_Term_XML_Change.jpg" alt="images_community/download/attachments/174469104/Search_Term_XML_Change.jpg" class="" />
            </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
                </td>
                <td rowspan="1" colspan="1">
                </td>
        </tr>
</tbody>        </table>
            </div>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Installation    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Import the plugin on the dynaTrace Server. For more details see <a href="https://community/display/DOCDT60/Plugins">Plugins</a>    </p>
    <p>
The table in the database can be created by running <a href="attachments_178488265_1_Create_EventLog.sql">this SQL script </a>or you can use the screenshot from the Design view in the table to manually enter the values. The script will create the table on a database called dynaTracePluginDB. Change this name if you desire    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    </div>
    <div class="section-2"  id="174469104_WindowsEventLogMonitor-"  >
        <h2></h2>
    </div>
    <div class="section-2"  id="174469104_WindowsEventLogMonitor-MonitorProperties"  >
        <h2>Monitor Properties</h2>
    <p>
When setting up the monitor you need to define the following configuration properties    </p>
    <div class="tablewrap">
        <table>
<thead class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Property    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Description    </p>
            </td>
        </tr>
</thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
EventLog    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Enter which Event Log the event is in (i.e. Application, System, Security, etc).    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Search Term    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
The search term obtained from the XML in windows event log. You can either tweak the values from the below example. Otherwise, in order to obtain the Search Term in regex format, you have to log onto the server, apply the filter (as seen in the image) and then click on the XML tab which will show you the XML of the filter. Remove everything from the filter (as seen in the image) and you'll want to make sure your Date/Time is set to greater than (&quot;&gt;&quot;). That way the first time the Monitor runs it will start with the records after that date and will keep track of where it left off going forward.    </p>
    <p>
    </p>
    <p>
EXAMPLE: *[System[Provider[@Name='eventlog' or @Name='Microsoft-Windows-Eventlog'] and (EventID=6008) and TimeCreated[@SystemTime&gt;'2014-01-13T06:00:00.000Z']]]    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
SQLServer    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Enter the name of you SQL Database Server where you created the dynaTracePluginDB database.    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Username    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Enter the username to connect to the database. (SQL Server Authentication)    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Password    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Enter password for username used. (SQL Server Authentication)    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    </div>
    <div class="section-2"  id="174469104_WindowsEventLogMonitor-MonitoringMeasures"  >
        <h2>Monitoring Measures</h2>
    <div class="tablewrap">
        <table>
<thead class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Measure    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Description    </p>
            </td>
        </tr>
</thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
New Message    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
If a new event log message is found this number will be 1, otherwise it will be 0    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    </div>
            </div>
        </div>
        <div class="footer">
        </div>
    </div>
</body>
</html>
