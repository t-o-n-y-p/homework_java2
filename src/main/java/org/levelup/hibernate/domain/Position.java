package org.levelup.hibernate.domain;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, unique = true)
  private String name;

  public Position() {}

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Position{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
