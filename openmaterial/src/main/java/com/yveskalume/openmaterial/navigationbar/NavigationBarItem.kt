package com.yveskalume.openmaterial.navigationbar

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import androidx.compose.ui.util.fastFirstOrNull
import com.yveskalume.openmaterial.MappedInteractionSource
import com.yveskalume.openmaterial.ProvideContentColorTextStyle
import com.yveskalume.openmaterial.fromToken
import com.yveskalume.openmaterial.tokens.NavigationBarTokens
import com.yveskalume.openmaterial.value
import kotlin.math.roundToInt

/**
 * Material Design navigation bar item.
 *
 * Navigation bars offer a persistent and convenient way to switch between primary destinations in
 * an app.
 *
 * The recommended configuration for a [NavigationBarItem] depends on how many items there are
 * inside a [NavigationBar]:
 *
 * - Three destinations: Display icons and text labels for all destinations.
 * - Four destinations: Active destinations display an icon and text label. Inactive destinations
 * display icons, and text labels are recommended.
 * - Five destinations: Active destinations display an icon and text label. Inactive destinations
 * use icons, and use text labels if space permits.
 *
 * A [NavigationBarItem] always shows text labels (if it exists) when selected. Showing text
 * labels if not selected is controlled by [alwaysShowLabel].
 *
 * @param selected whether this item is selected
 * @param onClick called when this item is clicked
 * @param icon icon for this item, typically an [Icon]
 * @param modifier the [Modifier] to be applied to this item
 * @param enabled controls the enabled state of this item. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label optional text label for this item
 * @param alwaysShowLabel whether to always show the label for this item. If `false`, the label will
 * only be shown when this item is selected.
 * @param colors [NavigationBarItemColors] that will be used to resolve the colors used for this
 * item in different states. See [NavigationBarItemDefaults.colors].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this item. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this item in different states.
 *
 * Cfr [androidx.compose.material3.NavigationBarItem]
 */
@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    with(LocalOpenMaterialNavigationBarItem.current) {
        Composable(
            selected,
            onClick,
            icon,
            modifier,
            enabled,
            label,
            alwaysShowLabel,
            colors,
            interactionSource
        )
    }
}

/**
 * [NavigationBarItem] represents a single item within a bottom navigation bar.
 *
 * It provides the logic, layout, and default values for navigation bar items.
 * It can be customized through its properties and methods.
 *
 * This class is designed to be extended for custom implementations.
 */
open class NavigationBarItem {

    /**
     * The default height of the navigation bar in Dp.
     * Derived from [NavigationBarTokens.ContainerHeight].
     *
     * Make sure it has the same value as [NavigationBar.navigationBarHeight]
     */
    protected open val navigationBarHeight: Dp = NavigationBarTokens.ContainerHeight

    /**
     * The default width of the active indicator in Dp.
     *
     * Derived from [NavigationBarTokens.ActiveIndicatorWidth]
     */
    protected open val activeIndicatorWidth: Dp = NavigationBarTokens.ActiveIndicatorWidth

    /**
     * The default height of the active indicator in Dp.
     *
     * Derived from [NavigationBarTokens.ActiveIndicatorHeight].
     */
    protected open val activeIndicatorHeight: Dp = NavigationBarTokens.ActiveIndicatorHeight

    /**
     * The default horizontal padding for the item in Dp.
     *
     * Make sure it is has the same value as [NavigationBar.navigationBarItemHorizontalPadding]
     */
    protected open val navigationBarItemHorizontalPadding: Dp = 8.dp

    /**
     * The default size of the icon in Dp.
     *
     * Derived from [NavigationBarTokens.IconSize].
     */
    protected open val iconSize: Dp = NavigationBarTokens.IconSize

    /**
     * The calculated horizontal padding for the indicator, ensuring it's centered around the icon.
     */
    protected open val indicatorHorizontalPadding: Dp
        get() = (this.activeIndicatorWidth - this.iconSize) / 2

    /**
     * The calculated vertical padding for the indicator, ensuring it's vertically aligned with the icon.
     */
    protected open val indicatorVerticalPadding: Dp
        get() = (this.activeIndicatorHeight - iconSize) / 2

    /**
     * The default animation duration for item selection changes in milliseconds.
     */
    protected open val itemAnimationDurationMillis: Int = 100

