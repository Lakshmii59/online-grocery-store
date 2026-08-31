import api from "./api"

export const getAllProducts = () => {
    return api.get("/api/products")
}

export const getProductsById= (productId) => {
    return api.get(`/api/products/${productId}`)
}