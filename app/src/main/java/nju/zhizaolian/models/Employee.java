package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ColorfulCode on 2015/4/23.
 */
public class Employee implements Serializable{
    private String address;
    private String age;
    private String chinaId;
    private String department;
    private String directLeader;
    private String eduBackground;
    private String eduField;
    private String eduSchool;
    private String email;
    private String employeeId;
    private String employeeLevel;
    private String employeeName;
    private String employeeState;
    private String entryTime;
    private String exBusiness;
    private String exCompany;
    private String exJob;
    private String hometown;
    private String jobPhone;
    private String leaveTime;
    private String phone1;
    private String phone2;
    private String qq;
    private String sex;

    public static Employee fromJson(JSONObject jsonObject){
        Employee employee = null;
        try {
        employee=new Employee();
        employee.address=jsonObject.getString("address");
        employee.age=jsonObject.getString("age");
        employee.chinaId=jsonObject.getString("chinaId");
        employee.department=jsonObject.getString("department");
        employee.directLeader=jsonObject.getString("directLeader");
        employee.eduBackground=jsonObject.getString("eduBackground");
        employee.eduField=jsonObject.getString("eduField");
        employee.eduSchool=jsonObject.getString("eduSchool");
        employee.email=jsonObject.getString("email");
        employee.employeeId=jsonObject.getString("employeeId");
        employee.employeeLevel=jsonObject.getString("employeeLevel");
        employee.employeeName=jsonObject.getString("employeeName");
        employee.employeeState=jsonObject.getString("employeeState");
        employee.entryTime=jsonObject.getString("entryTime");
        employee.exBusiness=jsonObject.getString("exBusiness");
        employee.exCompany=jsonObject.getString("exCompany");
        employee.exJob=jsonObject.getString("exJob");
        employee.hometown=jsonObject.getString("hometown");
        employee.jobPhone=jsonObject.getString("jobPhone");
        employee.leaveTime=jsonObject.getString("leaveTime");
        employee.phone1=jsonObject.getString("phone1");
        employee.phone2=jsonObject.getString("phone2");
        employee.qq=jsonObject.getString("qq");
        employee.sex=jsonObject.getString("sex");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static ArrayList<Employee> fromJson(JSONArray array){
        ArrayList<Employee> employees = new ArrayList<>();
        for(int i=0;i<array.length();i++){
            try {
                JSONObject object = array.getJSONObject(i);
                Employee employee = fromJson(object);
                employees.add(employee);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return  employees;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getChinaId() {
        return chinaId;
    }

    public void setChinaId(String chinaId) {
        this.chinaId = chinaId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDirectLeader() {
        return directLeader;
    }

    public void setDirectLeader(String directLeader) {
        this.directLeader = directLeader;
    }

    public String getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getEduField() {
        return eduField;
    }

    public void setEduField(String eduField) {
        this.eduField = eduField;
    }

    public String getEduSchool() {
        return eduSchool;
    }

    public void setEduSchool(String eduSchool) {
        this.eduSchool = eduSchool;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(String employeeLevel) {
        this.employeeLevel = employeeLevel;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeState() {
        return employeeState;
    }

    public void setEmployeeState(String employeeState) {
        this.employeeState = employeeState;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExBusiness() {
        return exBusiness;
    }

    public void setExBusiness(String exBusiness) {
        this.exBusiness = exBusiness;
    }

    public String getExCompany() {
        return exCompany;
    }

    public void setExCompany(String exCompany) {
        this.exCompany = exCompany;
    }

    public String getExJob() {
        return exJob;
    }

    public void setExJob(String exJob) {
        this.exJob = exJob;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getJobPhone() {
        return jobPhone;
    }

    public void setJobPhone(String jobPhone) {
        this.jobPhone = jobPhone;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
