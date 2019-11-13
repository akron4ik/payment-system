package ru.philit.ufs.model.converter.esb.asfs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;
import ru.philit.ufs.model.entity.user.Workplace;

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

  @Mappings({
      @Mapping(source = "responseCode", target = "responseCode"),
      @Mapping(source = "responseMsg", target = "responseMsg"),
      @Mapping(source = "cashOrderId", target = "cashOrderId"),
      @Mapping(source = "cashOrderINum", target = "cashOrderINum"),
      @Mapping(source = "cashOrderStatus", target = "cashOrderStatus"),
      @Mapping(source = "cashOrderType", target = "cashOrderType"),
      @Mapping(source = "createdDttm", target = "createdDttm"),
      @Mapping(source = "operationId", target = "operationId"),
      @Mapping(expression = "java(response.getRepFIO().split(\" \")[0])",
          target = "representative.firstName"),
      @Mapping(expression = "java(response.getRepFIO().split(\" \")[1])",
          target = "representative.lastName"),
      @Mapping(expression = "java(response.getRepFIO().split(\" \")[2])",
          target = "representative.patronymic"),
      @Mapping(source = "legalEntityShortName", target = "legalEntityShortName"),
      @Mapping(source = "INN", target = "subbranch.inn"),
      @Mapping(source = "amount", target = "amount"),
      @Mapping(source = "accountId", target = "accountId"),
      @Mapping(source = "cashSymbols.cashSymbolItem", target = "cashSymbols"),
      @Mapping(source = "senderBank", target = "senderBank"),
      @Mapping(source = "senderBankBIC", target = "senderBankBic"),
      @Mapping(source = "recipientBank", target = "recipientBank"),
      @Mapping(source = "recipientBankBIC", target = "recipientBankBic"),
      @Mapping(source = "clientTypeFK", target = "clientTypeFk"),
      //@Mapping(source = "fDestLEName", target = "fdestleName"),
      @Mapping(expression = "java(response.getFDestLEName())", target = "fdestleName"),
      @Mapping(source = "operatorPosition", target = "operatorPosition"),
      @Mapping(source = "userFullName", target = "userFullName"),
      @Mapping(source = "userPosition", target = "userPosition")
  })
  CashOrder covertCreateCashOrder(SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1 response);

  @Mappings({
      @Mapping(source = "responseCode", target = "responseCode"),
      @Mapping(source = "responseMsg", target = "responseMsg"),
      @Mapping(source = "cashOrderId", target = "cashOrderId"),
      @Mapping(source = "cashOrderINum", target = "cashOrderINum"),
      @Mapping(source = "cashOrderStatus", target = "cashOrderStatus"),
      @Mapping(source = "cashOrderType", target = "cashOrderType")
  })
  CashOrder convertUpdStCashOrder(SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage response);

  @Mappings({
      @Mapping(source = "workPlaceType", target = "type"),
      @Mapping(source = "cashboxOnBoard", target = "cashboxOnBoard"),
      @Mapping(source = "subbranchCode", target = "subbranchCode"),
      @Mapping(source = "cashboxOnBoardDevice", target = "cashboxDeviceId"),
      @Mapping(source = "cashboxDeviceType", target = "cashboxDeviceType"),
      @Mapping(source = "currentCurrencyType", target = "currencyType"),
      @Mapping(source = "amount", target = "amount"),
      @Mapping(source = "workPlaceLimit", target = "limit"),
      @Mapping(source = "workPlaceOperationTypeLimit.operationTypeLimitItem",
          target = "categoryLimits")
  })
  Workplace convertGetWorkPlaceInfo(SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage response);

  @Mappings({
      @Mapping(source = "cashSymbol", target = "code"),
      @Mapping(source = "cashSymbolAmount", target = "amount")
  })
  CashSymbol map(CashSymbolItem cashSymbolItem);

  @ValueMappings({
      @ValueMapping(source = "CREATED", target = "CREATED"),
      @ValueMapping(source = "COMMITTED", target = "COMMITTED")
  })
  CashOrderStatus map(CashOrderStatusType cashOrderStatusType);

  @ValueMappings({
      @ValueMapping(source = "CREATED", target = "CREATED"),
      @ValueMapping(source = "COMMITTED", target = "COMMITTED")
  })
  CashOrderStatusType map(CashOrderStatus cashOrderStatus);

  @Mappings({
      @Mapping(source = "code", target = "cashSymbol"),
      @Mapping(source = "amount", target = "cashSymbolAmount")
  })
  SrvCreateCashOrderRqMessage
      .AdditionalInfo.CashSymbols.CashSymbolItem map(CashSymbol  cashSymbol);

  @Mappings({
      @Mapping(source = "responseCode", target = "responseCode"),
      @Mapping(source = "status", target = "data")
  })
  ExternalEntityContainer<Boolean> convertCheckOverLimit(SrvCheckOverLimitRs
      .SrvCheckOverLimitRsMessage response);
}
