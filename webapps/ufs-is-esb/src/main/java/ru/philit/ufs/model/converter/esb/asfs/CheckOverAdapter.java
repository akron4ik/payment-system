package ru.philit.ufs.model.converter.esb.asfs;

import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;

import static ru.philit.ufs.model.entity.esb.asfs.LimitStatusType.LIMIT_ERROR;
import static ru.philit.ufs.model.entity.esb.asfs.LimitStatusType.LIMIT_PASSED;

public class CheckOverAdapter extends AsfsAdapter {


  private static boolean limitStatusType(LimitStatusType limitStatusType) {
    return (limitStatusType.value().equals(LIMIT_PASSED.value()));
  }

  private static void map(CheckOverLimitRequest params,
      SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage message) {
    message.setUserLogin(params.getUserLogin());
    message.setAmount(params.getAmount());
    message.setTobeIncreased(params.isTobeIncreased());
  }

  private static void map(SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage message,
      ExternalEntityContainer<Boolean> container) {
    container.setData(limitStatusType(message.getStatus()));
    container.setResponseCode(message.getResponseCode());
  }

  /**
   * Возвращает объект запроса проверки операции.
   */
  public static SrvCheckOverLimitRq requestByParams(CheckOverLimitRequest params) {
    SrvCheckOverLimitRq request = new SrvCheckOverLimitRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCheckOverLimitRqMessage(new SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage());
    map(params, request.getSrvCheckOverLimitRqMessage());
    return request;
  }

  /**
   * Преобразует транспортный объект ответа во внутреннюю сущность.
   */
  public static ExternalEntityContainer<Boolean> convert(SrvCheckOverLimitRs response) {
    ExternalEntityContainer<Boolean> container = new ExternalEntityContainer<>();
    map(response.getHeaderInfo(), container);
    map(response.getSrvCheckOverLimitRsMessage(), container);
    return container;
  }


}
