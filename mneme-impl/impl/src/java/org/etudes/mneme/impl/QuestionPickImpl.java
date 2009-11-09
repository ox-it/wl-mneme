/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2009 Etudes, Inc.
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

package org.etudes.mneme.impl;

import java.util.Map;

import org.etudes.mneme.api.Part;
import org.etudes.mneme.api.Pool;
import org.etudes.mneme.api.Question;
import org.etudes.mneme.api.QuestionPick;
import org.etudes.mneme.api.QuestionService;

/**
 * QuestionPickImpl implements QuestionPick.
 */
public class QuestionPickImpl extends PartDetailImpl implements QuestionPick
{
	/** The original question id. */
	protected String origQuestionId = null;

	/** The actual question id. */
	protected String questionId = null;

	/** Dependency: QuestionService. */
	protected transient QuestionService questionService = null;

	/**
	 * Construct.
	 * 
	 * @param part
	 *        The part.
	 * @param other
	 *        The other to copy.
	 */
	public QuestionPickImpl(Part part, QuestionPickImpl other)
	{
		super(part);
		set(other);
	}

	/**
	 * Construct.
	 * 
	 * @param part
	 *        The part.
	 * @param questionService
	 *        The QuestionService.
	 */
	public QuestionPickImpl(Part part, QuestionService questionService)
	{
		super(part);
		if (questionService == null) throw new IllegalArgumentException();
		this.part = part;
		this.questionService = questionService;
	}

	/**
	 * Construct.
	 * 
	 * @param part
	 *        The part.
	 * @param questionService
	 *        The QuestionService.
	 * @param questionId
	 *        The question id.
	 */
	public QuestionPickImpl(Part part, QuestionService questionService, String questionId)
	{
		super(part);
		if (questionService == null) throw new IllegalArgumentException();
		if (questionId == null) throw new IllegalArgumentException();
		this.part = part;
		this.questionId = questionId;
		this.origQuestionId = questionId;
		this.questionService = questionService;
	}

	/**
	 * Construct.
	 * 
	 * @param part
	 *        The part.
	 * @param questionService
	 *        The QuestionService.
	 * @param id
	 *        The detail id.
	 * @param questionId
	 *        The question id.
	 * @param origQid
	 *        The origQuestionId value.
	 * @param poolId
	 *        The pool.
	 */
	public QuestionPickImpl(Part part, QuestionService questionService, String id, String questionId, String origQid)
	{
		super(part);
		if (questionService == null) throw new IllegalArgumentException();
		if (questionId == null) throw new IllegalArgumentException();
		if (origQid == null) throw new IllegalArgumentException();
		this.id = id;
		this.questionId = questionId;
		this.origQuestionId = origQid;
		this.questionService = questionService;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj)
	{
		// equal if they have the same question
		if (this == obj) return true;
		if ((obj == null) || (obj.getClass() != this.getClass())) return false;
		return this.questionId.equals(((QuestionPickImpl) obj).questionId);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescription()
	{
		// use the question description
		Question q = this.questionService.getQuestion(this.questionId);
		if (q == null) return "?";
		return q.getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getInvalidMessage()
	{
		// we need a valid question
		if (this.questionId == null) return ((PartImpl) this.part).messages.getFormattedMessage("invalid-detail-missing-question", null);
		Question q = this.questionService.getQuestion(this.questionId);
		if (q == null) return ((PartImpl) this.part).messages.getFormattedMessage("invalid-detail-missing-question", null);

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean getIsSpecific()
	{
		return Boolean.TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean getIsValid()
	{
		// we need a valid question
		if (this.questionId == null) return Boolean.FALSE;
		Question q = this.questionService.getQuestion(this.questionId);
		if (q == null) return Boolean.FALSE;

		return Boolean.TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getNumQuestions()
	{
		return Integer.valueOf(1);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOrigQuestionId()
	{
		return this.origQuestionId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Pool getPool()
	{
		Question q = this.questionService.getQuestion(this.questionId);
		if (q == null) return null;
		return q.getPool();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPoolId()
	{
		Question q = this.questionService.getQuestion(this.questionId);
		if (q == null) return null;
		return q.getPool().getId();
	}

	/**
	 * {@inheritDoc}
	 */
	public Question getQuestion()
	{
		Question q = this.questionService.getQuestion(this.questionId);
		return q;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getQuestionId()
	{
		return this.questionId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Float getTotalPoints()
	{
		// TODO: allow an override...

		// get the question's point value
		Question q = this.questionService.getQuestion(this.questionId);
		if (q == null) return Float.valueOf(0);

		Pool p = q.getPool();
		if (p == null) return Float.valueOf(0);

		return p.getPoints();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getType()
	{
		// use the question type
		Question q = this.questionService.getQuestion(this.questionId);
		if (q == null) return "?";
		return q.getTypeName();
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode()
	{
		if (this.questionId == null) return "null".hashCode();
		return this.questionId.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean restoreToOriginal(Map<String, String> poolIdMap, Map<String, String> questionIdMap)
	{
		// if the map is present, translate to another question id
		if (questionIdMap != null)
		{
			String translated = questionIdMap.get(this.origQuestionId);
			if (translated != null)
			{
				this.origQuestionId = translated;
			}
		}

		// if no change
		if (this.questionId.equals(this.origQuestionId)) return true;

		// the question must exist, and be non-historical, and its pool must exist and be non-historical
		Question q = this.questionService.getQuestion(this.origQuestionId);
		if ((q == null) || (q.getIsHistorical()) || q.getPool().getIsHistorical()) return false;

		// restore
		this.questionId = this.origQuestionId;
		setChanged();

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setQuestionId(String questionId)
	{
		if (questionId == null) throw new IllegalArgumentException();
		if (!Different.different(questionId, this.questionId)) return;

		this.questionId = questionId;

		// set the original only once
		if (this.origQuestionId == null)
		{
			this.origQuestionId = questionId;
		}

		setChanged();
	}

	/**
	 * Set as a copy of another.
	 * 
	 * @param other
	 *        The other to copy.
	 */
	protected void set(QuestionPickImpl other)
	{
		super.set(other);
		this.origQuestionId = other.origQuestionId;
		this.questionId = other.questionId;
		this.questionService = other.questionService;
	}
}
