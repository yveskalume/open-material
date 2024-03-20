package com.yveskalume.openmaterial.button
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import com.yveskalume.openmaterial.ColorScheme
//import com.yveskalume.openmaterial.MaterialTheme
//import com.yveskalume.openmaterial.fromToken
//import com.yveskalume.openmaterial.tokens.ElevatedButtonTokens
//import com.yveskalume.openmaterial.tokens.FilledButtonTokens
//import com.yveskalume.openmaterial.tokens.FilledTonalButtonTokens
//import com.yveskalume.openmaterial.tokens.OutlinedButtonTokens
//import com.yveskalume.openmaterial.tokens.TextButtonTokens
//import com.yveskalume.openmaterial.value
//
//internal object ButtonDefaults {
//
//    private val ButtonHorizontalPadding = 24.dp
//    private val ButtonVerticalPadding = 8.dp
//
//    /**
//     * The default content padding used by [Button], [ElevatedButton], [FilledTonalButton], and
//     * [OutlinedButton] buttons.
//     *
//     * - See [TextButtonContentPadding] or [TextButtonWithIconContentPadding] for content padding
//     *  used by [TextButton].
//     * - See [ButtonWithIconContentPadding] for content padding used by [Button] that contains
//     * [Icon].
//     */
//    val ContentPadding =
//        PaddingValues(
//            start = ButtonHorizontalPadding,
//            top = ButtonVerticalPadding,
//            end = ButtonHorizontalPadding,
//            bottom = ButtonVerticalPadding
//        )
//
//    private val ButtonWithIconHorizontalStartPadding = 16.dp
//
//    /** The default content padding used by [Button] that contains an [Icon]. */
//    val ButtonWithIconContentPadding =
//        PaddingValues(
//            start = ButtonWithIconHorizontalStartPadding,
//            top = ButtonVerticalPadding,
//            end = ButtonHorizontalPadding,
//            bottom = ButtonVerticalPadding
//        )
//
//    private val TextButtonHorizontalPadding = 12.dp
//
//    /** The default content padding used by [TextButton].
//     *
//     * - See [TextButtonWithIconContentPadding] for content padding used by [TextButton] that
//     * contains [Icon].
//     */
//    val TextButtonContentPadding =
//        PaddingValues(
//            start = TextButtonHorizontalPadding,
//            top = ContentPadding.calculateTopPadding(),
//            end = TextButtonHorizontalPadding,
//            bottom = ContentPadding.calculateBottomPadding()
//        )
//
//    private val TextButtonWithIconHorizontalEndPadding = 16.dp
//
//    /** The default content padding used by [TextButton] that contains an [Icon]. */
//    val TextButtonWithIconContentPadding =
//        PaddingValues(
//            start = TextButtonHorizontalPadding,
//            top = ContentPadding.calculateTopPadding(),
//            end = TextButtonWithIconHorizontalEndPadding,
//            bottom = ContentPadding.calculateBottomPadding()
//        )
//
//    /**
//     * The default min width applied for all buttons. Note that you can override it by applying
//     * Modifier.widthIn directly on the button composable.
//     */
//    val MinWidth = 58.dp
//
//    /**
//     * The default min height applied for all buttons. Note that you can override it by applying
//     * Modifier.heightIn directly on the button composable.
//     */
//    val MinHeight = 40.dp
//
//    /** The default size of the icon when used inside any button. */
//    val IconSize = FilledButtonTokens.IconSize
//
//    /**
//     * The default size of the spacing between an icon and a text when they used inside any button.
//     */
//    val IconSpacing = 8.dp
//
//    /** Default shape for a button. */
//    val shape: Shape @Composable get() = FilledButtonTokens.ContainerShape.value
//
//    /** Default shape for an elevated button. */
//    val elevatedShape: Shape @Composable get() = ElevatedButtonTokens.ContainerShape.value
//
//    /** Default shape for a filled tonal button. */
//    val filledTonalShape: Shape @Composable get() = FilledTonalButtonTokens.ContainerShape.value
//
//    /** Default shape for an outlined button. */
//    val outlinedShape: Shape @Composable get() = OutlinedButtonTokens.ContainerShape.value
//
//    /** Default shape for a text button. */
//    val textShape: Shape @Composable get() = TextButtonTokens.ContainerShape.value
//
//    /**
//     * Creates a [ButtonColors] that represents the default container and content colors used in a
//     * [Button].
//     */
//    @Composable
//    fun buttonColors() = MaterialTheme.colorScheme.defaultButtonColors
//
//    /**
//     * Creates a [ButtonColors] that represents the default container and content colors used in a
//     * [Button].
//     *
//     * @param containerColor the container color of this [Button] when enabled.
//     * @param contentColor the content color of this [Button] when enabled.
//     * @param disabledContainerColor the container color of this [Button] when not enabled.
//     * @param disabledContentColor the content color of this [Button] when not enabled.
//     */
//    @Composable
//    fun buttonColors(
//        containerColor: Color = Color.Unspecified,
//        contentColor: Color = Color.Unspecified,
//        disabledContainerColor: Color = Color.Unspecified,
//        disabledContentColor: Color = Color.Unspecified,
//    ): ButtonColors = MaterialTheme.colorScheme.defaultButtonColors.copy(
//        containerColor = containerColor,
//        contentColor = contentColor,
//        disabledContainerColor = disabledContainerColor,
//        disabledContentColor = disabledContentColor
//    )
//
//    internal val ColorScheme.defaultButtonColors: ButtonColors
//        get() {
//            return defaultButtonColorsCached ?: ButtonColors(
//                containerColor = fromToken(FilledButtonTokens.ContainerColor),
//                contentColor = fromToken(FilledButtonTokens.LabelTextColor),
//                disabledContainerColor = fromToken(FilledButtonTokens.DisabledContainerColor)
//                    .copy(alpha = FilledButtonTokens.DisabledContainerOpacity),
//                disabledContentColor = fromToken(FilledButtonTokens.DisabledLabelTextColor)
//                    .copy(alpha = FilledButtonTokens.DisabledLabelTextOpacity)
//            ).also {
//                defaultButtonColorsCached = it
//            }
//        }
//
//
//    /**
//     * Creates a [ButtonColors] that represents the default container and content colors used in an
//     * [OutlinedButton].
//     */
//    @Composable
//    fun outlinedButtonColors() = MaterialTheme.colorScheme.defaultOutlinedButtonColors
//
//    /**
//     * Creates a [ButtonColors] that represents the default container and content colors used in an
//     * [OutlinedButton].
//     *
//     * @param containerColor the container color of this [OutlinedButton] when enabled
//     * @param contentColor the content color of this [OutlinedButton] when enabled
//     * @param disabledContainerColor the container color of this [OutlinedButton] when not enabled
//     * @param disabledContentColor the content color of this [OutlinedButton] when not enabled
//     */
//    @Composable
//    fun outlinedButtonColors(
//        containerColor: Color = Color.Unspecified,
//        contentColor: Color = Color.Unspecified,
//        disabledContainerColor: Color = Color.Unspecified,
//        disabledContentColor: Color = Color.Unspecified,
//    ): ButtonColors = MaterialTheme.colorScheme.defaultOutlinedButtonColors.copy(
//        containerColor = containerColor,
//        contentColor = contentColor,
//        disabledContainerColor = disabledContainerColor,
//        disabledContentColor = disabledContentColor
//    )
//
//    internal val ColorScheme.defaultOutlinedButtonColors: ButtonColors
//        get() {
//            return defaultOutlinedButtonColorsCached ?: ButtonColors(
//                containerColor = Color.Transparent,
//                contentColor = fromToken(OutlinedButtonTokens.LabelTextColor),
//                disabledContainerColor = Color.Transparent,
//                disabledContentColor = fromToken(OutlinedButtonTokens.DisabledLabelTextColor)
//                    .copy(alpha = OutlinedButtonTokens.DisabledLabelTextOpacity)
//            ).also {
//                defaultOutlinedButtonColorsCached = it
//            }
//        }
//
//    /**
//     * Creates a [ButtonColors] that represents the default container and content colors used in a
//     * [TextButton].
//     */
//    @Composable
//    fun textButtonColors() = MaterialTheme.colorScheme.defaultTextButtonColors
//
//    /**
//     * Creates a [ButtonColors] that represents the default container and content colors used in a
//     * [TextButton].
//     *
//     * @param containerColor the container color of this [TextButton] when enabled
//     * @param contentColor the content color of this [TextButton] when enabled
//     * @param disabledContainerColor the container color of this [TextButton] when not enabled
//     * @param disabledContentColor the content color of this [TextButton] when not enabled
//     */
//    @Composable
//    fun textButtonColors(
//        containerColor: Color = Color.Unspecified,
//        contentColor: Color = Color.Unspecified,
//        disabledContainerColor: Color = Color.Unspecified,
//        disabledContentColor: Color = Color.Unspecified,
//    ): ButtonColors = MaterialTheme.colorScheme.defaultTextButtonColors.copy(
//        containerColor = containerColor,
//        contentColor = contentColor,
//        disabledContainerColor = disabledContainerColor,
//        disabledContentColor = disabledContentColor
//    )
//
//    internal val ColorScheme.defaultTextButtonColors: ButtonColors
//        get() {
//            return defaultTextButtonColorsCached ?: ButtonColors(
//                containerColor = Color.Transparent,
//                contentColor = fromToken(TextButtonTokens.LabelTextColor),
//                disabledContainerColor = Color.Transparent,
//                disabledContentColor = fromToken(TextButtonTokens.DisabledLabelTextColor)
//                    .copy(alpha = TextButtonTokens.DisabledLabelTextOpacity)
//            ).also {
//                defaultTextButtonColorsCached = it
//            }
//        }
//
//    /**
//     * Creates a [ButtonElevation] that will animate between the provided values according to the
//     * Material specification for a [Button].
//     *
//     * @param defaultElevation the elevation used when the [Button] is enabled, and has no other
//     * [Interaction]s.
//     * @param pressedElevation the elevation used when this [Button] is enabled and pressed.
//     * @param focusedElevation the elevation used when the [Button] is enabled and focused.
//     * @param hoveredElevation the elevation used when the [Button] is enabled and hovered.
//     * @param disabledElevation the elevation used when the [Button] is not enabled.
//     */
//    @Composable
//    fun buttonElevation(
//        defaultElevation: Dp = FilledButtonTokens.ContainerElevation,
//        pressedElevation: Dp = FilledButtonTokens.PressedContainerElevation,
//        focusedElevation: Dp = FilledButtonTokens.FocusContainerElevation,
//        hoveredElevation: Dp = FilledButtonTokens.HoverContainerElevation,
//        disabledElevation: Dp = FilledButtonTokens.DisabledContainerElevation,
//    ): ButtonElevation = ButtonElevation(
//        defaultElevation = defaultElevation,
//        pressedElevation = pressedElevation,
//        focusedElevation = focusedElevation,
//        hoveredElevation = hoveredElevation,
//        disabledElevation = disabledElevation,
//    )
//
//    /** The default [BorderStroke] used by [OutlinedButton]. */
//    val outlinedButtonBorder: BorderStroke
//        @Composable
//        get() = BorderStroke(
//            width = OutlinedButtonTokens.OutlineWidth,
//            color = OutlinedButtonTokens.OutlineColor.value,
//        )
//}