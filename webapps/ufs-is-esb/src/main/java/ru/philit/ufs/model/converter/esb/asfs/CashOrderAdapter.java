package ru.philit.ufs.model.converter.esb.asfs;

import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.*;
import ru.philit.ufs.model.entity.oper.*;
import ru.philit.ufs.model.entity.oper.CashOrderType;
import ru.philit.ufs.model.entity.user.Subbranch;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;

import java.util.ArrayList;


/**
 * Преобразователь между сущностью CashOrder и соответствующим транспортным объектом.
 */
public class CashOrderAdapter extends AsfsAdapter {

  private static OperTypeLabel operTypeLabel(OperationTypeCode operationTypeCode) {
    return (operationTypeCode != null) ? OperTypeLabel.fromValue(operationTypeCode.code()) : null;

  }

  private static CashOrderStatusType cashOrderStatusType(CashOrderStatus cashOrderStatus) {
    return (cashOrderStatus != null) ? CashOrderStatusType.fromValue(cashOrderStatus.code()) : null;
  }

  private static CashOrderStatus cashOrderStatusTypeCode(CashOrderStatusType cashOrderStatusType) {
    return (cashOrderStatusType != null) ? CashOrderStatus.getByCode(cashOrderStatusType.value())
        : null;
  }

  private static CashOrderType cashOrderTypeCode(
      ru.philit.ufs.model.entity.esb.asfs.CashOrderType cashOrderType) {
    return (cashOrderType != null) ? CashOrderType.getByCode(cashOrderType.value()) : null;
  }

  private static SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData repData(
      Representative representative) {
    SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData repData = new SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData();
    if (representative != null) {
      repData.setAddress(representative.getAddress());
      repData.setDateOfBirth(xmlCalendar(representative.getBirthDate()));
      repData.setINN(representative.getInn());
      repData.setPlaceOfBirth(representative.getPlaceOfBirth());
      repData.setRepFIO(
          representative.getLastName() + " " + representative.getFirstName() + " " + representative
              .getPatronymic());
      repData.setRepID(representative.getId());
      repData.setResident(representative.isResident());
    }
    return repData;
  }

  private static SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo additionalInfo(
      Subbranch subbranch) {
    SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo additionalInfo = new SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo();
    if (subbranch != null) {
      additionalInfo.setGOSBCode(subbranch.getGosbCode());
      additionalInfo.setOSBCode(subbranch.getOsbCode());
      additionalInfo.setSubbranchCode(subbranch.getSubbranchCode());
      additionalInfo.setTBCode(subbranch.getTbCode());
      additionalInfo.setVSPCode(subbranch.getVspCode());
    }
    return additionalInfo;

  }

  //******** Mappers *******

  private static void map(CashOrder cashOrder,
      SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage message) {
    message.setAccountId(cashOrder.getAccountId());
    message.setAmount(cashOrder.getAmount());
    message.setAmountInWords(cashOrder.getAmountInWords());
    message.setCashOrderId(cashOrder.getCashOrderId());
    message.setCashOrderINum(cashOrder.getCashOrderINum());
    message.setCashOrderStatus(cashOrderStatusType(cashOrder.getCashOrderStatus()));
    message.setCurrencyType(cashOrder.getCurrencyType());
    message.setWorkPlaceUId(cashOrder.getWorkPlaceUId());
    message.setOperationType(operTypeLabel(cashOrder.getOperationTypeCode()));
    message.setRepData(repData(cashOrder.getRepData()));
    message.setAdditionalInfo(additionalInfo(cashOrder.getSubbranch()));

  }

  private static void map(SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage message,
      CashOrder cashOrder) {
    cashOrder.setCashOrderId(message.getKO1().getCashOrderId());
    cashOrder.setAccountId(message.getKO1().getAccountId());
    cashOrder.setAmount(message.getKO1().getAmount());
    cashOrder.setCashOrderId(message.getKO1().getCashOrderId());
    cashOrder.setCashOrderINum(message.getKO1().getCashOrderINum());
    cashOrder.setCashOrderStatus(cashOrderStatusTypeCode(message.getKO1().getCashOrderStatus()));
    cashOrder.setCashOrderType(cashOrderTypeCode(message.getKO1().getCashOrderType()));
    cashOrder.setCashSymbols(new ArrayList<CashSymbol>());
    if (message.getKO1().getCashSymbols() != null) {
      for (SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols.CashSymbolItem cashSymbolItem :
          message.getKO1().getCashSymbols().getCashSymbolItem()) {
        CashSymbol cashSymbol = new CashSymbol();
        cashSymbol.setCode(cashSymbolItem.getCashSymbol());
        cashSymbol.setAmount(cashSymbolItem.getCashSymbolAmount());
        cashOrder.getCashSymbols().add(cashSymbol);
      }
    }
    cashOrder.setCreatedDttm(date(message.getKO1().getCreatedDttm()));
    cashOrder.setFdestleName(message.getKO1().getFDestLEName());
    cashOrder.setRepData(new Representative());
    cashOrder.getRepData().setInn(message.getKO1().getINN());
    cashOrder.setLegalEntityShortName(message.getKO1().getLegalEntityShortName());
    cashOrder.setOperatorPosition(message.getKO1().getOperatorPosition());
    cashOrder.setOperationId(message.getKO1().getOperationId());
    cashOrder.setRecipientBank(message.getKO1().getRecipientBank());
    cashOrder.setRecipientBankBic(message.getKO1().getRecipientBankBIC());
    cashOrder.getRepData().setLastName(message.getKO1().getRepFIO().split(" ")[0]);
    cashOrder.getRepData().setFirstName(message.getKO1().getRepFIO().split(" ")[1]);
    cashOrder.getRepData().setPatronymic(message.getKO1().getRepFIO().split(" ")[2]);
    cashOrder.setResponseCode(message.getKO1().getResponseCode());
    cashOrder.setResponseMsg(message.getKO1().getResponseMsg());
    cashOrder.setUserFullName(message.getKO1().getUserFullName());
    cashOrder.setSenderBank(message.getKO1().getSenderBank());
    cashOrder.setSenderBankBic(message.getKO1().getSenderBankBIC());
    cashOrder.setClientTypeFk(message.getKO1().isClientTypeFK());
    cashOrder.setUserPosition(message.getKO1().getUserPosition());

  }

