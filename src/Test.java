import com.iiitb.model.User;
import com.iiitb.model.UserGroup;
import com.iiitb.model.UserRole;
import com.iiitb.util.ConnectionPool;


public class Test
{
	public static void main(String[] args)
	{
		
		User u;
		
		com.db4o.ObjectContainer c=ConnectionPool.getConnection();
		
		UserRole admin=new UserRole("admin");
		UserRole teamMember=new UserRole("team member");
		UserRole teamLead=new UserRole("team lead");
		
		UserGroup project1=new UserGroup("Project 1");
		UserGroup project2=new UserGroup("Project 2");
		
		u=new User("admin", "admin", "admin", "admin", null);
		u.setRole(admin);
		c.store(u);
		
		u=new User("u1", "u1", "u1", "u1", null);
		u.setRole(teamMember);
		u.setGroup(project1);
		c.store(u);
		
		u=new User("u2", "u2", "u2", "u2", null);
		u.setRole(teamMember);
		u.setGroup(project1);
		c.store(u);
		
		
		u=new User("u3", "u3", "u3", "u3", null);
		u.setRole(teamLead);
		u.setGroup(project1);
		c.store(u);
		
		u=new User("u4", "u4", "u4", "u4", null);
		u.setRole(teamLead);
		u.setGroup(project2);
		c.store(u);
		
		ConnectionPool.freeConnection(c);
		System.out.println("admin created");
		
	}
}
