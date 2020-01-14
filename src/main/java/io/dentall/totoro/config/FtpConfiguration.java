package io.dentall.totoro.config;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("ftp")
@Configuration
public class FtpConfiguration {

    private final FTPClient ftpClient = new FTPClient();

    public final static String FTP_SERVER_ADDR = System.getenv("TTR_F_U");

    public final static String FTP_ACCOUNT = System.getenv("TTR_F_A");

    public final static String FTP_SECRET = System.getenv("TTR_F_S");

    public final static int MIN_PORT =  Integer.parseInt(System.getenv("TTR_F_B_P"));

    public final static int MAX_PORT = Integer.parseInt(System.getenv("TTR_F_T_P"));

    public final static int FTP_SERVER_PORT = Integer.parseInt(System.getenv("TTR_F_P"));

    public FTPClient getFtpClient() {
        return ftpClient;
    }
}