    /**
     * The default vertical offset of the indicator from the bottom of the item in Dp.
     */
    protected open val indicatorVerticalOffset: Dp = 12.dp

    /**
     * The default padding between the indicator and the label in Dp.
     */
    protected open val navigationBarIndicatorToLabelPadding: Dp = 4.dp

    /**
     * Layout ID tag for the indicator ripple element.
     */
    protected open val indicatorRippleLayoutIdTag: String = "indicatorRipple"

    /**
     * Layout ID tag for the indicator element.
     */
    protected open val indicatorLayoutIdTag: String = "indicator"

    /**
     * Layout ID tag for the icon element.
     */
    protected open val iconLayoutIdTag: String = "icon"

    /**
     * Layout ID tag for the label element.
     */
    protected open val labelLayoutIdTag: String = "label"


    /**
     * This is the NavigationBarItem Composable
     * Has the same signature as the Material 3 [RowScope.NavigationBarItem] composable
     *
     * @param selected whether this item is selected
     * @param onClick called when this item is clicked
     * @param icon icon for this item, typically an [Icon]
     * @param modifier the [Modifier] to be applied to this item
     * @param enabled controls the enabled state of this item. When `false`, this component will not
     * respond to user input, and it will appear visually disabled and disabled to accessibility
     * services.
     * @param label optional text label for this item
     * @param alwaysShowLabel whether to always show the label for this item. If `false`, the label will
     * only be shown when this item is selected.
     * @param colors [NavigationBarItemColors] that will be used to resolve the colors used for this
     * item in different states. See [NavigationBarItemDefaults.colors].
     * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
     * for this item. You can create and pass in your own `remember`ed instance to observe
     * [Interaction]s and customize the appearance / behavior of this item in different states.
     *
     * Cfr [androidx.compose.material3.NavigationBarItem]
     */

