import com.ecommerce.CartItem;
import com.ecommerce.Customer;
import com.ecommerce.InvalidOrderException;
import com.ecommerce.Product;
import com.ecommerce.orders.*;
import java.util.ArrayList;
import java.util.List;

// Entry point of the application
// Demonstrates full e-commerce workflow: product browsing → cart → order → status update
public class Main {

    public static void main(String[] args) {

        System.out.println("===== WELCOME TO E-COMMERCE SYSTEM =====\n");

        // Creating products
        Product laptop = new Product(1, "Laptop", 1200, 5);
        Product phone = new Product(2, "Phone", 600, 10);

        System.out.println("=== AVAILABLE PRODUCTS ===");
        laptop.displayProduct();
        phone.displayProduct();

        // Creating customer
        Customer customer = new Customer(101, "Kawsar");

        System.out.println("\n=== ADDING ITEMS TO CART ===");
        customer.addToCart(laptop, 1);
        customer.addToCart(phone, 2);

        System.out.println("\n=== CART DETAILS ===");
        customer.viewCart();

        try {
            // Validate cart before order placement
            customer.validateOrder();

            // Prepare order items
            List<CartItem> items = new ArrayList<>(customer.getCart());

            System.out.println("\n=== PLACING ORDER ===");

            // Create order
            Order order = new Order(5001, customer, items);

            // Show initial order summary
            System.out.println(order.generateSummary());

            // Update order status
            order.updateStatus(OrderStatus.SHIPPED);

            System.out.println("\n=== AFTER SHIPPING UPDATE ===");
            System.out.println(order.generateSummary());

        } catch (InvalidOrderException e) {

            // Handle invalid order scenarios
            System.out.println("\nORDER FAILED: " + e.getMessage());
        }

        System.out.println("\n===== THANK YOU FOR USING OUR SYSTEM =====");
    }
}