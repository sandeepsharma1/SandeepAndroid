package com.paradigmcreatives.rajiworld.sql;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;

/**
 * Looks up the DataSource (see configuration in context.xml)
 * and uses that DataSource to get a database connection
 * from the connection pool
 * @author Rajeswari | Paradigm Creatives
 *
 */
public class ConnectionPool
{
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
 
    private ConnectionPool()
    {
        try
        {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/rajiworld");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /** 
     * Get an instance of the connection pool
     * @return ConnectionPool
     */
    public static ConnectionPool getInstance()
    {
        if (pool == null)
        {
            pool = new ConnectionPool();
        }
        return pool;
    }

    /**
     * Get a connection
     * @return Connection
     */
    public Connection getConnection()
    {
        try
        {
            return dataSource.getConnection();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }
    
    /**
     * Free a connection
     * @param c
     */
    public void freeConnection(Connection c)
    {
        try
        {
            c.close();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}