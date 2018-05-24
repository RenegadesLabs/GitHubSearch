package com.labs.renegades.githubsearch.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager
import com.labs.renegades.githubsearch.R
import com.labs.renegades.githubsearch.mvvm.Resource
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }
    private val adapter by lazy { SearchAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.observeLifecycle(this)
        initSearchList()
        initReposObserver()
        initListeners()
    }

    private fun initSearchList() {
        searchList.apply {
            adapter = this@SearchActivity.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun initReposObserver() {
        viewModel.getReposLiveData().observe(this, Observer { resource ->
            when (resource?.status) {
                Resource.Status.SUCCESS -> adapter.submitList(resource.data)
                Resource.Status.ERROR -> Snackbar.make(container,
                        resource.message ?: return@Observer, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun initListeners() {
        btnSearch.setOnClickListener {
            hideKeyboard()
            viewModel.searchRepos(
                    etRepos.text.toString().let {
                        if (it.isNotBlank()) it else return@setOnClickListener
                    })
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
    }
}
