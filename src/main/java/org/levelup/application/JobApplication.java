package org.levelup.application;

import org.levelup.application.dao.*;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.application.domain.CompanyLegalDetailsEntity;
import org.levelup.application.domain.UserAddressesEntity;
import org.levelup.hibernate.JobSessionFactoryConfiguration;
import org.levelup.hibernate.domain.UserEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class JobApplication {

  public static void main(String[] args) {

//    CompanyDao companyDao = new CompanyDaoImpl(JobSessionFactoryConfiguration.getFactory());
//    CompanyLegalDetailsDao legalDetailsDao = new CompanyLegalDetailsDaoImpl(
//        JobSessionFactoryConfiguration.getFactory()
//    );
////    companyDao.create("Company 100", "10989-66985", "Address 100");
////    CompanyEntity company = companyDao.findByEin("10989-66985");
////    legalDetailsDao.updateLegalDetailsInCompany(company.getId(), "Sberbank", "95864934");
////
////    Collection<CompanyLegalDetailsEntity> legalDetails = legalDetailsDao.findAllByBankName("Sberbank");
////    for (CompanyLegalDetailsEntity detail : legalDetails) {
////      System.out.println(detail.getCompany().getName());
////    }
//    UserDao userDao = new UserDaoImpl(JobSessionFactoryConfiguration.getFactory());
//    UserEntity user = userDao.createUser("User1", "Last2", "43443 433", new ArrayList<>(Arrays.asList(
//        "address 1", "address2", "address3"
//    )));
//
//    for (UserAddressesEntity addressesEntity : user.getAddresses()) {
//      System.out.println(addressesEntity.getId() + " " + addressesEntity.getAddress());
//    }

    AuthDetailsDao authDetailsDao = new AuthDetailsDaoImpl(JobSessionFactoryConfiguration.getFactory());
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter login: ");
    String login = sc.nextLine();
    System.out.print("Enter password: ");
    String password = sc.nextLine();
    try {
      System.out.println("Hello " + authDetailsDao.logIn(login, password).getName() + "!");
    } catch (NullPointerException e) {
      System.out.println("Name or password are incorrect.");
    }

    JobSessionFactoryConfiguration.closeFactory();

  }

}
