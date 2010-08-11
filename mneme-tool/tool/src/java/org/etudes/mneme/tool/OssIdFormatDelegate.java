/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2008 Etudes, Inc.
 * 
 * Portions completed before September 1, 2008
 * Copyright (c) 2007, 2008 The Regents of the University of Michigan & Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.etudes.mneme.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.ambrosia.api.Context;
import org.etudes.ambrosia.util.FormatDelegateImpl;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.entity.api.Entity;

/**
 * The "OSS ID" format delegate for the mneme tool.
 */
public class OssIdFormatDelegate extends FormatDelegateImpl
{
	/** Our log. */
	private static Log M_log = LogFactory.getLog(OssIdFormatDelegate.class);
	
	private UserDirectoryService userDirectoryService;

	/**
	 * Shutdown.
	 */
	public void destroy()
	{
		M_log.info("destroy()");
	}

	public String format(Context context, Object value)
	{
		return value.toString();
	}

	public Object formatObject(Context context, Object value)
	{
		if (!(value instanceof String))
			return null;
		
		Entity user = null;
		try {
			user = (Entity)userDirectoryService.getUser((String)value);
		} catch (UserNotDefinedException e) {
			M_log.info("formatObject(): User with the ID - " + value + " - not found.");
		}
		
		String ossId = user.getProperties().getProperty("oakOSSID"); 
		return ossId != null ? ossId : context.getMessages().getString("dash");
	}

	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init()
	{
		super.init();
		M_log.info("init()");
	}

	public UserDirectoryService getUserDirectoryService() 
	{
		return userDirectoryService;
	}

	public void setUserDirectoryService(UserDirectoryService userDirectoryService) 
	{
		this.userDirectoryService = userDirectoryService;
	}
}
