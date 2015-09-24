/*!/usr/bin/java
* -*- coding: utf-8 -*-
*-----------------------------------------------------------
* basic TML RPC client
*-----------------------------------------------------------
*/

package client;

import com.tmlsidex.jni.TmlSidexException;
import com.tmlsidex.sidex.SDXDocument;
import com.tmlsidex.sidex.SDXList;
import com.tmlsidex.tml.TMLCmd;
import com.tmlsidex.tml.TMLCore;
import com.tmlsidex.tml.TMLProfile;

public class Client
{
  // load the libraries
  static {
    System.loadLibrary("jniSidex11");   // The TML JNI library
    System.loadLibrary("jniTml11");     // The TML JNI library
  }
  
  public static void main( String[] args ) 
  { 
	  //set variables for the ip and port of receiver side
	  String preference_ip = "127.0.0.1";
	  String preference_port = "1234";
	  
	  //init core and profile
	  TMLCore tmlCore = null;
	  String profileid = "urn:your-domain:com:javaExample01";
	  TMLProfile profile = null;
	  
	try { 
		tmlCore = new TMLCore();
		profile = new TMLProfile(profileid, tmlCore);
      	
		//init commands
		TMLCmd cmd43 = new TMLCmd(43);
		TMLCmd cmd42 = new TMLCmd(42);
		
		//create a SDXList with different types
		SDXList sdx_list = new SDXList();
	    sdx_list.appendValue(1);
	    sdx_list.appendValue(2);
	    sdx_list.appendValue("drei");
	    sdx_list.appendValue(true);
	    
	    //get and modify data of command
	    SDXDocument doc = cmd42.acquireData();
	    doc.setValue("GroupName", "ValueName", sdx_list);
	    System.out.println("cmd42 value before: " + ((SDXList) doc.getValue("GroupName", "ValueName")).getContent());
	    cmd42.releaseData();
	    
	    //send the command
	    profile.sendSyncMessage(preference_ip, preference_port, cmd42, 6000);
	    System.out.println("Command 42 send");
		
	    //command data after reply
		doc = cmd42.acquireData();
	    System.out.println("cmd42 value after: " + doc.getValue("GroupName", "ValueName"));
	    cmd42.releaseData();
	    

		//adding a progress handler to a specific command
		cmd43.registerCmdProgressReply(new Cmd43ProgressHandler(), null);
		
		//sending a command
		profile.sendSyncMessage(preference_ip, preference_port, cmd43, 6000);
		System.out.println("Command 43 send");
			
	} catch (TmlSidexException e) {
		e.printStackTrace();
	}
  }
}
