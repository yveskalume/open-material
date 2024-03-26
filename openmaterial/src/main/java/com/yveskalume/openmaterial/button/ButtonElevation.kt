package com.yveskalume.openmaterial.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import com.yveskalume.openmaterial.animateElevation
import com.yveskalume.openmaterial.tokens.FilledButtonTokens

/**
 * Represents the shadow elevation used in a button, depending on its [enabled] state and
 * [interactionSource].
 *
 * Shadow elevation is used to apply a shadow around the button to give it higher emphasis.
 *
 * See [androidx.compose.material3.ButtonElevation.tonalElevation] which controls the elevation with a color shift to the surface.
 *
 * @param enabled whether the button is enabled
 * @param interactionSource the [InteractionSource] for this button
 *
 * Cfr [androidx.compose.material3.ButtonElevation.shadowElevation]
 */
@Composable
internal fun ButtonElevation.shadowElevation(
    enabled: Boolean,
    interactionSource: InteractionSource
): State<Dp> {
    return animateElevation(enabled = enabled, interactionSource = interactionSource)
}

/**
 * Cfr [androidx.compose.material3.ButtonElevation.animateElevation]
 */
@Composable
private fun ButtonElevation.animateElevation(
    enabled: Boolean,
    interactionSource: InteractionSource
): State<Dp> {
    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> {
                    interactions.add(interaction)
                }

                is HoverInteraction.Exit -> {
                    interactions.remove(interaction.enter)
                }

                is FocusInteraction.Focus -> {
                    interactions.add(interaction)
                }

                is FocusInteraction.Unfocus -> {
                    interactions.remove(interaction.focus)
                }

                is PressInteraction.Press -> {
                    interactions.add(interaction)
                }

                is PressInteraction.Release -> {
                    interactions.remove(interaction.press)
                }

                is PressInteraction.Cancel -> {
                    interactions.remove(interaction.press)
                }
            }
        }
    }

    val interaction = interactions.lastOrNull()

    val target =
        if (!enabled) {
            FilledButtonTokens.DisabledContainerElevation
        } else {
            when (interaction) {
                is PressInteraction.Press -> FilledButtonTokens.PressedContainerElevation
                is HoverInteraction.Enter -> FilledButtonTokens.HoverContainerElevation
                is FocusInteraction.Focus -> FilledButtonTokens.FocusContainerElevation
                else -> FilledButtonTokens.ContainerElevation
            }
        }

    val animatable = remember { Animatable(target, Dp.VectorConverter) }

    LaunchedEffect(target) {
        if (animatable.targetValue != target) {
            if (!enabled) {
                // No transition when moving to a disabled state
                animatable.snapTo(target)
            } else {
                val lastInteraction = when (animatable.targetValue) {
                    FilledButtonTokens.PressedContainerElevation -> PressInteraction.Press(Offset.Zero)
                    FilledButtonTokens.HoverContainerElevation -> HoverInteraction.Enter()
                    FilledButtonTokens.FocusContainerElevation -> FocusInteraction.Focus()
                    else -> null
                }
                animatable.animateElevation(
                    from = lastInteraction,
                    to = interaction,
                    target = target
                )
            }
        }
    }

    return animatable.asState()
}