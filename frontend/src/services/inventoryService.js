import axios from "axios";

const inventoryApi = axios.create({
  baseURL: "http://localhost:8082",
  headers: { "Content-Type": "application/json" },
});

export const getAllInventory = () => {
  return inventoryApi.get("/api/inventory");
};

export const getInventoryByProductId = (productId) => {
  return inventoryApi.get(`/api/inventory/${productId}`);
};

export const createInventory = (inventory) => {
  return inventoryApi.post("/api/inventory", inventory);
};

export const updateInventory = (productId, inventory) => {
  return inventoryApi.put(`/api/inventory/${productId}`, inventory);
};

export const deleteInventory = (productId) => {
  return inventoryApi.delete(`/api/inventory/${productId}`);
};
