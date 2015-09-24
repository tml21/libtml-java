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
 
package com.tmlsidex.jni;

/**
 * @ingroup tmlCallback
 * @brief event subscription / unsubscription request handler interface
 */
public interface TmlJniEvtPeerRegisterHandlerIF{
  /**
   * @brief event subscription / unsubscription request handler method
   * @see Tml#tml_Evt_Set_OnPeerRegister(long, String, TmlJniEvtPeerRegisterHandlerIF, Object)
   * @param bSubscribe  true = subscribe, false = unsubscribe 
   * @param sIP   hostname or IP address 
   * @param sPort  port number 
   * @param cbData custom data from handler registration or null
   * 
   * @return true on success
   */
  boolean jniEvtPeerRegistrationCB(boolean bSubscribe, String sIP, String sPort, Object cbData);
 
  /**
   * @brief event subscription / unsubscription request handler method name
   */
  public static final String CB_NAME = "jniEvtPeerRegistrationCB";
}
  
