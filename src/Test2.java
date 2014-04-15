import java.util.List;

import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.User;
import com.iiitb.model.UserGroup;
import com.iiitb.model.UserRole;
import com.iiitb.util.ConnectionPool;


public class Test2
{
	public static void main(String[] args)
	{
		
		List<DocFragmentInfo> dfil;
		
		com.db4o.ObjectContainer c=ConnectionPool.getConnection();
		
		dfil=c.queryByExample(DocFragmentInfo.class);
		dfil.get(0).getWorkflowInstance().setCurrentActivityName("review");
		c.store(dfil.get(0));
		
		ConnectionPool.freeConnection(c);
		System.out.println("done");
		
	}
}
