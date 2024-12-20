# AOP(Aspect Oreinted Programming)

aop is a programming paradigm or we can say a programming concept. As we know OOPS is alaso one of the programming paradigm. So it doesnt belong to any programming langiage. As OOPS can be implemented in any of the programming language similary AOP can also be implemeneted in any of the programming language.

aop is not introduced by spring, rather the concept of Aspect-Oriented Programming (AOP) was developed by Gregor Kiczales and his colleagues at Xerox PARC (Palo Alto Research Center). They introduced AOP as a way to increase modularity by allowing the separation of cross-cutting concerns, and later developed AspectJ, an aspect-oriented extension to the Java programming language.

Then later on spring has integrated with AspectJ

# What is AOP

As part of our application we will not only have the primary business logic. We also might have some secondary logic that helps our primary logic to either perform better or handle in a better or manage better or looks better, etc.

1. Primary Logic:
   The logic that is mandatory. Without having that logic the existence of the application will not be there such kind of piece of code is being considered as primary business. for example

2. Secondary Logic:
   The logic that which can used in many places and without this one also our core business application can run are called secondary logic. These logic/concerns are often termed crosscutting concerns in AOP literature. for example logging, caching, transaction management, security, etc.

# Problem when mixing secondary logic with primary logic?

1. Code Tangling:
   When cross-cutting concerns such as logging, security, and transaction management are mixed with the main business logic, the code becomes tangled. This makes it difficult to understand, maintain, and modify, as different concerns are mixed together.

2. Code Scattering:
   Cross-cutting concerns are often needed in multiple places. If they are embedded within the primary business logic, we end up with scattered, duplicated code throughout your application. And as we know how diffcicult to maintain the code duplication.

3. Reduced Reusability:
   When cross-cutting logic is embedded in the primary logic, it’s hard to reuse. For example, if we have logging code spread across various methods, reusing it in a new context means copying it everywhere we need it, which is error-prone and inefficient.

4. Increased Complexity:
   Mixing secondary logic with primary logic increases the complexity of the codebase. This makes it harder for developers to understand and follow the business logic because different pieces of logic that don't belong together are mixed in the same place.

5. Difficult Testing:
   Unit testing becomes more challenging when concerns are mixed. Each test must deal with the cross-cutting logic as well as the primary business logic, which complicates setup and can lead to more brittle tests.

6. Inflexibility:
   If the cross-cutting logic needs to change, you'll have to modify every piece of code where it is embedded. This can be time-consuming and error-prone. Aspect-Oriented Programming (AOP) addresses this by centralizing such concerns.

7. Violation of Single Responsibility Principle (SRP):
   The SRP states that a class should have one and only one reason to change. When we mix concerns, a class has multiple responsibilities, violating this principle and making the class harder to manage and evolve.

8. Harder to Enforce Consistency:
   Ensuring consistent behavior across various parts of the application becomes difficult. For instance, if logging needs to follow a specific format, maintaining that format everywhere the logging logic is duplicated is challenging. One user can write the log in different while other user can write in different format. This will be problem when finding the logs when any issues arises.

Using Aspect-Oriented Programming (AOP) helps to solve these issues by allowing we to separate cross-cutting concerns from the primary business logic, leading to cleaner, more maintainable, and more testable code. This modular approach also enhances the reusability and flexibility of the application components.

# AOP Concepts

1. Aspect: It is piece of code (secondary logic or crosscutting logic) which will be separate from the primary logic that has to be applied across various classes of the application.

2. Advice: How we can apply the the aspects. There are multiple types of advice:

   1. Before advice: Advice that runs before a join point but that does not have the ability to prevent execution flow (unless it throws an exception).
   2. After returning advice: Advice to be run after a join point completes normally (for example, if a method returns without throwing an exception).
   3. After throwing advice: Advice to be run if a method exits by throwing an exception.
   4. After (finally) advice: Advice to be run regardless of the means by which a join point exits (normal or exceptional return).
   5. Around Advice: Advice to be run before and after method calls.

