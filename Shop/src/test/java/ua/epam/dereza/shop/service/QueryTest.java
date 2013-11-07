package ua.epam.dereza.shop.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class QueryTest {

	Query query;
	String category;
	String keyword;
	Integer limit;
	List<String> manufacturers;
	Integer offset;
	BigDecimal priceFrom;
	BigDecimal priceTo;
	String sortBy;

	String queryContain[];
	String queryNotContain[];

	public QueryTest(String category, String keyword, Integer limit, List<String> manufacturers, Integer offset, BigDecimal priceFrom, BigDecimal priceTo, String sortBy, String queryContain[], String queryNotContain[]) {
		this.category = category;
		this.keyword = keyword;
		this.limit = limit;
		this.manufacturers = manufacturers;
		this.offset = offset;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.sortBy = sortBy;
		this.queryContain = queryContain;
		this.queryNotContain = queryNotContain;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{null, null, null, null, null, null, null, null, new String[]{}, new String[]{" WHERE ", " IN ", " LIMIT ", " OFFSET ", " AND "}},
				{"category", null, null, null, null, null, null, null, new String[]{" WHERE "}, new String[]{" AND "}},
				{"category", "keyword", null, null, null, null, null, null, new String[]{" WHERE ", " AND "}, new String[]{}},
				{"category", null, 0, null, null, null, null, null, new String[]{" WHERE ", "LIMIT"}, new String[]{" AND "}},
				{"category", null, null, Arrays.asList(new String[]{"manuf1","manuf2"}), null, null, null, null, new String[]{" WHERE ", " IN ", " AND "}, new String[]{}},
				{null, null, null, null, 0, null, null, null, new String[]{" OFFSET "}, new String[]{" AND ", " WHERE "}},
				{null, null, null, null, 0, null, null, "not existing field", new String[]{}, new String[]{" AND ", " WHERE ", " SORT BY "}},
				{null, null, null, null, 0, null, null, "name", new String[]{" ORDER BY "}, new String[]{" AND ", " WHERE "}}
		};
		return Arrays.asList(data);
	}

	@After
	public void tearDown() throws Exception {
		query = null;
	}

	@Test
	public void test() {
		query = new Query.Builder()
		.category(category)
		.keyword(keyword)
		.limit(limit)
		.manufacturers(manufacturers)
		.offset(offset)
		.priceFrom(priceFrom)
		.priceTo(priceTo)
		.sortBy(sortBy)
		.build();

		String quer = query.getProductQuery();

		for(String contain : queryContain){
			assertTrue("Query("+ quer +") must contains -> " + contain, quer.contains(contain));
		}

		for(String notContain : queryNotContain){
			assertFalse("Query("+ quer +") must doesn't contain -> " + notContain, quer.contains(notContain));
		}
	}

}
