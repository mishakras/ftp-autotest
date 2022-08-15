package com.mycompany.ftpclient;

import org.apache.commons.net.ftp.FTPClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import com.mycompany.jsonworker.Jsonworker;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author misha
 */
public class Ftpclient {
    public static FTPClient client = new FTPClient();
    
    public static void main(String[] args) throws Exception {
        run(args);
    }

    public static void run(String[] args) throws Exception {    
        String[] for_ftpclient= new String[args.length+1];
        for (Integer i = 0; i< args.length;i++)
            for_ftpclient[i] = args[i];
        for_ftpclient[for_ftpclient.length-1]="21";
        try {
            connect(for_ftpclient, client);
            Jsonworker worker = createworker(client);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Input 1 to enter passive mode, 2 to enter Active mode");
            String number = reader.readLine();
            switch (number) {
                case ("1"):
                    client.enterLocalPassiveMode();
                    break;
                case ("2"):
                    client.enterLocalActiveMode();
                    break;
            }
            print_instruction();
            boolean flag = true;
            while (flag) {
                number = reader.readLine();
                switch (number) {
                    case ("1"):
                        print_all_students(worker);
                        break;
                    case ("2"):
                        print_student(worker, Integer.parseInt(reader.readLine()));
                        break;
                    case ("3"):
                        add_student(worker, reader.readLine());
                        updateFTP(client, worker.getstudents().toString());
                        break;
                    case ("4"):
                        delete_student(worker, Integer.parseInt(reader.readLine()));
                        updateFTP(client, worker.getstudents().toString());
                        break;
                    case ("5"):
                        print_instruction();
                        break;
                    case ("6"):
                        flag = false;
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connect(String[] args, FTPClient client) throws IOException {
        client.connect(args[0], Integer.parseInt(args[3]));
        client.login(args[1], args[2]);
    }

    public static Jsonworker createworker(FTPClient client) throws IOException, ParseException{
        Jsonworker worker;
        try (InputStream loadinputStream = client.retrieveFileStream("students.json")) {
            System.out.println(client.stat());
            JSONParser jsonParser = new JSONParser();
            worker = new Jsonworker((JSONObject) jsonParser.parse(
                    new InputStreamReader(loadinputStream, "UTF-8")));
        }
        return worker;
        
    }
    public static void print_instruction() {
        System.out.println("Print numbers in comand line according to the instruction below");
        System.out.println("1 will print information about all students in alphabetical order");
        System.out.println("2 will print information about student with specified id");
        System.out.println("3 will add student with specified name");
        System.out.println("4 will delete student with specified id");
        System.out.println("5 will show this instruction");
        System.out.println("6 will close this application");
    }

    public static void print_all_students(Jsonworker worker) {
        String[][] students = worker.get_all_Students();
        for (Integer i = 0; i < students.length; i++) {
            System.out.println("id=" + students[i][0] + ", name =" + students[i][1]);
        }
    }

    public static void print_student(Jsonworker worker, Integer id) {
        try {
            System.out.println(worker.getStudentbyId(id));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean add_student(Jsonworker worker, String name) {
        try {
            worker.addStudent(name);
            System.out.println("Student added");
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean delete_student(Jsonworker worker, Integer id) {
        try {
            worker.deleteStudent(id);
            System.out.println("Student deleted"); 
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public static void updateFTP(FTPClient client, String File) throws IOException{
        client.deleteFile("students.json");
        InputStream savestream = new ByteArrayInputStream(File.getBytes(StandardCharsets.UTF_8));;
        client.storeFile("students.json", savestream);
    }
}
