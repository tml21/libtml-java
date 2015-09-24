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

import com.tmlsidex.jni.Sidex;
import com.tmlsidex.jni.SidexErr;
import com.tmlsidex.jni.SidexType;
import com.tmlsidex.jni.TmlSidexException;


/**
 * @ingroup sdxClasses
 * @brief Internal package helper methods.
 */
public class SDXUtils extends Object{

  /**
   * @brief Helper - returns false if variant has not the expected SIDEX data type
   * 
   * @param sidex instance of Sidex object
   * @param variant SIDEX variant for the request
   * @param expectedType expected SIDEX  data type
   * 
   * @return true, if SIDEX data type of variant is as expected 
   */
  static boolean sidexVariantEqualsType(Sidex sidex, long variant, int expectedType){
    boolean bRet = false;
    int iType = sidex.sidex_Variant_GetType(variant);
    bRet = (iType == expectedType);
    return bRet;
  }
  
  /**
   * @brief Helper - returns SIDEX variant as java object
   * 
   * @param sidex instance of Sidex object
   * @param variant SIDEX variant for the request
   * 
   * @return SDXBase driven java object
   *
   * @throws TmlSidexException 
   */
  static SDXBase sidexVariantAsJavaObject(Sidex sidex, long variant) throws TmlSidexException{
    SDXBase retValue = null;
    int iType = sidex.sidex_Variant_GetType(variant);
    switch (iType){
    case SidexType.SIDEX_DATA_TYPE_BINARY:
      retValue = new SDXBinary(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_BOOLEAN:
      retValue = new SDXBoolean(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_DATETIME:
      retValue = new SDXDateTime(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_DICT:
      retValue = new SDXDict(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_FLOAT:
      retValue = new SDXFloat(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_INTEGER:
      retValue = new SDXInteger(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_LIST:
      retValue = new SDXList(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_NONE:
      retValue = new SDXNone(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_STRING:
      retValue = new SDXString(variant, true);
      break;
    case SidexType.SIDEX_DATA_TYPE_TABLE:
      retValue = new SDXString(variant, true);
      break;
    default:  
      throw new TmlSidexException("SDXUtils::sidexVariantAsJavaObject / Error "+Integer.toString(SidexErr.SIDEX_ERR_WRONG_TYPE), SidexErr.SIDEX_ERR_WRONG_TYPE);
    }
    return retValue;
  }
  
  /**
   * @brief Helper - returns java object as SIDEX variant.
   * 
   * It's reference counter has to be decremented after use .
   * 
   * @param sidex instance of Sidex object
   * @param valObj java Object to convert of type
   *        integer, long, float, double, boolean, Integer, Long, Float, Double, Boolean, String or SDXBase      
   * 
   * @return SIDEX variant
   *
   * @throws TmlSidexException 
   */
  static long javaObjectAsSidexVariant(Sidex sidex, Object valObj) throws TmlSidexException{
    long variant = 0;
    if (valObj instanceof Integer){
      variant =sidex.sidex_Variant_New_Integer(((Integer)valObj).longValue());
    }
    else{
      if (valObj instanceof Long){
        variant =sidex.sidex_Variant_New_Integer(((Long)valObj).longValue());
      }
      else{
        if (valObj instanceof Float){
          variant =sidex.sidex_Variant_New_Float(((Float)valObj).doubleValue());
        }
        else{
          if (valObj instanceof Double){
            variant =sidex.sidex_Variant_New_Float(((Double)valObj).doubleValue());
          }
          else{
            if (valObj instanceof Boolean){
              variant =sidex.sidex_Variant_New_Boolean(((Boolean)valObj).booleanValue());
            }
            else{
              if (valObj instanceof String){
                variant =sidex.sidex_Variant_New_String((String)valObj);
              }
              else{
                if (valObj instanceof SDXBase){
                  variant = ((SDXBase)valObj).getVhandle();
                  sidex.sidex_Variant_IncRef(variant);
                }
                else{
                  throw new TmlSidexException("SDXUtils::javaObjectAsSidexVariant / unsupported value type", SidexErr.SIDEX_ERR_WRONG_TYPE);
                }
              }
            }
          }
        }
      }
    }
    return variant;
  }
}
