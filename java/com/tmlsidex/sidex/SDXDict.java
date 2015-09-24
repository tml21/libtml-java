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

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup sdxClasses
 * @brief Implementation of a SIDEX dictionary
 */
public class SDXDict extends SDXBase{

  // Member
  StringBuffer m_strBuf   = null;
  
  /**
   * @brief constructs a new SDXDict
   */
  public SDXDict(){
    m_var = m_sidex.sidex_Variant_New_Dict();
    
    m_strBuf = new StringBuffer();
  }

  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX Dict type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXDict(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_DICT)){
      throw new TmlSidexException("SDXDict::SDXDict / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
    }
    else{
      m_strBuf = new StringBuffer();
  
      m_bThisOwn = thisown;
      m_var = vHandle;
    }
  }
  
  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    m_strBuf = null;
    // Super destructrion
    super.finalize();
  }

  /**
   * @brief delete all key/value pairs
   *
   * @throws TmlSidexException 
   */
  public void clear() throws TmlSidexException{
    m_sidex.sidex_Variant_Dict_Clear(m_var);
  }

  /**
   * @brief set the cursor to the first element
   *
   * @throws TmlSidexException 
   */
  public void first() throws TmlSidexException{
    m_sidex.sidex_Variant_Dict_First(m_var);
  }

  /**
   * @brief get element from current cursor position and move next value
   * 
   * @return SDXBase object depending of the data type. If end is reached null is returned.
   *
   * @throws TmlSidexException 
   */
  public SDXBase next() throws TmlSidexException{
    SDXBase retValue = null;
    try{
      int l = m_strBuf.length();
      m_strBuf.delete(0, l);
      long variant = m_sidex.sidex_Variant_Dict_Next(m_var, m_strBuf);
      retValue = SDXUtils.sidexVariantAsJavaObject(m_sidex, variant);
    }
    catch (TmlSidexException e){
      if (SidexErr.SIDEX_ERR_NOCONTENT != e.getError()){
        throw e;
      }
    }
    return retValue;
  }

  /**
   * @brief check if kex exists
   *
   * @return True if the key exists
   * @throws TmlSidexException 
   */
  public boolean hasKey(String name) throws TmlSidexException{
    boolean bRet = m_sidex.sidex_Variant_Dict_HasKey(m_var, name);
    return bRet;
  }
  
  /**
   * @brief get all key names
   *
   * @return String array of key names
   *
   * @throws TmlSidexException 
   */
  public String[] getKeys() throws TmlSidexException{
    
    long keys = m_sidex.sidex_Variant_Dict_Keys(m_var);
    int length = m_sidex.sidex_Variant_List_Size (keys);
    
    String[] strRet = new String[length];
    for (int i = 0; i < length; ++i){
      long variant = m_sidex.sidex_Variant_List_Get (keys, i);
      String sItem = m_sidex.sidex_Variant_As_String (variant);
      strRet[i] = sItem;
    }
    m_sidex.sidex_Variant_DecRef(keys);
    return strRet;
  }
  
  /**
   * @brief delete a key/value pair
   *
   * @param key key name
   *
   * @throws TmlSidexException 
   */
  public void deleteItem(String key) throws TmlSidexException{
    m_sidex.sidex_Variant_Dict_Delete(m_var, key);
  }
  
  /**
   * @brief get content
   *
   * @return Dictionary of SDXBase driven object values
   *
   * @throws TmlSidexException 
   */
  public Dictionary<String, SDXBase> getContent() throws TmlSidexException{
    Dictionary<String, SDXBase> retValue = null;
    int length = this.length();
    if (0 < length){
      retValue = new Hashtable<String, SDXBase>();
      String[] keys = this.getKeys();
      for (int i = 0; i < length;++i){
        SDXBase value = this.getValue(keys[i]);
        retValue.put(keys[i], value);
      }
      keys = null;
    }
    return retValue;
  }
  
  /**
   * @brief get a value by key
   *
   * @param key key name
   * 
   * @return SDXBase object depending on the data type
   *
   * @throws TmlSidexException 
   */
  public SDXBase getValue(String key) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_Dict_Get(m_var, key);
    SDXBase retVar = SDXUtils.sidexVariantAsJavaObject(m_sidex, variant);
    return retVar;
  }
  
  /**
   * @brief set value to SDXNone
   *
   * @param key key name
   *
   * @throws TmlSidexException 
   */
  public void setValue(String key) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_New_None();
    m_sidex.sidex_Variant_Dict_Set(m_var, key, variant);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
  /**
   * @brief set a value
   *
   * @param key key name
   * @param sVal the value to set of type
   *        integer, long, float, double, boolean, Integer, Long, Float, Double, Boolean, String or SDXBase
   *
   * @throws TmlSidexException 
   */
  public void setValue(String key, Object sVal) throws TmlSidexException{
    long variant = SDXUtils.javaObjectAsSidexVariant(m_sidex, sVal);
    m_sidex.sidex_Variant_Dict_Set(m_var, key, variant);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
  /**
   * @brief get the number of key/value pairs
   *
   * @return number of key/value pairs
   *
   * @throws TmlSidexException 
   */
  public int length() throws TmlSidexException{
    
    int iRet = m_sidex.sidex_Variant_Dict_Size(m_var);
    return iRet;
  }
}
