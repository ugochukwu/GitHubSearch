package com.onwordiesquire.android.githubsearch.ui.search


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onwordiesquire.android.githubsearch.R
import com.onwordiesquire.android.githubsearch.domain.RepoPage
import com.onwordiesquire.android.githubsearch.ui.base.BaseFragment
import com.onwordiesquire.android.githubsearch.ui.base.State
import com.onwordiesquire.android.githubsearch.utils.MyLogger
import com.onwordiesquire.android.githubsearch.utils.disable
import com.onwordiesquire.android.githubsearch.utils.enable
import com.onwordiesquire.android.githubsearch.utils.hideSoftKeyboard
import com.onwordiesquire.android.githubsearch.utils.textValue
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment(), MyLogger {

    @Inject
    lateinit var viewModelFactoryProvider: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel

    private val repoListAdapter: RepoListAdapter by lazy { RepoListAdapter() }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = injectViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupList()
        observeUiModelState()
    }

    override fun injectViewModel(): SearchViewModel = ViewModelProviders.of(this, viewModelFactoryProvider)
            .get(SearchViewModel::class.java)

    private fun observeUiModelState() {
        viewModel.subscribeToUiEvents().observe(this, Observer { uiModel ->
            uiModel?.let {
                with(it) {
                    logE("state is $state")
                    when (state) {
                        is State.Loading -> renderLoadingState()
                        is State.Success<*> -> renderSuccessState(state)
                        is State.Empty -> renderFeedbackState(state.message, R.drawable.ic_empty_state)
                        is State.Error -> renderFeedbackState(state.message, R.drawable.ic_error_state)
                        is State.PaginationSuccess -> TODO("Currently not supported")
                        is State.PaginationError -> TODO("Currently not supported")
                    }
                }
            }
        })
    }

    private fun renderSuccessState(state: State.Success<*>) {
        repoListAdapter.submitList((state.data as RepoPage).repositoryList)
        activity?.hideSoftKeyboard()
        searchBtn.enable()

        val constraintSet = ConstraintSet()
        constraintSet.clone(searchViewConstraint)
        constraintSet.setVisibility(progressBar.id, View.GONE)
        constraintSet.setVerticalBias(textInputLayout.id, 0.0F)
        constraintSet.setVisibility(recyclerView.id, View.VISIBLE)
        constraintSet.applyTo(searchViewConstraint)
        TransitionManager.beginDelayedTransition(searchViewConstraint)
    }

    private fun setupList() {
        with(recyclerView) {
            adapter = repoListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun renderLoadingState() {
        activity?.hideSoftKeyboard()
        searchBtn.disable()

        val constraintSet = ConstraintSet()
        constraintSet.clone(searchViewConstraint)
        constraintSet.setVerticalBias(textInputLayout.id, 0.0F)
        constraintSet.setVisibility(error_emptyState.id, View.GONE)
        constraintSet.setVisibility(recyclerView.id, View.GONE)
        constraintSet.setVisibility(progressBar.id, View.VISIBLE)
        constraintSet.applyTo(searchViewConstraint)
        TransitionManager.beginDelayedTransition(searchViewConstraint)
    }

    private fun setupSearchView() {
        searchBtn.setOnClickListener { viewModel.onSearchForRepository(searchTerm = textInputLayout.textValue) }
    }

    private fun renderFeedbackState(message: String, imgResId: Int) {
        activity?.hideSoftKeyboard()
        searchBtn.enable()

        val constraintSet = ConstraintSet()
        constraintSet.clone(searchViewConstraint)
        constraintSet.setVisibility(progressBar.id, View.GONE)
        imgFeedbackState.setImageDrawable(getFeedbackImage(imgResId))
        feedbackMsgTxt.text = message
        constraintSet.setVisibility(error_emptyState.id, View.VISIBLE)

        constraintSet.applyTo(searchViewConstraint)
        TransitionManager.beginDelayedTransition(searchViewConstraint)
    }

    private fun getFeedbackImage(resourceId: Int) = context?.let {
        VectorDrawableCompat.create(it.resources, resourceId, it.theme)
    }
}
