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

/** @defgroup sdxGroup SIDEX API
 *  @ingroup basicAPI
 *  @brief SIDEX API functions.
 */

/** @ingroup sdxGroup
 *  @defgroup sdxgeneral General functions
 *  @brief General SIDEX API functions.
 */

/** @ingroup sdxGroup
 *  @defgroup sdxreturn Return values
 *  @brief Return values of SIDEX API function calls.
 */

/** @ingroup sdxGroup
 *  @defgroup sidexHandle SIDEX document
 *  @brief Handling of SIDEX documents
 */

/** @ingroup sidexHandle
 *  @defgroup navigationType Group/Key managment
 *  @brief Managing groups and keys in SIDEX documents
 */

/** @ingroup sidexHandle
 *  @defgroup sdxDocRWValues Read/Write values
 *  @brief Read/Write values from/to the SIDEX document.
 */

/** @ingroup sdxGroup
 *  @defgroup datatypes SIDEX datatypes
 *  @brief SIDEX API for supported datatypes
 */

/** @ingroup datatypes
 *  @defgroup varianttype General variant functions
 *  @brief General functions to handle data objects.
 */

/** @ingroup datatypes
 *  @defgroup simpletypes Simple data types
 *  @brief Simple data objects.
 */

/** @ingroup datatypes
 *  @defgroup complextypes container types
 *  @brief Datatypes containing multiple variant values.
 */

/** @ingroup datatypes
 *  @defgroup typeconst type id constants
 *  @brief Type id constants
 */

/** @ingroup simpletypes
 *  @defgroup inttype Integer
 *  @brief Handling of integer values
 */

/** @ingroup simpletypes
 *  @defgroup nonetype None
 *  @brief Handling of None values
 */

/** @ingroup simpletypes
 *  @defgroup booltype Boolean
 *  @brief Handling of Boolean values
 */

/** @ingroup simpletypes
 *  @defgroup floattype Float
 *  @brief Handling of Float values
 */

/** @ingroup simpletypes
 *  @defgroup stringtype String
 *  @brief Handling of String values
 */

/** @ingroup simpletypes
 *  @defgroup bintype Binary
 *  @brief Handling of Binary data
 */

/** @ingroup simpletypes
 *  @defgroup datetimetype DateTime
 *  @brief Handling of DateTime values
 */

/** @ingroup complextypes
 *  @defgroup listtype List
 *  @brief A list of values.
 */

/** @ingroup complextypes
 *  @defgroup dicttype Dictionary
 *  @brief Values organized in a dictionary with a string key.
 */

/** @ingroup complextypes
 *  @defgroup tabletype Table
 *  @brief A table with rows and columns.
 */


package com.tmlsidex.jni;

import java.nio.ByteBuffer;

/**
 * Native SIDEX API functions.
 **/                

public class Sidex {
  
  ///////////////////////
  // General:
  private native void    NativeSidexGetCopyright(StringBuffer sValue, int[] iLength);
  private native void    NativeSidexGetVersion(int[] apiVer, int[] libVer, StringBuffer vderStr);
  private native int     NativeSidexSetPassword(String pUserName, String pPassWord);

  ///////////////////////
  // SIDEX document:
  private native int     NativeSidexCreate(String sDoc, long[] sHandle);
  private native void    NativeSidexFree(long sHandle);
  //                    sidex_Free_Content is not necessary on JAVA
  private native void    NativeSidexClear(long sHandle);
  private native int     NativeSidexGetContent(long sHandle, StringBuffer sContent, int[] iLength);
  private native int     NativeSidexGetContentLength(long sHandle, int[] iLength);
  private native int     NativeSidexGetDocumentName(long sHandle, StringBuffer sName);
  private native int     NativeSidexMerge(long sBaseHandle, long sMergeHandle, boolean bOverwrite, String sGroup, String sKey);
  private native int     NativeSidexSaveContent(long sHandle, String sPath);
  private native int     NativeSidexLoadContent(long sHandle, String sPath);
  private native int     NativeSidexSetContent(long sHandle, String sContent);
  private native int     NativeSidexSetDocumentName(long sHandle, String sName);
  ////////////////////////////////////////////////
  //SIDEX document / Group/Key management
  private native int     NativeSidexDeleteGroup(long sHandle, String sGroup);
  private native int     NativeSidexDeleteKey(long sHandle, String sGroup, String sKey);
  private native int     NativeSidexGetGroups(long sHandle, long[] variant);
  private native int     NativeSidexGetKeys(long sHandle, String sGroup, long[] variant);
  private native boolean NativeSidexHasGroup(long sHandle, String sGroup);
  private native boolean NativeSidexHasKey(long sHandle, String sGroup, String sKey);
  ////////////////////////////////////
  // SIDEX document Read/Write values
  private native int     NativeSidexBinaryLength(long sHandle, String sGroup, String sKey, int[] iStrLength);
  private native int     NativeSidexBinaryRead(long sHandle, String sGroup, String sKey, ByteBuffer value, int[] iStrLength);
  private native int     NativeSidexBinaryWrite(long sHandle, String sGroup, String sKey, byte[] value, int iSize);
  private native int     NativeSidexBooleanRead(long sHandle, String sGroup, String sKey, boolean[] value);
  private native int     NativeSidexBooleanWrite(long sHandle, String sGroup, String sKey, boolean value);
  private native int     NativeSidexDateTimeRead(long sHandle, String sGroup, String sKey, long[] value);
  private native int     NativeSidexDateTimeWrite(long sHandle, String sGroup, String sKey,long value);
  private native int     NativeSidexDictRead(long sHandle, String sGroup, String sKey, long[] value);
  private native int     NativeSidexDictWrite(long sHandle, String sGroup, String sKey,long value);
  private native int     NativeSidexFloatRead(long sHandle, String sGroup, String sKey, double[] value);
  private native int     NativeSidexFloatWrite(long sHandle, String sGroup, String sKey,double value);
  //                     sidex_Free_Binary_ReadString is not necessary on JAVA
  //                     sidex_Free_ReadString is not necessary on JAVA
  private native int     NativeSidexIntegerRead(long sHandle, String sGroup, String sKey, long[] value);
  private native int     NativeSidexIntegerWrite(long sHandle, String sGroup, String sKey,long value);
  private native int     NativeSidexListRead(long sHandle, String sGroup, String sKey, long[] value);
  private native int     NativeSidexListWrite(long sHandle, String sGroup, String sKey,long value);
  private native int     NativeSidexNoneWrite(long sHandle, String sGroup, String sKey);
  private native int     NativeSidexStringLength(long sHandle, String sGroup, String sKey, int[] iLength);
  private native int     NativeSidexStringRead(long sHandle, String sGroup, String sKey, StringBuffer sValue, int[] iLength);
  private native int     NativeSidexStringWrite(long sHandle, String sGroup, String sKey, String sValue);
  private native int     NativeSidexTableRead(long sHandle, String sGroup, String sKey, long[] value);
  private native int     NativeSidexTableWrite(long sHandle, String sGroup, String sKey,long value);
  private native int     NativeSidexVariantRead(long sHandle, String sGroup, String sKey, long[] sVariant);
  private native int     NativeSidexVariantWrite(long sHandle, String sGroup, String sKey,long sVariant);
  
