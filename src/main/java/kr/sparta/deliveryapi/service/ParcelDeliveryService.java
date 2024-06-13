package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.model.Parcel;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ParcelDeliveryService extends OrderBaseService {
    private final ParcelRepository parcelRepository;

    public ParcelDeliveryService(DeliveryRepository deliveryRepository, ParcelRepository parcelRepository) {
        super(deliveryRepository);
        this.parcelRepository = parcelRepository;
    }

    @Override
    public Delivery deliverOrder(Long parcelId) {
        final Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(IllegalArgumentException::new);

        final String trackingNo = generateTrackingNo(parcel.getDescription());
        final Delivery delivery = Delivery.builder()
                .trackingNumber(trackingNo)
                .itemType(ItemType.PARCEL)
                .status(DeliveryStatus.SHIPPED)
                .itemId(parcel.getId())
                .name(parcel.getDescription())
                .build();

        super.deliveryRepository.save(delivery);

        return delivery;
    }

    @Override
    public List<Parcel> getAllOrders() {
        return parcelRepository.findAll();
    }
}
