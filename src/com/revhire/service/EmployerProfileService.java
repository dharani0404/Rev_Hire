package com.revhire.service;

import com.revhire.dao.EmployerProfileDAO;
import com.revhire.model.EmployerProfile;

public class EmployerProfileService {

    private EmployerProfileDAO dao = new EmployerProfileDAO();

    public boolean addProfile(EmployerProfile p) {
        return dao.addCompanyProfile(p);
    }

    public EmployerProfile getProfile(int userId) {
        return dao.getCompanyProfileByUserId(userId);
    }

    public boolean updateProfile(EmployerProfile p) {
        return dao.updateCompanyProfile(p);
    }
    
    public String getEmployerNameById(int employerId) {
        return dao.getCompanyNameById(employerId);
    }

}

//package com.revhire.service; // this code is for separate the ui and logic
//
//import com.revhire.dao.EmployerProfileDAO;
//import com.revhire.model.EmployerProfile;
//
//import java.util.Scanner;
//
//public class EmployerProfileService {
//
//    private EmployerProfileDAO dao = new EmployerProfileDAO();
//
//    public EmployerProfile getProfile(int userId) {
//        return dao.getProfileByUserId(userId);
//    }
//
//    public void addProfileUI(int userId, Scanner sc) {
//        EmployerProfile p = new EmployerProfile();
//        p.setUserId(userId);
//
//        System.out.print("Company Name: ");
//        p.setCompanyName(sc.nextLine());
//
//        System.out.print("Industry: ");
//        p.setIndustry(sc.nextLine());
//
//        System.out.print("Company Size: ");
//        p.setCompanySize(Integer.parseInt(sc.nextLine()));
//
//        System.out.print("Location: ");
//        p.setLocation(sc.nextLine());
//
//        System.out.print("Website: ");
//        p.setWebsite(sc.nextLine());
//
//        System.out.print("Description: ");
//        p.setDescription(sc.nextLine());
//
//        System.out.println(dao.addProfile(p) ? "Profile added." : "Failed.");
//    }
//
//    public void updateProfileUI(int userId, Scanner sc) {
//        EmployerProfile p = dao.getProfileByUserId(userId);
//        if (p == null) {
//            System.out.println("Profile not found.");
//            return;
//        }
//
//        System.out.print("New Location: ");
//        String loc = sc.nextLine();
//        if (!loc.isBlank()) p.setLocation(loc);
//
//        System.out.println(dao.updateProfile(p) ? "Updated." : "Update failed.");
//    }
//
//    public void viewProfile(int userId) {
//        EmployerProfile p = dao.getProfileByUserId(userId);
//        if (p == null) {
//            System.out.println("No profile found.");
//            return;
//        }
//        System.out.println(p.getCompanyName() + " | " + p.getLocation());
//    }
//}
