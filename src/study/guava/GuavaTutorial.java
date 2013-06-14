package study.guava;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

public class GuavaTutorial {

	public static void charMatcher() {

		String string = "Scream 4";
		CharMatcher matcher = CharMatcher.JAVA_LETTER_OR_DIGIT;

		int count = matcher.countIn(string);
		System.out.println("Letter or digit count: " + count);
		// 7 characters

		System.out.println(matcher.matchesAllOf("screma"));
		// true

		System.out.println(matcher.matchesAllOf("scream "));
		// flase because there's an empty

		System.out.println(matcher.matchesNoneOf("_?=)("));
		// true

		CharMatcher negatedMatcher = matcher.negate();
		System.out.println(negatedMatcher.matchesAllOf("scream"));
		// false
		System.out.println(negatedMatcher.matchesAllOf("scream "));
		// false
		System.out.println(negatedMatcher.matchesNoneOf("_?=)("));
		// false
	}

	public static void removeAndRetain() {
		String review = "Scream　4 is the #1 teen-slasher!";
		CharMatcher whitespaceMatcher = CharMatcher.JAVA_WHITESPACE;
		String result = whitespaceMatcher.removeFrom(review);
		System.out.println(result);
		// Scream4isthe#1teen-slasher!

		String result2 = CharMatcher.DIGIT.retainFrom(review);
		System.out.println("Retained digits: " + result2);
		// 41

		int indexOfDigit = CharMatcher.DIGIT.indexIn(review);
		System.out.println("index of ditit: " + indexOfDigit);
		// the first element is 4
		// 4's index is 7
	}

	public static void charMatcher3() {
		CharMatcher onlyEvenNumberMatcher = CharMatcher.anyOf("2468");
		CharMatcher noEvenNumberMatcher = CharMatcher.noneOf("2468");

		CharMatcher largeAtoZ = CharMatcher.inRange('A', 'Z');
		CharMatcher aToZ = CharMatcher.inRange('a', 'z').or(largeAtoZ);

		System.out.println(onlyEvenNumberMatcher.matchesAllOf("1354"));
		// false
		System.out.println(onlyEvenNumberMatcher.matchesAllOf("starwars"));
		// false
		System.out.println(onlyEvenNumberMatcher.matchesAllOf("2466"));
		// true

		System.out.println(noEvenNumberMatcher.matchesAllOf("1337"));
		// true
		System.out.println(noEvenNumberMatcher.matchesAllOf("super mario"));
		// true

		System.out.println(aToZ.matchesAllOf("sezin"));
		System.out.println(aToZ.matchesAllOf("SezIn"));
		// all true
	}

	public static void joiner() {
		ArrayList<String> charList = Lists.newArrayList("a","b","c","d");
		StringBuilder buffer = new StringBuilder();

		buffer = Joiner.on("|").appendTo(buffer, charList);
		System.out.println(buffer.toString());
		// a|b|c|d

		String joinedCharList = Joiner.on(", ").join(charList);
		System.out.println(joinedCharList);
		// a, b, c, d

		charList.add(null);
		String join4 = Joiner.on(" - ").skipNulls().join(charList);
		System.out.println(join4);
		// a - b - c - d

		join4 = Joiner.on(" - ").useForNull("defaultValue").join(charList);
		System.out.println(join4);

		String s = Joiner.on(",").join("apapend1", "apend2");
		System.out.println(s);

		Map<String, Long> employeeToNumber = Maps.newHashMap();
		employeeToNumber.put("obi wan", 1L);
		employeeToNumber.put("bobba", 2L);

		MapJoiner mapJoiner = Joiner.on("|").withKeyValueSeparator("->");
		String join5 = mapJoiner.join(employeeToNumber);
		System.out.println(join5);
		// obi wan->1|bobba->2
	}

	public static void splitter() {
		String text = "I　have to test my string splitter, for this purpose I'm writing this text　, ";
		String[] split = text.split(",");
		System.out.println(Arrays.asList(split));
		// [I　have to test my string splitter,  for this purpose I'm writing this text　,  ]

		Iterable<String> split2 = Splitter.on(",").omitEmptyStrings().trimResults().split(text);
		System.out.println(Lists.newArrayList(split2));
		// [I　have to test my string splitter, for this purpose I'm writing this text]

		Iterable<String> split3 = Splitter.fixedLength(5).split(text);
		System.out.println(Lists.newArrayList(split3));
		// [I　hav, e to , test , my st, ring , split, ter, , for t, his p, urpos, e I'm,  writ, ing t, his t, ext　,,  ]
	}

