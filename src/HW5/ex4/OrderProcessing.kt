package HW5.ex4

// Data model
data class OrderItem(
    val name: String,
    val price: Double,
    val quantity: Int
)

class Order(val id: String) {
    var customerName: String = ""
    var deliveryAddress: String = ""
    var discountPercent: Int = 0
    val items: MutableList<OrderItem> = mutableListOf()

    fun addItem(item: OrderItem) {
        items.add(item)
    }
}

fun main() {
    // Task 1. Order configuration and auditing (apply + also)
    val order = Order("ORD-2026-001").apply {
        customerName = "Oleksii Kovalenko"
        deliveryAddress = "22 Khreshchatyk St, Kyiv"
        discountPercent = 10

        addItem(OrderItem("Keyboard", 2500.0, 1))
        addItem(OrderItem("Mouse", 1200.0, 1))
        addItem(OrderItem("Mousepad", 400.0, 2)) // 2 pcs at 400.0 = 800.0 UAH
    }.also {
        println("[LOG] Order ${it.id} successfully configured. Total items: ${it.items.size}")
    }

    // Task 2. Final price calculation (run)
    val finalPrice: Double = order.run {
        val rawTotal = items.sumOf { it.price * it.quantity }
        val discountMultiplier = 1.0 - (discountPercent / 100.0)
        rawTotal * discountMultiplier
    }
    println("Calculated total: $finalPrice UAH")

    // Task 3. Scope localization and transformation (let)
    val paymentPayload: String = finalPrice.let {
        val amountInCents = (it * 100).toLong()
        "TXN-${order.id}_AMOUNT_${amountInCents}_UAH"
    }
    println("Transaction payload: $paymentPayload")

    // Task 4. Formatting and printing the receipt (with)
    with(order) {
        println("========================================")
        println("ORDER RECEIPT: $id")
        println("Customer: $customerName")
        println("Delivery address: $deliveryAddress")
        println("----------------------------------------")
        println("Items:")
        items.forEach {
            println(" • ${it.name} (${it.quantity} pcs) - ${it.price * it.quantity} UAH")
        }
        println("----------------------------------------")
        println("Customer discount: $discountPercent%")
        println("Total to pay: $finalPrice UAH")
        println("========================================")
    }
}