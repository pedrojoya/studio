package es.iessaladillo.pedrojoya.pr182.contacts;

import java.util.List;

import es.iessaladillo.pedrojoya.pr182.entity.Contact;
import es.iessaladillo.pedrojoya.pr182.utils.BaseView;

public interface ContactsView extends BaseView {

    void showContacts(List<Contact> contactList);
    void showContactAdded();
    void navigateToChatActivity();
    void navigateToLoginActivity();

}
