import { useNavigate } from "react-router-dom";
import { useCart } from "../CartContext";
import "../scss/Cart.scss";

function Cart() {
  const { cart, increaseQuantity, decreaseQuantity, removeFromCart } =
    useCart();

  const navigate = useNavigate();

  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,
    0,
  );

  const totalItems = cart.reduce((total, item) => total + item.quantity, 0);

  const handleCheckout = () => {
    const customer = localStorage.getItem("customer");

    if (customer) {
      navigate("/checkout");
    } else {
      navigate("/login", { state: { from: "/checkout" } });
    }
  };

  if (cart.length === 0) {
    return (
      <div className="cart-page">
        <div className="empty-cart">
          <div className="empty-cart-icon">🛒</div>

          <h1>Your Cart is Empty</h1>

          <p>Looks like you haven't added anything to your cart yet.</p>

          <button type="button" onClick={() => navigate("/products")}>
            Continue Shopping
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="cart-page">
      <div className="cart-header">
        <div>
          <span className="cart-label">SHOPPING CART</span>
          <h1>Your Cart</h1>
          <p>Review your items and proceed to checkout</p>
        </div>

        <button
          type="button"
          className="continue-shopping"
          onClick={() => navigate("/products")}
        >
          ← Continue Shopping
        </button>
      </div>

      <div className="cart-layout">
        <div className="cart-items-card">
          <div className="cart-items-header">
            <span>Product</span>
            <span>Price</span>
            <span>Quantity</span>
            <span>Total</span>
            <span></span>
          </div>

          <div className="cart-items">
            {cart.map((item) => (
              <div className="cart-item" key={item.productId}>
                <div className="product-details">
                  <div className="product-image">
                    <img src={item.imageUrl} alt={item.productName} />
                  </div>

                  <div className="product-info">
                    <h3>{item.productName}</h3>

                    <span className="stock-status">● In Stock</span>
                  </div>
                </div>

                <div className="item-price">₹{item.price}</div>

                <div className="quantity-controls">
                  <button
                    type="button"
                    onClick={() => decreaseQuantity(item.productId)}
                  >
                    −
                  </button>

                  <span>{item.quantity}</span>

                  <button
                    type="button"
                    onClick={() => increaseQuantity(item.productId)}
                  >
                    +
                  </button>
                </div>

                <div className="item-total">₹{item.price * item.quantity}</div>

                <button
                  type="button"
                  className="remove-button"
                  onClick={() => removeFromCart(item.productId)}
                  aria-label={`Remove ${item.productName}`}
                >
                  ×
                </button>
              </div>
            ))}
          </div>

          <div className="delivery-info">
            <div className="delivery-icon">🚚</div>

            <div>
              <strong>Estimated Delivery</strong>
              <p>Within 30 minutes</p>
            </div>
          </div>
        </div>

        <div className="order-summary">
          <div className="summary-header">
            <span className="summary-icon">▣</span>
            <h2>Order Summary</h2>
          </div>

          <div className="summary-row">
            <span>
              Subtotal ({totalItems} {totalItems === 1 ? "item" : "items"})
            </span>

            <strong>₹{totalPrice}</strong>
          </div>

          <div className="summary-row">
            <span>Delivery Charges</span>
            <strong className="free">FREE</strong>
          </div>

          <div className="summary-divider"></div>

          <div className="summary-total">
            <div>
              <strong>Total Amount</strong>
              <span>Inclusive of all applicable taxes</span>
            </div>

            <strong>₹{totalPrice}</strong>
          </div>

          <div className="savings">✓ You are getting free delivery</div>

          <button
            type="button"
            className="checkout-button"
            onClick={handleCheckout}
          >
            Proceed to Checkout
          </button>
        </div>
      </div>

      <div className="secure-checkout">
        <div className="secure-icon">🔒</div>

        <div>
          <strong>100% Secure Checkout</strong>
        </div>
      </div>
    </div>
  );
}

export default Cart;
