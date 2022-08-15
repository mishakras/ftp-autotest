/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        FileEntry fileEntry = new FileEntry("/test.json", students.toString());
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
            fail();
        }
    }
    @Test
    public void create_worker() {
        try {
            worker = Ftpclient.createworker(client);    
            Assert.assertEquals(worker.get_all_Students().length,3);
        } catch (IOException ex) {
            fail();
        } catch (ParseException ex) {
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
            Jsonworker worker2= new Jsonworker(students);
            Ftpclient.updateFTP(client, worker2.getstudents().toString());
            Jsonworker worker3 = Ftpclient.createworker(client);
            Assert.assertEquals(worker3.get_all_Students().length,2);
        } catch (IOException ex) {
            fail();
        }
    }
    @AfterTest
    public void tear_down(){
        fakeFtpServer.stop();
    }
}
