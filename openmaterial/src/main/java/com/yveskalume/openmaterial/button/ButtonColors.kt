package com.yveskalume.openmaterial.button

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
internal fun ButtonColors.containerColor(enabled: Boolean): Color =
    if (enabled) containerColor else disabledContainerColor

/**
 * Represents the content color for this button, depending on [enabled].
 *
 * @param enabled whether the button is enabled
 */
@Stable
internal fun ButtonColors.contentColor(enabled: Boolean): Color =
    if (enabled) contentColor else disabledContentColor