/*!/usr/bin/java
* -*- coding: utf-8 -*-
*-----------------------------------------------------------
* simple TML command handler implementing a TML command interface
*-----------------------------------------------------------
*/

package server;

import com.tmlsidex.jni.TmlSidexException;
import com.tmlsidex.sidex.SDXDocument;
import com.tmlsidex.sidex.SDXList;
import com.tmlsidex.tml.TMLCmd;
import com.tmlsidex.tml.TMLCmdDispatchIF;


public class Cmd42Handler implements TMLCmdDispatchIF{
   
  @Override
  public void cmdDispatchCB(TMLCmd cmd, Object data) {
    try {
      System.out.println("Command "+ cmd.getCmdID() +" received");
      
      //adding values to cmd-data (SDXDoc)
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
