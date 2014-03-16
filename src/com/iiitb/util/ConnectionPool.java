package com.iiitb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.model.User;

public class ConnectionPool
{
	public static ObjectContainer getConnection()
	{
		EmbeddedConfiguration config=Db4oEmbedded.newConfiguration();
		config.common().objectClass(User.class).cascadeOnActivate(true);
		config.common().objectClass(DocFragment.class).cascadeOnActivate(true);
		config.common().objectClass(DocFragmentInfo.class).cascadeOnActivate(true);
		config.common().objectClass(DocFragmentVersionInfo.class).cascadeOnActivate(true);
		config.common().objectClass(ArrayList.class).cascadeOnActivate(true);
		config.common().objectClass(Set.class).cascadeOnActivate(true);
		config.common().objectClass(HashMap.class).cascadeOnActivate(true);
		return Db4oEmbedded.openFile(config, DMSConstants.db4oPath+DMSConstants.db4oFileName);
	}
	
	public static void freeConnection(ObjectContainer c)
	{
		c.close();
	}
}
