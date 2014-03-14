package com.iiitb.dao;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.iiitb.model.User;
import com.iiitb.util.DMSConstants;

public class UserDao
{
	public UserDao()
	{
		createDb4oDirectory();
		createDb4oDatabaseFile();
	}

	public boolean createDb4oDirectory()
	{

		File file = new File(DMSConstants.db4oPath);

		if (!file.exists())
		{
			if (file.mkdirs())
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		return true;

	}

	public boolean createDb4oDatabaseFile()
	{
		File file = new File(DMSConstants.db4oPath + DMSConstants.db4oFileName);

		if (!file.exists())
		{
			try
			{
				if (file.createNewFile())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return true;
	}

	public boolean isUserNameExists(String username)
	{

		ObjectContainer container = Db4oEmbedded.openFile(DMSConstants.db4oPath + DMSConstants.db4oFileName);
		try
		{
			Query query = container.query();
			query.constrain(User.class);
			List<User> result = query.execute();
			for (User user : result)
			{
				if (user.getUsername().equals(username))
				{
					return true;
				}

			}

			// List<User> pilots = container.query(new Predicate<User>()
			// {
			// public boolean match(User user)
			// {
			// return user.getUsername().equals(username);
			// }
			// });

		}
		finally
		{
			container.close();
		}

		return false;
	}

	public void listAllUsers()
	{

		ObjectContainer container = Db4oEmbedded.openFile(DMSConstants.db4oPath + DMSConstants.db4oFileName);
		try
		{
			Query query = container.query();
			query.constrain(User.class);
			List<User> result = query.execute();
			for (User user : result)
			{
				System.out.println("userName: " + user.getUsername());
				System.out.println("password: " + user.getPassword());
				System.out.println("name: " + user.getName());
				System.out.println("email: " + user.getEmail());
				System.out.println("loginDate: " + user.getLastLoginDate());

			}
		}
		finally
		{
			container.close();
		}
	}

	public User storeUser(String name, String username, String password, String email)
	{

		User user = null;

		ObjectContainer container = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DMSConstants.db4oPath + DMSConstants.db4oFileName);
		try
		{
			user = new User(name, username, password, email, new Date());
			container.store(user);
		}
		catch (Exception ex)
		{
			user = null;
		}
		finally
		{
			container.close();
		}

		return user;
	}

	public User getUser(String username)
	{

		ObjectContainer container = Db4oEmbedded.openFile(DMSConstants.db4oPath + DMSConstants.db4oFileName);
		try
		{
			Query query = container.query();
			query.constrain(User.class);
			List<User> result = query.execute();
			for (User user : result)
			{
				if (user.getUsername().equals(username))
				{
					return user;
				}

			}

		}
		finally
		{
			container.close();
		}

		return null;
	}

}
