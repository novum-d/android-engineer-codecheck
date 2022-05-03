/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.app.GitRepoSearchViewModel
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.ui.theme.CodeCheckTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class GitRepoSearchFragment : Fragment(R.layout.fragment_search_git_repo) {

    private val viewModel: GitRepoSearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return ComposeView(requireContext()).apply {
            val navigateToDetailScreen = { gitRepo: GitRepo ->
                val action = GitRepoSearchFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(gitRepo)
                findNavController().navigate(action)
            }
            setContent {
                val repositories = viewModel.repositories.observeAsState().value ?: listOf()
                CodeCheckTheme {
                    SearchScreen(
                        repositories = repositories,
                        navigateToDetail = navigateToDetailScreen
                    )
                }
            }
        }
    }
}