	public static void strings() {
		String emptyToNull = Strings.emptyToNull("");
		System.out.println(emptyToNull);
		// null

		boolean nullOrEmpty = Strings.isNullOrEmpty("");
		System.out.println(nullOrEmpty);
		// true

		String padEnd = Strings.padEnd("star wars", 15, 'X');
		String padStart = Strings.padStart("star wars", 15, 'X');
		System.out.println(padEnd);
		// star warsXXXXXX
		System.out.println(padStart);
		// XXXXXXstar wars
		System.out.println(padStart.length() == 15);
		// true
	}

	static enum Colors{RED, GREEN, BLUE, YELLOW};
	public static void ordering() {
		Employee sezinKarli = new Employee(4, "Sezin Karli", 4);
		Employee darthVader = new Employee(3, "Darth Vader", 5);
		Employee hanSolo = new Employee(2, "Han Solo", 10);

		List<Employee> employeeList = Lists.newArrayList(sezinKarli, darthVader, hanSolo);

		Comparator<Employee> yearsComparator = new Comparator<Employee>() {
			public int compare(Employee employee1, Employee employee2) {
				return (employee1.getYearsOfService() - employee2.getYearsOfService());
			}
		};

		Comparator<Employee> idComparator = new Comparator<Employee>() {
			public int compare(Employee employee1, Employee employee2) {
				return (employee1.getId() - employee2.getId());
			}
		};

		Ordering<Employee> orderUsingYearsComparator = Ordering.from(yearsComparator);
		List<Employee> sortedCopy = orderUsingYearsComparator.sortedCopy(employeeList);
		System.out.println(sortedCopy);
		// [id=4-name=Sezin Karli-years of service=4, id=3-name=Darth Vader-years of service=5, id=2-name=Han Solo-years of service=10]

		Ordering<Color> explicitOrdering = Ordering.explicit(Color.RED, Color.BLUE, Color.GREEN);
		List<Color> colorList = Lists.newArrayList(Color.BLUE, Color.RED, Color.BLUE, Color.GREEN, Color.RED);
		List<Color> sortedCopy8 = explicitOrdering.sortedCopy(colorList);
		System.out.println(sortedCopy8);

		Ordering<Object> usingToString = Ordering.usingToString();
		List<Employee> sortedCopy3 = usingToString.sortedCopy(employeeList);
		System.out.println(sortedCopy3);
		// [id=2-name=Han Solo-years of service=10, id=3-name=Darth Vader-years of service=5, id=4-name=Sezin Karli-years of service=4]

		// natural usging  compareTo()
		Ordering<Employee> natural = Ordering.natural();
		List<Employee> sortedCopy4 = natural.sortedCopy(employeeList);
		System.out.println(sortedCopy4);
		// [id=3-name=Darth Vader-years of service=5, id=2-name=Han Solo-years of service=10, id=4-name=Sezin Karli-years of service=4]

		int binarySearch = natural.binarySearch(sortedCopy4, sezinKarli);
		System.out.println(binarySearch); // 2


		List<Employee> employeeListWithNulls = new ArrayList<Employee>(employeeList);
		employeeListWithNulls.add(null);
		employeeListWithNulls.add(null);

		List<Employee> sortedCopy5 = natural.nullsFirst().sortedCopy(employeeListWithNulls);
		System.out.println(sortedCopy5);

		Employee employeeWithMaxYearsOfService = orderUsingYearsComparator.max(employeeList);
		Employee employeeWithMinYearsOfService = orderUsingYearsComparator.min(employeeList);
		System.out.println(employeeWithMaxYearsOfService);
		System.out.println(employeeWithMinYearsOfService);

		Employee max = orderUsingYearsComparator.max(sezinKarli, darthVader);
		System.out.println(max);

		@SuppressWarnings("unused")
		Ordering<Employee> reverseOrdering = Ordering.from(yearsComparator).reverse();

		@SuppressWarnings("unused")
		Ordering<Employee> combinedComparatorOrdering = orderUsingYearsComparator.compound(idComparator);

		// less than/equal to
		@SuppressWarnings("unused")
		boolean ordered = Ordering.natural().isOrdered(employeeList);
		// strictly less than
		@SuppressWarnings("unused")
		boolean strictlyOrdered = Ordering.natural().isStrictlyOrdered(employeeList);


		// onResultOf()
		Function<String, String> onlyDomainPart = new Function<String, String>() {
			public String apply(String string) {
				int index = string.indexOf("@");
				return string.substring(index);
			}
		};
		ArrayList<String> usernames = Lists.newArrayList("luke@lightside.com", "darth@darkside.com", "obiwan@lightside.com", "leia@starwars.com");
		List<String> sortedCopy9 = Ordering.natural().onResultOf(onlyDomainPart).sortedCopy(usernames);
		System.out.println(sortedCopy9);
	}

