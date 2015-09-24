/*
 *  libTML:  A BEEP based Messaging Suite
 *  Copyright (C) 2015 wobe-systems GmbH
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this program; if not, write to the Free
 *  Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 *  
 *  You may find a copy of the license under this software is released
 *  at COPYING file. This is LGPL software: you are welcome to develop
 *  proprietary applications using this library without any royalty or
 *  fee but returning back any change, improvement or addition in the
 *  form of source code, project image, documentation patches, etc.
 *
 *  Homepage:
 *    http://www.libtml.org
 *
 *  For professional support contact us:
 *
 *    wobe-systems GmbH
 *    support@libtml.org
 */
 
package com.tmlsidex.tml;

import com.tmlsidex.jni.Tml;
import com.tmlsidex.jni.TmlSidexException;


/**
 * @ingroup tmlClasses
 * @brief The TML core manages all communication of a peer.
 */
public class TMLCore extends Object{

  // Member
  long      m_var               = 0;
  Tml       m_tml               = null;
  Object[]  m_CmdReadyObjArr    = null;
  Object[]  m_CmdProgressObjArr = null;
  Object[]  m_CmdStatusObjArr   = null;

  /**
   * @brief constructs a TMLCore instance
   * 
   * The constructor opens a TML core.
   *
   * @throws TmlSidexException 
   */
  public TMLCore() throws TmlSidexException{
    m_tml    = new Tml();
    m_var = m_tml.tml_Core_Open(0);
  }

