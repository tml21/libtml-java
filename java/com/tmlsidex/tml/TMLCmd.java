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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.Tml;
import com.tmlsidex.jni.TmlJniCmdProgressReplyIF;
import com.tmlsidex.jni.TmlJniCmdStatusReplyIF;
import com.tmlsidex.jni.TmlJniCmdReadyIF;
import com.tmlsidex.jni.TmlSidexException;
import com.tmlsidex.sidex.SDXDocument;


/**
 * @ingroup tmlClasses
 * @brief The TMLCmd object provides an interface to a TML command and its data.
 */
public class TMLCmd extends Object implements TmlJniCmdProgressReplyIF,
                                              TmlJniCmdStatusReplyIF,
                                              TmlJniCmdReadyIF
{

  // Member
  long                  m_var    = 0;
  Tml                   m_tml    = null;
  boolean               m_bThisOwn = false;
  
  /**
   * @brief command progress handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see TMLCmdProgressReplyIF
   */
  @Override
  public void jniCmdProgressReplyCB(long cmdHandle, Object cbData, int progress) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLCmdProgressReplyIF) objArr[0]).cmdProgressReplyCB(this, objArr[1], progress);
    }
  }
   
  /**
   * @brief command status reply handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLCmdStatusReplyIF
   */
  @Override
  public void jniCmdStatusReplyCB(long cmdHandle, Object cbData, int type, String msg) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLCmdStatusReplyIF) objArr[0]).cmdStatusReplyCB(this, objArr[1], type, msg);
    }
  }
 
  /**
   * @brief command ready handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLCmdReadyIF
   */
  @Override
  public void jniCmdReadyCB(long cmdHandle, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLCmdReadyIF) objArr[0]).cmdReadyCB(this, objArr[1]);
    }
  }
   
  /**
   * @brief constructor of TMLCmd
   * 
   * @param iCmdID command id
   * 
   * @throws TmlSidexException 
   */
  public TMLCmd(int iCmdID) throws TmlSidexException{
    m_tml      = new Tml();
    m_bThisOwn = true;
    m_var      = m_tml.tml_Cmd_Create();
    
    m_tml.tml_Cmd_Header_SetCommand(m_var, iCmdID);
  }

  /**
   * @brief constructor of TMLCmd
   * 
   * @param iCmdID command id
   * @param cbInstance instance of an implementation of the callback method interface / all TMLCmd callback registrations will be done for the instance
   * @param pCBData  custom data or null for the callback registrations
   * 
   * @throws TmlSidexException 
   * @see TMLCmd#registerCmdReady(TMLCmdReadyIF, Object)
   * @see TMLCmd#registerCmdProgressReply(TMLCmdProgressReplyIF, Object)
   * @see TMLCmd#registerCmdStatusReply(TMLCmdStatusReplyIF, Object)

   */
  public TMLCmd(int iCmdID, TMLCmdIF cbInstance, Object pCBData) throws TmlSidexException{
    m_tml      = new Tml();
    m_bThisOwn = true;
    m_var      = m_tml.tml_Cmd_Create();
    
    m_tml.tml_Cmd_Header_SetCommand(m_var, iCmdID);
    registerCmdReady(cbInstance, pCBData);
    registerCmdProgressReply(cbInstance, pCBData);
    registerCmdStatusReply(cbInstance, pCBData);
  }

  /**
   * @brief constructor of TMLCmd
   * 
   * @param vHandle handle of a TML command variant
   * @param thisown instance is owner of the TML command handle 
   *
   * @throws TmlSidexException 
   */
  public TMLCmd(long vHandle, boolean thisown) throws TmlSidexException{
    m_tml    = new Tml();
    m_bThisOwn = thisown;
    m_var = vHandle;
  }

  /**
   * @brief destructor of TMLCmd
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    if (m_bThisOwn && 0 != m_var){
      m_tml.tml_Cmd_Free(m_var);
      m_var = 0;
    }
    m_tml = null;
    m_var = 0;
    super.finalize();
  }
  
  /**
   * @brief get low level command handle
   *
   * @return TML command variant
   */
  public long getVHandle(){
    return m_var;
  }
  
  /**
   * @brief set command id
   * 
   * @param cmdID   command id
   *
   * @throws TmlSidexException 
   */
  public void setCmdID(int cmdID) throws TmlSidexException{
    m_tml.tml_Cmd_Header_SetCommand(m_var,  cmdID);
  }
  
  /**
   * @brief get the command id
   * 
   * @return command id
   * 
   * @throws TmlSidexException 
   */
  public int getCmdID() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetCommand(m_var);
  }
  
  /**
   * @brief get command creation time
   *  
   * @return  command creation time
   *
   * @throws TmlSidexException 
   */
  public Date getCreationTime() throws TmlSidexException{
    Date currentParsed = null;

    String sDate = m_tml.tml_Cmd_Header_GetCreationTime(m_var);    DateFormat df = null;
    try {
      df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
      currentParsed = df.parse(sDate);
    } catch (ParseException e) {
      df = null;
      e.printStackTrace();
      TmlSidexException tmlExcept = new TmlSidexException("TMLCmd::getCreationTime / parsing error", SidexErr.SIDEX_ERR_INVALID_DATETIME);
      throw tmlExcept;
    }
    df = null;
    return currentParsed;
  }
  
  /**
   * @brief set error code
   * 
   * If a custom error code is set while command processing it
   * will not be overwritten by TML specific standard error codes.
   * 
   * @param error  command execution error code
   * 
   * @throws TmlSidexException 
   */
  public void setError(int error) throws TmlSidexException{
    m_tml.tml_Cmd_Header_SetError(m_var,  error);
  }
  
  /**
   * @brief get error code
   * 
   * There are a number of predefined error codes that are set automatically.
   * Besides that a custom error code can be set while processing a command to inform
   * the caller about implementation specific problems.
   * 
   * @return command execution error code
   *
   * @throws TmlSidexException 
   */
  public int getError() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetError(m_var);
  }

  /**
   * @brief set error message
   * 
   * If a custom error message is set while command processing
   * it will not be overwritten by TML specific standard error messages.
   * 
   * @param msg command execution error message
   * 
   * @throws TmlSidexException 
   */
  public void setErrorMessage(int error, String msg) throws TmlSidexException{
    m_tml.tml_Cmd_Header_SetErrorMessage(m_var,  msg);
  }
  
  /**
   * @brief get error message
   * 
   * @return command execution error message
   *
   * @throws TmlSidexException 
   */
  public String getErrorMessage() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetErrorMessage(m_var);
  }
  
  /**
   * @brief get execution mode
   * 
   * @return execution mode
   *
   * @throws TmlSidexException
   * @see Tml#tml_Cmd_Header_GetMode(long)
   */
  public int getMode() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetMode(m_var);
  }
  
  /**
   * @brief set progress
   * 
   * @param progress  progress value (0-100)
   * 
   * @throws TmlSidexException 
   */
  public void setProgress(int progress) throws TmlSidexException{
    m_tml.tml_Cmd_Header_SetProgress(m_var,  progress);
  }
  
  /**
   * @brief get progress
   * 
   * If a progress reply is received, the command execution state is
   * TMLCOM_CSTATE_PENDING and the reply type is TMLCOM_RPY_PROGRESS.
   * In that case a progress is valid. Progress is also valid if
   * a progress was explicitly set.
   *
   * @return command execution progress (in percent).
   *
   * @throws TmlSidexException 
   */
  public int getProgress() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetProgress(m_var);
  }
  
  /**
   * @brief get status reply message
   *  
   * If a status reply is received, the command execution state is
   * TMLCOM_CSTATE_PENDING and the reply type is TMLCOM_RPY_WARNING,
   * TMLCOM_RPY_ERROR or TMLCOM_RPY_INFORMATION.
   * In that case a reply message is valid. A reply message is also
   * valid if a reply message was explicitly set.
   * 
   * @return reply message. 
   *
   * @throws TmlSidexException 
   */
  public String getReplyMessage() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetReplyMessage(m_var);
  }
  
  /**
   * @brief get reply type
   * 
   * If a progress or status reply is received, the command execution state is
   * TMLCOM_CSTATE_PENDING. In that case the reply type can be used to
   * determine the kind of reply.
   *  
   * @return reply type
   *
   * @throws TmlSidexException
   * @see Tml#tml_Cmd_Header_GetReplyType(long)
   */
  public int getReplyType() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetReplyType(m_var);
  }
  
  /**
   * @brief get execution state
   * 
   * @return execution state
   * 
   * @throws TmlSidexException
   * @see Tml#tml_Cmd_Header_GetState(long)
   */
  public int getState() throws TmlSidexException{
    return m_tml.tml_Cmd_Header_GetState(m_var);
  }
  
  /**
   * @brief acquire command data (SDXDocument)
   *
   * @note This is a lock mechanism to protect the message data from changes by
   * other threads while accessed. If the call to TMLCmd#releaseData() is missing,
   * a deadlock situation may be the result.
   * 
   * @return   SDXDocument
   * 
   * @throws TmlSidexException
   * @see TMLCmd#releaseData()
   */
  public SDXDocument acquireData() throws TmlSidexException{
    long sHandle =  m_tml.tml_Cmd_Acquire_Sidex_Handle(m_var);
    SDXDocument doc = new SDXDocument(sHandle, false);
    return doc;
  }
  
  /**
   * @brief release command data
   * 
   * @throws TmlSidexException
   * @see TMLCmd#acquireData()
   */
  public void releaseData() throws TmlSidexException{
    m_tml.tml_Cmd_Release_Sidex_Handle(m_var);
  }
  
  /**
   * @brief send status reply
   *  
   * @param rType  status reply type 
   * 
   * @param rMsg   status reply message
   * 
   * @throws TmlSidexException
   * @see Tml#tml_Send_AsyncStatusReply(long , int , String)
   */
  public void sendStatusReply(int rType, String rMsg) throws TmlSidexException{
    m_tml.tml_Send_AsyncStatusReply(m_var, rType, rMsg);
  }
  
  /**
   * @brief send progress reply
   *  
   * @param progress  progress value (0-100)
   * 
   * @throws TmlSidexException 
   */
  public void sendProgressReply(int progress) throws TmlSidexException{
    m_tml.tml_Send_AsyncProgressReply(m_var, progress);
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
   */
  public void registerCmdReady(TMLCmdReadyIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Cmd_Register_CommandReady(m_var, this, objArr);
    }
    else{
      m_tml.tml_Cmd_Register_CommandReady(m_var, null, null);
    }
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
   */
  public void registerCmdProgressReply(TMLCmdProgressReplyIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Cmd_Register_Progress(m_var, this, objArr);
    }
    else{
      m_tml.tml_Cmd_Register_Progress(m_var, null, null);
    }
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
   */
  public void registerCmdStatusReply(TMLCmdStatusReplyIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_Cmd_Register_StatusReply(m_var, this, objArr);
    }
    else{
      m_tml.tml_Cmd_Register_StatusReply(m_var, null, null);
    }
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
    Object[] objArrInstance = new Object[2];
    Object[] objArrData = new Object[2];
    m_tml.tml_Cmd_Registered_CommandReady(m_var, objArrInstance, objArrData);
    if (null != objArrInstance[0]){
      Object[] objArr = (Object[]) objArrData[0];
      cbInstance[0] = (TMLCmdReadyIF)objArr[0];
      pCBData[0] = objArr[1];
    }
    else{
      cbInstance[0] = null;
      pCBData[0] = null;
    }
    objArrInstance = null;
    objArrData = null;
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
    Object[] objArrInstance = new Object[2];
    Object[] objArrData = new Object[2];
    m_tml.tml_Cmd_Registered_Progress(m_var, objArrInstance, objArrData);
    if (null != objArrInstance[0]){
      Object[] objArr = (Object[]) objArrData[0];
      cbInstance[0] = (TMLCmdProgressReplyIF)objArr[0];
      pCBData[0] = objArr[1];
    }
    else{
      cbInstance[0] = null;
      pCBData[0] = null;
    }
    objArrInstance = null;
    objArrData = null;
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
    Object[] objArrInstance = new Object[2];
    Object[] objArrData = new Object[2];
    m_tml.tml_Cmd_Registered_StatusReply(m_var, objArrInstance, objArrData);
    if (null != objArrInstance[0]){
      Object[] objArr = (Object[]) objArrData[0];
      cbInstance[0] = (TMLCmdStatusReplyIF)objArr[0];
      pCBData[0] = objArr[1];
    }
    else{
      cbInstance[0] = null;
      pCBData[0] = null;
    }
    objArrInstance = null;
    objArrData = null;
  }
}