  ////////////////////////////////////////////////
  //SIDEX datatypes / General variant functions
  private native long    NativeSidexVariantCopy(long variant);
  private native int     NativeSidexVariantDecRef(long variant);
  private native int     NativeSidexVariantGetType(long variant);
  private native void    NativeSidexVariantIncRef(long variant);
  
  ////////////////////////////////////////////////
  //Simple data types / Integer
  private native int     NativeSidexVariantAsInteger(long variant, long[] value);
  private native boolean NativeSidexVariantIntegerCheck(long variant);
  private native long    NativeSidexVariantNewInteger(long value);
  
  ////////////////////////////////////////////////
  //Simple data types / None
  private native boolean NativeSidexVariantNoneCheck(long variant);
  private native long    NativeSidexVariantNewNone();
  
  ////////////////////////////////////////////////
  //Simple data types / Boolean
  private native int     NativeSidexVariantAsBoolean(long variant, boolean[] value);
  private native boolean NativeSidexVariantBooleanCheck(long variant);
  private native long    NativeSidexVariantNewBoolean(boolean value);
  
  ////////////////////////////////////////////////
  //Simple data types / Float
  private native int     NativeSidexVariantAsFloat(long variant, double[] value);
  private native boolean NativeSidexVariantFloatCheck(long variant);
  private native long    NativeSidexVariantNewFloat(double value);
  
  ////////////////////////////////////////////////
  //Simple data types / String
  private native int     NativeSidexVariantAsString(long variant, StringBuffer sValue, int[] iLength);
  private native int     NativeSidexVariantAsStringLength(long variant, int[] iLength);
  private native int     NativeSidexVariantNewString(String value, long[] variant);
  private native boolean NativeSidexVariantStringCheck(long variant);
  private native int     NativeSidexVariantStringGetFormat(long variant, StringBuffer sValue);
  private native int     NativeSidexVariantStringSetFormat(long variant, String sValue);
  
  ////////////////////////////////////////////////
  //Simple data types / Binary
  private native int     NativeSidexVariantAsBinary(long variant, ByteBuffer sValue, int[] iLength);
  private native int     NativeSidexVariantAsBinaryLength(long variant, int[] iLength);
  private native boolean NativeSidexVariantBinaryCheck(long variant);
  private native int     NativeSidexVariantNewBinary(byte[] value, int length, long[] variant);
  
  ////////////////////////////////////////////////
  //Simple data types / DateTime
  private native int     NativeSidexVariantAsDateTime(long variant, StringBuffer sValue);
  private native boolean NativeSidexVariantDateTimeCheck(long variant);
  private native int     NativeSidexVariantNewDateTime(String value, long[]variant);
  
  ////////////////////////////////////////////////
  //Comntainer data types / List
  private native int     NativeSidexVariantListAppend(long list, long value, int[]pos);
  private native boolean NativeSidexVariantListCheck(long list);
  private native int     NativeSidexVariantListClear(long list);
  private native int     NativeSidexVariantListDeleteItem(long list, int pos);
  private native int     NativeSidexVariantListGet(long list, int index, long[] variant);
  private native int     NativeSidexVariantListInsert(long list, long value, int pos);
  private native int     NativeSidexVariantListSet(long list, long value, int pos);
  private native int     NativeSidexVariantListSize(long list, int[] size);
  private native long    NativeSidexVariantNewList();
  
  ////////////////////////////////////////////////
  //Comntainer data types / Dict
  private native boolean NativeSidexVariantDictCheck(long dict);
  private native int     NativeSidexVariantDictClear(long dict);
  private native int     NativeSidexVariantDictDelete(long dict, String sKey);
  private native int     NativeSidexVariantDictFirst(long dict);
  private native int     NativeSidexVariantDictGet(long dict, String sKey, long[] variant);
  private native int     NativeSidexVariantDictHasKey(long dict, String sKey, boolean[] bRet);
  private native int     NativeSidexVariantDictKeys(long dict, long[] keys);
  private native int     NativeSidexVariantDictNext(long dict, StringBuffer sKey, long[] variant);
  private native int     NativeSidexVariantDictSet(long dict, String sKey, long value);
  private native int     NativeSidexVariantDictSize(long dict, int[] size);
  private native long    NativeSidexVariantNewDict();
  private native long    NativeSidexVariantNewDictBySize(int size);
  
  
  ////////////////////////////////////////////////
  //Comntainer data types / Table
  private native long    NativeSidexVariantNewTable();  
  private native int     NativeSidexVariantTableAddColumn(long table, String sColumn);
  private native int     NativeSidexVariantTableAddRow(long table, int[] iRowIdx);
  private native boolean NativeSidexVariantTableCheck(long table);
  private native int     NativeSidexVariantTableColumnNames(long table, long[] varNames);
  private native int     NativeSidexVariantTableColumns(long table, int[] iColumns);
  private native int     NativeSidexVariantTableDeleteColumn(long table, String sColumn);
  private native int     NativeSidexVariantTableDeleteRow(long table, int iRowIdx);
  private native int     NativeSidexVariantTableDeleteRows(long table);
  private native int     NativeSidexVariantTableGetColumnName(long table, int index, StringBuffer sColumn);
  private native int     NativeSidexVariantTableGetField(long table, int rowIndex, String sColumnName, long[] variant);
  private native int     NativeSidexVariantTableGetRow(long table, int rowIndex, long[] variant);
  private native int     NativeSidexVariantTableHasColumn(long table, String sColumnName, boolean[] bRet);
  private native int     NativeSidexVariantTableRows(long dict, int[] rows);
  private native int     NativeSidexVariantTableSetField(long table, int rowIndex, String sColumnName, long variant);

  
  
  
  
