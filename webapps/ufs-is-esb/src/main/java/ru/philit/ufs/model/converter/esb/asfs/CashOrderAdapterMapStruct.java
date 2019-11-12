package ru.philit.ufs.model.converter.esb.asfs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;

@Mapper(componentModel = "spring", uses = CashOrderMappers.class)
public interface CashOrderAdapterMapStruct {

  @Mappings({
      @Mapping(source = "cashOrderId", target = "cashOrderId"),
      @Mapping(source = "amount", target = "amount"),
      @Mapping(source = "amountInWords", target = "amountInWords"),
      @Mapping(source = "accountId", target = "accountId"),
      @Mapping(source = "cashOrderINum", target = "cashOrderINum"),
      @Mapping(source = "currencyType", target = "currencyType"),
      @Mapping(source = "workPlaceUId", target = "workPlaceUId"),
      @Mapping(source = "representative.id", target = "repData.repID"),
      @Mapping(expression = "java(cashOrder.getRepresentative().getFirstName() + \" \""
          + " + cashOrder.getRepresentative().getLastName() + \" \""
          + " + cashOrder.getRepresentative().getPatronymic())", target = "repData.repFIO"),
      @Mapping(source = "representative.address", target = "repData.address"),
      @Mapping(source = "representative.birthDate", target = "repData.dateOfBirth"),
      @Mapping(source = "cashSymbols", target = "additionalInfo.cashSymbols.cashSymbolItem"),
      @Mapping(source = "subbranch.subbranchCode", target = "additionalInfo.subbranchCode"),
      @Mapping(source = "subbranch.tbCode", target = "additionalInfo.TBCode"),
      @Mapping(source = "account20202Num", target = "additionalInfo.account20202Num"),
      @Mapping(source = "userLogin", target = "additionalInfo.userLogin"),
      @Mapping(source = "cashOrderStatus", target = "cashOrderStatus"),
      @Mapping(source = "operationTypeCode", target = "operationType")
  })
  SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage mapCreate(CashOrder cashOrder);

  @Mappings({
      @Mapping(source = "cashOrderId", target = "cashOrderId"),
      @Mapping(source = "cashOrderStatus", target = "cashOrderStatus")
  })
  SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage mapUpdSt(CashOrder cashOrder);


  @Mapping(source = "workPlaceId", target = "workPlaceUId")
  SrvGetWorkPlaceInfoRq.SrvGetWorkPlaceInfoRqMessage mapGetWorkplace(String workPlaceId);

  @Mappings({
      @Mapping(source = "userLogin", target = "userLogin"),
      @Mapping(source = "tobeIncreased", target = "tobeIncreased"),
      @Mapping(source = "amount", target = "amount")
  })
  SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage mapCheckOverLimit(CheckOverLimitRequest params);

}
