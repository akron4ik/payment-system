package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.util.List;

public class CashOrderDto implements Serializable {
  private String id;
  private List<CashSymbolDto> cashSymbols;
  private OperationTypeDto operationTypeDto;
  private SubbranchDto subbranchDto;
}
