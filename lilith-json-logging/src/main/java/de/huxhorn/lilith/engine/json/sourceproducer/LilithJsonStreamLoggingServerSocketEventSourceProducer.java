/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2011 Joern Huxhorn
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
package de.huxhorn.lilith.engine.json.sourceproducer;

import de.huxhorn.lilith.data.eventsource.EventWrapper;
import de.huxhorn.lilith.data.eventsource.SourceIdentifier;
import de.huxhorn.lilith.data.logging.LoggingEvent;
import de.huxhorn.lilith.engine.EventProducer;
import de.huxhorn.lilith.engine.impl.sourceproducer.AbstractServerSocketEventSourceProducer;
import de.huxhorn.lilith.engine.json.eventproducer.LilithJsonStreamLoggingEventProducer;
import de.huxhorn.sulky.buffers.AppendOperation;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

public class LilithJsonStreamLoggingServerSocketEventSourceProducer
	extends AbstractServerSocketEventSourceProducer<LoggingEvent>
{
	public LilithJsonStreamLoggingServerSocketEventSourceProducer(int port)
		throws IOException
	{
		super(port);
	}

	protected EventProducer<LoggingEvent> createProducer(SourceIdentifier id, AppendOperation<EventWrapper<LoggingEvent>> eventQueue, InputStream inputStream)
		throws IOException
	{
		return new LilithJsonStreamLoggingEventProducer(id, eventQueue, inputStream);
	}

	@Override
	public String toString()
	{
		return "LilithJsonStreamLoggingServerSocketEventSourceProducer[port=" + getPort() + "]";
	}

}
