package com.konak.service;

import com.konak.model.KonakOrder;
import com.konak.repository.KonakOrderRepository;
import com.konak.util.email.EmailHtmlSender;
import com.konak.util.email.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jmx.export.naming.KeyNamingStrategy;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class KonakOrderService {

    @Autowired
    private KonakOrderRepository repo;

    @Autowired
    private EmailHtmlSender emailHtmlSender;
    static final String KONAKUSER = "KonakUser";
    static final String ORDER_EMAIL_TEMPLATE_LOCATION = "email/orderEmailTemplate";
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
            newOrder = repo.save(newOrder);
        }
        //if (true)
        //throw new RuntimeException("Error sending email");

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

        Context context = new Context();
        String emailSubject = "Order Confirmation - Ref ID: " + order.getId();
        context.setVariable("id", order.getId());
        context.setVariable("firstName", order.getFirstName());
        context.setVariable("middleName", order.getMiddleName());
        context.setVariable("lastName", order.getLastName());
        context.setVariable("email", order.getEmail());
        context.setVariable("contactAreaCode", order.getContactAreaCode());
        context.setVariable("contactNumber", order.getContactNumber());
        context.setVariable("createdTAD", order.getCreatedTAD());
        context.setVariable("productName", order.getProductName());
        context.setVariable("orderComment", order.getOrderComment());


        EmailStatus emailStatus = emailHtmlSender.send(order.getEmail(), emailSubject, ORDER_EMAIL_TEMPLATE_LOCATION, context);
        emailSent = !emailStatus.isError();

        return emailSent;

    }
}