3. JoinPoint: The possible set of places/points where we can apply/advice the aspects is called join point. We can apply the secondary logic at the method level or at the constructor level for some initialization or at the variable assignment or at the time static block execution etc. But Spring will permits to apply the crosscutting logic only at the method level.

4. Pointcut: The set of joinpoints where we wanted to advice the cross cutting concerns is called as Pointcut. For example if a class has 3 methods means there are 3 jointpoint but we want to advice on only 2 methods/jointpoints. These 2 joinpoints are called as pointcut. There are 2 types of pointcut.

   1. Static pointcut: Static pointcuts determine the join points (where advice is applied) at compile time. They rely on metadata such as method names, annotations, or parameter types. Spring can evaluate a static pointcut only once, when a method is first invoked. After that, there is no need to evaluate the pointcut again with each method invocation. They use pattern matching on static attributes of code like method signature, annotations, or class names. Once determined, the advice will always be applied at the defined join points.

      ```java
        @Pointcut("execution(* com.example.service.*.*(..))")
        public void serviceLayer() {}
      ```

      This matches all methods in the com.example.service package at compile-time.

   2. Dynamic pointcut: Dynamic pointcuts evaluate join points at runtime based on runtime conditions or the state of the arguments/objects. In addition to the static checks, they execute runtime checks to decide if advice should be applied.
      ```java
      @Pointcut("execution(* com.example.service.*.*(..)) && args(param,..)")
      public void dynamicPointcut(String param) {}
      ```
      Advice is applied only if the method in the com.example.service package receives specific arguments matching runtime conditions.

   Spring initially introduced `programmatic pointcuts` as part of its AOP support. `Annotation-based pointcuts` came later, with the release of `Spring 2.0`, to simplify configuration and align with the growing preference for declarative programming. Programmatic pointcuts, such as those using `StaticMethodMatcherPointcut`, `RegexpMethodPointcutAdvisor`, and `JdkRegexpMethodPointcut`, were part of Spring's original AOP framework. Internally, Spring uses these classes when processing `@Pointcut` expressions.

5. Target: The class in which we wanted to advice the aspect is called as Target. This is primary logic class.

6. Weaving: The process of advising the aspect on the target class based on the pointcut is called as weaving. There are 3 types of weaving.

   1. Compile-Time Weaving: Aspects are woven into the code during the compilation process. The compiler generates the woven bytecode, and the resulting class files include the aspects.

      - AspectJ: Fully supports compile-time weaving.
      - Spring AOP: Does not support compile-time weaving directly.

   2. Load-Time Weaving (LTW): Aspects are woven into the code when the classes are loaded into the JVM. This requires a special class loader that performs the weaving.

      - AspectJ: Fully supports load-time weaving.
      - Spring AOP: Can support load-time weaving if integrated with AspectJ.

   3. Runtime Weaving: Aspects are woven into the code at runtime, typically using dynamic proxies or bytecode manipulation.
      - AspectJ: Supports runtime weaving, although it is less common compared to compile-time and load-time weaving.
      - Spring AOP: Primarily relies on runtime weaving using dynamic proxies or CGLIB proxies. This is the main approach used by Spring AOP for integrating aspects.

7. Proxy: Proxy is the complete logic that results from weaving process. Spring uses either JDK dynamic proxy or a CGLIB proxy. These libraries are used to create classes/interface at runtime.
   Someone asked me or told me if you want to change any class/method behaviour without modifying the class what you will do? We can create another class and extend the exisiting class and override its method change the method behaviour. This is what Spring does and create a new class that is called Proxy class at runtime by using these libraries.

8. Introduction: Adding new methods or fields to a target class dynamically. This is not supported by Spring AOP but supported by AspectJ. But till we can use `Introductions` in Spring but it is limited to introducing interfaces (using `@DeclareParents`) and can’t introduce arbitrary methods or fields.

# Open source AOP Library

1. AspectJ
2. Spring AOP
3. JBoss AOP
4. AspectWerkz
5. JAC (Java Aspect Components)
6. Aspect Faces
7. JAspect

# Spring AOP vs AspectJ

# Pointcut in Programmatic Approach

```java
proxyFactory.addAdvice(new LoggingAspect());
```

