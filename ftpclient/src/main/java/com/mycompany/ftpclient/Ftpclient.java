package com.mycompany.ftpclient;

import org.apache.commons.net.ftp.FTPClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import com.mycompany.jsonworker.Jsonworker;
/**
 *
 * @author misha
 */
public class Ftpclient {
    public static FTPClient client = new FTPClient();
    
    public static void main(String[] args) throws ParseException, Exception {
        run(args);
    }

    public static void run(String[] args) throws ParseException, Exception {    
        String[] for_ftpclient= new String[args.length+1];
        for (Integer i = 0; i< args.length;i++)
            for_ftpclient[i] = args[i];
        for_ftpclient[for_ftpclient.length]="20";
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

    }

    public static Jsonworker createworker(FTPClient client) throws IOException, ParseException{
        
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

    }

    public static void print_student(Jsonworker worker, Integer id) {

    }

    public static boolean add_student(Jsonworker worker, String name) {

    }

    public static boolean delete_student(Jsonworker worker, Integer id) {

    }
    
    public static void updateFTP(FTPClient client, String File) throws IOException{

}
