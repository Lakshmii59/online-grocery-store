import { createContext, useContext, useMemo, useState, useEffect } from "react"
 
const CartContext = createContext()
 
export function CartProvider({ children }) {
 
    const getCustomerId = () => {
        const customer = JSON.parse(localStorage.getItem("customer"))
        return customer?.customerId
    }
 
    const getCartKey = () => {
        const customerId = getCustomerId()
        return customerId ? `cart_${customerId}` : null
    }
 
    const [cart, setCart] = useState(() => {
        const cartKey = getCartKey()
        if (!cartKey) {return []}
 
        const savedCart = localStorage.getItem(cartKey)
        return savedCart ? JSON.parse(savedCart) : []
    })
 
    useEffect(() => {
 
        const loadCustomerCart = () => {
            const cartKey = getCartKey() 
            if (!cartKey) {
                setCart([])
                return
            }
            const savedCart = localStorage.getItem(cartKey)
            setCart(savedCart ? JSON.parse(savedCart) : [])
        }
 
        window.addEventListener("customerChanged", loadCustomerCart)
        return () => {
            window.removeEventListener("customerChanged", loadCustomerCart)
        }},[])
 
    const addToCart = (product) => {
        setCart((currentCart) => {
            const updatedCart = currentCart.some(
                (item) => item.productId === product.productId
            )
                ? currentCart.map((item) =>
                    item.productId === product.productId ?
                 { ...item, quantity: item.quantity + 1 } : item
                ) : [...currentCart, { ...product, quantity: 1 }]
 
            const cartKey = getCartKey()
 
            if (cartKey) {
                localStorage.setItem(cartKey,JSON.stringify(updatedCart))
            }
            return updatedCart
        })
    }
 
    const increaseQuantity = (productId) => {
        setCart((currentCart) => {
            const updatedCart = currentCart.map((item) =>
                item.productId === productId ?
             { ...item, quantity: item.quantity + 1 } : item)
 
            const cartKey = getCartKey()
 
            if (cartKey) {
                localStorage.setItem(cartKey,  JSON.stringify(updatedCart))
            }
 
            return updatedCart
        })
    }
 
    const decreaseQuantity = (productId) => {
        setCart((currentCart) => {
            const updatedCart = currentCart
                .map((item) =>
                    item.productId === productId ?
                 { ...item, quantity: item.quantity - 1 }: item
                ).filter((item) => item.quantity > 0)
 
            const cartKey = getCartKey()
 
            if (cartKey) {
                localStorage.setItem(cartKey,JSON.stringify(updatedCart))
            }
            return updatedCart
        })
    }
 
    const removeFromCart = (productId) => {
        setCart((currentCart) => {
            const updatedCart = currentCart.filter(
                (item) => item.productId !== productId
            )
 
            const cartKey = getCartKey()
 
            if (cartKey) {
                localStorage.setItem(cartKey,JSON.stringify(updatedCart))
            }
            return updatedCart
        })
    }
 
    const value = useMemo(
        () => ({
            cart,
            addToCart,
            increaseQuantity,
            decreaseQuantity,
            removeFromCart
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