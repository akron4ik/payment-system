package ru.philit.ufs.web.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.cache.AnnouncementCache;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.UserCache;
import ru.philit.ufs.model.cache.hazelcast.HazelcastCacheImpl;
import ru.philit.ufs.model.cache.mock.MockCacheImpl;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;
import ru.philit.ufs.web.exception.InvalidDataException;

@RunWith(DataProviderRunner.class)
public class UserProviderTest {

  private static final String LOGIN = "Ivanov_II";
  private static final String PASSWORD = "Password";
  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User(LOGIN), "2");
  private static final String SESSION_ID = "cafe";
  private static final String WORKPLACE_ID = "27841892";
  private static final String CURRENCY_TYPE = "RUB";
  private static final BigDecimal AMOUNT = BigDecimal.valueOf(17418);

  /**
   * Список операторов, считаемых пустыми.
   */
  @DataProvider
  public static Object[] cacheEmptyOperators() {
    return new Object[]{
        null,
        new Operator()
    };
  }

  @Mock
  private UserCache cache;
  @Spy
  private MockCache mockCache = new MockCacheImpl();

  private UserProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new UserProvider(cache, mockCache);
  }

  @Test
  public void testLoginUser() throws Exception {
    // when
    doNothing().when(cache).addUser(anyString(), any(User.class));
    provider.loginUser(LOGIN, PASSWORD);

    // verify
    verify(cache, times(1)).addUser(anyString(), any(User.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testLoginUser_NullLogin() throws Exception {
    // when
    provider.loginUser(null, PASSWORD);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test
  public void testLogoutUser() throws Exception {
    // when
    when(cache.removeUser(anyString())).thenReturn(true);
    provider.logoutUser(SESSION_ID);

    // verify
    verify(cache, times(1)).removeUser(anyString());
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetUser() throws Exception {
    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    provider.getUser(SESSION_ID);

    // verify
    verify(cache, times(1)).getUser(anyString());
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetOperator() throws Exception {
    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    provider.getOperator(CLIENT_INFO);

    // verify
    verify(cache, times(1)).getUser(anyString());
    verify(cache, times(1)).getOperator(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  @UseDataProvider("cacheEmptyOperators")
  public void testGetOperator_NullFromCache(Operator cached) throws Exception {
    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(cached);
    provider.getOperator(CLIENT_INFO);

    // verify
    verify(cache, times(1)).getUser(anyString());
    verify(cache, times(1)).getOperator(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetWorkplace() throws Exception {
    // given
    Workplace workplace = new Workplace();
    workplace.setType(WorkplaceType.UWP);
    workplace.setId(WORKPLACE_ID);
    workplace.setCashboxOnBoard(true);
    workplace.setCurrencyType(CURRENCY_TYPE);
    workplace.setAmount(new BigDecimal("12345"));
    workplace.setLimit(AMOUNT);

    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(workplace);

    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getWorkplace(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetWorkplace_NullFromCache() throws Exception {
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(null);
    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetWorkplace_WrongType() throws Exception {
    // given
    Workplace workplace = new Workplace();
    workplace.setType(WorkplaceType.UWP);
    workplace.setCashboxOnBoard(false);

    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(workplace);

    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetWorkplace_WrongCurrencyType() throws Exception {
    // given
    Workplace workplace = new Workplace();
    workplace.setType(WorkplaceType.CASHBOX);

    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    //when(mockCache.getWorkplace(WORKPLACE_ID)).thenReturn(workplace);
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO /*any(ClientInfo.class)*/))
        .thenReturn(workplace);

    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetWorkplace_NullAmount() throws Exception {
    // given
    Workplace workplace = new Workplace();
    workplace.setType(WorkplaceType.OTHER);
    workplace.setCurrencyType(CURRENCY_TYPE);

    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    //when(mockCache.getWorkplace(WORKPLACE_ID)).thenReturn(workplace);
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO /*any(ClientInfo.class)*/))
        .thenReturn(workplace);

    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetWorkplace_OverLimitedAmount() throws Exception {
    // given
    Workplace workplace = new Workplace();
    workplace.setType(WorkplaceType.OTHER);
    workplace.setCurrencyType(CURRENCY_TYPE);
    workplace.setCashboxOnBoard(true);
    workplace.setAmount(new BigDecimal("9999999999"));
    workplace.setLimit(new BigDecimal("1000000"));

    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(workplace);

    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);
  }

  @Test
  public void testCheckWorkplaceIncreasedAmount() throws Exception {
    // given
    Workplace workplace = new Workplace();
    workplace.setAmount(new BigDecimal("12345"));
    workplace.setCurrencyType(CURRENCY_TYPE);
    workplace.setLimit(AMOUNT);

    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(workplace);

    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);
  }

  @Test(expected = InvalidDataException.class)
  public void testCheckWorkplaceIncreasedAmount_NullFromCache() throws Exception {

    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(null);
    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);

  }

  @Test(expected = InvalidDataException.class)
  public void testCheckWorkplaceIncreasedAmount_NullAmount() throws Exception {
    Workplace workplace = new Workplace();
    workplace.setCurrencyType(CURRENCY_TYPE);
    workplace.setLimit(AMOUNT);
    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    //when(mockCache.getWorkplace(WORKPLACE_ID)).thenReturn(new Workplace());
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(new Workplace());
    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);
  }

  @Test(expected = InvalidDataException.class)
  public void testCheckWorkplaceIncreasedAmount_OverLimitedAmount() throws Exception {
    // given
    Workplace workplace = new Workplace();
    workplace.setAmount(new BigDecimal("9999999999"));
    workplace.setCurrencyType(CURRENCY_TYPE);
    workplace.setLimit(AMOUNT);

    // when
    when(cache.getUser(anyString())).thenReturn(new User());
    when(cache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(getOperator());
    when(cache.getWorkplace(WORKPLACE_ID, CLIENT_INFO)).thenReturn(workplace);
    provider.getWorkplaceInfo(WORKPLACE_ID, CLIENT_INFO);

    //provider.checkOverLimit(AMOUNT, CLIENT_INFO);
  }

  private Operator getOperator() {
    Operator operator = new Operator();
    operator.setWorkplaceId(WORKPLACE_ID);
    return operator;
  }

}