	public static void functional() {
		Function <Person, StringBuilder> returnNameInitials = new Function<Person, StringBuilder>() {
			public StringBuilder apply(Person person) {
				char firstCharOfName = Character.toUpperCase(person.getName().charAt(0));
				char firstCharOfLastName = Character.toUpperCase(person.getLastName().charAt(0));
				return new StringBuilder(firstCharOfName + "." + firstCharOfLastName);
			}
		};

		List<Person> personList = Lists.newArrayList(new Person("sezin", "karli"), new Person("brad", "delp"));
		List<StringBuilder> transform3 = Lists.transform(personList, returnNameInitials);
		System.out.println(transform3);
		// [S.K, B.D]

		// list has an impact on the result list !!!!
		personList.add(new Person("rick","neilsen"));
		System.out.println(transform3);
		// [S.K, B.D, R.N]

		// you cant do updates on the result list
		transform3.get(0).append(".B");
		System.out.println(transform3);
		// [S.K, B.D, R.N]
		// if you check the initial list
		// you will see no change too


		// Collections2.transform() is similar to Lists.transform() with the difference that it can
		// work on a larger number of classes. For example, if you want to transform an HashSet of Person
		// cant do that with Lists' or Sets' methods
		Set<Person> personSet = Sets.newHashSet(new Person("seizin", "karli"), new Person("brad", "delp"));
		Collection<StringBuilder> transform4 = Collections2.transform(personSet, returnNameInitials);
		System.out.println(transform4);
		// [S.K, B.D]



		// compose
		Function<Double, Double> squareOfADouble = new Function<Double, Double>() {
			public Double apply(Double double1) {
				return double1 * double1;
			}
		};

		Function<Float, Double> floatToDouble = new Function<Float, Double>() {
			public Double apply(Float float1) {
				return float1.doubleValue();
			}
		};

		ArrayList<Float> floatList = Lists.newArrayList(12.3536343f, 142.3346434633f, 1.4456346f);
		Function<Float, Double> floatToDoubleSquare = Functions.compose(squareOfADouble, floatToDouble);
		List<Double> squareOfFloatsAsDoubles = Lists.transform(floatList, floatToDoubleSquare);
		System.out.println(squareOfFloatsAsDoubles);

		// Functions.toStringFunction()
		// takes each object and returns their toString() result
		List<String> toStringList = Lists.transform(personList, Functions.toStringFunction());
		System.out.println(toStringList);
	}

	public static void functionalMap() {
		// Functions.forMap()
		// lookup from the given map and returns the value corresponding to the key in hand
		Map<String, Long> emailToEmployeeNo = Maps.newHashMap();
		emailToEmployeeNo.put("luke@starwars.com", 1684L);
		emailToEmployeeNo.put("darth@starwars.com", 2454L);
		emailToEmployeeNo.put("obiwan@starwars.com", 3675L);

		Function<String, Long> lookupFunction = Functions.forMap(emailToEmployeeNo);
		ArrayList<String> cantinaVisitOrder = Lists.newArrayList("luke@starwars.com", "luke@starwars.com", "obiwan@starwars.com", "darth@starwars.com");

		List<Long> cantinaVisitOrderById = Lists.transform(cantinaVisitOrder, lookupFunction);
		System.out.println(cantinaVisitOrderById);

		// if the corresponding key is not found during the lookup, then returns given default value
		Long defaultId = 0L;
		@SuppressWarnings("unused")
		Function<String, Long> lookupFunctionWithDefault = Functions.forMap(emailToEmployeeNo, defaultId);

		ArrayList<String> characterList = Lists.newArrayList("Luke 'Blondie' Skywalker", "Darth 'DarkHelmet' Vader", "Obiwan 'Ben' Kenobi");
		Function<String, String> extracNickName = new Function<String, String>() {
			public String apply(String name) {
				Iterable<String> tokens = Splitter.on(" ").trimResults().split(name);
				for (String token : tokens) {
					if(token.startsWith("'")) {
						return token.substring(1, token.length()-1);
					}
				}
				return "none";
			}
		};
		ImmutableMap<String, String> nicknameToNameMap = Maps.uniqueIndex(characterList, extracNickName);
		System.out.println(nicknameToNameMap);
		// {Blondie=Luke 'Blondie' Skywalker, DarkHelmet=Darth 'DarkHelmet' Vader, Ben=Obiwan 'Ben' Kenobi}



		Function<String, String> onlyUsernamePart = new Function<String, String>() {
			public String apply(String string) {
				int index = string.indexOf("@");
				return string.substring(0, index);
			}
		};
		Map<String, String> nicknameToMailMap = Maps.newHashMap();
		nicknameToMailMap.put("luke skywalker", "luck@starwars.com");
		nicknameToMailMap.put("darth vader", "vader@starwars.com");

		Map<String, String> viewMap = Maps.transformValues(nicknameToMailMap, onlyUsernamePart);
		System.out.println(viewMap);
		viewMap.remove("darth vader");
		System.out.println(nicknameToMailMap);
		// {luke skywalker=luck@starwars.com}
		// removal on the initial map has an impact on the view as before

	}

