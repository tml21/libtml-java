/*!/usr/bin/java
* -*- coding: utf-8 -*-
*-----------------------------------------------------------
* simple TML progress handler implementing a TML command (progress reply) interface 
*-----------------------------------------------------------
*/

package client;

import com.tmlsidex.tml.TMLCmd;
import com.tmlsidex.tml.TMLCmdProgressReplyIF;

public class Cmd43ProgressHandler implements TMLCmdProgressReplyIF{

	@Override
	public void cmdProgressReplyCB(TMLCmd cmd, Object cbData, int progress) {
		
		System.out.println("Progress:" + " " + progress);	
	}
}
