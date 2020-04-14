package org.levelup.application;

import org.levelup.application.dao.*;
import org.levelup.application.domain.JobListEntity;
import org.levelup.application.domain.PositionEntity;
import org.levelup.application.domain.UserAddressesEntity;
import org.levelup.hibernate.JobSessionFactoryConfiguration;
import org.levelup.application.domain.UserEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class JobApplication {

  public static void main(String[] args) {

    CompanyDao companyDao = new CompanyDaoImpl(JobSessionFactoryConfiguration.getFactory());
    CompanyLegalDetailsDao legalDetailsDao = new CompanyLegalDetailsDaoImpl(
        JobSessionFactoryConfiguration.getFactory()
    );
    companyDao.create("Company JobList", "8987332", "Saint-Petersburg");
    Integer companyId = companyDao.findByEin("8987332").getId();

    UserDao userDao = new UserDaoImpl(JobSessionFactoryConfiguration.getFactory());
    UserEntity user = userDao.createUser("User1", "Last2", "1113332 433", new ArrayList<>(Arrays.asList(
        "address 1", "address2", "address3"
    )));

    PositionDao positionDao = new PositionDaoImpl(JobSessionFactoryConfiguration.getFactory());
    Integer positionId = positionDao.createPosition("Product Owner 232").getId();

    JobListDao jobListDao = new JobListDaoImpl(JobSessionFactoryConfiguration.getFactory());
    jobListDao.createJobRecord(
        companyId, user.getId(), positionId, LocalDate.of(2019, 12, 4), null
    );

    JobListEntity jobRecord = jobListDao.findJobRecord(companyId, user.getId(), positionId);
    System.out.println(jobRecord.getCompany());
    System.out.println(jobRecord.getPosition());
    System.out.println(jobRecord.getUser());
    System.out.println(jobRecord.getStartDate());
    System.out.println(jobRecord.getEndDate());

    JobSessionFactoryConfiguration.closeFactory();

  }

}
