
package com.mycompany.jsonworker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author misha
 */
public class Jsonworker {
    private JSONObject students;
    
    public Jsonworker(JSONObject students){
        this.students=students;
    }
    
    public JSONObject getstudents(){
        return this.students;
    }
    
    public void get_all_Students(){

    }
    
    public boolean addStudent(String name) throws Exception{
        return true;
    }
    
    public boolean deleteStudent(Integer id) throws Exception{

    }
    public String getStudentbyId(Integer id) throws Exception{

    }
    
}
