package org.levelup.application.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class JobListId implements Serializable {

  @Column(name = "company_id")
  private Integer companyId;
  @Column(name = "user_id")
  private Integer userId;
  @Column(name = "position_id")
  private Integer positionId;

}
