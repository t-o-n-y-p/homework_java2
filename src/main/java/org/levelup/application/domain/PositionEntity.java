package org.levelup.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@Table(name = "positions")
public class PositionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 100, unique = true, nullable = false)
  private String name;

  @ManyToMany(mappedBy = "positions")
  private List<CompanyEntity> companies;

  @Override
  public String toString() {
    return "PositionEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
