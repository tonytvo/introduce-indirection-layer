package com.armakuni.event_sourcing_example;

import com.armakuni.event_sourcing_example.events.ItemAdded;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLines {
    private final HashMap<ItemCode, Quantity> lines;

    public OrderLines(HashMap<ItemCode, Quantity> lines) {
        this.lines = lines;
    }

    public HashMap<ItemCode, Quantity> getLines() {
        return lines;
    }

    void incrementQuatity(ItemAdded event) {
        var quantity = getLines().containsKey(event.itemCode)
                ? getLines().get(event.itemCode).increment()
                : Quantity.of(1);

        getLines().put(event.itemCode, quantity);
    }

    boolean isEmpty() {
        return getLines().isEmpty();
    }

    List<OrderLine> asList() {
        return getLines().entrySet().stream()
                .map((entry) -> new OrderLine(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
