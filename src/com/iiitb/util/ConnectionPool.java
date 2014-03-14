package com.iiitb.util;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class ConnectionPool
{
	public static ObjectContainer getConnection()
	{
		return Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DMSConstants.db4oPath+DMSConstants.db4oFileName);
	}
	
	public static void freeConnection(ObjectContainer c)
	{
		c.close();
	}
}
