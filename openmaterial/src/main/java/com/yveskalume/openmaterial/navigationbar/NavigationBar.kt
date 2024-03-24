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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
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

open class NavigationBar {

    protected open val navigationBarHeight: Dp = NavigationBarTokens.ContainerHeight
    protected open val navigationBarItemHorizontalPadding: Dp = 8.dp

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

val LocalOpenMaterialNavigationBar = compositionLocalOf { NavigationBar() }

