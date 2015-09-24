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
 * @brief Implementation of a float variant.
 */
public class SDXFloat extends SDXBase{

  // Member

  /**
   * @brief create a new SDXFloat from a double value
   *
   * @param dValue initial double value
   */
  public SDXFloat(double dValue){
    m_var = m_sidex.sidex_Variant_New_Float(dValue);
  }
  
  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX Float type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXFloat(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_FLOAT)){
      throw new TmlSidexException("SDXFloat::SDXFloat / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
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
    // Super destruction
    super.finalize();
  }

  /**
   * @brief get value as double
   *
   * @return representing double value
   */
  public double getValue(){
    double fValue = 0.0;
    try {
      fValue = m_sidex.sidex_Variant_As_Float(m_var);
    } catch (TmlSidexException e) {
      // Suppress exception here / it will be done in the constructor if variant type don't match
    }
    return fValue;
  }
  
  /**
   * @brief get value as string
   *
   * @return String object representation
   */
  @Override public String toString(){
    double fValue = 0.0;
    if (0 != m_var){
      try {
        fValue = m_sidex.sidex_Variant_As_Float(m_var);
      } catch (TmlSidexException e) {
        e.printStackTrace();
      }
    }
    Double lVal = Double.valueOf(fValue);
    return lVal.toString();
  }
  
  /**
   * @brief set value
   *
   * @param fValue new double value
   *
   * @throws TmlSidexException 
   */
  public void setValue(double fValue) throws TmlSidexException{
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_DecRef(m_var);
    }
    m_var = m_sidex.sidex_Variant_New_Float(fValue);
    m_bThisOwn = true;
  }
  
  /**
   * @brief compare value
   *
   * @param obj the object to compare with
   *
   * @return true if the SDXFloat objects represent the same double value, false otherwise.
   */
  @Override public boolean equals(Object obj) { 
    boolean bRet = false;
    if (obj instanceof SDXFloat){
      bRet = this.getValue() == ((SDXFloat)obj).getValue();
    }
    return bRet;
  }
}
