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
 * @ingroup tmlReturn
 * @brief TML return codes
 */
public class TmlErr extends Object{
  
/**
 * @brief operation successful 
 */
public static final int TML_SUCCESS =                             SidexErr.SIDEX_SUCCESS;

/**
* @brief unicode conversion error 
*/
public static final int TML_ERR_UNICODE =                         SidexErr.SIDEX_ERR_UNICODE;

//------------------------------------------------------------
//TML- specific errors:

/**
* @brief tml header contains returned error message
*/
public static final int  TML_ERR_ERROR_MSG_RECEIVED =             1;

/**
* @brief try to start event handling thread while object destruction in progress
*/
public static final int  TML_ERR_EVENT_HANDLER_IN_DESTRUCTION =   2;

/**
* @brief stream does not exist
*/
public static final int  TML_ERR_STREAM_DOES_NOT_EXIST =         11;

/**
* @brief unable to acquire/release critical section
*/
public static final int  TML_ERR_CRITICAL_SECTION =              12;

/**
* @brief unhandled error
*/
public static final int  TML_ERR_COMMON =                        13;

/**
* @brief connection failed
*/
public static final int  TML_ERR_CONNECT =                       14;

/**
* @brief a required object is missing
*/
public static final int  TML_ERR_MISSING_OBJ =                   15;

/**
 * @brief missing information 
 */
public static final int TML_ERR_INFORMATION_UNDEFINED =          16;

/**
*@brief unknown command execution state
*/
public static final int TML_ERR_COMMAND_STATE_UNDEFINED =        17;

/**
*@brief unknown command execution mode
*/
public static final int TML_ERR_COMMAND_MODE_UNDEFINED =         18;

/**
*@brief reply type is undefined
*/
public static final int TML_ERR_COMMAND_REPLY_TYPE_UNDEFINED =   19;

/**
*@brief progress value is out of range
*/
public static final int TML_ERR_COMMAND_PROGRESS_RANGE =         20;

/**
*@brief dispatcher not created or initialized
*/
public static final int TML_ERR_DISPATCHER_NOT_CREATED =         21;

/**
*@brief dispatcher already exists
*/
public static final int TML_ERR_DISPATCHER_ALREADY_EXISTS =      22;

/**
*@brief no command handler for this command id
*/
public static final int TML_ERR_DISPATCHER_CMD_NOT_REGISTERED =  23;

/**
*@brief listener not initialized
*/
public static final int TML_ERR_LISTENER_NOT_INITIALIZED =       25;

/**
*@brief listener already started
*/
public static final int TML_ERR_LISTENER_ALREADY_STARTED =       27;

/**
*@brief communication error at listener
*/
public static final int TML_ERR_LISTENER_COMMUNICATION =         28;

/**
*@brief sender not initialized
*/
public static final int TML_ERR_SENDER_NOT_INITIALIZED =         30;

/**
*@brief sender can't register profile
*/
public static final int TML_ERR_SENDER_PROFILE_REGISTRATION =    31;

/**
*@brief invalid host and/or port address
*/
public static final int TML_ERR_SENDER_INVALID_PARAMS =          32;

/**
*@brief sender channel not initilialized
*/
public static final int TML_ERR_CHANNEL_NOT_INITIALIZED =        33;

/**
*@brief communication error at sender
*/
public static final int TML_ERR_SENDER_COMMUNICATION =           35;

/**
*@brief profile is not supported
*/
public static final int TML_ERR_SENDER_PROFILE_NOT_SUPPORTED =   37;

/**
*@brief internal hash table error
*/
public static final int TML_ERR_HASH =                           38;

/**
*@brief key is of wrong type
*/
public static final int TML_ERR_HASH_WRONG_TYPE =                39;

/**
*@brief attribute is not set
*/
public static final int TML_ERR_ATTRIBUTE_NOT_SET =              40;

/**
*@brief try to change listener property while listener is started
*/
public static final int TML_ERR_NOT_OPERABLE_AT_THE_MOMENT =     41;

/**
*@brief cannot read TML state
*/
public static final int TML_ERR_COM_LAYER_READ_STATE =           42;

/**
*@brief BEEP frame type error
*/
public static final int TML_ERR_COM_LAYER_FRAME_TYPE_IS_ERR =    43;

/**
*@brief unexpected BEEP frame type
*/
public static final int TML_ERR_COM_LAYER_FRAME_UNEXP_TYPE =     44;

/**
*@brief server closed connection
*/
public static final int TML_ERR_COM_LAYER_CONNECTION_CLOSE =     45;

/**
*@brief initialization error
*/
public static final int TML_ERR_INITIALIZATION =                 46;

/**
*@brief command call timeout
*/
public static final int TML_ERR_TIMEOUT =                        47;

/**
*@brief no profiles registered
*/
public static final int TML_ERR_NOPROFILES =                     48;

/**
*@brief system out of resources
*/
public static final int TML_ERR_SYSTEMRESOURCES =                49;

/**
*@brief timeout on waiting for async command execution
*/
public static final int TML_ERR_TIMEOUT_ON_WAIT_FOR_ASYNC =      50;

/**
*@brief object not found
*/
public static final int TML_ERR_DESTINATION_OBJ_NOT_FOUND =       51;

/**
*@brief error in load balance calculation
*/
public static final int TML_ERR_LOAD_BALANCE_CALCULATION =        52;

/**
*@brief unable to bind listener address/port
*/
public static final int TML_ERR_LISTENER_ADDRESS_BINDING =        53;

/**
*@brief system shutdown in progress
*/
public static final int TML_ERR_SHUTDOWN =                        54;

/**
*@brief callback not initialized
*/
public static final int TML_ERR_STREAM_INVALID_CALLBACK =         55;

/**
*@brief invalid stream address parameter 
*/
public static final int TML_ERR_STREAM_INVALID_ADDRESS =          56;

/**
*@brief invalid stream identification
*/
public static final int TML_ERR_STREAM_INVALID_IDENTIFIER =       57;

/**
*@brief stream size not available
*/
public static final int TML_ERR_STREAM_SIZE_NOT_AVAILABLE =       58;

/**
*@brief stream read operation failed
*/
public static final int TML_ERR_STREAM_READ_FAILED =              59;

/**
*@brief stream position is not available
*/
public static final int TML_ERR_STREAM_POSITION_NOT_AVAILABLE =   60;

/**
*@brief stream seek operation is not operable
*/
public static final int TML_ERR_STREAM_SEEK_NOT_OPERABLE =        61;

/**
*@brief stream readBuffer operation reached end of file
*/
public static final int TML_ERR_STREAM_READBUFFER_EOF =           62;

/**
*@brief stream download error
*/
public static final int TML_ERR_STREAM_DOWNLOAD_FAILED =          63;

/**
*@brief stream download canceled
*/
public static final int TML_ERR_STREAM_DOWNLOAD_CANCELED =        64;

/**
*@brief stream download error in write callback
*/
public static final int TML_ERR_STREAM_DOWNLOAD_WRITE_FAILED =    65;

/**
*@brief stream is already in use
*/
public static final int TML_ERR_STREAM_ALREADY_IN_USE =           66;

/**
*@brief retain open error / profile & host & port don't match with the ID
*/
public static final int TML_ERR_STREAM_OPEN_ADDRESS_MISMATCH =    67;

/**
*@brief stream write failed
*/
public static final int TML_ERR_STREAM_WRITE_FAILED =             68;
}
