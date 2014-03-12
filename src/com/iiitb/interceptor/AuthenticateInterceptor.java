package com.iiitb.interceptor;

import java.util.Map;

import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticateInterceptor implements Interceptor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1572249206604664461L;

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception
	{

		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();

		if (session.get(DMSConstants.USER_LOGGED_IN) != null)
		{
			return actionInvocation.invoke();
		}
		else
		{
			return ActionSupport.LOGIN;
		}

	}
}
