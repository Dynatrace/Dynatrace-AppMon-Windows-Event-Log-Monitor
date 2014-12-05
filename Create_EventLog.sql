USE [dynaTracePluginDB]
GO

/****** Object:  Table [dbo].[EventLog]    Script Date: 8/7/2014 12:27:46 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EventLog](
	[RecordNumber] [nvarchar](max) NULL,
	[EventID] [nvarchar](max) NULL,
	[Host] [nvarchar](max) NULL,
	[EventSource] [nvarchar](max) NULL,
	[EventDescription] [nvarchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO