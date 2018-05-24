package com.onwordiesquire.android.githubsearch.ui.search


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onwordiesquire.android.githubsearch.R
import com.onwordiesquire.android.githubsearch.ui.base.BaseFragment
import com.onwordiesquire.android.githubsearch.ui.base.ModelState
import com.onwordiesquire.android.githubsearch.utils.textValue
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactoryProvider: ViewModelProvider.Factory

    private val viewModel: SearchViewModel by lazy { injectViewModel() }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSearchView()

        viewModel.subscribeToUiEvents().observe(this, Observer { uiModel ->
            uiModel?.let {
                with(it) {
                    when (state) {
                        is ModelState.Loading -> renderLoadingState()
                        is ModelState.Success<*> -> TODO()
                        is ModelState.Empty -> TODO()
                        is ModelState.Error -> TODO()
                        is ModelState.PaginationSuccess -> TODO()
                        is ModelState.PaginationError -> TODO()
                    }
                }
            }
        })
    }

    override fun injectViewModel(): SearchViewModel = ViewModelProviders.of(this, viewModelFactoryProvider)
            .get(SearchViewModel::class.java)

    private fun renderLoadingState() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(searchViewConstraint)
        constraintSet.setVerticalBias(textInputLayout.id, 0.0F)
        constraintSet.setVisibility(progressBar.id, View.VISIBLE)
        constraintSet.applyTo(searchViewConstraint)
        TransitionManager.beginDelayedTransition(searchViewConstraint)
    }

    private fun setupSearchView() {
        searchBtn.setOnClickListener { viewModel.onSearchForRepository(searchTerm = textInputLayout.textValue) }
    }

}
