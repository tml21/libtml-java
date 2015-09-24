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
import java.util.Enumeration;
import java.util.Hashtable;

import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;

/**
 * @ingroup sdxClasses
 * @brief Implementation of a Table type variant.
 */
public class SDXTable extends SDXBase{

  // Member
  int          m_iIterator= 0;
  
  /**
   * @brief constructs a newempty SDXTable object
   */
  public SDXTable(){
    m_var = m_sidex.sidex_Variant_New_Table();
  }

  /**
   * @brief constructs a new instance to access an existing low level handle
   *
   * @param vHandle handle of a SIDEX Table type variant
   * @param thisown instance is owner of the SIDEX variant handle 
   * @throws TmlSidexException 
   */
  public SDXTable(long vHandle, boolean thisown) throws TmlSidexException{
    if (!SDXUtils.sidexVariantEqualsType(m_sidex, vHandle, SidexType.SIDEX_DATA_TYPE_TABLE)){
      throw new TmlSidexException("SDXTable::SDXTable / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
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
   * @brief check if a column name exists
   *
   * @return True if the column name exists
   * @throws TmlSidexException 
   */
  public boolean hasColumn(String name) throws TmlSidexException{
    boolean bRet = m_sidex.sidex_Variant_Table_HasColumn(m_var, name);
    return bRet;
  }
  
  /**
   * @brief get column names
   *
   * @return String array of column names
   *
   * @throws TmlSidexException 
   */
  public String[] getColumnNames() throws TmlSidexException{
    
    long names = m_sidex.sidex_Variant_Table_ColumnNames(m_var);
    
    int length = m_sidex.sidex_Variant_List_Size (names);
    
    String[] strRet = new String[length];
    for (int i = 0; i < length; ++i){
      long lVar = m_sidex.sidex_Variant_List_Get (names, i);
      String sName = m_sidex.sidex_Variant_As_String (lVar);
      strRet[i] = sName;
    }
    m_sidex.sidex_Variant_DecRef(names);
    return strRet;
  }
  
  /**
   * @brief add a column
   * 
   * If row data is already added to the table, a default SDXNone value is set.
   *
   * @param key name of the column
   *
   * @throws TmlSidexException 
   */
  public void addColumn(String key) throws TmlSidexException{
    m_sidex.sidex_Variant_Table_AddColumn(m_var, key);
  }
  
  /**
   * @brief add a column with default value
   * 
   * If row data is already added to the table, defaultValue is set.
   *
   * @param key          name of the column
   * @param defaultValue default value
   *
   * @throws TmlSidexException 
   */
  public void addColumn(String key, SDXBase defaultValue) throws TmlSidexException{
    m_sidex.sidex_Variant_Table_AddColumn(m_var, key);
    int iRows = this.rows();
    for (int i = 0; i < iRows; ++i){
      this.setField(i, key, defaultValue);
    }
  }
  
  /**
   * @brief remove a column
   *
   * @param key name of the column to delete
   *
   * @throws TmlSidexException 
   */
  public void deleteColumn(String key) throws TmlSidexException{
    m_sidex.sidex_Variant_Table_DeleteColumn(m_var, key);
  }
  
  /**
   * @brief add a row
   * 
   * Each field will be filled with an SDXNone value
   *
   * @return row index of the added row 
   *
   * @throws TmlSidexException 
   */
  public int addRow() throws TmlSidexException{
    int iRowIdx = m_sidex.sidex_Variant_Table_AddRow(m_var);
    return iRowIdx;
  }

  /**
   * @brief add a row with values
   * 
   * @param rowData Dictionary of column names and values. A subset of existing columns is possible.
   *
   * @return row index of the added row 
   *
   * @throws TmlSidexException 
   */
  public int addRow(Dictionary<String, SDXBase> rowData) throws TmlSidexException{
    int iRowIdx = m_sidex.sidex_Variant_Table_AddRow(m_var);
    int size = rowData.size();
    if (0 < size){
      for (Enumeration<String> keys = rowData.keys(); keys.hasMoreElements();){
        String key = keys.nextElement();
        SDXBase value = rowData.get(key);
        this.setField(iRowIdx, key, value);
      }
    }
    
    
    return iRowIdx;
  }

  /**
   * @brief delete a row
   *
   * @param rowIndex index of the row to delete
   *
   * @throws TmlSidexException 
   */
  public void deleteRow(int rowIndex) throws TmlSidexException{
    m_sidex.sidex_Variant_Table_DeleteRow(m_var, rowIndex);
  }
  
  /**
   * @brief get the name of a column
   *
   * @param colIndex  column index 
   * 
   * @return name of the column
   *
   * @throws TmlSidexException 
   */
  public String getColumnName(int colIndex) throws TmlSidexException{
    String strRet = m_sidex.sidex_Variant_Table_GetColumnName(m_var, colIndex);
    return strRet;
  }
  
  /**
   * @brief get a row
   *
   * @param rowIndex  row index
   * 
   * @return Dictionary representing the the column name / value pairs of the row
   *
   * @throws TmlSidexException 
   */
  public Dictionary<String, SDXBase> getRow(int rowIndex) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_Table_GetRow(m_var, rowIndex);
    SDXDict dict = new SDXDict(variant, false);
    
    Dictionary<String, SDXBase> retDict = new Hashtable<String, SDXBase>();
    int length = dict.length();
    if (0 < length){
      String[] keys = dict.getKeys();
      for (int i = 0; i < length; ++i){
        SDXBase value = dict.getValue(keys[i]);
        retDict.put(keys[i], value);
      }
      keys = null;
    }
    dict = null;
    return retDict;
  }
  
  /**
   * @brief get field value
   *
   * @param rowIndex   row index
   * @param columnName name of the column
   *
   * @return SDXBase driven object depending of the data type
   *
   * @throws TmlSidexException 
   */
  public SDXBase getField(int rowIndex, String columnName) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_Table_GetField(m_var, rowIndex, columnName);
    SDXBase retVar = SDXUtils.sidexVariantAsJavaObject(m_sidex, variant);
    return retVar;
  }
 
  /**
   * @brief set a field value to SDXNone
   *
   * @param rowIndex   row index
   * @param columnName name of the column
   *
   * @throws TmlSidexException 
   */
  public void setField(int rowIndex, String columnName) throws TmlSidexException{
    long variant = m_sidex.sidex_Variant_New_None();
    m_sidex.sidex_Variant_Table_SetField(m_var, rowIndex, columnName, variant);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
  /**
   * @brief set a field value to SDXNone
   *
   * @param rowIndex   row index
   * @param columnName name of the column
   * @param sVal the value to set of type
   *        integer, long, float, double, boolean, Integer, Long, Float, Double, Boolean, String or SDXBase
   *
   * @throws TmlSidexException 
   */
  public void setField(int rowIndex, String columnName, Object sVal) throws TmlSidexException{
    long variant = SDXUtils.javaObjectAsSidexVariant(m_sidex, sVal);
    m_sidex.sidex_Variant_Table_SetField(m_var, rowIndex, columnName, variant);
    m_sidex.sidex_Variant_DecRef(variant);
  }
  
  /**
   * @brief delete all rows
   *
   * @throws TmlSidexException 
   */
  public void clear() throws TmlSidexException{
    m_sidex.sidex_Variant_Table_DeleteRows(m_var);
  }
  
 /**
   * @brief get column count
   *
   * @return number of columns
   *
   * @throws TmlSidexException 
   */
  public int columns() throws TmlSidexException{
    
    int columns = m_sidex.sidex_Variant_Table_Columns(m_var);
    return columns;
  }
  
  /**
   * @brief get row count
   *
   * @return number of rows
   *
   * @throws TmlSidexException 
   */
  public int rows() throws TmlSidexException{
    
    int rows = m_sidex.sidex_Variant_Table_Rows(m_var);
    return rows;
  }
  
  /**
   * @brief set cursor to the first row
   * @see SDXTable#next()
   */
  public void first(){
    m_iIterator = 0;
  }
  
  /**
   * @brief get next row
   * 
   * @return Dictionary representing the the column name / value pairs of the row. If end is reached null will be returned.
   *
   * @throws TmlSidexException 
   */  
  public Dictionary<String, SDXBase> next() throws TmlSidexException{
    Dictionary<String, SDXBase> retValue = null;
    try{
      retValue = this.getRow(m_iIterator);
      ++m_iIterator;
    }
    catch (TmlSidexException e){
      if (SidexErr.SIDEX_ERR_NOCONTENT != e.getError()){
        throw e;
      }
    }
    return retValue;
  }
}
