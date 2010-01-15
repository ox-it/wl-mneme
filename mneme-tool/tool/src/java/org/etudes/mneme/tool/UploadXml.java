/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2008 Etudes, Inc.
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.FileItem;
import org.w3c.dom.Document;
import org.apache.xml.resolver.tools.CatalogResolver;

/**
 * Upload handles file uploads of XML documents, parsing the text into a DOM.
 */
public class UploadXml
{
	/** The uploaded file parsed into a DOM. */
	protected Document upload = null;

	/**
	 * Construct.
	 */
	public UploadXml()
	{
	}

	/**
	 * Access the uploaded DOM.
	 * 
	 * @return The uploaded file reference, or null if there was not an upload.
	 */
	public Document getUpload()
	{
		return this.upload;
	}

	/**
	 * Accept a file upload from the user.
	 * 
	 * @param file
	 *        The file.
	 */
	public void setUpload(FileItem file)
	{
		try {
			DocumentBuilder docBuilder = getDocumentBuilder();
			CatalogResolver resolver = new CatalogResolver();
			resolver.getCatalog().parseCatalog("catalog.xml");
			String base = resolver.getCatalog().getCurrentBase();
			docBuilder.setEntityResolver(resolver);
			this.upload = docBuilder.parse(file.getInputStream(), base);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return a DocumentBuilder object for XML parsing.
	 */
	protected static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		return dbf.newDocumentBuilder();
	}

}
