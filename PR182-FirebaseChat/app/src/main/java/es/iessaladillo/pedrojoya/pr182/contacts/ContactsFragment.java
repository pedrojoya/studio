package es.iessaladillo.pedrojoya.pr182.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.iessaladillo.pedrojoya.pr182.R;
import es.iessaladillo.pedrojoya.pr182.entity.Contact;
import es.iessaladillo.pedrojoya.pr182.login.LoginActivity;
import es.iessaladillo.pedrojoya.pr182.utils.DividerItemDecoration;

public class ContactsFragment extends Fragment implements ContactsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstContacts)
    RecyclerView lstContacts;
    @BindView(R.id.lblEmptyView)
    TextView lblEmptyView;
    @BindView(R.id.fabAddContact)
    FloatingActionButton fabAddContact;

    private ContactsPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver;

    public ContactsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ContactsPresenterImpl();
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        mAdapter = new ContactsAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkAdapterIsEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkAdapterIsEmpty();
            }
        };
        mAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        lstContacts.setHasFixedSize(true);
        lstContacts.setAdapter(mAdapter);
        checkAdapterIsEmpty();
        mLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        lstContacts.setLayoutManager(mLayoutManager);
        lstContacts.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));
        lstContacts.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mPresenter.wantToRemoveContact(mAdapter.getItem(viewHolder.getAdapterPosition()));
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstContacts);
    }

    private void checkAdapterIsEmpty() {
        lblEmptyView.setVisibility(mAdaptador.getItemCount() == 0 ?
                View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onViewAttached(this);
    }

    @Override
    public void onStop() {
        mPresenter.onViewDetached();
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuLogOut) {
            mPresenter.wantToLogOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showContacts(List<Contact> contactList) {

    }

    @Override
    public void showContactAdded() {

    }

    @Override
    public void navigateToChatActivity() {

    }

    @Override
    public void navigateToLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

}