    @Composable
    open fun RowScope.Composable(
        selected: Boolean,
        onClick: () -> Unit,
        icon: @Composable () -> Unit,
        modifier: Modifier,
        enabled: Boolean,
        label: @Composable (() -> Unit)?,
        alwaysShowLabel: Boolean,
        colors: NavigationBarItemColors,
        interactionSource: MutableInteractionSource
    ) {

        var itemWidth by remember { mutableIntStateOf(0) }

        Box(
            modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Tab,
                    interactionSource = interactionSource,
                    indication = null,
                )
                .defaultMinSize(minHeight = navigationBarHeight)
                .weight(1f)
                .onSizeChanged {
                    itemWidth = it.width
                },
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true,
        ) {
            val animationProgress: State<Float> = animateFloatAsState(
                targetValue = if (selected) 1f else 0f,
                animationSpec = tween(itemAnimationDurationMillis)
            )

            // The entire item is selectable, but only the indicator pill shows the ripple. To achieve
            // this, we re-map the coordinates of the item's InteractionSource into the coordinates of
            // the indicator.
            val deltaOffset: Offset
            with(LocalDensity.current) {
                val indicatorWidth = activeIndicatorWidth.roundToPx()
                deltaOffset = Offset(
                    (itemWidth - indicatorWidth).toFloat() / 2, indicatorVerticalOffset.toPx()
                )
            }
            val offsetInteractionSource = remember(interactionSource, deltaOffset) {
                MappedInteractionSource(interactionSource, deltaOffset)
            }



            NavigationBarItemLayout(
                indicatorRipple = { indicatorRipple(offsetInteractionSource) },
                indicator = { indicator(animationProgress.value, colors) },
                icon = {
                    styledIcon(
                        colors = colors,
                        selected = selected,
                        enabled = enabled,
                        label = label,
                        alwaysShowLabel = alwaysShowLabel,
                        icon = icon
                    )
                },
                label = if (label != null) {
                    {
                        styledLabel(
                            colors = colors,
                            selected = selected,
                            enabled = enabled,
                            label = label
                        )
                    }
                } else null,
                alwaysShowLabel = alwaysShowLabel,
                animationProgress = { animationProgress.value },
            )
        }

    }

    /**
     * Responsible for styling the Navigation icon based on the provided parameters.
     *
     * @param colors A NavigationBarItemColors instance that provides theming colors for this item.
     * @param selected Whether this item is currently selected.
     * @param enabled Whether this item is interactable.
     * @param label An optional composable function that defines the text label for this item.
     * @param alwaysShowLabel Whether to always show the label regardless of selection state.
     * @param icon A composable function that defines the icon for this item.
     */
    @SuppressLint("ComposableNaming")
    @Composable
    protected open fun styledIcon(
        colors: NavigationBarItemColors,
        selected: Boolean,
        enabled: Boolean,
        label: @Composable (() -> Unit)?,
        alwaysShowLabel: Boolean,
        icon: @Composable () -> Unit
    ) {
        val iconColor by colors.iconColor(selected = selected, enabled = enabled)
        // If there's a label, don't have a11y services repeat the icon description.
        val clearSemantics = label != null && (alwaysShowLabel || selected)
        Box(modifier = if (clearSemantics) Modifier.clearAndSetSemantics {} else Modifier) {
            CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
        }
    }


    /**
     * Responsible for styling the label based on the provided parameters.
     *
     * @param colors A NavigationBarItemColors instance that provides theming colors for this item.
     * @param selected Whether this item is currently selected.
     * @param enabled Whether this item is interactable.
     * @param label A composable function that defines the text label for this item.
     */
    @Composable
    protected open fun styledLabel(
        colors: NavigationBarItemColors,
        selected: Boolean,
        enabled: Boolean,
        label: @Composable (() -> Unit)
    ) {
        val style = MaterialTheme.typography.fromToken(NavigationBarTokens.LabelTextFont)
        val textColor by colors.textColor(selected = selected, enabled = enabled)
        ProvideContentColorTextStyle(
            contentColor = textColor,
            textStyle = style,
            content = label
        )
    }

    /**
     * Creates the visual indicator for the selected item in the navigation bar.
     *
     * @param animationProgress A Float value between 0 and 1 representing the animation progress of the selection change.
     * @param colors A NavigationBarItemColors instance that provides theming colors for this item.
     *
     * Don't forget to provide the layoutId which is [indicatorLayoutIdTag]
     */
    @SuppressLint("ComposableNaming")
    @Composable
    protected open fun indicator(
        animationProgress: Float,
        colors: NavigationBarItemColors
    ) {
        Box(
            Modifier
                .layoutId(indicatorLayoutIdTag)
                .graphicsLayer { alpha = animationProgress }
                .background(
                    color = colors.selectedIndicatorColor,
                    shape = NavigationBarTokens.ActiveIndicatorShape.value,
                )
        )
    }

    /**
     * Creates the ripple effect for the indicator when the navigation bar item is interacted with.
     *
     * @param offsetInteractionSource A MappedInteractionSource object used to adjust the ripple location based on other elements.
     *
     * Don't forget to provide the layoutId which is [indicatorRippleLayoutIdTag]
     */
    @SuppressLint("ComposableNaming")
    @Composable
    protected open fun indicatorRipple(offsetInteractionSource: MappedInteractionSource) {
        Box(
            Modifier
                .layoutId(indicatorRippleLayoutIdTag)
                .clip(NavigationBarTokens.ActiveIndicatorShape.value)
                .indication(
                    offsetInteractionSource,
                    rememberRipple()
                )
        )
    }


    /**
     * Base layout for a [NavigationBarItem].
     *
     * @param indicatorRipple indicator ripple for this item when it is selected
     * @param indicator indicator for this item when it is selected
     * @param icon icon for this item
     * @param label text label for this item
     * @param alwaysShowLabel whether to always show the label for this item. If false, the label will
     * only be shown when this item is selected.
     * @param animationProgress progress of the animation, where 0 represents the unselected state of
     * this item and 1 represents the selected state. This value controls other values such as indicator
     * size, icon and label positions, etc.
     */
    @Composable
    private fun NavigationBarItemLayout(
        indicatorRipple: @Composable () -> Unit,
        indicator: @Composable () -> Unit,
        icon: @Composable () -> Unit,
        label: @Composable (() -> Unit)?,
        alwaysShowLabel: Boolean,
        animationProgress: () -> Float,
    ) {
        Layout({
            indicatorRipple()
            indicator()

            Box(Modifier.layoutId(iconLayoutIdTag)) { icon() }

            if (label != null) {
                Box(
                    Modifier
                        .layoutId(labelLayoutIdTag)
                        .graphicsLayer { alpha = if (alwaysShowLabel) 1f else animationProgress() }
                        .padding(horizontal = navigationBarItemHorizontalPadding / 2)
                ) { label() }
            }
        }) { measurables, constraints ->
            @Suppress("NAME_SHADOWING")
            val animationProgress = animationProgress()
            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
            val iconPlaceable =
                measurables.fastFirst { it.layoutId == iconLayoutIdTag }.measure(looseConstraints)

            val totalIndicatorWidth =
                iconPlaceable.width + (indicatorHorizontalPadding * 2).roundToPx()
            val animatedIndicatorWidth = (totalIndicatorWidth * animationProgress).roundToInt()
            val indicatorHeight = iconPlaceable.height + (indicatorVerticalPadding * 2).roundToPx()
            val indicatorRipplePlaceable =
                measurables
                    .fastFirst { it.layoutId == indicatorRippleLayoutIdTag }
                    .measure(
                        Constraints.fixed(
                            width = totalIndicatorWidth,
                            height = indicatorHeight
                        )
                    )
            val indicatorPlaceable =
                measurables
                    .fastFirstOrNull { it.layoutId == indicatorLayoutIdTag }
                    ?.measure(
                        Constraints.fixed(
                            width = animatedIndicatorWidth,
                            height = indicatorHeight
                        )
                    )

            val labelPlaceable =
                label?.let {
                    measurables
                        .fastFirst { it.layoutId == labelLayoutIdTag }
                        .measure(looseConstraints)
                }

            if (label == null) {
                placeIcon(iconPlaceable, indicatorRipplePlaceable, indicatorPlaceable, constraints)
            } else {
                placeLabelAndIcon(
                    labelPlaceable!!,
                    iconPlaceable,
                    indicatorRipplePlaceable,
                    indicatorPlaceable,
                    constraints,
                    alwaysShowLabel,
                    animationProgress
                )
            }
        }
    }

    /**
     * Places the provided [Placeable]s in the center of the provided [constraints].
     *
     * Cfr [androidx.compose.material3.placeIcon]
     */
    private fun MeasureScope.placeIcon(
        iconPlaceable: Placeable,
        indicatorRipplePlaceable: Placeable,
        indicatorPlaceable: Placeable?,
        constraints: Constraints
    ): MeasureResult {
        val width = constraints.maxWidth
        val height = constraints.constrainHeight(navigationBarHeight.roundToPx())

        val iconX = (width - iconPlaceable.width) / 2
        val iconY = (height - iconPlaceable.height) / 2

        val rippleX = (width - indicatorRipplePlaceable.width) / 2
        val rippleY = (height - indicatorRipplePlaceable.height) / 2

        return layout(width, height) {
            indicatorPlaceable?.let {
                val indicatorX = (width - it.width) / 2
                val indicatorY = (height - it.height) / 2
                it.placeRelative(indicatorX, indicatorY)
            }
            iconPlaceable.placeRelative(iconX, iconY)
            indicatorRipplePlaceable.placeRelative(rippleX, rippleY)
        }
    }

    /**
     * Places the provided [Placeable]s in the correct position, depending on [alwaysShowLabel] and
     * [animationProgress].
     *
     * When [alwaysShowLabel] is true, the positions do not move. The [iconPlaceable] and
     * [labelPlaceable] will be placed together in the center with padding between them, according to
     * the spec.
     *
     * When [animationProgress] is 1 (representing the selected state), the positions will be the same
     * as above.
     *
     * Otherwise, when [animationProgress] is 0, [iconPlaceable] will be placed in the center, like in
     * [placeIcon], and [labelPlaceable] will not be shown.
     *
     * When [animationProgress] is animating between these values, [iconPlaceable] and [labelPlaceable]
     * will be placed at a corresponding interpolated position.
     *
     * [indicatorRipplePlaceable] and [indicatorPlaceable] will always be placed in such a way that to
     * share the same center as [iconPlaceable].
     *
     * @param labelPlaceable text label placeable inside this item
     * @param iconPlaceable icon placeable inside this item
     * @param indicatorRipplePlaceable indicator ripple placeable inside this item
     * @param indicatorPlaceable indicator placeable inside this item, if it exists
     * @param constraints constraints of the item
     * @param alwaysShowLabel whether to always show the label for this item. If true, icon and label
     * positions will not change. If false, positions transition between 'centered icon with no label'
     * and 'top aligned icon with label'.
     * @param animationProgress progress of the animation, where 0 represents the unselected state of
     * this item and 1 represents the selected state. Values between 0 and 1 interpolate positions of
     * the icon and label.
     *
     * Cfr [androidx.compose.material3.placeLabelAndIcon]
     */
    private fun MeasureScope.placeLabelAndIcon(
        labelPlaceable: Placeable,
        iconPlaceable: Placeable,
        indicatorRipplePlaceable: Placeable,
        indicatorPlaceable: Placeable?,
        constraints: Constraints,
        alwaysShowLabel: Boolean,
        animationProgress: Float,
    ): MeasureResult {
        val contentHeight =
            iconPlaceable.height + indicatorVerticalPadding.toPx() +
                    navigationBarIndicatorToLabelPadding.toPx() + labelPlaceable.height
        val contentVerticalPadding = ((constraints.minHeight - contentHeight) / 2)
            .coerceAtLeast(indicatorVerticalPadding.toPx())
        val height = contentHeight + contentVerticalPadding * 2

        // Icon (when selected) should be `contentVerticalPadding` from top
        val selectedIconY = contentVerticalPadding
        val unselectedIconY =
            if (alwaysShowLabel) selectedIconY else (height - iconPlaceable.height) / 2

        // How far the icon needs to move between unselected and selected states.
        val iconDistance = unselectedIconY - selectedIconY

        // The interpolated fraction of iconDistance that all placeables need to move based on
        // animationProgress.
        val offset = iconDistance * (1 - animationProgress)

        // Label should be fixed padding below icon
        val labelY =
            selectedIconY + iconPlaceable.height + indicatorVerticalPadding.toPx() +
                    navigationBarIndicatorToLabelPadding.toPx()

        val containerWidth = constraints.maxWidth

        val labelX = (containerWidth - labelPlaceable.width) / 2
        val iconX = (containerWidth - iconPlaceable.width) / 2

        val rippleX = (containerWidth - indicatorRipplePlaceable.width) / 2
        val rippleY = selectedIconY - indicatorVerticalPadding.toPx()

        return layout(containerWidth, height.roundToInt()) {
            indicatorPlaceable?.let {
                val indicatorX = (containerWidth - it.width) / 2
                val indicatorY =
                    selectedIconY - indicatorVerticalPadding.roundToPx()
                it.placeRelative(indicatorX, (indicatorY + offset).roundToInt())
            }
            if (alwaysShowLabel || animationProgress != 0f) {
                labelPlaceable.placeRelative(labelX, (labelY + offset).roundToInt())
            }
            iconPlaceable.placeRelative(iconX, (selectedIconY + offset).roundToInt())
            indicatorRipplePlaceable.placeRelative(rippleX, (rippleY + offset).roundToInt())
        }
    }


}

