package net.stefangaertner.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import net.stefangaertner.util.RegexUtil;

public class RegexUtilTest {

	@Test
	public void test() {
		Assert.assertEquals("212", RegexUtil.first("abc 212 cba", "\\w+ (\\d+) \\w+"));
	}
	
}
