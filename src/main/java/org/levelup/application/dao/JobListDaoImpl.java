package org.levelup.application.dao;

import org.hibernate.SessionFactory;
import org.levelup.application.domain.JobListEntity;
import org.levelup.application.domain.JobListId;

import java.time.LocalDate;

public class JobListDaoImpl extends AbstractDao implements JobListDao {

  public JobListDaoImpl(SessionFactory factory) {
    super(factory);
  }

  @Override
  public JobListEntity createJobRecord(
      Integer companyId, Integer userId, Integer positionId, LocalDate startDate, LocalDate endDate
  ) {
    return runInTransaction(s -> {
      JobListId id = new JobListId(companyId, positionId, userId);
      JobListEntity jobRecord = new JobListEntity(id, startDate);
      if (endDate != null) {
        jobRecord.setEndDate(endDate);
      }
      s.persist(jobRecord);
      return jobRecord;
    });
  }

  @Override
  public JobListEntity findJobRecord(Integer companyId, Integer userId, Integer positionId) {
    return runWithoutTransaction(s -> s.get(JobListEntity.class, new JobListId(companyId, positionId, userId)));
  }

  @Override
  public JobListEntity updateJobRecord(
      Integer companyId, Integer userId, Integer positionId, LocalDate startDate, LocalDate endDate
  ) {
    JobListEntity jobRecord = findJobRecord(companyId, userId, positionId);
    return updateJobRecord(jobRecord, startDate, endDate);
  }

  @Override
  public JobListEntity updateJobRecord(JobListEntity jobRecord, LocalDate startDate, LocalDate endDate) {
    if (jobRecord == null) {
      return null;
    }
    return runInTransaction(s -> {
      jobRecord.setStartDate(startDate);
      jobRecord.setEndDate(endDate);
      return (JobListEntity) s.merge(jobRecord);
    });
  }
}
