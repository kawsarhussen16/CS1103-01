
import com.ecommerce.CartItem;
import com.ecommerce.Customer;
import com.ecommerce.InvalidOrderException;
import com.ecommerce.Product;
import com.ecommerce.orders.*;
import java.util.ArrayList;
import java.util.List;

// Main class is the entry point of the Java application
// It demonstrates the full workflow of the e-commerce system
public class Main {

    public static void main(String[] args) {

        // Creating product objects (available items in the store)
        Product laptop = new Product(1, "Laptop", 1200, 5);
        Product phone = new Product(2, "Phone", 600, 10);

        // Display product details
        laptop.displayProduct();
        phone.displayProduct();

        // Creating a customer object
        Customer customer = new Customer(101, "Kawsar");

        // Customer adds products to cart with quantity
        customer.addToCart(laptop, 1);
        customer.addToCart(phone, 2);

        // Display cart items and total price
        customer.viewCart();

        try {
            // Validate if order can be placed (cart can not be empty)
            customer.validateOrder();

            // Convert cart items into a separate list for order processing
            List<CartItem> items = new ArrayList<>(customer.getCart());

            // Create an order using customer cart data
            Order order = new Order(5001, customer, items);

            System.out.println(order.generateSummary());

            // Update order status to SHIPPED
            order.updateStatus(OrderStatus.SHIPPED);

            System.out.println("\nAfter Shipping:");
            System.out.println(order.generateSummary());
        } catch (InvalidOrderException e) {
            // Handle error if order cannot be placed
            System.out.println("Error: " + e.getMessage());
        }
    }
}
