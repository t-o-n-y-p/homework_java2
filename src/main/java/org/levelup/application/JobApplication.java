package org.levelup.application;

import org.levelup.application.dao.*;
import org.levelup.application.domain.*;
import org.levelup.hibernate.JobSessionFactoryConfiguration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class JobApplication {

  private static UserEntity user;

  public static void main(String[] args) {

//    CompanyDao companyDao = new CompanyDaoImpl(JobSessionFactoryConfiguration.getFactory());
//    CompanyLegalDetailsDao legalDetailsDao = new CompanyLegalDetailsDaoImpl(
//        JobSessionFactoryConfiguration.getFactory()
//    );
//    companyDao.create("Company JobList", "8987332", "Saint-Petersburg");
//    Integer companyId = companyDao.findByEin("8987332").getId();
//
//    UserDao userDao = new UserDaoImpl(JobSessionFactoryConfiguration.getFactory());
//    UserEntity user = userDao.createUser("User1", "Last2", "1113332 433", new ArrayList<>(Arrays.asList(
//        "address 1", "address2", "address3"
//    )));
//
    PositionDao positionDao = new PositionDaoImpl(JobSessionFactoryConfiguration.getFactory());
    PositionEntity nullPosition = positionDao.findByName("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    System.out.println(nullPosition);
//    Integer positionId = positionDao.createPosition(" ").getId();
//
//    JobListDao jobListDao = new JobListDaoImpl(JobSessionFactoryConfiguration.getFactory());
//    jobListDao.createJobRecord(
//        companyId, user.getId(), positionId, LocalDate.of(2019, 12, 4), null
//    );
//
//    JobListEntity jobRecord = jobListDao.findJobRecord(companyId, user.getId(), positionId);
//    System.out.println(jobRecord.getCompany());
//    System.out.println(jobRecord.getPosition());
//    System.out.println(jobRecord.getUser());
//    System.out.println(jobRecord.getStartDate());
//    System.out.println(jobRecord.getEndDate());

    AuthDetailsDao authDetailsDao = new AuthDetailsDaoImpl(JobSessionFactoryConfiguration.getFactory());
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter login: ");
    String login = sc.nextLine();
    System.out.print("Enter password: ");
    String password = sc.nextLine();
    user = authDetailsDao.logIn(login, password);
    if (user == null) {
      System.out.println("Name or password are incorrect.");
      return;
    }

    System.out.println("Hello " + user.getName() + " " + user.getLastName() + "!");
    int choice = 0;
    while (choice < 1 || choice > 4) {
      System.out.println("Choose action:");
      System.out.println("1. Create company");
      System.out.println("2. Create position");
      System.out.println("3. Create job record");
      choice = sc.nextInt();
    }
    if (choice == 1) {
      System.out.print("Enter company name: ");
      String name = sc.nextLine();
      System.out.print("Enter company ein: ");
      String ein = sc.nextLine();
      System.out.print("Enter company address: ");
      String address = sc.nextLine();
      createCompany(name, ein, address);
    } else if (choice == 2) {
      System.out.print("Enter position name: ");
      String name = sc.nextLine();
      createPosition(name);
    } else if (choice == 3) {
      createJobRecord();
    }

    JobSessionFactoryConfiguration.closeFactory();

  }

  private static CompanyEntity createCompany(String name, String ein, String address) {
    CompanyDao companyDao = new CompanyDaoImpl(JobSessionFactoryConfiguration.getFactory());
    CompanyEntity company = companyDao.findByEin(ein);
    if (company == null) {
      CompanyEntity newCompany = companyDao.createCompany(name, ein, address);
      System.out.println("Company " + name + " created.");
      return newCompany;
    } else if (company.getName().equals(name) && company.getAddress().equals(address)) {
      return company;
    } else {
      Scanner sc = new Scanner(System.in);
      System.out.print("Company with ein " + ein + " already exists. Update existing company? ");
      String answer = sc.nextLine();
      if (Answer.getAnswer(answer) == Answer.YES) {
        CompanyEntity updatedCompany = companyDao.updateCompany(company, name, address);
        System.out.println("Company with ein " + ein + " updated.");
        return updatedCompany;
      }
      return company;
    }
  }

  private static PositionEntity createPosition(String name) {
    PositionDao positionDao = new PositionDaoImpl(JobSessionFactoryConfiguration.getFactory());
    PositionEntity position = positionDao.findByName(name);
    if (position == null) {
      PositionEntity newPosition = positionDao.createPosition(name);
      System.out.println("Position " + name + " created.");
      return newPosition;
    }
    return position;
  }

  private static JobListEntity createJobRecord() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter company name: ");
    String companyName = sc.nextLine();
    System.out.print("Enter company ein: ");
    String ein = sc.nextLine();
    System.out.print("Enter company address: ");
    String address = sc.nextLine();
    System.out.print("Enter position name: ");
    String positionName = sc.nextLine();
    System.out.println("Enter year, month and day of the start date: ");
    LocalDate startDate = LocalDate.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
    LocalDate endDate = null;
    System.out.print("Are you still working here? ");
    sc = new Scanner(System.in);
    if (Answer.getAnswer(sc.nextLine()) == Answer.NO) {
      System.out.println("Enter year, month and day of the end date: ");
      endDate = LocalDate.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
    }

    CompanyEntity company = createCompany(companyName, ein, address);
    PositionEntity position = createPosition(positionName);
    JobListDao jobListDao = new JobListDaoImpl(JobSessionFactoryConfiguration.getFactory());
    JobListEntity jobRecord = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    if (jobRecord == null) {
      JobListEntity newJobRecord = jobListDao.createJobRecord(
          company.getId(), user.getId(), position.getId(), startDate, endDate
      );
      System.out.println("Job record created.");
      return newJobRecord;
    } else if (jobRecord.getStartDate().equals(startDate) && jobRecord.getEndDate().equals(endDate)) {
      return jobRecord;
    } else {
      System.out.print("Job record already exists. Update existing job record? ");
      sc = new Scanner(System.in);
      if (Answer.getAnswer(sc.nextLine()) == Answer.YES) {
        JobListEntity updatedJobRecord = jobListDao.updateJobRecord(jobRecord, startDate, endDate);
        System.out.println("Job record updated.");
        return updatedJobRecord;
      }
      return jobRecord;
    }
  }

  private enum Answer {
    YES,
    NO;

    private static final Collection<String> positiveAnswers = Arrays.asList("y", "yes");

    public static Answer getAnswer(String answer) {
      if (positiveAnswers.contains(answer.toLowerCase())) {
        return YES;
      }
      return NO;
    }
  }

}
