package com.example.multi_pane_shopping_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.multi_pane_shopping_app.ui.theme.MultipaneshoppingappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultipaneshoppingappTheme {
                ShoppingApp()
            }
        }
    }

    data class Product(
        val name: String,
        val price: String,
        val description: String
    )

    @Composable
    fun ShoppingApp() {
        val products = listOf(
            Product("Wireless Bluetooth Headphones", "$99", "High-quality wireless Bluetooth headphones with excellent sound and long battery life."),
            Product("Smartwatch", "$199", "Multi-functional smartwatch with health monitoring, message notifications, and GPS navigation."),
            Product("4K HDR TV", "$799", "55-inch 4K HDR smart TV with built-in streaming apps and voice control."),
            Product("Portable Power Bank", "$29", "10000mAh high-capacity power bank, compatible with multiple devices, fast charging design."),
            Product("Wireless Mouse", "$25", "Ergonomic wireless mouse with smooth clicking and long battery life."),
            Product("Mechanical Keyboard", "$85", "RGB backlit mechanical keyboard with high responsiveness switches, suitable for gaming and office use."),
            Product("Bluetooth Speaker", "$45", "Portable Bluetooth speaker with clear sound quality and waterproof design, perfect for outdoor use."),
            Product("Smartphone", "$699", "Latest model smartphone with high-resolution camera and powerful processor."),
            Product("Laptop", "$999", "Lightweight and portable laptop, suitable for daily office work and multimedia entertainment."),
            Product("Running Shoes", "$120", "Comfortable and breathable running shoes, ideal for jogging, fitness, and everyday wear."),
            Product("Coffee Maker", "$150", "Fully automatic coffee maker supporting multiple coffee drinks, easy to clean."),
            Product("Electric Toothbrush", "$60", "Smart electric toothbrush with multiple cleaning modes and long-lasting battery."),
            Product("Desk Lamp", "$35", "LED desk lamp with adjustable brightness and USB charging port, energy-efficient."),
            Product("Yoga Mat", "$40", "Non-slip yoga mat, thick and comfortable, suitable for all types of yoga and fitness exercises."),
            Product("Outdoor Backpack", "$80", "Multi-functional outdoor backpack with waterproof design and multiple storage compartments, perfect for hiking and travel.")
        )

        val configuration = LocalConfiguration.current
        val isLand = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        var Selected by remember { mutableStateOf<Product?>(null) }

        var isList by remember { mutableStateOf(true) }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            if (isLand) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    ProductList(
                        products = products,
                        onProductClick = { product ->
                            Selected = product
                            isList = false
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                    ProductDetails(
                        product = Selected,
                        modifier = Modifier
                            .weight(2f)
                            .padding(16.dp)
                    )
                }
            } else {
                if (isList) {
                    ProductList(
                        products = products,
                        onProductClick = { product ->
                            Selected = product
                            isList = !isList
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
                else{
                    ProductDetails(
                        product = Selected,
                        onBack = { isList = !isList },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun ProductList(
        products: List<Product>,
        onProductClick: (Product) -> Unit,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(modifier = modifier) {
            items(products) { product ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductClick(product) }
                        .padding(16.dp)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

    @Composable
    fun ProductDetails(
        product: Product?,
        onBack: (() -> Unit)? = null,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            if (onBack != null) {
                Button(
                    onClick = { onBack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(text = "Back")
                }
            }
            if (product != null) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Price: ${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 15.dp)
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 30.dp)
                )
            } else {
                Text(
                    text = "Please select a product.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
