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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup sdxClasses
 * @brief Implementation of a DateTime type variant.
 *
 * A SIDEX date time is formatted as string:<br>
 *           The value must be formatted according to the specification:<br>
 *           YYYY-MM-DD hh:mm:ss:ttt <br><br>
 *           YYYY year (0001-9999 / 0000 for undefined)<br>
 *           MM monst (01-12 / 00 for undefined)<br>
 *           DD day of month (01-31  / 00 for undefined)<br>
 *           hh hour (00-23)<br>
 *           mm minutes (00-59)<br>
 *           ss seconds (00-59)<br>
 *           ttt milliseseconds (000-999)<br>
 *           The date attributes may also be undefined. In that case year, month and day must be set to 0.
 *
 */
public class SDXDateTime extends SDXBase{

  // Member

  /**
   * @brief construct a new SDXDateTime from a formatted string
   *
   * @param sValue string value for the SIDEX DateTime
   *
   * @throws TmlSidexException 
   */
  public SDXDateTime(String sValue) throws TmlSidexException{
    m_var = m_sidex.sidex_Variant_New_DateTime(sValue);
  }
  
  /**
   * @brief construct a new SDXDateTime from a Date object
   *
   * @param dValue Date value
   *
   * @throws TmlSidexException 
   */
  public SDXDateTime(Date dValue) throws TmlSidexException{

    String str = String.format("%tY-%tm-%td %tH:%tM:%tS:%tL", dValue, dValue, dValue, dValue, dValue, dValue, dValue);

    m_var = m_sidex.sidex_Variant_New_DateTime(str);
  }
  
  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX DateTime type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXDateTime(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_DATETIME)){
      throw new TmlSidexException("SDXDateTime::SDXDateTime / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
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
   * @brief get value as Date object
   *
   * @return Date value
   * @throws TmlSidexException 
   */
  public Date getValue() throws TmlSidexException{
    Date currentParsed = null;

    String sRet = m_sidex.sidex_Variant_As_DateTime(m_var);
    DateFormat df = null;
    try {
      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
      currentParsed = df.parse(sRet);
    } catch (ParseException e) {
      df = null;
      e.printStackTrace();
      TmlSidexException tmlExcept = new TmlSidexException("SDXDateTime::getValue / parsing error", SidexErr.SIDEX_ERR_INVALID_DATETIME);
      throw tmlExcept;
    }
    df = null;
    return currentParsed;
  }
  
  /**
   * @brief get value as string
   *
   * @return formatted string
   */
  @Override public String toString(){
    String sRet = null;
    try {
      sRet = m_sidex.sidex_Variant_As_DateTime(m_var);
    } catch (TmlSidexException e) {
      e.printStackTrace();
    }
    return sRet;
  }
  
  /**
   * @brief set value as string
   *
   * @param sValue new String value for the SIDEX DateTime
   *
   * @throws TmlSidexException 
   */
  public void setValue(String sValue) throws TmlSidexException{
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_DecRef(m_var);
    }
    m_var = m_sidex.sidex_Variant_New_DateTime(sValue);
    m_bThisOwn = true;
  }
  
  /**
   * @brief set value as date
   *
   * @param dValue Date value
   *
   * @throws TmlSidexException 
   */
  public void setValue(Date dValue) throws TmlSidexException{
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_DecRef(m_var);
    }
    String str = String.format("%tY-%tm-%td %tH:%tM:%tS:%tL", dValue, dValue, dValue, dValue, dValue, dValue, dValue);
    m_var = m_sidex.sidex_Variant_New_DateTime(str);
    m_bThisOwn = true;
  }
  
  /**
   * @brief compare datetime value
   *
   * Returns true if and only if the argument is not null
   * and is a SDXDateTime object that represents the same SIDEX DateTime sequence of characters as this object.
   *
   * @param obj the object to compare with
   *
   * @return true if the SDXDateTime objects represent the same SIDEX DateTime sequence of characters, false otherwise.
   */
  @Override public boolean equals(Object obj) { 
    boolean bRet = false;
    if (obj instanceof SDXDateTime){
      try {
        bRet = (this.getValue()).equals(((SDXDateTime)obj).getValue());
      } catch (TmlSidexException e) {
        // Suppress because both Objects are dSDXateTime
      }
    }
    return bRet;
  }
}
