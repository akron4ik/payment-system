package ru.philit.ufs.model.entity.oper;

import lombok.*;
import ru.philit.ufs.model.entity.common.ExternalEntity;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CheckOverLimitRequest implements Serializable {

  private String userLogin;
  private boolean tobeIncreased;
  private BigDecimal amount;
  private boolean limitStatusTypeCode;
}
