package es.iessaladillo.pedrojoya.pr182.contacts;

import es.iessaladillo.pedrojoya.pr182.entity.Contact;

public class ContactsPresenterImpl implements ContactsPresenter {

    ContactsView mView;

    @Override
    public void wantToAddContact() {

    }

    @Override
    public void wantToShowContacts() {

    }

    @Override
    public void wantToRemoveContact(Contact contact) {

    }

    @Override
    public void wantToShowChatWithContact(Contact contact) {

    }

    @Override
    public void wantToLogOut() {
        // TODO logout
        if (mView != null) {
            mView.navigateToLoginActivity();
        }
    }

    @Override
    public void onViewAttached(ContactsView view) {
        mView = view;
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onViewDestroyed() {

    }
}
