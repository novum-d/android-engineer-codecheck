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
import jp.co.yumemi.android.codeCheck.databinding.FragmentRepositoryDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var _binding: FragmentRepositoryDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchRepositoryViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRepositoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", viewModel.lastSearchDate.toString())

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
