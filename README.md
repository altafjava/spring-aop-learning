# Aspect Oreinted Programming

Aspect-Oriented Programming (AOP) is a programming paradigm or concept. Similar to Object-Oriented Programming (OOP), which is also a programming paradigm, AOP is not tied to any specific programming language. Just as OOP can be implemented in various languages, AOP can also be implemented in multiple programming languages.

AOP was not introduced by Spring Framework. Instead, the concept was developed by Gregor Kiczales and his colleagues at Xerox PARC (Palo Alto Research Center). They introduced AOP to enhance modularity by enabling the separation of cross-cutting concerns, which are aspects of a program that affect multiple components. They also created AspectJ, an aspect-oriented extension to the Java programming language, to demonstrate the practical implementation of AOP.

# What is AOP

In application development, we often deal with two types of logic:

1. **Primary Logic**:
   This is the core business logic that is essential for the application's existence. Without this logic, the application cannot function. For example:

   - The calculation of interest in a banking application.
   - User authentication in a login system.
   - Order processing in an e-commerce platform.
   - Data validation in a form submission.
   - Payment processing in a financial application.
   - Inventory management in a retail system.

2. **Secondary Logic (Cross-Cutting Concerns)**:
   This logic is not mandatory for the core functionality of the application but supports it by improving performance, manageability, or user experience. Secondary logic is often reused across multiple parts of the application and is known as `cross-cutting concerns` in AOP terminology. Examples:
   - **Logging**: Recording system events for monitoring or debugging.
   - **Caching**: Improving performance by storing frequently used data temporarily.
   - **Transaction Management**: Ensuring data consistency during operations.
   - **Security**: Enforcing access controls and data protection.

AOP provides a mechanism to separate these cross-cutting concerns from the primary business logic, enhancing modularity and maintainability. This separation ensures that the primary logic remains focused and uncluttered, while the secondary logic can be applied declaratively or dynamically across the application.

# Problem when mixing secondary logic with primary logic?

1. **Code Tangling**:
   When cross-cutting concerns such as `logging`, `security`, and `transaction management` are mixed with the main business logic, the code becomes tangled. This makes it `difficult to understand`, `maintain`, and `modify`. It increases the complexity of the codebase and makes harder for developers to understand and follow the business logic because different pieces of logic that don't belong together are mixed in the same place.

2. **Code Scattering**:
   Cross-cutting concerns are often required in multiple places. Embedding them within the primary logic leads to scattered and `duplicated code` throughout the application. This duplication makes the codebase harder to `maintain`, `manage`, and `reuse`.

3. **Difficult Testing**:
   Unit testing becomes more challenging when concerns are mixed. Each test must deal with the cross-cutting logic as well as the primary business logic, which complicates setup and can lead to more `brittle tests`.

4. **Inflexibility**:
   If the cross-cutting logic needs to change, we will have to modify every piece of code where it is embedded. This can be time-consuming and error-prone.

5. **Violation of Single Responsibility Principle (SRP)**:
   The SRP states that a class should have one and only one reason to change. When we mix concerns, a class has multiple responsibilities, violating this principle and making the class harder to manage and evolve.

6. **Harder to Enforce Consistency**:
   Ensuring consistent behavior across various parts of the application becomes difficult. For instance, if security checks or transaction management logic are embedded within the primary business logic, maintaining consistency in how these concerns are handled can be challenging. Different developers might implement security checks or transaction management in slightly different ways, leading to inconsistencies and potential vulnerabilities or errors. This inconsistency can be problematic when trying to ensure that all parts of the application adhere to the same security policies or transaction boundaries.

AOP helps to solve these issues by allowing to separate cross-cutting concerns from the primary business logic, leading to cleaner, more maintainable, and more testable code. This modular approach also enhances the reusability and flexibility of the application components.

# AOP Concepts

1. **Aspect**  
   An aspect is a piece of code (secondary logic or cross-cutting concern) that is separated from the primary business logic. It encapsulates functionality like logging, security, or transaction management, which can be applied across multiple classes in an application.

