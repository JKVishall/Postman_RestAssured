package EcommerceDemoRequestResponseClasses;

import java.util.List;

public class CreateOrderRequest {
    private List<CreateOrderDetailsPartTwo> orders;

    public List<CreateOrderDetailsPartTwo> getOrders() {
        return orders;
    }

    public void setOrders(List<CreateOrderDetailsPartTwo> orders) {
        this.orders = orders;
    }
}
