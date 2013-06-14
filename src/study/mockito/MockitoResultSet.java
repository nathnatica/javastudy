package study.mockito;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoResultSet {

	@SuppressWarnings({ "unused", "unchecked" })
	public static ResultSet makeResultSet(final List<String> aColumns, final List<? extends Object>... rows) throws Exception {
		ResultSet result = Mockito.mock(ResultSet.class);
		final AtomicInteger currentIndex = new AtomicInteger(-1);

		Mockito.when(result.next()).thenAnswer(new Answer() {
			public Object answer (InvocationOnMock aInvocation) {
				return currentIndex.incrementAndGet() < rows.length;
			}
		});

		final ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		Answer rowLookupAnswer = new Answer() {
			public Object answer (InvocationOnMock aInvocation) {
				int rowIndex = currentIndex.get();
				int columnIndex = aColumns.indexOf(argument.getValue());
				return rows[currentIndex.get()].get(columnIndex);
			}
		};

		final ArgumentCaptor<Integer> argumentInt = ArgumentCaptor.forClass(Integer.class);
		Answer rowLookupAnswerInt = new Answer() {
			public Object answer (InvocationOnMock aInvocation) {
				int rowIndex = currentIndex.get();
				int columnIndex = argumentInt.getValue() -1;
				return rows[currentIndex.get()].get(columnIndex);
			}
		};

		Mockito.when(result.getString(argument.capture())).thenAnswer(rowLookupAnswer);
		Mockito.when(result.getShort(argument.capture())).thenAnswer(rowLookupAnswer);
		Mockito.when(result.getDate(argument.capture())).thenAnswer(rowLookupAnswer);
		Mockito.when(result.getInt(argument.capture())).thenAnswer(rowLookupAnswer);
		Mockito.when(result.getTimestamp(argument.capture())).thenAnswer(rowLookupAnswer);

		Mockito.when(result.getString(argumentInt.capture())).thenAnswer(rowLookupAnswerInt);

		
		
	
		
		// for resultSet MetaData
		// can /only/ use getColumnCount() and getColumnName()
		final ArgumentCaptor<Integer> argumentMetaData = ArgumentCaptor.forClass(Integer.class);
		
		Answer rsMetaDataAnswer = new Answer() {
			public Object answer (InvocationOnMock aInvocation) throws Exception {
				ResultSetMetaData rsMetaData = Mockito.mock(ResultSetMetaData.class);
				
				Mockito.when(rsMetaData.getColumnCount()).thenAnswer(new Answer() {
					public Object answer (InvocationOnMock aInvocation) {
						int colCount = aColumns.size();
						return colCount;
					}
				});
			
				Mockito.when(rsMetaData.getColumnName(argumentMetaData.capture())).thenAnswer(new Answer() {
					public Object answer (InvocationOnMock aInvocation) {
						return aColumns.get(argumentMetaData.getValue() -1);
					}
				});
				
				
				return rsMetaData;
			}
		};	
		
		Mockito.when(result.getMetaData()).thenAnswer(rsMetaDataAnswer);

		return result;
	}
}
