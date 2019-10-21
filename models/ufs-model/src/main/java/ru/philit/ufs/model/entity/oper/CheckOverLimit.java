package ru.philit.ufs.model.entity.oper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

import java.math.BigDecimal;

@EqualsAndHashCode(of = {"userLogin"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class CheckOverLimit extends ExternalEntity {

  private String userLogin;
  private boolean tobeIncreased;
  private BigDecimal amount;
  private String responseCode;
  private LimitStatusTypeCode limitStatusTypeCode;
}
