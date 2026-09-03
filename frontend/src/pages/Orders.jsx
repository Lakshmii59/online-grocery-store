import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllOrders } from "../services/orderService";
import "../scss/Order.scss";

function Orders() {
  const navigate = useNavigate();

  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const loadOrders = async () => {
      try {
        const response = await getAllOrders();

        const customer = JSON.parse(localStorage.getItem("customer"));

        const customerOrders = response.data.filter(
          (order) => String(order.customerId) === String(customer.customerId),
        );

        setOrders(customerOrders);
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
    };

    loadOrders();
  }, []);

  return (
    <div className="orders-page">
      <div className="orders-header">
        <div>
          <h1>My Orders</h1>
          <p>View and track your FreshNest orders</p>
        </div>

        <button type="button" onClick={() => navigate("/")}>
          Continue Shopping
        </button>
      </div>

      {orders.length === 0 ? (
        <div className="empty-orders">
          <h2>No orders yet</h2>

          <p>Your placed orders will appear here.</p>

          <button type="button" onClick={() => navigate("/products")}>
            Start Shopping
          </button>
        </div>
      ) : (
        <div className="orders-list">
          {orders.map((order) => (
            <div className="order-card" key={order.orderId}>
              <div className="order-card-header">
                <div>
                  <h2>Order #{order.orderId}</h2>

                  <p>{new Date(order.orderDate).toLocaleDateString()}</p>
                </div>

                <span className="order-status">{order.orderStatus}</span>
              </div>

              <div className="order-items">
                {order.orderItems.map((item) => (
                  <div className="order-item" key={item.productId}>
                    <div>
                      <strong>{item.productName}</strong>

                      <span>Quantity: {item.quantity}</span>
                    </div>

                    <strong>₹{item.totalPrice}</strong>
                  </div>
                ))}
              </div>

              <div className="order-total">
                <span>Total Amount</span>

                <strong>₹{order.totalAmount}</strong>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Orders;
