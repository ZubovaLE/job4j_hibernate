package ru.job4j.integration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrdersStoreTest {

    @Test
    void whenSave() {
        OrdersStore store = new OrdersStore();
        Order order = Order.of("order one", "description one");
        store.save(order);
        List<Order> orders = store.findAll();
        assertEquals(order, orders.get(0));
    }

    @Test
    void whenFindAllThenShouldReturnTwoOrders() {
        OrdersStore store = new OrdersStore();
        Order orderOne = Order.of("order one", "description one");
        Order orderTwo = Order.of("order two", "description two");
        store.save(orderOne);
        store.save(orderTwo);
        assertEquals(List.of(orderOne, orderTwo), store.findAll());
    }

    @Test
    void whenFindByIdThenShouldReturnCorrectOrder() {
        OrdersStore store = new OrdersStore();
        Order orderOne = Order.of("order one", "description one");
        Order orderTwo = Order.of("order two", "description two");
        store.save(orderOne);
        store.save(orderTwo);
        assertEquals(orderTwo, store.findById(orderTwo.getId()));
    }

    @Test
    void whenFindByNameThenShouldReturnCorrectOrder() {
        OrdersStore store = new OrdersStore();
        String name = "order";
        Order orderOne = Order.of("order one", "description one");
        Order orderTwo = Order.of(name, "description two");
        store.save(orderOne);
        store.save(orderTwo);
        assertEquals(orderTwo, store.findByName(name));
    }

    @Test
    void whenUpdate() {
        OrdersStore store = new OrdersStore();
        Order orderOne = Order.of("order one", "description one");
        Order newOrder = Order.of("new", "description two");
        store.save(orderOne);
        store.update(orderOne.getId(), newOrder);
        assertEquals(newOrder, store.findById(orderOne.getId()));
    }
}