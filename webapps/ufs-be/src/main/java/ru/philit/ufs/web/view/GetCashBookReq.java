package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetCashBookReq extends BaseRequest {

  private String cashOrderId;
  private String operator;
  private String amount;
}
