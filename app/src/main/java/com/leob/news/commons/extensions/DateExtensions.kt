package com.leob.news.commons.extensions

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String?.toDisplayPublishedAt(
    locale: Locale = Locale.getDefault(),
    fallbackToOriginal: Boolean = true,
): String {
    val raw = this?.trim().orEmpty()
    if (raw.isBlank()) return ""

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        return try {
            val instant = runCatching { java.time.Instant.parse(raw) }.getOrNull()
                ?: runCatching { java.time.OffsetDateTime.parse(raw).toInstant() }.getOrNull()
                ?: runCatching { java.time.ZonedDateTime.parse(raw).toInstant() }.getOrNull()

            if (instant == null) {
                if (fallbackToOriginal) raw else ""
            } else {
                val zoned = instant.atZone(java.time.ZoneId.systemDefault())
                val formatter = java.time.format.DateTimeFormatter
                    .ofLocalizedDateTime(java.time.format.FormatStyle.MEDIUM)
                    .withLocale(locale)
                formatter.format(zoned)
            }
        } catch (_: Exception) {
            if (fallbackToOriginal) raw else ""
        }
    }

    val date = parseIso8601Compat(raw, locale)
        ?: return if (fallbackToOriginal) raw else ""

    val out = SimpleDateFormat("MMM d, yyyy HH:mm", locale)
    return out.format(date)
}

private fun parseIso8601Compat(raw: String, locale: Locale): Date? {
    val patterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ssXXX",
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
    )

    for (pattern in patterns) {
        try {
            val sdf = SimpleDateFormat(pattern, locale)
            if (pattern.endsWith("'Z'")) {
                sdf.timeZone = TimeZone.getTimeZone("UTC")
            }
            return sdf.parse(raw)
        } catch (_: ParseException) {
            // try next
        }
    }

    return null
}

