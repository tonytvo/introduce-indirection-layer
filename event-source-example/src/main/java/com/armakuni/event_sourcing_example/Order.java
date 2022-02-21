package com.armakuni.event_sourcing_example;

import com.armakuni.event_sourcing_example.errors.*;
import com.armakuni.event_sourcing_example.events.ItemAdded;
import com.armakuni.event_sourcing_example.events.OrderCreated;
import com.armakuni.event_sourcing_example.events.OrderPlaced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Order {
    private ArrayList<OrderEvent> newEvents = new ArrayList<>();

    private OrderID id;
    private boolean placed = false;
    private OrderLines orderLines = new OrderLines();

    public static Order create(OrderID orderID) {
        var orderCreated = new OrderCreated(orderID);
        var events = Collections.singletonList(orderCreated);
        var order = Order.fromEventStream(new ArrayList<>(events));
        order.newEvents.add(orderCreated);
        return order;
    }

    public static Order fromEventStream(ArrayList<OrderEvent> events) {
        return new Order(events);
    }

    private Order(ArrayList<OrderEvent> events) {
        if (events.size() == 0) {
            throw new EmptyEventStream();
        }

        if (!(events.get(0) instanceof OrderCreated)) {
            throw new InvalidEvent(
                    "The first event must be OrderCreated"
            );
        }

        for (OrderEvent event : events) {
            applyEvent(event);
        }
    }

    public List<OrderEvent> retrieveNewEvents() {
        return newEvents;
    }

    public void addItem(ItemCode item) {
        OrderEvent event = new ItemAdded(item);
        applyEvent(event);
        newEvents.add(event);
    }

    public void place() {
        OrderEvent event = new OrderPlaced();
        applyEvent(event);
        newEvents.add(event);
    }

    public void print(OrderPrinter printer) {
        if (!placed) {
            throw new OrderHasNotBeenPlaced(id);
        }

        printer.print(orderLines.asList());
    }

    private void applyEvent(OrderEvent event) {
        if (event instanceof OrderCreated) {
            applyEvent((OrderCreated) event);
        } else if (event instanceof ItemAdded) {
            applyEvent((ItemAdded) event);
        } else if (event instanceof OrderPlaced) {
            applyEvent((OrderPlaced) event);
        }
    }

    private void applyEvent(OrderCreated event) {
        id = event.id;
    }

    private void applyEvent(ItemAdded event) {
        if (placed) {
            throw new OrderHasAlreadyBeenPlaced(id);
        }

        orderLines.incrementItemQuantity(event);
    }

    private void applyEvent(OrderPlaced event) {
        if (placed) {
            throw new OrderHasAlreadyBeenPlaced(id);
        }

        if (orderLines.isEmpty()) {
            throw new OrderHasNoItems(id);
        }

        placed = true;
    }

}