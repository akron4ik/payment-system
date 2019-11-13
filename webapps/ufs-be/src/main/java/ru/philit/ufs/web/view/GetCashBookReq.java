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
  /**
   * Аккаунт в кассовом ордере.
   */
  private String accountId;
  /**
   * Идентификатор рабочего места в кассовом ордере.
   */
  private String workPlaceUid;
}