/**
 * Represents the icon color for this item, depending on whether it is [selected].
 *
 * @param selected whether the item is selected
 * @param enabled whether the item is enabled
 *
 * Cfr [androidx.compose.material3.NavigationBarItemColors.iconColor]
 */
@Composable
internal fun NavigationBarItemColors.iconColor(
    selected: Boolean,
    enabled: Boolean,
    itemAnimationDurationMillis: Int = 100
): State<Color> {
    val targetValue = when {
        !enabled -> disabledIconColor
        selected -> selectedIconColor
        else -> unselectedIconColor
    }
    return animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(itemAnimationDurationMillis)
    )
}

/**
 * Represents the text color for this item, depending on whether it is [selected].
 *
 * @param selected whether the item is selected
 * @param enabled whether the item is enabled
 *
 * Cfr [androidx.compose.material3.NavigationBarItemColors.textColor]
 */
@Composable
internal fun NavigationBarItemColors.textColor(
    selected: Boolean,
    enabled: Boolean,
    itemAnimationDurationMillis: Int = 100
): State<Color> {
    val targetValue = when {
        !enabled -> disabledTextColor
        selected -> selectedTextColor
        else -> unselectedTextColor
    }
    return animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(itemAnimationDurationMillis)
    )
}

/**
 * Create a compositionLocal of [NavigationBarItem] and can be used to provide its other custom implementations to the composition
 */
val RowScope.LocalOpenMaterialNavigationBarItem: ProvidableCompositionLocal<NavigationBarItem>
    get() = compositionLocalOf { NavigationBarItem() }
