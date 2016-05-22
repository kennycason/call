package com.kennycason.run.commands

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by kenny on 5/21/16.
 */
class PlaceHolderValidatorTest {
    val placeHolderValidator = PlaceHolderValidator()

    @Test
    fun valid() {
        assertTrue(placeHolderValidator.validate("", emptyList()).valid)
        assertTrue(placeHolderValidator.validate("@{1}", listOf("foo")).valid)
        assertTrue(placeHolderValidator.validate("@{1} @{1}", listOf("foo")).valid)
        assertTrue(placeHolderValidator.validate("@{1} @{2}", listOf("foo", "bar")).valid)
        assertTrue(placeHolderValidator.validate("@{1} @{2} @{3}", listOf("foo", "bar", "wut")).valid)
    }

    @Test
    fun invalid() {
        assertFalse(placeHolderValidator.validate("", listOf("foo")).valid)
        assertFalse(placeHolderValidator.validate("@{1}", emptyList()).valid)
        assertFalse(placeHolderValidator.validate("@{1} @{2}", listOf("foo")).valid)
        assertFalse(placeHolderValidator.validate("@{1} @{2}", listOf("foo", "bar", "wut")).valid)
    }
}