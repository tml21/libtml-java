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

/** @defgroup tmlGroup TML API
 *  @ingroup basicAPI
 *  @brief Description of TML API functions.
 */

/** @ingroup tmlGroup
 *  @defgroup tmlReturn TML return codes
 *  @brief TML return codes
 */

/** @ingroup tmlGroup
 *  @defgroup coreGeneral General functions
 *  @brief General library functions
 */

/** @ingroup tmlGroup
 *  @defgroup coreHandle TMLCore
 *  @brief TMLCore functions
 *
 *  All communications in TML is related to a TMLCore object. At least one
 *  TMLCore must exist before any TML communication is possible.
 */

/** @ingroup coreHandle
 * @defgroup coreManagement Listener management
 * @brief TMLCore listener management
 *
 * A TMLCore listener must be started and initialized to enable incoming traffic.
 */

/** @ingroup coreHandle
 * @defgroup dispatching Profile management
 * @brief TMLCore profile
 *
 * A profile is linked to a TMLCore and diapatches incoming traffic to command handling functions.
 */

/** @ingroup coreHandle
 * @defgroup dataIO Sending commands
 * @brief Using the TMLCore to send commands.
 */

/** @ingroup coreHandle
 * @defgroup eventIO Event handling
 * @brief Sending of events to multiple destinations.
 *
 * Sending event messages to multiple peers without a reply.
 */

/** @ingroup coreHandle
 * @defgroup loadbalancedIO Load balancing
 * @brief Balance command calls.
 *
 * For backup and load balancing TML provides an API to call a group of peers instead of a specific peer to
 * process a command. If one peer fails the remaining will still handle the command.
 */

/** @ingroup tmlGroup
 *  @defgroup tmlHandle TML commands
 *  @brief Creating and handling of TML commands.
 *
 *  TML commands contain data and header information. The data can be set and read using
 *  the SIDEX API. All SIDEX datatypes can be used to be exchanged through a TML command call.
 */

/** @ingroup tmlHandle
 *  @defgroup tmlCmdCallbacks Command message callbacks
 *  @brief Callback methods for multiple answers and asynchronous reply handling.
 *
 *  If commands are called asynchronously or status/progress replies are used, callbacks can
 *  be registered for a command to handle this information.
 */

/** @ingroup tmlHandle
 *  @defgroup tmlheader Accessing header information
 *  @brief Functions to access command header information.
 */

/** @ingroup coreHandle
 * @defgroup streamIO Stream communication
 * @brief Using TML streams.
 *
 * The stream API enables TML to transfer files without using another port or protocol.
 */

/** @ingroup tmlGroup
 *  @defgroup tmlCallback TML callback interfaces
 *  @brief Definition of callback interfaces used in TML API.
 *
 *  TML API is using various callback interfaces to support asynchronous and threaded functionality.
 */

package com.tmlsidex.jni;

import java.nio.ByteBuffer;

/**
 * Native TML API functions.
 **/

public class Tml {

  private static final int TML_SUCCESS = 0;
  
  ///////////////////////
  // General:
  private native void   NativeTmlCoreGetCopyright(StringBuffer sValue, int[] iLength);
  private native int    NativeTmlCoreGetLoggingValue(long coreHandle, int[] iLogValue);
  private native void   NativeTmlCoreGetVersion(int[] apiVer, int[] libVer, StringBuffer verStr);
  private native int    NativeTmlCoreSetLoggingValue(long coreHandle, int iLogValue);
  private native int    NativeTmlCoreSetPassword(String pUserName, String pPassWord);
  ///////////////////////
  // TMLCore:
  private native int    NativeTmlCoreClose(long coreHandle);
  private native int    NativeTmlCoreGeneralDeregistration(long coreHandle);
  private native int    NativeTmlCoreOpen(long[] coreHandle, int iLogValue);
  
  ///////////////////////////////////////////////
  //TMLCore / Listener management
  private native int    NativeTmlCoreGetListenerEnabled(long coreHandle, boolean[] bEnable);
  private native int    NativeTmlCoreGetListenerIP(long coreHandle, StringBuffer sIP);
  private native int    NativeTmlCoreGetListenerPort(long coreHandle, StringBuffer sPort);
  private native int    NativeTmlCoreSetListenerEnabled(long coreHandle, boolean bEnable);
  private native int    NativeTmlCoreSetListenerIP(long coreHandle, String sIP);
  private native int    NativeTmlCoreSetListenerPort(long coreHandle, String sPort);
  