- This implicitly applies the advice (LoggingAspect) to all methods of the target class (Calculator).
- The ProxyFactory does not support fine-grained pointcuts directly. It applies the advice to all public methods of the target unless specified otherwise.
- If we want to define more specific pointcuts programmatically, we can use Spring's Pointcut and Advisor interfaces.

```java
package com.altafjava.advice.aftereturn;

import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcut;
import com.altafjava.advice.Calculator;

/**
 * This is the Aspect class implementing cross-cutting concerns.
 */
public class LoggingAspect implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("After Returning Advice: " + method.getName() + ", Result: " + returnValue);
    }
}

public class Test {

    public static void main(String[] args) {
        // Define the pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.addMethodName("add"); // Apply advice only to the "add" method

        // Define the advice
        LoggingAspect advice = new LoggingAspect();

        // Combine pointcut and advice into an Advisor
        org.springframework.aop.support.DefaultPointcutAdvisor advisor =
            new org.springframework.aop.support.DefaultPointcutAdvisor(pointcut, advice);

        // Create a proxy factory and set the target
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Calculator());
        proxyFactory.addAdvisor(advisor); // Add the Advisor to the ProxyFactory

        // Get the proxy object
        Calculator proxyCalculator = (Calculator) proxyFactory.getProxy();

        // Joinpoints
        proxyCalculator.add(10, 20); // Advice will apply (Pointcut matched)
        proxyCalculator.subtract(30, 15); // No advice applied (Pointcut not matched)
    }
}
```

# **Pointcut Designators (PCDs)**

Pointcut Designators (PCDs) are keywords used in pointcut expressions to specify the type of join points to match. Spring supports a subset of AspectJ's PCDs.

### **Types of Pointcut Designators**

| **PCD**              | **Description**                                                                              |
| -------------------- | -------------------------------------------------------------------------------------------- |
| `execution`          | Matches method execution join points.                                                        |
| `within`             | Matches join points within certain types or packages.                                        |
| `this`               | Matches join points where the proxy implements a given type.                                 |
| `target`             | Matches join points where the target object implements a given type.                         |
| `args`               | Matches join points where arguments are of a given type or match certain patterns.           |
| `bean` (Spring-only) | Matches beans with specific names or name patterns (e.g., `bean(*Controller)`).              |
| `@target`            | Matches join points where the target object's class is annotated with a specific annotation. |
| `@within`            | Matches join points within classes annotated with a specific annotation.                     |
| `@annotation`        | Matches methods annotated with a specific annotation.                                        |
| `@args`              | Matches join points where arguments are annotated with specific annotations.                 |

---

## **Syntax for Writing Pointcut Expressions**

### **General Syntax**

1. **Modifiers and Return Types**:  
   Can use wildcards (`*`) to match any access modifier or return type.  
   Example: `execution(public * *(..))` - Matches all public methods.

2. **Declaring Type**:  
   Fully qualified type where the method is declared. Use `*` for all types.  
   Example: `execution(* com.example.service.*.*(..))` - Matches all methods in classes under `com.example.service`.

3. **Method Name**:  
   Specify method names or use patterns (`*` for any).  
   Example: `execution(* get*(..))` - Matches all getter methods.

4. **Parameters**:  
   Specify parameter types or use patterns (`..` for any number of arguments).  
   Example: `execution(* *(String, ..))` - Matches methods with a `String` as the first argument.

---

## **Rules and Syntax for Each PCD**

### 1. **`execution`**

Matches method executions.  
**Syntax**: `execution([modifiers-pattern] [ret-type-pattern] [declaring-type-pattern].method-name-pattern(param-pattern) throws-pattern)`

**Example**:

```java
@Pointcut("execution(* com.example.service.UserService.findUserById(..))")
public void findUserByIdExecution() {}
```

- Matches `findUserById` in `UserService` with any arguments.

**Real-World Usage**:

- Apply logging to all service methods:

```java
@Pointcut("execution(* com.example.service.*.*(..))")
```

---

### 2. **`within`**

Matches all join points within a specific type or package.  
**Syntax**: `within(type-pattern)`