  /**
   * @brief constructs a TMLCore instance
   * 
   * The constructor opens a TML core with a specirfic log value for debugging
   * communication.
   *
   * @param iLog  logging value
   * 
   * @throws TmlSidexException 
   */
  public TMLCore(int iLog) throws TmlSidexException{
    m_tml    = new Tml();
    m_var = m_tml.tml_Core_Open(iLog);
  }

  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    m_CmdReadyObjArr    = null;
    m_CmdProgressObjArr = null;
    m_CmdStatusObjArr   = null;
    // Internal destructrion:
    if (0 >= m_var){
      m_tml.tml_Core_Close(m_var);
    }
    m_tml = null;
    m_var = 0;
    // Super destructrion
    super.finalize();
  }

  /**
   * @brief get listener status
   *
   * @return  listener status, true if listener is enabled, false is listener is disabled
   * 
   * @throws TmlSidexException 
   */
  public boolean getListenerEnabled() throws TmlSidexException{
    return m_tml.tml_Core_Get_ListenerEnabled(m_var);
  }
  
  /**
   * @brief get the listener interface (IP)
   * 
   * @return interface IP
   * 
   * @throws TmlSidexException 
   */
  public String getListenerIP() throws TmlSidexException{
    return m_tml.tml_Core_Get_ListenerIP(m_var);
  }
  
  /**
   * @brief get the listener port number
   * 
   * @return port number
   * 
   * @throws TmlSidexException 
   */
  public String getListenerPort() throws TmlSidexException{
    return m_tml.tml_Core_Get_ListenerPort(m_var);
  }
  
  /**
   * @brief enable or disable the TML listener
   *
   * @note The interface IP and port have to be configured
   * before enabling the listener.
   * 
   * @param bEnable  true to enable, false to disable the listener
   * 
   * @throws TmlSidexException 
   */
  public void setListenerEnabled(boolean bEnable) throws TmlSidexException{
    m_tml.tml_Core_Set_ListenerEnabled(m_var, bEnable);
  }
  
  /**
   * @brief set the listener interface (IP)
   *
   * The listener can be assigned to a specific network interface
   * or to all network interfaces available ("0.0.0.0) which is the default.
   * 
   * @param sIP  interface IP
   * 
   * @throws TmlSidexException 
   */
  public void setListenerIP(String sIP) throws TmlSidexException{
    m_tml.tml_Core_Set_ListenerIP(m_var, sIP);
  }
  
  /**
   * @brief set listener port
   *
   * @param  sPort number, 0 = random port number, default 44000
   * 
   * @throws TmlSidexException 
   */
  public void setListenerPort(String sPort) throws TmlSidexException{
    m_tml.tml_Core_Set_ListenerPort(m_var, sPort);
  }
  
  /**
   * @brief get the maximum event connection fail count
   * 
   * The maximum connection fail count defines after how many unsuccessful
   * tries to send an event an event consumer is automatically removed from the list.
   * A value of 0 means it is never removed. The default value is 1.
   *
   * @return  maximum fail count
   * 
   * @throws TmlSidexException 
   */
  public int getMaxEvtFailCount() throws TmlSidexException{
    return  m_tml.tml_Evt_Get_MaxConnectionFailCount(m_var);
  }
  
  /**
   * @brief get the maximum value of queued event messages.
   * 
   * If the maximum number of queued events for an event consumer
   * is reached, the oldest event is deleted. The default value is 1000.
   *
   * @return  maximum queue count
   * 
   * @throws TmlSidexException 
   */
  public int getEvtQueueSize() throws TmlSidexException{
    return  m_tml.tml_Evt_Get_MaxQueuedEventMessages(m_var);
  }
  
  /**
   * @brief set the maximum event connection fail count
   *
   * The maximum connection fail count defines after how many unsuccessful
   * tries to send an event an event consumer is automatically removed from the list.
   * A value of 0 means it is never removed. The default value is 1.
   *
   * @param iCount    maximum fail count 
   * 
   * @throws TmlSidexException 
   */
  public void setMaxEvtFailCount(int iCount) throws TmlSidexException{
    m_tml.tml_Evt_Set_MaxConnectionFailCount(m_var, iCount);
  }
    
  /**
   * @brief set the maximum value of queued events
   * 
   * If the maximum number of queued events for an event consumer
   * is reached, the oldest event will be deleted. The default value is 1000.
   *
   * @param iMaximum maximum queue count
   * 
   * @throws TmlSidexException 
   */
  public void setEvtQueueSize(int iMaximum) throws TmlSidexException{
    m_tml.tml_Evt_Set_MaxQueuedEventMessages(m_var, iMaximum);
  }
  
  /**
   * @brief get the maximum load balancing connection fail count
   *
   * The maximum connection fail count defines after how many unsuccessful
   * tries to connect, an event consumer is automatically removed from the list.
   * A value of 0 means it is never removed.
   * The default value is 1.
   *
   * @return  maximum fail count
   * 
   * @throws TmlSidexException 
   */
  public int getMaxBalFailCount() throws TmlSidexException{
    return  m_tml.tml_Bal_Get_MaxConnectionFailCount(m_var);
  }

  /**
   * @brief set the maximum load balancing connection fail count
   *
   * The maximum connection fail count defines after how many unsuccessful
   * tries to connect, an event consumer is automatically removed from the list.
   * A value of 0 means it is never removed.
   * The default value is 1.
   *
   * @param iCount    maximum fail count 
   * 
   * @throws TmlSidexException 
   */
  public void setMaxBalFailCount(int iCount) throws TmlSidexException{
     m_tml.tml_Bal_Set_MaxConnectionFailCount(m_var, iCount);
  }

  /**
   * @brief register cmd callback handler
   *
   * @param cmd   TMLCmd object
   * 
   * @throws TmlSidexException
   */
  private void registerCmdCallbackHandler(TMLCmd cmd) throws TmlSidexException{
    if (null != m_CmdReadyObjArr){
      TMLCmdReadyIF[]         cbInstanceCmdReady    = new TMLCmdReadyIF[1];
      Object[]                pCBData               = new Object[1];
      cmd.registeredCmdReady(cbInstanceCmdReady, pCBData);
      if (null == cbInstanceCmdReady[0]){
        cmd.registerCmdReady((TMLCmdReadyIF)m_CmdReadyObjArr[0], m_CmdReadyObjArr[1]);
      }
      cbInstanceCmdReady    = null;
      pCBData               = null;
    }
    if (null != m_CmdProgressObjArr){
      TMLCmdProgressReplyIF[] cbInstanceCmdProgress = new TMLCmdProgressReplyIF[1];
      Object[]                pCBData               = new Object[1];
      cmd.registeredCmdProgressReply(cbInstanceCmdProgress, pCBData);
      if (null == cbInstanceCmdProgress[0]){
        cmd.registerCmdProgressReply((TMLCmdProgressReplyIF)m_CmdProgressObjArr[0], m_CmdProgressObjArr[1]);
      }
      cbInstanceCmdProgress = null;
      pCBData               = null;
    }
    if (null != m_CmdStatusObjArr){
      TMLCmdStatusReplyIF[]   cbInstanceCmdStatus   = new TMLCmdStatusReplyIF[1];
      Object[]                pCBData               = new Object[1];
      cmd.registeredCmdStatusReply(cbInstanceCmdStatus, pCBData);
      if (null == cbInstanceCmdStatus[0]){
        cmd.registerCmdStatusReply((TMLCmdStatusReplyIF)m_CmdStatusObjArr[0], m_CmdStatusObjArr[1]);
      }
      cbInstanceCmdStatus   = null;
      pCBData               = null;
    }
  }
  
  /**
   * @brief send an asynchronous message
   *
   * The call returns after sending the message without waiting for a reply.
   * To process a reply the command ready event is called.
   *
   * @param sHost   hostname or IP address 
   * @param sPort  port number 
   * @param sProfile  profile name 
   * @param cmd   TMLCmd object
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void sendAsyncMessage(String sHost, String sPort, String sProfile, TMLCmd cmd, long timeout) throws TmlSidexException{
    registerCmdCallbackHandler(cmd);
    long cmdHandle = cmd.getVHandle();
    m_tml.tml_Send_AsyncMessage(m_var, cmdHandle, sProfile, sHost, sPort, timeout);
  }

  /**
   * @brief send a synchronous message
   *
   * Sending a message synchronously means that the call waits for a reply
   * and returns the result of the message or an error.
   *
   * @param sHost   hostname or IP address 
   * @param sPort  port number 
   * @param sProfile  profile name 
   * @param cmd   TMLCmd object
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void sendSyncMessage(String sHost, String sPort, String sProfile, TMLCmd cmd, long timeout) throws TmlSidexException{
    registerCmdCallbackHandler(cmd);
    long cmdHandle = cmd.getVHandle();
    m_tml.tml_Send_SyncMessage(m_var, cmdHandle, sProfile, sHost, sPort, timeout);
  }
  
  /**
   * @brief get low level TML core handle
   *
   * @return TML core handle
   */
  public long getCHandle(){
    return m_var;
  }
  
  /**
   * @brief register command ready handler
   *
   * The result of a command is passed to a command ready handler. In case of asynchronous commands
   * this is the only way to process the reply.
   *
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException
   * @see TMLCmdReadyIF
   * @see TMLCmd#registerCmdReady(TMLCmdReadyIF, Object)
   */
  public void registerCmdReady(TMLCmdReadyIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null == m_CmdReadyObjArr){
      m_CmdReadyObjArr = new Object[2];
    }
    m_CmdReadyObjArr[0] = cbInstance;
    m_CmdReadyObjArr[1] = pCBData;
  }
  
  /**
   * @brief register progress reply handler
   * 
   * The progress reply handler is called if a progress reply was received.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException
   * @see TMLCmdProgressReplyIF
   * @see TMLCmd#registerCmdProgressReply(TMLCmdProgressReplyIF, Object)
   */
  public void registerCmdProgressReply(TMLCmdProgressReplyIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null == m_CmdProgressObjArr){
      m_CmdProgressObjArr = new Object[2];
    }
    m_CmdProgressObjArr[0] = cbInstance;
    m_CmdProgressObjArr[1] = pCBData;
  }
  
  /**
   * @brief register status reply handler
   * 
   * The status reply handler is called if a status reply was received.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException
   * @see TMLCmdStatusReplyIF
   * @see TMLCmd#registerCmdStatusReply(TMLCmdStatusReplyIF, Object)
   */
  public void registerCmdStatusReply(TMLCmdStatusReplyIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null == m_CmdStatusObjArr){
      m_CmdStatusObjArr = new Object[2];
    }
    m_CmdStatusObjArr[0] = cbInstance;
    m_CmdStatusObjArr[1] = pCBData;
  }
  
  /**
   * @brief get registered command ready handler and custom data
   * 
   * @param cbInstance array of size 1 containing command ready handler
   * @param pCBData  array of size 1 - returns custom data
   *  
   * @throws TmlSidexException
   * @see TMLCmdReadyIF
   */
  public void registeredCmdReady(TMLCmdReadyIF[] cbInstance, Object[] pCBData) throws TmlSidexException {
    if (null == m_CmdReadyObjArr){
      cbInstance[0] = null;
      pCBData[0] = null;
    }
    else{
      if (null == m_CmdReadyObjArr[0]){
        cbInstance[0] = null;
        pCBData[0] = null;
      }
      else{
        cbInstance[0] = (TMLCmdReadyIF) m_CmdReadyObjArr[0];
        pCBData[0] = m_CmdReadyObjArr[1];
      }
    }
  }
  
  /**
   * @brief get registered progress reply handler and custom data
   * 
   * @param cbInstance array of size 1 containing progress reply handler
   * @param pCBData  array of size 1 - returns custom data
   *  
   * @throws TmlSidexException 
   * @see TMLCmdProgressReplyIF
   */
  public void registeredCmdProgressReply(TMLCmdProgressReplyIF[] cbInstance, Object[] pCBData) throws TmlSidexException{
    if (null == m_CmdProgressObjArr){
      cbInstance[0] = null;
      pCBData[0] = null;
    }
    else{
      if (null == m_CmdProgressObjArr[0]){
        cbInstance[0] = null;
        pCBData[0] = null;
      }
      else{
        cbInstance[0] = (TMLCmdProgressReplyIF) m_CmdProgressObjArr[0];
        pCBData[0] = m_CmdProgressObjArr[1];
      }
    }
  }
  
  /**
   * @brief get registered status reply handler and custom data
   * 
   * @param cbInstance array of size 1 containing status reply handler
   * @param pCBData  array of size 1 - returns custom data
   *  
   * @throws TmlSidexException 
   * @see TMLCmdStatusReplyIF
   */
  public void registeredCmdStatusReply(TMLCmdStatusReplyIF[] cbInstance, Object[] pCBData) throws TmlSidexException{
    if (null == m_CmdStatusObjArr){
      cbInstance[0] = null;
      pCBData[0] = null;
    }
    else{
      if (null == m_CmdStatusObjArr[0]){
        cbInstance[0] = null;
        pCBData[0] = null;
      }
      else{
        cbInstance[0] = (TMLCmdStatusReplyIF)m_CmdStatusObjArr[0];
        pCBData[0] = m_CmdStatusObjArr[1];
      }
    }
  }
}
