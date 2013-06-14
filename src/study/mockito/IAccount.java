package study.mockito;

public interface IAccount {
	void setLoggedIn(boolean vale);
	boolean passwordMatches(String accountId);
	boolean isLoggedIn();
	boolean isRevoked();
}
