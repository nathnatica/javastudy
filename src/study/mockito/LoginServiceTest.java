package study.mockito;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

	@Mock
	private IAccount account;
	private IAccountRepository accountRepository;
	private LoginService service;

	@Before
	public void init() {
		accountRepository = mock(IAccountRepository.class);
		when(accountRepository.find(anyString())).thenReturn(account);
		service = new LoginService(accountRepository);
	}

	private void willPasswordMatch(boolean value) {
		when(account.passwordMatches(anyString())).thenReturn(value);
	}

	@Test
	public void itShouldSetAccountLoggedInWhenPasswordMatches() {
		willPasswordMatch(true);
		service.login("brett", "password");
		verify(account, times(1)).setLoggedIn(true);
	}

	@Test
	public void itShouldSetAccountToRevokedAfterThreeFailedLoginAttempts() {
		willPasswordMatch(false);

		for (int i=0; i<3; i++) {
			service.login("brett", "password");
		}
		verify(account, times(1)).setLoggedIn(true);
	}

	@Test
	public void isShouldNotSetAccountLoggedInIfPasswordDoesNotMatch() {
		willPasswordMatch(false);
		service.login("brett", "password");
		service.login("brett", "password");
		service.login("schuchert", "password");
		verify(account, never()).setLoggedIn(true);
	}

	@Test (expected = AccountLoginLimitReachedException.class)
	public void itShouldNotAllowConcurrentLogins() {
		willPasswordMatch(true);
		when(account.isLoggedIn()).thenReturn(true);
		service.login("brett", "password");
	}

	@Test (expected = AccountNotFountException.class)
	public void isShouldThrowExceptionIfAccountNotFound() {
		when(accountRepository.find("schuchert")).thenReturn(null);
		service.login("schuchert", "password");
	}
	
	@Test (expected = AccountRevokedException.class)
	public void isShouldNotBePossibleToLogIntoRevokedAccount() {
		willPasswordMatch(true);
		when(account.isRevoked()).thenReturn(true);
		service.login("brett", "password");
	}
	
}
