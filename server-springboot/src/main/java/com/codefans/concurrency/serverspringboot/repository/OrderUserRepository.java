package com.codefans.concurrency.serverspringboot.repository;

import com.codefans.concurrency.serverspringboot.entity.OrderUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @author: mpif
 * @date: 2019-08-28 17:33
 */
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OrderUserRepository extends CrudRepository<OrderUser, Integer> {

}