  ///////////////////////
  // General:
  /**
   * \addtogroup sdxgeneral
   * @{
   */

  /**
   * @ingroup sdxgeneral
   * @brief get copyright information
   *
   * @param iLength array /element 0 returns the length of the copyright information
   * 
   * @return copyright information
   */
  public String sidex_Get_Copyright(int[] iLength){
    StringBuffer buffer = new StringBuffer("");
    NativeSidexGetCopyright(buffer, iLength);
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief get interface api and library version
   *
   * @param iApiVer   array of size 1 - returns api version
   * @param iLibVer   array of size 1 - returns library version
   * 
   * @return copyright information
   */
  public String sidex_Get_Version(int[] iApiVer, int[] iLibVer){
    StringBuffer buffer = new StringBuffer("");
    NativeSidexGetVersion(iApiVer, iLibVer, buffer);
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief set the license key
   * 
   * efore the sdk functions can be used a license key must be applied.
   *
   * @note This call is deprecated. It returns allways SIDEX_SUCCESS.<br>
   *       It has been left in the API to be backward compatible.
   *
   * @param pUserName user name (case sensitive)
   * @param pPassWord password (case insensitive)
   */
  public void sidex_Set_Password(String pUserName, String pPassWord) throws TmlSidexException{
    int iRet =  NativeSidexSetPassword(pUserName, pPassWord);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Set_Password / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */

  ///////////////////////
  // SIDEX document:

  /**
   * \addtogroup sidexHandle
   * @{
   */

  /**
   * @brief remove all values from the sidex document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   */
  public void sidex_Clear(long sHandle){
    NativeSidexClear(sHandle);
    return;
  }

  /**
   * @brief create a SIDEX document handle
   * 
   * SIDEX documents contain data based on SIDEX data objects organized in groups containing a number of keys.
   * Groups and keys are names to find a specific value in the document.
   * The document can be exported to/imported from an xml string or file.
   * 
   * @param sDoc   name of the document/xml root node
   * 
   * @return SIDEX document handle (SIDEX_HANDLE). Use sidex_Free() to deallocate handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Create(String sDoc) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexCreate(sDoc, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Create / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
  
  /**
   * @brief release a SIDEX document handle
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   */
  public void sidex_Free(long sHandle){
    NativeSidexFree(sHandle);
    return;
  }
  

  /**
   * @brief get the SIDEX document data as string
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * 
   * @return SIDEX document data as string
   *
   * @throws TmlSidexException 
   */
  public String sidex_Get_Content(long sHandle) throws TmlSidexException{
    int[] iLength = new int[1];
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeSidexGetContent(sHandle, buffer, iLength);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("sidex_Get_Content / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    iLength = null;
    return sRet;
  }
  
  /**
   * @brief  get length of the document content
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * 
   * @return Reference to the length of the document content
   *
   * @throws TmlSidexException
   * @see Sidex#sidex_Get_Content(long)
   */
  public int sidex_Get_Content_Length(long sHandle) throws TmlSidexException{
    int[] iLength = new int[1];
    int iRet = NativeSidexGetContentLength(sHandle, iLength);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Get_Content_Length / Error "+Integer.toString(iRet), iRet);
    }
    int iContLength = iLength[0];
    iLength = null;
    return iContLength;
  }
  
  /**
   * @brief get the document name
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * 
   * @return name of the document
   *
   * @throws TmlSidexException 
   */
  public String sidex_Get_DocumentName(long sHandle) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeSidexGetDocumentName(sHandle, buffer);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("sidex_Get_DocumentName / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief read a SIDEX document from a file
   *
   * @note If the document already contains data it will be deleted.
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sPath     path to SIDEX file
   * 
   * @throws TmlSidexException 
   */
  public void sidex_Load_Content(long sHandle, String sPath) throws TmlSidexException{
    int iRet = NativeSidexLoadContent(sHandle, sPath);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Load_Content / Error "+Integer.toString(iRet), iRet);
    }
  }


  /**
   * @brief merge SIDEX document data
   *
   * @param sBaseHandle SIDEX document handle (SIDEX_HANDLE)
   * @param sMergeHandle  SIDEX document handle (SIDEX_HANDLE) to be merged
   * @param bOverwrite  if true the existing group,key pairs will be overwritten
   * @param sGroup  name of the group
   * @param sKey  name of the key or SIDEX_HANDLETYPE_NULL (complete group)
   *
   * @throws TmlSidexException 
   */
  public void sidex_Merge(long sBaseHandle, long sMergeHandle, boolean bOverwrite, String sGroup, String sKey) throws TmlSidexException{
    int iRet = NativeSidexMerge(sBaseHandle, sMergeHandle, bOverwrite, sGroup, sKey);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Merge / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief Write a SIDEX document to a file
   *
   * @note If the file exits, it will be replaced.
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sPath     file path and name
   * 
   * @throws TmlSidexException 
   */
  public void sidex_Save_Content(long sHandle, String sPath) throws TmlSidexException{
    int iRet = NativeSidexSaveContent(sHandle, sPath);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Save_Content / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief read a SIDEX document from a string
   *
   * @note If the document already contains data it will be deleted.
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sContent  SIDEX document data
   * 
   * @throws TmlSidexException 
   */
  public void sidex_Set_Content(long sHandle, String sContent) throws TmlSidexException{
    int iRet = NativeSidexSetContent(sHandle, sContent);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Set_Content / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief change the document name
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sName   name of the document/xml root node
   *
   * @throws TmlSidexException 
   */
  public void sidex_Set_DocumentName(long sHandle, String sName) throws TmlSidexException{
    int iRet = NativeSidexSetDocumentName(sHandle, sName);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Set_DocumentName / Error "+Integer.toString(iRet), iRet);
    }
  }

  /* @} */


  ////////////////////////////////////////////////
  //SIDEX document / Group/Key management
  /**
   * \addtogroup navigationType
   * @{
   */

  /**
   * @brief delete a group from the document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   *
   * @throws TmlSidexException 
   */
  public void sidex_DeleteGroup(long sHandle, String sGroup)throws TmlSidexException{
    int iRet = NativeSidexDeleteGroup(sHandle, sGroup);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_DeleteGroup / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief delete a key, value pair from the document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   *
   * @throws TmlSidexException 
   */
  public void sidex_DeleteKey(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    int iRet = NativeSidexDeleteKey(sHandle, sGroup, sKey);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_DeleteKey / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get a list of group names
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * 
   * @return SIDEX variant List value. The list contains all group names of the SIDEX document
   * and must be released by sidex_Variant_DecRef().
   *
   * @throws TmlSidexException 
   */
  public long sidex_GetGroups(long sHandle) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexGetGroups(sHandle, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_GetGroups / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }

  /**
   * @brief get a list of key names in a group
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group 
   * 
   * @return SIDEX variant List value. The list contains all key names of the sGroup of the SIDEX document
   * and must be released by sidex_Variant_DecRef().
   *
   * @throws TmlSidexException 
   */
  public long sidex_GetKeys(long sHandle, String sGroup) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexGetKeys(sHandle, sGroup, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_GetKeys / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
    
  /**
   * @brief check if a group exists
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group 
   * 
   * @return true if group exists
   */
  public boolean sidex_HasGroup(long sHandle, String sGroup){
    boolean bRet = NativeSidexHasGroup(sHandle, sGroup);
    return bRet;
  }
    
  /**
   * @brief check if a key exists in a group
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group 
   * @param sKey   name of the key 
   * 
   * @return true if the key exists in the group
   */
  public boolean sidex_HasKey(long sHandle, String sGroup, String sKey){
    boolean bRet = NativeSidexHasKey(sHandle, sGroup, sKey);
    return bRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //SIDEX document / Read/Write values

  /**
   * \addtogroup sdxDocRWValues
   * @{
   */

  /**
   * @brief get the number of bytes of a binary value in a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return the byte count
   *
   * @throws TmlSidexException 
   */
  public int sidex_Binary_Length(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    int[] iStrLength = new int[1];
    int iRet = NativeSidexBinaryLength(sHandle, sGroup, sKey, iStrLength);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      iStrLength = null;
      throw new TmlSidexException("sidex_Binary_Length / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = iStrLength[0]; 
    iStrLength = null;
    return iLength;
  }

  /**
   * @brief get a binary value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param iLength   array of size 1 - returns the byte count
   * 
   * @return  binary data 
   *
   * @throws TmlSidexException 
   */
  public byte[] sidex_Binary_Read(long sHandle, String sGroup, String sKey,int[] iLength) throws TmlSidexException{
    int iBinLength;
    iBinLength = sidex_Binary_Length(sHandle, sGroup, sKey);
    ByteBuffer bStr = ByteBuffer.allocateDirect (iBinLength);
    int iRet = NativeSidexBinaryRead(sHandle, sGroup, sKey, bStr, iLength);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Binary_Read / Error "+Integer.toString(iRet), iRet);
    }
    byte[] bArray = new byte[iLength[0]];
    for (int i = 0; i < iLength[0]; ++i){
      bArray[i] = bStr.get(i);
    }
    bStr = null;
    return bArray;
  }

  /**
   * @brief write a binary value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value   byte array of binary data to write
   * @param iSize     length of binary data
   *
   * @throws TmlSidexException 
   */
  public void sidex_Binary_Write(long sHandle, String sGroup, String sKey, byte[] value, int iSize) throws TmlSidexException{
    int iRet = NativeSidexBinaryWrite(sHandle, sGroup, sKey, value, iSize);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Binary_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get a boolean value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  boolean value
   *
   * @throws TmlSidexException 
   */
  public boolean sidex_Boolean_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    boolean[] value = new boolean[1];
    int iRet = NativeSidexBooleanRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Boolean_Read / Error "+Integer.toString(iRet), iRet);
    }
    boolean bValue = value[0];
    value = null;
    return bValue;
  }

  /**
   * @brief write a boolean value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value   boolean data to write
   *
   * @throws TmlSidexException 
   */
  public void sidex_Boolean_Write(long sHandle, String sGroup, String sKey, boolean value) throws TmlSidexException{
    int iRet = NativeSidexBooleanWrite(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Boolean_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get a datetime value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  SIDEX_VARIANT handle
   *
   * @throws TmlSidexException 
   */
  public long sidex_DateTime_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexDateTimeRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_DateTime_Read / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
    
  /**
   * @brief write a dateTime value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value  SIDEX_VARIANT handle of dateTime value
   *
   * @throws TmlSidexException 
   */
  public void sidex_DateTime_Write(long sHandle, String sGroup, String sKey,long value) throws TmlSidexException{
    int iRet = NativeSidexDateTimeWrite(sHandle, sGroup, sKey,value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_DateTime_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get a dictionary value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  SIDEX_VARIANT handle
   *
   * @throws TmlSidexException 
   */
  public long sidex_Dict_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexDictRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Dict_Read / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
    
  /**
   * @brief write a dictionary value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value  SIDEX_VARIANT handle of dictionary value
   *
   * @throws TmlSidexException 
   */
  public void sidex_Dict_Write(long sHandle, String sGroup, String sKey,long value) throws TmlSidexException{
    int iRet = NativeSidexDictWrite(sHandle, sGroup, sKey,value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Dict_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get a float value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  float value
   *
   * @throws TmlSidexException 
   */
  public double sidex_Float_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    double[] value = new double[1];
    int iRet = NativeSidexFloatRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Float_Read / Error "+Integer.toString(iRet), iRet);
    }
    double dRet = value[0];
    value = null;
    return dRet;
  }
    
  /**
   * @brief write a float value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value  float value
   *
   * @throws TmlSidexException 
   */
  public void sidex_Float_Write(long sHandle, String sGroup, String sKey, double value) throws TmlSidexException{
    int iRet = NativeSidexFloatWrite(sHandle, sGroup, sKey,value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Float_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get an integer value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  integer value
   *
   * @throws TmlSidexException 
   */
  public long sidex_Integer_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexIntegerRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Integer_Read / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
    
  /**
   * @brief write an integer value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value  integer value
   *
   * @throws TmlSidexException 
   */
  public void sidex_Integer_Write(long sHandle, String sGroup, String sKey,long value) throws TmlSidexException{
    int iRet = NativeSidexIntegerWrite(sHandle, sGroup, sKey,value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Integer_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get a list value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  SIDEX_VARIANT handle
   *
   * @throws TmlSidexException 
   */
  public long sidex_List_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexListRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_List_Read / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
    
  /**
   * @brief write a list value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value  SIDEX_VARIANT handle of list value
   *
   * @throws TmlSidexException 
   */
  public void sidex_List_Write(long sHandle, String sGroup, String sKey,long value) throws TmlSidexException{
    int iRet = NativeSidexListWrite(sHandle, sGroup, sKey,value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_List_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief write a none value (empty value) to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   *
   * @throws TmlSidexException 
   */
  public void sidex_None_Write(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    int iRet = NativeSidexNoneWrite(sHandle, sGroup, sKey);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_None_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief wet the length of a string value in a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return string length
   *
   * @throws TmlSidexException 
   */
  public int sidex_String_Length(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexStringLength(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_String_Length / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }

  /**
   * @brief get a string value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  String data 
   *
   * @throws TmlSidexException 
   */
  public String sidex_String_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    int[] iLength = new int[1];
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeSidexStringRead(sHandle, sGroup, sKey, buffer, iLength);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("sidex_String_Read / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    iLength = null;
    return sRet;
  }

  /**
   * @brief write a string value to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param sValue  String value
   *
   * @throws TmlSidexException 
   */
  public void sidex_String_Write(long sHandle, String sGroup, String sKey, String sValue) throws TmlSidexException{
    int iRet = NativeSidexStringWrite(sHandle, sGroup, sKey, sValue);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_String_Write / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get a table from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  SIDEX_VARIANT handle
   *
   * @throws TmlSidexException 
   */
  public long sidex_Table_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexTableRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Table_Read / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
    
  /**
   * @brief write a table to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param value  SIDEX_VARIANT handle of table value
   *
   * @throws TmlSidexException 
   */
  public void sidex_Table_Write(long sHandle, String sGroup, String sKey,long value) throws TmlSidexException{
    int iRet = NativeSidexTableWrite(sHandle, sGroup, sKey,value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Table_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief get a boolean value from a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * 
   * @return  SIDEX_VARIANT handle reference. This call increments the reference counter of the SIDEX_VARIANT.
   * It must be decremented (sidex_Variant_DecRef()), if the value is no longer used.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_Read(long sHandle, String sGroup, String sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantRead(sHandle, sGroup, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Read / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
    
  /**
   * @brief write a variant to a SIDEX document
   *
   * @param sHandle   SIDEX document handle (SIDEX_HANDLE) 
   * @param sGroup   name of the group
   * @param sKey   name of the key
   * @param sVariant  SIDEX_VARIANT handle
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Write(long sHandle, String sGroup, String sKey,long sVariant) throws TmlSidexException{
    int iRet = NativeSidexVariantWrite(sHandle, sGroup, sKey,sVariant);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Write / Error "+Integer.toString(iRet), iRet);
    }
  }

  /* @} */

  ////////////////////////////////////////////////
  //SIDEX datatypes / General variant functions

  /**
   * \addtogroup varianttype
   * @{
   */

  /**
   * @brief get a copy of a variant handle
   *
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  a copy of a SIDEX_VARIANT handle on success or 0 on error 
   */
  public long sidex_Variant_Copy(long variant){
    long vCopy = NativeSidexVariantCopy(variant);
    return vCopy;
  }

  /**
   * @brief decrement the reference counter of a SIDEX_VARIANT.
   * 
   * A reference counter value of 0 signals that there is no more reference to the object and it will
   * be released.
   *
   * @param variant  SIDEX_VARIANT handle
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_DecRef(long variant) throws TmlSidexException{
    int iRet = NativeSidexVariantDecRef(variant);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_DecRef / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get variant type
   *
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  type of the variant
   * 
   * @see SidexType
   */
  public int sidex_Variant_GetType(long variant){
    int iRet = NativeSidexVariantGetType(variant);
    return iRet;
  }
  
  /**
   * @brief increment the reference counter of a variant.
   * 
   * If a new reference to the variant handle is created, this function must be called. Many of the SIDEX API
   * functions increment or decrement the reference counter automatically.
   * Please refer to the particular function description.
   *
   * @param variant  SIDEX_VARIANT handle
   */
  public void sidex_Variant_IncRef(long variant){
    NativeSidexVariantIncRef(variant);
    return;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Simple data types / Integer

  /**
   * \addtogroup inttype
   * @{
   */

  /**
   * @brief get the value from an integer variant
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  integer value
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_As_Integer(long variant) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantAsInteger(variant, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_As_Integer / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }

  /**
   * @brief check if variant type is integer
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is integer
   *
   */
  public boolean sidex_Variant_Integer_Check(long variant){
    boolean bRet = NativeSidexVariantIntegerCheck(variant);
    return bRet;
  }
  
  /**
   * @brief create an integer variant
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @param value   integer value
   *
   * @return  SIDEX_VARIANT handle.
   */
  public long sidex_Variant_New_Integer(long value){
    long lRet = NativeSidexVariantNewInteger(value);
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Simple data types / None

  /**
   * \addtogroup nonetype
   * @{
   */

  /**
   * @brief check if variant type is none
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is none
   *
   */
  public boolean sidex_Variant_None_Check(long variant){
    boolean bRet = NativeSidexVariantNoneCheck(variant);
    return bRet;
  }
  
  /**
   * @brief create a variant of type none.
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @return  SIDEX_VARIANT handle.
   */
  public long sidex_Variant_New_None(){
    long lRet =  NativeSidexVariantNewNone();
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Simple data types / Boolean

  /**
   * \addtogroup booltype
   * @{
   */

  /**
   * @brief get the value from an boolean variant.
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  boolean value
   *
   * @throws TmlSidexException 
   */
  public boolean sidex_Variant_As_Boolean(long variant) throws TmlSidexException{
    boolean[] value = new boolean[1]; 
    int iRet = NativeSidexVariantAsBoolean(variant, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_As_Boolean / Error "+Integer.toString(iRet), iRet);
    }
    boolean bRet = value[0];
    value = null;
    return bRet;
  }

  /**
   * @brief check if variant type is boolean
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is boolean
   *
   */
  public boolean sidex_Variant_Boolean_Check(long variant){
    boolean bRet = NativeSidexVariantBooleanCheck(variant);
    return bRet;
  }
  
  /**
   * @brief create a variant of type boolean
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @param value   boolean value
   *
   * @return  SIDEX_VARIANT handle.
   */
  public long   sidex_Variant_New_Boolean(boolean value){
    long lRet = NativeSidexVariantNewBoolean(value);
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Simple data types / Float

  /**
   * \addtogroup floattype
   * @{
   */

  /**
   * @brief get the value from an float variant.
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  float value
   *
   * @throws TmlSidexException 
   */
  public double sidex_Variant_As_Float(long variant) throws TmlSidexException{
    double[] value = new double[1];
    int iRet = NativeSidexVariantAsFloat(variant, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_As_Float / Error "+Integer.toString(iRet), iRet);
    }
    double dRet = value[0];
    value = null;
    return dRet;
  }

  /**
   * @brief check if variant type is float.
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is float
   *
   */
  public boolean sidex_Variant_Float_Check(long variant){
    boolean bRet = NativeSidexVariantFloatCheck(variant);
    return bRet;
  }
  
  /**
   * @brief create a variant of type float.
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @param value   float value
   *
   * @return  SIDEX_VARIANT handle.
   */
  public long sidex_Variant_New_Float(double value){
    long lRet = NativeSidexVariantNewFloat(value);
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Simple data types / String

  /**
   * \addtogroup stringtype
   * @{
   */

  /**
   * @brief get value and length from a string variant
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  String value
   *
   * @throws TmlSidexException 
   */
  public String sidex_Variant_As_String(long variant) throws TmlSidexException{
    int[] iLength = new int[1];
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeSidexVariantAsString(variant, buffer, iLength);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("sidex_Variant_As_String / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    iLength = null;
    return sRet;
  }

  /**
  * @brief get the length from a string variant
  * 
  * @param variant  SIDEX_VARIANT handle
  *
  * @return  string length
  *
  * @throws TmlSidexException 
  */
  public int sidex_Variant_As_String_Length(long variant) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexVariantAsStringLength(variant, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_As_String_Length / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }

  /**
   * @brief check if variant type is string
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is string
   *
   */
  public boolean sidex_Variant_String_Check(long variant){
    boolean bRet = NativeSidexVariantStringCheck(variant);
    return bRet;
  }
  
  /**
   * @brief get a string format attribute
   *
   * A string can be formatted in a special way. The format attribute allows to
   * store this format information with the string.
   *
   * Valid formats: 
   *
   *  <TABLE>
   *   <TR><TD><B>Name       </B></TD><TD><B>Description  </B></TD></TR>
   *   <TR><TD>unknown           </TD><TD>no special format (default)</TD></TR>
   *   <TR><TD>sidex           </TD><TD>SIDEX formatted string</TD></TR>
   *   <TR><TD>xml             </TD><TD>XML formatted string</TD></TR>
   *   <TR><TD>json            </TD><TD>JSON formatted string</TD></TR>
   *   <TR><TD>cvs             </TD><TD>comma seperated fields</TD></TR>
   *   <TR><TD>ini             </TD><TD>window ini format</TD></TR>
   *  </TABLE>
   *
   * @param variant  SIDEX_VARIANT handle
   *
   * @return   format of the string
   *
   * @throws TmlSidexException 
   */
  public String sidex_Variant_String_GetFormat(long variant) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeSidexVariantStringGetFormat(variant, buffer);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("sidex_Variant_String_GetFormat / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief set a string format attribute
   *
   * A string can be formatted in a special way. The format attribute allows to store this format
   * information with the string.
   * 
   * @param variant  SIDEX_VARIANT handle
   * @param format format of the string
   *
   * @see Sidex#sidex_Variant_String_GetFormat(long)
   */
  public void sidex_Variant_String_SetFormat(long variant, String format) throws TmlSidexException{
    int iRet = NativeSidexVariantStringSetFormat(variant, format);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_String_SetFormat / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief create a variant of type string
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @param strValue   string value
   *
   * @return  SIDEX_VARIANT handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_New_String(String strValue) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantNewString(strValue, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_New_String / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Simple data types / Binary

  /**
   * \addtogroup bintype
   * @{
   */

  /**
  * @brief get value and length from a binary variant
  * 
  * @param variant  SIDEX_VARIANT handle
  * @param iLength   array of size 1 - returns the binary string length
  *
  * @return  byte array
  *
  * @throws TmlSidexException 
  */
  public byte[] sidex_Variant_As_Binary(long variant, int[] iLength) throws TmlSidexException{
    int iBinLength = sidex_Variant_As_Binary_Length(variant);
    ByteBuffer bStr = ByteBuffer.allocateDirect (iBinLength);
    
    int iRet = NativeSidexVariantAsBinary(variant, bStr, iLength);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_As_Binary / Error "+Integer.toString(iRet), iRet);
    }
    byte[] bArray = new byte[iLength[0]];
    for (int i = 0; i < iLength[0]; ++i){
      bArray[i] = bStr.get(i);
    }
    bStr = null;
    return bArray;
  }

  /**
  * @brief get the length from a binary variant
  * 
  * @param variant  SIDEX_VARIANT handle
  *
  * @return  binary string length
  *
  * @throws TmlSidexException 
  */
  public int sidex_Variant_As_Binary_Length(long variant) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexVariantAsBinaryLength(variant, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_As_Binary_Length / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }

  /**
   * @brief check if variant type is binary.
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is binary
   *
   */
  public boolean sidex_Variant_Binary_Check(long variant){
    boolean bRet = NativeSidexVariantBinaryCheck(variant);
    return bRet;
  }
  
  /**
   * @brief create a variant of type binary.
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   * 
   * @param binValue   binary value
   * @param length   length of byte array
   *
   * @return  SIDEX_VARIANT handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_New_Binary(byte[] binValue, int length) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantNewBinary(binValue, length, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_New_Binary / Error "+Integer.toString(iRet), iRet);
    }
    long lLength = value[0];
    value = null;
    return lLength;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Simple data types / DateTime

  /**
   * \addtogroup datetimetype
   * @{
   */

  /**
   * @brief get the value from a datetime variant as string
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  formatted string
   *
   * @throws TmlSidexException
   * @see Sidex#sidex_Variant_New_DateTime(String)
   */
  public String sidex_Variant_As_DateTime(long variant) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeSidexVariantAsDateTime(variant, buffer);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("sidex_Variant_As_DateTime / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }

  /**
   * @brief check if variant type is datetime.
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant tpye is datetime
   *
   */
  public boolean sidex_Variant_DateTime_Check(long variant){
    boolean bRet = NativeSidexVariantDateTimeCheck(variant);
    return bRet;
  }
  
  /**
   * @brief create a variant of type datetime
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
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
   * @param value   formatted string.
   *
   * @return  SIDEX_VARIANT handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_New_DateTime(String value) throws TmlSidexException{
    long[] variant = new long[1];
    int iRet = NativeSidexVariantNewDateTime(value, variant);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      variant = null;
      throw new TmlSidexException("sidex_Variant_New_DateTime / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = variant[0];
    variant = null;
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Comntainer data types / List

  /**
   * \addtogroup listtype
   * @{
   */

  /**
   * @brief append a value to a list
   * 
   * @param list  SIDEX_VARIANT handle of the list 
   * @param value  SIDEX_VARIANT handle of the value 
   *
   * @return   index of the element
   * 
   * @throws TmlSidexException 
   */
  public int sidex_Variant_List_Append(long list, long value) throws TmlSidexException{
    int[] pos = new int[1];
    int iRet = NativeSidexVariantListAppend(list, value, pos);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      pos = null;
      throw new TmlSidexException("sidex_Variant_List_Append / Error "+Integer.toString(iRet), iRet);
    }
    int iPos = pos[0];
    pos = null;
    return iPos;
  }
  
  /**
   * @brief check if variant type is list
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is list
   */
  public boolean sidex_Variant_List_Check(long variant){
    boolean bRet = NativeSidexVariantListCheck(variant);
    return bRet;
  }
  
  /**
   * @brief remove all values from a list
   * 
   * @param list  SIDEX_VARIANT handle of the list 
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_List_Clear(long list) throws TmlSidexException{
    int iRet = NativeSidexVariantListClear(list);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_List_Clear / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief remove a value from a list
   * 
   * @param list  SIDEX_VARIANT handle of the list 
   * @param pos   the position in the list
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_List_DeleteItem(long list, int pos) throws TmlSidexException{
    int iRet = NativeSidexVariantListDeleteItem(list, pos);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_List_DeleteItem / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get a value from a list
   * 
   * @param list  SIDEX_VARIANT handle of the list 
   * @param pos   the position of the value
   *
   * @return  SIDEX_VARIANT value (borrowed reference)
   * 
   * @throws TmlSidexException 
   */
  public long sidex_Variant_List_Get(long list, int pos) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantListGet(list, pos, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_List_Get / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
  
  /**
   * @brief insert a value into a list
   * 
   * @param list  SIDEX_VARIANT handle of the list 
   * @param value  SIDEX_VARIANT handle of the value 
   * @param pos   the position in the list
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_List_Insert(long list, long value, int pos) throws TmlSidexException{
    int iRet = NativeSidexVariantListInsert(list, value, pos);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_List_Insert / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief replace a value in a list
   * 
   * @param list  SIDEX_VARIANT handle of the list 
   * @param value  SIDEX_VARIANT handle of the new value 
   * @param pos   the position in the list
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_List_Set(long list, long value, int pos) throws TmlSidexException{
    int iRet = NativeSidexVariantListSet(list, value, pos);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_List_Set / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get the number of items in a List
   * 
   * @param list  SIDEX_VARIANT handle of the list 
   *
   * @return  list item count
   * 
   * @throws TmlSidexException 
   */
  public int sidex_Variant_List_Size(long list) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexVariantListSize(list, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_List_Size / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }

  /**
   * @brief create a variant of type list
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @return  SIDEX_VARIANT handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_New_List(){
    long lRet = NativeSidexVariantNewList();
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Comntainer data types / Dict

  /**
   * \addtogroup dicttype
   * @{
   */

  /**
   * @brief check if variant type is dictionary
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is dictionary
   *
   */
  public boolean sidex_Variant_Dict_Check(long variant){
    boolean bRet = NativeSidexVariantDictCheck(variant);
    return bRet;
  }
  
  /**
   * @brief remove all entries of from a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Dict_Clear(long dict) throws TmlSidexException{
    int iRet = NativeSidexVariantDictClear(dict);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Dict_Clear / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief delete a key/value pair from a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of a dictionary 
   * @param sKey  name of the key
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Dict_Delete(long dict, String sKey) throws TmlSidexException{
    int iRet = NativeSidexVariantDictDelete(dict, sKey);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Dict_Delete / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief set the cursor to the first element of a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Dict_First(long dict) throws TmlSidexException{
    int iRet = NativeSidexVariantDictFirst(dict);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Dict_First / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get a value from a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   * @param sKey  name of the key
   *
   * @return  SIDEX_VARIANT value (borrowed reference)
   * 
   * @throws TmlSidexException 
   */
  public long sidex_Variant_Dict_Get(long dict, String sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantDictGet(dict, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Dict_Get / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
  
  /**
   * @brief check if a key exists in a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   * @param sKey  name of the key
   *
   * @return  true, if the key exists
   * 
   * @throws TmlSidexException 
   */
  public boolean sidex_Variant_Dict_HasKey(long dict, String sKey) throws TmlSidexException{
    boolean[] value = new boolean[1];
    int iRet = NativeSidexVariantDictHasKey(dict, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Dict_HasKey / Error "+Integer.toString(iRet), iRet);
    }
    boolean bValue = value[0];
    value = null;
    return bValue;
  }
  
  /**
   * @brief get all keys of a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   *
   * @return  SIDEX_VARIANT handle. The list contains all keys in the dictionary and must be released with sidex_Variant_DecRef().
   * 
   * @throws TmlSidexException 
   */
  public long sidex_Variant_Dict_Keys(long dict) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantDictKeys(dict, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Dict_Keys / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
  
  /**
   * @brief get the value from a dictionary at the current cursor position and move the cursor to the next value
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   * @param sKey  returns the next key in the dictionary
   *
   * @return  the next value of the dictionary (borrowed reference).
   * SIDEX_HANDLE_TYPE_NULL if the end of the dictionary was reached.
   * 
   * @throws TmlSidexException 
   */
  public long sidex_Variant_Dict_Next(long dict, StringBuffer sKey) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantDictNext(dict, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Dict_Next / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
  
  /**
   * @brief add a key/value pair to a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   * @param sKey  name of the key
   * @param value   SIDEX_VARIANT handle of the value
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Dict_Set(long dict, String sKey, long value) throws TmlSidexException{
    int iRet = NativeSidexVariantDictSet(dict, sKey, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Dict_Set / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get the number of entries in a dictionary
   * 
   * @param dict  SIDEX_VARIANT handle of the dict 
   *
   * @return  dictionary entry count
   *
   * @throws TmlSidexException 
   */
  public int sidex_Variant_Dict_Size(long dict) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexVariantDictSize(dict, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Dict_Size / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }

  /**
   * @brief create a variant of type dictionary with a predefined size
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @param size    number of dictionary entries
   *  
   * @return  SIDEX_VARIANT handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_New_DictBySize(int size){
    long lRet = NativeSidexVariantNewDictBySize(size);
    return lRet;
  }
  
  /**
   * @brief create a variant of type dict
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @return  SIDEX_VARIANT handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_New_Dict(){
    long lRet = NativeSidexVariantNewDict();
    return lRet;
  }

  /* @} */

  ////////////////////////////////////////////////
  //Comntainer data types / Table

  /**
   * \addtogroup tabletype
   * @{
   */

  /**
   * @brief create a variant of type table
   *
   * This function increments the reference counter of the variant.
   * It must be decremented, if the value is no longer used.
   *
   * @return  SIDEX_VARIANT handle.
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_New_Table(){  
    long lRet = NativeSidexVariantNewTable();  
    return lRet;
  }

  /**
   * @brief add a column to a table
   * 
   * For fields in existing rows the value is set to none.
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param sColumn    column name
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Table_AddColumn(long table, String sColumn) throws TmlSidexException{
    int iRet = NativeSidexVariantTableAddColumn(table, sColumn);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Table_AddColumn / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief add a row to a table
   * 
   * Fields of the new row are set to none.
   * 
   * @note Rows can be added before columns but sidex_Variant_Table_GetRow() will return SIDEX_ERR_NOCONTENT.
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   *  
   * @return  row index of the added row
   *
   * @throws TmlSidexException 
   */
  public int sidex_Variant_Table_AddRow(long table) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexVariantTableAddRow(table, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Table_AddRow / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }

  /**
   * @brief check if variant type is table.
   * 
   * @param variant  SIDEX_VARIANT handle
   *
   * @return  true, if variant type is table
   *
   */
  public boolean sidex_Variant_Table_Check(long variant){
    boolean bRet = NativeSidexVariantTableCheck(variant);
    return bRet;
  }

  /**
   * @brief get column names of a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   *  
   * @return  SIDEX_VARIANT handle. The list contains all column names of the table and must be released by sidex_Variant_DecRef().
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_Table_ColumnNames(long table) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantTableColumnNames(table, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Table_ColumnNames / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }

  /**
   * @brief get the number of columns of a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   *  
   * @return  column count
   *
   * @throws TmlSidexException 
   */
  public int sidex_Variant_Table_Columns(long table) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexVariantTableColumns(table, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Table_Columns / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }

  /**
   * @brief remove a column from a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param sColumn column name
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Table_DeleteColumn(long table, String sColumn) throws TmlSidexException{
    int iRet = NativeSidexVariantTableDeleteColumn(table, sColumn);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Table_DeleteColumn / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief delete a row from a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param iRowIdx row index
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Table_DeleteRow(long table, int iRowIdx) throws TmlSidexException{
    int iRet = NativeSidexVariantTableDeleteRow(table, iRowIdx);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Table_DeleteRow / Error "+Integer.toString(iRet), iRet);
    }
  }

  /**
   * @brief delete all rows from a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Table_DeleteRows(long table) throws TmlSidexException{
    int iRet = NativeSidexVariantTableDeleteRows(table);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Table_DeleteRows / Error "+Integer.toString(iRet), iRet);
    }
  }
  
  /**
   * @brief get the name of a table column
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param index column index
   *
   * @return  column name 
   *
   * @throws TmlSidexException 
   */
  public String sidex_Variant_Table_GetColumnName(long table, int index) throws TmlSidexException{
    StringBuffer buffer = new StringBuffer("");
    int iRet = NativeSidexVariantTableGetColumnName(table, index, buffer);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      buffer = null;
      throw new TmlSidexException("sidex_Variant_Table_GetColumnName / Error "+Integer.toString(iRet), iRet);
    }
    String sRet = buffer.toString();
    buffer = null;
    return sRet;
  }
  
  /**
   * @brief get a value from a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param rowIndex row index
   * @param sColumnName column name 
   *
   * @return  SIDEX_VARIANT handle (borrowed reference)
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_Table_GetField(long table, int rowIndex, String sColumnName) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantTableGetField(table, rowIndex, sColumnName, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Table_GetField / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
  
  /**
   * @brief get a row from a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param rowIndex row index
   *
   * @return  SIDEX_VARIANT handle with a dictionary of values (borrowed reference)
   *
   * @throws TmlSidexException 
   */
  public long sidex_Variant_Table_GetRow(long table, int rowIndex) throws TmlSidexException{
    long[] value = new long[1];
    int iRet = NativeSidexVariantTableGetRow(table, rowIndex, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Table_GetRow / Error "+Integer.toString(iRet), iRet);
    }
    long lRet = value[0];
    value = null;
    return lRet;
  }
  
  /**
   * @brief check if a column name exists
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param sColumnName  name of the column
   *
   * @return  true, if the column name exists
   * 
   * @throws TmlSidexException 
   */
  public boolean sidex_Variant_Table_HasColumn(long table, String sColumnName) throws TmlSidexException{
    boolean[] value = new boolean[1];
    int iRet = NativeSidexVariantTableHasColumn(table, sColumnName, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Table_HasColumn / Error "+Integer.toString(iRet), iRet);
    }
    boolean bValue = value[0];
    value = null;
    return bValue;
  }

  /**
   * @brief get the number of rows of a table
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   *
   * @return  row count
   * 
   * @throws TmlSidexException 
   */
  public int sidex_Variant_Table_Rows(long table) throws TmlSidexException{
    int[] value = new int[1];
    int iRet = NativeSidexVariantTableRows(table, value);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      value = null;
      throw new TmlSidexException("sidex_Variant_Table_Rows / Error "+Integer.toString(iRet), iRet);
    }
    int iLength = value[0];
    value = null;
    return iLength;
  }
  
  /**
   * @brief replace a value in a table
   * 
   * @note If the row index does not exist, the missing rows will automatically be added to the table.
   * 
   * @param table  SIDEX_VARIANT handle of the table 
   * @param rowIndex row index
   * @param sColumnName column name 
   * @param variant  SIDEX_VARIANT handle
   *
   * @throws TmlSidexException 
   */
  public void sidex_Variant_Table_SetField(long table, int rowIndex, String sColumnName, long variant) throws TmlSidexException{
    int iRet = NativeSidexVariantTableSetField(table, rowIndex, sColumnName, variant);
    if (SidexErr.SIDEX_SUCCESS != iRet){
      throw new TmlSidexException("sidex_Variant_Table_SetField / Error "+Integer.toString(iRet), iRet);
    }
  }
  /* @} */
}
