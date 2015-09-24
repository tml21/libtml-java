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
import com.tmlsidex.jni.TmlJniCmdDispatchIF;
import com.tmlsidex.jni.TmlJniCustomDispatchIF;
import com.tmlsidex.jni.TmlJniCmdDeleteIF;
import com.tmlsidex.jni.TmlJniBalBusyStatusRequestHandlerIF;
import com.tmlsidex.jni.TmlJniBalCalculationHandlerIF;
import com.tmlsidex.jni.TmlJniBalPopulateHandlerIF;
import com.tmlsidex.jni.TmlJniBalPeerRegisterHandlerIF;
import com.tmlsidex.jni.TmlJniEvtPopulateHandlerIF;
import com.tmlsidex.jni.TmlJniEvtPeerRegisterHandlerIF;
import com.tmlsidex.jni.TmlJniEvtErrorHandlerIF;
import com.tmlsidex.jni.TmlJniEvtQueueOverflowHandlerIF;
import com.tmlsidex.jni.TmlErr;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup tmlClasses
 * @brief TML Profile
 * 
 * Profiles represent different TML interfaces.
 */
public class TMLProfile extends Object implements TmlJniCmdDispatchIF, 
                                                  TmlJniCustomDispatchIF, 
                                                  TmlJniCmdDeleteIF,
                                                  TmlJniBalBusyStatusRequestHandlerIF,
                                                  TmlJniBalCalculationHandlerIF,
                                                  TmlJniBalPopulateHandlerIF,
                                                  TmlJniBalPeerRegisterHandlerIF,
                                                  TmlJniEvtPopulateHandlerIF,
                                                  TmlJniEvtPeerRegisterHandlerIF,
                                                  TmlJniEvtErrorHandlerIF,
                                                  TmlJniEvtQueueOverflowHandlerIF{
  
  // Member
  Tml       m_tml               = null;
  TMLCore   m_core              = null;
  long      m_coreHandle        = 0;
  String    m_sProfile          = null;
  Object[]  m_CmdReadyObjArr    = null;
  Object[]  m_CmdProgressObjArr = null;
  Object[]  m_CmdStatusObjArr   = null;
  

  /**
   * @brief command dispatch handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLCmdDispatchIF
   */
  @Override
  public void jniCmdDispatchCB(long cmdHandle, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      try {
        TMLCmd cmd = new TMLCmd(cmdHandle, false);
        ((TMLCmdDispatchIF) objArr[0]).cmdDispatchCB(cmd, objArr[1]);
      } catch (TmlSidexException e) {
        // Suppress exceptions:
        e.printStackTrace();
      }
    }
  }

  /**
   * @brief custom dispatch handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLCustomDispatchIF
   */
  @Override
  public void jniCustomDispatchCB(int iCmdID, long cmdHandle, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      try {
        TMLCmd cmd = new TMLCmd(cmdHandle, false);
        ((TMLCustomDispatchIF) objArr[0]).customDispatchCB(iCmdID, cmd, objArr[1]);
      } catch (TmlSidexException e) {
        // Suppress exceptions:
        e.printStackTrace();
      }
    }
  }

  /**
   * @brief command delete handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLCmdDeleteIF
   */
  @Override
  public void jniCmdDeleteHandlerCB(int iCmdID, Object cbCmdData, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLCmdDeleteIF) objArr[0]).cmdDeleteHandlerCB(iCmdID, cbCmdData, objArr[1]);
    }
  }

  /**
   * @brief balancing busy status request handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLBalBusyStatusRequestHandlerIF
   */
  @Override
  public int jniBalBusyStatusRequestCB(long cmdHandle, Object cbData) {
    int iRet = TmlErr.TML_ERR_INFORMATION_UNDEFINED;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      try {
        TMLCmd cmd = new TMLCmd(cmdHandle, false);
        iRet = ((TMLBalBusyStatusRequestHandlerIF) objArr[0]).balBusyStatusRequestCB(cmd, objArr[1]);
      } catch (TmlSidexException e) {
        // Suppress exceptions:
        e.printStackTrace();
      }
    }
    return iRet;
  }

  /**
   * @brief calculate next load balancing peer handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLBalCalculationHandlerIF
   */
  @Override
  public int jniBalCalculateNextReceiverCB(int iCountOfDestinations, long[] listenerBusyStateArray, Object cbData, int[] iNextIndex) {
    int iRet = TmlErr.TML_ERR_INFORMATION_UNDEFINED;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      try {
        TMLCmd[] cmdArray = new TMLCmd[iCountOfDestinations];
        for (int i = 0; i < iCountOfDestinations; ++i){
          TMLCmd cmd = new TMLCmd(listenerBusyStateArray[i], false);
          cmdArray[i] = cmd;
        }
        int iNextIdx = ((TMLBalCalculationHandlerIF) objArr[0]).balCalculateNextReceiverCB(iCountOfDestinations, cmdArray, objArr[1]);
        if (-1 < iNextIdx){
          iRet = TmlErr.TML_SUCCESS;
          iNextIndex[0] = iNextIdx;
        }
      } catch (TmlSidexException e) {
        // Suppress exceptions:
        e.printStackTrace();
      }
    }
    return iRet;
  }

  /**
   * @brief populate balancer peers handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLBalPopulateHandlerIF
   */
  @Override
  public int jniBalPopulateCB(String profile, Object cbData) {
    int iRet = TmlErr.TML_SUCCESS;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      iRet = ((TMLBalPopulateHandlerIF) objArr[0]).balPopulateCB(profile, objArr[1]);
    }
    return iRet;
  }


  /**
   * @brief populate event receiver list handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLEvtPopulateHandlerIF
   */
  @Override
  public int jniEvtPopulateCB(String profile, Object cbData) {
    int iRet = TmlErr.TML_SUCCESS;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      iRet = ((TMLEvtPopulateHandlerIF) objArr[0]).evtPopulateCB(profile, objArr[1]);
    }
    return iRet;
  }

  /**
   * @brief register balancing peer handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLBalPeerRegisterHandlerIF
   */
  @Override
  public boolean jniBalPeerRegistrationCB(boolean bSubscribe, String sIP, String sPort, Object cbData) {
    boolean bRet = true;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      bRet = ((TMLBalPeerRegisterHandlerIF) objArr[0]).balPeerRegistrationCB(bSubscribe, sIP, sPort, objArr[1]);
    }
    return bRet;
  }

  /**
   * @brief register event consumer handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLEvtPeerRegisterHandlerIF
   */
  @Override
  public boolean jniEvtPeerRegistrationCB(boolean bSubscribe, String sIP, String sPort, Object cbData) {
    boolean bRet = true;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      bRet = ((TMLEvtPeerRegisterHandlerIF) objArr[0]).evtPeerRegistrationCB(bSubscribe, sIP, sPort, objArr[1]);
    }
    return bRet;
  }

  /**
   * @brief register event error handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLEvtErrorHandlerIF
   */
  @Override
  public void jniEvtErrorCB(String profile, String sIP, String sPort, int iCmdID, int iError, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
       ((TMLEvtErrorHandlerIF) objArr[0]).evtErrorCB(profile, sIP, sPort, iCmdID, iError, objArr[1]);
    }
  }

  /**
   * @brief register event overflow handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLEvtErrorHandlerIF
   */
  @Override
  public void jniEvtQueueOverflowCB(String sProfile, int iCmdID, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLEvtQueueOverflowHandlerIF) objArr[0]).evtQueueOverflowCB(sProfile, iCmdID, objArr[1]);
    }
  }
  
  /**
   * @brief constructs a TMLProfile
   * 
   * @param profile   profile name (ID)
   *  
   * @param core  TML core instance 
   * 
   * @throws TmlSidexException 
   * 
   */
  public TMLProfile(String profile, TMLCore core) throws TmlSidexException{
    m_tml        = new Tml();
    m_sProfile   = profile;
    m_core       = core;
    m_coreHandle = m_core.getCHandle();
    m_tml.tml_Profile_Register (m_coreHandle, m_sProfile);
  }

  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    m_CmdReadyObjArr    = null;
    m_CmdProgressObjArr = null;
    m_CmdStatusObjArr   = null;
    // Internal destructrion:
    if (m_tml.tml_Profile_Get_RegisterState (m_coreHandle, m_sProfile)){
      m_tml.tml_Profile_Unregister (m_coreHandle, m_sProfile);
    }
    m_tml = null;
    // Super destructrion
    super.finalize();
  }
  
  /**
   * @brief get low lwvwl TML core Handle
   *
   * @return TML command variant
   */
  public long getCHandle(){
    return m_coreHandle;
  }

  /**
   * @brief get profile id
   *
   * @return Profile string identifier. 
   */
  public String getProfileUrl(){
    return m_sProfile;
  }
  
  /**
   * @brief register command handler
   * 
   * The command handler is a called if the listerner
   * receives a message with the iCmdID. If no command handler is registered for this id
   * either an error is returned to the caller or the general command handler
   * is called.
   * 
   * @param iCmdID command id
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException
   * @see TMLProfile#registerCustomDispatch(TMLCustomDispatchIF, Object)
   */
  public void registerCmd(int iCmdID, TMLCmdDispatchIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Profile_Register_Cmd(m_coreHandle, m_sProfile, iCmdID, this, objArr);
    }
    else{
      m_tml.tml_Profile_Register_Cmd(m_coreHandle, m_sProfile, iCmdID, null, null);
    }
  }
  
  /**
   * @brief register a general command handler
   * 
   * If no command handler was registered for a command id,
   * the profile calls the general command handler if registered.
   * This can be used to implement an own dispatching mechanism
   * to handle incoming commands and events.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerCustomDispatch(TMLCustomDispatchIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Profile_Set_OnCustomDispatch(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Profile_Set_OnCustomDispatch(m_coreHandle, m_sProfile, null, null);
    }
  }
  
  /**
   * @brief register delete command handler
   *
   * The delete command handler is used to release custon data.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerCmdDeleteHandler(TMLCmdDeleteIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Profile_Set_OnDeleteCmd(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Profile_Set_OnDeleteCmd(m_coreHandle, m_sProfile, null, null);
    }
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
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * @param cmd   TMLCmd object
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException
   * @see TMLCmd#registerCmdReady(TMLCmdReadyIF, Object)
   */
  public void sendAsyncMessage(String sIP, String sPort, TMLCmd cmd, long timeout) throws TmlSidexException{
    registerCmdCallbackHandler(cmd);
    long cmdHandle = cmd.getVHandle();
    m_tml.tml_Send_AsyncMessage(m_coreHandle, cmdHandle, m_sProfile, sIP, sPort, timeout);
  }

  /**
   * @brief send a synchronous message
   *
   * Sending a message synchronously means that the call waits for a reply
   * and returns the result of the message or an error.
   *
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * @param cmd   TMLCmd object
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void sendSyncMessage(String sIP, String sPort, TMLCmd cmd, long timeout) throws TmlSidexException{
    registerCmdCallbackHandler(cmd);
    long cmdHandle = cmd.getVHandle();
    m_tml.tml_Send_SyncMessage(m_coreHandle, cmdHandle, m_sProfile, sIP, sPort, timeout);
  }
  
  /**
   * @brief send an event to all registered event consumers
   * 
   * @param cmd   TMLCmd object
   * 
   * @throws TmlSidexException 
   */
  public void sendEventMessage(TMLCmd cmd) throws TmlSidexException{
    long cmdHandle = cmd.getVHandle();
    m_tml.tml_Evt_Send_Message(m_coreHandle, cmdHandle, m_sProfile);
  }
  
  /**
   * @brief send an event subscription request
   * 
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the event producer 
   * @param sDestPort port number  of the event producer 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void sendEventConsumerSubscription(String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    m_tml.tml_Evt_Send_SubscriptionRequest(m_coreHandle, m_sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
  }
  
  /**
   * @brief unregister an event message receiver
   * 
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the event producer 
   * @param sDestPort port number  of the event producer 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void sendEventConsumerUnsubscription(String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    m_tml.tml_Evt_Send_UnsubscriptionRequest(m_coreHandle, m_sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
  }

  /**
   * @brief send a load balanced asynchronous message
   *
   * This call works like TMLProfile#sendAsyncMessage but sends the command to a peer
   * picked from a group of registered command receivers utilizing the TML load balancing.
   * 
   * @param cmd   TMLCmd object
   * @param iTimeout timeout in milliseconds
   * 
   * @throws TmlSidexException 
   */
  public void sendBalancedAsyncMessage(TMLCmd cmd, long iTimeout) throws TmlSidexException{
    registerCmdCallbackHandler(cmd);
    long cmdHandle = cmd.getVHandle();
    m_tml.tml_Bal_Send_AsyncMessage(m_coreHandle, cmdHandle, m_sProfile, iTimeout);
  }
  
  /**
   * @brief send a load balancing subscription request
   *
   * A subscription as a load balancing peer informs a sender about a new resource to
   * process commands.
   *
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the destination
   * @param sDestPort port number  of the destination 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void sendBalancedReceiverSubscription(String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    m_tml.tml_Bal_Send_SubscriptionRequest(m_coreHandle, m_sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
  }
  
  /**
   * @brief send a load balanced synchronous message
   *
   * This call works like TMLProfile#sendSyncMessage but sends the command to a peer
   * picked from a group of registered command receivers utilizing the TML load balancing.
   * 
   * @param cmd   TMLCmd object
   * @param iTimeout timeout in milliseconds
   * 
   * @throws TmlSidexException 
   */
  public void sendBalancedSyncMessage(TMLCmd cmd, long iTimeout) throws TmlSidexException{
    registerCmdCallbackHandler(cmd);
    long cmdHandle = cmd.getVHandle();
    m_tml.tml_Bal_Send_SyncMessage(m_coreHandle, cmdHandle, m_sProfile, iTimeout);
  }

  /**
   * @brief unregister a load balancing command receiver
   * 
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the destination
   * @param sDestPort port number  of the destination 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void sendBalancedReceiverUnsubscription(String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    m_tml.tml_Bal_Send_UnsubscriptionRequest(m_coreHandle, m_sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
  }
  
  /**
   * @brief add event consumer
   * 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void addEventConsumer(String sIP, String sPort) throws TmlSidexException{
    m_tml.tml_Evt_Subscribe_MessageDestination(m_coreHandle, m_sProfile, sIP, sPort);
  }
  
  /**
   * @brief clear event consumer list
   * 
   * @throws TmlSidexException 
   */
  public void clearEventConsumer() throws TmlSidexException{
    m_tml.tml_Evt_Unsubscribe_All_MessageDestinations(m_coreHandle, m_sProfile);
  }
  
  /**
   * @brief remove event consumer
   * 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void removeEventConsumer(String sIP, String sPort) throws TmlSidexException{
    m_tml.tml_Evt_Unsubscribe_MessageDestination(m_coreHandle, m_sProfile, sIP, sPort);
  }
  
  /**
   * @brief add command receiver for load balancing
   * 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void addBalanceReceiver(String sIP, String sPort) throws TmlSidexException{
    m_tml.tml_Bal_Subscribe_MessageDestination(m_coreHandle, m_sProfile, sIP, sPort);
  }
  
  /**
   * @brief clear load balanced command receiver list
   * 
   * @throws TmlSidexException 
   */
  public void clearBalanceReceiver() throws TmlSidexException{
    m_tml.tml_Bal_Unsubscribe_All_MessageDestinations(m_coreHandle, m_sProfile);
  }
  
  /**
   * @brief remove load balancing command receiver
   * 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void removeBalanceReceiver(String sIP, String sPort) throws TmlSidexException{
    m_tml.tml_Bal_Unsubscribe_MessageDestination(m_coreHandle, m_sProfile, sIP, sPort);
  }

  /**
   * @brief register event error handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerEvtErrorHandler(TMLEvtErrorHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Evt_Set_OnError(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Evt_Set_OnError(m_coreHandle, m_sProfile, null, null);
    }
  }
  
  /**
   * @brief register an event subscription request handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerEvtPeerHandler(TMLEvtPeerRegisterHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Evt_Set_OnPeerRegister(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Evt_Set_OnPeerRegister(m_coreHandle, m_sProfile, null, null);
    }
  }
  
  /**
   * @brief register on populate handler (events)
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException
   */
  public void registerEvtPopulateHandler(TMLEvtPopulateHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Evt_Set_OnPopulate(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Evt_Set_OnPopulate(m_coreHandle, m_sProfile, null, null);
    }
  }
  
  /**
   * @brief register event queue overflow handler
   * 
   * Events are internally queued before they are sent. If the number
   * of queue entries reaches it's maximum the overflow handler is called.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerEvtQueueOverflowHandler(TMLEvtQueueOverflowHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Evt_Set_OnQueueOverflow(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Evt_Set_OnQueueOverflow(m_coreHandle, m_sProfile, null, null);
    }
  }
  
  /**
   * @brief register balancer busy status request handler
   * 
   * To implement a custom load balancing instead of a round robin
   * the status request handler can collect and return host specifc data.
   * The data is added to the TML command passed to the handler function.
   * On sender side the data is collected and passed to a load balance
   * calculation handler that will
   * return the index of the next registered peer to call. If either the busy
   * status request handler or the load balancing calculation handler is not
   * implemented round robin will be used.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException
   * @see TMLProfile#registerBalCalculationHandler(TMLBalCalculationHandlerIF, Object)
   * @see TMLBalCalculationHandlerIF
   */
  public void registerBalBusyStatusRequestHandler(TMLBalBusyStatusRequestHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Bal_Set_OnBusyStatusRequest(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Bal_Set_OnBusyStatusRequest(m_coreHandle, m_sProfile, null, null);
    }
  }
  
  /**
   * @brief register load balancing calculation handler
   * 
   * Evaluate load information returned by the status request handlers of
   * registered command receivers to calculate the index of the next peer
   * to receive a load balanced command.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException
   * @see TMLProfile#registerBalBusyStatusRequestHandler(TMLBalBusyStatusRequestHandlerIF, Object)
   */
  public void registerBalCalculationHandler(TMLBalCalculationHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Bal_Set_OnCalculation(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Bal_Set_OnCalculation(m_coreHandle, m_sProfile, null, null);
    }
  }
  
  /**
   * @brief register load balancing subscription request handler
   * 
   * The handler is called if a load balancing subscription or unsubscription request is received.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerBalPeerHandler(TMLBalPeerRegisterHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Bal_Set_OnPeerRegister(m_coreHandle, m_sProfile, this,objArr);
    }
    else{
      m_tml.tml_Bal_Set_OnPeerRegister(m_coreHandle, m_sProfile, null, null);
    }
  }

  /**
   * @brief register on populate handler (balancer)
   * 
   * If no load balanced command receiver is registered and a load
   * balanced command is requested by TMLProfile#sendSyncMessage() or
   * TMLProfile#sendAsyncMessage(), the OnPopulate handler can register
   * or reregister load balanced command receivers.
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerBalPopulateHandler(TMLBalPopulateHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Bal_Set_OnPopulate(m_coreHandle, m_sProfile, this, objArr);
    }
    else{
      m_tml.tml_Bal_Set_OnPopulate(m_coreHandle, m_sProfile, null, null);
    }
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
