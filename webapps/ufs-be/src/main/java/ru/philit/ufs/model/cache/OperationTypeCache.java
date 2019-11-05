package ru.philit.ufs.model.cache;

import java.math.BigDecimal;
import java.util.List;

import ru.philit.ufs.model.entity.oper.*;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Интерфейс доступа к кешу данных для типов операций.
 */
public interface OperationTypeCache {

  List<OperationType> getOperationTypes(ClientInfo clientInfo);

  List<CashSymbol> getCashSymbols(CashSymbolRequest request, ClientInfo clientInfo);

  List<OperationTypeFavourite> getOperationTypeFavourites(ClientInfo clientInfo);

  void saveOperationTypeFavourites(List<OperationTypeFavourite> current,
      List<OperationTypeFavourite> previous, ClientInfo clientInfo);

}
