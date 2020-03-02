package com.drive.order.services.impl;


import com.drive.common.beans.order.Order;
import com.drive.common.beans.statut.Status;
import com.drive.common.beans.keys.OrderCategoryType;
import com.drive.common.entities.OrderEntity;
import com.drive.order.repossitory.OrderRepository;
import com.drive.order.services.OrdersCrudService;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static java.util.UUID.randomUUID;

@Service
public class OrdersCrudServiceImpl implements OrdersCrudService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersCrudService.class);
	private final DozerBeanMapper mapper;
	private final OrderRepository orderRepository;

	public OrdersCrudServiceImpl(DozerBeanMapper mapper,
				OrderRepository orderRepository
	) {
		this.mapper = mapper;
		this.orderRepository = orderRepository;
	}


	@Override
	public Order create(Order order) {
		LOGGER.debug("create order: request -> {}", order);
		final OrderEntity entity = toEntity(order);

		if (null == entity.getCreationDateTime()) {
			entity.setCreationDateTime(LocalDateTime.now());
		}
		entity.setLastUpdateDateTime(LocalDateTime.now());
		final String id = randomUUID().toString();
		entity.setId(id);
		final OrderEntity savedEntity = orderRepository.save(entity);

		return fromEntity(savedEntity);
	}

	@Override
	public  Order findById(String id) throws Exception {
		final Optional<OrderEntity> maybeEntity = orderRepository.findById(id);
		return maybeEntity.map(this::fromEntity).orElseThrow(() -> new Exception(id));

	}

	@Override
	public  Order update(String id, OrderCategoryType categoryType) {
		final Optional<OrderEntity> maybeEntity = orderRepository.findById(id);
		if(maybeEntity.isPresent()){

			maybeEntity.get().setOrderCategoryType(categoryType);
			return fromEntity(orderRepository.save(maybeEntity.get()));
		}
	return null;
	}

	@Override
	public void update(Order order) {
		 orderRepository.save(toEntity(order));
	}

	@Override
	public  Order updateStatut(String id, Status status) {
		final Optional<OrderEntity> maybeEntity = orderRepository.findById(id);
		if(maybeEntity.isPresent()){
			if (CollectionUtils.isEmpty(maybeEntity.get().getAdditionalStatus())){
				maybeEntity.get().setAdditionalStatus(new ArrayList<>());
			}
			maybeEntity.get().getAdditionalStatus().add(status);
			return fromEntity(orderRepository.save(maybeEntity.get()));
		}
		return null;

	}



	private OrderEntity toEntity(Order order) {

		final OrderEntity orderEntity = mapper.map(order, OrderEntity.class);
		if (null == orderEntity.getAdditionalStatus()) {
			orderEntity.setAdditionalStatus(new ArrayList<>());
		}

		return orderEntity;
	}

	private Order fromEntity(OrderEntity entity) {

		final Order order = mapper.map(entity, Order.class);

		return order;
	}
}
