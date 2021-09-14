package com.odis.odis.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.odis.odis.SettableToolbarTitle
import com.odis.odis.Utils.ONE_ITEM_IN_LIST
import com.odis.odis.Utils.TITLE_IS_EMPTY
import com.odis.odis.Utils.TITLE_MANY_FAVORITES
import com.odis.odis.Utils.TITLE_ONE_FAVORITE
import com.odis.odis.databinding.FragmentFavoritesBinding
import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.ui.favorites.favoritesRecycler.FavoritesRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    private val adapterListener = object : FavoritesRecyclerAdapter.OnItemClickListenerFavorites {
        override fun onItemClick(picture: PictureOfDay) {
            findNavController().navigate(
                FavoritesFragmentDirections.actionNavigationFavoritesToNavigationHome(
                    picture
                )
            )
        }
    }

    private val adapterListenerDelete =
        object : FavoritesRecyclerAdapter.OnItemClickListenerDelete {
            override fun onItemClickDelete(picture: PictureOfDay) {
                viewModel.deleteFavorite(picture)
            }

        }

    private var favoritesAdapter = FavoritesRecyclerAdapter(adapterListener, adapterListenerDelete)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        binding.inputEditText.requestFocus()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.getList()

        viewModel.favoritesLiveData.observe(viewLifecycleOwner) {
            it?.let { favoriteList ->
                favoritesAdapter.setList(favoriteList)
                if (favoriteList.isNotEmpty()) {
                    if (it.size == ONE_ITEM_IN_LIST) {
                        (activity as? SettableToolbarTitle)?.setToolbarTitle(TITLE_ONE_FAVORITE)
                    } else {
                        (activity as? SettableToolbarTitle)?.setToolbarTitle(it.size.toString() + TITLE_MANY_FAVORITES)
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyListBackground.visibility = View.GONE
                } else {
                    (activity as? SettableToolbarTitle)?.setToolbarTitle(TITLE_IS_EMPTY)
                    binding.emptyListBackground.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }

        binding.inputEditText.doOnTextChanged { text, start, before, count ->
            viewModel.changeFilterText(text.toString())
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = favoritesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}