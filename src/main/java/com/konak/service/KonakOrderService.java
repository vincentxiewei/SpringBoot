package com.konak.service;

import com.konak.model.KonakOrder;
import com.konak.repository.KonakOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jmx.export.naming.KeyNamingStrategy;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class KonakOrderService {

    @Autowired
    private KonakOrderRepository repo;
    static final String KONAKUSER = "KonakUser";
    /*
        This function saves the order and calls sendOrderEmail to send an email to Konak
     */
    public KonakOrder addOrder (KonakOrder order){

        System.out.println("Inside Add order");
        System.out.println(order.getLastName());
        Date currentDate = new Date();
        String currentDateString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(currentDate);

        order.setCreatedBy(KONAKUSER);
        order.setUpdatedBy(KONAKUSER);
        order.setCreatedTAD(currentDateString);
        order.setUpdatedTAD(currentDateString);

        KonakOrder newOrder = repo.save(order);

        if (sendOrderEmail(newOrder) == true) {
            newOrder.setEmailSent(true);
            newOrder.setEmailSentTAD(currentDateString);
        }

        return newOrder;
    }

    /*
    This function saves the order and calls sendOrderEmail to send an email to Konak
 */
    public KonakOrder getOrder (Long id){
        System.out.println("Inside get order "+ id);
        KonakOrder newOrder = repo.findOne(id);
        return newOrder;
    }
    /*
        This function sends the order email and return success boolean
     */
    private boolean sendOrderEmail (KonakOrder order){
        boolean emailSent = false;

        emailSent = true;
        return emailSent;

    }
}
