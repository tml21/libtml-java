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
 * @brief stream read handler handler interface
 */
public interface TmlJniSndStreamReadHandlerIF{
  /**
   * @brief stream read handler handler method
   *
   * @see Tml#tml_SndStream_Register_Read(long, long, TmlJniSndStreamReadHandlerIF, Object)
   * @param strID clear stream identification
   * @param cbData custom data from handler registration or null
   * @param buffer buffer to return the read data
   * @param count number of bytes to read
   * 
   * @return number of bytes read (can be smaller than count if stream ends). -1 in case of an error
   */
  int tmlSndStreamReadCB(long strID, Object cbData, byte[] buffer, int count);

  /**
   * @brief stream read handler handler method name
   */
  public static final String CB_NAME = "tmlSndStreamReadCB";
}
  
