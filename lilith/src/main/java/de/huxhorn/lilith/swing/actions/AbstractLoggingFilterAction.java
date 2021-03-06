/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2015 Joern Huxhorn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.huxhorn.lilith.swing.actions;

import de.huxhorn.lilith.data.eventsource.EventWrapper;
import de.huxhorn.lilith.data.logging.LoggingEvent;

import javax.swing.Icon;
import java.io.Serializable;

public abstract class AbstractLoggingFilterAction
	extends AbstractFilterAction
{
	private static final long serialVersionUID = -2521087800102653740L;

	protected LoggingEvent loggingEvent;

	protected AbstractLoggingFilterAction(boolean htmlTooltip)
	{
		super(htmlTooltip);
	}

	protected AbstractLoggingFilterAction(String name, boolean htmlTooltip)
	{
		super(name, htmlTooltip);

	}

	protected AbstractLoggingFilterAction(String name, Icon icon, boolean htmlTooltip)
	{
		super(name, icon, htmlTooltip);
	}

	@Override
	public final void setEventWrapper(EventWrapper eventWrapper)
	{
		setLoggingEvent(resolveLoggingEvent(eventWrapper));
	}

	public final void setLoggingEvent(LoggingEvent loggingEvent)
	{
		this.loggingEvent = loggingEvent;
		updateState();
	}

	public static LoggingEvent resolveLoggingEvent(EventWrapper eventWrapper)
	{
		if(eventWrapper == null)
		{
			return null;
		}
		Serializable event = eventWrapper.getEvent();
		if(event == null)
		{
			return null;
		}
		if(event instanceof LoggingEvent)
		{
			return (LoggingEvent) event;
		}
		return null;
	}
}
