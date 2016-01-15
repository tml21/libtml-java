/*!/usr/bin/java
* -*- coding: utf-8 -*-
*-----------------------------------------------------------
* simple TML command handler, implementing a TML command interface
*-----------------------------------------------------------
*/

package server;

import com.tmlsidex.jni.TmlSidexException;
import com.tmlsidex.tml.TMLCmd;
import com.tmlsidex.tml.TMLCmdDispatchIF;


public class Cmd43Handler implements TMLCmdDispatchIF{
  
  @Override
  public void cmdDispatchCB(TMLCmd cmd, Object data) {  

    int progress = 10;
        
    try {
      System.out.println("Command "+ cmd.getCmdID() +" received");
      
      while (progress < 100) {  
        //send progress reply-message to sender
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
