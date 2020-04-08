package org.levelup.hibernate.service;

import org.levelup.hibernate.domain.UserEntity;

import java.util.List;

public class UserService implements Dao<UserEntity> {

  public UserEntity createUser(String name, String lastName, String passport) {
    return runInTransactionWithSingleResult(s -> {
      UserEntity userEntity = new UserEntity();
      userEntity.setName(name);
      userEntity.setLastName(lastName);
      userEntity.setPassport(passport);
      s.persist(userEntity);
      return userEntity;
    });
  }

  public UserEntity findByPassport(String passport) {
    List<UserEntity> userEntities = run(
        s -> s.createQuery("from UserEntity where passport = :passport", UserEntity.class)
          .setParameter("passport", passport)
          .getResultList()
    );
    return userEntities.isEmpty() ? null : userEntities.get(0);
  }

  public List<UserEntity> findByLastName(String lastName) {
    return run(
        s -> s.createQuery("from UserEntity where last_name = :last_name", UserEntity.class)
            .setParameter("last_name", lastName)
            .getResultList()
    );
  }

  public List<UserEntity> findByNameAndLastName(String name, String lastName) {
    return run(
        s -> s.createQuery("from UserEntity where name = :name and last_name = :last_name", UserEntity.class)
            .setParameter("name", name)
            .setParameter("last_name", lastName)
            .getResultList()
    );
  }

  public void deleteUserByPassport(String passport) {
    delete(
        s -> s.createQuery("delete UserEntity where passport = :passport")
            .setParameter("passport", passport)
            .executeUpdate()
    );
  }

  public UserEntity updateUser(String passport, String newName, String newLastName) {
    UserEntity userEntity = findByPassport(passport);
    userEntity.setName(newName);
    userEntity.setLastName(newLastName);
    return runInTransactionWithSingleResult(s -> (UserEntity) s.merge(userEntity));
  }

}
