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

import java.nio.ByteBuffer;

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.Tml;
import com.tmlsidex.jni.TmlJniRecStreamDldBlockHandlerIF;
import com.tmlsidex.jni.TmlJniRecStreamDldFinishHandlerIF;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup tmlClasses
 * @brief TML stream receiver
 *
 * The TMLRecStream object provides an interface to a TML receiver stream and its data.
 * It is connected to a sender stream on another peer.
 */
public class TMLRecStream extends Object implements TmlJniRecStreamDldBlockHandlerIF,
                                                    TmlJniRecStreamDldFinishHandlerIF{

  // Member
  long       m_StreamID   = 0;
  Tml        m_tml        = null;
  long       m_coreHandle = 0;
  String     m_sProfile   = null;
  TMLProfile m_profile    = null;

  /**
   * @brief stream download block handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLRecStreamDldBlockHandlerIF
   */
  @Override
  public int jniRecStreamDldBlockCB(long iID, Object cbData, byte[] buffer, int bytesRead, long totalBytesRead, long streamSize) {
    int iRet = -1;
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      iRet = ((TMLRecStreamDldBlockHandlerIF) objArr[0]).recStreamDldBlock(iID,  objArr[1], buffer, bytesRead, totalBytesRead, streamSize);
    }
    return iRet;
  }

  /**
   * @brief stream download finish handler implementation
   *
   * The handler implements the low level interface and calls the
   * the high level handler method.
   *
   * @see com.tmlsidex.tml.TMLRecStreamDldFinishHandlerIF
   */
  @Override
  public void jniRecStreamDldFinishCB(long iID, int iError, Object cbData) {
    Object[] objArr = (Object[]) cbData;
    
    if (null != objArr[0]){
      ((TMLRecStreamDldFinishHandlerIF) objArr[0]).recStreamDldFinish(iID, iError, objArr[1]);
    }
  }
  
  /**
   * @brief constructs a TMLRecStream instance
   * 
   * @param profile  profile instance
   * @param streamID clear stream identification 
   * @param sIP      hostname or IP address 
   * @param sPort    port number 
   * 
   * @throws TmlSidexException 
   */
  public TMLRecStream(TMLProfile profile, long streamID, String sIP, String sPort) throws TmlSidexException{
    m_tml        = new Tml();
    m_StreamID   = streamID;
    m_profile    = profile;
    m_coreHandle = m_profile.getCHandle();
    m_sProfile   = m_profile.getProfileUrl();
    
    m_tml.tml_RecStream_Open(m_coreHandle, m_StreamID, m_sProfile, sIP, sPort);
  }

  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    m_tml.tml_RecStream_Close(m_coreHandle, m_StreamID, false);
    m_tml = null;
    super.finalize();
  }
  
  /**
   * @brief get stream identification
   *
   * @return steamID
   */
  public long getStreamID(){
    return m_StreamID;
  }
  
  /**
   * @brief get stream position
   * 
   * @return actual stream position
   * 
   * @throws TmlSidexException 
   */
  public long getPosition() throws TmlSidexException{
    return m_tml.tml_RecStream_GetPosition(m_coreHandle, m_StreamID);
  }
  
  /**
   * @brief get stream size in bytes
   * 
   * @return stream size
   * 
   * @throws TmlSidexException 
   */
  public long getSize() throws TmlSidexException{
    return m_tml.tml_RecStream_GetSize(m_coreHandle, m_StreamID);
  }
  
  /**
   * @brief read a buffer from the stream
   * 
   * @param buffer returns the read content
   * @param count number of bytes to read
   *  
   * @return number of bytes read (can be smaller than count if stream ends)
   * 
   * @throws TmlSidexException 
   */
  public int read(ByteBuffer buffer, int count) throws TmlSidexException{
    return m_tml.tml_RecStream_Read(m_coreHandle, m_StreamID, buffer, count);
  }

  /**
   * @brief move to stream position (seek)
   * 
   * @param seekPos   position in stream 
   * @param origin    offset origin
   * 
   * <br><br>soFromBeginning (0) / from start of stream / seekPos > 0 
   * <br>soFromCurrent (1)  / from current position / 
   * <br>soFromEnd (2)  / from end of stream / seekPos < 0
   * 
   * @throws TmlSidexException 
   */
  public void seek(long seekPos, int origin) throws TmlSidexException{
    m_tml.tml_RecStream_Seek(m_coreHandle, m_StreamID, seekPos, origin);
  }
  
  /**
   * @brief write data to a stream
   * 
   * @param buffer buffer containing data
   * @param count number of bytes to write
   *  
   * @throws TmlSidexException 
   */
  public void write(byte[] buffer, int count) throws TmlSidexException{
    m_tml.tml_RecStream_Write(m_coreHandle, m_StreamID, buffer, count);
  }
  
  /**
   * @brief start a full download of a stream
   * 
   * @param buffersize  size of download buffer 
   * @param cbDldBlockInstance instance of an implementation of the download block callback method interface
   * @param pCBDataDld  custom data or null / buffer reception handler 
   * @param cbDldFinishInstance  instance of an implementation of the download finish callback method interface 
   * @param pCBDataDldFinish  custom data or null / download finished handler 
   *  
   * @throws TmlSidexException 
   */
  public void downloadData(int buffersize, TMLRecStreamDldBlockHandlerIF cbDldBlockInstance, Object pCBDataDld,
                                           TMLRecStreamDldFinishHandlerIF cbDldFinishInstance, Object pCBDataDldFinish) throws TmlSidexException{
    if (null != cbDldBlockInstance && null != cbDldFinishInstance){
      Object[] objArrDld = new Object[2];
      objArrDld[0] = cbDldBlockInstance;
      objArrDld[1] = pCBDataDld;
      
      Object[] objArrDldFinish = new Object[2];
      objArrDldFinish[0] = cbDldFinishInstance;
      objArrDldFinish[1] = pCBDataDldFinish;
      m_tml.tml_RecStream_DownloadData(m_coreHandle, m_StreamID, buffersize, 
                                                                  this, objArrDld, 
                                                                  this, objArrDldFinish);
    }
  }
}
