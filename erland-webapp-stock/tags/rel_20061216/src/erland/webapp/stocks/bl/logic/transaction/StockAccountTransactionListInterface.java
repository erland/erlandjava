package erland.webapp.stocks.bl.logic.transaction;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.webapp.stocks.bl.logic.transaction.StockAccountTransaction;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionFilterInterface;

import java.util.Iterator;
import java.util.Date;

public interface StockAccountTransactionListInterface {
    public Iterator iterator();
    public StockAccountTransaction[] toArray();
    public StockAccountTransaction getTransaction(int i);
    public int size();
    public StockAccountTransaction getTransaction(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter);
    public StockAccountTransactionListInterface getTransactions(String broker, String stock, Date fromDate, Date toDate, StockAccountTransactionFilterInterface filter);
    public StockAccountTransaction getTransactionBefore(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter);
    public StockAccountTransaction getTransactionAfter(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter);
}
