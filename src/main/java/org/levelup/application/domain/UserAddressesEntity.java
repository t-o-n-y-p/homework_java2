package org.levelup.application.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_addresses")
@NoArgsConstructor
public class UserAddressesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, length = 511)
  private String address;

//  @ManyToOne
//  private UserEntity user;

}
