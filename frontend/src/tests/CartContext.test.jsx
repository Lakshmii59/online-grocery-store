import { renderHook, act } from "@testing-library/react"
import { CartProvider, useCart } from "../CartContext"
 
const wrapper = ({ children }) => (
  <CartProvider>{children}</CartProvider>
)
 
describe("CartContext", () => {
  beforeEach(() => {
    localStorage.clear()
  })
 
  test("starts with an empty cart", () => {
    const { result } = renderHook(() => useCart(), { wrapper })
 
    expect(result.current.cart).toEqual([])
  })
 
  test("adds a product to the cart", () => {
    const { result } = renderHook(() => useCart(), { wrapper })
 
    const product = {
      productId: 1,
      productName: "Apple",
      price: 120
    }
 
    act(() => {
      result.current.addToCart(product)
    })
 
    expect(result.current.cart).toHaveLength(1)
    expect(result.current.cart[0].productName).toBe("Apple")
    expect(result.current.cart[0].quantity).toBe(1)
  })
 
  test("increases product quantity", () => {
    const { result } = renderHook(() => useCart(), { wrapper })
 
    const product = {
      productId: 1,
      productName: "Apple",
      price: 120
    }
 
    act(() => {
      result.current.addToCart(product)
      result.current.increaseQuantity(1)
    })
 
    expect(result.current.cart[0].quantity).toBe(2)
  })
 
  test("decreases product quantity", () => {
    const { result } = renderHook(() => useCart(), { wrapper })
 
    const product = {
      productId: 1,
      productName: "Apple",
      price: 120
    }
 
    act(() => {
      result.current.addToCart(product)
      result.current.increaseQuantity(1)
      result.current.decreaseQuantity(1)
    })
 
    expect(result.current.cart[0].quantity).toBe(1)
  })
 
  test("removes product from cart", () => {
    const { result } = renderHook(() => useCart(), { wrapper })
 
    const product = {
      productId: 1,
      productName: "Apple",
      price: 120
    }
 
    act(() => {
      result.current.addToCart(product)
      result.current.removeFromCart(1)
    })
 
    expect(result.current.cart).toEqual([])
  })
 
  test("clears the cart", () => {
    const { result } = renderHook(() => useCart(), { wrapper })
 
    const product = {
      productId: 1,
      productName: "Apple",
      price: 120
    }
 
    act(() => {
      result.current.addToCart(product)
      result.current.clearCart()
    })
 
    expect(result.current.cart).toEqual([])
  })
})