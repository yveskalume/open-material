package com.yveskalume.openmaterial.navigationbar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yveskalume.openmaterial.tokens.NavigationBarTokens

/**
 * <a href="https://m3.material.io/components/navigation-bar/overview" class="external" target="_blank">Material Design bottom navigation bar</a>.
 *
 * Navigation bars offer a persistent and convenient way to switch between primary destinations in
 * an app.
 *
 * ![Navigation bar image](https://developer.android.com/images/reference/androidx/compose/material3/navigation-bar.png)
 *
 * [NavigationBar] should contain three to five [NavigationBarItem]s, each representing a singular
 * destination.
 *
 *
 * See [NavigationBarItem] for configuration specific to each item, and not the overall
 * [NavigationBar] component.
 *
 * @param modifier the [Modifier] to be applied to this navigation bar
 * @param containerColor the color used for the background of this navigation bar. Use
 * [Color.Transparent] to have no color.
 * @param contentColor the preferred color for content inside this navigation bar. Defaults to
 * either the matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param tonalElevation when [containerColor] is [ColorScheme.surface], a translucent primary color
 * overlay is applied on top of the container. A higher tonal elevation value will result in a
 * darker color in light theme and lighter color in dark theme. See also: [Surface].
 * @param windowInsets a window insets of the navigation bar.
 * @param content the content of this navigation bar, typically 3-5 [NavigationBarItem]s
 *
 * Cfr [androidx.compose.material3.NavigationBar]
 */
@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    LocalOpenMaterialNavigationBar.current.Composable(
        modifier,
        containerColor,
        contentColor,
        tonalElevation,
        windowInsets,
        content
    )
}

/**
 * Provide structure and composable functions for building the navigation bar's UI.
 *
 * This class is designed to be extended for custom implementations.
 */
open class NavigationBar {

    /**
     * The default height of the navigation bar in Dp.
     * Derived from [NavigationBarTokens.ContainerHeight].
     *
     * Make sure it has the same value as [NavigationBarItem.navigationBarHeight]
     */
    protected open val navigationBarHeight: Dp = NavigationBarTokens.ContainerHeight

    /**
     * The horizontal padding for items within the navigation bar.
     *
     * Make sure it is has the same value as [NavigationBarItem.navigationBarItemHorizontalPadding]
     */
    protected open val navigationBarItemHorizontalPadding: Dp = 8.dp

    /**
     * Composable function for rendering the navigation bar.
     * Has the same signature as the Material 3 NavigationBar composable
     *
     * @param modifier The modifier for this composable.
     * @param containerColor The background color of the navigation bar.
     * @param contentColor The color of the content within the navigation bar.
     * @param tonalElevation The tonal elevation of the navigation bar.
     * @param windowInsets The window insets to apply to the navigation bar.
     * @param content The content to be displayed within the navigation bar.
     *
     * Cfr [androidx.compose.material3.NavigationBar]
     */
    @Composable
    fun Composable(
        modifier: Modifier = Modifier,
        containerColor: Color = NavigationBarDefaults.containerColor,
        contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
        tonalElevation: Dp = NavigationBarDefaults.Elevation,
        windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
        content: @Composable RowScope.() -> Unit
    ) {
        Surface(
            color = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            modifier = modifier
        ) {
            row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(windowInsets)
                    .defaultMinSize(minHeight = navigationBarHeight)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(navigationBarItemHorizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }

    /**
     * The Row layout within the navigation bar.
     *
     * @param content The content to be displayed within the row layout.
     * @param horizontalArrangement The horizontal arrangement of items within the row.
     * @param verticalAlignment The vertical alignment of items within the row.
     * @param modifier The modifier for this composable.
     */
    @SuppressLint("ComposableNaming")
    @Composable
    protected open fun row(
        content: @Composable RowScope.() -> Unit,
        horizontalArrangement: Arrangement.Horizontal,
        verticalAlignment: Alignment.Vertical,
        modifier: Modifier
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}

/**
 * CompositionLocal for providing access to the [NavigationBar] instance.
 * Can be used to provide its other custom implementations to the composition
 */
val LocalOpenMaterialNavigationBar = compositionLocalOf { NavigationBar() }

