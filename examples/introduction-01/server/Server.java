/*!/usr/bin/java
* -*- coding: utf-8 -*-
*-----------------------------------------------------------
* TML - simple procedure based RPC server
*-----------------------------------------------------------
*/

package server;

import com.tmlsidex.jni.TmlSidexException;
import com.tmlsidex.tml.TMLCore;
import com.tmlsidex.tml.TMLProfile;

public class Server
{
  // load the TML JNI libraries
  static {
    System.loadLibrary("jniSidex11");   
    System.loadLibrary("jniTml11");     
  }

  public static void main( String[] args )
  {
    //init core and profile
    TMLCore tmlcore = null;
    String profileid = "urn:your-domain:com:javaExample01";
    TMLProfile profile = null;
    
    try {
      tmlcore = new TMLCore();
      profile = new TMLProfile(profileid, tmlcore);

      //register commands and respective handler
      profile.registerCmd(42, new Cmd42Handler(), null);
      profile.registerCmd(43, new Cmd43Handler(), null);        
      System.out.println("handlers registered");
                
      //set Listener properties and start
      tmlcore.setListenerIP("127.0.0.1"); 
      tmlcore.setListenerPort("1234");
      tmlcore.setListenerEnabled(true);
    } catch (TmlSidexException e) {
      e.printStackTrace();
    }
    //do something
    try {
      Thread.sleep(15000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