2. **Advice**  
   Advice defines _how_ and _when_ an aspect's logic is applied. There are several types of advice in AOP:

   - **Before Advice**: Executes before a join point but cannot prevent the execution flow (unless it throws an exception).
   - **After Returning Advice**: Executes after a join point completes successfully without exceptions.
   - **After Throwing Advice**: Executes if a join point exits by throwing an exception.
   - **After (Finally) Advice**: Executes regardless of how a join point exits (normal return or exception).
   - **Around Advice**: Executes both before and after a method call, and it can control whether the method execution proceeds.

3. **JoinPoint**  
   The possible set of places/points where we can apply/advice the aspects is called join point. We can apply the secondary logic at the method level or constructor level or at the variable assignment or at the time static block execution etc. But Spring AOP support only at the **method level**.

4. **Pointcut**  
   The set of joinpoints where we wanted to advice the cross cutting concerns is called as Pointcut. For example if a class has 3 methods, it means there are 3 jointpoint and we want to advice on only 2 methods/jointpoints. These 2 joinpoints are called as pointcut. There are 2 types of pointcut.

   1. **Static pointcut**: Static pointcuts determine the join points at compile time. They rely on metadata such as method names, annotations, or parameter types. Spring can evaluate a static pointcut only once, when a method is first invoked. After that, there is no need to evaluate the pointcut again with each method invocation.

      ```java
        @Pointcut("execution(* com.example.service.*.*(..))")
        public void serviceLayer() {}
      ```

   2. **Dynamic pointcut**: Dynamic pointcuts evaluate join points at runtime based on runtime conditions or the state of the arguments/objects. In addition to the static checks, they execute runtime checks to decide if advice should be applied.
      ```java
      @Pointcut("execution(* com.example.service.*.*(..)) && args(param,..)")
      public void dynamicPointcut(String param) {}
      ```
      Advice is applied only if the method in the `com.example.service` package receives specific arguments `param` matching runtime conditions.

   **Note:** _Spring initially introduced Programmatic Pointcuts as part of its AOP support. Annotation-based Pointcuts were introduced later, with the release of `Spring 2.0`, to simplify configuration and align with the growing preference for declarative programming. Programmatic pointcuts, such as those using `StaticMethodMatcherPointcut`, `RegexpMethodPointcutAdvisor`, and `JdkRegexpMethodPointcut`, were part of Spring's original AOP framework. Internally, Spring uses these classes when processing `@Pointcut` expressions to match join points_.

5. **Target**  
   The target is the class or object on which the aspect is adviced. It contains the primary business logic, and the aspect provides additional behavior without modifying the target class directly.

6. **Weaving**  
   The process of advising the aspect on the target class based on the defined pointcut is called as weaving. There are 3 types of weaving.

   1. **Compile-Time Weaving**: The aspects are directly woven into the bytecode of the target classes at compile-time.

      - **AspectJ**: Fully supports compile-time weaving. Requires the AspectJ compiler (`ajc`).
      - **Spring AOP**: Does not support compile-time weaving directly.

   2. **Load-Time Weaving (LTW)**: Aspects are woven into the code when the classes are loaded into the JVM. This requires a weaving-enabled class loader and AspectJ LTW configuration. Allows us to apply aspects without modifying the compiled class files.

      - **AspectJ**: Fully supports load-time weaving.
      - **Spring AOP**: Can support load-time weaving if integrated with AspectJ.

   3. **Runtime Weaving**: Weaving is done at runtime by dynamically creating proxies (via `JDK dynamic proxies` or `CGLIB`).
      - **AspectJ**: Supports runtime weaving, although it is less common compared to compile-time and load-time weaving. AspectJ is primarily designed for static weaving (CTW and LTW), which allows for more powerful and efficient transformations of bytecode.
      - **Spring AOP**: This is the default behavior of Spring AOP.

7. **Proxy**  
   A proxy is a dynamically created object that acts as a substitute for another object. It contains the complete logic resulting from the weaving process. Spring uses proxies to implement AOP features:

   - **JDK Dynamic Proxy**: Used for creating proxies for interfaces.
   - **CGLIB Proxy**: Used for creating proxies for concrete classes.

   **Note:** _Someone asked me how to change the behavior of a class or method without modifying the original class? One way to achieve this is by creating another class, extending the existing class, and overriding its methods to change their behavior. However, Spring simplifies this process by dynamically creating a new class at runtime, known as a proxy class, to modify or enhance the behavior of the original class or method. This is achieved using libraries like JDK dynamic proxies (for interfaces) or CGLIB (for classes)_.

