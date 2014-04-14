package com.iiitb.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.internal.BlobImpl;
import com.db4o.types.Blob;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.UserGroup;
import com.iiitb.model.UserRole;
import com.iiitb.model.DocFragmentInfo.Access;
import com.iiitb.model.DocumentType;
import com.iiitb.model.DocFragmentInfo.FileFormat;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.model.DocFragmentVersionInfo.Action;
import com.iiitb.model.User;

public class ConnectionPool
{
	public static ObjectContainer getConnection()
	{
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(User.class).cascadeOnActivate(true);
		config.common().objectClass(DocFragment.class).cascadeOnActivate(true);
		config.common().objectClass(DocFragmentInfo.class).cascadeOnActivate(true);
		config.common().objectClass(DocFragmentVersionInfo.class).cascadeOnActivate(true);
		config.common().objectClass(ArrayList.class).cascadeOnActivate(true);
		config.common().objectClass(Set.class).cascadeOnActivate(true);
		config.common().objectClass(HashMap.class).cascadeOnActivate(true);
		config.common().objectClass(LinkedHashMap.class).cascadeOnActivate(true);
		config.common().objectClass(Blob.class).cascadeOnActivate(true);
		config.common().objectClass(BlobImpl.class).cascadeOnActivate(true);
		config.common().objectClass(String.class).cascadeOnActivate(true);
		config.common().objectClass(Date.class).cascadeOnActivate(true);
		config.common().objectClass(Access.class).cascadeOnActivate(true);
		config.common().objectClass(DocumentType.class).cascadeOnActivate(true);
		config.common().objectClass(FileFormat.class).cascadeOnActivate(true);
		config.common().objectClass(Action.class).cascadeOnActivate(true);
		config.common().objectClass(File.class).cascadeOnActivate(true);
		config.common().objectClass(UserRole.class).cascadeOnActivate(true);
		config.common().objectClass(UserGroup.class).cascadeOnActivate(true);
		
		config.common().objectClass(User.class).cascadeOnUpdate(true);
		config.common().objectClass(DocFragment.class).cascadeOnUpdate(true);
		config.common().objectClass(DocFragmentInfo.class).cascadeOnUpdate(true);
		config.common().objectClass(DocFragmentVersionInfo.class).cascadeOnUpdate(true);
		config.common().objectClass(ArrayList.class).cascadeOnUpdate(true);
		config.common().objectClass(Set.class).cascadeOnUpdate(true);
		config.common().objectClass(HashMap.class).cascadeOnUpdate(true);
		config.common().objectClass(LinkedHashMap.class).cascadeOnUpdate(true);
		config.common().objectClass(Blob.class).cascadeOnUpdate(true);
		config.common().objectClass(BlobImpl.class).cascadeOnUpdate(true);
		config.common().objectClass(String.class).cascadeOnUpdate(true);
		config.common().objectClass(Date.class).cascadeOnUpdate(true);
		config.common().objectClass(Access.class).cascadeOnUpdate(true);
		config.common().objectClass(DocumentType.class).cascadeOnUpdate(true);
		config.common().objectClass(FileFormat.class).cascadeOnUpdate(true);
		config.common().objectClass(Action.class).cascadeOnUpdate(true);
		config.common().objectClass(File.class).cascadeOnUpdate(true);
		config.common().objectClass(UserRole.class).cascadeOnUpdate(true);
		config.common().objectClass(UserGroup.class).cascadeOnUpdate(true);
		try
		{
			Db4o.configure().setBlobPath(DMSConstants.db4oPath + "blobs");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Db4oEmbedded.openFile(config, DMSConstants.db4oPath + DMSConstants.db4oFileName);
	}

	public static void freeConnection(ObjectContainer c)
	{
		c.close();
	}
}
