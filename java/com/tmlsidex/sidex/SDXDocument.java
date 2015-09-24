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


import java.util.Dictionary;
import java.util.Enumeration;

import com.tmlsidex.jni.Sidex;
import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup sdxClasses
 * @brief SIDEX document
 *
 * The SIDEX document manages a group/key based data container.
 * Datatypes are handled automatically by the document class and
 * the variant data objects to ensure the type is saved together with the data.
 */
public class SDXDocument extends Object{

  // Member
  long    m_var         = 0;
  Sidex   m_sidex       = null;
  boolean m_bThisOwn    = false;
  
  /**
   * @brief constructs new SDXDocument
   *
   * @param name name of the document
   *
  * @throws TmlSidexException 
   */
  public SDXDocument(String name) throws TmlSidexException{
    m_sidex    = new Sidex();
    m_bThisOwn = true;
    
    m_var = m_sidex.sidex_Create(name);
  }

  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param sHandle SIDEX handle value
   * @param thisown instance is owner of SIDEX variant handle 
   *
  * @throws TmlSidexException 
   */
  public SDXDocument(long sHandle, boolean thisown) throws TmlSidexException{
    m_sidex    = new Sidex();
    m_bThisOwn = thisown;
    m_var      = sHandle;
  }

  /**
   * @brief destructor
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Free(m_var);
    }
    m_var     = 0;
    m_sidex   = null;
    
    // Super destructrion
    super.finalize();
  }

  /**
   * @brief get low level handle
   *
   * @return SIDEX handle
   */
  public long getShandle(){
    return m_var;
  }
  

  /**
   * @brief delete all values
   */
  public void clear(){
    m_sidex.sidex_Clear(m_var);
  }

  /**
   * @brief get content as string (XML)
   *
   * @return SIDEX XML String
   *
   * @throws TmlSidexException 
   */
  public String getContent() throws TmlSidexException{
    String sRet = m_sidex.sidex_Get_Content(m_var);
    return sRet;
  }

  /**
   * @brief set content as string (XML)
   *
   * @param content SIDEX XML String
   *
   * @throws TmlSidexException 
   */
  public void setContent(String content) throws TmlSidexException{
    m_sidex.sidex_Set_Content(m_var, content);
  }

  /**
   * @brief get document name
   *
   * @return Document name
   *
   * @throws TmlSidexException 
   */
  public String getDocumentName() throws TmlSidexException{
    String sRet = m_sidex.sidex_Get_DocumentName(m_var);
    return sRet;
  }

  /**
   * @brief set document name
   *
   * @param name document name
   *
   * @throws TmlSidexException 
   */
  public void setDocumentName(String name) throws TmlSidexException{
    m_sidex.sidex_Set_DocumentName(m_var, name);
  }

  /**
   * @brief load content from a file
   *
   * @param path filename and path 
   *
   * @throws TmlSidexException 
   */
  public void loadFromFile(String path) throws TmlSidexException{
    m_sidex.sidex_Load_Content(m_var, path);
  }

  /**
   * @brief save content to a file
   *
   * @param path filename and path 
   *
   * @throws TmlSidexException 
   */
  public void saveToFile(String path) throws TmlSidexException{
    m_sidex.sidex_Save_Content(m_var, path);
  }
  
  /**
   * @brief read value
   *
   * @param group group name
   * @param key key name
   *
   * @return SDXBase driven data object
   *
   * @throws TmlSidexException 
   */
  public SDXBase getValue(String group, String key) throws TmlSidexException{
    SDXBase retValue = null;
    
    long variant = m_sidex.sidex_Variant_Read(m_var, group, key);
    retValue = SDXUtils.sidexVariantAsJavaObject(m_sidex, variant);
    return retValue;
  }
  
