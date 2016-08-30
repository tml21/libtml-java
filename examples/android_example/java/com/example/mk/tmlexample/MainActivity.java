package com.example.mk.tmlexample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tmlsidex.jni.TmlSidexException;
import com.tmlsidex.sidex.SDXDocument;
import com.tmlsidex.tml.TMLCmd;
import com.tmlsidex.tml.TMLCore;
import com.tmlsidex.tml.TMLProfile;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //properties needed for TML communications
    TMLCore tmlCore = null;
    TMLProfile tmlProfile = null;
    //hardcoded defaults of ip, port and profilename
    String preference_ip = "10.15.134.229";
    String preference_port = "4711";
    String preference_profileid = "urn:your-domain:com:Android01";

    public ArrayList<DataBaseEntry> databaseItemList = new ArrayList<DataBaseEntry>();    //db-items list for global use in app
    //for adding/editing/deleting recent selected databaseItem
    private int position = -1;
    private boolean itemSelected = false;

    //boolean flags for edit/delete (option action) the next selected dblist-item
    private boolean editFlag = false;
    private boolean deleteFlag = false;

    //global menu for changing the colors of icons
    private Menu menu;


    static {
        //libraries needed for the TML JNI libraries
        System.loadLibrary("axl");
        System.loadLibrary("vortex-1.1");
        System.loadLibrary("iconv");
        System.loadLibrary("stlport_shared");
        System.loadLibrary("sidex12");
        System.loadLibrary("tmlcore12");
        // The TML JNI libraries
        System.loadLibrary("jniSidex12");
        System.loadLibrary("jniTml12");
    }


    //enum for differentiating between menu-options later
    public enum Action {
        INSERT,
        EDIT
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        //init TML core and TML profile for setting up communications, they have a global scope-
        //all communications of the app should go through these two properties
        try {
            tmlCore = new TMLCore();
            tmlProfile = new TMLProfile(preference_profileid,tmlCore);
        } catch(TmlSidexException e) {
            e.printStackTrace();
        }

        //load database-items
        loadDataBase();

        //creating instance of ListFragment
        MainListFragment listFragment = new MainListFragment();

        //add fragment to FrameLayout, or if there is already one, replace it
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar- if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //for accessing the menu items later, save menu as global var
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
                // if an item is already selected - edit it
                if(position != -1 & itemSelected) {
                    startSpecificActivity(Action.EDIT, position);
                    //deselect item from dblist
                    itemSelected = false;
                    this.position = -1;
                } else if(!editFlag & !deleteFlag) {
                    editFlag = true;
                    //changeColor of icon edit
                    item.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                } else if(deleteFlag){  //if delete was already selected- only one of the flags should be true
                    deleteFlag = false;
                    //change color of icon delete (@see menu_main)
                    menu.getItem(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    editFlag = true;
                    //changeColor edit
                    item.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                }
                return true;
            case R.id.action_delete:
                // if an item is selected- delete it
                if(position != -1 & itemSelected) {
                    int id = databaseItemList.get(position).id;
                    deleteItem(id);
                    //deselect item from databaseItemList
                    itemSelected = false;
                    this.position = -1;

                    //notify listfragment the list has changed
                    getSupportFragmentManager().findFragmentById(R.id.fragment_container).onResume();
                }else if (!deleteFlag & !editFlag) {    //or set edit/delete flags
                    deleteFlag = true;
                    //changeColor delete
                    item.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                } else if (editFlag){
                    editFlag = false;
                    //change color of icon edit (@see menu_main)
                    menu.getItem(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    deleteFlag = true;
                    //changeColor delete
                    item.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                }
                return true;
            case R.id.action_settings:
                //create new intent with arguments
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                settingsIntent.putExtra("ip", preference_ip);
                settingsIntent.putExtra("port", preference_port);
                settingsIntent.putExtra("profileid", preference_profileid);
                // Verify that the intent will resolve to an activity
                if (settingsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(settingsIntent, 2);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //todo
        TextView infoText = (TextView) findViewById(R.id.infoText);
        infoText.setText(preference_ip + ":" + preference_port +
                " with TMLProfilename: " + preference_profileid);

    }

    //getting back results from a child Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) {
            editFlag = false;
            deleteFlag = false;
            itemSelected = false;
            position = -1;

            //change color of icon delete (@see menu_main) and edit
            this.menu.getItem(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            this.menu.getItem(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

            return;
        }
        // requestcode 2 is for SettingsActivity
        if(requestCode == 2) {
            preference_ip = data.getStringExtra("ip");
            preference_port = data.getStringExtra("port");
            preference_profileid = data.getStringExtra("profileid");

            //should the profileid be updated?
            if(!(tmlProfile.getProfileUrl().equals(preference_profileid))) {
                try {
                    tmlProfile = new TMLProfile(preference_profileid, tmlCore);
                } catch(TmlSidexException e) {
                    e.printStackTrace();
                }
                //get new database-items
                loadDataBase();
            }
        } else if(requestCode == 1 | requestCode == 0){ //requestCode 0 and 1 are for add/editing activity
            if(requestCode == 0) {
                updateDataBase(data, Action.INSERT);
            } else {
                updateDataBase(data, Action.EDIT);
            }
        }
    }

    /*  Method to be implemented for listener of @see MainListFragment.java, gets called upon when user selected an
     * item from the list
     *  @param int position - index of an specific item inside property dblist
     */
    public void onItemSelected(int position) {
        //checking for boolean flags
        if(editFlag) {
            startSpecificActivity(Action.EDIT, position);
            //deselect item from dblist
            itemSelected = false;
            this.position = -1;
            editFlag = false;
            //change color of icon edit (@see menu_main)
            this.menu.getItem(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        } else if(deleteFlag) {
            //call upon a method for sending a command to the database for deleting an entry
            int id = databaseItemList.get(position).id;
            deleteItem(id);
            deleteFlag = false;
            //change color of icon delete (@see menu_main)
            this.menu.getItem(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            //deselect item from dblist
            itemSelected = false;
            this.position = -1;

        } else {    //no option selected, the item was only just picked
            itemSelected = true;
            this.position = position;
        }
    }

    /* method sends a TMLCommand with args to server for adding or editing an item from database
     * @param   Intent data - args from user
     * @param   Action action - which action should be done
     */
    protected void updateDataBase(Intent data, Action action) {
        TMLCmd cmd = null;
        try {
            switch (action) {
                case INSERT:
                    cmd = new TMLCmd(2);    //adding / inserting an item to database
                    break;
                case EDIT:
                    cmd = new TMLCmd(3);    //editing / updating an item from database
                    break;
            }
            //args for server about the new item / item to be updated
            SDXDocument doc = cmd.acquireData();
            doc.setValue("input", "id", data.getIntExtra("id", 0));
            doc.setValue("input", "title", data.getStringExtra("title"));
            doc.setValue("input", "body", data.getStringExtra("body"));
            cmd.releaseData();

            tmlProfile.sendSyncMessage(preference_ip, preference_port, cmd, 6000);
            System.out.println("Command 03 send");
        } catch(TmlSidexException e) {
            e.printStackTrace();
        }

        //update local databaseItemList
        loadDataBase();
    }


    /*
     * method for getting all items from the database and visualzing them in app,
     * sending a TMLCommand
     */
    protected void loadDataBase() {
        TMLCmd cmd = null;

        try {
            //init TML command
            cmd = new TMLCmd(1);
            //send TML command through one global TML profile
            tmlProfile.sendSyncMessage(preference_ip, preference_port, cmd, 6000);
            System.out.println("Command 01 send");

            //adding every database-item as dbentry into our dblist
            SDXDocument doc = cmd.acquireData();
            databaseItemList.clear();
            int size = Integer.parseInt((doc.getValue("db", "size")).toString());
            for (int i = 0; i < size; i++) {
                databaseItemList.add(new DataBaseEntry(Integer.parseInt(doc.getValue(String.valueOf(i), "id").toString()),
                        doc.getValue(String.valueOf(i),"title").toString(),
                        doc.getValue(String.valueOf(i), "body").toString()));
            }
            cmd.releaseData();

        } catch(TmlSidexException e) {
            e.printStackTrace();
        }
    }

    /* method for deleting an item from database through sending the TMLCommand to server
     * @params position - index of item from dblist that is to be deleted
     */
    public void deleteItem(int id) {
        TMLCmd cmd04 = null;
        try {
            //init TML command
            cmd04 = new TMLCmd(4);
            //add args to command about item that is to be deleted
            SDXDocument doc = cmd04.acquireData();
            doc.setValue("input", "id", id);
            cmd04.releaseData();

            //send TML command with TML profile to Server
            tmlProfile.sendSyncMessage(preference_ip, preference_port, cmd04, 6000);
            System.out.println("Command 04 send");
        } catch(TmlSidexException e) {
            e.printStackTrace();
        }

        //load all items from database again
        loadDataBase();
        //reset flag
        deleteFlag = false;
        //change color of icon delete (@see menu_main)
        menu.getItem(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        //notify listfragment the list has changed
        getSupportFragmentManager().findFragmentById(R.id.fragment_container).onResume();
    }

    /*
     * onClick-method for the add-button. Calls InputActivity.
     * @param View view - not needed
     */
    public void addButton(View v) {
        // Create an intent for adding activity
        Intent sendIntent = new Intent(this, InputActivity.class);
        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(sendIntent, 0);
        }
    }

    /*
     * helper function for starting an InputActivity with different requestcodes
     * @param Action action - gives information about which activity should be started
     * @param int id - which item from the dblist is needed
     */
    protected void startSpecificActivity(Action action, int position) {
        Intent infoForActivity = new Intent(this, InputActivity.class);
        int requestCode = -1;
        DataBaseEntry entry = databaseItemList.get(position);

        switch(action) {
            case EDIT:
                requestCode = 1;
                break;
            case INSERT:
                requestCode = 0;
                break;
        }
        infoForActivity.putExtra("id", entry.id);
        infoForActivity.putExtra("title", entry.title);
        infoForActivity.putExtra("body", entry.body);

        // Verify that the intent will resolve to an activity
        if (infoForActivity.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(infoForActivity, requestCode);
        }

        //reset flag and change color of icon edit
        editFlag = false;
        this.menu.getItem(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
    }
}
