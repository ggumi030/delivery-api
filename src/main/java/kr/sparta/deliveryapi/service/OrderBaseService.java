package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.repository.DeliveryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class OrderBaseService implements OrderManagement{
    protected final DeliveryRepository deliveryRepository;

    public OrderBaseService(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    public String generateTrackingNo(String description) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                + String.valueOf(description.hashCode()).substring(0, 4);
    }

    public DeliveryStatus trackOrder(String trackingNumber) {
        return deliveryRepository.findById(trackingNumber)
                .map(Delivery::getStatus)
                .orElseThrow(IllegalReceiveException::new);
    }
}
