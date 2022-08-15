
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
    
    private void quickSort_string(String arr[][], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort_string(arr, begin, partitionIndex-1);
            quickSort_string(arr, partitionIndex+1, end);
        }
    }
    
    private int partition(String arr[][], int begin, int end) {
        String pivot = arr[end][1];
        int i = (begin-1);
        for (int j = begin; j < end; j++) {
            if (arr[j][1].compareTo(pivot) <= 0 ) {
                i++;
                String[] swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        String[] swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
        return i+1;
    }
    public String[][] get_all_Students(){
        JSONArray arr_students = (JSONArray)students.get("students");
        String[][] students_as_strings= new String[arr_students.size()][2];
        for (Integer i = 0; i <arr_students.size(); i++) {
            JSONObject student = (JSONObject)arr_students.get(i);
            students_as_strings[i][0]=student.get("id").toString();
            students_as_strings[i][1]=student.get("name").toString();
        }
        quickSort_string(students_as_strings,0,arr_students.size()-1);
        return students_as_strings;
    }
    
    public boolean addStudent(String name) throws Exception{
        int id=0;
        JSONArray arr_students = (JSONArray)students.get("students");
        for (Integer i = 0; i <arr_students.size(); i++) {
            JSONObject student = (JSONObject)arr_students.get(i);
            if (student.get("name")==name){
                throw new Exception("Student alredy exists");
            }
            if (id<(Integer)student.get("id"))
                id = (Integer) student.get("id");
        }
        JSONObject newstudent = new JSONObject();
        newstudent.put("id",id+1);
        newstudent.put("name",name);
        arr_students.add(newstudent);
        return true;
    }
    
    public boolean deleteStudent(Integer id) throws Exception{
        JSONArray arr_students = (JSONArray)students.get("students");
        for (int i = 0; i <arr_students.size(); i++) {
            JSONObject student = (JSONObject)arr_students.get(i);
            if (student.get("id")==id){
                arr_students.remove(i);
                return true;
            }
        }
        throw new Exception("Student does not exist");
    }
    
    public String getStudentbyId(Integer id) throws Exception{
        JSONArray arr_students = (JSONArray)students.get("students");
        for (int i = 0; i <arr_students.size(); i++) {
            JSONObject student = (JSONObject)arr_students.get(i);
            if (student.get("id").toString().equals(id.toString())){
                return student.get("name").toString();
            }
        }
        throw new Exception("Student does not exist");
    }
    
}
