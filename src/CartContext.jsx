import { createContext, useContext, useMemo, useState } from "react"
 
const CartContext = createContext()
 
export function CartProvider({ children }) {
 
const [cart, setCart] = useState(() => {
   const savedCart = localStorage.getItem("cart")
   return savedCart ? JSON.parse(savedCart) : []
})
 
const addToCart = (product) => {
   setCart((currentCart) => {
 
     const updatedCart = currentCart.some(
       (item) => item.productId === product.productId)?
        currentCart.map((item) =>
           item.productId === product.productId ?
         { ...item, quantity: item.quantity + 1 }: item)
       : [...currentCart, { ...product, quantity: 1 }]
     localStorage.setItem("cart", JSON.stringify(updatedCart))
 
     return updatedCart
   })
}
 
const increaseQuantity = (productId) => {
   setCart((currentCart) => {
 
     const updatedCart = currentCart.map((item) =>
       item.productId === productId?
      { ...item, quantity: item.quantity + 1 }: item
     )
     localStorage.setItem("cart", JSON.stringify(updatedCart))
     return updatedCart
   })
}
 
const decreaseQuantity = (productId) => {
   setCart((currentCart) => {
 
     const updatedCart = currentCart
       .map((item) =>
         item.productId === productId ?
        { ...item, quantity: item.quantity - 1 }: item
       )
       .filter((item) => item.quantity > 0)
     localStorage.setItem("cart", JSON.stringify(updatedCart))
 
     return updatedCart
   })
}
 
const removeFromCart = (productId) => {
   setCart((currentCart) => {
 
     const updatedCart = currentCart.filter(
       (item) => item.productId !== productId
     )
     localStorage.setItem("cart", JSON.stringify(updatedCart))
 
     return updatedCart
   })
}
 
const value = useMemo(
   () => ({
     cart,
     addToCart,
     increaseQuantity,
     decreaseQuantity,
     removeFromCart,
   }),[cart])
 
return (
   <CartContext.Provider value={value}>
     {children}
   </CartContext.Provider>
)
}
 
export function useCart() {
return useContext(CartContext)
}