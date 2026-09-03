import api from "./api";

export const getAllProducts = () => {
  return api.get("/api/products");
};

export const getProductsById = (productId) => {
  return api.get(`/api/products/${productId}`);
};

export const createProduct = (product) => {
  return api.post("/api/products", product);
};

export const updateProduct = (productId, product) => {
  return api.put(`/api/products/${productId}`, product);
};

export const deleteProduct = (productId) => {
  return api.delete(`/api/products/${productId}`);
};
