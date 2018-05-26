package c.srs41.myapplication;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayAdapter<Contact> adapter;
    private ListView contact_list;
    private ContactRepository contactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contact_list = (ListView) findViewById(R.id.contactsListView);
        adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1, contacts);

        if (contact_list != null) {
            contact_list.setAdapter(adapter);
            contact_list.setOnItemClickListener(this);
        }


        contactRepository = new ContactRepository(this);
        contactRepository.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> updatedContacts) {
                // update the contacts list when the database changes
                adapter.clear();
                adapter.addAll(updatedContacts);
            }
        });

        if (savedInstanceState != null) {
            for (Parcelable contact : savedInstanceState.getParcelableArrayList(
                    "contacts")) {
                contacts.add((Contact) contact);
            }
        } else {
            //contacts.add(new Contact("Joe Bloggs", "joe@bloggs.co.nz",
            //        "021123456"));
            //contacts.add(new Contact("Jane Doe", "jane@doe.co.nz",
            //       "022123456"));
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        savedState.putParcelableArrayList("contacts", contacts);
        super.onSaveInstanceState(savedState);
    }

    public void saveContact(View view) {
        EditText e5 = (EditText) findViewById(R.id.name);
        String name = e5.getText().toString();

        EditText e8 = (EditText) findViewById(R.id.email);
        String email = e8.getText().toString();

        EditText e9 = (EditText) findViewById(R.id.mobile);
        String mobile = e9.getText().toString();

        boolean isFound = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (name.equals(contacts.get(i).name)) {
                isFound = true;
                Toast.makeText(this, "data updated", Toast.LENGTH_LONG).show();
                // update the existing contacts with the new ones
                //  long id=contacts.get(i).id;
                /*contacts.get(i).email = email;
                contacts.get(i).mobile = mobile;
                contactRepository.update(i);*/
                Contact a = contacts.get(i);
                a.email = email;
                a.mobile = mobile;
                contactRepository.update(a);


                break;
            }
        }

        if (isFound == false) {

            Contact c = new Contact(name, email, mobile);

            contactRepository.insert(c);

            //contacts.add(new Contact(name, email, mobile));
            //setAdapter(contacts);


            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Saved contact for " + name + email + mobile,
                    Toast.LENGTH_SHORT).show();
        }}

        public void setAdapter (ArrayList < Contact > contacts) {
            adapter = new ArrayAdapter<Contact>(this,
                    android.R.layout.simple_list_item_1, contacts);
            contact_list.setAdapter(adapter);
        }

        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
            Contact contact = (Contact) parent.getAdapter().getItem(position);
            Toast.makeText(parent.getContext(), "Clicked " + contact,
                    Toast.LENGTH_SHORT).show();

            //we need to get name fields of the form
            EditText e5 = (EditText) findViewById(R.id.name);
            e5.setText(contact.name);

            EditText e8 = (EditText) findViewById(R.id.email);
            e8.setText(contact.email);

            EditText e9 = (EditText) findViewById(R.id.mobile);
            e9.setText(contact.mobile);


        }
    }

