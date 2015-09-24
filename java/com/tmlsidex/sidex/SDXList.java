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

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup sdxClasses
 * @brief Implementation of a List type variant.
 */
public class SDXList extends SDXBase{

  // Member
  
  /**
   * @brief constructs a new SDXList
   */
  public SDXList(){
    m_var = m_sidex.sidex_Variant_New_List();
  }

  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX List type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXList(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_LIST)){
      throw new TmlSidexException("SDXList::SDXList / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
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
   * @brief delete all values
   *
   * @throws TmlSidexException 
   */
  public void clear() throws TmlSidexException{
    m_sidex.sidex_Variant_List_Clear(m_var);
  }

  /**
   * @brief delete value
   *
   * @param pos the position in the list
   *
   * @throws TmlSidexException 
   */
  public void deleteItem(int pos) throws TmlSidexException{
    m_sidex.sidex_Variant_List_DeleteItem(m_var, pos);
  }
  
  /**
   * @brief get content
   *
   * @return ArrayList of SDXBase driven objects
   *
   * @throws TmlSidexException 
   */
  public ArrayList<SDXBase> getContent() throws TmlSidexException{
    ArrayList<SDXBase> retVar = null;
    
    int length = this.length();
    if (0 < length){
      retVar = new ArrayList<SDXBase>();
      for (int i = 0; i < length; ++i){
        SDXBase item = this.getValue(i);
        retVar.add(item);
      }
    }
    return retVar;
  }
  
  /**
   * @brief get an item by index
   *
   * @param index item index
   * 
   * @return SDXBase driven object depending of the data type
   *
   * @throws TmlSidexException 
   */
  public SDXBase getValue(int index) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_List_Get(m_var, index);
    SDXBase retVar = SDXUtils.sidexVariantAsJavaObject(m_sidex, variant);
    return retVar;
  }
  
  /**
   * @brief set value at index to SDXNone
   *
   * @param index list index
   *
   * @throws TmlSidexException 
   */
  public void setValue(int index) throws TmlSidexException{
    long var = m_sidex.sidex_Variant_New_None();
    m_sidex.sidex_Variant_List_Set(m_var, var, index);
    m_sidex.sidex_Variant_DecRef(var);
  }
  
  /**
   * @brief set value at index
   *
   * @param index list index
   * @param sVal the value to set of type
   *        integer, long, float, double, boolean, Integer, Long, Float, Double, Boolean, String or SDXBase
   *
   * @throws TmlSidexException 
   */
  public void setValue(int index, Object sVal) throws TmlSidexException{
    long variant = SDXUtils.javaObjectAsSidexVariant(m_sidex, sVal);
    m_sidex.sidex_Variant_List_Set(m_var, variant, index);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
  /**
   * @brief replace SDXList content with the ArrayList content
   *
   * @param lValue new ArrayList
   *
   * @throws TmlSidexException 
   */
  // maybe for later use:
  private void setValue(ArrayList<SDXBase> lValue) throws TmlSidexException{
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_List_Clear(m_var);
    }
    int index = 0;
    for (SDXBase current: lValue) {
      this.setValue(index++, current);
    }
    m_bThisOwn = true;
  }
  
  /**
   * @brief append SDXNone
   *
   * @return position of the added element in the list
   *
   * @throws TmlSidexException 
   */
  public int appendValue() throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_New_None();
    int iPos = m_sidex.sidex_Variant_List_Append(m_var, variant);
    m_sidex.sidex_Variant_DecRef(variant);
    return iPos;
  }
  
  /**
   * @brief append value
   *
   * @param sVal the value to append of type
   *        integer, long, float, double, boolean, Integer, Long, Float, Double, Boolean, String or SDXBase        
   *
   * @throws TmlSidexException 
   */
  public int appendValue(Object sVal) throws TmlSidexException{
    long variant = SDXUtils.javaObjectAsSidexVariant(m_sidex, sVal);
    int iPos = m_sidex.sidex_Variant_List_Append(m_var, variant);
    m_sidex.sidex_Variant_DecRef(variant);
    return iPos;
  }
  
  /**
   * @brief insert SDXNone at position
   *
   * @param pos the position in the list
   *
   * @throws TmlSidexException 
   */
  public void insertValue(int pos) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_New_None();
    m_sidex.sidex_Variant_List_Insert(m_var, variant, pos);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
  /**
   * @brief insert a value at position
   *
   * @param pos the position in the list
   * @param sVal the value to insert of type
   *        integer, long, float, double, boolean, Integer, Long, Float, Double, Boolean, String or SDXBase
   *
   * @throws TmlSidexException 
   */
  public void insertValue(int pos, Object sVal) throws TmlSidexException{
    long variant = SDXUtils.javaObjectAsSidexVariant(m_sidex, sVal);
    m_sidex.sidex_Variant_List_Insert(m_var, variant, pos);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
  /**
   * @brief get the number of items
   *
   * @return number of items
   *
   * @throws TmlSidexException 
   */
  public int length() throws TmlSidexException{
    
    int iSize = m_sidex.sidex_Variant_List_Size(m_var);
    return iSize;
  }
}
