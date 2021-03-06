package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ConversionTest {
	@Test
	public void dollarConversion_isCorrect() {
		int euros = Utils.convertDollarToEuro(1000);
		assertEquals(812, euros);
	}

	@Test
	public void euroConversion_isCorrect() {
		int dollars = Utils.convertEuroToDollar(812);
		assertEquals(1000, dollars);
	}

	@SuppressWarnings("HardCodedStringLiteral")
	@Test
	public void dateShouldHaveCorrectFormat() {
		assertTrue(Utils.getTodayDate().matches("\\d{1,2}/\\d{1,2}/\\d{4}$"));
	}
}
