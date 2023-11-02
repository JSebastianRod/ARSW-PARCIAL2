/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;


/**
 *
 * @author hcadavid
 */
@RestController

public class OrdersAPIController {

    @Autowired
    @Qualifier("RestaurantOrderServicesStub")
    RestaurantOrderServicesStub restaurantOder;



    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ResponseEntity<?> handlerGetOrders() {
        try {
            Gson gson = new Gson();
            List<Order> orders = getOrders();
            String ordersString = orders.toString();
            Set<Integer> tableNumbers = restaurantOder.getTablesWithOrders();
            return new ResponseEntity<>(tableNumbers, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public List<Order> getOrders() {
    List<Order> orders = new ArrayList<>();
    Set<Integer> tableNumbers = new HashSet<>(Arrays.asList(1, 3));
    for (Integer tableNumber : tableNumbers) {
        Order order = restaurantOder.getTableOrder(tableNumber);
        orders.add(order);
    }
    return orders;
}
}
