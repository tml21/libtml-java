# Introduction to libTML-java {#tml_intro}

* [How to use the library](#howtouse)
* [Implementing the Interface](#firstprofile)
	* [Create and prepare a TML core and profile](#prepcoreprofile)
	* [Add command handler (listener)](#addcommand)
	* [Start the Listener](#startlistener)
* [Sending commands (sender)](#callinterface)
	* [Receiving progress replies](#recprogress)
* [Using the data property and variants](#dataproperty)
	* [Add Lists and Dictionaries](#listdictdata)
* [Compilation and execution of example source code](#comp&exec)


Many use cases are covered by the features of libTML. The example described in this chapter is a brief overview of the most frequently used functions. 

> You can find the example source code in directory `"/examples/introduction-01"` of our [libTML-java source repository on GitHub](https://github.com/tml21/libtml-java). <br>All examples in this introduction contain only code lines to explain a special aspect of the library. Important things may be left out in favor of better readability.  

<a name="howtouse"></a>
## How to use the library ##

The libTML-java library provides access to the libtml-c low level functions through usage of the Java Native Interface (JNI). Two fundamental libraries are available.
 
- TML (The Missing Link) provides the messaging and network functions.
- SIDEX (Simple Data Exchange) provides the API to access message data. 
 
After installing the libraries, Java is able to load the native libraries at runtime.

~~~~{.java}
//load the TML JNI libraries
static {
    System.loadLibrary("jniSidex11");
    System.loadLibrary("jniTml11");
  } 
~~~~
 
Using the low level API is similar to the C API in most aspects. The error handling is different in Java. Low level functions do not return an error code but raise an exception instead.
The usage of low level functions is discussed more deeply in the [C API documentation](http://libtml.org/docs/libtml-c-html/). 

<a name="firstprofile"></a>
## Implementing the Interface ##

The TML communication is peer to peer (P2P) and the Client/Server pattern is a subset. The latter is used in this example to demonstrate how to implement and access a remote interface with TML.

<a name="prepcoreprofile"></a>
### Create and prepare a TML core and profile ###

To use TML at least one [TMLCore] object is required. To accept incoming traffic the listener thread needs an IP address, port and one or more  registered profiles. A TML profile is the interface to refer to by a remote call. The call to the constructor of both TMLCore and TMLProfile needs to be inside a try-catch code block.

~~~~{.java}
TMLCore tmlcore = new TMLCore();
tmlcore.setlistenerIP("127.0.0.1");
tmlcore.setlistenerPort("1234");
~~~~
    
To identify a profile as a unique interface, a unique profile name (ID) is required. Any string can be used, but usually a URN is selected.

~~~~{.java}
String profileid = 'urn:your-domain:com:javaExample01';
~~~~

The profile property is an instance of [TMLProfile]. It will be automatically added to the [TMLCore] during instantiation.

~~~~{.java}
TMLProfile profile = new TMLProfile(profileid, tmlcore);
~~~~

<a name="addcommand"></a>
### Add command handler (listener) ###

Interfaces are used to implement callback functions in Java .

A command handler instance implements one of the  TML command interfaces ([TMLCmdIF], [TMLCmdDispatchIF],...).
It will be invoked in case of an incoming command.


~~~~{.java}
//handler implementing a TML command interface
public class Cmd42Handler implements TMLCmdDispatchIF{
   
  @Override
  public void cmdDispatchCB(TMLCmd cmd, Object data) {
	  
	  //modifying a value of command property data (SDXDocument) from sender
	  try {
		  System.out.println("Command " + cmd.getCmdID() +" received");
		  SDXDocument doc = cmd.acquireData();
		  
		  //before modifying the data from sender
		  System.out.println("cmd42 value: " + ((SDXList) doc.getValue("GroupName", "ValueName")).getContent());
		  
		  doc.setValue("GroupName", "ValueName", false);
		  cmd.releaseData();

	  } catch (TmlSidexException e) {
		  e.printStackTrace();
	  }
  }
}

//another handler implementing a TML command interface
// returns progress reply-messages to sender
public class Cmd43Handler implements TMLCmdDispatchIF{
  
  @Override
  public void cmdDispatchCB(TMLCmd cmd, Object data) {	

	int progress = 10;	    
	try {
		System.out.println("Command "+ cmd.getCmdID() +" received");
		while (progress < 100) {
			cmd.sendProgressReply(progress);	
	    	
			//do something
	    	try {
				Thread.sleep(500);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	
	   	 	progress=progress+10;	    		
		}			
	} catch (TmlSidexException e) {
		e.printStackTrace();
	}    
  }
}
~~~~

To register a [TMLCmdIF], its instance has to be added to a profile using the [registerCmd()] method of the [TMLProfile] class (second parameter).

~~~~{.java}
//publishing handlers to profile
profile.registerCmd(42, new Cmd42Handler(), null);
profile.registerCmd(43, new Cmd43Handler(), null);
~~~~

The first parameter represents the "command Id".<br>For example Id 42 invokes the interface implementation of Cmd42Handler().<br>
The third parameter of [registerCmd()] holds custom callback data. The custom callback data can be for example a date or just null.	

<a name="startlistener"></a>
### Start the Listener ###

~~~~{.java}
tmlcore.setListenerEnabled(true);
 
Thread.sleep(15000);
~~~~	

The listener is started to receive the commands via the network. TML uses an explicit listener thread. Meanwhile the main thread needs to wait or can do different things. The `Thread.sleep(15000)` function in the example waits 15 seconds until it terminates. In programs it can easily be replaced with the application loop.

<a name="callinterface"></a>
## Sending commands (sender) ##

To send a command, a TCP/IP address and a profile ID is required. 

~~~~{.java}
String profileid = "urn:your-domain:com:javaExample01";
String preference_ip = "127.0.0.1";
String preference_port = "1234";
~~~~

The [TMLCore] is handling inbound and outbound messages. Before sending a command an instance of [TMLCore] has to be created. Afterwards the profile has to be instantiated using the [TMLCore] instance as a parameter.

~~~~{.java}
TMLCore tmlcore = new TMLCore();
TMLProfile profile = new TMLProfile(profileid, tmlCore);
~~~~

> Use the same [TMLCore] instance as long as the application is sending/receiving messages to keep the connections open. Reusing connections is much faster than starting a TCP/IP connection every time a message is sent.

Data and the "command Id" can be configured using a [TMLCmd] object and send with the [profile.sendSyncMessage()] method of [TMLProfile].<br>
Using synchronous calls, [profile.sendSyncMessage()] waits until its reply has been received or an error occurred. In case of an error an exception is raised.

~~~~{.java}
//creating command 42
TMLCmd cmd42 = new TMLCmd(42);

//example of how to create and add data to a command
SDXList sdx_list = new SDXList();
sdx_list.appendValue(1);
sdx_list.appendValue(2);
sdx_list.appendValue(new SDXString("drei"));
sdx_list.appendValue(true);
	    
SDXDocument doc = cmd42.acquireData();
doc.setValue("GroupName", "ValueName", sdx_list);	//saving it as a key-value pair
System.out.println("cmd42 value before: " + ((SDXList) doc.getValue("GroupName", "ValueName")).getContent());
cmd42.releaseData();
	    
//calling the command
profile.sendSyncMessage(preference_ip, preference_port, cmd42, 6000);
System.out.println("Command 42 send");

//data after sending command and receiving reply		
doc = cmd42.acquireData();
System.out.println("cmd42 value after: " + doc.getValue("GroupName", "ValueName"));
cmd42.releaseData();
~~~~


<a name="recprogress"></a>
### Receiving progress replies ###

TML allows to reply asynchronous status or progress information to the calling instance using [sendStatusReply()] respectively [sendProgressReply()]. Aside from providing status or progress information, they reset the TML timeout watchdog. In cases of long or unknown reply times, the receiver can keep the sender waiting without falling into timeout.

To visualize the progress and / or additional data, a progress handler needs to implement the [TMLCmdProgressReplyIF] interface. 

~~~~{.java}
//progress handler-class
public class Cmd43ProgressHandler implements TMLCmdProgressReplyIF{

	@Override
	public void cmdProgressReplyCB(TMLCmd cmd, Object cbData, int progress) {

		//do something
		System.out.println("Progress:" + " " + progress);	
	}
}
~~~~

Register the progress handler to a [TMLCmd]:
~~~~{.java}
//creating command and handler
TMLCmd cmd43 = new TMLCmd(43);
Cmd43ProgressHandler progHandler = new Cmd43ProgressHandler();

//registering handler
cmd43.registerCmdProgressReply(progHandler, null);

//calling the progress command with a timeout value of 6000 ms
profile.sendSyncMessage(preference_ip, preference_port, cmd43, 6000);
System.out.println("Command 43 send");
~~~~


The Java code of our example calling the command with the ID "42" and "43" produce the following output:

    cmd42 value before: [1, 2, drei, true]
	Command 42 send
	cmd42 value after: false
	Progress: 10
	Progress: 20
	Progress: 30
	Progress: 40
	Progress: 50
	Progress: 60
	Progress: 70
	Progress: 80
	Progress: 90
	Command 43 send

The output from the receiving thread (listener) would be as followed:

	Command 42 received
	cmd42 value: [1, 2, drei, true]
	Command 43 received


<a name="dataproperty"></a>
## Using the data property and variants ##

The SIDEX API (Simple Data Exchange) provides powerful functions to organize data. Any [TMLCmd] instance has its data stored in the [SDXDocument] available through the data property.

~~~~{.java}
// create a TMLCmd:
TMLCmd cmd42 = new TMLCmd(42);
.
.
.
.
// Acquire SDXDocument:
SDXDocument sdoc = cmd42.acquireData();
.
.
// Set data
.
.
// Release SDXDocument:
cmd42.releaseData();
t doc = new SDXDocument("SDXDocTest");
~~~~

A [SDXDocument] instance is storing data groups identified by a name (String). Each group can contain one or more values identified with keys (String). Value types can be integer, long, float, double, boolean, Integer, Long, Float, Double or [SDXBase].

~~~~{.java}
// Set data
sdoc.setValue("General", "int", 42);
sdoc.setValue("General", "float", 3.14);
sdoc.setValue("General", "bool", true); 

sdoc.setValue("General", "str", new SDXString("some text äüö !^s-§$"));
sdoc.setValue("General", "None", new SDXNone());

SDXDateTime sdxval = new SDXDateTime("2014-01-01 12:30:00:000");
sdoc.setValue("General", "date", sdxval);
~~~~ 

<a name="listdictdata"></a>
### Add Lists and Dictionaries ###

Lists and dictionaries are powerful tools to create hierarchical data structures. The [SDXDocument] supports lists and dictionaries, and they can simply be added by assignment.

~~~~{.java}
SDXList sdx_list = new SDXList();
sdx_list.appendValue(1);
sdx_list.appendValue(2);
sdx_list.appendValue("drei");
sdx_list.appendValue(true);

SDXList another_sdx_list = new SDXList();
another_sdx_list.appendValue("vier");
another_sdx_list.appendValue(5.5);
sdx_list.appendValue(another_sdx_list);

SDXDict dict = new SDXDict()
dict.setValue("key", "value");
dict.setValue("list", sdx_list);

sdoc.setValue("General", "dictContainer", dict);

~~~~

The example shows the assignment of a list with a mixture of values including another list and a dictionary type containing it all. 

Reading data from a [SDXDocument] is just using the dictionary behavior and address the value with group and key names.

~~~~{.java}
System.out.println(sdoc.getValue("General", "str"));
~~~~

All values are automatically converted into java values.


<a name="comp&exec"></a>
## Compilation and execution of example source code ##
> Before the compilation and execution of the example code you need to install libTML-c and libTML-java.

You can find the example source code in our [libTML-java source repository on GitHub](https://github.com/tml21/libtml-java). Download the ZIP archive and extract it.

To compile and execute it, change to the directory `"/examples/introduction-01"` and create the new folder `"class"`. Add the JAR file (tmlSidex.jar) to the classpath during the compilation-call.

*For example:* **compiling** *on windows or linux*:

	javac -cp /path/to/JAR -d class server/*.java
	javac -cp /path/to/JAR -d class client/*.java


Now execute the server instance on a terminal emulation window and the client instance on a second terminal emulation window. They will communicate with each other.

**executing** *on windows*:

	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR;class server.Server
	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR;class client.Client

*or linux*:

	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR:class server.Server
	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR:class client.Client

*) /path/to/nativeLib:

The native libraries (see libTML-java installation) are:

- jniSidex11 library
- jniTml11 library



*) /path/to/JAR:

Path to JNI Java Archive / JAR file  (see libTML-java installation / JNI / creating a JAR):

- tmlSidex.jar


[TMLCore]: @ref com.tmlsidex.tml.TMLCore "TMLCore"
[TMLCmd]: @ref com.tmlsidex.tml.TMLCmd "TMLCmd"
[TMLProfile]: @ref com.tmlsidex.tml.TMLProfile "TMLProfile"
[SDXDocument]: @ref com.tmlsidex.sidex.SDXDocument "SDXDocument"
[profile.sendSyncMessage()]: @ref com.tmlsidex.tml.TMLProfile.sendSyncMessage() "sendSyncMessage()"
[registerCmd()]: @ref com.tmlsidex.tml.TMLProfile.registerCmd() "registerCmd()"
[TMLCmdIF]: @ref com.tmlsidex.tml.TMLCmdIF "TMLCmdIF"
[profile.sendAsyncMessage()]: @ref com.tmlsidex.tml.TMLProfile.sendAsyncMessage() "profile.sendAsyncMessage()"
[TMLCmdProgressReplyIF]: @ref com.tmlsidex.tml.TMLCmdProgressReplyIF "TMLCmdProgressReplyIF"
[TMLCmdDispatchIF]: @ref com.tmlsidex.tml.TMLCmdDispatchIF "TMLCmdDispatchIF"
[SDXBase]: @ref com.tmlsidex.sidex.SDXBase "SDXBase"
[sendProgressReply()]: @ref com.tmlsidex.tml.TMLCmd.sendProgressReply() "sendProgressReply()"
[sendStatusReply()]: @ref com.tmlsidex.tml.TMLCmd.sendStatusReply() "sendStatusReply()"

	
