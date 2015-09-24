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
import com.tmlsidex.jni.TmlJniSndStreamIF;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup tmlClasses
 * @brief TML stream sender
 *
 * A sender stream provides a data stream to another peer to connect with a TMLRecStream for accessing the data.
 */
public class TMLSndStream extends Object implements TmlJniSndStreamIF{

  // Member
  long       m_StreamID   = 0;
  Tml        m_tml        = null;
  long       m_coreHandle = 0;
  String     m_sProfile   = null;
  TMLProfile m_profile    = null;

  /**
   * @brief stream close handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLSndStreamCloseHandlerIF
   */
  @Override
  public void jniSndStreamCloseCB(long strID, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLSndStreamCloseHandlerIF) objArr[0]).sndStreamClose(strID,  objArr[1]);
    }
  }

  /**
   * @brief stream error handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLSndStreamErrorHandlerIF
   */
  @Override
  public void tmlSndStreamErrorCB(long strID, int iError, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLSndStreamErrorHandlerIF) objArr[0]).sndStreamError(strID,  iError, objArr[1]);
    }
  }

  /**
   * @brief get stream position handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLSndStreamGetPositionHandlerIF
   */
  @Override
  public long tmlSndStreamGetPositionCB(long strID, Object cbData) {
    long lPos = -1;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      lPos = ((TMLSndStreamGetPositionHandlerIF) objArr[0]).sndStreamGetPosition(strID,  objArr[1]);
    }
    return lPos;
  }

  /**
   * @brief get stream size handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLSndStreamGetSizeHandlerIF
   */
  @Override
  public long tmlSndStreamGetSizeCB(long strID, Object cbData) {
    long lPos = -1;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      lPos = ((TMLSndStreamGetSizeHandlerIF) objArr[0]).sndStreamGetSize(strID,  objArr[1]);
    }
    return lPos;
  }

  /**
   * @brief read stream handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLSndStreamReadHandlerIF
   */
  @Override
  public int tmlSndStreamReadCB(long strID, Object cbData, byte[] buffer, int count) {
    int iBytesRead = -1;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      iBytesRead = ((TMLSndStreamReadHandlerIF) objArr[0]).sndStreamRead(strID, objArr[1], buffer, count);
    }
    return iBytesRead;
  }

  /**
   * @brief seek stream handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLSndStreamSeekHandlerIF
   */
  @Override
  public int tmlSndStreamSeekCB(long strID, Object cbData, long seekPosition, int seekOrigin) {
    int iRet = -1;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      iRet = ((TMLSndStreamSeekHandlerIF) objArr[0]).sndStreamSeek(strID, objArr[1], seekPosition, seekOrigin);
    }
    return iRet;
  }

  /**
   * @brief write stream handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLSndStreamWriteHandlerIF
   */
  @Override
  public int tmlSndStreamWriteCB(long strID, byte[] buffer, int count, Object cbData) {
    int iRet = -1;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      iRet = ((TMLSndStreamWriteHandlerIF) objArr[0]).sndStreamWrite(strID, buffer, count, objArr[1]);
    }
    return iRet;
  }
  
  /**
   * @brief constructs a TMLSndStream instance
   * 
   * @param profile  profile instance
   * @param sIP      hostname or IP address 
   * @param sPort    port number 
   * 
   * @throws TmlSidexException 
   */
  public TMLSndStream(TMLProfile profile, String sIP, String sPort) throws TmlSidexException{
    m_tml        = new Tml();
    m_profile    = profile;
    m_coreHandle = m_profile.getCHandle();
    m_sProfile   = m_profile.getProfileUrl();
    
    m_StreamID = m_tml.tml_SndStream_Open(m_coreHandle, m_sProfile, sIP, sPort);
  }

  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    m_tml.tml_SndStream_Close(m_coreHandle, m_StreamID);
    m_tml = null;
    super.finalize();
  }
  
  /**
   * @brief get stream id
   *
   * @return steamID
   */
  public long getStreamID(){
    return m_StreamID;
  }
  
  /**
   * @brief register close stream handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerCloseHandler(TMLSndStreamCloseHandlerIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_SndStream_Register_Close(m_coreHandle, m_StreamID, this, objArr);
    }
    else{
      m_tml.tml_SndStream_Register_Close(m_coreHandle, m_StreamID, null, null);
    }
  }
  
  /**
   * @brief register get stream position handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerGetPositionHandler(TMLSndStreamGetPositionHandlerIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_SndStream_Register_GetPosition(m_coreHandle, m_StreamID, this, objArr);
    }
    else{
      m_tml.tml_SndStream_Register_GetPosition(m_coreHandle, m_StreamID, null, null);
    }
  }
  
  /**
   * @brief register stream error handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerErrorHandler(TMLSndStreamErrorHandlerIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_SndStream_Register_OnError(m_coreHandle, m_StreamID, this, objArr);
    }
    else{
      m_tml.tml_SndStream_Register_OnError(m_coreHandle, m_StreamID, null, null);
    }
  }
  
  /**
   * @brief register get the stream size handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerGetSizeHandler(TMLSndStreamGetSizeHandlerIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_SndStream_Register_GetSize(m_coreHandle, m_StreamID, this, objArr);
    }
    else{
      m_tml.tml_SndStream_Register_GetSize(m_coreHandle, m_StreamID, null, null);
    }
  }
  
  /**
   * @brief register read from stream handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerReadHandler(TMLSndStreamReadHandlerIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_SndStream_Register_Read(m_coreHandle, m_StreamID, this, objArr);
    }
    else{
      m_tml.tml_SndStream_Register_Read(m_coreHandle, m_StreamID, null, null);
    }
  }
  
  /**
   * @brief register seek stream handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerSeekHandler(TMLSndStreamSeekHandlerIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_SndStream_Register_Seek(m_coreHandle, m_StreamID, this, objArr);
    }
    else{
      m_tml.tml_SndStream_Register_Seek(m_coreHandle, m_StreamID, null, null);
    }
  }
  
  /**
   * @brief register write to stream handler
   * 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   *  
   * @throws TmlSidexException 
   */
  public void registerWriteHandler(TMLSndStreamWriteHandlerIF cbInstance, Object pCBData) throws TmlSidexException {
    if (null != cbInstance){
      Object[] objArr = new Object[2];
      objArr[0] = cbInstance;
      objArr[1] = pCBData;
      m_tml.tml_SndStream_Register_Write(m_coreHandle, m_StreamID, this, objArr);
    }
    else{
      m_tml.tml_SndStream_Register_Write(m_coreHandle, m_StreamID, null, null);
    }
  }
}
