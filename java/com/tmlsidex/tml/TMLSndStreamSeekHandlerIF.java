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

/**
 * @ingroup tmlClassesCB
 * @brief stream seek handler interface
 */
public interface TMLSndStreamSeekHandlerIF{
  /**
   * @brief stream seek handler method
   *
   * @see TMLSndStream#registerSeekHandler(TMLSndStreamSeekHandlerIF, Object)
   * @param iID clear stream identification
   * @param cbData custom data from handler registration or null
   * @param seekPos   position in stream 
   * @param origin    offset origin
   * 
   * <br><br>soFromBeginning (0) / from start of stream / seekPos > 0 
   * <br>soFromCurrent (1)  / from current position / 
   * <br>soFromEnd (2)  / from end of stream / seekPos < 0
   * 
   * @return 0 on SUCCESS, -1 on error
   */
  int sndStreamSeek(long iID, Object cbData, long seekPos, int origin);
}
  
