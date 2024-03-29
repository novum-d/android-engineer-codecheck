package jp.co.yumemi.android.codeCheck.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.app.GitRepoSearchViewModel
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import org.koin.androidx.compose.getViewModel

/** Header ---------------------------------------------------------------------------- */

/**
 * 検索画面のHeader
 *
 * @param viewModel
 */
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchHeader(
    viewModel: GitRepoSearchViewModel = getViewModel()
) {
    val expanded = viewModel.expanded.observeAsState().value ?: true
    val color = MaterialTheme.colors.secondary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .drawBehind {
                drawLine(
                    color = color,
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
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.onSurface)
        )
        Spacer(Modifier.padding(4.dp))
        SearchTab(
            modifier = Modifier.weight(1f),
            onTabClick = viewModel::switchExpand,
            expanded = expanded,
        )
    }
}

/**
 * アプリアイコン
 *
 * @param expanded アニメーションの状態
 * @param color アイコンの色
 * @param modifier
 */
@ExperimentalAnimationApi
@Composable
private fun AppIcon(
    expanded: Boolean,
    color: Color,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = expanded) {
        Icon(
            painter = painterResource(R.drawable.ic_github),
            contentDescription = null,
            tint = color,
            modifier = modifier
        )
    }
}

/**
 * 検索タブ
 *
 * @param onTabClick タブクリック時
 * @param expanded アニメーションの状態
 * @param modifier
 */
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchTab(
    onTabClick: () -> Unit,
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
            ) { onTabClick() }
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

/**
 * 巻き上げ検索フィールド
 *
 * @param viewModel
 */
@ExperimentalComposeUiApi
@Composable
fun HoistedSearchTextField(
    viewModel: GitRepoSearchViewModel = getViewModel(),
) {
    var inputText by rememberSaveable { mutableStateOf("") }
    val onValueChange = { text: String -> inputText = text }
    val onSearch = {
        if (inputText.trim().isNotEmpty()) {
            viewModel.onSearch(inputText)
        }
    }

    SearchTextField(
        name = inputText,
        onKeywordChange = onValueChange,
        onSearch = onSearch,
        dismiss = viewModel::switchExpand
    )
}

/**
 * 検索フィールド
 *
 * @param name リポジトリ名
 * @param onKeywordChange 検索キーワード変更時
 * @param onSearch 検索
 * @param dismiss 入力をキャンセル
 * @param modifier
 */
@ExperimentalComposeUiApi
@Composable
fun SearchTextField(
    name: String,
    onKeywordChange: (String) -> Unit,
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
                value = name,
                onValueChange = onKeywordChange,
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
                modifier = modifier.fillMaxWidth().focusRequester(focusRequester)
            )

            if (name.isEmpty()) {
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

/** Body ---------------------------------------------------------------------------- */

/**
 * 検索画面のBody
 *
 * @param repositories Gitリポジトリリスト
 * @param navigateToDetail 詳細画面へ遷移
 * @param modifier
 */
@Composable
fun SearchBody(
    repositories: List<GitRepo>,
    navigateToDetail: (GitRepo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(repositories) {
            SearchCard(
                name = it.name,
                onCardClick = { navigateToDetail(it) }
            )
        }
    }
}

/**
 * Gitリポジトリのカード
 *
 * @param name リポジトリ名
 * @param onCardClick カードクリック時
 */
@Composable
fun SearchCard(
    name: String,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onCardClick)
            .fillMaxWidth(1f)
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            ),
        elevation = 4.dp
    ) {
        SearchCardContent(name)
    }
}

/**
 * Gitリポジトリのカードコンテンツ
 *
 * @param name リポジトリ名
 */
@Composable
fun SearchCardContent(name: String) {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
