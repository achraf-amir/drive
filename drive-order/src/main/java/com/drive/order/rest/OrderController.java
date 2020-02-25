package com.drive.order.rest;


import com.drive.common.beans.OrderRequest;
import com.drive.order.exception.OrderValidationException;
import com.drive.order.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
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



}
