/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var binding: FragmentTwoBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentTwoBinding.bind(view)

        var item = args.item

        _binding.also {
            it.ownerIconView.load(item.ownerIconUrl);
            it.nameView.text = item.name;
            it.languageView.text = item.language;
            it.starsView.text = "${item.stargazersCount} stars";
            it.watchersView.text = "${item.watchersCount} watchers";
            it.forksView.text = "${item.forksCount} forks";
            it.openIssuesView.text = "${item.openIssuesCount} open issues";
        }
    }
}
