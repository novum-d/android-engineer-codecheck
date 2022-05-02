/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.codeCheck.databinding.FragmentGitRepoDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitRepoDetailFragment : Fragment(R.layout.fragment_git_repo_detail) {

    private val args: GitRepoDetailFragmentArgs by navArgs()

    private var _binding: FragmentGitRepoDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchGitRepoViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGitRepoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", viewModel.lastSearchDate.toString())

        val repository = args.gitRepo
        binding.also {
            it.ownerIconView.load(repository.ownerIconUrl.avatarUrl)
            it.nameView.text = repository.name
            it.languageView.text = getString(R.string.written_language, repository.language)
            it.starsView.text = getString(R.string.stars_view, repository.stargazersCount)
            it.watchersView.text = getString(R.string.watchers_view, repository.watchersCount)
            it.forksView.text = getString(R.string.forks_view, repository.forksCount)
            it.openIssuesView.text = getString(R.string.openIssues_view, repository.openIssuesCount)
        }
    }
}
