/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.app.SearchGitRepoViewModel
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchGitRepoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchGitRepoFragment : Fragment(R.layout.fragment_search_git_repo) {

    private var _binding: FragmentSearchGitRepoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchGitRepoViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchGitRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = SearchAdapter(this@SearchGitRepoFragment)

        // call back flow
        viewModel.onSearch(binding.searchInputText)

        // Git Repository List
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        // Git Repository List に変更が加えられた時に更新
        viewModel.repositories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}

