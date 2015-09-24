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
 * @brief Implementation of a integer variant.
 */
public class SDXInteger extends SDXBase{

  // Member

  /**
   * @brief create a new SDXInteger from a long value
   *
   * @param lValue initial long value
   */
  public SDXInteger(long lValue){
    m_var = m_sidex.sidex_Variant_New_Integer(lValue);
  }
  
  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX Integer type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXInteger(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_INTEGER)){
      throw new TmlSidexException("SDXInteger::SDXInteger / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
    }
    else{
      m_bThisOwn = thisown;
      m_var = vHandle;
    }
  }
  
  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    // Super destructrion
    super.finalize();
  }

  /**
   * @brief get value as long
   *
   * @return long value
   */
  public long getValue(){
    long lValue = 0;
    try {
      lValue = m_sidex.sidex_Variant_As_Integer(m_var);
    } catch (TmlSidexException e) {
      // Suppress exception here / it will be done in the constructor if variant type don't match
    }
    return lValue;
  }
  
  /**
   * @brief get value as string
   *
   * @return string representation
   */
  @Override public String toString(){
    long lValue = 0;
    if (0 != m_var){
      try {
        lValue = m_sidex.sidex_Variant_As_Integer(m_var);
      } catch (TmlSidexException e) {
        e.printStackTrace();
      }
    }
    Long lVal = Long.valueOf(lValue);
    return lVal.toString();
  }
  
  /**
   * @brief set value
   *
   * @param lValue new long value
   *
   * @throws TmlSidexException 
   */
  public void setValue(long lValue) throws TmlSidexException{
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_DecRef(m_var);
    }
    m_var = m_sidex.sidex_Variant_New_Integer(lValue);
    m_bThisOwn = true;
  }
  
  /**
   * @brief compare value
   *
   * Returns true if and only if the argument is not null and is a SDXInteger
   * object that represents the same long value as this object.
   *
   * @param obj the object to compare with
   *
   * @return true if the SDXInteger objects represent the same long value, false otherwise.
   */
  @Override public boolean equals(Object obj) { 
    boolean bRet = false;
    if (obj instanceof SDXInteger){
      bRet = this.getValue() == ((SDXInteger)obj).getValue();
    }
    return bRet;
  }
}