  private static void map(SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage message,
      Workplace workplace) {
    workplace.setAmount(message.getAmount());
    workplace.setCashboxDeviceType(message.getCashboxDeviceType());
    workplace.setCashboxOnBoard(message.isCashboxOnBoard());
    workplace.setType(WorkplaceType.getByCode(integerValue(message.getWorkPlaceType())));
    workplace.setSubbranchCode(message.getSubbranchCode());
    workplace.setCurrencyType(message.getCurrentCurrencyType());
    workplace.setLimit(message.getWorkPlaceLimit());
    workplace.setCategoryLimits(new ArrayList<OperationTypeLimit>());
    if (message.getWorkPlaceOperationTypeLimit() != null) {
      for (SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem
          operationTypeLimitItem : message.getWorkPlaceOperationTypeLimit()
          .getOperationTypeLimitItem()) {
        OperationTypeLimit operationTypeLimit = new OperationTypeLimit();
        operationTypeLimit.setCategoryId(operationTypeLimitItem.getOperationCategory().toString());
        operationTypeLimit.setLimit(operationTypeLimitItem.getOperationLimit());
        workplace.getCategoryLimits().add(operationTypeLimit);
      }
    }
    workplace.setCashboxDeviceId(message.getCashboxOnBoardDevice());
  }

  private static void map(SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage message,
      CashOrder cashOrder) {
    cashOrder.setCashOrderId(message.getCashOrderId());
    cashOrder.setResponseCode(message.getResponseCode());
    cashOrder.setResponseMsg(message.getResponseMsg());
    cashOrder.setCashOrderINum(message.getCashOrderINum());
    cashOrder.setCashOrderStatus(cashOrderStatusTypeCode(message.getCashOrderStatus()));
    cashOrder.setCashOrderType(cashOrderTypeCode(message.getCashOrderType()));
  }

  public static SrvCreateCashOrderRq requestCreateOrder(CashOrder cashOrder) {
    SrvCreateCashOrderRq request = new SrvCreateCashOrderRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCreateCashOrderRqMessage(new SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage());
    map(cashOrder, request.getSrvCreateCashOrderRqMessage());
    return request;
  }

  public static SrvUpdStCashOrderRq requestUpdCashOrder(CashOrder cashOrder) {
    SrvUpdStCashOrderRq request = new SrvUpdStCashOrderRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvUpdCashOrderRqMessage(new SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage());
    request.getSrvUpdCashOrderRqMessage().setCashOrderId(cashOrder.getCashOrderId());
    request.getSrvUpdCashOrderRqMessage()
        .setCashOrderStatus(cashOrderStatusType(cashOrder.getCashOrderStatus()));
    return request;
  }

  public static SrvGetWorkPlaceInfoRq requestGetWorkPlace(String workPlaceId) {
    SrvGetWorkPlaceInfoRq request = new SrvGetWorkPlaceInfoRq();
    request.setHeaderInfo(headerInfo());
    request
        .setSrvGetWorkPlaceInfoRqMessage(new SrvGetWorkPlaceInfoRq.SrvGetWorkPlaceInfoRqMessage());
    request.getSrvGetWorkPlaceInfoRqMessage().setWorkPlaceUId(workPlaceId);
    return request;
  }

  public static CashOrder convert(SrvCreateCashOrderRs response) {
    CashOrder cashOrder = new CashOrder();
    map(response.getHeaderInfo(), cashOrder);
    map(response.getSrvCreateCashOrderRsMessage(), cashOrder);
    return cashOrder;
  }

  public static CashOrder convert(SrvUpdStCashOrderRs response) {
    CashOrder cashOrder = new CashOrder();
    map(response.getHeaderInfo(), cashOrder);
    map(response.getSrvUpdCashOrderRsMessage(), cashOrder);
    return cashOrder;
  }

  public static Workplace convert(SrvGetWorkPlaceInfoRs response) {
    Workplace workplace = new Workplace();
    map(response.getHeaderInfo(), workplace);
    map(response.getSrvGetWorkPlaceInfoRsMessage(), workplace);
    return workplace;
  }


}
