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
		u.setRole(teamMember);
		u.setGroup(project1);
		c.store(u);
		
		
		u=new User("u4", "u4", "u4", "u4", null);
		u.setRole(teamMember);
		u.setGroup(project1);
		c.store(u);
		
		
		
		u=new User("tl1", "tl1", "tl1", "tl1", null);
		u.setRole(teamLead);
		u.setGroup(project1);
		c.store(u);
		
		
		u=new User("tl2", "tl2", "tl2", "tl2", null);
		u.setRole(teamLead);
		u.setGroup(project1);
		c.store(u);
		
		u=new User("tl3", "tl3", "tl3", "tl3", null);
		u.setRole(teamLead);
		u.setGroup(project1);
		c.store(u);
		
		
		u=new User("tl4", "tl4", "tl4", "tl4", null);
		u.setRole(teamLead);
		u.setGroup(project1);
		c.store(u);
		
		u=new User("u21", "u21", "u21", "u21", null);
		u.setRole(teamMember);
		u.setGroup(project2);
		c.store(u);
		
		u=new User("u22", "u22", "u22", "u22", null);
		u.setRole(teamMember);
		u.setGroup(project2);
		c.store(u);
		
		u=new User("u23", "u23", "u23", "u23", null);
		u.setRole(teamMember);
		u.setGroup(project2);
		c.store(u);
		
		
		u=new User("u24", "u24", "u24", "u24", null);
		u.setRole(teamMember);
		u.setGroup(project2);
		c.store(u);
		
		
		
		u=new User("tl21", "tl21", "tl21", "tl21", null);
		u.setRole(teamLead);
		u.setGroup(project2);
		c.store(u);
		
		
		u=new User("tl22", "tl22", "tl22", "tl22", null);
		u.setRole(teamLead);
		u.setGroup(project2);
		c.store(u);
		
		u=new User("tl23", "tl23", "tl23", "tl23", null);
		u.setRole(teamLead);
		u.setGroup(project2);
		c.store(u);
		
		
		u=new User("tl24", "tl24", "tl24", "tl24", null);
		u.setRole(teamLead);
		u.setGroup(project2);
		c.store(u);
		
		
		ConnectionPool.freeConnection(c);
		System.out.println("admin created");
		
	}
}
