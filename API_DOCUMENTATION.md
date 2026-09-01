# API Documentation

## Product Catalog Service

### Create Category

POST

/api/categories

Request 

{
"categoryName": "Vegetables",
"description": "Fresh veggies"
}

### Add Product

POST

/api/products

Request

{
"productName": "Apple",
"description": "Fresh apple",
"price": 120,
"sku": "APL001",
"active": true,
"availableQuantity": 100,
"categoryId": 1
}
 
---

### Get All Products

GET

/api/products
 
---

### Get Product By ID

GET

/api/products/{id}
 
---

### Update Product

PUT

/api/products/{id}
 
---

### Delete Product

DELETE

/api/products/{id}
 
------------------------------------------------

## Inventory Service

### Add Inventory

POST

/api/inventory
 
---

### Get Inventory

GET

/api/inventory
 
---

### Update Inventory

PUT

/api/inventory/{id}
 
---

### Delete Inventory

DELETE

/api/inventory/{id}
 
------------------------------------------------

## Order Service

### Place Order

POST

/api/orders
 
---

### Get Orders

GET

/api/orders
 
---

### Get Order By ID

GET

/api/orders/{id}
 
---

### Update Order

PUT

/api/orders/{id}
 
---

### Delete Order

DELETE

/api/orders/{id}