  /**
   * @brief set SDXNone value
   *
   * @param group group name
   * @param key key name
   *
   * @throws TmlSidexException 
   */
  public void setValue(String group, String key) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_New_None();
    m_sidex.sidex_Variant_Write(m_var, group, key, variant);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
 /**
   * @brief write value
   *
   * @param group group name
   * @param key key name
   * @param sVal the Object data to write of type
   *        integer, long, float, double, boolean, Integer, Long, Float, Double, Boolean, String or SDXBase
   *
   * @throws TmlSidexException 
   */
  public void setValue(String group, String key, Object sVal) throws TmlSidexException{
    long variant = SDXUtils.javaObjectAsSidexVariant(m_sidex, sVal);
    m_sidex.sidex_Variant_Write(m_var, group, key, variant);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  

  /**
   * @brief check if group exists
   *
   * @param group name of the request
   * 
   * @return True if the group exists
   */
  public boolean hasGroup(String group){
    return m_sidex.sidex_HasGroup(m_var, group);
  }
  
  /**
   * @brief get a list of group names
   *
   * @return String array of group names
   *
   * @throws TmlSidexException 
   */
  public String[] getGroups() throws TmlSidexException{
    
    long groups = m_sidex.sidex_GetGroups(m_var);
    int length = m_sidex.sidex_Variant_List_Size (groups);
    
    String[] strRet = new String[length];
    for (int i = 0; i < length; ++i){
      long variant = m_sidex.sidex_Variant_List_Get (groups, i);
      String sGroup = m_sidex.sidex_Variant_As_String (variant);
      strRet[i] = sGroup;
    }
    m_sidex.sidex_Variant_DecRef(groups);
    return strRet;
  }

  /**
   * @brief check if a key exists
   *
   * @param group name of the request
   * @param key name of the request
   * 
   * @return True if the key exists
   */
  public boolean hasKey(String group,  String key){
    return m_sidex.sidex_HasKey(m_var, group, key);
  }
  
  /**
   * @brief get a list of key names for a group
   *
   * @param group name of the request
   * 
   * @return String array of key names
   *
   * @throws TmlSidexException 
   */
  public String[] getKeys(String group) throws TmlSidexException{
    
    long keys = m_sidex.sidex_GetKeys(m_var, group);
    int length = m_sidex.sidex_Variant_List_Size (keys);
    
    String[] strRet = new String[length];
    for (int i = 0; i < length; ++i){
      long key = m_sidex.sidex_Variant_List_Get (keys, i);
      String sKey = m_sidex.sidex_Variant_As_String (key);
      strRet[i] = sKey;
    }
    m_sidex.sidex_Variant_DecRef(keys);
    return strRet;
  }
  
 /**
   * @brief delete a group from the document
   *
   * @param group group name
   *
   * @throws TmlSidexException 
   */
  public void deleteGroup(String group) throws TmlSidexException{
    m_sidex.sidex_DeleteGroup(m_var, group);
  }

  /**
   * @brief delete key/value pair from the document
   *
   * @param group group name
   * @param key key name
   *
   * @throws TmlSidexException 
   */
  public void deleteKey(String group, String key) throws TmlSidexException{
    m_sidex.sidex_DeleteKey(m_var, group, key);
  }

 /**
   * @brief write a group of values
   *
   * @param group group name
   * @param dict dictionary with string keys and values
   *
   * @throws TmlSidexException 
   */
  // maybe for later use:
  private void setGroup(String group, Dictionary<String, Object> dict) throws TmlSidexException{
    Enumeration<String> keys = dict.keys();
    if (this.hasGroup(group)){
      this.deleteGroup(group);
    }
    while (keys.hasMoreElements()){
      Object keyObj = keys.nextElement();
      Object valObj = dict.get(keyObj);
      if (valObj instanceof SDXBase){
        long variant = 0;
        variant = ((SDXBase)valObj).getVhandle();
        m_sidex.sidex_Variant_Write(m_var, group, (String)keyObj, variant);
      }
      else{
        if (valObj instanceof Integer){
          m_sidex.sidex_Integer_Write(m_var, group, (String)keyObj, ((Integer)valObj).longValue());
        }
        else{
          if (valObj instanceof Long){
            m_sidex.sidex_Integer_Write(m_var, group, (String)keyObj, ((Long)valObj).longValue());
          }
          else{
            if (valObj instanceof Float){
              m_sidex.sidex_Float_Write(m_var, group, (String)keyObj, ((Float)valObj).doubleValue());
            }
            else{
              if (valObj instanceof Double){
                m_sidex.sidex_Float_Write(m_var, group, (String)keyObj, ((Double)valObj).doubleValue());
              }
              else{
                if (valObj instanceof Boolean){
                  m_sidex.sidex_Boolean_Write(m_var, group, (String)keyObj, ((Boolean)valObj).booleanValue());
                }
                else{
                  if (valObj instanceof String){
                    m_sidex.sidex_String_Write(m_var, group, (String)keyObj, (String)valObj);
                  }
                  else{
                    TmlSidexException e = new TmlSidexException("SDXDocument::writeGroup / unsupported value type", SidexErr.SIDEX_ERR_WRONG_PARAMETER);
                    throw e;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
