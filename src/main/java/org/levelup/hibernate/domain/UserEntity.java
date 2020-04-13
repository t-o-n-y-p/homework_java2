package org.levelup.hibernate.domain;

import lombok.*;
import org.levelup.application.domain.AuthDetailsEntity;
import org.levelup.application.domain.UserAddressesEntity;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 100, nullable = false)
  private String name;
  @Column(name = "last_name", length = 100, nullable = false)
  private String lastName;
  @Column(length = 50, nullable = false, unique = true)
  private String passport;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private List<UserAddressesEntity> addresses;
  @OneToOne(mappedBy = "user")
  private AuthDetailsEntity authDetails;

}
