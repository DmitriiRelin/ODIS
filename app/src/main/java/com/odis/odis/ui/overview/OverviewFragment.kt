package com.odis.odis.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.odis.odis.SettableToolbarTitle
import com.odis.odis.databinding.FragmentOverviewBinding
import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.ui.overview.overviewRecycler.OverviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@Keep
@AndroidEntryPoint
class OverviewFragment : Fragment() {

    companion object {
        private const val BASE_SPAN_COUNT = 3
        private const val TITLE_OVERVIEW = "Overview"
    }

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OverviewViewModel by viewModels()

    private val adapterListener = object : OverviewAdapter.OnItemClickListenerOverview {
        override fun onItemClick(picture: PictureOfDay) {
            findNavController().navigate(
                OverviewFragmentDirections.actionNavigationOverviewToNavigationHome(
                    picture
                )
            )
        }
    }

    private var adapterOver = OverviewAdapter(adapterListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        (activity as? SettableToolbarTitle)?.setToolbarTitle(TITLE_OVERVIEW)

        lifecycleScope.launchWhenCreated {
            viewModel.getList().collectLatest { pagingData ->
                adapterOver.submitData(pagingData)
            }
        }

    }

    private fun initRecyclerView() {
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.layoutManager = GridLayoutManager(context, BASE_SPAN_COUNT)
        binding.recyclerView.adapter = adapterOver
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}