**Example**:

```java
@Pointcut("within(com.example.repository.*)")
public void withinRepositoryLayer() {}
```

- Matches all methods in classes within the `repository` package.

**Real-World Usage**:

- Restrict logging to a specific layer (e.g., repositories).

---

### 3. **`this`**

Matches join points where the proxy object implements the specified type.  
**Syntax**: `this(type)`

**Example**:

```java
@Pointcut("this(com.example.service.MyInterface)")
public void proxyImplementsMyInterface() {}
```

- Matches methods executed on a proxy implementing `MyInterface`.

**Real-World Usage**:

- Ensure advice only applies to specific proxy behaviors.

---

### 4. **`target`**

Matches join points where the target object implements the specified type.  
**Syntax**: `target(type)`

**Example**:

```java
@Pointcut("target(com.example.service.UserService)")
public void targetUserService() {}
```

- Matches methods executed on `UserService` objects.

**Real-World Usage**:

- Differentiate between target object types in layered applications.

---

### 5. **`args`**

Matches join points with arguments matching specific patterns.  
**Syntax**: `args(type-patterns)`

**Example**:

```java
@Pointcut("args(java.lang.String, ..)")
public void methodsWithStringFirstArg() {}
```

- Matches methods where the first argument is a `String`.

**Real-World Usage**:

- Apply validation logic to methods accepting user input as `String`.

---

### 6. **`bean` (Spring-only)**

Matches beans with specific names or patterns.  
**Syntax**: `bean(name-or-pattern)`

**Example**:

```java
@Pointcut("bean(*Controller)")
public void allControllerBeans() {}
```

- Matches all beans ending with "Controller".

**Real-World Usage**:

- Logging or transaction management for specific beans.

---

### 7. **`@target`**

Matches join points where the target class is annotated with a specific annotation.  
**Syntax**: `@target(annotation)`

**Example**:

```java
@Pointcut("@target(org.springframework.stereotype.Service)")
public void targetAnnotatedWithService() {}
```

- Matches all methods in classes annotated with `@Service`.

**Real-World Usage**:

- Apply advice to all services, e.g., adding metrics collection.

---

### 8. **`@within`**

Matches join points within classes annotated with a specific annotation.  
**Syntax**: `@within(annotation)`

**Example**:

```java
@Pointcut("@within(org.springframework.stereotype.Controller)")
public void withinControllerAnnotated() {}
```

- Matches all methods in classes annotated with `@Controller`.

**Real-World Usage**:

- Apply advice to web layer components.

---

### 9. **`@annotation`**

Matches methods annotated with a specific annotation.  
**Syntax**: `@annotation(annotation)`

**Example**:

```java
@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
public void transactionalMethods() {}
```

- Matches methods annotated with `@Transactional`.

**Real-World Usage**:

- Apply retry logic to transactional methods.

---

### 10. **`@args`**

Matches join points where method arguments are annotated with a specific annotation.  
**Syntax**: `@args(annotation-types)`

**Example**:

```java
@Pointcut("@args(com.example.security.Secure)")
public void methodsWithSecureAnnotatedArgs() {}
```

- Matches methods where arguments are annotated with `@Secure`.

**Real-World Usage**:

- Validate user permissions dynamically based on argument annotations.

---

## **Summary of Usage in IT Industry**

- **Performance Monitoring**:

  ```java
  @Pointcut("execution(* com.example..*(..))")
  ```

  - Track execution time across layers.

- **Security Validations**:

  ```java
  @Pointcut("@annotation(com.example.security.RequiresAuth)")
  ```

  - Restrict access to methods requiring authentication.

- **Transaction Management**:

  ```java
  @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
  ```

  - Ensure transactions wrap specific methods.

- **Error Logging**:
  ```java
  @Pointcut("execution(* com.example.service.*.*(..)) && @annotation(com.example.loggable)")
  ```
  - Log errors for annotated service methods.

These flexible pointcut expressions allow developers to seamlessly apply cross-cutting concerns in large-scale IT systems.

# Predefined AOP

