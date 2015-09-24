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

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup sdxClasses
 * @brief Implementation of a string  variant.
 */
public class SDXString extends SDXBase{

  // Member

  /**
   * @brief constructs a new empty SDXString object
   *
   * @throws TmlSidexException 
   */
  public SDXString() throws TmlSidexException{
    m_var = m_sidex.sidex_Variant_New_String("");
  }

  /**
   * constructs a new SDXString object from string
   *
   * @param sValue initial String value
   *
   * @throws TmlSidexException 
   */
  public SDXString(String sValue) throws TmlSidexException{
    m_var = m_sidex.sidex_Variant_New_String(sValue);
  }
  
  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX String type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXString(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_STRING)){
      throw new TmlSidexException("SDXString::SDXString / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
    }
    else{
      m_bThisOwn = thisown;
      m_var = vHandle;
    }
  }
  
  /**
   * @brief destructor.
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    // Super destructrion
    super.finalize();
  }

  /**
   * @brief get value
   *
   * @return representing String value
   */
  public String getValue(){
    String sRet = null;
    try {
      sRet = m_sidex.sidex_Variant_As_String(m_var);
    } catch (TmlSidexException e) {
      // Suppress exception here / it will be done in the constructor if variant type don't match
    }
    return sRet;
    
  }
  
  /**
   * @brief get value as string
   *
   * @return String object representation
   */
  @Override public String toString(){
    String sRet = null;
    sRet = this.getValue();
    return sRet;
  }
  
  /**
   * @brief set value
   *
   * @param sValue new String value
   *
   * @throws TmlSidexException 
   */
  public void setValue(String sValue) throws TmlSidexException{
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_DecRef(m_var);
    }
    m_var = m_sidex.sidex_Variant_New_String(sValue);
    m_bThisOwn = true;
  }
  
  /**
   * @brief get length
   *
   * @return String length
   *
   * @throws TmlSidexException 
   */
  public int length() throws TmlSidexException{
    
    int length = m_sidex.sidex_Variant_As_String_Length (m_var);
    
    return length;
  }
  
  /**
   * @brief compare value
   *
   * Returns true if and only if the argument is not null and
   * is a SDXString object that represents the same sequence of
   * characters as this object.
   *
   * @param obj the object to compare with
   *
   * @return true if the SDXString objects represent the same
   * sequence of characters, false otherwise.
   */
  @Override public boolean equals(Object obj) { 
    boolean bRet = false;
    if (obj instanceof SDXString){
      bRet = (this.getValue()).equals(((SDXString)obj).getValue());
    }
    return bRet;
  }
}
