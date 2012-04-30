package ru.brandanalyst.monitoring;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * User: daddy-bear
 * Date: 30.04.12
 * Time: 12:28
 */
public class RemoteTopSnapshot {
    private final static Logger log = Logger.getLogger(RemoteTopSnapshot.class);

    private final static int DEFAULT_PORT = 22;

    private String host;
    private int port = DEFAULT_PORT;
    private String username;
    private String password;

    @Required
    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Required
    public void setUsername(String username) {
        this.username = username;
    }

    @Required
    public void setPassword(String password) {
        this.password = password;
    }

    public String buildSnapshot() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
            channelExec.setCommand("top");
            channelExec.connect();
            channelExec.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
            System.out.println(br.readLine());
        } catch (JSchException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }

        return "";
    }

}
