package org.levelup.application.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "companies")
public class CompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 100, nullable = false)
  private String name;
  @Column(length = 40, nullable = false, unique = true)
  private String ein;
  @Column(nullable = false)
  private String address;

}
