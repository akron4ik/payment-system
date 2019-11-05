package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.user.Subbranch;

/**
 * Кассовый ордер.
 */
@EqualsAndHashCode(of = {"cashOrderId"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class CashOrder extends ExternalEntity implements Serializable {

  private String cashOrderId;
  private String cashOrderINum;
  private CashOrderStatus cashOrderStatus;
  private CashOrderType cashOrderType;
  private Date createdDttm;
  private OperationTypeCode operationTypeCode;
  private BigDecimal amount;
  private String amountInWords;
  private String currencyType;
  private String workPlaceUId;
  private String accountId;
  private List<CashSymbol> cashSymbols;
  private String responseCode;
  private String responseMsg;
  private String senderBank;
  private String senderBankBic;
  private String recipientBank;
  private String recipientBankBic;
  private String fdestleName;
  private String operatorPosition;
  private String userFullName;
  private String userPosition;
  private String account20202Num;
  private String comment;
  private Subbranch subbranch;
  private String userLogin;
  private boolean clientTypeFk;
  private String operationId;
  private Representative repData;
  private String legalEntityShortName;

}
