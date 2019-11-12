package ru.philit.ufs.model.converter.esb.asfs;

import static ru.philit.ufs.model.entity.oper.CashOrderStatus.CREATED;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderType;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.user.Subbranch;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;


public class CashOrderAdapterTest extends AsfsAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-41f7-916c-4c17ca79e969";

  private CashOrder cashOrder;
  private Workplace workplace;
  private SrvCreateCashOrderRs response1;
  private SrvUpdStCashOrderRs response2;
  private SrvGetWorkPlaceInfoRs response3;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    cashOrder = new CashOrder();
    cashOrder.setCashOrderStatus(CREATED);
    cashOrder.setCashOrderId("12345");
    cashOrder.setAccountId("12344");
    cashOrder.setAmount(BigDecimal.valueOf(100000));
    cashOrder.setCashOrderINum("55555");
    cashOrder.setUserFullName("Ivanov Ivan Ivanovich");
    cashOrder.setFdestleName("123");
    cashOrder.setCashOrderType(CashOrderType.KO_1);
    cashOrder.setCashSymbols(new ArrayList<>());
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setAmount(BigDecimal.TEN);
    cashSymbol.setCode("A");
    cashOrder.getCashSymbols().add(cashSymbol);
    cashOrder.setCreatedDttm(date(2019, 10, 10, 10, 10));
    cashOrder.setLegalEntityShortName("OOO");
    cashOrder.setOperatorPosition("1");
    cashOrder.setOperationId("333");
    cashOrder.setRecipientBank("Bank");
    cashOrder.setRecipientBankBic("66666");
    cashOrder.setSenderBank("Bank1");
    cashOrder.setSenderBankBic("77777");
    cashOrder.setResponseCode("8888");
    cashOrder.setResponseMsg("message");
    cashOrder.setClientTypeFk(false);
    cashOrder.setUserPosition("position");
    Subbranch subbranch = new Subbranch();
    subbranch.setGosbCode("123456");
    subbranch.setOsbCode("1234");
    subbranch.setSubbranchCode("0987");
    subbranch.setTbCode("6543");
    subbranch.setVspCode("21");
    cashOrder.setSubbranch(subbranch);
    Representative repData = new Representative();
    repData.setAddress("Moscow, Arbat 12");
    repData.setBirthDate(new Date(1980, 03, 03));
    repData.setInn("1234567890");
    repData.setPlaceOfBirth("Moscow");
    repData.setFirstName("Ivan");
    repData.setPatronymic("Ivanovich");
    repData.setLastName("Ivanov");
    repData.setId("1");
    repData.setResident(false);
    cashOrder.setRepresentative(repData);

    workplace = new Workplace();
    workplace.setId("123");
    workplace.setReceiveDate(date(2019, 8, 2, 12, 00));
    workplace.setRequestUid("12345");
    workplace.setCashboxDeviceId("11111");
    workplace.setLimit(BigDecimal.TEN);
    workplace.setCurrencyType("USD");
    workplace.setSubbranchCode("9999");
    workplace.setType(WorkplaceType.CASHBOX);
    workplace.setCashboxOnBoard(false);
    workplace.setAmount(BigDecimal.valueOf(1000));
    workplace.setCashboxDeviceType("A");

    response1 = new SrvCreateCashOrderRs();
    response1.setHeaderInfo(headerInfo(FIX_UUID));
    response1
        .setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage());
    response1.getSrvCreateCashOrderRsMessage()
        .setKO1(new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1());
    response1.getSrvCreateCashOrderRsMessage().getKO1().setAccountId("12344");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setAmount(BigDecimal.valueOf(1000));
    response1.getSrvCreateCashOrderRsMessage().getKO1()
        .setCashOrderStatus(CashOrderStatusType.CREATED);
    response1.getSrvCreateCashOrderRsMessage().getKO1().setUserPosition("position");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setUserFullName("Ivanov Ivan Ivanovich");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setOperatorPosition("1");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setFDestLEName("123");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setClientTypeFK(false);
    response1.getSrvCreateCashOrderRsMessage().getKO1().setRecipientBankBIC("66666");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setRecipientBank("Bank");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setSenderBank("Bank1");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setSenderBankBIC("77777");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setLegalEntityShortName("OOO");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setRepFIO("Ivanov Ivan Ivanovich");
    SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols cashSymbols1
        = new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols();
    SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols.CashSymbolItem cashSymbolItem1
        = new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols.CashSymbolItem();
    cashSymbolItem1.setCashSymbol("A");
    cashSymbolItem1.setCashSymbolAmount(BigDecimal.TEN);
    cashSymbols1.getCashSymbolItem().add(cashSymbolItem1);
    response1.getSrvCreateCashOrderRsMessage().getKO1().setCashSymbols(cashSymbols1);
    response1.getSrvCreateCashOrderRsMessage().getKO1()
        .setCreatedDttm(xmlCalendar(2019, 10, 10, 10, 10));
    response1.getSrvCreateCashOrderRsMessage().getKO1().setINN("1234567890");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setOperationId("333");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderINum("55555");
    response1.getSrvCreateCashOrderRsMessage().getKO1()
        .setCashOrderType(ru.philit.ufs.model.entity.esb.asfs.CashOrderType.KO_1);
    response1.getSrvCreateCashOrderRsMessage().getKO1().setResponseMsg("8888");
    response1.getSrvCreateCashOrderRsMessage().getKO1().setResponseCode("message");

    response2 = new SrvUpdStCashOrderRs();
    response2.setHeaderInfo(headerInfo(FIX_UUID));
    response2.setSrvUpdCashOrderRsMessage(new SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage());
    response2.getSrvUpdCashOrderRsMessage()
        .setCashOrderType(ru.philit.ufs.model.entity.esb.asfs.CashOrderType.KO_1);
    response2.getSrvUpdCashOrderRsMessage().setCashOrderStatus(CashOrderStatusType.COMMITTED);
    response2.getSrvUpdCashOrderRsMessage().setCashOrderINum("55555");
    response2.getSrvUpdCashOrderRsMessage().setCashOrderId("12345");
    response2.getSrvUpdCashOrderRsMessage().setResponseMsg("message");
    response2.getSrvUpdCashOrderRsMessage().setResponseCode("8888");

    response3 = new SrvGetWorkPlaceInfoRs();
    response3.setHeaderInfo(headerInfo(FIX_UUID));
    response3
        .setSrvGetWorkPlaceInfoRsMessage(new SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage());
    response3.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceLimit(BigDecimal.TEN);
    response3.getSrvGetWorkPlaceInfoRsMessage().setAmount(BigDecimal.valueOf(1000));
    response3.getSrvGetWorkPlaceInfoRsMessage().setCurrentCurrencyType("USD");
    response3.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoardDevice("A");
    response3.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoard(false);
    response3.getSrvGetWorkPlaceInfoRsMessage().setSubbranchCode("9999");
    response3.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceType(BigInteger.valueOf(0));
    response3.getSrvGetWorkPlaceInfoRsMessage().setCashboxDeviceType("A");
  }

  @Test
  public void testRequestCreateCashOrder() {
    SrvCreateCashOrderRq request = CashOrderAdapter.requestCreateOrder(cashOrder);
    assertHeaderInfo(headerInfo());
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAccountId(),
        cashOrder.getAccountId());
  }

  @Test
  public void testRequestUpdateCashOrder() {
    SrvUpdStCashOrderRq request = CashOrderAdapter.requestUpdCashOrder(cashOrder);
    assertHeaderInfo(headerInfo());
    Assert.assertNotNull(request.getSrvUpdCashOrderRqMessage());
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderId(), "12345");
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderStatus(),
        CashOrderStatusType.CREATED);

  }

  @Test
  public void testRequestGetWorkPlaceInfo() {
    SrvGetWorkPlaceInfoRq request = CashOrderAdapter.requestGetWorkPlace("123");
    assertHeaderInfo(headerInfo());
    Assert.assertNotNull(request.getSrvGetWorkPlaceInfoRqMessage());
    Assert.assertEquals(request.getSrvGetWorkPlaceInfoRqMessage().getWorkPlaceUId(), "123");

  }

  @Test
  public void convertSrvCreateCashOrderRs() {
    CashOrder cashOrder = CashOrderAdapter.convert(response1);
    assertHeaderInfo(cashOrder, FIX_UUID);
    Assert.assertEquals(cashOrder.getAccountId(),
        response1.getSrvCreateCashOrderRsMessage().getKO1().getAccountId());
    Assert.assertEquals(cashOrder.getAmount(),
        response1.getSrvCreateCashOrderRsMessage().getKO1().getAmount());
  }

  @Test
  public void convertSrvUpdStCashOrderRs() {
    CashOrder cashOrder = CashOrderAdapter.convert(response2);
    assertHeaderInfo(cashOrder, FIX_UUID);
    Assert.assertEquals(cashOrder.getCashOrderINum(),
        response2.getSrvUpdCashOrderRsMessage().getCashOrderINum());
    Assert
        .assertEquals(cashOrder.getCashOrderStatus().code(), CashOrderStatusType.COMMITTED.value());
  }

  @Test
  public void convertGetWorkPlaceInfoRs() {
    Workplace workplace = CashOrderAdapter.convert(response3);
    assertHeaderInfo(workplace, FIX_UUID);
    Assert.assertEquals(workplace.getAmount(),
        response3.getSrvGetWorkPlaceInfoRsMessage().getAmount());
    Assert.assertEquals(workplace.getLimit(),
        response3.getSrvGetWorkPlaceInfoRsMessage().getWorkPlaceLimit());
    Assert.assertEquals(workplace.getCashboxDeviceType(),
        response3.getSrvGetWorkPlaceInfoRsMessage().getCashboxDeviceType());
    Assert.assertEquals(workplace.getSubbranchCode(),
        response3.getSrvGetWorkPlaceInfoRsMessage().getSubbranchCode());
    Assert.assertEquals(workplace.getType().code(),
        response3.getSrvGetWorkPlaceInfoRsMessage().getWorkPlaceType().intValue());
  }

  @Test
  public void testRequestCreateCashOrderMapStruct() {
    SrvCreateCashOrderRq request = CashOrderAdapter.requestCreateOrderMapStruct(cashOrder);
    assertHeaderInfo(headerInfo());
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAccountId(),
        cashOrder.getAccountId());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getCashOrderId(),
        cashOrder.getCashOrderId());
  }

  @Test
  public void testRequestUpdStCashOrderMapStruct() {
    SrvUpdStCashOrderRq request = CashOrderAdapter.requestUpdStCashOrderMapStruct(cashOrder);
    assertHeaderInfo(headerInfo());
    Assert.assertNotNull(request.getSrvUpdCashOrderRqMessage());
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderId(), "12345");
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderStatus(),
        CashOrderStatusType.CREATED);
  }

  @Test
  public void testRequestGetWorkPlaceInfoMapStruct() {
    SrvGetWorkPlaceInfoRq request =
        CashOrderAdapter.requestGetWorkPlaceInfoMapStruct("123");
    assertHeaderInfo(headerInfo());
    Assert.assertNotNull(request.getSrvGetWorkPlaceInfoRqMessage());
    Assert.assertEquals(request.getSrvGetWorkPlaceInfoRqMessage().getWorkPlaceUId(), "123");
  }


}
