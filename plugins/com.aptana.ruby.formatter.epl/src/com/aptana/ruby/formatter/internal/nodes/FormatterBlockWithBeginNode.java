/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package com.aptana.ruby.formatter.internal.nodes;

import java.util.ArrayList;
import java.util.List;

import com.aptana.formatter.FormatterBlockNode;
import com.aptana.formatter.IFormatterContext;
import com.aptana.formatter.IFormatterDocument;
import com.aptana.formatter.IFormatterTextNode;
import com.aptana.formatter.IFormatterWriter;

public abstract class FormatterBlockWithBeginNode extends FormatterBlockNode
{

	/**
	 * @param document
	 */
	public FormatterBlockWithBeginNode(IFormatterDocument document)
	{
		super(document);
	}

	private IFormatterTextNode begin;

	public void accept(IFormatterContext context, IFormatterWriter visitor) throws Exception
	{
		if (begin != null)
		{
			visitor.write(context, begin.getStartOffset(), begin.getEndOffset());
		}
		// TODO - Shalom: Check what's the effect of that change
		// final boolean indenting = isIndenting();
		// if (indenting) {
		context.incIndent();
		// }
		super.accept(context, visitor);
		// if (indenting) {
		context.decIndent();
		// }
	}

	/**
	 * @return the begin
	 */
	public IFormatterTextNode getBegin()
	{
		return begin;
	}

	/**
	 * @param begin
	 *            the begin to set
	 */
	public void setBegin(IFormatterTextNode begin)
	{
		this.begin = begin;
	}

	/*
	 * @see org.eclipse.dltk.ruby.formatter.node.FormatterBlockNode#getStartOffset()
	 */
	public int getStartOffset()
	{
		if (begin != null)
		{
			return begin.getStartOffset();
		}
		return super.getStartOffset();
	}

	/*
	 * @see org.eclipse.dltk.ruby.formatter.node.FormatterBlockNode#getEndOffset()
	 */
	public int getEndOffset()
	{
		if (!super.isEmpty())
		{
			return super.getEndOffset();
		}
		if (begin != null)
		{
			return begin.getEndOffset();
		}
		return DEFAULT_OFFSET;
	}

	/*
	 * @see org.eclipse.dltk.ruby.formatter.node.FormatterBlockNode#isEmpty()
	 */
	public boolean isEmpty()
	{
		return begin == null && super.isEmpty();
	}

	/*
	 * @see org.eclipse.dltk.formatter.nodes.FormatterBlockNode#getChildren()
	 */
	public List getChildren()
	{
		if (begin == null)
		{
			return super.getChildren();
		}
		else
		{
			List result = new ArrayList();
			if (begin != null)
			{
				result.add(begin);
			}
			result.addAll(super.getChildren());
			return result;
		}
	}

	/*
	 * @see org.eclipse.dltk.ruby.formatter.node.FormatterBlockNode#toString()
	 */
	public String toString()
	{
		return begin + "\n" + super.toString();
	}

}
