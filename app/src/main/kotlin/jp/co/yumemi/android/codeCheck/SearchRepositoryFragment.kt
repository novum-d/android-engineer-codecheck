/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchRepositoryBinding

class SearchRepositoryFragment : Fragment(R.layout.fragment_search_repository) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchRepositoryBinding.bind(view)

        val viewModel = OneViewModel(context!!)

        val layoutManager = LinearLayoutManager(context!!)
        val dividerItemDecoration = DividerItemDecoration(context!!, layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnRepositoryClickListener {
            override fun onRepositoryClick(repository: Repository) {
                navigateToRepositoryFragment(repository)
            }
        })

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                editText.text.toString().let {
                    viewModel.searchResults(it).apply {
                        adapter.submitList(this)
                    }
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    fun navigateToRepositoryFragment(repository: Repository) {
        val action =
            SearchRepositoryFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(
                repository
            )
        findNavController().navigate(action)
    }
}

val diffUtil = object : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(oldRepository: Repository, newRepository: Repository): Boolean {
        return oldRepository.name == newRepository.name
    }

    override fun areContentsTheSame(oldRepository: Repository, newRepository: Repository): Boolean {
        return oldRepository == newRepository
    }
}

class CustomAdapter(
    private val onRepositoryClickListener: OnRepositoryClickListener
) : ListAdapter<Repository, CustomAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnRepositoryClickListener {
        fun onRepositoryClick(repository: Repository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            repository.name
        holder.itemView.setOnClickListener {
            onRepositoryClickListener.onRepositoryClick(repository)
        }
    }
}
