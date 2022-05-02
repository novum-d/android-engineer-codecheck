package jp.co.yumemi.android.codeCheck.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.codeCheck.data.model.GitRepo
import jp.co.yumemi.android.codeCheck.databinding.LayoutItemBinding

class SearchAdapter(
    private val searchGitRepoFragment: SearchGitRepoFragment
) : ListAdapter<GitRepo, SearchAdapter.ViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        holder.also {
            it.bind(repository)
            it.itemView.setOnClickListener(ClickListener(repository, searchGitRepoFragment))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    private class ClickListener(val gitRepo: GitRepo, val searchGitRepoFragment: SearchGitRepoFragment) :
        View.OnClickListener {
        override fun onClick(v: View?) {
            val action = SearchGitRepoFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(gitRepo)
            searchGitRepoFragment.findNavController().navigate(action)
        }
    }

    class ViewHolder private constructor(
        private val binding: LayoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(gitRepo: GitRepo) {
            binding.repositoryNameView.text = gitRepo.name
        }
    }
}

val diffUtil = object : DiffUtil.ItemCallback<GitRepo>() {

    override fun areItemsTheSame(oldGitRepo: GitRepo, newGitRepo: GitRepo): Boolean {
        return oldGitRepo.name == newGitRepo.name
    }

    override fun areContentsTheSame(oldGitRepo: GitRepo, newGitRepo: GitRepo): Boolean {
        return oldGitRepo == newGitRepo
    }
}
