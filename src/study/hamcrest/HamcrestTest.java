package study.hamcrest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static study.hamcrest.IsNotNumber.*;

public class HamcrestTest {

	public class Person {
		public String name;

		public Person(String name) {
			this.name = name;
		}
	}


	public static void main(String[] args) {

		HamcrestTest test = new HamcrestTest();
		Person me = test.new Person("Rafael");
		Person theOther = test.new Person("Rafael");
		
		//examples
		assertThat(me, is(theOther));
		assertThat(me, equalTo(theOther));
		assertThat("it's me", me.name, equalTo("Rafael"));
		//custom matcher
		assertThat(1.0, is(notANumber()));

		
		//sugar generation

		//first create an xml configuration file listing all the Matcher classes that should be searched for
		//factory methods annotated with the org.hamcrest.Factory annotation
		//<matchers>
		//	<factory class="org.hamcrest.core.Is"/>
		//	<factory class="study.hamcrest.IsNotNumber"/>
		//</matchers>

		//second, run the org.hamcrest.generator.config.XmlConfigurator command-line tool
		//this tool takes the xml configuration file and generates a single java class that includes all the factory methods
		//specified yb the xml file. run with no arguments will display a usage message
	}




}