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
package de.huxhorn.lilith.conditions

import spock.lang.Specification
import spock.lang.Unroll

import static de.huxhorn.sulky.junit.JUnitTools.testClone
import static de.huxhorn.sulky.junit.JUnitTools.testSerialization
import static de.huxhorn.sulky.junit.JUnitTools.testXmlSerialization

class HttpStatusTypeConditionSpec extends Specification {
	@Unroll
	def "Corpus works as expected for #condition (searchString=#input)."() {
		expect:
		ConditionCorpus.executeConditionOnCorpus(condition) == expectedResult

		where:
		input           | expectedResult
		null            | [] as Set
		''              | [] as Set
		'snafu'         | [] as Set
		'INFORMATIONAL' | [52] as Set
		'SUCCESSFUL'    | [53, 54] as Set
		'REDIRECTION'   | [55] as Set
		'CLIENT_ERROR'  | [56, 57] as Set
		'SERVER_ERROR'  | [58] as Set

		'info'          | [52] as Set
		'succ'          | [53, 54] as Set
		'red'           | [55] as Set
		'cl'            | [56, 57] as Set
		'se'            | [58] as Set

		'1'             | [52] as Set
		'2'             | [53, 54] as Set
		'3'             | [55] as Set
		'4'             | [56, 57] as Set
		'5'             | [58] as Set

		' 1 '           | [52] as Set
		' 2 '           | [53, 54] as Set
		' 3 '           | [55] as Set
		' 4 '           | [56, 57] as Set
		' 5 '           | [58] as Set

		'1X'            | [52] as Set
		'2X'            | [53, 54] as Set
		'3X'            | [55] as Set
		'4X'            | [56, 57] as Set
		'5X'            | [58] as Set

		'1xx'           | [52] as Set
		'2xx'           | [53, 54] as Set
		'3xx'           | [55] as Set
		'4xx'           | [56, 57] as Set
		'5xx'           | [58] as Set

		condition = new HttpStatusTypeCondition(input)
	}

	@Unroll
	def "serialization works with searchString #input."() {
		when:
		def condition = new HttpStatusTypeCondition()
		condition.searchString = input

		and:
		def result = testSerialization(condition)

		then:
		result.searchString == input
		result.statusType == condition.statusType

		where:
		input << inputValues()
	}

	@Unroll
	def "XML serialization works with searchString #input."() {
		when:
		def condition = new HttpStatusTypeCondition()
		condition.searchString = input

		and:
		def result = testXmlSerialization(condition)

		then:
		result.searchString == input
		result.statusType == condition.statusType

		where:
		input << inputValues()
	}

	@Unroll
	def "cloning works with searchString #input."() {
		when:
		def condition = new HttpStatusTypeCondition()
		condition.searchString = input

		and:
		def result = testClone(condition)

		then:
		result.searchString == input
		result.statusType == condition.statusType

		where:
		input << inputValues()
	}

	def "equals behaves as expected."() {
		setup:
		def instance = new HttpStatusTypeCondition()
		def other = new HttpStatusTypeCondition('4xx')

		expect:
		instance.equals(instance)
		!instance.equals(null)
		!instance.equals(new Object())
		!instance.equals(other)
		!other.equals(instance)
	}

	def inputValues() {
		[null, '', 'value', '4xx']
	}
}
