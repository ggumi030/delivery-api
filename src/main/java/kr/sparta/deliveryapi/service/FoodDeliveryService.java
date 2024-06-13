package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.Food;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FoodDeliveryService extends OrderBaseService {
    private final FoodRepository foodRepository;

    public FoodDeliveryService(FoodRepository foodRepository, DeliveryRepository deliveryRepository) {
        super(deliveryRepository);
        this.foodRepository = foodRepository;
    }

    @Override
    public Delivery deliverOrder(Long foodId) {
        final Food food = foodRepository.findById(foodId)
                .orElseThrow(IllegalArgumentException::new);

        final String trackingNo = generateTrackingNo(food.getName());
        final Delivery delivery = Delivery.builder()
                .trackingNumber(trackingNo)
                .itemType(ItemType.FOOD)
                .status(DeliveryStatus.SHIPPED)
                .itemId(food.getId())
                .name(food.getName())
                .build();

        super.deliveryRepository.save(delivery);

        return delivery;
    }

    @Override
    public List<Food> getAllOrders() {
        return foodRepository.findAll();
    }
}
