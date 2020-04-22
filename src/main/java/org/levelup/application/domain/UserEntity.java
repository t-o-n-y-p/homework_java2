package org.levelup.application.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"id", "name", "lastName", "passport"})
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
  @JoinColumn(name = "user_id", nullable = false)
  private List<UserAddressesEntity> addresses;
  @OneToOne(mappedBy = "user")
  private AuthDetailsEntity authDetails;

  @Override
  public String toString() {
    return "UserEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", lastName='" + lastName + '\'' +
        ", passport='" + passport + '\'' +
        '}';
  }
}
