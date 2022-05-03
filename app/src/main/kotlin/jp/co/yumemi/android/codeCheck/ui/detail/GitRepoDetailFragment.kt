/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.app.GitRepoSearchViewModel
import jp.co.yumemi.android.codeCheck.ui.theme.CodeCheckTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitRepoDetailFragment : Fragment(R.layout.fragment_git_repo_detail) {

    private val args: GitRepoDetailFragmentArgs by navArgs()

    private val viewModel: GitRepoSearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CodeCheckTheme {
                    DetailsScreen(args.gitRepo)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("検索した日時", viewModel.lastSearchDate.toString())
    }
}