  ///////////////////////////////////////////////
  //TMLCore / Profile management
  private native int    NativeTmlProfileGetRegistered(long coreHandle, long[] profiles);
  private native int    NativeTmlProfileGetRegisteredCount(long coreHandle, int[] iSize);
  private native int    NativeTmlProfileGetRegisterState(long coreHandle, String profile, boolean[] bRegistered);
  private native int    NativeTmlProfileRegister(long coreHandle, String profile);
  private native int    NativeTmlProfileRegisterCmd(long coreHandle, String profile, int cmdID, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlProfileSetOnCustomDispatch(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlProfileSetOnDeleteCmd(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlProfileUnregister(long coreHandle, String profile);

  ///////////////////////////////////////////////
  //TMLCore / Sending commands
  private native int    NativeTmlSendAsyncMessage(long coreHandle, long cmdHandle, String sProfile, String sIP, String sPort, long timeout);
  private native int    NativeTmlSendAsyncProgressReply(long cmdHandle, int progress);
  private native int    NativeTmlSendAsyncStatusReply(long cmdHandle, int iType, String sStatus);
  private native int    NativeTmlSendSyncMessage(long coreHandle, long cmdHandle, String sProfile, String sIP, String sPort, long timeout);

  ///////////////////////////////////////////////
  //TMLCore / Event handling
  private native int    NativeTmlEvtGetMaxConnectionFailCount(long coreHandle, int[] iCount);
  private native int    NativeTmlEvtGetMaxQueuedEventMessages(long coreHandle, int[] iMaximum);
  private native int    NativeTmlEvtGetSubscribedMessageDestinations(long coreHandle, String sProfile, long[] subscriptions);
  private native int    NativeTmlEvtSendMessage(long coreHandle, long cmdHandle, String sProfile);
  private native int    NativeTmlEvtSendSubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout);
  private native int    NativeTmlEvtSendUnsubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout);
  private native int    NativeTmlEvtSetMaxConnectionFailCount(long coreHandle, int iCount);
  private native int    NativeTmlEvtSetMaxQueuedEventMessages(long coreHandle, int iMaximum);
  private native int    NativeTmlEvtSetOnError(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlEvtSetOnPeerRegister(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlEvtSetOnPopulate(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlEvtSetOnQueueOverflow(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlEvtSubscribeMessageDestination(long coreHandle, String sProfile, String sHost, String sPort);
  private native int    NativeTmlEvtUnsubscribeAllMessageDestinations(long coreHandle, String sProfile);
  private native int    NativeTmlEvtUnsubscribeMessageDestination(long coreHandle, String sProfile, String sHost, String sPort);

  ///////////////////////////////////////////////
  //TMLCore / Load balancing
  private native int    NativeTmlBalGetMaxConnectionFailCount(long coreHandle, int[] iCount);
  private native int    NativeTmlBalGetSubscribedMessageDestinations(long coreHandle, String sProfile, long[] subscriptions);
  private native int    NativeTmlBalSendAsyncMessage(long coreHandle, long cmdHandle, String sProfile, long iTimeout);
  private native int    NativeTmlBalSendSubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout);
  private native int    NativeTmlBalSendSyncMessage(long coreHandle, long cmdHandle, String sProfile, long iTimeout);
  private native int    NativeTmlBalSendUnsubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout);
  private native int    NativeTmlBalSetMaxConnectionFailCount(long coreHandle, int iCount);
  private native int    NativeTmlBalSetOnBusyStatusRequest(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlBalSetOnCalculation(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlBalSetOnPeerRegister(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlBalSetOnPopulate(long coreHandle, String profile, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlBalSubscribeMessageDestination(long coreHandle, String sProfile, String sHost, String sPort);
  private native int    NativeTmlBalUnsubscribeAllMessageDestinations(long coreHandle, String sProfile);
  private native int    NativeTmlBalUnsubscribeMessageDestination(long coreHandle, String sProfile, String sHost, String sPort);
  
  ///////////////////////////////////////////////
  //TMLCore / Stream communication
  private native int    NativeTmlRecStreamClose(long coreHandle, long iID, boolean bRetainOpen);
  private native int    NativeTmlRecStreamDownloadData(long coreHandle, long iID, int buffersize,
                                                       Object callerClassDld, String sCBNameDld, Object pCBDataDld,
                                                       Object callerClassFinish, String sCBNameFinish, Object pCBDataFinish);
  private native int    NativeTmlRecStreamGetPosition(long coreHandle, long iID, long[] rposition);
  private native int    NativeTmlRecStreamGetSize(long coreHandle, long iID, long[] rsize);
  private native int    NativeTmlRecStreamOpen(long coreHandle, long iID, String sProfile, String sIP, String sPort);
  private native int    NativeTmlRecStreamRead(long coreHandle, long iID, ByteBuffer buffer, int count, int[] bytesRead);
  private native int    NativeTmlRecStreamReadBuffer(long coreHandle, long iID, ByteBuffer buffer, int count);
  private native int    NativeTmlRecStreamSeek(long coreHandle, long iID, long seekPos, int origin);
  private native int    NativeTmlRecStreamWrite(long coreHandle, long iID, byte[] buffer, int count);
  private native int    NativeTmlSndStreamClose(long coreHandle, long iID);
  private native int    NativeTmlSndStreamOpen(long coreHandle, long[] iID, String sProfile, String sIP, String sPort);
  private native int    NativeTmlSndStreamRegisterClose(long coreHandle, long iID, Object callerClass, String sCBName, Object pCBData);  
  private native int    NativeTmlSndStreamRegisterGetPosition(long coreHandle, long iID, Object callerClass, String sCBName, Object pCBData);  
  private native int    NativeTmlSndStreamRegisterGetSize(long coreHandle, long iID, Object callerClass, String sCBName, Object pCBData);  
  private native int    NativeTmlSndStreamRegisterOnError(long coreHandle, long iID, Object callerClass, String sCBName, Object pCBData);  
  private native int    NativeTmlSndStreamRegisterRead(long coreHandle, long iID, Object callerClass, String sCBName, Object pCBData);  
  private native int    NativeTmlSndStreamRegisterSeek(long coreHandle, long iID, Object callerClass, String sCBName, Object pCBData);  
  private native int    NativeTmlSndStreamRegisterWrite(long coreHandle, long iID, Object callerClass, String sCBName, Object pCBData);  
  
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  ///////////////////////
  // TML commands:
  private native int    NativeTmlCmdAcquireSidexHandle(long cmdHandle, long[] sidexHandle);
  private native int    NativeTmlCmdCreate(long[] cmdHandle);
  private native int    NativeTmlCmdFree(long cmdHandle);
  private native int    NativeTmlCmdReleaseSidexHandle(long cmdHandle);
  
  ///////////////////////////////////////////////
  //TML commands / Command message callbacks
  private native int    NativeTmlCmdRegisterCommandReady(long cmdHandle, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlCmdRegisterProgress(long cmdHandle, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlCmdRegisterStatusReply(long cmdHandle, Object callerClass, String sCBName, Object pCBData);
  private native int    NativeTmlCmdRegisteredCommandReady(long cmdHandle, Object[] callerClass, Object sCBName, Object[] pCBData);
  private native int    NativeTmlCmdRegisteredStatusReply(long cmdHandle, Object[] callerClass, Object sCBName, Object[] pCBData);
  private native int    NativeTmlCmdRegisteredProgress(long cmdHandle, Object[] callerClass, Object sCBName, Object[] pCBData);

  ///////////////////////////////////////////////
  //TML commands / Accessing header information
  private native int    NativeTmlCmdHeaderGetCommand(long cmdHandle, int[] cmdID);
  private native int    NativeTmlCmdHeaderGetCreationTime(long cmdHandle, StringBuffer time);
  private native int    NativeTmlCmdHeaderGetError(long cmdHandle, int[] error);
  private native int    NativeTmlCmdHeaderGetErrorMessage(long cmdHandle, StringBuffer msg);
  private native int    NativeTmlCmdHeaderGetMode(long cmdHandle, int[] mode);
  private native int    NativeTmlCmdHeaderGetProgress(long cmdHandle, int[] progress);
  private native int    NativeTmlCmdHeaderGetReplyMessage(long cmdHandle, StringBuffer msg);
  private native int    NativeTmlCmdHeaderGetReplyType(long cmdHandle, int[] type);
  private native int    NativeTmlCmdHeaderGetState(long cmdHandle, int[] state);
  private native int    NativeTmlCmdHeaderSetCommand(long cmdHandle, int cmdID);
  private native int    NativeTmlCmdHeaderSetError(long cmdHandle, int error);
  private native int    NativeTmlCmdHeaderSetErrorMessage(long cmdHandle, String msg);
  private native int    NativeTmlCmdHeaderSetMode(long cmdHandle, int mode);
  private native int    NativeTmlCmdHeaderSetProgress(long cmdHandle, int progress);
  private native int    NativeTmlCmdHeaderSetReplyMessage(long cmdHandle, String msg);
  private native int    NativeTmlCmdHeaderSetReplyType(long cmdHandle, int type);
  private native int    NativeTmlCmdHeaderSetState(long cmdHandle, int state);

  ///////////////////////
  // General:

  /**
   * @addtogroup coreGeneral
   * @{
   */

  /**
   * @brief get copyright information
   *
   * @return copyright information
   */
  public String tml_Core_Get_Copyright(){
    int[] iLength = new int[1];
    StringBuffer buffer = new StringBuffer("");
    NativeTmlCoreGetCopyright(buffer, iLength);
    String sRet = buffer.toString();
    buffer = null;
    iLength = null;
    return sRet;
  }
  
  /**
   * @brief get interface api and library version
   *
   * @param iApiVer   array of size 1 - returns api version
   * @param iLibVer   array of size 1 - returns library version
   * 
   * @return library version as string
   */
  public String tml_Core_Get_Version(int[] iApiVer, int[] iLibVer){
    StringBuffer buffer = new StringBuffer("");
    NativeTmlCoreGetVersion(iApiVer, iLibVer, buffer);
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief get the license key
   * 
   * Before the sdk functions can be used a license key must be applied.
   *
   * @note Since TML/SIDEX is open source this API call is deprecated. It returns allways SIDEX_SUCCESS.
   *       It has been left in the API to be backward compatible.
   *
   * @param pUserName user name (case sensitive)
   * @param pPassWord password (case insensitive)
   */
  public void tml_Core_Set_Password(String pUserName, String pPassWord) throws TmlSidexException{
    int iRet =  NativeTmlCoreSetPassword(pUserName, pPassWord);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("tml_Core_Set_Password / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   *  @brief set debug log value 
   * 
   *  @param coreHandle  TML core handle (TML_CORE_HANDLE)
   *  @param iLogValue debug log value
   */
  public void tml_Core_Set_LoggingValue(long coreHandle, int iLogValue) throws TmlSidexException{
    int iRet = NativeTmlCoreSetLoggingValue(coreHandle, iLogValue);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Core_Set_LoggingValue / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   *  @brief get debug log value 
   * 
   *  @param coreHandle  TML core handle (TML_CORE_HANDLE)
   *  
   *  @return  current debug log value
   */
  public int tml_Core_Get_LoggingValue(long coreHandle) throws TmlSidexException{
    int[] iLogValue = new int[1];
    int iRet = NativeTmlCoreGetLoggingValue(coreHandle, iLogValue);
    if (TML_SUCCESS != iRet){
      iLogValue = null;
      throw new TmlSidexException("tml_Core_Get_LoggingValue / Error "+Integer.toString(iRet), iRet);
    }
    int iRetVal = iLogValue[0];
    iLogValue = null;
    return iRetVal;
  }

  /* @} */

  ///////////////////////
  // TMLCore:

  /**
   * @addtogroup coreHandle
   * @{
   */

  /**
   * @brief release a TML core handle
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Core_Close(long coreHandle) throws TmlSidexException{
    int iRet = NativeTmlCoreClose(coreHandle);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Core_Close / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief clear registration of all callback functions
   * 
   * Deregister all callbacks for a safe shutdown.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Core_GeneralDeregistration(long coreHandle) throws TmlSidexException{
    int iRet = NativeTmlCoreGeneralDeregistration(coreHandle);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Core_GeneralDeregistration / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief open a new TML core handle
   *
   * @param iLogValue debug log value
   * 
   * @return  TML core handle (TML_CORE_HANDLE).
   *
   * @throws TmlSidexException
   * @see Tml#tml_Core_Close(long)
   */
  public long tml_Core_Open(int iLogValue) throws TmlSidexException{
    long[] coreHandle = new long[1];
    int iRet = NativeTmlCoreOpen(coreHandle, iLogValue);
    if (TML_SUCCESS != iRet){
      coreHandle = null;
      throw new TmlSidexException("tml_Core_Open / Error "+Integer.toString(iRet), iRet);
    }
    long retValue = coreHandle[0];
    coreHandle = null;
    return retValue;
  }
  /* @} */

  ///////////////////////////////////////////////
  //TMLCore / Listener management

  /**
   * @addtogroup coreManagement
   * @{
   */

  /**
   * @brief get listener status
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * 
   * @return  listener status, true if listener is enabled, false if listener is disabled
   *
   * @throws TmlSidexException 
   */
  public boolean tml_Core_Get_ListenerEnabled(long coreHandle) throws TmlSidexException{
    boolean[] value = new boolean[1];
    int iRet = NativeTmlCoreGetListenerEnabled(coreHandle, value);
    if (TML_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("tml_Core_Get_ListenerEnabled / Error "+Integer.toString(iRet), iRet);
    }
    boolean bValue = value[0];
    return bValue;
  }
  
  /**
   * @brief get the listener interface (IP)
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * 
   * @return interface IP
   *
   * @throws TmlSidexException 
   */
  public String tml_Core_Get_ListenerIP(long coreHandle) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeTmlCoreGetListenerIP(coreHandle, buffer);
    if (TML_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("tml_Core_Get_ListenerIP / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief get listener port number
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * 
   * @return port number
   *
   * @throws TmlSidexException 
   */
  public String tml_Core_Get_ListenerPort(long coreHandle) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeTmlCoreGetListenerPort(coreHandle, buffer);
    if (TML_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("tml_Core_Get_ListenerPort / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief enable or disable TML listener
   *
   * The interface IP and port have to be configured before enabling the listener.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param bEnable  true to enable, false to disable the listener
   *
   * @throws TmlSidexException 
   */
  public void tml_Core_Set_ListenerEnabled(long coreHandle, boolean bEnable) throws TmlSidexException{
    int iRet = NativeTmlCoreSetListenerEnabled(coreHandle, bEnable);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Core_Set_ListenerEnabled / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set the listener interface (IP)
   *
   * The listener can be assigned to a specific network interface or to all network interfaces
   * available ("0.0.0.0) which is the default.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sIP  interface IP
   *
   * @throws TmlSidexException 
   */
  public void tml_Core_Set_ListenerIP(long coreHandle, String sIP) throws TmlSidexException{
    int iRet = NativeTmlCoreSetListenerIP(coreHandle, sIP);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Core_Set_ListenerIP / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set listener port
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param  sPort number, 0 = random port number, default 44000
   *
   * @throws TmlSidexException 
   */
  public void tml_Core_Set_ListenerPort(long coreHandle, String sPort) throws TmlSidexException{
    int iRet = NativeTmlCoreSetListenerPort(coreHandle, sPort);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Core_Set_ListenerPort / Error "+Integer.toString(iRet), iRet);
    }
  }

  /* @} */

  ///////////////////////////////////////////////
  //TMLCore / Profile management

  /**
   * @addtogroup dispatching
   * @{
   */

  /**
   * @brief get a list of registered profile names
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   *  
   * @return A SIDEX list containing profile names
   * 
   * @throws TmlSidexException 
   */
  public long tml_Profile_Get_Registered(long coreHandle) throws TmlSidexException{
    long[] profiles = new long[1];
    int iRet = NativeTmlProfileGetRegistered(coreHandle, profiles);
    if (TML_SUCCESS != iRet){
      profiles = null;
      throw new TmlSidexException("tml_Profile_Get_Registered / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = profiles[0];
    profiles = null;
    return lRet;
  }
  
  /**
   * @brief get the number of registered profiles
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   *  
   * @return number of registered profiles
   * 
   * @throws TmlSidexException 
   */
  public int tml_Profile_Get_Registered_Count(long coreHandle) throws TmlSidexException{
    int[] iSize = new int[1];
    int iRet = NativeTmlProfileGetRegisteredCount(coreHandle, iSize);
    if (TML_SUCCESS != iRet){
      iSize = null;
      throw new TmlSidexException("tml_Profile_Get_Registered_Count / Error "+Integer.toString(iRet), iRet);
    }
    int iCount = iSize[0];
    iSize = null;
    return iCount;
  }
  
  /**
   * @brief check if a profile name is already registered
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   *  
   * @return true if the profile is registered
   * 
   * @throws TmlSidexException 
   */
  public boolean tml_Profile_Get_RegisterState(long coreHandle, String profile) throws TmlSidexException{
    boolean[] bRegistered = new boolean[1];
    int iRet = NativeTmlProfileGetRegisterState(coreHandle, profile, bRegistered);
    if (TML_SUCCESS != iRet){
      bRegistered = null;
      throw new TmlSidexException("tml_Profile_Get_RegisterState / Error "+Integer.toString(iRet), iRet);
    }
    boolean bRet = bRegistered[0];
    bRegistered = null;
    return bRet;
  }
  
  /**
   * @brief register a profile
   * 
   * Before any other operation referring to a profile is accepted
   * by the TML core, the profile has to be registered.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   *  
   * @throws TmlSidexException 
   */
  public void tml_Profile_Register(long coreHandle, String profile) throws TmlSidexException{
    int iRet = NativeTmlProfileRegister(coreHandle, profile);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Profile_Register / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register a command handler
   * 
   * The command handler is a called if the listerner receives a message
   * with a matching iCmdID. If no command handler is registered either an error is returned
   * to the caller or the general command handler is called if registered.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param iCmdID command id
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException
   */
  public void tml_Profile_Register_Cmd(long coreHandle, String profile, int iCmdID, TmlJniCmdDispatchIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlProfileRegisterCmd(coreHandle, profile, iCmdID, cbInstance, TmlJniCmdDispatchIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Profile_Register_Cmd / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register a general command handler
   * 
   * If no command handler was registered for a command id,
   * the profile will call the general command handler if registered.
   * This can be used to implement an own dispatching mechanism to handle incoming commands and events.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Profile_Set_OnCustomDispatch(long coreHandle, String profile, TmlJniCustomDispatchIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlProfileSetOnCustomDispatch(coreHandle, profile, cbInstance, TmlJniCustomDispatchIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Profile_Set_OnCustomDispatch / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register a callback to release custom data assigned to a command handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Profile_Set_OnDeleteCmd(long coreHandle, String profile, TmlJniCmdDeleteIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlProfileSetOnDeleteCmd(coreHandle, profile, cbInstance, TmlJniCmdDeleteIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Profile_Set_OnDeleteCmd / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief deregister a profile
   * 
   * All command handlers registered for the profile will be removed from the TML core.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   *  
   * @throws TmlSidexException 
   */
  public void tml_Profile_Unregister(long coreHandle, String profile) throws TmlSidexException{
    int iRet = NativeTmlProfileUnregister(coreHandle, profile);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Profile_Unregister / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */

  ///////////////////////////////////////////////
  //TMLCore / Sending commands

  /**
   * @addtogroup dataIO
   * @{
   */

  /**
   * @brief send an asynchronous message
   * 
   * The call returns after sending the message without waiting for a reply.
   * If a result of the call is processed by the CommandReady handler.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param cmdHandle  TML command handle 
   * @param sProfile  profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException
   * @see Tml#tml_Cmd_Register_CommandReady(long, TmlJniCmdReadyIF, Object)
   */
  public void tml_Send_AsyncMessage(long coreHandle, long cmdHandle, String sProfile, String sIP, String sPort, long timeout) throws TmlSidexException{
    int iRet = NativeTmlSendAsyncMessage(coreHandle, cmdHandle, sProfile, sIP, sPort, timeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Send_AsyncMessage / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief send a progress reply to the caller
   *  
   * @param cmdHandle  TML command handle 
   * @param progress  progress value (0-100)
   * 
   * @throws TmlSidexException 
   */
  public void tml_Send_AsyncProgressReply(long cmdHandle, int progress) throws TmlSidexException{
    int iRet = NativeTmlSendAsyncProgressReply(cmdHandle, progress);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Send_AsyncProgressReply / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief send a status reply to the caller
   *
   * Status reply types:
   *  <TABLE>
   *   <TR><TD><B>Name       </B></TD><TD><B>Value </B></TD><TD><B>Description  </B></TD></TR>
   *   <TR><TD>TMLCOM_RPY_WARNING       </TD><TD>10</TD><TD>warning message reply</TD></TR>
   *   <TR><TD>TMLCOM_RPY_ERROR         </TD><TD>20</TD><TD>error message reply</TD></TR>
   *   <TR><TD>TMLCOM_RPY_INFORMATION   </TD><TD>30</TD><TD>information message reply</TD></TR>
   *  </TABLE>
   *
   * @param cmdHandle  TML command handle 
   * @param rType  status reply type 
   * @param rMsg   status reply message
   * 
   * @throws TmlSidexException 
   */
  public void tml_Send_AsyncStatusReply(long cmdHandle, int rType, String rMsg) throws TmlSidexException{
    int iRet = NativeTmlSendAsyncStatusReply(cmdHandle, rType, rMsg);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Send_AsyncStatusReply / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief send a synchronous message
   * 
   * Sending a message synchronously means that the call is waiting for the reply.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param cmdHandle  TML command handle 
   * @param sProfile  profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void tml_Send_SyncMessage(long coreHandle, long cmdHandle, String sProfile, String sIP, String sPort, long timeout) throws TmlSidexException{
    int iRet = NativeTmlSendSyncMessage(coreHandle, cmdHandle, sProfile, sIP, sPort, timeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Send_SyncMessage / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */

  ///////////////////////////////////////////////
  //TMLCore / Event handling

  /**
   * @addtogroup eventIO
   * @{
   */

  /**
   * @brief get maximum event connection fail count
   * 
   * The maximum connection fail count defines after how many unsuccessful connections an event
   * consumer is automatically removed from the list. A value of 0 means it is never removed.
   * The default value is 1.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   *
   * @return  maximum fail count
   * 
   * @throws TmlSidexException 
   */
  public int tml_Evt_Get_MaxConnectionFailCount(long coreHandle) throws TmlSidexException{
    int[] iCount = new int[1];
    int iRet = NativeTmlEvtGetMaxConnectionFailCount(coreHandle, iCount);
    if (TML_SUCCESS != iRet){
      iCount = null;
      throw new TmlSidexException("tml_Evt_Get_MaxConnectionFailCount / Error "+Integer.toString(iRet), iRet);
    }
    int iVal = iCount[0];
    iCount = null;
    return iVal;
  }
  
  /**
   * @brief get the maximum number of queued event
   * 
   * If the maximum number of queued events is reached, the oldest event
   * is deleted. The default value is 1000.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   *
   * @return  maximum queue count
   * 
   * @throws TmlSidexException 
   */
  public int tml_Evt_Get_MaxQueuedEventMessages(long coreHandle) throws TmlSidexException{
    int[] iMaximum = new int[1];
    int iRet = NativeTmlEvtGetMaxQueuedEventMessages(coreHandle, iMaximum);
    if (TML_SUCCESS != iRet){
      iMaximum = null;
      throw new TmlSidexException("tml_Evt_Get_MaxQueuedEventMessages / Error "+Integer.toString(iRet), iRet);
    }
    int iVal = iMaximum[0];
    return iVal;
  }

  /**
   * @brief get event receiver list
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name
   *  
   * @return    reference to a SIDEX table with event receiver data.
   *            The table has to be released with sidex_Variant_DecRef().
   *            Table columns are "PROFILE", "HOST" and "PORT".
   * 
   * @throws TmlSidexException 
   */
  public long tml_Evt_Get_Subscribed_MessageDestinations(long coreHandle, String sProfile) throws TmlSidexException{
    long[] subscriptions = new long[1];
    int iRet = NativeTmlEvtGetSubscribedMessageDestinations(coreHandle, sProfile, subscriptions);
    if (TML_SUCCESS != iRet){
      subscriptions = null;
      throw new TmlSidexException("tml_Evt_Get_Subscribed_MessageDestinations / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = subscriptions[0];
    subscriptions = null;
    return lRet;
  }
  
  /**
   * @brief send an event to all registered receivers
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param cmdHandle  TML command handle 
   * @param sProfile  profile name 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Evt_Send_Message(long coreHandle, long cmdHandle, String sProfile) throws TmlSidexException{
    int iRet = NativeTmlEvtSendMessage(coreHandle, cmdHandle, sProfile);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Send_Message / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief send an event subscription request
   * 
   * Register a peer to receive events of a specifc profile from an event producer.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the event producer 
   * @param sDestPort port number  of the event producer 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void tml_Evt_Send_SubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    int iRet = NativeTmlEvtSendSubscriptionRequest(coreHandle, sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Send_SubscriptionRequest / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief unsubscribe a peer from receiving events
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the event producer 
   * @param sDestPort port number  of the event producer 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException
   * @see Tml#tml_Evt_Send_SubscriptionRequest(long, String, String, String, String, String, long)
   */
  public void tml_Evt_Send_UnsubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    int iRet = NativeTmlEvtSendUnsubscriptionRequest(coreHandle, sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Send_UnsubscriptionRequest / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set the maximum event connection fail count
   * 
   * The maximum connection fail count defines after how many unsuccessful connections
   * an event consumer is automatically removed from the list. A value of 0 means it is never removed.
   * The default value is 1
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iCount    maximum fail count 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Evt_Set_MaxConnectionFailCount(long coreHandle, int iCount) throws TmlSidexException{
    int iRet = NativeTmlEvtSetMaxConnectionFailCount(coreHandle, iCount);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Set_MaxConnectionFailCount / Error "+Integer.toString(iRet), iRet);
    }
  }
    
  /**
   * @brief set the maximum number of queued events.
   * 
   * If the maximum number of queued events is reached, the oldest event will be removed.
   * The default value is 1000.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iMaximum maximum queue count
   * 
   * @throws TmlSidexException 
   */
  public void tml_Evt_Set_MaxQueuedEventMessages(long coreHandle, int iMaximum) throws TmlSidexException{
    int iRet = NativeTmlEvtSetMaxQueuedEventMessages(coreHandle, iMaximum);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Set_MaxQueuedEventMessages / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register an event error handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Evt_Set_OnError(long coreHandle, String profile, TmlJniEvtErrorHandlerIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlEvtSetOnError(coreHandle, profile, cbInstance, TmlJniEvtErrorHandlerIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Set_OnError / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register a handler for event subscription requests
   * 
   * The handler is called if an event subscription or unsubscription request is received.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Evt_Set_OnPeerRegister(long coreHandle, String profile, TmlJniEvtPeerRegisterHandlerIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlEvtSetOnPeerRegister(coreHandle, profile, cbInstance, TmlJniEvtPeerRegisterHandlerIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Set_OnPeerRegister / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register on populate handler (events).
   * 
   * If no event receiver is registered and an event is added to the queue
   * by calling tml_Evt_Send_Message(), the OnPopulate handler can register or reregister event receivers.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Evt_Set_OnPopulate(long coreHandle, String profile, TmlJniEvtPopulateHandlerIF  cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlEvtSetOnPopulate(coreHandle, profile, cbInstance, TmlJniEvtPopulateHandlerIF.CB_NAME,  cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Set_OnPopulate / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register an event queue overflow handler
   * 
   * Events are internally queued before they are sent.
   * If the number of queue entries reaches it's maximum the overflow handler is called.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Evt_Set_OnQueueOverflow(long coreHandle, String profile, TmlJniEvtQueueOverflowHandlerIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlEvtSetOnQueueOverflow(coreHandle, profile, cbInstance, TmlJniEvtQueueOverflowHandlerIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Set_OnQueueOverflow / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief subscribe event receiver
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Evt_Subscribe_MessageDestination(long coreHandle, String sProfile, String sIP, String sPort) throws TmlSidexException{
    int iRet = NativeTmlEvtSubscribeMessageDestination(coreHandle, sProfile, sIP, sPort);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Subscribe_MessageDestination / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief unsubscribe all event receivers
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Evt_Unsubscribe_All_MessageDestinations(long coreHandle, String sProfile) throws TmlSidexException{
    int iRet = NativeTmlEvtUnsubscribeAllMessageDestinations(coreHandle, sProfile);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Unsubscribe_All_MessageDestinations / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief unsubscribe event receiver
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Evt_Unsubscribe_MessageDestination(long coreHandle, String sProfile, String sIP, String sPort) throws TmlSidexException{
    int iRet = NativeTmlEvtUnsubscribeMessageDestination(coreHandle, sProfile, sIP, sPort);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Evt_Unsubscribe_MessageDestination / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */

  ///////////////////////////////////////////////
  //TMLCore / Load balancing

  /**
   * @addtogroup loadbalancedIO
   * @{
   */

  /**
   * @brief get the maximum load balancing connection fail count
   * 
   * The maximum connection fail count defines after how many unsuccessful
   * tries to connect, an event consumer is automatically removed from the list.
   * A value of 0 means it is never removed.
   * The default value is 1.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   *
   * @return  maximum fail count
   * 
   * @throws TmlSidexException 
   */
  public int tml_Bal_Get_MaxConnectionFailCount(long coreHandle) throws TmlSidexException{
    int[] iCount = new int[1];
    int iRet = NativeTmlBalGetMaxConnectionFailCount(coreHandle, iCount);
    if (TML_SUCCESS != iRet){
      iCount = null;
      throw new TmlSidexException("tml_Bal_Get_MaxConnectionFailCount / Error "+Integer.toString(iRet), iRet);
    }
    int iVal = iCount[0];
    return iVal;
  }
  
  /**
   * @brief get load balanced command receiver list
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name
   *  
   * @return    reference to a SIDEX table with command receiver data.
   *            The table has to be released with sidex_Variant_DecRef().
   *            Table columns are "PROFILE", "HOST" and "PORT".
   * 
   * @throws TmlSidexException 
   */
  public long tml_Bal_Get_Subscribed_MessageDestinations(long coreHandle, String sProfile) throws TmlSidexException{
    long[] subscriptions = new long[1];
    int iRet = NativeTmlBalGetSubscribedMessageDestinations(coreHandle, sProfile, subscriptions);
    if (TML_SUCCESS != iRet){
      subscriptions = null;
      throw new TmlSidexException("tml_Bal_Get_Subscribed_MessageDestinations / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = subscriptions[0];
    subscriptions = null;
    return lRet;
  }
  
  /**
   * @brief send a load balanced asynchronous message
   * 
   * Send an asynchronous message to a member of a group of registered
   * command receivers using the TML load balancing.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param cmdHandle  TML command handle 
   * @param sProfile  profile name 
   * @param iTimeout timeout in milliseconds
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Send_AsyncMessage(long coreHandle, long cmdHandle, String sProfile, long iTimeout) throws TmlSidexException{
    int iRet = NativeTmlBalSendAsyncMessage(coreHandle, cmdHandle, sProfile, iTimeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Send_AsyncMessage / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief send a load balancing subscription request
   * 
   * Subscribe to receive load balanced messages.
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the destination
   * @param sDestPort port number  of the destination 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Send_SubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    int iRet = NativeTmlBalSendSubscriptionRequest(coreHandle, sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Send_SubscriptionRequest / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief send a load balanced synchronous message
   *
   * Send an synchronous message to a member of a group of registered
   * command receivers using the TML load balancing.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param cmdHandle  TML command handle 
   * @param sProfile  profile name 
   * @param iTimeout timeout in milliseconds
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Send_SyncMessage(long coreHandle, long cmdHandle, String sProfile, long iTimeout) throws TmlSidexException{
    int iRet = NativeTmlBalSendSyncMessage(coreHandle, cmdHandle, sProfile, iTimeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Send_SyncMessage / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief unregister a load balancing message receiver.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sSourceHost host name / IP addresse of the subscriber
   * @param sSourcePort port number of the subscriber 
   * @param sDestHost host name / IP addresse of the destination
   * @param sDestPort port number  of the destination 
   * @param timeout   timeout in milliseconds  
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Send_UnsubscriptionRequest(long coreHandle, String sProfile, String sSourceHost, String sSourcePort, String sDestHost, String sDestPort, long timeout) throws TmlSidexException{
    int iRet = NativeTmlBalSendUnsubscriptionRequest(coreHandle, sProfile, sSourceHost, sSourcePort, sDestHost, sDestPort, timeout);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Send_UnsubscriptionRequest / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set the maximum load balancing connection fail count
   * 
   * The maximum connection fail count defines after how many unsuccessful tries to connect, a is automatically
   * removed from the list. A value of 0 means it is never removed. The default value is 1
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iCount    maximum fail count 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Set_MaxConnectionFailCount(long coreHandle, int iCount) throws TmlSidexException{
    int iRet = NativeTmlBalSetMaxConnectionFailCount(coreHandle, iCount);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Set_MaxConnectionFailCount / Error "+Integer.toString(iRet), iRet);
    }
  }
    
  /**
   * @brief register a busy status request handler
   * 
   * To implement a custom load balancing instead of a
   * round robin, the status request handler can collect and return host
   * specifc data. The data is added to the TML command passed to the handler
   * function. On sender side the data is collected and passed to a load balance
   * calculation handler to determine the
   * index of the next registered peer to call. If either the busy status request handler
   * or the load balancing calculation handler is not implemented round robin is used.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException
   * @see Tml#tml_Bal_Set_OnCalculation(long, String, TmlJniBalCalculationHandlerIF, Object)
   */
  public void tml_Bal_Set_OnBusyStatusRequest(long coreHandle, String profile, TmlJniBalBusyStatusRequestHandlerIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlBalSetOnBusyStatusRequest(coreHandle, profile, cbInstance, TmlJniBalBusyStatusRequestHandlerIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Set_OnBusyStatusRequest / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register load balancing calculation handler
   * 
   * Evaluate load information returned by the status request handlers
   * of registered command receivers to calculate the index of the next peer
   * to receive a load balanced message.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Bal_Set_OnCalculation(long coreHandle, String profile, TmlJniBalCalculationHandlerIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlBalSetOnCalculation(coreHandle, profile, cbInstance, TmlJniBalCalculationHandlerIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Set_OnCalculation / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register handler for load balancing subscription requests
   * 
   * The handler is called if a load balancing subscription or unsubscription request is received.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Bal_Set_OnPeerRegister(long coreHandle, String profile, TmlJniBalPeerRegisterHandlerIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlBalSetOnPeerRegister(coreHandle, profile, cbInstance, TmlJniBalPeerRegisterHandlerIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Set_OnPeerRegister / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register on populate handler (balancer)
   * 
   * If no message receivers are registered for load balancing and a load balanced command is
   * send, the OnPopulate handler can register or reregister peers to receive the request.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param profile profile name 
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param cbData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException
   * @see Tml#tml_Bal_Send_SyncMessage(long, long, String, long)
   */
  public void tml_Bal_Set_OnPopulate(long coreHandle, String profile, TmlJniBalPopulateHandlerIF cbInstance, Object cbData) throws TmlSidexException{
    int iRet = NativeTmlBalSetOnPopulate(coreHandle, profile, cbInstance, TmlJniBalPopulateHandlerIF.CB_NAME, cbData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Set_OnPopulate / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief add a command receiver for load balanced calls
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Subscribe_MessageDestination(long coreHandle, String sProfile, String sIP, String sPort) throws TmlSidexException{
    int iRet = NativeTmlBalSubscribeMessageDestination(coreHandle, sProfile, sIP, sPort);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Subscribe_MessageDestination / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief clear command receiver list
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Unsubscribe_All_MessageDestinations(long coreHandle, String sProfile) throws TmlSidexException{
    int iRet = NativeTmlBalUnsubscribeAllMessageDestinations(coreHandle, sProfile);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Unsubscribe_All_MessageDestinations / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief unsubscribe a command receiver
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile  profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void tml_Bal_Unsubscribe_MessageDestination(long coreHandle, String sProfile, String sIP, String sPort) throws TmlSidexException{
    int iRet = NativeTmlBalUnsubscribeMessageDestination(coreHandle, sProfile, sIP, sPort);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Bal_Unsubscribe_MessageDestination / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */

  ///////////////////////////////////////////////
  //TMLCore / Stream communication

  /**
   * @addtogroup streamIO
   * @{
   */

  /**
   * @brief close a receiver stream
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification 
   * @param bRetainOpen If this value is true the stream can be opened again with a call to tml_RecStream_Open()
   * 
   * @throws TmlSidexException 
   */
  public void tml_RecStream_Close(long coreHandle, long iID, boolean bRetainOpen) throws TmlSidexException{
    int iRet = NativeTmlRecStreamClose(coreHandle, iID, bRetainOpen);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_RecStream_Close / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief start a full download of a stream
   * 
   * To download a full stream two handler functions are used.
   * One to handle the data buffer by buffer and one to finish the download.
   * Data is passed to both handlers referring to object instances or records
   * used by the download. Depending on buffer and stream size the handlers are
   * automatically called to handle the download. A call to this function returns
   * after the download is finished or an error occurred.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param buffersize  size of download buffer 
   * @param cbInstanceDld instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBDataDld  custom data or null / buffer reception handler 
   * @param cbInstanceDldFinish instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBDataFinish  custom data or null / download finished handler 
   * 
   * @throws TmlSidexException 
   */
  public void tml_RecStream_DownloadData(long coreHandle, long iID, int buffersize, 
                                         TmlJniRecStreamDldBlockHandlerIF cbInstanceDld, Object pCBDataDld,
                                         TmlJniRecStreamDldFinishHandlerIF cbInstanceDldFinish, Object pCBDataFinish) throws TmlSidexException{
    int iRet = NativeTmlRecStreamDownloadData(coreHandle, iID, buffersize, 
                                              cbInstanceDld, TmlJniRecStreamDldBlockHandlerIF.CB_NAME, pCBDataDld,
                                              cbInstanceDldFinish, TmlJniRecStreamDldFinishHandlerIF.CB_NAME, pCBDataFinish);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_RecStream_DownloadData / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get stream position
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   *  
   * @return actual stream position
   * 
   * @throws TmlSidexException 
   */
  public long tml_RecStream_GetPosition(long coreHandle, long iID) throws TmlSidexException{
    long[] rposition = new long[1];
    int iRet = NativeTmlRecStreamGetPosition(coreHandle, iID, rposition);
    if (TML_SUCCESS != iRet){
      rposition = null;
      throw new TmlSidexException("tml_RecStream_GetPosition / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = rposition[0];
    rposition = null;
    return lRet;
  }
  
  /**
   * @brief get stream size in bytes
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   *  
   * @return stream size
   * 
   * @throws TmlSidexException 
   */
  public long tml_RecStream_GetSize(long coreHandle, long iID) throws TmlSidexException{
    long[] rsize = new long[1];
    int iRet = NativeTmlRecStreamGetSize(coreHandle, iID, rsize);
    if (TML_SUCCESS != iRet){
      rsize = null;
      throw new TmlSidexException("tml_RecStream_GetSize / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = rsize[0];
    rsize = null;
    return lRet;
  }
  
  /**
   * @bief open a receiver stream
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification 
   * @param sProfile profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @throws TmlSidexException 
   */
  public void tml_RecStream_Open(long coreHandle, long iID, String sProfile, String sIP, String sPort) throws TmlSidexException{
    int iRet = NativeTmlRecStreamOpen(coreHandle, iID, sProfile, sIP, sPort);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_RecStream_Open / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief read a buffer from the stream
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param buffer returns the read content
   * @param count number of bytes to read
   *  
   * @return number of bytes read (can be smaller than count if stream ends)
   * 
   * @throws TmlSidexException 
   */
  public int tml_RecStream_Read(long coreHandle, long iID, ByteBuffer buffer, int count) throws TmlSidexException{
    int[] bytesRead = new int[1];
    int iRet = NativeTmlRecStreamRead(coreHandle, iID, buffer, count, bytesRead);
    if (TML_SUCCESS != iRet){
      bytesRead = null;
      throw new TmlSidexException("tml_RecStream_Read / Error "+Integer.toString(iRet), iRet);
    }
    int iRetVal = bytesRead[0];
    bytesRead = null;
    return iRetVal;
  }

  /**
   * @brief read a buffer from the stream
   * 
   * This function reads the given number of bytes from a stream.
   * If the stream ends before all bytes are read, an exception will be thrown.
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param buffer returns the read content
   * @param count number of bytes to read
   *  
   * @throws TmlSidexException
   * @see Tml#tml_RecStream_Read(long, long, ByteBuffer, int)
   */
  public void tml_RecStream_ReadBuffer(long coreHandle, long iID, ByteBuffer buffer, int count) throws TmlSidexException{
    int iRet = NativeTmlRecStreamReadBuffer(coreHandle, iID, buffer, count);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_RecStream_ReadBuffer / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief move to stream position (seek)
   *
   * Values for offset origin are:
   *  <TABLE>
   *   <TR><TD><B>Name       </B></TD><TD><B>Value</B></TD><TD><B>Description  </B></TD></TR>
   *   <TR><TD>soFromBeginning       </TD><TD>0</TD><TD>from start of stream (>0)</TD></TR>
   *   <TR><TD>soFromCurrent         </TD><TD>1</TD><TD>from current position</TD></TR>
   *   <TR><TD>soFromEnd             </TD><TD>2</TD><TD>from end of stream (<0)</TD></TR>
   *  </TABLE>
   *
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param seekPos   position in stream 
   * @param origin    offset origin
   * 
   * @throws TmlSidexException 
   */
  public void tml_RecStream_Seek(long coreHandle, long iID, long seekPos, int origin) throws TmlSidexException{
    int iRet = NativeTmlRecStreamSeek(coreHandle, iID, seekPos, origin);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_RecStream_Seek / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief write data to a stream
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param buffer buffer containing data
   * @param count number of bytes to write
   *  
   * @throws TmlSidexException 
   */
  public void tml_RecStream_Write(long coreHandle, long iID, byte[] buffer, int count) throws TmlSidexException{
    int iRet = NativeTmlRecStreamWrite(coreHandle, iID, buffer, count);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_RecStream_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief close a sender stream
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification 
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Close(long coreHandle, long iID) throws TmlSidexException{
    int iRet = NativeTmlSndStreamClose(coreHandle, iID);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Close / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief open a sender stream
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param sProfile profile name 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * 
   * @return clear stream id to be used in all stream operations
   * 
   * @throws TmlSidexException 
   */
  public long tml_SndStream_Open(long coreHandle, String sProfile, String sIP, String sPort) throws TmlSidexException{
    long[] iID = new long[1];
    int iRet = NativeTmlSndStreamOpen(coreHandle, iID, sProfile, sIP, sPort);
    if (TML_SUCCESS != iRet){
      iID = null;
      throw new TmlSidexException("tml_SndStream_Open / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = iID[0];
    iID = null;
    return lRet;
  }

  /**
   * @brief register close stream handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Register_Close(long coreHandle, long iID, TmlJniSndStreamCloseHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlSndStreamRegisterClose(coreHandle, iID, cbInstance, TmlJniSndStreamCloseHandlerIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Register_Close / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register get position handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Register_GetPosition(long coreHandle, long iID, TmlJniSndStreamGetPositionHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlSndStreamRegisterGetPosition(coreHandle, iID, cbInstance, TmlJniSndStreamGetPositionHandlerIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Register_GetPosition / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register get stream size handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Register_GetSize(long coreHandle, long iID, TmlJniSndStreamGetSizeHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlSndStreamRegisterGetSize(coreHandle, iID, cbInstance, TmlJniSndStreamGetSizeHandlerIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Register_GetSize / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register streaming error handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Register_OnError(long coreHandle, long iID, TmlJniSndStreamErrorHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlSndStreamRegisterOnError(coreHandle, iID, cbInstance, TmlJniSndStreamErrorHandlerIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Register_OnError / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register read stream handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Register_Read(long coreHandle, long iID, TmlJniSndStreamReadHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlSndStreamRegisterRead(coreHandle, iID, cbInstance, TmlJniSndStreamReadHandlerIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Register_Read / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register seek stream handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Register_Seek(long coreHandle, long iID, TmlJniSndStreamSeekHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlSndStreamRegisterSeek(coreHandle, iID, cbInstance, TmlJniSndStreamSeekHandlerIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Register_Seek / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register write stream handler
   * 
   * @param coreHandle  TML core handle (TML_CORE_HANDLE) 
   * @param iID   clear stream identification
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null
   * 
   * @throws TmlSidexException 
   */
  public void tml_SndStream_Register_Write(long coreHandle, long iID, TmlJniSndStreamWriteHandlerIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlSndStreamRegisterWrite(coreHandle, iID, cbInstance, TmlJniSndStreamWriteHandlerIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_SndStream_Register_Write / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */

  // TML commands:
  /**
   * @addtogroup tmlHandle
   * @{
   */

  /**
   * @brief acquire command data (SIDEX_HANDLE)
   * 
   * A TML command has it's own SIDEX document to transport data that has to be locked before
   * safely accessed and/or changed (see SIDEX documentation) by a specific thread.
   * The lock has to be removed after accessing the data with tml_Cmd_Release_Sidex_Handle().
   *
   * @param   cmdHandle TML command handle (TML_COMMAND_HANDLE)
   * 
   * @return   SIDEX_HANDLE
   * 
   * @throws TmlSidexException
   * @see Tml#tml_Cmd_Release_Sidex_Handle(long)
   */
  public long tml_Cmd_Acquire_Sidex_Handle(long cmdHandle) throws TmlSidexException{
    long[] sidexHandle = new long[1];
    int iRet = NativeTmlCmdAcquireSidexHandle(cmdHandle, sidexHandle);
    if (TML_SUCCESS != iRet){
      sidexHandle = null;
      throw new TmlSidexException("tml_Cmd_Acquire_Sidex_Handle / Error "+Integer.toString(iRet), iRet);
    }
    long sHandle = sidexHandle[0];
    sidexHandle = null;
    return sHandle;
  }
  
  /**
   * @brief create a TML command.
   *  
   * @return  a TML command handle (TML_COMMAND_HANDLE)
   * 
   * @throws TmlSidexException 
   */
  public long tml_Cmd_Create() throws TmlSidexException{
    long[] cmdHandle = new long[1];
    int iRet = NativeTmlCmdCreate(cmdHandle);
    if (TML_SUCCESS != iRet){
      cmdHandle = null;
      throw new TmlSidexException("tml_Cmd_Create / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = cmdHandle[0];
    cmdHandle = null;
    return lRet;
  }
  
  /**
   * @brief release TML command handle
   *  
   * @param cmdHandle   TML command handle (TML_COMMAND_HANDLE)
   * 
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Free(long cmdHandle) throws TmlSidexException{
    int iRet = NativeTmlCmdFree(cmdHandle);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Free / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief release TML command data lock
   *  
   * @param cmdHandle   TML command handle (TML_COMMAND_HANDLE)
   * 
   * @throws TmlSidexException
   * @see Tml#tml_Cmd_Acquire_Sidex_Handle(long)
   */
  public void tml_Cmd_Release_Sidex_Handle(long cmdHandle) throws TmlSidexException{
    int iRet = NativeTmlCmdReleaseSidexHandle(cmdHandle);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Release_Sidex_Handle / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */

  ///////////////////////////////////////////////
  //TML commands / Command message callbacks

  /**
   * @addtogroup tmlCmdCallbacks
   * @{
   */

  /**
   * @brief register command ready handler
   * 
   * The result of a command is passed to a command ready handler. In case of asynchronous commands
   * this is the only way to process the reply.
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Register_CommandReady(long cmdHandle, TmlJniCmdReadyIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlCmdRegisterCommandReady(cmdHandle, cbInstance, TmlJniCmdReadyIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Register_CommandReady / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief register a progress reply handler
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null / set null for deregistration
   * 
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Register_Progress(long cmdHandle, TmlJniCmdProgressReplyIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlCmdRegisterProgress(cmdHandle, cbInstance, TmlJniCmdProgressReplyIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Register_Progress / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief register a status reply handler
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param cbInstance instance of an implementation of the callback method interface  / set null for deregistration
   * @param pCBData  custom data or null / set null for deregistration
   *  
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Register_StatusReply(long cmdHandle, TmlJniCmdStatusReplyIF cbInstance, Object pCBData) throws TmlSidexException{
    int iRet = NativeTmlCmdRegisterStatusReply(cmdHandle, cbInstance, TmlJniCmdStatusReplyIF.CB_NAME, pCBData);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Register_StatusReply / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get registered command ready handler and custom data
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param cbInstance array of size 1 containing handler (TmlJniCmdReadyIF)
   * @param pCBData  array of size 1 - returns custom data
   *  
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Registered_CommandReady(long cmdHandle, Object[] cbInstance, Object[] pCBData) throws TmlSidexException{
    StringBuffer sCBName = new StringBuffer("");
    int iRet = NativeTmlCmdRegisteredCommandReady(cmdHandle, cbInstance, sCBName, pCBData);
    if (TML_SUCCESS != iRet){
      sCBName = null;
      throw new TmlSidexException("tml_Cmd_Registered_CommandReady / Error "+Integer.toString(iRet), iRet);
    }
    sCBName = null;
  }
  
  /**
   * @brief get registered progress reply handler and custom data
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param cbInstance array of size 1 - containing handler (TmlJniCmdProgressReplyIF)
   * @param pCBData  array of size 1 - returns custom data
   *  
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Registered_Progress(long cmdHandle, Object[] cbInstance, Object[] pCBData) throws TmlSidexException{
    StringBuffer sCBName = new StringBuffer("");
    int iRet = NativeTmlCmdRegisteredProgress(cmdHandle, cbInstance, sCBName, pCBData);
    if (TML_SUCCESS != iRet){
      sCBName = null;
      throw new TmlSidexException("tml_Cmd_Registered_Progress / Error "+Integer.toString(iRet), iRet);
    }
    sCBName = null;
  }
  
  /**
   * @brief get registered status reply handler and custom data
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param cbInstance array of size 1 - containing handler (TmlJniCmdStatusReplyIF)
   * @param pCBData  array of size 1 - returns custom data
   *  
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Registered_StatusReply(long cmdHandle, Object[] cbInstance, Object[] pCBData) throws TmlSidexException{
    StringBuffer sCBName = new StringBuffer("");
    int iRet = NativeTmlCmdRegisteredStatusReply(cmdHandle, cbInstance, sCBName, pCBData);
    if (TML_SUCCESS != iRet){
      sCBName = null;
      throw new TmlSidexException("tml_Cmd_Registered_StatusReply / Error "+Integer.toString(iRet), iRet);
    }
    sCBName = null;
  }
  /* @} */

  ///////////////////////////////////////////////
  //TML commands / Accessing header information

  /**
   * @addtogroup tmlheader
   * @{
   */

  /**
   * @brief get command id
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return command id
   *
   * @throws TmlSidexException 
   */
  public int tml_Cmd_Header_GetCommand(long cmdHandle) throws TmlSidexException{
    int[] cmdID = new int[1];
    int iRet = NativeTmlCmdHeaderGetCommand(cmdHandle, cmdID);
    if (TML_SUCCESS != iRet){
      cmdID = null;
      throw new TmlSidexException("tml_Cmd_Header_GetCommand / Error "+Integer.toString(iRet), iRet);
    }
    int iCmd = cmdID[0];
    cmdID = null;
    return iCmd;
  }

  /**
   * @brief get command creation time
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return  command creation time. Format "YYYY-MM-DD hh:mm:ss:ttt"
   *
   * @throws TmlSidexException 
   */
  public String tml_Cmd_Header_GetCreationTime(long cmdHandle) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeTmlCmdHeaderGetCreationTime(cmdHandle, buffer);
    if (TML_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("tml_Cmd_Header_GetCreationTime / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }

  /**
   * @brief get the error code
   * 
   * There are a number of predefined error codes that are set automatically.
   * Besides that a custom error code can be set while processing a command to
   * inform the caller about problems related to implementation specific problems.
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return command execution error code
   *
   * @throws TmlSidexException 
   */
  public int tml_Cmd_Header_GetError(long cmdHandle) throws TmlSidexException{
    int[] error = new int[1];
    int iRet = NativeTmlCmdHeaderGetError(cmdHandle, error);
    if (TML_SUCCESS != iRet){
      error = null;
      throw new TmlSidexException("tml_Cmd_Header_GetError / Error "+Integer.toString(iRet), iRet);
    }
    int iError = error[0];
    error = null;
    return iError;
  }

  /**
   * @brief get the error message
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return command execution error message
   *
   * @throws TmlSidexException 
   */
  public String tml_Cmd_Header_GetErrorMessage(long cmdHandle) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeTmlCmdHeaderGetErrorMessage(cmdHandle, buffer);
    if (TML_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("tml_Cmd_Header_GetErrorMessage / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }

  /**
   * @brief get execution mode
   *
   *  <TABLE>
   *   <TR><TD><B>Name       </B></TD><TD><B>Value </B></TD><TD><B>Description  </B></TD></TR>
   *   <TR><TD>TMLCOM_MODE_ASYNC   </TD><TD>0</TD><TD>asynchronous command call</TD></TR>
   *   <TR><TD>TMLCOM_MODE_SYNC   </TD><TD>1</TD><TD>synchronous command call</TD></TR>
   *   <TR><TD>TMLCOM_MODE_EVT   </TD><TD>2</TD><TD>event call</TD></TR>
   *  </TABLE>
   *
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return Get execution mode. 
   *
   * @throws TmlSidexException 
   */
  public int tml_Cmd_Header_GetMode(long cmdHandle) throws TmlSidexException{
    int[] mode = new int[1];
    int iRet = NativeTmlCmdHeaderGetMode(cmdHandle, mode);
    if (TML_SUCCESS != iRet){
      mode = null;
      throw new TmlSidexException("tml_Cmd_Header_GetMode / Error "+Integer.toString(iRet), iRet);
    }
    int iMode = mode[0];
    mode = null;
    return iMode;
  }

  /**
   * @brief get progress
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return command execution progress (in percent).
   *
   * @throws TmlSidexException 
   */
  public int tml_Cmd_Header_GetProgress(long cmdHandle) throws TmlSidexException{
    int[] progress = new int[1];
    int iRet = NativeTmlCmdHeaderGetProgress(cmdHandle, progress);
    if (TML_SUCCESS != iRet){
      progress = null;
      throw new TmlSidexException("tml_Cmd_Header_GetProgress / Error "+Integer.toString(iRet), iRet);
    }
    int iProgress = progress[0];
    progress = null;
    return iProgress;
  }

  /**
   * @brief get status reply message
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return reply message. 
   *
   * @throws TmlSidexException 
   */
  public String tml_Cmd_Header_GetReplyMessage(long cmdHandle) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeTmlCmdHeaderGetReplyMessage(cmdHandle, buffer);
    if (TML_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("tml_Cmd_Header_GetReplyMessage / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }

  /**
   * @brief get reply type
   *
   * If a progress or status reply is received, the command
   * execution state is TMLCOM_CSTATE_PENDING.
   * In that case the reply type can be used to determine the kind of reply.
   *
   *  <TABLE>
   *   <TR><TD><B>Name       </B></TD><TD><B>Value </B></TD><TD><B>Description  </B></TD></TR>
   *   <TR><TD>TMLCOM_RPY_PROGRESS   </TD><TD>0</TD><TD>progress reply</TD></TR>
   *   <TR><TD>TMLCOM_RPY_WARNING   </TD><TD>10</TD><TD>warning status reply</TD></TR>
   *   <TR><TD>TMLCOM_RPY_ERROR   </TD><TD>20</TD><TD>error status reply</TD></TR>
   *   <TR><TD>TMLCOM_RPY_INFORMATION   </TD><TD>30</TD><TD>information status reply</TD></TR>
   *   <TR><TD>TMLCOM_CSTATE_UNDEFINED   </TD><TD>-1</TD><TD>undefined reply type</TD></TR>
   *  </TABLE>
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * 
   * @return reference to reply type
   *
   * @throws TmlSidexException 
   */
  public int tml_Cmd_Header_GetReplyType(long cmdHandle) throws TmlSidexException{
    int[] type = new int[1];
    int iRet = NativeTmlCmdHeaderGetReplyType(cmdHandle, type);
    if (TML_SUCCESS != iRet){
      type = null;
      throw new TmlSidexException("tml_Cmd_Header_GetReplyType / Error "+Integer.toString(iRet), iRet);
    }
    int iType = type[0];
    type = null;
    return iType;
  }

  /**
   * @brief get execution state
   *
   *  <TABLE>
   *   <TR><TD><B>Name       </B></TD><TD><B>Value </B></TD><TD><B>Description  </B></TD></TR>
   *   <TR><TD>TMLCOM_CSTATE_CREATED   </TD><TD>0</TD><TD>initial command state</TD></TR>
   *   <TR><TD>TMLCOM_CSTATE_EXECUTED   </TD><TD>1</TD><TD>command successfully executed</TD></TR>
   *   <TR><TD>TMLCOM_CSTATE_FAILED   </TD><TD>2</TD><TD>command execution failed</TD></TR>
   *   <TR><TD>TMLCOM_CSTATE_PENDING   </TD><TD>3</TD><TD>command execution pending</TD></TR>
   *   <TR><TD>TMLCOM_CSTATE_UNDEFINED  </TD><TD>-1</TD><TD>undefined command state</TD></TR>
   *  </TABLE>
   *  
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @return execution state
   * 
   * @throws TmlSidexException 
   */
  public int tml_Cmd_Header_GetState(long cmdHandle) throws TmlSidexException{
    int[] state = new int[1];
    int iRet = NativeTmlCmdHeaderGetState(cmdHandle, state);
    if (TML_SUCCESS != iRet){
      state = null;
      throw new TmlSidexException("tml_Cmd_Header_GetState / Error "+Integer.toString(iRet), iRet);
    }
    int iState = state[0];
    state = null;
    return iState;
  }

  /**
   * @brief set command id
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE) 
   * @param cmdID   command id
   *
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Header_SetCommand(long cmdHandle, int cmdID) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetCommand(cmdHandle, cmdID);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetCommand / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get the error code
   * 
   * If a custom error code is set while command processing
   * it will not be overwritten by TML specific standard error codes.
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param error  command execution error code
   *  
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Header_SetError(long cmdHandle, int error) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetError(cmdHandle, error);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetError / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set the error message
   * 
   * If a custom error message is set while command processing
   * it will not be overwritten by TML specific standard error messages.
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param msg command execution error message
   *
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Header_SetErrorMessage(long cmdHandle, String msg) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetErrorMessage(cmdHandle, msg);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetErrorMessage / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set execution mode
   * 
   * It is not recommended to change the execution mode.
   * This values is used internally. Changing the command state
   * might result in unpredictable behavior.
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   *  
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Header_SetMode(long cmdHandle, int mode) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetMode(cmdHandle, mode);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetMode / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set progress
   * 
   * The progress is usually set by tml_Send_AsyncProgressReply().
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param progress  progress value (0-100)
   * 
   * @throws TmlSidexException
   * @see Tml#tml_Send_AsyncProgressReply(long, int)
   */
  public void tml_Cmd_Header_SetProgress(long cmdHandle, int progress) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetProgress(cmdHandle, progress);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetProgress / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set status reply message
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param msg  reply message. 
   *
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Header_SetReplyMessage(long cmdHandle, String msg) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetReplyMessage(cmdHandle, msg);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetReplyMessage / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set reply type
   *  
   * It is not recommended to change the reply type.
   * This values is used internally. Changing the reply type might
   * result in unpredictable behavior.
   * 
   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param type  reply type
   *
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Header_SetReplyType(long cmdHandle, int type) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetReplyType(cmdHandle, type);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetReplyType / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set execution state
   * 
   * It is not recommended to change the command state.
   * This values is used internally. Changing the command state might result
   * in unpredictable behavior.

   * @param cmdHandle  TML command handle (TML_COMMAND_HANDLE)
   * @param state  execution state
   *
   * @throws TmlSidexException 
   */
  public void tml_Cmd_Header_SetState(long cmdHandle, int state) throws TmlSidexException{
    int iRet = NativeTmlCmdHeaderSetState(cmdHandle, state);
    if (TML_SUCCESS != iRet){
      throw new TmlSidexException("tml_Cmd_Header_SetState / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */
}
