package com.drive.mock.services.impl;

import com.drive.common.beans.order.Order;
import com.drive.common.beans.order.PrepareUpdateOrder;
import com.drive.mock.services.PrepareOrderService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;

@Service
public class PrepareOrderServiceImpl implements PrepareOrderService {


	@Override
	public PrepareUpdateOrder prepareOrder(Order order) {
		try {
		String fileName = "xml/PREAPARATION_"+ order.getOrderNumber()+ "_order.xml";
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();

			FileInputStream file = new FileInputStream(new File(classLoader.getResource(fileName).getFile()));

			JAXBContext jaxbContext = JAXBContext.newInstance(PrepareUpdateOrder.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			PrepareUpdateOrder prepareUpdateOrder = (PrepareUpdateOrder)jaxbUnmarshaller.unmarshal( file );
			prepareUpdateOrder.setOrderId(order.getId());
			return prepareUpdateOrder;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
