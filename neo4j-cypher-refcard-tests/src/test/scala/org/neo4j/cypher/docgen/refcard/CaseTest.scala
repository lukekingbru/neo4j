/**
 * Copyright (c) 2002-2013 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
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
package org.neo4j.cypher.docgen.refcard
import org.neo4j.cypher.{ ExecutionResult, StatisticsChecker }
import org.neo4j.cypher.docgen.RefcardTest

class CaseTest extends RefcardTest with StatisticsChecker {
  def graphDescription = List(
    "A KNOWS B")
  val title = "CASE"
  val css = "read c3-3 c4-3 c5-4"

  override def assert(name: String, result: ExecutionResult) {
    name match {
      case "simple" =>
        assertStats(result, nodesCreated = 0)
        assert(!result.dumpToString.contains("3"))
      case "generic" =>
        assertStats(result, nodesCreated = 0)
        assert(!result.dumpToString.contains("3"))
    }
  }

  override val properties = Map(
    "A" -> Map("name" -> "Alice", "age" -> 38, "eyes" -> "brown"),
    "B" -> Map("name" -> "Beth", "age" -> 38, "eyes" -> "blue"))

  def text = """
###assertion=simple
MATCH n
RETURN

CASE n.eyes
 WHEN 'blue' THEN 1
 WHEN 'brown' THEN 2
 ELSE 3
END

AS result
###

Return `THEN` value from the matching `WHEN` value.
The `ELSE` value is optional, and substituted for `NULL` if missing.

###assertion=generic
MATCH n
RETURN

CASE
 WHEN n.eyes = 'blue' THEN 1
 WHEN n.age < 40 THEN 2
 ELSE 3
END

AS result
###

Return `THEN` value from the first `WHEN` predicate evaluating to `TRUE`.
Predicates are evaluated in order.

"""
}
