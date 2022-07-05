package com.zrxedu.mhl.service;

import com.zrxedu.mhl.dao.EmployeeDAO;
import com.zrxedu.mhl.dommain.Employee;

/**
 * 该类完成对employee表的各种操作(通过调用EmployeeDAO对象完成)
 */
@SuppressWarnings({"all"})
public class EmployeeService {
    EmployeeDAO employeeDAO = new EmployeeDAO();

    //通过empid和pwd查找返回一个Employee对象，如果没有找到返回一个null
    public  Employee getEmployeeByEmpidAndPwd(String empid, String pwd) {
        String sql = "select * from employee where empid=? and pwd=md5(?);";
        Employee employee = employeeDAO.querySingle(sql, Employee.class, empid, pwd);
        return employee;
    }
}
