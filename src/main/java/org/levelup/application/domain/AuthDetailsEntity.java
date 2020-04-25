package org.levelup.application.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_details")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "login", "password"})
public class AuthDetailsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 100, nullable = false, unique = true)
  private String login;
  @Column(length = 50, nullable = false)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private UserEntity user;

}
