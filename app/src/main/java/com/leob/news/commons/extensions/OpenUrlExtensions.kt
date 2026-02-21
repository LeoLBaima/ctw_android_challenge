package com.leob.news.commons.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

fun Context.openExternalUrl(
    url: String?,
    chooserTitle: String? = null,
    normalizeMissingScheme: Boolean = true,
): Boolean {
    val raw = url?.trim().orEmpty()
    if (raw.isBlank()) return false

    val normalized = if (normalizeMissingScheme && !raw.contains("://")) {
        "https://$raw"
    } else {
        raw
    }

    val uri = runCatching { normalized.toUri() }.getOrNull() ?: return false

    val scheme = uri.scheme?.lowercase()
    if (scheme != "http" && scheme != "https") return false

    val viewIntent = Intent(Intent.ACTION_VIEW, uri)
    val intentToLaunch = if (!chooserTitle.isNullOrBlank()) {
        Intent.createChooser(viewIntent, chooserTitle)
    } else {
        viewIntent
    }

    val canHandle = intentToLaunch.resolveActivity(packageManager) != null
    if (!canHandle) return false

    return try {
        startActivity(intentToLaunch)
        true
    } catch (_: ActivityNotFoundException) {
        false
    } catch (_: Exception) {
        false
    }
}

