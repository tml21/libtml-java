# Android example #

### Libraries ###
To compile and execute the android exmaple app, some libraries are included. In the [installation guide for libTML-c](http://www.libtml.org/docs/libtml-c-html/md_tml_install.html) and [installation guide for libTML-java](Link to tml_install for java) is described how to build these libraries. Also another library is added, 'libstlport_shared.so'. It is a complete set of C++ standard library headers and needed for the other libraries. It can be found inside an android-ndk, /sources/cxx-stl/stlport/libs/ and depends on the android platform.

### Activities ###
- **MainActivity, MainListFragment**: The *MainActivity* is the Parentactivity of all others and organizes the application. For communicating with TML, the TMLcore should be initialised only once and then be used for sending every command. The MainActivity does all the communications with the Server and starts new Activities, expecting results from them and handling them approprietly. For example sending a specific command to the server with informations about an edited database-object. Afterwards the list of database-items will be discarded and directly loaded again from the database via the server.<br/>
The *MainListFragment* is a ListFragment that shows in the View of *MainActivity* as a list, each item displaying the title of the database-item. It calls upon the *MainActivity* every time a user selects an item of the list.
- **InputActivity**: Depending on the intent, it shows the title and textbody of the database-item or the fields are empty. The InputActivity is started for editing a database-item or adding a new item to the database. Through the save-option, button or going back, the activity is finished and the app returns to the MainActivity with informations about the edited or added item. 
- **SettingsActivity**: The Settings of the application can be seen in the MainActivity. The SettingsActivity displays them again and they can be edited and saved. Saving or going back finishes the activity and the user returns to the MainActivity.

#### A Java class ####
- **DataBaseEntry**: This class displays the row of a database-table as an object.

### Resources ###
- **menu**:	
	- menu_input: for the actionBar when the InputActivity is started
	- menu_main: for the actionBar in MainActivity

- **layout**:
	- input_item: layout for InputActivity
	- edit_settings: layout for SettingsActivity
	- layout for MainActivity, important here is a big FrameLayout, where the ListFragment is inserted in
<br/>
			
- **drawables**: there are four icons. The icon 'add' is used for the button in the main_view. Icons 'create' and 'delete' are used in the actionBar of MainActivity. And the 'save'-icon is used in the actionBar of InputActivity. The icons are downloaded from [Google Material icons](https://www.google.com/design/icons/index.html).
- **values**: there are some names add in string.xml, for the labels of some buttons and names of icons and options.
 