## Transaction Management in Spring

A common reason to use transactions is to maintain a high degree of data integrity and consistency. Transactions, however, impact the performance, introduce locking issues and database concurrency problems.

Transaction not only provides **atomicity**, which ensures that all updates are treatesd as a single unit and are either all committed or all rolled back. Transaction also provides **isolation**, which ensures that one unit of work is isolated from other units of work. Without proper transaction isolation, other units of work can access updates made by an ongoing unit of work, even though that unit of work is incomplete.

### Local transaction pitfalls

**Performing multiple table updates in the same method**

```java
public TradeData placeTrade(TradeData data) throws Exception {
    try {
        insertTrade(data);
        updateAccount(data);
        return data;
    } catch (Exception e) {
        //log the error
        throw e;
    }
}
```

In this case, the `insertTrade()` and `updateAccount()` methods use standard JDBC code without transactions. Once `insertTrade()` method ends, the database has persisted (and committed) the trade order. If the `updateAccount()` method should fail for any reason, the trade order would remain in the `TRADE` table at the end of the `placeTrade()` method, resulting in inconsistent data in the database.

If the `placeTrade()` method had used transactions, both of these activities would have been included in a single transaction, and the trade would have been rolled back if the account update failed.

If instead, we use ORM-based frameworks, we must use transactions. **ORM-based frameworks require a transaction in order to trigger the synchronization between the object cache and the database**. It is through a transaction commit, that the SQL code is generated and the database affected by the desired action (insert, update, or delete). Without a transaction there is no trigger for the ORM to generate SQL code and persist the changes, so the method simply ends - no exceptions, no updates. 

### Spring Framework @Transactional annotation pitfalls

### @Transactional read-only flag pitfalls

### REQUIRES_NEW transaction attribute pitfalls

### Using REQUIRES_NEW

Using `REQUIRES_NEW` is only relevant when the method is invoked from a transactional context; otherwise it will behave exactly as `REQUIRED`.

On rollback - using `REQUIRES_NEW` will force the start of a new transaction, and so an exception will rollback that transaction. If there is another transaction that was executing as well - that will or will not be rolled back depending on if the exception bubbles up the stack or is caught. 

### Transaction rollback pitfalls

