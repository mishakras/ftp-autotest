package test_Jsonworker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.mycompany.jsonworker.Jsonworker;

/**
 *
 * @author misha
 */
public class Test_Jsonworker {
    public Jsonworker worker;
    
    @BeforeMethod	
    public void setWorker(){
        JSONObject students= new JSONObject();
        JSONArray arr = new JSONArray();
        JSONObject student= new JSONObject();
        student.put("id",1);
        student.put("name","aaaa");
        arr.add(student);
        student= new JSONObject();
        student.put("id",2);
        student.put("name","bbbb");
        arr.add(student);
        student= new JSONObject();
        student.put("id",3);
        student.put("name","aaabbbb");
        arr.add(student);
        students.put("students", arr);
        worker = new Jsonworker(students);
    }
    
    @Test
    public void addStudent_positive() throws Exception {
        Assert.assertEquals(worker.addStudent("ccccc"),true);
        System.out.println("Student added");
    }
    
    @Test
    public void addStudent_negative() {
        try{
            worker.addStudent("bbbb");
        }
        catch(Exception e){
            Assert.assertEquals(e.getMessage(),"Student alredy exists");
            System.out.println("Student adding failure caught");
        }
    }
    
    @Test
    public void getStudent_positive() throws Exception {
        Assert.assertEquals(worker.getStudentbyId(1),"aaaa");
        System.out.println("Student got");
    }
    
    @Test
    public void getStudent_negative() {
        try{
            worker.getStudentbyId(3);
        }
        catch(Exception e){
            Assert.assertEquals(e.getMessage(),"Student does not exist");
            System.out.println("Student getting failure caught");
        }
    }
    
    @Test
    public void getStudents_positive() throws Exception {
        String[][] students= worker.get_all_Students();
        Assert.assertEquals(students[0][1],"aaaa");
        Assert.assertEquals(students.length,3);
        System.out.println("Students got");
    }
    
    @Test
    public void deleteStudent_negative() {
        try{
            worker.deleteStudent(5);
        }
        catch(Exception e){
            Assert.assertEquals(e.getMessage(),"Student does not exist");
            System.out.println("Student getting failure caught");
        }
    }
    
    @Test
    public void deleteStudent_positive() throws Exception {
        Assert.assertEquals(worker.deleteStudent(1),true);
        System.out.println("Student deleted");
    }
}
