package ru.philit.ufs.esb.mock.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.philit.ufs.esb.MessageProcessor;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.HeaderInfoType;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;


@Service
public class AsfsMockService extends CommonMockService implements MessageProcessor {

  private static final String CONTEXT_PATH = "ru.philit.ufs.model.entity.esb.asfs";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final EsbClient esbClient;
  private final MockCache mockCache;

  private final JaxbConverter jaxbConverter = new JaxbConverter(CONTEXT_PATH);

  @Autowired
  public AsfsMockService(EsbClient esbClient, MockCache mockCache) {
    this.esbClient = esbClient;
    this.mockCache = mockCache;
  }

  @PostConstruct
  public void init() {
    esbClient.addMessageProcessor(this);
    logger.info("{} started", this.getClass().getSimpleName());
  }

  @Override
  public boolean processMessage(String requestMessage) {
    try {
      Object request = jaxbConverter.getObject(requestMessage);
      logger.debug("Received message: {}", request);
      if (request != null) {
        if (request instanceof SrvCreateCashOrderRq) {
          sendResponse(getResponse((SrvCreateCashOrderRq) request));

        } else if (request instanceof SrvUpdStCashOrderRq) {
          sendResponse(getResponse((SrvUpdStCashOrderRq) request));

        } else if (request instanceof SrvGetWorkPlaceInfoRq) {
          sendResponse(getResponse((SrvGetWorkPlaceInfoRq) request));

        } else if (request instanceof SrvCheckOverLimitRq) {
          sendResponse(getResponse((SrvCheckOverLimitRq) request));
        }

        return true;
      }

    } catch (JAXBException e) {
      // this message can not be processed this processor
      logger.trace("this message can not be processed this processor", e);
    }
    return false;
  }

  private void sendResponse(Object responseObject) throws JAXBException {
    String responseMessage = jaxbConverter.getXml(responseObject);
    esbClient.sendMessage(responseMessage);
  }

  private SrvCreateCashOrderRs getResponse(SrvCreateCashOrderRq request) {
    System.out.println(request);
    SrvCreateCashOrderRs response = new SrvCreateCashOrderRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage());
    response.getSrvCreateCashOrderRsMessage()
        .setKO1(new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1());
    response.getSrvCreateCashOrderRsMessage().getKO1().setResponseCode("0");
    response.getSrvCreateCashOrderRsMessage().getKO1().setResponseMsg("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderId("12345");
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderINum("");
    response.getSrvCreateCashOrderRsMessage().getKO1()
        .setCashOrderStatus(CashOrderStatusType.CREATED);
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderType(CashOrderType.KO_1);
    response.getSrvCreateCashOrderRsMessage().getKO1()
        .setCreatedDttm(xmlCalendar(2017, 6, 4, 9, 30));
    response.getSrvCreateCashOrderRsMessage().getKO1().setOperationId("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setRepFIO("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setLegalEntityShortName("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setINN("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setAmount(BigDecimal.valueOf(70500));
    response.getSrvCreateCashOrderRsMessage().getKO1().setAccountId("");
    SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols cashSymbols =
        new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols();
    SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1
        .CashSymbols.CashSymbolItem cashSymbolItem =
        new SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols.CashSymbolItem();
    cashSymbolItem.setCashSymbol("02");
    cashSymbolItem.setCashSymbolAmount(BigDecimal.valueOf(70500));
    cashSymbols.getCashSymbolItem().add(cashSymbolItem);
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashSymbols(cashSymbols);
    response.getSrvCreateCashOrderRsMessage().getKO1().setSenderBank("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setSenderBankBIC("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setRecipientBank("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setRecipientBankBIC("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setClientTypeFK(true);
    response.getSrvCreateCashOrderRsMessage().getKO1().setFDestLEName("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setOperatorPosition("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setUserFullName("");
    response.getSrvCreateCashOrderRsMessage().getKO1().setUserPosition("");
    mockCache.saveCashOrders(response.getSrvCreateCashOrderRsMessage().getKO1().getCashOrderId(),
        response.getSrvCreateCashOrderRsMessage().getKO1());
    return response;
  }

  private SrvUpdStCashOrderRs getResponse(SrvUpdStCashOrderRq request) {
    SrvUpdStCashOrderRs response = new SrvUpdStCashOrderRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvUpdCashOrderRsMessage(new SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage());

    //String cashOrderId = request.getSrvUpdCashOrderRqMessage().getCashOrderId();
    //CashOrderStatusType statusType = request.getSrvUpdCashOrderRqMessage().getCashOrderStatus();
    response.getSrvUpdCashOrderRsMessage().setResponseCode("123456789 ");
    response.getSrvUpdCashOrderRsMessage().setResponseMsg("123");
    response.getSrvUpdCashOrderRsMessage().setCashOrderId(/*cashOrderId*/"12345");
    response.getSrvUpdCashOrderRsMessage().setCashOrderINum("1234");
    response.getSrvUpdCashOrderRsMessage().setCashOrderStatus(CashOrderStatusType.CREATED);
    response.getSrvUpdCashOrderRsMessage().setCashOrderType(CashOrderType.KO_1);
    mockCache.updateCashOrdersSt("12345", CashOrderStatusType.COMMITTED);
    return response;
  }

  private SrvGetWorkPlaceInfoRs getResponse(SrvGetWorkPlaceInfoRq request) {
    System.out.println(request);
    SrvGetWorkPlaceInfoRs response = new SrvGetWorkPlaceInfoRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response
        .setSrvGetWorkPlaceInfoRsMessage(new SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage());
    response.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceType(BigInteger.valueOf(0));
    response.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoard(true);
    response.getSrvGetWorkPlaceInfoRsMessage().setSubbranchCode("");
    response.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoardDevice("");
    response.getSrvGetWorkPlaceInfoRsMessage().setCurrentCurrencyType("");
    response.getSrvGetWorkPlaceInfoRsMessage().setAmount(BigDecimal.valueOf(50000));
    response.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceLimit(BigDecimal.valueOf(100000));
    SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit
        operationTypeLimit =
        new SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit();
    SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage
        .WorkPlaceOperationTypeLimit.OperationTypeLimitItem
        operationTypeLimitItem =
        new SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage
            .WorkPlaceOperationTypeLimit.OperationTypeLimitItem();
    operationTypeLimitItem.setOperationCategory(BigInteger.valueOf(1));
    operationTypeLimitItem.setOperationLimit(BigDecimal.valueOf(1));
    operationTypeLimit.getOperationTypeLimitItem().add(operationTypeLimitItem);
    response.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceOperationTypeLimit(operationTypeLimit);
    return response;
  }

  private SrvCheckOverLimitRs getResponse(SrvCheckOverLimitRq request) {
    System.out.println(request.toString());
    SrvCheckOverLimitRs response = new SrvCheckOverLimitRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCheckOverLimitRsMessage(new SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage());
    response.getSrvCheckOverLimitRsMessage().setResponseCode("1234");
    response.getSrvCheckOverLimitRsMessage()
        .setStatus(LimitStatusType.LIMIT_PASSED);
    return response;
  }

  private HeaderInfoType copyHeaderInfo(HeaderInfoType headerInfo0) {
    HeaderInfoType headerInfo = new HeaderInfoType();
    headerInfo.setRqUID(headerInfo0.getRqUID());
    headerInfo.setRqTm(headerInfo0.getRqTm());
    headerInfo.setSpName(headerInfo0.getSystemId());
    headerInfo.setSystemId(headerInfo0.getSpName());
    return headerInfo;
  }
}
