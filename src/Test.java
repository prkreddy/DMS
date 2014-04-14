import com.iiitb.model.User;
import com.iiitb.model.UserRole;
import com.iiitb.util.ConnectionPool;


public class Test
{
	public static void main(String[] args)
	{
		User u=new User("admin", "admin", "admin", "admin", null);
		UserRole r=new UserRole();r.setName("admin");
		u.setRole(r);
		
		com.db4o.ObjectContainer c=ConnectionPool.getConnection();
		c.store(u);
		ConnectionPool.freeConnection(c);
		System.out.println("admin created");
	}
}
