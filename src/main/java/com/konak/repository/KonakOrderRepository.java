package com.konak.repository;

import com.konak.model.Contact;
import com.konak.model.KonakOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface KonakOrderRepository extends CrudRepository<KonakOrder, Long> {

}