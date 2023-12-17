# architecture-patterns-with-java
Based on the book architecture-patterns-with-python

# Books to read
1. Domain Driven Design
2. Patterns of Enterprise Application Architecture
3. Implementing Domain-Driven Design

# Tools for managing complexity
1. Test-driven-development (TDD)
   1. Enable refactor and add new features without fear of regression
2. Domain-driven-design (DDD)
   1. Build a good model of the business domain
3. Loosely coupled (micro)services integrated via messages (reactive microservices)

# Part I, Building an Architecture to Support Domain Modeling
1. Domain modeling and DDD <br>
  The domain is a fancy way of saying the problem you're trying to solve.
  The domain model is the mental map that business owners have of their businesses.
   1. Encapsulation and abstraction
      1. simplifying behavior <br>
         We encapsulate behavior by identifying a task that needs to be done in our code and giving that task to a well-defined object or function (an abstraction).<br>
         Encapsulating behavior by using abstractions is a powerful tool to making code more expressive, more testable, and easier to maintain.
      2. hiding data
   2. The dependency inversion principle
      1. Depends on doesn't mean imports or call, necessarily, but rather a more general idea that one module knows about or needs an other module 
   3. Whenever we have a business concept that has data but no identity, we often choose to represent it using the Value Object pattern. A value object is any domain object that is uniquely identified by the data it holds; we usually make them immutable.
   4. We use the term entity to describe a domain that has long-lived identity.
   5. Exceptions can express domain concepts too
   6. Domain services are not the same thing as the services from the service layer. Although they are often closed related. A domain service represents a business concept or process, whereas a service-layer service represents a use case for you application. Often the service layer will call a domain service.
2. Repository, Service Layer, and Unit of Work patterns
   1. Building fakes your abstractions is an excellent way to get design feedback. if it's hard to fake, the abstraction is probably too complicated
   2. Ports and adapters came out of the OO word, and the definition we hold onto is that the port is the interface between our application and whatever it is we wish to abstract away, and the adapter is the implementation behind that interface or abstraction.
   3. Apply dependency inversion to your ORM
   4. Our domain model should be free of infrastructure concerns, so your ORM should import your model, and not the other way around.
   5. Functional Core, Imperative shell
   6. Service layer pattern to take care of orchestrating out workflows and defining the use cases of our system.
   7. All the orchestration logic is in the user case/service layer, and the domain logic stays in the domain
   8. Application service(our service layer). Its job is to handle requests from the outside world and to orchestrate an operation. What we mean is that the service layer drives the application by following a bunch of simple steps:
      1. Get some data from the database
      2. Update the domain model
      3. Persist any changes
   9. Domain service this is the name for a piece of logic that belongs in the domain model but doesn't sit naturally inside a stateful entity or value object.
   10. For a more complex application, you might have one file per class, you might have helper parent classes  for Entity, ValueObject, and Aggregate, and you might add an exceptions for domain-layer exceptions and, as you'll see, commands and events
3. Some thoughts about testing and abstractions
   1. Designing for testability really means designing for extensibility,
   2. Mock -> London-School TDD
   3. Fake are working implementations of the thing they're replacing, but they're designed for use only in tests. They wouldn't work "in real life"; our in-memory repository is a good example. But you can use them to make assertions about the end state of a system rather than the behaviours along the way, so they're associated with classic-style TDD
   4. Mocks Aren't Stubs
   5. We can make our systems easier to test and maintain by simplifying the interface between our business logic and messy IO

# Part II, Event-Driven Architecture
1. Event-driven architecture
2. Command-query responsibility segregation
3. Dependency injection