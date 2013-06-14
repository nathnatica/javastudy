package study.mockito;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;

public class MockitoResultSetTest {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		ResultSet resultSet = MockitoResultSet.makeResultSet(
				Arrays.asList("Id", "Name", "Birthday"),
				Arrays.asList("1", "Hamlet", new Date(9999L)),
				Arrays.asList("2", "Andres", new Date(8888L)),
				Arrays.asList("3", "Dierk",  new Date(7777L))
		);
		
		while (resultSet.next()) {
			System.out.println(resultSet.getString(1));
			System.out.println(resultSet.getString("Name"));
		}

		//metadata test
		int cnt = resultSet.getMetaData().getColumnCount();
		for (int i = 0; i < cnt; i++) {
			System.out.println(resultSet.getMetaData().getColumnName(i+1));
		}	

	}

}