8. **Introduction**  
   Introduction allows adding new methods or fields to a target class dynamically.
   - **Spring AOP**: Supports introductions but only for adding interfaces to a target class using `@DeclareParents`.
   - **AspectJ**: Fully supports arbitrary introductions of methods or fields.

# Most Popular Open Source AOP Libraries

1. **AspectJ**

   - Description: The most powerful and widely-used AOP library.
   - Features: Supports compile-time, load-time, and runtime weaving. Works directly on bytecode.
   - Use Case: Complex cross-cutting concerns requiring more than method-level interception.

2. **Spring AOP**

   - Description: A simpler AOP solution integrated into the Spring Framework.
   - Features: Proxy-based; supports runtime weaving only (method-level join points).
   - Use Case: Enterprise applications with straightforward AOP requirements.

3. **JBoss AOP**

   - Description: A robust AOP framework developed by Red Hat.
   - Features: Supports compile-time, load-time, and runtime weaving.
   - Use Case: Cross-cutting concerns in JBoss applications.

4. **Apache Commons Proxy**

   - Description: A library for creating lightweight proxies, used for AOP implementations.
   - Features: Focuses on proxy creation, providing a foundation for custom AOP solutions.
   - Use Case: Lightweight proxying for basic AOP needs.

5. **Guice AOP**

   - Description: A lightweight AOP module provided by Google Guice.
   - Features: Uses dynamic proxies; integrates well with Guice's dependency injection.
   - Use Case: AOP in applications using Google Guice for dependency injection.

6. **Seasar2 (S2AOP)**

   - Description: Part of the Seasar2 framework, providing AOP functionality.
   - Features: Proxy-based and annotation-driven configuration.
   - Use Case: Japanese enterprise applications and lightweight AOP needs.

7. **dynaop**

   - Description: A lightweight and highly dynamic AOP framework.
   - Features: Pure runtime-based AOP without compile-time or load-time weaving.
   - Use Case: Applications requiring a lightweight, dynamic proxy-based approach.

8. **Nanning**

   - Description: A straightforward AOP library with dependency injection features.
   - Features: Simple runtime-based AOP; supports proxies and lightweight cross-cutting concerns.
   - Use Case: Small projects needing basic AOP features.

9. **JAC (Java Aspect Components)**

   - Description: An open-source AOP framework focusing on dynamic weaving.
   - Features: Combines AOP with component-based software engineering principles.
   - Use Case: Modular software systems with dynamic behavior changes.

10. **AOP Alliance**
    - Description: A set of standard interfaces for AOP frameworks, providing interoperability.
    - Features: Simplifies the integration of AOP features into various frameworks.
    - Use Case: Standardizing AOP implementations across libraries.

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

# Controls in Advice

### Controls in Around Advice

1. **Access Method Information**: We can access both **static** (e.g., method name, annotations) and **dynamic** (e.g., method arguments) information about the intercepted method.

   ```java
   @Around("execution(* com.altafjava.advice.Calculator.*(..))")
   public Object accessMethodInfo(ProceedingJoinPoint joinPoint) throws Throwable {
   	// Static Information
   	System.out.println("Method Name: " + joinPoint.getSignature().getName());
   	System.out.println("Declaring Class: " + joinPoint.getSignature().getDeclaringTypeName());
   	// Dynamic Information
   	Object[] args = joinPoint.getArgs();
   	System.out.println("Method Arguments: " + java.util.Arrays.toString(args));
   	return joinPoint.proceed();
   }
   ```

2. **Execute Cross-Cutting Logic**: Perform additional logic, such as logging, security checks, or transaction management, before or after calling the target method.

   ```java
   @Around("execution(* com.altafjava.advice.Calculator.*(..))")
   public Object executeCrossCuttingLogic(ProceedingJoinPoint joinPoint) throws Throwable {
   	System.out.println("Before executing method: " + joinPoint.getSignature().getName());
   	Object result = joinPoint.proceed();
   	System.out.println("After executing method: " + joinPoint.getSignature().getName());
   	return result;
   }
   ```

