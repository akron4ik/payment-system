package ru.philit.ufs.model.cache.hazelcast;

import static org.mockito.Mockito.when;

import com.hazelcast.core.IMap;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

public class HazelcastMockCacheImplTest {

  private static final String CTRL_TASK_ELEMENT = "\"pkgTaskId\":10";
  private static final String INN = "77700044422232";

  static class TestTaskBody {

    public Long pkgTaskId;

    public TestTaskBody(Long pkgTaskId) {
      this.pkgTaskId = pkgTaskId;
    }
  }

  @Mock
  private HazelcastMockServer hazelcastMockServer;

  private HazelcastMockCacheImpl mockCache;

  private IMap<Long, Map<Long, String>> tasksCardDepositByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksCardWithdrawByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksAccountDepositByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksAccountWithdrawByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksCheckbookIssuingByPackageId = new MockIMap<>();
  private IMap<Date, Map<String, SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1>> cashOrders =
      new MockIMap<>();
  private IMap<Long, PkgTaskStatusType> taskStatuses = new MockIMap<>();
  private IMap<Long, OperationPackageInfo> packageById = new MockIMap<>();
  private IMap<String, Long> packageIdByInn = new MockIMap<>();

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockCache = new HazelcastMockCacheImpl(hazelcastMockServer);
    when(hazelcastMockServer.getTasksCardDepositByPackageId())
        .thenReturn(tasksCardDepositByPackageId);
    when(hazelcastMockServer.getTasksCardWithdrawByPackageId())
        .thenReturn(tasksCardWithdrawByPackageId);
    when(hazelcastMockServer.getTasksAccountDepositByPackageId())
        .thenReturn(tasksAccountDepositByPackageId);
    when(hazelcastMockServer.getTasksAccountWithdrawByPackageId())
        .thenReturn(tasksAccountWithdrawByPackageId);
    when(hazelcastMockServer.getTasksCheckbookIssuingByPackageId())
        .thenReturn(tasksCheckbookIssuingByPackageId);
    when(hazelcastMockServer.getTaskStatuses()).thenReturn(taskStatuses);
    when(hazelcastMockServer.getPackageById()).thenReturn(packageById);
    when(hazelcastMockServer.getPackageIdByInn()).thenReturn(packageIdByInn);
    when(hazelcastMockServer.getCashOrders()).thenReturn(cashOrders);
  }

  @Test
  public void testSaveTask() {
    // given
    TestTaskBody taskBody = new TestTaskBody(10L);

    // when
    mockCache.saveTaskCardDeposit(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCardDepositByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskCardWithdraw(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCardWithdrawByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCardWithdrawByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskAccountDeposit(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksAccountDepositByPackageId.containsKey(1L));
    Assert.assertTrue(tasksAccountDepositByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskAccountWithdraw(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksAccountWithdrawByPackageId.containsKey(1L));
    Assert.assertTrue(tasksAccountWithdrawByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskCheckbookIssuing(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCheckbookIssuingByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCheckbookIssuingByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));
  }

  @Test
  public void createCashOrder() throws Exception {
    //given
    SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1 ko1 =
        new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1();
    String cashOrderId = "12345";
    Date day = new Date();
    //when
    mockCache.createCashOrder(cashOrderId, ko1, day);
    //then
    Assert.assertTrue(cashOrders.containsKey(day));
  }

  @Test
  public void updCashOrderSt() throws Exception {
    SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1 ko1 =
        new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1();
    Date day = new Date();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(day);
    ko1.setCashOrderId("12345");
    ko1.setCashOrderStatus(CashOrderStatusType.CREATED);
    ko1.setCreatedDttm(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
    CashOrderStatusType cashOrderStatusType = CashOrderStatusType.COMMITTED;
    String cashOrderId = "12345";

    mockCache.createCashOrder(cashOrderId, ko1, day);
    Assert.assertEquals(cashOrders.get(day).get(cashOrderId).getCashOrderStatus(),
        CashOrderStatusType.CREATED);
    mockCache.updateCashOrderSt(cashOrderId, cashOrderStatusType);
    Assert.assertEquals(cashOrders.get(day).get(cashOrderId).getCashOrderStatus(),
        CashOrderStatusType.COMMITTED);
  }

  @Test
  public void checkOverLimit() {
    SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1 ko1 =
        new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1();
    ko1.setCashOrderId("123456");
    ko1.setCashOrderStatus(CashOrderStatusType.CREATED);
    ko1.setAmount(BigDecimal.valueOf(1000));
    ko1.setAccountId("1234567");
    Date day = new Date();
    String cashOrderId = "123456";
    mockCache.createCashOrder(cashOrderId, ko1, day);

    boolean flag = mockCache.checkOverLimit(ko1.getAccountId(), day);
    Assert.assertTrue(flag);
  }

  @Test
  public void testSaveTaskStatus() {
    // when
    mockCache.saveTaskStatus(1L, PkgTaskStatusType.ACTIVE);
    //then
    Assert.assertTrue(taskStatuses.containsKey(1L));
    Assert.assertEquals(taskStatuses.get(1L), PkgTaskStatusType.ACTIVE);
  }

  @Test
  public void testCreatePackage() {
    // when
    Long packageId = mockCache.checkPackage(INN);
    // then
    Assert.assertNull(packageId);

    // when
    OperationPackageInfo packageInfo = mockCache.createPackage(INN, "12345", "Sidorov_SS");
    // then
    Assert.assertNotNull(packageInfo.getId());

    // when
    Long packageId2 = mockCache.checkPackage(INN);
    // then
    Assert.assertEquals(packageInfo.getId(), packageId2);

    // when
    OperationPackageInfo packageInfo2 = mockCache.createPackage(INN, "12345", "Sidorov_SS");
    // then
    Assert.assertNotEquals(packageInfo.getId(), packageInfo2.getId());
  }

  @Test
  public void testSearchTaskCardDeposit() {
    // when
    Map<Long, List<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem>> resultMap =
        mockCache.searchTasksCardDeposit(null, null, null, null, null);
    // then
    Assert.assertNotNull(resultMap);
    Assert.assertTrue(resultMap.isEmpty());
  }
}
