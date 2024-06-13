package kr.sparta.deliveryapi.service;

import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;

import java.util.List;

public interface OrderManagement<T> {
    public abstract List<T> getAllOrders();
    public abstract Delivery deliverOrder(Long Id);
}
