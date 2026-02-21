package com.leob.news.commons.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class DateExtensionsTest {

    @Test
    fun `null publishedAt returns empty`() {
        val result = (null as String?).toDisplayPublishedAt(locale = Locale.US)
        assertEquals("", result)
    }

    @Test
    fun `blank publishedAt returns empty`() {
        val result = "   ".toDisplayPublishedAt(locale = Locale.US)
        assertEquals("", result)
    }

    @Test
    fun `invalid publishedAt returns original by default`() {
        val raw = "not-a-date"
        val result = raw.toDisplayPublishedAt(locale = Locale.US)
        assertEquals(raw, result)
    }

    @Test
    fun `valid iso z formats to non-empty`() {
        val raw = "2026-02-21T10:15:30Z"
        val result = raw.toDisplayPublishedAt(locale = Locale.US)
        // Format varies by API level / formatter, but should never be blank or unchanged.
        assert(result.isNotBlank())
        assert(result != raw)
    }
}

