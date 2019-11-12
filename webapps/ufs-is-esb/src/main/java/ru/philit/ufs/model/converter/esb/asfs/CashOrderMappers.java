package ru.philit.ufs.model.converter.esb.asfs;

import static ru.philit.ufs.model.entity.esb.asfs.LimitStatusType.LIMIT_PASSED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashOrderType;
import ru.philit.ufs.model.entity.oper.CashSymbol;

public class CashOrderMappers {
  protected static CashOrderStatusType map(CashOrderStatus cashOrderStatus) {
    return (cashOrderStatus != null) ? CashOrderStatusType.fromValue(cashOrderStatus.code()) : null;
  }

  protected static CashOrderStatus map(CashOrderStatusType cashOrderStatus) {
    return (cashOrderStatus != null) ? CashOrderStatus.valueOf(cashOrderStatus.value()) : null;
  }

  protected static CashOrderType map(
      ru.philit.ufs.model.entity.esb.asfs.CashOrderType cashOrderType) {
    return (cashOrderType != null) ? CashOrderType.getByCode(cashOrderType.value()) : null;
  }


  protected static OperTypeLabel map(OperationTypeCode operationTypeCode) {
    return (operationTypeCode != null) ? OperTypeLabel.fromValue(operationTypeCode.code()) : null;
  }

  protected static boolean map(LimitStatusType limitStatusType) {
    return (limitStatusType.value().equals(LIMIT_PASSED.value()));
  }

  protected static Date map(XMLGregorianCalendar xmlCalendar) {
    return (xmlCalendar != null) ? xmlCalendar.toGregorianCalendar().getTime() : null;
  }

  protected static List<SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage
      .AdditionalInfo.CashSymbols.CashSymbolItem> map(List<CashSymbol> cashSymbols) {
    List<SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage
        .AdditionalInfo.CashSymbols.CashSymbolItem> list = new ArrayList<>();
    if (cashSymbols != null) {
      for (CashSymbol cashSymbol: cashSymbols) {
        CashSymbolItem cashSymbolItem = new CashSymbolItem();
        cashSymbolItem.setCashSymbol(cashSymbol.getCode());
        cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
        list.add(cashSymbolItem);
      }
    }
    return list;
  }

}
