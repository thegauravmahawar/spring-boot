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

### How to use Spring's @Transactional annotation

```java
public class UserService {

    @Transactional
    public long registerUser(User user) {
        //insert user in DB
        return id;
    }
}
```

How this works:

- Make sure that your Spring Configuration is annotated with `@EnableTransactionManagement` annotation (in Spring Boot this will be done automatically)
- Make sure you specify a transaction manager in your Spring Configuration.
- And then Spring is smart enough to transparently handle transactions. Any bean's `public` method we annotate with `@Transactional` annotation, will execute inside a database transaction.

**To get the @Transactional annotation working**

```java
@Configuration
@EnableTransactionManagement
public class MySpringConfig {

    @Bean
    public PlatformTransactionManager txManager() {
        return txManager;
    }
}
```

The `@Transactional` UserService code above translates (simplified) to:

```java
public class UserService {

    public Long registerUser(User user) {
        Connection connection = dataSource.getConnection();
        try (connection) {
            connection.setAutoCommit(false);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }
}
```

### @Transactional under the covers

Spring cannot really rewrite our class, to insert the connection code. The `registerUser()` method really just calls `userDao.save(user)`, there's no way to change that on the fly.

Spring, however, has an advantage. At its core, it is an IoC container. It instantiates a `UserService` for us and makes sure to autowire that `UserService` into any other bean that needs a `UserService`.

Now whenever we are using `@Transactional` on a bean, Spring uses a tiny trick. It does not just instantiate a UserService, but also a transactional proxy of that `UserService`.

The `UserService` get proxied on the fly, and the proxy manages transactions. But it is not the proxy itself handling all this transactional state (open, commit, close), the proxy delegates that work to a transaction manager.

### Spring Framework @Transactional annotation pitfalls

`@Transactional` annotation by itself without any parameters, the propogation mode is set to `REQUIRED`, the read-only flag is set to `false`, the transaction isolation level is set to the database default (usually `READ_COMMITTED`), and the transaction will not rollback on a checked exception.

### REQUIRES_NEW transaction attribute pitfalls

The `REQUIRES_NEW` transaction attribute always starts a new transaction when the method is started, whether or not an existing transaction is present. 

Using `REQUIRES_NEW` is only relevant when the method is invoked from a transactional context; otherwise it will behave exactly as `REQUIRED`.

**Using the REQUIRES_NEW transaction attribute**

```java
@Transactional(propogation = REQUIRES_NEW)
public long insertTrade(Trade trade) throws Exception {}

@Transactional(propogation = REQUIRES_NEW)
public void updateAccount(Trade trade) throws Exception {}
```

Both of these methods are `public`, implying that they can be invoked independently from each other. Problems occur with the `REQUIRES_NEW` attribute when methods using it are invoked within the same logical unit of work via inter-service communication or through orchestration. 

For example, suppose we invoke the `updateAccount()` method independently of any other method in some use cases, but there's also the case where the `updateAccount()` method is also invoked in the `insertTrade()` method. Now, if an exception occurs after the `updateAccount()` method call, the trade order would be rolled back, but the account updates would be committed to the database.

**Multiple updates using the REQUIRES_NEW transaction attribute

```java
@Transactional(propogation = REQUIRES_NEW)
public long insertTrade(Trade trade) throws Exception {
    em.persist(trade);
    updateAccount(trade);
    //exception occurs here! Trade rolled back but account update is not!
}
```

This happens because a new transaction is started in the `updateAccount()` method, so that transaction commits once the `updateAccount()` method ends. When we use the `REQUIRES_NEW` transaction attribute, if an existing transaction context is present, the current transaction is suspended and a new transaction started. Once that method ends, the new transaction commits and the original transaction resumes. 

Because of this behavior, the `REQUIRES_NEW` transaction attribute should be used only if the database action in the method being invoked needs to be saved to the database regardless of the outcome of the overlaying transaction.

### Transaction rollback pitfalls

**No rollback support**

```java
@Transactional(propogation = REQUIRED)
public TradeData placeTrade(Trade trade) throws Exception {
    try {
        insertTrade(trade);
        updateAccount(trade);
        return trade;
    } catch (Exception e) {
        //log the error
        throw e;
    }
}
```

Suppose the account does not have enough funds to purchase the stock and throws a checked exception. Does the trade order get persisted in the database or is the entire logical unit of work rolled back? Upon a checked exception, the transaction commits any work that has not yet been committed. If a checked exception occurs during the `updateAccount()` method, the trade order is persisted, but the account isn't updated to reflect the trade.

Run-time exceptions automatically force the entire logical unit of work to roll back, but checked exceptions do not. 

The reason for this behavior is that, not all checked exceptions are bad; they might be used for event notification or to redirect processing based on certain conditions. But more to the point, the application code may be able to take corrective action on some types of checked exceptions, thereby allowing the transaction to complete. 

When we use `Declarative` transaction model, we must specify how the container or framework should handle checked exceptions.

**Adding transaction rollback support**

```java
@Transactional(propogation = REQUIRED, rollbackFor = Exception.class)
public TradeData placeTrade(Trade trade) throws Exception {
    try {
        insertTrade(trade);
        updateAccount(trade);
        return trade;
    } catch (Exception e) {
        //log the error
        throw e;
    }
}
```

On rollback - using `REQUIRES_NEW` will force the start of a new transaction, and so an exception will rollback that transaction. If there is another transaction that was executing as well - that will or will not be rolled back depending on if the exception bubbles up the stack or is caught. 