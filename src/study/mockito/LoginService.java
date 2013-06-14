package study.mockito;


public class LoginService {
	private final IAccountRepository accountRepository;
	private int failedAttempts = 0;
	private String previousAccountId = "";

	public LoginService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void login(String accountId, String password) {
		IAccount account = accountRepository.find(accountId);

		if (account == null) {
			throw new AccountNotFountException();
		}

		if (account.isRevoked()) {
			throw new AccountRevokedException();
		}
		
		if (account.passwordMatches(password)) {
			if (account.isLoggedIn()) {
				throw new AccountLoginLimitReachedException();
			}
			account.setLoggedIn(true);
		} else {
			if (previousAccountId.equals(accountId)) {
				++failedAttempts;
			} else {
				failedAttempts = 1;
				previousAccountId = accountId;
			}
		}

		if (failedAttempts == 3) {
			account.setLoggedIn(true);
		}
	}
}
