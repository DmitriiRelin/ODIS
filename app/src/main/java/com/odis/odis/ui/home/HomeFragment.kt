package com.odis.odis.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.odis.odis.*
import com.odis.odis.Utils.ERROR_TITLE
import com.odis.odis.Utils.REQUEST_MEDIA_TYPE_VIDEO
import com.odis.odis.databinding.FragmentHomeBinding
import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.ui.calendar.BottomSheetFragment
import com.odis.odis.ui.home.homeRecycler.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val arguments: HomeFragmentArgs by navArgs()

    private val adapterListener = object : HomeAdapter.OnItemClickListenerSeeAlso {
        override fun clickOnSeeAlso(pictureOfDay: PictureOfDay) {
            viewModel.clickOnSeeAlso(pictureOfDay)
        }
    }
    private var homeAdapter = HomeAdapter(adapterListener)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        setHasOptionsMenu(true)

        val argsPicture = arguments.picture

        if (argsPicture == null) {
            viewModel.getStartPicture()
        } else argsPicture.let { viewModel.getPictureFromOverview(it) }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.pictureLiveData.observe(viewLifecycleOwner) { response ->

            (activity as? ScrollableToTop)?.scrollToTop()

            when (response) {
                is ResponseResult.Loading -> {
                    binding.mainLinerLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResponseResult.Error -> {
                    (activity as? SettableToolbarTitle)?.setToolbarTitle(ERROR_TITLE)
                    binding.errorLiner.visibility = View.VISIBLE
                }
                is ResponseResult.Success -> {

                    binding.mainLinerLayout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE


                    val picture = response.data

                    (activity as? SettableToolbarTitle)?.setToolbarTitle(picture.date)
                    binding.titleTextView.text = picture.title
                    binding.descriptionTextView.text = picture.explanation
                    if (picture.inFavorite)
                        binding.addToFavouritesImageView.setImageResource(R.drawable.ic_baseline_star_24)
                    else
                        binding.addToFavouritesImageView.setImageResource(R.drawable.ic_baseline_star_border_24)

                    if (picture.mediaType == REQUEST_MEDIA_TYPE_VIDEO) {
                        binding.showVideoButton.visibility = View.VISIBLE
                        binding.mainImageView.visibility = View.INVISIBLE
                    } else {
                        binding.showVideoButton.visibility = View.INVISIBLE
                        binding.mainImageView.visibility = View.VISIBLE
                        Glide.with(requireContext())
                            .load(picture.url)
                            .into(binding.mainImageView)
                    }
                }
            }

        }

        viewModel.seeAlsoLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResponseResult.Loading -> {

                }
                is ResponseResult.Error -> {

                }
                is ResponseResult.Success -> homeAdapter.seeAlsoList = response.data
            }

        }

        viewModel.getListSeeAlso()

        binding.addToFavouritesImageView.setOnClickListener {
            viewModel.favoritesClick()
            viewModel.pictureLiveData.value?.let { response ->
                if (response is ResponseResult.Success) {
                    val picture = response.data
                    if (picture.inFavorite) {
                        binding.addToFavouritesImageView.setImageResource(R.drawable.ic_baseline_star_border_24)
                    } else {
                        binding.addToFavouritesImageView.setImageResource(R.drawable.ic_baseline_star_24)
                    }
                }
            }
        }

        binding.showVideoButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                val response = viewModel.pictureLiveData.value
                if (response is ResponseResult.Success) {
                    data = Uri.parse(response.data.url)
                }

            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tool_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_calendar -> {
                val blankFragment =
                    BottomSheetFragment(object : BottomSheetFragment.OnDateChangeListener {
                        override fun onDateChange(date: String) {
                            viewModel.getPictureFromCalendar(date)
                        }
                    })
                blankFragment.show(childFragmentManager, blankFragment.tag)
            }
            R.id.action_send -> {
                val response = viewModel.pictureLiveData.value
                if (response is ResponseResult.Success) {
                    val picture = response.data
                    val message = "URL: ${picture.url}, ${picture.title}"
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_TEXT, message)
                    intent.type = "text/plain"
                    startActivity(intent)
                }
            }
        }
        return false
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.seeAlsoRecyclerView.layoutManager = layoutManager
        binding.seeAlsoRecyclerView.adapter = homeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}