package uk.co.victoriajanedavis.chatapp.presentation.ui.friendrequests.sent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_friend_requests_tab.*
import kotlinx.android.synthetic.main.layout_message.*
import uk.co.victoriajanedavis.chatapp.R
import uk.co.victoriajanedavis.chatapp.domain.entities.FriendshipEntity
import uk.co.victoriajanedavis.chatapp.presentation.common.ListState
import uk.co.victoriajanedavis.chatapp.presentation.common.ListState.*
import uk.co.victoriajanedavis.chatapp.presentation.common.ViewModelFactory
import uk.co.victoriajanedavis.chatapp.presentation.ext.gone
import uk.co.victoriajanedavis.chatapp.presentation.ext.observe
import uk.co.victoriajanedavis.chatapp.presentation.ext.showSnackbar
import uk.co.victoriajanedavis.chatapp.presentation.ext.visible
import uk.co.victoriajanedavis.chatapp.presentation.ui.friendrequests.sent.adapter.SentFriendRequestAction
import uk.co.victoriajanedavis.chatapp.presentation.ui.friendrequests.sent.adapter.SentFriendRequestsAdapter
import javax.inject.Inject

class SentFriendRequestsFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    //@Inject lateinit var actionLiveData: MutableLiveData<SentFriendRequestAction>
    @Inject lateinit var adapter: SentFriendRequestsAdapter
    lateinit var viewModel: SentFriendRequestsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SentFriendRequestsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friend_requests_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFloatingAction()
        setupViewModelStateObserver()
    }

    private fun setupRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshItems()
        }
    }

    private fun setupFloatingAction() {
        fab.visible()
        fab.setOnClickListener { _ ->
            // Do something (probably a DialogFragment)
        }
    }

    private fun setupViewModelStateObserver() {
        viewModel.getSentFriendRequestsLiveData().observe(viewLifecycleOwner) {
            it?.let(::onStateChanged)
        }
    }

    private fun onStateChanged(state: ListState<List<FriendshipEntity>>) = when(state) {
        is ShowContent -> showContent(state.content)
        is ShowLoading -> {}
        is StopLoading -> swipeRefreshLayout.isRefreshing = false
        is ShowError -> showError(state.message)
        is ShowEmpty -> showEmpty()
    }

    private fun showContent(list: List<FriendshipEntity>) {
        message_layout.gone()
        adapter.submitList(list)
    }

    private fun showEmpty() {
        adapter.submitList(ArrayList())
        message_layout.visible()
        message_button.gone()
        message_imageview.setImageResource(R.drawable.ic_friend_request_none_72dp)
        message_textview.setText(R.string.error_no_items_to_display)
    }

    private fun showError(message: String) {
        showSnackbar(message, Snackbar.LENGTH_LONG).show()
        swipeRefreshLayout.isRefreshing = false
    }

    companion object {
        fun newInstance() = SentFriendRequestsFragment()
    }
}