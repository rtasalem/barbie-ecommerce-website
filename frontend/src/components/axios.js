import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8088/api/v1/',
});

export const searchItems = async (searchTerm) => {
  try {
    const response = await api.get(`/items/search?searchTerm=${searchTerm}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching items:', error);
    throw error;
  }
};

/* searchItems makes a GET request to the API with given searchTerm that returns response.data */
/* errors are caught and logged */