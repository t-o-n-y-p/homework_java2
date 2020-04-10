package org.levelup.application.dao;

import org.hibernate.SessionFactory;
import org.levelup.application.domain.UserAddressesEntity;
import org.levelup.hibernate.domain.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImpl extends AbstractDao implements UserDao {

  public UserDaoImpl(SessionFactory factory) {
    super(factory);
  }

  @Override
  public UserEntity createUser(String name, String lastName, String passport, Collection<String> addresses) {
    return runInTransaction(s -> {
      UserEntity user = new UserEntity();
      user.setName(name);
      user.setLastName(lastName);
      user.setPassport(passport);
      List<UserAddressesEntity> addressEntities = addresses.stream()
          .map(address -> {
            UserAddressesEntity userAddressesEntity = new UserAddressesEntity();
            userAddressesEntity.setAddress(address);
            userAddressesEntity.setUser(user);
            return userAddressesEntity;
          })
          .collect(Collectors.toList());

      user.setAddresses(addressEntities);
      s.persist(user);
      return user;
    });
  }
}
