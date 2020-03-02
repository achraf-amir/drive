package com.drive.order.rest;


import com.drive.common.beans.order.Order;
import com.drive.common.beans.order.OrderRequest;
import com.drive.order.exception.OrderValidationException;
import com.drive.order.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	private final Logger logger;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
		logger = LoggerFactory.getLogger(OrderController.class);
	}

	@RequestMapping(value = "/createOrder", method = RequestMethod.POST,produces = APPLICATION_JSON_VALUE)
	public ResponseEntity createOrder(
					@RequestBody final OrderRequest orderRequest
					){
		try {
			orderService.createPromiseOrder(orderRequest);
		} catch(OrderValidationException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity findById(@PathVariable String id)  {
		logger.info("orderId {}: find order by id", id);
		try {
			Order order = orderService.findById(id);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}



}
