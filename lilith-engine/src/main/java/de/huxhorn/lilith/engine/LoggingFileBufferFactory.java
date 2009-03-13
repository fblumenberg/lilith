/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2009 Joern Huxhorn
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
package de.huxhorn.lilith.engine;

import de.huxhorn.lilith.data.logging.LoggingEvent;
import de.huxhorn.lilith.data.logging.protobuf.LoggingEventWrapperProtobufCodec;
import de.huxhorn.lilith.data.logging.protobuf.CompressingLoggingEventWrapperProtobufCodec;
import de.huxhorn.lilith.data.eventsource.EventWrapper;
import de.huxhorn.lilith.engine.impl.LoggingEventWrapperXmlCodec;
import de.huxhorn.lilith.engine.impl.CompressingLoggingEventWrapperXmlCodec;
import de.huxhorn.sulky.codec.Codec;
import de.huxhorn.sulky.codec.SerializableCodec;
import de.huxhorn.sulky.codec.CompressingSerializableCodec;
import de.huxhorn.sulky.codec.filebuffer.MetaData;

import java.util.Map;

public class LoggingFileBufferFactory
	extends FileBufferFactory<LoggingEvent>
{
	public LoggingFileBufferFactory(LogFileFactory logFileFactory, Map<String, String> metaData)
	{
		super(logFileFactory, metaData);
	}

	public Codec<EventWrapper<LoggingEvent>> resolveCodec(MetaData metaData)
	{
		boolean compressed = false;
		String format = null;

		if(metaData != null)
		{
			Map<String, String> data = metaData.getData();
			compressed = Boolean.valueOf(data.get(FileConstants.COMPRESSED_KEY));
			format = data.get(FileConstants.CONTENT_FORMAT_KEY);
		}

		Codec<EventWrapper<LoggingEvent>> codec;

		if(FileConstants.CONTENT_FORMAT_VALUE_JAVA_BEANS_XML.equals(format))
		{
			if(compressed)
			{
				codec=new CompressingLoggingEventWrapperXmlCodec();
			}
			else
			{
				codec=new LoggingEventWrapperXmlCodec();
			}
		}
		else if(FileConstants.CONTENT_FORMAT_VALUE_PROTOBUF.equals(format))
		{
			if(compressed)
			{
				codec=new CompressingLoggingEventWrapperProtobufCodec();
			}
			else
			{
				codec=new LoggingEventWrapperProtobufCodec();
			}
		}
		else
		{
			if(compressed)
			{
				codec = new CompressingSerializableCodec<EventWrapper<LoggingEvent>>();
			}
			else
			{
				codec = new SerializableCodec<EventWrapper<LoggingEvent>>();
			}
		}
		return codec;
	}
}