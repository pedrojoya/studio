package es.iessaladillo.pedrojoya.pr182.contacts;

import es.iessaladillo.pedrojoya.pr182.entity.Contact;
import es.iessaladillo.pedrojoya.pr182.utils.BasePresenter;

public interface ContactsPresenter extends BasePresenter<ContactsView> {

    void wantToAddContact();
    void wantToShowContacts();
    void wantToRemoveContact(Contact contact);
    void wantToShowChatWithContact(Contact contact);
    void wantToLogOut();

}
