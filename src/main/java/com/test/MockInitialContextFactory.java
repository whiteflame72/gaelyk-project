package com.test;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
/**
 * Provides a JNDI initial context factory for the MockContext.
 * Reference: http://amazing-development.com/archives/2006/07/24/mocking-context-lookups/
 *
 */
public class MockInitialContextFactory //implements InitialContextFactory 
{
	class Context {
	}
    private static Context mockCtx = null;
    public static void setMockContext(Context ctx) {
        mockCtx = ctx;
    }   
    public Context getInitialContext(java.util.Hashtable< ?, ?> environment) //throws NamingException
    {
        if (mockCtx == null) {
            throw new IllegalStateException("mock context was not set.");
        }
        return mockCtx;
    }
}