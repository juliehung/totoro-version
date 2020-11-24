package io.dentall.totoro.business.service;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.config.FtpConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("ftp")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
@Ignore
public class ImageFtpBusinessServiceIntTest {

    private static final String UPLOAD_FILENAME = "chrome.png";
    private static final String FTP_USER = "user";
    private static final String FTP_PASSWORD = "password";
    private static final String FTP_HOME = "/data";

    private FakeFtpServer fakeFtpServer;

    @Autowired
    private ImageBusinessService imageBusinessService;

    @Before
    public void setup() {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount(FTP_USER, FTP_PASSWORD, FTP_HOME));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry(FTP_HOME));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(FtpConfiguration.FTP_SERVER_PORT);

        fakeFtpServer.start();
    }

    @After
    public void teardown() {
        fakeFtpServer.stop();
    }

    @Test
    public void testUploadFile() throws IOException {
        FileInputStream stream = FileUtils.openInputStream(ResourceUtils.getFile(getClass().getResource("/static/" + UPLOAD_FILENAME)));
        imageBusinessService.uploadFile(FTP_HOME + "/", UPLOAD_FILENAME, stream, "image/png");

        FTPClient client = new FTPClient();
        client.connect("localhost", fakeFtpServer.getServerControlPort());
        client.login(FTP_USER, FTP_PASSWORD);
        String[] files = client.listNames(FTP_HOME);
        assertThat(files).contains(UPLOAD_FILENAME);
    }
}
