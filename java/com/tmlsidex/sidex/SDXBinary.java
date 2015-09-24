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
 
package com.tmlsidex.sidex;

import java.nio.ByteBuffer;

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup sdxClasses
 * @brief Implementation of a String type variant.
 */
public class SDXBinary extends SDXBase{

  // Member
  int[] m_iArray = null;

  /**
   * @brief constructs a new instance of SDXBinary
   *
   * @throws TmlSidexException 
   */
  public SDXBinary() throws TmlSidexException{
    m_iArray = new int[1];
    byte[] binary = {};
    m_var = m_sidex.sidex_Variant_New_Binary(binary, 0);
  }

  /**
   * @brief constructs a new instance of SDXBinary with data
   *
   * @param binary initial binary value
   * @param iLength length of the binary string
   *
   * @throws TmlSidexException 
   */
  public SDXBinary(byte[] binary, int iLength) throws TmlSidexException{
    m_iArray = new int[1];
    m_var = m_sidex.sidex_Variant_New_Binary(binary, iLength);
  }
  
  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX String type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXBinary(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_BINARY)){
      throw new TmlSidexException("SDXBinary::SDXBinary / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
    }
    else{
      m_iArray = new int[1];
      m_bThisOwn = thisown;
      m_var = vHandle;
    }
  }
  
  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    m_iArray = null;   
    // Super destructrion
    super.finalize();
  }

  /**
   * @brief get binary data as byte array
   *
   * @return byte array
   */
  public byte[] getValue(){
    byte[] bRet = null;
    try {
      bRet = m_sidex.sidex_Variant_As_Binary(m_var, m_iArray);
    } catch (TmlSidexException e) {
      // Suppress exception here / it will be done in the constructor if variant type don't match
    }
    return bRet;
  }
  
  /**
   * @brief set binary data
   *
   * @param binary new binary value
   * @param iLength length of the binary string
   *
   * @throws TmlSidexException 
   */
  public void setValue(byte[] binary, int iLength) throws TmlSidexException{
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_DecRef(m_var);
    }
    m_var = m_sidex.sidex_Variant_New_Binary(binary, iLength);
    m_bThisOwn = true;
  }
  
  /**
   * @brief get data length in bytes
   *
   * @return data length
   *
   * @throws TmlSidexException 
   */
  public int length() throws TmlSidexException{
    int length = m_sidex.sidex_Variant_As_Binary_Length (m_var);
    return length;
  }
}
