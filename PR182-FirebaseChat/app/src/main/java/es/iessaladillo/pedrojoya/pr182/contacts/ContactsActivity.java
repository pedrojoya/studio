package es.iessaladillo.pedrojoya.pr182.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import es.iessaladillo.pedrojoya.pr182.R;
import es.iessaladillo.pedrojoya.pr182.utils.SingleFragmentActivity;

public class ContactsActivity extends SingleFragmentActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected int getFragmentContainerResId() {
        return R.id.flFragmentContainer;
    }

    @Override
    protected Fragment getFragment() {
        return new ContactsFragment();
    }

    @Override
    protected String getTag() {
        return "ContactsFragment";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
