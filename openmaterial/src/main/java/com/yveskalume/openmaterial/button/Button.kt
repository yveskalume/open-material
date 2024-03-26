package com.yveskalume.openmaterial.button

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yveskalume.openmaterial.MaterialTheme
import com.yveskalume.openmaterial.ProvideContentColorTextStyle

/**
 * <a href="https://m3.material.io/components/buttons/overview" class="external" target="_blank">Material Design button</a>.
 *
 * Buttons help people initiate actions, from sending an email, to sharing a document, to liking a
 * post.
 *
 * ![Filled button image](https://developer.android.com/images/reference/androidx/compose/material3/filled-button.png)
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.buttonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonElevation.shadowElevation] and [ButtonElevation.tonalElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 *
 * Cfr [androidx.compose.material3.Button]
 */
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    LocalOpenMaterialButton.current.Composable(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource,
        content
    )
}

/**
 * The base class for creating customizable filled button component.
 */
open class Button {

    /**
     * The minimum width of the button.
     */
    protected open val minWidth = ButtonDefaults.MinWidth

    /**
     * The minimum height of the button.
     */
    protected open val minHeight = ButtonDefaults.MinHeight

    /**
     * Composable function for rendering the Button.
     * Has the same signature as the Material 3 Button composable
     *
     * @param onClick called when this button is clicked
     * @param modifier the [Modifier] to be applied to this button
     * @param enabled controls the enabled state of this button. When `false`, this component will not
     * respond to user input, and it will appear visually disabled and disabled to accessibility
     * services.
     * @param shape defines the shape of this button's container, border (when [border] is not null),
     * and shadow (when using [elevation])
     * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
     * states. See [ButtonDefaults.buttonColors].
     * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
     * states. This controls the size of the shadow below the button. Additionally, when the container
     * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
     * See [ButtonElevation.shadowElevation] and [ButtonElevation.tonalElevation].
     * @param border the border to draw around the container of this button
     * @param contentPadding the spacing values to apply internally between the container and the
     * content
     * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
     * for this button. You can create and pass in your own `remember`ed instance to observe
     * [Interaction]s and customize the appearance / behavior of this button in different states.
     *
     * Cfr [androidx.compose.material3.Button]
     */

    @Composable
    fun Composable(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        shape: Shape = ButtonDefaults.shape,
        colors: ButtonColors = ButtonDefaults.buttonColors(),
        elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
        border: BorderStroke? = null,
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        interactionSource: MutableInteractionSource? = null,
        content: @Composable RowScope.() -> Unit
    ) {
        @Suppress("NAME_SHADOWING")
        val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
        val containerColor = colors.containerColor(enabled)
        val contentColor = colors.contentColor(enabled)
        val shadowElevation =
            elevation?.shadowElevation(enabled, interactionSource)?.value ?: 0.dp
        Surface(
            onClick = onClick,
            modifier = modifier.semantics { role = Role.Button },
            enabled = enabled,
            shape = shape,
            color = containerColor,
            contentColor = contentColor,
            shadowElevation = shadowElevation,
            border = border,
            interactionSource = interactionSource
        ) {
            provideContentColoTextStyle(
                contentColor = contentColor,
                textStyle = MaterialTheme.typography.labelLarge
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = minWidth,
                            minHeight = minHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }


    /**
     * Customizes how content color and text style are provided to the content. by using the [CompositionLocalProvider]
     *
     * @param contentColor The color of the content.
     * @param textStyle The text style to be applied.
     * @param content The content to be displayed.
     *
     * Cfr [androidx.compose.material3.ProvideContentColorTextStyle]
     */
    @SuppressLint("ComposableNaming")
    @Composable
    protected open fun provideContentColoTextStyle(
        contentColor: Color,
        textStyle: TextStyle,
        content: @Composable () -> Unit
    ) {
        ProvideContentColorTextStyle(
            contentColor = contentColor,
            textStyle = textStyle,
            content = content
        )
    }
}

/**
 * CompositionLocal for providing access to the [Button] instance.
 * Can be used to provide its other custom implementations to the composition
 */
val LocalOpenMaterialButton = compositionLocalOf { Button() }


