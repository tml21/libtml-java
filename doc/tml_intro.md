# Introduction to libTML-java {#tml_intro}

* [How to use the library](#howtouse)
* [Implementing the Interface](#firstprofile)
	* [Create and prepare a TML core and profile](#prepcoreprofile)
	* [Add command handler functions](#addcommand)
	* [Start the Listener](#startlistener)
* [Calling the Interface](#callinterface)
	* [Receiving progress replies](#recprogress)
* [Using the data property and variants](#dataproperty)
	* [Add Lists and Dictionaries](#listdictdata)
* [Compilation and execution of source code](#comp&exec)


Many use cases are covered by the features of libTML. The example in this chapter is a brief overview of the most frequently used functions.

> All examples in this introduction contain only code lines to explain a special aspect of the library. Important things may be left out in favor of better readability. 

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

To use TML at least one [TMLCore] object is required. To accept incoming traffic the listener thread needs an IP address, port and one or more profiles to be published.  A TML profile is the interface to refer to by a remote call. The call to the constructor of both TMLCore and TMLProfile need to be inside a try-catch code block.

~~~~{.java}
TMLCore tmlcore = new TMLCore();
tmlcore.setlistenerIP("127.0.0.1");
tmlcore.setlistenerPort("1234");
~~~~
    
To identify a profile as a unique interface, a unique profile name (ID) is required. Any string can be used, but usually a URN is selected. In this example `urn:your-domain:com:javaExample01` is used.

~~~~{.java}
String profileid = 'urn:your-domain:com:javaExample01';
~~~~

The profile property is an instance of [TMLProfile]. It will be automatically added to the [TMLCore] during instantiation.

~~~~{.java}
TMLProfile profile = new TMLProfile(profileid, tmlcore);
~~~~

<a name="addcommand"></a>
### Add command handler ###

A command handler implements one of the  TML command interfaces ([TMLCmdIF], [TMLCmdDispatchIF],...) and an instance of the handler will be assigned as parameter during the publishing to the profile.

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

To publish a [TMLCmdIF] an instance is added to a profile using the [registerCmd()] method of the [TMLProfile] instance.

~~~~{.java}
//publishing handlers to profile
profile.registerCmd(42, new Cmd42Handler(), null);
profile.registerCmd(43, new Cmd43Handler(), null);
~~~~

The third parameter of [registerCmd()] holds custom callback data. The callback data can be for example a date or just null.	

<a name="startlistener"></a>
### Start the Listener ###

Eventually the listener is started to publish the commands to the network. TML uses multiple threads for parallel execution of the listeners. Meanwhile the main thread needs to wait or can do different things. The `Thread.sleep(15000)` function in this example waits 15 seconds until it terminates. In programs it can easily be replaced with the application loop.

~~~~{.java}
tmlcore.setListenerEnabled(true);
 
Thread.sleep(15000);
~~~~	


<a name="callinterface"></a>
## Calling the Interface ##

To call a particular interface a TCP/IP address and a profile ID is required. 

~~~~{.java}
String profileid = "urn:your-domain:com:javaExample01";
String preference_ip = "127.0.0.1";
String preference_port = "1234";
~~~~

The [TMLCore] is handling inbound and outbound messages. Before sending a command an instance of [TMLCore] is created. The profile can only be constructed after the creation of the [TMLCore].

~~~~{.java}
TMLCore tmlcore = new TMLCore();
TMLProfile profile = new TMLProfile(profileid, tmlCore);
~~~~

> Use the same [TMLCore] instance as long as the application is sending/receiving messages to keep the connections open. Reusing connections is much faster than starting a TCP/IP connection every time a message is sent.

Before sending a command, a [TMLCmd] object is created with the command ID of the remote interface function. It is send with the [profile.sendSyncMessage()] method of [TMLProfile]. Using synchronous calls with [profile.sendSyncMessage()] waits until a reply was received or an error occurs before returning. In case of an error an exception is raised.

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

TML allows to send back status or progress replies to the caller. Besides providing information about the status of the command processing at the remote peer, progress replies reset the timeout passed to the [profile.sendSyncMessage()] or [profile.sendAsyncMessage()] method. In cases of long or unknown reply times, the receiver can keep the sender waiting.

The timeout watchdog reset is performed by [sendProgressReply()] whether the progress reply is handled or a handler is registered or not. A progress handler needs to implement [TMLCmdProgressReplyIF] and is used to visualize the progress or any additional data. 

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

The progress handler is published to a [TMLCmd]. The first parameter (cmd) is the command instance currently processed by the remote peer and custom callback data or null is the second. The progress value is part of the command data, but it is passed for convenience as a third parameter.

~~~~{.java}
//creating command and handler
TMLCmd cmd43 = new TMLCmd(43);
Cmd43ProgressHandler progHandler = new Cmd43ProgressHandler();

//registering handler
cmd43.registerCmdProgressReply(progHandler, null);

//calling the progress command
profile.sendSyncMessage(preference_ip, preference_port, cmd43, 6000);
System.out.println("Command 43 send");
~~~~


> If the data of the command is modified either at sender (progress handler) or receiver side, the modifications are transferred together with the progress change. Applications can use this behavior to implement advanced features.

A Java code calling the commands of the interface in this example is producing the output below.

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

The output from the receiving thread would be as followed.

	Command 42 received
	cmd42 value: [1, 2, drei, true]
	Command 43 received


<a name="dataproperty"></a>
## Using the data property and variants ##

The SIDEX API (Simple Data Exchange) provides powerful functions to organize data. Any [TMLCmd] instance has it's data stored in the [SDXDocument] available through the data property.

~~~~{.java}
// create a SDXDocument
SDXDocument doc = new SDXDocument("SDXDocTest");
~~~~

An [SDXDocument] instance is storing data groups identified by a name (String). Each group can contain one or more values identified with keys (String). Value types can be integer, long, float, double, boolean, Integer, Long, Float, Double or [SDXBase]. The SIDEX variant types can be created and assigned like java values as well.

~~~~{.java}
sdoc.setValue("General", "int", 42);
sdoc.setValue("General", "float", 3.14);
sdoc.setValue("General", "bool", true); 

sdoc.setValue("General", "str", new SDXString("some text äüö !^s-§$"));
sdoc.setValue("General", "None", new SDXNone());

SDXDateTime sdxval = new SDXDateTime("2014-01-01 12:30:00:000");
sdoc.setValue("General", "date", sdxval);
~~~~ 

Reading data from a [SDXDocument] is just using the dictionary behavior and address the value with group and key names.

~~~~{.java}
System.out.println(sdoc.getValue("GroupName", "ValueName"));
~~~~

All values are automatically converted into java values.

<a name="listdictdata"></a>
### Add Lists and Dictionaries ###

Lists and dictionaries are powerful tools to create hierarchical data structures. The [SDXDocument] supports lists and dictionaries as well and they can simply be added by assignment.

~~~~{.java}
SDXList sdx_list = new SDXList();
sdx_list.appendValue(1);
sdx_list.appendValue(2);
sdx_list.appendValue("drei");
SDXList another_sdx_list = new SDXList();
another_sdx_list.appendValue("vier");
another_sdx_list.appendValue(5.5);
sdx_list.appendValue(another_sdx_list);
SDXDict dict = new SDXDict()
dict.setValue("key", "value");
dict.setValue("list", another_sdx_list);
sdx_list.appendValue(true);
~~~~

The example shows the assignment of a list with a mixture of values including another list and a dictionary type as values. 


<a name="comp&exec"></a>
## Compilation and execution of source code ##
Inside the libtml-java/example directory is the source code of the example above located. To compile and execute it, change the directory to libtml-java/examples/introduction-01 and create the new folder 'class'. Then compile the java-files. You find the compiled class-files in the 'class'-folder. Add the JAR file (tmlSidex.jar) to the classpath during the compilation-call. To make the path to the JAR shorter, just copy it into the directory /introduction-01.

*For example:* **compiling** *on windows or linux*:

	javac -cp /path/to/JAR -d class server/*.java
	javac -cp /path/to/JAR -d class client/*.java


Now open two other terminals und execute the Server at first an then the Client. They will communicate with each other.
Afterwards execute the main-file and set the JAR file to the classpath again. Add the location of your native libraries during your execution-call. Put them inside the same folder beforehand. The dependencies of the native libraries are inside this folder as well or they are globally accessible.


**executing** *on windows*:

	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR;class server.Server
	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR;class client.Client

*or linux*:

	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR:class server.Server
	java -Djava.library.path=/path/to/nativeLibs -cp /path/to/JAR:class client.Client



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
	
