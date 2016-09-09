package com.konak.controller;

import com.konak.model.KonakOrder;
import com.konak.repository.KonakOrderRepository;
import com.konak.service.KonakOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/order")
public class OrderRestController {

    @Autowired
    private KonakOrderRepository repo;
    @Autowired
    private KonakOrderService konakOrderService;

    @RequestMapping(method=RequestMethod.GET)
    public List<KonakOrder> getAll() {
        System.out.println("got in GET");
        List<KonakOrder> target = new ArrayList<KonakOrder>();
        repo.findAll().forEach(target::add);
        return target;
    }

    @RequestMapping(method=RequestMethod.GET, value="{id}")
    public KonakOrder getOrder(@PathVariable String id) {
        System.out.println("got in GET ID");
        return konakOrderService.getOrder(Long.valueOf(id));
    }
    /*
        This method create an Order in database and send out email to Konak
     */
    @RequestMapping(method=RequestMethod.POST)
    public KonakOrder create(@RequestBody KonakOrder order) {
        System.out.println("got in POST");
        KonakOrder newOrder = konakOrderService.addOrder(order);
        return newOrder;
    }

    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    public void delete(@PathVariable String id) {
        System.out.println("got in DELETE");
        repo.delete(Long.getLong(id));
    }

    @RequestMapping(method=RequestMethod.PUT, value="{id}")
    public KonakOrder update(@PathVariable String id, @RequestBody KonakOrder order) {
        System.out.println("got in PUT");
        KonakOrder update = repo.findOne(Long.getLong(id));
        update.setFirstName(order.getFirstName());
        update.setLastName(order.getLastName());
        update.setEmail(order.getEmail());
        update.setOrderComment(order.getOrderComment());
        return repo.save(update);
    }

}
/*
{
    "firstName": "Vincent",
    "middleName": "W",
    "lastName": "Xie",
    "contactAreaCode": "02",
    "contactNumber": "12345678",
    "email": "vincentxieweitest@gmail.com",
    "productName": "NICE Billing Suite v2.5",
    "orderComment": "This is the order comment testing"
}
 */