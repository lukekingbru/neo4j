/*
 * Copyright (c) 2002-2016 "Neo Technology,"
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
package org.neo4j.kernel.impl.store;

import org.neo4j.kernel.impl.transaction.log.TransactionIdStore;

/**
 * Transaction id plus meta data that says something about its contents, for comparison.
 */
public class TransactionId
{
    private long transactionId;
    private long checksum;

    public TransactionId( long transactionId, long checksum )
    {
        this.transactionId = transactionId;
        this.checksum = checksum;
    }

    /**
     * Transaction id, generated by {@link TransactionIdStore#nextCommittingTransactionId()},
     * here accessible after that transaction being committed.
     */
    public long transactionId()
    {
        return transactionId;
    }

    /**
     * Checksum of a transaction, generated from transaction meta data or its contents.
     */
    public long checksum()
    {
        return checksum;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        TransactionId that = (TransactionId) o;
        return transactionId == that.transactionId && checksum == that.checksum;
    }

    @Override
    public int hashCode()
    {
        int result = (int) (transactionId ^ (transactionId >>> 32));
        result = 31 * result + (int) (checksum ^ (checksum >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "{" +
                "transactionId=" + transactionId +
                ", checksum=" + checksum +
                '}';
    }
}