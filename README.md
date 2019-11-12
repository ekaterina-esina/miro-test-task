# miro-test-task
Rest API. Create, view and delete widgets from memory (ConcurrentHashMap).
Widget is object with parameters: 
- id (generated on server)
- coordinateX
- coordinateY
- coordinateZ (This is the order of widgets. The widget with the highest index is in the foreground)
- width
- height
- lastChangesDate

If we do not specify a Z-index when creating the widget, then the widget moves to the foreground.
If we specify an existing Z-index, then the new widget shifts all widgets with the same and large index upwards.

# I use:
1. Spring Boot
2. Maven
3. Lombok

# How to start:
Download and start. All properties are ready.

# Methods:
```
POST: /widget {json: EditWidgetRequest}

PUT: /widget {json: EditWidgetRequest}

DELETE: /widget/{id}

GET: /widget/{id}

GET: /widget

```