	public static void predicate() {
		Predicate<Person> allowNamesWithVowels = new Predicate<Person>() {
			String[] vowels = {"a","e","i","o","u"};
			public boolean apply(Person person) {
				String firstName = person.getName().toLowerCase();
				for (String vowel : vowels) {
					if (firstName.startsWith(vowel)) {
						return true;
					}
				}
				return false;
			}
		};

		Person frodo = new Person("Fredo","Baggins");
		Person seizin = new Person("Seizin","Karli");
		Person luke = new Person("Luke","Skywalker");
		Person anakin = new Person("Anakin","Skywalker");
		Person eric = new Person("Eric","Draven");

		HashSet<Person> setOfPerson = Sets.newHashSet(eric, frodo, seizin, luke, anakin);
		System.out.println(setOfPerson);

		Set<Person> view = Sets.filter(setOfPerson, allowNamesWithVowels);
		System.out.println(view);
		view.remove(eric);
		System.out.println(view);
		// no eric
		System.out.println(setOfPerson);
		// no eric
		// as you see the removal action is bidirectional
		// the view is updated as well and  the elements that are accepted by teh predicate are added to the view



		Map<String, Integer> characterAppearancesMap = Maps.newHashMap();
		characterAppearancesMap.put("Mario", 15);
		characterAppearancesMap.put("Snake", 8);
		characterAppearancesMap.put("Kratos", 4);

		Predicate<String> allowNamesWithO = new Predicate<String>() {
			public boolean apply(String name) {
				String lowerCaseName = name.toLowerCase();
				return lowerCaseName.contains("o");
			}
		};

		Predicate<Integer> allowLotsOfAppearances = new Predicate<Integer>() {
			public boolean apply(Integer appearance) {
				int numberOfAppearance = appearance.intValue();
				return (numberOfAppearance > 10);
			}
		};

		Map<String, Integer> filterKeys = new HashMap<String, Integer>(
			Maps.filterKeys(characterAppearancesMap, allowNamesWithO));
		System.out.println(filterKeys);
		// {Mario=15, Kratos=4}

		Map<String, Integer> filterValues = new HashMap<String, Integer>(
			Maps.filterValues(filterKeys, allowLotsOfAppearances));
		System.out.println(filterValues);
		// {Mario=15}



		Predicate<Map.Entry<String, Integer>> characterPredicate = new Predicate<Map.Entry<String, Integer>>() {
			public boolean apply(Entry<String, Integer> entry) {
				String key = entry.getKey();
				int value = entry.getValue();
				return (key.contains("o")  && value > 10);
			}
		};
		Map<String, Integer> filterEntries = Maps.filterEntries(characterAppearancesMap, characterPredicate);
		System.out.println(filterEntries);
		// {Mario=15}


		// Functions class has a static method thatll convert your predicate to a Function
		@SuppressWarnings("unused")
		Function<Person, Boolean> vowelVerifier = Functions.forPredicate(allowNamesWithVowels);

		// With forPredicate() we can build a CharMatcher from a Predicate
		Predicate<Character> allowVowel = new Predicate<Character>() {
			char[] vowels = {'a','e','i','o','u'};
			public boolean apply(Character character) {
				char lowerCase = Character.toLowerCase(character.charValue());
				for(char chr : vowels) {
					if (chr == lowerCase)
						return true;
				}
				return false;
			}
		};
		CharMatcher vowelMatcher = CharMatcher.forPredicate(allowVowel);
		int vowelCount = vowelMatcher.countIn("startkiller");
		System.out.println(vowelCount);
		// 3


		Predicate<Person> allowShortLastNames = new Predicate<Person>() {
			public boolean apply(Person person) {
				String lastName = person.getLastName();
				return (lastName.length() < 8);
			}
		};

		Set<Person> onlyShortNames = Sets.filter(setOfPerson, allowShortLastNames);
		System.out.println(onlyShortNames);

		// Predicates.and() Predicates.or() Predicates.not()
		Predicate<Person> combinedPredicates = Predicates.and(allowShortLastNames, allowNamesWithVowels);
		Set<Person> filter = Sets.filter(setOfPerson, combinedPredicates);
		System.out.println(filter);
		// []

		// Predicates.isNull(), notNull()
		// Predicates.contains(), containsPattern()
		// Predicates.in()
		// Predicates.instanceOf()
		// Predicates.composes(Predicates, Function) -> do calculate with Function and Predicate with the return value

	}

