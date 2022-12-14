package testFtpclient;

import com.mycompany.ftpclient.Ftpclient;
import com.mycompany.jsonworker.Jsonworker;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import static org.testng.Assert.fail;
import org.testng.annotations.AfterTest;

/**
 *
 * @author misha
 */
public class TestFtpclient {
    static FTPClient client = new FTPClient();
    static FakeFtpServer fakeFtpServer=new FakeFtpServer();
    static Jsonworker worker;
    
    @BeforeTest
    public void startup() {
        fakeFtpServer.setServerControlPort(0);
        FileSystem fileSystem = new UnixFakeFileSystem();
        DirectoryEntry directoryEntry = new DirectoryEntry("/");
        directoryEntry.setPermissionsFromString("rwxrwxrwx");
        JSONObject students = new JSONObject();
        JSONArray arr = new JSONArray();
        JSONObject student = new JSONObject();
        student.put("id", 1);
        student.put("name", "aaaa");
        arr.add(student);
        student = new JSONObject();
        student.put("id", 2);
        student.put("name", "bbbb");
        arr.add(student);
        student = new JSONObject();
        student.put("id", 3);
        student.put("name", "aaabbbb");
        arr.add(student);
        students.put("students", arr);
        FileEntry fileEntry = new FileEntry("/students.json", students.toString());
        fileSystem.add(directoryEntry);
        fileSystem.add(fileEntry);
        fakeFtpServer.setFileSystem(fileSystem);
        UserAccount userAccount = new UserAccount("misha", "1111", "/");
        fakeFtpServer.addUserAccount(userAccount);
        fakeFtpServer.start();
    }
    
    @Test
    public void connect() {
        String args[] = {"localhost","misha","1111", String.valueOf(fakeFtpServer.getServerControlPort())};
        try {
            Ftpclient.connect(args, client);    
            Assert.assertEquals(fakeFtpServer.getSystemStatus(),"Connected");
        } catch (IOException ex) {
            System.out.println("Connection failed");
            fail();
        }
    }
    @Test
    public void create_worker() {
        try {
            worker = Ftpclient.createworker(client);    
            Assert.assertEquals(worker.get_all_Students().length,3);
        } catch (IOException ex) {
            System.out.println("IOException failure");
            fail();
        } catch (ParseException ex) {
            System.out.println("ParseException failure");
            fail();
        }
    }
    @Test
    public void updateFTP()  {
        try {
            JSONObject students = new JSONObject();
            JSONArray arr = new JSONArray();
            JSONObject student = new JSONObject();
            student.put("id", 1);
            student.put("name", "aaaa");
            arr.add(student);
            student = new JSONObject();
            student.put("id", 2);
            student.put("name", "bbbb");
            arr.add(student);
            students.put("students", arr);
            Jsonworker worker2 = new Jsonworker(students);
            if (!Ftpclient.updateFTP(client, worker2.getstudents().toString()))
                fail();
        } catch (IOException ex) {
            System.out.println("IOException failure");
            fail();
        }
    }
    @AfterTest
    public void tear_down(){
        fakeFtpServer.stop();
    }
}