3. **Modify Method Arguments**: Before invoking the target method, we can modify the argument values. This allows the target method to be called with altered input.

   ```java
   @Around("execution(* com.altafjava.advice.Calculator.*(..))")
   public Object modifyArguments(ProceedingJoinPoint joinPoint) throws Throwable {
   	Object[] args = joinPoint.getArgs();
   	System.out.println("Original Argument: " + Arrays.toString(args));
   	if (args != null && args.length > 0 && args[0] instanceof Integer) {
   		args[0] = (Integer) args[0] + 1; // Add 1 to the first argument
   	}
   	System.out.println("Modified Argument: " + Arrays.toString(args));
   	return joinPoint.proceed(args);
   }
   ```

4. **Modify Return Values**: After invoking the target method, we can alter its return value before passing it back to the caller. This is possible because the advice has complete control over the method's execution flow.

   ```java
   @Around("execution(* com.altafjava.advice.Calculator.*(..))")
   public Object modifyReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {
   	Object result = joinPoint.proceed();
   	System.out.println("Original Result Value: " + result);
   	if (result instanceof Integer) {
   		result = (Integer) result + 100; // Add 100 to the result
   	}
   	System.out.println("Modified Result Value: " + result);
   	return result;
   }
   ```

5. **Control Target Method Execution**:We have the complete control over the target method execution. We can choose to:

   - **Call the target method**: Use the `proceed()` method to execute the original method.
   - **Skip the target method**: Bypass the method execution entirely by not calling `proceed()`, instead returning a custom value or throwing an exception.
   - **Throw an exception**: Simulate an error scenario by throwing a custom exception without invoking the target method.

   ```java
   @Around("execution(* com.altafjava.advice.Calculator.*(..))")
   public Object controlExecution(ProceedingJoinPoint joinPoint) throws Throwable {
      String methodName = joinPoint.getSignature().getName();
      if ("add".equals(methodName)) {
         System.out.println("Skipping method: " + methodName);
         return 0; // Custom return value
      }
      if ("divide".equals(methodName)) {
         throw new RuntimeException("Simulated exception in Around Advice");
      }
      return joinPoint.proceed();
   }
   ```

6. **Handle Exceptions**: We can Catch and handle exceptions thrown by the target method. We can choose to log the exception, suppress it, or rethrow a different exception.

   ```java
   @Around("execution(* com.altafjava.advice.Calculator.*(..))")
   public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
      try {
         return joinPoint.proceed();
      } catch (ArithmeticException ex) {
         System.err.println("Caught exception: " + ex.getMessage());
         // Suppress the exception and return a default value
         return -1;
         // Or rethrow a custom exception
         // throw new RuntimeException("Handled exception in Around Advice", ex);
      }
   }
   ```

**ProceedingJoinPoint:**  
A special type of join point automatically passed by the AOP framework to the `@Around` advice method. Provides critical context about the intercepted method, including: Method signature, Method arguments, Ability to proceed with or halt the method execution using the proceed() method.

**Adding Extra Parameters to Advice Methods:**

- Spring AOP only injects the `ProceedingJoinPoint` object by default.
- If we add extra parameters like `String arg1` to the advice method, Spring cannot automatically inject them unless:

  - They are explicitly defined in the pointcut expression (e.g., using `args` to match method arguments).
  - They are annotations on the intercepted method (e.g., `@annotation` for custom annotations).
  - Extra parameters are not part of the `ProceedingJoinPoint` context and require explicit mapping or configuration in the pointcut.

- In addition to ProceedingJoinPoint, the advice method can include parameters to bind `method arguments`, `target objects`, or `annotations`. These parameters are determined by the pointcut expression and bindings in the `@Around` annotation.

1. Binding Method Arguments:
   ```java
   @Around("execution(* com.altafjava.service.*.*(..)) && args(arg1)")
   public Object aroundWithArgs(ProceedingJoinPoint joinPoint, String arg1) throws Throwable {
       System.out.println("Before");
       System.out.println("Arguments: " + arg1);
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
