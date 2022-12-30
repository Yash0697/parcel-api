package com.yash.costcalculator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class VoucherItemTest {

	@InjectMocks
	VoucherItem voucherItem;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testGetCode() {
		voucherItem.setCode("PFI");
		assertEquals(voucherItem.getCode(), "PFI");
	}
	
	@Test
	public void testGetDiscount() {
		voucherItem.setDiscount(7.5f);
		assertEquals(voucherItem.getDiscount(), 7.5f);
	}
	
	@Test
	public void testGetExpiry() {
		voucherItem.setExpiry(new Date());
		assertEquals(voucherItem.getExpiry(), new Date());
	}
}
