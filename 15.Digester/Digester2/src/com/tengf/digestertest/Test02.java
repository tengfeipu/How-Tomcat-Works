package com.tengf.digestertest;

import org.apache.commons.digester.Digester;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Test02 {
    public static void main(String[] args){
        String path = System.getProperty("user.dir") + File.separator  + "etc";
        File file = new File(path, "employee2.xml");
        Digester digester = new Digester();
        // add rules
        digester.addObjectCreate("employee", "com.tengf.digestertest.Employee");
        digester.addSetProperties("employee");
        digester.addObjectCreate("employee/office", "com.tengf.digestertest.Office");
        digester.addSetProperties("employee/office");
        digester.addSetNext("employee/office", "addOffice");
        digester.addObjectCreate("employee/office/address",
                "com.tengf.digestertest.Address");
        digester.addSetProperties("employee/office/address");
        digester.addSetNext("employee/office/address", "setAddress");
        try {
            Employee employee = (Employee) digester.parse(file);
            ArrayList offices = employee.getOffices();
            Iterator iterator = offices.iterator();
            System.out.println("-------------------------------------------------");
            while (iterator.hasNext()) {
                Office office = (Office) iterator.next();
                Address address = office.getAddress();
                System.out.println(office.getDescription());
                System.out.println("Address : " +
                        address.getStreetNumber() + " " + address.getStreetName());
                System.out.println("--------------------------------");
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
