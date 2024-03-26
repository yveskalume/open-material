# Open Material (🚧 Experimental 🚧)

While Material Design in Compose offers a rich set of pre-built components, there are times when
you might want to create custom elements that fit your specific design needs. However, reusing
existing Material Design components can be challenging.

- They often rely on private internal APIs.
- They are quite difficult to reuse without copying out the entire system.

This project aims to bridge this gap by exploring ways to effectively adapt existing Material Design
 components for your specific UI needs, without resorting to starting from scratch.

 > This project is not intended for immediate use. It's currently an exploration to see if creating
 a reusable layer for customization of all Material Design components is feasible.

 ## Idea

 Here's an example of customizing the Button composable:

 Here is the Compose Material Button

 ```Kotlin
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
     interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
     content: @Composable RowScope.() -> Unit
 ) {
     val containerColor = colors.containerColor(enabled)
     val contentColor = colors.contentColor(enabled)
     val shadowElevation = elevation?.shadowElevation(enabled, interactionSource)?.value ?: 0.dp
     val tonalElevation = elevation?.tonalElevation(enabled) ?: 0.dp
     Surface(
         onClick = onClick,
         modifier = modifier.semantics { role = Role.Button },
         enabled = enabled,
         shape = shape,
         color = containerColor,
         contentColor = contentColor,
         tonalElevation = tonalElevation,
         shadowElevation = shadowElevation,
         border = border,
         interactionSource = interactionSource
     ) {
         ProvideContentColorTextStyle(
             contentColor = contentColor,
             textStyle = MaterialTheme.typography.labelLarge) {
             Row(
                 Modifier
                     .defaultMinSize(
                         minWidth = ButtonDefaults.MinWidth,
                         minHeight = ButtonDefaults.MinHeight
                     )
                     .padding(contentPadding),
                 horizontalArrangement = Arrangement.Center,
                 verticalAlignment = Alignment.CenterVertically,
                 content = content
             )
         }
     }
 }
 ```

 We can create an open class Button with customizable properties and methods:

 ```Kotlin
 open class Button {

     protected open val minWidth = ButtonDefaults.MinWidth
     protected open val minHeight = ButtonDefaults.MinHeight

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
 ```

Here's the key: by keeping the class open, you gain the flexibility to customize specific aspects of
 the component without getting bogged down in copying internal implementation details.
The idea is to be able to override the Button or some of it properties (e.g : minHeight) or even a
part of it (e.g : ProvideContentColorTextStyle).

We also need to be able to provide a child of `Button` with customized behaviour (overridden methods or properties)

```Kotlin
class CustomButton : Button() {

    @Composable
    override fun provideContentColoTextStyle(
        contentColor: Color,
        textStyle: TextStyle,
        content: @Composable () -> Unit
    ) {
        ProvideContentColorTextStyle(
            contentColor = contentColor,
            textStyle = MyCustomTextStyle,
            content = content
        )
    }
}
```

For that we use CompositionLocal

```Kotlin
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
```

this allows us to inject the desired customization by passing the child component.

```Kotlin
@Composable
fun CustomButton(
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
    CompositionLocalProvider(LocalOpenMaterialButton provides CustomButton()) {
        Button(
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
}
```

