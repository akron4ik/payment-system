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

  /**
   * Идентификатор кассового ордера.
   */
  private String cashOrderId;

  private String accountId;

  private String workPlaceUid;
}
