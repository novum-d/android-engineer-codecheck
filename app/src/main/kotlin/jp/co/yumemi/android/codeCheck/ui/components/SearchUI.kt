package jp.co.yumemi.android.codeCheck.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.app.GitRepoSearchViewModel
import jp.co.yumemi.android.codeCheck.ui.theme.md_theme_dark_surface
import org.koin.androidx.compose.getViewModel

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchHeader(
    viewModel: GitRepoSearchViewModel = getViewModel()
) {

    var expanded = viewModel.expanded.observeAsState().value ?: true

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 8f
                )
            }
            .padding(
                vertical = 8.dp,
                horizontal = 8.dp,
            )
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        AppIcon(
            expanded = expanded,
            tint = MaterialTheme.colors.surface,
            modifier = Modifier.clip(CircleShape).background(MaterialTheme.colors.onSurface)
        )
        Spacer(Modifier.padding(4.dp))
        SearchTab(
            modifier = Modifier.weight(1f),
            onClick = viewModel::switchExpand,
            expanded = expanded,
        )
    }

    BackHandler(
        enabled = !expanded,
        onBack = { expanded = false }
    )
}

@ExperimentalAnimationApi
@Composable
private fun AppIcon(
    expanded: Boolean,
    tint: Color,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = expanded) {
        Icon(
            painter = painterResource(R.drawable.ic_github),
            contentDescription = null,
            tint = tint,
            modifier = modifier
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchTab(
    onClick: () -> Unit,
    expanded: Boolean,
    modifier: Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
    ) {
        Row(
            modifier = modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
        ) {
            AnimatedVisibility(visible = expanded) {
                Text(stringResource(R.string.searchInputText_hint), modifier.weight(1f))
            }
            AnimatedVisibility(visible = !expanded) {
                HoistedSearchTextField()
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun HoistedSearchTextField(
    viewModel: GitRepoSearchViewModel = getViewModel()
) {
    var inputText by rememberSaveable { mutableStateOf("") }
    val onValueChange = { text: String -> inputText = text }
    val onSearch = {
        if (inputText.trim().isNotEmpty()) {
            viewModel.onSearch(inputText)
        }
    }

    SearchTextField(
        inputText = inputText,
        onValueChange = onValueChange,
        onSearch = onSearch,
        dismiss = viewModel::switchExpand
    )
}

@ExperimentalComposeUiApi
@Composable
fun SearchTextField(
    inputText: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_github),
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = modifier
                .background(
                    shape = CircleShape,
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f)
                )
                .padding(4.dp)
        )
        Spacer(modifier.padding(4.dp))
        Box(contentAlignment = Alignment.CenterStart) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
            BasicTextField(
                value = inputText,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Uri
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch()
                        keyboardController?.hide()
                        dismiss()
                    }
                ),
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                modifier = modifier.focusRequester(focusRequester)
            )

            if (inputText.isEmpty()) {
                Text(
                    text = stringResource(R.string.please_enter_reository_name),
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
            }

            SideEffect {
                focusRequester.requestFocus()
            }
        }
    }
}
