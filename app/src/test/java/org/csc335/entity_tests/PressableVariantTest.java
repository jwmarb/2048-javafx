package org.csc335.entity_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.csc335.entity.PressableVariant;
import org.junit.jupiter.api.Test;

public class PressableVariantTest {
	
	@Test
	public void toStringTest() {
		assertEquals("filled", PressableVariant.FILLED + "");
		assertEquals("outlined", PressableVariant.OUTLINED + "");
	}
}
