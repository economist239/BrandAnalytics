package ru.brandanalyst.core.db.provider.mysql;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 22.01.12
 * Time: 21:35
 */
public class SSHTunnelDataSource extends BasicDataSource implements InitializingBean {
    private static final Logger log = Logger.getLogger(SSHTunnelDataSource.class);
    private static boolean isConnected = false;
    protected JSch jsch;
    protected Session session;
    protected String host;
    protected String sshPassword;
    protected String sshUsername;
    protected int port;
    protected int tunnelLocalPort;
    protected String tunnelRemoteHost;
    protected int tunnelRemotePort;

    @Required
    public void setHost(String host) {
        this.host = host;
    }

    @Required
    public void setPort(int port) {
        this.port = port;
    }

    @Required
    public void setTunnelLocalPort(int tunnelLocalPort) {
        this.tunnelLocalPort = tunnelLocalPort;
    }

    @Required
    public void setTunnelRemoteHost(String tunnelRemoteHost) {
        this.tunnelRemoteHost = tunnelRemoteHost;
    }

    @Required
    public void setTunnelRemotePort(int tunnelRemotePort) {
        this.tunnelRemotePort = tunnelRemotePort;
    }

    public void close() {
        session.disconnect();
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Required
    public void setSshUsername(String sshUsername) {
        this.sshUsername = sshUsername;
    }

    @Required
    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    @Override
    public void afterPropertiesSet() {
        jsch = new JSch();
        if(!isConnected){
            if (null == session || !session.isConnected()) {
                try {
                    session = jsch.getSession(sshUsername, host, port);
                    session.setPassword(sshPassword);

                    java.util.Properties config = new java.util.Properties();
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);

                    log.info("Открытие ssh соединения");
                    session.connect();
                    log.info("Tunnel status: " + session.isConnected());
                    log.info("Устанавливаем туннель");
                    session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
                } catch (Exception e) {
                    log.error("Ошибка при построении соединения", e);
                    System.exit(1);
                }
            }
            isConnected = true;
        }
    }
}
