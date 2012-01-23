package ru.brandanalyst.core.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 22.01.12
 * Time: 21:35
 */
public class SSHTunnelDataSource implements DataSource , InitializingBean {
    private static final Logger log = Logger.getLogger(SSHTunnelDataSource.class);

    protected JSch jsch;
    protected Session session;
    protected String host;
    protected String user;
    protected String pass;
    protected int port;
    protected int tunnelLocalPort;
    protected String tunnelRemoteHost;
    protected int tunnelRemotePort;
    protected String username;
    protected String password;
    protected String url;

    public void setHost(String host) {
        this.host = host;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTunnelLocalPort(int tunnelLocalPort) {
        this.tunnelLocalPort = tunnelLocalPort;
    }

    public void setTunnelRemoteHost(String tunnelRemoteHost) {
        this.tunnelRemoteHost = tunnelRemoteHost;
    }

    public void setTunnelRemotePort(int tunnelRemotePort) {
        this.tunnelRemotePort = tunnelRemotePort;
    }

    public void close(){
        session.disconnect();
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException{
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public <T> T unwrap(Class<T> iface)throws SQLException{
        throw new SQLException("SSHTunnelDataSource is not a wrapper.");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection getConnection() throws SQLException{
        return getConnection(username,password);
    } 
     //     Attempts to establish a connection with the data source that this DataSource object represents.
    public Connection getConnection(String username, String password)throws SQLException{
        return DriverManager.getConnection(url, username, password);
    } 
       //   Attempts to establish a connection with the data source that this DataSource object represents.
    public int getLoginTimeout(){
        return session.getTimeout();
    } 
        //  Gets the maximum time in seconds that this data source can wait while attempting to connect to a database.
    public PrintWriter getLogWriter(){
        return null;
    } 
         // Retrieves the log writer for this DataSource object.
    public void	setLoginTimeout(int seconds){
        try{
            session.setTimeout(seconds);
        }
        catch(Exception e){
            log.error("Ошибка при установке таймаута", e);
        }
    } 

    public void	setLogWriter(PrintWriter out){
        
    } 

    @Override
    public void afterPropertiesSet(){
        jsch = new JSch();
        if(null==session || !session.isConnected()){
            try{
                session=jsch.getSession(user, host, port);
                session.setPassword(pass);

                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);

                log.info("Открытие ssh соединения");
                session.connect();
                log.info("Устанавливаем туннель");
                session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
            }
            catch(Exception e){
                log.error("Ошибка при построении соединения", e);
            }
        }
    }
}
