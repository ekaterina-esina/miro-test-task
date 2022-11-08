# Task
A web service (HTTP REST API) to work with widgets.

A Widget is an object on a plane in a Cartesian coordinate system that has coordinates (X, Y), Z-index, width, height, last modification date, and a unique identifier. X, Y, and Z-index are integers (may be negative).

A Z-index is a unique sequence common to all widgets that determines the order of widgets (regardless of their coordinates). Gaps are allowed. The higher the value, the higher the widget lies on the plane.
Details
Operations to be provided by the web service:
- Creating a widget. Having set coordinates, z-index, width, and height, we get a complete widget description in the response. 
  - The server generates the identifier.
  - If a z-index is not specified, the widget moves to the foreground. If the existing z-index is specified, then the new widget shifts all widgets with the same and greater index upwards.
- Getting a widget by its identifier. In the response we get a complete description of the widget.
- Change widget data by its identifier. In the response we get an updated full description of the widget.
  - You cannot change widget id.
  - You cannot delete widget attributes.
  - All changes to widgets must occur atomically. That is, if we change the XY coordinates of the widget, then we should not get an intermediate state during concurrent reading.
- Deleting a widget. We can delete the widget by its identifier.
- Getting a list of widgets. In the response we get a list of all widgets sorted by z-index, from smallest to largest.

# Solution
Saving widgets is implemented in memory using HashSet - because in conditions of frequent addition, deletion or modification of widgets, the complexity of O(1) remains.

To solve in order to make widget changes atomic, ReentrantLock is used. When requesting a widget change, if changes are already being made by another thread, then the resource is blocked and waiting for availability. As soon as the first thread has finished the changes, the lock is released.

All errors in the program are logged and also written to the file: log/error.log. %d{yyyy-MM-dd}.%i.gz. The maximum file size is 15MB


# Technology
1. Java 11
2. Spring-boot-starter 2.1.9
3. Maven 3

# How to start
Run in directory
```
mvn clean install
mvn spring-boot:run
```

# API
http://localhost:9090/api
```
POST: /widgets {json: CreateWidgetRequest}

PUT: /widgets {json: ModifyWidgetRequest}

DELETE: /widgets/{id}

GET: /widgets/{id}

GET: /widgets

```
