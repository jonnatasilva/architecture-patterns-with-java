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
3. Some thoughts about testing and abstractions

# Part II, Event-Driven Architecture
1. Event-driven architecture
2. Command-query responsibility segregation
3. Dependency injection