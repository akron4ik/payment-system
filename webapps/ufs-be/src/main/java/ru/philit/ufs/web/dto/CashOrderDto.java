package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект кассового ордера.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class CashOrderDto implements Serializable {
  private String id;
  private String amount;
  private String currencyType;
  private RepresentativeDto representativeDto;
  private List<CashSymbolDto> cashSymbols;

  private SubbranchDto subbranchDto;
  private String account20202Num;
  private String cashOrderINum;
  private String cashOrderStatus;
}