	public static void iterablePredicate() {
		Band stratovarius = new Band("Stratovarius", "Finland");
		Band amorphis = new Band("Amorphis", "Finland");

		Set<Band> metalBands = Sets.newHashSet(stratovarius, amorphis);

		Predicate<Band> finlandPredicate = new Predicate<Band>() {
			public boolean apply(Band band) {
				boolean result = band.getCountry().equals("Finland");
				return result;
			}
		};

		boolean all = Iterables.all(metalBands, finlandPredicate);
		System.out.println(all);

		boolean any = Iterables.any(metalBands, finlandPredicate);
		System.out.println(any);

		if (Iterables.indexOf(metalBands, finlandPredicate) != -1) {
			Band bandFromFinland = Iterables.find(metalBands, finlandPredicate);
			System.out.println(bandFromFinland);
		}

		Iterables.removeIf(metalBands, finlandPredicate);
	}

	public static void primitives() {
		int saturatedCast = Ints.saturatedCast(Long.MAX_VALUE);
		System.out.println(saturatedCast);

		long long1 = Integer.MAX_VALUE;
		long1++;

		// IllegalArgumentException : Out of range
		int checkedCast = Ints.checkedCast(long1);
		System.out.println(checkedCast);

		// Collections class has similar min/max methods
		// but it was not straightforward to transfrom an int array to a Collection
		int[] integerArray = new int[]{3, 5, 7, -3, 7};
		System.out.println(Ints.min((integerArray)));

		int indexOf = Ints.indexOf(integerArray, 7);
		System.out.println(indexOf);

		System.out.println(Ints.asList(integerArray));

		System.out.println(Ints.join(",", integerArray));

		int lastIndexOf = Ints.lastIndexOf(integerArray, 7);
		System.out.println(lastIndexOf);

		System.out.println(Ints.contains(integerArray, 60));

		int[] concatResult = Ints.concat(new int[]{1,2,3}, new int[]{4,5,6});
		System.out.println(Ints.join("|", concatResult));

		System.out.println(Ints.compare(2, 4)); // -1
		System.out.println(Ints.compare(5, 4)); // 1
		System.out.println(Ints.compare(3, 3)); // 0

		// Ints.toArray()
		List<Integer> integerList = Lists.newArrayList(0, 2, -8);
		int[] array = Ints.toArray(integerList);
		System.out.println(Ints.join(",", array));

		// Ints.ensureCapacity(array, minlength, padding)
		int[] myIntegerArray = new int[] {1, 4, 5, 6, 3, 2};
		int[] ensureCapacity = Ints.ensureCapacity(myIntegerArray, 5, 3);
		System.out.println(Ints.join(",", ensureCapacity));
		// the same array is returned

		int[] ensureCapacity2 = Ints.ensureCapacity(myIntegerArray, 10, 3);
		System.out.println(Ints.join(",", ensureCapacity2));
		// a new array is returned with 0s at the end
		// 1,4,5,6,3,2,0,0,0,0,0,0,0
		// length = 10+3

		List<int[]> newArrayList = Lists.newArrayList(new int[]{1}, new int[]{2}, new int[]{}, new int[]{1,5,0});
		Collections.sort(newArrayList, Ints.lexicographicalComparator());
		List<String> transform5 = Lists.transform(newArrayList, new Function<int[], String>() {
			public String apply(int[] array) {
				return Ints.join("-", array);
			}
		});
		System.out.println(transform5);
		// [, 1, 1-5-0, 2]
		// empty array is the first

		// Ints.asList()
		// obtain a live view of the array, updates are bi-directional
		int[] integerArray2 = new int[] {1, 2, 3, 4};
		List<Integer> newIntegerList = Ints.asList(integerArray2);
		System.out.println(newIntegerList);
	}


	public static void main(String[] args) {
		//charMatcher();
		//removeAndRetain();
		//joiner();
		//splitter();
		//strings();
		//ordering();
		//functional();
		//functionalMap();
		//predicate();
		//iterablePredicate();

		//primitives();
	}

}
