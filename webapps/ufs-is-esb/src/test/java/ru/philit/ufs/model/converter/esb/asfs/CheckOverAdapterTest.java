package ru.philit.ufs.model.converter.esb.asfs;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;

public class CheckOverAdapterTest extends AsfsAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-41f7-916c-4c17ca79e969";
  private CheckOverLimitRequest checkOverLimitRequest;
  private SrvCheckOverLimitRs response;

  @Before
  public void setUp() {
    checkOverLimitRequest = new CheckOverLimitRequest();
    checkOverLimitRequest.setUserLogin("Ivanov");
    checkOverLimitRequest.setAmount(BigDecimal.TEN);
    checkOverLimitRequest.setTobeIncreased(false);
    checkOverLimitRequest.setLimitStatusTypeCode(true);

    response = new SrvCheckOverLimitRs();
    response.setHeaderInfo(headerInfo(FIX_UUID));
    response.setSrvCheckOverLimitRsMessage(new SrvCheckOverLimitRsMessage());
    response.getSrvCheckOverLimitRsMessage().setStatus(LimitStatusType.LIMIT_PASSED);
    response.getSrvCheckOverLimitRsMessage().setResponseCode("123");
  }

  @Test
  public void testRequestCheckOverLimit() {
    SrvCheckOverLimitRq request = CheckOverAdapter.requestByParams(checkOverLimitRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCheckOverLimitRqMessage());
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getAmount(),
        checkOverLimitRequest.getAmount());
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getUserLogin(),
        checkOverLimitRequest.getUserLogin());
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().isTobeIncreased(),
        checkOverLimitRequest.isTobeIncreased());
  }

  @Test
  public void testConvertCheckOverLimitRs() {
    ExternalEntityContainer<Boolean> container = CheckOverAdapter.convert(response);
    assertHeaderInfo(container, FIX_UUID);
    Assert.assertEquals(container.getData(), true);
  }

  @Test
  public void testMultiAdapterTest() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityContainer.class);
  }

}
