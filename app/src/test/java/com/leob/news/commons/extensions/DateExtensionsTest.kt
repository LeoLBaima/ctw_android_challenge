package com.leob.news.commons.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class DateExtensionsTest {

    @Test
    fun `GIVEN publishedAt is null WHEN toDisplayPublishedAt THEN returns empty string`() {
        val result = (null as String?).toDisplayPublishedAt(locale = Locale.US)
        assertEquals("", result)
    }

    @Test
    fun `GIVEN publishedAt is blank WHEN toDisplayPublishedAt THEN returns empty string`() {
        val result = "   ".toDisplayPublishedAt(locale = Locale.US)
        assertEquals("", result)
    }

    @Test
    fun `GIVEN publishedAt is invalid WHEN toDisplayPublishedAt THEN returns original string by default`() {
        val raw = "not-a-date"
        val result = raw.toDisplayPublishedAt(locale = Locale.US)
        assertEquals(raw, result)
    }

    @Test
    fun `GIVEN publishedAt is ISO-8601 Z WHEN toDisplayPublishedAt THEN returns formatted non-empty string`() {
        val raw = "2026-02-21T10:15:30Z"
        val result = raw.toDisplayPublishedAt(locale = Locale.US)
        assert(result.isNotBlank())
        assert(result != raw)
    }
}