Spring extensively uses AOP internally for several of its core features. The most common use cases where AOP is utilized include:

Transaction Management (@Transactional)
Spring uses AOP to manage transactions declaratively. When you annotate a method with @Transactional, Spring creates a proxy for the annotated class and wraps the target method call in transaction management logic.

Exception Handling (@ControllerAdvice and @ExceptionHandler)
@ControllerAdvice and @ExceptionHandler leverage AOP to handle exceptions globally for Spring MVC controllers.

Caching (@Cacheable, @CacheEvict)
Spring uses AOP to manage caching declaratively. When @Cacheable is used, it creates a proxy that checks the cache before executing the target method.

Security (Spring Security)
Spring Security uses AOP for method-level security annotations like @PreAuthorize, @PostAuthorize, etc.

Auditing (Spring Data Auditing)
Spring Data uses AOP for tracking and automatically populating audit-related fields like createdBy and lastModifiedDate.

Custom Logging and Monitoring
You can use AOP to log method executions, monitor performance, or gather metrics.

# @AfterThrowing Advice

```
// @AfterThrowing(pointcut = "calculatorMethods()", throwing = "ex")
@AfterThrowing(pointcut = "execution(* com.altafjava.advice.Calculator.*(..))", throwing = "ex")
public void afterThrowingAdvice(Exception ex) {
    System.out.println("After Throwing Advice - Exception: " + ex.getMessage());
}
```

- The `throwing` attribute specifies the name of the parameter in the advice method that should receive the exception thrown by the join point (the target method).
- When a method matching the pointcut throws an exception, the AOP framework captures it and attempts to pass it to the advice method(`afterThrowingAdvice()`). The parameter name(`ex`) specified in throwing must match the name of the corresponding parameter(`Exception ex`) in the advice method.
- If the throwing attribute is omitted, Spring has no way to map the thrown exception to a parameter in the advice method.
- Consequently the framework considers the advice is incompatible with the exception-handling process and the advice is not invoked, even if the target method throws an exception.

# @Around Advice

ProceedingJoinPoint: A special type of join point automatically passed by the AOP framework to the `@Around` advice method. Provides critical context about the intercepted method, including: Method signature, Method arguments, Ability to proceed with or halt the method execution using the proceed() method.

Adding Extra Parameters to Advice Methods: - Spring AOP only injects the ProceedingJoinPoint object by default. - If we add extra parameters like `String xyz` to the advice method, Spring cannot automatically inject them unless: - They are explicitly defined in the pointcut expression (e.g., using args to match method arguments). - They are annotations on the intercepted method (e.g., @annotation for custom annotations). - Extra parameters are not part of the ProceedingJoinPoint context and require explicit mapping or configuration in the pointcut.

In addition to ProceedingJoinPoint, the advice method can include parameters to bind `method arguments`, `target objects`, or `annotations`. These parameters are determined by the pointcut expression and bindings in the `@Around` annotation.

1. Binding Method Arguments:
   ```java
   @Around("execution(* com.altafjava.service.*.*(..)) && args(arg1, arg2)")
   public Object aroundWithArgs(ProceedingJoinPoint joinPoint, String arg1, int arg2) throws Throwable {
       System.out.println("Before");
       System.out.println("Arguments: " + arg1 + ", " + arg2);
       Object result = joinPoint.proceed();
       System.out.println("After");
       return result
   }
   ```
2. Binding Target Objects:

   ```java
   @Around("execution(* com.altafjava.service.*.*(..)) && target(targetObject)")
   public Object aroundWithTarget(ProceedingJoinPoint joinPoint, Object targetObject) throws Throwable {
       System.out.println("Before");
       System.out.println("Target Object: " + targetObject.getClass().getName());
       Object result = joinPoint.proceed();
       System.out.println("After");
       return result
   }
   ```

3. Binding Annotation Properties:
   ```java
   @Around("@annotation(cacheable)")
   public Object aroundWithAnnotation(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
       System.out.println("Before");
       System.out.println("Cacheable Annotation Name: " + cacheable.value());
       Object result = joinPoint.proceed();
       System.out.println("After");
       return result
   }
   ```
