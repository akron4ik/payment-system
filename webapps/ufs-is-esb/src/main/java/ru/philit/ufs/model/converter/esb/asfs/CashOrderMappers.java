package ru.philit.ufs.model.converter.esb.asfs;

import static ru.philit.ufs.model.entity.esb.asfs.LimitStatusType.LIMIT_PASSED;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit;
import ru.philit.ufs.model.entity.oper.CashOrderType;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;
import ru.philit.ufs.model.entity.user.WorkplaceType;

public class CashOrderMappers {

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

  protected static List<OperationTypeLimit> map(List<SrvGetWorkPlaceInfoRs
      .SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem> list) {
    List<OperationTypeLimit> newList = new ArrayList<>();
    if (list != null) {
      for (WorkPlaceOperationTypeLimit.OperationTypeLimitItem operationTypeLimitItem : list) {
        OperationTypeLimit operationTypeLimit = new OperationTypeLimit();
        operationTypeLimit.setCategoryId(operationTypeLimitItem.getOperationCategory().toString());
        operationTypeLimit.setLimit(operationTypeLimitItem.getOperationLimit());
        newList.add(operationTypeLimit);
      }
    }
    return newList;
  }

  protected static WorkplaceType map(BigInteger value) {
    switch (value.intValue()) {
      case 0:
        return WorkplaceType.CASHBOX;
      case 1:
        return WorkplaceType.UWP;
      case 2:
        return WorkplaceType.OTHER;
      default:
        return null;
    }
  }

}
