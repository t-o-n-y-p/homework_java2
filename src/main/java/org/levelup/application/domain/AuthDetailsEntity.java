package org.levelup.application.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.levelup.hibernate.domain.UserEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_details")
@NoArgsConstructor
public class AuthDetailsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, unique = true)
  private String login;
  @Column(nullable = false)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private UserEntity user;

}
