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

/**
 * @ingroup sdxClasses
 * @brief Abstract base class for SIDEX variants.
 */
public abstract class SDXBase extends Object{
  
  // Member
  long    m_var      = 0;
  Sidex   m_sidex    = null;
  boolean m_bThisOwn = false;

  /**
   * @brief constructor
   */
  public SDXBase(){
    m_bThisOwn = true;
    m_sidex = new Sidex();
    m_var = 0;
  }

  /**
   * @brief destructor.
   */
  protected void finalize() throws Throwable {
    // Internal destructrion:
    if (m_bThisOwn && 0 != m_var){
      m_sidex.sidex_Variant_DecRef(m_var);
      m_var = 0;
    }
    m_sidex = null;
    
    // Super destructrion
    super.finalize();
  }
  
  /**
   * @brief returns the SIDEX variant handle of this instance
   *
   * @return SIDEX variant handle
   */
  public long getVhandle(){
    return m_var;
  }
}
