/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testFtpclient;

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

    static FakeFtpServer fakeFtpServer=new FakeFtpServer();

    
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

    }
    @Test
    public void create_worker() {

    }
    @Test
    public void updateFTP()  {
        
    }
    @AfterTest
    public void tear_down(){
        fakeFtpServer.stop();
    }
}
