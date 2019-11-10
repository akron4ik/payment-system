package ru.philit.ufs.model.cache;

import java.util.List;

import ru.philit.ufs.model.entity.oper.*;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Интерфейс доступа к кешу данных для операций.
 */
public interface OperationCache {

  Operation getOperation(Long taskId);

  void addOperation(Long taskId, Operation operation);

  OperationPackage getPackage(OperationPackageRequest request, ClientInfo clientInfo);

  OperationPackage createPackage(OperationPackageRequest request, ClientInfo clientInfo);

  OperationPackage getTasksInPackage(OperationTasksRequest request, ClientInfo clientInfo);

  OperationPackage addTasksInPackage(OperationPackage request, ClientInfo clientInfo);

  OperationPackage updateTasksInPackage(OperationPackage request, ClientInfo clientInfo);

  List<OperationPackage> getTasksInPackages(OperationTasksRequest request, ClientInfo clientInfo);

  CashOrder createCashOrder(CashOrder cashOrder, ClientInfo clientInfo);

  CashOrder updCashOrder(CashOrder cashOrder, ClientInfo clientInfo);

  CashOrder getCashBook(String cashOrderId, ClientInfo clientInfo);

  void addCoToCashBook(CashOrder cashOrder, ClientInfo clientInfo);

}
