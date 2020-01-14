package io.dentall.totoro.business.service;

import io.dentall.totoro.config.FtpConfiguration;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Profile("ftp")
@Service
public class FtpClientService {

    private static Logger logger = LoggerFactory.getLogger(FtpClientService.class);

    static final int FTP_COULD_NOT_CREATE_FILE = 553;

    private FTPClient ftpClient;

    public FtpClientService(FtpConfiguration ftpConfiguration) {
        this.ftpClient = ftpConfiguration.getFtpClient();
    }

    void connect() throws IOException {
        logger.debug("Ftp configurations {}\n{}\n{}\n{}\n{}\n{}",
            FtpConfiguration.FTP_SERVER_ADDR,
            FtpConfiguration.FTP_SERVER_PORT,
            FtpConfiguration.FTP_ACCOUNT,
            FtpConfiguration.FTP_SECRET,
            FtpConfiguration.MIN_PORT,
            FtpConfiguration.MAX_PORT);

        ftpClient.connect(FtpConfiguration.FTP_SERVER_ADDR, FtpConfiguration.FTP_SERVER_PORT);
        ftpClient.login(FtpConfiguration.FTP_ACCOUNT, FtpConfiguration.FTP_SECRET);
        ftpClient.setActivePortRange(FtpConfiguration.MIN_PORT, FtpConfiguration.MAX_PORT);
        ftpClient.enterLocalPassiveMode();

        logger.info("Connect to ftp server with reply: {}", ftpClient.getReplyString());
    }


    void mkdir(String remotePath) throws IOException {
        logger.info("Try to make directory{}", remotePath);

        ftpClient.makeDirectory(remotePath);

        logger.info("Make directory to ftp server with reply: {}", ftpClient.getReplyString());
    }

    public int upload(String remotePath, String remoteFileName, InputStream inputStream) throws IOException {
        logger.info("Try to upload file {}{}", remotePath, remoteFileName);

        ftpClient.setFileType(FTPSClient.BINARY_FILE_TYPE);
        ftpClient.storeFile(remotePath.concat(remoteFileName), inputStream);

        logger.info("Upload file to ftp server with reply: {}", ftpClient.getReplyString());
        return ftpClient.getReplyCode();
    }

    public int disconnect() {
        try {
            ftpClient.logout();
            ftpClient.getReplyCode();
            logger.info("Logout from ftp server with reply: {}", ftpClient.getReplyString());
        } catch (IOException e) {
            logger.error("Logout from ftp server error:\n{}", e.getMessage());
        }

        int replyCode;
        try {
            ftpClient.disconnect();
            replyCode = ftpClient.getReplyCode();
            logger.info("Logout from ftp server with reply: {}", ftpClient.getReplyString());
        } catch (IOException e) {
            logger.error("Logout from ftp server error:\n{}", e.getMessage());
            return 500;
        }

        return replyCode;
    }
}
