/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.codeCheck.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.codeCheck.databinding.FragmentRepositoryDetailBinding

class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var _binding: FragmentRepositoryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        _binding = FragmentRepositoryDetailBinding.bind(view)

        val repository = args.repository

        binding.also {
            it.ownerIconView.load(repository.ownerIconUrl)
            it.nameView.text = repository.name
            it.languageView.text = repository.language
            it.starsView.text = "${repository.stargazersCount} stars"
            it.watchersView.text = "${repository.watchersCount} watchers"
            it.forksView.text = "${repository.forksCount} forks"
            it.openIssuesView.text = "${repository.openIssuesCount} open issues"
        }
    }
}
