package ru.philit.ufs.model.cache;

import java.math.BigDecimal;
import java.util.List;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Интерфейс доступа к кешу данных для объявлениями на взнос наличных.
 */
public interface AnnouncementCache {

  List<CashDepositAnnouncement> getAnnouncements(CashDepositAnnouncementsRequest request,
      ClientInfo clientInfo);

  CashDepositAnnouncement getAnnouncementById(String id, ClientInfo clientInfo);

  BigDecimal getCommission(AccountOperationRequest request, ClientInfo clientInfo);

  String getAccount20202(String workplaceId, ClientInfo clientInfo);

  Workplace getWorkplace(String workPlaceId, ClientInfo clientInfo);

  Boolean checkOverLimit(CheckOverLimitRequest request, ClientInfo clientInfo);

}
