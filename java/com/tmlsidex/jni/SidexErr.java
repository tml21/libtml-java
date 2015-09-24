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
 
package com.tmlsidex.jni;


/**
 * @ingroup sdxreturn
 * @brief SIDEX return code constants
 */
public class SidexErr extends Object{
  
/**
 * @brief operation successful 
 */
public static final int SIDEX_SUCCESS =                            0;

/**
 * @brief error on copying a string from or to a SIDEX object
 */
public static final int SIDEX_ERR_DUMPCONTENT =                 1001;

/**
 * @brief a sidex document group/key address has no content 
 */
public static final int SIDEX_ERR_NOCONTENT =                   1002;

/**
 * @brief value type in the SIDEX document does not match expected type of the read operation
 */
public static final int SIDEX_ERR_WRONG_TYPE =                  1003;

/**
 * @brief Key contains forbidden XML tag characters (Syntax Error)
 */
public static final int SIDEX_ERR_WRONG_KEY =                   1004;

/**
 * @brief Group contains forbidden XML tag characters (Syntax Error)
 */
public static final int SIDEX_ERR_WRONG_GROUP =                 1005;

/**
 * @brief key parameter is NULL
 */
public static final int SIDEX_ERR_MISSING_KEY =                 1006;

/**
 * @brief invalid parameter passed
 */
public static final int SIDEX_ERR_WRONG_PARAMETER =             1007;

/**
 * @brief dictionary value definition syntax error in SIDEX document
 */
public static final int SIDEX_ERR_WRONG_DICT_SYNTAX =           1008;

/**
 * @brief maximun number of dictionary entries reached
 */
public static final int SIDEX_ERR_DICT_FULL =                   1009;

/**
 * @brief boolean value definition syntax error
 */
public static final int SIDEX_ERR_INVALID_BOOLEAN =             1010;

/**
 * @brief integer value out of range
 */
public static final int SIDEX_ERR_INVALID_INTEGER =             1011;

/**
 * @brief float value out of range
 */
public static final int SIDEX_ERR_INVALID_FLOAT =               1012;

/**
 * @brief unhandled error
 */
public static final int SIDEX_ERR_COMMON =                      1013;

/**
 * @brief table column already exists
 */
public static final int SIDEX_ERR_TABLE_COLUMN_ALREADY_EXISTS = 1014;
  
/**
 * @brief DateTime format error
 */
public static final int SIDEX_ERR_INVALID_DATETIME =            1015;


/**
 * @brief product is not licensed or license has expired
*/
public static final int SIDEX_ERR_LICENSE =                     1016;

/**
 * @brief interation not inititialized by calling first(...) before next(...)
*/
public static final int SIDEX_ERR_FIRST_NEXT =                  1017;

/**
 * @brief unicode conversion error
*/
public static final int SIDEX_ERR_UNICODE =                     1018;

/**
 * @brief group name is null
*/
public static final int SIDEX_ERR_MISSING_GROUP =               1019;

/**
 * @brief syntax error in document name
*/
public static final int SIDEX_ERR_WRONG_DOC =                   1020;

/**
 * @brief string encoding format is NULL or unexpected
*/
public static final int SIDEX_ERR_WRONG_ENCODING_FORMAT =       1021;

/**
 * @brief update cycle has expired
*/
public static final int SIDEX_ERR_LICENSE_UDS =                 1022;

/**
 * @brief trial license has expired
*/
public static final int SIDEX_ERR_LICENSE_EXP =                 1023;

/**
 * @brief not licensed for this platform
*/
public static final int SIDEX_ERR_LICENSE_SYS =                 1024;
}
