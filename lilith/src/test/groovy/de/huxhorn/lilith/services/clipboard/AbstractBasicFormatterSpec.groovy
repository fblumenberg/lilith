/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2016 Joern Huxhorn
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

package de.huxhorn.lilith.services.clipboard

import de.huxhorn.lilith.services.BasicFormatter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

abstract class AbstractBasicFormatterSpec extends Specification {

	private static final Logger logger = LoggerFactory.getLogger(AbstractBasicFormatterSpec)
	def setupSpec() {
		System.setProperty('java.awt.headless', 'true')
	}

	def "works on corpus."() {
		setup:
		def instance = createInstance()
		def expectedIndices = expectedIndices()
		def expectedResults = expectedResults()
		def excludedIndices = excludedIndices()

		when:
		Set<Integer> compatibleIndices = BasicFormatterCorpus.isCompatible(instance, excludedIndices)
		List<String> results = BasicFormatterCorpus.toString(instance, excludedIndices)

		then:
		compatibleIndices == expectedIndices

		and:
		for(int i=0;i<results.size();i++) {
			if(compatibleIndices.contains(i)) {
				assert results[i] != null
			} else {
				assert results[i] == null
			}
		}

		and:
		results.removeIf({ it == null })

		results == expectedResults

		and:
		// sanity check
		results.size() == expectedIndices.size()
	}

	abstract BasicFormatter createInstance()
	abstract Set<Integer> expectedIndices()
	abstract List<String> expectedResults()

	Set<Integer> excludedIndices() {
		[]
	}
}
