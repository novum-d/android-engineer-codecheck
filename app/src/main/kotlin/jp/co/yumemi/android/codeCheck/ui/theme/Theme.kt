package jp.co.yumemi.android.codeCheck.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightThemeColors = lightColors(

    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryVariant = md_theme_light_PrimaryVariant,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryVariant = md_theme_light_secondaryVariant,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface
)
private val DarkThemeColors = darkColors(

    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryVariant = md_theme_dark_primaryVariant,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryVariant = md_theme_dark_secondaryVariant,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface
)

@Composable
fun CodeCheckTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightThemeColors
    } else {
        DarkThemeColors
    }

    // theme colors
    MaterialTheme(
        colors = colors,
        typography = CodeCheckTypography,
        content = content
    )

    // system ui color
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(colors.primary)
    }
}
